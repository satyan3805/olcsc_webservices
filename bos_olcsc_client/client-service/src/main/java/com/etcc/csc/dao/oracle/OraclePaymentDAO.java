package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.PaymentDAO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.dto.PaymentDetail;
import com.etcc.csc.dto.PaymentResponse;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.Violation;
import com.etcc.csc.dto.bean.OlcPaymentInfoRecBean;
import com.etcc.csc.dto.bean.OlcUninvoicedViolsRecBean;
import com.etcc.csc.dto.bean.OlcVpsInvoicesRecBean;
import com.etcc.csc.plsql.OLCSC_ACCT_MGMT;
import com.etcc.csc.plsql.OLCSC_PARAM;
import com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR;
import com.etcc.csc.plsql.OLC_ACCOUNT_TAG_REC;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_REC;
import com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_REC;
import com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR;
import com.etcc.csc.plsql.OLC_VPS_INVOICES_REC;
import com.etcc.csc.util.PaymentDetailUtil;
import com.etcc.csc.util.WSDateUtil;

/**
 * Oracle version of the PaymentDAO. Based on the original OraclePaymentDAO/OracleNewPaymentDAO from OLCSCService.
 */
public class OraclePaymentDAO extends PaymentDAO {
    private static final Logger logger = Logger.getLogger(OraclePaymentDAO.class);

