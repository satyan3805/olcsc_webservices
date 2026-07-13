/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.junit.BeforeClass;

/**
 * Tests the SessionDelegate using Ejbs.
 * @author Stephen Davidson
 *
 */
public class StateDelegateEjbIT extends StateDelegateTest{

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateEjb.setUpBeforeClass();
    }
}
