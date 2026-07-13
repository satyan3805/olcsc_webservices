package com.etcc.csc.dto;

/**
 * Represents an Account Device.
 */
public class AccountDeviceDTO extends BaseDTO {
    /**
     * Unique ID for serialization with version.
     */
    private static final long serialVersionUID = 1366691102202504632L;
    private long acctDeviceId;
    private String deviceValue;
    private boolean defaultFlag;

    /**
     * Constructor.
     */
    public AccountDeviceDTO() {
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
        sb.append("acctDeviceId=");
        sb.append(this.acctDeviceId);
        sb.append(", deviceValue=");
        sb.append(this.deviceValue);
        sb.append(", defaultFlag=");
        sb.append(this.defaultFlag);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the acctDeviceId
     */
    public long getAcctDeviceId() {
        return this.acctDeviceId;
        // end getAcctDeviceId
    }

    /**
     * @param acctDeviceId the acctDeviceId to set
     */
    public void setAcctDeviceId(long acctDeviceId) {
        this.acctDeviceId = acctDeviceId;
        // end setAcctDeviceId
    }

    /**
     * @return the deviceValue
     */
    public String getDeviceValue() {
        return this.deviceValue;
        // end getDeviceValue
    }

    /**
     * @param deviceValue the deviceValue to set
     */
    public void setDeviceValue(String deviceValue) {
        this.deviceValue = deviceValue;
        // end setDeviceValue
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
