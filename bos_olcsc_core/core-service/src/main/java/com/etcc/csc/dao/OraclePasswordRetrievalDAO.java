package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcAcctSecQuestRec;

public class OraclePasswordRetrievalDAO extends PasswordRetrievalDAO {

	private Logger logger = Logger.getLogger(OraclePasswordRetrievalDAO.class);

	public OnlineAccessSetupDTO retrieveAccountInfo(
			OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,
			String sessionId, UserEnvDTO userEnvDto, String locale)
			throws EtccException, EtccSecurityException {
		logger.info("Entering retrieveAccountInfo() @ OraclePasswordRetrievalDAO");
		try {
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
			setConnection(Util.getDbConnection());
			logger.info("retrieveAccountInfo() :: Calling OLCSC_ONLINE_ACCESS.Forgot_Pwd_Step1");
			cstmt = conn.prepareCall("{? = call OLCSC_ONLINE_ACCESS."
					+ "Forgot_Pwd_Step1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?, ?, ?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCT_SEC_QUEST_REC",
					OlcAcctSecQuestRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);

			if (onlineAccessDTO.getAcctId() == 0) {
				logger.debug("Inside if (onlineAccessDTO.getAcctId() == 0) @ retrieveAccountInfo()");
				cstmt.setString(idx++, null);
			} else {
				logger.debug("Inside else of if (onlineAccessDTO.getAcctId() == 0) @ retrieveAccountInfo()");
				cstmt.setString(idx++,
						Long.toString(onlineAccessDTO.getAcctId()));
			}

			cstmt.setString(idx++, Constants.LOGIN_TYPE_ACCOUNT);
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
			cstmt.setString(idx++, onlineAccessDTO.getDriverLicState());
			cstmt.setString(idx++, onlineAccessDTO.getDriverLicNbr());
			cstmt.setString(idx++, onlineAccessDTO.getCompanyTaxId());
			cstmt.setString(idx++, "EN");
			cstmt.setString(idx++, locale);
			cstmt.setString(idx++, onlineAccessDTO.getAcctCard());
			cstmt.registerOutParameter(2, Types.VARCHAR); // acct id
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ACCT_SEC_QUEST_ARR"); // sec question
			cstmt.registerOutParameter(idx++, Types.SMALLINT); // email addr
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // session id
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ retrieveAccountInfo()");
				logger.debug("Security Exception: retrieveAccountInfo");
				throw new EtccSecurityException(
						"Security Exception retrieving account info");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ retrieveAccountInfo()");
				logger.debug("Error retrieving account info");
				// TODO clean up
				Array errors = (Array) cstmt.getObject(idx);
				convertErrorMsgs(errors);
				return null;
			}

			String accountId = cstmt.getString(2);
			onlineAccessDTO.setAcctId(Long.parseLong(accountId));

			Array securityQuestions = (Array) cstmt.getObject(idx - 7);
			List<SecurityQuestionDTO> securityQuestion = null;

			Object array[] = (Object[]) securityQuestions.getArray();

			if (array != null) {
				logger.debug("Inside if (array != null) @ retrieveAccountInfo()");
				securityQuestion = new ArrayList<SecurityQuestionDTO>();
				String questionId;
				String answer = null;

				String questionOrder = null;
				for (int i = 0; i < array.length; i++) {
					OlcAcctSecQuestRec questionData = (OlcAcctSecQuestRec) array[i];
					questionId = questionData.getQuestionId().toString();
					// question=questionData.getSecurityQuestion();
					// translatedQuestion=
					// questionData.getTransSecurityQuestion();
					answer = questionData.getSecurityQuestionAnswer();
					questionOrder = questionData.getSecurityQuestionOrder()
							.toString();
					SecurityQuestionDTO questionItem = new SecurityQuestionDTO(
							questionId, answer, questionOrder);
					securityQuestion.add(questionItem);
				}
			}

			String emailAddress = (cstmt.getInt(idx - 6) == 1) ? "a" : null;

			logger.debug("emailExist:" + (emailAddress != null));
			onlineAccessDTO.setListOfQuestions(securityQuestion);
			onlineAccessDTO.setEmailAddress(emailAddress);

			onlineAccessDTO.setDbSessionId(cstmt.getString(idx - 5));
			onlineAccessDTO.setMinTag(cstmt.getLong(idx - 4));
			onlineAccessDTO.setMaxTag(cstmt.getLong(idx - 3));
			onlineAccessDTO.setMinVehicle(cstmt.getLong(idx - 2));
			onlineAccessDTO.setMaxVehicle(cstmt.getLong(idx - 1));

		} catch (Throwable t) {
			logger.error(
					"Error in retrieveAccountInfo() @ OraclePasswordRetrievalDAO: "
							+ t, t);
			throw new EtccException(
					"Error retrieveing account information" + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving retrieveAccountInfo() @ OraclePasswordRetrievalDAO");
		return onlineAccessDTO;
	}

	public boolean validateSecurityAnswer(String sessionId, String ipAddress,
			OnlineAccessSetupDTO onlineAccessDTO) throws EtccException,
			EtccSecurityException {
		logger.info("Entering validateSecurityAnswer() @ OraclePasswordRetrievalDAO");
		boolean valid = false;

		try {
			setConnection(Util.getDbConnection());
			Map typeMap = new HashMap();

			typeMap.put("OL_OWNER.OLC_ACCT_SEC_QUEST_REC",
					OlcAcctSecQuestRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			logger.info("validateSecurityAnswer() :: Calling OLCSC_ONLINE_ACCESS.Password_Retrieval");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ONLINE_ACCESS.Password_Retrieval(?,?,?,?,?,?,?,?)}");
			setErrorTypeMap();
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, sessionId);
			cstmt.setString(3, onlineAccessDTO.getLoginId());
			cstmt.setString(4, ipAddress);
			// TODO change hard coded emailExist value
			cstmt.setInt(5, 1);
			cstmt.setString(6, Long.toString(onlineAccessDTO.getAcctId()));
			cstmt.setString(7, "AC");
			logger.debug("answer:"
					+ onlineAccessDTO.getSecurityQuestionAnswer());
			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_ACCT_SEC_QUEST_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertToOracleSecurityQuestions(onlineAccessDTO
							.getListOfQuestions()));
			cstmt.setArray(8, array);

			// cstmt.setString(8, onlineAccessDTO.getSecurityQuestionAnswer());
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();
			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ validateSecurityAnswer()");
				logger.debug("Security Exception: validateSecurityAnswer");
				throw new EtccSecurityException(
						"Security Exception Validating Security Answer");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ validateSecurityAnswer()");
				logger.debug("Error validating security answer");
				// TODO clean up
				Array errors = (Array) cstmt.getObject(9);
				convertErrorMsgs(errors);
				logger.info("Leaving validateSecurityAnswer() @ OraclePasswordRetrievalDAO");
				return false;
			}

