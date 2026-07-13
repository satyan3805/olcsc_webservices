package com.etcc.csc.dao;

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
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcTagAuthoritiesRec;

public class OracleTagAuthorityDAO extends TagAuthorityDAO {
	private Logger logger = Logger.getLogger(OracleTagAuthorityDAO.class);

	public OracleTagAuthorityDAO() {
	}

	public Collection getTagAuthorities() throws EtccException,
			EtccSecurityException {
		logger.info("Entering getTagAuthorities() @ OracleTagAuthorityDAO");
		ArrayList result = null;

		try {
			/*
			 * FUNCTION GET_TAG_AUTHORITIES(P_TAG_AUTHORITIES OUT
			 * OLC_TAG_AUTHORITIES_ARR, P_ERROR_ARR OUT OLC_ERROR_MSG_ARR)
			 * RETURN NUMBER;
			 */
			setConnection(Util.getDbConnection());
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_TAG_AUTHORITIES_REC",
					OlcTagAuthoritiesRec.class);
			conn.setTypeMap(typeMap);
			logger.info("getTagAuthorities() :: Calling OLCSC_UTIL.GET_TAG_AUTHORITIES");
			cstmt = conn
					.prepareCall("{? = call OLCSC_UTIL.GET_TAG_AUTHORITIES("
							+ "?, ?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.registerOutParameter(2, Types.ARRAY,
					"OL_OWNER.OLC_TAG_AUTHORITIES_ARR");
			cstmt.registerOutParameter(3, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			Object[] objArray = (Object[]) cstmt.getArray(2).getArray();
			if (objArray != null && objArray.length > 0) {
				logger.debug("Inside if (objArray != null && objArray.length > 0) @ getTagAuthorities()");
				result = new ArrayList();
				for (int i = 0; i < objArray.length; i++) {
					TagAuthorityDTO tagDto = getObject((OlcTagAuthoritiesRec) objArray[i]);
					result.add(tagDto);
				}
			}
			logger.info("Leaving getTagAuthorities() @ OracleTagAuthorityDAO");
			return result;
		} catch (SQLException sqle) {
			logger.error(
					"Error in getTagAuthorities() @ OracleTagAuthorityDAO: "
							+ sqle, sqle);
			throw new EtccException("Error running getTagAuthorities: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}
	}

	private TagAuthorityDTO getObject(OlcTagAuthoritiesRec temp)
			throws SQLException {
		logger.info("Entering getObject() @ OracleTagAuthorityDAO");
		TagAuthorityDTO taDto = null;
		if (temp != null) {
			logger.debug("Inside if (temp != null)) @ getObject()");
			taDto = new TagAuthorityDTO();
			taDto.setBarcodePrefix(temp.getBarcodePrefix());
			taDto.setName(temp.getName());
			taDto.setTagIdentifier(temp.getTagIdentifier());
			taDto.setTaId(temp.getTaId().longValue());
			// taDto.setTaLcId(temp.get);
		}
		logger.info("Leaving getObject() @ OracleTagAuthorityDAO");
		return taDto;
	}

	public String getTagApplicationAgreement(String lang) throws EtccException {
		logger.info("Entering getTagApplicationAgreement() @ OracleTagAuthorityDAO");
		String result = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getTagApplicationAgreement() :: Calling OLCSC_PARAM.get_tt_app_agreement");
			cstmt = conn
					.prepareCall("{? = call OLCSC_PARAM.get_tt_app_agreement(?, ?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, lang);
			cstmt.registerOutParameter(3, Types.CLOB);
			cstmt.registerOutParameter(4, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			if (cstmt.getInt(1) == 1) {
				logger.debug("Inside if (cstmt.getInt(1) == 1) @ getTagApplicationAgreement()");
				result = cstmt.getString(3);
			}

			logger.info("Leaving getTagApplicationAgreement() @ OracleTagAuthorityDAO");
			return result;
		} catch (Throwable t) {
			logger.error(
					"Error in getTagApplicationAgreement() @ OracleTagAuthorityDAO: "
							+ t, t);
			throw new EtccException(
					"Error in getting Tag Application Agreement" + t, t);
		} finally {
			closeConnection();
		}
	}
}
