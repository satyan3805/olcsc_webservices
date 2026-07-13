/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.order;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.OrderDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.util.SessionUtil;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayOrderAction extends Action {
  @Override
public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    OrderDelegate del = new OrderDelegate();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
        request.getSession());
    Collection<OrderDTO> orders = null;    
    if (acctLoginDto != null) {
        orders = del.getOrders(acctLoginDto, false);
    } else {
        throw new EtccSecurityException("Security violation in "
            + "DisplayOrderAction");
    }
    request.setAttribute("orders", orders);
    return mapping.findForward("success");
  }
}
