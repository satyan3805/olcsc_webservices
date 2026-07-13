/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.xdb.XMLType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.AccountDAO;
import com.etcc.csc.dao.AppDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.oracle.util.CCTokenDAOHelper;
import com.etcc.csc.dao.oracle.util.PersonAndAddressDAOHelper;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountDeviceStatusDTO;
import com.etcc.csc.dto.AccountDocument;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountIopDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountLoginDTO.LoginType;
import com.etcc.csc.dto.AccountNotificationDocument;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.AccountPhoneInfo;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.AuthenticatedSessionidDTO;
import com.etcc.csc.dto.BaseContactInfo;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.BillingInfoResultDTO;
import com.etcc.csc.dto.BillingMethodLimits;
import com.etcc.csc.dto.CreditCardDTO.CreditCardType;
import com.etcc.csc.dto.DocumentUploadResponse;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.GetAccountDocumentResponse;
import com.etcc.csc.dto.GetAccountNotificationsDocumentResponse;
import com.etcc.csc.dto.GetAccountPhoneInfoResponse;
import com.etcc.csc.dto.GetInitialAutoChargeAmountsResponse;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.PersonalInfoUpdateResultDTO;
import com.etcc.csc.dto.RemoveBillingInfoResponse;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagUpdateResultDTO;
import com.etcc.csc.dto.TransactionDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.dto.UserPreferenceResultDTO;
import com.etcc.csc.dto.ValidateDocDownloadResponse;
import com.etcc.csc.payment.checkoutxml.CartItemXmlDetails;
import com.etcc.csc.payment.checkoutxml.CartXML;
import com.etcc.csc.plsql.OLCSC_ACCT_MGMT;
import com.etcc.csc.plsql.OLCSC_LOGIN_MGMT;
import com.etcc.csc.plsql.OLCSC_PAYMENT;
import com.etcc.csc.plsql.OLCSC_REP;
import com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC;
import com.etcc.csc.plsql.OLC_ACCOUNT_TAG_REC;
import com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_REC;
import com.etcc.csc.plsql.OLC_ACCT_ALERT_REC;
import com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_LIC_PLATE_ARR;
import com.etcc.csc.plsql.OLC_LIC_PLATE_REC;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_ARR;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_REC;
import com.etcc.csc.service.App;
import com.etcc.csc.util.CartItemTypeEnum;
import com.etcc.csc.util.CartUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WSDateUtil;


