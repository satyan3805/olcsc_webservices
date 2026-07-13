/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountUtilDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;

/**
 * Oracle implementation of the AccountUtilDAO.  Copied from OLCSCService com.etcc.csc.dao.OracleNewAccountUtilDAO.
 * @author Stephen Davidson
 */
public class OracleAccountUtilDAO extends AccountUtilDAO {
    private static final Logger logger = Logger.getLogger(OracleAccountUtilDAO.class);

    public AccountLoginDTO checkCloseAccount(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException {
        CallableStatement cstmt = null;
        try {
            long acctId = accountLogin.getAcctId();
            String loginType = accountLogin.getLoginTypeString();
            String dbSessionId = accountLogin.getDbSessionId();
            String lastLoginIp = accountLogin.getLastLoginIp();
            String loginId = accountLogin.getLoginId();
            if (logger.isDebugEnabled())
                logger.debug("olcsc_req_close_acct.req_close_acct_validations(acctId=" + acctId +
                ", loginType=" + loginType + ", dbSessionId=" + dbSessionId + ", lastLoginIp=" + lastLoginIp +
                ", loginId=" + loginId + ", OLC_ERROR_MSG_ARR);");
            //                 1                                                   2  3  4  5  6  7
            String theCall = "{? = call olcsc_req_close_acct.req_close_acct_validations(?, ?, ?, ?, ?, ?)}";
            cstmt = this.conn.prepareCall(theCall);
            @SuppressWarnings("unused")
            Map<String, Class<?>> typeMap  = setTypeMap();

            int idx = 1;
            cstmt.registerOutParameter(idx, Types.INTEGER);
            idx++;
            cstmt.setLong(idx, acctId);
            idx++;
            cstmt.setString(idx, loginType);
            idx++;
            cstmt.setString(idx, dbSessionId);
            idx++;
            cstmt.setString(idx, lastLoginIp);
            idx++;
            cstmt.setString(idx, loginId);
            idx++;
            int errorIdx = idx;
            cstmt.registerOutParameter(errorIdx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            if (logger.isDebugEnabled())
                logger.debug("checkCloseAccount.idx=" + idx);
            cstmt.execute();
            int result = cstmt.getInt(1);
            if (logger.isDebugEnabled())
                logger.info("checkCloseAccount.result: " + result);
            if (result == -1) {
                throw new EtccSecurityException("Check close account operation is blocked");
            } else if (result == 0) {
                Array errors = (Array)cstmt.getObject(errorIdx);
                accountLogin.setErrors(OracleUtil.convertToMessages(errors));
                return accountLogin;
            } else {
                return accountLogin;
            }
        } catch (SQLException sqle) {
            String message = "checkCloseAccount SQL problem: " + sqle.getMessage();
            throw new EtccException(message, sqle);
        } finally {
            close(cstmt);
        }
    }

    /**
     * @deprecated use {@link #closeAccountImpl(AccountLoginDTO, String)}
     * @param accountLogin
     * @param refundType
     * @return
     * @throws EtccSecurityException
     * @throws SQLException
     * @throws EtccException
     * @see {@link #closeAccountImpl(AccountLoginDTO, String)}
     */
    @SuppressWarnings("unused")
	@Deprecated
    private AccountLoginDTO closeAccountSqlj(AccountLoginDTO accountLogin, String refundType) throws EtccSecurityException, SQLException, EtccException {
        // jpublisher fails to generate sqlj due to declaration that are not used by OLCSC
        boolean sqljGenerated = false;
        if (sqljGenerated == false)
            throw new RuntimeException("olcsc_req_close_acct.REQ_CLOSE_ACCT");
        BigDecimal acctId = BigDecimal.valueOf(accountLogin.getAcctId());
//        String loginType = accountLogin.getLoginType();
        String dbSessionId = accountLogin.getDbSessionId();
        String lastLoginIp = accountLogin.getLastLoginIp();
        String loginId = accountLogin.getLoginId();
        OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
        if (logger.isDebugEnabled()) {
            logger.debug("new olcsc_req_close_acct(conn).REQ_CLOSE_ACCT(acctId" + acctId +
            ", loginType=" + accountLogin.getLoginType() + ", dbSessionId=" + dbSessionId + ", lastLoginIp=" + lastLoginIp +
            ", loginId=" + loginId + ", refundType=" + refundType + ", OLC_ERROR_MSG_ARR);");
        }
        BigDecimal ret = new BigDecimal("-1");
        /*BigDecimal ret = new olcsc_req_close_acct(conn).REQ_CLOSE_ACCT(acctId, loginType, dbSessionId,
            lastLoginIp, loginId, refundType, O_ERROR_MSG_ARR);*/
        if (logger.isInfoEnabled())
            logger.info("closeAccount.ret=" + ret);
        int result = ret.intValue();
        if (result == -1) {
            throw new EtccSecurityException("Close account operation is blocked");
        } else if (result == 0) {
            accountLogin.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
            logger.warn("Errors closing account: " + ErrorMessageDTO.toStringBuilder(accountLogin.getErrors()));
            return accountLogin;
        } else {
            return accountLogin;
        }
    }

    @Override
    public String loadCloseAccountAgreement() throws EtccException {
        String result = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_PARAM.get_agreement(?,?,?)}");
            setTypeMap();
            int paramIdx = 1;
            cstmt.registerOutParameter(paramIdx, Types.SMALLINT);
            paramIdx++;
            cstmt.setString(paramIdx, "CLOSE_AGREEMENT");
            paramIdx++;
            int agreementIdx = paramIdx;
            cstmt.registerOutParameter(agreementIdx, Types.CLOB);
            paramIdx++;
            int errorIdx = paramIdx;
            cstmt.registerOutParameter(errorIdx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.execute();
            if (cstmt.getInt(1) == 1) {
                result = cstmt.getString(agreementIdx);
            }
            return result;
        } catch (SQLException e) {
            throw new EtccException("Could not retrieve Account Closure Terms and Conditions: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }
    	//return "";
    }

    @Override
    protected ResultDTO closeAccountImpl(AccountLoginDTO accountLogin, String typeOfRefund) throws SQLException, EtccSecurityException {
    	// JDBC implementation of closeAccount
        CallableStatement cstmt = null;
        try {
            long acctId = accountLogin.getAcctId();
            String loginType = accountLogin.getLoginTypeString();
            String dbSessionId = accountLogin.getDbSessionId();
            String lastLoginIp = accountLogin.getLastLoginIp();
            String loginId = accountLogin.getLoginId();
            /*Timestamp agreementDate;
            agreementDate = new Timestamp(Calendar.getInstance().getTime().getTime( ));*/
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            Date agreementDate = new Date(timeInMillis);
            Time agreementTime = new Time(timeInMillis);

            if (logger.isDebugEnabled())
                logger.debug("olcsc_req_close_acct.req_close_acct(acctId=" + acctId +
                ", loginType=" + loginType + ", dbSessionId=" + dbSessionId + ", lastLoginIp=" + lastLoginIp +
                ", loginId=" + loginId + ", refundType=" + typeOfRefund + ", agreementDate=" + agreementDate +
                ", OLC_ERROR_MSG_ARR);");
            //                 1                                            2  3  4  5  6  7  8  9
            String theCall = "{? = call olcsc_req_close_acct.req_close_acct(?, ?, ?, ?, ?, ?, ?, ?)}";
            @SuppressWarnings("unused")
            Map<String, Class<?>> typeMap = setTypeMap();
            cstmt = this.conn.prepareCall(theCall);

            int idx = 1;
            cstmt.registerOutParameter(idx, Types.INTEGER);
            idx++;
            cstmt.setLong(idx, acctId);
            idx++;
            cstmt.setString(idx, loginType);
            idx++;
            cstmt.setString(idx, dbSessionId);
            idx++;
            cstmt.setString(idx, lastLoginIp);
            idx++;
            cstmt.setString(idx, loginId);
            idx++;
            cstmt.setString(idx, typeOfRefund ==  null ? "" : typeOfRefund);
            idx++;
            //cstmt.setDate(idx, agreementDate);
            cstmt.setTime(idx, agreementTime);
            idx++;
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.execute();
            int result = cstmt.getInt(1);
            if (logger.isInfoEnabled())
                logger.info("olcsc_req_close_acc.req_close_account result: " + result);
            if (result == -1) {
                throw new EtccSecurityException("Close account operation is blocked");
            } else {
                ResultDTO resultCarrier = new ResultDTO();
                if (result == 0)
                    resultCarrier.setErrors(OracleUtil.convertToMessages((Array)cstmt.getObject(idx)));
                return resultCarrier;
            }
        } finally {
            close(cstmt);
        }
    }

    @Override
    protected ResultDTO generatePlateReminderEmail(AccountLoginDTO accountLogin, String licensePlate) throws SQLException, EtccSecurityException {
        assert accountLogin.getLoginType() != null : "Login type not set.";
        CallableStatement cstmt = null;
        try {
            long acctId = accountLogin.getAcctId();
            String loginType = accountLogin.getLoginTypeString();
            String dbSessionId = accountLogin.getDbSessionId();
            String lastLoginIp = accountLogin.getLastLoginIp();
            String loginId = accountLogin.getLoginId();
            if (logger.isDebugEnabled())
                logger.debug("new utl_license_plate. generate_lp_reminder_email(acctId=" + acctId +
                ", loginType=" + loginType + ", dbSessionId=" + dbSessionId + ", lastLoginIp=" + lastLoginIp +
                ", loginId=" + loginId + ", licensePlate=" + licensePlate + ", OLC_ERROR_MSG_ARR);");
            //                 1                                            2  3  4  5  6  7  8
            String theCall = "{? = call utl_license_plate.generate_lp_reminder_email(?, ?, ?, ?, ?, ?, ?)}";
            @SuppressWarnings("unused")
            Map<String, Class<?>> typeMap  = setTypeMap();
            cstmt = this.conn.prepareCall(theCall);
            int idx = 1;
            cstmt.registerOutParameter(idx, Types.INTEGER);
            idx++;
            cstmt.setLong(idx, acctId);
            idx++;
            cstmt.setString(idx, loginType);
            idx++;
            cstmt.setString(idx, dbSessionId);
            idx++;
            cstmt.setString(idx, lastLoginIp);
            idx++;
            cstmt.setString(idx, loginId);
            idx++;
            cstmt.setString(idx, licensePlate ==  null ? "" : licensePlate);
            idx++;
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.execute();
            int result = cstmt.getInt(1);
            if (logger.isInfoEnabled())
                logger.info("utl_license_plate. generate_lp_reminder_email: " + result);
            if (result == -1) {
                throw new EtccSecurityException("Generate LP reminder email operation is blocked");
            } else if (result == 0) {
                Array errors = (Array)cstmt.getObject(idx);
                ResultDTO errorMsgCarrier = new ResultDTO();
                errorMsgCarrier.setErrors(OracleUtil.convertToMessages(errors));
                return errorMsgCarrier;
            } else {
                ResultDTO successCarrier = new ResultDTO();
                return successCarrier;
            }
        } finally {
            close(cstmt);
        }
    }

    public AccountLoginDTO autoLogin(String userName, String jSessionId,
            String ipAddress, String sessionId, String acctId, UserEnvDTO userEnvDto) throws EtccSecurityException,
                                                                      EtccException {
    /*
     * Olcsc_csr_auto_login.LOGIN(P_SESSION_ID          IN VARCHAR2,
             P_CSR_USER            IN VARCHAR2,
             P_ACCT_ID             IN NUMBER,
             P_JSESSION_ID         IN VARCHAR2,
             P_IP_ADDRESS          IN VARCHAR2,
             p_browser_type        VARCHAR2 DEFAULT NULL,
             p_browser_version     VARCHAR2 DEFAULT NULL,
             p_os_type             VARCHAR2 DEFAULT NULL,
             p_os_version          VARCHAR2 DEFAULT NULL,
             p_user_env_attribute1 VARCHAR2 DEFAULT NULL,
             p_user_env_attribute2 VARCHAR2 DEFAULT NULL,
             p_user_env_attribute3 VARCHAR2 DEFAULT NULL,
             p_user_env_attribute4 VARCHAR2 DEFAULT NULL,
             p_user_env_attribute5 VARCHAR2 DEFAULT NULL,
             O_SESSION_ID          OUT VARCHAR2,
             o_account_status_code OUT VARCHAR2,
             o_is_locked           OUT VARCHAR2,
             o_error_msg_arr       OUT OLC_error_msg_arr) RETURN NUMBER
     */


     AccountLoginDTO loginDto = null;
     try {
         OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

         String O_SESSION_ID[] = new String[] {new String()};
         String o_account_status_code[] = new String[] {new String()};
         String o_is_locked[] = new String[] {new String()};

         /*BigDecimal result = new Olcsc_csr_auto_login(this.conn).LOGIN(sessionId,userName,new BigDecimal(acctId),
                                                           jSessionId,ipAddress,userEnvDto.getBrowserType(),
                                                           userEnvDto.getBrowserVersion(),userEnvDto.getOsType(),
                                                           userEnvDto.getOsVersion(),userEnvDto.getAttribute1(),
                                                           userEnvDto.getAttribute2(),userEnvDto.getAttribute3(),
                                                           userEnvDto.getAttribute4(),userEnvDto.getAttribute5(),
                                                           O_SESSION_ID,o_account_status_code,o_is_locked,O_ERROR_MSG_ARR);*/

         int ret = -1;
         if (ret == 0) {
             loginDto = new AccountLoginDTO();
             loginDto.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
             logger.error("autoLogin.result=" + ret);
             logger.error("autoLogin.error=" + ErrorMessageDTO.toStringBuilder(loginDto.getErrors()));
         } else if (ret == -1) {
             throw new EtccSecurityException("Security exception in autoLogin");
         }else if (ret == 1){
             loginDto = new AccountLoginDTO();
             loginDto.setAcctId(Long.parseLong(acctId));
             loginDto.setLoginId(userName);
             loginDto.setLastLoginIp(ipAddress);
             loginDto.setAcctStatus(o_account_status_code[0]);
             loginDto.setDbSessionId(O_SESSION_ID[0]);
             loginDto.setLocked("Y".equalsIgnoreCase(o_is_locked[0]));
             loginDto.setLoginType(AccountLoginDTO.LoginType.AC);
         }

     } catch (SQLException sqle) {
         throw new EtccException("Exception in OracleAccountUtilDAO.autoLogin:" + sqle.getMessage());
     }

     return loginDto;
    }
}
