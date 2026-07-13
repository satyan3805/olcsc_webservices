/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.StateDTO;

/**
 * Tests the AccountAlertDelegate using WebServices.
 * @author Stephen Davidson
 *
 */
public class StateDelegateTest {

    StateDelegate delegate;
    
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
        this.delegate = new StateDelegate();
    }

    /**
     * Test method for {@link com.etcc.csc.delegate.StateDelegate#getStates()}.
     * @throws Exception if any exceptions occur.
     */
    @Test
    public void testGetStates() throws Exception {
        StateDTO[] states = this.delegate.getStates();
        assertNotNull("No states retrieved.", states);
        assertTrue("Empty list of states retrieved.", states.length > 0);
    }

}