/**
 * Copied from com.etcc.csc.dao.OracleNewAccountDAO in OLCSCService. Oracle implementation of AccountDAO.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class OracleAccountDAO extends AccountDAO {
    private static final Logger logger = Logger.getLogger(OracleAccountDAO.class);
    
    public AccountLoginDTO accountExists(String accountNumber, String tolltagPrefix, String tolltagNumber,
            String driverLicenseState, @Sensitive String driverLicenseNumber, @Sensitive String taxId) throws EtccException {

        AccountLoginDTO acctLoginDto = new AccountLoginDTO();
        // Collection result = null;
        boolean useTollTag = false;
        if (accountNumber == null || accountNumber.trim().length() == 0) {
            useTollTag = true;
        }
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call " + "Olcsc_Acct_Mgmt.document_exists("
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            setTypeMap();

            int idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setString(idx++, null); // session
            cstmt.setString(idx++, null); // user
            cstmt.setString(idx++, null); // ip address
            if (useTollTag) {
                cstmt.setLong(idx, Long.parseLong(tolltagNumber)); // doc_id
                cstmt.registerOutParameter(idx++, Types.NUMERIC);
            } else {
                cstmt.setLong(idx++, Long.parseLong(accountNumber)); // doc_id
            }
            if (useTollTag) {
                cstmt.setString(idx++, AccountLoginDTO.LoginType.TT.toString()); // doc_type
            } else {
                cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString()); // doc_type
            }
            if (useTollTag) {
                cstmt.setString(idx++, "X"); // doc_status
            } else {
                cstmt.setString(idx++, "A"); // doc_status
            }
            cstmt.setString(idx++, tolltagNumber);
            cstmt.setString(idx++, tolltagPrefix);
            cstmt.setString(idx++, driverLicenseState);
            cstmt.setString(idx++, driverLicenseNumber);
            cstmt.setString(idx++, taxId);
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            short docExists = cstmt.getShort(1);
            if (docExists == 0) {
                Array errors = (Array) cstmt.getObject(idx);
                if (errors == null || ((Object[]) errors.getArray()).length == 0) {
                    ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
                    errMsgDto.setMessage("Invalid information entered. " + "Please try again.");
                    acctLoginDto.addError(errMsgDto);
                } else {
                    acctLoginDto.setErrors(OracleUtil.convertToMessages(errors));
                }
                logger.error("accountExists.docExists=" + docExists);
                logger.error("accountExists.accountNumber=" + accountNumber + ";tolltagPrefix=" + tolltagPrefix
                        + ";tolltagNumber=" + tolltagNumber + ";driverLicenseState=" + driverLicenseState
                        + ";driverLicenseNumber=" + driverLicenseNumber + ";taxId=" + taxId);
                logger.error("accountExists.error=" + ErrorMessageDTO.toStringBuilder(acctLoginDto.getErrors()));
            } else {
                // TODO: populate acctId properly
                if (useTollTag) {
                    acctLoginDto.setAcctId(cstmt.getLong(5));
                } else {
                    acctLoginDto.setAcctId(Long.parseLong(accountNumber));
                }
            }

        } catch (SQLException t) {
            throw new EtccException("Error running accountExists: " + t, t);
        } finally {
            close(cstmt);
        }
        return acctLoginDto;
    }

    public AccountLoginDTO setupOnlineAccessStep1(String accountNumber, String tolltagPrefix, String tolltagNumber,
            String driverLicenseState, @Sensitive String driverLicenseNumber, @Sensitive String taxId, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto) throws EtccException {

        AccountLoginDTO acctLoginDto = new AccountLoginDTO();
        boolean useTollTag = false;
        if (StringUtil.isEmpty(accountNumber)) {
            useTollTag = true;
        }

        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call " + "Olcsc_olcsetup.set_online_access_step1("
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?)}");

            setTypeMap();
            int idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            if (useTollTag) {
                cstmt.setNull(idx, Types.NUMERIC); // doc_id
            } else {
                cstmt.setLong(idx, Long.parseLong(accountNumber)); // doc_id
            }
            cstmt.registerOutParameter(idx++, Types.NUMERIC);
            cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(idx++, ipAddress);
            cstmt.setString(idx++, sessionId);
            cstmt.setString(idx++, userEnvDto.getBrowserType());
            cstmt.setString(idx++, userEnvDto.getBrowserVersion());
            cstmt.setString(idx++, userEnvDto.getOsType()); // os type
            cstmt.setString(idx++, userEnvDto.getOsVersion()); // os version
            cstmt.setString(idx++, userEnvDto.getAttribute1());
            cstmt.setString(idx++, userEnvDto.getAttribute2());
            cstmt.setString(idx++, userEnvDto.getAttribute3());
            cstmt.setString(idx++, userEnvDto.getAttribute4());
            cstmt.setString(idx++, userEnvDto.getAttribute5());
            if (tolltagNumber == null || tolltagNumber.length() == 0) {
                cstmt.setNull(idx++, Types.VARCHAR);
                cstmt.setNull(idx++, Types.VARCHAR);
            } else {
                cstmt.setString(idx++, tolltagPrefix);
                cstmt.setString(idx++, tolltagNumber);
            }
            cstmt.setString(idx++, driverLicenseState);
            cstmt.setString(idx++, driverLicenseNumber);
            cstmt.setString(idx++, taxId);
            cstmt.setString(idx++, userEnvDto.getSourceUserName()); //Push Notification Phase II sourceUserName          
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.execute();

            short docExists = cstmt.getShort(1);
            if (docExists == 0) {
                acctLoginDto = new AccountLoginDTO();
                acctLoginDto.setErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
                logger.error("setupOnlineAccessStep1.docExists=" + docExists);

                Pattern CCPattern = Pattern.compile("\\d{14,16}");

            if (CCPattern.matcher(accountNumber).matches())
            {
              String maskAccountNumber = "****"+ accountNumber.substring(accountNumber.length()-4);
              logger.error("setupOnlineAccessStep1.accountNumber=" + maskAccountNumber + ";tolltagPrefix="
                        + tolltagPrefix + ";tolltagNumber=" + tolltagNumber + ";driverLicenseState="
                        + driverLicenseState + ";driverLicenseNumber=" + driverLicenseNumber + ";taxId=" + taxId
                        + ";ipAddress=" + ipAddress + ";sessionId=" + sessionId + ";userEnvDto=" + userEnvDto);
            }
            else
            {
                logger.error("setupOnlineAccessStep1.accountNumber=" + accountNumber + ";tolltagPrefix="
                        + tolltagPrefix + ";tolltagNumber=" + tolltagNumber + ";driverLicenseState="
                        + driverLicenseState + ";driverLicenseNumber=" + driverLicenseNumber + ";taxId=" + taxId
                        + ";ipAddress=" + ipAddress + ";sessionId=" + sessionId + ";userEnvDto=" + userEnvDto);

            }
                logger.error("setupOnlineAccessStep1.error="
                        + ErrorMessageDTO.toStringBuilder(acctLoginDto.getErrors()));
            } else {
                // TODO: populate acctId properly
                if (useTollTag) {
                    acctLoginDto.setAcctId(cstmt.getLong(2));
                } else {
                    acctLoginDto.setAcctId(Long.parseLong(accountNumber));
                }
                acctLoginDto.setDbSessionId(cstmt.getString(idx - 1));
                acctLoginDto.setLastLoginIp(ipAddress);
                acctLoginDto.setLoginId(AccountLoginDTO.GENERIC_USER);
            }

        } catch (SQLException t) {
            throw new EtccException("Error running accountExists: " + t, t);
        } finally {
            close(cstmt);
        }
        return acctLoginDto;
    }

    /**
     * <pre>
     * FUNCTION Set_Account_Login_Info(P_SESSION VARCHAR2,
     * 	P_ACCT_ID NUMBER,
     * 	P_IP_ADDRESS IN VARCHAR2,
     * 	P_USER VARCHAR2,
     * 	P_USER_ID VARCHAR2,
     * 	P_PASSWORD VARCHAR2 DEFAULT NULL,
     * 	P_SECURITY_QUESTION_ID user_verification_questions.q_id%type,
     * 	P_SQ_ANSWER VARCHAR2,
     * 	P_EMAIL VARCHAR2,
     * 	P_ALT_EMAIL VARCHAR2,
     * 	P_ERROR_ARR OUT OLC_ERROR_MSG_ARR,
     * 	P_UPDATE_ACCT BOOLEAN DEFAULT TRUE,
     * 	P_UI_CALL BOOLEAN DEFAULT TRUE,
     * 	O_SESSION_ID OUT VARCHAR2)
     * RETURN NUMBER
     * </pre>
     */
    public AccountLoginDTO setupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
            throws EtccSecurityException, EtccException {

        AccountLoginDTO result = null;
        CallableStatement cstmt = null;
        CallableStatement cstmt2 = null;
        try {
            if (logger.isInfoEnabled()) {
                logger.info(new StringBuilder("setupOnlineAccessStep: acct=").append(acctLoginDto.getAcctId()));
            }
            String loginId = oasDto.getLoginId();
            if (loginId != null) {
                loginId = loginId.toUpperCase();
            }

            setTypeMap();

            cstmt = this.conn.prepareCall("{? = call OLCSC_OLCSETUP.Set_Account_Login_Info(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "null, null, ?)}");
            StringBuilder sb = new StringBuilder();
            sb.append("OLCSC_OLCSETUP.Set_Account_Login_Info(");
            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            sb.append(acctLoginDto.getDbSessionId());
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            sb.append(", ");
            sb.append(acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            sb.append(", ");
            sb.append(acctLoginDto.getLastLoginIp());

            // TODO check the logic
            String user = oasDto.getUserID();
            if (StringUtil.isEmpty(user)) {
                user = oasDto.getLoginId();
            }
            cstmt.setString(idx++, user);
            sb.append(", ");
            sb.append(user);
            cstmt.setString(idx++, user);
            sb.append(", ");
            sb.append(user);

            cstmt.setString(idx++, oasDto.getPassword());
            sb.append(", ");
            sb.append("****");
            cstmt.setInt(idx++, oasDto.getSecurityQuestionID());
            sb.append(", ");
            sb.append(oasDto.getSecurityQuestionID());
            cstmt.setString(idx++, oasDto.getSecurityQuestionAnswer());
            sb.append(", ");
            sb.append("****");
            cstmt.setString(idx++, oasDto.getEmailAddress());
            sb.append(", ");
            sb.append(oasDto.getEmailAddress());
            cstmt.setString(idx++, oasDto.getAlternateEmail());
            sb.append(", ");
            sb.append(oasDto.getAlternateEmail());
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.registerOutParameter(idx, Types.VARCHAR);
            sb.append(")");
            if (logger.isDebugEnabled()) {
                logger.debug(sb.toString());
            }
            cstmt.execute();

            short success = cstmt.getShort(1);
            if (success == 0) {
                Array errors = (Array) cstmt.getObject(idx - 1);
                result = new AccountLoginDTO();
                result.setErrors(OracleUtil.convertToMessages(errors));
                logger.error("setupOnlineAccess.success=" + success);
                logger.error("setupOnlineAccess.acctLoginDto=" + acctLoginDto + ";oasDto=" + oasDto);
                logger.error("setupOnlineAccess.error=" + ErrorMessageDTO.toStringBuilder(result.getErrors()));
            } else if (success == -1) {
                throw new EtccSecurityException("Security exception in " + "setupOnlineAccess");
            } else if (success == 1) {
                result = acctLoginDto;
                result.setDbSessionId(cstmt.getString(idx));
                result.setLoginId(oasDto.getLoginId());
                // result.setLoginId(oasDto.getUserID());
                result.setLoginType(AccountLoginDTO.LoginType.AC);

                //Had to call this function to expire the url link if user resets password using email.
                cstmt2 = this.conn.prepareCall("{? = call Olcsc_Online_Access.set_email_validation_status(?,?)}");
                cstmt2.registerOutParameter(1, Types.SMALLINT);
                cstmt2.setString(2, Long.toString(acctLoginDto.getAcctId()));
                cstmt2.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
                cstmt2.execute();

                short success2 = cstmt2.getShort(1);
                if (success2 == 0) {
                    Array errors = (Array) cstmt2.getObject(3);
                    result = new AccountLoginDTO();
                    result.setErrors(OracleUtil.convertToMessages(errors));
                }
            }

        } catch (SQLException sqle) {
            throw new EtccException("Error running accountExists: " + sqle, sqle);
        } finally {
            close(cstmt);
            close(cstmt2);
        }
        return result;
    }

    /**
     * Called internally by public dao methods. Connection is closed by the calling method.
     * @deprecated loginAccount takes over the function of this method.
     * @param userName
     * @param password
     * @param ipAddress
     * @return
     * @throws SQLException If any exceptions with the Database occur.
     */
    @Deprecated
    protected AccountLoginDTO _validateAccount(String userName, String password, String ipAddress) throws SQLException {

        AccountLoginDTO acctLogin = null;

        CallableStatement cstmt = null;
        try {
        cstmt = this.conn.prepareCall("{? = call OLCSC_LOGIN.LOGIN(?, ?, ?, ?, "
                + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        byte idx = 1;
        if (userName != null) {
            userName = userName.toUpperCase();
        }
        if (password != null) {
            password = password.toUpperCase();
        }

        cstmt.registerOutParameter(idx++, Types.SMALLINT);
        cstmt.setString(idx++, userName);
        cstmt.setString(idx++, password);
        cstmt.setString(idx++, userName); // session id
        cstmt.setString(idx++, ipAddress);
        cstmt.setString(idx++, null); // user env attr 1
        cstmt.setString(idx++, null); // user env attr 2
        cstmt.setString(idx++, null); // user env attr 3
        cstmt.setString(idx++, null); // user env attr 4
        cstmt.setString(idx++, null); // user env attr 5
        cstmt.setString(idx++, null); // user env attr 1
        cstmt.setString(idx++, null); // user env attr 2
        cstmt.setString(idx++, null); // user env attr 3
        cstmt.setString(idx++, null); // user env attr 4
        cstmt.setString(idx++, null); // user env attr 5
        cstmt.registerOutParameter(idx++, Types.NUMERIC);
        cstmt.registerOutParameter(idx++, Types.VARCHAR);
        cstmt.registerOutParameter(idx++, Types.VARCHAR);
        cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

        cstmt.executeUpdate();

        int result = cstmt.getInt(1);
        if (result == 1) {
            acctLogin = new AccountLoginDTO();
            acctLogin.setAcctId(cstmt.getLong(idx - 3));
            acctLogin.setLoginId(userName);
            acctLogin.setLastLoginIp(ipAddress);
            acctLogin.setAcctStatus(cstmt.getString(idx - 1));
            acctLogin.setDbSessionId(cstmt.getString(idx - 2));
        }
        return acctLogin;
        } finally {
            close(cstmt);
        }
    }

    public AccountDTO getAccount(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
    	
    	long acctId = acctLoginDto.getAcctId();
        AccountDTO accountDTO = new AccountDTO();
        try {
            if (logger.isTraceEnabled()) {
                logger.trace(new StringBuilder("getAccount: acct=").append(acctId).append(";acctLoginDto=").append(acctLoginDto));
            }
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            OLC_ACCOUNT_INFO_REC O_ACCT_INFO[] = new OLC_ACCOUNT_INFO_REC[] { new OLC_ACCOUNT_INFO_REC() };
            String O_ACCT_VPN_TYPE[] = new String[] { new String() };
            String P_POSTAL_FLAG[] = new String[] { new String() };
            String P_EMAIL_FLAG[] = new String[] { new String() };
            String P_PHONE_FLAG[] = new String[] { new String() };
            String P_REVENUE_ACCT[] = new String[] { new String() }; // Y or N
            //Pay installment change start 
            String O_PMT_PLAN_FLAG[] = new String[] { new String() };
            BigDecimal[] O_PMT_PLAN_TOTAL_DUE = new BigDecimal[1];
            //Pay installment change end
            BigDecimal ret = new OLCSC_ACCT_MGMT(this.conn).GET_ACCOUNT_INFO(Long.toString(acctId), AccountLoginDTO.LoginType.AC.toString(),
                    acctLoginDto.getDbSessionId(), acctLoginDto.getLastLoginIp(), acctLoginDto.getLoginId(), P_POSTAL_FLAG,
                    P_EMAIL_FLAG, P_PHONE_FLAG, P_REVENUE_ACCT, O_ACCT_INFO, O_ACCT_VPN_TYPE, O_ERROR_MSG_ARR, true,  O_PMT_PLAN_FLAG,O_PMT_PLAN_TOTAL_DUE);//added two parameters for pay installments
            int result = ret.intValue();
            if (logger.isDebugEnabled())
                logger.debug("oracleaccountdao.getAccount.result=" + result);

            if (result == 0) {
                accountDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("getAccount.result=" + result);
                logger.error("getAccount.acctLoginDto=" + acctLoginDto + ";acctId=" + acctId);
                logger.error("getAccount.error=" + ErrorMessageDTO.toStringBuilder(accountDTO.getErrors()));
            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in getAccount");
            } else {
                populateAccountDTO(O_ACCT_INFO[0], accountDTO);
                accountDTO.setByEmail(convertYN(P_EMAIL_FLAG));
                accountDTO.setByMail(convertYN(P_POSTAL_FLAG));
                accountDTO.setByPhone(convertYN(P_PHONE_FLAG));
                accountDTO.setRevenueAccount(convertYN(P_REVENUE_ACCT));
                //Pay installment change start 
                accountDTO.setPaymentPlanExist(convertYN(O_PMT_PLAN_FLAG));
                Double totalAmount = O_PMT_PLAN_TOTAL_DUE[0].doubleValue();
                accountDTO.setPaymentPlanDueAmount(totalAmount);
                //Pay installment change end
                accountDTO.setAcctId(acctId);
            }

            return accountDTO;
        } catch (SQLException sqle) {
            throw new EtccException("Error running getAccount: " + sqle, sqle);
        }
    }

    private void populateAccountDTO(OLC_ACCOUNT_INFO_REC P_OLC_ACCOUNT_INFO_REC, AccountDTO accountDTO)
            throws EtccException {
        logger.debug("entering populateAccountDTO...");
        try {
            accountDTO.setAcctTypeCode(P_OLC_ACCOUNT_INFO_REC.getACCT_TYPE_CODE());
            accountDTO.setAcctTypeDescr(P_OLC_ACCOUNT_INFO_REC.getACCT_TYPE_DESCR());
            accountDTO.setFirstName(P_OLC_ACCOUNT_INFO_REC.getFIRST_NAME());
            accountDTO.setMiddleInitial(P_OLC_ACCOUNT_INFO_REC.getMIDDLE_INITIAL());
            accountDTO.setLastName(P_OLC_ACCOUNT_INFO_REC.getLAST_NAME());
            accountDTO.setAddress1(P_OLC_ACCOUNT_INFO_REC.getADDRESS1());
            accountDTO.setAddress2(P_OLC_ACCOUNT_INFO_REC.getADDRESS2());
            accountDTO.setCity(P_OLC_ACCOUNT_INFO_REC.getCITY());
            accountDTO.setState(P_OLC_ACCOUNT_INFO_REC.getSTATE());
            accountDTO.setZipCode(P_OLC_ACCOUNT_INFO_REC.getZIP_CODE());
            accountDTO.setPlus4(P_OLC_ACCOUNT_INFO_REC.getPLUS4());
            accountDTO.setHomePhoNbr(P_OLC_ACCOUNT_INFO_REC.getHOME_PHO_NBR());
            accountDTO.setWorkPhoNbr(P_OLC_ACCOUNT_INFO_REC.getWORK_PHO_NBR());
            accountDTO.setWorkPhoExt(P_OLC_ACCOUNT_INFO_REC.getWORK_PHO_EXT());
            accountDTO.setMobilePhoNbr(P_OLC_ACCOUNT_INFO_REC.getMOBILE_PHO_NBR());
            accountDTO.setDriverLicNbr(P_OLC_ACCOUNT_INFO_REC.getDRIVER_LIC_NBR());
            accountDTO.setDriverLicState(P_OLC_ACCOUNT_INFO_REC.getDRIVER_LIC_STATE());
            accountDTO.setCompanyName(P_OLC_ACCOUNT_INFO_REC.getCOMPANY_NAME());
            if (P_OLC_ACCOUNT_INFO_REC.getBALANCE_AMT() != null) {
            accountDTO.setBalanceAmt(P_OLC_ACCOUNT_INFO_REC.getBALANCE_AMT().doubleValue());
            }
            accountDTO.setBalLastUpdated(WSDateUtil.timestampToCalendar(P_OLC_ACCOUNT_INFO_REC.getBAL_LAST_UPDATED()));
            accountDTO.setEmailAddress(P_OLC_ACCOUNT_INFO_REC.getEMAIL_ADDRESS());
            accountDTO.setEmailAddress2(P_OLC_ACCOUNT_INFO_REC.getALT_EMAIL());
            //Express Account Changes for add plan for get Account
            accountDTO.setPlan(P_OLC_ACCOUNT_INFO_REC.getPLAN());
            // accountDTO.setEmailAddress3(P_OLC_ACCOUNT_INFO_REC.getEMAIL_ADDRESS3());
            // accountDTO.setMoStmtFlag(P_OLC_ACCOUNT_INFO_REC.getMO_STMT_FLAG());
            if (P_OLC_ACCOUNT_INFO_REC.getBAD_ADDRESS_FLAG()!=null 
            		&& "Y".equals(P_OLC_ACCOUNT_INFO_REC.getBAD_ADDRESS_FLAG())) {
            accountDTO.setBadAddressFlag(true);
            }else {
            accountDTO.setBadAddressFlag(false);
            }
            // accountDTO.setMsId(P_OLC_ACCOUNT_INFO_REC.getMS_ID());
            if (P_OLC_ACCOUNT_INFO_REC.getQ_ID() != null)
                accountDTO.setSecurityQuestionID(P_OLC_ACCOUNT_INFO_REC.getQ_ID().longValue());
            accountDTO.setSecurityQuestionAnswer(P_OLC_ACCOUNT_INFO_REC.getSECURITY_QUESTION_ANSWER());
            accountDTO.setCompanyTaxId(P_OLC_ACCOUNT_INFO_REC.getCOMPANY_TAX_ID());
            if(P_OLC_ACCOUNT_INFO_REC.getREBILL_AMT() !=null) {
            accountDTO.setRebillAmt(P_OLC_ACCOUNT_INFO_REC.getREBILL_AMT().doubleValue());
            }
            if(P_OLC_ACCOUNT_INFO_REC.getLOW_BAL_LEVEL() !=null) {
            accountDTO.setLowBalLevel(P_OLC_ACCOUNT_INFO_REC.getLOW_BAL_LEVEL().doubleValue());
            }
            accountDTO.setAddress3(P_OLC_ACCOUNT_INFO_REC.getADDRESS3());
            accountDTO.setAddress4(P_OLC_ACCOUNT_INFO_REC.getADDRESS4());
            final String country = P_OLC_ACCOUNT_INFO_REC.getCOUNTRY();
            if (country != null && country.trim().length() > 0){
                accountDTO.setCountry(country);
                accountDTO.setCountryCode(OracleCountryDAO.getCountryByName(country).getCountryCode());
            }
            accountDTO.setPmtTypeEnum(PaymentType.valueOfCode(P_OLC_ACCOUNT_INFO_REC.getPMT_TYPE_CODE()));
            if(P_OLC_ACCOUNT_INFO_REC.getNO_OF_TAGS() !=null) {
            accountDTO.setNoOfTags(P_OLC_ACCOUNT_INFO_REC.getNO_OF_TAGS().intValue());
            }


            if  (StringUtils.isNotBlank(P_OLC_ACCOUNT_INFO_REC.getPMT_TYPE_CODE())  
            		&& (P_OLC_ACCOUNT_INFO_REC.getPMT_TYPE_CODE().equals("E") 
            		 ||P_OLC_ACCOUNT_INFO_REC.getPMT_TYPE_CODE().equals("C") 
            		 || P_OLC_ACCOUNT_INFO_REC.getPMT_TYPE_CODE().equals("M"))) {
            	
            	if(P_OLC_ACCOUNT_INFO_REC.getCC_DEPOSIT_INCREMENT() !=null) {
	            accountDTO.setDepositIncrementCC(P_OLC_ACCOUNT_INFO_REC.getCC_DEPOSIT_INCREMENT().doubleValue());
            	}
            	if(P_OLC_ACCOUNT_INFO_REC.getEFT_DEPOSIT_INCREMENT() !=null) {
	            accountDTO.setDepositIncrementEFT(P_OLC_ACCOUNT_INFO_REC.getEFT_DEPOSIT_INCREMENT().doubleValue());
            	}
            	if(P_OLC_ACCOUNT_INFO_REC.getCC_MIN_BAL_INCREMENT() !=null) {
	            accountDTO.setMinBalIncrementCC(P_OLC_ACCOUNT_INFO_REC.getCC_MIN_BAL_INCREMENT().doubleValue());
            	}
            	if(P_OLC_ACCOUNT_INFO_REC.getEFT_MIN_BAL_INCREMENT() != null) {
	            accountDTO.setMinBalIncrementEFT(P_OLC_ACCOUNT_INFO_REC.getEFT_MIN_BAL_INCREMENT().doubleValue());
            	}
            	if(P_OLC_ACCOUNT_INFO_REC.getCC_DEPOSIT_AMT() != null) {
	            accountDTO.setDepositAmtCC(P_OLC_ACCOUNT_INFO_REC.getCC_DEPOSIT_AMT().doubleValue());
            	}
            	if(P_OLC_ACCOUNT_INFO_REC.getEFT_DEPOSIT_AMT() !=null) {
	            accountDTO.setDepositAmtEFT(P_OLC_ACCOUNT_INFO_REC.getEFT_DEPOSIT_AMT().doubleValue());
            	}
            	if(P_OLC_ACCOUNT_INFO_REC.getCC_MIN_BAL_AMT() !=null) {
	            accountDTO.setMinBalCC(P_OLC_ACCOUNT_INFO_REC.getCC_MIN_BAL_AMT().doubleValue());
            	}
            	if(P_OLC_ACCOUNT_INFO_REC.getEFT_MIN_BAL_AMT() !=null) {
	            accountDTO.setMinBalEFT(P_OLC_ACCOUNT_INFO_REC.getEFT_MIN_BAL_AMT().doubleValue());
            	}
            }
            // Added as a part of CCRMA changes
            accountDTO.setHomePhoExt(P_OLC_ACCOUNT_INFO_REC.getHOME_PHO_EXT());
            accountDTO.setMobilePhoExt(P_OLC_ACCOUNT_INFO_REC.getMOBILE_PHO_EXT());
            accountDTO.setSmsAlertsOptIn(P_OLC_ACCOUNT_INFO_REC.getSMS_ALERTS_OPT_IN());
           
        } catch (SQLException sqle) {
            throw new EtccException(sqle);
        }
        logger.debug("leaving populateAccountDTO...");
    }

    public AccountLoginDTO loginAccount(String userName, @Sensitive String password, String ipAddress, String sessionId,
            UserEnvDTO userEnvDto) throws EtccException {
        AccountLoginDTO acctLogin = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_LOGIN.LOGIN(?, ?, ?, ?, "
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}");	//defect 9954 txphung

            setTypeMap();
            byte idx = 1;
            if (userName != null) {
                userName = userName.toUpperCase();
            }
            if (password != null) {
                password = password.toUpperCase();
            }

            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setString(idx++, userName);
            cstmt.setString(idx++, password);
            cstmt.setString(idx++, sessionId); // session id
            cstmt.setString(idx++, ipAddress);
            cstmt.setString(idx++, userEnvDto.getBrowserType());
            cstmt.setString(idx++, userEnvDto.getBrowserVersion());
            cstmt.setString(idx++, userEnvDto.getOsType());
            cstmt.setString(idx++, userEnvDto.getOsVersion());
            cstmt.setString(idx++, userEnvDto.getAttribute1());
            cstmt.setString(idx++, userEnvDto.getAttribute2());
            cstmt.setString(idx++, userEnvDto.getAttribute3());
            cstmt.setString(idx++, userEnvDto.getAttribute4());
            cstmt.setString(idx++, userEnvDto.getAttribute5());
            cstmt.setString(idx++, userEnvDto.getSourceUserName());//Push Notification Phase II sourceUserName
            cstmt.registerOutParameter(idx++, Types.NUMERIC);
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx++, Types.TIMESTAMP);
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.registerOutParameter(idx + 1, Types.VARCHAR);	//defect 9954 txphung
            cstmt.registerOutParameter(idx + 2, Types.VARCHAR);	//defect 9954 txphung

            cstmt.executeUpdate();

            int result = cstmt.getInt(1);
            
            if(result == -1) {
           	 acctLogin = new AccountLoginDTO();
           	 acctLogin.setErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
           	 if (acctLogin.hasErrors()) {
           		 String errorMessage = ErrorMessageDTO.toStringBuilder(acctLogin.getErrors()).toString();
           		 logger.error("loginAccount.error=" + errorMessage);
           		 throw new EtccSecurityException("security exception in accountDAO.loginAccount()"+errorMessage);
           	 }else {
           		 throw new EtccSecurityException("security exception in accountDAO.loginAccount()");
           	    }
           }
            if (result == 1) {
                acctLogin = new AccountLoginDTO();
                acctLogin.setAcctId(cstmt.getLong(idx - 7));
                acctLogin.setLoginId(userName);
                acctLogin.setLastLoginIp(ipAddress);
                acctLogin.setAcctActivity(cstmt.getString(idx - 4));
                acctLogin.setAcctStatus(cstmt.getString(idx - 5));
                acctLogin.setDbSessionId(cstmt.getString(idx - 6));
                //Only set this to true if a value of 'N' is present.
                acctLogin.setPwChangeRequired("Y".equalsIgnoreCase(cstmt.getString(idx - 3)));
                acctLogin.setLocked(StringUtil.stringToBoolean(cstmt.getString(idx - 2)));
                acctLogin.setLoginType(AccountLoginDTO.LoginType.AC);
                //defect 9954 txphung: begin
                acctLogin.setLargeAccountFlag(cstmt.getString(idx + 1));
                acctLogin.setExtraLargeAccountFlag(cstmt.getString(idx + 2));
                //defect 9954 txphung: end
                Timestamp tm = cstmt.getTimestamp(idx - 1);
                if (tm != null) {
                    acctLogin.setLastLoginDate(WSDateUtil.timestampToDate(tm));
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("***loginAccount.result=1;PwChangeRequired=" + acctLogin.isPwChangeRequired());
                    logger.debug("***loginAccount.result=1;Locked=" + acctLogin.isLocked());
                }
            } else {
                // get error message
                acctLogin = new AccountLoginDTO();
                acctLogin.setErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
                acctLogin.setLocked(StringUtil.stringToBoolean(cstmt.getString(idx - 1)));

                logger.error("loginAccount.result=" + result);
                logger.error("loginAccount.ipAddress=" + ipAddress + ";sessionId=" + sessionId + ";userEnvDto="
                        + userEnvDto);
                logger.error("loginAccount.error=" + ErrorMessageDTO.toStringBuilder(acctLogin.getErrors()));
            }
        } catch (SQLException e) {
            throw new EtccException("Error getting account for " + userName + " " + e, e);
        } finally {
            close(cstmt);
        }
        return acctLogin;
    }

    public TagDTO[] getAccountTags(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException {

        TagDTO[] tags = null;
        CallableStatement cstmt = null;
        try {
            if (logger.isDebugEnabled())
                logger.debug(new StringBuilder("getAccountTags: acct=").append(accountLoginDTO.getAcctId()));
            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Tag_Info" + "(?,?,?,?,?,null,?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OLC_ACCOUNT_TAG_REC.class);
            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
            cstmt.setLong(idx++, accountLoginDTO.getAcctId());
            cstmt.setString(idx++, accountLoginDTO.getLoginId());
            cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ACCOUNT_TAG_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            byte success = cstmt.getByte(1);
            if (success == 1) {
                Object[] objArray = (Object[]) cstmt.getArray(idx - 1).getArray();
                if (objArray != null && objArray.length > 0) {
                    final int length = objArray.length;
                    tags = new TagDTO[length];
                    for (int i = 0; i < length; i++) {
                        TagDTO tagDto = copyTagProperties((OLC_ACCOUNT_TAG_REC) objArray[i]);
                        tagDto.setAcctId(accountLoginDTO.getAcctId());
                        tags[i] = tagDto;
                    }
                }
            } else if (success == -1) {
                throw new EtccSecurityException("Security Exception in " + "getAccountTags");
            } else {
                logger.error("getAccountTags.success=" + success);
                logger.error("getAccountTags.accountLoginDTO=" + accountLoginDTO);
                if (cstmt.getArray(idx) != null) {
                    accountLoginDTO.setErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
                    logger
                            .error("getAccountTags.error="
                                    + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
                }
            }

        } catch (SQLException sqle) {
            throw new EtccException("errror in getAcccountTags" + sqle, sqle);
        } finally {
            close(cstmt);
        }

        return tags;
    }

    public ErrorMessageDTO[] updateAccount(AccountLoginDTO acctLoginDto, AccountDTO acctDto) throws EtccException,
            EtccSecurityException {

        // Collection result = null;
        // ErrorMessageDTO[] errMsgs = null;
        CallableStatement cstmt = null;
        try {
            if (logger.isDebugEnabled())
                logger.debug(new StringBuilder("updateAccount: acct=").append(acctLoginDto.getAcctId()));

            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Edit_Acct_Info(?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?," + "?,?)}");

            setTypeMap();

            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLoginTypeString());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.setString(idx++, acctDto.getAddress1());
            cstmt.setString(idx++, acctDto.getAddress2());
            cstmt.setString(idx++, acctDto.getCity());
            cstmt.setString(idx++, acctDto.getState());
            cstmt.setString(idx++, acctDto.getZipCode());
            cstmt.setString(idx++, acctDto.getPlus4());
            cstmt.setString(idx++, acctDto.getHomePhoNbr());
            cstmt.setString(idx++, acctDto.getWorkPhoNbr());
            cstmt.setString(idx++, acctDto.getWorkPhoExt());
            cstmt.setString(idx++, acctDto.getMobilePhoNbr());
            cstmt.setString(idx++, acctDto.getEmailAddress());
            cstmt.setString(idx++, acctDto.getEmailAddress2());
            cstmt.setString(idx++, acctDto.getEmailAddress3());
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            short success = cstmt.getShort(1);
            if (success == 0) {
                acctLoginDto.setErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
                logger.error("updateAccount.success=" + success);
                logger.error("updateAccount.acctLoginDto=" + acctLoginDto + ";acctDto=" + acctDto);
                logger.error("updateAccount.error=" + ErrorMessageDTO.toStringBuilder(acctLoginDto.getErrors()));
            } else if (success == -1) {
                throw new EtccSecurityException("Security exception in " + "getAccountInfo");
            }

        } catch (SQLException sqle) {
            throw new EtccException("Error running accountExists: " + sqle, sqle);
        } finally {
            close(cstmt);
        }

        return acctLoginDto.getErrors();
    }

    /**
     * @deprecated the DB package <i>OLCSC_USER_PREFERENCE</i> does not exist.
     */
    @Deprecated
    public UserPreferenceResultDTO getTollTagUserPreference() throws EtccException, EtccSecurityException {
        UserPreferenceResultDTO userPrefResult = new UserPreferenceResultDTO();
        /*CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_USER_PREFERENCE.g_Get_Preference_Info(?,?,'Y')}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_PREF_INFO_REC", OlcPrefInfoRec.class);
            typeMap.put("OL_OWNER.OLC_DEVICE_VALUE_REC", OlcDeviceValueRec.class);
            typeMap.put("OL_OWNER.OLC_USER_PREF_VALUE_REC", OlcUserPrefValueRec.class);
            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_PREF_INFO_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            byte success = cstmt.getByte(1);
            if (success == 1) {
                UserPreferenceDTO[] userPrefs = convertUserPreferenceFromDB((Object[]) cstmt.getArray(idx - 1)
                        .getArray());
                userPrefResult.setUserPreferences(userPrefs);
            } else if (success == -1) {
                throw new EtccSecurityException("Security Exception in " + "getUserPreference");
            } else {
                logger.error("getTollTagUserPreference.success=" + success);
                if (cstmt.getArray(idx) != null) {
                    userPrefResult.setErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
                    logger.error("getTollTagUserPreference.error="
                            + ErrorMessageDTO.toStringBuilder(userPrefResult.getErrors()));
                }
            }
        } catch (SQLException sqle) {
            throw new EtccException("errror in getUserPreference" + sqle, sqle);
        } finally {
            close(cstmt);
        }*/
        return userPrefResult;
    }

    /**
     * @deprecated the DB package <i>OLCSC_USER_PREFERENCE</i> does not exist.
     */
    @Deprecated
    public UserPreferenceResultDTO getUserPreference(AccountLoginDTO accountLoginDTO) throws EtccException,
            EtccSecurityException {
        UserPreferenceResultDTO userPrefResult = new UserPreferenceResultDTO();
        /*CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_USER_PREFERENCE.Get_User_Preference_Info(?,?,?,?,?,?,?,?,?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_PREF_INFO_REC", OLC_REF.class);
            typeMap.put("OL_OWNER.OLC_DEVICE_VALUE_REC", OlcDeviceValueRec.class);
            typeMap.put("OL_OWNER.OLC_USER_PREF_VALUE_REC", OlcUserPrefValueRec.class);
            typeMap.put("OL_OWNER.OLC_IOP_ACCT_INFO_REC", OlcIopAcctInfoRec.class);
            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, accountLoginDTO.getAcctId());
            cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
            cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
            cstmt.setString(idx++, accountLoginDTO.getLoginId());
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_PREF_INFO_ARR");
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_IOP_ACCT_INFO_ARR");
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            byte success = cstmt.getByte(1);
            if (success == 1) {
                UserPreferenceDTO[] userPrefs = convertUserPreferenceFromDB((Object[]) cstmt.getArray(idx - 3)
                        .getArray());
                userPrefResult.setUserPreferences(userPrefs);

                String message = cstmt.getString(idx - 1);
                if (message != null) {
                    ErrorMessageDTO msg = new ErrorMessageDTO();
                    msg.setMessage(message);
                    userPrefResult.addError(msg);
                }

                Array tempArr = null;
                try {
                    tempArr = cstmt.getArray(idx - 2);
                } catch (SQLException e) {
                    logger.error("Exception getting AcountIops: " + e.getMessage(), e);
                }
                if (tempArr != null) {
                    Object[] tempAcctIops = (Object[]) tempArr.getArray();
                    if (tempAcctIops != null) {
                        AccountIopDTO[] acctIops = new AccountIopDTO[tempAcctIops.length];

                        for (int i = 0; i < tempAcctIops.length;) {
                            AccountIopDTO acctIop = new AccountIopDTO();
                            OlcIopAcctInfoRec temp = (OlcIopAcctInfoRec) tempAcctIops[i];
                            acctIop.setAcctId(temp.getAcctId().longValue());
                            acctIop.setAgcyId(temp.getAgcyId().longValue());
                            acctIop.setAgencyId(temp.getAgencyId());
                            acctIop.setIopStatus(temp.getIopStatus());
                            acctIop.setLicPlate(temp.getLicPlate());
                            acctIop.setLicState(temp.getLicState());
                            acctIop.setReasonDescr(temp.getReasonDescr());
                            acctIop.setTag(temp.getTag());
                            acctIop.setTagId(temp.getTagId());
                            acctIop.setUpdateable(StringUtil.stringToBoolean(temp.getUpdateable()));

                            String tagId = temp.getTagId();
                            while (i < tempAcctIops.length && tagId.equals(temp.getTagId())) {
                                boolean flag = false;
                                if (temp.getIopStatus().equals("A")) {
                                    flag = true;
                                }
                                boolean tempUpdateable = StringUtil.stringToBoolean(temp.getUpdateable());
                                if (temp.getAgcyAbbrev().equals(AccountIopDTO.IOP_DFW)) {
                                    acctIop.setAllowDfw(flag);
                                    acctIop.setUpdateDfw(tempUpdateable);
                                } else if (temp.getAgcyAbbrev().equals(AccountIopDTO.IOP_HCTRA)) {
                                    acctIop.setAllowHctra(flag);
                                    acctIop.setUpdateHctra(tempUpdateable);
                                } else if (temp.getAgcyAbbrev().equals(AccountIopDTO.IOP_LOVE)) {
                                    acctIop.setAllowLove(flag);
                                    acctIop.setUpdateLove(tempUpdateable);
                                } else if (temp.getAgcyAbbrev().equals(AccountIopDTO.IOP_TXDOT)) {
                                    acctIop.setAllowTxDot(flag);
                                    acctIop.setUpdateTxDot(tempUpdateable);
                                }
                                if (++i < tempAcctIops.length) {
                                    temp = (OlcIopAcctInfoRec) tempAcctIops[i];
                                }
                            }
                            acctIops[idx] = acctIop;
                        }

                        userPrefResult.setAccountIops(acctIops);
                    }
                }
            } else if (success == -1) {
                throw new EtccSecurityException("Security Exception in " + "getUserPreference");
            } else {
                userPrefResult.setErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
                logger.error("getUserPreference.success=" + success);
                logger.error("getUserPreference.accountLoginDTO=" + accountLoginDTO);
                if (cstmt.getArray(idx) != null) {
                    logger.error("getUserPreference.error="
                            + ErrorMessageDTO.toStringBuilder(userPrefResult.getErrors()));
                }
            }

        } catch (SQLException sqle) {
            throw new EtccException("errror in getUserPreference" + sqle, sqle);
        } finally {
            close(cstmt);
        }
        */
        return userPrefResult;

    }

    /**
     * @deprecated the DB package <i>OLCSC_USER_PREFERENCE</i> does not exist.
     */
    @Deprecated
    public ErrorMessageDTO[] updateUserPreference(AccountLoginDTO accountLoginDTO, AccountIopDTO[] acctIops,
            UserPreferenceDTO[] userPrefs) throws EtccException, EtccSecurityException {
        /*CallableStatement cstmt = null;
        try {
            if (logger.isDebugEnabled())
                logger.debug(new StringBuilder("updateUserPreference: acct=").append(accountLoginDTO.getAcctId()));

            cstmt = this.conn.prepareCall("{? = call OLCSC_USER_PREFERENCE.set_iop_account_info(?,?,?,?,?,?,?,'Y')}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_PREF_INFO_REC", OlcPrefInfoRec.class);
            typeMap.put("OL_OWNER.OLC_DEVICE_VALUE_REC", OlcDeviceValueRec.class);
            typeMap.put("OL_OWNER.OLC_USER_PREF_VALUE_REC", OlcUserPrefValueRec.class);
            typeMap.put("OL_OWNER.OLC_IOP_ACCT_INFO_REC", OlcIopAcctInfoRec.class);

            ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor("OL_OWNER.OLC_IOP_ACCT_INFO_ARR", this.conn);
            ARRAY array = new ARRAY(arraydesc, this.conn, convertAcctIopToDb(acctIops));
            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, accountLoginDTO.getAcctId());
            cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
            cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
            cstmt.setString(idx++, accountLoginDTO.getLoginId());
            cstmt.setArray(idx, array);
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_IOP_ACCT_INFO_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            byte success = cstmt.getByte(1);

            if (success == 1) {
                return updateUserPreference(accountLoginDTO, userPrefs);
            } else if (success == -1) {
                throw new EtccSecurityException("Security Exception in " + "updateUserPreference");
            } else if (success == 0) {
                Array errors = (Array) cstmt.getObject(idx);
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(errors));
                if (!accountLoginDTO.hasErrors()) {
                    ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
                    errMsgDto.setMessage("Unable to save user preferences. " + "Please try again.");
                    accountLoginDTO.addError(errMsgDto);
                }
                logger.error("updateUserPreference.success=" + success);
                logger.error("updateUserPreference.accountLoginDTO=" + accountLoginDTO);
                logger.error("updateUserPreference.error="
                        + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
            }

        } catch (SQLException sqle) {
            throw new EtccException("errror in updateUserPreference" + sqle, sqle);
        } finally {
            close(cstmt);
        }*/

        return accountLoginDTO.getErrors();
    }

    /**
     * @deprecated the DB package <i>OLCSC_USER_PREFERENCE</i> does not exist.
     */
    @Deprecated
    private ErrorMessageDTO[] updateUserPreference(AccountLoginDTO accountLoginDTO, UserPreferenceDTO[] userPrefs)
            throws EtccException, EtccSecurityException {
        CallableStatement cstmt = null;
        /*
        try {

                     FUNCTION Set_Preference_Info(p_doc_id           VARCHAR2,
                                                  p_doc_type         VARCHAR2 DEFAULT 'AC',
                                                  p_session_id       VARCHAR2,
                                                  p_ip_address       VARCHAR2,
                                                  p_user_id          VARCHAR2,
                                                  p_pref_info        olc_pref_info_set_arr,
                                                  o_error_msg_arr    OUT olc_error_msg_arr,
                                                  p_called_from_gui  IN VARCHAR2 DEFAULT 'N')
                        RETURN NUMBER IS

            cstmt = this.conn.prepareCall("{? = call OLCSC_USER_PREFERENCE.set_preference_info(?,?,?,?,?,?,?,'Y')}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_PREF_INFO_SET_REC", OlcIopAcctInfoRec.class);

            ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor("OL_OWNER.OLC_PREF_INFO_SET_ARR", this.conn);
            ARRAY array = new ARRAY(arraydesc, this.conn, convertUserPrefToDb(userPrefs));
            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, accountLoginDTO.getAcctId());
            cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
            cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
            cstmt.setString(idx++, accountLoginDTO.getLoginId());
            cstmt.setArray(idx, array);
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_PREF_INFO_SET_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            byte success = cstmt.getByte(1);

            if (success == -1) {
                throw new EtccSecurityException("Security Exception in " + "updateUserPreference");
            } else if (success == 0) {
                Array errors = (Array) cstmt.getObject(idx);
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(errors));
                if (!accountLoginDTO.hasErrors()) {
                    ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
                    errMsgDto.setMessage("Unable to save user preferences. " + "Please try again.");
                    accountLoginDTO.addError(errMsgDto);
                }
                logger.error("updateUserPreference.success=" + success);
                logger.error("updateUserPreference.accountLoginDTO=" + accountLoginDTO);
                logger.error("updateUserPreference.error="
                        + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
            }

        } catch (SQLException sqle) {
            throw new EtccException("errror in updateUserPreference" + sqle, sqle);
        } finally {
            close(cstmt);
        }
        */
        return accountLoginDTO.getErrors();
    }

    /*
    private UserPreferenceDTO[] convertUserPreferenceFromDB(Object[] objArray) throws SQLException {
        UserPreferenceDTO[] prefs = null;
        if (objArray != null && objArray.length > 0) {
            prefs = new UserPreferenceDTO[objArray.length];
            for (int i = 0; i < objArray.length; i++) {
                UserPreferenceDTO userPrefDto = new UserPreferenceDTO();
                OlcPrefInfoRec temp = (OlcPrefInfoRec) objArray[i];
                userPrefDto.setPrefId(temp.getPrefId().longValue());
                userPrefDto.setDisplayDesc(temp.getDisplayDesc());
                userPrefDto.setPrefValue(temp.getPrefValue());
                userPrefDto.setPrefType(temp.getPrefType());
                userPrefDto.setDisplayOrder(temp.getDisplayOrder().intValue());
                if (temp.getSteId() != null) {
                    userPrefDto.setSteId(temp.getSteId().longValue());
                }
                if (temp.getDeviceValue() != null) {
                    OlcDeviceValueRec[] deviceValues = temp.getDeviceValue().getArray();
                    if (deviceValues != null && deviceValues.length > 0) {
                        int length = deviceValues.length;
                        AccountDeviceDTO[] deviceValueList = new AccountDeviceDTO[length];
                        for (int j = 0; j < length; j++) {
                            OlcDeviceValueRec tempDevice = deviceValues[j];
                            deviceValueList[j] = new AccountDeviceDTO();
                            if (tempDevice.getAcctDeviceId() != null) {
                                deviceValueList[j].setAcctDeviceId(tempDevice.getAcctDeviceId().longValue());
                            }
                            deviceValueList[j].setDefaultFlag(StringUtil.stringToBoolean(tempDevice.getDefaultFlag()));
                            deviceValueList[j].setDeviceValue(tempDevice.getDeviceValue());
                        }
                        userPrefDto.setDeviceValues(deviceValueList);
                    }
                }
                if (temp.getUpValue() != null) {
                    OlcUserPrefValueRec[] userPrefValues = temp.getUpValue().getArray();
                    if (userPrefValues != null && userPrefValues.length > 0) {
                        UserPreferenceValueDTO[] userPrefValueList = new UserPreferenceValueDTO[userPrefValues.length];
                        for (int j = 0; j < userPrefValues.length; j++) {
                            OlcUserPrefValueRec tempUserPref = userPrefValues[j];
                            userPrefValueList[j] = new UserPreferenceValueDTO();
                            if (tempUserPref.getUserPrefId() != null) {
                                userPrefValueList[j].setUserPrefId(tempUserPref.getUserPrefId().longValue());
                            }
                            userPrefValueList[j].setDefaultFlag(StringUtil.stringToBoolean(tempUserPref
                                    .getDefaultFlag()));
                            userPrefValueList[j].setUpValue(tempUserPref.getUpValue());
                        }
                        userPrefDto.setUserPrefValues(userPrefValueList);
                    }
                }
                prefs[i] = userPrefDto;
            }
        }
        return prefs;
    }
    */

    public TagUpdateResultDTO updateAccountTags(AccountLoginDTO acctLoginDto, TagDTO[] tags, boolean checkDuplicate)
            throws EtccException, EtccSecurityException {
        TagUpdateResultDTO tagResult = new TagUpdateResultDTO();
        CallableStatement cstmt = null;
        try {
            if (tags != null && logger.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("updateAccountTags ");
                sb.append("tag list: acct=").append(acctLoginDto.getAcctId());
                for (int i = 0; i < tags.length; i++) {
                    sb.append(tags[i]);
                }
                logger.debug(sb);
            }
            /*
             FUNCTION Modify_Tags(p_acct_id         NUMBER,
                                  p_session         VARCHAR2,
                                  p_ip_address      VARCHAR2,
                                  p_user            VARCHAR2,
                                  p_check_dup       VARCHAR2,
                                  p_trantype_flag   VARCHAR2,
                                  p_account_tag_arr IN OUT olc_account_tag_arr,
                                  p_account_tag_err OUT olc_account_tag_arr,
                                  o_dup_flag        OUT VARCHAR2,
                                  o_error_msg_arr   OUT olc_error_msg_arr)

            */
            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.modify_tags(?,?,?,?,?,?,?,?,?,?)}");
            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OLC_ACCOUNT_TAG_REC.class);

            ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor("OL_OWNER.OLC_ACCOUNT_TAG_ARR", this.conn);
            ARRAY array = new ARRAY(arraydesc, this.conn, convertTagProperties(tags));
            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.setString(idx++, StringUtil.booleanToString(checkDuplicate));
            cstmt.setString(idx++, "U");
            cstmt.setArray(idx, array);
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ACCOUNT_TAG_ARR");
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ACCOUNT_TAG_ARR");
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            byte success = cstmt.getByte(1);

            if (success == 0) {
                Array errors = cstmt.getArray(idx);
                tagResult.setErrors(OracleUtil.convertToMessages(errors));
                logger.error("updateAccountTags.success=" + success);
                logger.error("updateAccountTags.acctLoginDto=" + acctLoginDto + ";checkDuplicate=" + checkDuplicate);
                logger.error("updateAccountTags.error=" + ErrorMessageDTO.toStringBuilder(tagResult.getErrors()));
                boolean hasDuplicates = StringUtil.stringToBoolean(cstmt.getString(idx - 1));
                if (hasDuplicates) {
                    // copy duplicates to the tag array
                    Object[] objArray = (Object[]) cstmt.getArray(idx - 2).getArray();
                    TagDTO[] tagsArr = null;
                    if (objArray != null && objArray.length > 0) {
                        int length = objArray.length;
                        tagsArr = new TagDTO[length];
                        for (int i = 0; i < length; i++) {
                            TagDTO tagDto = copyTagProperties((OLC_ACCOUNT_TAG_REC) objArray[i]);
                            tagDto.setAcctId(acctLoginDto.getAcctId());
                            tagsArr[i] = tagDto;
                        }

                        tagResult.setDuplicateTags(tagsArr);
                    }
                }
            } else if (success == -1) {
                throw new EtccSecurityException("Security Exception in " + "updateAccountTags");
            } else if (success == 1) {
                tagResult.setSuccessful(true);
            }

        } catch (SQLException sqle) {
            throw new EtccException("errror in updateAcccountTags" + sqle, sqle);
        } finally {
            close(cstmt);
        }
        return tagResult;
    }

    public TransactionDTO[] getLastTransactions(AccountLoginDTO acctLoginDto) throws EtccException,
            EtccSecurityException {
        TransactionDTO[] trans = null;
        CallableStatement cstmt = null;
        try {

            /*

             FUNCTION View_txns     (p_doc_id                  accounts.acct_id%type,
                                       p_doc_type                VARCHAR2 default 'AC',
                                       p_user_id                 VARCHAR2,
                                       p_session_id              VARCHAR2,
                                       p_ip_address              VARCHAR2,
                                       p_trans_type_id           transaction_types.trans_type_id%type default 0,
                                       p_called_from_ui boolean  default true,
                                       o_olc_acc_history_arr     OUT olc_account_history_arr,
                                       o_error_msg_arr           OUT OLC_ERROR_MSG_ARR)
            */

            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.View_txns(?,?,?,?,?,null,null,?,?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_ACCOUNT_HISTORY_REC", OLC_ACCOUNT_TRANSACTION_REC.class);

            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);

            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLoginTypeString());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            short success = cstmt.getShort(1);
            if (success == 1) {
                Object[] objArray = (Object[]) cstmt.getArray(idx - 1).getArray();
                if (objArray != null && objArray.length > 0) {
                    final int size = objArray.length;
                    trans = new TransactionDTO[size];
                    for (int i = 0; i < size; i++) {
                        TransactionDTO tranDto = copyTransactionProperties((OLC_ACCOUNT_TRANSACTION_REC) objArray[i]);
                        if (tranDto != null) {
                            trans[i] = tranDto;
                        }
                    }
                }
            } else if (success == -1) {
                throw new EtccSecurityException("Security exception in " + "getLastTransactions");
            } else {
                logger.error("getLastTransactions.success=" + success);
                logger.error("getLastTransactions.acctLoginDto=" + acctLoginDto);
                Array errors = cstmt.getArray(idx);
                if (errors != null) {
                    acctLoginDto.setErrors(OracleUtil.convertToMessages(errors));
                    logger.error("getLastTransactions.error="
                            + ErrorMessageDTO.toStringBuilder(acctLoginDto.getErrors()));
                }
            }
        } catch (SQLException sqle) {
            throw new EtccException("Error running getLastTransactions: " + sqle, sqle);
        } finally {
            close(cstmt);
        }

        return trans;
    }

    public AlertDTO[] getAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        AlertDTO[] alerts = null;
        CallableStatement cstmt = null;
        try {

            /*

             FUNCTION Get_Alerts   (p_doc_id           VARCHAR2,
                                  p_doc_type         VARCHAR2 ,
                                  p_session_id       VARCHAR2,
                                  p_ip_address       VARCHAR2,
                                  p_user_id          VARCHAR2,
                                  o_acct_alert_arr   OUT olc_acct_alert_arr,
                                  o_error_msg_arr    OUT olc_error_msg_arr)
            */

            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Alerts(?, ?, ?, ?, ?, ?, ?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_ACCOUNT_INFO_REC", OLC_ACCOUNT_INFO_REC.class);
            typeMap.put("OL_OWNER.OLC_ACCT_ALERT_REC", OLC_ACCT_ALERT_REC.class);

            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLoginTypeString());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ACCT_ALERT_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            short success = cstmt.getShort(1);
            if (success == 1) {
                Object[] objArray = (Object[]) cstmt.getArray(idx - 1).getArray();
                if (objArray != null && objArray.length > 0) {
                    final int length = objArray.length;
                    alerts = new AlertDTO[length];
                    for (int i = 0; i < length; i++) {
                        AlertDTO alertDto = new AlertDTO();
                        alertDto.setAlertMsg(((OLC_ACCT_ALERT_REC) objArray[i]).getALERT_MSG());
                        alerts[i] = alertDto;
                    }
                }
            } else if (success == -1) {
                throw new EtccSecurityException("Security exception in " + "getAlerts");
            } else {
                logger.error("getAlerts.success=" + success);
                logger.error("getAlerts.acctLoginDto=" + acctLoginDto);
                final Array errors = cstmt.getArray(idx);
                if (errors != null) {
                    acctLoginDto.setErrors(OracleUtil.convertToMessages(errors));
                    logger.error("getAlerts.error=" + ErrorMessageDTO.toStringBuilder(acctLoginDto.getErrors()));
                }
            }
        } catch (SQLException sqle) {
            throw new EtccException("Error running getAlerts: " + sqle, sqle);
        } finally {
            close(cstmt);
        }
        return alerts;
    }

    @Deprecated
    public boolean isPaymentOwed(AccountLoginDTO acctLoginDto, long transId, TagDTO[] licensePlates)
            throws EtccException, EtccSecurityException {
        boolean result = false;
        /*
        CallableStatement cstmt = null;
        try {


             FUNCTION Check_if_owes (p_doc_id           VARCHAR2,
                                     p_doc_type         VARCHAR2 DEFAULT 'AC',
                                     p_session_id       VARCHAR2,
                                     p_ip_address       VARCHAR2,
                                     p_user_id          VARCHAR2,
                                     p_trxn             IN NUMBER,
                                     p_lic_plate_arr    IN olc_lic_plate_invoices_arr,
                                     p_amt_owed         OUT VARCHAR2,
                                     o_error_msg_arr    OUT olc_error_msg_arr) RETURN NUMBER;


            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_LIC_PLATE_INVOICES_REC", OlcLicPlateInvoicesRec.class);

            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.check_if_owes(?, ?, ?, ?, ?, ?, ?, ?)}");


            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLoginTypeString());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.setLong(idx++, transId);
            // cstmt.setArray(idx++, array);
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            short success = cstmt.getShort(1);
            if (success == 1) {
                result = StringUtil.stringToBoolean(cstmt.getString(idx - 1));
            } else if (success == -1) {
                throw new EtccSecurityException("Security exception in " + "isPaymentOwed");
            } else {
                logger.error("isPaymentOwed.success=" + success);
                logger.error("isPaymentOwed.acctLoginDto=" + acctLoginDto + ";transId=" + transId);
                final Array errors = cstmt.getArray(idx);
                if (errors != null) {
                    acctLoginDto.setErrors(OracleUtil.convertToMessages(errors));
                    logger.error("isPaymentOwed.error=" + ErrorMessageDTO.toStringBuilder(acctLoginDto.getErrors()));
                }
            }
        } catch (SQLException sqle) {
            throw new EtccException("Error running isPaymentOwed: " + sqle, sqle);
        } finally {
            close(cstmt);
        }
        */
        return result;
    }

    /**
     * Creates an AccountDTO based on the values given by the Oracle-generated class.
     *
     * @param temp
     * @return
     */

    private AccountDTO copyAccountProperties(OLC_ACCOUNT_INFO_REC temp) {
       AccountDTO acctDto = null;
       try {
           if (temp != null) {
               acctDto = new AccountDTO();
               acctDto.setAddress1(temp.getADDRESS1());
               acctDto.setAddress2(temp.getADDRESS2());
               acctDto.setBalanceAmt(temp.getBALANCE_AMT().doubleValue());
               /*acctDto.setBalLastUpdated(DateUtil.timestampToCalendar(
                   temp.getBalLastUpdated()));*/
               acctDto.setCity(temp.getCITY());
               acctDto.setCompanyName(temp.getCOMPANY_NAME());
               acctDto.setDriverLicNbr(temp.getDRIVER_LIC_NBR());
               acctDto.setDriverLicState(temp.getDRIVER_LIC_STATE());
               acctDto.setEmailAddress(temp.getEMAIL_ADDRESS());
               acctDto.setFirstName(temp.getFIRST_NAME());
               acctDto.setHomePhoNbr(temp.getHOME_PHO_NBR());
               acctDto.setLastName(temp.getLAST_NAME());
               acctDto.setMiddleInitial(temp.getMIDDLE_INITIAL());
               acctDto.setPlus4(temp.getPLUS4());
               acctDto.setState(temp.getSTATE());
               acctDto.setWorkPhoExt(temp.getWORK_PHO_EXT());
               acctDto.setWorkPhoNbr(temp.getWORK_PHO_NBR());
               acctDto.setZipCode(temp.getZIP_CODE());
               acctDto.setMobilePhoNbr(temp.getMOBILE_PHO_NBR());
               acctDto.setEmailAddress2(temp.getEMAIL_ADDRESS2());
               acctDto.setEmailAddress3(temp.getEMAIL_ADDRESS3());
               acctDto.setAcctTypeCode(temp.getACCT_TYPE_CODE());
               acctDto.setAcctTypeDescr(temp.getACCT_TYPE_DESCR());
    //                logger.debug("oraclenewaccountdao.copyAccountProperties.first name=" + acctDto.getFirstName());
    //                logger.debug("oraclenewaccountdao.copyAccountProperties.driver lic=" + acctDto.getDriverLicNbr());
           }
           return acctDto;
       } catch (Exception e) {
           return null;
       }
    }

    /**
     * Creates a TagDTO based on the values given by the Oracle-generated class.
     *
     * @param temp
     * @return
     */
    private TagDTO copyTagProperties(OLC_ACCOUNT_TAG_REC temp) {
        TagDTO tagDto = null;
        try {
            if (temp != null) {
                tagDto = new TagDTO();
                tagDto.setAcctTagStatus(temp.getACCT_TAG_STATUS());
                tagDto.setAgencyId(temp.getAGENCY_ID());
                tagDto.setTagId(temp.getTAG_ID());
                tagDto.setAcctVehicleId(temp.getACCT_VEHICLE_ID().longValue());
                tagDto.setLicPlate(temp.getLIC_PLATE());
                tagDto.setLicState(temp.getLIC_STATE());
                tagDto.setVehicleYear(temp.getVEHICLE_YEAR());
                tagDto.setVehicleColor(temp.getVEHICLE_COLOR());
                tagDto.setVehicleMake(temp.getVEHICLE_MAKE());
                tagDto.setVehicleModel(temp.getVEHICLE_MODEL());
                tagDto.setVehicleClassCode(temp.getVEHICLE_CLASS_CODE());
                tagDto.setVehicleDescr(temp.getVEHICLE_CLASS_DESC());
                tagDto.setTagStatusDesc(temp.getTAG_STATUS_DESC());
                tagDto.setAcctTagSeq(temp.getACCT_TAG_SEQ().longValue());
                tagDto.setVehicleClassDesc(temp.getVEHICLE_CLASS_DESC());
                tagDto.setTemporaryLicPlate(StringUtil.stringToBoolean(temp.getTEMP_PLATE_FLAG()));
                tagDto.setPlateExpireDate(WSDateUtil.timestampToCalendar(temp.getPLATE_EXPIR_DATE()));
                tagDto.setBarcodePrefix(temp.getBARCODE_PREFIX());
                String recTypeCode = temp.getTAG_TYPE_CODE();
                tagDto.setTagTypeCode(recTypeCode);
                if (StringUtils.isNotEmpty(recTypeCode) && "EM".equals(recTypeCode)) { 
                    	tagDto.setMotorcycle(true);
                }
            }
            return tagDto;
        } catch (SQLException e) {
            return null;
        }
    }

    private TransactionDTO copyTransactionProperties(OLC_ACCOUNT_TRANSACTION_REC temp) {
        TransactionDTO tranDto = null;
        try {
            if (temp != null) {
                tranDto = new TransactionDTO();
                tranDto.setAmount(temp.getTRANS_AMT().doubleValue());
                tranDto.setTransTypeDescr(temp.getTRANS_TYPE_DESCR());
//                tranDto.setDirection(temp.getDirection());
//                tranDto.setLane(temp.getLaneName());
//                tranDto.setLicPlate(temp.getLicensePlate());
//                tranDto.setLocation(temp.getLocationName());
//                tranDto.setTagId(temp.getTagId());
//                tranDto.setTransactionDate(WSDateUtil.timestampToCalendar(temp.getTrxnDate()));
//
//                tranDto.setLaneDescription(temp.getLaneFullName());
//                tranDto.setSerialNum(temp.getSerialNum().intValue());
//                tranDto.setPostedDate(WSDateUtil.timestampToCalendar(temp.getPostedDate()));
            }
            return tranDto;
        } catch (SQLException e) {
            return null;
        }
    }

    private OLC_ACCOUNT_TAG_REC[] convertTagProperties(TagDTO[] tags) throws EtccException {

        try {
            OLC_ACCOUNT_TAG_REC[] result = null;
            if (tags != null) {
                result = new OLC_ACCOUNT_TAG_REC[tags.length];
                for (int i = 0; i < tags.length; i++) {
                    OLC_ACCOUNT_TAG_REC tagDto = new OLC_ACCOUNT_TAG_REC();
                    tagDto.setACCT_TAG_STATUS(tags[i].getAcctTagStatus());
                    tagDto.setAGENCY_ID(tags[i].getAgencyId());
                    tagDto.setTAG_ID(tags[i].getTagId());
                    tagDto.setLIC_PLATE(tags[i].getLicPlate());
                    tagDto.setLIC_STATE(tags[i].getLicState());
                    tagDto.setVEHICLE_YEAR(tags[i].getVehicleYear());
                    tagDto.setVEHICLE_COLOR(tags[i].getVehicleColor());
                    tagDto.setVEHICLE_MAKE(tags[i].getVehicleMake());
                    tagDto.setVEHICLE_MODEL(tags[i].getVehicleModel());
                    tagDto.setVEHICLE_CLASS_CODE(tags[i].getVehicleClassCode());
                    tagDto.setTAG_STATUS_DESC(tags[i].getTagStatusDesc());
                    tagDto.setTEMP_PLATE_FLAG(StringUtil.booleanToString(tags[i].isTemporaryLicPlate()));
                    tagDto.setPLATE_EXPIR_DATE(WSDateUtil.calendarToTimestamp(tags[i].getPlateExpireDate()));
                    tagDto.setACCT_TAG_SEQ(new BigDecimal(tags[i].getAcctTagSeq()));

                    result[i] = tagDto;
                }
            }
            return result;
        } catch (SQLException e) {
            throw new EtccException("Error copying tag properties: " + e);
        }
    }

    /*
    private OlcIopAcctInfoRec[] convertAcctIopToDb(AccountIopDTO[] recs) throws EtccException {
        try {
            OlcIopAcctInfoRec[] result = null;
            if (recs != null) {
                final int length = recs.length;
                result = new OlcIopAcctInfoRec[length];
                Collection<OlcIopAcctInfoRec> tempList = new ArrayList<OlcIopAcctInfoRec>(length);
                for (int i = 0; i < length; i++) {
                    OlcIopAcctInfoRec temp = new OlcIopAcctInfoRec();
                    temp.setAcctId(new BigDecimal(recs[i].getAcctId()));
                    temp.setAgencyId(recs[i].getAgencyId());
                    temp.setLicPlate(recs[i].getLicPlate());
                    temp.setLicState(recs[i].getLicState());
                    temp.setReasonDescr(recs[i].getReasonDescr());
                    temp.setTag(recs[i].getTag());
                    temp.setTagId(recs[i].getTagId());
                    temp.setUpdateable(StringUtil.booleanToString(recs[i].isUpdateable()));

                    // dfw
                    temp.setAgcyId(AccountIopDTO.IOP_DFW_AGCY_ID);
                    temp.setIopStatus(recs[i].isAllowDfw() ? "A" : "INA");
                    tempList.add(temp);

                    // HCTRA"
                    OlcIopAcctInfoRec tempHctra = new OlcIopAcctInfoRec();
                    tempHctra.setAcctId(new BigDecimal(recs[i].getAcctId()));
                    tempHctra.setAgencyId(recs[i].getAgencyId());
                    tempHctra.setLicPlate(recs[i].getLicPlate());
                    tempHctra.setLicState(recs[i].getLicState());
                    tempHctra.setReasonDescr(recs[i].getReasonDescr());
                    tempHctra.setTag(recs[i].getTag());
                    tempHctra.setTagId(recs[i].getTagId());
                    tempHctra.setUpdateable(StringUtil.booleanToString(recs[i].isUpdateable()));
                    tempHctra.setAgcyId(AccountIopDTO.IOP_HCTRA_AGCY_ID);
                    tempHctra.setIopStatus(recs[i].isAllowHctra() ? "A" : "INA");
                    tempList.add(tempHctra);

                    // DAL"
                    OlcIopAcctInfoRec tempDal = new OlcIopAcctInfoRec();
                    tempDal.setAcctId(new BigDecimal(recs[i].getAcctId()));
                    tempDal.setAgencyId(recs[i].getAgencyId());
                    tempDal.setLicPlate(recs[i].getLicPlate());
                    tempDal.setLicState(recs[i].getLicState());
                    tempDal.setReasonDescr(recs[i].getReasonDescr());
                    tempDal.setTag(recs[i].getTag());
                    tempDal.setTagId(recs[i].getTagId());
                    tempDal.setUpdateable(StringUtil.booleanToString(recs[i].isUpdateable()));
                    tempDal.setAgcyId(AccountIopDTO.IOP_LOVE_AGCY_ID);
                    tempDal.setIopStatus(recs[i].isAllowLove() ? "A" : "INA");
                    tempList.add(tempDal);

                    // TTA
                    OlcIopAcctInfoRec tempTxDot = new OlcIopAcctInfoRec();
                    tempTxDot.setAcctId(new BigDecimal(recs[i].getAcctId()));
                    tempTxDot.setAgencyId(recs[i].getAgencyId());
                    tempTxDot.setLicPlate(recs[i].getLicPlate());
                    tempTxDot.setLicState(recs[i].getLicState());
                    tempTxDot.setReasonDescr(recs[i].getReasonDescr());
                    tempTxDot.setTag(recs[i].getTag());
                    tempTxDot.setTagId(recs[i].getTagId());
                    tempTxDot.setUpdateable(StringUtil.booleanToString(recs[i].isUpdateable()));
                    tempTxDot.setAgcyId(AccountIopDTO.IOP_TXDOT_AGCY_ID);
                    tempTxDot.setIopStatus(recs[i].isAllowTxDot() ? "A" : "INA");
                    tempList.add(tempTxDot);

                    // result[i] = temp;
                }
                result = tempList.toArray(result);
            }
            return result;
        } catch (SQLException e) {
            throw new EtccException("Error copying acct iop properties: " + e, e);
        }
    }
    */

    /*
    private OlcPrefInfoSetRec[] convertUserPrefToDb(UserPreferenceDTO[] recs) throws EtccException {
        try {
            OlcPrefInfoSetRec[] result = null;
            if (recs != null) {
                final int length = recs.length;
                result = new OlcPrefInfoSetRec[length];
                for (int i = 0; i < length; i++) {
                    OlcPrefInfoSetRec temp = new OlcPrefInfoSetRec();
                    temp.setPrefId(new BigDecimal(recs[i].getPrefId()));
                    temp.setPrefType(recs[i].getPrefType());
                    temp.setPrefValue(recs[i].getPrefValue());
                    temp.setAcctDeviceId(new BigDecimal(recs[i].getSelectedDeviceId()));
                    if (recs[i].getSelecteduserPrefValue() != null) {
                        temp.setUpValue(recs[i].getSelecteduserPrefValue());
                    } else {
                        temp.setUpValue(recs[i].getPrefValue());
                    }
                    result[i] = temp;
                }
            }
            return result;
        } catch (SQLException e) {
            throw new EtccException("Error copying user pref properties: " + e, e);
        }
    }
    */

