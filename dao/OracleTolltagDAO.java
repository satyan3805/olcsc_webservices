package com.etcc.csc.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.DateUtil;
import com.etcc.csc.common.DelegateEnum;
import com.etcc.csc.common.DelegateFactory;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.StringUtil;
import com.etcc.csc.common.Util;
import com.etcc.csc.datatype.OlcMarketSourceRec;
import com.etcc.csc.datatype.PersonalInfoRequest;
import com.etcc.csc.datatype.PersonalInfoResponse;
import com.etcc.csc.dto.AccountCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountVehicleInfoDTO;
import com.etcc.csc.dto.AccountVehicleInfoHolderDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.enums.EventEnum;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OLCSC_ACCT_MGMT;
import com.etcc.csc.plsql.OlcAccountTagRec;
import com.etcc.csc.plsql.OlcAcctCardRemovalRsnRec;
import com.etcc.csc.plsql.OlcAcctSecQuestRec;
import com.etcc.csc.plsql.OlcErrorMsgArr;
import com.etcc.csc.plsql.OlcErrorMsgRec;
import com.etcc.csc.plsql.OlcPaymentInfoRec;
import com.etcc.csc.service.CountryInterface;

public class OracleTolltagDAO extends TolltagDAO {
	private static final String OLCSC_USER_NAME = "OLCSC_USER";
	private static final int OLCSC_USER_ID = 24057;
	private Logger logger = Logger.getLogger(OracleTolltagDAO.class);

	public OracleTolltagDAO() {
	}

	public AccountLoginDTO createAccount(String ipAddress, String sessionId,
			String browserType, String browserVersion, String os_type,
			String osVersion, String attribute1, String attribute2,
			String attributes3, String attribute4, String attribute5,
			String accountType, String firstName, String middleInitial,
			String lastName, String taxId, String email, String loginId,
			String password, List<SecurityQuestionDTO> listOfQuestionsnAnswers,
			String locale, String planCode, String eventId, String startDate,
			String endDate, String companyName)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering createAccount() @ OracleTolltagDAO");
		logger.debug("createAccount: Entered");
		logger.debug("createAccount: Inputs:ipAddress:" + ipAddress
				+ ",sessionId:" + sessionId + ", browserType:" + browserType
				+ ", browserVersion:" + browserVersion + ", os_type:" + os_type
				+ ", osVersion:" + osVersion + ", attribute1:" + attribute1
				+ ", attribute2:" + attribute2 + ",attributes3:" + attributes3
				+ ", attribute4:" + attribute4 + ", attribute5:" + attribute5
				+ ", accountType:" + accountType + ", firstName:" + firstName
				+ ", middleInitial:" + middleInitial + ", lastName:" + lastName
				+ ", os_type:" + os_type + ", taxId:" + taxId + ",  email:"
				+ email + ", loginId:" + loginId + ", password:" + password
				+ ",listOfQuestionsnAnswers:" + listOfQuestionsnAnswers.size()
				+ ", locale:" + locale + ",planCode:" + planCode + ", eventId:"
				+ eventId + ", startDate:" + startDate + ",endDate:" + endDate
				+ ",companyName:" + companyName);

		/*
		 * 
		 * 
		 * FUNCTION FUNCTION Account_Creation_Info(p_ip_address VARCHAR2,
		 * p_jsession_id VARCHAR2, p_browser_type VARCHAR2 DEFAULT NULL,
		 * p_browser_version VARCHAR2 DEFAULT NULL, p_os_type VARCHAR2 DEFAULT
		 * NULL, p_os_version VARCHAR2 DEFAULT NULL, p_user_env_attribute1
		 * VARCHAR2 DEFAULT NULL, p_user_env_attribute2 VARCHAR2 DEFAULT NULL,
		 * p_user_env_attribute3 VARCHAR2 DEFAULT NULL, p_user_env_attribute4
		 * VARCHAR2 DEFAULT NULL, p_user_env_attribute5 VARCHAR2 DEFAULT NULL,
		 * p_account_type Account_Types.Code%TYPE, -- Parul Kaushik 12/20/2011
		 * added account_type p_first_name VARCHAR2, p_middle_initial VARCHAR2,
		 * p_last_name VARCHAR2, p_tax_id VARCHAR2, -- Parul Kaushik 12/8/2011
		 * replace company_name with tax_id p_email_address VARCHAR2, p_user_id
		 * VARCHAR2, p_password VARCHAR2, p_sec_question OLC_ACCT_SEC_QUEST_ARR,
		 * p_lang_id LANGUAGES.LANGUAGE_CODE%TYPE DEFAULT 'EN', --added mla
		 * 05/10/2009 p_plan_code CHAR, o_acct_id IN OUT NUMBER, o_session_id
		 * OUT VARCHAR2, o_error_msg_arr OUT OLC_ERROR_MSG_ARR, p_event_id
		 * events.event_id%type default 0, p_start_date date default null,
		 * p_end_date )
		 */
		try {
			setConnection(Util.getDbConnection());
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCT_SEC_QUEST_REC",
					OlcAcctSecQuestRec.class);
			StringBuffer call = new StringBuffer();
			call.append("{? = call OLCSC_ACCT_MGMT.Account_creation_Info(");
			call.append("p_ip_address=>?,");
			call.append("p_jsession_id=>?,");
			call.append("p_browser_type=>?,");
			call.append("p_browser_version=>?,");
			call.append("p_os_type=>?,");
			call.append("p_os_version=>?,");
			call.append("p_user_env_attribute1=>?,");
			call.append("p_user_env_attribute2=>?,");
			call.append("p_user_env_attribute3=>?,");
			call.append("p_user_env_attribute4=>?,");
			call.append("p_user_env_attribute5=>?,");

			call.append("p_account_type=>?,");
			call.append("p_first_name=>?,");
			call.append("p_middle_initial=>?,");
			call.append("p_last_name=>?,");
			call.append("p_tax_id=>?,");
			call.append("p_email_address=>?,");
			call.append("p_user_id=>?,");
			call.append("p_password=>?,");

			call.append("p_sec_question =>?,");
			call.append("p_lang_id=>?,");
			call.append("p_plan_code=>?,");
			call.append("o_acct_id=>?,");
			call.append("o_session_id=>?,");
			call.append("o_error_msg_arr=>?,");

			call.append("o_min_tag=>?,");
			call.append("o_max_tag=>?,");
			call.append("o_min_vehicle=>?,");
			call.append("o_max_vehicle=>?,");
			call.append("o_plan_detail_id=>?,");

			call.append("p_event_id=>?,");
			call.append("p_start_date=>?,");
			call.append("p_end_date=>?,");
			call.append("p_company_name=>?");
			call.append(")}");

