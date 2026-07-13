/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountReceiptDetailsDTO;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.util.SessionUtil;

public class AccountReceiptsDetailAction extends BaseReportAction {
    private static final Logger logger = Logger.getLogger(AccountReceiptsDetailAction.class);
    private static final String ERROR_PROPERTY = "viewReceiptDetailErrors";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	HttpSession session = request.getSession();
    	int menuId = SessionUtil.getAcctMgmtTabMenuIdByLabel(session, "Account Activity");
        SessionUtil.setCurrentAcctMgmtTabMenuId(session, menuId);
        SessionUtil.setTertiaryMenusInRequest(request);
        String transactionID = request.getParameter("transactionID");
        String HASFlag = request.getParameter("HASFlag");
        logger.debug("transactionID=" + transactionID);
        logger.debug("HASFlag=" + HASFlag);
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        logger.debug("startDate=" + startDate);
        logger.debug("endDate=" + endDate);

        String args = "p_retail_trans_id=" + transactionID;
        String result = processReport(request, response,
                ReportInterface.ReportType.RCPT_REP,
                "ReceiptsReport",
                new Date(),
                args,
                ERROR_PROPERTY);
        if (result != null)
            return mapping.findForward(result);

        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(request.getSession());

       AccountHistoryDelegate acctHistoryDelegate =
            new AccountHistoryDelegate();
        AccountReceiptDetailsDTO accountReceiptDetails =
            acctHistoryDelegate.getAccountReceiptDetails(accountLogin,
                                                         Long.parseLong(transactionID),
                                                         HASFlag);
        accountReceiptDetails.setTransactionID(Long.parseLong(transactionID));

        request.setAttribute("accountReceiptDetails", accountReceiptDetails);
        request.setAttribute("transactionID",transactionID);
        request.setAttribute("HASFlag",HASFlag);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        SessionUtil.getAcctDTO(request);
        return mapping.findForward("success");
    }
}