//    private OlcLicPlateInvoicesRec[] convertToLicPlateArray(TagDTO[] licPlates, long acctId, String acctType)
//            throws EtccException {
//        try {
//            OlcLicPlateInvoicesRec[] result = null;
//            if (licPlates != null) {
//                final int length = licPlates.length;
//                result = new OlcLicPlateInvoicesRec[length];
//                for (int i = 0; i < length; i++) {
//                    OlcLicPlateInvoicesRec lic = new OlcLicPlateInvoicesRec();
//                    lic.setDocId(new BigDecimal(acctId));
//                    lic.setDocType(acctType);
//                    lic.setLicPlate(licPlates[i].getLicPlate());
//                    lic.setLicState(licPlates[i].getLicState());
//                    result[i] = lic;
//                }
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new EtccException("Error converting to License Plate array: " + e);
//        }
//    }

    public boolean isBigAccount(long accountId) throws EtccException {
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_REP.IS_BIG_ACCOUNT(?)}");
            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setLong(2, accountId);
            cstmt.execute();

            return cstmt.getInt(1) == 1;
        } catch (SQLException sqle) {
            // Eat this exception and return false
            logger.warn("Exception checking if BigAccount: " + sqle.getMessage(), sqle);
        } finally {
            close(cstmt);
        }
        return false;
    }

    public AccountLoginDTO setupAccountStep1(String loginId, @Sensitive String password, String emailAddress,
            @Sensitive int securityQuestionID, @Sensitive String securityQuestionAnswer, String alternateEmail, String ipAddress,
            String sessionID,UserEnvDTO userEnvDto) throws EtccException {
        try {
            BigDecimal[] acctID = new BigDecimal[1];
            String[] dbSessionID = new String[1];
            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            final LoginType loginType = LoginType.AC;
            //Push Notification Phase II sourceUserName
            int result = new OLCSC_LOGIN_MGMT(this.conn).LOGIN_CREATION(acctID, loginType.toString(), sessionID, ipAddress,null, null, null,
                    null, emailAddress, alternateEmail, loginId, password, new BigDecimal(securityQuestionID),
                    securityQuestionAnswer, userEnvDto.getSourceUserName(),dbSessionID, O_ERROR_MSG_ARR).intValue();
            if (logger.isDebugEnabled())
                logger.debug("oracleaccountdao.setupAccountStep1.result=" + result);
            if (result == 0) {
                if (logger.isDebugEnabled())
                    logger.debug("ERROR ARRAY is:" + Arrays.toString(O_ERROR_MSG_ARR[0].getArray()));
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("setupAccountStep1.result=" + result);
                logger.error("setupAccountStep1.emailAddress=" + emailAddress + ";securityQuestionID="
                        + securityQuestionID + ";securityQuestionAnswer=" + securityQuestionAnswer + ";alternateEmail="
                        + alternateEmail + ";ipAddress=" + ipAddress + ";sessionID=" + sessionID);
                logger.error("setupAccountStep1.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
            } else if (result == 1) {
                accountLoginDTO.setAcctId(acctID[0].longValue());
                accountLoginDTO.setDbSessionId(dbSessionID[0]);
                accountLoginDTO.setLoginId(loginId);
                accountLoginDTO.setLoginType(loginType);
            }

            return accountLoginDTO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EtccException("Exception in OracleAccountDAO.setupAccountStep1" + e.getMessage(), e);
        }
    }

    public AccountLoginDTO updateLoginId(String sessionID, String ipAddress, String loginId, String oldLoginId,
            @Sensitive String password, String acctID) throws EtccException, EtccSecurityException {
        try {
            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            int result = new OLCSC_LOGIN_MGMT(this.conn).CHANGE_LOGIN_ID(sessionID, ipAddress, loginId, oldLoginId,
                    password, acctID, O_ERROR_MSG_ARR).intValue();
            if (logger.isDebugEnabled())
                logger.debug("oraclenewaccountdao.updateLoginId.result=" + result);

            if (result == 0) {
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("updateLoginId.result=" + result);
                logger.error("updateLoginId.sessionID=" + sessionID + ";ipAddress=" + ipAddress + ";acctID=" + acctID);
                logger.error("updateLoginId.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
            } else if (result == 1) {
                accountLoginDTO.setLoginId(loginId.toUpperCase());
            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in updateLoginId");
            }

            return accountLoginDTO;
        } catch (SQLException sqle) {
            throw new EtccException("Exception in oracleaccountdao.updateLoginId" + sqle.getMessage(), sqle);
        }
    }

//	public AccountLoginDTO updatePassword(String sessionID, String ipAddress, String loginId, String oldPassword,
//            String password, String acctID) throws EtccException, EtccSecurityException {
//        try {
//            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
//            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
//            int result = new OLCSC_LOGIN_MGMT(this.conn).CHANGE_PASSWORD(sessionID, ipAddress, loginId, oldPassword,
//                    password, acctID, O_ERROR_MSG_ARR).intValue();
//            if (logger.isDebugEnabled())
//                logger.debug("oraclenewaccountdao.updatePassword.result=" + result);
//
//            if (result == 0) {
//                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
//                logger.error("updatePassword.result=" + result);
//                logger.error("updatePassword.sessionID=" + sessionID + ";ipAddress=" + ipAddress + ";acctID=" + acctID);
//                logger.error("updatePassword.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
//            } else if (result == -1) {
//                throw new EtccSecurityException("Security exception in updatePassword");
//            }
//
//            return accountLoginDTO;
//        } catch (SQLException sqle) {
//            throw new EtccException("Exception in oraclenewaccountdao.updatePassword" + sqle.getMessage());
//        }
//    }

    public PersonalInfoUpdateResultDTO setPersonalInfo(AccountLoginDTO acctLoginDto, String acctType,
            String companyName, String firstName, String lastName, String primaryPhone, String alternatePhone,
            String taxId, String driverLicState, String driverLicNumber, String address1, String address2,
            String address3, String address4, String city, String state, String zip, String country, boolean byMail,
            boolean byEmail, boolean byPhone, Long retailTransId, String altPhoneExt, String plus4)
            throws EtccException, EtccSecurityException {

        try {

            PersonalInfoUpdateResultDTO resultDto = null;
            long docId = acctLoginDto.getAcctId();
            String docType = AccountLoginDTO.LoginType.AC.toString();
            String loginId = acctLoginDto.getLoginId();
            String dbSessionId = acctLoginDto.getDbSessionId();
            String lastLoginIp = acctLoginDto.getLastLoginIp();
            //Express Account Changes
            if (!StringUtil.isEmpty(acctType)){
              acctType = acctType.equalsIgnoreCase("personal") ? "P" : "C";
            }else{
              acctType = "P";
            }

//            final boolean zMail = byMail != null && byMail.booleanValue();
            String strByMail = StringUtil.booleanToString(byMail);
            /*String ntfPref = (byEmail!=null && byEmail)?"E":"M";*/

            String[] O_DUPE_DL_FLAG = new String[] { "Y" };
            BigDecimal[] O_RTL_TRXN_ID = new BigDecimal[1];
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            if (retailTransId != null)
                O_RTL_TRXN_ID[0] = new BigDecimal(retailTransId.longValue());

            String P_POSTAL_FLAG = strByMail;
            String P_EMAIL_FLAG = StringUtil.booleanToString(byEmail);
            String P_PHONE_FLAG = StringUtil.booleanToString(byPhone);
            final boolean debugEnabled = logger.isDebugEnabled();
            if (debugEnabled) {
                logger.debug("new OLCSC_ACCT_MGMT(conn).PERSONAL_INFO(new BigDecimal(docId=" + docId + ")"
                        + ", docType=" + docType + ", acctType=" + acctType + ", P_POSTAL_FLAG=" + P_POSTAL_FLAG
                        + ", P_EMAIL_FLAG=" + P_EMAIL_FLAG + ", P_PHONE_FLAG=" + P_PHONE_FLAG + ", dbSessionId="
                        + dbSessionId + ", lastLoginIp=" + lastLoginIp + ", loginId=" + loginId + ", firstName="
                        + firstName + ", lastName=" + lastName + ", companyName=" + companyName + ", taxId=" + taxId
                        + ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3
                        + ", address4=" + address4 + ", city=" + city + ", state=" + state + ", country=" + country
                        + ", zip=" + zip + ", plus4=" + plus4 + ", primaryPhone=" + primaryPhone + ", alternatePhone="
                        + alternatePhone + ", altPhoneExt=" + altPhoneExt + ", driverLicNumber=" + driverLicNumber
                        + ", driverLicState=" + driverLicState + ", strByMail=" + strByMail + ", null=" + ", null="
                        + ", O_DUPE_DL_FLAG=[Y]" + ", O_RTL_TRXN_ID=new BigDecimal[1]"
                        + ", O_ERROR_MSG_ARR=new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() }" + ").intValue();\n");
            }
            int result = new OLCSC_ACCT_MGMT(this.conn).PERSONAL_INFO(new BigDecimal(docId), docType, acctType,
                    P_POSTAL_FLAG, P_EMAIL_FLAG, P_PHONE_FLAG, dbSessionId, lastLoginIp, loginId, firstName, lastName,
                    companyName, taxId, address1, address2, address3, address4, city, state, country, zip, plus4,
                    primaryPhone, alternatePhone, altPhoneExt, driverLicNumber, driverLicState, strByMail, null, null,
                    O_DUPE_DL_FLAG, O_RTL_TRXN_ID, O_ERROR_MSG_ARR).intValue();
            	
			
			if (result == -1) {
                if (debugEnabled)
                    logger.debug("acctId:" + acctLoginDto.getAcctId() + "****" + "setPersonalInfo result is -1");
                throw new EtccSecurityException("security exception in setPersonalInfo()");
            }

            resultDto = new PersonalInfoUpdateResultDTO();

            if (result == 1) {
                resultDto.setRetailTransId(O_RTL_TRXN_ID[0].longValue());
            } else {
                resultDto.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("setPersonalInfo.result=" + result);
                logger.error(new StringBuilder("setPersonalInfo:acctType=" + acctType).append(
                        ",companyName=" + companyName).append(",firstName=" + firstName)
                        .append(",lastName=" + lastName).append(",primaryPhone=" + primaryPhone).append(
                                ",alternatePhone=" + alternatePhone).append(",taxId=" + taxId).append(
                                ",driverLicState=" + driverLicState).append(",driverLicNumber=" + driverLicNumber)
                        .append(",address1=" + address1).append(",address2=" + address2)
                        .append(",address3=" + address3).append(",address4=" + address4).append(",city=" + city)
                        .append(",state=" + state).append(",zip=" + zip).append(",country=" + country).append(
                                ",byMail=" + byMail).append(",byEmail=" + byEmail).append(
                                ",retailTransId=" + retailTransId).append(",altPhoneExt=" + altPhoneExt).append(
                                ",plus4=" + plus4).append(",acctLoginDto=" + acctLoginDto));
                logger.error("setPersonalInfo.error=" + ErrorMessageDTO.toStringBuilder(resultDto.getErrors()));
            }

            return resultDto;
        } catch (SQLException ex) {
            throw new EtccException("Exception in SetPersonalInfo  " + ex, ex);
        }
    }

    public ResultDTO updateLoginInfo(AccountLoginDTO acctLoginDto, String loginId,
            @Sensitive String password, String emailAddress,
            @Sensitive int securityQuestionID, @Sensitive String securityQuestionAnswer,
            String alternateEmail, BaseContactInfo contactInfo)
    throws EtccException, EtccSecurityException {
        try {
            BigDecimal acctID = new BigDecimal(acctLoginDto.getAcctId());
            String firstName = null;
            String lastName = null;
            if (contactInfo != null) {
                firstName = contactInfo.getFirstName();
                lastName = contactInfo.getLastName();
            }
            String docType = AccountLoginDTO.LoginType.AC.toString();
            String sessionId = acctLoginDto.getDbSessionId();
            String ipAddress = acctLoginDto.getLastLoginIp();
            String userId = loginId;
            String oldUserId = acctLoginDto.getLoginId();
            BigDecimal sqId = new BigDecimal(securityQuestionID);
            ResultDTO baseDTO = new ResultDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            int result = new OLCSC_LOGIN_MGMT(this.conn).LOGIN_UPDATE(acctID, docType, sessionId, ipAddress, firstName, null,
                    lastName, emailAddress, alternateEmail, userId, password, oldUserId, sqId, securityQuestionAnswer,
                    O_ERROR_MSG_ARR).intValue();
            if (logger.isDebugEnabled())
                logger.debug("oraclenewaccountdao.updateLogin.result=" + result);
            if (result == -1) {
                if (logger.isDebugEnabled())
                    logger.debug("acctId:" + acctLoginDto.getAcctId() + "****" + "updateLoginInfo result is -1");
                throw new EtccSecurityException("security exception in updateLoginInfo()");
            }

            if (result == 0) {
                baseDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("updateLoginInfo.result=" + result);
                logger.error("updateLoginInfo.acctLoginDto=" + acctLoginDto + ";emailAddress=" + emailAddress
                        + ";securityQuestionID=" + securityQuestionID + ";securityQuestionAnswer="
                        + securityQuestionAnswer + ";alternateEmail=" + alternateEmail);
                logger.error("updateLoginInfo.error=" + ErrorMessageDTO.toStringBuilder(baseDTO.getErrors()));
            }

            return baseDTO;
        } catch (SQLException ex) {
            throw new EtccException("Exception in updateLoginInfo  " + ex, ex);
        }
    }

//	public AccountLoginDTO updateSecQn(String sessionID, String ipAddress, String loginId, String password,
//            String acctID, int securityQnID, String securityAns) throws EtccException, EtccSecurityException {
//        try {
//            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
//            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
//
//            BigDecimal ret = new OLCSC_LOGIN_MGMT(this.conn).CHANGE_SECURITY_QUE(sessionID, ipAddress, loginId, password,
//                    acctID, new BigDecimal(securityQnID), securityAns, O_ERROR_MSG_ARR);
//            int result = ret.intValue();
//            if (logger.isDebugEnabled())
//                logger.debug("oraclenewaccountdao.updateSecQn.result=" + result);
//
//            if (result == 0) {
//                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
//                logger.error("updateSecQn.result=" + result);
//                logger.error("updateSecQn.sessionID=" + sessionID + ";ipAddress=" + ipAddress + ";acctID=" + acctID
//                        + ";securityQnID=" + securityQnID + ";securityAns=" + securityAns);
//                logger.error("updateSecQn.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
//            } else if (result == -1) {
//                throw new EtccSecurityException("Security exceptio in updateSecQn");
//            }
//
//            return accountLoginDTO;
//        } catch (SQLException sqle) {
//            logger.error("SqlException: ", sqle);
//            throw new EtccException("Exception in oraclenewaccountdao.updateSecQn:" + sqle.getMessage());
//        }
//    }

    public AccountLoginDTO updateContactInfo(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String emailAddress, String altEmailAddress, String primaryPhone, String alternatePhone,
            String alternatePhoneExt) throws EtccSecurityException, EtccException {
        try {
            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            if (logger.isDebugEnabled()) {
                logger.debug("updateContactInfo: new OLCSC_ACCT_MGMT(conn).SET_CONTACT_INFO(acctID="
                        + new BigDecimal(acctID) + ", acctType=" + acctType + ", sessionID=" + sessionID
                        + ", ipAddress=" + ipAddress + ", loginId=" + loginId + ", emailAddress=" + emailAddress
                        + ", altEmailAddress=" + altEmailAddress + ", primaryPhone=" + primaryPhone
                        + ", alternatePhone=" + alternatePhone + ", alternatePhoneExt=" + alternatePhoneExt
                        + ", O_ERROR_MSG_ARR);");
            }
            BigDecimal ret = new OLCSC_ACCT_MGMT(this.conn).SET_CONTACT_INFO(new BigDecimal(acctID), acctType, sessionID,
                    ipAddress, loginId, emailAddress, altEmailAddress, primaryPhone, alternatePhone, alternatePhoneExt,
                    O_ERROR_MSG_ARR);

            int result = ret.intValue();
            
            
            if (logger.isDebugEnabled()) {
                logger.debug("updateContactInfo.result=" + result);
            }
            if (result == 0) {
            	
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
            	logger.error("updateContactInfo.result=" + result);
            	logger.error("updateContactInfo.acctD=" + acctID + ";acctType=" + acctType + ";ipAddress=" + ipAddress
                        + ";emailAddress=" + emailAddress + ";altEmailAddress=" + altEmailAddress + ";primaryPhone="
                        + primaryPhone + ";alternatePhone=" + alternatePhone + ";alternatePhoneExt="
                        + alternatePhoneExt);
            	logger.error("updateContactInfo.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in updateContactInfo");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("updateContactInfo.accountLoginDTO=" + accountLoginDTO);
            }
            return accountLoginDTO;
        } catch (SQLException sqle) {
            throw new EtccException("Exception in oraclenewaccountdao.updateContactInfo:" + sqle.getMessage());
        }
    }

  /*  public BillingInfoResultDTO setBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO)
            throws EtccException, EtccSecurityException {

        BillingInfoResultDTO resultDTO;
        try {

        	PaymentType paymentType = billingInfoDTO.getBillingType();
        	AccountPaymentMethodDTO paymentMethodDto = null;
    	 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString())){
    	 		paymentMethodDto = billingInfoDTO.getCards()[0];
    	 		//added year and month validation
		 		AccountCreditCardDTO ccDTO = (AccountCreditCardDTO) paymentMethodDto;
		 		if(ccDTO.getCardExpires()!=null && ccDTO.getCardExpires().length()>0){
		 			if(StringUtil.validateCardExpiryDate(ccDTO.getCardExpires().toString())){
		 				resultDTO =new BillingInfoResultDTO();
		 				resultDTO.setErrors(OracleUtil.convertToMessages(OracleUtil.getErrorMessage()));
		 				return resultDTO;
		 			}
		 		 }
    	 	}
            else
            	paymentMethodDto = billingInfoDTO.getEft();


            BigDecimal docId = acctLoginDto.getAcctId() > 0 ? new BigDecimal(acctLoginDto.getAcctId()): new BigDecimal(acctLoginDto.getInvoiceId());
            String docType = acctLoginDto.getAcctId() > 0 ? AccountLoginDTO.LoginType.AC.toString() : AccountLoginDTO.LoginType.IN.toString();
            String dbSessionId = acctLoginDto.getDbSessionId();
            String ipAddress = acctLoginDto.getLastLoginIp();
            String userId = acctLoginDto.getLoginId();
            Timestamp agreementDate = new Timestamp(Calendar.getInstance().getTime().getTime());

            OLC_PAYMENT_INFO_REC PAYMENT_INFO_REC = convertBillingDtoToArray(paymentMethodDto);

//            BigDecimal[] RTL_TRXN_ID = new BigDecimal[1];
//            RTL_TRXN_ID[0] = new BigDecimal(billingInfoDto.getTransactionId());

            BigDecimal[] MIN_BAL_AMT = new BigDecimal[1];
            BigDecimal[] TAG_SALES_AMT = new BigDecimal[1];
            BigDecimal[] PMT_AMT = new BigDecimal[1];
            BigDecimal[] REBILL_AMT = new BigDecimal[1];
            BigDecimal[] PREPAID_AMT = new BigDecimal[1];
            
            

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            if (logger.isDebugEnabled()) {
                logger.debug("new OLCSC_ACCT_MGMT(conn).SET_PAYMENT_INFO(acctId=" + docId + ", docType=" + docType
                        + ", dbSessionId=" + dbSessionId + ", ipAddress=" + ipAddress + ", userId=" + userId
                        + ",PAYMENT_INFO_ARR=" + paymentMethodDto + "N, RTL_TRXN_ID="
                        + ", MIN_BAL_AMT, REBILL_AMT, PMT_AMT,PREPAID_AMT, true, O_ERROR_MSG_ARR).intValue();");
            }
           String defaultBillingMethod = "Y";

           if (paymentMethodDto instanceof AccountCreditCardDTO)
           {
        	   defaultBillingMethod = ((AccountCreditCardDTO)paymentMethodDto).isPrimary() ? "Y" : "N";
           }



           int result = new OLCSC_ACCT_MGMT(this.conn).SET_PAYMENT_INFO( docId,docType, dbSessionId,ipAddress,userId,PAYMENT_INFO_REC,
        		   defaultBillingMethod,"N",MIN_BAL_AMT,REBILL_AMT,PMT_AMT,PREPAID_AMT,true,O_ERROR_MSG_ARR).intValue();

            if (result == -1) {
                throw new EtccSecurityException("security exception in setBillingInfo()");
            }

            resultDTO = new BillingInfoResultDTO();

            if (result == 0) {
                logger.error("setBillingInfo.result=" + result);
                logger.error("setBillingInfo.acctLoginDto=" + acctLoginDto + ";billingInfoDto=" + paymentMethodDto);
                resultDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                if (resultDTO.hasErrors()) {
                    logger.error("setBillingInfo.error=" + ErrorMessageDTO.toStringBuilder(resultDTO.getErrors()));
                }
            } else {
                if (PMT_AMT[0] != null) {
                    resultDTO.setTotalAmount(PMT_AMT[0].doubleValue());
                }

                if (PREPAID_AMT[0] != null) {
                    resultDTO.setDepositAmount(PREPAID_AMT[0].doubleValue());
                }

                if (TAG_SALES_AMT[0] != null) {
                    resultDTO.setTagAmount(TAG_SALES_AMT[0].doubleValue());
                }
                
                if (REBILL_AMT[0] != null) {
                    resultDTO.setRebillAmount(REBILL_AMT[0].doubleValue());
                }
                
                if (MIN_BAL_AMT[0] != null) {
                    resultDTO.setLowBalanceAmount(MIN_BAL_AMT[0].doubleValue());
                }
                
                // get the account billing method id based on token . 
                if ( PAYMENT_INFO_REC.getTOKEN() != null ) 
                {
                	Long accountBillingMethodId = new App().getAccountBillingMethodIdByToken( PAYMENT_INFO_REC.getTOKEN().longValue());
                	if (accountBillingMethodId != null  )
                	{
                		resultDTO.setAccontBillingMethodId(accountBillingMethodId);
                	}
                }
            }

        } catch (SQLException ex) {
            throw new EtccException("Exception in setBillingInfo " + ex, ex);
        }
       return resultDTO;
    }*/
    
    public BillingInfoResultDTO setBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO)
            throws EtccException, EtccSecurityException {
    	 
    	 BillingInfoResultDTO resultDTO = null;
    	 
    	 BigDecimal docId = acctLoginDto.getAcctId() > 0 ? new BigDecimal(acctLoginDto.getAcctId()): new BigDecimal(acctLoginDto.getInvoiceId());
    	 String docType = acctLoginDto.getAcctId() > 0 ? AccountLoginDTO.LoginType.AC.toString() : AccountLoginDTO.LoginType.IN.toString();
         String dbSessionId = acctLoginDto.getDbSessionId();
         String ipAddress = acctLoginDto.getLastLoginIp();
         String loginId = acctLoginDto.getLoginId();
         Timestamp agreementDate = new Timestamp(Calendar.getInstance().getTime().getTime());
         
         PaymentType paymentType = billingInfoDTO.getBillingType();
         AccountPaymentMethodDTO billingInfo = null;
      // credit card updat
 	 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString())){
 	 		try {
	 			AccountCreditCardDTO[] cards = billingInfoDTO.getCards();
				if ( billingInfoDTO != null && cards != null && cards.length > 0 )
	 			{
	 				for (  AccountCreditCardDTO billingInfoCard : cards  )
	 				{
						//  save the account billing method. 
	 					resultDTO = saveBillingMethodInfo(docId,docType, dbSessionId, ipAddress,loginId, billingInfoCard,acctLoginDto,billingInfoDTO); 
	 					if (acctLoginDto.hasErrors())
	 					{
	 						return resultDTO;
	 					}
	 				}
	 			}
			}catch(SQLException sqle){
				 throw new EtccException("Exception in oraclenewaccountdao.setBillingInfo:" + sqle.getMessage(), sqle);
			}
 	 	}else
 	        { // eft  -- update 
 	        	billingInfo = billingInfoDTO.getEft();
 	        try {
 				
 				//  save the account billing method.
 	        	resultDTO =  saveBillingMethodInfo(docId, docType, dbSessionId, ipAddress,loginId, billingInfo,acctLoginDto,billingInfoDTO);
 	        	} catch (SQLException sqle) {
 	            throw new EtccException("Exception in oraclenewaccountdao.setBillingInfo:" + sqle.getMessage(), sqle);
 	           }
 	        }	
    	 
    	 return resultDTO;
    }
    
	private BillingInfoResultDTO saveBillingMethodInfo(BigDecimal docId,String docType, String dbSessionId, String ipAddress,	
			String loginId, AccountPaymentMethodDTO billingInfo, AccountLoginDTO acctLoginDto ,BillingInfoDTO billingInfoDTO)throws SQLException, EtccSecurityException,EtccException
	{
		 BillingInfoResultDTO resultDTO = null;
	
	        try {

	        	PaymentType paymentType = billingInfoDTO.getBillingType();
	        	//AccountPaymentMethodDTO paymentMethodDto = null;
	    	 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString())){
	    	 		AccountCreditCardDTO[] cards = billingInfoDTO.getCards();
	    	 		if ( billingInfoDTO != null && cards != null && cards.length > 0 )
		 			{
		 				for (  AccountCreditCardDTO billingInfoCard : cards  )
		 				{
							//  save the account billing method. 
					 		if(billingInfoCard.getCardExpires()!=null && billingInfoCard.getCardExpires().length()>0){
					 			if(StringUtil.validateCardExpiryDate(billingInfoCard.getCardExpires().toString())){
					 				resultDTO =new BillingInfoResultDTO();
					 				resultDTO.setErrors(OracleUtil.convertToMessages(OracleUtil.getErrorMessage()));
					 				return resultDTO;
					 			}
					 		 }	
		 				}
		 			}
	    	 					 		
	    	 	}
	          
	            Timestamp agreementDate = new Timestamp(Calendar.getInstance().getTime().getTime());

	            OLC_PAYMENT_INFO_REC PAYMENT_INFO_REC = convertBillingDtoToArray(billingInfo);

