/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import javax.ejb.EJBException;

import org.junit.BeforeClass;

/**
 * Tests the MenuDelegate using WebServices.
 * @author Stephen Davidson
 *
 */
public class MenuDelegateEjbIT extends MenuDelegateTest{

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateEjb.setUpBeforeClass();
    }

    @Override
    public void testGetMenusMenuCategoryNPE() throws Exception {
        try {
            super.testGetMenusMenuCategoryNPE();
        } catch (EJBException e){
            if (e.getCause() != null && e.getCause().getClass() == NullPointerException.class){
                throw (NullPointerException)e.getCause();
            }//else
            throw e;
        }
    }
    
    
}
