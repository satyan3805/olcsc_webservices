/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.TolltagDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PersonalInfoResponse;
import com.etcc.csc.dto.bean.OlcMarketSourceRecBean;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_REC;
import com.etcc.csc.util.WSDateUtil;

/**
 * Copied from com.etcc.csc.dao.OracleTolltagDAO
 * @author Stephen Davidson
 */
public class OracleTolltagDAO extends TolltagDAO {

    public AccountLoginDTO createAccount(String firstName, String middleInitial, String lastName, String companyName,
            String email, String loginId, @Sensitive String securityQuestion, @Sensitive String securityAnswer, String ipAddress,
            String jsessionId, String userEnv) throws EtccErrorMessageException, EtccException {
        CallableStatement cstmt = null;
        try {

            String call = "{? = call OLCSC_ACCT_MGMT.Account_creation_Info( " + "p_first_name=>?, "
                    + "p_middle_initial=>?," + "p_last_name=>?," + "p_company_name=>?," + "p_email_address=>?,"
                    + "p_user_id=>?," + "p_sec_question=>?," + "p_sec_answer=>?," + "p_ip_address=>?,"
                    + "p_jsession_id=>?," + "p_user_env_attribute1=>?," + "o_acct_id=>?," + "o_session_id=>?,"
                    + "o_error_msg_arr=>?)}";

            setTypeMap();
            cstmt = this.conn.prepareCall(call);

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, firstName);
            cstmt.setString(3, middleInitial);
            cstmt.setString(4, lastName);
            cstmt.setString(5, companyName);
            cstmt.setString(6, email);
            cstmt.setString(7, loginId);
            cstmt.setString(8, securityQuestion);
            cstmt.setString(9, securityAnswer);
            cstmt.setString(10, ipAddress);
            cstmt.setString(11, jsessionId);
            cstmt.setString(12, userEnv);

            cstmt.registerOutParameter(13, Types.BIGINT);
            cstmt.registerOutParameter(14, Types.VARCHAR);
            cstmt.registerOutParameter(15, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            int result = cstmt.getInt(1);
            AccountLoginDTO accountLoginDTO = null;

            if (result == 1) {
                accountLoginDTO = new AccountLoginDTO();
                accountLoginDTO.setAcctId(cstmt.getLong(13));
                accountLoginDTO.setDbSessionId(cstmt.getString(14));
                accountLoginDTO.setLastLoginIp(ipAddress);
                accountLoginDTO.setLoginId(loginId);
            } else {
                Object[] objArray = (Object[]) cstmt.getArray(15).getArray(this.conn.getTypeMap());

                if (objArray != null && objArray.length > 0) {
                    EtccErrorMessageException em = new EtccErrorMessageException(
                            "TolltagWS::Database generated error message");
                    for (int i = 0; i < objArray.length; i++) {
                        em.addRecoverable(((OLC_ERROR_MSG_REC) objArray[i]).getERROR_MSG());
                    }
                    throw em;
                }// else
                throw new EtccException("Fatal error in createAccount.");
            }

            return accountLoginDTO;

        } catch (SQLException e) {
            throw new EtccException("Error creating account: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }
    }

    public PersonalInfoResponse setPersonalInfo(long accountId, String address, String addressLine2, String city,
            String state, String zipcode, String zipPlus4, String homePhoneNumber, String workPhoneNumber,
            String workPhoneExt, @Sensitive String dlNumber, String dlState, String monthlyStmtFlag, int msId, String dbSessionId,
            String loginId, String ipAddress, long transactionId, boolean checkDuplicateDriverLicense, Long posId)
            throws EtccErrorMessageException, EtccException {
        PersonalInfoResponse response = null;
        CallableStatement cstmt = null;
        try {
            String call = "{? = call OLCSC_ACCT_MGMT.personal_info(" + "p_acct_id=>?," + "p_address1=>?, "
                    + "p_address2=>?, " + "p_city=>?, " + "p_state=>?, " + "p_zip_code=>?, " + "p_plus4=>?, "
                    + "p_home_pho_nbr=>?, " + "p_work_pho_nbr=>?, " + "p_work_pho_ext=>?, " + "p_driver_lic_nbr=>?, "
                    + "p_driver_lic_state=>?, " + "p_mo_stmt_flag=>?, " + "p_ms_id=>?, " + "p_session_id=>?, "
                    + "p_ip_address=>?, " + "p_user_id=>?, " + "p_pos_id=>?, "
                    + "o_dupe_dl_flag=>?, o_rtl_trxn_id=>?, " + // 21
                    "o_error_msg_arr=>?" + // 22
                    ")}";

            setTypeMap();
            cstmt = this.conn.prepareCall(call);

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setLong(2, accountId);
            cstmt.setString(3, address);
            cstmt.setString(4, addressLine2);
            cstmt.setString(5, city);
            cstmt.setString(6, state);
            cstmt.setString(7, zipcode);
            cstmt.setString(8, zipPlus4);
            cstmt.setString(9, homePhoneNumber);
            cstmt.setString(10, workPhoneNumber);
            cstmt.setString(11, workPhoneExt);
            cstmt.setString(12, dlNumber);
            cstmt.setString(13, dlState);
            cstmt.setString(14, monthlyStmtFlag);
            cstmt.setInt(15, msId);
            cstmt.setString(16, dbSessionId);
            cstmt.setString(17, ipAddress);
            cstmt.setString(18, loginId);

            cstmt.setLong(19, (posId == null ? -1 : posId.longValue()));

            cstmt.setString(20, checkDuplicateDriverLicense ? "Y" : "N");
            cstmt.setLong(21, transactionId);

            cstmt.registerOutParameter(20, Types.VARCHAR);
            cstmt.registerOutParameter(21, Types.BIGINT);
            cstmt.registerOutParameter(22, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            int result = cstmt.getInt(1);

            if (result == 1) {
                response = new PersonalInfoResponse();
                response.setDuplicateDriverLicense("Y".equals(cstmt.getString(20)));
                response.setTransactionId(cstmt.getLong(21));
            } else if (result == 0) {
                Object[] objArray = (Object[]) cstmt.getArray(22).getArray(this.conn.getTypeMap());

                if (objArray != null && objArray.length > 0) {
                    EtccErrorMessageException em = new EtccErrorMessageException(
                            "TolltagWS::Database generated error message");
                    for (int i = 0; i < objArray.length; i++) {
                        em.addRecoverable(((OLC_ERROR_MSG_REC) objArray[i]).getERROR_MSG());
                    }
                    throw em;
                } // else
                throw new EtccException("Fatal error in setPersonalInfo.");
            } else {
                throw new EtccException("Fatal error in setPersonalInfo.");
            }

        } catch (SQLException e) {
            throw new EtccException("Error setting account personal info: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }
        return response;
    }

    public void setPaymentInfo(long acctId, String dbSessionId, String ipAddress, String loginId, long rtlTrxnId,
            String name, String adddress, String addressLine2, String city, String state, String zipcode, String plus4,
            @Sensitive String cardNumber, CreditCardDTO.CreditCardType cardType, @Sensitive String expirationMonth,
            @Sensitive String expirationYear)
            throws EtccErrorMessageException, EtccException {

        CallableStatement cstmt = null;
        try {
            String call = "{? = call OLCSC_ACCT_MGMT.set_payment_info(" + "p_acct_id=>?," + "p_doc_type=>?, "
                    + "p_session_id=>?, " + "p_ip_address=>?, " + "p_user_id=>?, " + "p_payment_info=>?, "
                    + "p_coming_from_payment=>?, " + "p_rtl_trxn_id=>?, " + "p_pmt_amt=>?, " + "o_error_msg_arr=>?"
                    + ")}";

            setTypeMap();
            cstmt = this.conn.prepareCall(call);

            OLC_PAYMENT_INFO_REC temp = new OLC_PAYMENT_INFO_REC();
            temp.setADDRESS_1(adddress);
            temp.setADDRESS_2(addressLine2);
            temp.setCARD_TYPE(new BigDecimal(cardType.getType()));
            // TODO: should the security code really always be "123"?
//            temp.setCardSecCode("123");
            temp.setCARD_EXPIRES(WSDateUtil.monthYearToTimestamp(expirationMonth + "/" + expirationYear));
            temp.setCARD_NBR(cardNumber);
            temp.setCITY(city);
            temp.setFIRST_NAME(name);
            temp.setLAST_NAME(name);
            temp.setPLUS4(plus4);
            temp.setSTATE(state);
            temp.setZIPCODE(zipcode);
            temp.setPMT_TYPE("CREDIT");

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setLong(2, acctId);
            cstmt.setString(3, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(4, dbSessionId);
            cstmt.setString(5, ipAddress);
            cstmt.setString(6, loginId);
            cstmt.setObject(7, temp);
            cstmt.setString(8, "N");
            cstmt.setNull(9, Types.NUMERIC);
            cstmt.registerOutParameter(9, Types.NUMERIC);
            cstmt.setNull(10, Types.NUMERIC);
            cstmt.registerOutParameter(10, Types.NUMERIC);
            cstmt.registerOutParameter(11, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            int result = cstmt.getInt(1);

            if (result == 1) {

            } else if (result == 0) {
                Object[] objArray = (Object[]) cstmt.getArray(11).getArray(this.conn.getTypeMap());

                if (objArray != null && objArray.length > 0) {
                    EtccErrorMessageException em = new EtccErrorMessageException(
                            "TolltagWS::Database generated error message");
                    for (int i = 0; i < objArray.length; i++) {
                        em.addRecoverable(((OLC_ERROR_MSG_REC) objArray[i]).getERROR_MSG());
                    }
                    throw em;
                } // else
                throw new EtccException("Fatal error in setPersonalInfo.");
            } else {
                throw new EtccException("Fatal error in setPersonalInfo.");
            }

        } catch (SQLException e) {
            throw new EtccException("error setting payment info: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }

    }

    public OlcMarketSourceRecBean[] getMarketSource() throws EtccException {
        OlcMarketSourceRecBean[] olcMarketSourceRecs = null;
        CallableStatement cstmt = null;
        try {
            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_MARKET_SOURCE_REC", OlcMarketSourceRecBean.class);
            cstmt = this.conn.prepareCall("{? = call olcsc_util.get_mark_source(?, ?)}");

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.registerOutParameter(2, Types.ARRAY, "OL_OWNER.OLC_MARKET_SOURCE_ARR");
            cstmt.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.execute();

            if (cstmt.getInt(1) == 1) {
                Object[] objArray = (Object[]) cstmt.getArray(2).getArray(this.conn.getTypeMap());
                if (objArray != null && objArray.length > 0) {
                    olcMarketSourceRecs = new OlcMarketSourceRecBean[objArray.length];
                    for (int i = 0; i < objArray.length; i++) {
                        olcMarketSourceRecs[i] = (OlcMarketSourceRecBean) objArray[i];
                    }
                }
                return olcMarketSourceRecs;
            } // else
            throw new EtccException("Fatal error in getMarketSource");
        } catch (SQLException e) {
            throw new EtccException("error getting market source: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }
    }

    public double getTagPayInfo(long accountId, String dbSessionId, String loginId, String ipAddress,
            long transactionId, Long posId) throws EtccErrorMessageException, EtccException {
        CallableStatement cstmt = null;
        try {
            String call = "{? = call OLCSC_ACCT_MGMT.GET_TAGSTORE_PMT_AMT(" + "p_doc_id=>?," + "p_session_id=>?, "
                    + "p_ip_address=>?, " + "p_user_id=>?, " + "p_pos_id=>?, " + // 6
                    "p_rtl_trxn_id=>?, " + // 7
                    "o_pmt_amt=>?, " + // 8
                    "o_error_msg_arr=>?" + // 9
                    ")}";

            setTypeMap();
            cstmt = this.conn.prepareCall(call.toString());

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setLong(2, accountId);
            cstmt.setString(3, dbSessionId);
            cstmt.setString(4, ipAddress);
            cstmt.setString(5, loginId);
            cstmt.setLong(6, (posId == null ? -1 : posId.longValue()));
            cstmt.setLong(7, transactionId);
            cstmt.registerOutParameter(8, Types.DOUBLE);
            cstmt.registerOutParameter(9, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            int result = cstmt.getInt(1);

            if (result == 1) {
                return cstmt.getLong(8);
            }// else
            Object[] objArray = (Object[]) cstmt.getArray(9).getArray(this.conn.getTypeMap());

            if (objArray != null && objArray.length > 0) {
                EtccErrorMessageException em = new EtccErrorMessageException(
                        "TolltagWS::Database generated error message");
                for (int i = 0; i < objArray.length; i++) {
                    em.addRecoverable(((OLC_ERROR_MSG_REC) objArray[i]).getERROR_MSG());
                }
                throw em;
            } // else
            throw new EtccException("Fatal error in getTagPayInfo.");

        } catch (SQLException e) {
            throw new EtccException("Error getting payment info: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }
    }

    public boolean makePayment(long accountId, String dbSessionId, String loginId, String ipAddress,
            long transactionId, double paymentAmount, String cardCode, String paymentType,
            @Sensitive String cardNumber, @Sensitive String expireMonth, @Sensitive String expireYear,
            String nameOnCard, String address, String address2, String city,
            String state, String zipCode, String plus4, @Sensitive String cardSecurityCode) throws EtccErrorMessageException,
            EtccException {
        boolean paymentSuccess = false;

        try {
            // OLCSC_ACCT_MGMT acctMgmt = new OLCSC_ACCT_MGMT(this.conn);
            OLC_PAYMENT_INFO_REC olcPaymentInfoRec = new OLC_PAYMENT_INFO_REC();
           /* olcPaymentInfoRec.setPMT_TYPE(paymentType);
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

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            // BigDecimal[] pTransactionId = new BigDecimal[] { new BigDecimal(transactionId) };
            // BigDecimal[] pPaymentAmount = new BigDecimal[] { new BigDecimal(paymentAmount) };

            // Commented out due to changes for HCTRA
            // int result =
            // acctMgmt.MAKE_PAYMENT("" + accountId, "AC", dbSessionId,
            // ipAddress, loginId, pTransactionId,
            // pPaymentAmount, olcPaymentInfoRec, "",
            // O_ERROR_MSG_ARR).intValue();

            int result = 1;
            if (result == 1) {
                paymentSuccess = true;

            } else {
                if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null
                        && O_ERROR_MSG_ARR[0].getArray().length > 0) {
                    OLC_ERROR_MSG_REC[] errorMsgRecs = O_ERROR_MSG_ARR[0].getArray();
                    EtccErrorMessageException em = new EtccErrorMessageException(
                            "PaymentWS::getPaymentDetail error message");
                    for (int i = 0; i < errorMsgRecs.length; i++) {
                        em.addRecoverable(errorMsgRecs[i].getERROR_MSG());
                    }
                    throw em;
                } // else
                throw new EtccException("PaymentWS::getPaymentDetail fatal error");
            }
        } catch (SQLException e) {
            throw new EtccException("error making payment: " + e.getMessage(), e);
        }

        return paymentSuccess;
    }

    public String loginCreation(BigDecimal accountId, String loginType, String dbSessionId, String loginId,
            String ipAddress, String firstName, String middleInitial, String lastName, String email)
            throws EtccErrorMessageException, EtccException {
        try {

            String[] O_SESSION_ID = new String[] { "" };
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            int result = 0;
            /*
             * new OLCSC_ACCT_MGMT(conn).LOGIN_CREATION(accountId, loginType, dbSessionId, ipAddress, firstName,
             * middleInitial, lastName, email, loginId, O_SESSION_ID, O_ERROR_MSG_ARR).intValue();
             */
            if (result == 1) {
                return O_SESSION_ID[0];
            }// else
            if (O_ERROR_MSG_ARR[0] != null && O_ERROR_MSG_ARR[0].getArray() != null
                    && O_ERROR_MSG_ARR[0].getArray().length > 0) {
                OLC_ERROR_MSG_REC[] errorMsgRecs = O_ERROR_MSG_ARR[0].getArray();
                EtccErrorMessageException em = new EtccErrorMessageException("loginCreation error message");
                for (int i = 0; i < errorMsgRecs.length; i++) {
                    em.addRecoverable(errorMsgRecs[i].getERROR_MSG());
                }
                throw em;
            } // else
            throw new EtccException("loginCreation fatal error");
        } catch (SQLException e) {
            throw new EtccException("error creating login: " + e.getMessage(), e);
        }
    }
}
