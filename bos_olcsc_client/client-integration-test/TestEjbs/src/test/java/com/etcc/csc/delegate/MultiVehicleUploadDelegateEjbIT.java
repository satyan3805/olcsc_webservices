/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.junit.BeforeClass;

/**
 * Tests the multi vehicle upload using EJBs.
 * @author Milosh Boroyevich
 */
public class MultiVehicleUploadDelegateEjbIT extends MultiVehicleUploadDelegateTest {
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateEjb.setUpBeforeClass();
    }
}
