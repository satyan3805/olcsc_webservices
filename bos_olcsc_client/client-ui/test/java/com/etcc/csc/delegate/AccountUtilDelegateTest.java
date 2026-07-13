/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.UserEnvDTO;

/**
 * @author Stephen Davidson
 */
public class AccountUtilDelegateTest {
//    private static final Logger logger = Logger.getLogger(AccountUtilDelegateTest.class);
    AccountUtilDelegate delegate;
    AccountLoginDTO accountLoginDTO;

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
        this.delegate = new AccountUtilDelegate();
        this.accountLoginDTO = new AccountLoginDTO();
    }

    /**
     * Test method for {@link AccountUtilDelegate#closeAccount(AccountLoginDTO, String)}.
     * @throws Exception thrown if any exceptions occur during the test.
     */
    @Test
    public void testCloseAccount() throws Exception {
        ResultDTO response = this.delegate.closeAccount(this.accountLoginDTO, null);
        assertNotNull(response);
        assertTrue(response.hasErrors());
    }

    /**
     * Test method for {@link AccountUtilDelegate#autoLogin(String, String, String, String, String, UserEnvDTO)}.
     * @throws Exception thrown if any exceptions occur during test.
     */
    @Test
    public void testAutoLogin() throws  Exception {
        this.accountLoginDTO = this.delegate.autoLogin("JUnit", "00001", "127.0.0.2", "0000001", "000101", null);
        assertNotNull(this.accountLoginDTO);
    }

    /**
     * Test method for {@link AccountUtilDelegate#generatePlateReminder(AccountLoginDTO, String)}.
     * @throws Exception thrown if any exceptions occur.
     */
    @Test
    public void testGeneratePlateReminder() throws Exception {
        ResultDTO response = this.delegate.generatePlateReminder(this.accountLoginDTO, "abc123");
        assertNull(response);
    }

    /**
     * Test method for {@link AccountUtilDelegate#getCloseAccountAgreement()}.
     * @throws Exception thrown if any exceptions occur.
     */
    @Test
    public void testGetCloseAccountAgreement() throws Exception{
        String response = this.delegate.getCloseAccountAgreement();
        assertNotNull(response);
    }

    /**
     * Test method for {@link AccountUtilDelegate#checkCloseAccount(AccountLoginDTO)}.
     * @throws Exception thrown if any exceptions occur.
     */
    @Test
    public void testCheckCloseAccount() throws Exception {
        AccountLoginDTO response = this.delegate.checkCloseAccount(this.accountLoginDTO);
        assertNull(response);
    }

}
