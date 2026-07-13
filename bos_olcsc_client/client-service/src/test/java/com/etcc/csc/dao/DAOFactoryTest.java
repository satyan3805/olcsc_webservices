/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dao.dummy.DummyUtil;
import com.etcc.csc.dao.oracle.OracleUtil;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class DAOFactoryTest {
    private static final Logger logger = Logger.getLogger(DAOFactoryTest.class);

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty(DAOFactory.IMPL, JUnitGenericDAOFactory.class.getName());
        System.setProperty("ETCC_HCTRA_DATABASE", "Dummy");
    }


    /**
     * Test method for {@link DAOFactory#getDefaultFactoryImplName()}.
     */
    @Test
    public void testGetDefaultFactoryImplName() {
        assertEquals(JUnitGenericDAOFactory.class.getName(), DAOFactory.getDefaultFactoryImplName());
        System.setProperty(DAOFactory.IMPL, DAOFactory.DEFAULT_IMPL);
        assertEquals(DAOFactory.DEFAULT_IMPL, DAOFactory.getDefaultFactoryImplName());
        //end testGetDefaultFactoryImplName
    }

    /**
     * Test method for {@link DAOFactory#loadDaoFactory(String)}.
     * @throws Exception If any exceptions occur.
     */
    @Test
    public void testLoadDaoFactory() throws Exception {
        DAOFactory f = DAOFactory.loadDaoFactory(JUnitGenericDAOFactory.class.getName());
        assertSame(JUnitGenericDAOFactory.class, f.getClass());
        //end testLoadDaoFactory
    }

    /**
     * Test method for {@link DAOFactory#getDAOFactory()}.
     */
    @Test
    public void testGetDAOFactory() {
        DAOFactory f = DAOFactory.getDAOFactory();
        assertSame(JUnitGenericDAOFactory.class, f.getClass());
        //end testGetDAOFactory
    }

    /**
     * Test method for {@link DAOFactory#getDAO(Class)}.
     */
    @Test
    public void testGetDAO() {
        BaseDAO dao = DAOFactory.getDAOFactory().getDAO(AccountAlertDAO.class);
        assertTrue("DAO Mismatch", dao instanceof AccountAlertDAO);
    }

    /**
     * Test method for {@link DAOFactory#invokeUtility(String)}.
     * @throws Exception if any exceptions occur
     */
    @Test
    public void testInvokeUtility() throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, Class<?>> factoryTypeMap = (Map<String, Class<?>>) DAOFactory.getDAOFactory().invokeUtility("getDbTypeMap");
        logger.debug("Type Map: " + factoryTypeMap);
        if (GenericDAOFactory.getDatabaseName().equals("oracle")) {
            Map<String, Class<?>> dbTypeMap = OracleUtil.getDbTypeMap();
            assertEquals("Type map from DAO Factory and Utility class not the same!", factoryTypeMap, dbTypeMap);
        } else if (GenericDAOFactory.getDatabaseName().equals("dummy")) {
            Map<String, Class<?>> dbTypeMap = DummyUtil.getDbTypeMap();
            assertEquals("Type map from DAO Factory and Utility class not the same!", factoryTypeMap, dbTypeMap);
        }
    }
}
