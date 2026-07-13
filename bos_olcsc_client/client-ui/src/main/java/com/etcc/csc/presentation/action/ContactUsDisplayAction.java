/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class ContactUsDisplayAction extends Action {
    private static final Logger logger = Logger.getLogger(ContactUsDisplayAction.class);
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) throws Exception{
        String emailAddress = getEmailAddress(request);
        DynaValidatorForm dynaForm = (DynaValidatorForm)form;
        dynaForm.set("replyAddress", emailAddress);
        return mapping.findForward("success");
    }

    protected String getEmailAddress(HttpServletRequest request) throws EtccException {
        String email = null;
        AccountDTO acctDto = SessionUtil.getAcctDTO(request);
        if (acctDto != null)
            email = acctDto.getEmailAddress();
        logger.info("email address: " + email);
        return email;
    }
}
