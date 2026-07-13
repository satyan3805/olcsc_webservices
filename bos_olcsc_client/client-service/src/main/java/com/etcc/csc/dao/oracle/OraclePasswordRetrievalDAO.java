/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.PasswordRetrievalDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;

/**
 * Oracle specific version of PasswordRetrievalDAO.
 *
 * @see PasswordRetrievalDAO
 */
public class OraclePasswordRetrievalDAO extends PasswordRetrievalDAO {

	private static final Logger logger = Logger
			.getLogger(OraclePasswordRetrievalDAO.class);

	public OnlineAccessSetupDTO retrieveAccountInfo(
			OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,
			String sessionId, UserEnvDTO userEnvDto) throws EtccException,
			EtccSecurityException {
		CallableStatement cstmt = null;
		try {
			setTypeMap();

			/*
			 * FUNCTION Forgot_Pwd_Step1(p_doc_id IN OUT VARCHAR2, p_doc_type
			 * VARCHAR2 DEFAULT 'AC', p_ip_address VARCHAR2, P_JSESSION_ID IN
			 * VARCHAR2, p_browser_type VARCHAR2 DEFAULT NULL, p_browser_version
			 * VARCHAR2 DEFAULT NULL, p_os_type VARCHAR2 DEFAULT NULL,
			 * p_os_version VARCHAR2 DEFAULT NULL, p_user_env_attribute1
			 * VARCHAR2 DEFAULT NULL, p_user_env_attribute2 VARCHAR2 DEFAULT
			 * NULL, p_user_env_attribute3 VARCHAR2 DEFAULT NULL,
			 * p_user_env_attribute4 VARCHAR2 DEFAULT NULL,
			 * p_user_env_attribute5 VARCHAR2 DEFAULT NULL, p_tag_prefix
			 * VARCHAR2, p_tag_id VARCHAR2 DEFAULT NULL, p_driver_lic_state
			 * VARCHAR2, p_driver_lic_nbr VARCHAR2 DEFAULT NULL, p_tax_id
			 * VARCHAR2 DEFAULT NULL, o_security_question OUT VARCHAR2,
			 * o_email_addr OUT NUMBER, O_SESSION_ID OUT VARCHAR2,
			 * o_error_msg_arr OUT olc_error_msg_arr) RETURN NUMBER IS
			 */

			cstmt = this.conn.prepareCall("{? = call OLCSC_ONLINE_ACCESS."
					+ "Forgot_Pwd_Step1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?)}");

			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			if (onlineAccessDTO.getAcctId() == 0)
				cstmt.setString(idx++, null);
			else
				cstmt.setString(idx++, Long.toString(onlineAccessDTO
						.getAcctId()));

			cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString());
			cstmt.setString(idx++, ipAddress);
			cstmt.setString(idx++, sessionId); // session Id
			cstmt.setString(idx++, userEnvDto.getBrowserType()); // browser type
			cstmt.setString(idx++, userEnvDto.getBrowserVersion()); // browser
			// version
			cstmt.setString(idx++, userEnvDto.getOsType()); // os type
			cstmt.setString(idx++, userEnvDto.getOsVersion()); // os version
			cstmt.setString(idx++, userEnvDto.getAttribute1()); // attrib 1
			cstmt.setString(idx++, userEnvDto.getAttribute2()); // attrib 2
			cstmt.setString(idx++, userEnvDto.getAttribute3()); // attrib 3
			cstmt.setString(idx++, userEnvDto.getAttribute4()); // attrib 4
			cstmt.setString(idx++, userEnvDto.getAttribute5()); // attrib 5
			cstmt.setString(idx++, onlineAccessDTO.getAgencyId());
			cstmt.setString(idx++, onlineAccessDTO.getTagId());
			cstmt.setString(idx++, onlineAccessDTO.getEmailAddress());
			cstmt.setString(idx++, onlineAccessDTO.getPhoneNumber());
			cstmt.setString(idx++, userEnvDto.getSourceUserName());
			cstmt.registerOutParameter(2, Types.VARCHAR); // acct id
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // sec question
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // email addr
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // session id
			cstmt.registerOutParameter(idx++, Types.SMALLINT); // security
			// question id
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // login id
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();

			int result = cstmt.getInt(1);
			System.out.println("pwd retrieval result=" + result);
			if (result == -1) {
				logger.debug("Security Exception: retrieveAccountInfo");
				throw new EtccSecurityException(
						"Security Exception retrieving account info");
			} else if (result == 0) {
				logger.debug("Error retrieving account info");
				Array errors = (Array) cstmt.getObject(idx);
				onlineAccessDTO.setErrors(OracleUtil.convertToMessages(errors));
				return onlineAccessDTO;
			}

			String accountId = cstmt.getString(2);
			onlineAccessDTO.setAcctId(Long.parseLong(accountId));

			String securityQuestion = cstmt.getString(idx - 5);
			// String emailAddress = (cstmt.getInt(idx-4)==1)?"a":null;
			String emailAddress = cstmt.getString(idx - 4);
			int securityQuestionID = cstmt.getInt(idx - 2);
			String loginID = cstmt.getString(idx - 1);

			logger.info("emailExist:" + (emailAddress != null));
			onlineAccessDTO.setSecurityQuestion(securityQuestion);
			onlineAccessDTO.setEmailAddress(emailAddress);
			onlineAccessDTO.setSecurityQuestionID(securityQuestionID);
			onlineAccessDTO.setLoginId(loginID);

			onlineAccessDTO.setDbSessionId(cstmt.getString(idx - 3));

		} catch (SQLException e) {
			throw new EtccException("errror retrieveing account information:"
					+ e.getMessage(), e);
		} finally {
			close(cstmt);
		}

