/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.common.FileFormat;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ReportDTO;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.service.ReportTest;

/**
 * Tests for the report delegate.
 * @author Milosh Boroyevich
 */
public class ReportDelegateTest extends ReportTest {
//    private static final Logger logger = Logger.getLogger(ReportDelegateTest.class);

    ReportDelegate delegate;

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
    @Override
    @Before
    public void setUp() throws Exception {
        this.delegate = new ReportDelegate();
    }

    /**
     * Test method for {@link ReportDelegate#getReport(ReportInterface.ReportType, FileFormat, String, AccountLoginDTO)}
     * @throws Exception if any exceptions occur during this test.
     */
    @Override
    @Test
    public void testGetReport() throws Exception {
        ReportDTO report = getReport(this.delegate);
    }

    /**
     * Convert report test is only relevant at service-tier testing.
     */
    @Override
    @Test
    public void testConvertReportUrl() throws Exception {
        // this test not applicable at the delegate tier
    }
}
