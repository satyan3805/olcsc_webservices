/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.menu;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.validator.DynaValidatorForm;

public class ExecuteMenuAction extends Action {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    DynaValidatorForm dynaValidatorForm = ( DynaValidatorForm ) form;
    String menuLoc = (String) dynaValidatorForm.get("loc");
    String topMenu = (String) dynaValidatorForm.get("top");
    String url = (String) dynaValidatorForm.get("url");
    String subMenu = (String) dynaValidatorForm.get("subMenu");
    
    request.getSession().setAttribute("currentMenu", form);
    
    request.setAttribute("loc", menuLoc);
    request.setAttribute("top", topMenu);
    if (subMenu != null && !subMenu.equalsIgnoreCase("undefined")) {
        request.setAttribute("subMenu", subMenu);
    }
    if (url != null && !url.startsWith("/")) {
        url = "/" + url;
    }
    return new ActionForward(url);
  }
}
