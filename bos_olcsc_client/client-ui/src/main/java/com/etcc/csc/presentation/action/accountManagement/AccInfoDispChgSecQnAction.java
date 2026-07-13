/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccInfoDispChgSecQnAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
    {
        OnlineAccessForm onlineAccessForm = new OnlineAccessForm();
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDTO accountDTO = SessionUtil.getAcctDTO(request);
        onlineAccessForm.setSecurityQuestionID(Long.toString(accountDTO.getSecurityQuestionID()));
        onlineAccessForm.setLoginId(acctLoginDto.getLoginId());
        onlineAccessForm.setSecurityQuestionAnswer(accountDTO.getSecurityQuestionAnswer());
        onlineAccessForm.setSecurityQuestionAnswer2(accountDTO.getSecurityQuestionAnswer());
        request.setAttribute("OnlineAccessForm", onlineAccessForm);
        return mapping.findForward("success");
    }
}