//	            BigDecimal[] RTL_TRXN_ID = new BigDecimal[1];
//	            RTL_TRXN_ID[0] = new BigDecimal(billingInfoDto.getTransactionId());

	            BigDecimal[] MIN_BAL_AMT = new BigDecimal[1];
	            BigDecimal[] TAG_SALES_AMT = new BigDecimal[1];
	            BigDecimal[] PMT_AMT = new BigDecimal[1];
	            BigDecimal[] REBILL_AMT = new BigDecimal[1];
	            BigDecimal[] PREPAID_AMT = new BigDecimal[1];
	            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
	            if (logger.isDebugEnabled()) {
	                logger.debug("new OLCSC_ACCT_MGMT(conn).SET_PAYMENT_INFO(acctId=" + docId + ", docType=" + docType
	                        + ", dbSessionId=" + dbSessionId + ", ipAddress=" + ipAddress + ", userId=" + loginId
	                        + ",PAYMENT_INFO_ARR=" + billingInfo + "N, RTL_TRXN_ID="
	                        + ", MIN_BAL_AMT, REBILL_AMT, PMT_AMT,PREPAID_AMT, true, O_ERROR_MSG_ARR).intValue();");
	            }
	           String defaultBillingMethod = "Y";

	           if (billingInfo instanceof AccountCreditCardDTO)
	           {
	        	   defaultBillingMethod = ((AccountCreditCardDTO)billingInfo).isPrimary() ? "Y" : "N";
	           }
	           int result = new OLCSC_ACCT_MGMT(this.conn).SET_PAYMENT_INFO( docId,docType, dbSessionId,ipAddress,loginId,PAYMENT_INFO_REC,
	        		   defaultBillingMethod,"N",MIN_BAL_AMT,REBILL_AMT,PMT_AMT,PREPAID_AMT,true,O_ERROR_MSG_ARR).intValue();

	            if (result == -1) {
	                throw new EtccSecurityException("security exception in setBillingInfo()");
	            }

	            resultDTO = new BillingInfoResultDTO();

	            if (result == 0) {
	                logger.error("setBillingInfo.result=" + result);
	                logger.error("setBillingInfo.acctLoginDto=" + acctLoginDto + ";billingInfoDto=" + billingInfo);
	                resultDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
	                if (resultDTO.hasErrors()) {
	                    logger.error("setBillingInfo.error=" + ErrorMessageDTO.toStringBuilder(resultDTO.getErrors()));
	                }
	            } else {
	                if (PMT_AMT[0] != null) {
	                    resultDTO.setTotalAmount(PMT_AMT[0].doubleValue());
	                }

	                if (PREPAID_AMT[0] != null) {
	                    resultDTO.setDepositAmount(PREPAID_AMT[0].doubleValue());
	                }

	                if (TAG_SALES_AMT[0] != null) {
	                    resultDTO.setTagAmount(TAG_SALES_AMT[0].doubleValue());
	                }
	                
	                if (REBILL_AMT[0] != null) {
	                    resultDTO.setRebillAmount(REBILL_AMT[0].doubleValue());
	                }
	                
	                if (MIN_BAL_AMT[0] != null) {
	                    resultDTO.setLowBalanceAmount(MIN_BAL_AMT[0].doubleValue());
	                }
	                
	                // get the account billing method id based on token . 
	                if ( PAYMENT_INFO_REC.getTOKEN() != null ) 
	                {
	                	Long accountBillingMethodId = new App().getAccountBillingMethodIdByToken( PAYMENT_INFO_REC.getTOKEN().longValue());
	                	if (accountBillingMethodId != null  )
	                	{
	                		resultDTO.setAccontBillingMethodId(accountBillingMethodId);
	                	}
	                }
	            }

	        } catch (SQLException ex) {
	            throw new EtccException("Exception in setBillingInfo " + ex, ex);
	        }
	     return resultDTO;
		
	}

	public ResultDTO makePayment(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO, Double rebillAmount, Double lowBalanceAmount, List<TagDTO> tagList, double paymentAmt)
            throws EtccException, EtccSecurityException {
        ResultDTO resultDTO = null;
 

        try {
      	PaymentType paymentType = billingInfoDTO != null ? billingInfoDTO.getBillingType() : null;

		 	AccountPaymentMethodDTO paymentMethodDTO = null;
		 	if (paymentType != null && paymentAmt > 0)
		 	{
			 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString())  )
			 	{
			 		paymentMethodDTO = billingInfoDTO.getCards()[0];
			 		//added year and month validation
			 		AccountCreditCardDTO ccDTO = (AccountCreditCardDTO) paymentMethodDTO;
			 		if(ccDTO.getCardType()!=null && !ccDTO.getCardType().toString().equals(StringUtil.AMERICANEXPRESS)){
			 		if(ccDTO.getCardExpires()!=null && ccDTO.getCardExpires().length()>0){
			 			if(StringUtil.validateCardExpiryDate(ccDTO.getCardExpires().toString())){
			 				resultDTO =new ResultDTO();
			 				resultDTO.setErrors(OracleUtil.convertToMessages(OracleUtil.getErrorMessage()));
			 				return resultDTO;
			 			}
			 		 }
			 	  }
			 		// set token if required.
			 		CCTokenDAOHelper.getInstance().getCCToken(acctLoginDto, paymentMethodDTO);
			 		//set address and persons if required
			 		if (paymentMethodDTO.getAccountBillingMethodId()==null){
			 			// need to implement
			 			Long person_id =PersonAndAddressDAOHelper.getInstance().createPersons(this.conn,paymentMethodDTO.getName(),acctLoginDto.getAcctId());
			 			if (person_id!=null && person_id>0){
				 			Long address_id=PersonAndAddressDAOHelper.getInstance().createAddress(this.conn,person_id,paymentMethodDTO);
				 			paymentMethodDTO.setPersonId(person_id);
				 			paymentMethodDTO.setAddressId(address_id);
			 			}
			 		}
			 	}
		        else // EFT 
		        {
		        	paymentMethodDTO = billingInfoDTO.getEft();
		        	//QC_9289 blocking payment if bank account no **** and acc billing method is null
		        	AccountEFTDTO eftDto = (AccountEFTDTO) paymentMethodDTO;
		        	if (eftDto.getAccountBillingMethodId()==null && eftDto.getAccountNumber() != null &&  eftDto.getAccountNumber().contains("*")){
			 			resultDTO =new ResultDTO();
			 				resultDTO.setErrors(OracleUtil.convertToMessages(OracleUtil.getBankAccountErrorMessage()));
			 				return resultDTO;
		 	         }
		        }
		 	}

		 	String postingXML = createAccountPaymentXML(acctLoginDto, paymentMethodDTO,
					rebillAmount, lowBalanceAmount, tagList, paymentAmt);


            String docId = acctLoginDto.getAcctId()>0 ? acctLoginDto.getAcctId() +"": acctLoginDto.getInvoiceId();
            String docType =  acctLoginDto.getAcctId()>0 ? AccountLoginDTO.LoginType.AC.toString() : AccountLoginDTO.LoginType.IN.toString();
            String sessionId = acctLoginDto.getDbSessionId();
            String ipAddress = acctLoginDto.getLastLoginIp();
            String userId = acctLoginDto.getLoginId();
           
            if (logger.isDebugEnabled())
            	if (paymentMethodDTO != null )
            	{
            		logger.debug("billingInfoDto:" + paymentMethodDTO.toString());
            	}

            OLC_PAYMENT_INFO_REC PAYMENT_INFO_ARR = convertBillingDtoToArray(paymentMethodDTO);

            //BigDecimal[] RTL_TRXN_ID = new BigDecimal[1];
            //RTL_TRXN_ID[0] = new BigDecimal(paymentMethodDTO.getTransactionId());

            BigDecimal[] PMT_AMT = new BigDecimal[1];
           // DecimalFormat formatter = new DecimalFormat("#####.00");
            //String paymentAmtS = formatter.format(paymentAmt);
            //PMT_AMT[0] = new BigDecimal(paymentAmtS);

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            if (logger.isDebugEnabled())
                logger.debug("OLCSC_ACCT_MGMT.MAKE_PAYMENT(docId=" + docId + ", docType=" + docType + ", sessionId="
                        + sessionId + ", ipAddress=" + ipAddress + ", userId=" + userId + ", RTL_TRXN_ID="
                       + ", PMT_AMT=" + PMT_AMT[0] + ", PAYMENT_INFO_ARR, N, O_ERROR_MSG_ARR)");
            XMLType xmlPosting = XMLType.createXML(conn, postingXML);
            int result = new OLCSC_PAYMENT(this.conn).MAKE_PAYMENT(docId+"", docType, sessionId, ipAddress, userId,
                    xmlPosting, null, null,"Y", PAYMENT_INFO_ARR, "N", O_ERROR_MSG_ARR).intValue();
            //int result = -1;
            if (result == -1) {

                resultDTO = new ResultDTO();
                resultDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                    if (resultDTO.hasErrors()) {
                    	String errorMessage = ErrorMessageDTO.toStringBuilder(resultDTO.getErrors()).toString();
                        logger.error("makePayment.error=" + errorMessage);
                        throw new EtccSecurityException("security exception in accountDAO.makePayment()"+errorMessage);
                    }else {
                         throw new EtccSecurityException("security exception in accountDAO.makePayment()");
                    }  
            }

            resultDTO = new ResultDTO();
            if (result == 0) {
                logger.error("makePayment.result=" + result);
                logger.error("makePayment.acctLoginDto=" + acctLoginDto + ";billingInfoDto=" + paymentMethodDTO
                        + ";paymentAmt=" + PMT_AMT[0]);
                resultDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                if (resultDTO.hasErrors()) {
                    logger.error("makePayment.error=" + ErrorMessageDTO.toStringBuilder(resultDTO.getErrors()));
                }
//            } else {
//                if (billingInfoDto.getTransactionId() == -1) {
//                    if (logger.isDebugEnabled())
//                        logger.debug("makePayment: transaction Id = " + RTL_TRXN_ID[0].longValue());
//                    billingInfoDto.setTransactionId(RTL_TRXN_ID[0].longValue());
//                }
//            }if(result == 1){
//            	 //recalculateting the Autocharge and Save in to database for REBILL_AMOUNT and LOW_BALANCE_LEVEL
//                new App().recalculateAutochargeAndSave(acctLoginDto.getAcctId(), userId);
            }
        } catch (SQLException ex) {
            throw new EtccException("Exception in accountDAO.makePayment " + ex, ex);
        }
        return resultDTO;
    }

    private OLC_PAYMENT_INFO_REC convertBillingDtoToArray(AccountPaymentMethodDTO paymentMethodDTO) throws SQLException {
    	if (paymentMethodDTO == null )
    	{
    		return new OLC_PAYMENT_INFO_REC(null,null,null,null,null,null,null,null,null,null,null,
    				null,null,null,null,null,null,null,
    				null,null,null,null,null,null,null, null,null,null,null);
    	}
        if (paymentMethodDTO instanceof AccountCreditCardDTO)
            return convertCCDtoToArray(paymentMethodDTO);
//        else
        return convertEftDtoToArray(paymentMethodDTO);
    }

    private OLC_PAYMENT_INFO_REC convertCCDtoToArray(AccountPaymentMethodDTO paymentMethod) throws SQLException {
        OLC_PAYMENT_INFO_ARR paymentArr = null;

        final boolean debugEnabled = logger.isDebugEnabled();
//        OLC_PAYMENT_INFO_REC[] recArr = new OLC_PAYMENT_INFO_REC[billingInfoDTO.getCards().length];

        AccountCreditCardDTO ccDto = (AccountCreditCardDTO) paymentMethod;
                //logger.debug("convertCCDtoToArray(AccountCreditCardDTO[]).ccCol.length is " + billingInfoDTO.getCards().length);

            // Iterator iter = ccCol.iterator();
            // int i = 0;
            // while (iter.hasNext()) {
           //for (int i = 0; i < ccCol.length; i++) {
                // AccountCreditCardDTO ccDto = (AccountCreditCardDTO)iter.next();
        		BigDecimal accountBillingMethodId = ccDto.getAccountBillingMethodId() != null ? new BigDecimal(ccDto.getAccountBillingMethodId()) :null;

        		        		
                return new OLC_PAYMENT_INFO_REC(
                        PaymentType.CREDIT.getCodeString(),null,
                        ccDto.getCardNbr().substring(ccDto.getCardNbr().length()-4),WSDateUtil.monthYearToTimestamp(ccDto.getCardExpires()),
                        new BigDecimal(ccDto.getCardType().getType()),ccDto.getCardType().name(),
                        accountBillingMethodId,ccDto.isPrimary()? "Y":"N","Y", ccDto.getToken() == 0 ? null : new BigDecimal(ccDto.getToken()),
                        null,null,StringUtil.extractFirstName(ccDto.getNameOnCard()),
                        StringUtil.extractLastName(ccDto.getNameOnCard()),
                        ccDto.getAddress1(),ccDto.getAddress2(),ccDto.getCity(),ccDto.getState(),ccDto.getCountry(),ccDto.getZipCode(),
                        ccDto.getPlus4(),null,null,null,null,null,null,null,null);

//                if (debugEnabled) {
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("convertCCDtoToArray(AccountCreditCardDTO[]).billingRec No");
//                    sb.append(i);
//                    sb.append("=[");
//                    sb.append(ccDto.toStringBuilder());
//                    sb.append("]");
//                    logger.debug(ccDto);
//                }
            //}
            //paymentArr = new OLC_PAYMENT_INFO_ARR(recArr);



    }

    private OLC_PAYMENT_INFO_REC convertEftDtoToArray(AccountPaymentMethodDTO paymentMethod) throws SQLException {
        OLC_PAYMENT_INFO_ARR paymentArr = null;

        AccountEFTDTO eftDto = (AccountEFTDTO) paymentMethod;
        if (eftDto != null) {
            OLC_PAYMENT_INFO_REC[] recArr = new OLC_PAYMENT_INFO_REC[1];
            OLC_PAYMENT_INFO_REC billingRec = new OLC_PAYMENT_INFO_REC();
            billingRec.setPMT_TYPE(PaymentType.EFT.getCodeString());
            billingRec.setBANK_ACCT_TYPE(eftDto.getAccountType().getCode());
            billingRec.setROUTING_NBR(eftDto.getRoutingNumber());
            billingRec.setBANK_ACCT_NUMBER(eftDto.getAccountNumber());
            
            BigDecimal accountBillingMethodId = eftDto.getAccountBillingMethodId() != null ? new BigDecimal(eftDto.getAccountBillingMethodId()) :null;
            billingRec.setACCOUNT_BILLING_METHOD_ID(accountBillingMethodId);
            
            billingRec.setIS_DEFAULT_BILLING_METHOD(eftDto.isPrimary() ? "Y" :"N");
            return billingRec;
            //paymentArr = new OLC_PAYMENT_INFO_ARR(recArr);
        }
        return null;
    }

    public BillingInfoDTO getBillingInfo(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
    	String docId = acctLoginDto.getAcctId() > 0 ? acctLoginDto.getAcctId()+"": acctLoginDto.getInvoiceId();
    	String docType = acctLoginDto.getAcctId() > 0 ? AccountLoginDTO.LoginType.AC.toString() : AccountLoginDTO.LoginType.IN.toString();
        String dbSessionID = acctLoginDto.getDbSessionId();
        String ipAddress = acctLoginDto.getLastLoginIp();
        String loginID = acctLoginDto.getLoginId();
        try {
            BillingInfoDTO billingInfoDTO = new BillingInfoDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            OLC_PAYMENT_INFO_ARR O_PAYMENT_INFO[] = new OLC_PAYMENT_INFO_ARR[] { new OLC_PAYMENT_INFO_ARR() };     
            OLC_BILLING_METHOD_LIMTS_REC[] O_BILLING_METHOD_LIMITS = new OLC_BILLING_METHOD_LIMTS_REC[] { new OLC_BILLING_METHOD_LIMTS_REC() };

            BigDecimal ret = new OLCSC_ACCT_MGMT(this.conn).GET_PAYMENT_INFO(docId,/*new BigDecimal(acctLoginDto.getViolatorId()),*/ docType, dbSessionID, ipAddress,
                    loginID, O_PAYMENT_INFO, O_ERROR_MSG_ARR,O_BILLING_METHOD_LIMITS);
            //BigDecimal ret = null;
            int result = ret.intValue();
            if (logger.isDebugEnabled())
                logger.debug("getBillingInfo.result=" + result);
            if (result == 0) {
                billingInfoDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("getBillingInfo.result=" + result);
                logger.error("getBillingInfo.acctID=" + docId + ";docType=" + docType + ";ipaddress=" + ipAddress
                        + ";loginID" + loginID);
                logger.error("getBillingInfo.error=" + ErrorMessageDTO.toStringBuilder(billingInfoDTO.getErrors()));
            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in getBillingInfo");
            } else {
                populateBillingInfo(O_PAYMENT_INFO[0], billingInfoDTO,docId);
                billingInfoDTO.setBillingMethodLimits(getBillingMethodLimits(O_BILLING_METHOD_LIMITS[0]));
            }
            if (logger.isDebugEnabled())
                logger.debug("getBillingInfo.billingInfoDTO=[" + billingInfoDTO + ']');
            return billingInfoDTO;
        } catch (SQLException e) {
            throw new EtccException("Exception in oraclenewaccountdao.getBillingInfo:" + e.getMessage(), e);
        }
    }

    private void populateBillingInfo(OLC_PAYMENT_INFO_ARR P_OLC_PAYMENT_INFO_ARR, BillingInfoDTO billingInfoDTO,String account_id)
            throws SQLException {
        OLC_PAYMENT_INFO_REC[] P_OLC_PAYMENT_INFO_REC = P_OLC_PAYMENT_INFO_ARR.getArray();
        Collection<AccountCreditCardDTO> cards = new ArrayList<AccountCreditCardDTO>(P_OLC_PAYMENT_INFO_REC.length);
        //naphan-2369
        AccountEFTDTO eft = null;
        //Collection<AccountEFTDTO> efts = new ArrayList<AccountEFTDTO>(P_OLC_PAYMENT_INFO_REC.length);
        AccountCreditCardDTO[] crdArr = null;
        boolean isCreditCard = true;
        for (int i = 0; i < P_OLC_PAYMENT_INFO_REC.length; i++) {
            OLC_PAYMENT_INFO_REC rec = P_OLC_PAYMENT_INFO_REC[i];

                switch (rec.getPMT_TYPE().charAt(0)) {
                case PaymentType.CODE_CREDIT:
                	//msp_2464 It should be checked by default billing method value
                	if ("Y".equals(rec.getIS_DEFAULT_BILLING_METHOD())){
                		billingInfoDTO.setBillingType(PaymentType.CREDIT);
                	}
                    // Collection cards = new ArrayList();

                    AccountCreditCardDTO accountCard = new AccountCreditCardDTO();
                    //cards.add(accountCard);
                    if (!StringUtil.isEmpty(account_id)){
                      accountCard.setAcctId(Long.parseLong(account_id));
                    }
                    accountCard.setCardCode(CreditCardType.valueOfCode(rec.getCARD_NAME()).getDisplay());
                    accountCard.setCardNbr(rec.getCARD_NBR());
                    Calendar exp = WSDateUtil.timestampToCalendar(rec.getCARD_EXPIRES());
                    accountCard.setCardExpires(String.format("%1$tm/%1$tY", exp));
                    //changes for last name null issue
                    if (StringUtil.isEmpty(rec.getLAST_NAME())){
                    	accountCard.setNameOnCard(rec.getFIRST_NAME().toUpperCase());
                    }else{
                    	accountCard.setNameOnCard(StringUtil.nameOnCard(rec.getFIRST_NAME(), rec.getLAST_NAME()));
                    }
                    accountCard.setPrimary(rec.getIS_DEFAULT_BILLING_METHOD().equals("Y"));

                    accountCard.setAddress1(rec.getADDRESS_1());
                    accountCard.setAddress2(rec.getADDRESS_2());
    //                accountCard.setAddress3(P_OLC_PAYMENT_INFO_REC[i].getADDRESS_3());
    //                accountCard.setAddress4(P_OLC_PAYMENT_INFO_REC[i].getADDRESS_4());
                    accountCard.setCity(rec.getCITY());
                    accountCard.setState(rec.getSTATE());
                    accountCard.setZipCode(rec.getZIPCODE());
                    accountCard.setPlus4(rec.getPLUS4());
                    accountCard.setCountry(rec.getCOUNTRY());
                    accountCard.setBillingType(rec.getBILLING_TYPE()); //added min and max billing type
                    accountCard.setIsBlocked(rec.getIS_BLOCKED()); //added min and max billing type
                    
                    if (rec.getACCOUNT_BILLING_METHOD_ID() != null) {
                    	accountCard.setAccountBillingMethodId(rec.getACCOUNT_BILLING_METHOD_ID().longValue());
                    }
                    if (rec.getTOKEN() != null ) {
                    	accountCard.setToken(rec.getTOKEN().longValue());
                    	accountCard.setOmniToken(rec.getTOKEN().toString());
                    }
                    
                    if (rec.getPERSON_ID() !=null) {
                    	accountCard.setPersonId(rec.getPERSON_ID().longValue());
                    }
                    
                    if (rec.getPHONE_ID()!=null)
                    {
                    	accountCard.setPhoneId(rec.getPHONE_ID().longValue());
                    }
                    
                    if (rec.getADDRESS_ID() !=null) {
                    	accountCard.setAddressId(rec.getADDRESS_ID().longValue());
                    }
                    
                    if(rec.getBILLING_PRIORITY() != null){//added min and max billing type
                    	accountCard.setBillingPriority(rec.getBILLING_PRIORITY().longValue());
                    }
                    cards.add(accountCard);
                    break;
                case PaymentType.CODE_EFT:
                	//msp_2464 It should be checked by default billing method value
                	if ("Y".equals(rec.getIS_DEFAULT_BILLING_METHOD())){
                		isCreditCard = false;
                		billingInfoDTO.setBillingType(PaymentType.EFT);
                	}
                    AccountEFTDTO eftDTO = new AccountEFTDTO();
                    billingInfoDTO.setEft(eftDTO);
                    eftDTO.setAccountNumber(rec.getBANK_ACCT_NUMBER());
                    eftDTO.setAccountType(rec.getBANK_ACCT_TYPE());
                    eftDTO.setRoutingNumber(rec.getROUTING_NBR());
                    //msp_5273_olcscfix
                    eftDTO.setAddress1(rec.getADDRESS_1());
                    eftDTO.setAddress2(rec.getADDRESS_2());
                    eftDTO.setCity(rec.getCITY());
                    eftDTO.setState(rec.getSTATE());
                    eftDTO.setZipCode(rec.getZIPCODE());
                    eftDTO.setPlus4(rec.getPLUS4());
                    eftDTO.setCountry(rec.getCOUNTRY());
                    eftDTO.setBillingType(rec.getBILLING_TYPE()); //added min and max billing type
                    eftDTO.setIsBlocked(rec.getIS_BLOCKED()); //added min and max billing type
                    if (rec.getACCOUNT_BILLING_METHOD_ID() != null) {
                    	eftDTO.setAccountBillingMethodId(rec.getACCOUNT_BILLING_METHOD_ID().longValue());
                    }
                    //billingInfoDTO.setToken(rec.getTOKEN().longValue());
                    if (rec.getPERSON_ID() != null)
                    {
                    	eftDTO.setPersonId(rec.getPERSON_ID().longValue());
                    }
                    if (rec.getPHONE_ID()!=null)
                    {
                    	eftDTO.setPhoneId(rec.getPHONE_ID().longValue());
                    }
                    if (rec.getADDRESS_ID() != null)
                    {
                    	eftDTO.setAddressId(rec.getADDRESS_ID().longValue());
                    }
                    
                    if(rec.getBILLING_PRIORITY() != null){//added min and max billing type
                    	eftDTO.setBillingPriority(rec.getBILLING_PRIORITY().longValue());
                    }
                    eftDTO.setFirstName(rec.getFIRST_NAME());
                    eftDTO.setLastName(rec.getLAST_NAME());
                    eftDTO.setPrimary(rec.getIS_DEFAULT_BILLING_METHOD().equals("Y"));
                    
                    billingInfoDTO.setEft(eftDTO);
                    eft = eftDTO;

                    break;
                case PaymentType.CODE_INVOICE:
                    billingInfoDTO.setBillingType(PaymentType.INVOICE);
                    break;
                }

        }

        //naphan-2369-set credit card list in case it will be used to display all credit cards if we change default billing method from EFT to Credit card
        if (cards.size()> 0) {
        	 crdArr = new AccountCreditCardDTO[cards.size()];
        	billingInfoDTO.setCards(cards.toArray(crdArr));
        }
        //msp_5273_olcscfix
        //if (!isCreditCard){
        billingInfoDTO.setEft(eft);
        //}
    }

    public AccountLoginDTO updateMailingAddr(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String country, String zip, String plus4) throws EtccSecurityException, EtccException {
        try {
            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            BigDecimal ret = new OLCSC_ACCT_MGMT(this.conn).SET_MAILING_INFO(new BigDecimal(acctID), acctType, sessionID,
                    ipAddress, loginId, address1, address2, address3, address4, city, state, country, zip, plus4,
                    O_ERROR_MSG_ARR);

            int result = ret.intValue();

            if (result == 0) {
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("updateMailingAddr.result=" + result);
                logger.error("updateMailingAddr.acctID=" + acctID + ";acctType=" + acctType + ";sessionid=" + sessionID
                        + ";ipaddress=" + ipAddress + ";address1=" + address1 + ";address2=" + address2 + ";address3="
                        + address3 + ";address4=" + address4 + ";city=" + city + ";state=" + state + ";country="
                        + country + "zip=" + zip + ";plus4=" + plus4);
                logger.error("updateMailingAddr.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));

            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in updateMailingAddr");
            }

            return accountLoginDTO;
        } catch (SQLException sqle) {
            throw new EtccException("Exception in oraclenewaccountdao.updateMailingAddr:" + sqle.getMessage(), sqle);
        }
    }

    /*public AccountLoginDTO updateBillingInfo(AccountLoginDTO acctLogin, BillingInfoDTO billingInfoDTO) throws EtccSecurityException, EtccException {
        BigDecimal acctID = BigDecimal.valueOf(acctLogin.getAcctId());
        String acctType = acctLogin.getLoginTypeString();
        String sessionID = acctLogin.getDbSessionId();
        String ipAddress = acctLogin.getLastLoginIp();
        String loginId = acctLogin.getLoginId();

        PaymentType paymentType = billingInfoDTO.getBillingType();
    	AccountPaymentMethodDTO billingInfo = null;
	 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString()))
	 		billingInfo = billingInfoDTO.getCards()[0];
        else
        	billingInfo = billingInfoDTO.getEft();


        try {
            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            OLC_PAYMENT_INFO_REC P_OLC_PAYMENT_INFO_REC = convertBillingDtoToArray(billingInfo);
            BigDecimal[] o_init_prepaid_bal = new BigDecimal[]{ new BigDecimal(0)};
            BigDecimal ret = new OLCSC_ACCT_MGMT(this.conn).SET_BILLING_INFO(acctID, acctType, sessionID,
                    ipAddress, loginId, P_OLC_PAYMENT_INFO_REC, "Y", true, o_init_prepaid_bal,O_ERROR_MSG_ARR);

            int result = ret.intValue();

            if (result == 0) {
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("updateBillingInfo.result=" + result);
                logger.error("updateBillingInfo.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
                logger.error("updateBillingInfo.acctID=" + acctID + ";acctType=" + acctType + ";sessionid=" + sessionID
                        + ";ipaddress=" + ipAddress + ";loginID" + loginId + ";billingInfo=" + billingInfo);
            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in updateBillingInfo");
            }

            return accountLoginDTO;
        } catch (SQLException sqle) {
            throw new EtccException("Exception in oraclenewaccountdao.updateBillingInfo:" + sqle.getMessage(), sqle);
        }
    }
    */
    
    
    public AccountLoginDTO updateBillingInfo(AccountLoginDTO acctLogin, BillingInfoDTO billingInfoDTO) throws EtccSecurityException, EtccException {
        BigDecimal acctID = BigDecimal.valueOf(acctLogin.getAcctId());
        String acctType = acctLogin.getLoginTypeString();
        String sessionID = acctLogin.getDbSessionId();
        String ipAddress = acctLogin.getLastLoginIp();
        String loginId = acctLogin.getLoginId();

        PaymentType paymentType = billingInfoDTO.getBillingType();
    	AccountPaymentMethodDTO billingInfo = null;
    	
    	// credit card update 
	 	if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString()))
	 	{
	 		try {
	 			AccountCreditCardDTO[] cards = billingInfoDTO.getCards();
				if ( billingInfoDTO != null && cards != null && cards.length > 0 )
	 			{
	 				for (  AccountCreditCardDTO billingInfoCard : cards  )
	 				{
						//  save the account billing method. 
	 					acctLogin = saveBillingMethod(acctID, acctType, sessionID, ipAddress,loginId, billingInfoCard);
	 					if (acctLogin.hasErrors())
	 					{
	 						return acctLogin;
	 					}
	 				}
	 			}
			} catch (SQLException sqle) {
				 throw new EtccException("Exception in oraclenewaccountdao.updateBillingInfo:" + sqle.getMessage(), sqle);
			}
	 	}
        else
        { // eft  -- update 
        	billingInfo = billingInfoDTO.getEft();
        try {
			
			//  save the account billing method.
        	acctLogin =  saveBillingMethod(acctID, acctType, sessionID, ipAddress,
					loginId, billingInfo);
        	} catch (SQLException sqle) {
            throw new EtccException("Exception in oraclenewaccountdao.updateBillingInfo:" + sqle.getMessage(), sqle);
           }
        }
	 	return acctLogin;
    }

	private AccountLoginDTO saveBillingMethod(BigDecimal acctID,
			String acctType, String sessionID, String ipAddress,
			String loginId, AccountPaymentMethodDTO billingInfo)
			throws SQLException, EtccSecurityException {
		
		String  defaultBillingMethod = "Y";
		String  expiryDate = null;
		
		 if (billingInfo instanceof AccountCreditCardDTO)
         {
      	   defaultBillingMethod = ((AccountCreditCardDTO)billingInfo).isPrimary() ? "Y" : "N";
	      	//added year and month validation
	      	 expiryDate =((AccountCreditCardDTO)billingInfo).getCardExpires();
		 		if(expiryDate!=null && expiryDate.length()>0){
		 			if(StringUtil.validateCardExpiryDate(expiryDate)){
		 				AccountLoginDTO resultDTO =new AccountLoginDTO();
		 				resultDTO.setErrors(OracleUtil.convertToMessages(OracleUtil.getErrorMessage()));
		 				return resultDTO;
		 			}
		 		 }
         }
		 
		if (billingInfo instanceof AccountEFTDTO) {
			defaultBillingMethod = ((AccountEFTDTO) billingInfo).isPrimary() ? "Y" : "N";
		}
		
		AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
		OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
		OLC_PAYMENT_INFO_REC P_OLC_PAYMENT_INFO_REC = convertBillingDtoToArray(billingInfo);
		BigDecimal[] o_init_prepaid_bal = new BigDecimal[]{ new BigDecimal(0)};
		BigDecimal ret = new OLCSC_ACCT_MGMT(this.conn).SET_BILLING_INFO(acctID, acctType, sessionID,
		        ipAddress, loginId, P_OLC_PAYMENT_INFO_REC, defaultBillingMethod, true, o_init_prepaid_bal,O_ERROR_MSG_ARR);

		int result = ret.intValue();

		if (result == 0) {
		    accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
		    logger.error("updateBillingInfo.result=" + result);
		    logger.error("updateBillingInfo.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
		    logger.error("updateBillingInfo.acctID=" + acctID + ";acctType=" + acctType + ";sessionid=" + sessionID
		            + ";ipaddress=" + ipAddress + ";loginID" + loginId + ";billingInfo=" + billingInfo);
		} else if (result == -1) {
		    throw new EtccSecurityException("Security exception in updateBillingInfo");
		}

            return accountLoginDTO;
    }

    public AccountLoginDTO updateBillingAddress(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, String address1, String address2, String address3, String address4, String city,
            String state, String zip, String plus4, String country) throws EtccSecurityException, EtccException {
        try {
            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

           BigDecimal ret = new OLCSC_ACCT_MGMT(this.conn).SET_BILLING_ADDRESS(new BigDecimal(acctID), acctType, sessionID,
                    ipAddress, loginId, address1, address2, address3, address4, city, state, country, zip, plus4,
                    O_ERROR_MSG_ARR);

            int result = ret.intValue();

            if (result == 0) {
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("updateBillingAddress.result=" + result);
                logger.error("updateBillingAddress.acctID=" + acctID + ";acctType=" + acctType + ";sessionid="
                        + sessionID + ";ipaddress=" + ipAddress + ";address1=" + address1 + ";address2=" + address2
                        + ";address3=" + address3 + ";address4=" + address4 + ";city=" + city + ";state=" + state
                        + ";country=" + country + "zip=" + zip + ";plus4=" + plus4);
                logger.error("updateBillingAddress.error="
                        + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in updateBillingAddress");
            }

            return accountLoginDTO;
        } catch (SQLException sqle) {
            throw new EtccException("Exception in oraclenewaccountdao.updateBillingAddress:" + sqle.getMessage(), sqle);
        }
    }

    public AccountLoginDTO updateRebillAmt(String acctID, String acctType, String sessionID, String ipAddress,
            String loginId, BigDecimal rebillAmt, BigDecimal lowBalanceLevel) throws EtccSecurityException, EtccException {
        try {
            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            if (logger.isDebugEnabled())
                logger.debug("OLCSC_ACCT_MGMT(conn).SET_REBILL_INFO(acctID=" + new BigDecimal(acctID) + ", acctType="
                        + acctType + ", sessionID=" + sessionID + ", ipAddress=" + ipAddress + ", loginId=" + loginId
                        + ", rebillAmt=" + rebillAmt + ", lowBalanceLevel="
                        + lowBalanceLevel + ", O_ERROR_MSG_ARR)");
            BigDecimal ret = new OLCSC_ACCT_MGMT(this.conn).SET_REBILL_INFO(new BigDecimal(acctID), acctType, sessionID,
                    ipAddress, loginId, rebillAmt, lowBalanceLevel, O_ERROR_MSG_ARR);


            int result = ret.intValue();
            if (logger.isInfoEnabled())
                logger.info("updateRebillAmt.result=" + result);
            if (result == 0) {
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("updateRebillAmt.result=" + result);
                logger.error("updateRebillAmt.acctID=" + acctID + ";acctType=" + acctType + ";sessionid=" + sessionID
                                + ";ipaddress=" + ipAddress + ";rebillamt=" + rebillAmt + ";lowbalancelevel="
                                + lowBalanceLevel);
                logger.error("updateRebillAmt.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in updateRebillAmt");
            }
            return accountLoginDTO;
        } catch (SQLException sqle) {
            throw new EtccException("Exception in oraclenewaccountdao.updateRebillAmt:" + sqle.getMessage(), sqle);
        }
    }

    public AccountDTO getAccountStatus(AccountLoginDTO acctLoginDto, AccountDTO accountDTO) throws EtccException,
            EtccSecurityException {

        try {

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            OLC_LIC_PLATE_ARR O_OLC_LIC_PLATE_ARR[] = new OLC_LIC_PLATE_ARR[] { new OLC_LIC_PLATE_ARR() };
            String O_OLC_SUSPENSION_FLAG[] = new String[] { "" };
            String O_OLC_VIOLATION_FLAG[] = new String[] { "" };

            BigDecimal ret = new OLCSC_REP(this.conn).CHECK_SUSPENSION_AND_VIOLATION(
                    new BigDecimal(acctLoginDto.getAcctId()), AccountLoginDTO.LoginType.AC.toString(), acctLoginDto.getDbSessionId(),
                    acctLoginDto.getLastLoginIp(), acctLoginDto.getLoginId(), O_ERROR_MSG_ARR, O_OLC_LIC_PLATE_ARR,
                    O_OLC_SUSPENSION_FLAG, O_OLC_VIOLATION_FLAG);

            int result = ret.intValue();

            if (result == 0) {
                accountDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("getAccountStatus.result=" + result);
                logger.error("getAccountStatus.acctLoginDto=" + acctLoginDto + ";accountDTO=" + accountDTO);
                logger.error("getAccountStatus.error=" + ErrorMessageDTO.toStringBuilder(accountDTO.getErrors()));
            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in getAccountStatus");
            } else {
                accountDTO.setSuspensionFlag(StringUtil.stringToBoolean(O_OLC_SUSPENSION_FLAG[0]));
                accountDTO.setViolationFlag(StringUtil.stringToBoolean(O_OLC_VIOLATION_FLAG[0]));

                if (O_OLC_LIC_PLATE_ARR[0] != null) {
                    OLC_LIC_PLATE_REC[] recs = O_OLC_LIC_PLATE_ARR[0].getArray();
                    ArrayList<String> licPlates = new ArrayList<String>(recs.length);
                    for (int i = 0; i < recs.length; i++) {
                        licPlates.add(recs[i].getLIC_STATE() + "-" + recs[i].getLIC_PLATE());
                    }
                    accountDTO.setViolationLicPlates(licPlates);
                } else {
                    accountDTO.setViolationLicPlates(new ArrayList<String>());
                }
            }

            return accountDTO;
        } catch (SQLException sqle) {
            throw new EtccException("Error running getAccountStatus: " + sqle, sqle);
        }
    }

    public ResultDTO updatePasswordEmailSecurityQA(AccountLoginDTO acctLoginDto,
            int updateFlags, @Sensitive String oldPw, @Sensitive String newPw, String emailAddress,
            @Sensitive int sQuestionID, @Sensitive String sAnswer) throws EtccSecurityException, EtccException {
        ResultDTO resultDto = null;

        try {
            BigDecimal acctId = new BigDecimal(acctLoginDto.getAcctId());
            String docType = acctLoginDto.getLoginType().toString();
            String sessionId = acctLoginDto.getDbSessionId();
            String ipAddress = acctLoginDto.getLastLoginIp();
            String userId = acctLoginDto.getLoginId();

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            int result = new OLCSC_LOGIN_MGMT(this.conn).CHANGE_EMAIL_SECQ_PW(acctId, docType, sessionId, ipAddress, userId,
                    newPw, oldPw, emailAddress, sAnswer == null ? null : new BigDecimal(sQuestionID), sAnswer,
                    O_ERROR_MSG_ARR).intValue();

            if (result == -1) {
                throw new EtccSecurityException("Security Exception in " + "updatePwEmailSq");
            }

            resultDto = new ResultDTO();

            if (result == 0) {
                resultDto.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("updatePwEmailSq.result=" + result);
                logger.error("updatePwEmailSq.acctLoginDto=" + acctLoginDto + ";emailaddress=" + emailAddress
                        + ";squestion=" + sQuestionID + ";sqans=" + sAnswer);
                logger.error("updatePwEmailSq.error=" + ErrorMessageDTO.toStringBuilder(resultDto.getErrors()));
            }

        } catch (SQLException e) {
            throw new EtccException("Exception in oraclenewaccountdao.updatePwEmailSq:" + e.getMessage(), e);
        }
        return resultDto;
    }

	public ResultDTO sendWelcomeNotification(AccountLoginDTO acctLoginDto,
			double activationFee, List<TagDTO> tagDTOs) throws EtccException,EtccSecurityException {

		 ResultDTO resultDto = null;

	        try {
	            BigDecimal acctId = new BigDecimal(acctLoginDto.getAcctId());

	            String plateDateExpire = null;
	            String plateNbr =null;
	            int tagCount = 0; 

	            
	            if(tagDTOs !=null && !tagDTOs.isEmpty()) {
	            	tagCount = tagDTOs.size();
	            	
	            	TagDTO tagDTO = tagDTOs.get(0);
	            	
	            	if (tagDTO != null && tagDTO.getPbpEndDate() != null)
	            	{
	            		plateDateExpire = new SimpleDateFormat("mm/DD/yyyy MM:HH:SS").format(tagDTO.getPbpEndDate().getTime());
	            		plateNbr = tagDTO.getLicState()+"-"+tagDTO.getLicPlate();
	            	}
	            }


	            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

	            int result = new OLCSC_PAYMENT(this.conn).SEND_WELCOME_NOTIFICATION(acctId, new BigDecimal(activationFee), new BigDecimal(tagCount),plateDateExpire,plateNbr,O_ERROR_MSG_ARR).intValue();


	            if (result == -1) {
	                throw new EtccSecurityException("Security Exception in " + "sendWelcomeNotification");
	            }

	            resultDto = new ResultDTO();

	            if (result == 0) {
	                resultDto.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
	                logger.error("sendWelcomeNotification.result=" + result);
	                logger.error("sendWelcomeNotification.error=" + ErrorMessageDTO.toStringBuilder(resultDto.getErrors()));
	            }

	        } catch (SQLException e) {
	            throw new EtccException("Exception in olcsc_payment.sendWelcomeNotification:" + e.getMessage(), e);
	        }
	        return resultDto;
	}
	/**
	 *
	 */
	/*public ResultDTO makeInvoicePayment(AccountLoginDTO acctLoginDto,
			AccountPaymentMethodDTO paymentMethodDTO, ViolatorDTO violatorDTO,
			String email) throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}*/
/*	public BaseDTO makeAccountPayment(AccountLoginDTO loginDTO,
			AccountPaymentMethodDTO primaryCard, Double rebillAmount,
			Double depositAmount, List<TagDTO> savedVehicles, Double paymentAmt)
			throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}*/
/*	public ResultDTO makeAccountPaymentCreditCard(AccountLoginDTO loginDTO,
			AccountCreditCardDTO primaryCard, Double rebillAmount,
			Double depAmount, List<TagDTO> tagList, double paymentAmt)
			throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	public ResultDTO makeAccountPaymentEFT(AccountLoginDTO loginDTO,
			AccountEFTDTO primaryCard, Double rebillAmount, Double depAmount,
			List<TagDTO> tagList, double paymentAmt) throws EtccException,
			EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}*/


	/*public ResultDTO makeAccountPayment(AccountLoginDTO loginDTO, BillingInfoDTO billingInfoDTO, Double rebillAmount, Double depAmount, java.util.List<TagDTO> tagList, double paymentAmt) throws EtccException ,EtccSecurityException
	{return null;}*/
/*	public BillingInfoResultDTO setBillingInfoCreditCard(
			AccountLoginDTO acctLoginDto, AccountCreditCardDTO paymentMethodDto)
			throws EtccException, EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	public BillingInfoResultDTO setBillingInfoEFT(AccountLoginDTO acctLoginDto,
			AccountEFTDTO paymentMethodDto) throws EtccException,
			EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	private String createAccountPaymentXML(AccountLoginDTO loginDTO,
			AccountPaymentMethodDTO primaryCard, Double rebillAmount,
			Double lowBalanceAmount, List<TagDTO> savedVehicles, Double paymentAmt)
			throws EtccException {
		List<CartXML> carts = new ArrayList<CartXML>();
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);

    	Long[] ids =appDao.getAccountPostingAndShiftId(loginDTO, 1L);

    	String xmlPosting = "";
		try {
			
			
			
			CartItemXmlDetails cartItemXmlDetails = new CartItemXmlDetails();
			cartItemXmlDetails.setItemType(CartItemTypeEnum.APPLY_UNDIRECTED
					.getValue());
			cartItemXmlDetails.setAmount(paymentAmt);
			if (paymentAmt > 0)
			{
				carts.add(cartItemXmlDetails);
			}
			if("P".equalsIgnoreCase(getAccountStatus(loginDTO.getAcctId())))
			{
				logger.info("adding add account cart--"+loginDTO.getAcctId());

				CartItemXmlDetails cartItemXmlDetailsAC = new CartItemXmlDetails();
				cartItemXmlDetailsAC.setItemType(CartItemTypeEnum.ADD_ACCOUNT
						.getValue());
				cartItemXmlDetailsAC.setAmount(0.0);
				carts.add(cartItemXmlDetailsAC);
			}

			StringBuffer accountVehicleIds = new StringBuffer();
			Map<String, String> items = new HashMap<String, String>();
			if(rebillAmount!=null)
				items.put("rebillAmount", rebillAmount + "");
			// Build accountIDs list from tag request form
			if(savedVehicles!=null){ 
				for (TagDTO dto : savedVehicles) {
					
					accountVehicleIds.append(",").append(dto.getAcctVehicleId());
					
				}
			}
			if(lowBalanceAmount!=null)
				items.put("lowBalanceAmount",lowBalanceAmount + "");
			if(accountVehicleIds.length()!=0)
				items.put("accountVehicleIds", accountVehicleIds.toString());

			carts.add(CartUtil.getInstance().createEndPostingApiCartItem(loginDTO,
					items, 0L));
			

			xmlPosting = CartUtil.getInstance().generatecheckoutxml(loginDTO.getAcctId(), carts, primaryCard, new BigDecimal(paymentAmt), ids);
			logger.info("xmlPosting--->"+loginDTO.getAcctId()+"--->"+xmlPosting);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlPosting;
	}

	private String getAccountStatus(long accountId ) throws EtccException
	{
		logger.info("inside getAccountStatus  status--"+accountId);
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT T2.CODE FROM ACCOUNTS T1, ACCOUNT_STATUSES T2 "
				+ "WHERE T1.ACCOUNT_STATUS_ID = T2.ACCOUNT_STATUS_ID "
				+ "AND T1.ACCOUNT_ID = ?";
		String status = null;
		try {
			preparedStatement = this.conn.prepareStatement(selectSQL);
			preparedStatement.setLong(1, accountId);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {

				status = rs.getString("CODE");

			}

		} catch (SQLException e) {
			throw new EtccException("Exception in getAccountStatus:" + e.getMessage(), e);

		} finally {
			 close(preparedStatement);

		}
		logger.info("getAccountStatus  status--"+accountId+"--->"+status);
		return status;
	}
	
	//defect 9064 txphung beign
	public ResultDTO validateRoutingNumber(String routingNumber)
			throws EtccException, EtccSecurityException {
		CallableStatement cstmt = null;
		ResultDTO baseDTO = null;

		try {
			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Validate_Routing_Number(?, ?, ?)}"); //defect 9064 txphung: add alert

			Map<String, Class<?>> typeMap = setTypeMap();
			typeMap.put("OL_OWNER.OLC_ACCT_ALERT_REC", OLC_ACCT_ALERT_REC.class);

			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, routingNumber);
			int alertsParamIdx = idx++;	//defect 9064 txphung: add alert
            cstmt.registerOutParameter(alertsParamIdx, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR"); //defect 9064 txphung: add alert
			cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			
			baseDTO = new ResultDTO();

			short success = cstmt.getShort(1);
			if (success == 0) {
				logger.error("validateRoutingNumber.success=" + success);
				logger.error("validateRoutingNumber.routingNumber=" + routingNumber);
				Array alerts = cstmt.getArray(alertsParamIdx);	//defect 9064 txphung: add alert
				final Array errors = cstmt.getArray(idx);
				if (errors != null) {
					baseDTO.setErrors(OracleUtil.convertToMessages(errors));
					logger.error("getAlerts.error="
							+ ErrorMessageDTO.toStringBuilder(baseDTO.getErrors()));
				}
				//defect 9064 txphung: add alert - begin
				if(alerts != null){
					baseDTO.setAlerts(OracleUtil.convertToAlerts(alerts));					
				}
				//defect 9064 txphung: add alert - end
			}
		} catch (SQLException sqle) {
			throw new EtccException("Error running validateRoutingNumber: "
					+ sqle, sqle);
		} finally {
			close(cstmt);
		}
		return baseDTO;
	}
	//defect 9064 txphung end 
	//Express Enh Account changes: added new method to create account
	public AccountLoginDTO setupAccountWithPlanStep1(String loginId, @Sensitive String password, String emailAddress,
            @Sensitive int securityQuestionID, @Sensitive String securityQuestionAnswer, String alternateEmail, String ipAddress,
            String sessionID,UserEnvDTO userEnvDto,String plan) throws EtccException {
        try {
            BigDecimal[] acctID = new BigDecimal[1];
            String[] dbSessionID = new String[1];
            AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            final LoginType loginType = LoginType.AC;
            
            int result = new OLCSC_LOGIN_MGMT(this.conn).LOGIN_CREATION(acctID, loginType.toString(), sessionID, ipAddress,plan, null, null,
                    null, emailAddress, alternateEmail, loginId, password, new BigDecimal(securityQuestionID),
                    securityQuestionAnswer, userEnvDto.getSourceUserName(),dbSessionID, O_ERROR_MSG_ARR).intValue();
            if (logger.isDebugEnabled())
                logger.debug("oracleaccountdao.setupAccountWithPlanStep1.result=" + result);
            if (result == 0) {
                if (logger.isDebugEnabled())
                    logger.debug("ERROR ARRAY is:" + Arrays.toString(O_ERROR_MSG_ARR[0].getArray()));
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                logger.error("setupAccountWithPlanStep1.result=" + result);
                logger.error("setupAccountWithPlanStep1.emailAddress=" + emailAddress + ";securityQuestionID="
                        + securityQuestionID + ";securityQuestionAnswer=" + securityQuestionAnswer + ";alternateEmail="
                        + alternateEmail + ";ipAddress=" + ipAddress + ";sessionID=" + sessionID);
                logger.error("setupAccountWithPlanStep1.error=" + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
            } else if (result == 1) {
                accountLoginDTO.setAcctId(acctID[0].longValue());
                accountLoginDTO.setDbSessionId(dbSessionID[0]);
                accountLoginDTO.setLoginId(loginId);
                accountLoginDTO.setLoginType(loginType);
            }

            return accountLoginDTO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EtccException("Exception in OracleAccountDAO.setupAccountWithPlanStep1" + e.getMessage(), e);
        }
    }

	public AccountDeviceStatusDTO getAccountDeviceStatus(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId, String fingerprintId) throws EtccException {
		CallableStatement cstmt = null;
		AccountDeviceStatusDTO accountDeviceStatusDTO = null;
		try {
			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Account_Device_Status(?,?,?,?,?,?,?,?,?)}");
			setTypeMap();
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, dbSessionId);
			cstmt.setString(3, loginId);
			cstmt.setString(4, ipAddress);
			cstmt.setString(5, acctId);
			cstmt.setString(6, acctType);
			cstmt.setString(7, fingerprintId);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.registerOutParameter(9, Types.DATE);
			cstmt.registerOutParameter(10, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			int result = cstmt.getInt(1);
			accountDeviceStatusDTO = new AccountDeviceStatusDTO();
			logger.debug("getAccountDeviceStatus acctId = " + acctId + " result=" + result);
			if (result == 1) {
				accountDeviceStatusDTO.setMfaRequired(cstmt.getString(8));
				accountDeviceStatusDTO.setDeviceDate(cstmt.getDate(9));
				logger.debug("getAccountDeviceStatus " + accountDeviceStatusDTO.getMfaRequired() + ""
						+ accountDeviceStatusDTO.getDeviceDate());
			} else if (result == 0) {
				final Array errors = cstmt.getArray(10);
				if (errors != null) {
					accountDeviceStatusDTO.setErrors(OracleUtil.convertToMessages(errors));
					logger.error("getAccountDeviceStatus.acctId=" + acctId);
					logger.error("getAccountDeviceStatus.error="
							+ ErrorMessageDTO.toStringBuilder(accountDeviceStatusDTO.getErrors()));
				}
			}
		} catch (Exception exception) {
			throw new EtccException(
					"Exception in OLCSC_ACCT_MGMT.Get_Account_Device_Status:	" + exception.getMessage(), exception);
		} finally {
			close(cstmt);
		}
		return accountDeviceStatusDTO;
	}

	public ResultDTO saveAccountDevice(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fingerprintId) throws EtccException {
		CallableStatement cstmt = null;
		ResultDTO resultDTO = null;
		try {
			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.SAVE_ACCOUNT_DEVICE(?,?,?,?,?,?,?)}");
			setTypeMap();
			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.INTEGER);
			cstmt.setString(idx++, dbSessionId);
			cstmt.setString(idx++, loginId);
			cstmt.setString(idx++, ipAddress);
			cstmt.setString(idx++, acctId);
			cstmt.setString(idx++, acctType);
			cstmt.setString(idx++, fingerprintId);
			cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			final int result = cstmt.getInt(1);
			logger.debug("saveAccountDevice result " + result);
			resultDTO = new ResultDTO();
			if (result == 1) {
				final Array errors = cstmt.getArray(idx);
				if (errors != null) {
					resultDTO.setErrors(OracleUtil.convertToMessages(errors));
					logger.error("saveAccountDevice.acctId=" + acctId);
					logger.error("saveAccountDevice.error=" + ErrorMessageDTO.toStringBuilder(resultDTO.getErrors()));
				}
			}
		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_ACCT_MGMT.SAVE_ACCOUNT_DEVICE:	" + exception.getMessage(),
					exception);
		} finally {
			close(cstmt);
		}
		return resultDTO;
	}

	public ResultDTO validateSecurityAnswer(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String securityAnswer) throws EtccException {
		CallableStatement cstmt = null;
		ResultDTO resultDTO = null;
		try {
			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.validate_security_answer(?,?,?,?,?,?,?)}");
			setTypeMap();
			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.INTEGER);
			cstmt.setString(idx++, dbSessionId);
			cstmt.setString(idx++, loginId);
			cstmt.setString(idx++, ipAddress);
			cstmt.setString(idx++, acctId);
			cstmt.setString(idx++, acctType);
			cstmt.setString(idx++, securityAnswer);
			cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			final int result = cstmt.getInt(1);
			resultDTO = new ResultDTO();
			logger.debug("validateSecurityAnswer.result=" + result);
			if (result == 0) {
				final Array errors = cstmt.getArray(idx);
				if (errors != null) {
					resultDTO.setErrors(OracleUtil.convertToMessages(errors));
					logger.error("validateSecurityAnswer.acctId=" + acctId);
					logger.error(
							"validateSecurityAnswer.error=" + ErrorMessageDTO.toStringBuilder(resultDTO.getErrors()));
				}
			}
		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_ACCT_MGMT.validate_security_answer:	" + exception.getMessage(),
					exception);
		} finally {
			close(cstmt);
		}
		return resultDTO;
	}

	public AuthenticatedSessionidDTO getAuthenticatedSessionid(String acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		CallableStatement cstmt = null;
		AuthenticatedSessionidDTO authenticatedSessionidDTO = null;
		try {
			cstmt = this.conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Authenticated_SessionID(?, ?, ?, ?, ?, ?, ?)}");
			setTypeMap();

			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, acctId);
			cstmt.setString(3, acctType);
			cstmt.setString(4, dbSessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, loginId);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.registerOutParameter(8, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			authenticatedSessionidDTO = new AuthenticatedSessionidDTO();
			final int result = cstmt.getInt(1);
			logger.debug("getAuthenticatedSessionid result= " + result);
			if (result == 1) {
				authenticatedSessionidDTO.setSessionId(cstmt.getString(7));
			} else if (result == -1) {
				Array errors = (Array) cstmt.getObject(8);
				authenticatedSessionidDTO.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("getAuthenticatedSessionid.acctId=" + acctId);
				logger.error("getAuthenticatedSessionid.error="
						+ ErrorMessageDTO.toStringBuilder(authenticatedSessionidDTO.getErrors()));
			}
		} catch (Exception exception) {
			throw new EtccException(
					"Exception in OLCSC_ACCT_MGMT.Get_Authenticated_SessionID:	" + exception.getMessage(), exception);
		} finally {
			close(cstmt);
		}
		return authenticatedSessionidDTO;
	}

	public AccountDTO getAcctDetailsTwoFa(String acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId) throws EtccException {
		CallableStatement cstmt = null;
		AccountDTO accountDTO = null;
		try {
			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Acct_Details_2FA(?, ?, ?, ?, ?, ?, ?)}");
			Map<String, Class<?>> typeMap = setTypeMap();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_INFO_REC", OLC_ACCOUNT_INFO_REC.class);

			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, acctId);
			cstmt.setString(3, acctType);
			cstmt.setString(4, dbSessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, loginId);
			cstmt.registerOutParameter(7, Types.STRUCT, "OL_OWNER.OLC_ACCOUNT_INFO_REC");
			cstmt.registerOutParameter(8, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			final int result = cstmt.getInt(1);
			accountDTO = new AccountDTO();
			logger.debug("getAcctDetailsTwoFa result= " + result);
			if (result == 1) {

				OLC_ACCOUNT_INFO_REC olcAccountInfoRecord = (OLC_ACCOUNT_INFO_REC) cstmt.getObject(7);
				populateAccountDTO(olcAccountInfoRecord, accountDTO);
				accountDTO.setAcctId(Long.valueOf(acctId));
				logger.debug("getAcctDetailsTwoFa accountDTO  " + accountDTO);
			} else if (result == 0) {
				Array errors = (Array) cstmt.getObject(8);
				accountDTO.setErrors(OracleUtil.convertToMessages(errors));

				logger.error("getAcctDetailsTwoFa.acctId=" + acctId);
				logger.error("getAcctDetailsTwoFa.error=" + ErrorMessageDTO.toStringBuilder(accountDTO.getErrors()));

			}
		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_ACCT_MGMT.Get_Acct_Details_2FA:	" + exception.getMessage(),
					exception);
		} finally {
			close(cstmt);
		}
		return accountDTO;
	}

	public DocumentUploadResponse uploadDocument(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String fileLocation, String fileName, String action, String documentType,
			String description, Long accountDocumentId) throws EtccException {
		CallableStatement cstmt = null;
		DocumentUploadResponse documentUploadResponse = null;
		try {

			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.set_acct_documents(?,?,?,?,?,?,?,?,?,?,?,?)}");
			Map<String, Class<?>> typeMap = setTypeMap();
			
			cstmt.registerOutParameter(1, Types.INTEGER);
			
			cstmt.setLong(2, acctId);
			cstmt.setString(3, acctType);
			cstmt.setString(4, dbSessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, loginId);
			cstmt.setString(7, fileLocation);
			cstmt.setString(8, fileName);
			cstmt.setString(9, action);
			cstmt.setString(10, documentType);
			cstmt.setString(11, description);
			
			if(accountDocumentId !=null) {
				cstmt.setLong(12, accountDocumentId);
			}else {
				cstmt.setObject(12, null);
			}
			
			cstmt.registerOutParameter(13, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			final int result = cstmt.getInt(1);
			
			documentUploadResponse = new DocumentUploadResponse();
			logger.debug("set_acct_documents result= " + result);
			if (result == 1) {
				documentUploadResponse.setMessage(fileName + " uploaded successfully");
				logger.debug("set_acct_documents documentUploadResponse  " + documentUploadResponse);
			} else if (result == 0) {
				Array errors = (Array) cstmt.getObject(13);
				documentUploadResponse.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("set_acct_documents.acctId=" + acctId);
				logger.error("set_acct_documents.error="
						+ ErrorMessageDTO.toStringBuilder(documentUploadResponse.getErrors()));
			}

		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_ACCT_MGMT.set_acct_documents:	" + exception.getMessage(),
					exception);

		} finally {
			close(cstmt);
		}
		return documentUploadResponse;
	}

	public GetAccountDocumentResponse getAccountDocument(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		CallableStatement cstmt = null;
		ResultSet rs = null;
		GetAccountDocumentResponse getAccountDocumentResponse = null;
		try {
			
			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.get_acct_documents(?,?,?,?,?,?,?)}");
			
			Map<String, Class<?>> typeMap = setTypeMap();
			
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setLong(2, acctId);
			cstmt.setString(3, acctType);
			cstmt.setString(4, dbSessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, loginId);
			cstmt.registerOutParameter(7, Types.REF_CURSOR);
			cstmt.registerOutParameter(8, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			
			cstmt.execute();
			
			final int result = cstmt.getInt(1);

			logger.debug("get_acct_documents result= "+result);
			
			getAccountDocumentResponse = new GetAccountDocumentResponse();
			if(result == 1) {
				rs = (ResultSet)cstmt.getObject(7);
				if(rs !=null) {
					final List<AccountDocument> accountDocuments = new ArrayList<AccountDocument>();
					
					while(rs.next()) {
						final AccountDocument accountDocument = new AccountDocument();
						accountDocument.setAccountDocumentId(rs.getLong(1));
						accountDocument.setAccountId(rs.getLong(2));
						accountDocument.setFileLocation(rs.getString(3));
						accountDocument.setFileName(rs.getString(4));
						accountDocument.setDocumentType(rs.getString(5));
						accountDocument.setDescription(rs.getString(6));
						accountDocument.setGeneratedDate(rs.getDate(7));
						
						accountDocuments.add(accountDocument);
						
					}
					
					getAccountDocumentResponse.getAccountDocumentList().addAll(accountDocuments);
				}
			}
			else if(result==0) {
				final Array errors = (Array) cstmt.getObject(11);
				getAccountDocumentResponse.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("get_acct_documents.acctId=" + acctId);
				logger.error("get_acct_documents.error="
						+ ErrorMessageDTO.toStringBuilder(getAccountDocumentResponse.getErrors()));
			}
			
		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_ACCT_MGMT.get_acct_documents:	" + exception.getMessage(),
					exception);

		} finally {
			close(cstmt);
			close(rs);
		}
		return getAccountDocumentResponse;
	}
	
	public GetAccountNotificationsDocumentResponse getAcctNotificationDocuments(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId) throws EtccException {
		CallableStatement cstmt = null;
		ResultSet rs = null;
		GetAccountNotificationsDocumentResponse getAccountNotificationsDocumentResponse = null;
		try {

			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.get_acct_notifications(?,?,?,?,?,?,?)}");

			Map<String, Class<?>> typeMap = setTypeMap();

			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setLong(2, acctId);
			cstmt.setString(3, acctType);
			cstmt.setString(4, dbSessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, loginId);
			cstmt.registerOutParameter(7, Types.REF_CURSOR);
			cstmt.registerOutParameter(8, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			final int result = cstmt.getInt(1);
			getAccountNotificationsDocumentResponse = new GetAccountNotificationsDocumentResponse();
			
			if (result == 1) {
				
				rs = (ResultSet) cstmt.getObject(7);
				
				if (rs != null) {

					final List<AccountNotificationDocument> accountNotificationDocuments = new ArrayList<AccountNotificationDocument>();

					while (rs.next()) {
						final AccountNotificationDocument accountNotificationDocument = new AccountNotificationDocument();
						accountNotificationDocument.setRowNum(rs.getBigDecimal(1));
						accountNotificationDocument.setAccountId(rs.getLong(2));
						accountNotificationDocument.setDescription(rs.getString(3));
						accountNotificationDocument.setDocumentType(rs.getString(4));
						accountNotificationDocument.setGeneratedDate(rs.getDate(5));
						accountNotificationDocument.setNotificationStatus(rs.getString(6));
						accountNotificationDocument.setReportFilePath(rs.getString(7));

						accountNotificationDocuments.add(accountNotificationDocument);

					}
					
					getAccountNotificationsDocumentResponse.getAccountNotifictaionDocuments()
							.addAll(accountNotificationDocuments);
				}

			} else if (result == 0) {
				final Array errors = (Array) cstmt.getObject(11);
				getAccountNotificationsDocumentResponse.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("get_acct_notifications.acctId=" + acctId);
				logger.error("get_acct_notifications.error="
						+ ErrorMessageDTO.toStringBuilder(getAccountNotificationsDocumentResponse.getErrors()));

			}

		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_ACCT_MGMT.get_acct_notifications:	" + exception.getMessage(),
					exception);

		} finally {
			close(rs);
			close(cstmt);
		}
		return getAccountNotificationsDocumentResponse;
	}
	
	public RemoveBillingInfoResponse removeBillingInfo(AccountLoginDTO acctLoginDto, BillingInfoDTO billingInfoDTO) throws EtccException {

		final PaymentType paymentType = billingInfoDTO.getBillingType();
		RemoveBillingInfoResponse removeBillingInfoResponse = null;

		// credit card
		if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString())) {
			AccountCreditCardDTO[] cards = billingInfoDTO.getCards();
			if (billingInfoDTO != null && cards != null && cards.length > 0) {
				for (AccountCreditCardDTO billingInfoCard : cards) {

					removeBillingInfoResponse = removeBillingInfoFromDb(acctLoginDto, billingInfoCard);

					if (removeBillingInfoResponse.hasErrors()) {
						return removeBillingInfoResponse;
					}
				}
			}
		} else { // eft
			removeBillingInfoResponse = removeBillingInfoFromDb(acctLoginDto, billingInfoDTO.getEft());

		}

		return removeBillingInfoResponse;

	}

	private RemoveBillingInfoResponse removeBillingInfoFromDb(AccountLoginDTO acctLoginDto,
			AccountPaymentMethodDTO billingInfo) throws EtccException {

		RemoveBillingInfoResponse removeBillingInfoResponse = null;
		try {

			BigDecimal docId = acctLoginDto.getAcctId() > 0 ? new BigDecimal(acctLoginDto.getAcctId())
					: new BigDecimal(acctLoginDto.getInvoiceId());
			
			String docType = acctLoginDto.getAcctId() > 0 ? AccountLoginDTO.LoginType.AC.toString()
					: AccountLoginDTO.LoginType.IN.toString();
			
			String dbSessionId = acctLoginDto.getDbSessionId();
			
			String ipAddress = acctLoginDto.getLastLoginIp();
			
			String loginId = acctLoginDto.getLoginId();

			OLC_PAYMENT_INFO_REC PAYMENT_INFO_REC = convertBillingDtoToArray(billingInfo);

			OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

			int result = new OLCSC_ACCT_MGMT(this.conn).REMOVE_BILLING_INFO(docId, docType, dbSessionId, ipAddress,
					loginId, PAYMENT_INFO_REC,true, O_ERROR_MSG_ARR).intValue();

			removeBillingInfoResponse = new RemoveBillingInfoResponse();

			if (result == 0) {
				logger.error("removeBillingInfoFromDb.result=" + result);
				logger.error("removeBillingInfoFromDb.acctLoginDto=" + acctLoginDto + ";AccountPaymentMethodDTO="
						+ billingInfo);
				removeBillingInfoResponse.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
				if (removeBillingInfoResponse.hasErrors()) {
					logger.error("removeBillingInfoFromDb.error="
							+ ErrorMessageDTO.toStringBuilder(removeBillingInfoResponse.getErrors()));
				}
			}
		} catch (Exception exception) {
			throw new EtccException("Exception in removeBillingInfoFromDb " + exception, exception);

		}

		return removeBillingInfoResponse;
	}
	
	public GetAccountPhoneInfoResponse getAcctountPhoneInfo(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {

		CallableStatement cstmt = null;
		ResultSet rs = null;
		GetAccountPhoneInfoResponse getAccountPhoneInfoResponse = null;
		try {

			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.get_acct_phone_info(?,?,?,?,?,?,?)}");

			Map<String, Class<?>> typeMap = setTypeMap();

			cstmt.registerOutParameter(1, Types.INTEGER);

			cstmt.setLong(2, acctId);
			cstmt.setString(3, acctType);
			cstmt.setString(4, dbSessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, loginId);
			cstmt.registerOutParameter(7, Types.REF_CURSOR);
			cstmt.registerOutParameter(8, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			final int result = cstmt.getInt(1);

			getAccountPhoneInfoResponse = new GetAccountPhoneInfoResponse();

			if (result == 1) {

				rs = (ResultSet) cstmt.getObject(7);

				if (rs != null) {

					final List<AccountPhoneInfo> accountPhoneInfos = new ArrayList<AccountPhoneInfo>();

					while (rs.next()) {
						final AccountPhoneInfo accountPhoneInfo = new AccountPhoneInfo();

						accountPhoneInfo.setPersonPhoneId(rs.getBigDecimal(1));
						accountPhoneInfo.setPhoneNumber(rs.getString(2));
						accountPhoneInfo.setPhoneExtension(rs.getString(3));
						accountPhoneInfo.setPrimaryCommunication(rs.getString(4));
						accountPhoneInfo.setCommunicationType(rs.getString(5));

						accountPhoneInfos.add(accountPhoneInfo);

					}

					getAccountPhoneInfoResponse.getAccountPhoneInfoList().addAll(accountPhoneInfos);
				}

			} else if (result == 0) {
				final Array errors = (Array) cstmt.getObject(8);
				getAccountPhoneInfoResponse.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("get_acct_phone_info.acctId=" + acctId);
				logger.error("get_acct_phone_info.error="
						+ ErrorMessageDTO.toStringBuilder(getAccountPhoneInfoResponse.getErrors()));

			}

		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_ACCT_MGMT.get_acct_phone_info:	" + exception.getMessage(),
					exception);
		} finally {
			close(rs);
			close(cstmt);

		}

		return getAccountPhoneInfoResponse;
	}
	
	
	public ResultDTO setContactInfoByContactId(BigDecimal acctId, String docType, String sessionId, String ipAddress,
			String userId, String emailAddress, String altEmail, String homePhoNbr, String workPhoNbr,
			String workPhoExt, String homePhoExt, String mobilePhone, String mobilePhoExt, String smsAlertsOptIn,
			BigDecimal homePhoneId, BigDecimal workPhoneId, BigDecimal mobilePhoneId) throws EtccException {
		CallableStatement cstmt = null;
		ResultDTO resultDTO = null;
		try {
			cstmt = this.conn.prepareCall(
					"{? = call OLCSC_ACCT_MGMT.SET_CONTACT_INFO_BY_CONTACT_ID(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			Map<String, Class<?>> typeMap = setTypeMap();

			cstmt.registerOutParameter(1, Types.INTEGER);

			cstmt.setBigDecimal(2, acctId);
			cstmt.setString(3, docType);
			cstmt.setString(4, sessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, userId);
			cstmt.setString(7, emailAddress);
			cstmt.setString(8, altEmail);
			cstmt.setString(9, homePhoNbr);
			cstmt.setString(10, workPhoNbr);
			cstmt.setString(11, workPhoExt);
			cstmt.registerOutParameter(12, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setString(13, homePhoExt);
			cstmt.setString(14, mobilePhone);
			cstmt.setString(15, mobilePhoExt);
			cstmt.setString(16, smsAlertsOptIn);
			
			if(homePhoneId !=null) {
				cstmt.setBigDecimal(17, homePhoneId);
			}else {
				cstmt.setObject(17, null);
			}
			
			if(workPhoneId !=null) {
				
				cstmt.setBigDecimal(18, workPhoneId);
			}else {
				cstmt.setObject(18, null);
			}
			
			if(mobilePhoneId !=null) {
				
				cstmt.setBigDecimal(19, mobilePhoneId);
			}else {
				cstmt.setObject(19, null);
			}

			cstmt.execute();
			
			int result = cstmt.getInt(1);
			
			resultDTO = new ResultDTO();
			if(result ==0) {
				final Array errors = (Array) cstmt.getObject(12);
				resultDTO.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("setContactInfoByContactId.acctId=" + acctId);
				logger.error("setContactInfoByContactId.error="
						+ ErrorMessageDTO.toStringBuilder(resultDTO.getErrors()));
			}

		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_ACCT_MGMT.setContactInfoByContactId:	" + exception.getMessage(),
					exception);
		} finally {
			close(cstmt);
		}
		
		return resultDTO;

	}
	
	public GetInitialAutoChargeAmountsResponse getInitialAutochargeAmounts(Long acctId, String acctType,
			String dbSessionId, String ipAddress, String loginId, BigDecimal acctTypeId, BigDecimal acctPlanId,
			BigDecimal planDetailId, BigDecimal paymentFormId, BigDecimal vehicleCount) throws EtccException {
		CallableStatement cstmt = null;
		GetInitialAutoChargeAmountsResponse getInitialAutoChargeAmountsResponse = null;

		try {
			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.get_initial_autocharge_amounts(?,?,?,?,?,?,?,?,?,?,?,?)}");

			Map<String, Class<?>> typeMap = setTypeMap();

			cstmt.registerOutParameter(1, Types.INTEGER);

			cstmt.setLong(2, acctId);
			cstmt.setString(3, acctType);
			cstmt.setString(4, dbSessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, loginId);
			cstmt.setBigDecimal(7, acctTypeId);
			cstmt.setBigDecimal(8, acctPlanId);
			cstmt.setBigDecimal(9, planDetailId);
			cstmt.setBigDecimal(10, paymentFormId);
			cstmt.setBigDecimal(11, vehicleCount);
			cstmt.registerOutParameter(12, Types.VARCHAR);
			cstmt.registerOutParameter(13, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			final int result = cstmt.getInt(1);
			logger.debug("getInitialAutochargeAmounts.result=" + result);
			getInitialAutoChargeAmountsResponse = new GetInitialAutoChargeAmountsResponse();

			if (result == 1) {
				final String initialAutoChargeAmounts = cstmt.getString(12);
				final String[] initialAutoChargeAmountsArr = initialAutoChargeAmounts.split(",");
				getInitialAutoChargeAmountsResponse.setInitialPrepaidBalance(initialAutoChargeAmountsArr[0]);
				getInitialAutoChargeAmountsResponse.setAutoChargAmount(initialAutoChargeAmountsArr[1]);
				getInitialAutoChargeAmountsResponse.setLowBalanceLevel(initialAutoChargeAmountsArr[2]);
			} else if (result == 0) {
				final Array errors = (Array) cstmt.getObject(13);
				getInitialAutoChargeAmountsResponse.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("get_initial_autocharge_amounts.acctId=" + acctId);
				logger.error("get_initial_autocharge_amounts.error="
						+ ErrorMessageDTO.toStringBuilder(getInitialAutoChargeAmountsResponse.getErrors()));

			}

		} catch (Exception exception) {
			throw new EtccException(
					"Exception in OLCSC_ACCT_MGMT.get_initial_autocharge_amounts:	" + exception.getMessage(),
					exception);
		} finally {
			close(cstmt);
		}
		return getInitialAutoChargeAmountsResponse;
	}
	
	
	public ValidateDocDownloadResponse validateDocDownload(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId) throws EtccException {
		CallableStatement cstmt = null;
		ValidateDocDownloadResponse validateDocDownloadResponse = null;
		try {
			cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.validation_doc_download(?,?,?,?,?,?)}");

			Map<String, Class<?>> typeMap = setTypeMap();

			cstmt.registerOutParameter(1, Types.INTEGER);

			cstmt.setLong(2, acctId);
			cstmt.setString(3, acctType);
			cstmt.setString(4, dbSessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, loginId);
			cstmt.registerOutParameter(7, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			final int result = cstmt.getInt(1);
			logger.debug("validateDocDownload.result=" + result);
			validateDocDownloadResponse = new ValidateDocDownloadResponse();

			if (result == 1) {
				validateDocDownloadResponse.setIsAccountValidationSuccess(true);
			} else if (result == 0) {
				validateDocDownloadResponse.setIsAccountValidationSuccess(false);
				final Array errors = (Array) cstmt.getObject(7);
				validateDocDownloadResponse.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("validation_doc_download.acctId=" + acctId);
				logger.error("validation_doc_download.error="
						+ ErrorMessageDTO.toStringBuilder(validateDocDownloadResponse.getErrors()));

			}

		} catch (Exception exception) {
			throw new EtccException("Exception in OLCSC_ACCT_MGMT.validation_doc_download:	" + exception.getMessage(),
					exception);

		} finally {
			close(cstmt);
		}

		return validateDocDownloadResponse;

	}
	
	private BillingMethodLimits getBillingMethodLimits(OLC_BILLING_METHOD_LIMTS_REC billingMethodLimit) throws SQLException {

		final BillingMethodLimits billingMethodLimits = new BillingMethodLimits();

		billingMethodLimits.setAccountId(billingMethodLimit.getACCOUNT_ID());
		billingMethodLimits.setPlan(billingMethodLimit.getPLAN());
		billingMethodLimits.setMinBillingMethod(billingMethodLimit.getMIN_BILLING_METHOD());
		billingMethodLimits.setMaxBillingMethod(billingMethodLimit.getMAX_BILLING_METHOD());
		billingMethodLimits.setMinCreditCard(billingMethodLimit.getMIN_CREDIT_CARD());
		billingMethodLimits.setMaxCreditCard(billingMethodLimit.getMAX_CREDIT_CARD());
		billingMethodLimits.setMinAchAccount(billingMethodLimit.getMIN_ACH_ACCOUNT());
		billingMethodLimits.setMaxAchAccount(billingMethodLimit.getMAX_ACH_ACCOUNT());

		return billingMethodLimits;
	}
	
	public AccountLoginDTO setupOnlineAccessStep1ForCCRMA(String accountNumber, String tolltagPrefix,
			String tolltagNumber, String emailAddress, String phoneNumber, String ipAddress, String jsessionId,
			UserEnvDTO userEnvDto) throws EtccException {

		AccountLoginDTO acctLoginDto = new AccountLoginDTO();
		boolean useTollTag = false;
		if (StringUtil.isEmpty(accountNumber)) {
			useTollTag = true;
		}

		CallableStatement cstmt = null;
		try {
			cstmt = this.conn.prepareCall("{? = call Olcsc_olcsetup.set_online_access_step1("
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)}");

			setTypeMap();
			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			if (useTollTag) {
				cstmt.setNull(idx, Types.NUMERIC);
			} else {
				cstmt.setLong(idx, Long.parseLong(accountNumber));
			}
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString());
			cstmt.setString(idx++, ipAddress);
			cstmt.setString(idx++, jsessionId);
			cstmt.setString(idx++, userEnvDto.getBrowserType());
			cstmt.setString(idx++, userEnvDto.getBrowserVersion());
			cstmt.setString(idx++, userEnvDto.getOsType());
			cstmt.setString(idx++, userEnvDto.getOsVersion());
			cstmt.setString(idx++, userEnvDto.getAttribute1());
			cstmt.setString(idx++, userEnvDto.getAttribute2());
			cstmt.setString(idx++, userEnvDto.getAttribute3());
			cstmt.setString(idx++, userEnvDto.getAttribute4());
			cstmt.setString(idx++, userEnvDto.getAttribute5());
			if (tolltagNumber == null || tolltagNumber.length() == 0) {
				cstmt.setNull(idx++, Types.VARCHAR);
				cstmt.setNull(idx++, Types.VARCHAR);
			} else {
				cstmt.setString(idx++, tolltagPrefix);
				cstmt.setString(idx++, tolltagNumber);
			}
			cstmt.setString(idx++, emailAddress);
			cstmt.setString(idx++, phoneNumber);
			cstmt.setString(idx++, userEnvDto.getSourceUserName());
			cstmt.registerOutParameter(idx++, Types.VARCHAR);
			cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short docExists = cstmt.getShort(1);
			if (docExists == 0) {
				acctLoginDto = new AccountLoginDTO();
				acctLoginDto.setErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
				logger.error("setupOnlineAccessStep1ForCCRMA.docExists=" + docExists);

				Pattern CCPattern = Pattern.compile("\\d{14,16}");

				if (CCPattern.matcher(accountNumber).matches()) {
					String maskAccountNumber = "****" + accountNumber.substring(accountNumber.length() - 4);
					logger.error("setupOnlineAccessStep1ForCCRMA.accountNumber=" + maskAccountNumber + ";tolltagPrefix="
							+ tolltagPrefix + ";tolltagNumber=" + tolltagNumber + ";emailAddress=" + emailAddress
							+ ";phoneNumber=" + phoneNumber + ";ipAddress=" + ipAddress + ";jsessionId=" + jsessionId
							+ ";userEnvDto=" + userEnvDto);
				} else {
					logger.error("setupOnlineAccessStep1ForCCRMA.accountNumber=" + accountNumber + ";tolltagPrefix="
							+ tolltagPrefix + ";tolltagNumber=" + tolltagNumber + ";emailAddress=" + emailAddress
							+ ";phoneNumber=" + phoneNumber + ";ipAddress=" + ipAddress + ";jsessionId=" + jsessionId
							+ ";userEnvDto=" + userEnvDto);

				}
				logger.error("setupOnlineAccessStep1ForCCRMA.error="
						+ ErrorMessageDTO.toStringBuilder(acctLoginDto.getErrors()));
			} else {
				
				if (useTollTag) {
					acctLoginDto.setAcctId(cstmt.getLong(2));
				} else {
					acctLoginDto.setAcctId(Long.parseLong(accountNumber));
				}
				acctLoginDto.setDbSessionId(cstmt.getString(idx - 1));
				acctLoginDto.setLastLoginIp(ipAddress);
				acctLoginDto.setLoginId(AccountLoginDTO.GENERIC_USER);
			}

		} catch (SQLException sqlException) {
			throw new EtccException("Error running setupOnlineAccessStep1ForCCRMA: " + sqlException, sqlException);
		} finally {
			close(cstmt);
		}
		return acctLoginDto;
	}
	
	public AccountLoginDTO ccrmaSetupOnlineAccessStep1(String accountNumber, String tolltagPrefix, String tolltagNumber,
			String emailAddress, String phoneNumber, String ipAddress, String jsessionId, UserEnvDTO userEnvDto)
			throws EtccException {

		AccountLoginDTO acctLoginDto = new AccountLoginDTO();
		boolean useTollTag = false;
		if (StringUtil.isEmpty(accountNumber)) {
			useTollTag = true;
		}

		CallableStatement cstmt = null;
		try {
			cstmt = this.conn.prepareCall("{? = call Olcsc_olcsetup.set_ccrma_online_access_step1("
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?)}");

			setTypeMap();
			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			if (useTollTag) {
				cstmt.setNull(idx, Types.NUMERIC);
			} else {
				cstmt.setLong(idx, Long.parseLong(accountNumber));
			}
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString());
			cstmt.setString(idx++, ipAddress);
			cstmt.setString(idx++, jsessionId);
			cstmt.setString(idx++, userEnvDto.getBrowserType());
			cstmt.setString(idx++, userEnvDto.getBrowserVersion());
			cstmt.setString(idx++, userEnvDto.getOsType());
			cstmt.setString(idx++, userEnvDto.getOsVersion());
			cstmt.setString(idx++, userEnvDto.getAttribute1());
			cstmt.setString(idx++, userEnvDto.getAttribute2());
			cstmt.setString(idx++, userEnvDto.getAttribute3());
			cstmt.setString(idx++, userEnvDto.getAttribute4());
			cstmt.setString(idx++, userEnvDto.getAttribute5());
			if (tolltagNumber == null || tolltagNumber.length() == 0) {
				cstmt.setNull(idx++, Types.VARCHAR);
				cstmt.setNull(idx++, Types.VARCHAR);
			} else {
				cstmt.setString(idx++, tolltagPrefix);
				cstmt.setString(idx++, tolltagNumber);
			}
			cstmt.setString(idx++, emailAddress);
			cstmt.setString(idx++, phoneNumber);
			cstmt.setString(idx++, userEnvDto.getSourceUserName());
			cstmt.registerOutParameter(idx++, Types.VARCHAR);
			cstmt.registerOutParameter(idx++, Types.VARCHAR);
			cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short docExists = cstmt.getShort(1);
			if (docExists == 0) {
				acctLoginDto = new AccountLoginDTO();
				acctLoginDto.setErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
				logger.error("ccrmaSetupOnlineAccessStep1.docExists=" + docExists);

				Pattern CCPattern = Pattern.compile("\\d{14,16}");

				if (CCPattern.matcher(accountNumber).matches()) {
					String maskAccountNumber = "****" + accountNumber.substring(accountNumber.length() - 4);
					logger.error("ccrmaSetupOnlineAccessStep1.accountNumber=" + maskAccountNumber + ";tolltagPrefix="
							+ tolltagPrefix + ";tolltagNumber=" + tolltagNumber + ";emailAddress=" + emailAddress
							+ ";phoneNumber=" + phoneNumber + ";ipAddress=" + ipAddress + ";jsessionId=" + jsessionId
							+ ";userEnvDto=" + userEnvDto);
				} else {
					logger.error("ccrmaSetupOnlineAccessStep1.accountNumber=" + accountNumber + ";tolltagPrefix="
							+ tolltagPrefix + ";tolltagNumber=" + tolltagNumber + ";emailAddress=" + emailAddress
							+ ";phoneNumber=" + phoneNumber + ";ipAddress=" + ipAddress + ";jsessionId=" + jsessionId
							+ ";userEnvDto=" + userEnvDto);

				}
				logger.error("ccrmaSetupOnlineAccessStep1.error="
						+ ErrorMessageDTO.toStringBuilder(acctLoginDto.getErrors()));
			} else {

				if (useTollTag) {
					acctLoginDto.setAcctId(cstmt.getLong(2));
				} else {
					acctLoginDto.setAcctId(Long.parseLong(accountNumber));
				}
				acctLoginDto.setDbSessionId(cstmt.getString(idx - 2));
				acctLoginDto.setLastLoginIp(ipAddress);
				
				String loginId = cstmt.getString(idx - 1);
				if (StringUtil.isEmpty(loginId)){
					loginId = "CCRMA_ANONYMOUS";
				}
				acctLoginDto.setLoginId(loginId);
			}

		} catch (SQLException sqlException) {
			throw new EtccException("Error running set_ccrma_online_access_step1: " + sqlException, sqlException);
		} finally {
			close(cstmt);
		}
		return acctLoginDto;
	}

	public AccountLoginDTO ccrmaSetupOnlineAccess(AccountLoginDTO acctLoginDto, OnlineAccessSetupDTO oasDto)
			throws EtccSecurityException, EtccException {

		AccountLoginDTO result = null;
		CallableStatement cstmt = null;
		CallableStatement cstmt2 = null;
		try {
			if (logger.isInfoEnabled()) {
				logger.info(new StringBuilder("ccrmaSetupOnlineAccess: acct=").append(acctLoginDto.getAcctId()));
			}
			String loginId = oasDto.getLoginId();
			if (loginId != null) {
				loginId = loginId.toUpperCase();
			}

			setTypeMap();

			cstmt = this.conn.prepareCall(
					"{? = call OLCSC_OLCSETUP.set_account_login_info_ccrma(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
							+ "null, null, ?)}");

			StringBuilder sb = new StringBuilder();
			sb.append("OLCSC_OLCSETUP.Set_Account_Login_Info(");

			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);

			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			sb.append(acctLoginDto.getDbSessionId());

			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			sb.append(", ");
			sb.append(acctLoginDto.getAcctId());

			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			sb.append(", ");
			sb.append(acctLoginDto.getLastLoginIp());

			// TODO check the logic
			String user = oasDto.getUserID();
			if (StringUtil.isEmpty(user)) {
				user = oasDto.getLoginId();
			}
			cstmt.setString(idx++, user);
			sb.append(", ");
			sb.append(user);
			cstmt.setString(idx++, user);
			sb.append(", ");
			sb.append(user);

			cstmt.setString(idx++, oasDto.getPassword());
			sb.append(", ");
			sb.append("****");

			cstmt.setInt(idx++, oasDto.getSecurityQuestionID());
			sb.append(", ");
			sb.append(oasDto.getSecurityQuestionID());

			cstmt.setString(idx++, oasDto.getSecurityQuestionAnswer());
			sb.append(", ");
			sb.append("****");

			cstmt.setString(idx++, oasDto.getEmailAddress());
			sb.append(", ");
			sb.append(oasDto.getEmailAddress());

			cstmt.setString(idx++, oasDto.getAlternateEmail());
			sb.append(", ");
			sb.append(oasDto.getAlternateEmail());

			cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.registerOutParameter(idx, Types.VARCHAR);
			sb.append(")");
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
			cstmt.execute();

			short success = cstmt.getShort(1);
			if (success == 0) {
				Array errors = (Array) cstmt.getObject(idx - 1);
				result = new AccountLoginDTO();
				result.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("ccrmaSetupOnlineAccess.success=" + success);
				logger.error("ccrmaSetupOnlineAccess.acctLoginDto=" + acctLoginDto + ";oasDto=" + oasDto);
				logger.error("ccrmaSetupOnlineAccess.error=" + ErrorMessageDTO.toStringBuilder(result.getErrors()));
			} else if (success == -1) {
				throw new EtccSecurityException("Security exception in " + "ccrmaSetupOnlineAccess");
			} else if (success == 1) {
				result = acctLoginDto;
				result.setDbSessionId(cstmt.getString(idx));
				result.setLoginId(oasDto.getLoginId());
				// result.setLoginId(oasDto.getUserID());
				result.setLoginType(AccountLoginDTO.LoginType.AC);

				// Had to call this function to expire the url link if user resets password
				// using email.
				cstmt2 = this.conn.prepareCall("{? = call Olcsc_Online_Access.set_email_validation_status(?,?)}");
				cstmt2.registerOutParameter(1, Types.SMALLINT);
				cstmt2.setString(2, Long.toString(acctLoginDto.getAcctId()));
				cstmt2.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
				cstmt2.execute();

				short success2 = cstmt2.getShort(1);
				if (success2 == 0) {
					Array errors = (Array) cstmt2.getObject(3);
					result = new AccountLoginDTO();
					result.setErrors(OracleUtil.convertToMessages(errors));
				}
			}

		} catch (SQLException sqle) {
			throw new EtccException("Error running ccrmaSetupOnlineAccess: " + sqle, sqle);
		} finally {
			close(cstmt);
			close(cstmt2);
		}
		return result;

	}

	public PersonalInfoUpdateResultDTO setPersonalInfoWithPrimaryPhoneExtAndSmsAlertOptIn(AccountLoginDTO acctLoginDto,
			String acctType, String companyName, String firstName, String lastName, String primaryPhone,
			String alternatePhone, String taxId, String driverLicState, String driverLicNumber, String address1,
			String address2, String address3, String address4, String city, String state, String zip, String country,
			boolean byMail, boolean byEmail, boolean byPhone, Long retailTransId, String altPhoneExt, String plus4,
			String primaryPhoneExt, String smsAlertsOptIn) throws EtccException, EtccSecurityException {

		CallableStatement cstmt = null;
		try {

			PersonalInfoUpdateResultDTO resultDto = null;
			long docId = acctLoginDto.getAcctId();
			String docType = AccountLoginDTO.LoginType.AC.toString();
			String loginId = acctLoginDto.getLoginId();
			String dbSessionId = acctLoginDto.getDbSessionId();
			String lastLoginIp = acctLoginDto.getLastLoginIp();
			// Express Account Changes
			if (!StringUtil.isEmpty(acctType)) {
				acctType = acctType.equalsIgnoreCase("personal") ? "P" : "C";
			} else {
				acctType = "P";
			}

			// final boolean zMail = byMail != null && byMail.booleanValue();
			String strByMail = StringUtil.booleanToString(byMail);
			/* String ntfPref = (byEmail!=null && byEmail)?"E":"M"; */

			String[] O_DUPE_DL_FLAG = new String[] { "Y" };
			BigDecimal[] O_RTL_TRXN_ID = new BigDecimal[1];
			OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

			if (retailTransId != null)
				O_RTL_TRXN_ID[0] = new BigDecimal(retailTransId.longValue());

			String P_POSTAL_FLAG = strByMail;
			String P_EMAIL_FLAG = StringUtil.booleanToString(byEmail);
			String P_PHONE_FLAG = StringUtil.booleanToString(byPhone);
			final boolean debugEnabled = logger.isDebugEnabled();
			if (debugEnabled) {
				logger.debug("new OLCSC_ACCT_MGMT(conn).PERSONAL_INFO(new BigDecimal(docId=" + docId + ")"
						+ ", docType=" + docType + ", acctType=" + acctType + ", P_POSTAL_FLAG=" + P_POSTAL_FLAG
						+ ", P_EMAIL_FLAG=" + P_EMAIL_FLAG + ", P_PHONE_FLAG=" + P_PHONE_FLAG + ", dbSessionId="
						+ dbSessionId + ", lastLoginIp=" + lastLoginIp + ", loginId=" + loginId + ", firstName="
						+ firstName + ", lastName=" + lastName + ", companyName=" + companyName + ", taxId=" + taxId
						+ ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3 + ", address4="
						+ address4 + ", city=" + city + ", state=" + state + ", country=" + country + ", zip=" + zip
						+ ", plus4=" + plus4 + ", primaryPhone=" + primaryPhone + ", alternatePhone=" + alternatePhone
						+ ", altPhoneExt=" + altPhoneExt + ", driverLicNumber=" + driverLicNumber + ", driverLicState="
						+ driverLicState + ", strByMail=" + strByMail + ", null=" + ", null=" + ", O_DUPE_DL_FLAG=[Y]"
						+ ", O_RTL_TRXN_ID=new BigDecimal[1]"
						+ ", O_ERROR_MSG_ARR=new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() }" + ").intValue()"
						+ "primaryPhoneExt= " + primaryPhoneExt + "smsAlertsOptIn= " + smsAlertsOptIn + "\n");
			}

			cstmt = this.conn.prepareCall(
					"{? = call OLCSC_ACCT_MGMT.Personal_Info(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			setTypeMap();
			int parameterIndex = 1;
			cstmt.registerOutParameter(parameterIndex++, Types.SMALLINT);
			cstmt.setLong(parameterIndex++, docId);
			cstmt.setString(parameterIndex++, docType);
			cstmt.setString(parameterIndex++, acctType);
			cstmt.setString(parameterIndex++, P_POSTAL_FLAG);
			cstmt.setString(parameterIndex++, P_EMAIL_FLAG);
			cstmt.setString(parameterIndex++, P_PHONE_FLAG);
			cstmt.setString(parameterIndex++, dbSessionId);
			cstmt.setString(parameterIndex++, lastLoginIp);
			cstmt.setString(parameterIndex++, loginId);
			cstmt.setString(parameterIndex++, firstName);
			cstmt.setString(parameterIndex++, lastName);
			cstmt.setString(parameterIndex++, companyName);
			cstmt.setString(parameterIndex++, address1);
			cstmt.setString(parameterIndex++, address2);
			cstmt.setString(parameterIndex++, address3);
			cstmt.setString(parameterIndex++, address4);
			cstmt.setString(parameterIndex++, city);
			cstmt.setString(parameterIndex++, state);
			cstmt.setString(parameterIndex++, country);
			cstmt.setString(parameterIndex++, zip);
			cstmt.setString(parameterIndex++, plus4);
			cstmt.setString(parameterIndex++, primaryPhone);
			cstmt.setString(parameterIndex++, alternatePhone);
			cstmt.setString(parameterIndex++, altPhoneExt);
			cstmt.setString(parameterIndex++, strByMail);
			cstmt.setString(parameterIndex++, null);
			cstmt.setString(parameterIndex++, null);
			cstmt.setLong(parameterIndex++, retailTransId);
			cstmt.registerOutParameter(parameterIndex, Types.NUMERIC);
			cstmt.registerOutParameter(parameterIndex++, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setString(parameterIndex++, primaryPhoneExt);
			cstmt.setString(parameterIndex, smsAlertsOptIn);

			cstmt.execute();
			int result = cstmt.getInt(1);
			logger.debug("OracleAccountDAO.setPersonalInfo result= " + result);

			if (result == -1) {
				if (debugEnabled)
					logger.debug("acctId:" + acctLoginDto.getAcctId() + "****" + "setPersonalInfo result is -1");
				throw new EtccSecurityException("security exception in setPersonalInfo()");
			}

			resultDto = new PersonalInfoUpdateResultDTO();

			if (result == 1) {
				logger.debug("OracleAccountDAO.setPersonalInfo O_RTL_TRXN_ID= " + cstmt.getLong(29));
				resultDto.setRetailTransId(cstmt.getLong(29));
			} else {
				Array errors = (Array) cstmt.getObject(30);
				if (errors != null) {
					resultDto.setErrors(OracleUtil.convertToMessages(errors));
					logger.error("setPersonalInfo.result=" + result);
					logger.error(new StringBuilder("setPersonalInfo:acctType=" + acctType)
							.append(",companyName=" + companyName).append(",firstName=" + firstName)
							.append(",lastName=" + lastName).append(",primaryPhone=" + primaryPhone)
							.append(",alternatePhone=" + alternatePhone).append(",taxId=" + taxId)
							.append(",driverLicState=" + driverLicState).append(",driverLicNumber=" + driverLicNumber)
							.append(",address1=" + address1).append(",address2=" + address2)
							.append(",address3=" + address3).append(",address4=" + address4).append(",city=" + city)
							.append(",state=" + state).append(",zip=" + zip).append(",country=" + country)
							.append(",byMail=" + byMail).append(",byEmail=" + byEmail)
							.append(",retailTransId=" + retailTransId).append(",altPhoneExt=" + altPhoneExt)
							.append(",plus4=" + plus4).append(",acctLoginDto=" + acctLoginDto).append("primaryPhoneExt")
							.append(primaryPhoneExt).append("smsAlertsOptIn").append(smsAlertsOptIn));
					logger.error("setPersonalInfo.error=" + ErrorMessageDTO.toStringBuilder(resultDto.getErrors()));
				}
			}

			return resultDto;
		} catch (SQLException ex) {
			throw new EtccException("Exception in SetPersonalInfo  " + ex, ex);
		} finally {
			close(cstmt);
		}

	}

}
