/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OrderDTO;

/**
 * Generates the return values for the methods needed for Tests (and demos).
 * @author Milosh Boroyevich
 */
public class OrderFactory {

	//Package level.  The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final OrderInterface mocked){
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                //Note: If the exact values are NOT passed as parameters, the getAccount will through an Exception.
                //So, set up to use "Wildcards".
                allowing(mocked).getOrders(with(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID)), with(true));
                will(returnValue(getPendingOrders()));
                allowing(mocked).getOrders(with(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID)), with(false));
                will(returnValue(getAllOrders()));
                allowing(mocked).getOrders(with(any(AccountLoginDTO.class)), with(any(boolean.class)));
                will(returnValue(null));
            }});
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Collection<OrderDTO> getPendingOrders() {
    	return addOrders(null, 2, true);
    }

    public static Collection<OrderDTO> getAllOrders() {
    	return addOrders(getPendingOrders(), 3, false);
    }

    static Collection<OrderDTO> addOrders(Collection<OrderDTO> c, int count, boolean pending) {
    	if (c == null)
    		c = new ArrayList<OrderDTO>(count);
    	Calendar orderDate = Calendar.getInstance();
    	int size = c.size();
    	for (long transactionId = size+count; transactionId > size; transactionId--) {
    		OrderDTO order = new OrderDTO();
    		order.setAcctId(AccountFactory.POPULATED_ACCOUNT_ID);
    		order.setOrderDate(orderDate);
    		order.setTransactionId(transactionId);
        	// TODO: What is a pending order???
    		if (pending)
    			order.setStatus("pending");
    		c.add(order);
    	}
        return c;
    }
}
