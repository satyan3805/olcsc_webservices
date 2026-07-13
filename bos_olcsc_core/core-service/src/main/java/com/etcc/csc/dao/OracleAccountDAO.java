package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.DateUtil;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.datatype.PlanDetailBalances;
import com.etcc.csc.dto.AccountCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountDeviceDTO;
import com.etcc.csc.dto.AccountIopDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.CitationInfoRecDTO;
import com.etcc.csc.dto.InvoicesDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagUpdateResultDTO;
import com.etcc.csc.dto.TransactionDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.dto.UserPreferenceResultDTO;
import com.etcc.csc.dto.UserPreferenceValueDTO;
import com.etcc.csc.enums.EventEnum;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcAccountCardStatusRec;
import com.etcc.csc.plsql.OlcAccountHistoryRec;
import com.etcc.csc.plsql.OlcAccountInfoRec;
import com.etcc.csc.plsql.OlcAccountTagRec;
import com.etcc.csc.plsql.OlcAcctAlertRec;
import com.etcc.csc.plsql.OlcAcctCardRemovalRsnRec;
import com.etcc.csc.plsql.OlcAcctSecQuestRec;
import com.etcc.csc.plsql.OlcCitationInfoArr;
import com.etcc.csc.plsql.OlcCitationInfoRec;
import com.etcc.csc.plsql.OlcDeviceValueRec;
import com.etcc.csc.plsql.OlcErrorMsgArr;
import com.etcc.csc.plsql.OlcErrorMsgRec;
import com.etcc.csc.plsql.OlcIopAcctInfoRec;
import com.etcc.csc.plsql.OlcPlanDetailBalanceRec;
import com.etcc.csc.plsql.OlcPrefInfoRec;
import com.etcc.csc.plsql.OlcPrefInfoSetRec;
import com.etcc.csc.plsql.OlcUserPrefValueRec;
import com.etcc.csc.plsql.OlcViewInvoiceRec;

public class OracleAccountDAO extends AccountDAO {

	private Logger logger = Logger.getLogger(OracleAccountDAO.class);

	public OracleAccountDAO() {
	}

