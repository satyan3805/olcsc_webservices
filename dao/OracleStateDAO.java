package com.etcc.csc.dao;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.plsql.OlcStateCodeRec;

/**
 * Oracle implementation of state dao.
 */
public class OracleStateDAO extends StateDAO {

	private Logger logger = Logger.getLogger(OracleStateDAO.class);

	public OracleStateDAO() {
	}

	public Collection getStates() throws EtccException {
		logger.info("Entering getStates() @ OracleStateDAO");
		ArrayList result = null;
		try {
			result = (ArrayList) getStatesList(null);
		} catch (Throwable t) {
			logger.error("Error in getStates() @ OracleStateDAO ", t);
			throw new EtccException("Error getting states: " + t, t);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getStates() @ OracleStateDAO");
		return result;
	}

	private StateDTO createObject(OlcStateCodeRec rec) throws SQLException {
		logger.info("Entering createObject() @ OracleStateDAO");
		StateDTO state = new StateDTO();
		if (rec != null) {
			logger.debug("Inside if (rec != null) @ createObject()");
			String temp = rec.getDefaultValue();
			if (temp != null && temp.equalsIgnoreCase("Y")) {
				logger.debug("Inside if (temp != null && temp.equalsIgnoreCase(\"Y\")) @ createObject()");
				state.setDefaultValueFlag(true);
			}
			state.setStateCode(rec.getStateCode());
			state.setStateName(rec.getStateName());
		}
		logger.info("Leaving createObject() @ OracleStateDAO");
		return state;
	}

	public Collection getStatesByCountry(String countryCode)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getStatesByCountry() @ OracleStateDAO");
		ArrayList result = null;
		try {
			result = (ArrayList) getStatesList(countryCode);
		} catch (Throwable t) {
			logger.error("Error in getStatesByCountry() @ OracleStateDAO ", t);
			throw new EtccException("Error getting states: " + t, t);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getStatesByCountry() @ OracleStateDAO");
		return result;
	}

	private Collection getStatesList(String countryCode) throws EtccException {
		logger.info("Entering getStatesList() @ OracleStateDAO");
		ArrayList result = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getStatesList() :: Calling OLCSC_UTIL.get_states");
			cstmt = conn
					.prepareCall("{? = call OLCSC_UTIL.get_states(?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_STATE_CODE_REC", OlcStateCodeRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.registerOutParameter(2, Types.ARRAY,
					"OL_OWNER.OLC_STATE_CODE_ARR");
			cstmt.registerOutParameter(3, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setString(4, countryCode);
			cstmt.execute();

			Array states = (Array) cstmt.getObject(2);
			Object array[] = (Object[]) states.getArray();
			if (array != null) {
				logger.debug("Inside if (array != null) @ getStatesList()");
				result = new ArrayList();
				for (int i = 0; i < array.length; i++) {
					result.add(createObject((OlcStateCodeRec) array[i]));
				}
			}
		} catch (Throwable t) {
			logger.error("Error in getStatesList() @ OracleStateDAO: " + t, t);
			throw new EtccException("Error getting states: " + t, t);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getStatesList() @ OracleStateDAO");
		return result;
	}
}
