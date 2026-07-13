/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.SessionUtil;

public class AccInfoChgMailingAddrAction extends CSCBaseAction{
    private static final Logger logger = Logger.getLogger(AccInfoChgMailingAddrAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
        OnlineAccessForm onlineAccessForm = (OnlineAccessForm)form;
        logger.debug("in AccInfoChgMailingAddrAction");
        
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDelegate acctDel = new AccountDelegate();
        AccountLoginDTO result = acctDel.updateMailingAddress(acctLogin, onlineAccessForm.clean());
        
        if (Boolean.parseBoolean(request.getParameter("acctInfoRefund"))) {
            request.setAttribute("closure", Boolean.TRUE);
               request.setAttribute("acctInfoRefund","true");
               request.setAttribute("paymenttype","checkV");
           }    
        if (saveErrorMessages(request, result, "editMailingAddressError"))
            return mapping.findForward("failure");
        request.setAttribute("setEvent3", Boolean.TRUE);
        return mapping.findForward("success");
     }
}