		return onlineAccessDTO;
	}

	public OnlineAccessSetupDTO retrieveEmailAddressInfo(
			OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,
			String sessionId, UserEnvDTO userEnvDto) throws EtccException,
			EtccSecurityException {
		CallableStatement cstmt = null;
		try {
			setTypeMap();

			cstmt = this.conn.prepareCall("{? = call OLCSC_ONLINE_ACCESS."
					+ "ChgPassword_url(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.setString(3, AccountLoginDTO.LoginType.AC.toString());
			cstmt.setString(4, ipAddress);
			cstmt.setString(5, sessionId); // session Id
			cstmt.setString(6, userEnvDto.getBrowserType()); // browser type
			cstmt.setString(7, userEnvDto.getBrowserVersion()); // browser
			// version
			cstmt.setString(8, userEnvDto.getOsType()); // os type
			cstmt.setString(9, userEnvDto.getOsVersion()); // os version
			cstmt.setString(10, userEnvDto.getAttribute1()); // attrib 1
			cstmt.setString(11, userEnvDto.getAttribute2()); // attrib 2
			cstmt.setString(12, userEnvDto.getAttribute3()); // attrib 3
			cstmt.setString(13, userEnvDto.getAttribute4()); // attrib 4
			cstmt.setString(14, userEnvDto.getAttribute5()); // attrib 5
			cstmt.setString(15, onlineAccessDTO.getEmailAddress());
			cstmt.setString(16, userEnvDto.getSourceUserName());	
			cstmt.registerOutParameter(17, Types.VARCHAR); 
			cstmt.registerOutParameter(18, Types.VARCHAR); // sec question
			cstmt.registerOutParameter(19, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			
			cstmt.executeUpdate();

			int result = cstmt.getInt(1);
			System.out.println("email retrieval result=" + result);
			
			if (result == -1) {
				logger.debug("Security Exception: retrieveAccountInfo");
				throw new EtccSecurityException(
						"Security Exception retrieving account info");
			} else if (result == 0) {
				logger.debug("Error retrieving account info");
				Array errors = (Array) cstmt.getObject(19);
				onlineAccessDTO.setErrors(OracleUtil.convertToMessages(errors));
				logger.info("Errors.............."
						+ onlineAccessDTO.getErrors());
				return onlineAccessDTO;
			}
			String secQuestion = cstmt.getString(18);
			onlineAccessDTO.setSecurityQuestion(secQuestion);
			String dbSessionId = cstmt.getString(17);
			onlineAccessDTO.setDbSessionId(dbSessionId);

		} catch (SQLException e) {
			throw new EtccException("errror retrieveing account information:"
					+ e.getMessage(), e);
		} finally {
			close(cstmt);
		}

		return onlineAccessDTO;
	}

	public OnlineAccessSetupDTO validationData(
			OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,
			String sessionId, UserEnvDTO userEnvDto)
			throws EtccSecurityException, EtccException {

		CallableStatement cstmt = null;
		try {
			setTypeMap();
			cstmt = this.conn
					.prepareCall("{? = call OLCSC_ONLINE_ACCESS."
							+ "get_email_validation_data(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
							+ "?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, onlineAccessDTO.getValidationId());
			cstmt.setString(3, sessionId);
			cstmt.setString(4, ipAddress);
			cstmt.setString(5, userEnvDto.getBrowserType()); // browser type
			cstmt.setString(6, userEnvDto.getBrowserVersion()); // browser
																// version
			cstmt.setString(7, userEnvDto.getOsType()); // os type
			cstmt.setString(8, userEnvDto.getOsVersion()); // os version
			cstmt.setString(9, userEnvDto.getAttribute1()); // attrib 1
			cstmt.setString(10, userEnvDto.getAttribute2()); // attrib 2
			cstmt.setString(11, userEnvDto.getAttribute3()); // attrib 3
			cstmt.setString(12, userEnvDto.getAttribute4()); // attrib 4
			cstmt.setString(13, userEnvDto.getAttribute5()); // attrib 5
			cstmt.registerOutParameter(14, Types.INTEGER);// validation status
			cstmt.registerOutParameter(15, Types.INTEGER);// account id
			cstmt.registerOutParameter(16, Types.VARCHAR);// email address
			cstmt.registerOutParameter(17, Types.VARCHAR);// security question
			cstmt.registerOutParameter(18, Types.INTEGER);// sec id
			cstmt.registerOutParameter(19, Types.VARCHAR);// login id
			cstmt.registerOutParameter(20, Types.VARCHAR);// dbsession id
			cstmt.registerOutParameter(21, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();
			int result = cstmt.getInt(1);
			System.out
					.println("email validation retrieval security data result="
							+ result);
			if (result == -1) {
				logger.debug("Security Exception: validationData");
				throw new EtccSecurityException(
						"Security Exception validation Data");
			} else if (result == 0) {
				logger.debug("Error retrieving account info");
				Array errors = (Array) cstmt.getObject(21);
				onlineAccessDTO.setErrors(OracleUtil.convertToMessages(errors));
				return onlineAccessDTO;
			}

//			int validationStatus = cstmt.getInt(14);
//			if (validationStatus == 2) {
//				logger.debug("Error retrieving account info");
//				  String str ="Password reset link has been used. Please resubmit email address below for a new link, or reset password using your EZ Tag account information";
//				  String strArray[] = str.split(" ");
//				  onlineAccessDTO.setErrors(OracleUtil.convertToMessages(strArray));
//				return onlineAccessDTO;
//			}
//			else if (validationStatus == 4) {
//				logger.debug("Error retrieving account info");
//				String str ="Password reset link has expired. Please resubmit email address below for a new link, or reset password using your EZ Tag account information";
//				String strArray[] = str.split(" ");
//				onlineAccessDTO.setErrors(OracleUtil.convertToMessages(strArray));
//				return onlineAccessDTO;
//			}

			String accountId = cstmt.getString(15);
			onlineAccessDTO.setAcctId(Long.parseLong(accountId));

			String securityQuestion = cstmt.getString(17);
			String emailAddress = cstmt.getString(16);
			int securityQuestionID = cstmt.getInt(18);
			String loginID = cstmt.getString(19);
			String dbSessionID = cstmt.getString(20);

			onlineAccessDTO.setSecurityQuestion(securityQuestion);
			onlineAccessDTO.setEmailAddress(emailAddress);
			onlineAccessDTO.setSecurityQuestionID(securityQuestionID);
			onlineAccessDTO.setLoginId(loginID);
			onlineAccessDTO.setDbSessionId(dbSessionID);

		} catch (SQLException e) {
			throw new EtccException(e.getMessage(), e);
		} finally {
			close(cstmt);
		}

		return onlineAccessDTO;
	}

	protected static final long accountId(BigDecimal[] theAcctId) {
		try {
			if (theAcctId == null)
				return -1;
			if (theAcctId.length == 0)
				return -1;
			if (theAcctId[0] == null)
				return -1;
			return theAcctId[0].longValue();
		} catch (Throwable t) {
			logger.error("accountId failed: " + t.getMessage(), t);
			return -1;
		}
	}

	protected static final String emailAddress(String[] theEmailAddress) {
		if (theEmailAddress == null)
			return "";
		if (theEmailAddress.length == 0)
			return "";
		if (theEmailAddress[0] == null)
			return "";
		return theEmailAddress[0];
	}

	public OnlineAccessSetupDTO validateSecurityAnswer(String sessionId,
			String ipAddress, OnlineAccessSetupDTO onlineAccessDTO)
			throws EtccException, EtccSecurityException {

		CallableStatement cstmt = null;
		try {
			setTypeMap();
			cstmt = this.conn
					.prepareCall("{? = call OLCSC_ONLINE_ACCESS.Password_Retrieval(?,?,?,?,?,?,?,?)}");

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, sessionId);
			cstmt.setString(3, onlineAccessDTO.getLoginId());
			cstmt.setString(4, ipAddress);
			if ((onlineAccessDTO.getEmailAddress() != null)
					&& (onlineAccessDTO.getEmailAddress().trim().length() > 0)) {
				cstmt.setInt(5, 1);
			} else {
				cstmt.setInt(5, 0);
			}
			cstmt.setString(6, Long.toString(onlineAccessDTO.getAcctId()));
			cstmt.setString(7, "AC");
			logger
					.info("answer:"
							+ onlineAccessDTO.getSecurityQuestionAnswer());
			cstmt.setString(8, onlineAccessDTO.getSecurityQuestionAnswer());
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();
			int result = cstmt.getInt(1);
			System.out.println("validateSecurityAnswer.result=" + result);
			if (result == -1) {
				logger.debug("Security Exception: validateSecurityAnswer");
				throw new EtccSecurityException(
						"Security Exception Validating Security Answer");
			} else if (result == 0) {
				logger.debug("Error validating security answer");
				Array errors = (Array) cstmt.getObject(9);
				onlineAccessDTO.setErrors(OracleUtil.convertToMessages(errors));

				if (logger.isDebugEnabled()) {
					logger.debug("validateSecurityAnswer.errors="
							+ ErrorMessageDTO.toStringBuilder(onlineAccessDTO
									.getErrors()));
				}
				// return false;
			}

		} catch (SQLException e) {
			throw new EtccException("errror validating security answer: "
					+ e.getMessage(), e);
		} finally {
			close(cstmt);
		}

		return onlineAccessDTO;
	}

	public OnlineAccessSetupDTO addSecurityQuestionAnswer(String sessionId,
			String ipAddress, OnlineAccessSetupDTO onlineAccessDTO)
			throws EtccException, EtccSecurityException {

		CallableStatement cstmt = null;
		try {
			setTypeMap();
			cstmt = this.conn
					.prepareCall("{? = call OLCSC_ONLINE_ACCESS.Add_Sec_Question(?,?,?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, sessionId);
			cstmt.setString(3, onlineAccessDTO.getLoginId());
			cstmt.setString(4, ipAddress);
			cstmt.setString(5, Long.toString(onlineAccessDTO.getAcctId()));
			cstmt.setString(6, "AC");
			// TODO change hard coded emailExist value
			cstmt.setInt(7, 1);
			cstmt.setInt(8, onlineAccessDTO.getSecurityQuestionID());
			cstmt.setString(9, onlineAccessDTO.getSecurityQuestionAnswer());
			cstmt.registerOutParameter(10, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Security Exception: addSecurityQuestionAnswer");
				throw new EtccSecurityException(
						"Security Exception add Security Question and Answer");
			} else if (result == 0) {
				logger.debug("Error adding security question and answer");
				Array errors = (Array) cstmt.getObject(10);
				onlineAccessDTO.setErrors(OracleUtil.convertToMessages(errors));

				// return false;
			}

		} catch (SQLException e) {
			throw new EtccException(
					"errror adding security question and answer: "
							+ e.getMessage(), e);
		} finally {
			close(cstmt);
		}

		return onlineAccessDTO;
	}

	public OnlineAccessSetupDTO addEmailAddress(String sessionId,
			String ipAddress, OnlineAccessSetupDTO onlineAccessDTO)
			throws EtccException, EtccSecurityException {

		CallableStatement cstmt = null;
		try {
			setTypeMap();
			cstmt = this.conn
					.prepareCall("{? = call OLCSC_ONLINE_ACCESS.Validate_Email_Addr(?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, sessionId);
			cstmt.setString(3, onlineAccessDTO.getLoginId());
			cstmt.setString(4, ipAddress);
			cstmt.setString(5, Long.toString(onlineAccessDTO.getAcctId()));
			cstmt.setString(6, "AC");
			cstmt.setString(7, onlineAccessDTO.getEmailAddress());
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Security Exception: addEmailAddress");
				throw new EtccSecurityException(
						"Security Exception add email address");
			} else if (result == 0) {
				logger.debug("Error adding email address");
				Array errors = (Array) cstmt.getObject(8);
				onlineAccessDTO.setErrors(OracleUtil.convertToMessages(errors));
				// return false;
			}

		} catch (SQLException e) {
			throw new EtccException("errror adding email address: "
					+ e.getMessage(), e);
		} finally {
			close(cstmt);
		}
		return onlineAccessDTO;
	}

	public Collection<String> changePassword(AccountLoginDTO acctLoginDTO,
			@Sensitive String oldPwd, @Sensitive String pwd)
			throws EtccException, EtccSecurityException {

		CallableStatement cstmt = null;
		try {
			setTypeMap();
			cstmt = this.conn
					.prepareCall("{? = call OLCSC_LOGIN_MGMT.change_password(?,?,?,?,?,?,?)}");

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, acctLoginDTO.getDbSessionId());
			cstmt.setString(3, acctLoginDTO.getLastLoginIp());
			cstmt.setString(4, acctLoginDTO.getLoginId());
			cstmt.setString(5, oldPwd);
			cstmt.setString(6, pwd);
			cstmt.setString(7, Long.toString(acctLoginDTO.getAcctId()));
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Security Exception: changePassword");
				throw new EtccSecurityException(
						"Security Exception change password");
			} else if (result == 0) {
				logger.debug("Error changing password");
				// TODO clean up
				Array errors = (Array) cstmt.getObject(8);
				OLC_ERROR_MSG_REC[] errorArray = ((OLC_ERROR_MSG_ARR[]) errors
						.getArray())[0].getArray();
				final Collection<String> strCol = new ArrayList<String>(
						errorArray.length);
				for (OLC_ERROR_MSG_REC error : errorArray) {
					strCol.add(error.getERROR_MSG());
				}
				return strCol;
			}
		} catch (SQLException e) {
			throw new EtccException("errror changing password: "
					+ e.getMessage(), e);
		} finally {
			close(cstmt);
		}
		return null;
	}

	// private Collection convertErrorMsgs(Array errors) throws SQLException {
	// Collection result = null;
	// if (errors != null)
	// {
	// Object array[] = (Object[])errors.getArray();
	// if (array != null && array.length >= 0) {
	// result = new ArrayList();
	// for (int i=0; i<array.length; i++) {
	// ErrorMsgRec msgRec = (ErrorMsgRec) array[i];
	// logger.info("application error:"+msgRec.getErrMsg());
	//
	// result.add(msgRec.getErrMsg());
	// }
	// }
	// }
	// return result;
	// }
}
