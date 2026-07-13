/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

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
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AuthorizedContactDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class AccInfoChgAuthorizedContactsAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccInfoChgAuthorizedContactsAction.class);

    private static final int MAX_PWD_FAIL_COUNT = 2;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    throws Exception
    {
        ContactInfoForm aContactInfoForm = (ContactInfoForm)form;
        HttpSession session = request.getSession();
        String page = null;

        request.setAttribute("preq", Boolean.valueOf(request.getParameter("preq")));
        String password = StringUtil.safeToString(request.getParameter("password"));

        String failure = "failure";
        String success = "success";
        if (aContactInfoForm.getWhichPageEnum() == ContactInfoForm.WhichPage.UPDATE) {
            failure = "managefailure";
            success = "managesuccess";
        }
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(session);

        AuthorizedContactDelegate authContDel = new AuthorizedContactDelegate();
        logger.trace("AccInfoChgAuthorizedContactsAction.password: " + password);
        try {
            logger.trace("Before the set Auth contacts");
            List<AuthorizedContactDTO> authList = aContactInfoForm.getAuthorizedContacts();
            logger.trace("Auth contacts SIZE: " + authList.size());

            Object sessionPassword = session.getAttribute("password");
            if (StringUtil.equalsIgnoreCase(sessionPassword, password)) {
                AuthorizedContactDTO[] authListDel = authContDel.getAuthContacts(acctLogin);
                if (authListDel != null && authListDel.length > 0) {
                    List<AuthorizedContactDTO> deleteAuthList = new ArrayList<AuthorizedContactDTO>();
                    for (AuthorizedContactDTO newAuthContact : authListDel) {
                        newAuthContact.setActionDelete();
                        deleteAuthList.add(newAuthContact);
                    }
                    authContDel.deleteAuthorizedContactsList(acctLogin,deleteAuthList,password);
                }
            }
            List<AuthorizedContactDTO> modifiedAuthList = new ArrayList<AuthorizedContactDTO>();
            for (AuthorizedContactDTO authContact : authList) {
                    AuthorizedContactDTO newAuthContact = authContact.clone();
                    newAuthContact.setActionModify();
                    modifiedAuthList.add(newAuthContact);
            }

            if (StringUtil.equalsIgnoreCase(sessionPassword, password)) {
                ResultDTO result = authContDel.modifyAuthorizedContactsList(acctLogin,modifiedAuthList,password);
                if (saveErrorMessages(request, result, "saveFailed")) {
                    page = "returnAuthPage";
                } else {
                    aContactInfoForm.setAuthorizedContacts(authContDel.getAuthContacts(acctLogin));
                    session.removeAttribute("authPwdfailCount");
                }
            } else {
                int authPwdfailCount = 0;
                String authPwdfailCountAttr = StringUtil.safeToString(session.getAttribute("authPwdfailCount"));
                if (authPwdfailCountAttr != null)
                    authPwdfailCount = Integer.parseInt(authPwdfailCountAttr);
                if (authPwdfailCount >= MAX_PWD_FAIL_COUNT) {
                    session.removeAttribute("authPwdfailCount");
                    aContactInfoForm.setAuthorizedContacts(authList);
                    saveErrorMessages(request,
                            new String[] { "Due to unsuccessful password entry, your request to add authorized contact can no longer be done online. <br /> Please contact an EZ Tag Customer Service Representative at 281-875-EASY(3279) <a href= 'https://www.hctra.org/about_locations' title='locations'> during regular business hours</a> for assistance." },
                    "authAlerts");
                                       
                    request.setAttribute("headAuth", Boolean.TRUE);
                    session.setAttribute("pwdAuthAlert", Boolean.TRUE);
                } else {
                    authPwdfailCount++;
                    session.setAttribute("authPwdfailCount", Integer.valueOf(authPwdfailCount));
                    aContactInfoForm.setAuthorizedContacts(modifiedAuthList);
                    saveErrorMessages(request,
                            new String[] { "Please provide valid password." },
                    "invalidAccount");
                    request.setAttribute("pwdAlert", Boolean.TRUE);
                    page = "returnAuthPage";
                }
            }
            logger.trace("After the set Auth contacts " + authList.size() + " " + authList);
        } catch (EtccSecurityException se) {
            logger.debug("Security error in AccInfoChgSecQnAction.");
            return mapping.findForward(failure);
        } catch (EtccException etccEx) {
            saveErrorMessages(request, new String[] { etccEx.getMessage() }, "changeAuthorizedContactsError");
            return mapping.findForward(failure);
        } catch (Exception e) {
            logger.debug("Exception error in AccInfoChgSecQnAction.");
            return mapping.findForward(failure);
        }

        request.setAttribute("contactInfoForm", aContactInfoForm);
        SessionUtil.getAcctDTO(request);
        SessionUtil.getAcctPrefDTO(request);

        if (page != null)
            return mapping.findForward(failure);

        SessionUtil.getBillingInfo(request);  // ensure the billing info is available to JSP on success
        return mapping.findForward(success);
    }
}
