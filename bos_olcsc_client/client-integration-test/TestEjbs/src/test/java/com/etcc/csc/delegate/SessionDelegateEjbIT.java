/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.junit.BeforeClass;

/**
 * Tests the AccountAlertDelegate using Ejbs.
 * @author Stephen Davidson
 *
 */
public class SessionDelegateEjbIT extends SessionDelegateTest{

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateEjb.setUpBeforeClass();
    }
}
