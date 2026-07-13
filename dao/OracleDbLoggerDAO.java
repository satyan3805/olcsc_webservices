package com.etcc.csc.dao;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DateUtil;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Util;
import com.etcc.csc.plsql.OlcErrorMsgRec;

/**
 * Oracle implementation of the Menu DAO.
 */
public class OracleDbLoggerDAO extends DbLoggerDAO {
	private Logger logger = Logger.getLogger(OracleDbLoggerDAO.class);

	public OracleDbLoggerDAO() {
	}

	public String logError(String message, String stack, String sessionId,
			String ip) throws EtccException {
		logger.info("Entering logError() @ OracleDbLoggerDAO");
		/*
		 * olcsc_error_util.error_handle(p_error_code => :p_error_code,
		 * p_error_message => :p_error_message, p_procedure_name =>
		 * :p_procedure_name, p_document_id => :p_document_id, p_u_doc_id =>
		 * :p_u_doc_id, p_doc_type => :p_doc_type, p_session_id =>
		 * :p_session_id, p_ip_address => :p_ip_address, p_display_message =>
		 * :p_display_message, p_custom_error => :p_custom_error, p_sec_custom1
		 * => :p_sec_custom1, p_sec_custom2 => :p_sec_custom2, p_sec_custom3 =>
		 * :p_sec_custom3, p_base_lang_id => :p_base_lang_id, p_lang_id =>
		 * :p_lang_id, p_error_msg_arr => p_error_msg_arr, p_email => :p_email);
		 */
		String result = "";

		try {
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", OlcErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_ERROR_MSG_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					new OlcErrorMsgRec[] { new OlcErrorMsgRec() });

			byte idx = 1;
			setConnection(Util.getDbConnection());
			logger.info("logError() :: Calling OLCSC_ERROR_UTIL.error_handle");
			cstmt = conn.prepareCall("{? = call OLCSC_ERROR_UTIL.error_handle("
					+ "-20014,?,'JAVA',null,null,null,?,?,null,null,null,null,"
					+ "null, 'EN','EN' ,?,'Y')}");
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, ((DateUtil.getShortDateTime(new Date())
					+ "-" + stack).substring(0, 1500)));
			cstmt.setString(idx++, sessionId);
			cstmt.setString(idx++, ip);
			cstmt.setArray(idx, array);
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.executeUpdate();
			int exists = cstmt.getInt(1);
			if (exists == 1) {
				logger.debug("Inside if (exists == 1) @ logError()");
				result = "123";
			}

		} catch (Throwable t) {
			// throw new EtccException("Error logging error: " + th, th);
			logger.error(
					"Error in logError() @ OracleDbLoggerDAO :: logging error: "
							+ t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving logError() @ OracleDbLoggerDAO");
		return result;
	}

	public String logSecurityViolation(String message) throws EtccException {
		logger.info("Entering logSecurityViolation() @ OracleDbLoggerDAO");
		String result = "";

		try {
			setConnection(Util.getDbConnection());
		} catch (Throwable t) {
			logger.error("Error in logSecurityViolation() @ OracleDbLoggerDAO "
					+ t, t);
			throw new EtccException("Error logging security violation: " + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving logSecurityViolation() @ OracleDbLoggerDAO");
		return result;
	}
}