/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.TagDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagReplaceOrReactivateDTO;
import com.etcc.csc.plsql.OLCSC_ACCT_MGMT;
import com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR;
import com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_ARR;
import com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_REC;
import com.etcc.csc.plsql.OLC_ACCOUNT_TAG_REC;
import com.etcc.csc.plsql.OLC_ACCOUNT_VEHID_TAG_ARR;
import com.etcc.csc.plsql.OLC_ACCOUNT_VEHID_TAG_REC;
import com.etcc.csc.plsql.OLC_ACCOUNT_VEH_ID_ARR;
import com.etcc.csc.plsql.OLC_ACCOUNT_VEH_ID_REC;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;
import com.etcc.csc.plsql.OLC_TAG_AUTHORITIES_REC;
import com.etcc.csc.util.StringUtil;

/**
 * Oracle version of the TagRequestDAO.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
// Based on the original OracleTagReqyestDAO/OracleNewTagRequestDAO from OLCSCService.
public class OracleTagDAO extends TagDAO {
	
    private static final Logger logger = Logger.getLogger(OracleTagDAO.class);
    //TXDOT changes
    private static String bringYourOwnTagFlg = "BRING_YOUR_OWN_TAG_IS_ON";
    
    public TagDTO addTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId)
            throws EtccException, EtccSecurityException {
        String sessionId = acctLoginDTO.getDbSessionId();
        String ipAddress = acctLoginDTO.getLastLoginIp();
        String userId = acctLoginDTO.getLoginId();
        CallableStatement cstmt = null;
        try {
        	//TXDOT changes
        	String bringYourOwnTagisOn =  loadSysParam(bringYourOwnTagFlg);
            boolean debugEnabled = logger.isDebugEnabled();
            if (debugEnabled)
                logger.debug("tagDto before insert:" + tagRequestDto.toString());
            StringBuilder theStoredProcedureCall = new StringBuilder();
            setTypeMap();
            if ("Y".equals(bringYourOwnTagisOn)) {
            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Add_Tag_byot(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            }else {
            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Add_tag(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            }
            theStoredProcedureCall.append("OLCSC_ACCT_MGMT.Add_tag(");
            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setLong(2, tagRequestDto.getAcctId());
            theStoredProcedureCall.append("acctId=");
            theStoredProcedureCall.append(tagRequestDto.getAcctId());
            cstmt.setString(3, sessionId);
            theStoredProcedureCall.append(", sessionId=");
            theStoredProcedureCall.append(sessionId);
            cstmt.setString(4, ipAddress);
            theStoredProcedureCall.append(", ipAddress=");
            theStoredProcedureCall.append(ipAddress);
            cstmt.setString(5, userId);
            theStoredProcedureCall.append(", userId=");
            theStoredProcedureCall.append(userId);
            cstmt.setString(6, tagRequestDto.getLicPlate());
            theStoredProcedureCall.append(", licPlate=");
            theStoredProcedureCall.append(tagRequestDto.getLicPlate());
            cstmt.setString(7, tagRequestDto.getLicState());
            theStoredProcedureCall.append(", licState=");
            theStoredProcedureCall.append(tagRequestDto.getLicState());
            cstmt.setString(8, tagRequestDto.getVehicleYear());
            theStoredProcedureCall.append(", vehicleYear=");
            theStoredProcedureCall.append(tagRequestDto.getVehicleYear());
            cstmt.setString(9, tagRequestDto.getVehicleColor());
            theStoredProcedureCall.append(", vehicleColor=");
            theStoredProcedureCall.append(tagRequestDto.getVehicleColor());
            cstmt.setString(10, tagRequestDto.getVehicleMake());
            theStoredProcedureCall.append(", vehicleMake=");
            theStoredProcedureCall.append(tagRequestDto.getVehicleMake());
            cstmt.setString(11, tagRequestDto.getVehicleModel());
            theStoredProcedureCall.append(", vehicleModel=");
            theStoredProcedureCall.append(tagRequestDto.getVehicleModel());
            
            //Defect ID : 13021  OLCSC new account creation with motor cycle - fulfilment is creating with a car sticker
			//if (tagRequestDto.isMotorcycle()) {
				//cstmt.setString(12, "11");
			//} else {
			if (!StringUtil.isEmpty(tagRequestDto.getVehicleClassCode())) {
				cstmt.setString(12, tagRequestDto.getVehicleClassCode());
			} else {
				cstmt.setString(12, "2");
			}
			//}
            theStoredProcedureCall.append(", vehicleClassCode=");
            theStoredProcedureCall.append(tagRequestDto.getVehicleClassCode());
            cstmt.setString(13, StringUtil.booleanToString(tagRequestDto.isMotorcycle()));
            theStoredProcedureCall.append(", isMotorcycle=");
            theStoredProcedureCall.append(StringUtil.booleanToString(tagRequestDto.isMotorcycle()));

            // cstmt.setString(14, (tagRequestDto.isCheckDup())?"Y":"N");

            cstmt.setString(14, "N");
            theStoredProcedureCall.append(", checkDup=N");
            cstmt.setString(15, StringUtil.booleanToString(tagRequestDto.isTemporaryLicPlate()));
            theStoredProcedureCall.append(", temporaryLicPlate=");
           theStoredProcedureCall.append(StringUtil.booleanToString(tagRequestDto.isTemporaryLicPlate()));

            Calendar expires = tagRequestDto.getPlateExpireDate();
            Timestamp expirationDate = null;
            if (expires != null)
                expirationDate = new Timestamp(expires.getTimeInMillis());
            cstmt.setTimestamp(16, expirationDate); //
           theStoredProcedureCall.append(", expirationDate=");
           theStoredProcedureCall.append(expirationDate == null ? NULL_STRING : expirationDate.toString());
            if (debugEnabled)
                logger.debug("posId:" + posId);
            cstmt.setLong(17, (posId == null ? -1 : posId.longValue())); // pos id
           theStoredProcedureCall.append(", posId=");
           theStoredProcedureCall.append((posId == null ? -1 : posId.longValue()));
            if (tagRequestDto.getTransactionId() > 0){
             cstmt.setLong(18, tagRequestDto.getTransactionId()); // trans id
            }else{
             cstmt.setLong(18, 9999999999L); // trans id
            }
            theStoredProcedureCall.append(", transactionId=");
           theStoredProcedureCall.append(tagRequestDto.getTransactionId());
            cstmt.registerOutParameter(18, Types.INTEGER);

            /*
             * For "EZ plate", tagTypacode="P" pbpStart AND pbpEnd must be match this data
             * format:" MM/dd/yyyy HH:mm:ss  "
             */
            Date pbpStart = tagRequestDto.getPbpStartDate();
            Date pbpEnd = tagRequestDto.getPbpEndDate();
            /*if (StringUtil.isEmpty(tagRequestDto.getTagTypeCode())){
            	tagRequestDto.setTagTypeCode("P");
            }*/
            if (debugEnabled)
                logger.debug("addTag.tagRequestDto.getTagTypeCode()=" + tagRequestDto.getTagTypeCode());
            theStoredProcedureCall.append(", tagTypeCode=");
            if ("P".equalsIgnoreCase(tagRequestDto.getTagTypeCode())) {
                cstmt.setString(19, "P"); // EZ Plate. Pay by Plate. No Tags
                theStoredProcedureCall.append("P");
            } else {
                cstmt.setString(19, "ER"); // tag type
                theStoredProcedureCall.append("ER");
            }
            theStoredProcedureCall.append(", pbpStart=");
            if (pbpStart != null && pbpStart.getTime() > 0) {
                cstmt.setTimestamp(20, new Timestamp(pbpStart.getTime()));
                theStoredProcedureCall.append(pbpStart);
            } else {
                cstmt.setNull(20, Types.TIMESTAMP);
                theStoredProcedureCall.append(NULL_STRING);
            }
           theStoredProcedureCall.append(", pbpEnd=");
            if (pbpEnd != null && pbpEnd.getTime() > 0) {
                cstmt.setTimestamp(21, new Timestamp(pbpEnd.getTime()));
               theStoredProcedureCall.append(pbpEnd);
            } else {
                cstmt.setNull(21, Types.TIMESTAMP);
               theStoredProcedureCall.append(NULL_STRING);
            }
            cstmt.setString(22, tagRequestDto.getNickname());
            theStoredProcedureCall.append(", nickname=");
           theStoredProcedureCall.append(tagRequestDto.getNickname());
            if (debugEnabled)
                logger.debug("transId before add tag " + tagRequestDto.getLicPlate() + " is "
                        + tagRequestDto.getTransactionId());
            cstmt.registerOutParameter(23, Types.DOUBLE); // tag sales amount
            cstmt.registerOutParameter(24, Types.DOUBLE); // min bal amount
            cstmt.registerOutParameter(25, Types.DOUBLE); // total payment amount
            cstmt.registerOutParameter(26, Types.DOUBLE); // tag charge
            String tagSeqIn = Long.toString(tagRequestDto.getAcctTagSeq());
            if (tagRequestDto.getAcctTagSeq() == -1)
                tagSeqIn = "";
            if (debugEnabled)
                logger.debug("addTag.tagSeqIn=\"" + tagSeqIn + "\"");
            cstmt.setString(27, tagSeqIn); // inout parameter
            theStoredProcedureCall.append(", tagSeqIn=");
            theStoredProcedureCall.append(tagSeqIn);
            cstmt.registerOutParameter(27, Types.VARCHAR); // tag seq
            cstmt.registerOutParameter(28, Types.VARCHAR); // dup flag
            cstmt.registerOutParameter(29, Types.VARCHAR); // has violation
            // cstmt.registerOutParameter(30, Types.ARRAY,
            // "OL_OWNER.OLC_ERROR_MSG_ARR"); //Error msg

            cstmt.registerOutParameter(30, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR"); // Alert msg
            cstmt.registerOutParameter(31, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR"); // Error msg
            //TXDOT changes
            if ("Y".equals(bringYourOwnTagisOn)) {
            	if (!StringUtil.isEmpty(tagRequestDto.getTagPrefix())) {
            	 cstmt.setString(32, tagRequestDto.getTagPrefix()); 
            	 theStoredProcedureCall.append(tagRequestDto.getTagPrefix());
            	}else {
                 cstmt.setNull(32,Types.VARCHAR);
            	}
            	if (!StringUtil.isEmpty(tagRequestDto.getTagId())) {
                 cstmt.setString(33, tagRequestDto.getTagId());
                 theStoredProcedureCall.append(tagRequestDto.getTagId());
            	}else {
            	 cstmt.setNull(33,Types.VARCHAR);
            	}
            	cstmt.registerOutParameter(34, Types.INTEGER);
            }
            theStoredProcedureCall.append(")");
            if (debugEnabled)
                logger.debug(theStoredProcedureCall.toString());
            cstmt.execute();

            int result = cstmt.getInt(1);
            if (result == -1) {
                logger.info("security exception: addTag");
                throw new EtccSecurityException("Our system detected a security threat");
                // throw new EtccSecurityException("security exception in addTag");
            } else if (result == 0) {
                if (debugEnabled) {
                    logger.debug("addTag.result=" + result);
                    logger.debug("addTag.sessionId=" + sessionId + ";ipAddress=" + ipAddress + ";userId=" + userId
                            + ";posId" + posId);
                }
                Array alerts = (Array) cstmt.getObject(30);
                Array errors = (Array) cstmt.getObject(31);
                String hasViolation = cstmt.getString(29);
                if (debugEnabled)
                    logger.debug("addTag.hasViolation=" + hasViolation);
                tagRequestDto.setViolationExist(StringUtil.stringToBoolean(hasViolation));
                // If 0 is returned then may not be any error message
                // However, if 0 is returned then there must be either error messages or alerts
                // The way the web service user knows that the web service failed is that the errors has at least one
                // element
               setMessages(tagRequestDto, errors, alerts, "Vehicle add failed.");
                //setMessages(tagRequestDto, errors, alerts, "");
                return tagRequestDto;
            }

            tagRequestDto.setAcctVehicleId(cstmt.getLong(18));

            String tagSeq = cstmt.getString(27);
            if (tagSeq != null)
                tagRequestDto.setAcctTagSeq(Long.parseLong(tagSeq));

            String dupFlag = cstmt.getString(28);
            tagRequestDto.setDup(StringUtil.stringToBoolean(dupFlag));

            String hasViolation = cstmt.getString(29);
            tagRequestDto.setViolationExist(StringUtil.stringToBoolean(hasViolation));

            double tagAmount = cstmt.getDouble(26);
            double tagSalesAmount = cstmt.getDouble(23);
            double depositAmount = cstmt.getDouble(24);
            double totalSalesAmount = cstmt.getDouble(25);
            if (debugEnabled)
                logger.debug("after add tags==>hasViolation: " + hasViolation + ", tagAmount: " + tagAmount
                        + ", tagSalesAmount: " + tagSalesAmount + ", depositAmount: " + depositAmount
                        + ", totalSalesAmount: " + totalSalesAmount);
            tagRequestDto.setTagSalesAmt(tagSalesAmount);
            tagRequestDto.setDepositAmt(depositAmount);
            tagRequestDto.setTotalAmt(totalSalesAmount);
            tagRequestDto.setTagAmount(tagAmount);
            tagRequestDto.setAlerts(convertAlertMsgs((Array) cstmt.getObject(30)));
            //TXDOT changes
            if ("Y".equals(bringYourOwnTagisOn)) {
            	tagRequestDto.setAccountTagId(cstmt.getLong(34));
            }
            if (debugEnabled)
                logger.debug("tagDTO after insert: " + tagRequestDto);

        } catch (SQLException e) {
            throw new EtccException("Error adding tag: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }

        return tagRequestDto;
    }

    @Override
    protected TagDTO modifyTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, TransactionType transType, Long posId) throws EtccException {
        String sessionId = acctLoginDTO.getDbSessionId();
        String ipAddress = acctLoginDTO.getLastLoginIp();
        String userId = acctLoginDTO.getLoginId();
        int acctVehicleIdx = 0;
        CallableStatement cstmt = null;
        try {
        	//TXDOT changes
        	String bringYourOwnTagisOn =  loadSysParam(bringYourOwnTagFlg);
            setTypeMap();
            StringBuilder sb = new StringBuilder(1024);
            // 1 2 3
            // 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2
          //TXDOT changes
            if ("Y".equals(bringYourOwnTagisOn)) {
            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Modify_Tag_byot(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            }else {
            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Modify_tag(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            }
            sb.append("OLCSC_ACCT_MGMT.Modify_tag(");
            int paramIdx = 1;
            cstmt.registerOutParameter(paramIdx, Types.SMALLINT);
            paramIdx++;
            cstmt.setLong(paramIdx, tagRequestDto.getAcctId()); // p_acct_id NUMBER
            sb.append("AcctId(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getAcctId());
            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getAcctTagSeq() + ""); // p_tag_seq VARCHAR2
            sb.append(", AcctTagSeq(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getAcctTagSeq());
            paramIdx++;

            cstmt.setLong(paramIdx, tagRequestDto.getAcctVehicleId()); // vehicle transaction id
            sb.append(", account vehicle id(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getAcctVehicleId());
            acctVehicleIdx = paramIdx;
            cstmt.registerOutParameter(paramIdx, Types.NUMERIC);
            paramIdx++;

            cstmt.setString(paramIdx, sessionId); // p_session VARCHAR2
            sb.append(", sessionId(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(sessionId);
            paramIdx++;
            cstmt.setString(paramIdx, ipAddress); // p_ip_address VARCHAR2
            sb.append(", ipAddress(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(ipAddress);
            paramIdx++;
            cstmt.setString(paramIdx, userId); // p_user VARCHAR2
            sb.append(", userId(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(userId);
            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getLicPlate()); // p_lic_plate VARCHAR2
            sb.append(", licPlate(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getLicPlate());
            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getLicState()); // p_lic_state VARCHAR2
            sb.append(", licState(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getLicState());
            paramIdx++;
            String motorCycleValue = StringUtil.booleanToString(tagRequestDto.isMotorcycle());
            cstmt.setString(paramIdx, motorCycleValue); // p_motorcycle_flag varchar2 default null
            sb.append(", motorCycleValue(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(motorCycleValue);
            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getVehicleYear()); // p_year VARCHAR2
            sb.append(", vehicleYear(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getVehicleYear());
            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getVehicleColor()); // p_color VARCHAR2
            sb.append(", vehicleColor(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getVehicleColor());
            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getVehicleMake()); // p_vehicle_make VARCHAR2
            sb.append(", vehicleMake(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getVehicleMake());
            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getVehicleModel()); // p_vehicle_model VARCHAR2
            sb.append(", vehicleModel(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getVehicleModel());
            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getVehicleClassCode()); // p_vehicle_class VARCHAR2
            sb.append(", vehicleClassCode(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getVehicleClassCode());
            paramIdx++;
            String checkDupValue = StringUtil.booleanToString(tagRequestDto.isCheckDup());
            cstmt.setString(paramIdx, checkDupValue); // p_check_dup VARCHAR2
            sb.append(", checkDupValue(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(checkDupValue);
            paramIdx++;
            String temporaryLicPlateValue = StringUtil.booleanToString(tagRequestDto.isTemporaryLicPlate());
            cstmt.setString(paramIdx, temporaryLicPlateValue); // p_temp_plate VARCHAR2
            sb.append(", temporaryLicPlateValue(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(temporaryLicPlateValue);
            Calendar expires = tagRequestDto.getPlateExpireDate();
            Timestamp expirationDate = null;
            if (expires != null)
                expirationDate = new Timestamp(expires.getTimeInMillis());
            paramIdx++;
            cstmt.setTimestamp(paramIdx, expirationDate); // p_temp_date DATE
            sb.append(", expirationDate(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(expirationDate);
            paramIdx++;
            cstmt.setString(paramIdx, transType.getCode().substring(0,1)); // p_trantype_flag VARCHAR2
            sb.append(", transType(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(transType);
            paramIdx++;
            long posIdValue = (posId == null ? -1 : posId.longValue());
            cstmt.setLong(paramIdx, posIdValue); // P_POS_ID NUMBER DEFAULT -1
            sb.append(", posIdValue(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(posIdValue);
            paramIdx++;
            if (tagRequestDto.getTransactionId() != -1) { // p_retail_trans_id retail_transactions.retail_trans_id%type
                cstmt.setLong(paramIdx, tagRequestDto.getTransactionId());
                sb.append(", TransactionId(");
                sb.append(paramIdx);
                sb.append(") = ");
                sb.append(tagRequestDto.getTransactionId());
            } else {
                cstmt.setNull(paramIdx, Types.INTEGER);
                sb.append(", TransactionId(");
                sb.append(paramIdx);
                sb.append(") = ");
                sb.append(NULL_STRING);
            }
            // For "EZ plate", tagTypeCode="P"
            // pbpStart AND pbpEnd must be match this data format:" MM/dd/yyyy HH:mm:ss  "
            Date pbpStart = tagRequestDto.getPbpStartDate();
            paramIdx++;
            if (pbpStart != null && pbpStart.getTime() > 0) { // p_pbp_start_date IN date default null
                Timestamp pbpStartValue = new Timestamp(pbpStart.getTime());
                cstmt.setTimestamp(paramIdx, pbpStartValue);
                sb.append(", pbpStartValue(");
                sb.append(paramIdx);
                sb.append(") = ");
                sb.append(pbpStartValue);
            } else {
                cstmt.setNull(paramIdx, Types.TIMESTAMP);
                sb.append(", pbpStartValue(");
                sb.append(paramIdx);
                sb.append(") = ");
                sb.append(NULL_STRING);
            }
            Date pbpEnd = tagRequestDto.getPbpEndDate();
            paramIdx++;
            if (pbpEnd != null && pbpEnd.getTime() > 0) { // p_pbp_end_date IN date default null
                Timestamp pbpEndValue = new Timestamp(pbpEnd.getTime());
                cstmt.setTimestamp(paramIdx, pbpEndValue);
                sb.append(", pbpEndValue(");
                sb.append(paramIdx);
                sb.append(") = ");
                sb.append(pbpEndValue);
            } else {
                cstmt.setNull(paramIdx, Types.TIMESTAMP);
                sb.append(", pbpEndValue(");
                sb.append(paramIdx);
                sb.append(") = ");
                sb.append(NULL_STRING);
            }
            paramIdx++;
            String tagTypeCode = null;
            // if the type code is null, the DB will not overwrite and update (so if not selected, no assumption should be made about the type code)
            if (tagRequestDto.getTagTypeCode() != null) {
                if (tagRequestDto.getTagTypeCode().equalsIgnoreCase("P")) { // p_tag_type IN tag_types.tag_type_code%type
                    tagTypeCode = "P"; // EZ Plate. Pay by Plate. No Tags
                } else if (tagRequestDto.isMotorcycle()) {
                    tagTypeCode = "EM"; // Tag type, Motorcycle
                } else {
                    //TODO: if the only valid values are "P", "EM", "ER" & "null", shouldn't it be validated and not just default to "ER"
                    tagTypeCode = "ER";
                }
            } else if (tagRequestDto.isMotorcycle()) {
                tagTypeCode = "EM"; // Tag type, Motorcycle
            }
            sb.append(", TagTypeCode(");
            sb.append(paramIdx);
            sb.append(") = ");
            if (tagTypeCode == null) {
                cstmt.setNull(paramIdx, Types.VARCHAR);
                sb.append(NULL_STRING);
            } else {
                cstmt.setString(paramIdx, tagTypeCode);
                sb.append(tagTypeCode);
            }
            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getNickname()); // p_nickname in account_tags.nickname%type default
                                                                    // null
            sb.append(", Nickname(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getNickname());

            paramIdx++;
            cstmt.setString(paramIdx, tagRequestDto.getShowContinueMsg()); // this should fix the issue with edit screen and new alert message RFC 20140252
            sb.append(", ShowContinueMsg(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(tagRequestDto.getShowContinueMsg());

            paramIdx++;
            String acctTagStatusValue = tagRequestDto.getAcctTagStatus();
            if (acctTagStatusValue == null)
                acctTagStatusValue = "";
            cstmt.setString(paramIdx, acctTagStatusValue); // p_acct_tag_status in account_tags.acct_tag_status%type
            sb.append(", acctTagStatusValue(");
            sb.append(paramIdx);
            sb.append(") = ");
            sb.append(acctTagStatusValue);
            paramIdx++;
            cstmt.registerOutParameter(paramIdx, Types.VARCHAR); // p_active_tags_ind out varchar2: close account?
            int activeTagsIndParamIdx = paramIdx;
            paramIdx++;
            cstmt.registerOutParameter(paramIdx, Types.DOUBLE); // tag sales amount // p_tag_sales_amt out number
            int tagSalesAmountParamIdx = paramIdx;
            paramIdx++;
            cstmt.registerOutParameter(paramIdx, Types.DOUBLE); // deposit amount // p_min_bal_amt out number
            int depositAmountParamIdx = paramIdx;
            paramIdx++;
            cstmt.registerOutParameter(paramIdx, Types.DOUBLE); // total sales amount // p_pmt_amt out number
            int totalSalesAmountParamIdx = paramIdx;
            paramIdx++;
            cstmt.registerOutParameter(paramIdx, Types.VARCHAR); // dup flag // o_dup_flag OUT VARCHAR2
            int dupFlagParamIdx = paramIdx;
            paramIdx++;
            cstmt.registerOutParameter(paramIdx, Types.VARCHAR); // has violation // o_has_violations OUT VARCHAR2
            int hasViolationParamIdx = paramIdx;
            paramIdx++;
            cstmt.registerOutParameter(paramIdx, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR"); // Alert msg //
                                                                                                  // o_Alerts OUT
                                                                                                  // OLC_ALERT_DISPLAY_ARR
            int alertsParamIdx = paramIdx;
            paramIdx++;
            cstmt.registerOutParameter(paramIdx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR"); // o_error_msg_arr OUT
                                                                                              // olc_error_msg_arr
            int errorsParamIdx = paramIdx;
          //TXDOT changes
            if ("Y".equals(bringYourOwnTagisOn)) {
            	 paramIdx++;
            	if (!StringUtil.isEmpty(tagRequestDto.getTagPrefix())) {
            	 cstmt.setString(paramIdx, tagRequestDto.getTagPrefix()); 
            	 sb.append(tagRequestDto.getTagPrefix());
            	}else {
                 cstmt.setNull(paramIdx,Types.VARCHAR);
            	}
            	 paramIdx++;
            	if (!StringUtil.isEmpty(tagRequestDto.getTagId())) {
                 cstmt.setString(paramIdx, tagRequestDto.getTagId());
                 sb.append(tagRequestDto.getTagId());
            	}else {
            	 cstmt.setNull(paramIdx,Types.VARCHAR);
            	}
            	paramIdx++;
            	if (tagRequestDto.getAccountTagId() > 0 ) {
                 cstmt.setLong(paramIdx, tagRequestDto.getAccountTagId());
                 sb.append(tagRequestDto.getAccountTagId());
            	}else {
            	 cstmt.setNull(paramIdx,Types.INTEGER);
               }
               cstmt.registerOutParameter(paramIdx, Types.INTEGER);
            }
            sb.append(")");
            boolean debugEnabled = logger.isDebugEnabled();
            if (debugEnabled)
                logger.debug("modifyTag: " + sb.toString());
            cstmt.execute();
            int result = cstmt.getInt(1);
            if (logger.isInfoEnabled())
                logger.info("modifyTag.result=" + result);
            if (result == -1) {
                throw new EtccSecurityException(
                        "Not allowed to add second ez-plate tag to the same active account by addTag");
                // throw new EtccSecurityException("security exception in modifyTag");
            } else if (result == 0) {
                // If 0 is returned then may not be any error message
                // However, if 0 is returned then there must be either error messages or alerts
                // The way the web service user knows that the web service failed is that the errors has at least one
                // element
                Array errors = (Array) cstmt.getObject(errorsParamIdx);
                Array alerts = (Array) cstmt.getObject(alertsParamIdx);
                String hasViolation = cstmt.getString(hasViolationParamIdx);
                tagRequestDto.setViolationExist(StringUtil.stringToBoolean(hasViolation));
                setMessages(tagRequestDto, errors, alerts, "");
                return tagRequestDto;
            }
            String dupFlag = cstmt.getString(dupFlagParamIdx);
            tagRequestDto.setDup(StringUtil.stringToBoolean(dupFlag));
            String hasViolation = cstmt.getString(hasViolationParamIdx);
            tagRequestDto.setViolationExist(StringUtil.stringToBoolean(hasViolation));
            double tagSaleAmt = cstmt.getDouble(tagSalesAmountParamIdx);
            double depositAmt = cstmt.getDouble(depositAmountParamIdx);
            double totalAmt = cstmt.getDouble(totalSalesAmountParamIdx);
            long newAcctVehicleId = cstmt.getLong(acctVehicleIdx);
            tagRequestDto.setAcctVehicleId(newAcctVehicleId);
            tagRequestDto.setTagSalesAmt(tagSaleAmt);
            tagRequestDto.setDepositAmt(depositAmt);
            tagRequestDto.setTotalAmt(totalAmt);
            tagRequestDto.setAcctTagSeq(newAcctVehicleId);
            tagRequestDto.setAlerts(convertAlertMsgs((Array) cstmt.getObject(alertsParamIdx)));
            String promptAcctClose = cstmt.getString(activeTagsIndParamIdx);
            if ("Y".equals(promptAcctClose)) { // the active tag indicator needs to be negated
                promptAcctClose = "N";
            } else if ("N".equals(promptAcctClose)) {
                promptAcctClose = "Y";
            }
            tagRequestDto.setPromptAcctClose(promptAcctClose);
            //TXDOT changes
            if ("Y".equals(bringYourOwnTagisOn)) {
            	tagRequestDto.setAccountTagId(cstmt.getLong(38));
            }
            if (debugEnabled) {
                logger.debug("modify tag==>after update: " + tagRequestDto.toString());
                logger.debug("modify  tags==>hasViolation: " + hasViolation + ", tagSalesAmount: " + tagSaleAmt
                        + ", depositAmount: " + depositAmt + ", totalSalesAmount: " + totalAmt);
            }
        } catch (SQLException e) {
            throw new EtccException("Error modifying tag: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }
        return tagRequestDto;
    }

    public TagDTO confirmAddTags(AccountLoginDTO acctLoginDTO, long transactionId, TagDTO.DeliveryMethod deliveryMethod, List<TagDTO> tagDtos) throws EtccException, EtccSecurityException {
        String sessionId = acctLoginDTO.getDbSessionId();
        String ipAddress = acctLoginDTO.getLastLoginIp();
        String userId = acctLoginDTO.getLoginId();
        String acctId = Long.toString(acctLoginDTO.getAcctId());
        TagDTO resultDTO = null;
        String deliverMode =null;
        // TODO: (IDEA) deliveryMethod - temporary fix must be passed as parameter
//        String deliveryMethod = deliverMethod;// "MAIL"; // or PICKUP, // l_delivery_method not in ('MAIL','PICKUP')
        // if ((deliveryMethod == null) || (deliveryMethod.length() == 0)) // The deliveryMethod is empty if EZ Plates
        // are the only tags to be confirmed
        // deliveryMethod = OracleNewTagRequestDAO.DELIVERY_METHOD_PICKUP; // defaults to PICKUP
        // The web tier defaults to MAIL in case of EZ Plate only tag orders

        try {
            if (logger.isDebugEnabled())
                logger.debug("confirmAddTags.deliveryMethod=" + deliveryMethod);
          //TXDOT changes
        	String bringYourOwnTagisOn =  loadSysParam(bringYourOwnTagFlg);
        	
            OLC_ERROR_MSG_ARR[] errors = new OLC_ERROR_MSG_ARR[]{new OLC_ERROR_MSG_ARR()};
            BigDecimal[] fulfilmentIds = new BigDecimal[1];
            OLC_ACCOUNT_VEH_ID_REC[] recs = new OLC_ACCOUNT_VEH_ID_REC[tagDtos.size()];
            OLC_ACCOUNT_VEHID_TAG_REC[] tagRecs = new OLC_ACCOUNT_VEHID_TAG_REC[tagDtos.size()];
            if (tagDtos != null)
        	{
            	int i = 0;
            	for (TagDTO tagDTO : tagDtos)
            	{	//TXDOT changes
            	   if ("Y".equals(bringYourOwnTagisOn)) {
            		   OLC_ACCOUNT_VEHID_TAG_REC olc_ACCOUNT_VEHID_TAG_REC = new OLC_ACCOUNT_VEHID_TAG_REC();
            		   olc_ACCOUNT_VEHID_TAG_REC.setACCT_VEHICLE_ID(new BigDecimal(tagDTO.getAcctVehicleId()));
            		   olc_ACCOUNT_VEHID_TAG_REC.setMOTOCYCLE_FLAG(tagDTO.isMotorcycle()?"Y":"N");
            		   olc_ACCOUNT_VEHID_TAG_REC.setNO_TAG_FLAG(tagDTO.getNoTagFlag()!=null?tagDTO.getNoTagFlag():"N");
            		   if(tagDTO.getAccountTagId()>0) {
            		    olc_ACCOUNT_VEHID_TAG_REC.setACCOUNT_TAG_ID(new BigDecimal(tagDTO.getAccountTagId()));
            		   }
            		   tagRecs[i++] = olc_ACCOUNT_VEHID_TAG_REC;
            		   olc_ACCOUNT_VEHID_TAG_REC.toDatum(conn);
            		}else {
	            		OLC_ACCOUNT_VEH_ID_REC olc_ACCOUNT_VEH_ID_REC = new OLC_ACCOUNT_VEH_ID_REC();
	            		olc_ACCOUNT_VEH_ID_REC.setACCT_VEHICLE_ID(new BigDecimal(tagDTO.getAcctVehicleId()));
	            		olc_ACCOUNT_VEH_ID_REC.setMOTOCYCLE_FLAG(tagDTO.isMotorcycle()?"Y":"N");
	            		//Flex Pay Changes
	            		olc_ACCOUNT_VEH_ID_REC.setNO_TAG_FLAG(tagDTO.getNoTagFlag()!=null?tagDTO.getNoTagFlag():"N");
						recs[i++] = olc_ACCOUNT_VEH_ID_REC;
						olc_ACCOUNT_VEH_ID_REC.toDatum(conn);
            		}
            	}
        	}
            //ExpressAccount changes
	         if (deliveryMethod!=null){
	        	 deliverMode = deliveryMethod.toString();
	         }
	        int result =0;
	        if ("Y".equals(bringYourOwnTagisOn)) {
	        	OLC_ACCOUNT_VEHID_TAG_ARR tagRec = new OLC_ACCOUNT_VEHID_TAG_ARR(tagRecs);
	        	OLC_ACCOUNT_VEHID_TAG_ARR[] acctTagVehicleIds =new OLC_ACCOUNT_VEHID_TAG_ARR[]{tagRec};
	        	result = new OLCSC_ACCT_MGMT(conn).CONFIRM_ADD_TAGS_BYOT(acctLoginDTO.getAcctId()+"",
	            		AccountLoginDTO.LoginType.AC.toString(), sessionId, ipAddress, userId, fulfilmentIds,
	            		acctTagVehicleIds, deliverMode, errors).intValue();
    			 
     		}else {
	            OLC_ACCOUNT_VEH_ID_ARR rec = new OLC_ACCOUNT_VEH_ID_ARR(recs);
	            //System.out.println(rec.toString());
				OLC_ACCOUNT_VEH_ID_ARR[] acctVehicleIds = new OLC_ACCOUNT_VEH_ID_ARR[]{rec};
	              result = new OLCSC_ACCT_MGMT(conn).CONFIRM_ADD_TAGS(acctLoginDTO.getAcctId()+"",
	            		AccountLoginDTO.LoginType.AC.toString(), sessionId, ipAddress, userId, fulfilmentIds,
	            		acctVehicleIds, deliverMode, errors).intValue();
     		}

            boolean infoEnabled = logger.isInfoEnabled();
            if (infoEnabled)
                logger.info("confirmAddTags==>transId:" + transactionId);

            if (infoEnabled)
                logger.info("confirmAddTags.result=" + result);
            if (result == -1) {
                logger.info("security exception: confirmAddTags");
                throw new EtccSecurityException("security exception in confirmAddTags");
            }
            resultDTO = new TagDTO();
            if(fulfilmentIds!=null && fulfilmentIds.length>0 && fulfilmentIds[0]!=null)
            	resultDTO.setTransactionId(fulfilmentIds[0].longValue());
            if (result == 0) {
                logger.info("Error in confirmAddTags");
                resultDTO.setErrors(OracleUtil.convertToMessages(errors));
            }
            return resultDTO;
        } catch (SQLException e) {
            throw new EtccException("Error in confirmAddTags: " + e.getMessage(), e);
        }
    }

    public String addTagsReceipt(AccountLoginDTO acctLoginDTO, String reportFormat)
            throws EtccException, EtccSecurityException {
        String sessionId = acctLoginDTO.getDbSessionId();
        String ipAddress = acctLoginDTO.getLastLoginIp();
        String userId = acctLoginDTO.getLoginId();
        long acctId = acctLoginDTO.getAcctId();
        CallableStatement cstmt = null;
        try {
            setTypeMap();
            cstmt = this.conn.prepareCall("{? = call OLCSC_REP.Add_Tag_Receipt_RPT(?,?,?,?,?,?,?,?)}");
            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, reportFormat);
            cstmt.setLong(3, acctId);
            cstmt.setString(4, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(5, sessionId);
            cstmt.setString(6, ipAddress);
            cstmt.setString(7, userId);
            cstmt.registerOutParameter(8, Types.VARCHAR);
            cstmt.registerOutParameter(9, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            int result = cstmt.getInt(1);

            if (result == -1) {
                logger.debug("security exception: addTagsReceipt");
                throw new EtccSecurityException("security exception in addTagsReceipt");
            } else if (result == 0) {
                logger.debug("Error in addTagsReceipt");
                Array errors = (Array) cstmt.getObject(9);
                logger.warn("Oracle Exceptions: " + Arrays.toString((Object[]) errors.getArray()));
                // convertErrorMsgs(errors);
                return null;
            }
            String url = cstmt.getString(8);
            return url;

        } catch (SQLException e) {
            throw new EtccException("Error in addTagsReceipt: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }
    }

    public AccountTagsDTO getAccountTags(AccountLoginDTO acctLoginDTO, String searchValue) throws EtccException,
            EtccSecurityException {

        AccountTagsDTO resultDTO = null;
        try {
        	String bringYourOwnTagisOn =  loadSysParam(bringYourOwnTagFlg);
            final boolean debugEnabled = logger.isDebugEnabled();
            if (debugEnabled) {
                logger.debug("getAcctTags.acctLoginDTO=" + acctLoginDTO);
                logger.debug("getAcctTags.searchValue=" + searchValue);
            }
            String dbSession = acctLoginDTO.getDbSessionId();
            BigDecimal acctId = new BigDecimal(acctLoginDTO.getAcctId());
            String user = acctLoginDTO.getLoginId();
            String ipAddress = acctLoginDTO.getLastLoginIp();

            OLC_ACCOUNT_TAG_ARR[] O_ACCOUNT_TAG_ARR = new OLC_ACCOUNT_TAG_ARR[] { new OLC_ACCOUNT_TAG_ARR() };
            OLC_ACCOUNT_TAG_ARR[] O_LIC_PLATE_TAG_ARR = new OLC_ACCOUNT_TAG_ARR[] { new OLC_ACCOUNT_TAG_ARR() };
            //TxDOt changes
            OLC_ACCOUNT_TAG_LICPLATE_ARR[] O_LIC_ACCOUNT_TAG_ARR = new OLC_ACCOUNT_TAG_LICPLATE_ARR[] { new OLC_ACCOUNT_TAG_LICPLATE_ARR()};
            OLC_ACCOUNT_TAG_LICPLATE_ARR[] O_LIC_LICPLATE_TAG_ARR = new OLC_ACCOUNT_TAG_LICPLATE_ARR[] { new OLC_ACCOUNT_TAG_LICPLATE_ARR()};
            
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            if (debugEnabled)
                logger.debug("new OLCSC_ACCT_MGMT(conn).GET_TAG_INFO(dbSession=" + dbSession + ", acctId=" + acctId
                        + ", user=" + user + ", ipAddress=" + ipAddress + ", searchValue=" + searchValue
                        + ", O_ACCOUNT_TAG_ARR/O_LIC_ACCOUNT_TAG_ARR, O_LIC_PLATE_TAG_ARR/O_LIC_LICPLATE_TAG_ARR, true, O_ERROR_MSG_ARR).intValue();");
            //txDot changes
            int result =0;
            if ("Y".equals(bringYourOwnTagisOn)) {
            	result = new OLCSC_ACCT_MGMT(this.conn).GET_TAG_INFO_BYOT(dbSession, acctId, user, ipAddress, searchValue,
            			O_LIC_ACCOUNT_TAG_ARR, O_LIC_LICPLATE_TAG_ARR, true, O_ERROR_MSG_ARR).intValue();
            }else {
                result = new OLCSC_ACCT_MGMT(this.conn).GET_TAG_INFO(dbSession, acctId, user, ipAddress, searchValue,
                    O_ACCOUNT_TAG_ARR, O_LIC_PLATE_TAG_ARR, true, O_ERROR_MSG_ARR).intValue();
            }
            if (logger.isInfoEnabled())
                logger.info("getAcctTags.result is " + result);
            if (result == -1) {
                throw new EtccSecurityException("security exception in OracleTagRequestDAO.getAccountTags(), result="
                        + result);
            }
            resultDTO = new AccountTagsDTO();
            if (result == 0) {
                resultDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                if (debugEnabled) {
                    OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                    for (int i = 0; i < errorArray.length; i++) {
                        logger.debug("getAccountTags==>errorArray[" + i + "]=" + errorArray[i].getERROR_MSG());
                    }
                }
            } else {
            	 //TxDot changes
            	if ("Y".equals(bringYourOwnTagisOn)) {
            		OLC_ACCOUNT_TAG_LICPLATE_REC[] tagRecs = O_LIC_ACCOUNT_TAG_ARR[0].getArray();
            		OLC_ACCOUNT_TAG_LICPLATE_REC[] plateTagRecs = O_LIC_LICPLATE_TAG_ARR[0].getArray();
    	                if (tagRecs != null) {
    	                    resultDTO.setAccountTags(convertTagPlateRecs(tagRecs, 0));
    	                } else {
    	                    logger.info("getAccountTags.tagRecs=null");
    	                }
    	                if (plateTagRecs != null) {
    	                    int startIdx = 0;
    	                    if (tagRecs != null) {
    	                        startIdx = tagRecs.length;
    	                    }
    	                    resultDTO.setPbpTags(convertTagPlateRecs(plateTagRecs, startIdx));
    	                } else {
    	                    logger.info("getAccountTags.plateTagRecs=null");
    	                }
    	                if (debugEnabled)
    	                    logger.debug("getAcctTags.resultDTO=" + resultDTO);
            	}else {
	                OLC_ACCOUNT_TAG_REC[] tagRecs = O_ACCOUNT_TAG_ARR[0].getArray();
	                OLC_ACCOUNT_TAG_REC[] plateTagRecs = O_LIC_PLATE_TAG_ARR[0].getArray();
		                if (tagRecs != null) {
		                    resultDTO.setAccountTags(convertTagRecs(tagRecs, 0));
		                } else {
		                    logger.info("getAccountTags.tagRecs=null");
		                }
		                if (plateTagRecs != null) {
		                    int startIdx = 0;
		                    if (tagRecs != null) {
		                        startIdx = tagRecs.length;
		                    }
		                    resultDTO.setPbpTags(convertTagRecs(plateTagRecs, startIdx));
		                } else {
		                    logger.info("getAccountTags.plateTagRecs=null");
		                }
		                if (debugEnabled)
		                    logger.debug("getAcctTags.resultDTO=" + resultDTO);
		       }
           }
        } catch (SQLException e) {
            throw new EtccException("getAccountTags failed: " + e.getMessage(), e);
        }
        return resultDTO;
    }

    public TagDTO getTagBySeqNum(AccountLoginDTO acctLoginDTO, String seqNumber) throws EtccException,
            EtccSecurityException {
        TagDTO resultDTO = null;
        try {
            String dbSession = acctLoginDTO.getDbSessionId();
            BigDecimal acctId = new BigDecimal(acctLoginDTO.getAcctId());
            String user = acctLoginDTO.getLoginId();
            String ipAddress = acctLoginDTO.getLastLoginIp();
            BigDecimal seq = new BigDecimal(Integer.parseInt(seqNumber));
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            OLC_ACCOUNT_TAG_REC[] O_OLC_ACCOUNT_TAG_REC = new OLC_ACCOUNT_TAG_REC[] { new OLC_ACCOUNT_TAG_REC() };
            /*int result = new OLCSC_REP(this.conn).GET_TEMP_TAG_INFO(acctId, AccountLoginDTO.LoginType.AC.toString(),
                    dbSession, ipAddress, user, O_ERROR_MSG_ARR, seq, O_OLC_ACCOUNT_TAG_REC).intValue();*/
            int result = -1;
            if (result == -1)
                throw new EtccSecurityException("security exception in OracleTagRequestDAO.getTagBySeqNum()");
            resultDTO = new TagDTO();
            if (result == 0) {
                resultDTO.setErrors(OracleUtil.convertToMessages(O_ERROR_MSG_ARR));
                if (logger.isDebugEnabled()) {
                    logger.debug("getTagBySeqNum ==> result = 0");
                    logger.debug(acctLoginDTO.toString());
                    logger.debug("seqNumber: " + seqNumber);
                    OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                    for (int i = 0; i < errorArray.length; i++) {
                        logger.debug("getTagBySeqNum==>errorArray[" + i + "]=" + errorArray[i].getERROR_MSG());
                    }
                }
            } else {
                OLC_ACCOUNT_TAG_REC tagRec = O_OLC_ACCOUNT_TAG_REC[0];
                resultDTO = convertTagRecToTagDTO(tagRec);
            }
        } catch (SQLException e) {
            throw new EtccException("Exception in OracleTagRequestDAO.getTagBySeqNum: " + e.getMessage(), e);
        }
        return resultDTO;
    }

    public ArrayList<TagDTO> getMailedTags(String transactionId, String driverLicenseNumber, String driverLicenseState,
            @Sensitive String taxId) throws EtccException, EtccSecurityException {
        CallableStatement cstmt = null;
        boolean debugEnabled = logger.isDebugEnabled();
        try {
            StringBuilder theStoredProcedureCall = new StringBuilder();
            setTypeMap().put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OLC_ACCOUNT_TAG_REC.class);

            // 1 2 3 4 5 6 7
            cstmt = this.conn.prepareCall("{? = call olcsc_mailed_tags_activation.get_mailedtag_info(?,?,?,?,?,?,?)}");
            // p_transaction_id NUMBER,
            // p_dl_num accounts.driver_lic_nbr%type, varchar2(25)
            // p_dl_state accounts.driver_lic_state%type, varchar2(3)
            // p_tax_id accounts.secondary_id%type, varchar2(30)
            // P_ACCOUNT_TAG_ARR OUT OLC_ACCOUNT_TAG_ARR,
            // o_Alerts OUT OLC_ALERT_DISPLAY_ARR,
            // o_error_msg_arr OUT OLC_ERROR_MSG_ARR) return number;
            theStoredProcedureCall.append("olcsc_mailed_tags_activation.get_mailedtag_info(");
            int paramIdx = 1;
            cstmt.registerOutParameter(paramIdx, Types.SMALLINT);
            paramIdx++;
            theStoredProcedureCall.append("transactionId=");
            theStoredProcedureCall.append(transactionId);
            cstmt.setLong(paramIdx, Long.parseLong(transactionId));
            paramIdx++;
            theStoredProcedureCall.append(", driverLicenseNumber=");
            String str = StringUtil.isEmpty(driverLicenseNumber) ? "" : driverLicenseNumber;
            theStoredProcedureCall.append(str);
            cstmt.setString(paramIdx, str);
            paramIdx++;
            theStoredProcedureCall.append(", driverLicenseState=");
            str = StringUtil.isEmpty(driverLicenseState) ? "" : driverLicenseState;
            theStoredProcedureCall.append(str);
            cstmt.setString(paramIdx, str);
            paramIdx++;
            theStoredProcedureCall.append(", taxId=");
            str = StringUtil.isEmpty(taxId) ? "" : taxId;
            theStoredProcedureCall.append(str);
            cstmt.setString(paramIdx, str);
            paramIdx++;
            int tagsParamIdx = paramIdx;
            cstmt.registerOutParameter(tagsParamIdx, Types.ARRAY, "OL_OWNER.OLC_ACCOUNT_TAG_ARR");
            paramIdx++;
            int alertsParamIdx = paramIdx;
            cstmt.registerOutParameter(alertsParamIdx, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR");
            paramIdx++;
            int errorsParamIdx = paramIdx;
            cstmt.registerOutParameter(errorsParamIdx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            theStoredProcedureCall.append(")");
            if (debugEnabled)
                logger.debug(theStoredProcedureCall.toString());
            cstmt.execute();
            int result = cstmt.getInt(1);
            if (logger.isInfoEnabled())
                logger.info("getMailedTags.result=" + result);
            if (result == -1) { // failed
                logger.info("security exception: getMailedTags");
                throw new EtccSecurityException("Service to list mailed tags denied");
            } else if (result == 0) { // failed with error message
                ArrayList<TagDTO> messageContainer = new ArrayList<TagDTO>();
                TagDTO tag = new TagDTO();
                messageContainer.add(tag);
                Array alerts = (Array) cstmt.getObject(alertsParamIdx);
                Array errors = (Array) cstmt.getObject(errorsParamIdx);
                setMessages(tag, errors, alerts, "Failed to retrieve mailed EZ TAGs.");
                if (debugEnabled)
                    logger.debug("getMailedTags.theErrorContainer=" + messageContainer);
                return messageContainer;
            } else if (result == 1) { // success
                Array theTagRecords = (Array) cstmt.getObject(tagsParamIdx);
                ArrayList<TagDTO> theTags = convertTagRecs(theTagRecords);
                if (debugEnabled)
                    logger.debug("getMailedTags.theTags=" + theTags);
                return theTags;
            } else { // unexpected return value
                logger.error("getMailedTags.result has invalid value of " + result);
                throw new EtccSecurityException("Service to list mailed tags denied (invalid data)");
            }
        } catch (EtccSecurityException ese) {
            if (debugEnabled)
                logger.debug("getMailedTags security exception: " + ese.getMessage(), ese);
            throw ese;
            /*
             * } catch (EtccException ee) { logger.debug("getMailedTags exception: " + ee.getMessage(), ee); throw ee;
             */
        } catch (SQLException sqle) {
            throw new EtccException("getMailedTags SQL exception: " + sqle.getMessage(), sqle);
        } finally {
            close(cstmt);
        }
    }

    public ResultDTO activateMailedTags(String transactionId) throws EtccException, EtccSecurityException {
        CallableStatement cstmt = null;
        boolean debugEnabled = logger.isDebugEnabled();
        try {
            StringBuilder theStoredProcedureCall = new StringBuilder();
            setTypeMap();
            // 1 2 3
            cstmt = this.conn.prepareCall("{? = call olcsc_mailed_tags_activation.activate_mailed_tags(?,?,?)}");
            // p_transaction_id NUMBER,
            // o_Alerts OUT OLC_ALERT_DISPLAY_ARR,
            // o_error_msg_arr OUT OLC_ERROR_MSG_ARR) return number;
            theStoredProcedureCall.append("olcsc_mailed_tags_activation.get_mailedtag_info(");
            int paramIdx = 1;
            cstmt.registerOutParameter(paramIdx, Types.SMALLINT);
            paramIdx++;
            theStoredProcedureCall.append("transactionId=");
            theStoredProcedureCall.append(transactionId);
            cstmt.setLong(paramIdx, Long.parseLong(transactionId));
            paramIdx++;
            int alertsParamIdx = paramIdx;
            cstmt.registerOutParameter(alertsParamIdx, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR");
            paramIdx++;
            int errorsParamIdx = paramIdx;
            cstmt.registerOutParameter(errorsParamIdx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            theStoredProcedureCall.append(")");
            if (debugEnabled)
                logger.debug(theStoredProcedureCall.toString());
            cstmt.execute();
            int result = cstmt.getInt(1);
            if (logger.isInfoEnabled())
                logger.info("activateMailedTags.result=" + result);
            if (result == -1) { // failed
                logger.info("security exception: activateMailedTags");
                throw new EtccSecurityException("Service to activate mailed tags denied");
            } else if (result == 0) { // failed with error message
                ResultDTO theErrorContainer = new ResultDTO();
                Array alerts = (Array) cstmt.getObject(alertsParamIdx);
                Array errors = (Array) cstmt.getObject(errorsParamIdx);
                setMessages(theErrorContainer, errors, alerts, "Failed to activate mailed EZ TAGs.");
                if (debugEnabled)
                    logger.debug("activateMailedTags.theErrorContainer=" + theErrorContainer);
                return theErrorContainer;
            } else if (result == 1) { // success
                ResultDTO theSuccess = new ResultDTO();
                Array alerts = (Array) cstmt.getObject(alertsParamIdx);
                theSuccess.setAlerts(convertAlertMsgs(alerts));
                if (debugEnabled)
                    logger.debug("getMailedTags.theTags=" + theSuccess);
                return theSuccess;
            } else { // unexpected return value
                logger.error("activateMailedTags.result has invalid value of " + result);
                throw new EtccSecurityException("Service to activate mailed tags denied (invalid data)");
            }
        } catch (EtccSecurityException ese) {
            if (debugEnabled)
                logger.debug("activateMailedTags security exception: " + ese.getMessage(), ese);
            throw ese;
            /*
             * } catch (EtccException ee) { logger.debug("activateMailedTags exception: " + ee.getMessage(), ee); throw
             * ee;
             */
        } catch (SQLException sqle) {
            throw new EtccException("activateMailedTags SQL exception: " + sqle.getMessage(), sqle);
        } finally {
            close(cstmt);
        }
    }

    private ArrayList<TagDTO> convertTagRecs(OLC_ACCOUNT_TAG_REC[] tagRecs, int startIndex) throws SQLException {
        ArrayList<TagDTO> col = null;
        if (tagRecs != null && tagRecs.length > 0) {
            col = new ArrayList<TagDTO>(tagRecs.length);
            for (OLC_ACCOUNT_TAG_REC rec : tagRecs) {
                TagDTO tagDTO = convertTagRecToTagDTO(rec);
                tagDTO.setStartIndex(startIndex);
                col.add(tagDTO);
            }
        }
        return col;
    }
    //TxDot changes
    private ArrayList<TagDTO> convertTagPlateRecs(OLC_ACCOUNT_TAG_LICPLATE_REC[] tagRecs, int startIndex) throws SQLException {
        ArrayList<TagDTO> col = null;
        if (tagRecs != null && tagRecs.length > 0) {
            col = new ArrayList<TagDTO>(tagRecs.length);
            for (OLC_ACCOUNT_TAG_LICPLATE_REC rec : tagRecs) {
                TagDTO tagDTO = convertTagRecToPlateTagDTO(rec);
                tagDTO.setStartIndex(startIndex);
                col.add(tagDTO);
            }
        }
        return col;
    }

    private ArrayList<TagDTO> convertTagRecs(Array tagRecords) throws SQLException {
        if (tagRecords != null) {
            Object[] tagArray = (Object[]) tagRecords.getArray();
            try {
                return convertTagRecs((OLC_ACCOUNT_TAG_REC[]) tagArray, 0);
            } catch (ClassCastException e) {
                logger.warn("Error casting tagRecords to OLC_ACCOUNT_TAG_REC[] (will attempt cast on each element): " + e.getMessage(), e);
                if (tagArray.length > 0) {
                    ArrayList<TagDTO> tags = new ArrayList<TagDTO>(tagArray.length);
                    for (Object tagRec : tagArray)
                        tags.add(convertTagRecToTagDTO((OLC_ACCOUNT_TAG_REC) tagRec));
                    return tags;
                }
            }
        }
        return null;
    }

    private TagDTO convertTagRecToTagDTO(OLC_ACCOUNT_TAG_REC tagRec) throws SQLException {
        TagDTO tagDTO = null;
        if (tagRec != null) {
            tagDTO = new TagDTO();
            if (tagRec.getACCT_TAG_SEQ()!=null) {
            tagDTO.setAcctTagSeq(tagRec.getACCT_TAG_SEQ().longValue());
            }else {
            tagDTO.setAcctTagSeq(0L);
            }
            tagDTO.setAcctTagStatus(tagRec.getACCT_TAG_STATUS());
            tagDTO.setTagId(tagRec.getTAG_ID());
			tagDTO.setBarcodePrefix(tagRec.getBARCODE_PREFIX());
            tagDTO.setAgencyId(tagRec.getAGENCY_ID());
            if (tagRec.getACCT_VEHICLE_ID()!=null) {
            tagDTO.setAcctVehicleId(tagRec.getACCT_VEHICLE_ID().longValue());
            }else {
            tagDTO.setAcctVehicleId(0L);	
            }
            tagDTO.setLicPlate(tagRec.getLIC_PLATE());
            tagDTO.setLicState(tagRec.getLIC_STATE());
            tagDTO.setVehicleYear(tagRec.getVEHICLE_YEAR());
            tagDTO.setVehicleColor(tagRec.getVEHICLE_COLOR());
            tagDTO.setVehicleMake(tagRec.getVEHICLE_MAKE());
            tagDTO.setVehicleModel(tagRec.getVEHICLE_MODEL());
            tagDTO.setVehicleClassCode(tagRec.getVEHICLE_CLASS_CODE());
            tagDTO.setVehicleClassDesc(tagRec.getVEHICLE_CLASS_DESC());
            tagDTO.setTemporaryLicPlate(StringUtil.stringToBoolean(tagRec.getTEMP_PLATE_FLAG()));
            tagDTO.setTagStatusDesc(tagRec.getTAG_STATUS_DESC());
            tagDTO.setNickname(tagRec.getNICKNAME());
            tagDTO.setViolationExist(StringUtil.stringToBoolean(tagRec.getHAS_VIOL_INVOICES()));
            if (tagRec.getPBP_START_DATE() != null)
                tagDTO.setPbpStartDate(tagRec.getPBP_START_DATE());
            if (tagRec.getPBP_END_DATE() != null)
                tagDTO.setPbpEndDate(tagRec.getPBP_END_DATE());

            String recTypeCode = tagRec.getTAG_TYPE_CODE();
            tagDTO.setTagTypeCode(recTypeCode);
          //Defect ID : 13021  OLCSC new account creation with motor cycle - fulfilment is creating with a car sticker 
            if ((tagRec.getVEHICLE_CLASS_CODE() != null && tagRec.getVEHICLE_CLASS_CODE().equals("11")) 
				|| (!StringUtil.isEmpty(recTypeCode) && recTypeCode.equals("EM"))) {
                    tagDTO.setMotorcycle(true);
            }
            tagDTO.setTagStatusFlip(tagRec.getTAGSTATUSFLIP());
        }
        return tagDTO;
    }
    //TxDot changes
    private TagDTO convertTagRecToPlateTagDTO(OLC_ACCOUNT_TAG_LICPLATE_REC tagRec) throws SQLException {
        TagDTO tagDTO = null;
        if (tagRec != null) {
            tagDTO = new TagDTO();
            if (tagRec.getACCT_TAG_SEQ()!=null) {
            tagDTO.setAcctTagSeq(tagRec.getACCT_TAG_SEQ().longValue());
            }else {
            tagDTO.setAcctTagSeq(0L);
            }
            tagDTO.setAcctTagStatus(tagRec.getACCT_TAG_STATUS());
            tagDTO.setTagId(tagRec.getTAG_ID());
			tagDTO.setBarcodePrefix(tagRec.getBARCODE_PREFIX());
            tagDTO.setAgencyId(tagRec.getAGENCY_ID());
            if (tagRec.getACCT_VEHICLE_ID()!=null) {
            tagDTO.setAcctVehicleId(tagRec.getACCT_VEHICLE_ID().longValue());
            }else {
            tagDTO.setAcctVehicleId(0L);	
            }
            tagDTO.setLicPlate(tagRec.getLIC_PLATE());
            tagDTO.setLicState(tagRec.getLIC_STATE());
            tagDTO.setVehicleYear(tagRec.getVEHICLE_YEAR());
            tagDTO.setVehicleColor(tagRec.getVEHICLE_COLOR());
            tagDTO.setVehicleMake(tagRec.getVEHICLE_MAKE());
            tagDTO.setVehicleModel(tagRec.getVEHICLE_MODEL());
            tagDTO.setVehicleClassCode(tagRec.getVEHICLE_CLASS_CODE());
            tagDTO.setVehicleClassDesc(tagRec.getVEHICLE_CLASS_DESC());
            tagDTO.setTemporaryLicPlate(StringUtil.stringToBoolean(tagRec.getTEMP_PLATE_FLAG()));
            tagDTO.setTagStatusDesc(tagRec.getTAG_STATUS_DESC());
            tagDTO.setNickname(tagRec.getNICKNAME());
            tagDTO.setViolationExist(StringUtil.stringToBoolean(tagRec.getHAS_VIOL_INVOICES()));
            if (tagRec.getPBP_START_DATE() != null)
                tagDTO.setPbpStartDate(tagRec.getPBP_START_DATE());
            if (tagRec.getPBP_END_DATE() != null)
                tagDTO.setPbpEndDate(tagRec.getPBP_END_DATE());

            String recTypeCode = tagRec.getTAG_TYPE_CODE();
            tagDTO.setTagTypeCode(recTypeCode);
          //Defect ID : 13021  OLCSC new account creation with motor cycle - fulfilment is creating with a car sticker 
            if ((tagRec.getVEHICLE_CLASS_CODE() != null && tagRec.getVEHICLE_CLASS_CODE().equals("11")) 
				|| (!StringUtil.isEmpty(recTypeCode) && recTypeCode.equals("EM"))) {
                    tagDTO.setMotorcycle(true);
            }
            tagDTO.setTagStatusFlip(tagRec.getTAGSTATUSFLIP());
            if (tagRec.getACCOUNT_TAG_ID()!=null) {
            tagDTO.setAccountTagId(tagRec.getACCOUNT_TAG_ID().longValue());
            }
            tagDTO.setTagPrefix(tagRec.getBARCODE_PREFIX());
        }
        return tagDTO;
    }

    /**
     * @see OracleUtil#convertToAlerts(Array)
     */
    @Override
	protected AlertDTO[] convertAlertMsgs(Array alerts) throws SQLException {
        return OracleUtil.convertToAlerts(alerts);
    }

    @Override
    protected Collection<TagAuthorityDTO> loadTagAuthorities() throws EtccException, EtccSecurityException {
        Collection<TagAuthorityDTO> result = null;
        CallableStatement cstmt = null;
        try {
            /*
             * FUNCTION GET_TAG_AUTHORITIES(P_TAG_AUTHORITIES OUT OLC_TAG_AUTHORITIES_ARR, P_ERROR_ARR OUT
             * OLC_ERROR_MSG_ARR) RETURN NUMBER;
             */
            setTypeMap().put("OL_OWNER.OLC_TAG_AUTHORITIES_REC", OLC_TAG_AUTHORITIES_REC.class);

            cstmt = this.conn.prepareCall("{? = call OLCSC_UTIL.GET_TAG_AUTHORITIES(?, ?)}");
            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.registerOutParameter(2, Types.ARRAY, "OL_OWNER.OLC_TAG_AUTHORITIES_ARR");
            cstmt.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            Object[] objArray = (Object[]) cstmt.getArray(2).getArray();
            if (objArray != null && objArray.length > 0) {
                result = new ArrayList<TagAuthorityDTO>();
                for (int i = 0; i < objArray.length; i++) {
                    TagAuthorityDTO taDto = getObject((OLC_TAG_AUTHORITIES_REC) objArray[i]);
                    result.add(taDto);
                }
            }
            return result;
        } catch (SQLException sqle) {
            throw new EtccException("Error running getTagAuthorities: " + sqle.getMessage(), sqle);
        } finally {
            close(cstmt);
        }
    }

    private TagAuthorityDTO getObject(OLC_TAG_AUTHORITIES_REC temp) throws SQLException {
        TagAuthorityDTO taDto = null;
        if (temp != null) {
            taDto = new TagAuthorityDTO();
            taDto.setBarcodePrefix(temp.getBARCODE_PREFIX());
            taDto.setName(temp.getNAME());
            taDto.setTagIdentifier(temp.getTAG_IDENTIFIER());
            taDto.setTaId(temp.getTA_ID().longValue());
            // taDto.setTaLcId(temp.get);
        }
        return taDto;
    }

    @Override
	protected String loadTagApplicationAgreement() throws EtccException {
        String result = null;
        CallableStatement cstmt = null;
        try {
            setTypeMap();
            cstmt = this.conn.prepareCall("{? = call OLCSC_PARAM.get_agreement(?,?,?)}");
            int paramIdx = 1;
            cstmt.registerOutParameter(paramIdx, Types.SMALLINT);
            paramIdx++;
            cstmt.setString(paramIdx, "TT_AGREEMENT");
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
            throw new EtccException(e.getMessage(), e);
        } finally {
            close(cstmt);
        }
    }

	 @Override
	protected String loadAgencyInfo() throws EtccException {
        String result = null;
        CallableStatement cstmt = null;
        try {
            setTypeMap();
            cstmt = this.conn.prepareCall("{? = call OLCSC_PARAM.get_agreement(?,?,?)}");
            int paramIdx = 1;
            cstmt.registerOutParameter(paramIdx, Types.SMALLINT);
            paramIdx++;
            cstmt.setString(paramIdx, "AGENCY_INFO");
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
            throw new EtccException(e.getMessage(), e);
        } finally {
            close(cstmt);
        }
    }


    public double calculateActivationFee(AccountLoginDTO acctLoginDTO,
			long stickerTagCount, long motocycleCount, long licensePlateTagCount)
			throws EtccException {

    	double result = 0d;
        CallableStatement cstmt = null;
        try {
            setTypeMap();
            cstmt = this.conn.prepareCall("{? = call TAG_OWNER.ACCOUNT_MAINTENANCE_API.apply_rule_tag_activation_fee(?,?,?,?)}");
            int paramIdx = 1;
            cstmt.registerOutParameter(paramIdx++, Types.NUMERIC);
            cstmt.setLong(paramIdx++, acctLoginDTO.getAcctId());
            cstmt.setLong(paramIdx++, stickerTagCount);
            cstmt.setLong(paramIdx++, motocycleCount);
            cstmt.setLong(paramIdx++, licensePlateTagCount);
            cstmt.execute();
            result = cstmt.getDouble(1);
        } catch (SQLException e) {
            throw new EtccException(e.getMessage(), e);
        } finally {
            close(cstmt);
        }
        return result;
	}



	/**
     * #see OracleUtil#convertToMessages(Array)
     */
    @Override
    protected ErrorMessageDTO[] convertErrorMsgs(Array errors) throws SQLException {
        return OracleUtil.convertToMessagesFromRecs(errors);
    }

	public ResultDTO replaceOrReactivateTag(TagReplaceOrReactivateDTO tagReplaceReactivateDTO) throws EtccException {
		logger.debug("Entry OracleTagDAO.replaceOrReactivateTag");
		CallableStatement cstmt = null;
		ResultDTO resultDto = null;
		try {
			setTypeMap();
			cstmt = this.conn.prepareCall(
					"{? = call OL_OWNER.OLCSC_ACCT_MGMT.replace_reactive_tag(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			int paramIdx = 1;
			cstmt.registerOutParameter(paramIdx++, Types.NUMERIC);
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getAccountId());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getDocType());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getSessionId());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getIpAddress());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getUserId());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getTagId());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getLicPlate());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getLicState());
			cstmt.setLong(paramIdx++, tagReplaceReactivateDTO.getAccountVehicleId());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getMotorcycleFlag());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getDeliveryMethod());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getAction());
			cstmt.setString(paramIdx++, tagReplaceReactivateDTO.getReason());
			cstmt.registerOutParameter(paramIdx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			int result = cstmt.getInt(1);
			resultDto = new ResultDTO();
			logger.debug("OracleTagDAO.replaceOrReactivateTag replaceOrReactivateTag.result=" + result);
			if (result == 0) {
				final Array errors = (Array) cstmt.getObject(15);
				resultDto.setErrors(convertErrorMsgs(errors));
				logger.error("OracleTagDAO.replaceOrReactivateTag Oracle Exceptions: "
						+ Arrays.toString((Object[]) errors.getArray()));
			}
		} catch (SQLException sqlException) {
			throw new EtccException("SQLException occurred during call OL_OWNER.OLCSC_ACCT_MGMT.replace_reactive_tag"
					+ sqlException.getMessage(), sqlException);
		} finally {
			close(cstmt);
		}
		return resultDto;
	}
	
	public ResultDTO activateMailedTagsSecurely(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId,String transactionId) throws EtccException, EtccSecurityException {
        CallableStatement cstmt = null;
        boolean debugEnabled = logger.isDebugEnabled();
        try {
            StringBuilder theStoredProcedureCall = new StringBuilder();
       
            setTypeMap();
       
            cstmt = this.conn.prepareCall("{? = call olcsc_mailed_tags_activation.activate_mailed_tags(?,?,?,?,?,?,?,?)}");
            
            theStoredProcedureCall.append("olcsc_mailed_tags_activation.activate_mailed_tags(");
            
            int paramIdx = 1;
            cstmt.registerOutParameter(paramIdx, Types.SMALLINT);
            
            paramIdx++;
            theStoredProcedureCall.append("acctId=");
            theStoredProcedureCall.append(acctId);
            cstmt.setLong(paramIdx, acctId);
            
            paramIdx++;
            theStoredProcedureCall.append("acctType=");
            theStoredProcedureCall.append(acctType);
            cstmt.setString(paramIdx, acctType);
            
            paramIdx++;
            theStoredProcedureCall.append("dbSessionId=");
            theStoredProcedureCall.append(dbSessionId);
            cstmt.setString(paramIdx, dbSessionId);
            
            paramIdx++;
            theStoredProcedureCall.append("ipAddress=");
            theStoredProcedureCall.append(ipAddress);
            cstmt.setString(paramIdx, ipAddress);
            
            paramIdx++;
            theStoredProcedureCall.append("loginId=");
            theStoredProcedureCall.append(loginId);
            cstmt.setString(paramIdx, loginId);
            
            paramIdx++;
            theStoredProcedureCall.append("transactionId=");
            theStoredProcedureCall.append(transactionId);
            cstmt.setLong(paramIdx, Long.parseLong(transactionId));
            
            paramIdx++;
            int alertsParamIdx = paramIdx;
            cstmt.registerOutParameter(alertsParamIdx, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR");
            
            paramIdx++;
            int errorsParamIdx = paramIdx;
            cstmt.registerOutParameter(errorsParamIdx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            
            theStoredProcedureCall.append(")");
            
            if (debugEnabled)
                logger.debug(theStoredProcedureCall.toString());
           
            cstmt.execute();
            
            int result = cstmt.getInt(1);
            
            if (logger.isInfoEnabled())
                logger.info("activateMailedTagsSecurely.result=" + result);
            
            if (result == -1) { // failed
                logger.info("security exception: activateMailedTagsSecurely");
                throw new EtccSecurityException("Service to activate mailed tags denied");
            } else if (result == 0) { // failed with error message
                ResultDTO theErrorContainer = new ResultDTO();
                Array alerts = (Array) cstmt.getObject(alertsParamIdx);
                Array errors = (Array) cstmt.getObject(errorsParamIdx);
                setMessages(theErrorContainer, errors, alerts, "Failed to activate mailed TAGs.");
                if (debugEnabled)
                    logger.debug("activateMailedTagsSecurely.theErrorContainer=" + theErrorContainer);
                return theErrorContainer;
            } else if (result == 1) { // success
                ResultDTO theSuccess = new ResultDTO();
                Array alerts = (Array) cstmt.getObject(alertsParamIdx);
                theSuccess.setAlerts(convertAlertMsgs(alerts));
                if (debugEnabled)
                    logger.debug("activateMailedTagsSecurely.theTags=" + theSuccess);
                return theSuccess;
            } else { // unexpected return value
                logger.error("activateMailedTagsSecurely.result has invalid value of " + result);
                throw new EtccSecurityException("Service to activate mailed tags denied (invalid data)");
            }
        } catch (EtccSecurityException ese) {
            if (debugEnabled)
                logger.debug("activateMailedTagsSecurely security exception: " + ese.getMessage(), ese);
            throw ese;
            
        } catch (SQLException sqle) {
            throw new EtccException("activateMailedTagsSecurely SQL exception: " + sqle.getMessage(), sqle);
        } finally {
            close(cstmt);
        }
    }
	private String loadSysParam(String paramName) throws EtccException {
        this.logger.debug("Start");
        String result = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call " + "utl_web.GETSYSPARM('" + paramName + "')}");

            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.execute();

            result = cstmt.getString(1);
            this.logger.info(paramName + ": " + result);
        } catch (SQLException sqle) {
            throw new EtccException("Error running getSysParam (" + paramName + "): " + ": " + sqle, sqle);
        } finally {
            close(cstmt);
        }
        return result;
    }
}
