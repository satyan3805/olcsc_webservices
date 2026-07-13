package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;


/**
 * Represents an Account Device.
 */
public class UserPreferenceValueDTO extends BaseDTO {
    private long userPrefId;
    private String upValue;
    private String displayUpValue;
    private boolean defaultFlag;

    public UserPreferenceValueDTO() {
    }

    public void setUserPrefId(long userPrefId) {
        this.userPrefId = userPrefId;
    }

    public long getUserPrefId() {
        return userPrefId;
    }

    public void setUpValue(String upValue) {
        this.upValue = upValue;
    }

    public String getUpValue() {
        return upValue;
    }

    public void setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public boolean isDefaultFlag() {
        return defaultFlag;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("userPrefId=");
        sb.append(userPrefId);
        sb.append(", upValue=");
        sb.append(upValue);
        sb.append(", defaultFlag=");
        sb.append(defaultFlag);
        sb.append("]");
        return sb.toString();
    }

    public void setDisplayUpValue(String displayUpValue) {
        this.displayUpValue = displayUpValue;
    }

    public String getDisplayUpValue() {
        return displayUpValue;
    }
}
