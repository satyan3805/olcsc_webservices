/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

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
import com.etcc.csc.dto.bean.OlcAccountTagRecBean;
import com.etcc.csc.service.AccountHistoryInterface;
import com.etcc.csc.service.ServiceFactory;

/**
 * Delegate for the AccountHistory service.
 * @author original author unknown
 * @author Stephen Davidson (task 488)
 */
public class AccountHistoryDelegate implements AccountHistoryInterface {
//    private static final Logger logger = Logger.getLogger(AccountHistoryDelegate.class);

    /*public String getStatement(AccountLoginDTO accountLoginDTO, Date postDate,
                               String reportFormat) throws EtccException, EtccSecurityException{
        try {
            return stub().getStatement(convert(accountLoginDTO), convert(postDate), reportFormat);
        } catch (Throwable t) {
            throw new EtccException("Error running accountExists: " + t, t);
        }
    }*/


    /*public String getTransactions(AccountLoginDTO accountLoginDTO,
                                  Date startDate, Date endDate,
                                  String agencyId, String tagId,
                                  String transactionTypeCode,
                                  String reportFormat) throws EtccException, EtccSecurityException {

        try {
            return stub().getTransactions(convert(accountLoginDTO), convert(startDate), convert(endDate), agencyId, tagId, transactionTypeCode, reportFormat);
        } catch (Throwable t) {
            throw new EtccException("Error running accountExists: " + t, t);
        }
    }*/

    /*public String getSummary(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException {
        try {
            return stub().getSummary(convert(accountLoginDTO));
        } catch (Throwable t) {
            throw new EtccException("Error running accountExists: " + t, t);
        }
    }*/

    public String getReportFilePath(AccountLoginDTO accountLoginDTO) throws EtccException, EtccReportException {
        return stub().getReportFilePath(accountLoginDTO);
    }

    public String getStatementPDF(AccountLoginDTO accountLoginDTO,
                                  String accountVPNType) throws EtccException, EtccSecurityException {
        return stub().getStatementPDF(accountLoginDTO, accountVPNType);
    }

    public String getSummaryPDF(AccountLoginDTO accountLoginDTO,
                                String accountVPNType) throws EtccException, EtccSecurityException {
        return stub().getSummaryPDF(accountLoginDTO, accountVPNType);
    }


    public AccountStatementDTO getStatementHTML(AccountLoginDTO accountLoginDTO,
                                                String postDate,
                                                String acctVPNType) throws EtccException, EtccSecurityException {
        return stub().getStatementHTML(accountLoginDTO, postDate, acctVPNType);
    }
    public AccountYearlySummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO,
                                            String postDate) throws EtccException, EtccSecurityException {
        return stub().getSummaryHTML(accountLoginDTO, postDate);
    }

    public StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO, boolean isMonthly) throws EtccException, EtccSecurityException {
        return stub().getStatementDates(accountLoginDTO, isMonthly);
    }

    public SummaryAvailableDTO hasLastYearSummary(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException {
        return stub().hasLastYearSummary(accountLoginDTO);
    }

    public OlcAccountTagRecBean[] getAcccountTags(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException {
        return stub().getAcccountTags(accountLoginDTO);
    }
	//QC_11613 dateType field added
    public TransactionRecordsDTO getTransactionRecords(AccountLoginDTO accountLoginDTO,
                                                         Calendar startDate,
                                                         Calendar endDate,
                                                         String agencyId,
                                                         String tagId,
                                                         String transFilter,
                                                         String nickName,
                                                         String nickNameType,String dateType) throws EtccException, EtccSecurityException {
        return stub().getTransactionRecords(accountLoginDTO, startDate, endDate, agencyId, tagId, transFilter, nickName, nickNameType,dateType);
    }

    public AccountTransactionDTO viewTransactionMain(AccountLoginDTO accountLoginDTO,
                                                     Calendar startDate,
                                                     Calendar endDate,
                                                     String acctVPNType) throws EtccException, EtccSecurityException{
        return stub().viewTransactionMain(accountLoginDTO, startDate, endDate, acctVPNType);
    }

    public String getReportServletUrl(String reportName) throws EtccException{
        return stub().getReportServletUrl(reportName);
    }

    public String getReportURL(AccountLoginDTO loginDto, String format, String name, String args) throws EtccException, EtccSecurityException {
        return stub().getReportURL(loginDto, format, name, args);
    }

    public InvoiceDatesDTO getInvoiceDates(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException {
            return stub().getInvoiceDates(accountLogin);
    }

    public MonthlyInvoicesDTO getMonthlyInvoices(String statementMonth, AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException {
        return stub().getMonthlyInvoices(statementMonth, accountLogin);
    }

    public AccountReceiptsDTO getAccountReceipts(AccountLoginDTO login, Calendar start, Calendar end) throws EtccSecurityException, EtccException {
        return stub().getAccountReceipts(login, start, end);
    }

    public AccountReceiptDetailsDTO getAccountReceiptDetails(AccountLoginDTO login, long transactionID, String HASFlag) throws EtccSecurityException, EtccException {
        return stub().getAccountReceiptDetails(login, transactionID, HASFlag);
    }

    public AccountSummaryDTO getSummaryGraph(AccountLoginDTO login) throws EtccSecurityException, EtccException{
        return stub().getSummaryGraph(login);
    }

    private static final AccountHistoryInterface stub() {
    	return ServiceFactory.getImplementation(AccountHistoryInterface.class);
    }

	public AccountTransactionHistoryResponse getAccountTransactionHistory(Long acctId, String docType, String sessionId,
			String ipAddress, String userId, Date fromDate, Date toDate, String dateType, List<Integer> categoryTab,
			List<Integer> subCategoryTab, List<Integer> acctVehIds, List<Integer> acctTagIds, Integer pageSize,
			Integer pageNumber, Integer sortOrder, BigDecimal eventId, Integer paramId, Integer maxReturnRows)
			throws EtccException {
		return stub().getAccountTransactionHistory(acctId, docType, sessionId, ipAddress, userId, fromDate, toDate,
				dateType, categoryTab, subCategoryTab, acctVehIds, acctTagIds, pageSize, pageNumber, sortOrder, eventId,
				paramId, maxReturnRows);
	}

	
}
