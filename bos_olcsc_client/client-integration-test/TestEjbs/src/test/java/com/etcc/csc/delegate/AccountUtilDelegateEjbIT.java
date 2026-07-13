/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.junit.BeforeClass;

/**
 * Tests the AccountUtilDelegate using Ejbs.
 * @author Stephen Davidson
 *
 */
public class AccountUtilDelegateEjbIT extends AccountAlertDelegateTest {
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateEjb.setUpBeforeClass();
    }
}
