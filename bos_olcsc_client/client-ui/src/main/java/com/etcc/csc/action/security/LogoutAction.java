/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.security;

import com.etcc.csc.delegate.SessionDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logs the user out of OLCSC and invalidates the HTTPSession.
 */
public class LogoutAction extends Action {
    private static final Logger logger = Logger.getLogger(LogoutAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(false);
        if (session != null) {
            SessionDelegate sessionDel = new SessionDelegate();
            AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);
            if (accountLogin != null)
                sessionDel.destroySession(accountLogin.getDbSessionId());
            if (logger.isDebugEnabled()){
                logger.debug("User logged out: " + SessionUtil.getSessionAccountLogin(session));
            }
            session.invalidate();
        }

        HttpDataUtil.removeCookies(response);
        SessionUtil.setCurrentTabMenuId(request.getSession(), 0);

        request.setAttribute("setEvent3", Boolean.TRUE);
        return mapping.findForward("success");
    }
}