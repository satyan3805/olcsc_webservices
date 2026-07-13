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
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.CountryDTO;
import com.etcc.csc.plsql.OlcCountryCodeRec;

public class OracleCountryDAO extends CountryDAO {
	private Logger logger = Logger.getLogger(OracleCountryDAO.class);

	public OracleCountryDAO() {
	}

	public Collection getCountry() throws EtccException {
		logger.info("Entering getCountry() @ OracleCountryDAO");
		ArrayList result = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getCountry() :: Calling OLCSC_UTIL.GET_COUNTRIES");
			cstmt = conn
					.prepareCall("{? = call OLCSC_UTIL.GET_COUNTRIES(?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_COUNTRY_CODE_REC",
					OlcCountryCodeRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.registerOutParameter(2, Types.ARRAY,
					"OL_OWNER.OLC_COUNTRY_CODE_ARR");
			cstmt.registerOutParameter(3, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			Array country = (Array) cstmt.getObject(2);
			Object array[] = (Object[]) country.getArray();
			if (array != null) {
				logger.debug("Inside if (array != null) @ createObject()");
				result = new ArrayList();
				for (int i = 0; i < array.length; i++) {
					result.add(createObject((OlcCountryCodeRec) array[i]));
				}
			}
		} catch (Throwable t) {
			logger.error("Error in getCountry() @ OracleCountryDAO " + t, t);
			throw new EtccException("Error getting states: " + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getCountry() @ OracleCountryDAO");
		return result;
	}

	private CountryDTO createObject(OlcCountryCodeRec rec) throws SQLException {
		logger.info("Entering createObject() @ OracleCountryDAO");
		CountryDTO country = new CountryDTO();

		if (rec != null) {
			logger.debug("Inside if (rec != null) @ createObject()");
			country.setCountryCode(rec.getCountryCode());
			country.setCountryName(rec.getCountryName());
		}

		logger.info("Leaving createObject() @ OracleCountryDAO");
		return country;
	}

	public String getDefaultCountryCode() throws EtccException {
		logger.info("Entering getDefaultCountryCode() @ OracleCountryDAO");
		String result = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getDefaultCountryCode() :: Calling OLCSC_UTIL.GETSYSPARM");
			cstmt = conn.prepareCall("{? = call "
					+ "OLCSC_UTIL.GETSYSPARM('DEFAULT_COUNTRY_CODE')}");

			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.execute();

			result = cstmt.getString(1);
		} catch (SQLException sqle) {
			logger.error(
					"Error in getDefaultCountryCode() @ OracleCreditCardDAO "
							+ sqle, sqle);
			throw new EtccException(
					"Error running getSysParam (DEFAULT_COUNTRY_CODE): " + ": "
							+ sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getDefaultCountryCode() @ OracleCountryDAO");
		return result;
	}
}
