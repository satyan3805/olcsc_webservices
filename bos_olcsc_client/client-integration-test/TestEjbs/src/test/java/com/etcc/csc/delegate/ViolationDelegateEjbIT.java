/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.ViolatorDTO;

/**
 * Tests the ViolationDelegate using EJBs.
 * @author Stephen Davidson
 *
 */
public class ViolationDelegateEjbIT extends ViolationDelegateTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateEjb.setUpBeforeClass();
    }

    /**
     * Test method for {@link ViolationDelegate#makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)}.
     * @throws Exception if any errors occur during testing.
     * @see #makePaymentInvalidCreditCard()
     */
    @Test
    public void testMakePaymentInvalidCreditCard() throws Exception {
    	makePaymentInvalidCreditCard();
    }
}
