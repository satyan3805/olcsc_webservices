/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ReportDTO;
import com.etcc.csc.util.CoreDateUtil;

/**
 * Tests for the reports service.
 * @author Milosh Boroyevich
 */
public class ReportTest {
    private static final Logger logger = Logger.getLogger(ReportTest.class);

    private static boolean initialized = false;

    Report reportService;

    /**
     * Do the initial class level setup.  Called by the setupBeforeClass method in the child classes.
     * @throws Exception If any exceptions occur during setup.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initialized) {
            SetupFactories.setUpBeforeClass();
            initialized = true;
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.reportService = new Report();
    }

    /**
     * Test method for {@link Report#convertReport(String)}.
     * @throws Exception if any exceptions occur during this test.
     */
    @Test
    public void testConvertReportUrl() throws Exception {
        try {
            ReportDTO report = this.reportService.convertReport(AccountHistoryFactory.REPORT_URL,0,"xyz");
            assertNotNull("Report is null.", report);
            assertTrue("Report is empty.", report.size() > 0);
        } catch (IOException e) {
            logger.error("Network error in testConvertReportUrl: " + e.getMessage(), e);
        }
    }

    /**
     * Test method for {@link Report#getReport(ReportInterface.ReportType, FileFormat, String, AccountLoginDTO)}
     * @throws Exception if any exceptions occur during this test.
     */
    @Test
    public void testGetReport() throws Exception {
        ReportDTO report = getReport(this.reportService);
    }

    /**
     * Invoke <tt>getReport()</tt> on the specified report interface implementation and return the result.
     * @param reportInterface implementation of the report interface
     * @return report returned by the method invocation
     * @throws EtccException if any exceptions occur while getting the report
     * @see ReportInterface#getReport(ReportInterface.ReportType, FileFormat, String, AccountLoginDTO)
     */
    public static ReportDTO getReport(ReportInterface reportInterface) throws EtccException {
        Calendar cal = Calendar.getInstance();
        String endString = CoreDateUtil.getShortDate(cal); // today
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-1);
        String startString = CoreDateUtil.getShortDate(cal); // today's date last year
        String nickNameValue = "All Vehicles";
        String transactionType = "All Transactions";
        String dateType = "P";
        String args="p_acct_id="+AccountFactory.POPULATED_ACCOUNT_ID+"&p_date_from="+startString+"&p_date_to="+endString+"&p_vehicle_filter="+nickNameValue.replaceAll("\\s", "%20")+"&p_trans_type_filter="+transactionType.replaceAll("\\s", "%20")+"&p_date_type="+dateType;
        ReportDTO report = null;
        try {
            report = reportInterface.getReport(
                ReportInterface.ReportType.TRANS_REP,
                FileFormat.PDF,
                args,
                AccountFactory.getAccountLoginDTO());
            assertNotNull("Report is null.", report);
            assertTrue("Report is empty.", report.size() > 0);
            logger.debug("Retrieved report of length: " + report.size());
        } catch (EtccException e) {
            Throwable ioe = e.getCause();
            if (ioe != null && ioe instanceof IOException)
                logger.error("Network error in getReport: " + ioe.getMessage(), ioe);
            else
                throw e;
        }
        return report;
    }
}
