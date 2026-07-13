package com.etcc.csc.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.generated.OlcAccountTagRecBean;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.AccountSummaryDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.InvoiceArchiveDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.dto.SummaryAvailableDTO;
import com.etcc.csc.dto.TransactionRecordsDTO;

public interface AccountHistoryInterface extends BusinessObjectInterface {

	public String getTransactions(AccountLoginDTO accountLoginDTO,
			Date startDate, Date endDate, String agencyId, String tagId,
			String transactionTypeCode, String reportFormat)
			throws EtccException, EtccSecurityException;

	public String getStatement(AccountLoginDTO accountLoginDTO, Date postDate,
			String reportFormat) throws EtccException, EtccSecurityException;

	public String getSummary(AccountLoginDTO accountLoginDTO)
			throws EtccException, EtccSecurityException;

	public String getReportFilePath(AccountLoginDTO accountLoginDTO)
			throws EtccReportException;

	public AccountStatementDTO getStatementHTML(
			AccountLoginDTO accountLoginDTO, String postDate, String acctVPNType)
			throws EtccException, EtccSecurityException;

	public StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO)
			throws EtccException, EtccSecurityException;

	public SummaryAvailableDTO hasLastYearSummary(
			AccountLoginDTO accountLoginDTO) throws EtccException,
			EtccSecurityException;

	public OlcAccountTagRecBean[] getAcccountTags(
			AccountLoginDTO accountLoginDTO) throws EtccException,
			EtccSecurityException;

	public TransactionRecordsDTO getTransactionRecords(
			AccountLoginDTO accountLoginDTO, Calendar startDate,
			Calendar endDate, String agencyId, String tagId, String licPlate,
			String licState, String transTypeId, String acctVPNType)
			throws EtccException, EtccSecurityException;

	public AccountSummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO,
			String postDate) throws EtccException, EtccSecurityException;

	public String getStatementPDF(AccountLoginDTO accountLoginDTO,
			String accountVPNType, String lang) throws EtccException,
			EtccSecurityException;

	public String getSummaryPDF(AccountLoginDTO accountLoginDTO,
			String accountVPNType, String lang) throws EtccException,
			EtccSecurityException;

	public AccountTransactionDTO viewTransactionMain(
			AccountLoginDTO accountLoginDTO, Calendar startDate,
			Calendar endDate, String acctVPNType) throws EtccException,
			EtccSecurityException;

	public String getReportServletUrl(String reportName, String sessionId)
			throws EtccException;

	public List<InvoiceArchiveDTO> getArchiveInvoices(
			AccountLoginDTO accountLoginDTO) throws EtccException;
}
