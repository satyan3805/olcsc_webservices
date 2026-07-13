/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;

import com.etcc.csc.dto.bean.OlcAccountHistoryRecBean;


/**
 * Data Transfer Object for Transaction Records.
 */
public class TransactionRecordsDTO extends BaseDTO {
    
    /**
     * Unique ID for Serialization.
     */
    //Do NOT regenerate for external clients such as Idea/HCTRA
    private static final long serialVersionUID = 9109900334428647153L;
    
    protected OlcAccountHistoryRecBean[] records;
    protected OlcAccountHistoryRecBean[] parkingRecords;
    protected double totalAmt;
    
    /**
     * Constructor.
     */
    public TransactionRecordsDTO() {
        //end <init>
    }
    
    public OlcAccountHistoryRecBean[] getRecords() {
        return this.records;
    }
    
    public void setRecords(OlcAccountHistoryRecBean[] records) {
        this.records = records;
    }
    
    /**
     * Gets the total amount for the transaction records.
     * @return the total amount for the transaction records.
     */
    public double getTotalAmt() {
        return this.totalAmt;
    }

    /**
     * Sets the total amount for the transaction records.
     * @param totalAmt the total amount for the transaction records.
     */
    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }
    
    public OlcAccountHistoryRecBean[] getParkingRecords() {
        return this.parkingRecords;
    }
    
    public void setParkingRecords(OlcAccountHistoryRecBean[] parkingRecords) {
        this.parkingRecords = parkingRecords;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("records="); sb.append(Arrays.toString(this.records));
        sb.append(", totalAmt="); sb.append(this.totalAmt);
        sb.append(", parkingRecords="); sb.append(Arrays.toString(this.parkingRecords));
        sb.append("]");
        return sb.toString();
    }
}
