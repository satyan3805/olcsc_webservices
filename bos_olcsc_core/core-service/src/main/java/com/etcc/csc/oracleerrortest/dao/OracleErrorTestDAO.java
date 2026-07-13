package com.etcc.csc.oracleerrortest.dao;

import java.sql.Array;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.etcc.csc.common.EtccException;

public class OracleErrorTestDAO extends ErrorTestDAO {
	public OracleErrorTestDAO() {
	}

	public Collection performETest(String param1) throws EtccException {

		Collection result = null;
		try {
			cstmt = conn.prepareCall("{? = call get_err_msg(?)}");

			Map typeMap = new HashMap();
			typeMap.put("OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.registerOutParameter(2, Types.ARRAY, "OLC_ERROR_MSG_ARR");
			cstmt.executeUpdate();

			Array errors = (Array) cstmt.getObject(2);
			Object array[] = (Object[]) errors.getArray();

			// could be contained in an ErrorMsgDTO
			if (array != null && array.length >= 0) {
				result = new ArrayList();
				for (int i = 0; i < array.length; i++) {
					ErrorMsgRec msgRec = (ErrorMsgRec) array[i];
					result.add(msgRec.getErrMsg());
				}
			}
		} catch (Throwable t) {
			throw new EtccException("Error getting errors: " + t, t);
		} finally {
			closeConnection();
		}
		return result;
	}
}