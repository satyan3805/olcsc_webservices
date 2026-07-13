/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.delegate.ReportDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ReportDTO;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.util.HttpResponseUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class AccountMultipleVehicleReportAction extends BaseReportAction {
	private static final Logger logger = Logger.getLogger(AccountMultipleVehicleReportAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        // TagRequestForm tagRequestForm = (TagRequestForm) form;
         logger.debug("AccountMultipleVehicleReportAction is enabled in execute START");
//         TagRequestDelegate delegate = new TagRequestDelegate();
         AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
         if (loginDTO == null)
             throw new EtccSecurityException("Session Timed out in AccountUploadMultipleVehicleAction");

//        DynaValidatorForm actionForm = (DynaValidatorForm)form;
        logger.debug("retailTransId :"+request.getParameter("retailTransId"));
        request.setAttribute("retailTransId","retailTransId");
        request.setAttribute("reportId","0");
        // for PDF report
        String reportId = request.getParameter("reportId");
//        if (StringUtil.stringToBoolean(request.getParameter("PDF")) && reportId != null) {
//            ReportDelegate reDelegate = new ReportDelegate();
//            String args="p_report_id="+reportId;
//            ReportDTO report = reDelegate.getReport(ReportInterface.ReportType.MVU_REPORT, FileFormat.PDF,args, loginDTO);
//            HttpResponseUtil.streamBytes(request, response, report.getBytes(), "Multiple_Vehicle_Upload_Report", FileFormat.PDF);
//        }

        return mapping.findForward("success");
    }
}
