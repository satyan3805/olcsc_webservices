/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.presentation.datatype.BillingContext;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AcctModifyTagFromConfirmationAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
    {
            TagRequestForm tagRequestForm = (TagRequestForm) form;

            int indexToModify = tagRequestForm.getVehicleIndexToModify();
            boolean delete = tagRequestForm.isDeleteVehicle();

            BillingContext billingContext =(BillingContext) request.getSession().getAttribute("billingContext");
            tagRequestForm = billingContext.getTagRequestForm();
            tagRequestForm.setVehicleIndexToModify(indexToModify);
            tagRequestForm.setDeleteVehicle(delete);

            SessionUtil.getAcctDTO(request);
            if (Boolean.parseBoolean(request.getParameter("ezTagsExist")))
                request.setAttribute("ezTagsExist", Boolean.TRUE);
            request.setAttribute("tagRequestForm", tagRequestForm);
            request.setAttribute("confirmEdit","confirmEdit");
            return mapping.findForward("success");
    }
}
