/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.security;

import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.util.SslUtil;
import com.etcc.csc.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UpdateUrlAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
        
      String url = request.getParameter("url");
      String ssl = request.getParameter("ssl");
               
      boolean isSsl = StringUtil.stringToBoolean(ssl);

      if (new AppDelegate().isSwitchProtocol()) {
          String redirectString = SslUtil.getRedirectString(request,
                     isSsl,url);
          if (redirectString!=null)
            url = redirectString;
      }
        
      ActionForward af = new ActionForward(url);
      if (url.startsWith("http"))
        af.setRedirect(true);
      return af;    
  }
}
