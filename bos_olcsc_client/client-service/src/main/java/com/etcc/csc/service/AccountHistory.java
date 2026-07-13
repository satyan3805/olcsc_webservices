/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import oracle.j2ee.ejb.StatelessDeployment;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountHistoryDAO;
import com.etcc.csc.dao.DAOFactory;
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
 * Webservice Interface Implementation for AccountHistory. Copied from OLCSCService, the updated for task 488.
 * com.etcc.csc.ws.accounthist.AccountHistory
 *
 * @author Stephen Davidson
 */
@Stateless(name = "com/etcc/AccountHistory")
//Get TransactionRecords can take a while to load if active account.
@StatelessDeployment(transactionTimeout=120)
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "AccountHistory", targetNamespace = "http://ws.csc.etcc.com/AccountHistory")
public class AccountHistory implements AccountHistoryInterface {

    /*public String getTransactions(AccountLoginDTO accountLoginDTO,
                                  Date startDate, Date endDate,
                                  String agencyId, String tagId,
                                  String transactionTypeCode,
                                  String reportFormat) throws EtccException {
        String obj = accountLoginDTO ==null?"":	accountLoginDTO.toString();
        ActivityLogger.logWSCall(wsName, "getTransactions",
                                                 obj + "-" +
                                                 startDate + "-" +
                                                 endDate + "-" +
                                                 agencyId + "-" +
                                                 tagId + "-" +
                                                 transactionTypeCode + "-" +
                                                 reportFormat );
        return "Account_Info.pdf";
    }*/

    /*public String getStatement(AccountLoginDTO accountLoginDTO, Date postDate,
                               String reportFormat) throws EtccException {
        String obj = accountLoginDTO ==null?"":	accountLoginDTO.toString();
        ActivityLogger.logWSCall(wsName, "getStatement", obj + "-" + reportFormat );
        return "http://www.etcc.com/news/ETC_Brochure.pdf";
    }*/

    /*public String getSummary(AccountLoginDTO accountLoginDTO) throws EtccException {
        String obj = accountLoginDTO ==null?"":	accountLoginDTO.toString();
        ActivityLogger.logWSCall(wsName, "getSummary", obj);
        return "http://www.etcc.com/news/ETC_Brochure.pdf";
    }*/

