/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;



import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.OrderDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.util.SessionUtil;

import java.util.Collection;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccountViewOrdersAction extends Action {
    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
         OrderDelegate del = new OrderDelegate();
         AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
         Collection<OrderDTO> orders = null;
         if (acctLoginDto != null) {
             orders = del.getOrders(acctLoginDto, false);
             request.setAttribute("dateCreated", acctLoginDto.getDateCreated());
         } else {
             throw new EtccSecurityException("Security violation in DisplayOrderAction");
         }
         SessionUtil.setTertiaryMenusInRequest(request);
         
       //  logger.debug("getOrders: " + noOrders);
         
         request.setAttribute("orders", orders);

         String orderShippingTime = new AppDelegate().getSysParam("ORDER SHIPPING TIME");
         request.setAttribute("orderShippingTime", orderShippingTime);         

         request.setAttribute("hasPending", hasPending(orders));
         request.setAttribute("noOrders",noOrders(orders));
         request.setAttribute("acctLoginInfo", acctLoginDto);
                  
         return mapping.findForward("success");
     }

    private Boolean noOrders(Collection<OrderDTO> orders) {
    	
    	Boolean hasNoOrders = Boolean.FALSE;
    	
    	if (orders.isEmpty()) 
    	{    
    		 hasNoOrders =  Boolean.TRUE;
    		         		 
    	}
    	
    	return hasNoOrders;
    	
    }
    
    
     private Boolean hasPending(Collection<OrderDTO> orders) {
         if (orders == null) return Boolean.FALSE;
         Boolean hasPendingOrder = Boolean.FALSE;
         for (OrderDTO order : orders) {
             if (order.getStatus().equalsIgnoreCase("PENDING")) {
                 hasPendingOrder = Boolean.TRUE;
                 break;
             }
         }
         return hasPendingOrder;
     }
}
