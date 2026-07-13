/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.report.ReportParamBean;
import com.etcc.csc.util.UIDateUtil;
import com.etcc.csc.util.SessionUtil;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DisplayPDFTransactionsAction extends DisplayTransactionsAction{
    private static final Logger logger = Logger.getLogger(DisplayPDFTransactionsAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response)
      throws Exception
    {
        AccountHistoryDelegate ahDelegate = new AccountHistoryDelegate();
        String vpnType = getVPNType(request);
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        if (accountLogin == null)
              throw new EtccSecurityException();

        AccountTransactionDTO txnDTO = ahDelegate.viewTransactionMain(accountLogin, null,null,vpnType);

        logger.info("pdfUrl:"+txnDTO.getPdfLink());

        String startString = request.getParameter("fromDate");
        Calendar startCal =  Calendar.getInstance();
        if (startString != null)
            startCal.setTime(UIDateUtil.parseDate(startString));

        String endString = request.getParameter("toDate");
        Calendar endCal = Calendar.getInstance();
        if (endString != null) 
            endCal.setTime(UIDateUtil.parseDate(endString));

        String tagIdString = request.getParameter("tagId");
        String agencyId = null;
        String tagId = null;
        if(tagIdString != null) {
            int pos = tagIdString.indexOf("$|$");
            if (pos > 0) {
            	agencyId = tagIdString.substring(0,pos);
            	tagId = tagIdString.substring(pos+3);
            }
        }

        String transactionTypeId = request.getParameter("transactionType");
        String transTypeDescription =  getTransTypeDesc(txnDTO.getTxnTypes(), transactionTypeId);
        if (transTypeDescription != null && transTypeDescription.length()>0)
            transTypeDescription = transTypeDescription.toUpperCase();
        if (transactionTypeId!=null && (transactionTypeId.equals("")||transactionTypeId.equals("0")))
            transactionTypeId = null;

        String url = buildPDFParameterString(txnDTO.getPdfLink(), accountLogin, startString, endString, agencyId, tagId, transTypeDescription,request);
        request.setAttribute( ReportParamBean.KEY, url);

        return mapping.findForward("pdfResult");
    }
}
