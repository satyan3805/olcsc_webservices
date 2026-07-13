package com.etcc.csc.presentation.action;

import com.etcc.csc.delegate.CreditCardDelegate;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.util.ActionFormUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class GetTollTagPaymentInfoAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {

        request.setAttribute("states", new StateDelegate().getStates());
        request.getSession().setAttribute("creditCardTypes", 
                                          new CreditCardDelegate().getCreditCardTypes());
        populateForm(form);                                          
        return mapping.findForward("success");
    }

    private void populateForm(ActionForm form) {

        DynaActionForm dynaForm = (DynaActionForm)form;

        if (StringUtils.isEmpty(ActionFormUtil.getString(dynaForm, 
                                                         "billingName"))) {
            dynaForm.set("billingName", 
                         ActionFormUtil.getString(dynaForm, "firstName") + 
                         " " + ActionFormUtil.getString(dynaForm, "lastName"));
        }

        if (StringUtils.isEmpty(ActionFormUtil.getString(dynaForm, 
                                                         "billingAddress")) && 
            StringUtils.isEmpty(ActionFormUtil.getString(dynaForm, 
                                                         "billingAddressLine2"))) {
            dynaForm.set("billingAddress", 
                         ActionFormUtil.getString(dynaForm, "address"));
            dynaForm.set("billingAddressLine2", 
                         ActionFormUtil.getString(dynaForm, "addressLine2"));

        }

        if (StringUtils.isEmpty(ActionFormUtil.getString(dynaForm, 
                                                         "billingCity"))) {
            dynaForm.set("billingCity", 
                         ActionFormUtil.getString(dynaForm, "city"));

        }

        if (StringUtils.isEmpty(ActionFormUtil.getString(dynaForm, 
                                                         "billingState"))) {
            dynaForm.set("billingState", 
                         ActionFormUtil.getString(dynaForm, "state"));

        }

        if (StringUtils.isEmpty(ActionFormUtil.getString(dynaForm, 
                                                         "billingZipcode")) && 
            StringUtils.isEmpty(ActionFormUtil.getString(dynaForm, 
                                                         "billingPlus4"))) {
            dynaForm.set("billingZipcode", 
                         ActionFormUtil.getString(dynaForm, "zipcode"));
            dynaForm.set("billingPlus4", 
                         ActionFormUtil.getString(dynaForm, "plus4"));
        }
    }
}
