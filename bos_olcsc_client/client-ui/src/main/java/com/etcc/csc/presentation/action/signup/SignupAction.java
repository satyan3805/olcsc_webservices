/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import com.etcc.csc.delegate.SessionDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SignupAction extends Action {
	private static final Logger logger = Logger.getLogger(SignupAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

        HttpSession session = request.getSession();
           if( !"false".equalsIgnoreCase( request.getParameter("refresh")))
            {
                if (session!=null)
                {
                    SessionDelegate sessionDel = new SessionDelegate();
                    AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);
                    if (accountLogin!=null)
                        sessionDel.destroySession(accountLogin.getDbSessionId());
                    logger.debug("user session removed: " + accountLogin);
                         //can't invalidate the session because the menu items saved in session are needed in the sign up process.
    //                 request.getSession().invalidate();
                     session.removeAttribute( "OnlineAccessForm" );
                     session.removeAttribute( "contactInfoForm" );
                     session.removeAttribute( "tagRequestForm" );
                     session.removeAttribute( "billingInfoForm" );
                     session.removeAttribute( "creditCardList" );
                     SessionUtil.setSessionAccountLogin(session, null);
                }
                 HttpDataUtil.removeCookies(response);
            }
            else
            {
            	request.setAttribute("fromConfirmation", "true");
            }
        return mapping.findForward("success");
    }
}
