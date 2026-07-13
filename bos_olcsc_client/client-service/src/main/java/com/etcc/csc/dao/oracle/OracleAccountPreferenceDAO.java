/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountPreferenceDAO;
import com.etcc.csc.dto.AccountIopValueDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;
import com.etcc.csc.dto.AccountPreferenceValueDTO;
import com.etcc.csc.plsql.OLCSC_USER_PREFERENCES;
import com.etcc.csc.plsql.OLC_ACCT_NOTIFICATION_ARR;
import com.etcc.csc.plsql.OLC_ACCT_NOTIFICATION_REC;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;
import com.etcc.csc.plsql.OLC_IOP_ACCT_INFO;
import com.etcc.csc.plsql.OLC_IOP_ACCT_INFO_ARR;
import com.etcc.csc.util.StringUtil;

/**
 * Based on OracleNewAccountPreferenceDAO from OLSCService module.
 * @author Stephen Davidson
 */
public class OracleAccountPreferenceDAO extends AccountPreferenceDAO {
    private static final Logger logger = Logger.getLogger(OracleAccountPreferenceDAO.class);

    private AccountIopValueDTO[] convertIopSettings(OLC_IOP_ACCT_INFO[] iopArray) {
        AccountIopValueDTO[] iopValues = new AccountIopValueDTO[iopArray.length];
        for (int i = 0; i < iopArray.length; ++i) {
            try {
                iopValues[i] = new AccountIopValueDTO(
                		iopArray[i].getACCT_ID(),
                		iopArray[i].getAGCY_ID(),
                		iopArray[i].getAGCY_ABBREV(),
                		iopArray[i].getAGCY_NAME(),
                		iopArray[i].getREASON_CODE(),
                		StringUtil.stringToBoolean(iopArray[i].getIS_ACTIVE()));
            } catch (Exception e) {
                logger.error("convertIopSettings(OLC_IOP_ACCT_INFO[]) error", e);
            }
        }
        return iopValues;
    }

    private AccountPreferenceValueDTO[] convertPreferences(OLC_ACCT_NOTIFICATION_REC[] notificationArray) {
        AccountPreferenceValueDTO[] preValues = new AccountPreferenceValueDTO[notificationArray.length];
        for (int i = 0; i < notificationArray.length; ++i) {
            try {
                preValues[i] = new AccountPreferenceValueDTO(
                		notificationArray[i].getACCOUNT_ID(),
                		notificationArray[i].getNOTIFICATION_TYPE(),
                		notificationArray[i].getNTFT_LONG_DESC(),
                		notificationArray[i].getNOTIFICATION_FORMAT(),
                		StringUtil.stringToBoolean(notificationArray[i].getYES_OR_NO()),
                        notificationArray[i].getSECTION());
            } catch (Exception e) {
                logger.error("convertPreferences(OLC_ACCT_NOTIFICATION_REC[])", e);
            }
        }
        return preValues;
    }

    public AccountPreferencesDTO getPreferences(AccountLoginDTO acctLoginDTO) throws EtccException,
            EtccSecurityException {
        AccountPreferencesDTO resultDTO = null;

        try {

            String userId = acctLoginDTO.getLoginId();
            BigDecimal acctId = new BigDecimal(acctLoginDTO.getAcctId());
            String sessionId = acctLoginDTO.getDbSessionId();
            String ipAddress = acctLoginDTO.getLastLoginIp();

            OLC_IOP_ACCT_INFO_ARR[] O_OLC_IOP_ACCT_INFO_ARR = new OLC_IOP_ACCT_INFO_ARR[] { new OLC_IOP_ACCT_INFO_ARR() };
            String[] p_postal_flag = new String[1];// { new String()};
            String[] p_email_flag = new String[1] ;// { new String()};;
            String[] p_sms_flag = new String[1] ;// { new String()};;
            

            OLC_ACCT_NOTIFICATION_ARR[] O_OLC_ACCT_NOTIFICATION_ARR = new OLC_ACCT_NOTIFICATION_ARR[] { new OLC_ACCT_NOTIFICATION_ARR() };

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            BigDecimal result = new OLCSC_USER_PREFERENCES(this.conn).GET_PREFERENCES(userId, acctId, sessionId,
                    ipAddress, O_OLC_IOP_ACCT_INFO_ARR, O_OLC_ACCT_NOTIFICATION_ARR,p_postal_flag , p_email_flag , p_sms_flag , O_ERROR_MSG_ARR);
            if (result != null) {
                if (result.intValue() == -1) {
                    throw new EtccSecurityException("security exception in getPreferences");
                }

                resultDTO = new AccountPreferencesDTO();
                if (result.intValue() == 0) {
                    OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                    resultDTO.setErrors(OracleUtil.convertToMessages(errorArray));
                    if (logger.isDebugEnabled())
                    	for (int i = 0; i < errorArray.length; i++)
                            logger.debug("errorArray[" + i + "]=" + errorArray[i].getERROR_MSG());
                } else {
                    OLC_IOP_ACCT_INFO[] iopArray = O_OLC_IOP_ACCT_INFO_ARR[0].getArray();
                    resultDTO.setIopSettings(convertIopSettings((iopArray)));

                    OLC_ACCT_NOTIFICATION_REC[] notificationArray = O_OLC_ACCT_NOTIFICATION_ARR[0].getArray();
                    resultDTO.setPreferences(convertPreferences(notificationArray));
                    
                    if (! StringUtils.isEmpty(p_postal_flag[0]) )
                    { 
                    	resultDTO.setByMail( p_postal_flag[0].equalsIgnoreCase("Y") ? true : false);
                    }
                    if (! StringUtils.isEmpty(p_email_flag[0]) )
                    { 
                    	resultDTO.setByEmail( p_email_flag[0].equalsIgnoreCase("Y") ? true : false);
                    }
                    if (! StringUtils.isEmpty(p_sms_flag[0]) )
                    { 
                    	resultDTO.setBySms( p_sms_flag[0].equalsIgnoreCase("Y") ? true : false);
                    }
                    
                }
                // TODO: should the e-mail format actually be loaded from DB?
//                resultDTO.setEmailFormat(format);
                if (logger.isDebugEnabled())
                    logger.debug("getPreferences: " + resultDTO);
            }
            return resultDTO;
        } catch (SQLException e) {
            throw new EtccException("acctId:" + acctLoginDTO.getAcctId() + "****" + "errror getting preferences" + e, e);
        }
    }

