/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.VelcroDTO;
import com.etcc.csc.service.AccountFactory;
import com.etcc.csc.service.VelcroFactory;

/**
 * Tests for Velcro delegate.
 * @author Milosh Boroyevich
 */
public class VelcroDelegateTest {
	private static final Logger logger = Logger.getLogger(VelcroDelegateTest.class);

	VelcroDelegate delegate;

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
        this.delegate = new VelcroDelegate();
    }

    /**
     * Test for {@link VelcroDelegate#getVelcroInfo(AccountLoginDTO)}.
     * @throws Exception
     */
    @Test
    public void testGetVelcroInfo() throws Exception {
    	VelcroDTO velcro = delegate.getVelcroInfo(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID));
    	logger.debug("Retrieved velcro info: " + velcro);
    	assertNotNull("No velcro info generated.", velcro);
    }

    /**
     * Test for {@link VelcroDelegate#getVelcroReceiptPDF(AccountLoginDTO)}.
     * @throws Exception
     */
    @Test
    public void testGetVelcroReceiptPDF() throws Exception {
    	String url = delegate.getVelcroReceiptPDF(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID));
        assertNotNull("No url generated for velcro PDF receipt.", url);
    }

    /**
     * Test for {@link VelcroDelegate#submitVelcroRequest(AccountLoginDTO, int)}.
     * @throws Exception
     */
    @Test
    public void testSubmitVelcroRequest() throws Exception {
    	ResultDTO result = delegate.submitVelcroRequest(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID), VelcroFactory.TEST_VELCRO_QUANTITY);
    	assertNull("Errors encountered submitting velcro request.", result.getErrors());
    }
}
