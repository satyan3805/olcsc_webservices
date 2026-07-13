/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.menu;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.MenuDelegate;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.SslUtil;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.validator.DynaValidatorForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateTabMenuAction extends Action {
	private static final Logger logger = Logger.getLogger(UpdateTabMenuAction.class); 

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) 
	throws Exception {    
		String menu = null;
		String url = null;
		String secure = null;
		HttpSession session = request.getSession();
		boolean isDebugEnabled = logger.isDebugEnabled();
		boolean isTraceEnabled = logger.isTraceEnabled();

		try {
			DynaValidatorForm dynaValidatorForm = ( DynaValidatorForm ) form;
			menu = (String) dynaValidatorForm.get("menu");
			url = (String) dynaValidatorForm.get("url");
			secure = (String) dynaValidatorForm.get("secure");

			// start siva idea integration
			logger.info("Start Idea integration code");
			if (isDebugEnabled) {
				logger.debug("Menu :"+menu);
				logger.debug("URL :"+url);
				logger.debug("secure :"+secure);
			}

			if (StringUtil.isEmpty(menu)) {
				return mapping.findForward("home");
			}

			int parsedMenu;
			try {
				parsedMenu = Integer.parseInt(menu);
			} catch(NumberFormatException e) {
				logger.error("Menu parameter parse error:" + e.getMessage(), e);
				return mapping.findForward("home");
			}

			boolean errorPage=true;
			logger.trace("Before colPagemenus.");
			MenuItemDTO[] colPageMenus = SessionUtil.getPageMenus(session);
			if(colPageMenus != null) {
				if (isTraceEnabled) logger.trace("After colPagemenus Size: " + colPageMenus.length);
                for (MenuItemDTO menuItemDTO : colPageMenus) {
                    if (isTraceEnabled)
                        logger.trace("Page menu: " + menuItemDTO.getItemId() + "=" + menuItemDTO.getItemUrl());
                    if(menuItemDTO.getItemUrl().equals(url) && menuItemDTO.getItemId()==parsedMenu) {
                        errorPage = false;
                    }
                }
            }
            logger.trace("Before Tab menus.");
            MenuDelegate menuDel = new MenuDelegate();      
            MenuItemDTO[] colTabMenus = menuDel.getMenus(MenuItemDTO.MenuCategory.OLCSC_UNLG);
            if (isTraceEnabled) logger.trace("After Tab menus Size: " + colTabMenus.length);
            int i = 0;
            for (MenuItemDTO menuItemDTO : colTabMenus) {
                if (isTraceEnabled)
                    logger.trace("Tab menu: " + menuItemDTO.getItemId() + "=" + menuItemDTO.getItemUrl());
                if (menuItemDTO.getItemId() == parsedMenu
                        && (menuItemDTO.getItemUrl().equals(url)
                                || (i == 3 && (url.equals("/logout.do") || url.equals("/login.do")))
                        )) {
                    errorPage = false;
                }
                i++;
            }  
            logger.info("End idea integration code");
            // end siva idea integration

			SessionUtil.setCurrentTabMenuId(session, parsedMenu);
			if (url != null && !url.startsWith("/") && !url.startsWith("http")) {
				url = "/" + url;
			}

			if (SessionUtil.isAcctLoginSessionActive(session))
				session.removeAttribute("currentMenu");
			else
				HttpDataUtil.removeCookies(response);

			boolean isSsl = (url.indexOf("login.do") != -1 || url.indexOf("violatorLoginDisplay.do") != -1);

			AppDelegate appDel = new AppDelegate();
			String label = appDel.getMyEZTAGMenuLabel();
			int menuId = SessionUtil.getPageMenuIdByLabel(session, label);
			if (menuId == parsedMenu && SessionUtil.isAcctLoginSessionActive(session)) {
				url = "/myAccountHome.do";
			}

			if (appDel.isSwitchProtocol()) {
				String redirectString = SslUtil.getRedirectString(request,isSsl,url);
				if (redirectString!=null) {
					url = redirectString;
					if (url.startsWith("http") && !url.startsWith("https")) {
						request.setAttribute("destination", url);
						return mapping.findForward("redirectPage");
					}
				}
			}

			// start siva idea integration 
			if(errorPage)
				url = "/cssError.do";
			// end siva idea integration   

			if (isDebugEnabled) logger.debug("Forwarding to URL: " + url);
			ActionForward af = new ActionForward(url);
			if (url.startsWith("http"))
				af.setRedirect(true);
			return af;
		} catch (Throwable t) {
			String msg = "Error updating tab menu (/menu/url/ip): " 
				+ menu + "/" + url + "/" + request.getRemoteAddr() + "/" 
				+ request.getHeader("USER-AGENT") + "/" + t;
			logger.debug(msg, t);
			throw new EtccException(msg, t);
		}
	}
}
