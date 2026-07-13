/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountHistoryDAO;
import com.etcc.csc.dto.AccountDocument;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountReceiptDetailsDTO;
import com.etcc.csc.dto.AccountReceiptsDTO;
import com.etcc.csc.dto.AccountReceiptsDTO.AccountReceipt;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.AccountSummaryDTO;
import com.etcc.csc.dto.AccountTransaction;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.AccountTransactionHistory;
import com.etcc.csc.dto.AccountTransactionHistoryResponse;
import com.etcc.csc.dto.AccountYearlySummaryDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.InvoiceDatesDTO;
import com.etcc.csc.dto.MonthlyInvoicesDTO;
import com.etcc.csc.dto.ParkTransaction;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.dto.SummaryAvailableDTO;
import com.etcc.csc.dto.TransactionRecordsDTO;
import com.etcc.csc.dto.bean.AccountAlertDetailBean;
import com.etcc.csc.dto.bean.AccountSummaryDetailBean;
import com.etcc.csc.dto.bean.OlcAccountHistoryRecBean;
import com.etcc.csc.dto.bean.OlcAccountTagRecBean;
import com.etcc.csc.dto.bean.OlcAcctSummaryRecBean;
import com.etcc.csc.dto.bean.OlcCorpInvRecP;
import com.etcc.csc.dto.bean.OlcTagSummaryRecBean;
import com.etcc.csc.dto.bean.OlcViewMonthlyRecBean;
import com.etcc.csc.dto.bean.TxnTypeBean;
import com.etcc.csc.dto.bean.VehicleNickNameBean;
import com.etcc.csc.plsql.OLCSC_REP;
import com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_ARR;
import com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_REC;
import com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT;
import com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR;
import com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_REC;
import com.etcc.csc.plsql.OLC_ACCOUNT_TAG_REC;
import com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_ARR;
import com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_REC;
import com.etcc.csc.plsql.OLC_ACCT_ALERT_REC;
import com.etcc.csc.plsql.OLC_ACCT_SUMMARY_REC;
import com.etcc.csc.plsql.OLC_ACCT_SUM_ARR;
import com.etcc.csc.plsql.OLC_ACCT_SUM_TYPE_REC;
import com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR;
import com.etcc.csc.plsql.OLC_ALERT_DISPLAY_REC;
import com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_ARR;
import com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_REC;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;
import com.etcc.csc.plsql.OLC_HAS_PARK_RECEIPT_ARR;
import com.etcc.csc.plsql.OLC_HAS_PARK_RECEIPT_REC;
import com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_ARR;
import com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_REC;
import com.etcc.csc.plsql.OLC_STTMT_DATE_REC;
import com.etcc.csc.plsql.OLC_TAG_SUMMARY_REC;
import com.etcc.csc.plsql.OLC_VIEWTXN_TXNTYPE_ARR;
import com.etcc.csc.plsql.OLC_VIEWTXN_TXNTYPE_REC;
import com.etcc.csc.plsql.OLC_VIEW_MONTHLY_REC;
import com.etcc.csc.plsql.OLC_VIEW_RECEIPT_HEAD_ARR;
import com.etcc.csc.plsql.OLC_VIEW_RECEIPT_HEAD_REC;
import com.etcc.csc.util.WSDateUtil;

/**
 * Oracle implementation of AccountHistoryDAO.  Copied from OLCSCService com.etcc.csc.dao.OracleNewAccountHistoryDAO.
 * @author Stephen Davidson
 */
public class OracleAccountHistoryDAO extends AccountHistoryDAO {
    private static final Logger logger = Logger.getLogger(OracleAccountHistoryDAO.class);

    public String getReportFilePath(AccountLoginDTO accountLoginDTO) throws EtccException, EtccReportException {
        String filePath = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn
                    .prepareCall("{call olcsc_param.get_report_url(p_report_name=>?,o_report_url=>?,O_ERROR_MSG_ARR=>?)}");

            setTypeMap();

            // cstmt.registerOutParameter(1, Types.SMALLINT);

            cstmt.setString("p_report_name", "TEST-REPORT_NAME");

            cstmt.registerOutParameter("o_report_url", Types.VARCHAR);
            cstmt.registerOutParameter("O_ERROR_MSG_ARR", Types.ARRAY, "OLC_ERROR_MSG_ARR");

            cstmt.execute();
            int result = cstmt.getInt(1);

            if (result == 1) {
                filePath = cstmt.getString(4);
            } else {
                throw new EtccReportException("Failed to retrieve file path");
            }

        } catch (SQLException t) {
            String message = "getReportFilePath.SQLException: " + t.getMessage();
            throw new EtccReportException(message, t);
        }

