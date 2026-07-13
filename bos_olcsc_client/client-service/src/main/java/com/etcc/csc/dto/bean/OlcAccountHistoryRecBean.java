package com.etcc.csc.dto.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.CoreDateUtil;
import com.etcc.csc.util.StringUtil;

public class OlcAccountHistoryRecBean implements Serializable {
    private static final long serialVersionUID = -7288871228544235654L;

    /**
     * Constructor.
     */
    public OlcAccountHistoryRecBean() { }

/*
    public OlcAccountHistoryRecBean(OLC_ACCOUNT_HISTORY_REC olcAccountHistoryRec) throws SQLException{
        if (olcAccountHistoryRec!=null)
        {
            Calendar transactionDate = Calendar.getInstance();
            if (olcAccountHistoryRec.getTRXN_DATE()!=null)
            {
                transactionDate.setTime(new Date(olcAccountHistoryRec.getTRXN_DATE().getTime()));
                setTrxnDate(transactionDate);
            }

            Calendar postedDate = Calendar.getInstance();
            //System.out.println("posted date:"+olcAccountHistoryRec.getPostedDate());
            if (olcAccountHistoryRec.getPOSTED_DATE()!=null) {
                postedDate.setTime(new Date(olcAccountHistoryRec.getPOSTED_DATE().getTime()));
                setPostedDate(postedDate);
            }

            setTagId(olcAccountHistoryRec.getTAG_ID());
            setLicensePlate(olcAccountHistoryRec.getLICENSE_PLATE());
            setLaneName(olcAccountHistoryRec.getLANE_NAME());
            setLocationName(olcAccountHistoryRec.getLOCATION_NAME());
            setTransType(olcAccountHistoryRec.getTRANS_TYPE());
            setAmount(olcAccountHistoryRec.getAMOUNT());
            setDirection(olcAccountHistoryRec.getDIRECTION());
            setParkingReportUrl(olcAccountHistoryRec.getPARKING_REPORT_URL());
            setSerialNum(olcAccountHistoryRec.getSERIAL_NUM());
            setLaneFullName(olcAccountHistoryRec.getLANE_FULL_NAME());
            setUnitId(olcAccountHistoryRec.getUNIT_ID());
            setLicenseState(olcAccountHistoryRec.getLICENSE_STATE());
            setVehicleNickName(olcAccountHistoryRec.getNICKNAME());
            setVehicleClassCode(olcAccountHistoryRec.getVEHICLE_CLASS_CODE());
        }
    }
    */

    private Calendar trxnDate;
    private Calendar postedDate;
    private String tagId;
    private String licensePlate;
    private String laneName;
    private String laneFullName;
    private String locationName;
    private String transType;
    private java.math.BigDecimal amount;
    private String direction;
    private String parkingReportUrl;
    private java.math.BigDecimal serialNum;
    private String unitId;
    private String licenseState;
    private String vehicleNickName;
//    private String strTrxnDate;
//    private String strPostedDate;
    private String vehicleClassCode;



    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return this.tagId;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public String getLaneName() {
        return this.laneName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransType() {
        return this.transType;
    }

    @Deprecated
    @IgnoreProperty
    public void setStrAmount(String strAmount) {
    	throw new UnsupportedOperationException("Use setAmount(BigDecimal) instead.");
    }

    @Deprecated
    @IgnoreProperty
    public String getStrAmount() {
        Logger.getLogger(this.getClass()).warn("Deprecated Method \"getStrAmount\" called -- use JSP Formatters");
        Thread.dumpStack();

    	if (amount == null)
            return "";
    	else
            return StringUtil.currencyFormat.format(amount.doubleValue());
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
//        if (amount != null)
//        {
//            NumberFormat nf = NumberFormat.getInstance();
//            nf.setMaximumFractionDigits(2);
//            nf.setMinimumFractionDigits(2);
//            strAmount = nf.format(amount.doubleValue());
//        }
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    @Deprecated
    @IgnoreProperty
    public void setStrTrxnDate(String strTrxnDate) {
    	throw new UnsupportedOperationException("Use setTrxnDate(Calendar) instead.");
    }

    @Deprecated
    @IgnoreProperty
    public String getStrTrxnDate() {
    	if (trxnDate == null)
            return "";
    	else
            return CoreDateUtil.getShortDateTime(trxnDate.getTime());
    }

    public void setTrxnDate(Calendar trxnDate) {
        this.trxnDate = trxnDate;
//        if (trxnDate != null)
//         this.strTrxnDate = DateUtil.getShortDateTime(trxnDate.getTime());
    }

    public Calendar getTrxnDate() {
        return this.trxnDate;
    }

    @Deprecated
    @IgnoreProperty
    public void setStrPostedDate(String strPostedDate) {
    	throw new UnsupportedOperationException("Use setPostedDate(Calendar) instead.");
    }

    @Deprecated
    @IgnoreProperty
    public String getStrPostedDate() {
    	if (postedDate == null)
            return "";
    	else
            return CoreDateUtil.getShortDateTime(postedDate.getTime());
    }

    public void setPostedDate(Calendar postedDate){
        this.postedDate = postedDate;
//        if (postedDate != null)
//          this.strPostedDate = DateUtil.getShortDateTime(postedDate.getTime());
    }

    public Calendar getPostedDate() {
        return this.postedDate;
    }


    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setParkingReportUrl(String parkingReportUrl) {
        this.parkingReportUrl = parkingReportUrl;
    }

    public String getParkingReportUrl() {
        return this.parkingReportUrl;
    }

    public void setSerialNum(BigDecimal serialNum) {
        this.serialNum = serialNum;
    }

    public BigDecimal getSerialNum() {
        return this.serialNum;
    }

    public String getLaneFullName() {
        return this.laneFullName;
    }

    public void setLaneFullName(String laneFullName){
        this.laneFullName = laneFullName;
    }


    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return this.unitId;
    }


    public void setLicenseState(String licenseState) {
        this.licenseState = licenseState;
    }

    public String getLicenseState() {
        return this.licenseState;
    }

    public void setVehicleNickName(String vehicleNickName) {
        this.vehicleNickName = vehicleNickName;
    }

    public String getVehicleNickName() {
    	//Start defect#9873
    	//return this.vehicleNickName;
    	if (!StringUtils.isBlank(this.vehicleNickName)) {
        return this.vehicleNickName;
    	} else {
    		if (!StringUtils.isBlank(this.licenseState)) {
    			return this.licenseState +"-"+ this.licensePlate;
    		} else {
    			return this.licensePlate;
    		}
    	}
    	//End defect#9873
    }

//    public String getStrTrxnDate(){
//        return this.strTrxnDate;
//    }

//    public String getStrPostedDate(){
//        return strPostedDate;
//    }

    @IgnoreProperty
    public String getTransactionMONYY() {
        return String.format("%1$tb %1$te", this.trxnDate);
    }
    @IgnoreProperty
    public String getTransactionHHMM() {
        return String.format("%1$tI:%1$tM %1$Tp", this.trxnDate);
    }

//    public String getPostedMMDDYYHHMM() {
//        return String.format("%1$tm/%1$td/%1$tY %1$tI:%1$tM %1$Tp", this.postedDate);
//    }
//
//    public String getTransactionMMDDYYHHMM() {
//        return String.format("%1$tm/%1$td/%1$tY %1$tI:%1$tM %1$Tp", this.trxnDate);
//    }

    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
    }

    public String getVehicleClassCode() {
        return this.vehicleClassCode;
    }
}
