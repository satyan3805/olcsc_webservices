/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.security;

import com.etcc.csc.delegate.DbLoggerDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Noel Ternida
 */
public class GlobalErrorAction extends Action {
    private static final Logger logger = Logger.getLogger(GlobalErrorAction.class);
    private static final DbLoggerDelegate dbLogger = new DbLoggerDelegate();

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    String target = "notLoggedIn";
    try {
    	Exception ex = (Exception) request.getAttribute(Globals.EXCEPTION_KEY);
    	// log error in the database
    	if (ex!=null) {
    		String message = "Error in web application " 
    			+ request.getRemoteAddr() + "/user: "
    			+ SessionUtil.getSessionAccountLogin(request.getSession())
    			+ ":" + ex.toString();
    		dbLogger.logError(message, ex.getStackTrace());
    	}
    	// send email if necessary
        // display issue id to the user

        String loginEntryPoint = SessionUtil.getLoginEntryPoint(request.getSession());
        if (loginEntryPoint != null && loginEntryPoint.equals(AccountLoginDTO.LoginType.AC.toString())) {
            target = "success";
        }

        if (request.getSession() == null) {
            logger.error("Calling removeCookies...");
            HttpDataUtil.removeCookies(response);
        }
    } catch (Throwable t) {
        String message = "Error during logging of Exception: " + t.getMessage();
        logger.error(message, t);
    }
    return mapping.findForward(target);
  }
}
