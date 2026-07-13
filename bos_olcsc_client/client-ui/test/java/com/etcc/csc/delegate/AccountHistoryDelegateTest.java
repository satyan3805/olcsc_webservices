/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.AccountSummaryDTO;

/**
 * @author Stephen Davidson
 */
public class AccountHistoryDelegateTest {
    private static final Logger logger = Logger.getLogger(AccountHistoryDelegateTest.class);

    AccountHistoryDelegate delegate;
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
        this.delegate = new AccountHistoryDelegate();
        this.accountLoginDTO = new AccountLoginDTO();
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getReportFilePath(AccountLoginDTO)}.
     * @throws Exception If any exceptions occur.
     */
    @Test
    public void testGetReportFilePath() throws Exception {
        String path = this.delegate.getReportFilePath(this.accountLoginDTO);
        assertNotNull(path);
        assertEquals("file:///tmp", path);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getStatementPDF(AccountLoginDTO, String)}.
     * @throws Exception If any exceptions occur.
     */
    @Test
    public void testGetStatementPDF() throws Exception {
        String pdf = this.delegate.getStatementPDF(this.accountLoginDTO, "Unknown");
        assertNull(pdf);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getSummaryPDF(AccountLoginDTO, String)}.
     */
    @Test
    public void testGetSummaryPDF() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getStatementHTML(AccountLoginDTO, String, String)}.
     * @throws Exception if any exceptions occur during testing.
     */
    @Test
    public void testGetStatementHTML() throws Exception{
        AccountStatementDTO dto = this.delegate.getStatementHTML(accountLoginDTO, "October, 2008", null);
        assertNotNull(dto);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getSummaryHTML(AccountLoginDTO, String)}.
     */
    @Test
    public void testGetSummaryHTML() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getStatementDates(AccountLoginDTO, boolean)}.
     */
    @Test
    public void testGetStatementDates() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#hasLastYearSummary(AccountLoginDTO)}.
     */
    @Test
    public void testHasLastYearSummary() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getAcccountTags(AccountLoginDTO)}.
     */
    @Test
    public void testGetAcccountTags() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getTransactionRecords(AccountLoginDTO, Calendar, Calendar, String, String, String, String, String)}.
     */
    @Test
    public void testGetTransactionRecords() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#viewTransactionMain(AccountLoginDTO, Calendar, Calendar, String)}.
     */
    @Test
    public void testViewTransactionMain() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getReportServletUrl(String)}.
     */
    @Test
    public void testGetReportServletUrl() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getReportURL(AccountLoginDTO, String, String, String)}.
     */
    @Test
    public void testGetReportURL() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getInvoiceDates(AccountLoginDTO)}.
     */
    @Test
    public void testGetInvoiceDates() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getMonthlyInvoices(String, AccountLoginDTO)}.
     */
    @Test
    public void testGetMonthlyInvoices() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getAccountReceipts(AccountLoginDTO, Calendar, Calendar)}.
     */
    @Test
    public void testGetAccountReceipts() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getAccountReceiptDetails(AccountLoginDTO, long, String)}.
     */
    @Test
    public void testGetAccountReceiptDetails() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountHistoryDelegate#getSummaryGraph(AccountLoginDTO)}.
     * @throws Exception If any exceptions occur during the test.
     */
    @Test
    public void testGetSummaryGraph() throws Exception {
        AccountSummaryDTO summary = this.delegate.getSummaryGraph(this.accountLoginDTO);
        assertNotNull(summary);
    }
}
