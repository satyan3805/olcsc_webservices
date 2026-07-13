/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;

import java.util.Date;

/**
 * Representation of a parking transaction.
 */
public class ParkTransaction implements Serializable {
    
    /**
     * Unique ID for serialization with version.
     */
    //Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = -886013341774150430L;
    
    private Date entryDate;
    private String entryLane;
    private Date exitDate;
    private String exitLane;
    private String ezTagNum;
    private String licPlate;
    private String licState;
    private double parkingFee;

    /**
     * Constructor.
     */
    public ParkTransaction() {
        //end <init>
    }

    /**
     * @return the entryDate
     */
    public Date getEntryDate() {
        return this.entryDate;
        //end getEntryDate
    }

    /**
     * @param entryDate the entryDate to set
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
        //end setEntryDate
    }

    /**
     * @return the entryLane
     */
    public String getEntryLane() {
        return this.entryLane;
        //end getEntryLane
    }

    /**
     * @param entryLane the entryLane to set
     */
    public void setEntryLane(String entryLane) {
        this.entryLane = entryLane;
        //end setEntryLane
    }

    /**
     * @return the exitDate
     */
    public Date getExitDate() {
        return this.exitDate;
        //end getExitDate
    }

    /**
     * @param exitDate the exitDate to set
     */
    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
        //end setExitDate
    }

    /**
     * @return the exitLane
     */
    public String getExitLane() {
        return this.exitLane;
        //end getExitLane
    }

    /**
     * @param exitLane the exitLane to set
     */
    public void setExitLane(String exitLane) {
        this.exitLane = exitLane;
        //end setExitLane
    }

    /**
     * @return the ezTagNum
     */
    public String getEzTagNum() {
        return this.ezTagNum;
        //end getEzTagNum
    }

    /**
     * @param ezTagNum the ezTagNum to set
     */
    public void setEzTagNum(String ezTagNum) {
        this.ezTagNum = ezTagNum;
        //end setEzTagNum
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
     * @return the parkingFee
     */
    public double getParkingFee() {
        return this.parkingFee;
        //end getParkingFee
    }

    /**
     * @param parkingFee the parkingFee to set
     */
    public void setParkingFee(double parkingFee) {
        this.parkingFee = parkingFee;
        //end setParkingFee
    }

//    public String getEntryDateStr() {
//        return String.format("%1$tB %1$td, %1$tY %1$tH:%1$tM", this.entryDate);
//    }


//    public String getExitDateStr() {
//        return String.format("%1$tB %1$td, %1$tY %1$tH:%1$tM", this.exitDate);
//    }

}