	public AccountLoginDTO accountExists(String accountNumber,
			String tolltagPrefix, String tolltagNumber,
			String driverLicenseState, String driverLicenseNumber, String taxId)
			throws EtccException {
		logger.info("Entering accountExists() @ OracleAccountDAO");
		logger.debug(new StringBuilder("accountExists: acct=")
				.append(accountNumber));

		setConnection(Util.getDbConnection());

		AccountLoginDTO acctLoginDto = new AccountLoginDTO();
		Collection result = null;
		boolean useTollTag = false;
		if (accountNumber == null || accountNumber.trim().length() == 0) {
			useTollTag = true;
		}
		try {
			logger.info("accountExists() :: Calling Olcsc_Acct_Mgmt.document_exists");
			cstmt = conn.prepareCall("{? = call "
					+ "Olcsc_Acct_Mgmt.document_exists("
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, null); // session
			cstmt.setString(idx++, null); // user
			cstmt.setString(idx++, null); // ip address
			if (useTollTag) {
				// cstmt.setNull(idx++, Types.NUMERIC);
				cstmt.setLong(idx, Long.parseLong(tolltagNumber)); // doc_id
				cstmt.registerOutParameter(idx++, Types.NUMERIC);
			} else {
				cstmt.setLong(idx++, Long.parseLong(accountNumber)); // doc_id
			}
			if (useTollTag) {
				cstmt.setString(idx++, Constants.LOGIN_TYPE_TOLLTAG); // doc_type
			} else {
				cstmt.setString(idx++, Constants.LOGIN_TYPE_ACCOUNT); // doc_type
			}
			// cstmt.setString(idx++, null); // doc_status
			if (useTollTag) {
				cstmt.setString(idx++, "X"); // doc_status
			} else {
				cstmt.setString(idx++, "A"); // doc_status
			}
			cstmt.setString(idx++, tolltagNumber);
			cstmt.setString(idx++, tolltagPrefix);
			cstmt.setString(idx++, driverLicenseState);
			cstmt.setString(idx++, driverLicenseNumber);
			cstmt.setString(idx++, taxId);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			short docExists = cstmt.getShort(1);
			if (docExists == 0) {
				Array errors = (Array) cstmt.getObject(idx);
				result = Util.convertErrorMsgs(errors);
				if (result == null || result.size() == 0) {
					result = new ArrayList();
					ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
					errMsgDto.setMessage("Invalid information entered. "
							+ "Please try again.");
					result.add(errMsgDto);
				}
				acctLoginDto.setErrors(result);
			} else {
				// TODO: populate acctId properly
				if (useTollTag) {
					acctLoginDto.setAcctId(cstmt.getLong(5));
				} else {
					acctLoginDto.setAcctId(Long.parseLong(accountNumber));
				}
			}

		} catch (Throwable t) {
			logger.error("Error in accountExists() @ OracleAccountDAO ", t);
			throw new EtccException("Error running accountExists: " + t, t);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving accountExists() @ OracleAccountDAO");
		return acctLoginDto;
	}// end of accountExists()

	public AccountLoginDTO setupOnlineAccessStep1(String acctCard,
			String accountNumber, String tolltagPrefix, String tolltagNumber,
			String driverLicenseState, String driverLicenseNumber,
			String taxId, String ipAddress, String sessionId,
			UserEnvDTO userEnvDto, String locale) throws EtccException {
		logger.info("Entering setupOnlineAccessStep1() @ OracleAccountDAO");
		logger.debug(new StringBuilder("setupOnlineAccessStep1: acct=")
				.append(accountNumber).append(", tolltagPrefix=")
				.append(tolltagPrefix).append(", tolltagNumber=")
				.append(tolltagNumber));

		setConnection(Util.getDbConnection());

		AccountLoginDTO acctLoginDto = new AccountLoginDTO();
		Collection result = null;
		boolean useTollTag = false;
		boolean useDriverLicense = true;

		// old
		if (accountNumber == null || accountNumber.trim().length() == 0) {
			useTollTag = true;
		}

		if (driverLicenseNumber == null
				|| driverLicenseNumber.trim().length() == 0) {

			useDriverLicense = false;
		}
		if (tolltagPrefix != null && !tolltagPrefix.endsWith(".")) {
			tolltagPrefix = tolltagPrefix + ".";
		}

		try {
			logger.info("setupOnlineAccessStep1() :: Calling Olcsc_olcsetup.set_online_access_step1");
			cstmt = conn
					.prepareCall("{? = call "
							+ "Olcsc_olcsetup.set_online_access_step1("
							+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			if (useTollTag) {
				cstmt.setNull(idx, Types.NUMERIC); // doc_id
			} else {

				cstmt.setLong(idx, Long.parseLong(accountNumber)); // doc_id

			}

			if (accountNumber == null)
				cstmt.setNull(idx, Types.NUMERIC);

			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.setString(idx++, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(idx++, ipAddress);
			cstmt.setString(idx++, sessionId);
			cstmt.setString(idx++, userEnvDto.getBrowserType());
			cstmt.setString(idx++, userEnvDto.getBrowserVersion());
			cstmt.setString(idx++, userEnvDto.getOsType()); // os type
			cstmt.setString(idx++, userEnvDto.getOsVersion()); // os version
			cstmt.setString(idx++, userEnvDto.getAttribute1());
			cstmt.setString(idx++, userEnvDto.getAttribute2());
			cstmt.setString(idx++, userEnvDto.getAttribute3());
			cstmt.setString(idx++, userEnvDto.getAttribute4());
			cstmt.setString(idx++, userEnvDto.getAttribute5());
			if (tolltagNumber == null || tolltagNumber.length() == 0) {
				cstmt.setNull(idx++, Types.VARCHAR);
				cstmt.setNull(idx++, Types.VARCHAR);
			} else {
				cstmt.setString(idx++, tolltagPrefix);
				cstmt.setString(idx++, tolltagNumber);
			}
			cstmt.setString(idx++, driverLicenseState);
			cstmt.setString(idx++, driverLicenseNumber);
			cstmt.setString(idx++, taxId);
			cstmt.setString(idx++, "EN");
			cstmt.setString(idx++, locale);
			cstmt.registerOutParameter(idx++, Types.VARCHAR);
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setString(idx, acctCard);
			cstmt.execute();

			short docExists = cstmt.getShort(1);
			if (docExists == 0) {
				Array errors = (Array) cstmt.getObject(23);
				result = Util.convertErrorMsgs(errors);
				if (result == null || result.size() == 0) {
					result = new ArrayList();
					ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
					errMsgDto.setMessage("Invalid information entered. "
							+ "Please try again.");
					result.add(errMsgDto);
				}
				acctLoginDto.setErrors(result);
			} else {
				// TODO: populate acctId properly
				if (useTollTag) {
					acctLoginDto.setAcctId(cstmt.getLong(2));

				} else {
					acctLoginDto.setAcctId(Long.parseLong(accountNumber));
				}
				acctLoginDto.setDbSessionId(cstmt.getString(22));
				acctLoginDto.setLastLoginIp(ipAddress);
				acctLoginDto.setLoginId(Constants.LOGIN_GENERIC_USER);
			}

		} catch (Throwable t) {
			logger.error(
					"Error in setupOnlineAccessStep1() @ OracleAccountDAO ", t);
			throw new EtccException("Error running accountExists: " + t, t);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving setupOnlineAccessStep1() @ OracleAccountDAO");
		return acctLoginDto;
	}// end of setupOnlineAccessStep1()

	/**
	 * Creates acct login for the user.
	 * 
	 * @param acctLoginDto
	 * @param oasDto
	 * @return
	 * @throws EtccSecurityException
	 * @throws EtccException
	 */
	public AccountLoginDTO setupOnlineAccess(AccountLoginDTO acctLoginDto,
			OnlineAccessSetupDTO oasDto) throws EtccSecurityException,
			EtccException {
		logger.info("Entering setupOnlineAccess() @ OracleAccountDAO");
		logger.debug(new StringBuilder("setupOnlineAccessStep: acct=")
				.append(acctLoginDto.getAcctId()).append(", loginId=")
				.append(oasDto.getLoginId()));
		AccountLoginDTO result = null;
		setConnection(Util.getDbConnection());
		try {
			String loginId = oasDto.getLoginId();
			if (loginId != null) {
				loginId = loginId.toUpperCase();
			}

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCT_SEC_QUEST_REC",
					OlcAcctSecQuestRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			logger.info("setupOnlineAccess() :: Calling Set_Account_Login_Info");
			cstmt = conn.prepareCall("{? = call OLCSC_OLCSETUP."
					+ "Set_Account_Login_Info(?,?,?,   ?,?,?,  ?,?,?, "
					+ "TRUE,TRUE,?, ?,?,?, ?,?,?)}");
			byte idx = 1;

			cstmt.registerOutParameter(idx++, Types.SMALLINT);

			cstmt.setString(idx++, acctLoginDto.getDbSessionId());// 2
			cstmt.setLong(idx++, acctLoginDto.getAcctId()); // 3
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());// 4

			cstmt.setString(idx++, acctLoginDto.getLoginId());// 5
			cstmt.setString(idx++, loginId);// 6
			cstmt.setString(idx++, oasDto.getPassword());// 7

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_ACCT_SEC_QUEST_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertToOracleSecurityQuestions(oasDto
							.getListOfQuestionsnAnswers()));

			cstmt.setArray(idx++, array);// 8
			cstmt.setString(idx++, oasDto.getEmailAddress());// 9
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");// 10

			cstmt.registerOutParameter(idx++, Types.VARCHAR);// 11

			cstmt.registerOutParameter(idx++, Types.NUMERIC);// 12
			cstmt.registerOutParameter(idx++, Types.NUMERIC);// 13
			cstmt.registerOutParameter(idx++, Types.NUMERIC);// 14

			cstmt.registerOutParameter(idx++, Types.NUMERIC);// 15
			cstmt.registerOutParameter(idx++, Types.VARCHAR);// 16
			cstmt.setInt(idx, 21443);// 17

			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 0) {
				result = new AccountLoginDTO();
				Array errors = (Array) cstmt.getObject(10);
				result.setErrors(Util.convertErrorMsgs(errors));
				if (result.getErrors() == null
						|| result.getErrors().size() == 0) {
					Collection temp = new ArrayList();
					ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
					errMsgDto.setMessage("Unable to save the information.");
					temp.add(errMsgDto);
					result.setErrors(temp);
				}
			} else if (success == -1) {
				logger.error("Error in setupOnlineAccess() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "setupOnlineAccess");
			} else if (success == 1) {
				result = acctLoginDto;
				result.setDbSessionId(cstmt.getString(11));
				result.setLoginId(oasDto.getLoginId());
				result.setLoginType(Constants.LOGIN_TYPE_ACCOUNT);

				BigDecimal minTag = (BigDecimal) cstmt.getObject(12);
				BigDecimal maxTag = (BigDecimal) cstmt.getObject(13);
				BigDecimal minVehicle = (BigDecimal) cstmt.getObject(14);
				BigDecimal maxVehicle = (BigDecimal) cstmt.getObject(15);
				String planCode = cstmt.getString(16);

				result.setMinTag(minTag == null ? -100 : minTag.longValue());
				result.setMaxTag(maxTag == null ? -100 : maxTag.longValue());
				result.setMinVehicle(minVehicle == null ? -100 : minVehicle
						.longValue());
				result.setMaxVehicle(maxVehicle == null ? -100 : maxVehicle
						.longValue());
				result.setAcctPlan(planCode);

				logger.debug("minTag = " + result.getMinTag() + ",maxTag = "
						+ result.getMaxTag() + ",minVehicle="
						+ result.getMinVehicle() + ",maxVehicle="
						+ result.getMaxVehicle() + ",planCode="
						+ result.getAcctPlan());

			}

		} catch (SQLException sqle) {
			logger.error("Error in setupOnlineAccess() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("Error running accountExists: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving setupOnlineAccess() @ OracleAccountDAO");
		return result;
	}// end of setupOnlineAccess()

	/**
	 * @Deprecated loginAccount takes over the function of this method.
	 * 
	 *             Validates an account given a user name, password, and ip
	 *             address.
	 * @throws com.etcc.csc.common.EtccException
	 * @return
	 * @param userName
	 * @param password
	 * @param ipAddress
	 */
	public AccountLoginDTO validateAccount(String userName, String password,
			String ipAddress) throws EtccException {
		logger.info("Entering validateAccount() @ OracleAccountDAO");
		AccountLoginDTO acctLogin = null;
		try {
			acctLogin = _validateAccount(userName, password, ipAddress);
		} catch (Throwable t) {
			logger.error("Error in validateAccount() @ OracleAccountDAO ", t);
			throw new EtccException("Error getting account for " + userName
					+ " " + t, t);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving validateAccount() @ OracleAccountDAO");
		return acctLogin;
	}// end of validateAccount()

	/**
	 * @Deprecated loginAccount takes over the function of this method. Called
	 *             internally by public dao methods. Connection is closed by the
	 *             calling method.
	 * @param userName
	 * @param password
	 * @param ipAddress
	 * @return
	 * @throws EtccException
	 */
	private AccountLoginDTO _validateAccount(String userName, String password,
			String ipAddress) throws Exception {
		logger.info("Entering _validateAccount() @ OracleAccountDAO");
		AccountLoginDTO acctLogin = null;
		setConnection(Util.getDbConnection());
		logger.info("_validateAccount() :: Calling OLCSC_LOGIN.LOGIN");
		cstmt = conn
				.prepareCall("{? = call OLCSC_LOGIN.LOGIN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		byte idx = 1;
		if (userName != null) {
			userName = userName.toUpperCase();
		}
		if (password != null) {
			password = password.toUpperCase();
		}

		cstmt.registerOutParameter(idx++, Types.SMALLINT);
		cstmt.setString(idx++, userName);
		cstmt.setString(idx++, password);
		cstmt.setString(idx++, userName); // session id
		cstmt.setString(idx++, ipAddress);
		cstmt.setString(idx++, null); // user env attr 1
		cstmt.setString(idx++, null); // user env attr 2
		cstmt.setString(idx++, null); // user env attr 3
		cstmt.setString(idx++, null); // user env attr 4
		cstmt.setString(idx++, null); // user env attr 5
		cstmt.setString(idx++, null); // user env attr 1
		cstmt.setString(idx++, null); // user env attr 2
		cstmt.setString(idx++, null); // user env attr 3
		cstmt.setString(idx++, null); // user env attr 4
		cstmt.setString(idx++, null); // user env attr 5
		cstmt.registerOutParameter(idx++, Types.NUMERIC);
		cstmt.registerOutParameter(idx++, Types.VARCHAR);
		cstmt.registerOutParameter(idx++, Types.VARCHAR);
		cstmt.registerOutParameter(idx, Types.ARRAY,
				"OL_OWNER.OLC_ERROR_MSG_ARR");

		cstmt.executeUpdate();

		int result = cstmt.getInt(1);
		if (result == 1) {
			acctLogin = new AccountLoginDTO();
			acctLogin.setAcctId(cstmt.getLong(idx - 3));
			acctLogin.setLoginId(userName);
			acctLogin.setLastLoginIp(ipAddress);
			acctLogin.setAcctStatus(cstmt.getString(idx - 1));
			acctLogin.setDbSessionId(cstmt.getString(idx - 2));
		}
		logger.info("Leaving _validateAccount() @ OracleAccountDAO");
		return acctLogin;
	}// end of _validateAccount()

	public AccountDTO getAccount(AccountLoginDTO acctLoginDto, long acctId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getAccount() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getAccount: acct=").append(acctId));
		AccountDTO result = null;
		setConnection(Util.getDbConnection());
		try {
			logger.info("getAccount() :: Calling OLCSC_ACCT_MGMT.Get_Account_Info");
			cstmt = conn.prepareCall("{? = call OLCSC_ACCT_MGMT."
					+ "Get_Account_Info(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null)}");

			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_INFO_REC",
					OlcAccountInfoRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", OlcErrorMsgRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_ARR", OlcErrorMsgArr.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			if ("IN".equals(acctLoginDto.getLoginType())) {
				logger.debug("param 2 =" + acctLoginDto.getInvoiceId());
				cstmt.setLong(2, acctLoginDto.getInvoiceId());
			} else {
				logger.debug("param 2 =" + acctLoginDto.getAcctId());
				cstmt.setLong(2, acctLoginDto.getAcctId());
			}

			logger.debug("param 3 =" + acctLoginDto.getLoginType());
			cstmt.setString(3, acctLoginDto.getLoginType());

			logger.debug("param 4 =" + acctLoginDto.getDbSessionId());
			cstmt.setString(4, acctLoginDto.getDbSessionId());

			logger.debug("param 5 =" + acctLoginDto.getLastLoginIp());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());

			logger.debug("param 6 =" + acctLoginDto.getLoginId());
			cstmt.setString(6, acctLoginDto.getLoginId());

			cstmt.registerOutParameter(7, Types.STRUCT,
					"OL_OWNER.OLC_ACCOUNT_INFO_REC");
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.registerOutParameter(9, Types.VARCHAR);
			cstmt.registerOutParameter(10, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			logger.debug("param 2 = 0");
			cstmt.setInt(11, 0);

			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 1) {
				OlcAccountInfoRec acctInfo = (OlcAccountInfoRec) cstmt
						.getObject(7);
				if (acctInfo != null) {
					result = copyAccountProperties(acctInfo);
					if (result != null) {
						result.setAcctId(acctId);
						result.setVpnType(cstmt.getString(8));
						result.setPmtTypeCode(cstmt.getString(9));
					}
				}
			} else if (success == -1) {
				Array errors = (Array) cstmt.getObject(10);
				String zips = errors.toString();
				logger.debug(zips);
				logger.error("Error in getAccount() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "getAccountInfo");
			}

		} catch (SQLException sqle) {
			logger.error("Error in getAccount() @ OracleAccountDAO ", sqle);
			throw new EtccException("Error running accountExists: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getAccount() @ OracleAccountDAO");
		return result;
	}// end of getAccount()

	private String fetchInvoicesQuery() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT i.invoice_id,");
        builder.append(" i.account_id,");
        builder.append(" ies.short_description invoice_type,");
        builder.append(" CAST (i.current_issue_date AS TIMESTAMP) AS invoice_date,");
        builder.append(" ni.pdf_file_path      pdf_file_path, ");
        builder.append(" ist.short_description ||DECODE(nvl(ist.short_description,").append("'Open')").append(",'Open',").append("' '").append(",'/'").append("||hty.short_description) Status_Reason, ");
        builder.append(" CAST (DECODE(NVL(h.hold_type_code,").append("'X')").append(", consts$.HLD_TYP_MANUAL_HOLD,H.EXPIRATION_DATE, i.curr_due_date) as TIMESTAMP) Invoice_Due_DATE");
        builder.append(" FROM invoices i, holds h, hold_transactions ht,");
        builder.append(" invoice_escalation_states ies, invoice_notifications inv_not,");
        builder.append(" notification_items ni,");
        builder.append(" invoice_batches ib,");
        builder.append(" invoice_statuses ist, hold_types hty");
        builder.append(" WHERE i.invoice_batch_id = ib.invoice_batch_id");
        builder.append(" AND i.invoice_escalation_state_code = ies.invoice_escalation_state_code");
        builder.append(" AND i.account_id = ?");
        builder.append(" AND inv_not.notification_item_id = ni.notification_item_id(+)");
        builder.append(" AND inv_not.invoice_id(+) = i.invoice_id");
        builder.append(" AND i.invoice_id          = ht.invoice_id(+)");
        builder.append(" AND i.invoice_status_code  = ist.invoice_status_code");
        builder.append(" and ht.hold_id            = h.hold_id(+)");
        builder.append(" AND h.hold_type_code      = hty.hold_type_code(+)");
        //builder.append(" AND ib.invoice_batch_mail_status_code(+) = ");
        //builder.append("consts$.INV_MAIL_STS_CONFIRM -- Confirmation received / Sent...*/ ");
        builder.append(" AND i.current_issue_date BETWEEN SYSDATE - (olcsc_util.GETSYSPARM(").append("'OLC HOME PAGE DAYS BACK'))").append(" AND SYSDATE ");
        builder.append(" ORDER BY i.invoice_id ");
        
        return builder.toString();
	}
	
	public Collection<InvoicesDTO> getInvoiceDetails(AccountLoginDTO accountLoginDTO)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getInvoiceDetails() @ OracleAccountDAO");
		ArrayList<InvoicesDTO> result = null;
		setConnection(Util.getDbConnection());
		try {

			logger.debug("inside sql");
			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_VIEW_INVOICE_REC",
					OlcViewInvoiceRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_ARR", OlcErrorMsgArr.class);
			conn.setTypeMap(typeMap);
			
			logger.info("getInvoiceDetails() :: Calling olcsc_rep.View_invoices");
			
			cstmt = conn
					.prepareCall("{? = call olcsc_rep.View_invoices(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, accountLoginDTO.getAcctId());
			cstmt.setString(3, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(4, accountLoginDTO.getLoginId());
			cstmt.setString(5, accountLoginDTO.getDbSessionId());
			cstmt.setString(6, accountLoginDTO.getLastLoginIp());
			cstmt.setString(7, "Y");
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_VIEW_INVOICE_ARR");
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setInt(10, 21746);
			
			//cstmt.execute();
			
			ps = conn.prepareStatement(fetchInvoicesQuery());
			ps.setLong(1, accountLoginDTO.getAcctId());
			rs = ps.executeQuery();
			
			//short success = cstmt.getShort(1);
				result = new ArrayList<InvoicesDTO>();
				 while(rs.next()) {
					 	InvoicesDTO invoiceDTO = new InvoicesDTO();
						invoiceDTO.setInvoiceId((BigDecimal)rs.getObject(1));
						invoiceDTO.setInvoiceType((String)rs.getObject(3));
						invoiceDTO.setInvoiceDate((Timestamp)rs.getObject(4));
						invoiceDTO.setInvioceUrl((String)rs.getObject(5));
						invoiceDTO.setInvoiceStatusReason((String)rs.getObject(6));
						invoiceDTO.setInvoiceDueDate((Timestamp)rs.getObject(7));
						
						logger.debug("invoices DTO record : " + invoiceDTO.toString());

						result.add(invoiceDTO);
					 
				 }
		} catch (SQLException sqle) {
			logger.error("Error in getInvoiceDetails() @ OracleAccountDAO ",
					sqle);
			logger.error("Error Details in getInvoiceDetails() --> " +
					sqle.getCause() + " Message : " + sqle.getMessage() + " Loc Message : "+sqle.getLocalizedMessage() + " Error Code : "+sqle.getErrorCode(), sqle);
			sqle.printStackTrace();
			
			throw new EtccException("Error running accountExists: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getInvoiceDetails() @ OracleAccountDAO");
		return result;
	}// end of getInvoiceDetails()

	public AccountDTO getNewAccount(AccountLoginDTO acctLoginDto, long acctId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getNewAccount() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getAccount: acct=").append(acctId));
		AccountDTO result = null;
		setConnection(Util.getDbConnection());
		try {
			logger.info("getNewAccount() :: Calling OLCSC_ACCT_MGMT.Get_New_Account_Info");
			cstmt = conn.prepareCall("{? = call OLCSC_ACCT_MGMT."
					+ "Get_New_Account_Info(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_INFO_REC",
					OlcAccountInfoRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setLong(2, acctId);
			cstmt.setString(3, acctLoginDto.getLoginType());
			cstmt.setString(4, acctLoginDto.getDbSessionId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.setString(6, acctLoginDto.getLoginId());
			cstmt.registerOutParameter(7, Types.STRUCT,
					"OL_OWNER.OLC_ACCOUNT_INFO_REC");
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.registerOutParameter(9, Types.VARCHAR);
			cstmt.registerOutParameter(10, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 1) {
				OlcAccountInfoRec acctInfo = (OlcAccountInfoRec) cstmt
						.getObject(7);
				if (acctInfo != null) {
					result = copyAccountProperties(acctInfo);
					if (result != null) {
						result.setAcctId(acctId);
						result.setVpnType(cstmt.getString(8));
						result.setPmtTypeCode(cstmt.getString(9));
					}
				}
			} else if (success == -1) {
				logger.error("Error in getNewAccount() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "getNewAccount");
			}

		} catch (SQLException sqle) {
			logger.error("Error in getNewAccount() @ OracleAccountDAO ", sqle);
			throw new EtccException("Error running accountExists: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getNewAccount() @ OracleAccountDAO");
		return result;
	}// end of getNewAccount()

	public AccountLoginDTO loginAccount(String userName, String password,
			String ipAddress, String sessionId, UserEnvDTO userEnvDto)
			throws EtccException {
		logger.info("Entering loginAccount() @ OracleAccountDAO");
		logger.debug(new StringBuilder("loginAccount: userName=")
				.append(userName).append(", ip=").append(ipAddress));
		AccountLoginDTO acctLogin = null;
		setConnection(Util.getDbConnection());
		try {
			logger.info("loginAccount() :: Calling OLCSC_LOGIN.LOGIN");
			cstmt = conn
					.prepareCall("{? = call OLCSC_LOGIN.LOGIN(?, ?, ?, ?, "
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			byte idx = 1;
			if (userName != null) {
				userName = userName.toUpperCase();
			}
			if (password != null) {
				password = password.toUpperCase();
			}
			StringBuilder debug = new StringBuilder(
					"Calling OLCSC_LOGIN.LOGIN(");
			debug.append(userName).append(",");
			debug.append("<password>,");
			debug.append(sessionId).append(",");
			debug.append(ipAddress).append(",");
			debug.append(userEnvDto.getBrowserType()).append(",");
			debug.append(userEnvDto.getBrowserVersion()).append(",");
			debug.append(userEnvDto.getOsType()).append(",");
			debug.append(userEnvDto.getAttribute1()).append(",");
			debug.append(userEnvDto.getAttribute2()).append(",");
			debug.append(userEnvDto.getAttribute3()).append(",");
			debug.append(userEnvDto.getAttribute4()).append(",");
			debug.append(userEnvDto.getAttribute5()).append(",");
			debug.append(userEnvDto.getLocale()).append(")");
			logger.debug(debug.toString());
			cstmt.registerOutParameter(idx++, Types.SMALLINT); // 1
			cstmt.setString(idx++, userName);// 2 P_USER
			cstmt.setString(idx++, password);// 3 P_PASSWORD
			cstmt.setString(idx++, sessionId); // 4 session id
			cstmt.setString(idx++, ipAddress);// 5 P_IP_ADDRESS
			cstmt.setString(idx++, userEnvDto.getBrowserType());// 6
																// p_browser_type
			cstmt.setString(idx++, userEnvDto.getBrowserVersion());// 7
																	// p_browser_version
			cstmt.setString(idx++, userEnvDto.getOsType());// 8 p_os_type
			cstmt.setString(idx++, userEnvDto.getOsVersion());// 9 p_os_version
			cstmt.setString(idx++, userEnvDto.getAttribute1());// 10
																// p_user_env_attribute1
			cstmt.setString(idx++, userEnvDto.getAttribute2());// 11
																// p_user_env_attribute2
			cstmt.setString(idx++, userEnvDto.getAttribute3());// 13
																// p_user_env_attribute3
			cstmt.setString(idx++, userEnvDto.getAttribute4());// 14
																// p_user_env_attribute4
			cstmt.setString(idx++, userEnvDto.getAttribute5());// 15
																// p_user_env_attribute5
			cstmt.setString(idx++, userEnvDto.getLocale());// 16 p_lang_id
			cstmt.registerOutParameter(idx++, Types.NUMERIC);// 17 P_ACCT_ID
			cstmt.registerOutParameter(idx++, Types.VARCHAR);// 18 O_SESSION_ID
			cstmt.registerOutParameter(idx++, Types.VARCHAR);// 19
																// P_ACCT_LOGIN_STATUS_CODE
			cstmt.registerOutParameter(idx++, Types.VARCHAR);// 20 p_min_tag
			cstmt.registerOutParameter(idx++, Types.NUMERIC);// 21 p_max_tag
			cstmt.registerOutParameter(idx++, Types.NUMERIC);// 22 p_min_vehicle
			cstmt.registerOutParameter(idx++, Types.NUMERIC);// 23 p_max_vehicle
			cstmt.registerOutParameter(idx++, Types.NUMERIC);// 24 o_plan_code
			cstmt.registerOutParameter(idx++, Types.VARCHAR);// 25 o_plan_code
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");// 26 OLC_error_msg_arr

			cstmt.executeUpdate();

			int result = cstmt.getInt(1);
			if (result == 1) {
				acctLogin = new AccountLoginDTO();
				acctLogin.setAcctId(cstmt.getLong(16));
				acctLogin.setLoginId(userName);
				acctLogin.setLastLoginIp(ipAddress);
				acctLogin.setAcctActivity(cstmt.getString(18));
				acctLogin.setAcctStatus(cstmt.getString(19));
				acctLogin.setDbSessionId(cstmt.getString(17));
				acctLogin.setLoginType(Constants.LOGIN_TYPE_ACCOUNT);

				BigDecimal maxVehicle = (BigDecimal) cstmt.getObject(23);
				BigDecimal minVehicle = (BigDecimal) cstmt.getObject(22);
				BigDecimal maxTag = (BigDecimal) cstmt.getObject(21);
				BigDecimal minTag = (BigDecimal) cstmt.getObject(20);
				String planCode = cstmt.getString(24);

				acctLogin.setMinTag(minTag == null ? -100 : minTag.longValue());
				acctLogin.setMaxTag(maxTag == null ? -100 : maxTag.longValue());
				acctLogin.setMinVehicle(minVehicle == null ? -100 : minVehicle
						.longValue());
				acctLogin.setMaxVehicle(maxVehicle == null ? -100 : maxVehicle
						.longValue());
				acctLogin.setAcctPlan(planCode);

				logger.debug("minTag = " + acctLogin.getMinTag() + ",maxTag = "
						+ acctLogin.getMaxTag() + ",minVehicle="
						+ acctLogin.getMinVehicle() + ",maxVehicle="
						+ acctLogin.getMaxVehicle() + ",planCode="
						+ acctLogin.getAcctPlan());

			} else {
				// get error message
				acctLogin = new AccountLoginDTO();
				acctLogin.setErrors(Util.convertErrorMsgs(cstmt.getArray(idx)));
			}
		} catch (Throwable t) {
			logger.error("Error in loginAccount() @ OracleAccountDAO ", t);
			throw new EtccException("Error getting account for " + userName
					+ " " + t, t);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving loginAccount() @ OracleAccountDAO");
		return acctLogin;
	}// end of loginAccount()

	public Collection getAccountTags(AccountLoginDTO accountLoginDTO)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getAccountTags() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getAccountTags: acct=")
				.append(accountLoginDTO.getAcctId()));
		Collection result = null;
		setConnection(Util.getDbConnection());
		try {
			logger.info("getAccountTags() :: Calling OLCSC_ACCT_MGMT.Get_Tag_Info");
			cstmt = conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Tag_Info"
					+ "(?,?,?,?,?,null,?)}");

			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OlcAccountTagRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
			cstmt.setLong(idx++, accountLoginDTO.getAcctId());
			cstmt.setString(idx++, accountLoginDTO.getLoginId());
			cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			byte success = cstmt.getByte(1);
			if (success == 1) {
				Object[] objArray = (Object[]) cstmt.getArray(idx - 1)
						.getArray();
				if (objArray != null && objArray.length > 0) {
					result = new ArrayList();
					for (int i = 0; i < objArray.length; i++) {
						TagDTO tagDto = copyTagProperties((OlcAccountTagRec) objArray[i]);
						tagDto.setAcctId(accountLoginDTO.getAcctId());
						result.add(tagDto);
					}
				}
			} else if (success == -1) {
				logger.error("Error in getAcccountTags() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security Exception in "
						+ "getAccountTags");
			}

		} catch (SQLException sqle) {
			logger.error("Error in getAcccountTags() @ OracleAccountDAO ", sqle);
			throw new EtccException("errror in getAcccountTags" + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getAccountTags() @ OracleAccountDAO");
		return result;
	}// end of getAccountTags()

	public Collection getAccountTagsBySearch(AccountLoginDTO accountLoginDTO,
			String[] searchArr) throws EtccException, EtccSecurityException {
		logger.info("Entering getAccountTagsBySearch() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getAccountTagsBySearch: acct=")
				.append(accountLoginDTO.getAcctId()));
		Collection result = null;
		setConnection(Util.getDbConnection());
		try {
			logger.info("getAccountTagsBySearch() :: Calling OLCSC_VEH_MGMT.Get_Tag_Info");
			cstmt = conn.prepareCall("{? = call OLCSC_VEH_MGMT.Get_Tag_Info"
					+ "(?,?,?,?,?,?,null,?)}");

			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OlcAccountTagRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"RITE_COMMON.STRING_ARRAY", conn);
			ARRAY strArr = new ARRAY(arraydesc, conn, searchArr);

			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
			cstmt.setLong(idx++, accountLoginDTO.getAcctId());
			cstmt.setString(idx++, accountLoginDTO.getLoginId());
			cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
			cstmt.setArray(idx++, strArr);
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
			// cstmt.setString(idx++,"Y");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			byte success = cstmt.getByte(1);
			if (success == 1) {
				Object[] objArray = (Object[]) cstmt.getArray(idx - 1)
						.getArray();
				if (objArray != null && objArray.length > 0) {
					result = new ArrayList();
					for (int i = 0; i < objArray.length; i++) {
						TagDTO tagDto = copyTagProperties((OlcAccountTagRec) objArray[i]);
						tagDto.setAcctId(accountLoginDTO.getAcctId());
						result.add(tagDto);
					}
				}
			} else if (success == -1) {
				logger.error("Error in getAccountTagsBySearch() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException(
						"Security Exception in getAccountTagsBySearch");
			}

		} catch (SQLException sqle) {
			logger.error(
					"Error in getAccountTagsBySearch() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("errror in getAccountTagsBySearch" + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getAccountTagsBySearch() @ OracleAccountDAO");
		return result;
	}// end of getAccountTagsBySearch()

	public List<TagDTO> getProposedAccountTags(AccountLoginDTO accountLoginDTO)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getProposedAccountTags() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getProposedAccountTags: acct=")
				.append(accountLoginDTO.getAcctId()));
		List<TagDTO> result = null;
		setConnection(Util.getDbConnection());
		try {
			logger.info("getProposedAccountTags() :: Calling OLCSC_VEH_MGMT.GET_PROPOSED_ACCT_TAG_INFO");
			cstmt = conn
					.prepareCall("{? = call OLCSC_VEH_MGMT.GET_PROPOSED_ACCT_TAG_INFO(?,?,?,?,?,null,?)}");

			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OlcAccountTagRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
			cstmt.setLong(idx++, accountLoginDTO.getAcctId());
			cstmt.setString(idx++, accountLoginDTO.getLoginId());
			cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			byte success = cstmt.getByte(1);
			if (success == 1) {
				Object[] objArray = (Object[]) cstmt.getArray(idx - 1)
						.getArray();
				if (objArray != null && objArray.length > 0) {
					result = new ArrayList<TagDTO>();
					for (int i = 0; i < objArray.length; i++) {
						TagDTO tagDto = copyTagProperties((OlcAccountTagRec) objArray[i]);
						tagDto.setAcctId(accountLoginDTO.getAcctId());
						result.add(tagDto);
					}
				}
			} else if (success == -1) {
				logger.error("Error in getProposedAccountTags() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException(
						"Security Exception in getProposedAccountTags");
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in getProposedAccountTags() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("errror in getProposedAccountTags" + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getProposedAccountTags() @ OracleAccountDAO");
		return result;
	}// end of getProposedAccountTags()

	public Collection updateAccount(AccountLoginDTO acctLoginDto,
			AccountDTO acctDto) throws EtccException, EtccSecurityException {
		logger.info("Entering updateAccount() @ OracleAccountDAO");
		logger.debug(new StringBuilder("updateAccount: acct=")
				.append(acctLoginDto.getAcctId()));
		Collection result = null;
		setConnection(Util.getDbConnection());
		try {
			logger.info("updateAccount() :: Calling OLCSC_ACCT_MGMT.Edit_Acct_Info");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT."
							+ "Edit_Acct_Info(?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,"
							+ "?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);

			logger.debug("param " + idx + "=" + acctLoginDto.getAcctId());
			cstmt.setLong(idx++, acctLoginDto.getAcctId());

			logger.debug("param " + idx + "=" + acctLoginDto.getLoginType());
			cstmt.setString(idx++, acctLoginDto.getLoginType());

			logger.debug("param " + idx + "=" + acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());

			logger.debug("param " + idx + "=" + acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());

			logger.debug("param " + idx + "=" + acctLoginDto.getLoginId());
			cstmt.setString(idx++, acctLoginDto.getLoginId());

			logger.debug("param " + idx + "=" + acctDto.getAddress1());
			cstmt.setString(idx++, acctDto.getAddress1());

			logger.debug("param " + idx + "=" + acctDto.getAddress2());
			cstmt.setString(idx++, acctDto.getAddress2());

			logger.debug("param " + idx + "=" + acctDto.getCity());
			cstmt.setString(idx++, acctDto.getCity());

			logger.debug("param " + idx + "=" + acctDto.getState());
			cstmt.setString(idx++, acctDto.getState());

			logger.debug("param " + idx + "=" + acctDto.getZipCode());
			cstmt.setString(idx++, acctDto.getZipCode());

			logger.debug("param " + idx + "=" + acctDto.getPlus4());
			cstmt.setString(idx++, acctDto.getPlus4());

			logger.debug("param " + idx + "=" + acctDto.getHomePhoNbr());
			cstmt.setString(idx++, acctDto.getHomePhoNbr());

			logger.debug("param " + idx + "=" + acctDto.getWorkPhoNbr());
			cstmt.setString(idx++, acctDto.getWorkPhoNbr());

			logger.debug("param " + idx + "=" + acctDto.getWorkPhoExt());
			cstmt.setString(idx++, acctDto.getWorkPhoExt());

			logger.debug("param " + idx + "=" + acctDto.getMobilePhoNbr());
			cstmt.setString(idx++, acctDto.getMobilePhoNbr());

			logger.debug("param " + idx + "=" + acctDto.getEmailAddress());
			cstmt.setString(idx++, acctDto.getEmailAddress());

			logger.debug("param " + idx + "=" + acctDto.getEmailAddress2());
			cstmt.setString(idx++, acctDto.getEmailAddress2());

			logger.debug("param " + idx + "=" + acctDto.getEmailAddress3());
			cstmt.setString(idx++, acctDto.getEmailAddress3());

			cstmt.setInt(idx++, 1);
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 0) {
				// process error
				result = Util.convertErrorMsgs(cstmt.getArray(idx - 1));
			} else if (success == -1) {
				logger.error("Error in updateAccount() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "updateAccount");
			}

		} catch (SQLException sqle) {
			logger.error("Error in updateAccount() @ OracleAccountDAO ", sqle);
			throw new EtccException("Error running accountExists: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leavring updateAccount() @ OracleAccountDAO");
		return result;
	}// end of updateAccount()

	public UserPreferenceResultDTO getTollTagUserPreference(String locale)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getTollTagUserPreference() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getTollTagUserPreference:"));
		UserPreferenceResultDTO userPrefResult = new UserPreferenceResultDTO();
		setConnection(Util.getDbConnection());
		try {
			logger.info("getTollTagUserPreference() :: Calling OLCSC_USER_PREFERENCE.g_Get_Preference_Info");
			cstmt = conn.prepareCall("{? = call OLCSC_USER_PREFERENCE."
					+ "g_Get_Preference_Info(?,?,?,?,'Y')}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_PREF_INFO_REC", OlcPrefInfoRec.class);
			typeMap.put("OL_OWNER.OLC_DEVICE_VALUE_REC",
					OlcDeviceValueRec.class);
			typeMap.put("OL_OWNER.OLC_USER_PREF_VALUE_REC",
					OlcUserPrefValueRec.class);
			typeMap.put("OL_OWNER.OLC_IOP_ACCT_INFO_REC",
					OlcIopAcctInfoRec.class);

			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, "EN");
			cstmt.setString(idx++, locale);
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_PREF_INFO_ARR");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			byte success = cstmt.getByte(1);
			if (success == 1) {
				Collection userPrefs = convertUserPreferenceFromDB((Object[]) cstmt
						.getArray(idx - 1).getArray());
				userPrefResult.setUserPreferences(userPrefs);
			} else if (success == -1) {
				logger.error("Error in getTollTagUserPreference() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security Exception in "
						+ "getTollTagUserPreference");
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in getTollTagUserPreference() @ OracleAccountDAO ",
					sqle);
			throw new EtccException(
					"errror in getTollTagUserPreference" + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getTollTagUserPreference() @ OracleAccountDAO");
		return userPrefResult;
	}// end of getTollTagUserPreference()

	public UserPreferenceResultDTO getUserPreference(
			AccountLoginDTO accountLoginDTO) throws EtccException,
			EtccSecurityException {
		logger.info("Entering getUserPreference() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getUserPreference: acct=")
				.append(accountLoginDTO.getAcctId()));
		UserPreferenceResultDTO userPrefResult = new UserPreferenceResultDTO();
		setConnection(Util.getDbConnection());
		try {
			logger.info("getUserPreference() :: Calling OLCSC_USER_PREFERENCE.Get_User_Preference_Info");
			cstmt = conn.prepareCall("{? = call OLCSC_USER_PREFERENCE."
					+ "Get_User_Preference_Info(?,?,?,?,?,?,?,?,?,?,?)}");

			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_PREF_INFO_REC", OlcPrefInfoRec.class);
			typeMap.put("OL_OWNER.OLC_DEVICE_VALUE_REC",
					OlcDeviceValueRec.class);
			typeMap.put("OL_OWNER.OLC_USER_PREF_VALUE_REC",
					OlcUserPrefValueRec.class);
			typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OlcAccountTagRec.class);
			typeMap.put("OL_OWNER.OLC_IOP_ACCT_INFO_REC",
					OlcIopAcctInfoRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setLong(idx++, accountLoginDTO.getAcctId());
			cstmt.setString(idx++, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
			cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
			cstmt.setString(idx++, accountLoginDTO.getLoginId());
			cstmt.setLong(idx++, EventEnum.OLCSCUSERPREF.getId());
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_PREF_INFO_ARR");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_IOP_ACCT_INFO_ARR");
			cstmt.registerOutParameter(idx++, Types.VARCHAR);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			byte success = cstmt.getByte(1);
			if (success == 1) {
				Collection<UserPreferenceDTO> userPrefs = convertUserPreferenceFromDB((Object[]) cstmt
						.getArray(idx - 4).getArray());
				userPrefResult.setUserPreferences(userPrefs);

				String message = cstmt.getString(idx - 1);
				if (message != null) {
					Collection<ErrorMessageDTO> msgs = new ArrayList<ErrorMessageDTO>();
					ErrorMessageDTO msg = new ErrorMessageDTO();
					msg.setMessage(message);
					msgs.add(msg);
					userPrefResult.setErrors(msgs);
				}
				Collection<AccountIopDTO> acctIops = new ArrayList<AccountIopDTO>();
				userPrefResult.setAccountIops(acctIops);
				Array vehicleArr = cstmt.getArray(idx - 3);
				if (vehicleArr != null) {
					Object[] vehicleDetailsArr = (Object[]) vehicleArr
							.getArray();
					if (vehicleDetailsArr != null
							&& vehicleDetailsArr.length > 0) {
						HashMap<Long, AccountIopDTO> accountTagIdToAccontIopMap = new HashMap<Long, AccountIopDTO>(
								vehicleDetailsArr.length);
						HashMap<String, AccountIopDTO> licensePlateToAccontIopMap = new HashMap<String, AccountIopDTO>(
								vehicleDetailsArr.length);
						for (int i = 0; i < vehicleDetailsArr.length; i++) {
							OlcAccountTagRec accountVehicleTag = (OlcAccountTagRec) vehicleDetailsArr[i];
							AccountIopDTO acctIop = new AccountIopDTO();

							acctIop.setAcctId(accountLoginDTO.getAcctId());
							acctIop.setAgencyId(accountVehicleTag.getAgencyId());
							acctIop.setTagId(Long.toString(accountVehicleTag
									.getAcctTagSeq().longValue()));
							acctIop.setTag(accountVehicleTag.getAgencyId()
									+ "." + accountVehicleTag.getTagId());
							acctIop.setAccountVehicleId(Long
									.toString(accountVehicleTag
											.getAccountVehicleId().longValue()));
							acctIop.setLicPlate(accountVehicleTag.getLicPlate());
							acctIop.setLicState(accountVehicleTag.getLicState());

							acctIops.add(acctIop);
							accountTagIdToAccontIopMap.put(accountVehicleTag
									.getAcctTagSeq().longValue(), acctIop);
							licensePlateToAccontIopMap.put(
									accountVehicleTag.getLicState()
											+ accountVehicleTag.getLicPlate(),
									acctIop);
						}

						Array tempArr = cstmt.getArray(idx - 2);
						if (tempArr != null) {
							Object[] tempAcctIops = (Object[]) tempArr
									.getArray();
							if (tempAcctIops != null) {

								for (int i = 0; i < tempAcctIops.length; i++) {

									OlcIopAcctInfoRec temp = (OlcIopAcctInfoRec) tempAcctIops[i];
									if (temp != null) {
										AccountIopDTO acctIop = null;
										if (StringUtils.isNotBlank(temp
												.getAccountTagId())) {
											acctIop = accountTagIdToAccontIopMap
													.get(Long.valueOf(temp
															.getAccountTagId()));
										} else {
											acctIop = licensePlateToAccontIopMap
													.get(temp.getLicState()
															+ temp.getLicPlate());
										}
										if (acctIop != null) {
											boolean tempUpdateable = Util
													.stringToBoolean(temp
															.getUpdateable());
											if (temp.getAgcyAbbrev().equals(
													Constants.IOP_DFW)) {
												acctIop.setAllowDfw(false);
												acctIop.setExistingAllowDfw(false);
												acctIop.setUpdateDfw(tempUpdateable);
												// acctIop.setReasonDescr(temp.getReasonCodeDescr());
											} else if (temp
													.getAgcyAbbrev()
													.equals(Constants.IOP_HCTRA)) {
												acctIop.setAllowHctra(false);
												acctIop.setExistingAllowHctra(false);
												acctIop.setUpdateHctra(tempUpdateable);
												// acctIop.setReasonDescr(temp.getReasonCodeDescr());
											} else if (temp.getAgcyAbbrev()
													.equals(Constants.IOP_LOVE)) {
												acctIop.setAllowLove(false);
												acctIop.setExistingAllowLove(false);
												acctIop.setUpdateLove(tempUpdateable);
												// acctIop.setReasonDescr(temp.getReasonCodeDescr());
											} else if (temp
													.getAgcyAbbrev()
													.equals(Constants.IOP_TXDOT)) {
												acctIop.setAllowTxDot(false);
												acctIop.setExistingAllowTxDot(false);
												acctIop.setUpdateTxDot(tempUpdateable);
												// acctIop.setReasonDescr(temp.getReasonCodeDescr());
											}
										}
									}
								}

							}
						}
					}
				}
			} else if (success == -1) {
				logger.error("Error in getUserPreference() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException(
						"Security Exception in getUserPreference");
			}

		} catch (SQLException sqle) {
			logger.error("Error in getUserPreference() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("errror in getUserPreference" + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getUserPreference() @ OracleAccountDAO");
		return userPrefResult;
	}// end of getUserPreference()

	public Collection<ErrorMessageDTO> updateUserPreference(
			AccountLoginDTO accountLoginDTO, AccountIopDTO[] acctIops,
			UserPreferenceDTO[] userPrefs) throws EtccException,
			EtccSecurityException {
		logger.info("Entering updateUserPreference(AccountLoginDTO accountLoginDTO, AccountIopDTO[] acctIops, UserPreferenceDTO[] userPrefs) @ OracleAccountDAO");
		logger.debug(new StringBuilder("updateUserPreference: acct=")
				.append(accountLoginDTO.getAcctId()));
		Collection<ErrorMessageDTO> result = null;
		// if user changes found for iop_blocks then call the
		// set_iop_account_info
		if (acctIops != null && acctIops.length > 0) {
			logger.debug("updateUserPreference calling set_iop_account_info: acct="
					+ accountLoginDTO.getAcctId());
			setConnection(Util.getDbConnection());
			try {
				logger.info("updateUserPreference(AccountLoginDTO accountLoginDTO, AccountIopDTO[] acctIops, UserPreferenceDTO[] userPrefs) :: Calling OLCSC_USER_PREFERENCE.set_iop_account_info");
				cstmt = conn.prepareCall("{? = call OLCSC_USER_PREFERENCE."
						+ "set_iop_account_info(?,?,?,?,?,?,?,?,'Y')}");

				Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
				typeMap.put("OL_OWNER.OLC_PREF_INFO_REC", OlcPrefInfoRec.class);
				typeMap.put("OL_OWNER.OLC_DEVICE_VALUE_REC",
						OlcDeviceValueRec.class);
				typeMap.put("OL_OWNER.OLC_USER_PREF_VALUE_REC",
						OlcUserPrefValueRec.class);
				typeMap.put("OL_OWNER.OLC_IOP_ACCT_INFO_REC",
						OlcIopAcctInfoRec.class);
				typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
				conn.setTypeMap(typeMap);

				ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
						"OL_OWNER.OLC_IOP_ACCT_INFO_ARR", conn);
				ARRAY array = new ARRAY(arraydesc, conn,
						convertAcctIopToDb(acctIops));
				byte idx = 1;
				cstmt.registerOutParameter(idx++, Types.SMALLINT);
				cstmt.setLong(idx++, accountLoginDTO.getAcctId());
				cstmt.setString(idx++, Constants.LOGIN_TYPE_ACCOUNT);
				cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
				cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
				cstmt.setString(idx++, accountLoginDTO.getLoginId());
				cstmt.setLong(idx++, EventEnum.SETIOPACCTINFO.getId());
				cstmt.setArray(idx, array);
				cstmt.registerOutParameter(idx++, Types.ARRAY,
						"OL_OWNER.OLC_IOP_ACCT_INFO_ARR");
				cstmt.registerOutParameter(idx, Types.ARRAY,
						"OL_OWNER.OLC_ERROR_MSG_ARR");

				cstmt.execute();
				byte success = cstmt.getByte(1);

				if (success == -1) {
					logger.error("Error in updateUserPreference() @ OracleAccountDAO :: Security exception");
					throw new EtccSecurityException(
							"Security Exception in updateUserPreference::set_iop_account_info");
				} else if (success == 0) {
					Array errors = (Array) cstmt.getObject(idx);
					result = Util.convertErrorMsgs(errors);
					if (result == null || result.size() == 0) {
						result = new ArrayList<ErrorMessageDTO>();
						ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
						errMsgDto
								.setMessage("Unable to save user preferences. Please try again.");
						result.add(errMsgDto);
					}
				}

			} catch (SQLException sqle) {
				logger.error(
						"Error in updateUserPreference(AccountLoginDTO accountLoginDTO, AccountIopDTO[] acctIops, UserPreferenceDTO[] userPrefs) @ OracleAccountDAO ",
						sqle);
				throw new EtccException(
						"errror in updateUserPreference" + sqle, sqle);
			} finally {
				closeConnection();
			}
		} else {
			logger.debug("updateUserPreference does not found any user IOP_BLOCK changes to call set_iop_account_info: acct="
					+ accountLoginDTO.getAcctId());
		}
		if (result == null && userPrefs != null && userPrefs.length > 0) {
			result = updateUserPreference(accountLoginDTO, userPrefs);
		}// end of try-catch-finally()
		logger.info("Leaving updateUserPreference(AccountLoginDTO accountLoginDTO, AccountIopDTO[] acctIops, UserPreferenceDTO[] userPrefs) @ OracleAccountDAO");
		return result;
	}// end of updateUserPreference()

	private Collection<ErrorMessageDTO> updateUserPreference(
			AccountLoginDTO accountLoginDTO, UserPreferenceDTO[] userPrefs)
			throws EtccException, EtccSecurityException {
		logger.info("Entering updateUserPreference(AccountLoginDTO accountLoginDTO, UserPreferenceDTO[] userPrefs) @ OracleAccountDAO");
		logger.debug(new StringBuilder("updateUserPreference: acct=")
				.append(accountLoginDTO.getAcctId()));
		Collection<ErrorMessageDTO> result = null;
		setConnection(Util.getDbConnection());
		try {
			logger.info("updateUserPreference() :: Calling OLCSC_USER_PREFERENCE.set_preference_info");
			cstmt = conn.prepareCall("{? = call OLCSC_USER_PREFERENCE."
					+ "set_preference_info(?,?,?,?,?,?,?,'Y',?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_PREF_INFO_SET_REC",
					OlcIopAcctInfoRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_PREF_INFO_SET_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertUserPrefToDb(userPrefs));
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setLong(idx++, accountLoginDTO.getAcctId());
			cstmt.setString(idx++, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
			cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
			cstmt.setString(idx++, accountLoginDTO.getLoginId());
			cstmt.setArray(idx++, array);
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setInt(idx++, 2222);
			cstmt.setInt(idx,getMethodIdForStatement(userPrefs));
			cstmt.execute();
			byte success = cstmt.getByte(1);
			if (success == -1) {
				throw new EtccSecurityException("Security Exception in "
						+ "updateUserPreference");
			} else if (success == 0) {
				Array errors = (Array) cstmt.getObject(idx - 1);
				result = Util.convertErrorMsgs(errors);
				if (result == null || result.size() == 0) {
					result = new ArrayList<ErrorMessageDTO>();
					ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
					errMsgDto.setMessage("Unable to save user preferences. "
							+ "Please try again.");
					result.add(errMsgDto);
				}
			}/*
			 * else if(success== 1){ Array userPrefsTemp =
			 * (Array)cstmt.getObject(idx-2); }
			 */

		} catch (SQLException sqle) {
			logger.error(
					"Error in updateUserPreference(AccountLoginDTO accountLoginDTO, UserPreferenceDTO[] userPrefs) @ OracleAccountDAO ",
					sqle);
			throw new EtccException("errror in updateUserPreference" + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving updateUserPreference(AccountLoginDTO accountLoginDTO, UserPreferenceDTO[] userPrefs) @ OracleAccountDAO");
		return result;
	}// end of updateUserPreference()

	
	private int getMethodIdForStatement(UserPreferenceDTO[] userPrefs)
	{
		logger.info("Entering getMethodIdForStatement() @ OracleAccountDAO");
			OlcPrefInfoSetRec[] result = null;
			String stmt_prefValue="";
			int  methodID=0;
			String notification_prefValue="";
			if (userPrefs != null) {
				for (int i = 0; i < userPrefs.length; i++) {
					if(userPrefs[i].getPrefId() == 4)
					{
						stmt_prefValue= userPrefs[i].getPrefValue();
					}	
					if("Y".equals(stmt_prefValue) && userPrefs[i].getPrefId() == 6)
					{
						notification_prefValue = userPrefs[i].getPrefValue();
						
						if(notification_prefValue.equals("Y"))
						{
							methodID=2;//for Mail statements
						}	
						else if(notification_prefValue.equals("N")) 
						{
							methodID=1; //for Email
						}
					}	
			}
			}	
			logger.info("Leaving getMethodIdForStatement() @ OracleAccountDAO");
			return methodID;
		
		
	}
	
	private Collection<UserPreferenceDTO> convertUserPreferenceFromDB(
			Object[] objArray) throws SQLException {
		logger.info("Entering convertUserPreferenceFromDB() @ OracleAccountDAO");
		Collection<UserPreferenceDTO> result = null;
		if (objArray != null && objArray.length > 0) {
			result = new ArrayList<UserPreferenceDTO>();
			for (int i = 0; i < objArray.length; i++) {
				UserPreferenceDTO userPrefDto = new UserPreferenceDTO();
				OlcPrefInfoRec temp = (OlcPrefInfoRec) objArray[i];
				userPrefDto.setPrefId(temp.getPrefId().longValue());
				userPrefDto.setDisplayDesc(temp.getDisplayDesc());
				userPrefDto.setPrefValue(temp.getPrefValue());
				userPrefDto.setPrefType(temp.getPrefType());
				userPrefDto.setDisplayOrder(temp.getDisplayOrder().intValue());
				if (temp.getSteId() != null) {
					userPrefDto.setSteId(temp.getSteId().longValue());
				}
				if (temp.getDeviceValue() != null) {
					Object[] deviceValues = (Object[]) temp.getDeviceValue()
							.getArray();

					if (deviceValues != null && deviceValues.length > 0) {
						AccountDeviceDTO[] deviceValueList = new AccountDeviceDTO[deviceValues.length];
						for (int j = 0; j < deviceValues.length; j++) {
							OlcDeviceValueRec tempDevice = (OlcDeviceValueRec) deviceValues[j];
							deviceValueList[j] = new AccountDeviceDTO();
							if (tempDevice.getAcctDeviceId() != null) {
								deviceValueList[j].setAcctDeviceId(tempDevice
										.getAcctDeviceId().longValue());
							}
							deviceValueList[j].setDefaultFlag(Util
									.stringToBoolean(tempDevice
											.getDefaultFlag()));
							deviceValueList[j].setDeviceValue(tempDevice
									.getDeviceValue());
						}
						userPrefDto.setDeviceValues(deviceValueList);
					}
				}
				if (temp.getUpValue() != null) {
					Object[] userPrefValues = (Object[]) temp.getUpValue()
							.getArray();
					if (userPrefValues != null && userPrefValues.length > 0) {
						UserPreferenceValueDTO[] userPrefValueList = new UserPreferenceValueDTO[userPrefValues.length];
						for (int j = 0; j < userPrefValues.length; j++) {
							OlcUserPrefValueRec tempUserPref = (OlcUserPrefValueRec) userPrefValues[j];
							userPrefValueList[j] = new UserPreferenceValueDTO();
							if (tempUserPref.getUserPrefId() != null) {
								userPrefValueList[j].setUserPrefId(tempUserPref
										.getUserPrefId().longValue());
							}
							userPrefValueList[j].setDefaultFlag(Util
									.stringToBoolean(tempUserPref
											.getDefaultFlag()));
							userPrefValueList[j].setUpValue(tempUserPref
									.getUpValue());
							userPrefValueList[j].setDisplayUpValue(tempUserPref
									.getDisplayUpValue());
						}
						userPrefDto.setUserPrefValues(userPrefValueList);
					}
				}
				result.add(userPrefDto);
			}
		}
		logger.info("Leaving convertUserPreferenceFromDB() @ OracleAccountDAO");
		return result;
	}// end of convertUserPreferenceFromDB()

	public TagUpdateResultDTO updateAccountTags(AccountLoginDTO acctLoginDto,
			TagDTO[] tags, boolean checkDuplicate) throws EtccException,
			EtccSecurityException {
		logger.info("Entering updateAccountTags() @ OracleAccountDAO");
		logger.debug(new StringBuilder("updateAccountTags: acct=")
				.append(acctLoginDto.getAcctId()));
		TagUpdateResultDTO tagResult = new TagUpdateResultDTO();
		setConnection(Util.getDbConnection());
		try {
			if (tags != null) {
				StringBuilder sb = new StringBuilder("updateAccountTags ");
				sb.append("tag list: acct=").append(acctLoginDto.getAcctId());
				for (int i = 0; i < tags.length; i++) {
					sb.append(tags[i]);
				}
				logger.debug(sb);
			}
			logger.info("updateAccountTags() :: Calling OLCSC_ACCT_MGMT.Account_Tags_Modify");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Account_Tags_Modify"
							+ "(?,?,?,?,?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OlcAccountTagRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn, convertTagProperties(tags));
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setString(idx++, Util.booleanToString(checkDuplicate));
			cstmt.setString(idx++, "U");
			cstmt.setArray(idx, array);
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
			cstmt.registerOutParameter(idx++, Types.VARCHAR);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			byte success = cstmt.getByte(1);

			if (success == 0) {
				tagResult.setErrors(Util.convertErrorMsgs(cstmt.getArray(idx)));

				boolean hasDuplicates = Util.stringToBoolean(cstmt
						.getString(idx - 1));
				if (hasDuplicates) {
					// copy duplicates to the tag array
					Object[] objArray = (Object[]) cstmt.getArray(idx - 2)
							.getArray();
					if (objArray != null && objArray.length > 0) {
						Collection result = new ArrayList();
						for (int i = 0; i < objArray.length; i++) {
							TagDTO tagDto = copyTagProperties((OlcAccountTagRec) objArray[i]);
							tagDto.setAcctId(acctLoginDto.getAcctId());
							result.add(tagDto);
						}
						tagResult.setDuplicateTags(result);
					}
				}
			} else if (success == -1) {
				logger.error("Error in updateAccountTags() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security Exception in "
						+ "updateAccountTags");
			} else if (success == 1) {
				tagResult.setSuccessful(true);
			}

		} catch (SQLException sqle) {
			logger.error("Error in updateAcccountTags() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("errror in updateAcccountTags" + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving updateAccountTags() @ OracleAccountDAO");
		return tagResult;
	}// end of updateAccountTags()

	public Collection getLastTransactions(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getLastTransactions() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getLastTransactions: acct=")
				.append(acctLoginDto.getAcctId()));
		Collection result = null;
		setConnection(Util.getDbConnection());
		try {
			logger.debug("getLastTransactions:acctLoginDto.getAcctId():"
					+ acctLoginDto.getAcctId());
			logger.debug("getLastTransactions:acctLoginDto.getLoginType():"
					+ acctLoginDto.getLoginType());
			logger.debug("getLastTransactions:acctLoginDto.getLoginId():"
					+ acctLoginDto.getLoginId());
			logger.debug("getLastTransactions:acctLoginDto.getDbSessionId():"
					+ acctLoginDto.getDbSessionId());
			logger.debug("getLastTransactions:acctLoginDto.getLastLoginIp():"
					+ acctLoginDto.getLastLoginIp());
			logger.info("getLastTransactions() :: Calling OLCSC_ACCT_MGMT.View_txns");
			cstmt = conn.prepareCall("{? = call OLCSC_ACCT_MGMT."
					+ "View_txns(?,?,?,?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_HISTORY_REC",
					OlcAccountHistoryRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setLong(2, acctLoginDto.getAcctId());
			cstmt.setString(3, acctLoginDto.getLoginType());
			cstmt.setString(4, acctLoginDto.getLoginId());
			cstmt.setString(5, acctLoginDto.getDbSessionId());
			cstmt.setString(6, acctLoginDto.getLastLoginIp());
			cstmt.setBigDecimal(7, new BigDecimal(0));
			cstmt.setString(8, "Y");
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
			cstmt.registerOutParameter(10, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setInt(11, 0);
			logger.debug("before excute");
			cstmt.execute();
			logger.debug("getLastTransactions:after excute");

			short success = cstmt.getShort(1);
			logger.debug("getLastTransactions:after success:" + success);
			if (success == 1) {
				logger.debug("getLastTransactions:after success:" + success);
				Object[] objArray = (Object[]) cstmt.getArray(9).getArray();
				if (objArray != null && objArray.length > 0) {
					result = new ArrayList();
					for (int i = 0; i < objArray.length; i++) {
						logger.debug("getLastTransactions in loop");
						TransactionDTO tranDto = copyTransactionProperties((OlcAccountHistoryRec) objArray[i]);
						if (tranDto != null) {
							result.add(tranDto);
						}
					}
				}
			} else if (success == -1) {
				logger.error("Error in getLastTransactions() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "getLastTransactions()");
			}
		} catch (SQLException sqle) {
			logger.error("Error in getLastTransactions() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("Error running getLastTransactions(): "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getLastTransactions() @ OracleAccountDAO");
		return result;
	}// end of getLastTransactions()

	public Collection getAlerts(AccountLoginDTO acctLoginDto, String pageContext)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getAlerts() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getAlerts: acct=").append(acctLoginDto
				.getAcctId()));
		Collection result = null;
		setConnection(Util.getDbConnection());
		try {
			logger.info("getAlerts() :: Calling OLCSC_ACCT_MGMT.Get_Alerts");
			cstmt = conn.prepareCall("{? = call OLCSC_ACCT_MGMT."
					+ "Get_Alerts(?, ?, ?, ?, ?, ?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_INFO_REC",
					OlcAccountInfoRec.class);
			typeMap.put("OL_OWNER.OLC_ACCT_ALERT_REC", OlcAcctAlertRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			logger.debug("param 2: " + acctLoginDto.getAcctId());
			cstmt.setLong(2, acctLoginDto.getAcctId());
			logger.debug("param 3: " + acctLoginDto.getLoginType());
			cstmt.setString(3, acctLoginDto.getLoginType());
			logger.debug("param 4: " + acctLoginDto.getDbSessionId());
			cstmt.setString(4, acctLoginDto.getDbSessionId());
			logger.info("param 5: " + acctLoginDto.getLastLoginIp());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			logger.debug("param 6: " + acctLoginDto.getLoginId());
			cstmt.setString(6, acctLoginDto.getLoginId());

			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ACCT_ALERT_ARR");
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.setInt(9, 0);
			cstmt.execute();
			List<CitationInfoRecDTO> citationInfoList = null;
			short success = cstmt.getShort(1);
			if (success == 1) {
				logger.debug("getAlerts: success:" + success);
				Object[] objArray = (Object[]) cstmt.getArray(7).getArray();
				logger.debug("getAlerts: success:objArray:" + objArray.length);

				String url_start;
				String url_end = "</a>";
				String overallAddress;
				if (objArray != null && objArray.length > 0) {
					result = new ArrayList();
					for (int i = 0; i < objArray.length; i++) {
						AlertDTO alertDto = new AlertDTO();

						BigDecimal invoiceId = ((OlcAcctAlertRec) objArray[i])
								.getInvoiceId();
						if (invoiceId != null) {
							alertDto.setInvoiceId(new Long(invoiceId
									.longValue()));
						}
						OlcCitationInfoArr infoCitationArr = (((OlcAcctAlertRec) objArray[i])
								.getInfoCitationItemsArr());

						if (infoCitationArr != null) {
							Object[] infoCitationArray = infoCitationArr
									.getArray();

							if (infoCitationArray != null
									&& infoCitationArray.length > 0) {
								citationInfoList = new ArrayList();
								for (int j = 0; j < infoCitationArray.length; j++) {
									CitationInfoRecDTO citationInfo = new CitationInfoRecDTO();
									citationInfo
											.setInvoiceId(new Long(
													(((OlcCitationInfoRec) infoCitationArray[j])
															.getInvoiceId())
															.longValue()));
									citationInfo
											.setCitationId(new Long(
													(((OlcCitationInfoRec) infoCitationArray[j])
															.getCitationId())
															.longValue()));
									citationInfo
											.setCourtId(new Long(
													(((OlcCitationInfoRec) infoCitationArray[j])
															.getCourtId())
															.longValue()));
									citationInfo
											.setCourtName(((OlcCitationInfoRec) infoCitationArray[j])
													.getCourtName());
									citationInfo
											.setPhoneNumber(((OlcCitationInfoRec) infoCitationArray[j])
													.getPhoneNumber());
									citationInfo
											.setJudge(((OlcCitationInfoRec) infoCitationArray[j])
													.getJudge());
									citationInfo
											.setAddress1(((OlcCitationInfoRec) infoCitationArray[j])
													.getAddress1());
									citationInfo
											.setAddress2(((OlcCitationInfoRec) infoCitationArray[j])
													.getAddress2());
									citationInfo
											.setCity(((OlcCitationInfoRec) infoCitationArray[j])
													.getCity());
									citationInfo
											.setState(((OlcCitationInfoRec) infoCitationArray[j])
													.getState());
									citationInfo
											.setZipCode(((OlcCitationInfoRec) infoCitationArray[j])
													.getZipCode());
									citationInfo
											.setPlus4(((OlcCitationInfoRec) infoCitationArray[j])
													.getPlus4());
									citationInfo
											.setCourtDate(((OlcCitationInfoRec) infoCitationArray[j])
													.getCourtDate());
									citationInfo.setOverallAddress(citationInfo
											.getAddress1()
											+ " "
											+ citationInfo.getAddress2());

									if (citationInfo.getAddress2() != null
											&& citationInfo.getAddress2()
													.length() > 1) {
										citationInfo
												.setOverallAddress(citationInfo
														.getAddress1()
														+ " "
														+ citationInfo
																.getAddress2());
									} else {
										citationInfo
												.setOverallAddress(citationInfo
														.getAddress1());
									}

									if (citationInfo.getPlus4() != null
											&& citationInfo.getPlus4().length() == 4) {
										citationInfo
												.setCityStateZip(citationInfo
														.getCity()
														+ ", "
														+ citationInfo
																.getState()
														+ " "
														+ citationInfo
																.getZipCode()
														+ "-"
														+ citationInfo
																.getPlus4());
									} else {
										citationInfo
												.setCityStateZip(citationInfo
														.getCity()
														+ ", "
														+ citationInfo
																.getState()
														+ " "
														+ citationInfo
																.getZipCode());
									}
									logger.debug("citationInfo.toString()"
											+ citationInfo.toString());
									citationInfoList.add(citationInfo);
								}
								alertDto.setInfoCitationItems(citationInfoList);
								url_start = "<a href=\"#\" onclick=\"window.showModalDialog('"
										+ pageContext
										+ "/showCitation.do?invoiceId="
										+ invoiceId
										+ "', 'resizable: yes', 'dialogHeight:200px', 'dialogWidth:1000px');\">";
								alertDto.setAlertMsg(url_start
										+ ((OlcAcctAlertRec) objArray[i])
												.getAlertMsg() + url_end);
							}
						} else {
							alertDto.setAlertMsg(((OlcAcctAlertRec) objArray[i])
									.getAlertMsg());
						}
						logger.debug("getAlerts: alert messages:"
								+ ((OlcAcctAlertRec) objArray[i]).getAlertMsg());
						result.add(alertDto);
					}
				} else {
					logger.debug("getAlerts: success:empty alerts");
					result = new ArrayList();
				}
			} else if (success == -1) {
				logger.debug("getAlerts: success:" + success);
				Object[] objArray = (Object[]) cstmt.getArray(8).getArray(
						conn.getTypeMap());

				if (objArray != null && objArray.length > 0) {
					String error = "";
					for (int i = 0; i < objArray.length; i++) {
						error = ((ErrorMsgRec) objArray[i]).getErrMsg();
						logger.debug("getAlerts: " + error);
					}
					logger.error("Error in getAlerts() @ OracleAccountDAO :: Security exception");
					throw new EtccSecurityException("Security exception in "
							+ "getAlerts:" + error);
				} else {
					logger.debug("getAlerts: in else no records");
					logger.error("Error in getAlerts() @ OracleAccountDAO :: Security exception");
					throw new EtccSecurityException(
							"EtccSecurityException  in " + "getAlerts:");
				}
			}// end of if-else()
		} catch (SQLException sqle) {
			logger.error("getAlerts: SQLException:" + sqle.getMessage());
			throw new EtccException("Error running getAlerts: " + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getAlerts() @ OracleAccountDAO");
		return result;
	}// end of getAlerts()

	public boolean isPaymentOwed(AccountLoginDTO acctLoginDto, long transId,
			TagDTO[] licensePlates) throws EtccException, EtccSecurityException {
		logger.info("Entering isPaymentOwed() @ OracleAccountDAO");
		logger.debug(new StringBuilder("isPaymentOwed: acct=")
				.append(acctLoginDto.getAcctId()).append(", transId=")
				.append(transId));
		boolean result = false;
		setConnection(Util.getDbConnection());
		try {
			logger.info("isPaymentOwed() :: Calling OLCSC_ACCT_MGMT.check_if_owes");
			cstmt = conn.prepareCall("{? = call OLCSC_ACCT_MGMT."
					+ "check_if_owes(?, ?, ?, ?, ?, ?, ?, ?)}");
			Map typeMap = new HashMap();
			conn.setTypeMap(typeMap);
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getLoginType());
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setLong(idx++, transId);
			cstmt.registerOutParameter(idx++, Types.VARCHAR);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 1) {
				result = Util.stringToBoolean(cstmt.getString(idx - 1));
			} else if (success == -1) {
				logger.error("Error in isPaymentOwed() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "isPaymentOwed");
			}
		} catch (SQLException sqle) {
			throw new EtccException("Error running isPaymentOwed: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving isPaymentOwed() @ OracleAccountDAO");
		return result;
	}// end of isPaymentOwed()

	/**
	 * Creates an AccountDTO based on the values given by the Oracle-generated
	 * class.
	 * 
	 * @param temp
	 * @return
	 */
	private AccountDTO copyAccountProperties(OlcAccountInfoRec temp) {
		logger.info("Entering copyAccountProperties() @ OracleAccountDAO");
		AccountDTO acctDto = null;
		try {
			if (temp != null) {
				acctDto = new AccountDTO();
				acctDto.setAddress1(temp.getAddress1());
				acctDto.setAddress2(temp.getAddress2());
				acctDto.setBalanceAmt(temp.getBalanceAmt().doubleValue());
				acctDto.setDepAmt(temp.getDepositAmt().doubleValue());
				acctDto.setBalLastUpdated(DateUtil.timestampToCalendar(temp
						.getBalLastUpdated()));
				acctDto.setCity(temp.getCity());
				acctDto.setCompanyName(temp.getCompanyName());
				acctDto.setDriverLicNbr(temp.getDriverLicNbr());
				acctDto.setDriverLicState(temp.getDriverLicState());
				acctDto.setDLCountry(temp.getDriverLicCountry());
				if (temp.getMsId() != null) {
					acctDto.setMsId(temp.getMsId().longValue());
				}
				acctDto.setEmailAddress(temp.getEmailAddress());
				acctDto.setFirstName(temp.getFirstName());
				acctDto.setHomePhoNbr(temp.getHomePhoNbr());
				acctDto.setLastName(temp.getLastName());
				acctDto.setMiddleInitial(temp.getMiddleInitial());
				acctDto.setPlus4(temp.getPlus4());
				acctDto.setState(temp.getState());
				acctDto.setWorkPhoExt(temp.getWorkPhoExt());
				acctDto.setWorkPhoNbr(temp.getWorkPhoNbr());
				acctDto.setZipCode(temp.getZipCode());
				acctDto.setMobilePhoNbr(temp.getMobilePhoNbr());
				// acctDto.setEmailAddress2(temp.getEmailAddress2());
				// acctDto.setEmailAddress3(temp.getEmailAddress3());
				acctDto.setAcctTypeCode(temp.getAcctTypeCode());
				acctDto.setAcctTypeDescr(temp.getAcctTypeDescr());
				acctDto.setExcessiveChargeBacks(temp.getExcessiveChargebacks());
			}
			logger.info("Leaving copyAccountProperties() @ OracleAccountDAO");
			return acctDto;
		} catch (Exception e) {
			logger.error("Error copying accountproperties: " + e.getMessage(),
					e);
			return null;
		}// end of try-catch()
	}// end of copyAccountProperties()

	/**
	 * Creates a TagDTO based on the values given by the Oracle-generated class.
	 * 
	 * @param temp
	 * @return
	 */
	private TagDTO copyTagProperties(OlcAccountTagRec temp) {
		logger.info("Entering copyTagProperties() @ OracleAccountDAO");
		TagDTO tagDto = null;
		try {
			if (temp != null) {
				tagDto = new TagDTO();
				tagDto.setAcctTagStatus(temp.getAcctTagStatus());
				tagDto.setAccountVehicleId(temp.getAccountVehicleId()
						.longValue());
				tagDto.setAgencyId(temp.getAgencyId());
				tagDto.setTagId(temp.getTagId());
				tagDto.setLicPlate(temp.getLicPlate());
				tagDto.setLicState(temp.getLicState());
				tagDto.setPlateTypeCode(temp.getLicPlateTypeCode());
				tagDto.setPlateTypeDesc(temp.getLicPlateTypeDesc());
				tagDto.setVehicleYear(temp.getVehicleYear());
				tagDto.setVehicleColor(temp.getVehicleColor());
				tagDto.setVehicleMake(temp.getVehicleMake());
				tagDto.setVehicleModel(temp.getVehicleModel());
				tagDto.setVehicleClassCode(temp.getVehicleClassCode());
				tagDto.setVehicleDescr(temp.getVehicleClassDesc());
				tagDto.setTagStatusDesc(temp.getTagStatusDesc());
				if (temp.getAcctTagSeq() != null) {
					tagDto.setAcctTagSeq(temp.getAcctTagSeq().longValue());
				}
				tagDto.setVehicleClassDesc(temp.getVehicleClassDesc());
				tagDto.setTemporaryLicPlate(Util.stringToBoolean(temp
						.getTempPlateFlag()));
				tagDto.setPlateExpirDate(DateUtil.timestampToCalendar(temp
						.getPlateExpirDate()));
				tagDto.setBarcodePrefix(temp.getBarcodePrefix());
				temp.setResponsibilityTypeCode(temp.getResponsibilityTypeCode());
				if ((temp.getResponsibilityTypeCode() != null)
						&& (temp.getResponsibilityTypeCode().equals("R"))) {
					tagDto.setRentalVehicle(true);
					// tagDto.setEffectiveDate(DateUtil.timestampToCalendar(temp.getEffectiveDate()));
					// tagDto.setExpirDate(DateUtil.timestampToCalendar(temp.getExpiryDate()));

				} else {
					tagDto.setRentalVehicle(false);
					// tagDto.setEffectiveDate(DateUtil.timestampToCalendar(null));
					// tagDto.setExpirDate(DateUtil.timestampToCalendar(null));
				}
				tagDto.setEffectiveDate(DateUtil.timestampToCalendar(temp
						.getEffectiveDate()));
				tagDto.setExpirDate(DateUtil.timestampToCalendar(temp
						.getExpiryDate()));
				tagDto.setVinNumber(temp.getVin());
			}
			logger.info("Leaving copyTagProperties() @ OracleAccountDAO");
			return tagDto;
		} catch (Exception e) {
			logger.error("Error copying tag properties: " + e.getMessage(), e);
			return null;
		}// end of try-catch()
	}// end of copyTagProperties()

	private TransactionDTO copyTransactionProperties(OlcAccountHistoryRec temp) {
		logger.info("Entering copyTransactionProperties() @ OracleAccountDAO");
		TransactionDTO tranDto = null;
		try {
			if (temp != null) {
				tranDto = new TransactionDTO();
				tranDto.setAmount(temp.getAmount().doubleValue());
				tranDto.setDirection(temp.getDirection());
				tranDto.setLane(temp.getLaneName());
				tranDto.setLicPlate(temp.getLicensePlate());
				tranDto.setLocation(temp.getLocationName());
				tranDto.setTagId(temp.getTagId());
				tranDto.setTransactionDate(DateUtil.timestampToCalendar(temp
						.getTrxnDate()));
				tranDto.setTransTypeDescr(temp.getTransType());
				tranDto.setLaneDescription(temp.getLaneFullName());
				tranDto.setSerialNum(temp.getSerialNum().intValue());
				tranDto.setPostedDate(DateUtil.timestampToCalendar(temp
						.getPostedDate()));
			}
			logger.info("Leaving copyTransactionProperties() @ OracleAccountDAO");
			return tranDto;
		} catch (Exception e) {
			logger.error(
					"Error copying transaction properties: " + e.getMessage(),
					e);
			return null;
		}// end of try-catch()
	}// end of copyTransactionProperties()

	private OlcAccountTagRec[] convertTagProperties(TagDTO[] tags)
			throws EtccException {
		logger.info("Entering convertTagProperties() @ OracleAccountDAO");
		try {
			OlcAccountTagRec[] result = null;
			if (tags != null) {
				result = new OlcAccountTagRec[tags.length];
				for (int i = 0; i < tags.length; i++) {
					OlcAccountTagRec tagDto = new OlcAccountTagRec();
					tagDto.setAcctTagStatus(tags[i].getAcctTagStatus());
					tagDto.setAgencyId(tags[i].getAgencyId());
					tagDto.setTagId(tags[i].getTagId());
					tagDto.setLicPlate(tags[i].getLicPlate());
					tagDto.setLicState(tags[i].getLicState());
					tagDto.setVehicleYear(tags[i].getVehicleYear());
					tagDto.setVehicleColor(tags[i].getVehicleColor());
					tagDto.setVehicleMake(tags[i].getVehicleMake());
					tagDto.setVehicleModel(tags[i].getVehicleModel());
					tagDto.setVehicleClassCode(tags[i].getVehicleClassCode());
					tagDto.setTagStatusDesc(tags[i].getTagStatusDesc());
					tagDto.setTempPlateFlag(Util.booleanToString(tags[i]
							.isTemporaryLicPlate()));
					tagDto.setPlateExpirDate(DateUtil
							.calendarToTimestamp(tags[i].getPlateExpirDate()));
					tagDto.setAcctTagSeq(new BigDecimal(tags[i].getAcctTagSeq()));

					result[i] = tagDto;
				}
			}
			logger.info("Leaving convertTagProperties() @ OracleAccountDAO");
			return result;
		} catch (Exception e) {
			logger.error("Error copying tag properties: " + e.getMessage(), e);
			throw new EtccException("Error copying tag properties: " + e);
		}// end of try-catch()
	}// end of convertTagProperties()

	private OlcIopAcctInfoRec[] convertAcctIopToDb(AccountIopDTO[] recs)
			throws EtccException {
		logger.info("Entering convertAcctIopToDb() @ OracleAccountDAO");
		try {
			OlcIopAcctInfoRec[] result = null;
			if (recs != null) {
				ArrayList<OlcIopAcctInfoRec> tempList = new ArrayList<OlcIopAcctInfoRec>(
						recs.length);
				for (int i = 0; i < recs.length; i++) {
					if (recs[i] != null) {
						OlcIopAcctInfoRec temp = null;
						if (recs[i].isUpdateHctra()
								&& recs[i].isAllowHctra() != recs[i]
										.isExistingAllowHctra()) {
							temp = createOlcIopAcctInfoRec(recs[i],
									Constants.IOP_HCTRA,
									Constants.IOP_HCTRA_AGCY_ID,
									recs[i].isAllowHctra() ? "U" : "B");
							tempList.add(temp);
						}
						if (recs[i].isUpdateTxDot()
								&& recs[i].isAllowTxDot() != recs[i]
										.isExistingAllowTxDot()) {
							temp = createOlcIopAcctInfoRec(recs[i],
									Constants.IOP_TXDOT,
									Constants.IOP_TXDOT_AGCY_ID,
									recs[i].isAllowTxDot() ? "U" : "B");
							tempList.add(temp);
						}
						if (recs[i].isUpdateDfw()
								&& recs[i].isAllowDfw() != recs[i]
										.isExistingAllowDfw()) {
							temp = createOlcIopAcctInfoRec(recs[i],
									Constants.IOP_DFW,
									Constants.IOP_DFW_AGCY_ID,
									recs[i].isAllowDfw() ? "U" : "B");
							tempList.add(temp);
						}
						if (recs[i].isUpdateLove()
								&& recs[i].isAllowLove() != recs[i]
										.isExistingAllowLove()) {
							temp = createOlcIopAcctInfoRec(recs[i],
									Constants.IOP_LOVE,
									Constants.IOP_LOVE_AGCY_ID,
									recs[i].isAllowLove() ? "U" : "B");
							tempList.add(temp);
						}
					}
				}
				result = tempList
						.toArray(new OlcIopAcctInfoRec[tempList.size()]);
			}
			logger.info("Leaving convertAcctIopToDb() @ OracleAccountDAO");
			return result;
		} catch (Exception e) {
			logger.error(
					"Error copying acct iop properties: " + e.getMessage(), e);
			throw new EtccException("Error copying acct iop properties: " + e,
					e);
		}// end of try-catch()
	}// end of convertAcctIopToDb()

	private OlcIopAcctInfoRec createOlcIopAcctInfoRec(
			AccountIopDTO accountIopDTO, String agencyAbbrev, int agcyId,
			String iopStatus) throws SQLException {
		logger.info("Entering createOlcIopAcctInfoRec() @ OracleAccountDAO");
		OlcIopAcctInfoRec temp = new OlcIopAcctInfoRec();
		temp.setAcctId(new BigDecimal(accountIopDTO.getAcctId()));
		temp.setAgencyId(accountIopDTO.getAgencyId());
		temp.setLicPlate(accountIopDTO.getLicPlate());
		temp.setLicState(accountIopDTO.getLicState());
		temp.setTag(accountIopDTO.getTag());
		temp.setAccountTagId(accountIopDTO.getTagId());
		temp.setAgcyAbbrev(agencyAbbrev);
		temp.setAgcyId(new BigDecimal(agcyId));
		temp.setIopStatus(iopStatus);
		logger.info("Leaving createOlcIopAcctInfoRec() @ OracleAccountDAO");
		return temp;
	}// end of createOlcIopAcctInfoRec()

	private OlcPrefInfoSetRec[] convertUserPrefToDb(UserPreferenceDTO[] recs)
			throws EtccException {
		logger.info("Entering convertUserPrefToDb() @ OracleAccountDAO");
		try {
			OlcPrefInfoSetRec[] result = null;
			if (recs != null) {
				result = new OlcPrefInfoSetRec[recs.length];
				Collection tempList = new ArrayList();
				for (int i = 0; i < recs.length; i++) {
					OlcPrefInfoSetRec temp = new OlcPrefInfoSetRec();
					temp.setPrefId(new BigDecimal(recs[i].getPrefId()));
					temp.setPrefType(recs[i].getPrefType());
					temp.setPrefValue(recs[i].getPrefValue());
					temp.setAcctDeviceId(new BigDecimal(recs[i]
							.getSelectedDeviceId()));
					if (recs[i].getSelecteduserPrefValue() != null) {
						temp.setUpValue(recs[i].getSelecteduserPrefValue());
					} else {
						temp.setUpValue(recs[i].getPrefValue());
					}
					tempList.add(temp);
				}
				result = (OlcPrefInfoSetRec[]) tempList.toArray(result);
			}
			logger.info("Leaving convertUserPrefToDb() @ OracleAccountDAO");
			return result;
		} catch (Exception e) {
			logger.error("Error in convertUserPrefToDb() @ OracleAccountDAO ",
					e);
			throw new EtccException("Error copying user pref properties: " + e,
					e);
		}// end of try-catch()
	}// end of convertUserPrefToDb()

	private Collection convertToAcctCardStatus(Object[] recs)
			throws EtccException {
		logger.info("Entering convertToAcctCardStatus() @ OracleAccountDAO");
		ArrayList result = null;
		try {

			if (recs != null) {
				result = new ArrayList();
				for (int i = 0; i < recs.length; i++) {
					AccountCardDTO temp = new AccountCardDTO();
					OlcAccountCardStatusRec rec = (OlcAccountCardStatusRec) recs[i];
					temp.setAccountInventoryId(rec.getAccountInventoryId()
							.toString());
					temp.setAcctCardStatus(rec.getAcctCardStatus());
					// temp.setCardId(rec.getCardId();
					temp.setCardIdStr(rec.getCardId());
					temp.setCardStatus(rec.getCardStatus().trim());
					logger.debug("rec.getAcctCardStatus()"
							+ rec.getAccountInventoryId().toString());
					temp.setAccountInventoryId(rec.getAccountInventoryId()
							.toString());
					result.add(temp);
				}

			}

		} catch (Exception e) {
			logger.error(
					"Error in convertToAcctCardStatus() @ OracleAccountDAO ", e);
			throw new EtccException(
					"Error copying account card status properties: " + e, e);
		}// end of try-catch()
		logger.info("Leaving convertToAcctCardStatus() @ OracleAccountDAO");
		return result;
	}// end of convertToAcctCardStatus()

	private String formatCardId(String cardId) {
		logger.info("Entering formatCardId(String cardId) @ OracleAccountDAO");
		int zeroPadding = 8;
		StringBuffer zeros = new StringBuffer();
		int padding = zeroPadding - cardId.length();
		if (padding <= 0)
			return cardId;
		for (int i = 0; i < padding; i++)
			zeros.append("0");
		logger.info("Leaving formatCardId(String cardId) @ OracleAccountDAO");
		return zeros.toString() + cardId;
	}// end of formatCardId()

	private String formatCardId(long cardId) {
		logger.info("Entering formatCardId(long cardId) @ OracleAccountDAO");
		int zeroPadding = 8;
		StringBuffer zeros = new StringBuffer();
		if (cardId == 0)
			return String.valueOf(cardId);

		String cardIdStr = String.valueOf(cardId);
		int padding = zeroPadding - cardIdStr.length();

		if (padding <= 0)
			return cardIdStr;

		for (int i = 0; i < padding; i++)
			zeros.append("0");
		logger.info("Leaving formatCardId(long cardId) @ OracleAccountDAO");
		return zeros.toString() + cardIdStr;
	}// end of formatCardId()

	private Collection convertToAcctCardRsnCode(Object[] recs)
			throws EtccException {
		logger.info("Entering convertToAcctCardRsnCode() @ OracleAccountDAO");
		ArrayList result = null;
		try {

			if (recs != null) {
				result = new ArrayList();
				for (int i = 0; i < recs.length; i++) {
					AccountCardDTO temp = new AccountCardDTO();
					OlcAcctCardRemovalRsnRec rec = (OlcAcctCardRemovalRsnRec) recs[i];
					temp.setAcctCardStatus(rec.getAcctCardStatus().trim());
					temp.setCardStatus(rec.getCardStatus().trim());

					result.add(temp);
				}

			}

		} catch (Exception e) {
			logger.error(
					"Error in convertToAcctCardRsnCode() @ OracleAccountDAO ",
					e);
			throw new EtccException(
					"Error copying account card status properties: " + e, e);
		}
		logger.info("Leaving convertToAcctCardRsnCode() @ OracleAccountDAO");
		return result;
	}// end of convertToAcctCardRsnCode()

	public boolean isBigAccount(long accountId) {
		logger.info("Entering isBigAccount() @ OracleAccountDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("isBigAccount() :: Calling OLCSC_REP.IS_BIG_ACCOUNT");
			cstmt = conn.prepareCall("{? = call OLCSC_REP.IS_BIG_ACCOUNT(?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, accountId);
			cstmt.execute();
			logger.info("Leaving isBigAccount() @ OracleAccountDAO");
			return cstmt.getInt(1) == 1;
		} catch (Exception e) {
			logger.error("Error in isBigAccount() @ OracleAccountDAO ", e);
		} finally {
			closeConnection();
		}
		logger.info("Leaving isBigAccount() @ OracleAccountDAO");
		return false;
	}// end of isBigAccount()

	public void resetEmailAddressValid(AccountLoginDTO acctLoginDto,
			String emailAddress) {
		logger.info("Entering resetEmailAddressValid() @ OracleAccountDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("resetEmailAddressValid() :: Calling OLCSC_ACCT_MGMT.Set_Email_Address_Valid");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Set_Email_Address_Valid(?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, "" + acctLoginDto.getAcctId());
			cstmt.setString(3, acctLoginDto.getLoginType());
			cstmt.setString(4, acctLoginDto.getDbSessionId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.setString(6, acctLoginDto.getLoginId());
			cstmt.setString(7, emailAddress);
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			if (cstmt.getInt(1) != 1) {
				Collection errors = Util.convertErrorMsgs(cstmt.getArray(8));
				Iterator iter = errors.iterator();
				while (iter.hasNext()) {
					logger.error(iter.next());
				}
			}
		} catch (Exception e) {
			logger.error(
					"Error in resetEmailAddressValid() @ OracleAccountDAO ", e);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving resetEmailAddressValid() @ OracleAccountDAO");
	}// end of resetEmailAddressValid()

	public Collection<AccountCardDTO> getAccountCardDetailInfo(
			AccountLoginDTO acctLoginDto, String locale) throws EtccException,
			EtccSecurityException {
		logger.info("Entering getAccountCardDetailInfo() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getAccountCardDetailInfo: acct=")
				.append(acctLoginDto.getAcctId()));
		Collection<AccountCardDTO> result = null;
		try {
			logger.debug("calling act_card_status");

			setConnection(Util.getDbConnection());
			logger.info("getAccountCardDetailInfo() :: Calling OLCSC_ACCT_MGMT.Get_Account_Card_Status");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Account_Card_Status"
							+ "(?,?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_CARD_STATUS_REC",
					OlcAccountCardStatusRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setLong(2, acctLoginDto.getAcctId());
			cstmt.setString(3, acctLoginDto.getDbSessionId());
			cstmt.setString(4, acctLoginDto.getLastLoginIp());
			cstmt.setString(5, acctLoginDto.getLoginId());
			cstmt.setString(6, locale);
			cstmt.setLong(7, EventEnum.ACCOUNTCARDS.getId());
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_CARD_STATUS_ARR");
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 1) {
				result = convertToAcctCardStatus((Object[]) cstmt.getArray(8)
						.getArray());
			} else if (success == -1) {
				logger.error("Error in getAccountCardDetailInfo() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "getAccountCardDetailInfo");
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in getAccountCardDetailInfo() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("Error running getAccountCardDetailInfo: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getAccountCardDetailInfo() @ OracleAccountDAO");
		return result;
	}// end of getAccountCardDetailInfo()

	public Collection getAccountReasonCodes(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getAccountReasonCodes() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getAccountReasonCodes: acct=")
				.append(acctLoginDto.getAcctId()));
		Collection result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getAccountReasonCodes() :: Calling OLCSC_ACCT_MGMT.Get_Remove_Acct_Card_Rsn_Code");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Remove_Acct_Card_Rsn_Code"
							+ "(?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCT_CARD_REMOVAL_RSN_REC",
					OlcAcctCardRemovalRsnRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setLong(2, acctLoginDto.getAcctId());
			cstmt.setString(3, acctLoginDto.getDbSessionId());
			cstmt.setString(4, acctLoginDto.getLastLoginIp());
			cstmt.setString(5, acctLoginDto.getLoginId());
			cstmt.registerOutParameter(6, Types.ARRAY,
					"OL_OWNER.OLC_ACCT_CARD_REMOVAL_RSN_ARR");
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setLong(8, EventEnum.ACCOUNTCARDS.getId());

			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 1) {
				result = convertToAcctCardRsnCode((Object[]) cstmt.getArray(6)
						.getArray());
			} else if (success == -1) {
				logger.error("Error in getAccountReasonCodes() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "getAccountReasonCodes");
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in getAccountReasonCodes() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("Error running getAccountReasonCodes: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getAccountReasonCodes() @ OracleAccountDAO");
		return result;

	}// end of getAccountReasonCodes()

	public Map<String, Integer> getAccountCardInfo(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getAccountCardInfo() @ OracleAccountDAO");
		logger.debug(new StringBuilder("getAccountCardInfo: acct=")
				.append(acctLoginDto.getAcctId()));
		Map<String, Integer> result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getAccountCardInfo() :: Calling OLCSC_ACCT_MGMT.Get_Account_Card_Info");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Account_Card_Info(?,?,?,?,?,?,?,?,null)}");

			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", OlcErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			logger.debug("Param 2 - Account ID:" + acctLoginDto.getAcctId());
			logger.debug("Param 3 - Login Type:" + acctLoginDto.getLoginType());
			logger.debug("Param 4 - DB Session ID:"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Param 5 - Login IP:" + acctLoginDto.getLastLoginIp());
			logger.debug("Param 6 - Login ID:" + acctLoginDto.getLoginId());

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, acctLoginDto.getAcctId());
			cstmt.setString(3, acctLoginDto.getLoginType());
			cstmt.setString(4, acctLoginDto.getDbSessionId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.setString(6, acctLoginDto.getLoginId());
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 1) {
				result = new HashMap<String, Integer>();
				int numOfCards = cstmt.getInt(7);
				int numOfRequest = cstmt.getInt(8);
				logger.debug("numOfCards" + numOfCards);
				logger.debug("numOfRequest" + numOfRequest);
				result.put("numOfCards", new Integer(numOfCards));
				result.put("numOfRequest", new Integer(numOfRequest));
			} else if (success == -1) {
				logger.error("Error in getAccountCardInfo() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "getAccountCardInfo");
			}
		} catch (SQLException sqle) {
			throw new EtccException(
					"Error running getAccountCardInfo: " + sqle, sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getAccountCardInfo() @ OracleAccountDAO");
		return result;
	} // end of getAccountCardInfo()

	public boolean saveAccountCardRequest(AccountLoginDTO acctLoginDto,
			int numOfUnits) throws EtccException, EtccSecurityException {
		logger.info("Entering saveAccountCardRequest() @ OracleAccountDAO");
		logger.debug(new StringBuilder("saveAccountCardRequest: acct=")
				.append(acctLoginDto.getAcctId()));

		boolean result = false;
		try {
			setConnection(Util.getDbConnection());
			logger.info("saveAccountCardRequest() :: Calling OLCSC_ACCT_MGMT.Request_Account_Card");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Request_Account_Card"
							+ "(?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, acctLoginDto.getAcctId());
			// cstmt.setString(3, acctLoginDto.getLoginType());
			cstmt.setString(3, acctLoginDto.getDbSessionId());
			cstmt.setString(4, acctLoginDto.getLastLoginIp());
			cstmt.setString(5, acctLoginDto.getLoginId());
			cstmt.setInt(6, numOfUnits);
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			short success = cstmt.getShort(1);
			if (success == 1) {
				result = true;
			} else if (success == -1) {
				logger.error("Error in saveAccountCardRequest() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "saveAccountCardRequest");
			} else if (success == 0) {
				logger.error("Error in saveAccountCardRequest() @ OracleAccountDAO");
				throw new EtccException("Exception occurred in "
						+ "saveAccountCardRequest");
			}
		} catch (SQLException sqle) {
			throw new EtccException("Error running saveAccountCardRequest: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving saveAccountCardRequest() @ OracleAccountDAO");
		return result;
	}// end of saveAccountCardRequest()

	public int removeAccountCard(AccountLoginDTO acctLoginDto,
			AccountCardDTO acctCardDto) throws EtccException,
			EtccSecurityException {
		logger.info("Entering removeAccountCard() @ OracleAccountDAO");
		logger.debug(new StringBuilder("removeAccountCard: acct=")
				.append(acctLoginDto.getAcctId()));
		short success = -1;
		try {
			logger.debug("Card Id: " + acctCardDto.getCardId());
			logger.debug("Account status Id: "
					+ acctCardDto.getAcctCardStatus());
			logger.debug("Card Inventory: "
					+ acctCardDto.getAccountInventoryId());

			setConnection(Util.getDbConnection());
			logger.info("removeAccountCard() :: Calling OLCSC_ACCT_MGMT.Remove_Account_Card");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Remove_Account_Card"
							+ "(?,?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setLong(2, acctLoginDto.getAcctId());
			cstmt.setString(3, acctCardDto.getAccountInventoryId());
			cstmt.setString(4, acctCardDto.getAcctCardStatus().trim());
			cstmt.setString(5, acctLoginDto.getDbSessionId());
			cstmt.setString(6, acctLoginDto.getLastLoginIp());
			cstmt.setString(7, acctLoginDto.getLoginId());
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setLong(9, EventEnum.ACCOUNTCARDS.getId());

			cstmt.execute();

			success = cstmt.getShort(1);
			if (success == 1) {
				// result = true;
			} else if (success == -1) {
				logger.error("Error in removeAccountCard() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "removeAccountCard");
			} else if (success == 0) {
				logger.error("Error in removeAccountCard() @ OracleAccountDAO");
				throw new EtccException("Exception in removeAccountCard");
			}

		} catch (SQLException sqle) {
			logger.error("Error in removeAccountCard() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("Error running removeAccountCard: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving removeAccountCard() @ OracleAccountDAO");
		return success;
	}// end of removeAccountCard()

	private OlcAcctSecQuestRec[] convertToOracleSecurityQuestions(
			List<SecurityQuestionDTO> listOfQuestionsnAnswers)
			throws SQLException {
		logger.info("Entering convertToOracleSecurityQuestions() @ OracleAccountDAO");
		if (listOfQuestionsnAnswers != null
				&& listOfQuestionsnAnswers.size() > 0) {
			OlcAcctSecQuestRec[] result = new OlcAcctSecQuestRec[listOfQuestionsnAnswers
					.size()];
			for (int i = 0; i < listOfQuestionsnAnswers.size(); i++) {
				result[i] = new OlcAcctSecQuestRec();
				result[i].setQuestionId(new BigDecimal(listOfQuestionsnAnswers
						.get(i).getQuestionId()));
				result[i].setSecurityQuestionAnswer(listOfQuestionsnAnswers
						.get(i).getAnswer());
				result[i].setSecurityQuestionOrder(new BigDecimal(
						listOfQuestionsnAnswers.get(i)
								.getSecurityQuestionOrder()));
			}
			logger.info("Leaving convertToOracleSecurityQuestions() @ OracleAccountDAO");
			return result;
		}
		return null;
	}// end of convertToOracleSecurityQuestions()

	public int isVerificationRequired(AccountLoginDTO acctLoginDto) {
		logger.info("Entering isVerificationRequired() @ OracleAccountDAO");
		logger.debug("isVerificationRequired:acctLoginDto.getDbSessionId():"
				+ acctLoginDto.getDbSessionId());
		logger.debug("isVerificationRequired:acctLoginDto.getAcctId():"
				+ acctLoginDto.getAcctId());
		logger.debug("isVerificationRequired:acctLoginDto.getLoginId():"
				+ acctLoginDto.getLoginId());
		logger.debug("isVerificationRequired:acctLoginDto.getLastLoginIp():"
				+ acctLoginDto.getLastLoginIp());
		try {
			setConnection(Util.getDbConnection());
			logger.info("isVerificationRequired() :: Calling OLCSC_ACCT_MGMT.ACCOUNT_INFO_VERIFY_REQUIRED");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.ACCOUNT_INFO_VERIFY_REQUIRED(?,?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, acctLoginDto.getDbSessionId());
			cstmt.setLong(3, acctLoginDto.getAcctId());
			// cstmt.setString(3, acctLoginDto.getLoginType());
			cstmt.setString(4, acctLoginDto.getLoginId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.registerOutParameter(6, Types.SMALLINT);
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setInt(8, 1);
			cstmt.execute();
			int success = cstmt.getShort(6);
			logger.info("Leaving isVerificationRequired() @ OracleAccountDAO");
			return success;
		} catch (Exception sqle) {
			logger.error(
					"Error in isVerificationRequired() @ OracleAccountDAO ",
					sqle);
		} finally {
			closeConnection();
		}
		return 0;
	}// end of isVerificationRequired()

	public int verifyAccount(AccountLoginDTO acctLoginDto) {
		logger.info("Entering verifyAccount() @ OracleAccountDAO");
		logger.debug("verifyAccount:acctLoginDto.getDbSessionId():"
				+ acctLoginDto.getDbSessionId());
		logger.debug("verifyAccount:acctLoginDto.getAcctId():"
				+ acctLoginDto.getAcctId());
		logger.debug("verifyAccount:acctLoginDto.getLoginId():"
				+ acctLoginDto.getLoginId());
		logger.debug("verifyAccount:acctLoginDto.getLastLoginIp():"
				+ acctLoginDto.getLastLoginIp());

		try {
			setConnection(Util.getDbConnection());
			logger.info("verifyAccount() :: Calling OLCSC_ACCT_MGMT.ACCOUNT_INFORMATION_VERIFIED");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.ACCOUNT_INFORMATION_VERIFIED"
							+ "(?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, acctLoginDto.getDbSessionId());
			cstmt.setLong(3, acctLoginDto.getAcctId());
			// cstmt.setString(3, acctLoginDto.getLoginType());
			cstmt.setString(4, acctLoginDto.getLoginId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.registerOutParameter(6, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setInt(7, 1);
			cstmt.execute();
			short success = cstmt.getShort(1);
			logger.info("Leaving verifyAccount() @ OracleAccountDAO");
			return success;
		} catch (Exception sqle) {
			logger.error("Error in verifyAccount() @ OracleAccountDAO ", sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving verifyAccount() @ OracleAccountDAO");
		return 0;
	}// end of verifyAccount()

	public int isEnoughtSecurityQuestions(AccountLoginDTO acctLoginDto) {
		logger.info("Entering isEnoughtSecurityQuestions() @ OracleAccountDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("isEnoughtSecurityQuestions() :: Calling OLCSC_ACCT_MGMT.SECURITY_QUESTION_UPDATE_REQ");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.SECURITY_QUESTION_UPDATE_REQ"
							+ "(?,?,?,?,?)}");
			Map typeMap = new HashMap();
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, acctLoginDto.getDbSessionId());
			cstmt.setLong(3, acctLoginDto.getAcctId());
			// cstmt.setString(3, acctLoginDto.getLoginType());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.setString(4, acctLoginDto.getLoginId());
			cstmt.registerOutParameter(6, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			short success = cstmt.getShort(1);
			logger.info("Leaving isEnoughtSecurityQuestions() @ OracleAccountDAO");
			return success;
		} catch (Exception sqle) {
			logger.error(
					"Error in isEnoughtSecurityQuestions() @ OracleAccountDAO ",
					sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving isEnoughtSecurityQuestions() @ OracleAccountDAO");
		return 0;
	}// end of isEnoughtSecurityQuestions()

	public String getIntialPrepaidBalanceDetails(AccountLoginDTO acctLoginDto) {
		logger.info("Entering getIntialPrepaidBalanceDetails() @ OracleAccountDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("getIntialPrepaidBalanceDetails() :: Calling OLCSC_ACCT_MGMT.get_initial_autocharge_amounts");
			cstmt = conn
					.prepareCall("{call OLCSC_ACCT_MGMT.get_initial_autocharge_amounts"
							+ "(?,?,?, ?,?,?, ?)}");

			logger.debug("getIntialPrepaidBalanceDetails: Calling OLCSC_ACCT_MGMT.get_initial_autocharge_amounts");

			Map typeMap = new HashMap();
			conn.setTypeMap(typeMap);

			logger.debug("param 1 = NULL");
			cstmt.setNull(1, Types.VARCHAR);

			logger.debug("param 2 = " + acctLoginDto.getDbSessionId());
			cstmt.setString(2, acctLoginDto.getDbSessionId());

			logger.debug("param 3 = " + acctLoginDto.getLastLoginIp());
			cstmt.setString(3, acctLoginDto.getLastLoginIp());

			logger.debug("param 4 = " + acctLoginDto.getLoginId());
			cstmt.setString(4, acctLoginDto.getLoginId());

			logger.debug("param 5 = " + acctLoginDto.getAcctId());
			cstmt.setBigDecimal(5, new BigDecimal(acctLoginDto.getAcctId()));

			cstmt.registerOutParameter(6, Types.VARCHAR);

			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			String result = cstmt.getString(6);
			if (result != null) {
				result = result.trim();
				if (result.startsWith(",")) {
					result = "0.00" + result;
				}
			}
			logger.debug("RESULT = " + result);
			logger.info("Leaving getIntialPrepaidBalanceDetails() @ OracleAccountDAO");
			return result;
		} catch (Exception sqle) {
			logger.error(
					"Error in getIntialPrepaidBalanceDetails() @ OracleAccountDAO ",
					sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getIntialPrepaidBalanceDetails() @ OracleAccountDAO");
		return "";
	}// end of getIntialPrepaidBalanceDetails()

	public List<PlanDetailBalances> getPlanDetailBalances(long planDetailId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getPlanDetailBalances() @ OracleAccountDAO");
		logger.debug("Inside getPlanDetailBalances() @ OracleAccountDAO :: Plan Detail Id ::-->"
				+ planDetailId);
		List<PlanDetailBalances> result = new ArrayList<PlanDetailBalances>();
		try {
			setConnection(Util.getDbConnection());
			logger.info("getPlanDetailBalances() :: Calling OLCSC_UTIL.get_plan_detail_balances");
			cstmt = conn
					.prepareCall("{? = call OLCSC_UTIL.get_plan_detail_balances(?,?,?)}");
			Map typeMap = new HashMap();
			PlanDetailBalances pb;
			OlcPlanDetailBalanceRec planDetailRec = null;
			Array planDetails = null;
			typeMap.put("OL_OWNER.OLC_PLAN_DETAIL_BALANCE_REC",
					OlcPlanDetailBalanceRec.class);
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, Long.toString(planDetailId));
			cstmt.registerOutParameter(3, Types.ARRAY,
					"OL_OWNER.OLC_PLAN_DETAIL_BALANCE_ARR");
			cstmt.registerOutParameter(4, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			short success = cstmt.getShort(1);
			if (success == 1) {
				planDetails = (Array) cstmt.getArray(7);
			} else if (success == -1) {
				throw new EtccSecurityException(
						"Security exception in getPlanDetailBalances()");
			}// end of if-else()
			Object array[] = (Object[]) planDetails.getArray();
			if (array != null) {
				for (int i = 0; i < array.length; i++) {
					planDetailRec = (OlcPlanDetailBalanceRec) array[i];
					pb = new PlanDetailBalances();
					pb.setPlanDetailBalanceId(planDetailRec
							.getPlanDetailBalanceId().longValue());
					pb.setPlanDetailId(planDetailRec.getPlanDetailId()
							.longValue());
					pb.setAccountBalanceTypeCode(planDetailRec
							.getAccountBalanceTypeCode());
					pb.setAmount(planDetailRec.getAmount().doubleValue());
					logger.debug("Inside getPlanDetailBalances() @ OracleAccountDAO :: Plan Detail Id ::-->"
							+ planDetailRec.getPlanDetailId().longValue());
					logger.debug("Inside getPlanDetailBalances() @ OracleAccountDAO :: Balance Type Code ::-->"
							+ planDetailRec.getAccountBalanceTypeCode());
					logger.debug("Inside getPlanDetailBalances() @ OracleAccountDAO :: Amount ::-->"
							+ planDetailRec.getAmount().doubleValue());
					pb.setMultiplier(planDetailRec.getMultiplier().intValue());
					result.add(pb);
				}// end of for (int i=0; i<array.length; i++)
			}// end of if (array!=null)
			logger.info("Leaving getPlanDetailBalances() @ OracleAccountDAO");
			return result;
		} catch (SQLException sqlEx) {
			logger.error(
					"Error in getPlanDetailBalances() @ OracleAccountDAO ",
					sqlEx);
			throw new EtccException("Error running getPlanDetailBalances: "
					+ sqlEx, sqlEx);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
	}// end of getPlanDetailBalances()

	// the utility method to the inventory sale price, with/with out an
	// activation date.

	public Double getItemPriceByInventoryTypeIdAndDate(
			AccountLoginDTO acctLoginDto, Long inventoryTypeId, Date date)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getItemPriceByInventoryTypeIdAndDate() @ OracleAccountDAO");
		try {
			setConnection(Util.getDbConnection());
			Long itemId = null;
			Double price = null;

			cstmt = conn
					.prepareCall("{? = call OLCSC_UTIL.Get_Tag_Inventory_Sale_Price(?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setLong(2, inventoryTypeId);
			cstmt.setNull(3, java.sql.Types.BIGINT);
			java.util.Date now = Calendar.getInstance().getTime();
			cstmt.setTimestamp(4, new Timestamp(now.getTime()));
			cstmt.setTimestamp(5, new Timestamp(now.getTime()));
			cstmt.registerOutParameter(6, Types.DOUBLE);
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			short success = cstmt.getShort(1);

			if (success == 1) {
				price = cstmt.getDouble(6);
			} else if (success == -1) {
				logger.error("Error in getItemPriceByInventoryTypeIdAndDate() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "getItemPriceByInventoryTypeIdAndDate");
			}
			// ArrayList<PlanDetailBalances> result =
			// getPlanDetailList(cstmt.getArray(6)); //need to develop utility
			// method to convert oracle
			// array to plan detail list.
			logger.info("Leaving getItemPriceByInventoryTypeIdAndDate() @ OracleAccountDAO");
			return price;
		} catch (Exception sqle) {
			logger.error(
					"Error in getItemPriceByInventoryTypeIdAndDate() @ OracleAccountDAO ",
					sqle);
			throw new EtccException(
					"Error running getItemPriceByInventoryTypeIdAndDate: "
							+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
	}// end of getItemPriceByInventoryTypeIdAndDate()

	public Double getTagSalePriceByItemType(AccountLoginDTO acctLoginDto,
			long itemTypeId, Date date) throws EtccException,
			EtccSecurityException {
		logger.info("Entering getTagSalePriceByItemType() @ OracleAccountDAO");
		try {
			setConnection(Util.getDbConnection());

			Long itemId = null;
			Double price = null;

			logger.info("getTagSalePriceByItemType() :: Calling OLCSC_UTIL.Get_Tag_Inventory_Sale_Price");
			cstmt = conn
					.prepareCall("{? = call OLCSC_UTIL.Get_Tag_Inventory_Sale_Price(?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.INTEGER);

			logger.debug("param 2= NULL");
			cstmt.setNull(2, Types.INTEGER);

			logger.debug("param 3= " + itemTypeId);
			cstmt.setLong(3, itemTypeId);

			Timestamp now = new Timestamp(Calendar.getInstance().getTime()
					.getTime());

			logger.debug("param 4= " + now);
			cstmt.setTimestamp(4, now);

			logger.debug("param 5= " + now);
			cstmt.setTimestamp(5, now);

			logger.debug("param 6= " + acctLoginDto.getAcctId());
			cstmt.setLong(6, acctLoginDto.getAcctId());

			cstmt.registerOutParameter(7, Types.DOUBLE);

			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			short success = cstmt.getShort(1);

			if (success == 1) {
				price = cstmt.getDouble(7);
				logger.debug("result = " + price);
			} else if (success == -1) {
				logger.error("Error in getTagSalePriceByItemType() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "getTagSalePriceByItemType");
			} else {
				price = 0.0;
				logger.debug("Error occured, returning 0");
			}
			logger.info("Leaving getTagSalePriceByItemType() @ OracleAccountDAO");
			return price;
		} catch (Exception sqle) {
			logger.error(
					"Error in getTagSalePriceByItemType() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("Error running getTagSalePriceByItemType: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
	}// end of getTagSalePriceByItemType()

	// Method for re-calculating the auto charge
	public String getAutoChargeAmounts(AccountLoginDTO acctLoginDto)
			throws Exception {
		logger.info("Entering getAutoChargeAmounts() @ OracleAccountDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("getAutoChargeAmounts() :: Calling OLCSC_ACCT_MGMT.calculate_autocharge");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.calculate_autocharge(?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, acctLoginDto.getDbSessionId());
			cstmt.setLong(3, acctLoginDto.getAcctId());
			// cstmt.setString(3, acctLoginDto.getLoginType());
			cstmt.setString(4, acctLoginDto.getLoginId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			String result = cstmt.getString(6);
			logger.info("Leaving getAutoChargeAmounts() @ OracleAccountDAO");
			return result;
		} catch (Exception sqle) {
			logger.error("Error in getAutoChargeAmounts() @ OracleAccountDAO ",
					sqle);
			throw new EtccException("Error running getAutoChargeAmounts: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
	}// end of getAutoChargeAmounts()

	public double getTagDepositAmout(AccountLoginDTO accountLoginDTO)
			throws Exception {
		logger.info("Entering getTagDepositAmout() @ OracleAccountDAO");
		try {
			setConnection(Util.getDbConnection());
			Long itemId = null;
			Double price = null;

			logger.info("getTagDepositAmout() :: Calling OLCSC_ACCT_MGMT.get_deposit_amount");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.get_deposit_amount(?,?,?, ?,?,?, ?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.DOUBLE);

			logger.debug("param 2= " + accountLoginDTO.getAcctId());
			cstmt.setLong(2, accountLoginDTO.getAcctId());

			logger.debug("param 3= AC");
			cstmt.setString(3, "AC");

			logger.debug("param 4= " + accountLoginDTO.getDbSessionId());
			cstmt.setString(4, accountLoginDTO.getDbSessionId());

			logger.debug("param 5= " + accountLoginDTO.getLastLoginIp());
			cstmt.setString(5, accountLoginDTO.getLastLoginIp());

			logger.debug("param 6= " + accountLoginDTO.getLoginId());
			cstmt.setString(6, accountLoginDTO.getLoginId());

			logger.debug("param 7= Y");
			cstmt.setString(7, "Y");

			logger.debug("param 8= " + EventEnum.OLCSC_GET_DEPOSIT_AMT.getId());
			cstmt.setLong(8, EventEnum.OLCSC_GET_DEPOSIT_AMT.getId());

			cstmt.registerOutParameter(9, Types.DOUBLE);

			cstmt.registerOutParameter(10, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			short success = cstmt.getShort(1);

			if (success == 1) {
				price = cstmt.getDouble(9);
				logger.debug("result = " + price);
			} else if (success == -1) {
				logger.error("Error in getTagDepositAmout() @ OracleAccountDAO :: Security exception");
				throw new EtccSecurityException("Security exception in "
						+ "getTagSalePriceByItemType");
			} else {
				price = 0.0;
				logger.debug("Error occured, setting price to 0");
			}
			logger.info("Leaving getTagDepositAmout() @ OracleAccountDAO");
			return price;
		} catch (Exception sqle) {
			logger.error("Error in getTagDepositAmout() @ OracleAccountDAO ",
					sqle);
			throw new EtccException(
					"Error running getTagDepositAmout: " + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
	}// end of getTagDepositAmout()

	public double getCancelFulfillmentTagDepositAmout(
			AccountLoginDTO accountLoginDTO, String fulfillmentID)
			throws Exception {
		logger.info("Entering getCancelFulfillmentTagDepositAmout() @ OracleAccountDAO");
		try {
			setConnection(Util.getDbConnection());

			Long itemId = null;
			Double price = null;

			logger.info("getCancelFulfillmentTagDepositAmout() :: Calling OLCSC_ACCT_MGMT.get_deposit_amount with fulfillment_id param");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.get_deposit_amount(?,?,?, ?,?,?, ?,?,?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.DOUBLE);

			logger.debug("param 2 P_ACCT_ID= " + accountLoginDTO.getAcctId());
			cstmt.setLong(2, accountLoginDTO.getAcctId());

			logger.debug("param 3 P_DOC_TYPE= AC");
			cstmt.setString(3, "AC");

			logger.debug("param 4 P_SESSION_ID= "
					+ accountLoginDTO.getDbSessionId());
			cstmt.setString(4, accountLoginDTO.getDbSessionId());

			logger.debug("param 5 P_IP_ADDRESS= "
					+ accountLoginDTO.getLastLoginIp());
			cstmt.setString(5, accountLoginDTO.getLastLoginIp());

			logger.debug("param 6 P_USER_ID= " + accountLoginDTO.getLoginId());
			cstmt.setString(6, accountLoginDTO.getLoginId());

			logger.debug("param 7 P_CALLED_FROM_GUI= Y");
			cstmt.setString(7, "Y");

			logger.debug("param 8 P_FULFILLMENT_ID= " + fulfillmentID);
			cstmt.setString(8, fulfillmentID);

			logger.debug("param 9 P_EVENT_ID= o"); // EventEnum.OLCSC_GET_DEPOSIT_AMT.getId());
			cstmt.setLong(9, 0);// EventEnum.OLCSC_GET_DEPOSIT_AMT.getId());

			cstmt.registerOutParameter(10, Types.DOUBLE);

			cstmt.registerOutParameter(11, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			short success = cstmt.getShort(1);

			if (success == 1) {
				price = cstmt.getDouble(10);
				logger.debug("result = " + price);
			} else if (success == -1) {
				logger.error("getCancelFulfillmentTagDepositAmout cought application exception (-1) from OLCSC_ACCT_MGMT.get_deposit_amount ");
				throw new EtccSecurityException("Security exception in "
						+ "getTagSalePriceByItemType");
			} else {
				price = 0.0;
				logger.debug("Error occured, setting price to 0");
			}
			logger.info("Leaving getCancelFulfillmentTagDepositAmout() @ OracleAccountDAO");
			return price;
		} catch (Exception sqle) {
			logger.error("getCancelFulfillmentTagDepositAmout cought SQL exception from OLCSC_ACCT_MGMT.get_deposit_amount ::"
					+ sqle.getMessage());
			throw new EtccException(
					"Error running getTagDepositAmout: " + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
	}// end of getCancelFulfillmentTagDepositAmout()

}// end of OracleAccountDAO Class
