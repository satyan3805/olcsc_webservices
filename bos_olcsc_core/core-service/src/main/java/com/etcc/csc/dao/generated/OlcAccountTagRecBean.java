package com.etcc.csc.dao.generated;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;

import com.etcc.csc.common.DateUtil;
import com.etcc.csc.plsql.OlcAccountTagRec;

public class OlcAccountTagRecBean {
    private BigDecimal acctTagSeq;
    private String acctTagStatus;
    private String barcodePrefix;
    private String tagId;
    private String agencyId;
    private String agencyDesc;
    private String licPlate;
    private String licState;
    private String vehicleYear;
    private String vehicleColor;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleClassCode;
    private String vehicleClassDesc;
    private String tempPlateFlag;
    private Calendar plateExpirDate;
    private String tagStatusDesc;
    
    public OlcAccountTagRecBean() {
    }
    
    public OlcAccountTagRecBean(OlcAccountTagRec tagRec) throws SQLException {
        this.acctTagSeq = tagRec.getAcctTagSeq();
        this.acctTagStatus = tagRec.getAcctTagStatus();
        this.agencyDesc = tagRec.getAgencyDesc();
        this.agencyId = tagRec.getAgencyId();
        this.barcodePrefix = tagRec.getBarcodePrefix();
        this.licPlate = tagRec.getLicPlate();
        this.licState = tagRec.getLicState();
        this.plateExpirDate = DateUtil.timestampToCalendar(tagRec.getPlateExpirDate());
        this.tagId = tagRec.getTagId();
        this.tagStatusDesc = tagRec.getTagStatusDesc();
        this.tempPlateFlag = tagRec.getTempPlateFlag();
        this.vehicleClassCode = tagRec.getVehicleClassCode();
        this.vehicleClassDesc = tagRec.getVehicleClassDesc();
        this.vehicleColor = tagRec.getVehicleColor();
        this.vehicleMake = tagRec.getVehicleMake();
        this.vehicleModel = tagRec.getVehicleModel();
        this.vehicleYear = tagRec.getVehicleYear();
    }


    public void setAcctTagSeq(BigDecimal acctTagSeq) {
        this.acctTagSeq = acctTagSeq;
    }

    public BigDecimal getAcctTagSeq() {
        return acctTagSeq;
    }

    public void setAcctTagStatus(String acctTagStatus) {
        this.acctTagStatus = acctTagStatus;
    }

    public String getAcctTagStatus() {
        return acctTagStatus;
    }

    public void setBarcodePrefix(String barcodePrefix) {
        this.barcodePrefix = barcodePrefix;
    }

    public String getBarcodePrefix() {
        return barcodePrefix;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyDesc(String agencyDesc) {
        this.agencyDesc = agencyDesc;
    }

    public String getAgencyDesc() {
        return agencyDesc;
    }

    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
    }

    public String getLicPlate() {
        return licPlate;
    }

    public void setLicState(String licState) {
        this.licState = licState;
    }

    public String getLicState() {
        return licState;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
    }

    public String getVehicleClassCode() {
        return vehicleClassCode;
    }

    public void setVehicleClassDesc(String vehicleClassDesc) {
        this.vehicleClassDesc = vehicleClassDesc;
    }

    public String getVehicleClassDesc() {
        return vehicleClassDesc;
    }

    public void setTempPlateFlag(String tempPlateFlag) {
        this.tempPlateFlag = tempPlateFlag;
    }

    public String getTempPlateFlag() {
        return tempPlateFlag;
    }

    public void setPlateExpirDate(Calendar plateExpirDate) {
        this.plateExpirDate = plateExpirDate;
    }

    public Calendar getPlateExpirDate() {
        return plateExpirDate;
    }

    public void setTagStatusDesc(String tagStatusDesc) {
        this.tagStatusDesc = tagStatusDesc;
    }

    public String getTagStatusDesc() {
        return tagStatusDesc;
    }
}
