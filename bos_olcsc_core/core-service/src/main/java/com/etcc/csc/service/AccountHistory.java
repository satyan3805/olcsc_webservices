package com.etcc.csc.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountHistoryDAO;
import com.etcc.csc.dao.generated.OlcAccountTagRecBean;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.AccountSummaryDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.InvoiceArchiveDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.dto.SummaryAvailableDTO;
import com.etcc.csc.dto.TransactionRecordsDTO;

public class AccountHistory implements AccountHistoryInterface {
	private Logger logger = Logger.getLogger(AccountHistory.class);

	public String getTransactions(AccountLoginDTO accountLoginDTO,
			Date startDate, Date endDate, String agencyId, String tagId,
			String transactionTypeCode, String reportFormat)
			throws EtccException {
		return "Account_Info.pdf";
	}

	public String getStatement(AccountLoginDTO accountLoginDTO, Date postDate,
			String reportFormat) throws EtccException {
		return "http://www.etcc.com/news/ETC_Brochure.pdf";
	}

	public String getSummary(AccountLoginDTO accountLoginDTO)
			throws EtccException {
		return "http://www.etcc.com/news/ETC_Brochure.pdf";
	}

	public String getReportFilePath(AccountLoginDTO accountLoginDTO)
			throws EtccReportException {

		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountHistoryDAO accountHistoryDAO = daoFactory.getAccountHistoryDAO();

		return accountHistoryDAO.getReportFilePath(accountLoginDTO);
	}

	public AccountStatementDTO getStatementHTML(
			AccountLoginDTO accountLoginDTO, String postDate, String acctVPNType)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.getStatementHTML(accountLoginDTO,
					postDate, acctVPNType);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getStatementHTML() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("EtccException in getStatementHTML() " + ee, ee);
			throw ee;
		}

	}

	public StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.getStatementDates(accountLoginDTO);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getStatementDates() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("EtccException in getStatementDates() " + ee, ee);
			throw ee;
		}
	}

	public SummaryAvailableDTO hasLastYearSummary(
			AccountLoginDTO accountLoginDTO) throws EtccException,
			EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.hasLastYearSummary(accountLoginDTO);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in hasLastYearSummary() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("EtccException in hasLastYearSummary() " + ee, ee);
			throw ee;
		}

	}

	public OlcAccountTagRecBean[] getAcccountTags(
			AccountLoginDTO accountLoginDTO) throws EtccException,
			EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.getAcccountTags(accountLoginDTO);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getAcccountTags() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("EtccException in getAcccountTags() " + ee, ee);
			throw ee;
		}
	}

	public TransactionRecordsDTO getTransactionRecords(
			AccountLoginDTO accountLoginDTO, Calendar startDate,
			Calendar endDate, String agencyId, String tagId, String licPlate,
			String licState, String transTypeId, String acctVPNType)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.getTransactionRecords(accountLoginDTO,
					startDate, endDate, agencyId, tagId, licPlate, licState,
					transTypeId, acctVPNType);
		} catch (EtccSecurityException ese) {
			logger.error(
					"Security exception in getTransactionRecords() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("EtccException in getTransactionRecords() " + ee, ee);
			throw ee;
		}
	}

	public AccountSummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO,
			String postDate) throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.getSummaryHTML(accountLoginDTO, postDate);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getSummaryHTML() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("EtccException in getSummaryHTML() " + ee, ee);
			throw ee;
		}
	}

	public String getStatementPDF(AccountLoginDTO accountLoginDTO,
			String accountVPNType, String lang) throws EtccException,
			EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.getStatementPDF(accountLoginDTO,
					accountVPNType, lang);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getStatementPDF() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("EtccException in getStatementPDF() " + ee, ee);
			throw ee;
		}
	}

	public String getSummaryPDF(AccountLoginDTO accountLoginDTO,
			String accountVPNType, String lang) throws EtccException,
			EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.getSummaryPDF(accountLoginDTO,
					accountVPNType, lang);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getSummaryPDF() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("EtccException in getSummaryPDF() " + ee, ee);
			throw ee;
		}
	}

	public AccountTransactionDTO viewTransactionMain(
			AccountLoginDTO accountLoginDTO, Calendar startDate,
			Calendar endDate, String acctVPNType) throws EtccException,
			EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();
			return accountHistoryDAO.viewTransactionMain(accountLoginDTO,
					startDate, endDate, acctVPNType);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in viewTransactionMain() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("EtccException in viewTransactionMain() " + ee, ee);
			throw ee;
		}
	}

	public String getReportServletUrl(String reportName, String sessionId)
			throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.getReportServletUrl(reportName, sessionId);
		} catch (EtccException ee) {
			logger.error("EtccException in getReportServletUrl " + ee, ee);
			throw ee;
		}
	}

	public List<InvoiceArchiveDTO> getArchiveInvoices(
			AccountLoginDTO accountLoginDTO) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AccountHistoryDAO accountHistoryDAO = daoFactory
					.getAccountHistoryDAO();

			return accountHistoryDAO.getArchiveInvoices(accountLoginDTO);
		} catch (EtccException ee) {
			logger.error("EtccException in getArchiveInvoices " + ee, ee);
			throw ee;
		}
	}
}
