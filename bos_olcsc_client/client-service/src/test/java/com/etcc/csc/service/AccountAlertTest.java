/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;

/**
 * @author (task 488) Stephen Davidson
 */
public class AccountAlertTest {
    private static final Logger logger = Logger.getLogger(AccountAlertTest.class);
    
    private AccountAlert service;

    private static boolean initialized = false;
    
    /**
     * Do the initial class level setup.  Called by the setupBeforeClass method in the child classes.
     * @throws Exception If any exceptions occur during setup.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initialized) {
            SetupFactories.setUpBeforeClass();
            initialized = true;
        }
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.service = new AccountAlert();
    }

    /**
     * Test method for {@link AccountAlert#getAcctSummAlerts(AccountLoginDTO)}.
     * @throws Exception NPE should be thrown by this test.
     */
    @Test(expected=NullPointerException.class)
    public void testGetAcctSummAlertsWithExceptions() throws Exception {
        logger.debug("getAcctSummAlerts(null) should throw NPE.");
        this.service.getAcctSummAlerts(null);
    }

    /**
     * Test method for {@link AccountAlert#getAcctSummAlerts(AccountLoginDTO)}.
     */
    @Test
    public void testGetAcctSummAlerts() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link AccountAlert#getVehicleInfoAlerts(AccountLoginDTO)}.
     */
    @Test
    public void testGetVehicleInfoAlerts() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link AccountAlert#getContactInfoAlerts(AccountLoginDTO)}.
     */
    @Test
    public void testGetContactInfoAlerts() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link AccountAlert#addressCleanseAlertResponse(AccountLoginDTO, String)}.
     */
    @Test
    public void testAddressCleanseAlertResponse() {
        logger.warn("Not yet implemented");
    }
}
