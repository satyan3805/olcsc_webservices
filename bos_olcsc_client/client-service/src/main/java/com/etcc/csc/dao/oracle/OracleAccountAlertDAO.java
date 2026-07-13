/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountAlertDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC;
import com.etcc.csc.plsql.OLC_ALERT_DISPLAY_REC;


/**
 * Oracle implementation of the AccountAlertDAO.  Copied from OLCSCService com.etcc.csc.dao.OracleNewAccountAlertDAO.
 * @author Stephen Davidson
 */
public class OracleAccountAlertDAO extends AccountAlertDAO {
    private static final Logger logger = Logger.getLogger(OracleAccountAlertDAO.class);

    public AlertDTO[] getAcctSummAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        final boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled)
            logger.debug("Method Name = getAcctSummAlerts");
        AlertDTO[] result = null;
        CallableStatement cstmt = null;
        try {

            cstmt = this.conn.prepareCall("{? = call ACCT_ALERTS.Acct_Summ_Alerts(?, ?, ?, ?, ?, ?, ?)}");

            Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
            typeMap.put("OL_OWNER.OLC_ACCOUNT_INFO_REC", OLC_ACCOUNT_INFO_REC.class);
            typeMap.put("OL_OWNER.OLC_ALERT_DISPLAY_REC", OLC_ALERT_DISPLAY_REC.class);
            setTypeMap();
            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLoginTypeString());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            short success = cstmt.getShort(1);
            if (isDebugEnabled)
                logger.debug("Method Name = getAcctSummAlerts: success = " + success);
            if (success == 1) {

                Object[] objArray = (Object[]) cstmt.getArray(idx - 1).getArray();
                if (objArray != null && objArray.length > 0) {
                    final int length = objArray.length;
                    result = new AlertDTO[length];
                    OLC_ALERT_DISPLAY_REC alertRec;
                    for (int i = 0; i < length; i++) {
                        alertRec = (OLC_ALERT_DISPLAY_REC) objArray[i];
                        AlertDTO alertDto = new AlertDTO(alertRec.getALERT_ID(), alertRec.getDISPLAY());
                        if (isDebugEnabled) {
                            logger.debug("Method Name = getAcctSummAlerts: i = " + i);
                            logger.debug("Method Name = getAcctSummAlerts: alertRec.getDisplay is "
                                    + alertDto.getAlertMsg());
                            logger.debug("Method Name = getAcctSummAlerts: getALERT_ID is "
                                    + alertDto.getAlertId());
                        }
                        alertDto.setAddressCleanseAlertType(alertDto.getAlertId() == ADDRESS_CLEANSE_ALERT_TYPE_ID);
                        result[i] = alertDto;
                    }
                }
            } else if (success == -1) {
                throw new EtccSecurityException("Security exception in getAlerts");
            } else {
                logger.error("getAlerts.success=" + success);
                logger.error("getAlerts.acctLoginDto=" + acctLoginDto);
                if (cstmt.getArray(idx) != null) {
                    logger.error("getAlerts.error="
                            + toErrorString(OracleUtil.convertToMessagesFromRecs(cstmt.getArray(idx))));
                }
            }

        } catch (SQLException sqle) {
            throw new EtccException("Error running getAlerts: " + sqle, sqle);
        } finally {
            close(cstmt);
        }

        return result;
    }

    public AlertDTO[] getContactInfoAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        final boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled)
            logger.debug("Method Name = getContactInfoAlerts");
        Collection<AlertDTO> result = null;
        CallableStatement cstmt = null;

        try {

            cstmt = this.conn.prepareCall("{? = call ACCT_ALERTS.Contact_Info_Alerts(?, ?, ?, ?, ?, ?, ?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_ACCOUNT_INFO_REC", OLC_ACCOUNT_INFO_REC.class);
            typeMap.put("OL_OWNER.OLC_ALERT_DISPLAY_REC", OLC_ALERT_DISPLAY_REC.class);

            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLoginTypeString());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            short success = cstmt.getShort(1);
            if (success == 1) {
                Object[] objArray = (Object[]) cstmt.getArray(idx - 1).getArray();
                if (objArray != null && objArray.length > 0) {
                    result = new ArrayList<AlertDTO>(objArray.length);
                    OLC_ALERT_DISPLAY_REC alertRec;
                    for (int i = 0; i < objArray.length; i++) {
                        alertRec = (OLC_ALERT_DISPLAY_REC) objArray[i];
                        AlertDTO alertDto = new AlertDTO(alertRec.getALERT_ID(), alertRec.getDISPLAY());
                        alertDto
                                .setAddressCleanseAlertType(alertDto.getAlertId() == ADDRESS_CLEANSE_ALERT_TYPE_ID);
                        result.add(alertDto);
                    }
                }
            } else if (success == -1) {
                throw new EtccSecurityException("Security exception in " + "getAlerts");
            } else {
                logger.error("getAlerts.success=" + success);
                logger.error("getAlerts.acctLoginDto=" + acctLoginDto);
                if (cstmt.getArray(idx) != null) {
                    logger.error("getAlerts.error="
                            + toErrorString(OracleUtil.convertToMessagesFromRecs(cstmt.getArray(idx))));
                }
            }
        } catch (SQLException sqle) {
            throw new EtccException("Error running getAlerts: " + sqle, sqle);
        } catch (RuntimeException e) {
            logger.error("Error running getAlerts: " + e.getMessage(), e);
            throw e;
        } finally {
            close(cstmt);
        }


        return result == null ? null : result.toArray(new AlertDTO[result.size()]);
    }

    public AlertDTO[] getVehicleInfoAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        final boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled)
            logger.debug("Method Name = getVehicleInfoAlerts");
        Collection<AlertDTO> result = null;
        /*CallableStatement cstmt = null;
        try {

            cstmt = this.conn.prepareCall("{? = call ACCT_ALERTS.Vehicle_Info_Alerts(?, ?, ?, ?, ?, ?, ?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_ACCOUNT_INFO_REC", OlcAccountInfoRec.class);
            typeMap.put("OL_OWNER.OLC_ALERT_DISPLAY_REC", OLC_ALERT_DISPLAY_REC.class);

            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLoginTypeString());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            short success = cstmt.getShort(1);
            if (success == 1) {
                Object[] objArray = (Object[]) cstmt.getArray(idx - 1).getArray();
                if (objArray != null && objArray.length > 0) {
                    result = new ArrayList<AlertDTO>(objArray.length);
                    OLC_ALERT_DISPLAY_REC alertRec;
                    for (int i = 0; i < objArray.length; i++) {
                        alertRec = (OLC_ALERT_DISPLAY_REC) objArray[i];
                        AlertDTO alertDto = new AlertDTO(alertRec.getALERT_ID(), alertRec.getDISPLAY());
                        alertDto.setAddressCleanseAlertType(alertDto.getAlertId() == ADDRESS_CLEANSE_ALERT_TYPE_ID);
                        result.add(alertDto);
                    }
                }
            } else if (success == -1) {
                throw new EtccSecurityException("Security exception in " + "getAlerts");
            } else {
                logger.error("getAlerts.success=" + success);
                logger.error("getAlerts.acctLoginDto=" + acctLoginDto);
                if (cstmt.getArray(idx) != null) {
                    logger.error("getAlerts.error="
                            + toErrorString(OracleUtil.convertToMessagesFromRecs(cstmt.getArray(idx))));
                }
            }
        } catch (SQLException sqle) {
            throw new EtccException("Error running getAlerts: " + sqle, sqle);
        } finally {
            close(cstmt);
        }*/

        return result == null ? null : result.toArray(new AlertDTO[result.size()]);
    }

    public ResultDTO addressCleanseAlertResponse(AccountLoginDTO accountLogin, String cleanseAlertResponse)
            throws EtccException, EtccSecurityException {
        CallableStatement cstmt = null;
        try {
            long acctId = accountLogin.getAcctId();
            String loginType = accountLogin.getLoginTypeString();
            String dbSessionId = accountLogin.getDbSessionId();
            String lastLoginIp = accountLogin.getLastLoginIp();
            String loginId = accountLogin.getLoginId();
//            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            if (logger.isInfoEnabled()) {
                logger.info("new ACCT_ALERTS.Address_Cleanse_Alert_Response(acctId=" + acctId + ", loginType="
                        + loginType + ", dbSessionId=" + dbSessionId + ", lastLoginIp=" + lastLoginIp + ", loginId="
                        + loginId + ", cleanseAlertResponse=" + cleanseAlertResponse + ", OLC_ERROR_MSG_ARR);");
            }
            @SuppressWarnings("unused")
            Map<String, Class<?>> typeMap = setTypeMap();

            // 1 2 3 4 5 6 7 8
            String theCall = "{? = call ACCT_ALERTS.Address_Cleanse_Alert_Response(?, ?, ?, ?, ?, ?, ?)}";
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
            cstmt.setString(idx, cleanseAlertResponse == null ? "" : cleanseAlertResponse);
            idx++;
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.execute();
            int result = cstmt.getInt(1);
            if (logger.isInfoEnabled())
                logger.info("ACCT_ALERTS.Address_Cleanse_Alert_Response: " + result);
            if (result == -1) {
                throw new EtccSecurityException("Security exception in " + "addressCleanseAlertResponse");
            } else if (result == 0) {
                Array errors = (Array) cstmt.getObject(idx);
                ResultDTO errorMsgCarrier = new ResultDTO();
                errorMsgCarrier.setErrors(OracleUtil.convertToMessages(errors));
                return errorMsgCarrier;
            } else {
                ResultDTO successCarrier = new ResultDTO();
                return successCarrier;
            }
        } catch (SQLException sqle) {
            throw new EtccException("Error running addressCleanseAlertResponse: " + sqle, sqle);
        } finally {
            close(cstmt);
        }
    }
}