	@SuppressWarnings("deprecation")
	@Deprecated
	public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress, String loginId, long docId,
            String docType, String licPlate, String licState, BigDecimal transactionId)
            throws EtccException, EtccErrorMessageException {
        PaymentDetail paymentDetail = null;
        try {
            OLC_ACCOUNT_TAG_ARR[] P_ACCOUNT_TAG_ARR = new OLC_ACCOUNT_TAG_ARR[] { new OLC_ACCOUNT_TAG_ARR() };
            String[] P_FORCE_PMT = new String[] { "" };
            BigDecimal[] P_PMT_AMT = new BigDecimal[] { new BigDecimal(0) };
            BigDecimal[] P_RTL_TRXN_ID = new BigDecimal[] { transactionId };

            OLC_VPS_INVOICES_ARR[] O_VPS_INVOICES_ARR = new OLC_VPS_INVOICES_ARR[] { new OLC_VPS_INVOICES_ARR() };
            // OLC_UNINVOICED_VIOLS_ARR[] O_UNINVOICED_VIOLS_ARR =
            // new OLC_UNINVOICED_VIOLS_ARR[] { new OLC_UNINVOICED_VIOLS_ARR() };
//            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            int result = 0;
            /*
             * new OLCSC_INV(conn).GET_PMT_INFO(dbSessionId, ipAddress, loginId, new BigDecimal( docId ), docType,
             * licPlate, licState, P_ACCOUNT_TAG_ARR, P_FORCE_PMT, P_PMT_AMT, P_RTL_TRXN_ID, O_VPS_INVOICES_ARR,
             * O_UNINVOICED_VIOLS_ARR, O_ERROR_MSG_ARR).intValue();
             */
            if (result == 1) {
                paymentDetail = new PaymentDetail();

                if (P_ACCOUNT_TAG_ARR[0] != null && !ArrayUtils.isEmpty(P_ACCOUNT_TAG_ARR[0].getArray())) {
                    OLC_ACCOUNT_TAG_REC[] accountTagRecs = P_ACCOUNT_TAG_ARR[0].getArray();
                    TagDTO[] tags = new TagDTO[accountTagRecs.length];
                    for (int i = 0; i < accountTagRecs.length; i++) {
                        tags[i] = new TagDTO();
                        tags[i].setLicPlate(accountTagRecs[i].getLIC_PLATE());
                        tags[i].setLicState(accountTagRecs[i].getLIC_STATE());
                        tags[i].setVehicleYear(accountTagRecs[i].getVEHICLE_YEAR());
                        tags[i].setVehicleColor(accountTagRecs[i].getVEHICLE_COLOR());
                        tags[i].setVehicleMake(accountTagRecs[i].getVEHICLE_MAKE());
                        tags[i].setVehicleModel(accountTagRecs[i].getVEHICLE_MODEL());
                    }
                    paymentDetail.setTags(tags);
                }

                if (!ArrayUtils.isEmpty(P_FORCE_PMT)) {
                    paymentDetail.setForcePayment(P_FORCE_PMT[0]);
                }

                if (!ArrayUtils.isEmpty(P_PMT_AMT)) {
                    paymentDetail.setTagAmount(P_PMT_AMT[0]);
                }

                if (!ArrayUtils.isEmpty(P_RTL_TRXN_ID)) {
                    paymentDetail.setTransactionId(P_RTL_TRXN_ID[0]);
                }

                if (O_VPS_INVOICES_ARR[0] != null && !ArrayUtils.isEmpty(O_VPS_INVOICES_ARR[0].getArray())) {
                    OLC_VPS_INVOICES_REC[] vpsInvoicesRecs = O_VPS_INVOICES_ARR[0].getArray();
                    Invoice[] invoices = new Invoice[vpsInvoicesRecs.length];
                    for (int i = 0; i < vpsInvoicesRecs.length; i++) {
                        invoices[i] = PaymentDetailUtil.convertToWsInvoice(vpsInvoicesRecs[i]);
                    }
                    paymentDetail.setInvoices(invoices);
                }

                /*
                 * if (O_UNINVOICED_VIOLS_ARR[0] != null && !ArrayUtils.isEmpty(O_UNINVOICED_VIOLS_ARR[0].getArray()))
                 * { OLC_UNINVOICED_VIOLS_REC[] uninvoicedViolsRecs = O_UNINVOICED_VIOLS_ARR[0].getArray(); Violation[]
                 * violations = new Violation[uninvoicedViolsRecs.length]; for (int i = 0; i <
                 * uninvoicedViolsRecs.length; i++) { violations[i] =
                 * PaymentDetailUtil.convertToViolation(uninvoicedViolsRecs[i]); }
                 * paymentDetail.setViolations(violations); }
                 *
                 * } else { /* if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null &&
                 * O_ERROR_MSG_ARR[0].getArray().length > 0) { OLC_ERROR_MSG_REC[] errorMsgRecs =
                 * O_ERROR_MSG_ARR[0].getArray(); EtccErrorMessageException em = new
                 * EtccErrorMessageException("PaymentWS::getPaymentDetail error message"); for (int i = 0; i <
                 * errorMsgRecs.length; i++) { em.addRecoverable(errorMsgRecs[i].getERROR_MSG()); } throw em; } else {
                 * throw new EtccException("PaymentWS::getPaymentDetail fatal error"); }
                 */
                throw new EtccException("PaymentWS::getPaymentDetail fatal error");
            }
        } catch (SQLException e) {
            throw new EtccErrorMessageException("Payment detail retrieval failed: " + e.getMessage(), e);
        }
        return paymentDetail;
    }

	@SuppressWarnings("deprecation")
	@Deprecated
	public PaymentResponse combinedPayment(long docId, String docType, String dbSessionId, String ipAddress,
            String loginId, BigDecimal transactionId, BigDecimal tagAmount, BigDecimal totalAmount, String dlState,
            @Sensitive String dlNumber, String email, boolean updatePmtInfo, @Sensitive String cardCode, String paymentType,
            @Sensitive String cardNumber, @Sensitive String expireMonth, @Sensitive String expireYear, String nameOnCard, String address,
            String address2, String city, String state, String zipCode, String plus4, @Sensitive String cardSecurityCode,
            Invoice[] invoices, Violation[] violations, boolean veaAccepted) throws EtccErrorMessageException,
            EtccException {
        PaymentResponse paymentResponse = null;
        try {

//            BigDecimal acctId = null;
//            if ("AC".equals(docType)) {
//                acctId = new BigDecimal(docId);
//            }

            OLC_PAYMENT_INFO_REC olcPaymentInfoRec = new OLC_PAYMENT_INFO_REC();
            /*olcPaymentInfoRec.setPMT_TYPE(paymentType);
            olcPaymentInfoRec.setCARD_CODE(cardCode);
            olcPaymentInfoRec.setCARD_SEC_CODE(cardSecurityCode);
            olcPaymentInfoRec.setCARD_NBR(cardNumber);
            olcPaymentInfoRec.setCARD_EXPIRES(WSDateUtil.monthYearToTimestamp(expireMonth + "/" + expireYear));
            olcPaymentInfoRec.setNAME_ON_CARD(nameOnCard);
            olcPaymentInfoRec.setADDRESS1(address);
            olcPaymentInfoRec.setADDRESS2(address2);
            olcPaymentInfoRec.setCITY(city);
            olcPaymentInfoRec.setSTATE(state);
            olcPaymentInfoRec.setZIP_CODE(zipCode);
            olcPaymentInfoRec.setPLUS4(plus4);*/

//            OLC_VPS_INV_PMT_ARR P_INV_PMT_ARR = new OLC_VPS_INV_PMT_ARR();
//            OLC_VPS_INV_FEE_PMT_ARR P_INV_FEE_PMT_ARR = new OLC_VPS_INV_FEE_PMT_ARR();
//            OLC_VPS_UNINV_PMT_ARR P_UNINV_VIOL_PMT_ARR = new OLC_VPS_UNINV_PMT_ARR();
//            OLC_VPS_UNINV_FEE_PMT_ARR P_UNINV_FEE_PMT_ARR = new OLC_VPS_UNINV_FEE_PMT_ARR();

//            P_INV_PMT_ARR.setArray(PaymentDetailUtil.wsInvoicesToOLC_VPS_INV_PMT_RECs(invoices, veaAccepted));
//            P_INV_FEE_PMT_ARR.setArray(PaymentDetailUtil.wsInvoicesToOLC_VPS_INV_FEE_PMT_RECs(invoices, veaAccepted));
//            P_UNINV_VIOL_PMT_ARR.setArray(PaymentDetailUtil.wsViolationsToOLC_VPS_UNINV_PMT_RECs(violations));
//            P_UNINV_FEE_PMT_ARR.setArray(PaymentDetailUtil.wsViolationsToOLC_VPS_UNINV_FEE_PMT_RECs(violations));

            String[] P_POSTING_STATUS = new String[] { "" };
            BigDecimal[] P_PAYMENT_ID = new BigDecimal[] { new BigDecimal(0) };
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            int result = 0;
            /*
             * new OLCSC_PAYMENT(conn).MAKE_PAYMENT(new BigDecimal(docId), docType, dbSessionId, ipAddress, loginId,
             * transactionId, acctId, tagAmount, totalAmount, P_INV_PMT_ARR, P_INV_FEE_PMT_ARR, P_UNINV_VIOL_PMT_ARR,
             * P_UNINV_FEE_PMT_ARR, olcPaymentInfoRec, dlState, dlNumber, email, true, updatePmtInfo ? "Y" : "N",
             * P_POSTING_STATUS, P_PAYMENT_ID, O_ERROR_MSG_ARR).intValue();
             */
            if (result == 1) {
                if (!ArrayUtils.isEmpty(P_POSTING_STATUS) && !ArrayUtils.isEmpty(P_PAYMENT_ID)) {
                    paymentResponse = new PaymentResponse();
                    paymentResponse.setPostingStatus(P_POSTING_STATUS[0].trim());
                    paymentResponse.setPaymentId(P_PAYMENT_ID[0]);
                }
            } else {
                if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null
                        && O_ERROR_MSG_ARR[0].getArray().length > 0) {
                    OLC_ERROR_MSG_REC[] errorMsgRecs = O_ERROR_MSG_ARR[0].getArray();
                    EtccErrorMessageException em = new EtccErrorMessageException(
                            "PaymentWS::combinedPayment error message");
                    for (int i = 0; i < errorMsgRecs.length; i++) {
                        em.addRecoverable(errorMsgRecs[i].getERROR_MSG());
                    }
                    throw em;
                } //else
                throw new EtccException("PaymentWS::combinedPayment fatal error");
            }
        } catch (SQLException e){
            throw new EtccException("Exception connecting to Database to make payment: " + e.getMessage(), e);
        }
        return paymentResponse;
    }

    public boolean veaAutoComments(long docId, String docType, String sessionId, String ipAddress, String userId,
            String veaName, OlcVpsInvoicesRecBean[] invoices) throws EtccException {

        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_VPS_COMMENTS.vea_auto_comments(?,?,?,?,?,?,?,?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_VPS_INVOICES_REC", OLC_VPS_INVOICES_REC.class);

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setLong(2, docId);
            cstmt.setString(3, docType);
            cstmt.setString(4, sessionId); // session id
            cstmt.setString(5, ipAddress);
            cstmt.setString(6, userId);
            cstmt.setString(7, veaName);

            ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor("OL_OWNER.OLC_VPS_INVOICES_ARR", this.conn);
            ARRAY array = new ARRAY(arraydesc, this.conn, convertToOracleInvoices(invoices));
            cstmt.setArray(8, array);

            cstmt.registerOutParameter(9, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            byte success = cstmt.getByte(1);

            if (success == 0) {
                if (logger.isInfoEnabled()){
                    final ResultDTO errors = new ResultDTO();
                    errors.setErrors(OracleUtil.convertToMessages(cstmt.getArray(9)));
                    logger.info("Errors: " + ErrorMessageDTO.toStringBuilder(errors.getErrors()));
                }
                return false;
            }
            return true;

        } catch (SQLException ex) {
            throw new EtccException(ex.getMessage(), ex);
        }
    }

    public boolean autoComments(long docId, String docType, String sessionId, String ipAddress, String userId,
            long paymentId, String payerName, double payAmt, String failedFlag, OlcVpsInvoicesRecBean[] invoices,
            OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException {
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_VPS_COMMENTS.auto_comments(?,?,?,?,?,?,?,?,?,?,?,?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_VPS_INVOICES_REC", OLC_VPS_INVOICES_REC.class);
            typeMap.put("OL_OWNER.OLC_UNINVOICED_VIOLS_REC", OLC_UNINVOICED_VIOLS_REC.class);

            // OlcVpsInvoicesRec[] invoices = new OlcVpsInvoicesRec[2];
            // invoices[0] = new OlcVpsInvoicesRec();
            // invoices[0].setInvoiceId(new BigDecimal(10000));
            // invoices[0].setViolatorId(new BigDecimal(390));
            //
            // invoices[1] = new OlcVpsInvoicesRec();
            // invoices[1].setInvoiceId(new BigDecimal(10001));
            // invoices[1].setViolatorId(new BigDecimal(390));

            // OlcUninvoicedViolsRec[] uninvoiced = new OlcUninvoicedViolsRec[2];
            // uninvoiced[0] = new OlcUninvoicedViolsRec();
            // uninvoiced[0].setViolationId(new BigDecimal(10000));
            // uninvoiced[0].setViolatorId(new BigDecimal(390));
            //
            // uninvoiced[1] = new OlcUninvoicedViolsRec();
            // uninvoiced[1].setViolationId(new BigDecimal(10001));
            // uninvoiced[1].setViolatorId(new BigDecimal(390));

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

            ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor("OL_OWNER.OLC_VPS_INVOICES_ARR", this.conn);
            ARRAY array = new ARRAY(arraydesc, this.conn, convertToOracleInvoices(invoices));
            cstmt.setArray(11, array);

            ArrayDescriptor arraydesc1 = ArrayDescriptor.createDescriptor("OL_OWNER.OLC_UNINVOICED_VIOLS_ARR", this.conn);
            ARRAY array1 = new ARRAY(arraydesc1, this.conn, convertToOracleUninvoiced(uninvoiced));
            cstmt.setArray(12, array1);

            cstmt.registerOutParameter(13, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            byte success = cstmt.getByte(1);

            if (success == 0) {
                if (logger.isDebugEnabled()){
                    final ResultDTO errors = new ResultDTO();
                    errors.setErrors(OracleUtil.convertToMessages(cstmt.getArray(9)));
                    logger.debug("Errors: " + ErrorMessageDTO.toStringBuilder(errors.getErrors()));
                }
                return false;
            }

            return true;

        } catch (SQLException ex) {
            throw new EtccException(ex.getMessage(), ex);
        } finally {
            close(cstmt);
        }
    }

    public boolean veaExists(BigDecimal docId, String docType, String dbSessionId, String ipAddress, String loginId,
            String licPlate, String licState) throws EtccException {
        try {
            BigDecimal[] O_VEA_EXIST = new BigDecimal[] { new BigDecimal(0) };
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            OLCSC_ACCT_MGMT pkg = new OLCSC_ACCT_MGMT(this.conn);

            /*int result = pkg.VEA_EXIST_INFO(docId.toPlainString(), docType, dbSessionId, ipAddress, loginId, licPlate,
                    licState, O_VEA_EXIST, O_ERROR_MSG_ARR).intValue();*/

            int result = 0;

            if (result == 1) {
                if (!ArrayUtils.isEmpty(O_VEA_EXIST)) {
                    return O_VEA_EXIST[0].intValue() == 1;
                }
            }
            throw new EtccException("PaymentWS::veaExists fatal error");
        } catch (SQLException e){
            throw new EtccException("Database Exception getting payment receipt: " + e.getMessage(), e);
        }
    }

    /**
     * Method is deprecated due to HCTRA changes.  No replacement is available.
     * @deprecated no replacement available
     * @throws EtccException <b>always</b>
     */
    @SuppressWarnings("deprecation")
	@Deprecated
    public OlcPaymentInfoRecBean getPaymentInfo(BigDecimal docId, String docType, String dbSessionId, String ipAddress,
            String loginId) throws EtccErrorMessageException, EtccException {
        OlcPaymentInfoRecBean/* OLC_PAYMENT_INFO_RECBean */olcPaymentInfoRecBean = null;
        try {
            OLC_PAYMENT_INFO_REC[] O_PAYMENT_INFO_REC = new OLC_PAYMENT_INFO_REC[] { new OLC_PAYMENT_INFO_REC() };
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

//            OLCSC_ACCT_MGMT plsql = new OLCSC_ACCT_MGMT(this.conn);

            // Commented out because the signature has changed due to HCTRA changes
            // int result =
            // plsql.GET_PAYMENT_INFO(docId.toPlainString(), docType, dbSessionId,
            // ipAddress, loginId, O_PAYMENT_INFO_REC,
            // O_ERROR_MSG_ARR).intValue();

            int result = 0;
            if (result == 1) {
                if (!ArrayUtils.isEmpty(O_PAYMENT_INFO_REC)) {
                    olcPaymentInfoRecBean = createOlcPaymentInfoRecBean(O_PAYMENT_INFO_REC[0]); // new
                                                                                                // OLC_PAYMENT_INFO_RECBean(O_PAYMENT_INFO_REC[0]);
                }
            } else {
                if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null
                        && O_ERROR_MSG_ARR[0].getArray().length > 0) {
                    OLC_ERROR_MSG_REC[] errorMsgRecs = O_ERROR_MSG_ARR[0].getArray();
                    EtccErrorMessageException em = new EtccErrorMessageException(
                            "PaymentWS::getPaymentInfo error message");
                    for (int i = 0; i < errorMsgRecs.length; i++) {
                        em.addRecoverable(errorMsgRecs[i].getERROR_MSG());
                    }
                    throw em;
                } else {
                    throw new EtccException("PaymentWS::getPaymentInfo fatal error");
                }
            }
        } catch (SQLException e){
            throw new EtccException("Database Exception getting payment receipt: " + e.getMessage(), e);
        }
        return olcPaymentInfoRecBean;
    }

    public String getPaymentReceipt(BigDecimal docId, String docType, String dbSessionId, String ipAddress,
            String loginId, BigDecimal paymentId, String reportFormat) throws EtccErrorMessageException, EtccException {
        String reportUrl = null;

        try {
            String[] O_REPORT_URL = new String[] { "" };
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

//            int result = new OLCSC_REP(this.conn).ONLINE_RECEIPT_RPT(reportFormat, docId, docType, paymentId, dbSessionId,
//                    ipAddress, loginId, O_REPORT_URL, O_ERROR_MSG_ARR).intValue();
            int result = -1;
            if (result == 1) {
                if (!ArrayUtils.isEmpty(O_REPORT_URL)) {
                    reportUrl = O_REPORT_URL[0].trim();
                }
            } else {
                if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null
                        && O_ERROR_MSG_ARR[0].getArray().length > 0) {
                    OLC_ERROR_MSG_REC[] errorMsgRecs = O_ERROR_MSG_ARR[0].getArray();
                    EtccErrorMessageException em = new EtccErrorMessageException("postViolations error message");
                    for (int i = 0; i < errorMsgRecs.length; i++) {
                        em.addRecoverable(errorMsgRecs[i].getERROR_MSG());
                    }
                    throw em;
                } else {
                    throw new EtccException("postViolations fatal error");
                }
            }
        } catch (SQLException e){
            throw new EtccException("Database Exception getting payment receipt: " + e.getMessage(), e);
        }
        return reportUrl;
    }

    public BigDecimal getPaymentLimit() throws EtccException {
        try {
            return new OLCSC_PARAM(this.conn).GET_OL_PAYMENT_LIMIT(new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() });
        } catch (SQLException e){
            throw new EtccException("Database Exception getting payment limit: " + e.getMessage(), e);
        }
    }

    private OLC_VPS_INVOICES_REC[] convertToOracleInvoices(OlcVpsInvoicesRecBean[] invoices) throws SQLException {
        if (invoices != null && invoices.length > 0) {
        	OLC_VPS_INVOICES_REC[] result = new OLC_VPS_INVOICES_REC[invoices.length];
            for (int i = 0; i < invoices.length; i++) {
                result[i] = new OLC_VPS_INVOICES_REC();
                result[i].setCURR_DUE_DATE(WSDateUtil.calendarToTimestamp(invoices[i].getCurrDueDate()));
                result[i].setFIRST_NAME(invoices[i].getFirstName());
                result[i].setINVOICE_AMOUNT(invoices[i].getInvoiceAmount());
                result[i].setINVOICE_DATE(WSDateUtil.calendarToTimestamp(invoices[i].getInvoiceDate()));
                result[i].setINVOICE_ID(invoices[i].getInvoiceId());
                result[i].setLAST_NAME(invoices[i].getLastName());
                result[i].setLIC_PLATE_NBR(invoices[i].getLicPlateNbr());
                result[i].setLIC_STATE(invoices[i].getLicState());
                result[i].setONLINE_FEE(invoices[i].getOnlineFee());
                result[i].setVEA_AMOUNT(invoices[i].getVeaAmount());
                // result[i].setViolations();
                result[i].setVIOLATOR_ID(invoices[i].getViolatorId());
            }
            return result;
        }

        return null;
    }

    private OLC_UNINVOICED_VIOLS_REC[] convertToOracleUninvoiced(OlcUninvoicedViolsRecBean[] uninvoiced) throws SQLException {
        if (uninvoiced != null && uninvoiced.length > 0) {
        	OLC_UNINVOICED_VIOLS_REC[] result = new OLC_UNINVOICED_VIOLS_REC[uninvoiced.length];
            for (int i = 0; i < uninvoiced.length; i++) {
                result[i] = new OLC_UNINVOICED_VIOLS_REC();
                result[i].setAVI_AMT(uninvoiced[i].getAviAmt());
                result[i].setCASH_AMT(uninvoiced[i].getCashAmt());
                result[i].setSTATUS(uninvoiced[i].getStatus());
                result[i].setVIOLATION_DATE_TIME(WSDateUtil.calendarToTimestamp(uninvoiced[i].getViolationDateTime()));
                result[i].setVIOLATION_ID(uninvoiced[i].getViolationId());
                result[i].setVIOLATION_LOCATION(uninvoiced[i].getViolationLocation());
                result[i].setVIOLATOR_ID(uninvoiced[i].getViolatorId());
                /*
                 * result[i].setFullLocationName(uninvoiced[i].getFullLocationName());
                 * result[i].setLicPlate(uninvoiced[i].getLicPlate());
                 * result[i].setLicState(uninvoiced[i].getLicState());
                 * result[i].setOnlineFee(uninvoiced[i].getOnlineFee());
                 */
            }
            return result;
        }
        return null;
    }

    private OlcPaymentInfoRecBean createOlcPaymentInfoRecBean(OLC_PAYMENT_INFO_REC rec) throws SQLException {
        OlcPaymentInfoRecBean aBean = new OlcPaymentInfoRecBean();
        /*aBean.setPmtType(rec.getPMT_TYPE());
        aBean.setCardCode(rec.getCARD_CODE());
        aBean.setCardSecCode(rec.getCARD_SEC_CODE());
        aBean.setCardNbr(rec.getCARD_NBR());
        aBean.setCardExpires(WSDateUtil.timestampToCalendar(rec.getCARD_EXPIRES()));
        aBean.setNameOnCard(rec.getNAME_ON_CARD());
        aBean.setAddress1(rec.getADDRESS1());
        aBean.setAddress2(rec.getADDRESS2());
        aBean.setCity(rec.getCITY());
        aBean.setState(rec.getSTATE());
        aBean.setZipCode(rec.getZIP_CODE());
        aBean.setPlus4(rec.getPLUS4());
        aBean.setBankAcctType(rec.getBANK_ACCT_TYPE());
        aBean.setBankAcctNumber(rec.getBANK_ACCT_NUMBER());
        aBean.setRoutingNbr(rec.getROUTING_NBR());*/
        return aBean;
    }
}
