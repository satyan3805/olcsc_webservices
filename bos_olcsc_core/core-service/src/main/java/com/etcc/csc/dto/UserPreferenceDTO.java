package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Collection;

/**
 * Contains information about the user's environment.
 */
public class UserPreferenceDTO extends BaseDTO {
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
    public UserPreferenceDTO() {
    }



    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("]");
        return sb.toString();
    }

    public void setPrefId(long prefId) {
        this.prefId = prefId;
    }

    public long getPrefId() {
        return prefId;
    }

    public void setDisplayDesc(String displayDesc) {
        this.displayDesc = displayDesc;
    }

    public String getDisplayDesc() {
        return displayDesc;
    }

    public void setPrefValue(String prefValue) {
        this.prefValue = prefValue;
    }

    public String getPrefValue() {
        return prefValue;
    }

    public void setPrefType(String prefType) {
        this.prefType = prefType;
    }

    public String getPrefType() {
        return prefType;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDeviceValues(AccountDeviceDTO[] deviceValues) {
        this.deviceValues = deviceValues;
    }

    public AccountDeviceDTO[] getDeviceValues() {
        return deviceValues;
    }

    public void setUserPrefValues(UserPreferenceValueDTO[] userPrefValues) {
        this.userPrefValues = userPrefValues;
    }

    public UserPreferenceValueDTO[] getUserPrefValues() {
        return userPrefValues;
    }

    public void setSelectedDeviceId(long selectedDeviceId) {
        this.selectedDeviceId = selectedDeviceId;
    }

    public long getSelectedDeviceId() {
        return selectedDeviceId;
    }

    public void setSelectedDeviceValue(String selectedDeviceValue) {
        this.selectedDeviceValue = selectedDeviceValue;
    }

    public String getSelectedDeviceValue() {
        return selectedDeviceValue;
    }

    public void setSelecteduserPrefValue(String selecteduserPrefValue) {
        this.selecteduserPrefValue = selecteduserPrefValue;
    }

    public String getSelecteduserPrefValue() {
        return selecteduserPrefValue;
    }

    public void setSteId(long steId) {
        this.steId = steId;
    }

    public long getSteId() {
        return steId;
    }
}