    public AccountPreferencesDTO setPreferences(AccountLoginDTO acctLoginDTO, AccountPreferencesDTO preferenceDTO)
            throws EtccException, EtccSecurityException {

        try {
            if (logger.isDebugEnabled())
                logger.debug("in setPrefernece: " + preferenceDTO);
            String userId = acctLoginDTO.getLoginId();
            BigDecimal acctId = new BigDecimal(acctLoginDTO.getAcctId());
            String sessionId = acctLoginDTO.getDbSessionId();
            String ipAddress = acctLoginDTO.getLastLoginIp();
            OLC_IOP_ACCT_INFO_ARR iopArray = convertToIopArray(preferenceDTO.getIopSettings());
            OLC_ACCT_NOTIFICATION_ARR ntfArray = covertToNotificationArray(preferenceDTO.getPreferences());
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            String p_postal_flag = preferenceDTO.isByMail() == true ? "Y" : "N";
            String p_email_flag =  preferenceDTO.isByEmail() == true ? "Y" : "N";
            String p_sms_flag =    preferenceDTO.isBySms() == true ? "Y" : "N";
            
            int result = new OLCSC_USER_PREFERENCES(this.conn).SET_PREFERENCES(userId, acctId, sessionId, ipAddress,
                    iopArray, ntfArray, preferenceDTO.getEmailFormat(),p_postal_flag , p_email_flag , p_sms_flag ,  O_ERROR_MSG_ARR).intValue();
            if (result == -1) {
                throw new EtccSecurityException("security exception in setPreferences");
            }

            if (result == 0) {
                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                preferenceDTO.setErrors(OracleUtil.convertToMessages(errorArray));
                if (logger.isDebugEnabled())
                	for (int i = 0; i < errorArray.length; i++)
                        logger.debug("errorArray[" + i + "]=" + errorArray[i].getERROR_MSG());
            }

        } catch (SQLException e) {
            throw new EtccException("acctId:" + acctLoginDTO.getAcctId() + "****" + "errror setting preferences" + e, e);
        }
        return preferenceDTO;
    }

    private OLC_IOP_ACCT_INFO_ARR convertToIopArray(AccountIopValueDTO[] dtoArray) throws SQLException {

        OLC_IOP_ACCT_INFO_ARR result = null;
        if (dtoArray != null && dtoArray.length > 0) {
            OLC_IOP_ACCT_INFO[] iopArray = new OLC_IOP_ACCT_INFO[dtoArray.length];
            for (int i = 0; i < dtoArray.length; i++) {
                OLC_IOP_ACCT_INFO rec = new OLC_IOP_ACCT_INFO();
                rec.setACCT_ID(dtoArray[i].getAcctId());
                rec.setAGCY_ABBREV(dtoArray[i].getAgcyAbbrev());
                rec.setAGCY_ID(dtoArray[i].getAgcyId());
                rec.setAGCY_NAME(dtoArray[i].getAgcyName());
                rec.setIS_ACTIVE(StringUtil.booleanToString(dtoArray[i].isActive()));
                rec.setREASON_CODE("USR");

                iopArray[i] = rec;
            }
            result = new OLC_IOP_ACCT_INFO_ARR(iopArray);
        }
        return result;
    }

