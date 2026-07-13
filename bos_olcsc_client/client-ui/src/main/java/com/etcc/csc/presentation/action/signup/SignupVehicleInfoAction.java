/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import com.etcc.csc.presentation.form.TagRequestForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SignupVehicleInfoAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

        TagRequestForm tagRequestForm = (TagRequestForm) form;
        if( !tagRequestForm.isFromConfirmation())
            return mapping.findForward("success");
        else
        {

//            HttpSession session = request.getSession();
//            if (session != null)
//            {
//                BillingInfoForm billingInfoForm = (BillingInfoForm) request.getSession().getAttribute("billingInfoForm");
//                if (billingInfoForm != null)
//                request.setAttribute("primaryCardName", new CreditCardDelegate().getCreditCardName(billingInfoForm.getCardTypeEnum()));
//            }
            if (tagRequestForm.setSavedVehicles(request))
                 request.getSession().setAttribute("tagRequestForm", tagRequestForm);
            return mapping.findForward("confirmation");
        }
	}
}
