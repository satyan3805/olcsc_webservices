/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.presentation.form.TagRequestForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccountVehicleAddDisplayAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception {
        saveToken(request);
        HttpSession session = request.getSession();
        session.removeAttribute("billingContext");
        session.removeAttribute("tagRequestForm");
        //System.out.print("pbp ex ...."+ pbpExists);
        TagRequestForm tagRequestForm = (TagRequestForm) session.getAttribute("tagRequestForm");
        if(tagRequestForm == null ){
            tagRequestForm = new TagRequestForm();
        }
        String pbpExists;
        if(request.getAttribute("activePbpTagExists")!=null)
           pbpExists = request.getAttribute("activePbpTagExists").toString();
        else
            pbpExists = request.getParameter("activePbpTagExists");

        request.setAttribute("activePbpTagExists", pbpExists);
        tagRequestForm.setActivePbpTagExists(Boolean.parseBoolean(pbpExists));

        session.setAttribute("tagRequestForm" , tagRequestForm);
        request.setAttribute("tagRequestForm" , tagRequestForm);
        return mapping.findForward("success");
    }    
}