    private OLC_ACCT_NOTIFICATION_ARR covertToNotificationArray(AccountPreferenceValueDTO[] dtoArray)
            throws SQLException {
        OLC_ACCT_NOTIFICATION_ARR result = null;

        if (dtoArray != null && dtoArray.length > 0) {
            OLC_ACCT_NOTIFICATION_REC[] ntfArray = new OLC_ACCT_NOTIFICATION_REC[dtoArray.length];
            for (int i = 0; i < dtoArray.length; i++) {
                OLC_ACCT_NOTIFICATION_REC rec = new OLC_ACCT_NOTIFICATION_REC();
                rec.setACCOUNT_ID(dtoArray[i].getAcctId());
                rec.setNOTIFICATION_FORMAT(dtoArray[i].getNotificationFormat());
                rec.setNOTIFICATION_TYPE(dtoArray[i].getNotificationType());
                rec.setNTFT_LONG_DESC(dtoArray[i].getDescription());
                rec.setSECTION(dtoArray[i].getSection());
                rec.setYES_OR_NO(StringUtil.booleanToString(dtoArray[i].isActive()));
                ntfArray[i] = rec;
            }
            result = new OLC_ACCT_NOTIFICATION_ARR(ntfArray);
        }
        return result;
    }

	public String updateOptin(AccountLoginDTO acctLoginDTO, String pushNtfOptin)
			throws EtccException, EtccSecurityException {
		String oPushNotifyOtpin[] = new String[1];
		try {
			if (logger.isDebugEnabled())
				logger.debug("in update optin: " + acctLoginDTO
						+ "input option: " + pushNtfOptin);
			// inputs to service
			BigDecimal pDocId = new BigDecimal(acctLoginDTO.getAcctId());
			String pDocType = acctLoginDTO.getLoginTypeString();
			String sessionId = acctLoginDTO.getDbSessionId();
			String ipAddress = acctLoginDTO.getLastLoginIp();
			String userId = acctLoginDTO.getLoginId();
			String pLoginPwd = null;
			String pPushNotifyOtpin = pushNtfOptin;

			OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

			int result = new OLCSC_USER_PREFERENCES(this.conn)
					.OLCSC_UPDATE_OPTIN(pDocId, pDocType, sessionId, ipAddress,
							userId, pLoginPwd, pPushNotifyOtpin,
							oPushNotifyOtpin, O_ERROR_MSG_ARR).intValue();

			if (result == -1) {
				throw new EtccSecurityException(
						"security exception in setPreferences");
			}

			if (result == 0) {
				OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
				if (logger.isDebugEnabled())
					for (int i = 0; i < errorArray.length; i++)
						logger.debug("errorArray[" + i + "]="
								+ errorArray[i].getERROR_MSG());
			}

		} catch (SQLException e) {
			throw new EtccException("acctId:" + acctLoginDTO.getAcctId()
					+ "****" + "errror setting preferences" + e, e);
		}
		return oPushNotifyOtpin[0];
	}
	
	public String getOptin(AccountLoginDTO acctLoginDTO) throws EtccException,
			EtccSecurityException {
		String oPushNotifyOtpin[] = new String[1];
		try {
			if (logger.isDebugEnabled())
				logger.debug("in get optin: " + acctLoginDTO);
			// inputs to service
			BigDecimal pDocId = new BigDecimal(acctLoginDTO.getAcctId());
			String pDocType = acctLoginDTO.getLoginTypeString();
			String sessionId = acctLoginDTO.getDbSessionId();
			String ipAddress = acctLoginDTO.getLastLoginIp();
			String userId = acctLoginDTO.getLoginId();
			String pLoginPwd = null;

			OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

			int result = new OLCSC_USER_PREFERENCES(this.conn).OLCSC_GET_OPTIN(
					pDocId, pDocType, sessionId, ipAddress, userId, pLoginPwd,
					oPushNotifyOtpin, O_ERROR_MSG_ARR).intValue();

			if (result == -1) {
				throw new EtccSecurityException(
						"security exception in setPreferences");
			}

			if (result == 0) {
				OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
				if (logger.isDebugEnabled())
					for (int i = 0; i < errorArray.length; i++)
						logger.debug("errorArray[" + i + "]="
								+ errorArray[i].getERROR_MSG());
			}

		} catch (SQLException e) {
			throw new EtccException("acctId:" + acctLoginDTO.getAcctId()
					+ "****" + "errror setting preferences" + e, e);
		}
		return oPushNotifyOtpin[0];
	}
}
