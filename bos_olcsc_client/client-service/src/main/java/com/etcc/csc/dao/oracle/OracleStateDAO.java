package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.StateDAO;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.dto.bean.OlcAcctSummaryRecBean;
import com.etcc.csc.dto.bean.OlcCorpInvRecP;
import com.etcc.csc.plsql.OLC_STATE_CODE_REC;

/**
 * Oracle implementation of state DAO.
 */
public class OracleStateDAO extends StateDAO {
//    private Logger logger = Logger.getLogger(OracleStateDAO.class);

    @Override
    public StateDTO[] loadStates() throws EtccException {
        StateDTO[] result = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_UTIL.get_states(?, ?)}");

            setTypeMap();
            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.registerOutParameter(2, Types.ARRAY, "OL_OWNER.OLC_STATE_CODE_ARR");
            cstmt.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.execute();

            Object obj = cstmt.getObject(2);
            result = convertDBTypeToJavaForStateCodeArray(obj);

        } catch (Exception t) {
            throw new EtccException("Error getting states:  " + t, t);
        } finally {
            close(cstmt);
        }
        return result;
    }

	private StateDTO[] convertDBTypeToJavaForStateCodeArray(Object object)
			throws ClassNotFoundException, SQLException {
		StateDTO[] statesArr = null;
		ARRAY oraStateArray = null;
		if (object instanceof weblogic.jdbc.wrapper.Array) {
			oraStateArray = (ARRAY) (((weblogic.jdbc.wrapper.Array) object)
					.unwrap(Class.forName("oracle.sql.ARRAY")));
		} else {
			oraStateArray = (ARRAY) object;
		}
		statesArr = new StateDTO[oraStateArray.length()];
		ResultSet rs = oraStateArray.getResultSet();
		StateDTO stateDTO = null;
		int index = 0;
		while (rs.next()) {
			stateDTO = new StateDTO();
			STRUCT obj = (STRUCT) rs.getObject(2);
			Object[] attrs = obj.getAttributes();

			stateDTO.setStateCode((String) attrs[0]);
			stateDTO.setStateName((String) attrs[1]);
			String temp = (String) attrs[2];
			if (temp != null && temp.equalsIgnoreCase("Y")) {
				stateDTO.setDefaultValueFlag(true);
			}
			stateDTO.setDriverLicensePattern((String) attrs[3]);

			statesArr[index++] = stateDTO;
		}
		return statesArr;
	}
}