			logger.info("createAccount() :: Calling OLCSC_ACCT_MGMT.Account_creation_Info");
			cstmt = conn.prepareCall(call.toString());

			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_ACCT_SEC_QUEST_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertToOracleSecurityQuestions(listOfQuestionsnAnswers));

			cstmt.registerOutParameter(1, Types.SMALLINT);

			logger.debug("param 2= " + ipAddress);
			cstmt.setString(2, ipAddress);

			logger.debug("param 3= " + sessionId);
			cstmt.setString(3, sessionId);

			logger.debug("param 4= " + browserType);
			cstmt.setString(4, browserType);

			logger.debug("param 5= " + browserVersion);
			cstmt.setString(5, browserVersion);

			logger.debug("param 6= " + os_type);
			cstmt.setString(6, os_type);

			logger.debug("param 7= " + osVersion);
			cstmt.setString(7, osVersion);

			logger.debug("param 8= " + attribute1);
			cstmt.setString(8, attribute1);

			logger.debug("param 9= " + attribute2);
			cstmt.setString(9, attribute2);

			logger.debug("param 10= " + attributes3);
			cstmt.setString(10, attributes3);

			logger.debug("param 11= " + attributes3);
			cstmt.setString(11, attributes3);

			logger.debug("param 12= " + attribute5);
			cstmt.setString(12, attribute5);

			logger.debug("param 13= " + accountType);
			cstmt.setString(13, accountType);

			logger.debug("param 14= " + firstName);
			cstmt.setString(14, firstName);

			logger.debug("param 15= " + middleInitial);
			cstmt.setString(15, middleInitial);

			logger.debug("param 16= " + lastName);
			cstmt.setString(16, lastName);

			logger.debug("param 17= " + ipAddress);
			cstmt.setString(17, taxId);

			logger.debug("param 18= " + email);
			cstmt.setString(18, email);

			logger.debug("param 19= " + loginId);
			cstmt.setString(19, loginId);

			logger.debug("param 20= " + password);
			cstmt.setString(20, password);

			logger.debug("param 21= " + array);
			cstmt.setArray(21, array);

			logger.debug("param 22= " + locale);
			cstmt.setString(22, locale);

			logger.debug("param 23= " + planCode);
			cstmt.setString(23, planCode);

			cstmt.registerOutParameter(24, Types.BIGINT);
			cstmt.registerOutParameter(25, Types.VARCHAR);
			cstmt.registerOutParameter(26, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.registerOutParameter(27, Types.BIGINT);
			cstmt.registerOutParameter(28, Types.BIGINT);
			cstmt.registerOutParameter(29, Types.BIGINT);
			cstmt.registerOutParameter(30, Types.BIGINT);
			cstmt.registerOutParameter(31, Types.BIGINT);

			logger.debug("param 32= " + eventId);
			cstmt.setString(32, eventId);

			logger.debug("param 33= " + startDate);
			cstmt.setDate(33, convertToDate(startDate));

			logger.debug("param 34= " + endDate);
			cstmt.setDate(34, convertToDate(endDate));

			logger.debug("param 35= " + companyName);
			cstmt.setString(35, companyName);

			cstmt.execute();

			int result = cstmt.getInt(1);
			logger.debug("createAccount:result:" + result);
			AccountLoginDTO accountLoginDTO = null;

			if (result == 1) {
				logger.debug("Inside if (result == 1) @ createAccount()");
				accountLoginDTO = new AccountLoginDTO();
				accountLoginDTO.setAcctId(cstmt.getLong(24));
				logger.debug("createAccount:account id:"
						+ accountLoginDTO.getAcctId());
				accountLoginDTO.setDbSessionId(cstmt.getString(25));
				accountLoginDTO.setLastLoginIp(ipAddress);
				accountLoginDTO.setLoginId(loginId);

				Long minTag = (Long) cstmt.getObject(27);
				Long maxTag = (Long) cstmt.getObject(28);
				Long minVehicle = (Long) cstmt.getObject(29);
				Long maxVehicle = (Long) cstmt.getObject(30);
				Long planDetailId = cstmt.getLong(31);

				accountLoginDTO.setPlanDetailId(planDetailId);

				accountLoginDTO.setMinTag(minTag == null ? -100 : minTag
						.longValue());
				accountLoginDTO.setMaxTag(maxTag == null ? -100 : maxTag
						.longValue());
				accountLoginDTO.setMinVehicle(minVehicle == null ? -100
						: minVehicle.longValue());
				accountLoginDTO.setMaxVehicle(maxVehicle == null ? -100
						: maxVehicle.longValue());

				logger.debug("minTag = " + accountLoginDTO.getMinTag()
						+ ",maxTag = " + accountLoginDTO.getMaxTag()
						+ ",minVehicle=" + accountLoginDTO.getMinVehicle()
						+ ",maxVehicle=" + accountLoginDTO.getMaxVehicle()
						+ ",planDetailId=" + accountLoginDTO.getPlanDetailId());
			} else {
				logger.debug("Inside else of if (result == 1) @ createAccount()");
				Object[] objArray = (Object[]) cstmt.getArray(26).getArray(
						conn.getTypeMap());

				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0) @ createAccount()");
					EtccErrorMessageException em = new EtccErrorMessageException(
							"TolltagWS::Database generated error message");
					logger.error("Error in createAccount() @ OracleTolltagDAO :: olltagWS::Database generated error message");
					for (int i = 0; i < objArray.length; i++) {
						em.addRecoverable(((ErrorMsgRec) objArray[i])
								.getErrMsg());
						String errorMsgRec = ((ErrorMsgRec) objArray[i])
								.getErrMsg();
						logger.error("createAccount:objArray[i]).getErrMsg():"
								+ errorMsgRec);
					}
					throw em;
				} else {
					logger.debug("Inside else of if (objArray != null && objArray.length > 0) @ createAccount()");
					logger.error("Error in createAccount() @ OracleTolltagDAO :: createAccount:before throwig fatal error:");
					throw new EtccException("Fatal error in createAccount.");
				}
			}
			logger.info("Leaving createAccount() @ OracleTolltagDAO");
			return accountLoginDTO;
		} finally {
			closeConnection();
		}
	}

	/*
	 * public PersonalInfoResponse setPersonalInfo(long accountId, String
	 * address, String addressLine2, String city, String state, String zipcode,
	 * String zipPlus4, String homePhoneNumber, String workPhoneNumber, String
	 * workPhoneExt, String dlNumber, String dlState, String monthlyStmtFlag,
	 * int msId, String dbSessionId, String loginId, String ipAddress, Long
	 * transactionId, boolean checkDuplicateDriverLicense, Long posId) throws
	 * EtccErrorMessageException, Exception {
	 */

	public PersonalInfoResponse setPersonalInfo(PersonalInfoRequest pir)
			throws Exception {
		logger.info("Entering setPersonalInfo() @ OracleTolltagDAO");
		PersonalInfoResponse response = null;
		logger.debug("setPersonalInfo: Entered");
		logger.debug("setPersonalInfo: Inputs:p_acct_id:" + pir.getAccountId()
				+ ",p_doc_type:" + pir.getLoginType() + ", p_session_id:"
				+ pir.getDbSessionId() + ", p_ip_address:" + pir.getIpAddress()
				+ ", p_user_id:" + pir.getLoginId() + ", p_address1:"
				+ pir.getAddress() + ", p_address2:" + pir.getAddressLine2()
				+ ", p_city:" + pir.getCity() + ",p_state:" + pir.getState()
				+ ", p_zip_code:" + pir.getZipcode() + ", p_plus4:"
				+ pir.getZipPlus4() + ", p_home_pho_nbr:"
				+ pir.getHomePhoneNumber() + ",  p_work_pho_nbr:"
				+ pir.getWorkPhoneNumber() + ", p_work_pho_ext:"
				+ pir.getWorkPhoneExt() + ", p_driver_lic_nbr:"
				+ pir.getDlNumber() + ",p_driver_lic_state:" + pir.getDlState()
				+ ", p_mo_stmt_flag:" + pir.getMonthlyStmtFlag() + ",p_ms_id:"
				+ pir.getMsId() + ", o_dupe_dl_flag:"
				+ (pir.isCheckDuplicateDriverLicense() ? "Y" : "N")
				+ ", p_event_id:" + pir.getEventId() + ", p_driver_lic_cnty:"
				+ pir.getDlCountry());

		/*
		 * FUNCTION Personal_Info(p_acct_id NUMBER, p_doc_type VARCHAR2 DEFAULT
		 * 'AC', p_session_id VARCHAR2, p_ip_address VARCHAR2, p_user_id
		 * VARCHAR2, p_address1 VARCHAR2, p_address2 VARCHAR2 DEFAULT NULL,
		 * p_city VARCHAR2, p_state VARCHAR2, p_zip_code VARCHAR2, p_plus4
		 * VARCHAR2 DEFAULT NULL, p_home_pho_nbr VARCHAR2, p_work_pho_nbr
		 * VARCHAR2 DEFAULT NULL, p_work_pho_ext VARCHAR2 DEFAULT NULL,
		 * p_driver_lic_nbr VARCHAR2, p_driver_lic_state VARCHAR2,
		 * p_mo_stmt_flag VARCHAR2 DEFAULT 'N', p_ms_id NUMBER, o_dupe_dl_flag
		 * IN OUT VARCHAR2, o_error_msg_arr OUT OLC_ERROR_MSG_ARR, p_event_id
		 * events.event_id%type default 0)
		 */

		try {
			setConnection(Util.getDbConnection());
			StringBuffer call = new StringBuffer();
			call.append("{? = call OLCSC_ACCT_MGMT.personal_info(");
			call.append("p_acct_id=>?,");
			call.append("p_doc_type=>?,");
			call.append("p_session_id=>?,");
			call.append("p_ip_address=>?,");
			call.append("p_user_id=>?, ");
			call.append("p_address1=>?, ");
			call.append("p_address2=>?, ");
			call.append("p_city=>?, ");
			call.append("p_state=>?, ");
			call.append("p_zip_code=>?, ");
			call.append("p_plus4=>?, ");
			call.append("p_home_pho_nbr=>?, ");
			call.append("p_work_pho_nbr=>?, ");
			call.append("p_work_pho_ext=>?, ");
			call.append("p_driver_lic_nbr=>?, ");
			call.append("p_driver_lic_state=>?, ");
			call.append("p_mo_stmt_flag=>?, ");
			call.append("p_ms_id=>?, ");
			call.append("o_dupe_dl_flag=>?, ");// 20
			call.append("o_error_msg_arr=>?, ");// 21
			call.append("p_event_id=>?, ");
			call.append("p_driver_lic_cnty=>?");
			call.append(")}");

			logger.info("setPersonalInfo() :: Calling OLCSC_ACCT_MGMT.personal_info");
			cstmt = conn.prepareCall(call.toString());

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, pir.getAccountId());
			cstmt.setString(3, pir.getLoginType());
			cstmt.setString(4, pir.getDbSessionId());
			cstmt.setString(5, pir.getIpAddress());
			cstmt.setString(6, pir.getLoginId());
			cstmt.setString(7, pir.getAddress());
			cstmt.setString(8, pir.getAddressLine2());
			cstmt.setString(9, pir.getCity());
			cstmt.setString(10, pir.getState());
			cstmt.setString(11, pir.getZipcode());
			cstmt.setString(12, pir.getZipPlus4());
			cstmt.setString(13, pir.getHomePhoneNumber());
			cstmt.setString(14, pir.getWorkPhoneNumber());
			cstmt.setString(15, pir.getWorkPhoneExt());
			cstmt.setString(16, pir.getDlNumber());
			cstmt.setString(17, pir.getDlState());
			cstmt.setString(18, pir.getMonthlyStmtFlag());
			cstmt.setInt(19, pir.getMsId());
			cstmt.registerOutParameter(20, Types.VARCHAR);// inout
			cstmt.setString(20, pir.isCheckDuplicateDriverLicense() ? "Y" : "N");// inout
			cstmt.registerOutParameter(21, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setInt(22, 0);
			cstmt.setString(23, pir.getDlCountry());
			cstmt.execute();

			int result = cstmt.getInt(1);
			logger.debug("setPersonalInfo: result:" + result);

			if (result == 1) {
				logger.debug("Inside if (result == 1) @ setPersonalInfo()");
				response = new PersonalInfoResponse();
				// dl exist on another account
				response.setDuplicateDriverLicense("Y".equals(cstmt
						.getString(20)));

			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ setPersonalInfo()");
				Object[] objArray = (Object[]) cstmt.getArray(21).getArray(
						conn.getTypeMap());

				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0)) @ setPersonalInfo()");
					EtccErrorMessageException em = new EtccErrorMessageException(
							"TolltagWS::Database generated error message");
					for (int i = 0; i < objArray.length; i++) {
						em.addRecoverable(((ErrorMsgRec) objArray[i])
								.getErrMsg());
						logger.error("setPersonalInfo: ((ErrorMsgRec)objArray[i]).getErrMsg():"
								+ ((ErrorMsgRec) objArray[i]).getErrMsg());
					}
					logger.error("Error in setPersonalInfo() @ OracleTolltagDAO :: TolltagWS::Database generated error message");
					throw em;
				} else {
					logger.debug("Inside else of if (objArray != null && objArray.length > 0)) @ setPersonalInfo()");
					logger.error("Error in setPersonalInfo() @ OracleTolltagDAO :: setPersonalInfo: EtccException:"
							+ result);
					throw new EtccException("Fatal error in setPersonalInfo.");
				}
			} else {
				logger.debug("Inside else of if (result == 1) @ setPersonalInfo()");
				logger.error("Error in setPersonalInfo() @ OracleTolltagDAO :: setPersonalInfo: EtccException last else:"
						+ result);
				throw new EtccException("Fatal error in setPersonalInfo.");
			}

		} finally {
			closeConnection();
		}
		logger.info("Leaving setPersonalInfo() @ OracleTolltagDAO");
		return response;
	}

	public void setPaymentInfo(long acctId, String dbSessionId,
			String ipAddress, String loginId, long personId, String cardNumber,
			long cardType, String token, Calendar expirationDate, long eventId)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering setPaymentInfo() @ OracleTolltagDAO");
		try {
			setConnection(Util.getDbConnection());
			StringBuffer call = new StringBuffer();
			call.append("{? = call OLCSC_ACCT_MGMT.set_payment_info(");
			call.append("p_acct_id=>?,");
			call.append("p_doc_type=>?, ");
			call.append("p_session_id=>?, ");
			call.append("p_ip_address=>?, ");
			call.append("p_user_id=>?, ");
			call.append("p_ui_call=>?, ");
			call.append("p_default_Blng_Methd=>?, ");
			call.append("P_event_id=>?, ");
			call.append("p_payment_info=>?, ");
			call.append("o_error_msg_arr=>?");
			call.append(")}");

			logger.info("setPaymentInfo() :: Calling OLCSC_ACCT_MGMT.set_payment_info");
			cstmt = conn.prepareCall(call.toString());

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			OlcPaymentInfoRec temp = new OlcPaymentInfoRec();
			temp.setPmtType("3");
			temp.setPersonId(new BigDecimal(personId));
			temp.setCardNbr(cardNumber);
			temp.setCardExpires(new Timestamp(expirationDate.getTimeInMillis()));
			temp.setCardType(new BigDecimal(cardType));
			temp.setToken(new BigDecimal(token));

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, acctId);
			cstmt.setString(3, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(4, dbSessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, loginId);
			cstmt.setBoolean(7, true);
			cstmt.setString(8, "Y");
			cstmt.setLong(9, eventId);
			cstmt.setObject(10, temp);
			cstmt.registerOutParameter(11, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			int result = cstmt.getInt(1);

			if (result == 1) {
				logger.debug("Inside if (result == 1) @ setPaymentInfo()");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ setPaymentInfo()");
				Object[] objArray = (Object[]) cstmt.getArray(11).getArray(
						conn.getTypeMap());

				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0) @ setPaymentInfo()");
					EtccErrorMessageException em = new EtccErrorMessageException(
							"TolltagWS::Database generated error message");
					for (int i = 0; i < objArray.length; i++) {
						em.addRecoverable(((ErrorMsgRec) objArray[i])
								.getErrMsg());
					}
					logger.error("Error in setPaymentInfo() @ OracleTolltagDAO :: TolltagWS::Database generated error message");
					throw em;
				} else {
					logger.debug("Inside else of if (objArray != null && objArray.length > 0) @ setPaymentInfo()");
					logger.error("Error in setPaymentInfo() @ OracleTolltagDAO :: Fatal error in setPersonalInfo.");
					throw new EtccException("Fatal error in setPersonalInfo.");
				}
			} else {
				logger.debug("Inside else of if (result == 1) @ setPaymentInfo()");
				throw new EtccException("Fatal error in setPersonalInfo.");
			}
		} finally {
			closeConnection();
		}
		logger.info("Leaving setPaymentInfo() @ OracleTolltagDAO");
	}

	public OlcMarketSourceRec[] getMarketSource(String lang) throws Exception {
		logger.info("Entering getMarketSource() @ OracleTolltagDAO");
		OlcMarketSourceRec[] olcMarketSourceRecs = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getMarketSource() :: Calling olcsc_util.get_mark_source");
			cstmt = conn
					.prepareCall("{? = call olcsc_util.get_mark_source(? ,? ,?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			typeMap.put("OL_OWNER.OLC_MARKET_SOURCE_REC",
					OlcMarketSourceRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, "EN");
			cstmt.setString(3, lang);
			cstmt.registerOutParameter(4, Types.ARRAY,
					"OL_OWNER.OLC_MARKET_SOURCE_ARR");
			cstmt.registerOutParameter(5, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			if (cstmt.getInt(1) == 1) {
				logger.debug("Inside if (cstmt.getInt(1) == 1) @ getMarketSource()");
				Object[] objArray = (Object[]) cstmt.getArray(4).getArray(
						conn.getTypeMap());
				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0) @ getMarketSource()");
					olcMarketSourceRecs = new OlcMarketSourceRec[objArray.length];
					for (int i = 0; i < objArray.length; i++) {
						olcMarketSourceRecs[i] = (OlcMarketSourceRec) objArray[i];
					}
				}
				logger.info("Leaving getMarketSource() @ OracleTolltagDAO");
				return olcMarketSourceRecs;
			} else {
				logger.debug("Inside else of if (cstmt.getInt(1) == 1) @ getMarketSource()");
				logger.error("Error in getMarketSource() @ OracleTolltagDAO :: Fatal error in getMarketSource.");
				throw new EtccException("Fatal error in getMarketSource");
			}
		} finally {
			closeConnection();
		}
	}

	public double getTagPayInfo(long accountId, String dbSessionId,
			String loginId, String ipAddress, long transactionId, Long posId)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering getTagPayInfo() @ OracleTolltagDAO");
		try {
			setConnection(Util.getDbConnection());
			StringBuffer call = new StringBuffer();
			call.append("{? = call OLCSC_ACCT_MGMT.GET_TAGSTORE_PMT_AMT(");
			call.append("p_doc_id=>?,");
			call.append("p_session_id=>?, ");
			call.append("p_ip_address=>?, ");
			call.append("p_user_id=>?, ");
			call.append("p_pos_id=>?, ");// 6
			call.append("p_rtl_trxn_id=>?, ");// 7
			call.append("o_pmt_amt=>?, ");// 8
			call.append("o_dep_amt=>?, ");// 9
			call.append("o_error_msg_arr=>?");// 10
			call.append(")}");

			logger.info("getTagPayInfo() :: Calling OLCSC_ACCT_MGMT.GET_TAGSTORE_PMT_AMT");
			cstmt = conn.prepareCall(call.toString());

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, accountId);
			cstmt.setString(3, dbSessionId);
			cstmt.setString(4, ipAddress);
			cstmt.setString(5, loginId);
			cstmt.setLong(6, (posId == null ? -1 : posId.longValue()));
			cstmt.setLong(7, transactionId);
			cstmt.registerOutParameter(8, Types.DOUBLE);
			cstmt.registerOutParameter(9, Types.DOUBLE);
			cstmt.registerOutParameter(10, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			int result = cstmt.getInt(1);

			if (result == 1) {
				logger.debug("Inside if (result == 1) @ getTagPayInfo()");
				logger.info("Leaving getTagPayInfo() @ OracleTolltagDAO");
				return cstmt.getLong(8);
			} else {
				logger.debug("Inside else of if (result == 1) @ getTagPayInfo()");
				Object[] objArray = (Object[]) cstmt.getArray(9).getArray(
						conn.getTypeMap());

				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0) @ getTagPayInfo()");
					EtccErrorMessageException em = new EtccErrorMessageException(
							"TolltagWS::Database generated error message");
					for (int i = 0; i < objArray.length; i++) {
						em.addRecoverable(((ErrorMsgRec) objArray[i])
								.getErrMsg());
					}
					logger.error("Error in getTagPayInfo() @ OracleTolltagDAO :: TolltagWS::Database generated error message");
					throw em;
				} else {
					logger.debug("Inside else of if (objArray != null && objArray.length > 0) @ getTagPayInfo()");
					logger.error("Error in getTagPayInfo() @ OracleTolltagDAO :: Fatal error in getTagPayInfo.");
					throw new EtccException("Fatal error in getTagPayInfo.");
				}
			}

		} finally {
			closeConnection();
		}
	}

	public boolean makePayment(long accountId, String dbSessionId,
			String loginId, String ipAddress, long transactionId,
			double paymentAmount, String cardCode, String paymentType,
			String cardNumber, String expireMonth, String expireYear,
			String nameOnCard, String address, String address2, String city,
			String state, String zipCode, String plus4, String cardSecurityCode)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering makePayment() @ OracleTolltagDAO");
		boolean paymentSuccess = false;

		try {
			setConnection(Util.getDbConnection());
			int result = 1;
			/*
			 * acctMgmt.makePayment("" + accountId, "AC", dbSessionId,
			 * ipAddress, loginId, pTransactionId, pPaymentAmount,
			 * olcPaymentInfoRec, "", O_ERROR_MSG_ARR).intValue();
			 */

			if (result == 1) {
				logger.debug("Inside if (result == 1) @ makePayment()");
				paymentSuccess = true;
			} else {
				logger.debug("Inside else of if (result == 1) @ makePayment()");
				// if (O_ERROR_MSG_ARR[0] != null &&
				// O_ERROR_MSG_ARR[0].getArray() != null &&
				// O_ERROR_MSG_ARR[0].getArray().length > 0) {
				// OlcErrorMsgRec[] errorMsgRecs =
				// O_ERROR_MSG_ARR[0].getArray();
				// EtccErrorMessageException em =
				// new
				// EtccErrorMessageException("PaymentWS::getPaymentDetail error message");
				// for (int i = 0; i < errorMsgRecs.length; i++) {
				// em.addRecoverable(errorMsgRecs[i].getErrorMsg());
				// }
				// throw em;
				// } else {
				// throw new
				// EtccException("PaymentWS::getPaymentDetail fatal error");
				// }
			}
		} finally {
			closeConnection();
		}
		logger.info("Leaving makePayment() @ OracleTolltagDAO");
		return paymentSuccess;
	}

	public String loginCreation(BigDecimal accountId, String loginType,
			String dbSessionId, String loginId, String password,
			String ipAddress, String firstName, String middleInitial,
			String lastName, String email) throws EtccErrorMessageException,
			Exception {
		logger.info("Entering loginCreation() @ OracleTolltagDAO");
		try {
			setConnection(Util.getDbConnection());
			String[] O_SESSION_ID = new String[] { "" };
			OlcErrorMsgArr[] O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new OlcErrorMsgArr() };

			int result = new OLCSC_ACCT_MGMT(conn).loginCreation(accountId,
					loginType, dbSessionId, ipAddress, firstName,
					middleInitial, lastName, email, loginId, password,
					O_SESSION_ID, O_ERROR_MSG_ARR).intValue();

			if (result == 1) {
				logger.debug("Inside if (result == 1) @ loginCreation()");
				logger.info("Leaving loginCreation() @ OracleTolltagDAO");
				return O_SESSION_ID[0];
			} else {
				logger.debug("Inside else of if (result == 1) @ loginCreation()");
				if (O_ERROR_MSG_ARR[0] != null
						&& O_ERROR_MSG_ARR[0].getArray() != null
						&& O_ERROR_MSG_ARR[0].getArray().length > 0) {
					logger.debug("Inside if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null && O_ERROR_MSG_ARR[0].getArray().length > 0) @ loginCreation()");
					OlcErrorMsgRec[] errorMsgRecs = O_ERROR_MSG_ARR[0]
							.getArray();
					EtccErrorMessageException em = new EtccErrorMessageException(
							"loginCreation error message");
					for (int i = 0; i < errorMsgRecs.length; i++) {
						em.addRecoverable(errorMsgRecs[i].getErrorMsg());
					}
					logger.error("Error in loginCreation() @ OracleTolltagDAO :: login creation error.");
					throw em;
				} else {
					logger.debug("Inside else of if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null && O_ERROR_MSG_ARR[0].getArray().length > 0) @ loginCreation()");
					logger.error("Error in loginCreation() @ OracleTolltagDAO :: loginCreation fatal error.");
					throw new EtccException("loginCreation fatal error");
				}
			}
		} finally {
			closeConnection();
		}
	}

	private OlcAcctSecQuestRec[] convertToOracleSecurityQuestions(
			List<SecurityQuestionDTO> listOfQuestionsnAnswers)
			throws SQLException {
		logger.info("Entering convertToOracleSecurityQuestions() @ OracleTolltagDAO");
		if (listOfQuestionsnAnswers != null
				&& listOfQuestionsnAnswers.size() > 0) {
			logger.debug("Inside if (listOfQuestionsnAnswers != null && listOfQuestionsnAnswers.size() > 0) @ convertToOracleSecurityQuestions()");
			OlcAcctSecQuestRec[] result = new OlcAcctSecQuestRec[listOfQuestionsnAnswers
					.size()];
			for (int i = 0; i < listOfQuestionsnAnswers.size(); i++) {
				result[i] = new OlcAcctSecQuestRec();
				logger.debug("listOfQuestionsnAnswers getQuestionId:"
						+ listOfQuestionsnAnswers.get(i).getQuestionId());
				logger.debug("listOfQuestionsnAnswers getAnswer:"
						+ listOfQuestionsnAnswers.get(i).getAnswer());
				logger.debug("listOfQuestionsnAnswers getSecurityQuestionOrder:"
						+ listOfQuestionsnAnswers.get(i)
								.getSecurityQuestionOrder());
				result[i].setQuestionId(new BigDecimal(listOfQuestionsnAnswers
						.get(i).getQuestionId()));
				result[i].setSecurityQuestionAnswer(listOfQuestionsnAnswers
						.get(i).getAnswer());
				result[i].setSecurityQuestionOrder(new BigDecimal(
						listOfQuestionsnAnswers.get(i)
								.getSecurityQuestionOrder()));
			}
			logger.info("Leaving convertToOracleSecurityQuestions() @ OracleTolltagDAO");
			return result;
		}
		return null;
	}

	public ArrayList<AccountCardDTO> getTollRemoveReasonList(
			AccountLoginDTO acctLoginDto) throws EtccErrorMessageException,
			Exception {
		logger.info("Entering getTollRemoveReasonList() @ OracleTolltagDAO");
		// logger.info(new StringBuilder("getAccountReasonCodes: acct=")
		// .append(acctLoginDto.getAcctId()));
		ArrayList<AccountCardDTO> result = null;

		try {
			/*
			 * FUNCTION Get_Remove_Acct_Card_Rsn_Code(p_acct_id NUMBER,
			 * p_session VARCHAR2, p_ip_address VARCHAR2, p_user VARCHAR2,
			 * p_reason_code_arr OUT OLC_ACCT_CARD_REMOVAL_RSN_ARR,
			 * o_error_msg_arr OUT olc_error_msg_arr) RETURN NUMBER
			 */

			setConnection(Util.getDbConnection());
			logger.info("getTollRemoveReasonList() :: Calling OLCSC_ACCT_MGMT.Get_Remove_tag_Rsn_Code");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Remove_tag_Rsn_Code"
							+ "(?,?,?,?,?,?)}");

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

			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 1) {
				logger.debug("Inside if (success == 1) @ getTollRemoveReasonList()");
				result = convertToAcctCardRsnCode((Object[]) cstmt.getArray(6)
						.getArray());
			} else if (success == -1) {
				logger.debug("Inside else if (success == -1) @ getTollRemoveReasonList()");
				logger.error("Error in getTollRemoveReasonList() @ OracleTolltagDAO :: Security exception in getTollRemoveReasonList.");
				throw new EtccSecurityException(
						"Security exception in getTollRemoveReasonList");
			}

		} catch (SQLException sqle) {
			logger.error(
					"Error in getTollRemoveReasonList() @ OracleTolltagDAO ",
					sqle);
			throw new EtccException("Error running getTollRemoveReasonList: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getTollRemoveReasonList() @ OracleTolltagDAO");
		return result;
	}

	private ArrayList<AccountCardDTO> convertToAcctCardRsnCode(Object[] recs)
			throws EtccException {
		logger.info("Entering convertToAcctCardRsnCode() @ OracleTolltagDAO");
		ArrayList<AccountCardDTO> result = null;

		try {
			if (recs != null) {
				logger.debug("Inside if (recs != null) @ convertToAcctCardRsnCode()");
				result = new ArrayList();
				for (int i = 0; i < recs.length; i++) {
					AccountCardDTO temp = new AccountCardDTO();
					OlcAcctCardRemovalRsnRec rec = (OlcAcctCardRemovalRsnRec) recs[i];
					temp.setAcctCardStatus(rec.getAcctCardStatus());
					temp.setCardStatus(rec.getCardStatus());

					result.add(temp);
				}
			}

		} catch (Exception ex) {
			logger.error(
					"Error in convertToAcctCardRsnCode() @ OracleTolltagDAO: "
							+ ex, ex);
			throw new EtccException(
					"Error copying account card status properties: " + ex, ex);
		}
		logger.info("Leaving convertToAcctCardRsnCode() @ OracleTolltagDAO");
		return result;
	}

	public Collection removeTag(AccountLoginDTO acctLoginDto,
			String accountTagId, String rsnCode, String flag)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering removeTag() @ OracleTolltagDAO");
		Collection coll = null;
		// logger.info(new StringBuilder("getAccountReasonCodes: acct=")
		// .append(acctLoginDto.getAcctId()));
		logger.debug("accountId: " + acctLoginDto.getAcctId());
		logger.debug("accountTagId: " + accountTagId);
		logger.debug("rsnCode: " + rsnCode);

		try {
			setConnection(Util.getDbConnection());
			logger.info("removeTag() :: Calling Olcsc_acct_mgmt.Remove_Deactivate_Tag");
			cstmt = conn
					.prepareCall("{? = call Olcsc_acct_mgmt.Remove_Deactivate_Tag"
							+ "(?,?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG__REC",
					OlcAcctCardRemovalRsnRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setLong(2, acctLoginDto.getAcctId());
			cstmt.setString(3, acctLoginDto.getDbSessionId());
			cstmt.setString(4, acctLoginDto.getLastLoginIp());
			cstmt.setString(5, acctLoginDto.getLoginId());
			cstmt.setLong(6, new Long(accountTagId));
			cstmt.setString(7, rsnCode);
			cstmt.setLong(8, EventEnum.REMOVETAG.getId());
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 1) {
				logger.debug("Inside if (success == 1) @ removeTag()");
				logger.info("Leaving removeTag() @ OracleTolltagDAO");
				return coll;
				// result = Integer.toString(success);
			} else if (success == 0) {
				logger.debug("Inside else if (success == 0) @ removeTag()");
				// Populate the error message to the first entry in the TagsDTO
				// array
				Array errors = (Array) cstmt.getObject(9);
				coll = Util.convertErrorMsgs(errors);
			} else if (success == -1) {
				logger.debug("Inside else if (success == -1) @ removeTag()");
				logger.error("Error in removeTag() @ OracleTolltagDAO :: Security exception in removeTag");
				throw new EtccSecurityException(
						"Security exception in removeTag");
			}
		} catch (SQLException sqle) {
			logger.error("Error in removeTag() @ OracleTolltagDAO: " + sqle,
					sqle);
			throw new EtccException("Error running removeTag: " + sqle, sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving removeTag() @ OracleTolltagDAO");
		return coll;
	}

	private java.sql.Date convertToDate(String date) {
		logger.info("Entering convertToDate() @ OracleTolltagDAO");
		java.sql.Date dateSql = null;

		if (date != null && date.trim().length() > 0) {
			logger.debug("Inside if (date != null && date.trim().length() > 0) @ convertToDate()");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			java.util.Date dateUtl;
			try {
				dateUtl = sdf.parse(date);
				dateSql = new java.sql.Date(dateUtl.getTime());
			} catch (ParseException ex) {
				logger.error("Error in convertToDate() @ OracleTolltagDAO ", ex);
			}
			logger.info("Leaving convertToDate() @ OracleTolltagDAO");
			return dateSql;
		} else {
			logger.debug("Inside else of if (date != null && date.trim().length() > 0) @ convertToDate()");
			logger.info("Leaving convertToDate() @ OracleTolltagDAO");
			return dateSql;
		}
	}

	public int validateVehicleInfo(AccountLoginDTO acctLoginDto, TagDTO tags)
			throws EtccException, EtccSecurityException, IOException {
		logger.info("Entering validateVehicleInfo() @ OracleTolltagDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("validateVehicleInfo() :: Calling Olcsc_acct_mgmt.Ini_Val_Get_Dmv_Trn_Cnt_Amt");
			cstmt = conn
					.prepareCall("{? = call Olcsc_acct_mgmt.Ini_Val_Get_Dmv_Trn_Cnt_Amt"
							+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			logger.debug("*********** test 33 ***********");
			List<AccountVehicleInfoDTO> acctVehInfoLst = new ArrayList<AccountVehicleInfoDTO>();
			cstmt.registerOutParameter(1, Types.SMALLINT);

			cstmt.setLong(2, acctLoginDto.getAcctId());
			cstmt.setString(3, acctLoginDto.getDbSessionId());
			cstmt.setString(4, acctLoginDto.getLastLoginIp());
			cstmt.setString(5, acctLoginDto.getLoginId());
			cstmt.setString(6, tags.getLicPlate());
			cstmt.setString(7, tags.getLicState());
			CountryInterface cntry = (CountryInterface) DelegateFactory
					.create(DelegateEnum.COUNTRY_DELEGATE);
			String defaultCountryCode = cntry.getDefaultCountryCode();
			cstmt.setString(8, defaultCountryCode);
			cstmt.setString(9, tags.getPlateTypeCode());
			cstmt.setString(10, tags.getResponsibilityCode());
			if (tags.getVehicleYear() != null) {
				logger.debug("Inside if (tags.getVehicleYear() != null) @ validateVehicleInfo()");
				cstmt.setLong(11, Long.valueOf(tags.getVehicleYear()));
			} else {
				logger.debug("Inside else of if (tags.getVehicleYear() != null) @ validateVehicleInfo()");
				cstmt.setLong(11, 0);
			}
			logger.debug("hello AGAIN 1111");
			if (tags.getEffectiveDate() != null) {
				logger.debug("Inside if (tags.getEffectiveDate() != null) @ validateVehicleInfo()");
				cstmt.setTimestamp(12, new Timestamp(tags.getEffectiveDate()
						.getTime().getTime()));
			} else {
				logger.debug("Inside else of if (tags.getEffectiveDate() != null) @ validateVehicleInfo()");
				cstmt.setTimestamp(12, null);
			}

			if (tags.getExpireDate() != null) {
				logger.debug("Inside if (tags.getExpireDate() != null) @ validateVehicleInfo()");
				cstmt.setTimestamp(12, new Timestamp(tags.getExpireDate()
						.getTime()));
			} else {
				logger.debug("Inside else of if (tags.getExpireDate() != null) @ validateVehicleInfo()");
				cstmt.setTimestamp(13, null);
			}

			cstmt.setString(14, tags.getVinNumber());
			cstmt.setLong(15, tags.getAccountVehicleId());
			cstmt.setLong(16, EventEnum.EDITVEHICLE.getId());
			cstmt.setString(17, tags.getTagId());
			cstmt.setString(18, tags.getAgencyId());
			cstmt.registerOutParameter(19, Types.ARRAY, "RITE_COMMON.STRING_ARRAY");
			cstmt.registerOutParameter(20, Types.ARRAY, "RITE_COMMON.STRING_ARRAY");
			cstmt.registerOutParameter(21, Types.ARRAY, "RITE_COMMON.STRING_ARRAY");
			cstmt.registerOutParameter(22, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			short success = cstmt.getShort(1);
			if (success == 1) {
				logger.debug("Inside if (success == 1) @ validateVehicleInfo()");
				/*
				 * Clob clob = cstmt.getClob(20); StringBuffer strBuffer = new
				 * StringBuffer(); String str = null; br = new
				 * BufferedReader(clob.getCharacterStream()); while
				 * ((str=br.readLine())!=null){ strBuffer.append(str); }// end
				 * of while ((str=br.readLine())!=null)
				 */Array txnCntArray = cstmt.getArray(20);

				Object obj = txnCntArray.getArray();
				if (obj != null && obj.getClass().isArray()) {
					logger.debug("Inside if (obj != null && obj.getClass().isArray()) @ validateVehicleInfo()");
					String[] StrArray = (String[]) obj;
					for (String aRecord : StrArray) {
						AccountVehicleInfoHolderDTO arrayHolder = Util
								.readXMLData(aRecord,
										AccountVehicleInfoHolderDTO.class);
						if (arrayHolder != null
								&& arrayHolder.getAcctvehRecord() != null
								&& arrayHolder.getAcctvehRecord().length > 0) {
							logger.debug("Inside if (arrayHolder != null && arrayHolder.getAcctvehRecord() != null && arrayHolder.getAcctvehRecord().length > 0) @ validateVehicleInfo()");
							for (AccountVehicleInfoDTO singleRow : arrayHolder
									.getAcctvehRecord()) {
								if (singleRow != null) {
									logger.debug("Inside if (singleRow != null) @ validateVehicleInfo()");
									acctVehInfoLst.add(singleRow);
								}
							}
						}
					}
				}
				logger.info("Leaving validateVehicleInfo() @ OracleTolltagDAO");
				return acctVehInfoLst.size();
			} else if (success == -1) {
				logger.debug("Inside else if (success == -1) @ validateVehicleInfo()");
				logger.error("Error in validateVehicleInfo() @ OracleTolltagDAO :: Security exception in validateVehicleInfo");
				throw new EtccSecurityException(
						"Security exception in validateVehicleInfo");
			}

		} catch (SQLException sqle) {
			logger.error("Error in validateVehicleInfo() @ OracleTolltagDAO: "
					+ sqle, sqle);
			throw new EtccException("Error running validateVehicleInfo: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}
		// return result;
		return 0;
	}

	public Collection getAccountVehicles(AccountLoginDTO accountLoginDTO,
			String includeTag) throws EtccException, EtccSecurityException {
		logger.info("Entering getAccountVehicles() @ OracleTolltagDAO");
		logger.debug(new StringBuilder("getAccountTags: acct=")
				.append(accountLoginDTO.getAcctId()));
		Collection result = null;
		setConnection(Util.getDbConnection());
		try {
			/*
			 * FUNCTION GET_VEHICLE_INFO(P_SESSION VARCHAR2, P_ACCT_ID NUMBER,
			 * P_USER VARCHAR2, P_IP_ADDRESS IN VARCHAR2, P_INCLUDE_TAG in
			 * VARCHAR2, P_ACCOUNT_TAG_ARR OUT OLC_ACCOUNT_TAG_ARR, P_UI_CALL IN
			 * BOOLEAN DEFAULT TRUE, P_ERROR_ARR OUT OLC_ERROR_MSG_ARR,
			 * P_EVENT_ID IN EVENTS.EVENT_ID%TYPE DEFAULT NULL) RETURN NUMBER;
			 */
			logger.info("getAccountVehicles() :: Calling OLCSC_VEH_MGMT.GET_VEHICLE_INFO");
			cstmt = conn
					.prepareCall("{? = call OLCSC_VEH_MGMT.GET_VEHICLE_INFO"
							+ "(?,?,?,?,?,?,null,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OlcAccountTagRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);

			logger.debug("param 2=" + accountLoginDTO.getDbSessionId());
			cstmt.setString(2, accountLoginDTO.getDbSessionId());

			logger.debug("param 3=" + accountLoginDTO.getAcctId());
			cstmt.setLong(3, accountLoginDTO.getAcctId());

			logger.debug("param 4=" + accountLoginDTO.getLoginId());
			cstmt.setString(4, accountLoginDTO.getLoginId());

			logger.debug("param 5=" + accountLoginDTO.getLastLoginIp());
			cstmt.setString(5, accountLoginDTO.getLastLoginIp());

			logger.debug("param 6=" + includeTag);
			cstmt.setString(6, includeTag);

			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_TAG_ARR");

			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setLong(9, 0);

			cstmt.execute();
			byte success = cstmt.getByte(1);
			if (success == 1) {
				logger.debug("Inside if (success == 1) @ getAccountVehicles()");
				Object[] objArray = (Object[]) cstmt.getArray(7).getArray();
				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0) @ getAccountVehicles()");
					result = new ArrayList();
					for (int i = 0; i < objArray.length; i++) {
						TagDTO tagDto = copyTagProperties((OlcAccountTagRec) objArray[i]);
						tagDto.setAcctId(accountLoginDTO.getAcctId());
						result.add(tagDto);
					}
				}
			} else if (success == -1) {
				logger.debug("Inside else if (success == -1) @ getAccountVehicles()");
				logger.error("Error in getAccountVehicles() @ OracleTolltagDAO :: Security Exception in getAccountVehicles");
				throw new EtccSecurityException(
						"Security Exception in getAccountVehicles");
			}

		} catch (SQLException sqle) {
			logger.error("Error in getAccountVehicles() @ OracleTolltagDAO: "
					+ sqle, sqle);
			throw new EtccException("errror in getAccountVehicles" + sqle, sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getAccountVehicles() @ OracleTolltagDAO");
		return result;
	}

	/**
	 * Creates a TagDTO based on the values given by the Oracle-generated class.
	 * 
	 * @param temp
	 * @return
	 */
	private TagDTO copyTagProperties(OlcAccountTagRec temp) {
		logger.info("Entering copyTagProperties() @ OracleTolltagDAO");
		TagDTO tagDto = null;

		try {
			if (temp != null) {
				logger.debug("Inside if (temp != null) @ copyTagProperties()");
				tagDto = new TagDTO();
				tagDto.setAcctTagStatus(temp.getAcctTagStatus());
				tagDto.setAccountVehicleId(temp.getAccountVehicleId()
						.longValue());
				tagDto.setAgencyId(temp.getAgencyId());
				tagDto.setTagId(temp.getTagId());
				tagDto.setLicPlate(temp.getLicPlate());
				tagDto.setLicState(temp.getLicState());
				tagDto.setVehicleYear(temp.getVehicleYear());
				tagDto.setVehicleColor(temp.getVehicleColor());
				tagDto.setVehicleMake(temp.getVehicleMake());
				tagDto.setVehicleModel(temp.getVehicleModel());
				tagDto.setVehicleClassCode(temp.getVehicleClassCode());
				tagDto.setVehicleDescr(temp.getVehicleClassDesc());
				tagDto.setTagStatusDesc(temp.getTagStatusDesc());

				if (temp.getAcctTagSeq() != null) {
					logger.debug("Inside if (temp.getAcctTagSeq() != null) @ copyTagProperties()");
					tagDto.setAcctTagSeq(temp.getAcctTagSeq().longValue());
				}
				tagDto.setVehicleClassDesc(temp.getVehicleClassDesc());
				tagDto.setTemporaryLicPlate(Util.stringToBoolean(temp
						.getTempPlateFlag()));
				tagDto.setPlateExpirDate(DateUtil.timestampToCalendar(temp
						.getPlateExpirDate()));
				tagDto.setPlateTypeCode(temp.getLicPlateTypeCode());
				tagDto.setBarcodePrefix(temp.getBarcodePrefix());
				tagDto.setResponsibilityCode(temp.getResponsibilityTypeCode());
				if ((temp.getResponsibilityTypeCode() != null)
						&& (temp.getResponsibilityTypeCode().equals("R"))) {
					logger.debug("Inside if ((temp.getResponsibilityTypeCode() != null) && (temp.getResponsibilityTypeCode().equals(\"R\"))) @ copyTagProperties()");
					tagDto.setRentalVehicle(true);
					/*
					 * tagDto.setEffectiveDate(DateUtil.timestampToCalendar(temp.
					 * getEffectiveDate()));
					 * tagDto.setExpirDate(DateUtil.timestampToCalendar
					 * (temp.getExpiryDate()));
					 */

				} else {
					logger.debug("Inside else of if ((temp.getResponsibilityTypeCode() != null) && (temp.getResponsibilityTypeCode().equals(\"R\"))) @ copyTagProperties()");
					tagDto.setRentalVehicle(false);
					/*
					 * tagDto.setEffectiveDate(DateUtil.timestampToCalendar(null)
					 * );
					 * tagDto.setExpirDate(DateUtil.timestampToCalendar(null));
					 */
				}
				tagDto.setEffectiveDate(DateUtil.timestampToCalendar(temp
						.getEffectiveDate()));
				tagDto.setExpirDate(DateUtil.timestampToCalendar(temp
						.getExpiryDate()));
				tagDto.setVinNumber(temp.getVin());
				tagDto.setTagTypeCode(temp.getTagTypeCode());
			}
			logger.info("Leaving copyTagProperties() @ OracleTolltagDAO");
			return tagDto;
		} catch (Exception ex) {
			logger.error("Error in copyTagProperties() @ OracleTolltagDAO ", ex);
			return null;
		}
	}
	
	private Long getSystemShiftId(String userName){
		Long shiftId = 0L;
		logger.info("Entering getSystemShiftId()");
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT Current_Shift_Id ");
		sb.append(" FROM system_shifts ");
		sb.append(" WHERE system_shift_name = ?");
		try {
			setConnection(Util.getDbConnection());
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if (rs.next()) {
				shiftId = rs.getLong(1);
				logger.debug("shiftId " + shiftId);
			}else{
				shiftId = 378986L;
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in getSystemShiftId(): "
							+ sqle, sqle);
		} 
		return shiftId;
	}
	
	public void createShiftEditsRecord(Long posId, String editItemTypeCode,
			String activityCode) {
		logger.info("Entering createShiftEditsRecord() @ OracleTolltagDAO");
		setConnection(Util.getDbConnection());
		try {
			cstmt = conn
					.prepareCall("{call TAG_OWNER.ACTIVITITIES_LOGGER.THIRDPARTY_RECORD_EDIT"
							+ "(?,?,?,?,?,?)}");

			cstmt.setLong(1, posId);
			logger.debug("posId " + posId);
			cstmt.setString(2, editItemTypeCode);
			cstmt.setLong(3, getSystemShiftId(OLCSC_USER_NAME));
			logger.debug("shiftId " + getSystemShiftId(OLCSC_USER_NAME));
			cstmt.setLong(4, OLCSC_USER_ID);
			cstmt.setString(5, activityCode);
			logger.debug("activityCode " + activityCode);
			cstmt.setLong(6, 1L);

			cstmt.execute();
		} catch (SQLException sqle) {
			logger.error("Error in createShiftEditsRecord() @ OracleTolltagDAO: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}
	}
}