package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcAcctSecQuestRec;
import com.etcc.csc.plsql.OlcSQuestionsRec;

public class OracleSecurityQuestionDAO extends SecurityQuestionDAO {
	private Logger logger = Logger.getLogger(OracleSecurityQuestionDAO.class);

	public OracleSecurityQuestionDAO() {
	}

	public ArrayList<SecurityQuestionDTO> getSecurityQuestions(String locale)
			throws EtccException {
		logger.info("Entering getSecurityQuestions() @ OracleSecurityQuestionDAO");
		ArrayList<SecurityQuestionDTO> result = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getSecurityQuestions() :: Calling OLCSC_UTIL.get_security_questions");
			cstmt = conn
					.prepareCall("{? = call OLCSC_UTIL.get_security_questions(?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_S_QUESTIONS_REC", OlcSQuestionsRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.ARRAY,
					"OL_OWNER.OLC_S_QUESTIONS_ARR");
			cstmt.setString(2, "EN");
			cstmt.setString(3, locale);
			cstmt.registerOutParameter(4, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			Array securityQuestions = (Array) cstmt.getObject(1);
			Object array[] = (Object[]) securityQuestions.getArray();
			if (array != null) {
				logger.debug("Inside if (array != null) @ getSecurityQuestions()");
				result = new ArrayList<SecurityQuestionDTO>();
				String questionId;
				String question = null;
				String translatedQuestion = null;
				String questionOrder = null;
				for (int i = 0; i < array.length; i++) {
					OlcSQuestionsRec questionData = (OlcSQuestionsRec) array[i];
					questionId = questionData.getQuestionId().toString();
					question = questionData.getSecurityQuestion();
					translatedQuestion = questionData
							.getTransSecurityQuestion();
					questionOrder = questionData.getSecurityQuestionOrder()
							.toString();
					SecurityQuestionDTO questionItem = new SecurityQuestionDTO(
							questionId, question, translatedQuestion,
							questionOrder);
					result.add(questionItem);
				}
			}
		} catch (Throwable t) {
			logger.error(
					"Error in getSecurityQuestions() @ OracleSecurityQuestionDAO: "
							+ t, t);
			throw new EtccException("error getting security questions " + t, t);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getSecurityQuestions() @ OracleSecurityQuestionDAO");
		return result;
	}

	public ArrayList<SecurityQuestionDTO> getSecurityQuestionsnAnswers(
			AccountLoginDTO acctLoginDto, String locale) throws EtccException {
		logger.info("Entering getSecurityQuestionsnAnswers() @ OracleSecurityQuestionDAO");
		ArrayList<SecurityQuestionDTO> result = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getSecurityQuestionsnAnswers() :: Calling OLCSC_ACCT_MGMT.Get_Acct_Security_Questions");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Acct_Security_Questions(?,?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCT_SEC_QUEST_REC",
					OlcAcctSecQuestRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			byte idx = 1;
			cstmt.setInt(idx++, Types.INTEGER);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ACCT_SEC_QUEST_ARR");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setString(idx, locale);
			cstmt.execute();

			Array securityQuestions = (Array) cstmt.getObject(idx - 2);
			Object array[] = (Object[]) securityQuestions.getArray();
			if (array != null) {
				logger.debug("Inside if (array != null) @ getSecurityQuestionsnAnswers()");
				result = new ArrayList<SecurityQuestionDTO>();
				String questionId;
				String answer;
				String questionOrder;
				for (int i = 0; i < array.length; i++) {
					OlcAcctSecQuestRec questionData = (OlcAcctSecQuestRec) array[i];
					questionId = questionData.getQuestionId().toString();
					answer = questionData.getSecurityQuestionAnswer();
					questionOrder = questionData.getSecurityQuestionOrder()
							.toString();
					SecurityQuestionDTO questionItem = new SecurityQuestionDTO(
							questionId, answer, questionOrder);
					result.add(questionItem);
				}
			}
		} catch (Throwable t) {
			logger.error(
					"Error in getSecurityQuestionsnAnswers() @ OracleSecurityQuestionDAO: "
							+ t, t);
			throw new EtccException("error getting security questions " + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getSecurityQuestionsnAnswers() @ OracleSecurityQuestionDAO");
		return result;
	}

	public String getSysParam(String paramName) throws EtccException {
		logger.info("Entering getSysParam() @ OracleSecurityQuestionDAO");
		String result = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getSysParam() :: Calling OLCSC_UTIL.GETSYSPARM");
			cstmt = conn.prepareCall("{? = call OLCSC_UTIL.GETSYSPARM('"
					+ paramName + "')}");
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.execute();
			result = cstmt.getString(1);
		} catch (SQLException sqle) {
			logger.error("Error in getSysParam() @ OracleSecurityQuestionDAO: "
					+ sqle, sqle);
			throw new EtccException("Error running getSysParam (" + paramName
					+ "): " + ": " + sqle, sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getSysParam() @ OracleSecurityQuestionDAO");
		return result;
	}

	public int saveAnswers(AccountLoginDTO acctLoginDto,
			List<SecurityQuestionDTO> listOfQuestionsnAnswers)
			throws EtccException {
		logger.info("Entering saveAnswers() @ OracleSecurityQuestionDAO");
		int result = 0;

		try {
			setConnection(Util.getDbConnection());
			logger.info("saveAnswers() :: Calling OLCSC_ACCT_MGMT.Set_Acct_Security_Questions");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Set_Acct_Security_Questions(?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCT_SEC_QUEST_REC",
					OlcAcctSecQuestRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.INTEGER);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_ACCT_SEC_QUEST_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertToOracleSecurityQuestions(listOfQuestionsnAnswers));
			cstmt.setArray(idx++, array);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			result = cstmt.getInt(1);
		} catch (SQLException sqle) {
			logger.error("Error in saveAnswers() @ OracleSecurityQuestionDAO: "
					+ sqle, sqle);
			throw new EtccException("Error running saveAnswers (): " + ": "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving saveAnswers() @ OracleSecurityQuestionDAO");
		return result;
	}

	private OlcAcctSecQuestRec[] convertToOracleSecurityQuestions(
			List<SecurityQuestionDTO> listOfQuestionsnAnswers)
			throws SQLException {
		logger.info("Entering convertToOracleSecurityQuestions() @ OracleSecurityQuestionDAO");
		if (listOfQuestionsnAnswers != null
				&& listOfQuestionsnAnswers.size() > 0) {
			logger.debug("Inside if (listOfQuestionsnAnswers != null && listOfQuestionsnAnswers.size() > 0) @ convertToOracleSecurityQuestions()");
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
			logger.info("Leaving convertToOracleSecurityQuestions() @ OracleSecurityQuestionDAO");
			return result;
		}
		return null;
	}
}
