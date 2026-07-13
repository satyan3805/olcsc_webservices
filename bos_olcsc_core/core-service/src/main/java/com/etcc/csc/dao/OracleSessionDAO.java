package com.etcc.csc.dao;

import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SessionDTO;

public class OracleSessionDAO extends SessionDAO {
	private Logger logger = Logger.getLogger(OracleSessionDAO.class);

	public OracleSessionDAO() {
	}

	public String makeSession(SessionDTO sessionDto) throws EtccException {
		logger.info("Entering makeSession() @ OracleSessionDAO");
		String sessionId = null;
		try {
			/*
			 * function CREATE_SESSION(p_Document_id IN NUMBER, p_doc_type IN
			 * VARCHAR2, p_Ip_address IN VARCHAR2 , p_login_id IN VARCHAR2
			 * DEFAULT NULL, p_jsession_id IN VARCHAR2 DEFAULT NULL,
			 * p_browser_type IN VARCHAR2 DEFAULT NULL, p_browser_version IN
			 * VARCHAR2 DEFAULT NULL, p_os_type IN VARCHAR2 DEFAULT NULL,
			 * p_os_version IN VARCHAR2 DEFAULT NULL, p_user_env_attribute1 IN
			 * VARCHAR2 DEFAULT NULL, p_user_env_attribute2 IN VARCHAR2 DEFAULT
			 * NULL, p_user_env_attribute3 IN VARCHAR2 DEFAULT NULL,
			 * p_user_env_attribute4 IN VARCHAR2 DEFAULT NULL,
			 * p_user_env_attribute5 IN VARCHAR2 DEFAULT NULL, p_api_routine_id
			 * IN olc_session_activities.api_routine_id%type, p_lang_id IN
			 * ap_languages.ap_lang_id%type,---Mlavu 05/10/2007 o_session_id OUT
			 * VARCHAR2 , o_error_msg_arr OUT OLC_error_msg_arr) return NUMBER
			 * IS
			 */
			// create session object
			setConnection(Util.getDbConnection());
			logger.info("makeSession() :: Calling OLCSC_HTTP_SESSION.CREATE_SESSION");
			cstmt = conn
					.prepareCall("{? = call OLCSC_HTTP_SESSION.CREATE_SESSION(?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)}");
			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setLong(idx++, sessionDto.getDocumentId());
			cstmt.setString(idx++, sessionDto.getDocType());
			cstmt.setString(idx++, sessionDto.getIpAddress());
			cstmt.setString(idx++, sessionDto.getUserName().toUpperCase());
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, null);
			cstmt.setString(idx++, sessionDto.getLang());
			cstmt.registerOutParameter(idx++, Types.VARCHAR);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.executeUpdate();
			int result = cstmt.getInt(1);
			sessionId = cstmt.getString(idx - 1);

			if (result != 1) {
				logger.debug("Inside if (result != 1) @ makeSession()");
				logger.error("Error in makeSession() @ OracleSessionDAO :: Unable to create account session.");
				throw new EtccException("Unable to create account session.");
			}
			logger.info("Leaving makeSession() @ OracleSessionDAO");
			return sessionId;
		} catch (Exception ex) {
			logger.error("Error in makeSession() @ OracleSessionDAO: " + ex, ex);
			throw new EtccException("Exception in createSession() " + ex, ex);
		} finally {
			closeConnection();
		}
	}

	public void destroySession(String dbSessionId) throws EtccException {
		logger.info("Entering destroySession() @ OracleSessionDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("destroySession() :: Calling OLCSC_HTTP_SESSION.LOGOUT");
			cstmt = conn
					.prepareCall("{? = call OLCSC_HTTP_SESSION.LOGOUT(?, ?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, dbSessionId);
			cstmt.registerOutParameter(3, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.executeUpdate();
			int result = cstmt.getInt(1);

			if (result != 1) {
				logger.debug("Inside if (result != 1) @ destroySession()");
				logger.error("Error in destroySession() @ OracleSessionDAO :: Unable to destroy session.");
				throw new EtccException("Unable to destroy session.");
			}
			logger.info("Leaving destroySession() @ OracleSessionDAO");
		} catch (Exception ex) {
			logger.error("Error in destroySession() @ OracleSessionDAO: " + ex,
					ex);
			throw new EtccException("Exception in destroySession() " + ex, ex);
		} finally {
			closeConnection();
		}
	}

	public void updateSessionLanguage(AccountLoginDTO acctLoginDto, String lang)
			throws EtccException {
		logger.info("Entering updateSessionLanguage() @ OracleSessionDAO");
		logger.debug(new StringBuilder("updateSessionLanguage: acct=")
				.append(acctLoginDto.getAcctId()));
		try {
			/*
			 * PROCEDURE SET_TRANS_VALUE (P_SESSION VARCHAR2, P_ACCT_ID NUMBER,
			 * P_IP_ADDRESS IN VARCHAR2, P_LANG_ID IN VARCHAR2 DEFAULT 'EN' );
			 */
			setConnection(Util.getDbConnection());
			// need to prefix with schema owner
			// as there is another UTL_WEB package exists in RITE_COMMON
			// which has a public synonym
			logger.info("updateSessionLanguage() :: Calling OL_OWNER.UTL_WEB.SET_TRANS_VALUE");
			cstmt = conn
					.prepareCall("{call OL_OWNER.UTL_WEB.SET_TRANS_VALUE(?, ?, ?, ?)}");
			int idx = 1;
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			if (Constants.LOGIN_TYPE_ACCOUNT
					.equals(acctLoginDto.getLoginType())
					|| Constants.LOGIN_TYPE_ZIPCASH_ACCOUNT.equals(acctLoginDto
							.getLoginType())) {
				logger.debug("Inside if (Constants.LOGIN_TYPE_ACCOUNT.equals(acctLoginDto.getLoginType()) || Constants.LOGIN_TYPE_ZIPCASH_ACCOUNT.equals(acctLoginDto.getLoginType()))) @ updateSessionLanguage()");
				cstmt.setLong(idx++, acctLoginDto.getAcctId());
			} else {
				logger.debug("Inside else of if (Constants.LOGIN_TYPE_ACCOUNT.equals(acctLoginDto.getLoginType()) || Constants.LOGIN_TYPE_ZIPCASH_ACCOUNT.equals(acctLoginDto.getLoginType()))) @ updateSessionLanguage()");
				cstmt.setLong(idx++, acctLoginDto.getInvoiceId());
			}
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, lang);
			cstmt.execute();
			logger.info("Leaving updateSessionLanguage() @ OracleSessionDAO");
		} catch (SQLException sqle) {
			logger.error("Error in destroySession() @ OracleSessionDAO: "
					+ sqle, sqle);
			throw new EtccException("Error calling SET_TRANS_VALUE: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}
	}
}
