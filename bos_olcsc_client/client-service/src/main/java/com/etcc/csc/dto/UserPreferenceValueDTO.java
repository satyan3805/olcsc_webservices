/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;


/**
 * Represents an Account Device.
 */
public class UserPreferenceValueDTO extends BaseDTO {
    /**
     * Unique ID for Serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA
    private static final long serialVersionUID = -1309949698519726786L;
    
    private long userPrefId;
    private String upValue;
    private boolean defaultFlag;

    /**
     * Constructor.
     */
    public UserPreferenceValueDTO() {
        // end <init>
    }

    /**
     * @param defaultFlag the defaultFlag to set
     */
    public void setDefaultFlag(Boolean defaultFlag) {
        this.defaultFlag = defaultFlag.booleanValue();
        // end setDefaultFlag
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("userPrefId=");
        sb.append(this.userPrefId);
        sb.append(", upValue=");
        sb.append(this.upValue);
        sb.append(", defaultFlag=");
        sb.append(this.defaultFlag);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the userPrefId
     */
    public long getUserPrefId() {
        return this.userPrefId;
        // end getUserPrefId
    }

    /**
     * @param userPrefId the userPrefId to set
     */
    public void setUserPrefId(long userPrefId) {
        this.userPrefId = userPrefId;
        // end setUserPrefId
    }

    /**
     * @return the upValue
     */
    public String getUpValue() {
        return this.upValue;
        // end getUpValue
    }

    /**
     * @param upValue the upValue to set
     */
    public void setUpValue(String upValue) {
        this.upValue = upValue;
        // end setUpValue
    }

    /**
     * @return the defaultFlag
     */
    public boolean isDefaultFlag() {
        return this.defaultFlag;
        // end isDefaultFlag
    }

    /**
     * @param defaultFlag the defaultFlag to set
     */
    public void setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
        // end setDefaultFlag
    }

}
