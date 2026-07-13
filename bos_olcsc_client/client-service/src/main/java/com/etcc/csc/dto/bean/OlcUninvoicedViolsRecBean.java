package com.etcc.csc.dto.bean;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Calendar;

public class OlcUninvoicedViolsRecBean implements Serializable {
    private BigDecimal violationId;
    private String violationLocation;
    private Calendar violationDateTime;
    private String status;
    private BigDecimal cashAmt;
    private BigDecimal aviAmt;
    private BigDecimal violatorId;
    private String fullLocationName;
    private String licPlate;
    private String licState;
    private BigDecimal onlineFee;

    public OlcUninvoicedViolsRecBean() {
    }

    public void setViolationId(BigDecimal violationId) {
        this.violationId = violationId;
    }

    public BigDecimal getViolationId() {
        return violationId;
    }

    public void setViolationLocation(String violationLocation) {
        this.violationLocation = violationLocation;
    }

    public String getViolationLocation() {
        return violationLocation;
    }

    public void setViolationDateTime(Calendar violationDateTime) {
        this.violationDateTime = violationDateTime;
    }

    public Calendar getViolationDateTime() {
        return violationDateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCashAmt(BigDecimal cashAmt) {
        this.cashAmt = cashAmt;
    }

    public BigDecimal getCashAmt() {
        return cashAmt;
    }

    public void setAviAmt(BigDecimal aviAmt) {
        this.aviAmt = aviAmt;
    }

    public BigDecimal getAviAmt() {
        return aviAmt;
    }

    public void setViolatorId(BigDecimal violatorId) {
        this.violatorId = violatorId;
    }

    public BigDecimal getViolatorId() {
        return violatorId;
    }

    public void setFullLocationName(String fullLocationName) {
        this.fullLocationName = fullLocationName;
    }

    public String getFullLocationName() {
        return fullLocationName;
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

    public void setOnlineFee(BigDecimal onlineFee) {
        this.onlineFee = onlineFee;
    }

    public BigDecimal getOnlineFee() {
        return onlineFee;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("violationId=").append(violationId);
        sb.append(", violationLocation=").append(violationLocation);
        sb.append(", violationDateTime=").append(violationDateTime);
        sb.append(", status=").append(status);
        sb.append(", cashAmt=").append(cashAmt);
        sb.append(", aviAmt=").append(aviAmt);
        sb.append(", violatorId=").append(violatorId);
        sb.append(", fullLocationName=").append(fullLocationName);
        sb.append(", licPlate=").append(licPlate);
        sb.append(", licState=").append(licState);
        sb.append(", onlineFee=").append(onlineFee);
        sb.append("]");
        return sb.toString();
    }
}
