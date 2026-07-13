/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountYearlySummaryDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.UIDateUtil;

public class AccountYearlySummaryAction extends BaseReportAction {
//    private static final Logger logger = Logger.getLogger(AccountYearlySummaryAction.class);
    private static final String ERROR_PROPERTY = "viewYearlySummaryErrors";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        //         SessionUtil.getAcctDTO(request);

        HttpSession session = request.getSession();
        AccountHistoryDelegate acctHistoryDelegate =
            new AccountHistoryDelegate();
        AccountLoginDTO accountLogin =
            SessionUtil.getSessionAccountLogin(session);
        if (accountLogin == null)
            throw new EtccSecurityException("session timed out in AccountYearlySummaryAction");

        SessionUtil.setTertiaryMenusInRequest(request);

        Collection<LabelValueBean> years = getSummaryYears(session);

        if (years == null || years.size() == 0) {
            ErrorMessageDTO errorMessage = new ErrorMessageDTO().withMessage("No yearly summary available");
            saveErrorMessage(request, errorMessage , ERROR_PROPERTY);
            return mapping.findForward("success");
        }

        DynaValidatorForm summaryForm = (DynaValidatorForm)form;
        String summaryYear =
            ActionFormUtil.getString(summaryForm, "summaryYear");
        if (summaryYear == null || summaryYear.trim().length() == 0) {
            summaryYear = UIDateUtil.getLastYear() + "";
        }

        AccountYearlySummaryDTO summaryDTO =
            acctHistoryDelegate.getSummaryHTML(accountLogin,
                                               "January, " + summaryYear);
        //         if (summaryDTO==null ||
        //            ((summaryDTO.getAcctSummary()==null || summaryDTO.getAcctSummary().length==0) &&
        //            ((summaryDTO.getTagSummary()==null || summaryDTO.getTagSummary().length==0))))
        //            return mapping.findForward("failure");

        //         request.setAttribute("valid", summaryAvailDTO.isSummaryAvail());
        request.setAttribute("accountSummary", summaryDTO);
        request.setAttribute("summaryYear", summaryYear);
        request.setAttribute("summaryYears", years);

        if (StringUtil.stringToBoolean(request.getParameter("preview"))) {
            request.setAttribute("pageSize", Integer.valueOf(0));
            request.setAttribute("preview", Boolean.TRUE);
            return mapping.findForward("preview");
        } else {
            String args="p_acct_id="+accountLogin.getAcctId()+"&p_date=01/01"+summaryYear;
            String result = this.processReport(request, response,
                    ReportInterface.ReportType.YEAR_REP,
                    "YearlyReport",
                    null,
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

    private Collection<LabelValueBean> getSummaryYears(HttpSession session) throws Exception {
        AccountHistoryDelegate acctHistoryDelegate =
            new AccountHistoryDelegate();
        StatementDatesDTO datesDTO;
        AccountLoginDTO accountLogin =
            SessionUtil.getSessionAccountLogin(session);
        if (accountLogin == null)
            throw new EtccSecurityException();
        datesDTO = acctHistoryDelegate.getStatementDates(accountLogin, false);

        Collection<LabelValueBean> dateComboList;
        dateComboList = new ArrayList<LabelValueBean>();

        String[] dates = null;

        if (datesDTO != null)
            dates = datesDTO.getDates();

        if (dates != null && dates.length > 0) {
            for (String dateString : dates) {
                dateComboList.add(new LabelValueBean(dateString, dateString));
            }
        } else {
            return null;
        }
        return dateComboList;
    }
}
