/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.fault.XFireFault;
import org.junit.BeforeClass;

/**
 * Tests the MenuDelegate using WebServices.
 * @author Stephen Davidson
 */
public class MenuDelegateWSTest extends MenuDelegateTest {
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateWS.setUpBeforeClass();
    }

    @Override
    public void testGetMenusMenuCategoryNPE() throws Exception {
        try {
            super.testGetMenusMenuCategoryNPE();
        } catch (XFireRuntimeException e){
            XFireFault fault = (XFireFault)e.getCause();
            if (fault != null && fault.getMessage() != null && fault.getMessage().endsWith(NullPointerException.class.getName())){
                throw new NullPointerException("Wrapped NPE for WS Exception caused by a NPE on the server side.");
            } //else
            throw e;
        }
    }
}
