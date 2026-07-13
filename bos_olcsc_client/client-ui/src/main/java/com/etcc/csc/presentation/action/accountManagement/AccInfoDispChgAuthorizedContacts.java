/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.presentation.form.ContactInfoForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccInfoDispChgAuthorizedContacts extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
    //        SessionUtil.getAcctDTO(request);
    /*
        OnlineAccessForm onlineAccessForm = new OnlineAccessForm();
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDelegate acctDel = new AccountDelegate();
        AccountDTO accountDTO = acctDel.getAccount(acctLoginDto, acctLoginDto.getAcctId());
        onlineAccessForm.setSecurityQuestionID("" + accountDTO.getSecurityQuestionID());
        onlineAccessForm.setLoginId(acctLoginDto.getLoginId());
        onlineAccessForm.setSecurityQuestionAnswer(accountDTO.getSecurityQuestionAnswer());
        onlineAccessForm.setSecurityQuestionAnswer2(accountDTO.getSecurityQuestionAnswer());
        request.setAttribute("OnlineAccessForm", onlineAccessForm);

        return mapping.findForward("success");
     }
     */
        //Not currently used.
//        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
//        AccountDelegate acctDel = new AccountDelegate();
        
       // List<AuthorizedContactBean> authorizedContacts = acctDel.getAuthorizedContacts(acctLoginDto);
        ContactInfoForm aContactInfoForm = new ContactInfoForm(); // only the authorized contacts is used
      //  aContactInfoForm.setAuthorizedContacts(AuthorizedContact.toAuthorizedContacts(authorizedContacts));
        request.getSession(true).setAttribute("contactInfoForm", aContactInfoForm);
        return mapping.findForward("success");
    }
}
