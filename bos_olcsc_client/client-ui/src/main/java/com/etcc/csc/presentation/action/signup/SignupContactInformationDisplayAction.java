/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.AuthorizedContactDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gathers the data needed to display the Signup Contact Information screen.
 */
public class SignupContactInformationDisplayAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

        ContactInfoForm contactForm = (ContactInfoForm) form;
        HttpSession session = request.getSession();

        final String fromConfirmContact = request.getParameter("fromConfirmationContact");
        if (Boolean.parseBoolean(fromConfirmContact)) {
            contactForm.setFromConfirmation(true);
            request.setAttribute("fromConfirmation", Boolean.TRUE);
        }
        AuthorizedContactDelegate authContDel = new AuthorizedContactDelegate();
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
        try {
            contactForm.setAuthorizedContacts(authContDel.getAuthContacts(loginDTO));
            contactForm.setAuthContactsLimit(authContDel.getAuthContactLimit());
        } catch (EtccException e) {
            ErrorMessageDTO msg = new ErrorMessageDTO().withMessage(e.getMessage());
            saveErrorMessage(request, msg, "cancelContacts");
        }

        request.setAttribute("contactInfoForm", contactForm);
        session.setAttribute("contactInfoForm", contactForm);

        return mapping.findForward("success");
    }
}
