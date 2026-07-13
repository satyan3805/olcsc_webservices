/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.junit.BeforeClass;

/**
 * Tests the TagDelegate using Ejbs.
 * @author Stephen Davidson
 *
 */
public class TagDelegateEjbIT extends TagDelegateTest {
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateEjb.setUpBeforeClass();
    }
    
}
