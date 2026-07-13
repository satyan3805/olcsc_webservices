package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.DateUtil;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Util;
import com.etcc.csc.datatype.Invoice;
import com.etcc.csc.datatype.OlcUninvoicedViolsRecBean;
import com.etcc.csc.datatype.OlcVpsInvoicesRecBean;
import com.etcc.csc.datatype.PaymentDetail;
import com.etcc.csc.datatype.PaymentDetailUtil;
import com.etcc.csc.datatype.PaymentResponse;
import com.etcc.csc.datatype.Violation;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OLCSC_ACCT_MGMT;
import com.etcc.csc.plsql.OLCSC_PARAM;
import com.etcc.csc.plsql.OLCSC_REP;
import com.etcc.csc.plsql.OLCSC_VB_INV;
import com.etcc.csc.plsql.OLCSC_VTOLL;
import com.etcc.csc.plsql.OlcAccountTagArr;
import com.etcc.csc.plsql.OlcAccountTagRec;
import com.etcc.csc.plsql.OlcErrorMsgArr;
import com.etcc.csc.plsql.OlcErrorMsgRec;
import com.etcc.csc.plsql.OlcPaymentInfoRec;
import com.etcc.csc.plsql.OlcUninvoicedViolsArr;
import com.etcc.csc.plsql.OlcUninvoicedViolsRec;
import com.etcc.csc.plsql.OlcVbInvoicesArr;
import com.etcc.csc.plsql.OlcVbInvoicesRec;
import com.etcc.csc.plsql.OlcVpsInvoicesRec;

public class OracleZipCashDAO extends ZipCashDAO {
	private Logger logger = Logger.getLogger(OracleZipCashDAO.class);

