/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.util.SessionUtil;

/**
 * Displays the tab's home page.
 */
public class DisplayHomeTabAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception {
        try {
            SessionUtil.resetCurrentTabMenuId(request.getSession());
            request.getSession().removeAttribute("currentMenu");
            String path = mapping.getParameter();
            ActionForward retVal = new ActionForward(path);
    //        retVal.setContextRelative(true);
            return retVal;
        } catch (Throwable t) {
            throw new EtccSysException("Error displaying home tab.");
        }
    }
}
