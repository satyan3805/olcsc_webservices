/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MenuAdjustmentAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
//         SessionUtil.getAcctDTO(request);
    	HttpSession session = request.getSession();
         if (session==null)
            throw new EtccSecurityException("session timed out in MenuAdjustmentAction");
         String sysParamName =  request.getParameter("sysParamName");
         String makePaymentMenuLabel = new AppDelegate().getSysParam(sysParamName);
         int menuId = SessionUtil.getAcctMgmtTabMenuIdByLabel(session, makePaymentMenuLabel);
         SessionUtil.setCurrentAcctMgmtTabMenuId(session, menuId);
         SessionUtil.setTertiaryMenusInRequest(request);
         return mapping.findForward("success");
     }
}
