/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.delegate.AccountAlertDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddressCleansingAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
        String forward = null;
        String addClean = request.getParameter("addClean");
        if (!StringUtil.isEmpty(addClean)) {
            if (addClean.equalsIgnoreCase("A"))
                forward = "success";
            else if (addClean.equalsIgnoreCase("R"))
                forward = "mailingInfo";
        }
        if (forward != null)
            new AccountAlertDelegate().addressCleanseAlertResponse(loginDTO, addClean);
        else
            forward = "success";
        return mapping.findForward(forward);
    }
}
