/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountReceiptDetailsDTO;
import com.etcc.csc.dto.AccountReceiptsDTO;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.AccountSummaryDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.AccountTransactionHistoryResponse;
import com.etcc.csc.dto.AccountYearlySummaryDTO;
import com.etcc.csc.dto.InvoiceDatesDTO;
import com.etcc.csc.dto.MonthlyInvoicesDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.dto.SummaryAvailableDTO;
import com.etcc.csc.dto.TransactionRecordsDTO;
import com.etcc.csc.dto.bean.OlcAccountHistoryRecBean;
import com.etcc.csc.dto.bean.OlcAccountTagRecBean;
import com.etcc.csc.dto.bean.OlcViewMonthlyRecBean;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
//TODO: Switch back to the Factory when JMock supports matching Multiple Parameters for a method.
public class AccountHistoryTestImpl implements AccountHistoryInterface {

    /**
     * @see AccountHistoryInterface#getAcccountTags(AccountLoginDTO)
     */
    public OlcAccountTagRecBean[] getAcccountTags(AccountLoginDTO accountLoginDTO) throws EtccException,
            EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("getAcccountTags not implemented yet.");
        //end getAcccountTags

    }

    /**
     * @see AccountHistoryInterface#getAccountReceiptDetails(AccountLoginDTO, long, String)
     */
    public AccountReceiptDetailsDTO getAccountReceiptDetails(AccountLoginDTO login, long transactionID, String HASFlag)
            throws EtccSecurityException, EtccException {

        //return null;
        throw new UnsupportedOperationException("getAccountReceiptDetails not implemented yet.");
        //end getAccountReceiptDetails

    }

    /**
     * @see AccountHistoryInterface#getAccountReceipts(AccountLoginDTO, Calendar, Calendar)
     */
    public AccountReceiptsDTO getAccountReceipts(AccountLoginDTO login, Calendar start, Calendar end)
            throws EtccSecurityException, EtccException {

        //return null;
        throw new UnsupportedOperationException("getAccountReceipts not implemented yet.");
        //end getAccountReceipts

    }

    /**
     * @see AccountHistoryInterface#getInvoiceDates(AccountLoginDTO)
     */
    public InvoiceDatesDTO getInvoiceDates(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("getInvoiceDates not implemented yet.");
        //end getInvoiceDates

    }

    /**
     * @see AccountHistoryInterface#getMonthlyInvoices(String, AccountLoginDTO)
     */
    public MonthlyInvoicesDTO getMonthlyInvoices(String statementMonth, AccountLoginDTO accountLogin)
            throws EtccException, EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("getMonthlyInvoices not implemented yet.");
        //end getMonthlyInvoices

    }

    /**
     * @see AccountHistoryInterface#getReportFilePath(AccountLoginDTO)
     */
    public String getReportFilePath(AccountLoginDTO accountLoginDTO) throws EtccException, EtccReportException {
        return AccountHistoryFactory.FILE_PATH;
    }

    /**
     * @see AccountHistoryInterface#getReportServletUrl(String)
     */
    public String getReportServletUrl(String reportName) throws EtccException {

        //return null;
        throw new UnsupportedOperationException("getReportServletUrl not implemented yet.");
        //end getReportServletUrl

    }

    /**
     * @see AccountHistoryInterface#getReportURL(AccountLoginDTO, String, String, String)
     */
    public String getReportURL(AccountLoginDTO loginDto, String format, String name, String args)
            throws EtccException, EtccSecurityException {
        return AccountHistoryFactory.REPORT_URL;
    }

    /**
     * @see AccountHistoryInterface#getStatementDates(AccountLoginDTO, boolean)
     */
    public StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO, boolean isMonthly)
            throws EtccException, EtccSecurityException {
        StatementDatesDTO dto = new StatementDatesDTO();
        if (accountLoginDTO.getLoginId().equals(AccountFactory.ACCOUNT_USERNAME)){
            int size = 5;
            String[] dates = new String[size];
            dto.setDates(dates);
        }
        return dto;
        //end getStatementDates

    }

    /**
     * @see AccountHistoryInterface#getStatementHTML(AccountLoginDTO, String, String)
     */
    public AccountStatementDTO getStatementHTML(AccountLoginDTO accountLoginDTO, String postDate, String acctVPNType)
            throws EtccException, EtccSecurityException {
        AccountStatementDTO dto = new AccountStatementDTO();
        dto.setAccountId(String.valueOf(accountLoginDTO.getAcctId()));
        OlcViewMonthlyRecBean records[] = new OlcViewMonthlyRecBean[1];
        records[0] = new OlcViewMonthlyRecBean();
        records[0].setAmount(new BigDecimal(3.00));
        records[0].setDirection("South");
        records[0].setLane("10");
        records[0].setLaneFullName("Lane 10");
        records[0].setLocation("Location 5");
        records[0].setPostedDate(Calendar.getInstance());
        records[0].setSerialNumber(new BigDecimal(1000));
        records[0].setTagId("10101000");
        records[0].setTransactionDate(Calendar.getInstance());
        dto.setRecords(records);
        return dto;
        //end getStatementHTML
    }

    /**
     * @see AccountHistoryInterface#getStatementPDF(AccountLoginDTO, String)
     */
    public String getStatementPDF(AccountLoginDTO accountLoginDTO, String accountVPNType) throws EtccException,
            EtccSecurityException {
        return null;
        //end getStatementPDF

    }

    /**
     * @see AccountHistoryInterface#getSummaryGraph(AccountLoginDTO)
     */
    public AccountSummaryDTO getSummaryGraph(AccountLoginDTO login) throws EtccSecurityException, EtccException {
        return AccountHistoryFactory.makeSummaryGraph();
    }

    /**
     * @see AccountHistoryInterface#getSummaryHTML(AccountLoginDTO, String)
     */
    public AccountYearlySummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO, String postDate)
            throws EtccException, EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("getSummaryHTML not implemented yet.");
        //end getSummaryHTML

    }

    /**
     * @see AccountHistoryInterface#getSummaryPDF(AccountLoginDTO, String)
     */
    public String getSummaryPDF(AccountLoginDTO accountLoginDTO, String accountVPNType) throws EtccException,
            EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("getSummaryPDF not implemented yet.");
        //end getSummaryPDF

    }

    /**
     * @see AccountHistoryInterface#getTransactionRecords(AccountLoginDTO, Calendar, Calendar, String, String, String, String, String)
     */
	 // QC_11613 dateType field added
    public TransactionRecordsDTO getTransactionRecords(AccountLoginDTO accountLoginDTO, Calendar startDate,
            Calendar endDate, String agencyId, String tagId, String transFilter, String nickName, String nickNameType,String dateType)
            throws EtccException, EtccSecurityException {
        TransactionRecordsDTO dto = new TransactionRecordsDTO();
        //Add some transactions
        final int size = 4;
        double total = 0;
        OlcAccountHistoryRecBean[] records = new OlcAccountHistoryRecBean[size];
        for(int i = 0; i< size; i++){
            OlcAccountHistoryRecBean record = new OlcAccountHistoryRecBean();
            record.setAmount(BigDecimal.valueOf(i));
            total += i;
            record.setDirection( i % 2 == 0 ? "North" : "South");
            Calendar txDate = Calendar.getInstance();
            txDate.add(Calendar.DAY_OF_MONTH, -i);
            record.setTrxnDate(txDate);
            Calendar postDate = (Calendar)txDate.clone();
            postDate.add(Calendar.DAY_OF_MONTH, 1);
            record.setPostedDate(postDate);
            record.setSerialNum(BigDecimal.valueOf(i + 1));
            record.setLocationName("Toll Plaza " + (i/2));
            records[i] = record;
        }
        dto.setRecords(records);
        dto.setTotalAmt(total);
        return dto;
        //end getTransactionRecords

    }

    /**
     * @see AccountHistoryInterface#hasLastYearSummary(AccountLoginDTO)
     */
    public SummaryAvailableDTO hasLastYearSummary(AccountLoginDTO accountLoginDTO) throws EtccException,
            EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("hasLastYearSummary not implemented yet.");
        //end hasLastYearSummary

    }

    /**
     * @see AccountHistoryInterface#viewTransactionMain(AccountLoginDTO, Calendar, Calendar, String)
     */
    public AccountTransactionDTO viewTransactionMain(AccountLoginDTO accountLoginDTO, Calendar startDate,
            Calendar endDate, String acctVPNType) throws EtccException, EtccSecurityException {
        AccountTransactionDTO dto = new AccountTransactionDTO();
        return dto;
        //end viewTransactionMain
    }

	public AccountTransactionHistoryResponse getAccountTransactionHistory(Long acctId, String docType, String sessionId,
			String ipAddress, String userId, Date fromDate, Date toDate, String dateType, List<Integer> categoryTab,
			List<Integer> subCategoryTab, List<Integer> acctVehIds, List<Integer> acctTagIds, Integer pageSize,
			Integer pageNumber, Integer sortOrder, BigDecimal eventId, Integer paramId, Integer maxReturnRows)
			throws EtccException {
		throw new UnsupportedOperationException("getAccountTransactionHistory not implemented yet.");
	}

    
}
