/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.dao.oracle;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author (task 488) Stephen Davidson
 *
 */
public class OracleUtilTest {
    private static final Logger logger = Logger.getLogger(OracleUtilTest.class);

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link com.etcc.csc.dao.oracle.OracleUtil#convertToMessages(java.sql.Array)}.
     */
    @Test
    public void testConvertToMessagesArray() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dao.oracle.OracleUtil#getCallerClass()}.
     */
    @Test
    public void testGetCallerClass() {
        Class<?> clazz = callerClassWrapper(2); //Call wrapper of current test method, to insure correct class returned.
        assertEquals("Class Mismatch: ", OracleUtilTest.class, clazz);
    }
    
    /**
     * Get Caller Class needs to go up "two" levels from its caller in order to determine the class that call OracleUtil.
     * @return Should return this test class.
     */
    private Class<?> callerClassWrapper(int depth){
        if (depth == 0){
            return OracleUtil.getCallerClass();
        }//else
        return callerClassWrapper(depth - 1);
    }

    /**
     * Test method for {@link com.etcc.csc.dao.oracle.OracleUtil#convertToMessageList(com.etcc.csc.plsql.OLC_ERROR_MSG_ARR[])}.
     */
    @Test
    public void testConvertToMessageListOLC_ERROR_MSG_ARRArray() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dao.oracle.OracleUtil#convertToMessageList(com.etcc.csc.plsql.OLC_ERROR_MSG_REC[])}.
     */
    @Test
    public void testConvertToMessageListOLC_ERROR_MSG_RECArray() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dao.oracle.OracleUtil#convertToMessages(com.etcc.csc.plsql.OLC_ERROR_MSG_ARR[])}.
     */
    @Test
    public void testConvertToMessagesOLC_ERROR_MSG_ARRArray() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dao.oracle.OracleUtil#convertToMessages(com.etcc.csc.plsql.OLC_ERROR_MSG_ARR[], java.lang.Object)}.
     */
    @Test
    public void testConvertToMessagesOLC_ERROR_MSG_ARRArrayObject() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dao.oracle.OracleUtil#convertToMessages(com.etcc.csc.plsql.OLC_ERROR_MSG_REC[])}.
     */
    @Test
    public void testConvertToMessagesOLC_ERROR_MSG_RECArray() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.dao.oracle.OracleUtil#convertToMessages(com.etcc.csc.plsql.OLC_ERROR_MSG_REC[], java.lang.Object)}.
     */
    @Test
    public void testConvertToMessagesOLC_ERROR_MSG_RECArrayObject() {
        logger.warn("Not yet implemented");
    }

}
