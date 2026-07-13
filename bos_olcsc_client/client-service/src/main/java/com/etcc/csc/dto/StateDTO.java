/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Data Transfer Object representing a state.
 *
 */
public class StateDTO extends BaseDTO {
    /**
     * Unique Id for serialization.
     */
    //Do NOT modify for external clients such as HCTRA & Idea.
    private static final long serialVersionUID = -2732780901735178461L;
    private String stateCode;  // PK in DB
    private String stateName;
    private String countryCode;  // unique in DB
    private short stateOrder;
    private boolean defaultValueFlag;
    private String driverLicensePattern;

    /**
     * Constructor.
     */
    public StateDTO() {
        // end <init>
    }

    /**
     * Constructor.
     * @param stateCode The code for this state.
     * @param stateName the name for this state.
     */
    public StateDTO(String stateCode, String stateName) {
        super();
        this.stateCode = stateCode;
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("stateCode=");
        sb.append(this.stateCode);
        sb.append(", stateName=");
        sb.append(this.stateName);
        sb.append(", countryCode=");
        sb.append(this.countryCode);
        sb.append(", stateOrder=");
        sb.append(this.stateOrder);
        sb.append(", defaultValueFlag=");
        sb.append(this.defaultValueFlag);
        sb.append(", driverLicensePattern=");
        sb.append(this.driverLicensePattern);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the stateCode
     */
    public String getStateCode() {
        return this.stateCode;
        // end getStateCode
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
        // end setStateCode
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return this.stateName;
        // end getStateName
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
        // end setStateName
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return this.countryCode;
        // end getCountryCode
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        // end setCountryCode
    }

    /**
     * @return the stateOrder
     */
    public short getStateOrder() {
        return this.stateOrder;
        // end getStateOrder
    }

    /**
     * @param stateOrder the stateOrder to set
     */
    public void setStateOrder(short stateOrder) {
        this.stateOrder = stateOrder;
        // end setStateOrder
    }

    /**
     * @return the defaultValueFlag
     */
    public boolean isDefaultValueFlag() {
        return this.defaultValueFlag;
        // end isDefaultValueFlag
    }

    /**
     * @param defaultValueFlag the defaultValueFlag to set
     */
    public void setDefaultValueFlag(boolean defaultValueFlag) {
        this.defaultValueFlag = defaultValueFlag;
        // end setDefaultValueFlag
    }

	public String getDriverLicensePattern() {
		return driverLicensePattern;
	}

	public void setDriverLicensePattern(String driverLicensePattern) {
		this.driverLicensePattern = driverLicensePattern;
	}

}