			valid = true;

		} catch (Throwable t) {
			logger.error(
					"Error in validateSecurityAnswer() @ OraclePasswordRetrievalDAO: "
							+ t, t);
			throw new EtccException("Errror validating security answer" + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving validateSecurityAnswer() @ OraclePasswordRetrievalDAO");
		return valid;
	}

	public boolean addSecurityQuestionAnswer(String sessionId,
			String ipAddress, OnlineAccessSetupDTO onlineAccessDTO)
			throws EtccException, EtccSecurityException {
		logger.info("Entering addSecurityQuestionAnswer() @ OraclePasswordRetrievalDAO");
		boolean valid = false;

		try {
			setConnection(Util.getDbConnection());
			Map typeMap = new HashMap();

			typeMap.put("OL_OWNER.OLC_ACCT_SEC_QUEST_REC",
					OlcAcctSecQuestRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			logger.info("addSecurityQuestionAnswer() :: Calling OLCSC_ONLINE_ACCESS.Add_Sec_Question");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ONLINE_ACCESS.Add_Sec_Question(?,?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, sessionId);
			cstmt.setString(3, onlineAccessDTO.getLoginId());
			cstmt.setString(4, ipAddress);
			cstmt.setString(5, Long.toString(onlineAccessDTO.getAcctId()));
			cstmt.setString(6, "AC");
			// TODO change hard coded emailExist value
			cstmt.setInt(7, 1);
			// cstmt.setString(8, onlineAccessDTO.getSecurityQuestion());

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_ACCT_SEC_QUEST_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertToOracleSecurityQuestions(onlineAccessDTO
							.getListOfQuestions()));
			cstmt.setArray(8, array);
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ addSecurityQuestionAnswer()");
				logger.debug("Security Exception: addSecurityQuestionAnswer");
				throw new EtccSecurityException(
						"Security Exception add Security Question and Answer");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ addSecurityQuestionAnswer()");
				logger.debug("Error adding security question and answer");
				// TODO clean up
				Array errors = (Array) cstmt.getObject(10);
				convertErrorMsgs(errors);
				logger.info("Leaving addSecurityQuestionAnswer() @ OraclePasswordRetrievalDAO");
				return false;
			}
			valid = true;

		} catch (Throwable t) {
			logger.error(
					"Error in addSecurityQuestionAnswer() @ OraclePasswordRetrievalDAO: "
							+ t, t);
			throw new EtccException(
					"Errror adding security question and answer" + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving addSecurityQuestionAnswer() @ OraclePasswordRetrievalDAO");
		return valid;
	}

	public boolean addEmailAddress(String sessionId, String ipAddress,
			OnlineAccessSetupDTO onlineAccessDTO) throws EtccException,
			EtccSecurityException {
		logger.info("Entering addEmailAddress() @ OraclePasswordRetrievalDAO");
		try {
			setConnection(Util.getDbConnection());
			setErrorTypeMap();
			logger.info("addEmailAddress() :: Calling OLCSC_ONLINE_ACCESS.Validate_Email_Addr");
			cstmt = conn
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
				logger.debug("Inside if (result == -1) @ addEmailAddress()");
				logger.debug("Security Exception: addEmailAddress");
				throw new EtccSecurityException(
						"Security Exception add email address");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ addEmailAddress()");
				logger.debug("Error adding email address");
				// TODO clean up
				Array errors = (Array) cstmt.getObject(8);
				convertErrorMsgs(errors);
				logger.info("Leaving addEmailAddress() @ OraclePasswordRetrievalDAO");
				return false;
			}
		} catch (Throwable t) {
			logger.error(
					"Error in addEmailAddress() @ OraclePasswordRetrievalDAO: "
							+ t, t);
			throw new EtccException("errror adding email address" + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving addEmailAddress() @ OraclePasswordRetrievalDAO");
		return true;
	}

	public Collection changePassword(AccountLoginDTO acctLoginDTO,
			String oldPwd, String pwd) throws EtccException,
			EtccSecurityException {
		logger.info("Entering changePassword() @ OraclePasswordRetrievalDAO");
		try {
			setConnection(Util.getDbConnection());
			setErrorTypeMap();
			logger.info("changePassword() :: Calling OLCSC_LOGIN_MGMT.change_password");
			cstmt = conn
					.prepareCall("{? = call OLCSC_LOGIN_MGMT.change_password(?,?,?,?,?,?,?,?)}");

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, acctLoginDTO.getDbSessionId());
			cstmt.setString(3, acctLoginDTO.getLastLoginIp());
			cstmt.setString(4, acctLoginDTO.getLoginId());
			cstmt.setString(5, oldPwd);
			cstmt.setString(6, pwd);
			cstmt.setString(7, Long.toString(acctLoginDTO.getAcctId()));
			cstmt.setInt(8, 21502);
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ changePassword()");
				logger.debug("Security Exception: changePassword");
				throw new EtccSecurityException(
						"Security Exception change password");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ changePassword()");
				logger.error("Error changing password");
				// TODO clean up
				Array errors = (Array) cstmt.getObject(9);
				logger.info("Leaving changePassword() @ OraclePasswordRetrievalDAO");
				return convertErrorMsgs(errors);
			}
		} catch (SQLException t) {
			logger.error(
					"Error in changePassword() @ OraclePasswordRetrievalDAO: "
							+ t, t);
			throw new EtccException("Errror changing password" + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving changePassword() @ OraclePasswordRetrievalDAO");
		return null;
	}

	public Collection resetPassword(AccountLoginDTO acctLoginDTO, String pwd)
			throws EtccException, EtccSecurityException {
		logger.info("Entering resetPassword() @ OraclePasswordRetrievalDAO");
		try {
			setConnection(Util.getDbConnection());
			setErrorTypeMap();
			logger.info("resetPassword() :: Calling OLCSC_LOGIN_MGMT.reset_password");
			cstmt = conn
					.prepareCall("{? = call OLCSC_LOGIN_MGMT.reset_password(?,?,?,?,?,?)}");

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, acctLoginDTO.getDbSessionId());
			cstmt.setString(3, acctLoginDTO.getLastLoginIp());
			cstmt.setString(4, acctLoginDTO.getLoginId());
			cstmt.setString(5, pwd);
			cstmt.setString(6, Long.toString(acctLoginDTO.getAcctId()));
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.executeUpdate();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ resetPassword()");
				logger.error("Error in resetPassword() @ OraclePasswordRetrievalDAO :: Security Exception");
				throw new EtccSecurityException(
						"Security Exception change password");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ resetPassword()");
				logger.debug("Error changing password");
				// TODO clean up
				Array errors = (Array) cstmt.getObject(7);
				logger.info("Leaving resetPassword() @ OraclePasswordRetrievalDAO");
				return convertErrorMsgs(errors);
			}
		} catch (SQLException t) {
			logger.error(
					"Error in resetPassword() @ OraclePasswordRetrievalDAO: "
							+ t, t);
			throw new EtccException("Errror changing password" + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving resetPassword() @ OraclePasswordRetrievalDAO");
		return null;
	}

	private void setErrorTypeMap() throws SQLException {
		logger.info("Entering setErrorTypeMap() @ OraclePasswordRetrievalDAO");
		Map typeMap = new HashMap();
		typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
		conn.setTypeMap(typeMap);
		logger.info("Leaving setErrorTypeMap() @ OraclePasswordRetrievalDAO");
	}

	private Collection convertErrorMsgs(Array errors) throws SQLException {
		logger.info("Entering convertErrorMsgs() @ OraclePasswordRetrievalDAO");
		Collection result = null;
		if (errors != null) {
			Object array[] = (Object[]) errors.getArray();
			if (array != null && array.length >= 0) {
				result = new ArrayList();
				for (int i = 0; i < array.length; i++) {
					ErrorMsgRec msgRec = (ErrorMsgRec) array[i];
					logger.info("application error:" + msgRec.getErrMsg());
					result.add(msgRec.getErrMsg());
				}
			}
		}

		logger.info("Leaving convertErrorMsgs() @ OraclePasswordRetrievalDAO");
		return result;
	}

	private OlcAcctSecQuestRec[] convertToOracleSecurityQuestions(
			List<SecurityQuestionDTO> listOfQuestionsnAnswers)
			throws SQLException {
		logger.info("Entering convertToOracleSecurityQuestions() @ OraclePasswordRetrievalDAO");
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

			logger.info("Leaving convertToOracleSecurityQuestions() @ OraclePasswordRetrievalDAO");
			return result;
		}
		return null;
	}
}
