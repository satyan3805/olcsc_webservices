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
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.UserEnvDTO;

public class TagDelegateTest {
    private static final Logger logger = Logger.getLogger(TagDelegateTest.class);
    
    AccountDelegate accountDelegate;
    TagDelegate delegate;

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
        this.delegate = new TagDelegate();
        this.accountDelegate = new AccountDelegate();
    }
    
    @Test
    public void testAddTag() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    @Test
    public void testModifyTag() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    @Test
    public void testConfirmAddTags() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    @Test
    public void testAddTagsReceipt() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    @Test
    public void testGetAccountTags() throws Exception {
        AccountLoginDTO acctLoginDTO = this.accountDelegate.loginAccount("junit", "password", "127.0.0.1", "0000", new UserEnvDTO());
        AccountTagsDTO dto = this.delegate.getAccountTags(acctLoginDTO, "");
        assertNotNull(dto);
    }

    @Test
    public void testGetTagBySeqNum() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    @Test
    public void testGetMailedTags() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    @Test
    public void testActivateMailedTags() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    @Test
    public void testGetTagAuthorities() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    @Test
    public void testGetTagApplicationAgreement() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }
}
