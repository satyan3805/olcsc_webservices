package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

public class StateDTO extends BaseDTO{
    private String stateCode;
    private String stateName;
    private String countryCode;
    private short stateOrder;
    private boolean defaultValueFlag;
    
    public StateDTO() {
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setStateOrder(short stateOrder) {
        this.stateOrder = stateOrder;
    }

    public short getStateOrder() {
        return stateOrder;
    }

    public void setDefaultValueFlag(boolean defaultValueFlag) {
        this.defaultValueFlag = defaultValueFlag;
    }

    public boolean isDefaultValueFlag() {
        return defaultValueFlag;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("stateCode=");
        sb.append(stateCode);
        sb.append(", stateName=");
        sb.append(stateName);
        sb.append(", countryCode=");
        sb.append(countryCode);
        sb.append(", stateOrder=");
        sb.append(stateOrder);
        sb.append(", defaultValueFlag=");
        sb.append(defaultValueFlag);
        sb.append("]");
        return sb.toString();
    }
}
