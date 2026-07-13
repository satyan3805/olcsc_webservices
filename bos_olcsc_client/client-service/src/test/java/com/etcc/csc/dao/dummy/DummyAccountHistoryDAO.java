/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.dummy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountHistoryDAO;
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
import com.etcc.csc.dto.bean.AccountSummaryDetailBean;
import com.etcc.csc.dto.bean.OlcAccountTagRecBean;
import com.etcc.csc.service.AccountHistoryFactory;
import com.etcc.csc.service.AccountHistoryInterface;
import com.etcc.csc.service.AccountHistoryTestImpl;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class DummyAccountHistoryDAO extends AccountHistoryDAO {

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
    public String getReportFilePath(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException, EtccReportException {
        return (AccountHistoryFactory.FILE_PATH);
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
        throw new UnsupportedOperationException("getStatementDates not implemented yet.");
    }

    /**
     * @see AccountHistoryInterface#getStatementHTML(AccountLoginDTO, String, String)
     */
    public AccountStatementDTO getStatementHTML(AccountLoginDTO accountLoginDTO, String postDate, String acctVPNType)
            throws EtccException, EtccSecurityException {
        return new AccountHistoryTestImpl().getStatementHTML(accountLoginDTO, postDate, acctVPNType);
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
        final AccountSummaryDTO dto = AccountHistoryFactory.makeSummaryGraph();
        dto.setLastLoginDate(login.getLastLoginDate());
        return dto;
        //end getSummaryGraph
    }

    @SuppressWarnings("unused")
	private AccountSummaryDetailBean[] makeSummaryDetails(){
        AccountSummaryDetailBean[] details = new AccountSummaryDetailBean[3];
        int i = 0;
        details[i++] = new AccountSummaryDetailBean("Payment", BigDecimal.valueOf(50));
        details[i++] = new AccountSummaryDetailBean("Tolls", BigDecimal.valueOf(2.25));
        details[i++] = new AccountSummaryDetailBean("Tolls", BigDecimal.valueOf(4.50));
        return details;
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

        //return null;
        throw new UnsupportedOperationException("getTransactionRecords not implemented yet.");
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

        //return null;
        throw new UnsupportedOperationException("viewTransactionMain not implemented yet.");
        //end viewTransactionMain

    }

	@Override
	protected String getMonthDesc(Object statementDate) throws SQLException {
        throw new UnsupportedOperationException();
	}

	public AccountTransactionHistoryResponse getAccountTransactionHistory(Long acctId, String docType, String sessionId,
			String ipAddress, String userId, Date fromDate, Date toDate, String dateType, List<Integer> categoryTab,
			List<Integer> subCategoryTab, List<Integer> acctVehIds, List<Integer> acctTagIds, Integer pageSize,
			Integer pageNumber, Integer sortOrder, BigDecimal eventId, Integer paramId, Integer maxReturnRows)
			throws EtccException {
		throw new UnsupportedOperationException("getAccountTransactionHistory not implemented yet.");
	}

	
}
