/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dao.dummy.DummyAccountAlertDAO;

/**
 * @author Stephen Davidson
 *
 */
public class GenericDAOFactoryTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty(DAOFactory.IMPL, JUnitGenericDAOFactory.class.getName());
        System.setProperty("ETCC_HCTRA_DATABASE", "Dummy");
    }
    
    /**
     * Test method for {@link com.etcc.csc.dao.GenericDAOFactory#getDAO(java.lang.Class)}.
     */
    @Test
    public void testGetDAO() {
        final GenericDAOFactory f = new JUnitGenericDAOFactory();
        AccountAlertDAO dao = f.getDAO(AccountAlertDAO.class);
        assertNotNull("Missing Dao", dao);
        assertSame("Class mismatch: ", DummyAccountAlertDAO.class, dao.getClass());
    }
}
