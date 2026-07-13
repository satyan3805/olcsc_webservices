/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.account;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.OrderDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.form.AccountForm;
import com.etcc.csc.util.SessionUtil;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayAccountHomeAction extends Action {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, HttpServletResponse response) 
        throws Exception {
    AccountForm acctForm = (AccountForm) form;
    AccountDelegate acctDel = new AccountDelegate();
    OrderDelegate orderDel = new OrderDelegate();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
        request.getSession());
    if (acctLoginDto != null) {
    /*
        if ( "P".equalsIgnoreCase( acctLoginDto.getAcctActivity() ) ) 
        {
          return mapping.findForward( "pendingAccount" );    
        }
        */
        request.setAttribute("alerts", acctDel.getAlerts(acctLoginDto));

        request.setAttribute("trans", acctDel.getLastTransactions(acctLoginDto));
        
    //TODO: Need to fix this for HCTRA
//        Collection pendingOrders = orderDel.getOrders(acctLoginDto, true);
//        request.setAttribute("pendingOrders", pendingOrders);
    } else {
        throw new EtccSecurityException("Security violation in "
            + "DisplayAccountHomeAction");
    }
    return mapping.findForward("success");
  }
}