        return filePath;
    }

    public AccountStatementDTO getStatementHTML(AccountLoginDTO accountLoginDTO, String postDate, String acctVPNType)
            throws EtccException, EtccSecurityException {

        AccountStatementDTO statementDTO = null;
        CallableStatement cstmt = null;
        try {
        	//Defect 9973 txphung: add more a parameters
            cstmt = this.conn
                    .prepareCall("{? = call olcsc_rep.view_statements_HTML(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_VIEW_MONTHLY_REC", OLC_VIEW_MONTHLY_REC.class);
            typeMap.put("OL_OWNER.OLC_TAG_SUMMARY_REC", OLC_TAG_SUMMARY_REC.class);
            typeMap.put("OL_OWNER.OLC_ACCT_SUMMARY_REC", OLC_ACCT_SUMMARY_REC.class);
            typeMap.put("OL_OWNER.OLC_ACCT_ALERT_REC", OLC_ACCT_ALERT_REC.class);		//Defect 9973 txphung

            cstmt.registerOutParameter(1, Types.SMALLINT);

            cstmt.setString(2, Long.toString(accountLoginDTO.getAcctId()));
            cstmt.setString(3, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(4, accountLoginDTO.getLoginId());
            cstmt.setString(5, accountLoginDTO.getDbSessionId());
            cstmt.setString(6, accountLoginDTO.getLastLoginIp());
            cstmt.setString(7, postDate);
            cstmt.setString(8, "HTML");
            cstmt.setString(9, acctVPNType);

            cstmt.registerOutParameter(10, Types.VARCHAR);
            cstmt.registerOutParameter(11, Types.VARCHAR);
            cstmt.registerOutParameter(12, Types.ARRAY, "OL_OWNER.OLC_VIEW_MONTHLY_ARR");
            cstmt.registerOutParameter(13, Types.ARRAY, "OL_OWNER.OLC_TAG_SUMMARY_ARR");
            cstmt.registerOutParameter(14, Types.ARRAY, "OL_OWNER.OLC_ACCT_SUMMARY_ARR");
            cstmt.registerOutParameter(15, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.registerOutParameter(16, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR");	//Defect 9973 txphung
            boolean traceEnabled = logger.isTraceEnabled();
            if (traceEnabled)
                logger.trace("AcctId:" + accountLoginDTO.getAcctId() + " PostDate:" + postDate + "****"
                        + "Start calling statement HTML api");
            cstmt.executeUpdate();
            if (traceEnabled)
                logger.trace("AcctId:" + accountLoginDTO.getAcctId() + " PostDate:" + postDate + "****"
                        + "End calling statement HTML api");

            statementDTO = new AccountStatementDTO();

            int result = cstmt.getInt(1);
            if (result == -1) {
                if (traceEnabled)
                    logger.trace("AcctId:" + accountLoginDTO.getAcctId() + " PostDate:" + postDate
                            + "*****getStatementHTML result is -1");
                throw new EtccSecurityException("security exception in getStatementHTML()");
            } else if (result == 0) {
                if (traceEnabled)
                    logger.trace("AcctId:" + accountLoginDTO.getAcctId() + " PostDate:" + postDate + "****"
                            + "Error getting html statements");
                //#5434 getObject(18) => getObject(15)
                Array errors = (Array) cstmt.getObject(15);
                statementDTO.setErrors(OracleUtil.convertToMessages(errors));
                
                //Defect 9973 txphung: begin
                Array alerts = cstmt.getArray(16);
                if(alerts != null){
                	statementDTO.setAlerts(OracleUtil.convertToAlerts(alerts));					
				}
                //Defect 9973 txphung: end
                //Bug?  Why is this commented out?
                //#5434 - uncomment this return statement.
                return statementDTO;
            }

            String endingBalance = cstmt.getString(10);
            String lastDayOfMonth = cstmt.getString(11);

            statementDTO.setEndingBalance(endingBalance);
            statementDTO.setLastDayOfMonth(lastDayOfMonth);

            Object[] details = (Object[]) cstmt.getArray(12).getArray(this.conn.getTypeMap());
            if (details != null && details.length > 0) {
                OlcViewMonthlyRecBean[] olcViewMonthlyRecBeans = new OlcViewMonthlyRecBean[details.length];
                for (int i = 0; i < details.length; i++) {
                    olcViewMonthlyRecBeans[i] = convert((OLC_VIEW_MONTHLY_REC) details[i]);
                }
                statementDTO.setRecords(olcViewMonthlyRecBeans);
                statementDTO.setRecordsHAS(olcViewMonthlyRecBeans);
            }

//            Object[] detailsHAS = (Object[]) cstmt.getArray(13).getArray(this.conn.getTypeMap());
//            if (detailsHAS != null && detailsHAS.length > 0) {
//                OlcViewMonthlyRecBean[] olcViewMonthlyRecBeans = new OlcViewMonthlyRecBean[detailsHAS.length];
//                for (int i = 0; i < detailsHAS.length; i++) {
//                    olcViewMonthlyRecBeans[i] = convert((OLC_VIEW_MONTHLY_REC) detailsHAS[i]);
//                }
//                statementDTO.setRecordsHAS(olcViewMonthlyRecBeans);
//            }

            Object[] tagSummary = (Object[]) cstmt.getArray(13).getArray(this.conn.getTypeMap());
            if (tagSummary != null && tagSummary.length > 0) {
                OlcTagSummaryRecBean[] olcTagSummaryRecs = new OlcTagSummaryRecBean[tagSummary.length];
                for (int i = 0; i < tagSummary.length; i++) {
                    olcTagSummaryRecs[i] = convert((OLC_TAG_SUMMARY_REC) tagSummary[i]);
                }

                statementDTO.setTagSummary(olcTagSummaryRecs);
                statementDTO.setTagSummaryHAS(olcTagSummaryRecs);
            }

//            Object[] tagSummaryHAS = (Object[]) cstmt.getArray(15).getArray(this.conn.getTypeMap());
//            if (tagSummaryHAS != null && tagSummaryHAS.length > 0) {
//                OlcTagSummaryRecBean[] olcTagSummaryRecs = new OlcTagSummaryRecBean[tagSummaryHAS.length];
//                for (int i = 0; i < tagSummaryHAS.length; i++) {
//                    olcTagSummaryRecs[i] = convert((OLC_TAG_SUMMARY_REC) tagSummaryHAS[i]);
//                }
//
//
//            }

            Object[] acctSummary = (Object[]) cstmt.getArray(14).getArray(this.conn.getTypeMap());
            if (acctSummary != null && acctSummary.length > 0) {
                OlcAcctSummaryRecBean[] olcAcctSummaryRecs = new OlcAcctSummaryRecBean[acctSummary.length];
                for (int i = 0; i < acctSummary.length; i++) {
                    olcAcctSummaryRecs[i] = convert((OLC_ACCT_SUMMARY_REC) acctSummary[i]);
                }
                statementDTO.setAcctSummary(olcAcctSummaryRecs);
                statementDTO.setAcctSummaryHAS(olcAcctSummaryRecs);
            }

//            Object[] acctSummaryHAS = (Object[]) cstmt.getArray(17).getArray(this.conn.getTypeMap());
//            if (acctSummaryHAS != null && acctSummaryHAS.length > 0) {
//                OlcAcctSummaryRecBean[] olcAcctSummaryRecs = new OlcAcctSummaryRecBean[acctSummaryHAS.length];
//                for (int i = 0; i < acctSummaryHAS.length; i++) {
//                    olcAcctSummaryRecs[i] = convert((OLC_ACCT_SUMMARY_REC) acctSummaryHAS[i]);
//                }
//
//            }
        } catch (SQLException e) {
                String message = "Error retrieving statement - AcctId:" + accountLoginDTO.getAcctId() + " PostDate:" + postDate + "*****"
                        + "SQLException occured in getStatementHTML()" + e.getMessage();
            throw new EtccException(message, e);
        }

        return statementDTO;
    }

    private OlcAcctSummaryRecBean convert(OLC_ACCT_SUMMARY_REC theInput) throws SQLException {
        if (theInput == null)
            return null;
        OlcAcctSummaryRecBean theOutput = new OlcAcctSummaryRecBean();
        theOutput.setAmount(theInput.getAMOUNT());
        theOutput.setDescription(theInput.getDESCRIPTION());
        theOutput.setQuantity(theInput.getQUANTITY());
        return theOutput;
    }

    private OlcTagSummaryRecBean convert(OLC_TAG_SUMMARY_REC theInput) throws SQLException {
        if (theInput == null)
            return null;
        OlcTagSummaryRecBean theOutput = new OlcTagSummaryRecBean();
        theOutput.setAmount(theInput.getAMOUNT());
        theOutput.setDescription(theInput.getDESCRIPTION());
        theOutput.setLicensePlate(theInput.getLICENSE_PLATE());
        theOutput.setQuantity(theInput.getQUANTITY());
        theOutput.setTagId(theInput.getTAG_ID());
        theOutput.setUnitId(theInput.getUNIT_ID());
        return theOutput;
    }

    private OlcViewMonthlyRecBean convert(OLC_VIEW_MONTHLY_REC theInput) throws SQLException {
        if (theInput == null)
            return null;
        OlcViewMonthlyRecBean theOutput = new OlcViewMonthlyRecBean();
        theOutput.setAmount(theInput.getAMOUNT());
        theOutput.setDescription(theInput.getDESCRIPTION());
        theOutput.setDirection(theInput.getDIRECTION());
        theOutput.setLane(theInput.getLANE());
        theOutput.setLaneFullName(theInput.getLANE_FULL_NAME());
        theOutput.setLicensePlate(theInput.getLICENSE_PLATE());
        theOutput.setLocation(theInput.getLOCATION());
        theOutput.setPostedDate(convert(theInput.getPOSTED_DATE()));
        theOutput.setSerialNumber(theInput.getSERIAL_NUMBER());
        theOutput.setTagId(theInput.getTAG_ID());
        theOutput.setTransactionDate(convert(theInput.getTRANSACTION_DATE()));
        theOutput.setUnitId(theInput.getUNIT_ID());
        //theOutput.setVehicleClassCode(theInput.getVEHICLE_CLASS_CODE());
        return theOutput;
    }

    public StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO, boolean isMonthly)
            throws EtccException, EtccSecurityException {

        StatementDatesDTO statementDatesDTO;
        CallableStatement cstmt = null;
        try {
            if (isMonthly)
                cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Statement_Dates(?,?,?,?,?,?)}");
            else
                cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Statement_Years(?,?,?,?,?,?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_STTMT_DATE_REC", OLC_STTMT_DATE_REC.class);

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, accountLoginDTO.getDbSessionId());
            cstmt.setLong(3, accountLoginDTO.getAcctId());
            cstmt.setString(4, accountLoginDTO.getLoginId());
            cstmt.setString(5, accountLoginDTO.getLastLoginIp());
            cstmt.registerOutParameter(6, Types.ARRAY, "OL_OWNER.OLC_STTMT_DATES_ARR");
            cstmt.registerOutParameter(7, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            final boolean infoEnabled = logger.isInfoEnabled();
            if (infoEnabled)
                logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****"
                        + "start retrieving statement dates...");
            cstmt.executeUpdate();
            if (infoEnabled)
                logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****" + "statement dates retrieved...");

            statementDatesDTO = new StatementDatesDTO();
            int result = cstmt.getInt(1);
            if (result == -1) {
                if (infoEnabled)
                    logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****"
                            + "getStatementDates:Security Exception");
                throw new EtccSecurityException("security exception");
            } else if (result == 0) {
                if (infoEnabled)
                    logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****"
                            + "error retrieveing statement dates.");
                return null;
            }

            Array dates = (Array) cstmt.getObject(6);
            statementDatesDTO.setDates(getDateList(dates, accountLoginDTO.getAcctId()));
            if (logger.isDebugEnabled())
                logger.debug("AcctId:" + accountLoginDTO.getAcctId() + "****" + "getStatementDates: Success");
        } catch (SQLException e) {
            String message = "Error retrieveing statement dates - AcctId:" + accountLoginDTO.getAcctId() + "****"
                    + "SQLException Occurred in getStatementDates()" + e.getMessage();
            throw new EtccException(message, e);
        }

        return statementDatesDTO;
    }

    public SummaryAvailableDTO hasLastYearSummary(AccountLoginDTO accountLoginDTO) throws EtccException,
            EtccSecurityException {
        SummaryAvailableDTO summaryAvailDTO;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Has_Last_Year_Stmt(?,?,?,?,?,?)}");
            setTypeMap();
            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, accountLoginDTO.getDbSessionId());
            cstmt.setLong(3, accountLoginDTO.getAcctId());
            cstmt.setString(4, accountLoginDTO.getLoginId());
            cstmt.setString(5, accountLoginDTO.getLastLoginIp());
            cstmt.registerOutParameter(6, Types.VARCHAR);
            cstmt.registerOutParameter(7, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.executeUpdate();

            summaryAvailDTO = new SummaryAvailableDTO();
            int result = cstmt.getInt(1);
            boolean traceEnabled = logger.isTraceEnabled();
            if (result == -1) {
                if (traceEnabled)
                    logger.trace("AcctId:" + accountLoginDTO.getAcctId() + "****"
                            + "hasLastYearSummary:Security Exception");
                throw new EtccSecurityException("security exception in hasLastYearSummary()");
            } else if (result == 0) {
                summaryAvailDTO.setSummaryAvail(false);
            }

            String available = cstmt.getString(6);
            if (traceEnabled)
                logger.trace("AcctId:" + accountLoginDTO.getAcctId() + "****" + "Last year summary available:"
                        + available);
            summaryAvailDTO.setSummaryAvail(available.equals("1"));
        } catch (SQLException e) {
            String message = "Error retrieveing \"hasLastYearSummary\" - AcctId:" + accountLoginDTO.getAcctId() + "****"
            + "SQLException Occurred in hasLastYearSummary():" + e.getMessage();
            throw new EtccException(message, e);
        }

        return summaryAvailDTO;
    }

    public OlcAccountTagRecBean[] getAcccountTags(AccountLoginDTO accountLoginDTO) throws EtccException,
            EtccSecurityException {
        OlcAccountTagRecBean[] olcAccountTagRecs = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.Get_Tag_Info(?,?,?,?,?,?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC", OLC_ACCOUNT_TAG_REC.class);

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, accountLoginDTO.getDbSessionId());
            cstmt.setLong(3, accountLoginDTO.getAcctId());
            cstmt.setString(4, accountLoginDTO.getLoginId());
            cstmt.setString(5, accountLoginDTO.getLastLoginIp());
            cstmt.registerOutParameter(6, Types.ARRAY, "OL_OWNER.OLC_ACCOUNT_TAG_ARR");
            cstmt.registerOutParameter(7, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            if (cstmt.getInt(1) == 1) {
                Object[] objArray = (Object[]) cstmt.getArray(6).getArray(this.conn.getTypeMap());
                if (objArray != null && objArray.length > 0) {
                    olcAccountTagRecs = new OlcAccountTagRecBean[objArray.length];

                    for (int i = 0; i < objArray.length; i++) {
                        olcAccountTagRecs[i] = convert((OLC_ACCOUNT_TAG_REC) objArray[i]);
                    }
                }
            }
        } catch (SQLException t) {
            throw new EtccException(t.getMessage(), t);
        }
        return olcAccountTagRecs;
    }

    private OlcAccountTagRecBean convert(OLC_ACCOUNT_TAG_REC theInput) throws SQLException {
        if (theInput == null)
            return null;
        OlcAccountTagRecBean theOutput = new OlcAccountTagRecBean();
        theOutput.setAcctTagSeq(theInput.getACCT_TAG_SEQ());
        theOutput.setAcctTagStatus(theInput.getACCT_TAG_STATUS());
        theOutput.setAgencyDesc(theInput.getAGENCY_DESC());
        theOutput.setAgencyId(theInput.getAGENCY_ID());
        theOutput.setBarcodePrefix(theInput.getBARCODE_PREFIX());
        theOutput.setLicPlate(theInput.getLIC_PLATE());
        theOutput.setLicState(theInput.getLIC_STATE());
        theOutput.setPlateExpirDate(convert(theInput.getPLATE_EXPIR_DATE()));
        theOutput.setTagId(theInput.getTAG_ID());
        theOutput.setTagStatusDesc(theInput.getTAG_STATUS_DESC());
        theOutput.setTempPlateFlag(theInput.getTEMP_PLATE_FLAG());
        theOutput.setVehicleClassCode(theInput.getVEHICLE_CLASS_CODE());
        theOutput.setVehicleClassDesc(theInput.getVEHICLE_CLASS_DESC());
        theOutput.setVehicleColor(theInput.getVEHICLE_COLOR());
        theOutput.setVehicleMake(theInput.getVEHICLE_MAKE());
        theOutput.setVehicleModel(theInput.getVEHICLE_MODEL());
        theOutput.setVehicleYear(theInput.getVEHICLE_YEAR());
        return theOutput;
    }

    /*
     * (result)	number
    p_doc_id                  accounts.acct_id%type,
    p_doc_type                VARCHAR2 default 'AC',
    p_user_id                 VARCHAR2,
    p_session_id              VARCHAR2,
    p_ip_address              VARCHAR2,
    p_begin_date              DATE,
    p_end_date                DATE,
    p_acct_VPN_type           VARCHAR2,
    p_agency_id               account_tags.agency_id%type,
    p_tag_id                  account_tags.tag_id%type,
    p_trans_type_id           transaction_types.trans_type_id%type,
    p_called_from_ui boolean  default true,
    o_olc_acc_history_arr     OUT olc_account_history_arr,
    o_error_msg_arr           OUT OLC_ERROR_MSG_ARR
     */
	 //QC_11613 dateType field added
    public TransactionRecordsDTO getTransactionRecords(AccountLoginDTO accountLoginDTO, Calendar startDate,
            Calendar endDate, String agencyId, String tagId, String transFilter, String nickName, String nickNameType,String dateType)
            throws EtccException, EtccSecurityException {
        OlcAccountHistoryRecBean[] accountHistoryBeans = null;
        // OlcAccountHistoryRecBean[] parkingHistBeans = null;
        TransactionRecordsDTO recordsDTO = null;
        try {
            long docId = accountLoginDTO.getAcctId();
            String docType = AccountLoginDTO.LoginType.AC.toString();
            String loginId = accountLoginDTO.getLoginId();
            String dbSessionId = accountLoginDTO.getDbSessionId();
            String lastLoginIp = accountLoginDTO.getLastLoginIp();

            Timestamp startTS = null;
            if (startDate != null)
                startTS = new Timestamp(startDate.getTime().getTime());

            Timestamp endTS = null;
            if (endDate != null)
                endTS = new Timestamp(endDate.getTime().getTime());

            OLC_ACCOUNT_HISTORY_ARR[] O_OLC_ACCOUNT_HISTORY_ARR = new OLC_ACCOUNT_HISTORY_ARR[] { new OLC_ACCOUNT_HISTORY_ARR() };

            // OLC_ACCOUNT_HISTORY_ARR[] O_OLC_PARKING_HIST_ARR =
            // new OLC_ACCOUNT_HISTORY_ARR[] {new OLC_ACCOUNT_HISTORY_ARR()};

            BigDecimal O_TOTAL[] = new BigDecimal[1];

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            boolean debugEnabled = logger.isDebugEnabled();
            if (debugEnabled) {
            	StringBuilder sb = new StringBuilder();
            	sb.append("getTransactionRecords: ").append("OLCSC_REP.VIEW_TXNS(");
            	sb.append("docId=").append(docId);
            	sb.append(", docType=").append(docType);
            	sb.append(", loginId=").append(loginId);
            	sb.append(", dbSessionId=").append(dbSessionId);
            	sb.append(", lastLoginIp=").append(lastLoginIp);
            	sb.append(", startTS=").append(startTS);
            	sb.append(", endTS=").append(endTS);
            	sb.append(", transFilter=").append(transFilter);
            	sb.append(", nickName=").append(nickName);
            	sb.append(", nickNameType=").append(nickNameType);
            	sb.append(", O_OLC_ACCOUNT_HISTORY_ARR");
            	// O_OLC_PARKING_HIST_ARR,
            	sb.append(", O_TOTAL, O_ERROR_MSG_ARR");
            	sb.append(")");
                logger.debug(sb.toString());
            }
            int result = new OLCSC_REP(this.conn).VIEW_TXNS(new BigDecimal(docId), docType, loginId, dbSessionId,
                    lastLoginIp, dateType,startTS, endTS, transFilter, nickName, nickNameType, O_OLC_ACCOUNT_HISTORY_ARR,
                    // O_OLC_PARKING_HIST_ARR,
                    O_TOTAL, O_ERROR_MSG_ARR).intValue();

            recordsDTO = new TransactionRecordsDTO();
            if (debugEnabled)
                logger.debug("getTransactionRecords.result: " + result);

            if (result == 0) {
                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                recordsDTO.setErrors(OracleUtil.convertToMessages(errorArray));
            } else if (result == -1) {
                if (logger.isInfoEnabled())
                    logger.info("acctId:" + accountLoginDTO.getAcctId() + "****"
                            + "getTransactionRecords result is -1");
                throw new EtccSecurityException("security exception in getTransactionRecords()");
            }

            if (result != -1) {
                recordsDTO.setTotalAmt(O_TOTAL[0].doubleValue());
                OLC_ACCOUNT_HISTORY_REC[] acctHistory = O_OLC_ACCOUNT_HISTORY_ARR[0].getArray();
                if (acctHistory != null && acctHistory.length > 0) {
                    accountHistoryBeans = new OlcAccountHistoryRecBean[acctHistory.length];

                    for (int i = 0; i < acctHistory.length; i++) {
                        accountHistoryBeans[i] = convert(acctHistory[i]);
                    }
                }

                // OLC_ACCOUNT_HISTORY_REC[] parkingHist = O_OLC_PARKING_HIST_ARR[0].getArray() ;
                // if (parkingHist != null && parkingHist.length >0)
                // {
                // parkingHistBeans = new OlcAccountHistoryRecBean[parkingHist.length];
                //
                // for (int i=0; i<parkingHist.length; i++)
                // {
                // parkingHistBeans[i] = new OlcAccountHistoryRecBean((OLC_ACCOUNT_HISTORY_REC)parkingHist[i]);
                // }
                //
                // }
            }

            // StringBuilder call = new StringBuilder();
            // call.append( "{? = call OLCSC_REP.view_txns(" );
            // call.append("p_doc_id=>?,");
            // call.append("p_doc_type=>?,");
            // call.append("p_user_id=>?,");
            // call.append("p_session_id=>?,");
            // call.append("p_ip_address=>?,");
            // call.append("p_begin_date=>?,");
            // call.append("p_end_date=>?,");
            // call.append("p_acct_VPN_type=>?,");
            // call.append("p_agency_id=>?,");
            // call.append("p_tag_id=>?,");
            // call.append("p_trans_type_id=>?,");
            // call.append("o_olc_acc_history_arr=>?,");
            // call.append("o_error_msg_arr=>?");
            // call.append(")}");
            //
            // cstmt = conn.prepareCall(call.toString());
            // setErrorTypeMap();
            // conn.getTypeMap().put("OL_OWNER.OLC_ACCOUNT_HISTORY_REC",
            // OlcAccountHistoryRec.class);
            //
            // cstmt.registerOutParameter(1, Types.SMALLINT);
            // cstmt.setLong(2, accountLoginDTO.getAcctId());
            // cstmt.setString(3, Constants.LOGIN_TYPE_ACCOUNT );
            // cstmt.setString(4, accountLoginDTO.getLoginId() );
            // cstmt.setString(5, accountLoginDTO.getDbSessionId());
            // cstmt.setString(6, accountLoginDTO.getLastLoginIp());
            // if (startDate!=null)
            // {
            // startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE),
            // 0, 0, 0);
            // cstmt.setTimestamp(7, new java.sql.Timestamp( startDate.getTime().getTime( ) ) );
            // }
            // else
            // cstmt.setTimestamp(7, null);
            //
            // if (endDate!=null)
            // {
            // endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE), 23,
            // 59,59);
            // cstmt.setTimestamp(8, new java.sql.Timestamp( endDate.getTime( ).getTime() ) );
            // }
            // else
            // cstmt.setTimestamp(8, null);
            //
            // cstmt.setString(9, acctVPNType);
            // cstmt.setString(10, agencyId );
            // cstmt.setString(11, tagId );
            //
            // if (transTypeId != null)
            // cstmt.setInt(12, Integer.parseInt( transTypeId ) );
            // else
            // cstmt.setInt(12, 0);
            //
            // cstmt.registerOutParameter(13, Types.ARRAY,
            // "OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
            // cstmt.registerOutParameter(14, Types.ARRAY,
            // "OL_OWNER.OLC_ERROR_MSG_ARR");
            //
            // logger.info("acctId:"+ accountLoginDTO.getAcctId()+"****"+ "start retrieving transactions...");
            // cstmt.execute();
            // logger.info("acctId:"+ accountLoginDTO.getAcctId()+"****"+"transactions retrieved");
            //
            // recordsDTO = new TransactionRecordsDTO();
            //
            // int result = cstmt.getInt(1);
            //
            // if (result == -1){
            // logger.info("acctId:"+ accountLoginDTO.getAcctId()+"****"+"getTransactionRecords result is -1");
            // throw new EtccSecurityException("security exception in getStatementHTML()");
            // }
            // else if(result==0) {
            // logger.info("acctId:"+ accountLoginDTO.getAcctId()+"****"+ "getTransactionRecords result is 0");
            // Array errors = (Array) cstmt.getObject(14);
            // recordsDTO.setErrors(convertErrorMsgs(errors));
            // // return recordsDTO;
            // }
            //
            // if (cstmt.getArray(13)!=null)
            // {
            // Object[] objArray = (Object[]) cstmt.getArray(13).getArray(conn.getTypeMap());
            //
            // if (objArray != null && objArray.length > 0)
            // {
            // olcAccountHistoryRecBeans = new OlcAccountHistoryRecBean[objArray.length];
            //
            // for (int i = 0; i < objArray.length; i++)
            // {
            // olcAccountHistoryRecBeans[i] =
            // new OlcAccountHistoryRecBean((OlcAccountHistoryRec)objArray[i]);
            // }
            // }
            // }
        } catch (SQLException t) {
            throw new EtccException("Error in account history DAO getTransactionRecords - acctId:" + accountLoginDTO.getAcctId() + "****: " + t.getMessage(), t);
        }

        recordsDTO.setRecords(accountHistoryBeans);
        // recordsDTO.setParkingRecords(parkingHistBeans);
        return recordsDTO;
    }

    private OlcAccountHistoryRecBean convert(OLC_ACCOUNT_HISTORY_REC theInput) throws SQLException {
        if (theInput == null)
            return null;
        OlcAccountHistoryRecBean theOutput = new OlcAccountHistoryRecBean();
        theOutput.setAmount(theInput.getAMOUNT());
        theOutput.setDirection(theInput.getDIRECTION());
        theOutput.setLaneFullName(theInput.getLANE_FULL_NAME());
        theOutput.setLaneName(theInput.getLANE_NAME());
        theOutput.setLicensePlate(theInput.getLICENSE_PLATE());
        theOutput.setLicenseState(theInput.getLICENSE_STATE());
        theOutput.setLocationName(theInput.getLOCATION_NAME());
        theOutput.setVehicleNickName(theInput.getNICKNAME());
        theOutput.setParkingReportUrl(theInput.getPARKING_REPORT_URL());
        theOutput.setPostedDate(convert(theInput.getPOSTED_DATE()));
        theOutput.setSerialNum(theInput.getSERIAL_NUM());
        theOutput.setTagId(theInput.getTAG_ID());
        theOutput.setTransType(theInput.getTRANS_TYPE());
        theOutput.setTrxnDate(convert(theInput.getTRXN_DATE()));
        theOutput.setUnitId(theInput.getUNIT_ID());
        theOutput.setVehicleClassCode(theInput.getVEHICLE_CLASS_CODE());
        return theOutput;
    }

    // private ErrorMessageDTO[] convert(OLC_ERROR_MSG_REC[] theInput) throws SQLException {
    // if (theInput == null)
    // return null;
    // ArrayList<ErrorMessageDTO> theOutput = new ArrayList<ErrorMessageDTO>();
    // for (int i = 0; i < theInput.length; i++) {
    // theOutput.add(convert(theInput[i]));
    // }
    // return theOutput.toArray(new ErrorMessageDTO[theOutput.size()]);
    // }
    //
    // private ErrorMessageDTO convert(OLC_ERROR_MSG_REC theInput) throws SQLException {
    // if (theInput == null)
    // return null;
    // ErrorMessageDTO theOutput = new ErrorMessageDTO();
    // theOutput.setMessage(theInput.getERROR_MSG());
    // return theOutput;
    // }

    // private ErrorMessageDTO[] convertErrorMsgs(Array errors) throws SQLException {
    // ArrayList<ErrorMessageDTO> result = null;
    // if (errors != null)
    // {
    // Object array[] = (Object[])errors.getArray();
    // if (array != null && array.length >= 0) {
    // result = new ArrayList<ErrorMessageDTO>();
    // for (int i=0; i<array.length; i++) {
    // ErrorMsgRec msgRec = (ErrorMsgRec) array[i];
    // if (msgRec != null) {
    // ErrorMessageDTO resultItem = new ErrorMessageDTO();
    // resultItem.setMessage(msgRec.getErrMsg());
    // result.add(resultItem);
    // }
    // }
    // }
    // }
    // else {
    // logger.info("error array is null");
    // }
    // if (result == null)
    // return null;
    // else
    // return result.toArray(new ErrorMessageDTO[result.size()]);
    // }

    public AccountYearlySummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO, String postDate)
            throws EtccException, EtccSecurityException {
        AccountYearlySummaryDTO summaryDTO = null;
        CallableStatement cstmt = null;
        try {

            String timeout = getConnectionTimeout();

            cstmt = this.conn
                    .prepareCall("{? = call olcsc_rep.View_Summary_HTML(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            if (timeout != null) {
                int iTimeout = Integer.parseInt(timeout);
                cstmt.setQueryTimeout(iTimeout);
            }

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_TAG_SUMMARY_REC", OLC_TAG_SUMMARY_REC.class);
            typeMap.put("OL_OWNER.OLC_ACCT_SUMMARY_REC", OLC_ACCT_SUMMARY_REC.class);

            cstmt.registerOutParameter(1, Types.SMALLINT);

            cstmt.setString(2, Long.toString(accountLoginDTO.getAcctId()));
            cstmt.setString(3, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(4, accountLoginDTO.getLoginId());
            cstmt.setString(5, accountLoginDTO.getDbSessionId());
            cstmt.setString(6, accountLoginDTO.getLastLoginIp());
            cstmt.setString(7, postDate);
            cstmt.setString(8, "HTML");

            cstmt.registerOutParameter(9, Types.VARCHAR);
            cstmt.registerOutParameter(10, Types.VARCHAR);
            cstmt.registerOutParameter(11, Types.ARRAY, "OL_OWNER.OLC_TAG_SUMMARY_ARR");
            cstmt.registerOutParameter(12, Types.ARRAY, "OL_OWNER.OLC_ACCT_SUMMARY_ARR");
            cstmt.registerOutParameter(13, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            if (logger.isInfoEnabled())
                logger.info("acctId:" + accountLoginDTO.getAcctId() + "****" + "Start calling summary HTML api");
            cstmt.executeUpdate();
            if (logger.isInfoEnabled())
                logger.info("acctId:" + accountLoginDTO.getAcctId() + "****" + "End calling summary HTML api");

            summaryDTO = new AccountYearlySummaryDTO();

            int result = cstmt.getInt(1);
            if (result == -1) {
                if (logger.isInfoEnabled())
                    logger.info("acctId:" + accountLoginDTO.getAcctId() + "****" + "getSummaryHTML result is -1");
                throw new EtccSecurityException("security exception in getSummaryHTML");
            } else if (result == 0) {
                Array errors = (Array) cstmt.getObject(13);
                summaryDTO.setErrors(OracleUtil.convertToMessages(errors));
                if (logger.isInfoEnabled()) {
                    logger.info("acctId:" + accountLoginDTO.getAcctId() + "****" + "getSummaryHTML result is 0");
                    logger.info("Errors: " + ErrorMessageDTO.toStringBuilder(summaryDTO.getErrors()));
                }
                return null;
            }

            String endingBalance = cstmt.getString(9);
            String lastDayOfYear = cstmt.getString(10);

            summaryDTO.setEndingBalance(endingBalance);
            summaryDTO.setLastDayOfYear(lastDayOfYear);

            Object[] tagSummary = (Object[]) cstmt.getArray(11).getArray(this.conn.getTypeMap());
            if (tagSummary != null && tagSummary.length > 0) {
                OLC_TAG_SUMMARY_REC[] olcTagSummaryRecs = new OLC_TAG_SUMMARY_REC[tagSummary.length];
                for (int i = 0; i < tagSummary.length; i++) {
                    olcTagSummaryRecs[i] = (OLC_TAG_SUMMARY_REC) tagSummary[i];
                }

                summaryDTO.setTagSummary(convert(olcTagSummaryRecs));
            }

            Object[] acctSummary = (Object[]) cstmt.getArray(12).getArray(this.conn.getTypeMap());
            if (acctSummary != null && acctSummary.length > 0) {
                OLC_ACCT_SUMMARY_REC[] olcAcctSummaryRecs = new OLC_ACCT_SUMMARY_REC[acctSummary.length];
                for (int i = 0; i < acctSummary.length; i++) {
                    olcAcctSummaryRecs[i] = (OLC_ACCT_SUMMARY_REC) acctSummary[i];
                }
                summaryDTO.setAcctSummary(convert(olcAcctSummaryRecs));
            }

        } catch (SQLException e) {
            throw new EtccException("Error getting Summary HTML - acctId:" + accountLoginDTO.getAcctId() + "****"
                    + e.getMessage(), e);
        }
        return summaryDTO;
    }

    private OlcAcctSummaryRecBean[] convert(OLC_ACCT_SUMMARY_REC[] theInput) throws SQLException {
        if (theInput == null)
            return null;
        ArrayList<OlcAcctSummaryRecBean> theOutput = new ArrayList<OlcAcctSummaryRecBean>();
        for (int i = 0; i < theInput.length; i++) {
            theOutput.add(convert(theInput[i]));
        }
        return theOutput.toArray(new OlcAcctSummaryRecBean[theOutput.size()]);
    }

    private OlcTagSummaryRecBean[] convert(OLC_TAG_SUMMARY_REC[] theInput) throws SQLException {
        if (theInput == null)
            return null;
        ArrayList<OlcTagSummaryRecBean> theOutput = new ArrayList<OlcTagSummaryRecBean>();
        for (int i = 0; i < theInput.length; i++) {
            theOutput.add(convert(theInput[i]));
        }
        return theOutput.toArray(new OlcTagSummaryRecBean[theOutput.size()]);
    }

    public String getStatementPDF(AccountLoginDTO accountLoginDTO, String accountVPNType) throws EtccException,
            EtccSecurityException {
        String url = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call olcsc_rep.View_Statements_PDF(?, ?, ?, ?)}");

            setTypeMap();

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, "PDF");
            cstmt.setString(3, accountVPNType);

            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            int result = cstmt.getInt(1);
            final boolean infoEnabled = logger.isInfoEnabled();
            if (result == -1) {
                if (infoEnabled)
                    logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****" + "getStatementPDF result is -1");
                throw new EtccSecurityException("security exception in getStatementPDF");
            } else if (result == 0) {
                Array errors = (Array) cstmt.getObject(5);
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(errors));
                if (infoEnabled) {
                    logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****" + "getStatementPDF result is 0");
                    logger.info("Database Errors: " + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
                }
                return null;
            }

            url = cstmt.getString(4);
            if (infoEnabled)
                logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****" + "getStatementPDF returns " + url);
        } catch (SQLException e) {
            throw new EtccException("Error getting Statement PDF - AcctId:" + accountLoginDTO.getAcctId() + "****"
                    + e.getMessage(), e);
        }
        return url;
    }

    public String getSummaryPDF(AccountLoginDTO accountLoginDTO, String accountVPNType) throws EtccException,
            EtccSecurityException {
        String url = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call olcsc_rep.View_Summary_PDF(?, ?, ?, ?)}");

            setTypeMap();

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, "PDF");
            cstmt.setString(3, accountVPNType);

            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            int result = cstmt.getInt(1);
            if (result == -1) {
                if (logger.isInfoEnabled())
                    logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****" + "getSummaryPDF result is -1");
                throw new EtccSecurityException("security exception in getSummaryPDF");
            } else if (result == 0) {
                Array errors = (Array) cstmt.getObject(5);
                accountLoginDTO.setErrors(OracleUtil.convertToMessages(errors));
                if (logger.isInfoEnabled()) {
                    logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****" + "getSummaryPDF result is 0");
                    logger.info("Errors: " + ErrorMessageDTO.toStringBuilder(accountLoginDTO.getErrors()));
                }
                return null;
            }

            url = cstmt.getString(4);
            if (logger.isInfoEnabled())
                logger.info("AcctId:" + accountLoginDTO.getAcctId() + "****" + "getSummaryPDF returns " + url);

        } catch (SQLException t) {
            throw new EtccException(
                    "AcctId:" + accountLoginDTO.getAcctId() + "****" + "error getting Summary PDF " + t, t);
        }
        return url;
    }

    public AccountTransactionDTO viewTransactionMain(AccountLoginDTO accountLoginDTO, Calendar startDate,
            Calendar endDate, String acctVPNType) throws EtccException, EtccSecurityException {
        AccountTransactionDTO trxDto = null;

        try {
            long docId = accountLoginDTO.getAcctId();
            String docType = AccountLoginDTO.LoginType.AC.toString();
            String loginId = accountLoginDTO.getLoginId();
            String dbSessionId = accountLoginDTO.getDbSessionId();
            String lastLoginIp = accountLoginDTO.getLastLoginIp();

            Timestamp startTS = null;
            if (startDate != null)
                startTS = new Timestamp(startDate.getTime().getTime());

            Timestamp endTS = null;
            if (endDate != null)
                endTS = new Timestamp(endDate.getTime().getTime());

            OLC_VIEWTXN_TXNTYPE_ARR[] O_OLC_VIEWTXN_TXNTYPE_ARR = new OLC_VIEWTXN_TXNTYPE_ARR[] { new OLC_VIEWTXN_TXNTYPE_ARR() };
            OLCSC_VEHICLE_NICKNAME_ARR[] O_OLCSC_VEHICLE_NICKNAME_ARR = new OLCSC_VEHICLE_NICKNAME_ARR[] { new OLCSC_VEHICLE_NICKNAME_ARR() };
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
            String[] O_PDF_LINK = new String[] { "" };
            String[] O_XLS_LINK = new String[] { "" };

            int result = new OLCSC_REP(this.conn).VIEW_TXNS_MAIN(new BigDecimal(docId), docType, loginId, dbSessionId,
                    lastLoginIp, startTS, endTS, acctVPNType, O_OLC_VIEWTXN_TXNTYPE_ARR, O_OLCSC_VEHICLE_NICKNAME_ARR,
                    O_PDF_LINK, O_XLS_LINK, O_ERROR_MSG_ARR).intValue();
            if (logger.isDebugEnabled())
                logger.debug("viewTransactionMain.result: " + result);

            if (result == 1) {
                trxDto = new AccountTransactionDTO();
                trxDto.setPdfLink(O_PDF_LINK[0]);
                trxDto.setXslLink(O_XLS_LINK[0]);
                if (O_OLC_VIEWTXN_TXNTYPE_ARR[0] != null) {
                    trxDto.setTxnTypes(convertToTxnCol(O_OLC_VIEWTXN_TXNTYPE_ARR[0].getArray()));
                }

                trxDto.setVehicleNickNames(convertToNickNameCol(O_OLCSC_VEHICLE_NICKNAME_ARR[0].getArray()));
                return trxDto;
            } else if (result == 0) {
                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                trxDto = new AccountTransactionDTO();
                trxDto.setErrors(OracleUtil.convertToMessages(errorArray));
            } else if (result == -1) {
                if (logger.isInfoEnabled())
                    logger.info("acctId:" + accountLoginDTO.getAcctId() + "****"
                            + "viewTransactonMain result is -1");
                throw new EtccSecurityException("security exception in viewTransactonMain()");
            } else {
                logger.error("viewTransactionMain.result=" + result + " is invalid");
                throw new EtccSecurityException("Security exception in viewTransactionMain (result is " + result + ")");
            }

            // cstmt =
            // conn.prepareCall("{? = call olcsc_rep.View_txns_Main(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            //
            // Map typeMap = new HashMap();
            // typeMap.put("OL_OWNER.OLC_ACCOUNT_TAG_REC",
            // OlcAccountTagRec.class);
            // typeMap.put("OL_OWNER.OLC_VIEWTXN_TXNTYPE_REC",
            // OlcViewtxnTxntypeRec.class);
            // typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
            // conn.setTypeMap(typeMap);
            //
            // cstmt.registerOutParameter(1, Types.SMALLINT);
            //
            // cstmt.setLong(2, accountLoginDTO.getAcctId());
            // cstmt.setString(3, "AC" );
            // cstmt.setString(4, accountLoginDTO.getLoginId() );
            // cstmt.setString(5, accountLoginDTO.getDbSessionId());
            // cstmt.setString(6, accountLoginDTO.getLastLoginIp());
            // if (startDate!=null)
            // cstmt.setTimestamp(7, new java.sql.Timestamp( startDate.getTime().getTime( ) ) );
            // else
            // cstmt.setTimestamp(7, null);
            //
            // if (endDate!=null)
            // cstmt.setTimestamp(8, new java.sql.Timestamp( endDate.getTime( ).getTime() ) );
            // else
            // cstmt.setTimestamp(8, null);
            //
            // cstmt.setString(9, acctVPNType);
            // cstmt.registerOutParameter(10, Types.ARRAY,
            // "OL_OWNER.OLC_ACCOUNT_TAG_ARR");
            // cstmt.registerOutParameter(11, Types.ARRAY,
            // "OL_OWNER.OLC_VIEWTXN_TXNTYPE_ARR");
            // cstmt.registerOutParameter(12, Types.VARCHAR);
            // cstmt.registerOutParameter(13, Types.VARCHAR);
            // cstmt.registerOutParameter(14, Types.ARRAY,
            // "OL_OWNER.OLC_ERROR_MSG_ARR");
            //
            // logger.info("acctId:"+ accountLoginDTO.getAcctId() + "****"+"start calling View_txns_Main api");
            // cstmt.execute();
            // logger.info("acctId:"+ accountLoginDTO.getAcctId() + "****"+"end calling View_txns_Main api");
            //
            // trxDto = new AccountTransactionDTO();
            // int result = cstmt.getInt(1);
            // if (result == -1){
            // logger.info("acctId:"+ accountLoginDTO.getAcctId() + "****"+"viewTransactonMain result is -1");
            // throw new EtccSecurityException("security exception in viewTransactonMain()");
            // }
            // else if(result == 0) {
            // logger.info("acctId:"+ accountLoginDTO.getAcctId() + "****"+"viewTransactonMain result is 0");
            // Array errors = (Array) cstmt.getObject(14);
            // trxDto.setErrors(convertErrorMsgs(errors));
            // return trxDto;
            // }
            //
            // trxDto.setPdfLink(cstmt.getString(12));
            // trxDto.setXslLink(cstmt.getString(13));
            //
            // Object[] acctTags = (Object[])cstmt.getArray(10).getArray(conn.getTypeMap());
            // if (acctTags != null && acctTags.length>0)
            // {
            // OlcAccountTagRecBean[] olcAccountTagRecs = new OlcAccountTagRecBean[acctTags.length];
            // for(int i=0;i<acctTags.length;i++) {
            // olcAccountTagRecs[i] = new OlcAccountTagRecBean((OlcAccountTagRec)acctTags[i]);
            // }
            // trxDto.setAcctTags(olcAccountTagRecs);
            // }
            //
            // Object[] txnTypes = (Object[])cstmt.getArray(11).getArray(conn.getTypeMap());
            // if (txnTypes != null && txnTypes.length>0) {
            // OlcViewtxnTxntypeRec[] olcTxntypeRecs = new OlcViewtxnTxntypeRec[txnTypes.length];
            // for(int i=0;i<olcTxntypeRecs.length;i++) {
            // olcTxntypeRecs[i] = (OlcViewtxnTxntypeRec)txnTypes[i];
            // }
            // trxDto.setTxnTypes(olcTxntypeRecs);
            // }

        } catch (SQLException t) {
            throw new EtccException("acctId:" + accountLoginDTO.getAcctId() + "****"
                    + "error in viewTransactionMain() " + t, t);
        }
        return trxDto;
    }

    public String getReportServletUrl(String reportName) throws EtccException {
        String reportServletUrl = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call olcsc_param.get_report_servlet_url(?, ?, ?)}");
            setTypeMap();

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, reportName);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            int result = cstmt.getInt(1);
            if (logger.isDebugEnabled())
                logger.debug("oracleaccounthistorydao.getReportServletUrl.result=" + result);

            if (result == 0) {
                logger.info("getReportServletUrl result is 0");
                Array errors = (Array) cstmt.getObject(4);
                logger.info("Errors: " + ErrorMessageDTO.toStringBuilder(OracleUtil.convertToMessages(errors)));
                return null;
            }

            // reportServletUrl = cstmt.getString(3);
            // reportServletUrl = "https://hwwwd3:4443/pls/wcust/olcsc_inv_vps.pdf?";
            reportServletUrl = "http://hwwwd3.tra.pri:7778/reports/rwservlet/olcsc_inv_vps.pdf?";

        } catch (SQLException e) {
            throw new EtccException("error in getReportServletUrl()");
        }
        return reportServletUrl;
    }

    /**
     *
     * @return
     * @throws SQLException
     * @deprecated
     */
    @Deprecated
    //TODO Use a Caching function.
    private String getConnectionTimeout() throws SQLException {
        String result = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call " + "utl_web.GETSYSPARM('OLCSC_CONNECTION_TIMEOUT')}");

            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.execute();

            result = cstmt.getString(1);
            if (logger.isDebugEnabled())
                logger.debug("connectionTimeout:" + result);
        } finally {
            close(cstmt);
        }

        return result;
    }

    private TxnTypeBean[] convertToTxnCol(OLC_VIEWTXN_TXNTYPE_REC[] arr) throws SQLException {

        ArrayList<TxnTypeBean> col = null;

        if (arr != null && arr.length > 0) {
            final int length = arr.length;
            col = new ArrayList<TxnTypeBean>(length);

            for (int i = 0; i < length; i++) {
                col.add(convertToTxnBean(arr[i]));
            }
        }
        if (col == null)
            return null;
        // else
        return col.toArray(new TxnTypeBean[col.size()]);
    }

    private TxnTypeBean convertToTxnBean(OLC_VIEWTXN_TXNTYPE_REC rec) throws SQLException {

        TxnTypeBean txnTypeBean = null;

        if (rec != null) {
            txnTypeBean = new TxnTypeBean();
            txnTypeBean.setDescription(rec.getDESCRIPTION());
            txnTypeBean.setTransTypeId(rec.getTRANS_TYPE_ID().intValue());
        }

        return txnTypeBean;

    }

    private VehicleNickNameBean[] convertToNickNameCol(OLCSC_VEHICLE_NICKNAME_REC[] arr) throws SQLException {

        ArrayList<VehicleNickNameBean> col = null;

        if (arr != null && arr.length > 0) {
            final int length = arr.length;
            col = new ArrayList<VehicleNickNameBean>(length);
            for (int i = 0; i < length; i++) {
                col.add(convertToNickNameBean(arr[i]));
            }
        }
        return col == null ? null : col.toArray(new VehicleNickNameBean[col.size()]);
    }

    private VehicleNickNameBean convertToNickNameBean(OLCSC_VEHICLE_NICKNAME_REC rec) throws SQLException {
        VehicleNickNameBean nickNameBean = null;

        if (rec != null) {
            nickNameBean = new VehicleNickNameBean();
            nickNameBean.setVehicleNickName(rec.getVEHICLE_NICKNAME());
            nickNameBean.setVehicleNickNameType(rec.getVEHICLE_NICKNAME_TYPE());
        }

        return nickNameBean;
    }

    public InvoiceDatesDTO getInvoiceDates(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException {
    	InvoiceDatesDTO dto = new InvoiceDatesDTO();
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn
                    .prepareCall("{? = call OL_OWNER.OLCSC_CORP_INV.GET_INVOICE_PERIOD(?,?,?,?,?,?,?)}");
//												                    		+ "p_doc_type=>?,"
//												                    		+ "p_session_id=>?,"
//												                    		+ "p_ip_address=>?,"
//												                    		+ "p_user_id=>?,"
//												                    		+ "O_CORP_INV_ARR_P=>?,"
//												                    		+ "o_error_msg_arr=>?)}");

            setTypeMap();

            cstmt.registerOutParameter(1, Types.SMALLINT);

            cstmt.setLong(2, accountLogin.getAcctId());
            cstmt.setString(3, "AC");
            cstmt.setString(4, accountLogin.getDbSessionId());
            cstmt.setString(5, accountLogin.getLastLoginIp());
            cstmt.setString(6, accountLogin.getLoginId());

            cstmt.registerOutParameter(7, Types.ARRAY, "OL_OWNER.OLC_CORP_INV_ARR_P");
            cstmt.registerOutParameter(8, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            int result = cstmt.getInt(1);
            final boolean infoEnabled = logger.isInfoEnabled();
            if (result == 0) {
            	if (infoEnabled) {
                    Array errors = (Array) cstmt.getObject(8);
                    accountLogin.setErrors(OracleUtil.convertToMessages(errors));
                    logger.info("AcctId:" + accountLogin.getAcctId() + "****" + "getReportURL result is 0");
                    logger.info("Errors: " + ErrorMessageDTO.toStringBuilder(accountLogin.getErrors()));
                    throw new EtccReportException("Errors: " + ErrorMessageDTO.toStringBuilder(accountLogin.getErrors()));
                }
                return null;
            } else if (result == 1){
            	Object object = cstmt.getObject(7);
            	OlcCorpInvRecP[] olcCorpInvRecBeans = convertDBTypeToJavaForInvoiceDate(object);
            	dto.setInvoiceDates(olcCorpInvRecBeans);
            }

        } catch (SQLException t) {
            String message = "getInvoiceDates.SQLException: " + t.getMessage();
            throw new EtccReportException(message, t);
        } catch (ClassNotFoundException t) {
        	String message = "getInvoiceDates.ClassNotFoundException: " + t.getMessage();
            throw new EtccReportException(message, t);
		}

        return dto;
//        try {
//            InvoiceDatesDTO dto = new InvoiceDatesDTO();
//            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
//            OLC_CORP_INV_ARR_P[] O_CORP_INV_ARR_P = new OLC_CORP_INV_ARR_P[1];
//            BigDecimal ret = new OLCSC_CORP_INV(this.conn).GET_INVOICE_PERIOD(new BigDecimal(accountLogin.getAcctId()),
//                    "AC", accountLogin.getDbSessionId(), accountLogin.getLastLoginIp(), accountLogin.getLoginId(),
//                    O_CORP_INV_ARR_P, O_ERROR_MSG_ARR);
//            int result = ret.intValue();
//            if (logger.isDebugEnabled())
//                logger.debug("getInvoiceDates.result=" + result);
//            if (result == 0) {
//                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
//                dto.setErrors(OracleUtil.convertToMessages(errorArray));
//            } else if (result == -1) {
//                throw new EtccSecurityException("Security exception in getInvoiceDates");
//            } else {
//                populateInvoiceDates(dto, O_CORP_INV_ARR_P);
//            }
//            return dto;
//
//        } catch (SQLException t) {
//            throw new EtccException("Exception in OracleAccountHistoryDAO.getInvoiceDates:" + t.getMessage());
//        }
    }

    private OlcCorpInvRecP[] convertDBTypeToJavaForInvoiceDate(Object object) throws ClassNotFoundException, SQLException {
    	OlcCorpInvRecP[] olcCorpInvRecPs = null;
    	ResultSet rs = null;
    	ARRAY olcCorpInvArray = null;
    	if (object instanceof weblogic.jdbc.wrapper.Array) {
    		olcCorpInvArray = (ARRAY) (((weblogic.jdbc.wrapper.Array) object)
					.unwrap(Class.forName("oracle.sql.ARRAY")));
    	} else {
    		olcCorpInvArray = (ARRAY) object;
    	}
    	olcCorpInvRecPs = new OlcCorpInvRecP[olcCorpInvArray.length()];
		OlcCorpInvRecP olcCorpInvRecP = null;
		rs = olcCorpInvArray.getResultSet();
		int index = 0;
		while (rs.next()) {
			olcCorpInvRecP = new OlcCorpInvRecP();
			STRUCT obj = (STRUCT) rs.getObject(2);
			Object[] attrs = obj.getAttributes();
			String statementPeriod = (String) attrs[0];
			String invoicePeriod = (String) attrs[1];
			olcCorpInvRecP.setStatementPeriod(statementPeriod);
			olcCorpInvRecP.setInvoicePeriod(invoicePeriod);
			olcCorpInvRecPs[index++] = olcCorpInvRecP;
		}

    	return olcCorpInvRecPs;
    }

//    private void populateInvoiceDates(InvoiceDatesDTO dto, OLC_CORP_INV_ARR_P[] O_CORP_INV_ARR_P) throws SQLException {
//        OLC_CORP_INV_REC_P[] recs = O_CORP_INV_ARR_P[0].getArray();
//        ArrayList<OlcCorpInvRecP> dates = new ArrayList<OlcCorpInvRecP>();
//        for (int i = 0; i < recs.length; i++) {
//            dates.add(convert(recs[i]));
//        }
//        dto.setInvoiceDates(dates.toArray(new OlcCorpInvRecP[dates.size()]));
//    }

//    private OlcCorpInvRecP convert(OLC_CORP_INV_REC_P theInput) throws SQLException {
//        if (theInput == null)
//            return null;
//        OlcCorpInvRecP theOutput = new OlcCorpInvRecP();
//        theOutput.setInvoicePeriod(theInput.getINVOICE_PERIOD());
//        theOutput.setStatementPeriod(theInput.getSTATEMENT_PERIOD());
//        return theOutput;
//    }

    public MonthlyInvoicesDTO getMonthlyInvoices(String statementMonth, AccountLoginDTO dto)
            throws EtccSecurityException, EtccException {
    	MonthlyInvoicesDTO monthlyInvoice = new MonthlyInvoicesDTO();
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn
                    .prepareCall("{? = call OL_OWNER.OLCSC_CORP_INV.GET_CORP_INVOICES(p_acct_id=>?,"
												                    		+ "p_doc_type=>?,"
												                    		+ "p_session_id=>?,"
												                    		+ "p_ip_address=>?,"
												                    		+ "p_user_id=>?,"
												                    		+ "P_Invoice_period=>?,"
												                    		+ "P_first_name=>?,"
												                    		+ "P_last_name=>?,"
												                    		+ "p_address1=>?,"
												                    		+ "p_address2=>?,"
												                    		+ "p_address3=>?,"
												                    		+ "p_address4=>?,"
												                    		+ "p_city=>?,"
												                    		+ "p_state=>?,"
												                    		+ "p_zip_code=>?,"
												                    		+ "p_plus4=>?,"
												                    		+ "p_country=>?,"
												                    		+ "p_Invoice_Number=>?,"
												                    		+ "p_stmt_period=>?,"
												                    		+ "p_invoice_date=>?,"
												                    		+ "p_total_amount_due=>?,"
												                    		+ "p_rebill_amt=>?,"
												                    		+ "p_Ending_Acct_Bal=>?,"
												                    		+ "O_CORP_INV_ARR_D=>?,"
												                    		+ "O_CORP_INV_ARR_S=>?,"
												                    		+ "o_error_msg_arr=>?,"	//defect 9973 txphung
												                    		+ "o_alerts=>?)}");		//defect 9973 txphung

            setTypeMap();

            cstmt.registerOutParameter(1, Types.SMALLINT);

            cstmt.setBigDecimal(2, new BigDecimal(dto.getAcctId()));
            cstmt.setString(3, "AC");
            cstmt.setString(4, dto.getDbSessionId());
            cstmt.setString(5, dto.getLastLoginIp());
            cstmt.setString(6, dto.getLoginId());
            cstmt.setString(7, statementMonth);

            cstmt.registerOutParameter(8, Types.VARCHAR);
            cstmt.registerOutParameter(9, Types.VARCHAR);
            cstmt.registerOutParameter(10, Types.VARCHAR);
            cstmt.registerOutParameter(11, Types.VARCHAR);
            cstmt.registerOutParameter(12, Types.VARCHAR);
            cstmt.registerOutParameter(13, Types.VARCHAR);
            cstmt.registerOutParameter(14, Types.VARCHAR);
            cstmt.registerOutParameter(15, Types.VARCHAR);
            cstmt.registerOutParameter(16, Types.VARCHAR);
            cstmt.registerOutParameter(17, Types.VARCHAR);
            cstmt.registerOutParameter(18, Types.VARCHAR);
            cstmt.registerOutParameter(19, Types.VARCHAR);
            cstmt.registerOutParameter(20, Types.VARCHAR);
            cstmt.registerOutParameter(21, Types.DATE);
            cstmt.registerOutParameter(22, Types.SMALLINT);
            cstmt.registerOutParameter(23, Types.SMALLINT);
            cstmt.registerOutParameter(24, Types.SMALLINT);
            cstmt.registerOutParameter(25, Types.ARRAY, "OLC_CORP_INV_ARR_D");
            cstmt.registerOutParameter(26, Types.ARRAY, "OLC_CORP_INV_ARR_S");
            cstmt.registerOutParameter(27, Types.ARRAY, "OLC_ERROR_MSG_ARR");
            cstmt.registerOutParameter(28, Types.ARRAY, "OL_OWNER.OLC_ALERT_DISPLAY_ARR");	//Defect 9973 txphung

            cstmt.execute();
            int result = cstmt.getInt(1);
            final boolean infoEnabled = logger.isInfoEnabled();
            if (result == 0) {
            	//Defect 9973 txphung: begin
            	Array errors = (Array) cstmt.getObject(27);
                if(errors != null){
                	monthlyInvoice.setErrors(OracleUtil.convertToMessages(errors));
                }
                Array alerts = cstmt.getArray(28);
                if(alerts != null){
                	monthlyInvoice.setAlerts(OracleUtil.convertToAlerts(alerts));
				}                
                if (infoEnabled) {
                    //Array errors = (Array) cstmt.getObject(27);
                    //dto.setErrors(OracleUtil.convertToMessages(errors));
                    logger.info("AcctId:" + dto.getAcctId() + "****" + "getReportURL result is 0");
                    logger.info("Errors: " + ErrorMessageDTO.toStringBuilder(monthlyInvoice.getErrors()));
                    logger.info("InvoiceNumber:" + cstmt.getString(19));
                    //logger.info("Errors: " + ErrorMessageDTO.toStringBuilder(dto.getErrors()));
                    //throw new EtccReportException("Errors: " + ErrorMessageDTO.toStringBuilder(dto.getErrors()));
                }
                //return null;
                monthlyInvoice.setInvoiceNumber(cstmt.getString(19)); //defect#10404
            	return monthlyInvoice;
              //Defect 9973 txphung: end
            } else if (result == 1){
            	monthlyInvoice.setAcctId(dto.getAcctId());
            	monthlyInvoice.setFirstName(cstmt.getString(8));
            	monthlyInvoice.setLastName(cstmt.getString(9));
            	monthlyInvoice.setAddress1(cstmt.getString(10));
            	monthlyInvoice.setAddress2(cstmt.getString(11));
            	monthlyInvoice.setAddress3(cstmt.getString(12));
            	monthlyInvoice.setAddress4(cstmt.getString(13));
            	monthlyInvoice.setCity(cstmt.getString(14));
              	monthlyInvoice.setState(cstmt.getString(15));
              	monthlyInvoice.setZip(cstmt.getString(16));
              	monthlyInvoice.setCountry(cstmt.getString(18));
              	monthlyInvoice.setInvoiceNumber(cstmt.getString(19));
              	monthlyInvoice.setStmtPeriod(cstmt.getString(20));
              	monthlyInvoice.setInvoiceDate(WSDateUtil.timestampToCalendar(cstmt.getTimestamp(21)));
              	monthlyInvoice.setTotalAmtDue(cstmt.getDouble(22));

              	monthlyInvoice.setInvSumEndingBalance(cstmt.getDouble(24));

              	monthlyInvoice.setBalSumBalanceRequirement(cstmt.getDouble(23));
              	monthlyInvoice.setBalSumCurrentBalance(cstmt.getDouble(24));
              	monthlyInvoice.setBalSumReplenishmentAmt(cstmt.getDouble(23) - cstmt.getDouble(24));

              	Object OLC_CORP_INV_ARR_D = cstmt.getObject(25);
              	Object OLC_CORP_INV_ARR_S = cstmt.getObject(26);

              	OlcAcctSummaryRecBean[] outstandingInvoices = convertDBTypeToJavaForOutstandingInvoices(OLC_CORP_INV_ARR_S);
              	monthlyInvoice.setOutstandingInvoices(outstandingInvoices);

              	monthlyInvoice.setTotalOutstandingAmt(getTotalOutstandingAmt(monthlyInvoice.getOutstandingInvoices()));

              	OlcAccountHistoryRecBean[] invoiceDetails = convertDBTypeToJavaForInvoiceDetails(OLC_CORP_INV_ARR_D);
              	monthlyInvoice.setInvoiceDetails(invoiceDetails);

              	monthlyInvoice.setInvoiceSubTotal(getInvoiceSubTotal(monthlyInvoice.getInvoiceDetails()));

              	OlcAcctSummaryRecBean[] suspensionDate = convertDBTypeToJavaForSuspensionDate(OLC_CORP_INV_ARR_S);
              	monthlyInvoice.setSuspensionDate(suspensionDate);

              	monthlyInvoice.setDueMonth(getDueMonth(monthlyInvoice.getOutstandingInvoices()));

              	monthlyInvoice.setDueAmount(getDueAmount(monthlyInvoice.getOutstandingInvoices()));

              	OlcAcctSummaryRecBean[] invoiceSummary = convertDBTypeToJavaForInvoiceSummary(OLC_CORP_INV_ARR_S);
              	monthlyInvoice.setInvoiceSummary(invoiceSummary);
            }

        } catch (SQLException t) {
            String message = "getMonthlyInvoices.SQLException: " + t.getMessage();
            throw new EtccReportException(message, t);
        } catch (ClassNotFoundException t) {
        	String message = "getMonthlyInvoices.ClassNotFoundException: " + t.getMessage();
            throw new EtccReportException(message, t);
		}

        return monthlyInvoice;

//        try {
//
//            MonthlyInvoicesDTO monthlyInvoice = new MonthlyInvoicesDTO();
//            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
//
//            String P_FIRST_NAME[] = new String[1];
//            String P_LAST_NAME[] = new String[1];
//            String P_ADDRESS1[] = new String[1];
//            String P_ADDRESS2[] = new String[1];
//            String P_ADDRESS3[] = new String[1];
//            String P_ADDRESS4[] = new String[1];
//            String P_CITY[] = new String[1];
//            String P_STATE[] = new String[1];
//            String P_ZIP_CODE[] = new String[1];
//            String P_PLUS4[] = new String[1];
//            String P_COUNTRY[] = new String[1];
//            String P_INVOICE_NUMBER[] = new String[1];
//            String P_STMT_PERIOD[] = new String[1];
//            java.sql.Timestamp P_INVOICE_DATE[] = new Timestamp[1];
//            java.math.BigDecimal P_TOTAL_AMOUNT_DUE[] = new BigDecimal[1];
//            java.math.BigDecimal P_REBILL_AMT[] = new BigDecimal[1];
//            java.math.BigDecimal P_ENDING_ACCT_BAL[] = new BigDecimal[1];
//            OLC_CORP_INV_ARR_D O_CORP_INV_ARR_D[] = new OLC_CORP_INV_ARR_D[1];
//            OLC_CORP_INV_ARR_S O_CORP_INV_ARR_S[] = new OLC_CORP_INV_ARR_S[1];
//
//            BigDecimal ret = new OLCSC_CORP_INV(this.conn).GET_CORP_INVOICES(new BigDecimal(dto.getAcctId()), "AC", dto
//                    .getDbSessionId(), dto.getLastLoginIp(), dto.getLoginId(), statementMonth, P_FIRST_NAME,
//                    P_LAST_NAME, P_ADDRESS1, P_ADDRESS2, P_ADDRESS3, P_ADDRESS4, P_CITY, P_STATE, P_ZIP_CODE, P_PLUS4,
//                    P_COUNTRY, P_INVOICE_NUMBER, P_STMT_PERIOD, P_INVOICE_DATE, P_TOTAL_AMOUNT_DUE, P_REBILL_AMT,
//                    P_ENDING_ACCT_BAL, O_CORP_INV_ARR_D, O_CORP_INV_ARR_S, O_ERROR_MSG_ARR);
//            int result = ret.intValue();
//            if (logger.isDebugEnabled())
//                logger.debug("getMonthlyInvoices.result123=" + result);
//            if (result == 0) {
//                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
//                for (int i = 0; i < errorArray.length; i++) {
//                	logger.info(errorArray[i].getERROR_MSG());
//                    }
//                logger.debug("Value of errorArray" + OracleUtil.convertToMessages(errorArray));
//                monthlyInvoice.setErrors(OracleUtil.convertToMessages(errorArray));
//
//            } else if (result == -1) {
//                throw new EtccSecurityException("Security exception in getMonthlyInvoices");
//            }
//            if (result != -1){
//                monthlyInvoice.setFirstName(P_FIRST_NAME[0]);
//                monthlyInvoice.setLastName(P_LAST_NAME[0]);
//                monthlyInvoice.setAddress1(P_ADDRESS1[0]);
//                monthlyInvoice.setAddress2(P_ADDRESS2[0]);
//                monthlyInvoice.setAddress3(P_ADDRESS3[0]);
//                monthlyInvoice.setAddress4(P_ADDRESS4[0]);
//                monthlyInvoice.setCity(P_CITY[0]);
//                monthlyInvoice.setState(P_STATE[0]);
//                monthlyInvoice.setZip(P_ZIP_CODE[0]);
//                monthlyInvoice.setCountry(P_COUNTRY[0]);
//                monthlyInvoice.setInvoiceNumber(P_INVOICE_NUMBER[0]);
//                monthlyInvoice.setStmtPeriod(P_STMT_PERIOD[0]);
//                monthlyInvoice.setInvoiceDate(WSDateUtil.timestampToCalendar(P_INVOICE_DATE[0]));
//                monthlyInvoice.setTotalAmtDue(P_TOTAL_AMOUNT_DUE[0].doubleValue());
//
//                monthlyInvoice.setInvSumEndingBalance(P_ENDING_ACCT_BAL[0].doubleValue());
//
//                monthlyInvoice.setBalSumBalanceRequirement(P_REBILL_AMT[0].doubleValue());
//                monthlyInvoice.setBalSumCurrentBalance(P_ENDING_ACCT_BAL[0].doubleValue());
//                monthlyInvoice.setBalSumReplenishmentAmt(P_REBILL_AMT[0].doubleValue()
//                        - P_ENDING_ACCT_BAL[0].doubleValue());
//
//                monthlyInvoice.setOutstandingInvoices(getOutstandingInvoices(O_CORP_INV_ARR_S));
//                monthlyInvoice.setTotalOutstandingAmt(getTotalOutstandingAmt(monthlyInvoice.getOutstandingInvoices()));
//
//                monthlyInvoice.setInvoiceDetails(getInvoiceDetails(O_CORP_INV_ARR_D));
//                monthlyInvoice.setInvoiceSubTotal(getInvoiceSubTotal(monthlyInvoice.getInvoiceDetails()));
//
//                monthlyInvoice.setSuspensionDate(getSuspensionDate(O_CORP_INV_ARR_S));
//
//                monthlyInvoice.setDueMonth(getDueMonth(monthlyInvoice.getOutstandingInvoices()));
//
//                monthlyInvoice.setDueAmount(getDueAmount(monthlyInvoice.getOutstandingInvoices()));
//
//                monthlyInvoice.setInvoiceSummary(getInvoiceSummary(O_CORP_INV_ARR_S));
//            }
//
//            return monthlyInvoice;
//
//
//        } catch (SQLException e) {
//            throw new EtccException(e.getMessage(), e);
//        }

    }

    private OlcAcctSummaryRecBean[] convertDBTypeToJavaForOutstandingInvoices (Object object) throws ClassNotFoundException, SQLException {
    	List<OlcAcctSummaryRecBean> OlcAcctSummaryRecBeans = new ArrayList<OlcAcctSummaryRecBean>();
    	ResultSet rs = null;
    	ARRAY OlcAcctSummaryArray = null;
    	if (object instanceof weblogic.jdbc.wrapper.Array) {
    		OlcAcctSummaryArray = (ARRAY) (((weblogic.jdbc.wrapper.Array) object)
					.unwrap(Class.forName("oracle.sql.ARRAY")));
    	} else {
    		OlcAcctSummaryArray = (ARRAY) object;
    	}
    	OlcAcctSummaryRecBean olcAcctSummaryRecBean = null;
		rs = OlcAcctSummaryArray.getResultSet();
		while (rs.next()) {
			olcAcctSummaryRecBean = new OlcAcctSummaryRecBean();
			STRUCT obj = (STRUCT) rs.getObject(2);
			Object[] attrs = obj.getAttributes();
			String groupType = (String) attrs[3];
			if("OI".equals(groupType)) {
				String description = (String) attrs[0];
				BigDecimal quantity = (BigDecimal) attrs[1];
				BigDecimal amount = (BigDecimal) attrs[2];
				String strAmount = quantity.toString();

				olcAcctSummaryRecBean.setAmount(amount);
				olcAcctSummaryRecBean.setQuantity(quantity);
				olcAcctSummaryRecBean.setDescription(description);
				olcAcctSummaryRecBean.setGroupType(groupType);
				olcAcctSummaryRecBean.setStrAmount(strAmount);
				OlcAcctSummaryRecBeans.add(olcAcctSummaryRecBean);
			}
		}

    	return OlcAcctSummaryRecBeans.size() > 0 ?
    			OlcAcctSummaryRecBeans.toArray(new OlcAcctSummaryRecBean[OlcAcctSummaryRecBeans.size()]):null;
    }

    private OlcAccountHistoryRecBean[] convertDBTypeToJavaForInvoiceDetails (Object object) throws ClassNotFoundException, SQLException {
    	OlcAccountHistoryRecBean[] OlcAccountHistoryRecBeans = null;
    	ResultSet rs = null;
    	ARRAY OlcAccountHistoryArray = null;
    	if (object instanceof weblogic.jdbc.wrapper.Array) {
    		OlcAccountHistoryArray = (ARRAY) (((weblogic.jdbc.wrapper.Array) object)
					.unwrap(Class.forName("oracle.sql.ARRAY")));
    	} else {
    		OlcAccountHistoryArray = (ARRAY) object;
    	}
    	OlcAccountHistoryRecBeans = new OlcAccountHistoryRecBean[OlcAccountHistoryArray.length()];
    	OlcAccountHistoryRecBean olcAccountHistoryRecBean = null;
		rs = OlcAccountHistoryArray.getResultSet();
		int index = 0;
		while (rs.next()) {
			olcAccountHistoryRecBean = new OlcAccountHistoryRecBean();
			STRUCT obj = (STRUCT) rs.getObject(2);
			Object[] attrs = obj.getAttributes();
			Calendar trxnDate = WSDateUtil.timestampToCalendar((Timestamp)attrs[0]);
			String locationName = (String) attrs[1];
			Calendar postedDate = WSDateUtil.timestampToCalendar((Timestamp)attrs[2]);
			String vehicleNickName = (String) attrs[3];
			String transType = (String) attrs[4];
			BigDecimal amount = (BigDecimal) attrs[5];
			String vehicleClassCode = (String) attrs[6];

			olcAccountHistoryRecBean.setTrxnDate(trxnDate);
			olcAccountHistoryRecBean.setLocationName(locationName);
			olcAccountHistoryRecBean.setPostedDate(postedDate);
            olcAccountHistoryRecBean.setVehicleNickName(vehicleNickName);
            olcAccountHistoryRecBean.setTransType(transType);
            olcAccountHistoryRecBean.setAmount(amount);
            olcAccountHistoryRecBean.setVehicleClassCode(vehicleClassCode);
			OlcAccountHistoryRecBeans[index++] = olcAccountHistoryRecBean;
		}

    	return OlcAccountHistoryRecBeans;
    }

    private OlcAcctSummaryRecBean[] convertDBTypeToJavaForSuspensionDate(Object object) throws ClassNotFoundException, SQLException {
    	List<OlcAcctSummaryRecBean> olcAcctSummaryRecBeanList = new ArrayList<OlcAcctSummaryRecBean>();
    	ResultSet rs = null;
    	ARRAY olcCorpInvArray = null;
    	if (object instanceof weblogic.jdbc.wrapper.Array) {
    		olcCorpInvArray = (ARRAY) (((weblogic.jdbc.wrapper.Array) object)
					.unwrap(Class.forName("oracle.sql.ARRAY")));
    	} else {
    		olcCorpInvArray = (ARRAY) object;
    	}
    	OlcAcctSummaryRecBean olcAcctSummaryRecBean = null;
		rs = olcCorpInvArray.getResultSet();
		while (rs.next()) {
			olcAcctSummaryRecBean = new OlcAcctSummaryRecBean();
			STRUCT obj = (STRUCT) rs.getObject(2);
			Object[] attrs = obj.getAttributes();
			String groupType = (String) attrs[3];
			if("ST".equals(groupType)){
				String description = (String) attrs[0];
				BigDecimal quantity = (BigDecimal) attrs[1];
				BigDecimal amount = (BigDecimal) attrs[2];
				String strAmount = amount == null ? "0":amount.toString();

				olcAcctSummaryRecBean.setAmount(amount);
				olcAcctSummaryRecBean.setQuantity(quantity);
				olcAcctSummaryRecBean.setDescription(description);
				olcAcctSummaryRecBean.setGroupType(groupType);
				olcAcctSummaryRecBean.setStrAmount(strAmount);
				olcAcctSummaryRecBeanList.add(olcAcctSummaryRecBean);
			}
		}

    	return olcAcctSummaryRecBeanList.size() > 0 ?
    			olcAcctSummaryRecBeanList.toArray(new OlcAcctSummaryRecBean[olcAcctSummaryRecBeanList.size()]):null;
    }

    private OlcAcctSummaryRecBean[] convertDBTypeToJavaForInvoiceSummary(Object object) throws ClassNotFoundException, SQLException {
    	List<OlcAcctSummaryRecBean> olcAcctSummaryRecBeanList = new ArrayList<OlcAcctSummaryRecBean>();
    	ResultSet rs = null;
    	ARRAY olcCorpInvArray = null;
    	if (object instanceof weblogic.jdbc.wrapper.Array) {
    		olcCorpInvArray = (ARRAY) (((weblogic.jdbc.wrapper.Array) object)
					.unwrap(Class.forName("oracle.sql.ARRAY")));
    	} else {
    		olcCorpInvArray = (ARRAY) object;
    	}
    	OlcAcctSummaryRecBean olcAcctSummaryRecBean = null;
		rs = olcCorpInvArray.getResultSet();
		while (rs.next()) {
			olcAcctSummaryRecBean = new OlcAcctSummaryRecBean();
			STRUCT obj = (STRUCT) rs.getObject(2);
			Object[] attrs = obj.getAttributes();
			String groupType = (String) attrs[3];
			if("IS".equals(groupType)){
				String description = (String) attrs[0];
				BigDecimal quantity = (BigDecimal) attrs[1];
				BigDecimal amount = (BigDecimal) attrs[2];
				String strAmount = amount == null ? "0":amount.toString();

				olcAcctSummaryRecBean.setAmount(amount);
				olcAcctSummaryRecBean.setQuantity(quantity);
				olcAcctSummaryRecBean.setDescription(description);
				olcAcctSummaryRecBean.setGroupType(groupType);
				olcAcctSummaryRecBean.setStrAmount(strAmount);
				olcAcctSummaryRecBeanList.add(olcAcctSummaryRecBean);
			}
		}

    	return olcAcctSummaryRecBeanList.size() > 0 ?
    			olcAcctSummaryRecBeanList.toArray(new OlcAcctSummaryRecBean[olcAcctSummaryRecBeanList.size()]):null;
    }

    private double getInvoiceSubTotal(OlcAccountHistoryRecBean[] beans) {
        double total = 0;
        if (beans == null || beans.length == 0)
            return 0;
        for (int i = 0; i < beans.length; i++) {
            total += beans[i].getAmount().doubleValue();
        }
        return total;
    }

    private double getTotalOutstandingAmt(OlcAcctSummaryRecBean[] beans) {
        double balance = 0;
        if (beans == null)
            return balance;
        for (int i = 0; i < beans.length; i++) {
            balance += beans[i].getAmount().doubleValue();
        }
        return balance;
    }

    private double getDueAmount(OlcAcctSummaryRecBean[] beans){


    	logger.info("Inside getDueAmount");
    	if (beans == null)
    		return 0;

    	int i =0;
    	i = beans.length;
    	return beans[i-1].getAmount().doubleValue();

    }


 private String getDueMonth(OlcAcctSummaryRecBean[] beans){


	    logger.info("Inside getDueMonth");
    	if (beans == null)
    		return null;

    	int i =0;
    	i = beans.length;
    	logger.info(i);
    	return beans[i-1].getDescription();

    }

