/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Represents an alert message for a given account.
 */
public class AlertDTO extends BaseDTO {
	/**
     * Unique ID for Serialization with Version number.
     */
    //Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
	private static final long serialVersionUID = -4234506004461422711L;
    
    private int alertId;
    private boolean addressCleanseAlertType;
    private String alertMsg;

    /**
     * Constructor.
     */
    public AlertDTO() {
        // end <init>
    }

    /**
     * Constructor.
     * @param alertId The ID of this alert.
     * @param alertMsg The Alert message for this Alert.
     */
    public AlertDTO(int alertId, String alertMsg) {
        super();
        this.alertMsg = alertMsg;
        this.alertId = alertId;
        //end <init>
    }

    @Override
    public String toString() {
        return getAlertMsg() + ',' + this.alertId;
    }

    /**
     * @return the alertMsg
     */
    public String getAlertMsg() {
        return this.alertMsg;
        // end getAlertMsg
    }

    /**
     * @param alertMsg the alertMsg to set
     */
    public void setAlertMsg(String alertMsg) {
        this.alertMsg = alertMsg;
        // end setAlertMsg
    }

    /**
     * @return the alertId
     */
    public int getAlertId() {
        return this.alertId;
        //end getAlertId
    }

    /**
     * @param alertId the alertId to set
     */
    public void setAlertId(int alertId) {
        this.alertId = alertId;
        //end setAlertId
    }

    /**
     * @return the addressCleanseAlertType
     */
    public boolean isAddressCleanseAlertType() {
        return this.addressCleanseAlertType;
        //end isAddressCleanseAlertType
    }

    /**
     * @param addressCleanseAlertType the addressCleanseAlertType to set
     */
    public void setAddressCleanseAlertType(boolean addressCleanseAlertType) {
        this.addressCleanseAlertType = addressCleanseAlertType;
        //end setAddressCleanseAlertType
    }

}
