/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Calendar;

import com.etcc.csc.util.CoreDateUtil;

/**
 * Represents a Toll Violation.
 */
public class ViolationDTO extends BaseDTO {
    /**
     * Unique ID for serialization with version.
     */
    // Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = -1460520438124340695L;

    private long violationId;
    private Calendar dateTime;
    private String location;
    private float amountDue;

    /**
     * Constructor.
     */
    public ViolationDTO() {
        super();
        // end <init>
    }

    /**
     * Constructor.
     * 
     * @param violationId the Database ID of this violation.
     * @param dateTime the date & time this violation occured.
     * @param location the location of this violation.
     * @param amtDue The amount of the toll for this violation.
     */
    public ViolationDTO(long violationId, Calendar dateTime, String location, float amtDue) {
        super();
        this.violationId = violationId;
        this.dateTime = dateTime;
        this.location = location;
        this.amountDue = amtDue;
    }

// public void setDateTime(Calendar dateTime) {
// this.dateTime = dateTime;
// setStrDateTime(String.format("%1$tm/%1$td/%1$tY %1$tl:%1$tM %1$Tp", dateTime));
// }

//    @Deprecated
//    public String getStrDateTime() {
//        if (dateTime == null)
//            return "";
//        else
//            return CoreDateUtil.getShortDateTime(dateTime.getTime());
//    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("violationId=");
        sb.append(this.violationId);
        sb.append(",dateTime=");
        sb.append(CoreDateUtil.getMediumDateTime(this.dateTime));
        sb.append(",location=");
        sb.append(this.location);
        sb.append(",amtDue=");
        sb.append(this.amountDue);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the violationId
     */
    public long getViolationId() {
        return this.violationId;
        // end getViolationId
    }

    /**
     * @param violationId the violationId to set
     */
    public void setViolationId(long violationId) {
        this.violationId = violationId;
        // end setViolationId
    }

    /**
     * @return the dateTime
     */
    public Calendar getDateTime() {
        return this.dateTime;
        // end getDateTime
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
        // end setDateTime
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
     * @return the amtDue
     */
    public float getAmountDue() {
        return this.amountDue;
        // end getAmtDue
    }

    /**
     * @param amtDue the amtDue to set
     */
    public void setAmountDue(float amtDue) {
        this.amountDue = amtDue;
        // end setAmtDue
    }
}
