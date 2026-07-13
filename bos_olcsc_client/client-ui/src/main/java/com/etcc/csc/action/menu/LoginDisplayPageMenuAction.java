/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.etcc.csc.delegate.MenuDelegate;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.util.SessionUtil;

/**
 * Action to generate the page menu.
 */
public class LoginDisplayPageMenuAction extends Action {
  private static final Logger logger = Logger.getLogger(LoginDisplayPageMenuAction.class);

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
		  HttpServletRequest request, HttpServletResponse response) throws Exception {

    try {
        if (SessionUtil.getPageMenus(request.getSession()) == null) {
            MenuItemDTO[] col = null;
            DynaValidatorForm dynaValidatorForm = ( DynaValidatorForm ) form;
    //        String menu = (String) dynaValidatorForm.get("menu");
            int menu = SessionUtil.getCurrentTabMenuId(request.getSession());
            MenuDelegate menuDel = new MenuDelegate();
            col = menuDel.getMenus(MenuItemDTO.MenuCategory.OLCSC_PAGE);
            // Load different menu FOR TESTING ONLY
            //col = menuDel.getMenus("SessionID", 330303, "OLCSC_PAGE", "127.0.0.1", "OLCSC_USER");
    //        if ((menu == null || menu.equals("")) && col != null) {
            if (menu == 0 && col != null) {
                menu = Integer.parseInt(BeanUtils.getProperty(col[0], "itemId"));
                SessionUtil.setCurrentTabMenuId(request.getSession(), menu);        
            }
    //        dynaValidatorForm.set("menu", menu);
    //        SessionUtil.setCurrentTabMenuId(request.getSession(), 
    //            Long.parseLong(menu));
            request.setAttribute(mapping.getName(), dynaValidatorForm);
    //        request.setAttribute("pageMenus", col);
            SessionUtil.setPageMenus(request.getSession(), col);
        }
    } catch (Exception e) {
    	String message = "Error displaying login page menus: " + e.getMessage();
    	logger.error(message, e);
    }
    return mapping.findForward("success");
  }
}