//    private OlcAcctSummaryRecBean[] getInvoiceSummary(OLC_CORP_INV_ARR_S O_CORP_INV_ARR_S[]) throws SQLException {
//        OLC_CORP_INV_REC_S[] recs = O_CORP_INV_ARR_S[0].getArray();
//        ArrayList<OlcAcctSummaryRecBean> beansCollection = new ArrayList<OlcAcctSummaryRecBean>();
//        for (int i = 0; i < recs.length; i++) {
//            OlcAcctSummaryRecBean rec = new OlcAcctSummaryRecBean();
//            if (recs[i].getGROUP_TYPE().equals("IS")) {
//                rec.setDescription(recs[i].getDESCRIPTION());
//                rec.setAmount(recs[i].getAMOUNT());
//                rec.setQuantity(recs[i].getQUANTITY());
//                rec.setGroupType(recs[i].getGROUP_TYPE());
//                beansCollection.add(rec);
//            }
//        }
//        return beansCollection.toArray(new OlcAcctSummaryRecBean[beansCollection.size()]);
//    }
//
//    private OlcAcctSummaryRecBean[] getOutstandingInvoices(OLC_CORP_INV_ARR_S O_CORP_INV_ARR_S[]) throws SQLException {
//        OLC_CORP_INV_REC_S[] recs = O_CORP_INV_ARR_S[0].getArray();
//        logger.info("Inside getoutstandinginvoices");
//        logger.info(recs);
//        final int length = recs.length;
//        ArrayList<OlcAcctSummaryRecBean> beansCollection = new ArrayList<OlcAcctSummaryRecBean>(length);
//        for (int i = 0; i < length; i++) {
//            if (recs[i].getGROUP_TYPE().equals("OI")) {
//                OlcAcctSummaryRecBean rec = new OlcAcctSummaryRecBean();
//                rec.setDescription(recs[i].getDESCRIPTION());
//                rec.setAmount(recs[i].getAMOUNT());
//                rec.setQuantity(new BigDecimal(1));
//                rec.setGroupType(recs[i].getGROUP_TYPE());
//                beansCollection.add(rec);
//            }
//        }
//        return beansCollection.size() > 0 ? beansCollection.toArray(new OlcAcctSummaryRecBean[beansCollection.size()])
//                : null;
//
//    }

