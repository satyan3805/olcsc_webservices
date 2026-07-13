/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.service.AccountFactory;

/**
 * Tests for AuthorizedContact delegate.
 * @author Milosh Boroyevich
 */
public class AuthorizedContactDelegateTest {
	private static final Logger logger = Logger.getLogger(AuthorizedContactDelegateTest.class);

	AuthorizedContactDelegate delegate;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseTestDelegate.setUpBeforeClass();
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.delegate = new AuthorizedContactDelegate();
    }


    /**
     * Test for {@link AuthorizedContactDelegate#getAuthContacts(AccountLoginDTO)}.
     * @throws Exception
     */
    @Test
	public void testGetAuthContacts() throws Exception {
    	AuthorizedContactDTO[] contacts = delegate.getAuthContacts(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID));
    	assertNotNull("No contacts provided.", contacts);
	}

    /**
     * Test for {@link AuthorizedContactDelegate#modifyAuthContacts(AccountLoginDTO, AuthorizedContactDTO[], String)}.
     * @throws Exception
     */
    @Test
	public void testModifyAuthContacts() throws Exception {
    	logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
//    	BaseDTO dto = delegate.modifyAuthContacts(AccountTestUtil.getAccountLoginDTO(AccountTestUtil.POPULATED_ACCOUNT_ID), authorizedContacts, password);
	}
}
