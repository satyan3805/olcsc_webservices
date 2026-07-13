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

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.util.SessionUtil;

public class AccInfoDispChgBillingAddressAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
         AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
         AccountDelegate acctDel = new AccountDelegate();
         AccountDTO accountDTO = SessionUtil.getAcctDTO(request);
         
        BillingInfoDTO billingInfo = acctDel.getBillingInfo(acctLoginDto);
        billingInfo.setBillingType(PaymentType.CREDIT);
        BillingInfoForm billingInfoForm = new BillingInfoForm(billingInfo);

        request.setAttribute("billingInfoForm", billingInfoForm);
        request.setAttribute(SessionUtil.REQUEST_ACCOUNT_INFO, accountDTO);         
        return mapping.findForward("success");
     }
}
