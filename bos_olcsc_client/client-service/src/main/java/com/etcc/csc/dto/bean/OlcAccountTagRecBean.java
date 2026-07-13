package com.etcc.csc.dto.bean;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Calendar;

public class OlcAccountTagRecBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6531557512528635188L;
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

    public void setAcctTagSeq(BigDecimal acctTagSeq) {
        this.acctTagSeq = acctTagSeq;
    }

    public BigDecimal getAcctTagSeq() {
        return this.acctTagSeq;
    }

    public void setAcctTagStatus(String acctTagStatus) {
        this.acctTagStatus = acctTagStatus;
    }

    public String getAcctTagStatus() {
        return this.acctTagStatus;
    }

    public void setBarcodePrefix(String barcodePrefix) {
        this.barcodePrefix = barcodePrefix;
    }

    public String getBarcodePrefix() {
        return this.barcodePrefix;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return this.tagId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyId() {
        return this.agencyId;
    }

    public void setAgencyDesc(String agencyDesc) {
        this.agencyDesc = agencyDesc;
    }

    public String getAgencyDesc() {
        return this.agencyDesc;
    }

    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
    }

    public String getLicPlate() {
        return this.licPlate;
    }

    public void setLicState(String licState) {
        this.licState = licState;
    }

    public String getLicState() {
        return this.licState;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleYear() {
        return this.vehicleYear;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleColor() {
        return this.vehicleColor;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleMake() {
        return this.vehicleMake;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleModel() {
        return this.vehicleModel;
    }

    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
    }

    public String getVehicleClassCode() {
        return this.vehicleClassCode;
    }

    public void setVehicleClassDesc(String vehicleClassDesc) {
        this.vehicleClassDesc = vehicleClassDesc;
    }

    public String getVehicleClassDesc() {
        return this.vehicleClassDesc;
    }

    public void setTempPlateFlag(String tempPlateFlag) {
        this.tempPlateFlag = tempPlateFlag;
    }

    public String getTempPlateFlag() {
        return this.tempPlateFlag;
    }

    public void setPlateExpirDate(Calendar plateExpirDate) {
        this.plateExpirDate = plateExpirDate;
    }

    public Calendar getPlateExpirDate() {
        return this.plateExpirDate;
    }

    public void setTagStatusDesc(String tagStatusDesc) {
        this.tagStatusDesc = tagStatusDesc;
    }

    public String getTagStatusDesc() {
        return this.tagStatusDesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("acctTagSeq="); sb.append(this.acctTagSeq);
        sb.append(", acctTagStatus="); sb.append(this.acctTagStatus);
        sb.append(", barcodePrefix="); sb.append(this.barcodePrefix);
        sb.append(", tagId="); sb.append(this.tagId);
        sb.append(", agencyId="); sb.append(this.agencyId);
        sb.append(", agencyDesc="); sb.append(this.agencyDesc);
        sb.append(", licPlate="); sb.append(this.licPlate);
        sb.append(", licState="); sb.append(this.licState);
        sb.append(", vehicleYear="); sb.append(this.vehicleYear);
        sb.append(", vehicleColor="); sb.append(this.vehicleColor);
        sb.append(", vehicleMake="); sb.append(this.vehicleMake);
        sb.append(", vehicleModel="); sb.append(this.vehicleModel);
        sb.append(", vehicleClassCode="); sb.append(this.vehicleClassCode);
        sb.append(", vehicleClassDesc="); sb.append(this.vehicleClassDesc);
        sb.append(", tempPlateFlag="); sb.append(this.tempPlateFlag);
        sb.append(", plateExpirDate="); sb.append(this.plateExpirDate);
        sb.append(", tagStatusDesc="); sb.append(this.tagStatusDesc);
        sb.append("]");
        return sb.toString();
    }
}
