package com.etcc.csc.presentation.action.accountManagement;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountReceiptsDTO;
import com.etcc.csc.presentation.form.AccountReceiptsForm;
import com.etcc.csc.util.UIDateUtil;
import com.etcc.csc.util.SessionUtil;

public class AccountReceiptsAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                                 throws Exception
     {
//         SessionUtil.getAcctDTO(request);

        AccountReceiptsForm accountReceiptsForm = (AccountReceiptsForm)form;
        SessionUtil.setTertiaryMenusInRequest(request);

        HttpSession session = request.getSession();
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);

        if ((accountReceiptsForm != null) && (accountReceiptsForm.getStartDate() != null)) {
            Calendar startDate = UIDateUtil.stringToCalendar(accountReceiptsForm.getStartDate());
            Calendar endDate = UIDateUtil.stringToCalendar(accountReceiptsForm.getEndDate());

            AccountHistoryDelegate acctHistoryDelegate = new AccountHistoryDelegate();
            AccountReceiptsDTO accountReceipts = acctHistoryDelegate.getAccountReceipts(accountLogin, startDate, endDate);

            request.setAttribute("accountReceipts", accountReceipts);
        }

         if ((accountReceiptsForm == null) || (accountReceiptsForm.getStartDate() == null)) {
            Calendar end = new GregorianCalendar();
            Calendar start = new GregorianCalendar();
            start.add(Calendar.MONTH, -1);
            request.setAttribute("startDate", UIDateUtil.getShortDate(start));
            request.setAttribute("endDate", UIDateUtil.getShortDate(end));
            System.out.println("***AccountReceiptsAction.start=" + UIDateUtil.getShortDate(start) + ";end=" + UIDateUtil.getShortDate(end));
         } else {
             request.setAttribute("startDate", accountReceiptsForm.getStartDate());
             request.setAttribute("endDate", accountReceiptsForm.getEndDate());
         }

        request.setAttribute("accountReceiptsForm", accountReceiptsForm);
        return mapping.findForward("success");
     }
}
