/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.junit.BeforeClass;

/**
 * Tests the AccountAlertDelegate using WebServices.
 * @author Stephen Davidson
 *
 */
public class AccountAlertDelegateWSTest extends AccountAlertDelegateTest {
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateWS.setUpBeforeClass();
    }
}
