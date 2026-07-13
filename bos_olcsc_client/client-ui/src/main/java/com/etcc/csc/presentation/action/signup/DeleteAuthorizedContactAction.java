/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.util.StringUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Deletes an Authorized Contact from the User's contact list.
 */
public class DeleteAuthorizedContactAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(DeleteAuthorizedContactAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

        ContactInfoForm contactForm = (ContactInfoForm) form;
        List<AuthorizedContactDTO> authorizedContacts = contactForm.getAuthorizedContacts();
        String strIndex = request.getParameter("index");

        //            String failure = "failure";
        String forward = "success";
        if (contactForm.getWhichPageEnum() == ContactInfoForm.WhichPage.UPDATE) {
            //                failure ="managefailure";
            forward ="managesuccess";
        }
        if (Boolean.parseBoolean(request.getParameter("preq"))) {
            request.setAttribute("preq", Boolean.TRUE);
            String password = request.getParameter("password");
            if (!StringUtil.isEmpty(password))
                request.setAttribute("pwd",password);
        } else {
            request.setAttribute("preq", Boolean.FALSE);
        }
        String fromConfirmation = request.getParameter("fromConfirmation");
        if (!StringUtil.isEmpty(fromConfirmation))
            request.setAttribute("fromConfirmationContact", fromConfirmation);
        String fromConfirmationContact = request.getParameter("fromConfirmationContact");
        if (!StringUtil.isEmpty(fromConfirmationContact))
            request.setAttribute("fromConfirmationContact", fromConfirmationContact);

        if (!StringUtil.isEmpty(strIndex)) {
            //Adding the webservice Call ...
            boolean traceEnabled = logger.isTraceEnabled();
            if (traceEnabled)
                logger.trace("before delete: " + authorizedContacts.size());
            authorizedContacts.remove(Integer.parseInt(strIndex));

            if (traceEnabled)
                logger.trace("after delete: " + contactForm.getAuthorizedContacts());
            if(authorizedContacts.size()<=0) {
                if (contactForm.getWhichPageEnum() == ContactInfoForm.WhichPage.UPDATE) {
                    ErrorMessageDTO msg = new ErrorMessageDTO();
                    msg.setMessage("There is no Authorized Contact information for your account. To authorize a contact on your behalf, please execute the 'ADD ANOTHER CONTACT' button.");
                    saveErrorMessage(request, msg, "invalidAccount");
                }
            }
        }

        request.setAttribute("contactInfoForm", contactForm);
        //request.setAttribute("authorizedContacts", authorizedContacts);

        return mapping.findForward(forward);
    }
}
