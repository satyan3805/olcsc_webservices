package com.etcc.csc.action.menu;

import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateAcctMgmtTabMenuAction extends Action {
	private static final Logger logger = Logger.getLogger(UpdateAcctMgmtTabMenuAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
          HttpServletRequest request, HttpServletResponse response) 
          throws Exception {

    	String menu = null;
    	String url = null;
    	try {
    		if (SessionUtil.isSessionExpired(request)) {
    			return mapping.findForward("expiredSession");
    		}
    		DynaValidatorForm dynaValidatorForm = ( DynaValidatorForm ) form;
    		menu = (String) dynaValidatorForm.get("menu");
    		url = (String) dynaValidatorForm.get("url");

    		if ( StringUtil.isEmpty( menu ) && StringUtil.isEmpty( url ) ) {
    			return mapping.findForward( "accountHome" ); 
    		}

    		if (!StringUtil.isEmpty(menu))
    			SessionUtil.setCurrentAcctMgmtTabMenuId(request.getSession(), Integer.parseInt(menu));
    		if (url == null)
    			return mapping.findForward( "accountHome" ); 
    		if (!url.startsWith("/")) {
    			url = "/" + url;
    		}

    		SessionUtil.setTertiaryMenusInRequest(request);
    		return new ActionForward(url);
    	} catch (Throwable t) {
    		logger.error("Error updating acct mgmt tab menu (/menu/url/ip): " 
    				+ menu + "/" + url + "/" + request.getRemoteAddr() + "/" 
    				+ request.getHeader("USER-AGENT") + "/" + t, t);
    	}
		return mapping.findForward( "accountHome" ); 
    }
}
