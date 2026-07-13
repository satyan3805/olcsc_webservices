/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;

/**
 * Tests for EmailValidation delegate.
 * @author Milosh Boroyevich
 */
public class EmailValidationDelegateTest {
	private static final Logger logger = Logger.getLogger(EmailValidationDelegateTest.class);

    EmailValidationDelegate delegate;

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
        this.delegate = new EmailValidationDelegate();
    }

    /**
     * Test for {@link EmailValidationDelegate#generateEmailValidationMsg(AccountLoginDTO)}.
     * @throws Exception
     */
    @Test
    public void testGenerateEmailValidationMsg() throws Exception {
    	logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
//    	delegate.generateEmailValidationMsg(acctLoginDto);
    }

    /**
     * Test for {@link EmailValidationDelegate#getEmailValidationData}.
     * @throws Exception
     */
    @Test
    public void testEmailValidationData() throws Exception {
    	logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
//    	delegate.getEmailValidationData(acctLoginDto, request);
    }

    /**
     * Test for {@link EmailValidationDelegate#setValidationDone(AccountLoginDTO)}.
     * @throws Exception
     */
    @Test
    public void testSetValidationDone() throws Exception {
    	logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
//    	delegate.setValidationDone(acctLoginDto);
    }

    /**
     * Test for {@link EmailValidationDelegate#validationData(AccountLoginDTO)}.
     * @throws Exception
     */
    @Test
    public void testValidationData() throws Exception {
    	logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
//    	delegate.validationData(acctLoginDto);
    }

    /**
     * Test for {@link EmailValidationDelegate#validationStatus(AccountLoginDTO)}.
     * @throws Exception
     */
    @Test
    public void testValidationStatus() throws Exception {
    	logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
//    	delegate.validationStatus(acctLoginDto);
    }
}
