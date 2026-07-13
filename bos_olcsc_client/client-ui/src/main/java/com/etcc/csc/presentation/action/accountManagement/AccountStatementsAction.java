/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.UIDateUtil;

public class AccountStatementsAction extends BaseReportAction {
    private static final Logger logger = Logger.getLogger(AccountStatementsAction.class);
    private static final String ERROR_PROPERTY = "viewStatementErrors";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        //         SessionUtil.getAcctDTO(request);
        HttpSession session = request.getSession();
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);
        if (accountLogin == null)
            throw new EtccSecurityException("session timed out in AccountStatementsAction");
        boolean getMonthsSuccess = getStatementMonths(request, accountLogin);
        SessionUtil.setTertiaryMenusInRequest(request);
        if (!getMonthsSuccess) {
            saveErrorMessages(request, new String[] {"No monthly statements available"}, ERROR_PROPERTY);
            return mapping.findForward("success");
        }

        DynaValidatorForm statementForm = (DynaValidatorForm)form;
        String statementMonth =
            ActionFormUtil.getString(statementForm, "statementMonth");

        if (StringUtil.isEmpty(statementMonth))
            statementMonth = getFirstMonthInList(request);

        retrieveStatement(request, accountLogin, statementMonth);

        if (StringUtil.stringToBoolean(request.getParameter("preview"))) {
            request.setAttribute("pageSize", Integer.valueOf(0));
            request.setAttribute("preview", Boolean.TRUE);
            return mapping.findForward("preview");
        } else {
            Date date = getDate(statementMonth);
            String args="p_acct_id="+accountLogin.getAcctId()+"&p_date="+UIDateUtil.getShortDate(date)+"&p_as_filter=DESCRIPTION&p_ts_filter=DESCRIPTION&p_sd_filter=DESCRIPTION";
            String result = this.processReport(request, response,
                    ReportInterface.ReportType.OL_STMT_REP,
                    "StatementsReport",
                    date,
                    args,
                    ERROR_PROPERTY);
            if (result != null)
                return mapping.findForward(result);

            request.setAttribute("pageSize", Integer.valueOf(10));
            request.setAttribute("preview", Boolean.FALSE);
            request.setAttribute("pdf", Boolean.FALSE);
            request.setAttribute("excel", Boolean.FALSE);
            return mapping.findForward("success");
        }
    }

    private Date getDate(String statementMonth) throws ParseException {
        boolean debugEnabled = logger.isDebugEnabled();
        String dateStr = "01-" + statementMonth.substring(0, 3) + "-"
            + statementMonth.substring(statementMonth.length() - 4, statementMonth.length());
        if (debugEnabled) {
            logger.debug("Date : " + dateStr);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = dateFormat.parse(dateStr);
        if (debugEnabled) {logger.debug("date" + date);
        }
        return date;
    }

    private boolean getStatementMonths(HttpServletRequest request, AccountLoginDTO accountLogin) throws Exception {
        AccountHistoryDelegate acctHistoryDelegate =
            new AccountHistoryDelegate();
        StatementDatesDTO datesDTO;
        datesDTO = acctHistoryDelegate.getStatementDates(accountLogin, true);
        //        DynaValidatorForm statementForm = (DynaValidatorForm) form;
        Collection<LabelValueBean> dateComboList;
        dateComboList = new ArrayList<LabelValueBean>();

        String[] dates = null;

        if (datesDTO != null)
            dates = datesDTO.getDates();

        if (dates != null && dates.length > 0) {
            for (String dateString : dates) {
            //            boolean setDefaultMonth = false;
                //                if (!setDefaultMonth && statementForm.getString("statementMonth")==null) {
                //                    statementForm.set("statementMonth", dateString);
                //                    setDefaultMonth = true;
                //                }
                dateComboList.add(new LabelValueBean(dateString, dateString));
            }
        } else {
            logger.info("No month returned from api");
            return false;
        }

        request.setAttribute("monthList", dateComboList);
        return true;
    }

    private String getFirstMonthInList(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Collection<LabelValueBean> monthList =
        	(Collection<LabelValueBean>)request.getAttribute("monthList");
        for (LabelValueBean valueBean : monthList)
            return valueBean.getValue();
        return null;
    }

    private void retrieveStatement(HttpServletRequest request,
            AccountLoginDTO accountLogin,
            String statementMonth) throws Exception {
        AccountHistoryDelegate delegate = new AccountHistoryDelegate();
        AccountStatementDTO statementDTO =
            delegate.getStatementHTML(accountLogin,
                                      statementMonth, null);

        if (saveErrorMessages(request, statementDTO, ERROR_PROPERTY)) {
            request.setAttribute("itemsFound", null);
        } else if (statementDTO == null || statementDTO.getRecords() == null || statementDTO.getRecords().length == 0)
            request.setAttribute("itemsFound", "0");
        else
            request.setAttribute("itemsFound",
                                 (statementDTO.getRecords())[statementDTO.getRecords().length - 1].getSerialNumber());

        if (statementDTO == null || statementDTO.getRecordsHAS() == null ||
            statementDTO.getRecordsHAS().length == 0)
            request.setAttribute("itemsFoundHAS", "0");
        else
            request.setAttribute("itemsFoundHAS",
                                 (statementDTO.getRecordsHAS())[statementDTO.getRecordsHAS().length - 1].getSerialNumber());

        request.setAttribute("accountStatement", statementDTO);
        request.setAttribute("statementMonth", statementMonth);
    }
}
