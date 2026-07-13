/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.presentation.datatype.BillingContext;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PaymentConfirmationDisplayAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                                 throws Exception {

        HttpSession session = request.getSession();
        BillingInfoForm billingInfoForm = (BillingInfoForm) form;
        if (! billingInfoForm.isNonUsBillingAddress()) {
            billingInfoForm.setCountry("USA");
        }
        String pageReturn = request.getParameter("pageReturn");
        request.setAttribute("retPageInfo", Boolean.valueOf((pageReturn != null && pageReturn.equalsIgnoreCase("info"))));

        if (request.getParameter("ezTagsExist") != null)
            request.setAttribute("ezTagsExist", request.getParameter("ezTagsExist"));
      if(request.getParameter("mutipleUpload")!=null)
        request.setAttribute("mutipleUpload",request.getParameter("mutipleUpload"));

        BillingContext billingContext = (BillingContext) session.getAttribute("billingContext");
        SessionUtil.getAcctDTO(request);
        billingContext.setBillingInfoForm(billingInfoForm.upperCaseAddress());
        session.setAttribute("billingContext", billingContext);
        return mapping.findForward("success");
    }
}
