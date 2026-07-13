/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.lf5.util.StreamUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.MultiVehicleUploadDelegate;
import com.etcc.csc.delegate.ReportDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.FileMultiVehicleUploadDTO;
import com.etcc.csc.dto.MultiVehicleUploadDTO;
import com.etcc.csc.dto.ReportDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.util.HttpResponseUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Action for handling the upload of Multiple Vehicles.
 */
public class AccountUploadMultipleVehicleAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccountUploadMultipleVehicleAction.class);

    private static final String FILE_TEMPLATE_NAME = "Multiple_Vehicle_Upload_Template.xls";
    private static final String FILE_INSTRUCTIONS_NAME = "Multiple_Vehicle_Upload_Instructions.pdf";
    private static final String FILENAME_SUFFIX_CSV = ".csv";
    private static final int MAX_ROW_NUMBER = 1500;
    private static final int MIN_ROW_NUMBER = 1;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // TagRequestForm tagRequestForm = (TagRequestForm) form;
        HttpSession session = request.getSession();
        AccountLoginDTO loginDTO =  SessionUtil.getSessionAccountLogin(session);
        if (loginDTO == null)
            throw new EtccSecurityException("Session Timed out in AccountUploadMultipleVehicleAction");
        session.removeAttribute("errorVehicleRecords");
        DynaValidatorForm actionForm = (DynaValidatorForm)form;
        logger.debug("retailTransId :"+request.getParameter("retailTransId"));
        request.setAttribute("retailTransId", "retailTransId");
        request.setAttribute("reportId","0");
        // for PDF report
        String reportIdParam = request.getParameter("reportId");
        if (StringUtil.stringToBoolean(request.getParameter("PDF"))
                && !StringUtil.isEmpty(reportIdParam)) {
            ReportDelegate reDelegate = new ReportDelegate();
            String args = "p_report_id=" + reportIdParam;
            ReportDTO report = reDelegate.getReport(
                    ReportInterface.ReportType.MVU_REPORT,
                    FileFormat.PDF,
                    args,
                    loginDTO);
            request.setAttribute("byteArray", report.getBytes());
            return mapping.findForward("pdf");
        }

        if (Boolean.parseBoolean(request.getParameter("success"))) {
            MultiVehicleUploadDTO outputDTO = (MultiVehicleUploadDTO)session.getAttribute("multiVehicleUploadDTO");
            if (outputDTO != null) {
//                TagDTO[] addTagList = outputDTO.getMultiVehicleAddTags();
//                if (addTagList.length > 0) {
//                    SessionUtil.setVehicles(request, addTagList);
//                    request.setAttribute("retailTransId", Long.valueOf(outputDTO.getTransactionId()));
//                    request.setAttribute("activationAmount", Double.valueOf(outputDTO.getTagActivateAmt()));
//                    request.setAttribute("totalAmount", Double.valueOf(outputDTO.getTotalAmt()));
//                    request.setAttribute("depositAmount", Double.valueOf(outputDTO.getMinBalAmt()));
//                    request.setAttribute("ezTagsExist", Boolean.valueOf(outputDTO.isAddEzTagsExist()));
//                    request.setAttribute("mutipleUpload", "M");
//                    resetToken(request);
//                    saveToken(request);
//                    return mapping.findForward("paymentConfirm");
//                } else {
                    return mapping.findForward("vehiclesAndTags");
//                }
            }
        }
        // Process the FormFile
        FormFile myFile = (FormFile)actionForm.get("uploadFile");
        if (myFile != null && myFile.getFileSize() > 0) {
            String contentType = myFile.getContentType();
            logger.debug("MVU file content type: " + contentType);
            String remoteFileDir = new AppDelegate().getSysParam("FEP_MVU_FOLDER_MVU_REMOTE_CURRENT");
            String fileName = myFile.getFileName();
			if (!FileFormat.XLS.isValidContentType(contentType)) {
                logger.info("Invalid MVU content type: " + contentType);
                // If CSV file type is not associated with MS Excel, content type of "application/octet-stream" will be provided
                if ((contentType == null || FileFormat.UNKNOWN.isValidContentType(contentType))
                        && fileName.toLowerCase().endsWith(FILENAME_SUFFIX_CSV)) {
                    logger.info("MVU file suffix is valid: " + fileName);
                } else {
                    ErrorMessageDTO errorMessage = new ErrorMessageDTO();
                    errorMessage.setMessage("The File is not CSV. Please Upload CSV file only");
                    saveErrorMessage(request, errorMessage, "saveFailed");
                    return mapping.findForward("success");
                }
            }
			
            MultiVehicleUploadDelegate mvuDelegate = new MultiVehicleUploadDelegate();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(myFile.getFileData());
            FileOutputStream output = new FileOutputStream(remoteFileDir+"/"+fileName);
			StreamUtils.copy(byteArrayInputStream, output);
			output.close();
			File file = new File(remoteFileDir+"/"+fileName);
			//Defect #2656
			int totalRecord = countMVUUpload(remoteFileDir + "/" + fileName);
			if(totalRecord > MAX_ROW_NUMBER) {
				file.delete();
				ErrorMessageDTO errorMessage = new ErrorMessageDTO();
                errorMessage.setMessage("Total records in the file are lower than the limit, file has a record limit for 1 - 1500 records");
                saveErrorMessage(request, errorMessage, "saveFailed");
                return mapping.findForward("success");
			}
			//End Defect #2656
			//Defect #2542
			if(totalRecord < MIN_ROW_NUMBER) {
				file.delete();
				ErrorMessageDTO errorMessage = new ErrorMessageDTO();
                errorMessage.setMessage("Uploaded file doesn't have any vehicles to upload.");
                saveErrorMessage(request, errorMessage, "saveFailed");
                return mapping.findForward("success");
			}
			//END Defect #2542
			
			String checkSum = org.apache.commons.codec.digest.DigestUtils.md5Hex(myFile.getFileData());
            FileMultiVehicleUploadDTO uploadRequest = new FileMultiVehicleUploadDTO();
            uploadRequest.setFileName(fileName);
            uploadRequest.setFileLocation(remoteFileDir);
            uploadRequest.setFileCheckSum(checkSum);
            MultiVehicleUploadDTO outputDTO = mvuDelegate.loadMultipleVehicles(loginDTO,uploadRequest );
            if (outputDTO != null && ((outputDTO.getErrors() != null && outputDTO.getErrors().length > 0) ||
            		outputDTO.getMultiVehicleTags() != null && outputDTO.getMultiVehicleTags().length >0 )) {
                saveErrorMessages(request, outputDTO.getErrors(), "saveFailed");
                if (outputDTO.getMultiVehicleTags().length >0 )
                {
                	saveErrorReportMessage(request,outputDTO);
                }
                request.getSession().setAttribute("errorVehicleRecords", outputDTO.getMultiVehicleTags());
            } else {
                // for success message
                saveCustomAlert(request,outputDTO.getVehicleAddCount(),outputDTO.getVehicleInactivateCount());
                session.setAttribute("multiVehicleUploadDTO",outputDTO);
            }
            return mapping.findForward("success");
        }





        if (StringUtil.stringToBoolean(request.getParameter("upload"))) {
            return mapping.findForward("upload");
        } else if (StringUtil.stringToBoolean(request.getParameter("inst"))) {

            HttpResponseUtil.streamFile(response, session.getServletContext(), FILE_INSTRUCTIONS_NAME, FileFormat.PDF);
            return mapping.findForward("inst");

        } else if (StringUtil.stringToBoolean(request.getParameter("download"))) {

            HttpResponseUtil.streamFile(response, session.getServletContext(), FILE_TEMPLATE_NAME, FileFormat.XLS);
            return mapping.findForward("download");
        }
        return mapping.findForward("success");
    }

    private void saveErrorReportMessage(HttpServletRequest request, MultiVehicleUploadDTO outputDTO) {
        String text =" Errors. Click <a href=\"#pdf\" onclick=\"javascipt:downloadPDF();\">here</a> to download the errors";
        ErrorMessageDTO message = new ErrorMessageDTO();
        message.setMessage(text);
        saveErrorMessage(request, message, "saveFailed");
    }

    private void saveCustomAlert(HttpServletRequest request, int addCount, int inactiveCount) {
        StringBuilder text = new StringBuilder("Your file was uploaded successfully. ");
        if (inactiveCount > 0)
            text.append(" '").append(inactiveCount).append("' vehicles have been inactivated");
        if (inactiveCount > 0 && addCount > 0)
            text.append(" and");
        if (addCount > 0)
            text.append(" '").append(addCount).append("' vehicles added to your account");
        AlertDTO alert = new AlertDTO();
        alert.setAlertMsg(text.append(". Click on <a href=\"##\" onclick=\"javascript:proceedToContinue();\">Continue</a> to proceed.").toString());
        saveAlert(request, alert, "alertMsg");
    }
    
    private int countMVUUpload(String pathFileCSV) {
		File file = new File(pathFileCSV);
		int count = 0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while (br.readLine() != null) {
				count++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		count = count - 1; // header
		return count;
	}
}
