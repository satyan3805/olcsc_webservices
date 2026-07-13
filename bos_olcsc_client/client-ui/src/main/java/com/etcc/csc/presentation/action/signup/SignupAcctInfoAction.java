/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action.signup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BaseContactInfo;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class SignupAcctInfoAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(SignupAcctInfoAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

        OnlineAccessForm oaForm = (OnlineAccessForm)form;
        HttpSession session = request.getSession();
        ContactInfoForm contactInfo = (ContactInfoForm) session.getAttribute("contactInfoForm");
        if (contactInfo != null && logger.isDebugEnabled()) {
            logger.debug("contactInfo.fname=" + contactInfo.getFirstName());
            logger.debug("contactInfo.lname=" + contactInfo.getLastName());
        }

        if( !oaForm.isFromConfirmation()) {
            logger.info("!oaForm.isFromConfirmation()");
            AccountLoginDTO accountLoginDTO = SessionUtil.getSessionAccountLogin(session);
            if (accountLoginDTO == null) {
    //            AuthorizedContact contact = new AuthorizedContact();
    //            List authorizedContacts = new ArrayList();
    //            authorizedContacts.add(contact);
    //            request.setAttribute("authorizedContacts", authorizedContacts);
                if (createLogin(request, response, oaForm)) {
                    session.setAttribute("password",oaForm.getPassword());
                    return mapping.findForward("success");
                }
            } else {
                if (updateLogin(request, oaForm, contactInfo)) {
                    session.setAttribute("password",oaForm.getPassword());
                    return mapping.findForward("success");
                }
            }
        } else {
            logger.info("!oaForm.updateLogin()");
            if (updateLogin(request, oaForm, contactInfo)) {
//                HttpSession session = request.getSession();
//                if (session != null)
//                {
//                    BillingInfoForm billingInfoForm = (BillingInfoForm) request.getSession().getAttribute("billingInfoForm");
//                    if (billingInfoForm != null)
//                        request.setAttribute("primaryCardName", new CreditCardDelegate().getCreditCardName(billingInfoForm.getCardTypeEnum()));
//                }
                return mapping.findForward("confirmation");
            }
        }
        return mapping.findForward("failure");
    }

    private boolean createLogin(HttpServletRequest request, 
                                HttpServletResponse response, 
                                OnlineAccessForm oaForm) throws Exception {
        AccountLoginDTO accountLoginDTO;
        AccountDelegate accountDelegate = new AccountDelegate();
        boolean success = false;
        accountLoginDTO = accountDelegate.setupAccountStep1(
                    oaForm.getLoginId(), oaForm.getPassword(), oaForm.getEmailAddress(),
                    Integer.parseInt(oaForm.getSecurityQuestionID()), 
                    oaForm.getSecurityQuestionAnswer(), 
                    oaForm.getAlternateEmail(), 
                    HttpDataUtil.getClientIpAddress(request), 
                    request.getSession().getId(),HttpDataUtil.getUserAgentAttributes(request));

        if (!saveErrorMessages(request, accountLoginDTO.getErrors(), "invalidAccountInfo")){
            success = true;
            oaForm.setAcctId(Long.toString(accountLoginDTO.getAcctId()));
            accountLoginDTO.setLastLoginIp(HttpDataUtil.getClientIpAddress( request ));
            SessionUtil.setSessionAccountLogin(request.getSession(), accountLoginDTO);
            HttpDataUtil.setDbSessionIdCookie(response, 
                accountLoginDTO.getDbSessionId());
        }
        return success;
    }

    private boolean updateLogin(HttpServletRequest request,
                                OnlineAccessForm oaForm,
                                ContactInfoForm contactInfoForm) throws Exception {
        AccountLoginDTO acctLoginDTO;
        acctLoginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
        boolean success = false;
        BaseContactInfo contactInfo = (contactInfoForm == null ? null : contactInfoForm.getContactInfo());
        ResultDTO result = new AccountDelegate().updateLoginInfo(acctLoginDTO, oaForm.getLoginId(),
                                                        oaForm.getPassword(),
                                                        oaForm.getEmailAddress(),
                                                        Integer.parseInt(oaForm.getSecurityQuestionID()),
                                                        oaForm.getSecurityQuestionAnswer(),
                                                        oaForm.getAlternateEmail(),
                                                        contactInfo);
        if (!saveErrorMessages(request, result, "invalidAccountInfo")) {
            acctLoginDTO.setLoginId(oaForm.getLoginId());
            success = true;
        }
        return success;
    }
}