    //@WebMethod(operationName = "getReportFilePath", action = "urn:getReportFilePath")
    //@WebResult(name = "reportUrl")
    public String getReportFilePath(AccountLoginDTO accountLoginDTO) throws EtccException, EtccReportException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getReportFilePath(accountLoginDTO);
    }

    //@WebMethod(operationName = "getStatementHTML", action = "urn:getStatementHTML")
    //@WebResult(name = "statement")
    public AccountStatementDTO getStatementHTML(AccountLoginDTO accountLoginDTO, String postDate, String acctVPNType)
            throws EtccException, EtccSecurityException {


        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getStatementHTML(accountLoginDTO, postDate, acctVPNType);
    }

    //@WebMethod(operationName = "getStatementDates", action = "urn:getStatementDates")
    //@WebResult(name = "statementDates")
    public StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO, boolean isMonthly)
            throws EtccException, EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getStatementDates(accountLoginDTO, isMonthly);
    }

    //@WebMethod(operationName = "hasLastYearSummary", action = "urn:hasLastYearSummary")
    //@WebResult(name = "summaryAvailable")
    public SummaryAvailableDTO hasLastYearSummary(AccountLoginDTO accountLoginDTO) throws EtccException,
            EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.hasLastYearSummary(accountLoginDTO);
    }

    //@WebMethod(operationName = "getAcccountTags", action = "urn:getAcccountTags")
    //@WebResult(name = "tags")
    public OlcAccountTagRecBean[] getAcccountTags(AccountLoginDTO accountLoginDTO) throws EtccException,
            EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getAcccountTags(accountLoginDTO);
    }

    //@WebMethod(operationName = "getTransactionRecords", action = "urn:getTransactionRecords")
    //@WebResult(name = "transactionRecords")
	//QC_11613 dateType field added
    public TransactionRecordsDTO getTransactionRecords(AccountLoginDTO accountLoginDTO, Calendar startDate,
            Calendar endDate, String agencyId, String tagId, String transFilter, String nickName, String nickNameType,String dateType)
            throws EtccException, EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getTransactionRecords(accountLoginDTO, startDate, endDate, agencyId, tagId,
                transFilter, nickName, nickNameType,dateType);
    }

    //@WebMethod(operationName = "getSummaryHTML", action = "urn:getSummaryHTML")
    //@WebResult(name = "yearlySummary")
    public AccountYearlySummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO, String postDate)
            throws EtccException, EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getSummaryHTML(accountLoginDTO, postDate);
    }

    //@WebMethod(operationName = "getStatementPDF", action = "urn:getStatementPDF")
    //@WebResult(name = "pdf")
    public String getStatementPDF(AccountLoginDTO accountLoginDTO, String accountVPNType) throws EtccException,
            EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getStatementPDF(accountLoginDTO, accountVPNType);
    }

    //@WebMethod(operationName = "getSummaryPDF", action = "urn:getSummaryPDF")
    //@WebResult(name = "summaryPdf")
    public String getSummaryPDF(AccountLoginDTO accountLoginDTO, String accountVPNType) throws EtccException,
            EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getSummaryPDF(accountLoginDTO, accountVPNType);
    }

    //@WebMethod(operationName = "viewTransactionMain", action = "urn:viewTransactionMain")
    //@WebResult(name = "transaction")
    public AccountTransactionDTO viewTransactionMain(AccountLoginDTO accountLoginDTO, Calendar startDate,
            Calendar endDate, String acctVPNType) throws EtccException, EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.viewTransactionMain(accountLoginDTO, startDate, endDate, acctVPNType);
    }

    //@WebMethod(operationName = "getReportServletUrl", action = "urn:getReportServletUrl")
    //@WebResult(name = "reportServletUrl")
    public String getReportServletUrl(String reportName) throws EtccException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getReportServletUrl(reportName);
    }

    //@WebMethod(operationName = "getInvoiceDates", action = "urn:getInvoiceDates")
    //@WebResult(name = "invoiceDates")
    public InvoiceDatesDTO getInvoiceDates(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getInvoiceDates(accountLogin);
    }

    //@WebMethod(operationName = "getMonthlyInvoices", action = "urn:getMonthlyInvoices")
    //@WebResult(name = "monthlyInvoices")
    public MonthlyInvoicesDTO getMonthlyInvoices(String statementMonth, AccountLoginDTO accountLogin)
            throws EtccException, EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getMonthlyInvoices(statementMonth, accountLogin);
    }

    //@WebMethod(operationName = "getAccountReceipts", action = "urn:getAccountReceipts")
    //@WebResult(name = "receipts")
    public AccountReceiptsDTO getAccountReceipts(AccountLoginDTO login, Calendar start, Calendar end)
            throws EtccSecurityException, EtccException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getAccountReceipts(login, start, end);
    }

    //@WebMethod(operationName = "getAccountReceiptDetails", action = "urn:getAccountReceiptDetails")
    //@WebResult(name = "receiptDetails")
    public AccountReceiptDetailsDTO getAccountReceiptDetails(AccountLoginDTO login, long transactionID, String HASFlag)
            throws EtccSecurityException, EtccException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getAccountReceiptDetails(login, transactionID, HASFlag);
    }

    //@WebMethod(operationName = "getSummaryGraph", action = "urn:getSummaryGraph")
    //@WebResult(name = "summary")
    public AccountSummaryDTO getSummaryGraph(AccountLoginDTO login) throws EtccSecurityException, EtccException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getSummaryGraph(login);
    }

    //@WebMethod(operationName = "getReportURL", action = "urn:getReportURL")
    //@WebResult(name = "reportUrl")
    public String getReportURL(AccountLoginDTO loginDto, String format, String name, String args) throws EtccException,
            EtccSecurityException {

        AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        return accountHistoryDAO.getReportURL(loginDto, format, name, args);
    }

	public AccountTransactionHistoryResponse getAccountTransactionHistory(Long acctId, String docType, String sessionId,
			String ipAddress, String userId, Date fromDate, Date toDate, String dateType, List<Integer> categoryTab,
			List<Integer> subCategoryTab, List<Integer> acctVehIds, List<Integer> acctTagIds, Integer pageSize, Integer pageNumber,
			Integer sortOrder, BigDecimal eventId, Integer paramId, Integer maxReturnRows) throws EtccException {

		final AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
		
		final AccountTransactionHistoryResponse accountTransactionHistory = accountHistoryDAO.getAccountTransactionHistory(acctId, docType, sessionId, ipAddress, userId, fromDate, toDate,
				dateType, categoryTab, subCategoryTab, acctVehIds, acctTagIds, pageSize, pageNumber, sortOrder, eventId,
				paramId, maxReturnRows);
		return accountTransactionHistory;
	}
    
    

}
