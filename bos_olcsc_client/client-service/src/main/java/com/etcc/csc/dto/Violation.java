/*
 * Copyright 2009 Electronic Transaction Consultants
 */
/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Calendar;

/**
 * Representation of a Violation.
 */
public class Violation implements Serializable {
    
    /**
     * Unique ID for serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = -3690997121678137349L;
    
    private BigDecimal cashAmount;
    private BigDecimal aviAmount;
    private String id;
    private Calendar timestamp;
    private String location;
    private String locationDesc;
    private String status;
    private String type;
    private String licPlate;
    private String licState;
    private BigDecimal violatorId;
    private BigDecimal onlineFee;
    private boolean authorized;

    /**
     * Constructor.
     */
    public Violation() {
        // end <init>
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("cashAmount=");
        sb.append(this.cashAmount);
        sb.append(", aviAmount=");
        sb.append(this.aviAmount);
        sb.append(", id=");
        sb.append(this.id);
        sb.append(", timestamp=");
        sb.append(this.timestamp);
        sb.append(", location=");
        sb.append(this.location);
        sb.append(", locationDesc=");
        sb.append(this.locationDesc);
        sb.append(", status=");
        sb.append(this.status);
        sb.append(", type=");
        sb.append(this.type);
        sb.append(", licPlate=");
        sb.append(this.licPlate);
        sb.append(", licState=");
        sb.append(this.licState);
        sb.append(", violatorId=");
        sb.append(this.violatorId);
        sb.append(", onlineFee=");
        sb.append(this.onlineFee);
        sb.append(", authorized=");
        sb.append(this.authorized);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the cashAmount
     */
    public BigDecimal getCashAmount() {
        return this.cashAmount;
        // end getCashAmount
    }

    /**
     * @param cashAmount the cashAmount to set
     */
    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
        // end setCashAmount
    }

    /**
     * @return the aviAmount
     */
    public BigDecimal getAviAmount() {
        return this.aviAmount;
        // end getAviAmount
    }

    /**
     * @param aviAmount the aviAmount to set
     */
    public void setAviAmount(BigDecimal aviAmount) {
        this.aviAmount = aviAmount;
        // end setAviAmount
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
        // end getId
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
        // end setId
    }

    /**
     * @return the timestamp
     */
    public Calendar getTimestamp() {
        return this.timestamp;
        // end getTimestamp
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
        // end setTimestamp
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return this.location;
        // end getLocation
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
        // end setLocation
    }

    /**
     * @return the locationDesc
     */
    public String getLocationDesc() {
        return this.locationDesc;
        // end getLocationDesc
    }

    /**
     * @param locationDesc the locationDesc to set
     */
    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
        // end setLocationDesc
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return this.status;
        // end getStatus
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
        // end setStatus
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
        // end getType
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
        // end setType
    }

    /**
     * @return the licPlate
     */
    public String getLicPlate() {
        return this.licPlate;
        // end getLicPlate
    }

    /**
     * @param licPlate the licPlate to set
     */
    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
        // end setLicPlate
    }

    /**
     * @return the licState
     */
    public String getLicState() {
        return this.licState;
        // end getLicState
    }

    /**
     * @param licState the licState to set
     */
    public void setLicState(String licState) {
        this.licState = licState;
        // end setLicState
    }

    /**
     * @return the violatorId
     */
    public BigDecimal getViolatorId() {
        return this.violatorId;
        // end getViolatorId
    }

    /**
     * @param violatorId the violatorId to set
     */
    public void setViolatorId(BigDecimal violatorId) {
        this.violatorId = violatorId;
        // end setViolatorId
    }

    /**
     * @return the onlineFee
     */
    public BigDecimal getOnlineFee() {
        return this.onlineFee;
        // end getOnlineFee
    }

    /**
     * @param onlineFee the onlineFee to set
     */
    public void setOnlineFee(BigDecimal onlineFee) {
        this.onlineFee = onlineFee;
        // end setOnlineFee
    }

    /**
     * @return the authorized
     */
    public boolean isAuthorized() {
        return this.authorized;
        // end isAuthorized
    }

    /**
     * @param authorized the authorized to set
     */
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
        // end setAuthorized
    }
}
