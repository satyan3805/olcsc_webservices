/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.AuthorizedContactDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class AddAuthorizedContactAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AddAuthorizedContactAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
        logger.trace("AddAuthorizedContacts....");

        ContactInfoForm contactForm = (ContactInfoForm) form;

        String pageReturn = null;
        // Searching which page ....
        HttpSession session = request.getSession();
        if(Boolean.parseBoolean(request.getParameter("preq"))) {
            request.setAttribute("preq", Boolean.TRUE);
            String password = request.getParameter("password");
            if (!StringUtil.isEmpty(password))
                request.setAttribute("pwd", password);
        } else {
            request.setAttribute("preq", Boolean.FALSE);
        }

        ContactInfoForm.WhichPage whichPage = ContactInfoForm.WhichPage.valueOfTag((String) session.getAttribute("whichPage"));
        contactForm.setWhichPageEnum(whichPage);
        String success = "add-contact";
        String failure = "failure";
        if (whichPage == ContactInfoForm.WhichPage.UPDATE) {
            success ="managesuccess";
            failure ="managefailure";
        }

        String fromConfirmation = request.getParameter("fromConfirmation");
        if (fromConfirmation != null)
            request.setAttribute("fromConfirmationContact", fromConfirmation);
        String fromConfirmationContact = request.getParameter("fromConfirmationContact");
        if (fromConfirmationContact != null)
            request.setAttribute("fromConfirmationContact", fromConfirmationContact);

        //function for add
        List<AuthorizedContactDTO> authorizedContacts = contactForm.getAuthorizedContacts();
        AuthorizedContactDelegate authContDel = new AuthorizedContactDelegate();
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(session);
        if (Boolean.parseBoolean(request.getParameter("cancel"))) {
            try{
                contactForm.setAuthorizedContacts(authContDel.getAuthContacts(acctLogin));
                contactForm.setAuthContactsLimit(authContDel.getAuthContactLimit());
                if (Boolean.parseBoolean(fromConfirmationContact)) {
                    request.setAttribute("fromConfirmation", Boolean.TRUE);
                    contactForm.setFromConfirmation(true);
                } 
            } catch (Exception e) {
                ErrorMessageDTO msg = new ErrorMessageDTO().withMessage(e.getMessage());
                saveErrorMessage(request, msg, "cancelContacts");
            }
            if (whichPage == null) {
                pageReturn = "signupContactInformationPage";
            } else {
                switch (whichPage) {
                case CONTACT:
                case INCOMPLETE:
                    pageReturn = "signupContactInformationPage";
                    break;
                case CONFIRM:
                    pageReturn = "signupBillingInfoPage";
                    break;
                case UPDATE:
                    pageReturn = "accountInformationPage";
                    break;
                }
            }
            request.setAttribute("contactInfoForm", contactForm);
            session.setAttribute("contactInfoForm", contactForm);
            if (pageReturn == null)
                return mapping.findForward(success);
            else
                return mapping.findForward(pageReturn);
        }
        if (contactForm.getAuthContactsLimit() == 0 ) {
    		contactForm.setAuthContactsLimit(authContDel.getAuthContactLimit()-1);
            //logger.info("The Max number of Auth Contacts" + contactForm.getAuthContactsLimit());
        }
        //This is to implement the add authorized contacts .....
        String method = request.getParameter("method");
        if (method != null && method.equals("add")) {
            if (authorizedContacts == null) {
                authorizedContacts = new ArrayList<AuthorizedContactDTO>();
                authorizedContacts.add(new AuthorizedContactDTO());
            } else if(authorizedContacts.size() < contactForm.getAuthContactsLimit()) {
                authorizedContacts.add(new AuthorizedContactDTO());
            }
            List<AuthorizedContactDTO> modifiedAuthList = new ArrayList<AuthorizedContactDTO>(authorizedContacts.size());
            for (AuthorizedContactDTO authContact : authorizedContacts) {
                AuthorizedContactDTO newAuthContact = authContact.clone();
                modifiedAuthList.add(newAuthContact);
            }
            contactForm.setAuthorizedContacts(modifiedAuthList);
            request.setAttribute("contactInfoForm", contactForm);
            session.setAttribute("contactInfoForm", contactForm);
            return mapping.findForward(success);
        }

        //function add ends 

        //Added this for account Information .................
        if(Boolean.parseBoolean(request.getParameter("addContacts"))) {
            String password = (String) session.getAttribute("password");
            if (StringUtil.isEmpty(password)) {
                OnlineAccessForm onlineAccessForm = (OnlineAccessForm)session.getAttribute( "OnlineAccessForm" );
                password = onlineAccessForm.getPassword();
            }
            if (!StringUtil.isEmpty(password)) {
                try{
                    AuthorizedContactDTO[] authListDel = authContDel.getAuthContacts(acctLogin);
                    if (authListDel != null && authListDel.length > 0) {
                        List<AuthorizedContactDTO> deleteAuthList = new ArrayList<AuthorizedContactDTO>(authListDel.length);
                        for(int i = 0; i < authListDel.length; i++) {
                            AuthorizedContactDTO newAuthContact = authListDel[i];
                            newAuthContact.setActionDelete();
                            deleteAuthList.add(newAuthContact);
                        }
                        authContDel.deleteAuthorizedContactsList(acctLogin,deleteAuthList,password);
                    }
                    List<AuthorizedContactDTO> authList=contactForm.getAuthorizedContacts();
                    List<AuthorizedContactDTO> modifiedAuthList = new ArrayList<AuthorizedContactDTO>(authList.size());
                    for (AuthorizedContactDTO authContact : authList) {
                        AuthorizedContactDTO newAuthContact = authContact.clone();
                        newAuthContact.setActionModify();
                        modifiedAuthList.add(newAuthContact);
                    }
                    if (modifiedAuthList.size() > 0)
                        authContDel.modifyAuthorizedContactsList(acctLogin,modifiedAuthList,password);
                    contactForm.setAuthorizedContacts(authContDel.getAuthContacts(acctLogin));
                    contactForm.removeBlankAuthContacts();
                } catch (EtccException e) {
                    ErrorMessageDTO msg = new ErrorMessageDTO().withMessage(e.getMessage());
                    saveErrorMessage(request, msg, "authorizedContact");
                } 
            }// if password
        } else if ((whichPage == ContactInfoForm.WhichPage.INCOMPLETE
                || whichPage == ContactInfoForm.WhichPage.CONFIRM
                || whichPage == ContactInfoForm.WhichPage.UPDATE)
                && method != null && method.equals("get")) {
            try{
                logger.info("Begin AddAuthorisedContactAction.update-authorized-contact-page"); 
                if (contactForm.hasAuthorizedContacts()) {
                    contactForm.setAuthorizedContacts(authContDel.getAuthContacts(acctLogin));
                    contactForm.removeBlankAuthContacts();
                } else {
                    authorizedContacts = new ArrayList<AuthorizedContactDTO>();
                    authorizedContacts.add(new AuthorizedContactDTO());
                    contactForm.setAuthorizedContacts(authorizedContacts);
                }
                logger.info("End AddAuthorisedContactAction.update-authorized-contact-page");
            } catch (Exception e) {
                ErrorMessageDTO msg = new ErrorMessageDTO().withMessage(e.getMessage());
                saveErrorMessage(request, msg, "updateAuthorizedContactsError");
                return mapping.findForward(failure);
            }            
            request.setAttribute("contactInfoForm", contactForm);
            session.setAttribute("contactInfoForm", contactForm);
            return mapping.findForward(success);
        } 
        logger.info("AddAuthorizedContactAction : Coming from page ......" + whichPage);
        contactForm.setFromConfirmation(whichPage == ContactInfoForm.WhichPage.CONFIRM);
        // end of Searching which page .....
        if (Boolean.parseBoolean(fromConfirmationContact)) {
            request.setAttribute("fromConfirmation", Boolean.TRUE);
            request.setAttribute("fromConfirmationContact", Boolean.TRUE);
            contactForm.setFromConfirmation(true);
        }
        if (Boolean.parseBoolean(request.getParameter("pgVal"))) {
            if (whichPage == null) {
                pageReturn = "signupContactInformationPage";
            } else {
                switch (whichPage) {
                case CONTACT:
                case INCOMPLETE:
                    pageReturn = "signupContactInformationPage";
                    break;
                case CONFIRM:
                    pageReturn = "signupBillingInfoPage";
                    break;
                case UPDATE:
                    pageReturn = "updateAuthContactsPage";
                    break;
                }
            }
        }
        request.setAttribute("contactInfoForm", contactForm);
        session.setAttribute("contactInfoForm", contactForm);
        if(pageReturn == null)
            return mapping.findForward(success);
        else
            return mapping.findForward(pageReturn);
    }
}
