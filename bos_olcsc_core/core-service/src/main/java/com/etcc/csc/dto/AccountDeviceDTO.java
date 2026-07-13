package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;


/**
 * Represents an Account Device.
 */
public class AccountDeviceDTO extends BaseDTO {
    private long acctDeviceId;
    private String deviceValue;
    private boolean defaultFlag;
    
    public AccountDeviceDTO() {
    }

    public void setAcctDeviceId(long acctDeviceId) {
        this.acctDeviceId = acctDeviceId;
    }

    public long getAcctDeviceId() {
        return acctDeviceId;
    }

    public void setDeviceValue(String deviceValue) {
        this.deviceValue = deviceValue;
    }

    public String getDeviceValue() {
        return deviceValue;
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
        sb.append("acctDeviceId=");
        sb.append(acctDeviceId);
        sb.append(", deviceValue=");
        sb.append(deviceValue);
        sb.append(", defaultFlag=");
        sb.append(defaultFlag);
        sb.append("]");
        return sb.toString();
    }
}
