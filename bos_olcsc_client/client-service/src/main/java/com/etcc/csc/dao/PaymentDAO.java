package com.etcc.csc.dao;

import java.math.BigDecimal;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.Violation;
import com.etcc.csc.service.PaymentInterface;

public abstract class PaymentDAO extends BaseDAO implements PaymentInterface {

	/**
	 * Method is deprecated due to HCTRA changes.  No replacement is available.
	 * @deprecated no replacement available
	 * @return <tt>false</tt> always
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	public boolean postViolations(BigDecimal docId, String docType, String dbSessionId,
			String ipAddress, String loginId, Violation[] violations)
			throws EtccErrorMessageException, EtccException {
		boolean success = false;
		try {

			/*
			 * OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
			 * 
			 * OLC_UNINVOICED_VIOLS_ARR UNINVOICED_VIOLS_ARR = new OLC_UNINVOICED_VIOLS_ARR();`
			 * UNINVOICED_VIOLS_ARR.setArray(PaymentDetailUtil.convertToOLC_UNINVOICED_VIOLS_RECs(violations));
			 * OLC_UNINVOICED_VIOLS_ARR[] P_UNINVOICED_VIOLS = new OLC_UNINVOICED_VIOLS_ARR[] { UNINVOICED_VIOLS_ARR };
			 * OLCSC_VTOLL plsql = new OLCSC_VTOLL(conn);
			 * 
			 * int result = plsql.PAY_UNINVOICED_VIOLS(P_UNINVOICED_VIOLS, dbSessionId, ipAddress, loginId, docId, "Y",
			 * O_ERROR_MSG_ARR).intValue();
			 * 
			 * if (result == 1) { success = true; } else { if (O_ERROR_MSG_ARR[0] != null &&
			 * O_ERROR_MSG_ARR[0].getArray() != null && O_ERROR_MSG_ARR[0].getArray().length > 0) { OLC_ERROR_MSG_REC[]
			 * errorMsgRecs = O_ERROR_MSG_ARR[0].getArray(); EtccErrorMessageException em = new
			 * EtccErrorMessageException("postViolations error message"); for (int i = 0; i < errorMsgRecs.length; i++)
			 * { em.addRecoverable(errorMsgRecs[i].getERROR_MSG()); } throw em; } else { throw new
			 * EtccException("postViolations fatal error"); } }
			 */
		} finally {
		    //nothing to do.
		}
		return success;
	}
}
