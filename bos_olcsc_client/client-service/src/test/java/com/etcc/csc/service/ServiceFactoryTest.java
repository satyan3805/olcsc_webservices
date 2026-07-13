/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;

/**
 * @author Stephen Davidson
 */
public class ServiceFactoryTest {
    private static final Class<ServiceFactoryTestImpl> TEST_IMPL_CLASS = ServiceFactoryTestImpl.class;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty(ServiceFactory.IMPL, TEST_IMPL_CLASS.getName() );
    }

    /**
     * Test method for {@link ServiceFactory#loadDefaultServiceFactory(java.lang.String)}.
     * @throws Exception If any exceptions occur loading the ServiceFactory Impl.
     */
    @Test
    public void testDefaultLoadServiceFactory() throws Exception {
        final ServiceFactory factory = ServiceFactory.loadDefaultServiceFactory(TEST_IMPL_CLASS.getName());
        assertNotNull(factory);
        assertTrue("Service Factory not instance of ServiceFactoryTestImpl: " + factory.getClass().getName(), 
                factory instanceof ServiceFactoryTestImpl);
        //end testDefaultLoadServiceFactory
    }

    /**
     * Test method for {@link ServiceFactory#getImpl(java.lang.Class)}.
     * @throws Exception If any errors occur during the test.
     */
    @Test
    public void testGetImpl() throws Exception {
        final ServiceFactory factory = ServiceFactory.loadDefaultServiceFactory(TEST_IMPL_CLASS.getName());
        AccountInterface mockImpl = factory.getImpl(AccountInterface.class);
        assertNotNull(mockImpl);
        assertNull(mockImpl.getAccount(null));
        AccountLoginDTO dto = new AccountLoginDTO();
        dto.setAcctId(AccountFactory.BASIC_ACCOUNT_ID);
        dto.setLoginType(AccountLoginDTO.LoginType.AC);
        assertNotNull(mockImpl.getAccount(dto));
        dto.setLoginType(AccountLoginDTO.LoginType.IN);
        assertNull(mockImpl.getAccount(dto));
        assertNull(mockImpl.getAccount(null));
        //end testGetImp
    }

    /**
     * Test method for {@link ServiceFactory#getImplementation(java.lang.Class)}.
     * @throws Exception If any exceptions occur.
     */
    @Test
    public void testGetImplementation() throws Exception {
        final ServiceFactory factory = ServiceFactory.loadDefaultServiceFactory(TEST_IMPL_CLASS.getName());
        AccountInterface mockImpl = factory.getImpl(AccountInterface.class);
        assertNotNull(mockImpl);
        assertNull(mockImpl.getAccount(null));
        AccountLoginDTO dto = new AccountLoginDTO();
        dto.setAcctId(AccountFactory.BASIC_ACCOUNT_ID);
        dto.setLoginType(AccountLoginDTO.LoginType.AC);
        assertNotNull(mockImpl.getAccount(dto));
        //end testGetImplementation
    }
}
