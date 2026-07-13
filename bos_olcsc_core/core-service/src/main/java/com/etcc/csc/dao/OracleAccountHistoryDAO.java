package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dao.generated.OlcAccountHistoryRecBean;
import com.etcc.csc.dao.generated.OlcAccountTagRecBean;
import com.etcc.csc.dao.generated.OlcViewMonthlyRecBean;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.AccountSummaryDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.GenericNameValueDTO;
import com.etcc.csc.dto.InvoiceArchiveDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.dto.SummaryAvailableDTO;
import com.etcc.csc.dto.TransactionRecordsDTO;
import com.etcc.csc.enums.EventEnum;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcAccountHistoryRec;
import com.etcc.csc.plsql.OlcAccountTagRec;
import com.etcc.csc.plsql.OlcAcctSummaryRec;
import com.etcc.csc.plsql.OlcSttmtDateRec;
import com.etcc.csc.plsql.OlcTagSummaryRec;
import com.etcc.csc.plsql.OlcViewMonthlyRec;
import com.etcc.csc.plsql.OlcViewtxnTxntypeRec;

public class OracleAccountHistoryDAO extends AccountHistoryDAO {
	private Logger logger = Logger.getLogger(OracleAccountHistoryDAO.class);

	public OracleAccountHistoryDAO() {
	}

