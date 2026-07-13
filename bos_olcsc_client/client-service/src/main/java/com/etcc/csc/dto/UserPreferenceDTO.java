/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;

/**
 * Contains information about the user's environment.
 */
public class UserPreferenceDTO extends BaseDTO {
    /**
     * Unique ID for Serialization with version
     */
    //Do not regenerate for external clients such as Idea/HCTRA
    private static final long serialVersionUID = -3403809811598823312L;
    private long prefId;
    private String displayDesc;
    private String prefValue;
    private String prefType;
    private int displayOrder;
    private AccountDeviceDTO[] deviceValues;
    private UserPreferenceValueDTO[] userPrefValues;
    private long selectedDeviceId;
    private String selectedDeviceValue;
    private String selecteduserPrefValue;
    private long steId;

    /**
     * Constructor.
     */
    public UserPreferenceDTO() {
        // end <init>
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("prefId=");
        sb.append(this.prefId);
        sb.append(", displayDesc=");
        sb.append(this.displayDesc);
        sb.append(", prefValue=");
        sb.append(this.prefValue);
        sb.append(", prefType=");
        sb.append(this.prefType);
        sb.append(", displayOrder=");
        sb.append(this.displayOrder);
        sb.append(", deviceValues=");
        sb.append(Arrays.toString(this.deviceValues));
        sb.append(", userPrefValues=");
        sb.append(Arrays.toString(this.userPrefValues));
        sb.append(", selectedDeviceId=");
        sb.append(this.selectedDeviceId);
        sb.append(", selectedDeviceValue=");
        sb.append(this.selectedDeviceValue);
        sb.append(", selecteduserPrefValue=");
        sb.append(this.selecteduserPrefValue);
        sb.append(", steId=");
        sb.append(this.steId);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the prefId
     */
    public long getPrefId() {
        return this.prefId;
        // end getPrefId
    }

    /**
     * @param prefId the prefId to set
     */
    public void setPrefId(long prefId) {
        this.prefId = prefId;
        // end setPrefId
    }

    /**
     * @return the displayDesc
     */
    public String getDisplayDesc() {
        return this.displayDesc;
        // end getDisplayDesc
    }

    /**
     * @param displayDesc the displayDesc to set
     */
    public void setDisplayDesc(String displayDesc) {
        this.displayDesc = displayDesc;
        // end setDisplayDesc
    }

    /**
     * @return the prefValue
     */
    public String getPrefValue() {
        return this.prefValue;
        // end getPrefValue
    }

    /**
     * @param prefValue the prefValue to set
     */
    public void setPrefValue(String prefValue) {
        this.prefValue = prefValue;
        // end setPrefValue
    }

    /**
     * @return the prefType
     */
    public String getPrefType() {
        return this.prefType;
        // end getPrefType
    }

    /**
     * @param prefType the prefType to set
     */
    public void setPrefType(String prefType) {
        this.prefType = prefType;
        // end setPrefType
    }

    /**
     * @return the displayOrder
     */
    public int getDisplayOrder() {
        return this.displayOrder;
        // end getDisplayOrder
    }

    /**
     * @param displayOrder the displayOrder to set
     */
    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
        // end setDisplayOrder
    }

    /**
     * @return the deviceValues
     */
    public AccountDeviceDTO[] getDeviceValues() {
        return this.deviceValues;
        // end getDeviceValues
    }

    /**
     * @param deviceValues the deviceValues to set
     */
    public void setDeviceValues(AccountDeviceDTO[] deviceValues) {
        this.deviceValues = deviceValues;
        // end setDeviceValues
    }

    /**
     * @return the userPrefValues
     */
    public UserPreferenceValueDTO[] getUserPrefValues() {
        return this.userPrefValues;
        // end getUserPrefValues
    }

    /**
     * @param userPrefValues the userPrefValues to set
     */
    public void setUserPrefValues(UserPreferenceValueDTO[] userPrefValues) {
        this.userPrefValues = userPrefValues;
        // end setUserPrefValues
    }

    /**
     * @return the selectedDeviceId
     */
    public long getSelectedDeviceId() {
        return this.selectedDeviceId;
        // end getSelectedDeviceId
    }

    /**
     * @param selectedDeviceId the selectedDeviceId to set
     */
    public void setSelectedDeviceId(long selectedDeviceId) {
        this.selectedDeviceId = selectedDeviceId;
        // end setSelectedDeviceId
    }

    /**
     * @return the selectedDeviceValue
     */
    public String getSelectedDeviceValue() {
        return this.selectedDeviceValue;
        // end getSelectedDeviceValue
    }

    /**
     * @param selectedDeviceValue the selectedDeviceValue to set
     */
    public void setSelectedDeviceValue(String selectedDeviceValue) {
        this.selectedDeviceValue = selectedDeviceValue;
        // end setSelectedDeviceValue
    }

    /**
     * @return the selecteduserPrefValue
     */
    public String getSelecteduserPrefValue() {
        return this.selecteduserPrefValue;
        // end getSelecteduserPrefValue
    }

    /**
     * @param selecteduserPrefValue the selecteduserPrefValue to set
     */
    public void setSelecteduserPrefValue(String selecteduserPrefValue) {
        this.selecteduserPrefValue = selecteduserPrefValue;
        // end setSelecteduserPrefValue
    }

    /**
     * @return the steId
     */
    public long getSteId() {
        return this.steId;
        // end getSteId
    }

    /**
     * @param steId the steId to set
     */
    public void setSteId(long steId) {
        this.steId = steId;
        // end setSteId
    }

}
