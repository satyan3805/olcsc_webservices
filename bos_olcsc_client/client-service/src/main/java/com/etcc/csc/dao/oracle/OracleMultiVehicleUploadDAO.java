/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.MultiVehicleUploadDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.FileMultiVehicleUploadDTO;
import com.etcc.csc.dto.MultiVehicleDTO;
import com.etcc.csc.dto.MultiVehicleUploadDTO;
import com.etcc.csc.plsql.MULTIPLE_VEHICLE_UPLOAD_REC;
//import com.etcc.csc.plsql.MULTIPLE_VEHICLE_UPLOAD_REC;
import com.etcc.csc.plsql.Multiple_Vehicle_Upload_obj;
import com.etcc.csc.util.StringUtil;

/**
 * Oracle implementation of the MultiVehicleUploadDAO.
 * @author (task 488) Stephen Davidson
 */
public class OracleMultiVehicleUploadDAO extends MultiVehicleUploadDAO {
    private static final Logger logger = Logger.getLogger(OracleMultiVehicleUploadDAO.class);

    @Override
    public boolean isMultiVehicleUploadAllowed(AccountLoginDTO accountLogin)
    throws EtccException, EtccSecurityException{
/*
 MULTIPLE_VEHICLE_UPLOAD.AllowMultipleVehicleUpload
function AllowMultipleVehicleUpload(p_acct_id          NUMBER,
                                    p_session          VARCHAR2,
                                    p_ip_address       VARCHAR2,
                                    p_user             VARCHAR2,
                                    o_error_msg_arr OUT olc_error_msg_arr) return number is

 2 = Account not allowed.
 1 = Account is set allowed access to the functionality
 0 = An exception has been encountered.  Check the error message array.
 -1 = Invalid session
  */
        CallableStatement cstmt = null;
         try{
             long acctId = accountLogin.getAcctId();
             String dbSessionId = accountLogin.getDbSessionId();
             String lastLoginIp = accountLogin.getLastLoginIp();
             String loginId = accountLogin.getLoginId();
             final boolean debugEnabled = logger.isDebugEnabled();
            if (debugEnabled)
                 logger.debug("MULTIPLE_VEHICLE_UPLOAD.AllowMultipleVehicleUpload(acctId=" + acctId +
                 ",  dbSessionId=" + dbSessionId + ", lastLoginIp=" + lastLoginIp +
                 ", loginId=" + loginId  + ", OLC_ERROR_MSG_ARR);");
             //                 1                                                           2  3  4  5  6
             String theCall = "{? = call MULTIPLE_VEHICLE_UPLOAD.AllowMultipleVehicleUpload(?, ?, ?, ?, ?)}";
             cstmt = this.conn.prepareCall(theCall);
             setTypeMap();
             int idx = 1;
             cstmt.registerOutParameter(idx, Types.INTEGER); //1
             idx++;
             cstmt.setLong(idx, acctId); //2
             idx++;
             cstmt.setString(idx, dbSessionId);  //3
             idx++;
             cstmt.setString(idx, lastLoginIp);  //4
             idx++;
             cstmt.setString(idx, loginId);  //5
             idx++;
             int errorIdx = idx;
             cstmt.registerOutParameter(errorIdx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");  //6
             cstmt.execute();
             int result = cstmt.getInt(1);
             if (debugEnabled)
                logger.debug("isMultiVehicleUploadAllowed.result=" + result);
             switch (result){
             case -1:
                 throw new EtccSecurityException("Security exception in isMultiVehicleUploadAllowed");
             case 0: //Error
                 Array errors = (Array)cstmt.getObject(errorIdx);
                 accountLogin.setErrors(OracleUtil.convertToMessages(errors));
                 if (!accountLogin.hasErrors()){
                     logger.warn("Database Exception occurred (result == 0), but no messages were sent by the Database.");
                 }
                 throw new EtccException("Exceptions occurred during database access: " +
                         ErrorMessageDTO.toStringBuilder(accountLogin.getErrors()));
             case 1: //Allowed
                 return true;
             case 2: //Not allowed
                 return false;
             default: //Unknown
                 throw new EtccException("Unknown result returned for is Multiple Vehicle Upload allowed: " + result);
             }
         } catch (SQLException sqle) {
             if (sqle.getErrorCode() == -20247){
                 throw new EtccSecurityException("Security exception in isMultiVehicleUploadAllowed");
             }
             String message = "SQL Error running isMultiVehicleUploadAllowed SQLException: " + sqle.getMessage();
             throw new EtccException(message, sqle);
         } finally {
             close(cstmt);
         }
    }

    public MultiVehicleUploadDTO validateMultipleVehicles(AccountLoginDTO accountLogin,
                                                          MultiVehicleUploadDTO uploadRequest)
                                                          throws EtccException, EtccSecurityException{

        CallableStatement cstmt = null;
        logger.info("OracleMultiVehicleUploadDAO.validateMultipleVehicles for account: " + accountLogin.getAcctId()); // defect#9521
        try{
            long acctId = accountLogin.getAcctId();
            String dbSessionId = accountLogin.getDbSessionId();
            String lastLoginIp = accountLogin.getLastLoginIp();
            String loginId = accountLogin.getLoginId();
            long posId = uploadRequest.getPosId();
            String fileName = uploadRequest.getFileName();
            Map<String, Class<?>> typeMap = setTypeMap();
           typeMap.put("OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_OBJ", Multiple_Vehicle_Upload_obj.class);

           ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor("OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_TAB", this.conn);
            ARRAY array = new ARRAY(arraydesc, this.conn, convertMultiVehicleTagsToDb(uploadRequest.getMultiVehicleTags(), acctId));
//                             1                                                         2  3  4  5  6  7  8  9  10 11 12 13 14
            String theCall = "{? = call MULTIPLE_VEHICLE_UPLOAD.ValidateMultipleVehicles(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            StringBuilder sb = new StringBuilder();
            sb.append("MULTIPLE_VEHICLE_UPLOAD.ValidateMultipleVehicles(");
            cstmt = this.conn.prepareCall(theCall);

/*
 FUNCTION ValidateMultipleVehicles(p_acct_id              NUMBER,    2
                                  p_session              VARCHAR2,   3 
                                  p_ip_address           VARCHAR2,   4
                                  p_user                 VARCHAR2,   5
                                  p_pos_id               NUMBER DEFAULT -1,  6
                                  p_hard_errors          OUT NUMBER,  7
                                  p_add_recs             OUT NUMBER,  8
                                  p_inactivate_recs      OUT NUMBER,  9                             
                                  p_report_id            OUT NUMBER,     -- Exception report ID used to create PDF error report  10
                                  p_validate_session     IN varchar2 default 'Y',  11
                                  p_MultipleVehicleARR   IN OUT Multiple_Vehicle_Upload_Tab, 12
                                  o_error_msg_arr        OUT   olc_error_msg_arr) 13
*/

            int idx = 1;
            cstmt.registerOutParameter(idx, Types.INTEGER);                                         //1
            idx++;
            cstmt.setLong(idx, acctId);                                                             //2
            sb.append("acctId(").append(idx).append(")=").append(acctId);
            idx++;
            cstmt.setString(idx, dbSessionId);                                                      //3
            sb.append(", dbSessionId(").append(idx).append(")=").append(dbSessionId);
            idx++;
            cstmt.setString(idx, lastLoginIp);                                                      //4
            sb.append(", lastLoginIp(").append(idx).append(")=").append(lastLoginIp);
            idx++;
            cstmt.setString(idx, loginId);                                                          //5
            sb.append(", loginId(").append(idx).append(")=").append(loginId);
            idx++;
            cstmt.setLong(idx, posId); 																//6 p_pos_id NUMBER DEFAULT
            sb.append(", posId(").append(idx).append(")=").append(posId);
            idx++;
            cstmt.registerOutParameter(idx, Types.INTEGER);                                         //7
            int hardErrorParamIdx = idx;
            sb.append(", hardErrorParamIdx(").append(hardErrorParamIdx).append(")");
            idx++;
            cstmt.registerOutParameter(idx, Types.INTEGER);                                         //8
            int addRecordsParamIdx = idx;
            sb.append(", addRecordsParamIdx(").append(addRecordsParamIdx).append(")");
            idx++;
            cstmt.registerOutParameter(idx, Types.INTEGER);                                         //9
            int inactiveRecordsParamIdx = idx;
            sb.append(", inactiveRecordsParamIdx(").append(inactiveRecordsParamIdx).append(")");
            idx++;
            cstmt.registerOutParameter(idx, Types.INTEGER);                                         //10
            int reportIdParamIdx = idx;
            sb.append(", reportIdParamIdx(").append(reportIdParamIdx).append(")");
            idx++;
            String p_validate_session = "Y";
            cstmt.setString(idx, p_validate_session);                                                              //11
            sb.append(", p_validate_session(").append(idx).append(")=").append(p_validate_session);
            idx++;
            cstmt.setArray(idx, array);                                                             //12
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_TAB" ); //13
            int vehicleParamIdx = idx;
            sb.append(", array(vehicleParamIdx=").append(vehicleParamIdx).append(")");
            idx++;
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");            //12
            int errorParamIdx = idx;
            sb.append(", array(errorParamIdx=").append(errorParamIdx).append(")");
            //Start defect#9521
            idx++;
            cstmt.setString(idx, fileName);                                                          //5
            sb.append(", FileName(").append(idx).append(")=").append(fileName);
            //End defect#9521
            sb.append(")");
            if (logger.isDebugEnabled())
                logger.debug("validateMultipleVehicles.cstmt=" + sb.toString());
            cstmt.execute();
            int result = cstmt.getInt(1);
            if (logger.isInfoEnabled())
                logger.info("validateMultipleVehicles.result=" + result);
            if (result == -1) {
                logger.info("security exception: validateMultipleVehicles");
                throw new EtccSecurityException("Security Exception in validateMultipleVehicles");
            } else if (result == 0) {
                //0 = Exception occurred and the data can not be submitted to the DB commit as is.
                //    Check the error message array for information
                logger.error("validateMultipleVehicles.result=" + result);
                Array errors = (Array)cstmt.getObject(errorParamIdx);
                uploadRequest.setErrors(OracleUtil.convertToMessages(errors));
            }
            //1 = Success where there are no hard errors and the user can submit the data to be committed to the DB.
            uploadRequest.setHardErrorCount(cstmt.getInt(hardErrorParamIdx));
            uploadRequest.setVehicleAddCount(cstmt.getInt(addRecordsParamIdx));
            uploadRequest.setVehicleInactivateCount(cstmt.getInt(inactiveRecordsParamIdx));
            uploadRequest.setReportId(cstmt.getLong(reportIdParamIdx));
            uploadRequest.setMultiVehicleTags(convertVehicleRecs(cstmt.getArray(vehicleParamIdx),acctId));
            if (logger.isDebugEnabled()) {
                logger.debug("validateMultipleVehicles.return.uploadRequest=" + uploadRequest.toString(0));
            }
            //Defect#9521
            if (logger.isInfoEnabled())
                logger.info("validateMultipleVehicles.return.hardErrorCount=" + uploadRequest.getHardErrorCount());
            return uploadRequest;
         } catch (SQLException sqle) {
             if (sqle.getErrorCode() == -20247){
                 throw new EtccSecurityException("Security exception in validateMultipleVehicles");
             }
            String message = "SQL Error running validateMultipleVehicles: " + sqle.getMessage();
            throw new EtccException(message, sqle);
        } finally {
            close(cstmt);
        }
    }
    
    public MultiVehicleUploadDTO loadMultipleVehicles(
			AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO uploadRequest)
			throws EtccException, EtccSecurityException {
	
    	// stub implementation , need to be fixed once PL/SQL dev confirms the procedure & package param
    	logger.info("OracleMultiVehicleUploadDAO.loadMultipleVehicles for account: " + acctLoginDto.getAcctId()); // defect#9521
    	 CallableStatement cstmt = null;
         try{
              long acctId = acctLoginDto.getAcctId();
              String dbSessionId = acctLoginDto.getDbSessionId();
              String lastLoginIp = acctLoginDto.getLastLoginIp();
              String loginId = acctLoginDto.getLoginId();
              long posId = uploadRequest.getPosId();
              String fileName = uploadRequest.getFileName();

              Map<String, Class<?>> typeMap = setTypeMap();
              typeMap.put("OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_OBJ", Multiple_Vehicle_Upload_obj.class);

              ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor("OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_TAB", this.conn);
               ARRAY array = new ARRAY(arraydesc, this.conn, convertMultiVehicleTagsToDb(uploadRequest.getMultiVehicleTags(), acctId));
//                               1                                                     2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18
              String theCall = "{? = call MULTIPLE_VEHICLE_UPLOAD.LoadMultipleVehicles(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )}";
              StringBuilder sb = new StringBuilder(512);
              sb.append("MULTIPLE_VEHICLE_UPLOAD.LoadMultipleVehicles(");
              
              cstmt = this.conn.prepareCall(theCall);
 /*
             1 = Success where there are no hard errors and the data has been inserted into the DB.  Additional steps defined below are to follow.
             0 = Exception occurred and the data can not be submitted to the DB.  Check the error message array for information.
             -1 = Invalid Session

 function LoadMultipleVehicles(p_acct_id          NUMBER,                                                        2
                             p_session          VARCHAR2,                                                        3
                             p_ip_address       VARCHAR2,                                                        4
                             p_user             VARCHAR2,                                                        5
                             p_pos_id           NUMBER DEFAULT -1,                                               6
                             p_hard_errors      OUT NUMBER,                                                      7
                             p_add_recs         OUT NUMBER,                                                      8
                             p_inactivate_recs  OUT NUMBER,                                                      9
                             p_trxn             IN OUT NUMBER,                                                   10
                             p_tag_activate_amt out number,     -- UI Display and charged at fulfillment         11
                             p_pending_dep_amt  out number,     -- Deposit amount due at time of fulfillment     12
                             p_min_bal_amt      out number,     -- Minimum balance amount for the account        13
                             p_total_amt_due    out number,     -- Total due at time of check-out                14
                             p_report_id        out number,                                                      15
                             MultipleVehicleARR in out Multiple_vehicle_upload_arr,                              16
                             o_error_msg_arr    OUT olc_error_msg_arr);                                          17
 */
              
              int idx = 1;
              cstmt.registerOutParameter(idx, Types.INTEGER);    //1 result
              idx++;
              cstmt.setLong(idx, acctId);    //2 p_acct_id NUMBER
              sb.append("acctId(").append(idx).append(")=").append(acctId);
              idx++;
              cstmt.setString(idx, dbSessionId); //3 p_session VARCHAR2
              sb.append(", dbSessionId(").append(idx).append(")=").append(dbSessionId);
              idx++;
              cstmt.setString(idx, lastLoginIp); //4 p_ip_address VARCHAR2
              sb.append(", lastLoginIp(").append(idx).append(")=").append(lastLoginIp);
              idx++;
              cstmt.setString(idx, loginId);   //5 p_user VARCHAR2
              sb.append(", loginId(").append(idx).append(")=").append(loginId);
              idx++;
              cstmt.setLong(idx, posId); //6 p_pos_id NUMBER DEFAULT
              sb.append(", posId(").append(idx).append(")=").append(posId);
              idx++;
              cstmt.registerOutParameter(idx, Types.INTEGER);   //7 p_hard_errors OUT NUMBER
              int hardErrorParamIdx = idx;
              sb.append(", hardErrorParamIdx(").append(hardErrorParamIdx).append(")");
              idx++;
              cstmt.registerOutParameter(idx, Types.INTEGER);   //8 p_add_recs OUT NUMBER
              int addRecordsParamIdx = idx;
              sb.append(", addRecordsParamIdx(").append(addRecordsParamIdx).append(")");
              idx++;
              cstmt.registerOutParameter(idx, Types.INTEGER);    //9 p_inactivate_recs  OUT NUMBER
              int inactiveRecordsParamIdx = idx;
              sb.append(", inactiveRecordsParamIdx(").append(inactiveRecordsParamIdx).append(")");
              idx++;
              cstmt.setLong(idx, uploadRequest.getTransactionId()); //10 p_trxn IN NUMBER
              sb.append(", TransactionId(").append(idx).append(")=").append(uploadRequest.getTransactionId());
              cstmt.registerOutParameter(idx, Types.INTEGER);   //10 p_trxn OUT NUMBER
              int transIdParamIdx = idx;
              sb.append(", transIdParamIdx(").append(transIdParamIdx).append(")");
              idx++;
              cstmt.registerOutParameter(idx, Types.DOUBLE);    //11 p_tag_activate_amt out number
              int tagActivateParamIdx = idx;
              sb.append(", tagActivateParamIdx(").append(tagActivateParamIdx).append(")");
              idx++;
              cstmt.registerOutParameter(idx, Types.DOUBLE);   //12 p_pending_dep_amt out number
              int pendingDepAmtParamIdx = idx;
              sb.append(", pendingDepAmtParamIdx(").append(pendingDepAmtParamIdx).append(")");
              idx++;
              cstmt.registerOutParameter(idx, Types.DOUBLE);   //13 p_min_bal_amt out number
              int minBalanceAmtParamIdx = idx;
              sb.append(", minBalanceAmtParamIdx(").append(minBalanceAmtParamIdx).append(")");
              idx++;
              cstmt.registerOutParameter(idx, Types.DOUBLE);  //14 p_total_amt_due out number
              int totalAmtDueParamIdx = idx;
              sb.append(", totalAmtDueParamIdx(").append(totalAmtDueParamIdx).append(")");
              idx++;
              cstmt.registerOutParameter(idx, Types.DOUBLE);   //15 p_report_id out number
              int reportIdParamIdx = idx;
              sb.append(", reportIdParamIdx(").append(reportIdParamIdx).append(")");
              idx++;             
              cstmt.setArray(idx, array);  //16 MultipleVehicleARR in Multiple_Vehicle_Upload_Tab
              sb.append(", array(").append(idx).append(")=array");
              cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_TAB" ); //16 MultipleVehicleARR out Multiple_Vehicle_Upload_Tab
              int vehicleParamIdx = idx;
              sb.append(", vehicleParamIdx(").append(vehicleParamIdx).append(")");
              idx++;
              cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");            //17
              int errorParamIdx = idx;
              sb.append(", errorParamIdx(").append(errorParamIdx).append(")");
              //Start defect#9521
              idx++;
              cstmt.setString(idx, fileName);                                                          //5
              sb.append(", FileName(").append(idx).append(")=").append(fileName);
              //End defect#9521
              sb.append(")");
              if (logger.isDebugEnabled())
                 logger.debug("loadMultipleVehicles.cstmt=" + sb.toString());
              cstmt.execute();
              int result = cstmt.getInt(1);
              if (result == -1) {
                  logger.info("security exception: loadMultipleVehicles");
                  throw new EtccSecurityException("Security Exception in loadMultipleVehicles");
              } else if (result == 0) {
                 //0 = Exception occurred and the data can not be submitted to the DB commit as is.
                 //    Check the error message array for information
                  logger.error("loadMultipleVehicles.result=" + result);
                  Array errors = (Array)cstmt.getObject(errorParamIdx);
                  uploadRequest.setErrors(OracleUtil.convertToMessagesFromRecs(errors));
              }
              //1 = Success where there are no hard errors and the user can submit the data to be committed to the DB.
              uploadRequest.setTransactionId(cstmt.getLong(transIdParamIdx));
              uploadRequest.setTagActivateAmt(cstmt.getDouble(tagActivateParamIdx));
              uploadRequest.setPendingDepositAmt(cstmt.getDouble(pendingDepAmtParamIdx));
              uploadRequest.setMinBalAmt(cstmt.getDouble(minBalanceAmtParamIdx));
              uploadRequest.setTotalAmt(cstmt.getDouble(totalAmtDueParamIdx));
              uploadRequest.setHardErrorCount(cstmt.getInt(hardErrorParamIdx));
              uploadRequest.setVehicleAddCount(cstmt.getInt(addRecordsParamIdx));
              uploadRequest.setVehicleInactivateCount(cstmt.getInt(inactiveRecordsParamIdx));
              uploadRequest.setReportId(cstmt.getLong(reportIdParamIdx));
              uploadRequest.setMultiVehicleTags(convertVehicleRecs(cstmt.getArray(vehicleParamIdx),acctId));
              if (logger.isDebugEnabled()) {
                  logger.debug("loadMultipleVehicles.return.uploadRequest=" + uploadRequest.toString(0));
              }
              //Defect#9521
              if (logger.isInfoEnabled())
                  logger.info("validateMultipleVehicles.return.hardErrorCount=" + uploadRequest.getHardErrorCount());
              
              return uploadRequest;
          } catch (SQLException sqle) {
              if (sqle.getErrorCode() == -20247){
                  throw new EtccSecurityException("Security exception in loadMultipleVehicles");
              }
              throw new EtccException("SQL Error running loadMultipleVehicles.sqle: " + sqle.getMessage(), sqle);
          } finally {
              close(cstmt);
          }
	}
    

    public MultiVehicleUploadDTO loadMultipleVehiclesFile(AccountLoginDTO accountLogin, FileMultiVehicleUploadDTO uploadRequest)
                                                      throws EtccException, EtccSecurityException{

        CallableStatement cstmt = null;
        try{
             long acctId = accountLogin.getAcctId();
             String dbSessionId = accountLogin.getDbSessionId();
             String lastLoginIp = accountLogin.getLastLoginIp();
             String loginId = accountLogin.getLoginId();
             long posId = -99L;

             Map<String, Class<?>> typeMap = setTypeMap();
             typeMap.put("OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_REC", MULTIPLE_VEHICLE_UPLOAD_REC.class);
             /*ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor("OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_ARR", this.conn);
             ARRAY array = new ARRAY(arraydesc, this.conn, convertMultiVehicleTagsToDb(
                 uploadRequest.getMultiVehicleTags(), acctId));*/
//                              1                                                     2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20
             String theCall = "{? = call MULTIPLE_VEHICLE_UPLOAD.LoadMultipleVehicles(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
             StringBuilder sb = new StringBuilder(512);
             sb.append("MULTIPLE_VEHICLE_UPLOAD.LoadMultipleVehicles(");

             cstmt = this.conn.prepareCall(theCall);
/*
            1 = Success where there are no hard errors and the data has been inserted into the DB.  Additional steps defined below are to follow.
            0 = Exception occurred and the data can not be submitted to the DB.  Check the error message array for information.
            -1 = Invalid Session

function LoadMultipleVehicles(p_acct_id          NUMBER,                                                        2
                            p_session          VARCHAR2,                                                        3
                            p_ip_address       VARCHAR2,                                                        4
                            p_user             VARCHAR2,                                                        5
                            p_pos_id           NUMBER DEFAULT -1,                                               6
                            p_file_location								                                        7
                            p_file_name																			8
                            p_check_sum																		    9
                            p_hard_errors      OUT NUMBER,                                                      10
                            p_add_recs         OUT NUMBER,                                                      11
                            p_inactivate_recs  OUT NUMBER,                                                      12
                            p_trxn             IN OUT NUMBER,                                                   13
                            p_tag_activate_amt out number,     -- UI Display and charged at fulfillment         14
                            p_pending_dep_amt  out number,     -- Deposit amount due at time of fulfillment     15
                            p_min_bal_amt      out number,     -- Minimum balance amount for the account        16
                            p_total_amt_due    out number,     -- Total due at time of check-out                17
                            p_report_id        out number,                                                      18
                            o_error_msg_arr    OUT olc_error_msg_arr);                                          19
*/

             int idx = 1;
             cstmt.registerOutParameter(idx, Types.INTEGER);    //1 result
             idx++;
             cstmt.setLong(idx, acctId);    //2 p_acct_id NUMBER
             sb.append("acctId(").append(idx).append(")=").append(acctId);
             idx++;
             cstmt.setString(idx, dbSessionId); //3 p_session VARCHAR2
             sb.append(", dbSessionId(").append(idx).append(")=").append(dbSessionId);
             idx++;
             cstmt.setString(idx, lastLoginIp); //4 p_ip_address VARCHAR2
             sb.append(", lastLoginIp(").append(idx).append(")=").append(lastLoginIp);
             idx++;
             cstmt.setString(idx, loginId);   //5 p_user VARCHAR2
             sb.append(", loginId(").append(idx).append(")=").append(loginId);
             idx++;
             cstmt.setLong(idx, posId); //6 p_pos_id NUMBER DEFAULT
             sb.append(", posId(").append(idx).append(")=").append(posId);
             idx++;
             cstmt.setString(idx, uploadRequest.getFileLocation()); //7 file_location NUMBER DEFAULT
             sb.append(", fileLocation(").append(idx).append(")=").append(uploadRequest.getFileLocation());
             idx++;
             cstmt.setString(idx, uploadRequest.getFileName()); //8 file_location NUMBER DEFAULT
             sb.append(", fileName(").append(idx).append(")=").append(uploadRequest.getFileName());
             idx++;
             cstmt.setString(idx, uploadRequest.getFileCheckSum()); //9 file_location NUMBER DEFAULT
             sb.append(", checkSum(").append(idx).append(")=").append(uploadRequest.getFileCheckSum());
             idx++;
             cstmt.registerOutParameter(idx, Types.INTEGER);   //10 p_hard_errors OUT NUMBER
             int hardErrorParamIdx = idx;
             sb.append(", hardErrorParamIdx(").append(hardErrorParamIdx).append(")");
             idx++;
             cstmt.registerOutParameter(idx, Types.INTEGER);   //11 p_add_recs OUT NUMBER
             int addRecordsParamIdx = idx;
             sb.append(", addRecordsParamIdx(").append(addRecordsParamIdx).append(")");
             idx++;
             cstmt.registerOutParameter(idx, Types.INTEGER);    //12 p_inactivate_recs  OUT NUMBER
             int inactiveRecordsParamIdx = idx;
             sb.append(", inactiveRecordsParamIdx(").append(inactiveRecordsParamIdx).append(")");
             idx++;
             cstmt.setNull(idx,Types.NUMERIC); //13 p_trxn IN NUMBER
             sb.append(", TransactionId(").append(idx).append(")=").append("");
             cstmt.registerOutParameter(idx, Types.INTEGER);   //13 p_trxn OUT NUMBER
             int transIdParamIdx = idx;
             sb.append(", transIdParamIdx(").append(transIdParamIdx).append(")");
             idx++;
             cstmt.registerOutParameter(idx, Types.DOUBLE);    //14 p_tag_activate_amt out number
             int tagActivateParamIdx = idx;
             sb.append(", tagActivateParamIdx(").append(tagActivateParamIdx).append(")");
             idx++;
             cstmt.registerOutParameter(idx, Types.DOUBLE);   //15 p_pending_dep_amt out number
             int pendingDepAmtParamIdx = idx;
             sb.append(", pendingDepAmtParamIdx(").append(pendingDepAmtParamIdx).append(")");
             idx++;
             cstmt.registerOutParameter(idx, Types.DOUBLE);   //16 p_min_bal_amt out number
             int minBalanceAmtParamIdx = idx;
             sb.append(", minBalanceAmtParamIdx(").append(minBalanceAmtParamIdx).append(")");
             idx++;
             cstmt.registerOutParameter(idx, Types.DOUBLE);  //17 p_total_amt_due out number
             int totalAmtDueParamIdx = idx;
             sb.append(", totalAmtDueParamIdx(").append(totalAmtDueParamIdx).append(")");
             idx++;
             cstmt.registerOutParameter(idx, Types.DOUBLE);   //18 p_report_id out number
             int reportIdParamIdx = idx;
             sb.append(", reportIdParamIdx(").append(reportIdParamIdx).append(")");
             idx++;
             cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_ARR" ); //19 MultipleVehicleARR out Multiple_vehicle_upload_arr
             int vehicleParamIdx = idx;
             sb.append(", vehicleParamIdx(").append(vehicleParamIdx).append(")");
             idx++;
             cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");            //20
             int errorParamIdx = idx;
             sb.append(", errorParamIdx(").append(errorParamIdx).append(")");
             sb.append(")");
             if (logger.isDebugEnabled())
                logger.debug("loadMultipleVehicles.cstmt=" + sb.toString());
             cstmt.execute();
             int result = cstmt.getInt(1);
             if (result == -1) {
                 logger.info("security exception: loadMultipleVehicles");
                 throw new EtccSecurityException("Security Exception in loadMultipleVehicles");
             } else if (result == 0) {
                //0 = Exception occurred and the data can not be submitted to the DB commit as is.
                //    Check the error message array for information
                 logger.error("loadMultipleVehicles.result=" + result);
                 Array errors = (Array)cstmt.getObject(errorParamIdx);
                 uploadRequest.setErrors(OracleUtil.convertToMessagesFromRecs(errors));
             }
             //1 = Success where there are no hard errors and the user can submit the data to be committed to the DB.
             uploadRequest.setTransactionId(cstmt.getLong(transIdParamIdx));
             uploadRequest.setTagActivateAmt(cstmt.getDouble(tagActivateParamIdx));
             uploadRequest.setPendingDepositAmt(cstmt.getDouble(pendingDepAmtParamIdx));
             uploadRequest.setMinBalAmt(cstmt.getDouble(minBalanceAmtParamIdx));
             uploadRequest.setTotalAmt(cstmt.getDouble(totalAmtDueParamIdx));
             uploadRequest.setHardErrorCount(cstmt.getInt(hardErrorParamIdx));
             uploadRequest.setVehicleAddCount(cstmt.getInt(addRecordsParamIdx));
             uploadRequest.setVehicleInactivateCount(cstmt.getInt(inactiveRecordsParamIdx));
             uploadRequest.setReportId(cstmt.getLong(reportIdParamIdx));
             uploadRequest.setMultiVehicleTags(convertVehicleRecsFile(cstmt.getArray(vehicleParamIdx)));
             if (logger.isDebugEnabled()) {
                 logger.debug("loadMultipleVehicles.return.uploadRequest=" + uploadRequest.toString(0));
             }
             return uploadRequest;
         } catch (SQLException sqle) {
             if (sqle.getErrorCode() == -20247){
                 throw new EtccSecurityException("Security exception in loadMultipleVehicles");
             }
             throw new EtccException("SQL Error running loadMultipleVehicles.sqle: " + sqle.getMessage(), sqle);
         } finally {
             close(cstmt);
         }
    }


    private Multiple_Vehicle_Upload_obj[] convertMultiVehicleTagsToDb(MultiVehicleDTO[] recs, long acctId)
            throws EtccException{

                try {
                	Multiple_Vehicle_Upload_obj[] result = null;
                    if (recs != null) {
                        result = new Multiple_Vehicle_Upload_obj[recs.length];
                        List<Multiple_Vehicle_Upload_obj> tempList = new ArrayList<Multiple_Vehicle_Upload_obj>();
                        MultiVehicleDTO uiDto;
                        for (int i=0; i<recs.length; i++) {
                            uiDto = recs[i];
                            StringBuilder sb = new StringBuilder();
                            sb.append("Account-").append(acctId);
                            sb.append(": MULTIPLE_VEHICLE_UPLOAD_OBJ[").append(i).append("]=[");
                            Multiple_Vehicle_Upload_obj temp = new Multiple_Vehicle_Upload_obj();
                            BigDecimal ROW_NO = new BigDecimal(uiDto.getRowNumber());
                            sb.append("ROW_NO=").append(ROW_NO.toString());
                            temp.setROW_NO(ROW_NO);
                            String ACTION = uiDto.getAction();
                            sb.append(", Veh_Action=").append(ACTION);
                            temp.setVEH_ACTION(ACTION);;
                            //Start defect#9521
                            //String REQUIREEZTAG = StringUtil.booleanToString(uiDto.isEzTagRequired());
                            String REQUIREEZTAG = uiDto.getEzTagEnter();
                            sb.append(", IS_EZTag=").append(uiDto.isEzTagRequired());
                            sb.append(", IS_EZTagEnter=").append(uiDto.getEzTagEnter());
                            temp.setIS_EZTAG(REQUIREEZTAG);
                            //Start defect#9521
                            String LIC_STATE = uiDto.getLicState();
                            sb.append(", LicensePlateState=").append(LIC_STATE);
                            temp.setLICENSEPLATESTATE(LIC_STATE);
                            String LIC_PLATE = uiDto.getLicPlate();
                            sb.append(", LicensePlateNumber=").append(LIC_PLATE);
                            temp.setLICENSEPLATENUMBER(LIC_PLATE);
                            //QC_12068 fix for vehicle class code issue
                            if (!StringUtils.isEmpty(uiDto.getVehicleClassCode())){
                            	temp.setVEH_CLASS_CODE(uiDto.getVehicleClassCode());
                            	sb.append(", Veh_Class_Code=").append(uiDto.getVehicleClassCode());
                            }else if (!StringUtils.isEmpty(uiDto.getVehicleClassDesc())){
	                            sb.append(", Veh_Class_Desc=").append(uiDto.getVehicleClassDesc());
	                            temp.setVEH_CLASS_DESC(uiDto.getVehicleClassDesc());
                            }
                            //QC_12068 fix for vehicle class code issue end
                            String VEHICLE_YEAR = uiDto.getVehicleYear();
                            sb.append(", VEHICLE_YEAR=").append(VEHICLE_YEAR);
                            temp.setVEHICLE_YEAR(VEHICLE_YEAR);
                            String VEHICLE_COLOR = uiDto.getVehicleColor();
                            sb.append(", VEHICLE_COLOR=").append(VEHICLE_COLOR);
                            temp.setVEHICLE_COLOR(VEHICLE_COLOR);
                            String VEHICLE_MAKE = uiDto.getVehicleMake();
                            sb.append(", VEHICLE_MAKE=").append(VEHICLE_MAKE);
                            temp.setVEHICLE_MAKE(VEHICLE_MAKE);
                            String VEHICLE_MODEL = uiDto.getVehicleModel();
                            sb.append(", VEHICLE_MODEL=").append(VEHICLE_MODEL);
                            temp.setVEHICLE_MODEL(VEHICLE_MODEL);
                            String VEHICLE_NICKNAME = uiDto.getNickname();
                            sb.append(", VEHICLE_NICKNAME=").append(VEHICLE_NICKNAME);
                            temp.setVEHICLE_NICKNAME(VEHICLE_NICKNAME);
                            //Start defect#9521
                            //String TEMP_LIC_PLATE = StringUtil.booleanToString(uiDto.isTemporaryLicPlate());
                            String TEMP_LIC_PLATE = uiDto.getTemporaryLicPlateEnter();
                            sb.append(", IS_Temporary_Plate=").append(uiDto.isTemporaryLicPlate());
                            sb.append(", IS_Temporary_PlateEnter=").append(uiDto.getTemporaryLicPlateEnter());
                            temp.setIS_TEMPORARY_PLATE(TEMP_LIC_PLATE);
                            //End defect#9521
                            tempList.add(temp);
                            sb.append("]");
                            if (logger.isInfoEnabled()) //defect#9521
                                logger.debug("convertMultiVehicleTagsToDb: " + sb.toString());
                        }
                        result = tempList.toArray(result);
                    } else {
                        logger.error("convertMultiVehicleTagsToDb.recs is null");
                    }
                    return result;
                } catch (SQLException e) {
                    throw new EtccException("Error copying multiple vehicle properties: " + e.getMessage(), e);
                }
        }


	@Override
	protected MultiVehicleDTO convertVehicleRec(Object vehicle) throws SQLException {
		Multiple_Vehicle_Upload_obj vehicleRec = (Multiple_Vehicle_Upload_obj)vehicle;
        MultiVehicleDTO vehicleDTO = null;
        if (vehicleRec != null) {
            vehicleDTO = new MultiVehicleDTO();
            StringBuilder sb = new StringBuilder();
            sb.append("MULTIPLE_VEHICLE_UPLOAD_OBJ=[");
            vehicleDTO.setRowNumber(vehicleRec.getROW_NO().intValue());
            sb.append("ROW_NO=").append(vehicleRec.getROW_NO().toString());
            if(vehicleRec.getVEH_ACTION()!=null){
	            vehicleDTO.setAction(vehicleRec.getVEH_ACTION());
	            sb.append(", Veh_Action=").append(vehicleRec.getVEH_ACTION());
            }else{
            	vehicleRec.setVEH_ACTION("");
            	vehicleDTO.setAction("");
            	sb.append(", Veh_Action=").append(vehicleRec.getVEH_ACTION());
            }
            vehicleDTO.setEzTagRequired(StringUtil.stringToBoolean(vehicleRec.getIS_EZTAG()));
            vehicleDTO.setEzTagEnter(vehicleRec.getIS_EZTAG()); //defect#9521
            sb.append(", IS_EZTag=").append(vehicleRec.getIS_EZTAG());
            vehicleDTO.setLicPlate(vehicleRec.getLICENSEPLATENUMBER());
            sb.append(", LicensePlateNumber=").append(vehicleRec.getLICENSEPLATENUMBER());
            vehicleDTO.setLicState(vehicleRec.getLICENSEPLATESTATE());
            sb.append(", LicensePlateState=").append(vehicleRec.getLICENSEPLATESTATE());
            //QC_12068 fix for vehicle class code issue
            if (!StringUtils.isEmpty(vehicleRec.getVEH_CLASS_CODE())){
             vehicleDTO.setVehicleClassCode(vehicleRec.getVEH_CLASS_CODE());
            }
            vehicleDTO.setVehicleClassDesc(vehicleRec.getVEH_CLASS_DESC());
            sb.append(", Veh_Class_Desc=").append(vehicleRec.getVEH_CLASS_DESC());
            vehicleDTO.setVehicleYear(vehicleRec.getVEHICLE_YEAR());
            sb.append(", VEHICLE_YEAR=").append(vehicleRec.getVEHICLE_YEAR());
            vehicleDTO.setVehicleColor(vehicleRec.getVEHICLE_COLOR());
            sb.append(", VEHICLE_COLOR=").append(vehicleRec.getVEHICLE_COLOR());
            vehicleDTO.setVehicleMake(vehicleRec.getVEHICLE_MAKE());
            sb.append(", VEHICLE_MAKE=").append(vehicleRec.getVEHICLE_MAKE());
            vehicleDTO.setVehicleModel(vehicleRec.getVEHICLE_MODEL());
            sb.append(", VEHICLE_MODEL=").append(vehicleRec.getVEHICLE_MODEL());
            vehicleDTO.setNickname(vehicleRec.getVEHICLE_NICKNAME());
            sb.append(", VEHICLE_NICKNAME=").append(vehicleRec.getVEHICLE_NICKNAME());
            vehicleDTO.setErrorType(vehicleRec.getERROR_TYPE());
            sb.append(", ERROR_TYPE=").append(vehicleRec.getERROR_TYPE());
            vehicleDTO.setErrorMsg(vehicleRec.getERROR_MSG());
            sb.append(", ERROR_MSG=").append(vehicleRec.getERROR_MSG());
            vehicleDTO.setTemporaryLicPlate(StringUtil.stringToBoolean(vehicleRec.getIS_TEMPORARY_PLATE()));
            vehicleDTO.setTemporaryLicPlateEnter(vehicleRec.getIS_TEMPORARY_PLATE());//defect#9521
            sb.append(", IS_Temporary_Plate=").append(vehicleRec.getIS_TEMPORARY_PLATE());
            sb.append("]");
            if (logger.isDebugEnabled())
                logger.debug("convertVehicleRecs(Array): " + sb.toString());
        }
        return vehicleDTO;
    }
	
    protected MultiVehicleDTO convertVehicleRecFile(Object vehicle) throws SQLException {
        MULTIPLE_VEHICLE_UPLOAD_REC vehicleRec = (MULTIPLE_VEHICLE_UPLOAD_REC)vehicle;
        MultiVehicleDTO vehicleDTO = null;
        if (vehicleRec != null) {
            vehicleDTO = new MultiVehicleDTO();
            StringBuilder sb = new StringBuilder();
            sb.append("MULTIPLE_VEHICLE_UPLOAD_REC=[");
            vehicleDTO.setReason(vehicleRec.getREASON());
            sb.append(", ACTION=").append(vehicleRec.getREASON());
            vehicleDTO.setLicPlate(vehicleRec.getLIC_PLATE());
            sb.append(", LIC_PLATE=").append(vehicleRec.getLIC_PLATE());
            vehicleDTO.setLicState(vehicleRec.getLIC_STATE());
            sb.append(", LIC_STATE=").append(vehicleRec.getLIC_STATE());
            vehicleDTO.setRawRecord(vehicleRec.getRAW_RECORD());
            sb.append(", RAW_RECORD=").append(vehicleRec.getRAW_RECORD());
            vehicleDTO.setErrorMsg(vehicleRec.getERROR_MSG());
            sb.append(", ERROR_MSG=").append(vehicleRec.getERROR_MSG());
            sb.append("]");
            if (logger.isDebugEnabled())
                logger.debug("convertVehicleRecs(Array): " + sb.toString());
        }
        return vehicleDTO;
    }

}
