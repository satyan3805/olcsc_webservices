/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.it.basic;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.etcc.csc.it.BaseIntegrationTest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

/**
 * Tests that the login pages and options work.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class LoginIT extends BaseIntegrationTest {
    private static final Logger logger = Logger.getLogger(LoginIT.class);

    /**
     * Tests the Account Login Page.
     * @throws Exception if any errors occur during testing.
     */
    @Test
    public void testAccountLogin() throws Exception {
    	WebResponse resp = getAccountLoginResponse();
        if (resp == null) return;  // could not connect to server
        WebLink[] links = resp.getLinks();
        logger.debug(toString(links));
    }

    /**
     * Tests the Violator Login Page.
     * @throws Exception if any errors occur during testing.
     */
    @Test
    public void testViolatorLogin() throws Exception {
        WebResponse resp = getViolatorLoginResponse();
        if (resp == null) return;  // could not connect to server
        WebLink[] links = resp.getLinks();
        logger.debug(toString(links));
    }
}
