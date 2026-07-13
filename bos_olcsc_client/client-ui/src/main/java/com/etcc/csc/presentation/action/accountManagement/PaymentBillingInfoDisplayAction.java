/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.presentation.datatype.BillingContext;
import com.etcc.csc.presentation.form.BillingInfoForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PaymentBillingInfoDisplayAction extends Action {
    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    	String type = request.getParameter("type");
    	BillingContext billingContext = (BillingContext) request.getSession().getAttribute("billingContext");
    	BillingInfoForm billingInfoForm = billingContext.getBillingInfoForm();

    	billingInfoForm.setPaymentType(type);
    	billingInfoForm.cleanupBillingInfo();

    	String pageReturn = request.getParameter("pageReturn");
    	request.setAttribute("retPageInfo", Boolean.valueOf((pageReturn != null && pageReturn.equalsIgnoreCase("info"))));
    	request.setAttribute("billingInfoForm", billingInfoForm);


    	AppDelegate appDel = new AppDelegate();
		request.setAttribute("LITLE_PAYPAGE_URL",appDel.getSysParam("LITLE_PAYPAGE_URL"));
        request.setAttribute("LITLE_PAYPAGE_ID",appDel.getSysParam("LITLE_PAYPAGE_ID"));
        request.setAttribute("LITLE_REPORT_GROUP",appDel.getSysParam("LITLE_REPORT_GROUP"));

//            String changePayMethod = request.getParameter("changePayMethod");
//            if ("y".equalsIgnoreCase(changePayMethod))
//                request.setAttribute("changePayMethod", "true");
    	if (request.getParameter("ezTagsExist") != null)
    	    request.setAttribute("ezTagsExist", request.getParameter("ezTagsExist"));
                if(request.getParameter("mutipleUpload")!=null)
                  request.setAttribute("mutipleUpload",request.getParameter("mutipleUpload"));
            return mapping.findForward("success");
    }
}
