/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Calendar;

/**
 * Transaction Data Transfer Object.  Copied from OLCSCService com.etcc.csc.dto.TransactionDTO
 *
 */
public class TransactionDTO extends BaseDTO {
    
    /**
     * Unique ID for Serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = 4344407323947406285L;
    
    private Calendar transactionDate;
    private String tagId;
    private String licPlate;
    private String licState;
    private String lane;
    private String direction;
    private String location;
    private String transTypeDescr;
    private double amount;
    private String laneDescription;
    private int serialNum;
    private Calendar postedDate;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("transactionDate=");
        sb.append(this.transactionDate);
        sb.append(", tagId=");
        sb.append(this.tagId);
        sb.append(", licPlate=");
        sb.append(this.licPlate);
        sb.append(", licState=");
        sb.append(this.licState);
        sb.append(", lane=");
        sb.append(this.lane);
        sb.append(", direction=");
        sb.append(this.direction);
        sb.append(", location=");
        sb.append(this.location);
        sb.append("transTypeDescr=");
        sb.append(this.transTypeDescr);
        sb.append("amount=");
        sb.append(this.amount);
        sb.append("postedDate=");
        sb.append(this.postedDate);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the transactionDate
     */
    public Calendar getTransactionDate() {
        return this.transactionDate;
        //end getTransactionDate
    }

    /**
     * @param transactionDate the transactionDate to set
     */
    public void setTransactionDate(Calendar transactionDate) {
        this.transactionDate = transactionDate;
        //end setTransactionDate
    }

    /**
     * @return the tagId
     */
    public String getTagId() {
        return this.tagId;
        //end getTagId
    }

    /**
     * @param tagId the tagId to set
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
        //end setTagId
    }

    /**
     * @return the licPlate
     */
    public String getLicPlate() {
        return this.licPlate;
        //end getLicPlate
    }

    /**
     * @param licPlate the licPlate to set
     */
    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
        //end setLicPlate
    }

    /**
     * @return the licState
     */
    public String getLicState() {
        return this.licState;
        //end getLicState
    }

    /**
     * @param licState the licState to set
     */
    public void setLicState(String licState) {
        this.licState = licState;
        //end setLicState
    }

    /**
     * @return the lane
     */
    public String getLane() {
        return this.lane;
        //end getLane
    }

    /**
     * @param lane the lane to set
     */
    public void setLane(String lane) {
        this.lane = lane;
        //end setLane
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return this.direction;
        //end getDirection
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
        //end setDirection
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return this.location;
        //end getLocation
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
        //end setLocation
    }

    /**
     * @return the transTypeDescr
     */
    public String getTransTypeDescr() {
        return this.transTypeDescr;
        //end getTransTypeDescr
    }

    /**
     * @param transTypeDescr the transTypeDescr to set
     */
    public void setTransTypeDescr(String transTypeDescr) {
        this.transTypeDescr = transTypeDescr;
        //end setTransTypeDescr
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return this.amount;
        //end getAmount
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
        //end setAmount
    }

    /**
     * @return the laneDescription
     */
    public String getLaneDescription() {
        return this.laneDescription;
        //end getLaneDescription
    }

    /**
     * @param laneDescription the laneDescription to set
     */
    public void setLaneDescription(String laneDescription) {
        this.laneDescription = laneDescription;
        //end setLaneDescription
    }

    /**
     * @return the serialNum
     */
    public int getSerialNum() {
        return this.serialNum;
        //end getSerialNum
    }

    /**
     * @param serialNum the serialNum to set
     */
    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
        //end setSerialNum
    }

    /**
     * @return the postedDate
     */
    public Calendar getPostedDate() {
        return this.postedDate;
        //end getPostedDate
    }

    /**
     * @param postedDate the postedDate to set
     */
    public void setPostedDate(Calendar postedDate) {
        this.postedDate = postedDate;
        //end setPostedDate
    }


}
