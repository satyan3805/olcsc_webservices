/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.EmailValidationDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.EmailValidationDataDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.plsql.OLCSC_EMAIL_VALIDATION;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;

public class OracleEmailValidationDAO extends EmailValidationDAO {
    public static final Logger logger = Logger.getLogger(OracleEmailValidationDAO.class);

    public EmailValidationDataDTO validationData(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException {
        try {
            validateAccountLogin(acctLogin);
            //5184
            BigDecimal validationId = new BigDecimal(acctLogin.getValidationId());
            String sessionId = acctLogin.getDbSessionId();
            String ipAddress = acctLogin.getLastLoginIp();
            BigDecimal[] validationStatus = new BigDecimal[1];
            BigDecimal[] acctId = new BigDecimal[1];
            String[] emailAddress = new String[1];
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            if (logger.isDebugEnabled())
                logger.debug("new olcsc_email_validation.GET_EMAIL_VALIDATION_DATA(validationId=" + validationId +
                    ", sessionId=" + sessionId + ", ipAddress=" + ipAddress + ")");
            BigDecimal dbRetVal = new OLCSC_EMAIL_VALIDATION(conn).GET_EMAIL_VALIDATION_DATA(validationId, sessionId, ipAddress,
                validationStatus, acctId, emailAddress, O_ERROR_MSG_ARR);
            int retVal = dbRetVal.intValue();
            if (logger.isInfoEnabled())
                logger.info("validationData.retVal=" + retVal);
            if (retVal == -1) {
                throw new EtccSecurityException("Email validation data retrieval is blocked");
            } else if (retVal == 0) {
                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                EmailValidationDataDTO errorMsgCarrier = new EmailValidationDataDTO();
                errorMsgCarrier.setErrors(toErrorMessageDTOArray(errorArray));
                return errorMsgCarrier;
            } else if (retVal == 1) {
                EmailValidationDataDTO validationData = new EmailValidationDataDTO();
                validationData.setAccountId(accountId(acctId));
                validationData.setEmailAddress(emailAddress(emailAddress));
                validationData.setValidationStatus(validationStatus[0].intValue());
                validationData.setErrors((ErrorMessageDTO[])null);
                if (logger.isInfoEnabled())
                    logger.info("validationData.validationData.accountId=" + validationData.getAccountId()
                        + ", validationData.validationData.emailAddress=" + validationData.getEmailAddress()
                        + ", validationData.validationData.validationStatus=" + validationData.getValidationStatus());
                return validationData;
            } else {
                throw new EtccException("Invalid Return value form Database: " + retVal);
            }
        } catch (SQLException e) {
            throw new EtccException(e.getMessage(), e);
        }
    }

    public ResultDTO validationStatus(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException {
        try {
            validateAccountLogin(acctLogin);
            BigDecimal docId = new BigDecimal(acctLogin.getAcctId());
            String docType = acctLogin.getLoginTypeString();
            String sessionId = acctLogin.getDbSessionId();
            String ipAddress = acctLogin.getLastLoginIp();
            String loginId = acctLogin.getLoginId();
            BigDecimal[] validationStatus = new BigDecimal[1];
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            if (logger.isDebugEnabled())
                logger.debug("olcsc_email_validation.GET_EMAIL_VALIDATION_STATUS(docId=" + docId + ", docType=" + docType +
                    ", sessionId=" + sessionId + ", ipAddress=" + ipAddress + ", loginId=" + loginId);
            BigDecimal dbRetVal = new OLCSC_EMAIL_VALIDATION(conn).GET_EMAIL_VALIDATION_STATUS(docId, docType,
                sessionId, ipAddress, loginId, validationStatus, O_ERROR_MSG_ARR);
            int retVal = dbRetVal.intValue();
            if (logger.isInfoEnabled())
                logger.info("validationStatus.retVal=" + retVal);
            if (retVal == -1) {
                throw new EtccSecurityException("Email validation status retrieval is blocked");
            } else if (retVal == 0) {
                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                ResultDTO errorMsgCarrier = new ResultDTO();
                errorMsgCarrier.setErrors(toErrorMessageDTOArray(errorArray));
                return errorMsgCarrier;
            } else if (retVal == 1) {
                ResultDTO currentStatus = new ResultDTO();
                currentStatus.setStatus(validationStatus[0].intValue());
                currentStatus.setErrors((ErrorMessageDTO[])null);
                if (logger.isInfoEnabled())
                    logger.info("validationStatus.currentStatus.status=" + currentStatus.getStatus());
                return currentStatus;
            } else {
                throw new EtccException("Invalid data value");
            }
        } catch (SQLException e) {
            throw new EtccException(e.getMessage(),e);
        }
    }

    public ResultDTO setValidationDone(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException {
        try {
            validateAccountLogin(acctLogin);
            BigDecimal docId = new BigDecimal(acctLogin.getAcctId());
            String docType = acctLogin.getLoginTypeString();
            String sessionId = acctLogin.getDbSessionId();
            String ipAddress = acctLogin.getLastLoginIp();
            String loginId = acctLogin.getLoginId();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            if (logger.isDebugEnabled())
                logger.debug("olcsc_email_validation.SET_EMAIL_VALIDATION_STATUS(docId=" + docId + ", docType=" + docType +
                    ", sessionId=" + sessionId + ", ipAddress=" + ipAddress + ", loginId=" + loginId);
            BigDecimal dbRetVal = new OLCSC_EMAIL_VALIDATION(conn).SET_EMAIL_VALIDATION_STATUS(docId, docType, sessionId, ipAddress, loginId, O_ERROR_MSG_ARR);
            int retVal = dbRetVal.intValue();
            if (logger.isInfoEnabled())
                logger.info("setValidationStatus.retVal=" + retVal);
            if (retVal == -1) {
                throw new EtccSecurityException("Setting email validation status is blocked");
            } else if (retVal == 0) {
                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                ResultDTO errorMsgCarrier = new ResultDTO();
                errorMsgCarrier.setErrors(toErrorMessageDTOArray(errorArray));
                return errorMsgCarrier;
            } else if (retVal == 1) {
                ResultDTO successCarrier = new ResultDTO();
                successCarrier.setErrors((ErrorMessageDTO[])null);
                return successCarrier;
            } else {
                throw new EtccException("Invalid data value");
            }
        } catch (SQLException e) {
            throw new EtccException(e.getMessage(), e);
        }
    }

    public ResultDTO generateEmailValidationMsg(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException {
        try {
            validateAccountLogin(acctLogin);
            BigDecimal docId = new BigDecimal(acctLogin.getAcctId());
            String docType = acctLogin.getLoginTypeString();
            String sessionId = acctLogin.getDbSessionId();
            String ipAddress = acctLogin.getLastLoginIp();
            String loginId = acctLogin.getLoginId();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            if (logger.isDebugEnabled())
                logger.debug("olcsc_email_validation.GENERATE_VALIDATION_EMAIL(docId=" + docId + ", docType=" + docType +
                    ", sessionId=" + sessionId + ", ipAddress=" + ipAddress + ", loginId=" + loginId);
            BigDecimal dbRetVal = new OLCSC_EMAIL_VALIDATION(conn).GENERATE_VALIDATION_EMAIL(docId, docType, sessionId, ipAddress, loginId, O_ERROR_MSG_ARR);
            int retVal = dbRetVal.intValue();
            if (logger.isInfoEnabled())
                logger.info("generateEmailValidationMsg.retVal=" + retVal);
            if (retVal == -1) {
                throw new EtccSecurityException("Setting email validation status is blocked");
            } else if (retVal == 0) {
                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                ResultDTO errorMsgCarrier = new ResultDTO();
                errorMsgCarrier.setErrors(toErrorMessageDTOArray(errorArray));
                return errorMsgCarrier;
            } else if (retVal == 1) {
                ResultDTO successCarrier = new ResultDTO();
                successCarrier.setErrors((ErrorMessageDTO[])null);
                return successCarrier;
            } else {
                throw new EtccException("Invalid data value");
            }
        } catch (SQLException e) {
            throw new EtccException(e.getMessage(),e);
        }
    }

	@Override
	protected String getErrorMsg(Object error) throws SQLException {
		OLC_ERROR_MSG_REC e = (OLC_ERROR_MSG_REC) error;
		return e.getERROR_MSG();
	}
}
