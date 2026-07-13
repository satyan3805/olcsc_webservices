/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.delegate.PasswordRetrievalDelegate;
import com.etcc.csc.util.SessionUtil;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class ChangePasswordAction extends Action {
	private static final Logger logger = Logger.getLogger(ChangePasswordAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        DynaValidatorForm changePasswordForm = (DynaValidatorForm) form;
        String oldPwd = changePasswordForm.getString("oldPassword");
        String pwd = changePasswordForm.getString("newPassword");
        PasswordRetrievalDelegate delegate = new PasswordRetrievalDelegate();
        
        AccountLoginDTO acctLoginDto = new AccountLoginDTO();
        AccountLoginDTO loginDtoInSession = SessionUtil.getSessionAccountLogin(request.getSession());
        
        BeanUtils.copyProperties(acctLoginDto, loginDtoInSession);
        logger.debug("***loginId: " + acctLoginDto);
        //acctLoginDto.setLoginId(changePasswordForm.getString("userId"));
        Collection<String> errors = null;
        if (!acctLoginDto.getLoginId().equalsIgnoreCase(changePasswordForm.getString("userId"))) {
        	// loginId in session is not same as userId submitted with form
            errors = new ArrayList<String>();
            errors.add("");
        } else {
            errors = delegate.changePassword(acctLoginDto, oldPwd, pwd);
        }

        if (errors != null && !errors.isEmpty()) {
        	logger.debug("errors="+errors);
            ActionMessages messages = getMessages(request);
            ActionMessage msg = new ActionMessage("changePasswordForm.error");
            messages.add("changePwdFailed", msg);
            saveMessages(request, messages);
            return mapping.findForward("failure");
        }
        return mapping.findForward("success");
    }
}
