/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.service.AccountInterface;
import com.etcc.csc.util.SessionUtil;

/**
 * Action for changing the user's password.
 */
public class AccInfoChgPasswordAction extends CSCBaseAction {
	private static final Logger logger = Logger.getLogger(AccInfoChgPasswordAction.class);

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // TODO update DB. reload AccountDTO.
        OnlineAccessForm dynaActionForm = (OnlineAccessForm) form;
        request.setAttribute("changePasswordMessage", null);
        // logger.debug("AccInfoChgUsernameAction.password=" + dynaActionForm.getPassword());
        HttpSession session = request.getSession();

        String forward = null;
        BaseDTO result = null;
        try {
            AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(session);
            AccountDelegate acctDel = new AccountDelegate();
            result = acctDel.updatePasswordEmailSecurityQA(acctLogin, 
                    AccountInterface.PASSWORD, dynaActionForm.getPassword(), dynaActionForm.getNewPassword(), null, 0, null);
        } catch (EtccSecurityException se) {
            logger.debug("Security error in changePassword.");
            forward = "securityError";
        }

        if (forward == null) {
        	if (saveErrorMessages(request, result, "changePasswordError")) {
        		request.setAttribute("changePasswordMessage", "Invalid Password change");
        		forward = "failure";
        	} else {
        		SessionUtil.getAcctDTO(request);
        		request.setAttribute("setEvent3", Boolean.TRUE);
        		session.setAttribute("password", dynaActionForm.getPassword());
        		forward = "success";
        	}
        }
        logger.debug("Forwarding to: " + forward);
        return mapping.findForward(forward);
    }
}
