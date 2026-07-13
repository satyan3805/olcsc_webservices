package com.etcc.csc.dao;

import java.io.StringReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.CLOB;

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
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.enums.EventEnum;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OLCSC_ACCT_MGMT;
import com.etcc.csc.plsql.OLCSC_INV;
import com.etcc.csc.plsql.OLCSC_PARAM;
import com.etcc.csc.plsql.OLCSC_REP;
import com.etcc.csc.plsql.OLCSC_VTOLL;
import com.etcc.csc.plsql.OlcAccountTagArr;
import com.etcc.csc.plsql.OlcAccountTagRec;
import com.etcc.csc.plsql.OlcErrorMsgArr;
import com.etcc.csc.plsql.OlcErrorMsgRec;
import com.etcc.csc.plsql.OlcPaymentInfoRec;
import com.etcc.csc.plsql.OlcUninvoicedViolsArr;
import com.etcc.csc.plsql.OlcUninvoicedViolsRec;
import com.etcc.csc.plsql.OlcVpsInvoicesArr;
import com.etcc.csc.plsql.OlcVpsInvoicesRec;

public class OraclePaymentDAO extends PaymentDAO {
	private Logger logger = Logger.getLogger(OraclePaymentDAO.class);

	public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress,
			String loginId, long docId, String docType, String licPlate,
			String licState, BigDecimal transactionId)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering getPaymentDetail() @ OraclePaymentDAO");
		PaymentDetail paymentDetail = null;

		try {
			setConnection(Util.getDbConnection());
			OlcAccountTagArr[] P_ACCOUNT_TAG_ARR = new OlcAccountTagArr[] { new OlcAccountTagArr() };
			String[] P_FORCE_PMT = new String[] { "" };
			BigDecimal[] P_PMT_AMT = new BigDecimal[] { new BigDecimal(0) };
			BigDecimal[] P_DEP_AMT = new BigDecimal[] { new BigDecimal(0) };
			BigDecimal[] P_RTL_TRXN_ID = new BigDecimal[] { transactionId };

			OlcVpsInvoicesArr[] O_VPS_INVOICES_ARR = new OlcVpsInvoicesArr[] { new OlcVpsInvoicesArr() };

			OlcUninvoicedViolsArr[] O_UNINVOICED_VIOLS_ARR = new OlcUninvoicedViolsArr[] { new OlcUninvoicedViolsArr() };

			OlcErrorMsgArr[] O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new OlcErrorMsgArr() };

			logger.debug("Calling OLCSC_INV.getPmtInfo");
			logger.debug(" dbSessionId= " + dbSessionId);
			logger.debug(" ipAddress= " + ipAddress);
			logger.debug(" loginId= " + loginId);
			logger.debug(" docId= " + docId);
			logger.debug(" docType= " + docType);
			logger.debug(" licPlate= " + licPlate);
			logger.debug(" licState= " + licState);
			logger.debug(" P_RTL_TRXN_ID= " + P_RTL_TRXN_ID);

			int result = new OLCSC_INV(conn).getPmtInfo(dbSessionId, ipAddress,
					loginId, new BigDecimal(docId), docType, licPlate,
					licState, P_ACCOUNT_TAG_ARR, P_RTL_TRXN_ID,
					O_VPS_INVOICES_ARR, O_UNINVOICED_VIOLS_ARR,
					O_ERROR_MSG_ARR,
					new BigDecimal(EventEnum.MAKEPAYMENT.getId())).intValue();
			logger.debug("result = " + result);
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
						tags[i].setLicState(accountTagRecs[i].getLicPlate());
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
					logger.debug("Inside first if (!ArrayUtils.isEmpty(P_FORCE_PMT)) @ getPaymentDetail()");
					paymentDetail.setForcePayment(P_FORCE_PMT[0]);
				}

				if (!ArrayUtils.isEmpty(P_PMT_AMT)) {
					logger.debug("Inside first if (!ArrayUtils.isEmpty(P_PMT_AMT)) @ getPaymentDetail()");
					paymentDetail.setTagAmount(P_PMT_AMT[0]);
				}

				if (!ArrayUtils.isEmpty(P_DEP_AMT)) {
					logger.debug("Inside first if (!ArrayUtils.isEmpty(P_DEP_AMT)) @ getPaymentDetail()");
					paymentDetail.setDepAmount(P_DEP_AMT[0]);
				}

				if (!ArrayUtils.isEmpty(P_RTL_TRXN_ID)) {
					logger.debug("Inside first if (!ArrayUtils.isEmpty(P_RTL_TRXN_ID)) @ getPaymentDetail()");
					paymentDetail.setTransactionId(P_RTL_TRXN_ID[0]);
				}

				if (O_VPS_INVOICES_ARR[0] != null
						&& !ArrayUtils
								.isEmpty(O_VPS_INVOICES_ARR[0].getArray())) {
					logger.debug("Inside if (O_VPS_INVOICES_ARR[0] != null && !ArrayUtils.isEmpty(O_VPS_INVOICES_ARR[0].getArray())) @ getPaymentDetail()");
					OlcVpsInvoicesRec[] vpsInvoicesRecs = O_VPS_INVOICES_ARR[0]
							.getArray();

					Invoice[] invoices = new Invoice[vpsInvoicesRecs.length];

					for (int i = 0; i < vpsInvoicesRecs.length; i++) {
						invoices[i] = PaymentDetailUtil
								.convertToInvoice(vpsInvoicesRecs[i]);
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
				logger.debug(paymentDetail.toString());
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ getPaymentDetail()");
				paymentDetail = new PaymentDetail();

				if (!ArrayUtils.isEmpty(P_FORCE_PMT)) {
					logger.debug("Inside second if (!ArrayUtils.isEmpty(P_FORCE_PMT)) @ getPaymentDetail()");
					paymentDetail.setForcePayment(P_FORCE_PMT[0]);
				}

				if (!ArrayUtils.isEmpty(P_PMT_AMT)) {
					logger.debug("Inside second if (!ArrayUtils.isEmpty(P_PMT_AMT)) @ getPaymentDetail()");
					paymentDetail.setTagAmount(P_PMT_AMT[0]);
				}

				if (!ArrayUtils.isEmpty(P_DEP_AMT)) {
					logger.debug("Inside second if (!ArrayUtils.isEmpty(P_DEP_AMT)) @ getPaymentDetail()");
					paymentDetail.setDepAmount(P_DEP_AMT[0]);
				}

				if (!ArrayUtils.isEmpty(P_RTL_TRXN_ID)) {
					logger.debug("Inside second if (!ArrayUtils.isEmpty(P_RTL_TRXN_ID)) @ getPaymentDetail()");
					paymentDetail.setTransactionId(P_RTL_TRXN_ID[0]);
				}

			} else {
				logger.debug("Inside else of if (result == 1) @ getPaymentDetail()");
				try {
					if (O_ERROR_MSG_ARR[0] != null
							&& !ArrayUtils.isEmpty(O_ERROR_MSG_ARR[0]
									.getArray())) {
						logger.debug("Inside if (O_ERROR_MSG_ARR[0] != null && !ArrayUtils.isEmpty(O_ERROR_MSG_ARR[0].getArray())) @ getPaymentDetail()");
						OlcErrorMsgRec[] rec = O_ERROR_MSG_ARR[0].getArray();
						for (OlcErrorMsgRec obj : rec) {
							logger.error("Error occured during retrieveing payment info : "
									+ obj.getErrorMsg());
						}
					} else {
						logger.debug("Inside else of if (O_ERROR_MSG_ARR[0] != null && !ArrayUtils.isEmpty(O_ERROR_MSG_ARR[0].getArray())) @ getPaymentDetail()");
						logger.debug("No error messages returned when result <> 1");
					}
				} catch (Exception ex) {
					logger.error(
							"Error in getPaymentDetail() @ OraclePaymentDAO: "
									+ ex, ex);
				}
				logger.error("Error in getPaymentDetail() @ OraclePaymentDAO :: PaymentWS::getPaymentDetail fatal error");
				throw new EtccException(
						"PaymentWS::getPaymentDetail fatal error");
			}
		} finally {
			closeConnection();
		}

		logger.info("Leaving getPaymentDetail() @ OraclePaymentDAO");
		return paymentDetail;
	}

	public boolean veaAutoComments(long docId, String docType,
			String sessionId, String ipAddress, String userId, String veaName,
			OlcVpsInvoicesRecBean[] invoices) throws EtccException {
		logger.info("Entering veaAutoComments() @ OraclePaymentDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("veaAutoComments() :: Calling OLCSC_VPS_COMMENTS.vea_auto_comments");
			cstmt = conn
					.prepareCall("{? = call OLCSC_VPS_COMMENTS.vea_auto_comments(?,?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("ol_owner.OlcVpsInvoicesRec", OlcVpsInvoicesRec.class);
			typeMap.put("ol_OWNER.OlcErrorMsgRec", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, docId);
			cstmt.setString(3, docType);
			cstmt.setString(4, sessionId); // session id
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, userId);
			cstmt.setString(7, veaName);

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"ol_owner.OlcVpsInvoicesArr", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertToOracleInvoices(invoices));
			cstmt.setArray(8, array);

			cstmt.registerOutParameter(9, Types.ARRAY,
					"ol_OWNER.OlcErrorMsgArr");

			cstmt.execute();

			byte success = cstmt.getByte(1);

			if (success == 0) {
				logger.debug("Inside if (success == 0) @ veaAutoComments()");
				Collection errors = Util.convertErrorMsgs(cstmt.getArray(9));
				Iterator iter = errors.iterator();
				while (iter.hasNext()) {
					ErrorMessageDTO errorDTO = (ErrorMessageDTO) iter.next();
					logger.error("error occured in veaAutoComments:"
							+ errorDTO.getMessage());
				}
				logger.info("Leaving veaAutoComments() @ OraclePaymentDAO");
				return false;
			}

			logger.info("Leaving veaAutoComments() @ OraclePaymentDAO");
			return true;
		} catch (SQLException sqle) {
			logger.error("Error in veaAutoComments() @ OraclePaymentDAO: "
					+ sqle, sqle);
			throw new EtccException("Error in veaAutoComments: " + sqle, sqle);
		} finally {
			closeConnection();
		}
	}

	public boolean autoComments(long docId, String docType, String sessionId,
			String ipAddress, String userId, long paymentId, String payerName,
			double payAmt, String failedFlag, OlcVpsInvoicesRecBean[] invoices,
			OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException {
		logger.info("Entering autoComments() @ OraclePaymentDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("autoComments() :: Calling OLCSC_VPS_COMMENTS.auto_comments");
			cstmt = conn
					.prepareCall("{? = call OLCSC_VPS_COMMENTS.auto_comments(?,?,?,?,?,?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("ol_owner.OlcVpsInvoicesRec", OlcVpsInvoicesRec.class);
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
					"ol_owner.OlcVpsInvoicesArr", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					convertToOracleInvoices(invoices));
			cstmt.setArray(11, array);

			ArrayDescriptor arraydesc1 = ArrayDescriptor.createDescriptor(
					"ol_owner.OlcUninvoicedViolsArr", conn);
			ARRAY array1 = new ARRAY(arraydesc1, conn,
					convertToOracleUninvoiced(uninvoiced));
			cstmt.setArray(12, array1);

			cstmt.registerOutParameter(13, Types.ARRAY,
					"ol_OWNER.OlcErrorMsgArr");

			cstmt.execute();

			byte success = cstmt.getByte(1);

			if (success == 0) {
				logger.debug("Inside if (success == 0) @ autoComments()");
				Collection errors = Util.convertErrorMsgs(cstmt.getArray(9));
				Iterator iter = errors.iterator();
				while (iter.hasNext()) {
					ErrorMessageDTO errorDTO = (ErrorMessageDTO) iter.next();
					logger.error("error occured in autoComments:"
							+ errorDTO.getMessage());
				}
				logger.info("Leaving autoComments() @ OraclePaymentDAO");
				return false;
			}
			logger.info("Leaving autoComments() @ OraclePaymentDAO");
			return true;
		} catch (SQLException ex) {
			logger.error("Error in autoComments() @ OraclePaymentDAO: " + ex,
					ex);
			throw new EtccException();
		} finally {
			closeConnection();
		}
	}

	private OlcVpsInvoicesRec[] convertToOracleInvoices(
			OlcVpsInvoicesRecBean[] invoices) throws SQLException {
		logger.info("Entering convertToOracleInvoices() @ OraclePaymentDAO");
		if (invoices != null && invoices.length > 0) {
			logger.debug("Inside if (invoices != null && invoices.length > 0) @ convertToOracleInvoices()");
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
			logger.info("Leaving convertToOracleInvoices() @ OraclePaymentDAO");
			return result;
		}
		return null;
	}

	public PaymentResponse combinedPayment(long docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			BigDecimal transactionId, BigDecimal tagAmount,
			BigDecimal totalAmount, String dlState, String dlNumber,
			String email, boolean updatePmtInfo, String cardCode,
			String paymentType, String cardNumber, String expireMonth,
			String expireYear, String nameOnCard, String address,
			String address2, String city, String state, String zipCode,
			String plus4, String cardSecurityCode, Invoice[] invoices,
			Violation[] violations, boolean veaAccepted)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering combinedPayment() @ OraclePaymentDAO");
		PaymentResponse paymentResponse = null;
		/*
		 * try {
		 * 
		 * setConnection(Util.getDbConnection()); BigDecimal acctId = null; if
		 * ("AC".equals(docType)) { acctId = new BigDecimal(docId); }
		 * 
		 * OlcPaymentInfoRec olcPaymentInfoRec = new OlcPaymentInfoRec();
		 * olcPaymentInfoRec.setPmtType(paymentType);
		 * olcPaymentInfoRec.setCardCode(cardCode);
		 * olcPaymentInfoRec.setCardSecCode(cardSecurityCode);
		 * olcPaymentInfoRec.setCardNbr(cardNumber);
		 * olcPaymentInfoRec.setCardExpires
		 * (DateUtil.monthYearToTimestamp(expireMonth + "/" + expireYear));
		 * 
		 * olcPaymentInfoRec.setNameOnCard(nameOnCard);
		 * olcPaymentInfoRec.setAddress1(address);
		 * olcPaymentInfoRec.setAddress2(address2);
		 * olcPaymentInfoRec.setCity(city); olcPaymentInfoRec.setState(state);
		 * olcPaymentInfoRec.setZipCode(zipCode);
		 * olcPaymentInfoRec.setPlus4(plus4);
		 * 
		 * OlcVpsInvPmtArr P_INV_PMT_ARR = new OlcVpsInvPmtArr();
		 * OlcVpsInvFeePmtArr P_INV_FEE_PMT_ARR = new OlcVpsInvFeePmtArr();
		 * OlcVpsUninvPmtArr P_UNINV_VIOL_PMT_ARR = new OlcVpsUninvPmtArr();
		 * OlcVpsUninvFeePmtArr P_UNINV_FEE_PMT_ARR = new
		 * OlcVpsUninvFeePmtArr();
		 * 
		 * P_INV_PMT_ARR.setArray(PaymentDetailUtil.invoicesToOLC_VPS_INV_PMT_RECs
		 * (invoices,veaAccepted));
		 * P_INV_FEE_PMT_ARR.setArray(PaymentDetailUtil.
		 * invoicesToOLC_VPS_INV_FEE_PMT_RECs(invoices, veaAccepted)); //
		 * P_UNINV_VIOL_PMT_ARR
		 * .setArray(PaymentDetailUtil.violationsToOLC_VPS_UNINV_PMT_RECs
		 * (violations)); P_UNINV_FEE_PMT_ARR.setArray(PaymentDetailUtil.
		 * violationsToOLC_VPS_UNINV_FEE_PMT_RECs(violations));
		 * 
		 * String[] P_POSTING_STATUS = new String[] { "" }; BigDecimal[]
		 * P_PAYMENT_ID = new BigDecimal[] { new BigDecimal(0) };
		 * OlcErrorMsgArr[] O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new
		 * OlcErrorMsgArr() };
		 * 
		 * int result = new OLCSC_PAYMENT(conn).makePayment(new
		 * BigDecimal(docId), docType, dbSessionId, ipAddress, loginId,
		 * transactionId, acctId, tagAmount, totalAmount, P_INV_PMT_ARR,
		 * P_INV_FEE_PMT_ARR, P_UNINV_VIOL_PMT_ARR, P_UNINV_FEE_PMT_ARR,
		 * olcPaymentInfoRec, dlState, dlNumber, email, true, updatePmtInfo ?
		 * "Y" : "N", P_POSTING_STATUS, P_PAYMENT_ID,
		 * O_ERROR_MSG_ARR).intValue();
		 * 
		 * if (result == 1) { if (!ArrayUtils.isEmpty(P_POSTING_STATUS) &&
		 * !ArrayUtils.isEmpty(P_PAYMENT_ID)) { paymentResponse = new
		 * PaymentResponse();
		 * paymentResponse.setPostingStatus(P_POSTING_STATUS[0].trim());
		 * paymentResponse.setPaymentId(P_PAYMENT_ID[0]); } } else { if
		 * (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null
		 * && O_ERROR_MSG_ARR[0].getArray().length > 0) { OlcErrorMsgRec[]
		 * errorMsgRecs = O_ERROR_MSG_ARR[0].getArray();
		 * EtccErrorMessageException em = new
		 * EtccErrorMessageException("PaymentWS::combinedPayment error message"
		 * ); for (int i = 0; i < errorMsgRecs.length; i++) {
		 * em.addRecoverable(errorMsgRecs[i].getErrorMsg()); } throw em; } else
		 * { throw new EtccException("PaymentWS::combinedPayment fatal error");
		 * } } } finally { closeConnection(); }
		 */
		logger.info("Leaving combinedPayment() @ OraclePaymentDAO");
		return paymentResponse;
	}

	private OlcUninvoicedViolsRec[] convertToOracleUninvoiced(
			OlcUninvoicedViolsRecBean[] uninvoiced) throws SQLException {
		logger.info("Entering convertToOracleUninvoiced() @ OraclePaymentDAO");
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

			logger.info("Leaving convertToOracleUninvoiced() @ OraclePaymentDAO");
			return result;
		}
		return null;
	}

	public boolean veaExists(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			String licPlate, String licState) throws Exception {
		logger.info("Entering veaExists() @ OraclePaymentDAO");
		try {
			setConnection(Util.getDbConnection());
			BigDecimal[] O_VEA_EXIST = new BigDecimal[] { new BigDecimal(0) };
			// commented by sunil temporarily
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
					logger.info("Leaving veaExists() @ OraclePaymentDAO");
					return O_VEA_EXIST[0].intValue() == 1;
				}
			}
			logger.error("Error in veaExists() @ OraclePaymentDAO :: PaymentWS::veaExists fatal error");
			throw new EtccException("PaymentWS::veaExists fatal error");
		} finally {
			closeConnection();
		}
	}

	public OlcPaymentInfoRec getPaymentInfo(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId, long eventId)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering getPaymentInfo() @ OraclePaymentDAO");
		logger.debug("Doc Id " + docId);
		logger.debug("docType " + docType);
		logger.debug("dbSessionId " + dbSessionId);
		logger.debug("ipAddress " + ipAddress);
		logger.debug("loginId " + loginId);
		logger.debug("event id " + eventId);
		OlcPaymentInfoRec olcPaymentInfoRecBean = null;

		try {
			setConnection(Util.getDbConnection());
			OlcPaymentInfoRec[] O_PAYMENT_INFO_REC = new OlcPaymentInfoRec[] { new OlcPaymentInfoRec() };
			OlcErrorMsgArr[] O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new OlcErrorMsgArr() };

			OLCSC_ACCT_MGMT plsql = new OLCSC_ACCT_MGMT(conn);

			int result = plsql.getPaymentInfo(docId.toPlainString(), docType,
					dbSessionId, ipAddress, loginId, new BigDecimal(eventId),
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
					logger.error("Error in getPaymentInfo() @ OraclePaymentDAO");
					throw em;
				} else {
					logger.error("Error in getPaymentInfo() @ OraclePaymentDAO :: PaymentWS::getPaymentInfo fatal error");
					throw new EtccException(
							"PaymentWS::getPaymentInfo fatal error");
				}
			}
		} finally {
			closeConnection();
		}
		logger.info("Leaving getPaymentInfo() @ OraclePaymentDAO");
		return olcPaymentInfoRecBean;
	}

	public boolean postViolations(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			Violation[] violations) throws EtccErrorMessageException, Exception {
		logger.info("Entering postViolations() @ OraclePaymentDAO");
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
					logger.error("Error in postViolations() @ OraclePaymentDAO.");
					throw em;
				} else {
					logger.error("Error in postViolations() @ OraclePaymentDAO :: postViolations fatal error.");
					logger.debug("Inside else of if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null && O_ERROR_MSG_ARR[0].getArray().length > 0) @ postViolations()");
					throw new EtccException("postViolations fatal error");
				}
			}
		} finally {
			closeConnection();
		}
		logger.info("Leaving postViolations() @ OraclePaymentDAO");
		return success;
	}

	public String getPaymentReceipt(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			BigDecimal paymentId, String reportFormat)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering getPaymentReceipt() @ OraclePaymentDAO");
		String reportUrl = null;

		try {
			setConnection(Util.getDbConnection());
			String[] O_REPORT_URL = new String[] { "" };
			OlcErrorMsgArr[] O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new OlcErrorMsgArr() };

			int result = new OLCSC_REP(conn).onlineReceiptRpt(reportFormat,
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
							"getPaymentReceipt error message");
					for (int i = 0; i < errorMsgRecs.length; i++) {
						em.addRecoverable(errorMsgRecs[i].getErrorMsg());
					}
					logger.error("Error in getPaymentReceipt() @ OraclePaymentDAO.");
					throw em;
				} else {
					logger.error("Error in getPaymentReceipt() @ OraclePaymentDAO :: getPaymentReceipt fatal error");
					throw new EtccException("getPaymentReceipt fatal error");
				}
			}
		} finally {
			closeConnection();
		}

		logger.info("Leaving getPaymentReceipt() @ OraclePaymentDAO");
		return reportUrl;
	}

	public BigDecimal getPaymentLimit() throws Exception {
		logger.info("Entering getPaymentLimit() @ OraclePaymentDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("Leaving getPaymentLimit() @ OraclePaymentDAO");
			return new OLCSC_PARAM(conn)
					.getOlPaymentLimit(new OlcErrorMsgArr[] { new OlcErrorMsgArr() });
		} finally {
			closeConnection();
		}
	}

	public boolean updateSessionId(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			String paymentId) throws EtccErrorMessageException, Exception {
		logger.info("Entering updateSessionId() @ OraclePaymentDAO");
		boolean success = false;
		/*
		 * try {
		 * 
		 * setConnection(Util.getDbConnection()); OlcErrorMsgArr[]
		 * O_ERROR_MSG_ARR = new OlcErrorMsgArr[] { new OlcErrorMsgArr() }; int
		 * result = new OLCSC_PAYMENT(conn).updateSessionId(docId, docType,
		 * dbSessionId, ipAddress, loginId, true, new BigDecimal( paymentId),
		 * O_ERROR_MSG_ARR).intValue();
		 * 
		 * if (result == 1) { success = true; } else { if (O_ERROR_MSG_ARR[0] !=
		 * null && O_ERROR_MSG_ARR[0].getArray() != null &&
		 * O_ERROR_MSG_ARR[0].getArray().length > 0) { OlcErrorMsgRec[]
		 * errorMsgRecs = O_ERROR_MSG_ARR[0].getArray();
		 * EtccErrorMessageException em = new EtccErrorMessageException(
		 * "updateSessionId error message"); for (int i = 0; i <
		 * errorMsgRecs.length; i++) {
		 * em.addRecoverable(errorMsgRecs[i].getErrorMsg()); } throw em; } else
		 * { throw new EtccException("update session Id error"); } } } finally {
		 * closeConnection(); }
		 */
		logger.info("Leaving updateSessionId() @ OraclePaymentDAO");
		return success;
	}

	public byte makePayment(AccountLoginDTO acctLoginDto, String postingXML,
			long eventId) throws EtccErrorMessageException, Exception {
		logger.info("Entering makePayment() @ OraclePaymentDAO");
		/*
		 * FUNCTION make_payment(p_doc_id IN NUMBER, p_doc_type IN VARCHAR2,
		 * p_session_id VARCHAR2, p_ip_address VARCHAR2, p_user_id VARCHAR2, XS
		 * IN XMLTYPE, --p_account_posting_id IN
		 * account_postings.ACCOUNT_POSTING_ID%type, P_event_id IN
		 * events.event_id%TYPE, p_ui_call IN BOOLEAN DEFAULT TRUE,
		 * o_error_msg_arr OUT olc_error_msg_arr) RETURN NUMBER IS
		 */
		// InputStream reader = new ByteArrayInputStream(postingXML.getBytes());
		StringReader reader = new StringReader(postingXML);

		try {
			logger.debug("Calling OLCSC_PAYMENT.MAKE_PAYMENT");
			setConnection(Util.getDbConnection());
			logger.info("makePayment() :: Calling OLCSC_PAYMENT.MAKE_PAYMENT");
			cstmt = conn
					.prepareCall("{? = call OLCSC_PAYMENT.MAKE_PAYMENT(?, ?, ?, ?, ?, XMLTYPE(?), ?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			if ("IN".equals(acctLoginDto.getLoginType())) {
				logger.debug("Inside if (\"IN\".equals(acctLoginDto.getLoginType())) @ makePayment()");
				cstmt.setLong(idx++, acctLoginDto.getInvoiceId());
			} else {
				logger.debug("Inside else of if (\"IN\".equals(acctLoginDto.getLoginType())) @ makePayment()");
				cstmt.setLong(idx++, acctLoginDto.getAcctId());
			}
			cstmt.setString(idx++, acctLoginDto.getLoginType());
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			CLOB clob = getCLOB(postingXML, conn);
			cstmt.setObject(idx++, clob);
			cstmt.setLong(idx++, eventId);
			cstmt.setString(idx++, "Y");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			byte success = cstmt.getByte(1);
			logger.debug("After calling OLCSC_PAYMENT.MAKE_PAYMENT, success = "
					+ success);
			if (success == 0) {
				logger.debug("Inside if (success == 0) @ makePayment()");
				logger.debug("OLCSC_PAYMENT.MAKE_PAYMENT, -----------Failed--------------");
				Collection errors = Util.convertErrorMsgs(cstmt.getArray(idx));
				Iterator iter = errors.iterator();
				while (iter.hasNext()) {
					ErrorMessageDTO errorDTO = (ErrorMessageDTO) iter.next();
					logger.debug("error occured in OLCSC_PAYMENT.MAKE_PAYMENT:"
							+ errorDTO.getMessage());
				}
				logger.info("Leaving makePayment() @ OraclePaymentDAO");
				return success;
			}
		} catch (SQLException sqle) {
			logger.error("Error in makePayment() @ OraclePaymentDAO: " + sqle,
					sqle);
			throw new EtccException("Error running makePayment: " + sqle, sqle);
		} catch (Exception ex) {
			logger.error("Error in makePayment() @ OraclePaymentDAO ", ex);
			throw new EtccException("Error running makePayment: " + ex, ex);
		} finally {
			closeConnection();
			reader.close();
		}
		return 1;
	}

	private CLOB getCLOB(String xmlData, Connection conn) throws SQLException {
		logger.info("Entering getCLOB() @ OraclePaymentDAO");
		CLOB tempClob = null;

		try {
			// If the temporary CLOB has not yet been created, create one
			tempClob = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION);

			// Open the temporary CLOB in readwrite mode, to enable writing
			tempClob.open(CLOB.MODE_READWRITE);
			// Get the output stream to write
			@SuppressWarnings("deprecation")
			Writer tempClobWriter = tempClob.getCharacterOutputStream();
			// Write the data into the temporary CLOB
			tempClobWriter.write(xmlData);

			// Flush and close the stream
			tempClobWriter.flush();
			tempClobWriter.close();

			// Close the temporary CLOB
			tempClob.close();
		} catch (SQLException sqle) {
			logger.error("Error in getCLOB() @ OraclePaymentDAO: " + sqle, sqle);
			tempClob.freeTemporary();
		} catch (Exception ex) {
			logger.error("Error in getCLOB() @ OraclePaymentDAO: " + ex, ex);
			tempClob.freeTemporary();
		}

		logger.info("Leaving getCLOB() @ OraclePaymentDAO");
		return tempClob;
	}
}