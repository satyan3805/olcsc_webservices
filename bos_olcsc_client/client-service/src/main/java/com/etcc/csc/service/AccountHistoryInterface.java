/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

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

/**
 * API for the Account History implementations.  From the original OLSCSService project.
 */
@Local
public interface AccountHistoryInterface extends ServiceInterface {

//    public String getTransactions(AccountLoginDTO accountLoginDTO,
//                                  Date startDate, Date endDate,
//                                  String agencyId, String tagId,
//                                  String transactionTypeCode,
//                                  String reportFormat) throws EtccException, EtccSecurityException;
//
//
//    public String getStatement(AccountLoginDTO accountLoginDTO, Date postDate,
//                               String reportFormat) throws EtccException, EtccSecurityException;
//
//
//    public String getSummary(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException;

    public String getReportFilePath(AccountLoginDTO accountLoginDTO) throws EtccException, EtccReportException;

    public AccountStatementDTO getStatementHTML(AccountLoginDTO accountLoginDTO,
                                                String postDate,
                                                String acctVPNType) throws EtccException, EtccSecurityException;

    public StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO, boolean isMonthly) throws EtccException, EtccSecurityException;

    public SummaryAvailableDTO hasLastYearSummary(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException;

    public OlcAccountTagRecBean[] getAcccountTags(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException;
	//QC_11613 dateType field added
    public TransactionRecordsDTO getTransactionRecords(AccountLoginDTO accountLoginDTO,
                                                         Calendar startDate,
                                                         Calendar endDate,
                                                         String agencyId,
                                                         String tagId,
                                                         String transFilter,
                                                         String nickName,
                                                         String nickNameType,String dateType) throws EtccException, EtccSecurityException;

    public AccountYearlySummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO,
                                            String postDate) throws EtccException, EtccSecurityException;

    public String getStatementPDF(AccountLoginDTO accountLoginDTO,
                                  String accountVPNType) throws EtccException, EtccSecurityException;

    public String getSummaryPDF(AccountLoginDTO accountLoginDTO,
                                String accountVPNType) throws EtccException, EtccSecurityException;

    public AccountTransactionDTO viewTransactionMain(AccountLoginDTO accountLoginDTO,
                                                     Calendar startDate,
                                                     Calendar endDate,
                                                     String acctVPNType) throws EtccException, EtccSecurityException;

    public String getReportServletUrl (String reportName) throws EtccException;

    public InvoiceDatesDTO getInvoiceDates(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException;

    public MonthlyInvoicesDTO getMonthlyInvoices(String statementMonth, AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException;

    public AccountReceiptsDTO getAccountReceipts(AccountLoginDTO login, Calendar start, Calendar end) throws EtccSecurityException, EtccException;

    public AccountReceiptDetailsDTO getAccountReceiptDetails(AccountLoginDTO login, long transactionID, String HASFlag) throws EtccSecurityException, EtccException;
    public AccountSummaryDTO getSummaryGraph(AccountLoginDTO login) throws EtccSecurityException, EtccException;
    public String getReportURL(AccountLoginDTO loginDto, String format, String name, String args) throws EtccException, EtccSecurityException;
    
    public AccountTransactionHistoryResponse getAccountTransactionHistory(Long acctId, String docType, String sessionId,
			String ipAddress, String userId, Date fromDate, Date toDate, String dateType, List<Integer> categoryTab,
			List<Integer> subCategoryTab, List<Integer> acctVehIds, List<Integer> acctTagIds, Integer pageSize, Integer pageNumber,
			Integer sortOrder, BigDecimal eventId, Integer paramId, Integer maxReturnRows) throws EtccException ;

}