	public String getReportFilePath(AccountLoginDTO accountLoginDTO)
			throws EtccReportException {
		logger.info("Entering getReportFilePath() @ OracleAccountHistoryDAO");
		String filePath = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getReportFilePath() :: Calling olcsc_param.get_report_url");
			cstmt = conn
					.prepareCall("{call olcsc_param.get_report_url(p_report_name=>?,o_report_url=>?,O_ERROR_MSG_ARR=>?)}");

			setErrorTypeMap();

			// cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setString("p_report_name", "TEST-REPORT_NAME");

			cstmt.registerOutParameter("o_report_url", Types.VARCHAR);
			cstmt.registerOutParameter("O_ERROR_MSG_ARR", Types.ARRAY,
					"OLC_ERROR_MSG_ARR");

			cstmt.execute();
			int result = cstmt.getInt(1);

			if (result == 1) {
				logger.debug("Inside if (result == 1) @ getReportFilePath()");
				filePath = cstmt.getString(4);
			} else {
				logger.debug("Inside else of if (result == 1) @ getReportFilePath()");
				logger.error("Error in getReportFilePath() @ OracleAccountHistoryDAO :: Query result = "
						+ result);
				throw new Exception(
						"Error in getReportFilePath() @ OracleAccountHistoryDAO :: Query result = "
								+ result);
			}

		} catch (Throwable t) {
			logger.error(
					"Error in getReportFilePath() @ OracleAccountHistoryDAO "
							+ t, t);
			throw new EtccReportException("Error in getting Report File Path "
					+ t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getReportFilePath() @ OracleAccountHistoryDAO");
		return filePath;
	}

	public AccountStatementDTO getStatementHTML(
			AccountLoginDTO accountLoginDTO, String postDate, String acctVPNType)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getStatementHTML() @ OracleAccountHistoryDAO");
		AccountStatementDTO statementDTO = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getStatementHTML() :: Calling olcsc_rep.view_statements_HTML");
			cstmt = conn
					.prepareCall("{? = call olcsc_rep.view_statements_HTML(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_VIEW_MONTHLY_REC",
					OlcViewMonthlyRec.class);
			typeMap.put("OL_OWNER.OLC_TAG_SUMMARY_REC", OlcTagSummaryRec.class);
			typeMap.put("OL_OWNER.OLC_ACCT_SUMMARY_REC",
					OlcAcctSummaryRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setString(2, Long.toString(accountLoginDTO.getAcctId()));
			cstmt.setString(3, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(4, accountLoginDTO.getLoginId());
			cstmt.setString(5, accountLoginDTO.getDbSessionId());
			cstmt.setString(6, accountLoginDTO.getLastLoginIp());
			cstmt.setString(7, postDate);
			cstmt.setString(8, "HTML");
			cstmt.setString(9, acctVPNType);

			cstmt.registerOutParameter(10, Types.VARCHAR);
			cstmt.registerOutParameter(11, Types.VARCHAR);
			cstmt.registerOutParameter(12, Types.ARRAY,
					"OL_OWNER.OLC_VIEW_MONTHLY_ARR");
			cstmt.registerOutParameter(13, Types.ARRAY,
					"OL_OWNER.OLC_TAG_SUMMARY_ARR");
			cstmt.registerOutParameter(14, Types.ARRAY,
					"OL_OWNER.OLC_ACCT_SUMMARY_ARR");
			cstmt.registerOutParameter(15, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			logger.debug("AcctId:" + accountLoginDTO.getAcctId() + " PostDate:"
					+ postDate + "****" + "Start calling statement HTML api");
			cstmt.executeUpdate();
			logger.debug("AcctId:" + accountLoginDTO.getAcctId() + " PostDate:"
					+ postDate + "****" + "End calling statement HTML api");

			statementDTO = new AccountStatementDTO();

			int result = cstmt.getInt(1);
			if (result == -1) {
				logger.debug("Inside if (result == -1) @ getStatementHTML()");
				logger.error("Error in getStatementHTML() @ OracleAccountHistoryDAO :: AcctId:"
						+ accountLoginDTO.getAcctId()
						+ " PostDate:"
						+ postDate
						+ "*****getStatementHTML result is -1");
				throw new EtccSecurityException(
						"Security exception in getStatementHTML()");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ getStatementHTML()");
				logger.error("Error in getStatementHTML() @ OracleAccountHistoryDAO :: AcctId:"
						+ accountLoginDTO.getAcctId()
						+ " PostDate:"
						+ postDate
						+ "****" + "Error getting html statements");
				Array errors = (Array) cstmt.getObject(15);
				statementDTO.setErrors(convertErrorMsgs(errors));
				// return statementDTO;
			}

			String endingBalance = cstmt.getString(10);
			String lastDayOfMonth = cstmt.getString(11);

			statementDTO.setEndingBalance(endingBalance);
			statementDTO.setLastDayOfMonth(lastDayOfMonth);

			Object[] details = (Object[]) cstmt.getArray(12).getArray(
					conn.getTypeMap());

			if (details != null && details.length > 0) {
				logger.debug("Inside if (details != null && details.length > 0) @ getStatementHTML()");
				OlcViewMonthlyRecBean[] olcViewMonthlyRecBeans = new OlcViewMonthlyRecBean[details.length];
				for (int i = 0; i < details.length; i++) {
					olcViewMonthlyRecBeans[i] = new OlcViewMonthlyRecBean(
							(OlcViewMonthlyRec) details[i]);
				}
				statementDTO.setRecords(olcViewMonthlyRecBeans);
			}

			Object[] tagSummary = (Object[]) cstmt.getArray(13).getArray(
					conn.getTypeMap());

			if (tagSummary != null && tagSummary.length > 0) {
				logger.debug("Inside if (tagSummary != null && tagSummary.length > 0) @ getStatementHTML()");
				OlcTagSummaryRec[] olcTagSummaryRecs = new OlcTagSummaryRec[tagSummary.length];
				for (int i = 0; i < tagSummary.length; i++) {
					olcTagSummaryRecs[i] = (OlcTagSummaryRec) tagSummary[i];
				}

				statementDTO.setTagSummary(olcTagSummaryRecs);
			}

			Object[] acctSummary = (Object[]) cstmt.getArray(14).getArray(
					conn.getTypeMap());

			if (acctSummary != null && acctSummary.length > 0) {
				logger.debug("Inside if (acctSummary != null && acctSummary.length > 0) @ getStatementHTML()");
				OlcAcctSummaryRec[] olcAcctSummaryRecs = new OlcAcctSummaryRec[acctSummary.length];
				for (int i = 0; i < acctSummary.length; i++) {
					olcAcctSummaryRecs[i] = (OlcAcctSummaryRec) acctSummary[i];
				}
				statementDTO.setAcctSummary(olcAcctSummaryRecs);
			}
			
			//set value for total tag Summary
			OlcTagSummaryRec tagSummaryTotal = new OlcTagSummaryRec();
			int qty = 0;
			double amount = 0.0;
			OlcTagSummaryRec[] tagSummaryArr = statementDTO.getTagSummary();
			if (tagSummaryArr != null && tagSummaryArr.length > 0) {
				for (OlcTagSummaryRec olcTagSummaryRec : tagSummaryArr) {
					qty += olcTagSummaryRec.getQuantity().intValue();
					amount += olcTagSummaryRec.getAmount().doubleValue();
				}
			}
			tagSummaryTotal.setQuantity(new BigDecimal(qty));
			tagSummaryTotal.setAmount(new BigDecimal(amount));
			statementDTO.setTagSummaryTotal(tagSummaryTotal);
			
		} catch (SQLException sqle) {
			logger.error(
					"Error in getStatementHTML() @ OracleAccountHistoryDAO :: AcctId:"
							+ accountLoginDTO.getAcctId() + " PostDate:"
							+ postDate + "*****"
							+ "SQLException occured in getStatementHTML() "
							+ sqle, sqle);
			throw new EtccException("Errror retrieveing statement " + sqle,
					sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getStatementHTML() @ OracleAccountHistoryDAO");
		return statementDTO;
	}

	/**
	 * Gets the list of valid statement months
	 * 
	 * @param accountLoginDTO
	 * @return
	 * @throws EtccException
	 */
	public StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getStatementDates() @ OracleAccountHistoryDAO");
		StatementDatesDTO statementDatesDTO;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getStatementDates() :: Calling OLCSC_ACCT_MGMT.Get_Statement_Dates");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Statement_Dates(?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_STTMT_DATE_REC", OlcSttmtDateRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, accountLoginDTO.getDbSessionId());
			cstmt.setLong(3, accountLoginDTO.getAcctId());
			cstmt.setString(4, accountLoginDTO.getLoginId());
			cstmt.setString(5, accountLoginDTO.getLastLoginIp());
			cstmt.registerOutParameter(6, Types.ARRAY,
					"OL_OWNER.OLC_STTMT_DATES_ARR");
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			logger.debug("AcctId:" + accountLoginDTO.getAcctId() + "****"
					+ "start retrieving statement dates...");
			cstmt.executeUpdate();
			logger.debug("AcctId:" + accountLoginDTO.getAcctId() + "****"
					+ "statement dates retrieved...");

			statementDatesDTO = new StatementDatesDTO();
			int result = cstmt.getInt(1);
			if (result == -1) {
				logger.debug("Inside if (result == -1) @ getStatementDates()");
				logger.error("Error in getStatementDates() @ OracleAccountHistoryDAO :: AcctId:"
						+ accountLoginDTO.getAcctId()
						+ "****"
						+ "getStatementDates:Security Exception");
				throw new EtccSecurityException("security exception");
			} else if (result == 0) {
				logger.debug("Inside if (result == 0) @ getStatementDates()");
				logger.debug("Error in getStatementDates() @ OracleAccountHistoryDAO :: AcctId:"
						+ accountLoginDTO.getAcctId()
						+ "****"
						+ "error retrieveing statement dates.");
				return null;
			}

			Array dates = (Array) cstmt.getObject(6);
			statementDatesDTO.setDates(getDateList(dates,
					accountLoginDTO.getAcctId()));
			logger.debug("AcctId:" + accountLoginDTO.getAcctId() + "****"
					+ "getStatementDates: Success");
		} catch (SQLException sqle) {
			logger.error(
					"Error in getStatementDates() @ OracleAccountHistoryDAO :: AcctId:"
							+ accountLoginDTO.getAcctId() + "****"
							+ "SQLException Occurred in getStatementDates() "
							+ sqle, sqle);
			throw new EtccException("errror retrieveing statement dates "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getStatementDates() @ OracleAccountHistoryDAO");
		return statementDatesDTO;
	}

	public SummaryAvailableDTO hasLastYearSummary(
			AccountLoginDTO accountLoginDTO) throws EtccException,
			EtccSecurityException {
		logger.info("Entering hasLastYearSummary() @ OracleAccountHistoryDAO");
		SummaryAvailableDTO summaryAvailDTO;

		try {
			setConnection(Util.getDbConnection());
			logger.info("hasLastYearSummary() :: Calling OLCSC_ACCT_MGMT.Has_Last_Year_Stmt");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Has_Last_Year_Stmt(?,?,?,?,?,?)}");
			setErrorTypeMap();
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, accountLoginDTO.getDbSessionId());
			cstmt.setLong(3, accountLoginDTO.getAcctId());
			cstmt.setString(4, accountLoginDTO.getLoginId());
			cstmt.setString(5, accountLoginDTO.getLastLoginIp());
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();

			summaryAvailDTO = new SummaryAvailableDTO();
			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ hasLastYearSummary()");
				logger.error("Error in hasLastYearSummary() @ OracleAccountHistoryDAO :: AcctId:"
						+ accountLoginDTO.getAcctId()
						+ "****"
						+ "hasLastYearSummary:Security Exception");
				throw new EtccSecurityException(
						"Security exception in hasLastYearSummary()");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ hasLastYearSummary()");
				summaryAvailDTO.setSummaryAvail(false);
			}

			String available = cstmt.getString(6);
			logger.debug("AcctId:" + accountLoginDTO.getAcctId() + "****"
					+ "Last year summary available:" + available);
			summaryAvailDTO.setSummaryAvail(available.equals("1"));
		} catch (SQLException sqle) {
			logger.error(
					"Error in hasLastYearSummary() @ OracleAccountHistoryDAO :: AcctId:"
							+ accountLoginDTO.getAcctId() + "****"
							+ "SQLException Occurred in hasLastYearSummary() "
							+ sqle, sqle);
			throw new EtccException("Errror retrieveing \"hasLastYearSummary\""
					+ sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving hasLastYearSummary() @ OracleAccountHistoryDAO");
		return summaryAvailDTO;
	}

	private Collection getDateList(Array dates, long acctId)
			throws SQLException {
		logger.info("Entering getDateList() @ OracleAccountHistoryDAO");
		Collection result = null;
		Object array[] = (Object[]) dates.getArray();

		if (array != null && array.length >= 0) {
			logger.debug("Inside if (array != null && array.length >= 0) @ getDateList()");
			result = new ArrayList();
			for (int i = 0; i < array.length; i++) {
				OlcSttmtDateRec dateRec = (OlcSttmtDateRec) array[i];
				logger.debug("AcctId:" + acctId + "****" + "statement Months ["
						+ i + "]: " + dateRec.getMonthDesc() + "|"
						+ dateRec.getTransMonthDesc());
				GenericNameValueDTO dateEntry = new GenericNameValueDTO(
						dateRec.getMonthDesc(), dateRec.getTransMonthDesc());
				result.add(dateEntry);
			}
		} else {
			logger.debug("Inside else of if (array != null && array.length >= 0) @ getDateList()");
			logger.debug("AcctId:" + acctId + "****"
					+ "no monthly statement available");
		}

		logger.info("Leaving getDateList() @ OracleAccountHistoryDAO");
		return result;
	}

	private void setErrorTypeMap() throws SQLException {
		logger.info("Entering setErrorTypeMap() @ OracleAccountHistoryDAO");
		Map typeMap = new HashMap();
		typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
		conn.setTypeMap(typeMap);
		logger.info("Leaving setErrorTypeMap() @ OracleAccountHistoryDAO");
	}

	public OlcAccountTagRecBean[] getAcccountTags(
			AccountLoginDTO accountLoginDTO) throws EtccException,
			EtccSecurityException {
		logger.info("Entering getAcccountTags() @ OracleAccountHistoryDAO");
		OlcAccountTagRecBean[] olcAccountTagRecs = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getAcccountTags() :: Calling OLCSC_ACCT_MGMT.Get_Tag_Info");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Tag_Info(?,?,?,?,?,?)}");

			setErrorTypeMap();
			conn.getTypeMap().put("OL_OWNER.OLC_ACCOUNT_TAG_REC",
					OlcAccountTagRec.class);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, accountLoginDTO.getDbSessionId());
			cstmt.setLong(3, accountLoginDTO.getAcctId());
			cstmt.setString(4, accountLoginDTO.getLoginId());
			cstmt.setString(5, accountLoginDTO.getLastLoginIp());
			cstmt.registerOutParameter(6, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			if (cstmt.getInt(1) == 1) {
				logger.debug("Inside if (cstmt.getInt(1) == 1) @ getAcccountTags()");
				Object[] objArray = (Object[]) cstmt.getArray(6).getArray(
						conn.getTypeMap());
				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0) @ getAcccountTags()");
					olcAccountTagRecs = new OlcAccountTagRecBean[objArray.length];

					for (int i = 0; i < objArray.length; i++) {
						olcAccountTagRecs[i] = new OlcAccountTagRecBean(
								(OlcAccountTagRec) objArray[i]);
					}
				}
			}
		} catch (Throwable t) {
			logger.error(
					"Error in getAcccountTags() @ OracleAccountHistoryDAO " + t,
					t);
			throw new EtccException(
					"Errror in account history DAO getAcccountTags" + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getAcccountTags() @ OracleAccountHistoryDAO");
		return olcAccountTagRecs;
	}

	/*
	 * (result) number p_doc_id accounts.acct_id%type, p_doc_type VARCHAR2
	 * default 'AC', p_user_id VARCHAR2, p_session_id VARCHAR2, p_ip_address
	 * VARCHAR2, p_begin_date DATE, p_end_date DATE, p_acct_VPN_type VARCHAR2,
	 * p_agency_id account_tags.agency_id%type, p_tag_id
	 * account_tags.tag_id%type, p_trans_type_id
	 * transaction_types.trans_type_id%type, p_called_from_ui boolean default
	 * true, o_olc_acc_history_arr OUT olc_account_history_arr, o_error_msg_arr
	 * OUT OLC_ERROR_MSG_ARR
	 */
	public TransactionRecordsDTO getTransactionRecords(
			AccountLoginDTO accountLoginDTO, Calendar startDate,
			Calendar endDate, String agencyId, String tagId, String licPlate,
			String licState, String transTypeId, String acctVPNType)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getTransactionRecords() @ OracleAccountHistoryDAO");
		OlcAccountHistoryRecBean[] olcAccountHistoryRecBeans = null;
		TransactionRecordsDTO recordsDTO = null;

		try {
			StringBuffer call = new StringBuffer();
			call.append("{? = call OLCSC_REP.view_txns(");
			call.append("p_doc_id=>?,");
			call.append("p_doc_type=>?,");
			call.append("p_user_id=>?,");
			call.append("p_session_id=>?,");
			call.append("p_ip_address=>?,");
			call.append("p_begin_date=>?,");
			call.append("p_end_date=>?,");
			call.append("p_acct_VPN_type=>?,");
			call.append("p_agency_id=>?,");
			call.append("p_tag_id=>?,");
			call.append("p_lic_plate_no=>?,");
			call.append("p_lic_plate_state=>?,");
			call.append("p_trans_type_id=>?,");
			call.append("o_olc_acc_history_arr=>?,");
			call.append("o_error_msg_arr=>?,");
			call.append("p_event_id=>?");
			call.append(")}");

			setConnection(Util.getDbConnection());
			logger.info("getTransactionRecords() :: Calling OLCSC_REP.view_txns");
			cstmt = conn.prepareCall(call.toString());
			setErrorTypeMap();
			conn.getTypeMap().put("OL_OWNER.OLC_ACCOUNT_HISTORY_REC",
					OlcAccountHistoryRec.class);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, accountLoginDTO.getAcctId());
			cstmt.setString(3, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(4, accountLoginDTO.getLoginId());
			cstmt.setString(5, accountLoginDTO.getDbSessionId());
			cstmt.setString(6, accountLoginDTO.getLastLoginIp());

			if (startDate != null) {
				logger.debug("Inside if (startDate != null) @ getTransactionRecords()");
				startDate.set(startDate.get(Calendar.YEAR),
						startDate.get(Calendar.MONTH),
						startDate.get(Calendar.DATE), 0, 0, 0);
				cstmt.setTimestamp(7, new java.sql.Timestamp(startDate
						.getTime().getTime()));
			} else {
				logger.debug("Inside else of if (startDate != null) @ getTransactionRecords()");
				cstmt.setTimestamp(7, null);
			}

			if (endDate != null) {
				logger.debug("Inside if (endDate != null) @ getTransactionRecords()");
				endDate.set(endDate.get(Calendar.YEAR),
						endDate.get(Calendar.MONTH),
						endDate.get(Calendar.DATE), 23, 59, 59);
				cstmt.setTimestamp(8, new java.sql.Timestamp(endDate.getTime()
						.getTime()));
			} else {
				logger.debug("Inside else of if (endDate != null) @ getTransactionRecords()");
				cstmt.setTimestamp(8, null);
			}

			cstmt.setString(9, acctVPNType);
			cstmt.setString(10, agencyId);
			cstmt.setString(11, tagId);
			cstmt.setString(12, licPlate);
			cstmt.setString(13, licState);

			if (transTypeId != null) {
				logger.debug("Inside if (transTypeId != null) @ getTransactionRecords()");
				cstmt.setInt(14, Integer.parseInt(transTypeId));
			} else {
				logger.debug("Inside else of if (transTypeId != null) @ getTransactionRecords()");
				cstmt.setInt(14, 0);
			}

			cstmt.registerOutParameter(15, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
			cstmt.registerOutParameter(16, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setLong(17, EventEnum.VIEWTRANSACTIONS.getId());
			logger.debug("acctId:" + accountLoginDTO.getAcctId() + "****"
					+ "start retrieving transactions...");
			cstmt.execute();
			logger.debug("acctId:" + accountLoginDTO.getAcctId() + "****"
					+ "transactions retrieved");

			recordsDTO = new TransactionRecordsDTO();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ getTransactionRecords()");
				logger.error("Error in getTransactionRecords() @ OracleAccountHistoryDAO :: acctId:"
						+ accountLoginDTO.getAcctId()
						+ "****"
						+ "getTransactionRecords result is -1");
				throw new EtccSecurityException(
						"Security exception in getTransactionRecords()");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ getTransactionRecords()");
				logger.debug("acctId:" + accountLoginDTO.getAcctId() + "****"
						+ "getTransactionRecords result is 0");
				Array errors = (Array) cstmt.getObject(16);
				recordsDTO.setErrors(convertErrorMsgs(errors));
				return recordsDTO;
			}

			if (cstmt.getArray(15) != null) {
				logger.debug("Inside if (cstmt.getArray(15) != null) @ getTransactionRecords()");
				Object[] objArray = (Object[]) cstmt.getArray(15).getArray(
						conn.getTypeMap());

				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0) @ getTransactionRecords()");
					olcAccountHistoryRecBeans = new OlcAccountHistoryRecBean[objArray.length];

					for (int i = 0; i < objArray.length; i++) {
						olcAccountHistoryRecBeans[i] = new OlcAccountHistoryRecBean(
								(OlcAccountHistoryRec) objArray[i]);
					}
				}
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in getTransactionRecords() @ OracleAccountHistoryDAO "
							+ sqle, sqle);
			throw new EtccException("acctId:" + accountLoginDTO.getAcctId()
					+ "****"
					+ "error in account history DAO getTransactionRecords"
					+ sqle, sqle);
		} finally {
			closeConnection();
		}

		recordsDTO.setRecords(olcAccountHistoryRecBeans);

		logger.info("Leaving getTransactionRecords() @ OracleAccountHistoryDAO");
		return recordsDTO;
	}

	private Collection convertErrorMsgs(Array errors) throws SQLException {
		logger.info("Entering convertErrorMsgs() @ OracleAccountHistoryDAO");
		Collection result = null;

		if (errors != null) {
			logger.debug("Inside if (errors != null) @ convertErrorMsgs()");
			Object array[] = (Object[]) errors.getArray();
			if (array != null && array.length >= 0) {
				logger.debug("Inside if (array != null && array.length >= 0) @ convertErrorMsgs()");
				result = new ArrayList();
				for (int i = 0; i < array.length; i++) {
					ErrorMsgRec msgRec = (ErrorMsgRec) array[i];
					logger.error("application error:" + msgRec.getErrMsg());
					result.add(msgRec.getErrMsg());
				}
			}
		} else {
			logger.debug("Inside else of if (errors != null) @ convertErrorMsgs()");
			logger.debug("error array is null");
		}

		logger.info("Leaving convertErrorMsgs() @ OracleAccountHistoryDAO");
		return result;
	}

	public AccountSummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO,
			String postDate) throws EtccException, EtccSecurityException {
		logger.info("Entering getSummaryHTML() @ OracleAccountHistoryDAO");
		AccountSummaryDTO summaryDTO = null;

		try {
			setConnection(Util.getDbConnection());
			String timeout = getConnectionTimeout();
			logger.info("getSummaryHTML() :: Calling olcsc_rep.View_Summary_HTML");
			cstmt = conn
					.prepareCall("{? = call olcsc_rep.View_Summary_HTML(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

			if (timeout != null) {
				logger.debug("Inside if (timeout != null) @ getSummaryHTML()");
				int iTimeout = Integer.parseInt(timeout);
				cstmt.setQueryTimeout(iTimeout);
			}

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_TAG_SUMMARY_REC", OlcTagSummaryRec.class);
			typeMap.put("OL_OWNER.OLC_ACCT_SUMMARY_REC",
					OlcAcctSummaryRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setString(2, Long.toString(accountLoginDTO.getAcctId()));
			cstmt.setString(3, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(4, accountLoginDTO.getLoginId());
			cstmt.setString(5, accountLoginDTO.getDbSessionId());
			cstmt.setString(6, accountLoginDTO.getLastLoginIp());
			cstmt.setString(7, postDate);
			cstmt.setString(8, "HTML");

			cstmt.registerOutParameter(9, Types.VARCHAR);
			cstmt.registerOutParameter(10, Types.VARCHAR);
			cstmt.registerOutParameter(11, Types.ARRAY,
					"OL_OWNER.OLC_TAG_SUMMARY_ARR");
			cstmt.registerOutParameter(12, Types.ARRAY,
					"OL_OWNER.OLC_ACCT_SUMMARY_ARR");
			cstmt.registerOutParameter(13, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			logger.debug("acctId:" + accountLoginDTO.getAcctId() + "****"
					+ "Start calling summary HTML api");
			cstmt.executeUpdate();
			logger.debug("acctId:" + accountLoginDTO.getAcctId() + "****"
					+ "End calling summary HTML api");

			summaryDTO = new AccountSummaryDTO();

			int result = cstmt.getInt(1);
			if (result == -1) {
				logger.debug("Inside if (result == -1) @ getSummaryHTML()");
				logger.error("Error in getSummaryHTML() @ OracleAccountHistoryDAO :: acctId:"
						+ accountLoginDTO.getAcctId()
						+ "****"
						+ "getSummaryHTML() result is -1");
				throw new EtccSecurityException(
						"Security exception in getSummaryHTML()");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ getSummaryHTML()");
				logger.debug("acctId:" + accountLoginDTO.getAcctId() + "****"
						+ "getSummaryHTML() result is 0");
				Array errors = (Array) cstmt.getObject(13);
				convertErrorMsgs(errors);
				return null;
			}

			String endingBalance = cstmt.getString(9);
			String lastDayOfYear = cstmt.getString(10);

			summaryDTO.setEndingBalance(endingBalance);
			summaryDTO.setLastDayOfYear(lastDayOfYear);

			Object[] tagSummary = (Object[]) cstmt.getArray(11).getArray(
					conn.getTypeMap());
			if (tagSummary != null && tagSummary.length > 0) {
				logger.debug("Inside if (tagSummary != null && tagSummary.length > 0) @ getSummaryHTML()");
				OlcTagSummaryRec[] olcTagSummaryRecs = new OlcTagSummaryRec[tagSummary.length];
				for (int i = 0; i < tagSummary.length; i++) {
					olcTagSummaryRecs[i] = (OlcTagSummaryRec) tagSummary[i];
				}

				summaryDTO.setTagSummary(olcTagSummaryRecs);
			}

			Object[] acctSummary = (Object[]) cstmt.getArray(12).getArray(
					conn.getTypeMap());
			if (acctSummary != null && acctSummary.length > 0) {
				logger.debug("Inside if (acctSummary != null && acctSummary.length > 0) @ getSummaryHTML()");
				OlcAcctSummaryRec[] olcAcctSummaryRecs = new OlcAcctSummaryRec[acctSummary.length];
				for (int i = 0; i < acctSummary.length; i++) {
					olcAcctSummaryRecs[i] = (OlcAcctSummaryRec) acctSummary[i];
				}
				summaryDTO.setAcctSummary(olcAcctSummaryRecs);
			}

		} catch (SQLException sqle) {
			logger.error("Error in getSummaryHTML() @ OracleAccountHistoryDAO "
					+ sqle, sqle);
			throw new EtccException("acctId:" + accountLoginDTO.getAcctId()
					+ "****" + "errror getting Summary HTML" + sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getSummaryHTML() @ OracleAccountHistoryDAO");
		return summaryDTO;
	}

	public String getStatementPDF(AccountLoginDTO accountLoginDTO,
			String accountVPNType, String lang) throws EtccException,
			EtccSecurityException {
		logger.info("Entering getStatementPDF() @ OracleAccountHistoryDAO");
		String url = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getStatementPDF() :: Calling olcsc_rep.View_Statements_PDF");
			cstmt = conn
					.prepareCall("{? = call olcsc_rep.View_Statements_PDF(?, ?, ?, ?, ?, ?)}");

			setErrorTypeMap();

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, "PDF");
			cstmt.setString(3, accountVPNType);
			cstmt.setString(4, "ENG");
			cstmt.setString(5, lang);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			int result = cstmt.getInt(1);
			if (result == -1) {
				logger.debug("Inside if (result == -1) @ getStatementPDF()");
				logger.error("Error in getStatementPDF() @ OracleAccountHistoryDAO :: AcctId:"
						+ accountLoginDTO.getAcctId()
						+ "****"
						+ "getStatementPDF result is -1");
				throw new EtccSecurityException(
						"Security exception in getStatementPDF()");
			} else if (result == 0) {
				logger.debug("AcctId:" + accountLoginDTO.getAcctId() + "****"
						+ "getStatementPDF result is 0");
				Array errors = (Array) cstmt.getObject(7);
				convertErrorMsgs(errors);
				return null;
			}

			url = cstmt.getString(6);
			logger.debug("AcctId:" + accountLoginDTO.getAcctId() + "****"
					+ "getStatementPDF() returns " + url);
		} catch (SQLException sqle) {
			logger.error(
					"Error in getStatementPDF() @ OracleAccountHistoryDAO "
							+ sqle, sqle);
			throw new EtccException("AcctId:" + accountLoginDTO.getAcctId()
					+ "****" + "error getting Statement PDF " + sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getStatementPDF() @ OracleAccountHistoryDAO");
		return url;
	}

	public String getSummaryPDF(AccountLoginDTO accountLoginDTO,
			String accountVPNType, String lang) throws EtccException,
			EtccSecurityException {
		logger.info("Entering getSummaryPDF() @ OracleAccountHistoryDAO");
		String url = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getSummaryPDF() :: Calling olcsc_rep.View_Summary_PDF");
			cstmt = conn
					.prepareCall("{? = call olcsc_rep.View_Summary_PDF(?, ?, ?, ?, ?, ?)}");

			setErrorTypeMap();

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, "PDF");
			cstmt.setString(3, accountVPNType);
			cstmt.setString(4, "EN");
			cstmt.setString(5, lang);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			int result = cstmt.getInt(1);
			if (result == -1) {
				logger.debug("Inside if (result == -1) @ getSummaryPDF()");
				logger.error("Error in getSummaryPDF() @ OracleAccountHistoryDAO :: AcctId:"
						+ accountLoginDTO.getAcctId()
						+ "****"
						+ "getSummaryPDF() result is -1");
				throw new EtccSecurityException(
						"Security exception in getSummaryPDF()");
			} else if (result == 0) {
				logger.debug("AcctId:" + accountLoginDTO.getAcctId() + "****"
						+ "getSummaryPDF result is 0");
				Array errors = (Array) cstmt.getObject(7);
				convertErrorMsgs(errors);
				return null;
			}

			url = cstmt.getString(6);
			logger.debug("AcctId:" + accountLoginDTO.getAcctId() + "****"
					+ "getSummaryPDF() returns " + url);

		} catch (SQLException sqle) {
			logger.error("Error in getSummaryPDF() @ OracleAccountHistoryDAO "
					+ sqle, sqle);
			throw new EtccException("AcctId:" + accountLoginDTO.getAcctId()
					+ "****" + "error getting Summary PDF " + sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getSummaryPDF() @ OracleAccountHistoryDAO");
		return url;
	}

	public AccountTransactionDTO viewTransactionMain(
			AccountLoginDTO accountLoginDTO, Calendar startDate,
			Calendar endDate, String acctVPNType) throws EtccException,
			EtccSecurityException {
		logger.info("Entering viewTransactionMain() @ OracleAccountHistoryDAO");
		AccountTransactionDTO trxDto = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("viewTransactionMain() :: Calling olcsc_rep.View_txns_Main");
			cstmt = conn
					.prepareCall("{? = call olcsc_rep.View_txns_Main(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OlcAccountTagRec.class);
			typeMap.put("OL_OWNER.OLC_VIEWTXN_TXNTYPE_REC",
					OlcViewtxnTxntypeRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setLong(2, accountLoginDTO.getAcctId());
			cstmt.setString(3, "AC");
			cstmt.setString(4, accountLoginDTO.getLoginId());
			cstmt.setString(5, accountLoginDTO.getDbSessionId());
			cstmt.setString(6, accountLoginDTO.getLastLoginIp());
			if (startDate != null) {
				logger.debug("Inside if (startDate != null)) @ viewTransactionMain()");
				cstmt.setTimestamp(7, new java.sql.Timestamp(startDate
						.getTime().getTime()));
			} else {
				logger.debug("Inside else of if (startDate != null)) @ viewTransactionMain()");
				cstmt.setTimestamp(7, null);
			}

			if (endDate != null) {
				logger.debug("Inside if (endDate != null)) @ viewTransactionMain()");
				cstmt.setTimestamp(8, new java.sql.Timestamp(endDate.getTime()
						.getTime()));
			} else {
				logger.debug("Inside else of if (endDate != null)) @ viewTransactionMain()");
				cstmt.setTimestamp(8, null);
			}

			cstmt.setString(9, acctVPNType);
			cstmt.registerOutParameter(10, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
			cstmt.registerOutParameter(11, Types.ARRAY,
					"OL_OWNER.OLC_VIEWTXN_TXNTYPE_ARR");
			cstmt.registerOutParameter(12, Types.VARCHAR);
			cstmt.registerOutParameter(13, Types.VARCHAR);
			cstmt.registerOutParameter(14, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			logger.debug("acctId:" + accountLoginDTO.getAcctId() + "****"
					+ "start calling View_txns_Main api");
			cstmt.execute();
			logger.debug("acctId:" + accountLoginDTO.getAcctId() + "****"
					+ "end calling View_txns_Main api");

			trxDto = new AccountTransactionDTO();
			int result = cstmt.getInt(1);
			if (result == -1) {
				logger.debug("Inside if (result == -1) @ viewTransactionMain()");
				logger.error("Error in viewTransactonMain() @ OracleAccountHistoryDAO:: acctId:"
						+ accountLoginDTO.getAcctId()
						+ "****"
						+ "viewTransactonMain() result is -1");
				throw new EtccSecurityException(
						"Security exception in viewTransactonMain()");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ viewTransactionMain()");
				logger.debug("acctId:" + accountLoginDTO.getAcctId() + "****"
						+ "viewTransactonMain() result is 0");
				Array errors = (Array) cstmt.getObject(14);
				trxDto.setErrors(convertErrorMsgs(errors));
				return trxDto;
			}

			trxDto.setPdfLink(cstmt.getString(12));
			trxDto.setXslLink(cstmt.getString(13));

			Object[] acctTags = (Object[]) cstmt.getArray(10).getArray(
					conn.getTypeMap());
			if (acctTags != null && acctTags.length > 0) {
				logger.debug("Inside if (acctTags != null && acctTags.length > 0) @ viewTransactionMain()");
				OlcAccountTagRecBean[] olcAccountTagRecs = new OlcAccountTagRecBean[acctTags.length];
				for (int i = 0; i < acctTags.length; i++) {
					olcAccountTagRecs[i] = new OlcAccountTagRecBean(
							(OlcAccountTagRec) acctTags[i]);
				}
				trxDto.setAcctTags(olcAccountTagRecs);
			}

			Object[] txnTypes = (Object[]) cstmt.getArray(11).getArray();

			if (txnTypes != null && txnTypes.length > 0) {
				logger.debug("Inside if (txnTypes != null && txnTypes.length > 0) @ viewTransactionMain()");
				OlcViewtxnTxntypeRec[] olcTxntypeRecs = new OlcViewtxnTxntypeRec[txnTypes.length];
				for (int i = 0; i < olcTxntypeRecs.length; i++) {
					olcTxntypeRecs[i] = (OlcViewtxnTxntypeRec) txnTypes[i];
				}
				trxDto.setTxnTypes(olcTxntypeRecs);
			}

		} catch (SQLException sqle) {
			logger.error(
					"Error in viewTransactonMain() @ OracleAccountHistoryDAO "
							+ sqle, sqle);
			throw new EtccException("acctId:" + accountLoginDTO.getAcctId()
					+ "****" + "error in viewTransactionMain() " + sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving viewTransactionMain() @ OracleAccountHistoryDAO");
		return trxDto;
	}

	public String getReportServletUrl(String reportName, String sessionId)
			throws EtccException {
		logger.info("Entering getReportServletUrl() @ OracleAccountHistoryDAO");
		String reportServletUrl = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getReportServletUrl() :: Calling olcsc_param.get_report_servlet_url");
			cstmt = conn
					.prepareCall("{? = call olcsc_param.get_report_servlet_url(?, ?, ?, ?)}");
			setErrorTypeMap();

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, sessionId);
			cstmt.setString(3, reportName);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			int result = cstmt.getInt(1);

			if (result == 0) {
				logger.debug("Inside if (result == 0) @ getReportServletUrl()");
				logger.debug("getReportServletUrl result is 0");
				Array errors = (Array) cstmt.getObject(5);
				convertErrorMsgs(errors);
				return null;
			}
			reportServletUrl = cstmt.getString(4);
		} catch (SQLException sqle) {
			logger.error(
					"Error in getReportServletUrl() @ OracleAccountHistoryDAO "
							+ sqle, sqle);
			throw new EtccException("Error in getReportServletUrl() " + sqle,
					sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getReportServletUrl() @ OracleAccountHistoryDAO");
		return reportServletUrl;
	}

	private String getConnectionTimeout() throws SQLException {
		logger.info("Entering getConnectionTimeout() @ OracleAccountHistoryDAO");
		String result = null;
		logger.info("getConnectionTimeout() :: Calling utl_web.GETSYSPARM");
		cstmt = conn.prepareCall("{? = call "
				+ "utl_web.GETSYSPARM('OLCSC_CONNECTION_TIMEOUT')}");

		cstmt.registerOutParameter(1, Types.VARCHAR);
		cstmt.execute();

		result = cstmt.getString(1);
		logger.debug("connectionTimeout:" + result);

		logger.info("Leaving getConnectionTimeout() @ OracleAccountHistoryDAO");
		return result;
	}

	public List<InvoiceArchiveDTO> getArchiveInvoices(
			AccountLoginDTO accountLoginDTO) throws EtccException {
		logger.info("Entering getArchiveInvoices() @ OracleAccountHistoryDAO");
		List<InvoiceArchiveDTO> result = new ArrayList<InvoiceArchiveDTO>();
		InvoiceArchiveDTO tmpDto = null;

		try {
			setConnection(Util.getDbConnection());
			String timeout = getConnectionTimeout();
			logger.info("getArchiveInvoices() :: Calling olcsc_rep.View_Archive_invoices");
			cstmt = conn
					.prepareCall("{? = call olcsc_rep.View_Archive_invoices(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

			if (timeout != null) {
				logger.debug("Inside if (timeout != null) @ getOlcscShiftId()");
				int iTimeout = Integer.parseInt(timeout);
				cstmt.setQueryTimeout(iTimeout);
			}

			Map typeMap = new HashMap();
			// typeMap.put("OL_OWNER.OLC_TAG_SUMMARY_REC",OlcArchiveInvsRec.class)
			// ;
			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setLong(2, accountLoginDTO.getAcctId());
			cstmt.setString(3, "AC");
			cstmt.setString(4, accountLoginDTO.getLoginId());
			cstmt.setString(5, accountLoginDTO.getDbSessionId());
			cstmt.setString(6, accountLoginDTO.getLastLoginIp());
			cstmt.execute();

			Array archivedInvs = (Array) cstmt.getObject(2);
			Object array[] = (Object[]) archivedInvs.getArray();

			for (int i = 0; i < array.length; i++) {
				result = new ArrayList<InvoiceArchiveDTO>(array.length);
				tmpDto = new InvoiceArchiveDTO();
				/*
				 * OlcArchiveInvsRec archive = (OlcArchiveInvsRec)array[i];
				 * tmpDto.setInvoiceDate(archive.getInvoiceDate());
				 * tmpDto.setInvoiceId(archive.getIvoiceId());
				 * tmpDto.setInvoiceType(archive.getInvoiceType());
				 * tmpDto.setInvoiceUrl(archive.getInvoiceUrl());
				 */
				result.add(tmpDto);
			}

		} catch (Throwable t) {
			logger.error(
					"Error in getArchiveInvoices() @ OracleAccountHistoryDAO "
							+ t, t);
			throw new EtccException("Error running getArchiveInvoices: " + t, t);
		}

		logger.info("Leaving getArchiveInvoices() @ OracleAccountHistoryDAO");
		return result;
	}
}
