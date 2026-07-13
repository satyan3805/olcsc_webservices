/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.service.AccountFactory;

/**
 * Tests for Order delegate.
 * @author Milosh Boroyevich
 */
public class OrderDelegateTest {
	private static final Logger logger = Logger.getLogger(OrderDelegateTest.class);

    OrderDelegate delegate;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseTestDelegate.setUpBeforeClass();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.delegate = new OrderDelegate();
    }

    /**
     * Test for {@link OrderDelegate#getOrders(AccountLoginDTO, boolean)}.
     * @throws Exception
     */
    @Test
    public void testGetOrdersPendingOnly() throws Exception {
    	Collection<OrderDTO> orders = delegate.getOrders(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID), true);
    	logger.trace("Retrieved pending orders: " + orders);
    	assertNotNull("No pending orders returned.", orders);
    }

    /**
     * Test for {@link OrderDelegate#getOrders(AccountLoginDTO, boolean)}.
     * @throws Exception
     */
    @Test
    public void testGetOrders() throws Exception {
    	Collection<OrderDTO> orders = delegate.getOrders(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID), false);
    	logger.trace("Retrieved all orders: " + orders);
    	assertNotNull("No orders returned.", orders);
    }
}
