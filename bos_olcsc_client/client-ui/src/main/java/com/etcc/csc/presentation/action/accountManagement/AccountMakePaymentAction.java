/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.util.SessionUtil;

public class AccountMakePaymentAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
         request.getSession().removeAttribute("billingContext");

         AccountDTO acctDto = SessionUtil.getAcctDTO(request);
         AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
         if (loginDTO == null)
             throw new EtccSecurityException("Session Timed out in AccountMakePaymentAction");

         acctDto = new AccountDelegate().getAccountStatus(loginDTO, acctDto);

         if (acctDto.isSuspensionFlag() ) {
             ActionMessages messages = getMessages(request);
             if (acctDto.isViolationFlag())
             {
                 ActionMessage msg = new ActionMessage("account.suspended.with.violation", 
                         acctDto.getViolationLicPlatesDisplay(), 
                         request.getContextPath()+"/violatorLoginDisplay.do?returnAction=accountMakePayment");
                 messages.add("alerts", msg);
             }
             else {
                 ActionMessage msg = new ActionMessage("account.suspended.without.violation");
                 messages.add("alerts", msg);
             }
             saveMessages(request, messages);
         }
         if(request.getParameter("page")!=null && request.getParameter("page").equalsIgnoreCase("info"))
         {
           request.setAttribute("retPageInfo",Boolean.TRUE);
         }else 
           request.setAttribute("retPageInfo",Boolean.FALSE);
         return mapping.findForward("success");
     }
}
