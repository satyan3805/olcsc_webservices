/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.menu;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.util.SessionUtil;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.validator.DynaValidatorForm;

public class LoginUpdateTabMenuAction extends Action {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, HttpServletResponse response) 
        throws Exception {
    String menu = null;
    String url = null;
    try {    
        DynaValidatorForm dynaValidatorForm = ( DynaValidatorForm ) form;
        menu = (String) dynaValidatorForm.get("menu");
        url = (String) dynaValidatorForm.get("url");
        if ( StringUtils.isEmpty( menu ) && StringUtils.isEmpty( url ) ) {
        	return mapping.findForward( "accountHome" ); 
        }

        SessionUtil.setCurrentTabMenuId(request.getSession(), 
            Integer.parseInt(menu));
        if (url != null && !url.startsWith("/")) {
            url = "/" + url;
        }

    //    request.getSession().removeAttribute("currentMenu");

        return new ActionForward(url);
      } catch (Throwable t) {
          throw new EtccSysException("Error updating tab menu (/menu/url/ip): " 
            + menu + "/" + url + "/" + request.getRemoteAddr() + "/" 
            + request.getHeader("USER-AGENT") + "/" + t, t);
      }
  }
}
