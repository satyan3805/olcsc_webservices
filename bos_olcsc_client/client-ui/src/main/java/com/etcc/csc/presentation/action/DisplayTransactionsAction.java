/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action;

import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.bean.TxnTypeBean;
import com.etcc.csc.presentation.viewhelper.TransactionsViewHelper;
import com.etcc.csc.report.ReportParamBean;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.UIDateUtil;

public class DisplayTransactionsAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(DisplayTransactionsAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response)
    throws Exception
    {
        String startIndex=ActionFormUtil.getString((DynaActionForm) form, "startIndex");

        DynaValidatorForm dynaForm = (DynaValidatorForm)form;

        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        if (accountLogin == null)
              throw new EtccSecurityException();

        AccountHistoryDelegate ahDelegate = new AccountHistoryDelegate();

        String vpnType = getVPNType(request);

        AccountTransactionDTO txnDTO = ahDelegate.viewTransactionMain(accountLogin, null,null,vpnType);

        logger.info("pdfUrl:"+txnDTO.getPdfLink());
        logger.info("xslUrl:"+txnDTO.getXslLink());

        Object buttonClicked = request.getParameter("buttonClicked");
        AccountDelegate accountDel = new AccountDelegate();
        boolean isBigAccount = accountDel.isBigAccount(accountLogin.getAcctId());

        if (isBigAccount && (buttonClicked == null || !buttonClicked.toString().equalsIgnoreCase("Y"))) {
            dynaForm.set("startDate", "");
            dynaForm.set("endDate", "");

            ErrorMessageDTO msg = new ErrorMessageDTO("TransactionsForm.error.bigAccount", null);
            saveErrorMessage(request, msg, "viewTransactonErrors");

            request.setAttribute("itemsFound", null);
            request.setAttribute("transactionsViewHelper", new TransactionsViewHelper(txnDTO.getAcctTags(), 
                    null, txnDTO.getTxnTypes(), startIndex));
            request.setAttribute("startDate", "");
            request.setAttribute("endDate", "");

            return mapping.findForward("success");
        }

        String startString = dynaForm.getString("startDate");
        Calendar startCal =  Calendar.getInstance();
        if (startString != null)
            startCal.setTime(UIDateUtil.parseDate(startString));

        String endString = dynaForm.getString("endDate");
        Calendar endCal = Calendar.getInstance();
        if (endString != null) 
            endCal.setTime(UIDateUtil.parseDate(endString));
            
        String tagIdString = dynaForm.getString("tagId");
        String agencyId = null;
        String tagId = null;
        if(tagIdString != null) {
            int pos = tagIdString.indexOf("$|$");
            if (pos > 0) {
                agencyId = tagIdString.substring(0,pos);
                tagId = tagIdString.substring(pos+3);
            }
        }

        String transactionTypeId = dynaForm.getString("transactionType");
        if (transactionTypeId!=null && (transactionTypeId.equals("")||transactionTypeId.equals("0")))
            transactionTypeId = null;
            
        String transTypeDescription =  getTransTypeDesc(txnDTO.getTxnTypes(), transactionTypeId);

        String reportFormat = dynaForm.getString("reportFormat");
            
        logger.info("startDate:"+startString+" endDate:"+endString+" agencyId:"+agencyId+" tagId:"+tagId+" transactionTypeId:"+transactionTypeId+" reportFormat:"+reportFormat);

        if (reportFormat.equalsIgnoreCase("xls")) {
            String url = txnDTO.getXslLink();
            if (transTypeDescription != null && transTypeDescription.length()>0)
                transTypeDescription = transTypeDescription.toUpperCase();
            url = buildParameterString(url, accountLogin, startString, endString, agencyId, tagId, transTypeDescription);
            request.setAttribute(ReportParamBean.KEY, url);
            return mapping.findForward("xlsResult");
        }

   //commented out because the function signature changed for HCTRA     
//      TransactionRecordsDTO transactionsDTO = ahDelegate.getTransactionRecords(accountLogin,startCal,endCal,agencyId,tagId,transactionTypeId,vpnType);
        // TODO: BUG: ERROR: THIS ASSIGNMENT WILL ALWAYS CAUSE A NPE TO BE GENERATED!!!
/*  COMMENT OUT ENTIRE CODE BLOCK SINCE IT CANNOT POSSIBLY EXECUTE DUE TO NPE
        TransactionRecordsDTO transactionsDTO = null;
        OlcAccountHistoryRecBean[] ahRecBeans = transactionsDTO.getRecords();
        request.setAttribute(TRANSACTIONS_TRANS_REC, ahRecBeans);

        if (saveErrorMessages(request, transactionsDTO, "viewTransactonErrors")){
            request.setAttribute("itemsFound", null);
        }
        else if (ahRecBeans == null || ahRecBeans.length==0) 
        {
            logger.info("no transactions from " + startString + " to "+ endString);
            request.setAttribute("itemsFound", "0");
        }
        else
            request.setAttribute("itemsFound", ahRecBeans[ahRecBeans.length-1].getSerialNum());

        TransactionsViewHelper transactionsViewHelper = 
            new TransactionsViewHelper(txnDTO.getAcctTags(),
                    ahRecBeans,
                    txnDTO.getTxnTypes(),
                    startIndex);

        request.setAttribute("transactionsViewHelper", transactionsViewHelper);
        request.setAttribute("startDate", startString);
        request.setAttribute("endDate", endString);
        request.setAttribute("transTypeDescription", transTypeDescription);
*/
        return mapping.findForward("success");
    }

    protected String getVPNType(HttpServletRequest request) throws Exception{
        String VPNType = null;
        AccountDTO acctDto = SessionUtil.getAcctDTO(request);
        if (acctDto != null) {
            VPNType = acctDto.getVpnType();
            request.setAttribute("vpnType", VPNType);
            logger.info("vpnType: " + VPNType);
        }
        return VPNType;
    }

    protected String buildParameterString(String url,AccountLoginDTO loginDTO, String startDate, String endDate, String agencyId, String tagId, String transType) throws Exception{
        StringBuilder sb = new StringBuilder(url);

        sb.append("&p_acct_no=");
        sb.append(loginDTO.getAcctId());
        sb.append("&p_session_id=");
        sb.append(loginDTO.getDbSessionId());
        sb.append("&p_user=");
        String loginId = loginDTO.getLoginId();
        loginId = URLEncoder.encode(loginId, "UTF-8");
        sb.append(loginId);

        sb.append("&p_ip_address=");
        sb.append(loginDTO.getLastLoginIp());

        sb.append("&p_begin_trans_date=");
        sb.append(startDate);
        sb.append("&p_end_trans_date=");
        sb.append(endDate);
        if(agencyId!=null) {
            sb.append("&p_agency_id=");
            sb.append(agencyId);
        }
    	// TODO: Which one is it?  Should the 'tagId' parameter be assigned the value "null", or should it not be listed??? 
        if(tagId != null) {
            sb.append("&p_tag_id=");
            sb.append(tagId);
        }
        sb.append("&p_trans_type=");
        sb.append(transType==null?"ALL":transType);

        return sb.toString();
    }

    protected String buildPDFParameterString(String url,AccountLoginDTO loginDTO, String startDate, String endDate, 
                                           String agencyId, String tagId, String transType, HttpServletRequest request)
                                           throws Exception{
        String urlPDF = buildParameterString(url,loginDTO,startDate,endDate,agencyId,tagId,transType);
        
        AppDelegate appDelegate = new AppDelegate();
        String appUrl = appDelegate.getApplicationUrl();
        
        URL reconstructedURL = new URL(appUrl+"/buildReportUrl.do");
        
        urlPDF += "&p_reporturl="+reconstructedURL.toString();
        return urlPDF;
    }

    protected String getTransTypeDesc(TxnTypeBean[] olcTransTypes, String transTypeId) {
        if (olcTransTypes != null && olcTransTypes.length >0 && transTypeId != null) {
            for (TxnTypeBean record : olcTransTypes) {
                BigDecimal transTypeIdDecimal = new BigDecimal(transTypeId);
                if (transTypeIdDecimal.equals(BigDecimal.valueOf(record.getTransTypeId())))
                    return record.getDescription();
            }
        }
        return null;
    }
}