//    private OlcAcctSummaryRecBean[] getSuspensionDate(OLC_CORP_INV_ARR_S O_CORP_INV_ARR_S[]) throws SQLException {
//        OLC_CORP_INV_REC_S[] recs = O_CORP_INV_ARR_S[0].getArray();
//        logger.info("Inside getsuspensiondate");
//        final int length = recs.length;
//        ArrayList<OlcAcctSummaryRecBean> beansCollection = new ArrayList<OlcAcctSummaryRecBean>(length);
//        for (int i = 0; i < length; i++) {
//            if (recs[i].getGROUP_TYPE().equals("ST")) {
//                OlcAcctSummaryRecBean rec = new OlcAcctSummaryRecBean();
//                rec.setDescription(recs[i].getDESCRIPTION());
//                rec.setAmount(recs[i].getAMOUNT());
//                rec.setQuantity(new BigDecimal(1));
//                rec.setGroupType(recs[i].getGROUP_TYPE());
//                beansCollection.add(rec);
//            }
//        }
//        return beansCollection.size() > 0 ? beansCollection.toArray(new OlcAcctSummaryRecBean[beansCollection.size()])
//                : null;
//
//    }
//
//
//    private OlcAccountHistoryRecBean[] getInvoiceDetails(OLC_CORP_INV_ARR_D O_CORP_INV_ARR_D[]) throws SQLException {
//        OLC_CORP_INV_REC_D[] recs = O_CORP_INV_ARR_D[0].getArray();
//        logger.info(recs);
//        if (recs.length == 0)
//            {
//        	logger.info("In getInvoice details no records");
//        	 return null;
//            }
//
//        OlcAccountHistoryRecBean[] beans = new OlcAccountHistoryRecBean[recs.length];
//        for (int i = 0; i < recs.length; i++) {
//            OlcAccountHistoryRecBean bean = new OlcAccountHistoryRecBean();
//            bean.setTrxnDate(WSDateUtil.timestampToCalendar(recs[i].getTRAN_DATE()));
//            bean.setLocationName(recs[i].getLOCATION());
//            bean.setPostedDate(WSDateUtil.timestampToCalendar(recs[i].getPOSTED_DATE()));
//            bean.setVehicleNickName(recs[i].getVEHICLE());
//            bean.setTransType(recs[i].getTRAN_TYPE());
//            bean.setAmount(recs[i].getAMOUNT());
//            bean.setVehicleClassCode(recs[i].getVEHICLE_CLASS_CODE());
//            beans[i] = bean;
//        }
//        return beans;
//    }
//
//    public AccountReceiptsDTO getAccountReceipts(AccountLoginDTO login, Calendar start, Calendar end)
//            throws EtccSecurityException, EtccException {
//        try {
//            AccountReceiptsDTO dto = new AccountReceiptsDTO();
//            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
//            OLC_VIEW_RECEIPT_HEAD_ARR O_VIEW_RECEIPT_HEAD_ARR[] = new OLC_VIEW_RECEIPT_HEAD_ARR[] { new OLC_VIEW_RECEIPT_HEAD_ARR() };
//            logger.error("getAccountReceipts==>Start");
//            BigDecimal ret = new OLCSC_VIEW_RECEIPT(this.conn).OLCSC_VIEW_RECEIPT_HEAD(
//                    new BigDecimal(login.getAcctId()), "AC", login.getDbSessionId(), login.getLastLoginIp(), login
//                            .getLoginId(), WSDateUtil.calendarToTimestamp(start), WSDateUtil.calendarToTimestamp(end),
//                    O_ERROR_MSG_ARR, O_VIEW_RECEIPT_HEAD_ARR);
//            int result = ret.intValue();
//            logger.error("getAccountReceipts.result=" + result);
//            if (result == 0) {
//                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
//                dto.setErrors(OracleUtil.convertToMessages(errorArray));
//            } else if (result == -1) {
//                throw new EtccSecurityException("Security exception in getAccountReceipts");
//            } else {
//                populateAccountReceipts(dto, O_VIEW_RECEIPT_HEAD_ARR[0]);
//            }
//            return dto;
//
//        } catch (SQLException e) {
//            throw new EtccException(e.getMessage(), e);
//        }
//    }

    private void populateAccountReceipts(AccountReceiptsDTO dto, OLC_VIEW_RECEIPT_HEAD_ARR receiptHead)
            throws SQLException {
        OLC_VIEW_RECEIPT_HEAD_REC[] recs = receiptHead.getArray();
        AccountReceiptsDTO.AccountReceipt[] receipts = new AccountReceiptsDTO.AccountReceipt[recs.length];
        for (int i = 0; i < recs.length; i++) {
            OLC_VIEW_RECEIPT_HEAD_REC rec = recs[i];
            AccountReceipt receipt = new AccountReceiptsDTO.AccountReceipt();
            receipt.setTransactionDate(WSDateUtil.timestampToCalendar(rec.getTRANS_DATE()));
            receipt.setTransactionID(rec.getRETAIL_TRANS_ID().longValue());
            receipt.setTransactionAmt(rec.getTRANS_AMT().floatValue());
            receipt.setTransactionType(rec.getTRANS_TYPE_DESCR());
            receipt.setHASFlag(rec.getHAS_FLAG());
            receipts[i] = receipt;
        }
        dto.setAccountReceipts(receipts);
    }

    public AccountReceiptDetailsDTO getAccountReceiptDetails(AccountLoginDTO login, long transactionID, String HASFlag)
            throws EtccSecurityException, EtccException {
        try {
            AccountReceiptDetailsDTO dto = new AccountReceiptDetailsDTO();
            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            BigDecimal O_AP_USER_ID[] = new BigDecimal[1];
            Timestamp O_TRANS_DATE[] = new Timestamp[1];
            OLC_ACCOUNT_TRANSACTION_ARR O_OLC_ACCOUNT_TRANSACTION_ARR[] = new OLC_ACCOUNT_TRANSACTION_ARR[1];
            OLC_PAYMENT_DETAIL_ARR O_OLC_PAYMENT_DETAIL_ARR[] = new OLC_PAYMENT_DETAIL_ARR[1];
            OLC_DEPOSIT_TRANSACTION_ARR O_OLC_DEPOSIT_TRANSACTION_ARR[] = new OLC_DEPOSIT_TRANSACTION_ARR[1];
            OLC_HAS_PARK_RECEIPT_ARR O_OLC_HAS_PARK_RECEIPT_ARR[] = new OLC_HAS_PARK_RECEIPT_ARR[1];

            BigDecimal ret = new OLCSC_VIEW_RECEIPT(this.conn).OLCSC_VIEW_RECEIPT_DETAIL(new BigDecimal(login
                    .getAcctId()), "AC", "" + transactionID, login.getDbSessionId(), login.getLastLoginIp(), login
                    .getLoginId(), O_ERROR_MSG_ARR, O_AP_USER_ID, O_TRANS_DATE, O_OLC_ACCOUNT_TRANSACTION_ARR,
                    O_OLC_PAYMENT_DETAIL_ARR, O_OLC_DEPOSIT_TRANSACTION_ARR, O_OLC_HAS_PARK_RECEIPT_ARR, HASFlag);

            int result = ret.intValue();
            if (logger.isDebugEnabled())
                logger.debug("getAccountReceiptDetails.result=" + result);
            if (result == 0) {
                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                dto.setErrors(OracleUtil.convertToMessages(errorArray));
            } else if (result == -1) {
                throw new EtccSecurityException("Security exception in getAccountReceiptDetails");
            } else {
                if (HASFlag.equals("Y")) {
                    populateParkingTransaction(dto, O_OLC_HAS_PARK_RECEIPT_ARR[0]);
                    dto.setTransactionType("PARKING");
                } else {
                    dto.setCsrID(O_AP_USER_ID[0].toString());
                    dto.setTransactionDate(O_TRANS_DATE[0]);
                    dto.setDepositTransactions(getTransactions(O_OLC_DEPOSIT_TRANSACTION_ARR[0]));
                    dto.setAccountTransactions(getTransactions(O_OLC_ACCOUNT_TRANSACTION_ARR[0]));
                    dto.setPmtDetails(getTransactions(O_OLC_PAYMENT_DETAIL_ARR[0]));
                    dto.setTransactionType("ACCOUNT");
                }
            }
            return dto;
        } catch (SQLException e) {
            throw new EtccException(e.getMessage(), e);
        }
    }

    private AccountTransaction[] getTransactions(OLC_PAYMENT_DETAIL_ARR arr) throws SQLException {
        ArrayList<AccountTransaction> col = new ArrayList<AccountTransaction>();
        OLC_PAYMENT_DETAIL_REC recs[] = arr.getArray();
        for (int i = 0; i < recs.length; i++) {
            AccountTransaction trn = new AccountTransaction();
            float txnAmt = recs[i].getTRANS_AMT() != null ? recs[i].getTRANS_AMT().floatValue() : 0;
			trn.setAmt(txnAmt);
            trn.setDescription(recs[i].getTRANS_TYPE_DESCR());
            col.add(trn);
        }
        return col.toArray(new AccountTransaction[col.size()]);
    }

    private AccountTransaction[] getTransactions(OLC_DEPOSIT_TRANSACTION_ARR arr) throws SQLException {
        ArrayList<AccountTransaction> col = new ArrayList<AccountTransaction>();
        OLC_DEPOSIT_TRANSACTION_REC recs[] = arr.getArray();
        for (int i = 0; i < recs.length; i++) {
            AccountTransaction trn = new AccountTransaction();
            trn.setAmt(recs[i].getTRANS_AMT().floatValue());
            trn.setDescription(recs[i].getTRANS_TYPE_DESCR());
            col.add(trn);
        }
        return col.toArray(new AccountTransaction[col.size()]);
    }

    private AccountTransaction[] getTransactions(OLC_ACCOUNT_TRANSACTION_ARR arr) throws SQLException {
        ArrayList<AccountTransaction> col = new ArrayList<AccountTransaction>();
        OLC_ACCOUNT_TRANSACTION_REC recs[] = arr.getArray();
        for (int i = 0; i < recs.length; i++) {
            AccountTransaction trn = new AccountTransaction();
            trn.setAmt(recs[i].getTRANS_AMT().floatValue());
            trn.setDescription(recs[i].getTRANS_TYPE_DESCR());
            col.add(trn);
        }
        return col.toArray(new AccountTransaction[col.size()]);
    }

    public AccountSummaryDTO getSummaryGraph(AccountLoginDTO login) throws EtccException, EtccSecurityException {

        AccountSummaryDTO resultDTO = null;
        try {
            final boolean infoEnabled = logger.isInfoEnabled();
            if (infoEnabled)
                logger.info("getSummaryGraph.login=" + toString(login));
            BigDecimal docId = new BigDecimal(login.getAcctId());
            if (infoEnabled)
                logger.info("getSummaryGraph.docId=" + docId);
            String docType = AccountLoginDTO.LoginType.AC.toString();
            String sessionId = login.getDbSessionId();
            if (infoEnabled)
                logger.info("getSummaryGraph.seesionId=" + sessionId);
            String ipAddress = login.getLastLoginIp();
            if (infoEnabled)
                logger.info("getSummaryGraph.ipAddress=" + ipAddress);
            String userId = login.getLoginId();
            if (infoEnabled)
                logger.info("getSummaryGraph.userId=" + userId);

            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };

            OLC_ALERT_DISPLAY_ARR[] O_OLC_ALERT_DISPLAY_ARR = new OLC_ALERT_DISPLAY_ARR[] { new OLC_ALERT_DISPLAY_ARR() };

            OLC_ACCT_SUM_ARR[] O_ACCT_SUM_ARR = new OLC_ACCT_SUM_ARR[] { new OLC_ACCT_SUM_ARR() };

            java.sql.Timestamp[] O_LAST_LOGIN_DATE = new java.sql.Timestamp[] { new Timestamp(1) };

            java.sql.Timestamp[] O_REBILL_DATE = new java.sql.Timestamp[] { new Timestamp(1) };

            String[] O_CC_EFT = new String[] { "" };
            if (infoEnabled) {
                logger.info("getSummaryGraph.OLCSC_REP(conn) calling");
                logger.info("getSummaryGraph.conn=" + this.conn);
                logger.info("getSummaryGraph:  new OLCSC_REP(conn).VIEW_ACCT_SUM_GRAPH(docId="
                                + docId
                                + ", docType="
                                + docType
                                + ", sessionId="
                                + sessionId
                                + ", ipAddress="
                                + ipAddress
                                + ", userId="
                                + userId
                                + "O_ERROR_MSG_ARR,O_OLC_ALERT_DISPLAY_ARR, O_ACCT_SUM_ARR, O_LAST_LOGIN_DATE, O_REBILL_DATE, O_CC_EFT).intValue();");
            }
            int result = new OLCSC_REP(this.conn).VIEW_ACCT_SUM_GRAPH(docId, docType, sessionId, ipAddress, userId,
                    O_ERROR_MSG_ARR, O_OLC_ALERT_DISPLAY_ARR, O_ACCT_SUM_ARR, O_LAST_LOGIN_DATE, O_REBILL_DATE,
                    O_CC_EFT).intValue();
            if (infoEnabled)
                logger.info("getSummaryGraph.result=" + result);
            if (result == -1) {
                if (infoEnabled)
                    logger.info("AcctId:" + login.getAcctId() + "****" + "getSummaryGraph:Security Exception");
                throw new EtccSecurityException("security exception in getSummaryGraph()");
            }

            resultDTO = new AccountSummaryDTO();
            if (result == 0) {
                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
                // logger.info("getSummaryGraph.errorArray.length=" + errorArray.length);
                // logger.info("getSummaryGraph.errorArray[0].msg=" + errorArray[0].getERROR_MSG());
                resultDTO.setErrors(OracleUtil.convertToMessages(errorArray));

            } else if (result == 1) {
                Timestamp tm = O_LAST_LOGIN_DATE[0];
                if (tm != null)
                    resultDTO.setLastLoginDate(new Date(tm.getTime()));

                tm = O_REBILL_DATE[0];
                if (tm != null)
                    resultDTO.setRebillDate(new Date(tm.getTime()));

                if (O_ACCT_SUM_ARR[0] != null) {
                    resultDTO.setAcctSummary(convertAcctSumArrToCol(O_ACCT_SUM_ARR[0].getArray(), resultDTO));
                }
                if (O_OLC_ALERT_DISPLAY_ARR[0] != null) {
                    resultDTO.setAcctAlert(convertAcctAlertArrToCol(O_OLC_ALERT_DISPLAY_ARR[0].getArray()));
                }

                if (O_CC_EFT[0] != null) {
                    resultDTO.setBillingAcctNumber(O_CC_EFT[0]);
                }
            } else {
                String msg = "getSummaryGraph.result: Invalid result=" + result;
                logger.error(msg);
                throw new EtccException(msg);
            }
        } catch (SQLException e) {
            throw new EtccException(e.getMessage(), e);
        }

        return resultDTO;
    }

    private void populateParkingTransaction(AccountReceiptDetailsDTO dto, OLC_HAS_PARK_RECEIPT_ARR parkReceipt)
            throws SQLException {
        OLC_HAS_PARK_RECEIPT_REC[] recs = parkReceipt.getArray();
        ParkTransaction trn = new ParkTransaction();
        trn.setEntryDate(recs[0].getENTRY_DATE());
        trn.setEntryLane(recs[0].getENTRY_LANE());
        trn.setExitDate(recs[0].getEXIT_DATE());
        trn.setExitLane(recs[0].getEXIT_LANE());
        trn.setEzTagNum(recs[0].getEZ_TAG_NUMBER());
        trn.setLicPlate(recs[0].getLIC_PLATE());
        trn.setLicState(recs[0].getLIC_STATE());
        trn.setParkingFee(recs[0].getPARKING_FEE().doubleValue());
        dto.setParkTransaction(trn);
    }

    private AccountSummaryDetailBean[] convertAcctSumArrToCol(OLC_ACCT_SUM_TYPE_REC[] acctSumArr,
            AccountSummaryDTO resultDTO) throws SQLException {
        List<AccountSummaryDetailBean> col = null;

        if (acctSumArr != null && acctSumArr.length > 0) {
            final int length = acctSumArr.length;
            col = new ArrayList<AccountSummaryDetailBean>(length);
            for (int i = 0; i < length; i++) {
                OLC_ACCT_SUM_TYPE_REC rec = acctSumArr[i];

                if (rec.getAMT_TYPE().equalsIgnoreCase("DAILY Average")) {
                    resultDTO.setDaily(rec.getAMT());
                } else if (rec.getAMT_TYPE().equalsIgnoreCase("MONTHLY Average")) {
                    resultDTO.setMonthly(rec.getAMT());
                } else {
                    AccountSummaryDetailBean bean = new AccountSummaryDetailBean();
                    bean.setAmt(rec.getAMT());
                    bean.setAmtType(rec.getAMT_TYPE());
                    col.add(bean);
                }
            }
        }

        return col == null ? null : col.toArray(new AccountSummaryDetailBean[col.size()]);
    }

    private AccountAlertDetailBean[] convertAcctAlertArrToCol(OLC_ALERT_DISPLAY_REC[] acctAlertArr) throws SQLException {
        ArrayList<AccountAlertDetailBean> col = null;

        if ((acctAlertArr != null) && (acctAlertArr.length > 0)) {
            col = new ArrayList<AccountAlertDetailBean>();
            for (int i = 0; i < acctAlertArr.length; i++) {
                OLC_ALERT_DISPLAY_REC rec = acctAlertArr[i];
                AccountAlertDetailBean bean = new AccountAlertDetailBean();
                if (rec != null) {
                    bean.setAlertText(rec.getDISPLAY());
                    if (logger.isDebugEnabled())
                        logger.debug("convertAcctAlertArrToCol.rec.DISPLAY: " + rec.getDISPLAY());
                    bean.setAlertId(rec.getALERT_ID());
                    if (logger.isDebugEnabled())
                        logger.debug("convertAcctAlertArrToCol.rec.ALERT_ID: " + rec.getALERT_ID());
                } else {
                    if (logger.isDebugEnabled())
                        logger.debug("convertAcctAlertArrToCol.rec[" + i + "]=null");
                }
                col.add(bean);
            }
        } else {
            if (logger.isDebugEnabled())
                logger.debug("convertAcctAlertArrToCol.acctAlertArr is empty");
        }
        return col == null ? null : col.toArray(new AccountAlertDetailBean[col.size()]);
    }

    public String getReportURL(AccountLoginDTO loginDto, String format, String name, String args) throws EtccException,
            EtccSecurityException {
        String url = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn
                    .prepareCall("{? = call ONLINE_REPORTS.getreporturl(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            setTypeMap();

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setLong(2, loginDto.getAcctId());
            cstmt.setString(3, loginDto.getLoginType().toString());
            //Pay Installment Change
            if(StringUtils.isNotEmpty(loginDto.getLoginId())){
               cstmt.setString(4, loginDto.getLoginId());
            }else{
               cstmt.setString(4, "OLCSC_ANONYMOUS");
            }
            //Pay Installment Change
            cstmt.setString(5, loginDto.getDbSessionId());
            cstmt.setString(6, loginDto.getLastLoginIp());
            cstmt.setString(7, name);

//            cstmt.setLong(8, Long.valueOf(new SimpleDateFormat("YYYY").format(date)));
//            cstmt.setString(9, new SimpleDateFormat("MM").format(date));

            cstmt.setString(8, args);
            cstmt.setString(9, format);
            cstmt.setString(10, "OLCSC_USER");

            cstmt.registerOutParameter(11, Types.VARCHAR);
            cstmt.registerOutParameter(12, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            int result = cstmt.getInt(1);
            final boolean infoEnabled = logger.isInfoEnabled();
            if (result == -1) {
                if (infoEnabled)
                    logger.info("AcctId:" + loginDto.getAcctId() + "****" + "getReportURL result is -1");
                throw new EtccSecurityException("security exception in getReportURL");
            } else if (result == 0) {
                if (infoEnabled) {
                    Array errors = (Array) cstmt.getObject(12);
                    loginDto.setErrors(OracleUtil.convertToMessages(errors));
                    logger.info("AcctId:" + loginDto.getAcctId() + "****" + "getReportURL result is 0");
                    logger.info("Errors: " + ErrorMessageDTO.toStringBuilder(loginDto.getErrors()));
                }
                return null;
            }

            url = cstmt.getString(11);
            if (infoEnabled)
                logger.info("AcctId:" + loginDto.getAcctId() + "****" + "getReportURL returns " + url);
        } catch (SQLException e) {
            throw new EtccException("AcctId:" + loginDto.getAcctId() + "****" + "error getting getReportURL " + e.getMessage(), e);
        }
        return url;
    }

	@Override
	protected String getMonthDesc(Object statementDate) throws SQLException {
        return ((OLC_STTMT_DATE_REC) statementDate).getMONTH_DESC();
	}

	public AccountReceiptsDTO getAccountReceipts(AccountLoginDTO login,
			Calendar start, Calendar end) throws EtccSecurityException,
			EtccException {
		 try {
	            AccountReceiptsDTO dto = new AccountReceiptsDTO();
	            OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
	            OLC_VIEW_RECEIPT_HEAD_ARR O_VIEW_RECEIPT_HEAD_ARR[] = new OLC_VIEW_RECEIPT_HEAD_ARR[] { new OLC_VIEW_RECEIPT_HEAD_ARR() };
	            logger.error("getAccountReceipts==>Start");
	            BigDecimal ret = new OLCSC_VIEW_RECEIPT(this.conn).OLCSC_VIEW_RECEIPT_HEAD(
	                    new BigDecimal(login.getAcctId()), "AC", login.getDbSessionId(), login.getLastLoginIp(), login
	                            .getLoginId(), WSDateUtil.calendarToTimestamp(start), WSDateUtil.calendarToTimestamp(end),
	                    O_ERROR_MSG_ARR, O_VIEW_RECEIPT_HEAD_ARR);
	            int result = ret.intValue();
	            logger.error("getAccountReceipts.result=" + result);
	            if (result == 0) {
	                OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
	                dto.setErrors(OracleUtil.convertToMessages(errorArray));
	            } else if (result == -1) {
	                throw new EtccSecurityException("Security exception in getAccountReceipts");
	            } else {
	                populateAccountReceipts(dto, O_VIEW_RECEIPT_HEAD_ARR[0]);
	            }
	            return dto;

	        } catch (SQLException e) {
	            throw new EtccException(e.getMessage(), e);
	        }
	}
	
	public AccountTransactionHistoryResponse getAccountTransactionHistory(Long acctId, String docType, String sessionId,
			String ipAddress, String userId, Date fromDate, Date toDate, String dateType, List<Integer> categoryTab,
			List<Integer> subCategoryTab, List<Integer> acctVehIds, List<Integer> acctTagIds, Integer pageSize, Integer pageNumber,
			Integer sortOrder, BigDecimal eventId, Integer paramId, Integer maxReturnRows) throws EtccException {

		CallableStatement cstmt = null;
		AccountTransactionHistoryResponse accountTransactionHistoryResponse = null;
		ResultSet rs = null;
		try {

			cstmt = this.conn
					.prepareCall("{? = call OLCSC_REP.OLCSC_ACCT_HISTORY(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			setTypeMap();

			cstmt.registerOutParameter(1, Types.INTEGER);

			cstmt.setLong(2, acctId);
			cstmt.setString(3, docType);
			cstmt.setString(4, sessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, userId);
			
			if(fromDate !=null) {
				
				cstmt.setDate(7, new java.sql.Date(fromDate.getTime()));
			}else {
				cstmt.setObject(7, null);
			}
			
			if(toDate !=null) {
				
				cstmt.setDate(8, new java.sql.Date(toDate.getTime()));
			}else {
				cstmt.setObject(8, null);
			}
			
			cstmt.setString(9, dateType);

			Integer[] categoryInputArray = null;
			if(categoryTab!=null && !categoryTab.isEmpty()) {
				categoryInputArray = categoryTab.toArray(new Integer[0]);
			}else {
				
				categoryInputArray = new Integer[] { 19 };
			}
			final ArrayDescriptor categoryTabArrayDesc = ArrayDescriptor.createDescriptor("RITE_COMMON.ID_TYPE_TAB",
					this.conn);
			final ARRAY categoryTabArray = new ARRAY(categoryTabArrayDesc, this.conn, categoryInputArray);
			cstmt.setArray(10, categoryTabArray);
			
			Integer[] subCategoryInputArray = null;
			if(subCategoryTab !=null && !subCategoryTab.isEmpty()) {
				subCategoryInputArray = subCategoryTab.toArray(new Integer[0]);
			}else {
				
				subCategoryInputArray = new Integer[] { 76, 73, 74 };
			}
			
			final ArrayDescriptor subCategoryTabArrayDesc = ArrayDescriptor.createDescriptor("RITE_COMMON.ID_TYPE_TAB",
					this.conn);
			final ARRAY subCategoryTabArray = new ARRAY(subCategoryTabArrayDesc, this.conn, subCategoryInputArray);
			cstmt.setArray(11, subCategoryTabArray);

			Integer[] acctVehIdInputArray = null;
			if(acctVehIds !=null && !acctVehIds.isEmpty() ) {

				acctVehIdInputArray = acctVehIds.toArray(new Integer[0]);
			}else {
				
				acctVehIdInputArray = new Integer[] {};
			}
			final ArrayDescriptor acctVehIdArrayDesc = ArrayDescriptor.createDescriptor("RITE_COMMON.ID_TYPE_TAB",
					this.conn);
			final ARRAY acctVehIdArray = new ARRAY(acctVehIdArrayDesc, this.conn, acctVehIdInputArray);
			cstmt.setArray(12, acctVehIdArray);
			
			Integer[] acctTagIdInputArray = null;
			if(acctTagIds!=null && !acctTagIds.isEmpty() ) {
				acctTagIdInputArray = acctTagIds.toArray(new Integer[0]);
			}else {
				
				acctTagIdInputArray = new Integer[] {};
			}
			final ArrayDescriptor acctTagIdArrayDesc = ArrayDescriptor.createDescriptor("RITE_COMMON.ID_TYPE_TAB",
					this.conn);
			final ARRAY acctTagIdArray = new ARRAY(acctTagIdArrayDesc, this.conn, acctTagIdInputArray);
			cstmt.setArray(13, acctTagIdArray);

			if(pageSize != null) {
				
				cstmt.setInt(14, pageSize);
			}else {
				cstmt.setObject(14, null);
			}
			
			if(pageNumber != null) {
				
				cstmt.setInt(15, pageNumber);
			}else {
				cstmt.setObject(15, null);
			}
			
			if(sortOrder !=null) {
				
				cstmt.setInt(16, sortOrder);
			}else {
				cstmt.setObject(16, null);
			}
			
			if(eventId != null) {
				
				cstmt.setBigDecimal(17, eventId);
			}else {
				cstmt.setObject(17, null);
			}
			
			if(paramId !=null) {
				
				cstmt.setInt(18, paramId);
			}else {
				cstmt.setObject(18, null);
			}
			
			if(maxReturnRows !=null) {
				
				cstmt.setInt(19, maxReturnRows);
			}else {
				cstmt.setObject(19, null);
			}
			
			cstmt.registerOutParameter(20, Types.REF_CURSOR);
			cstmt.registerOutParameter(21, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			final int result = cstmt.getInt(1);
			
			accountTransactionHistoryResponse = new AccountTransactionHistoryResponse();

			if (result == 1) {
				rs = (ResultSet) cstmt.getObject(20);
								
				if (rs != null) {
					final List<AccountTransactionHistory> accountTransactionHistories = new ArrayList<AccountTransactionHistory>();

					while (rs.next()) {
						
						final AccountTransactionHistory accountTransactionHistory = new AccountTransactionHistory();

						accountTransactionHistory.setPostedDate(rs.getDate(1));
						accountTransactionHistory.setTransactionDate(rs.getDate(2));
						accountTransactionHistory.setItemId(rs.getString(3));
						accountTransactionHistory.setItemType(rs.getString(4));
						accountTransactionHistory.setItemInfo(rs.getString(5));
						accountTransactionHistory.setItemDesc(rs.getString(6));
						accountTransactionHistory.setItemLocation(rs.getString(7));
						accountTransactionHistory.setItemVehicle(rs.getString(8));
						accountTransactionHistory.setItemAmount(rs.getBigDecimal(9));
						accountTransactionHistory.setAccountBalance(rs.getBigDecimal(10));
						accountTransactionHistory.setDepositAmount(rs.getBigDecimal(11));
						accountTransactionHistory.setItemFlag(rs.getString(12));
						accountTransactionHistory.setItemGrouping(rs.getString(13));
						accountTransactionHistory.setCsrName(rs.getString(14));
						accountTransactionHistory.setPaymentId(rs.getString(15));
						accountTransactionHistory.setShiftId(rs.getString(16));
						accountTransactionHistory.setInvoiceNumber(rs.getString(17));
						accountTransactionHistory.setFileSizeBytes(rs.getBigDecimal(18));
						accountTransactionHistory.setFileGroupId(rs.getBigDecimal(19));
						accountTransactionHistory.setRunningAcctBalance(rs.getBigDecimal(20));
						accountTransactionHistory.setTotalRecord(rs.getBigDecimal(21));
						accountTransactionHistory.setQryBlockName(rs.getString(22));
						accountTransactionHistory.setWildCardAttribute(rs.getString(23));
						accountTransactionHistory.setCategoryName(rs.getString(24));
						accountTransactionHistory.setSubCategoryName(rs.getString(25));
						accountTransactionHistory.setRowNumber(rs.getBigDecimal(26));
						accountTransactionHistory.setMaxRecordMess(rs.getString(27));
						
						accountTransactionHistory.setPreviousBalance(accountTransactionHistory.getRunningAcctBalance()
								.subtract(accountTransactionHistory.getItemAmount()));
						
						accountTransactionHistories.add(accountTransactionHistory);

					}

					accountTransactionHistoryResponse.getAccountTransactionHistoryList()
							.addAll(accountTransactionHistories);
				}
			} else if (result == 0) {
				final Array errors = (Array) cstmt.getObject(21);
				accountTransactionHistoryResponse.setErrors(OracleUtil.convertToMessages(errors));
				logger.error("getAccountTransactionHistory.acctId=" + acctId);
				logger.error("getAccountTransactionHistory.error="
						+ ErrorMessageDTO.toStringBuilder(accountTransactionHistoryResponse.getErrors()));
			}

		} catch (Exception exception) {
			throw new EtccException("errror in OLCSC_REP.OLCSC_ACCT_HISTORY" + exception, exception);
		} finally {
			close(rs);
			close(cstmt);
		}

		return accountTransactionHistoryResponse;
	}	
	
}