	public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress,
			String loginId, long docId, String docType, String licPlate,
			String licState, BigDecimal transactionId)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering getPaymentDetail() @ OracleZipCashDAO");
		PaymentDetail paymentDetail = null;
		try {
			setConnection(Util.getDbConnection());
			OlcAccountTagArr[] P_ACCOUNT_TAG_ARR = new OlcAccountTagArr[] { new OlcAccountTagArr() };
			String[] P_FORCE_PMT = new String[] { "" };
			BigDecimal[] P_PMT_AMT = new BigDecimal[] { new BigDecimal(0) };
			BigDecimal[] P_DEP_AMT = new BigDecimal[] { new BigDecimal(0) };
			BigDecimal[] P_RTL_TRXN_ID = new BigDecimal[] { transactionId };

			OlcVbInvoicesArr[] O_VPS_INVOICES_ARR = new OlcVbInvoicesArr[] { new OlcVbInvoicesArr() };
			OlcUninvoicedViolsArr[] O_UNINVOICED_VIOLS_ARR = new OlcUninvoicedViolsArr[] { new OlcUninvoicedViolsArr() };
			OlcErrorMsgArr[] O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new OlcErrorMsgArr() };
			int result = new OLCSC_VB_INV(conn).openInvoices(dbSessionId,
					ipAddress, loginId, new BigDecimal(docId), docType,
					licPlate, licState, P_ACCOUNT_TAG_ARR, P_FORCE_PMT,
					P_PMT_AMT, P_DEP_AMT, P_RTL_TRXN_ID, O_VPS_INVOICES_ARR,
					O_UNINVOICED_VIOLS_ARR, O_ERROR_MSG_ARR).intValue();
			if (result == 1) {
				logger.debug("Inside if (result == 1) @ getPaymentDetail()");
				paymentDetail = new PaymentDetail();
				if (P_ACCOUNT_TAG_ARR[0] != null
						&& !ArrayUtils.isEmpty(P_ACCOUNT_TAG_ARR[0].getArray())) {
					logger.debug("Inside if (P_ACCOUNT_TAG_ARR[0] != null && !ArrayUtils.isEmpty(P_ACCOUNT_TAG_ARR[0].getArray())) @ getPaymentDetail()");
					OlcAccountTagRec[] accountTagRecs = P_ACCOUNT_TAG_ARR[0]
							.getArray();
					TagDTO[] tags = new TagDTO[accountTagRecs.length];
					for (int i = 0; i < accountTagRecs.length; i++) {
						tags[i] = new TagDTO();
						tags[i].setLicPlate(accountTagRecs[i].getLicPlate());
						tags[i].setLicState(accountTagRecs[i].getLicState());
						tags[i].setVehicleYear(accountTagRecs[i]
								.getVehicleYear());
						tags[i].setVehicleColor(accountTagRecs[i]
								.getVehicleColor());
						tags[i].setVehicleMake(accountTagRecs[i]
								.getVehicleMake());
						tags[i].setVehicleModel(accountTagRecs[i]
								.getVehicleModel());
					}
					paymentDetail.setTags(tags);
				}
				if (!ArrayUtils.isEmpty(P_FORCE_PMT)) {
					logger.debug("Inside if (!ArrayUtils.isEmpty(P_FORCE_PMT)) @ getPaymentDetail()");
					paymentDetail.setForcePayment(P_FORCE_PMT[0]);
				}
				if (!ArrayUtils.isEmpty(P_PMT_AMT)) {
					logger.debug("Inside if (!ArrayUtils.isEmpty(P_PMT_AMT)) @ getPaymentDetail()");
					paymentDetail.setTagAmount(P_PMT_AMT[0]);
				}

				if (!ArrayUtils.isEmpty(P_RTL_TRXN_ID)) {
					logger.debug("Inside if (!ArrayUtils.isEmpty(P_RTL_TRXN_ID)) @ getPaymentDetail()");
					paymentDetail.setTransactionId(P_RTL_TRXN_ID[0]);
				}

				if (O_VPS_INVOICES_ARR[0] != null
						&& !ArrayUtils
								.isEmpty(O_VPS_INVOICES_ARR[0].getArray())) {
					logger.debug("Inside if (O_VPS_INVOICES_ARR[0] != null && !ArrayUtils.isEmpty(O_VPS_INVOICES_ARR[0].getArray())) @ getPaymentDetail()");
					OlcVbInvoicesRec[] vpsInvoicesRecs = O_VPS_INVOICES_ARR[0]
							.getArray();
					Invoice[] invoices = new Invoice[vpsInvoicesRecs.length];
					for (int i = 0; i < vpsInvoicesRecs.length; i++) {
						invoices[i] = null;
					}
					paymentDetail.setInvoices(invoices);
				}

				if (O_UNINVOICED_VIOLS_ARR[0] != null
						&& !ArrayUtils.isEmpty(O_UNINVOICED_VIOLS_ARR[0]
								.getArray())) {
					logger.debug("Inside if (O_UNINVOICED_VIOLS_ARR[0] != null && !ArrayUtils.isEmpty(O_UNINVOICED_VIOLS_ARR[0].getArray())) @ getPaymentDetail()");
					OlcUninvoicedViolsRec[] uninvoicedViolsRecs = O_UNINVOICED_VIOLS_ARR[0]
							.getArray();
					Violation[] violations = new Violation[uninvoicedViolsRecs.length];
					for (int i = 0; i < uninvoicedViolsRecs.length; i++) {
						violations[i] = PaymentDetailUtil
								.convertToViolation(uninvoicedViolsRecs[i]);
					}
					paymentDetail.setViolations(violations);
				}
			} else {
				logger.debug("Inside else of if (result == 1) @ getPaymentDetail()");
				if (O_ERROR_MSG_ARR[0] != null
						&& O_ERROR_MSG_ARR[0].getArray() != null
						&& O_ERROR_MSG_ARR[0].getArray().length > 0) {
					logger.debug("Inside if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null && O_ERROR_MSG_ARR[0].getArray().length > 0) @ getPaymentDetail()");
					OlcErrorMsgRec[] errorMsgRecs = O_ERROR_MSG_ARR[0]
							.getArray();
					EtccErrorMessageException em = new EtccErrorMessageException(
							"PaymentWS::getPaymentDetail error message");
					for (int i = 0; i < errorMsgRecs.length; i++) {
						em.addRecoverable(errorMsgRecs[i].getErrorMsg());
					}
					logger.error("Error in getPaymentDetail() @ OracleZipCashDAO :: PaymentWS::getPaymentDetail error");
					throw em;
				} else {
					logger.error("Error in getPaymentDetail() @ OracleZipCashDAO :: PaymentWS::getPaymentDetail fatal error");
					throw new EtccException(
							"PaymentWS::getPaymentDetail fatal error");
				}
			}
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getPaymentDetail() @ OracleZipCashDAO");
		return paymentDetail;
	}// end of getPaymentDetail()

	public boolean veaAutoComments(long docId, String docType,
			String sessionId, String ipAddress, String userId, String veaName,
			OlcVpsInvoicesRecBean[] invoices) throws EtccException {
		logger.info("Entering veaAutoComments() @ OracleZipCashDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("veaAutoComments() :: Calling OLCSC_VPS_COMMENTS.vea_auto_comments");
			cstmt = conn
					.prepareCall("{? = call OLCSC_VPS_COMMENTS.vea_auto_comments(?,?,?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_VPS_INVOICES_REC",
					OlcVpsInvoicesRec.class);
			typeMap.put("OL_OWNER.OlcErrorMsgRec", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, docId);
			cstmt.setString(3, docType);
			cstmt.setString(4, sessionId); // session id
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, userId);
			cstmt.setString(7, veaName);

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_VPS_INVOICES_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertToOracleInvoices(invoices));
			cstmt.setArray(8, array);

			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER. OlcErrorMsgArr");

			cstmt.execute();

			byte success = cstmt.getByte(1);

			if (success == 0) {
				logger.debug("Inside if (success == 0) @ veaAutoComments()");
				Collection errors = Util.convertErrorMsgs(cstmt.getArray(9));
				Iterator iter = errors.iterator();
				while (iter.hasNext()) {
					ErrorMessageDTO errorDTO = (ErrorMessageDTO) iter.next();
					logger.info("error occured in veaAutoComments:"
							+ errorDTO.getMessage());
				}
				logger.info("Leaving veaAutoComments() @ OracleZipCashDAO");
				return false;
			}// end of (success == 0)
			logger.info("Leaving veaAutoComments() @ OracleZipCashDAO");
			return true;
		} catch (SQLException ex) {
			logger.error("Error in veaAutoComments() @ OracleZipCashDAO ", ex);
			throw new EtccException("Error in veaAutoComments().");
		} finally {
			closeConnection();
		}// end of try-catch-finally()
	}// end of veaAutoComments()

	public boolean autoComments(long docId, String docType, String sessionId,
			String ipAddress, String userId, long paymentId, String payerName,
			double payAmt, String failedFlag, OlcVpsInvoicesRecBean[] invoices,
			OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException {
		logger.info("Entering autoComments() @ OracleZipCashDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("autoComments() :: Calling OLCSC_VPS_COMMENTS.auto_comments");
			cstmt = conn
					.prepareCall("{? = call OLCSC_VPS_COMMENTS.auto_comments(?,?,?,?,?,?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_VPS_INVOICES_REC",
					OlcVpsInvoicesRec.class);
			typeMap.put("ol_owner.OlcUninvoicedViolsRec",
					OlcUninvoicedViolsRec.class);
			typeMap.put("ol_owner.OlcErrorMsgRec", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, docId);
			cstmt.setString(3, docType);
			cstmt.setString(4, sessionId); // session id
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, userId);
			cstmt.setLong(7, paymentId); // payment id
			cstmt.setString(8, payerName);
			cstmt.setDouble(9, payAmt);
			cstmt.setString(10, failedFlag); //

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"ol_owner.OLC_VPS_INVOICES_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertToOracleInvoices(invoices));
			cstmt.setArray(11, array);

			ArrayDescriptor arraydesc1 = ArrayDescriptor.createDescriptor(
					"ol_owner.OlcUninvoicedViolsArr", conn);
			ARRAY array1 = new ARRAY(arraydesc1, conn,
					convertToOracleUninvoiced(uninvoiced));
			cstmt.setArray(12, array1);

			cstmt.registerOutParameter(13, Types.ARRAY,
					"ol_owner. OlcErrorMsgArr");

			cstmt.execute();

			byte success = cstmt.getByte(1);
			if (success == 0) {
				logger.debug("Inside if (success == 0) @ autoComments()");
				Collection errors = Util.convertErrorMsgs(cstmt.getArray(9));
				Iterator iter = errors.iterator();
				while (iter.hasNext()) {
					ErrorMessageDTO errorDTO = (ErrorMessageDTO) iter.next();
					logger.info("error occured in autoComments:"
							+ errorDTO.getMessage());
				}
				logger.info("Leaving autoComments() @ OracleZipCashDAO");
				return false;
			}// end of if (success == 0)
			logger.info("Leaving autoComments() @ OracleZipCashDAO");
			return true;
		} catch (SQLException ex) {
			logger.error("Error in autoComments() @ OracleZipCashDAO ", ex);
			throw new EtccException("Error in autoComments().");
		} finally {
			closeConnection();
		}// end of try-catch-finally()
	}// end of autoComments()

	private OlcVpsInvoicesRec[] convertToOracleInvoices(
			OlcVpsInvoicesRecBean[] invoices) throws SQLException {
		logger.info("Entering convertToOracleInvoices() @ OracleZipCashDAO");
		if (invoices != null && invoices.length > 0) {
			logger.debug("Inside if (invoices != null && invoices.length > 0) @ convertToOracleInvoicess()");
			OlcVpsInvoicesRec[] result = new OlcVpsInvoicesRec[invoices.length];
			for (int i = 0; i < invoices.length; i++) {
				result[i] = new OlcVpsInvoicesRec();
				result[i].setCurrDueDate(DateUtil
						.calendarToTimestamp(invoices[i].getCurrDueDate()));
				result[i].setFirstName(invoices[i].getFirstName());
				result[i].setInvoiceAmount(invoices[i].getInvoiceAmount());
				result[i].setInvoiceDate(DateUtil
						.calendarToTimestamp(invoices[i].getInvoiceDate()));
				result[i].setInvoiceId(invoices[i].getInvoiceId());
				result[i].setLastName(invoices[i].getLastName());
				result[i].setLicPlateNbr(invoices[i].getLicPlateNbr());
				result[i].setLicState(invoices[i].getLicState());
				result[i].setOnlineFee(invoices[i].getOnlineFee());
				result[i].setVeaAmount(invoices[i].getVeaAmount());
				// result[i].setViolations();
				result[i].setViolatorId(invoices[i].getViolatorId());
			}
			logger.info("Leaving convertToOracleInvoices() @ OracleZipCashDAO");
			return result;
		}// end of if (invoices != null && invoices.length > 0)
		logger.info("Leaving convertToOracleInvoices() @ OracleZipCashDAO");
		return null;
	}// end of convertToOracleInvoices()

	public PaymentResponse zipCashCombinedPayment(long acctId, long docId,
			String docType, String dbSessionId, String ipAddress,
			String loginId, BigDecimal transactionId, BigDecimal tagAmount,
			BigDecimal totalAmount, String dlState, String dlNumber,
			String email, boolean updatePmtInfo, String cardCode,
			String paymentType, String cardNumber, String expireMonth,
			String expireYear, String nameOnCard, String address,
			String address2, String city, String state, String zipCode,
			String plus4, String cardSecurityCode, Invoice[] invoices,
			Violation[] violations, boolean veaAccepted)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering zipCashCombinedPayment() @ OracleZipCashDAO");
		PaymentResponse paymentResponse = null;
		try {
			// Do Nothing
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving zipCashCombinedPayment() @ OracleZipCashDAO");
		return paymentResponse;
	}// end of zipCashCombinedPayment()

	private OlcUninvoicedViolsRec[] convertToOracleUninvoiced(
			OlcUninvoicedViolsRecBean[] uninvoiced) throws SQLException {
		logger.info("Entering convertToOracleUninvoiced() @ OracleZipCashDAO");
		if (uninvoiced != null && uninvoiced.length > 0) {
			logger.debug("Inside if (uninvoiced != null && uninvoiced.length > 0) @ convertToOracleUninvoiced()");
			OlcUninvoicedViolsRec[] result = new OlcUninvoicedViolsRec[uninvoiced.length];
			for (int i = 0; i < uninvoiced.length; i++) {
				result[i] = new OlcUninvoicedViolsRec();
				result[i].setAviAmt(uninvoiced[i].getAviAmt());
				result[i].setCashAmt(uninvoiced[i].getCashAmt());
				result[i].setStatus(uninvoiced[i].getStatus());
				result[i].setViolationDateTime(DateUtil
						.calendarToTimestamp(uninvoiced[i]
								.getViolationDateTime()));
				result[i].setViolationId(uninvoiced[i].getViolationId());
				result[i].setViolationLocation(uninvoiced[i]
						.getViolationLocation());
				result[i].setViolatorId(uninvoiced[i].getViolatorId());
				result[i].setFullLocationName(uninvoiced[i]
						.getFullLocationName());
				result[i].setLicPlate(uninvoiced[i].getLicPlate());
				result[i].setLicState(uninvoiced[i].getLicState());
				result[i].setOnlineFee(uninvoiced[i].getOnlineFee());
			}
			logger.info("Leaving convertToOracleUninvoiced() @ OracleZipCashDAO");
			return result;
		}// end of if()
		logger.info("Leaving convertToOracleUninvoiced() @ OracleZipCashDAO");
		return null;
	}// end of convertToOracleUninvoiced()

	public boolean veaExists(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			String licPlate, String licState) throws Exception {
		logger.info("Entering veaExists() @ OracleZipCashDAO");
		try {
			setConnection(Util.getDbConnection());
			BigDecimal[] O_VEA_EXIST = new BigDecimal[] { new BigDecimal(0) };
			// commented temorarily by sunil
			int result = 1;
			/*
			 * pkg.VEA_EXIST_INFO(docId.toPlainString(), docType, dbSessionId,
			 * ipAddress, loginId, licPlate, licState, O_VEA_EXIST,
			 * O_ERROR_MSG_ARR).intValue();
			 */
			if (result == 1) {
				logger.debug("Inside if (result == 1) @ veaExists()");
				if (!ArrayUtils.isEmpty(O_VEA_EXIST)) {
					logger.debug("Inside if (!ArrayUtils.isEmpty(O_VEA_EXIST)) @ veaExists()");
					logger.info("Leaving veaExists() @ OracleZipCashDAO");
					return O_VEA_EXIST[0].intValue() == 1;
				}
			}
			logger.error("Error in veaExists() @ OracleZipCashDAO :: PaymentWS::veaExists fatal error.");
			throw new EtccException("PaymentWS::veaExists fatal error");
		} finally {
			closeConnection();
		}// end of try-catch-finally()

	}// end of veaExists()

	public OlcPaymentInfoRec getPaymentInfo(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId, long event)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering getPaymentInfo() @ OracleZipCashDAO");
		OlcPaymentInfoRec olcPaymentInfoRecBean = null;
		try {
			setConnection(Util.getDbConnection());
			OlcPaymentInfoRec[] O_PAYMENT_INFO_REC = new OlcPaymentInfoRec[] { new OlcPaymentInfoRec() };
			OlcErrorMsgArr[] O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new OlcErrorMsgArr() };

			OLCSC_ACCT_MGMT plsql = new OLCSC_ACCT_MGMT(conn);

			int result = plsql.getPaymentInfo(docId.toPlainString(), docType,
					dbSessionId, ipAddress, loginId, new BigDecimal(0),
					O_PAYMENT_INFO_REC, O_ERROR_MSG_ARR).intValue();

			if (result == 1) {
				logger.debug("Inside if (result == 1) @ getPaymentInfo()");
				if (!ArrayUtils.isEmpty(O_PAYMENT_INFO_REC)) {
					logger.debug("Inside if (!ArrayUtils.isEmpty(O_PAYMENT_INFO_REC)) @ getPaymentInfo()");
					olcPaymentInfoRecBean = O_PAYMENT_INFO_REC[0];
				}
			} else {
				logger.debug("Inside else of if (result == 1) @ getPaymentInfo()");
				if (O_ERROR_MSG_ARR[0] != null
						&& O_ERROR_MSG_ARR[0].getArray() != null
						&& O_ERROR_MSG_ARR[0].getArray().length > 0) {
					logger.debug("Inside if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null && O_ERROR_MSG_ARR[0].getArray().length > 0) @ getPaymentInfo()");
					OlcErrorMsgRec[] errorMsgRecs = O_ERROR_MSG_ARR[0]
							.getArray();
					EtccErrorMessageException em = new EtccErrorMessageException(
							"PaymentWS::getPaymentInfo error message");
					for (int i = 0; i < errorMsgRecs.length; i++) {
						em.addRecoverable(errorMsgRecs[i].getErrorMsg());
					}
					logger.error("Error in getPaymentInfo @ OracleZipCashDAO :: PaymentWS::getPaymentInfo error.");
					throw em;
				} else {
					logger.error("Error in getPaymentInfo @ OracleZipCashDAO :: PaymentWS::getPaymentInfo fatal error.");
					throw new EtccException(
							"PaymentWS::getPaymentInfo fatal error");
				}
			}
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getPaymentInfo() @ OracleZipCashDAO");
		return olcPaymentInfoRecBean;
	}// end of getPaymentInfo()

	public boolean postViolations(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			Violation[] violations) throws EtccErrorMessageException, Exception {
		logger.info("Entering postViolations() @ OracleZipCashDAO");
		boolean success = false;
		try {
			setConnection(Util.getDbConnection());
			OlcErrorMsgArr[] O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new OlcErrorMsgArr() };

			OlcUninvoicedViolsArr UNINVOICED_VIOLS_ARR = new OlcUninvoicedViolsArr();
			UNINVOICED_VIOLS_ARR.setArray(PaymentDetailUtil
					.convertToOLC_UNINVOICED_VIOLS_RECs(violations));
			OlcUninvoicedViolsArr[] P_UNINVOICED_VIOLS = new OlcUninvoicedViolsArr[] { UNINVOICED_VIOLS_ARR };
			OLCSC_VTOLL plsql = new OLCSC_VTOLL(conn);

			int result = plsql.payUninvoicedViols(P_UNINVOICED_VIOLS,
					dbSessionId, ipAddress, loginId, docId, "Y",
					O_ERROR_MSG_ARR).intValue();
			if (result == 1) {
				logger.debug("Inside if (result == 1) @ postViolations()");
				success = true;
			} else {
				logger.debug("Inside else of if (result == 1) @ postViolations()");
				if (O_ERROR_MSG_ARR[0] != null
						&& O_ERROR_MSG_ARR[0].getArray() != null
						&& O_ERROR_MSG_ARR[0].getArray().length > 0) {
					logger.debug("Inside if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null && O_ERROR_MSG_ARR[0].getArray().length > 0) @ postViolations()");
					OlcErrorMsgRec[] errorMsgRecs = O_ERROR_MSG_ARR[0]
							.getArray();
					EtccErrorMessageException em = new EtccErrorMessageException(
							"postViolations error message");
					for (int i = 0; i < errorMsgRecs.length; i++) {
						em.addRecoverable(errorMsgRecs[i].getErrorMsg());
					}
					logger.error("Error in postViolations() @ OracleZipCashDAO :: postViolations error.");
					throw em;
				} else {
					logger.error("Error in postViolations() @ OracleZipCashDAO :: postViolations fatal error.");
					throw new EtccException("postViolations fatal error");
				}
			}
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving postViolations() @ OracleZipCashDAO");
		return success;
	}// end of postViolations()

	public String getPaymentReceipt(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			BigDecimal paymentId, String reportFormat)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering getPaymentReceipt() @ OracleZipCashDAO");
		String reportUrl = null;
		try {
			setConnection(Util.getDbConnection());
			String[] O_REPORT_URL = new String[] { "" };
			OlcErrorMsgArr[] O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new OlcErrorMsgArr() };
			int result = new OLCSC_REP(conn).zcOnlineReceiptRpt(reportFormat,
					docId, docType, paymentId, dbSessionId, ipAddress, loginId,
					O_REPORT_URL, O_ERROR_MSG_ARR).intValue();
			if (result == 1) {
				logger.debug("Inside if (result == 1) @ getPaymentReceipt()");
				if (!ArrayUtils.isEmpty(O_REPORT_URL)) {
					logger.debug("Inside if (!ArrayUtils.isEmpty(O_REPORT_URL)) @ getPaymentReceipt()");
					reportUrl = O_REPORT_URL[0].trim();
				}
			} else {
				logger.debug("Inside else of if (result == 1) @ getPaymentReceipt()");
				if (O_ERROR_MSG_ARR[0] != null
						&& O_ERROR_MSG_ARR[0].getArray() != null
						&& O_ERROR_MSG_ARR[0].getArray().length > 0) {
					logger.debug("Inside if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null && O_ERROR_MSG_ARR[0].getArray().length > 0) @ getPaymentReceipt()");
					OlcErrorMsgRec[] errorMsgRecs = O_ERROR_MSG_ARR[0]
							.getArray();
					EtccErrorMessageException em = new EtccErrorMessageException(
							"paymentReceipt error message");
					for (int i = 0; i < errorMsgRecs.length; i++) {
						em.addRecoverable(errorMsgRecs[i].getErrorMsg());
					}
					logger.error("Error in getPaymentReceipt() @ OracleZipCashDAO :: paymentReceipt error.");
					throw em;
				} else {
					logger.error("Error in getPaymentReceipt() @ OracleZipCashDAO :: paymentReceipt fatal error.");
					throw new EtccException("paymentReceipt fatal error");
				}
			}
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getPaymentReceipt() @ OracleZipCashDAO");
		return reportUrl;
	}// end of getPaymentReceipt()

	public BigDecimal getPaymentLimit() throws Exception {
		logger.info("Entering getPaymentLimit() @ OracleZipCashDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("Leaving getPaymentLimit() @ OracleZipCashDAO");
			return new OLCSC_PARAM(conn)
					.getOlPaymentLimit(new OlcErrorMsgArr[] { new OlcErrorMsgArr() });
		} finally {
			closeConnection();
		}// end of try-catch-finally()
	}// end of getPaymentLimit()

	public boolean updateSessionId(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			String paymentId) throws EtccErrorMessageException, Exception {
		logger.info("Entering updateSessionId() @ OracleZipCashDAO");
		boolean success = false;
		try {
			setConnection(Util.getDbConnection());
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving updateSessionId() @ OracleZipCashDAO");
		return success;
	}// end of updateSessionId()

}// end of OracleZipCashDAO Class