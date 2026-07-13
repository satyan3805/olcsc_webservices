/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.apache.log4j.Logger;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.fault.XFireFault;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the ReportDelegate using WebServices.
 * @author Milosh Boroyevich
 */
public class ReportDelegateWSTest extends ReportDelegateTest {
    private static final Logger logger = Logger.getLogger(ReportDelegateWSTest.class);

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateWS.setUpBeforeClass();
    }

    @Override
    @Test
    public void testGetReport() throws Exception {
        try {
            super.testGetReport();
        } catch (XFireRuntimeException e) {
            Throwable e2 = e.getCause();
            if (e2 != null && e2.getClass() == XFireFault.class) {
                logger.error("Network error in testGetReport: " + e2.getMessage(), e2);
            } else {
                throw e;
            }
        }
    }
}
