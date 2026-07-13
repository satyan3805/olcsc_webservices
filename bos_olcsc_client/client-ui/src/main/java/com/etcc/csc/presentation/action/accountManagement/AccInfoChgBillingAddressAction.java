/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressUS;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.util.SessionUtil;

public class AccInfoChgBillingAddressAction extends CSCBaseAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception {
        BillingInfoForm billingInfoForm = (BillingInfoForm)form;
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDelegate acctDel = new AccountDelegate();
        AccountDTO accountDTO = SessionUtil.getAcctDTO(request);
        if (!billingInfoForm.isNonUsBillingAddress())
            billingInfoForm.setCountry(AddressUS.COUNTRY_CODE_USA);
        AccountLoginDTO result = acctDel.updateBillingAddress(acctLogin, billingInfoForm);
        if (saveErrorMessages(request, result, "changeBillingAddressError"))
            return mapping.findForward("failure");
        request.setAttribute("billingInfoForm", billingInfoForm);
        request.setAttribute(SessionUtil.REQUEST_ACCOUNT_INFO, accountDTO);
        //SessionUtil.getAcctDTO(request);         
        request.setAttribute("setEvent3", Boolean.TRUE);
        return mapping.findForward("success");
    }
}
