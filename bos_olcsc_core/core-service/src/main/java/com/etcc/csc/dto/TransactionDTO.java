package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Calendar;

public class TransactionDTO extends BaseDTO {
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

    public TransactionDTO() {
    }

    public void setTransactionDate(Calendar transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Calendar getTransactionDate() {
        return transactionDate;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
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

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getLane() {
        return lane;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setTransTypeDescr(String transTypeDescr) {
        this.transTypeDescr = transTypeDescr;
    }

    public String getTransTypeDescr() {
        return transTypeDescr;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
    public void setLaneDescription(String laneDescription) {
        this.laneDescription = laneDescription;
    }

    public String getLaneDescription() {
        return laneDescription;
    }
    
    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public int getSerialNum() {
        return serialNum;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("transactionDate=");
        sb.append(transactionDate);
        sb.append(", tagId=");
        sb.append(tagId);
        sb.append(", licPlate=");
        sb.append(licPlate);
        sb.append(", licState=");
        sb.append(licState);
        sb.append(", lane=");
        sb.append(lane);
        sb.append(", direction=");
        sb.append(direction);
        sb.append(", location=");
        sb.append(location);
        sb.append("transTypeDescr=");
        sb.append(transTypeDescr);
        sb.append("amount=");
        sb.append(amount);
        sb.append("postedDate=");
        sb.append(postedDate);
        sb.append("]");
        return sb.toString();
    }

    public void setPostedDate(Calendar postedDate) {
        this.postedDate = postedDate;
    }

    public Calendar getPostedDate() {
        return postedDate;
    }
}
