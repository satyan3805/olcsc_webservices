/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.security;

import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class AccountLoginAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        SessionUtil.resetCurrentTabMenuId(request.getSession());
//        Cookie userNameCookie = getCookieByName(request, "NTTACustomerServiceUserName");
//        Cookie rememberUserCookie = getCookieByName(request, "NTTACustomerServiceRememberUser");
//        Cookie passwordCookie = getCookieByName(request, "NTTACustomerServicePassword");
        
        DynaValidatorForm dynaValidatorForm = ( DynaValidatorForm ) form;
  //      if ( userNameCookie != null ) {
  //          dynaValidatorForm.set("userName", userNameCookie.getValue( ));
  //          dynaValidatorForm.set("remember", Boolean.TRUE);
  //      } else {
  //          dynaValidatorForm.set("remember", Boolean.FALSE);
  //      }

        return mapping.findForward("success");
    }

    private Cookie getCookieByName(HttpServletRequest request, String name) {
        if (name == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
        	for (Cookie i : cookies) {
        		if (name.equals(i.getName())) {
        			return i;
        		}
        	}
        }
        return null;
    }
}
