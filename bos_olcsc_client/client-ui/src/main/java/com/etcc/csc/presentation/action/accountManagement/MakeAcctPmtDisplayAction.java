/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.presentation.datatype.BillingContext;

import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MakeAcctPmtDisplayAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        BillingContext billingContext = null;

        HttpSession session = request.getSession();
        if (session == null)
            throw new EtccSecurityException("session timed out in MakeAcctPmtDisplayAction");

        Object objBilling = session.getAttribute("billingContext");
        String strAmt =  request.getParameter("payAmount");

        request.setAttribute("retPageInfo",
                Boolean.valueOf(StringUtil.equalsIgnoreCase(request.getParameter("pageReturn"), "info")));

        if (objBilling != null) {
            billingContext = (BillingContext) objBilling;
            BillingInfoForm billingInfoForm = billingContext.getBillingInfoForm();
            if (billingInfoForm != null && billingInfoForm.getPaymentTypeEnum() != null) {
                billingContext.setPaymentAmt(Double.parseDouble(strAmt));
                billingContext.setEntryPoint("acctPmt");
                session.setAttribute("billingContext", billingContext);
                return mapping.findForward("confirmation");
            }
        }

        billingContext = new BillingContext();
        billingContext.setPaymentAmt(Double.parseDouble(strAmt));
        billingContext.setEntryPoint("acctPmt");

        BillingInfoDTO billingInfoDTO = SessionUtil.getBillingInfo(request);
        if (billingInfoDTO != null) {
            BillingInfoForm billingInfoForm = new BillingInfoForm(billingInfoDTO);
            billingInfoForm.setPaymentTypeEnum(null);
            billingContext.setBillingInfoForm(billingInfoForm);
            request.setAttribute("billingInfoForm", billingInfoForm);
        }
        session.setAttribute("billingContext", billingContext);

        return mapping.findForward(SessionUtil.REQUEST_BILLING_INFO);
    }
}
