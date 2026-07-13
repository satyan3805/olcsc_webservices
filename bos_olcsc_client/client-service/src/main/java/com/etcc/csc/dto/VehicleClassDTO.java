/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Data Transfer Object for Vehicle Classes.
 */
public class VehicleClassDTO extends BaseDTO {
    /**
     * Unique ID for serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = 7962545005047068927L;
    
    private String vehicleClassCode;
    private String vehicleClassDescr;
    private String vehicleClassLongDescr;
    private short vehicleClassOrder;
    private boolean defaultValueFlag;
    private boolean activeFlag;

    /**
     * Constructor.
     */
    public VehicleClassDTO() {
        // end <init>
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("vehicleClassCode=");
        sb.append(this.vehicleClassCode);
        sb.append("vehicleClassDescr=");
        sb.append(this.vehicleClassDescr);
        sb.append("vehicleClassLongDescr=");
        sb.append(this.vehicleClassLongDescr);
        sb.append("vehicleClassOrder=");
        sb.append(this.vehicleClassOrder);
        sb.append("defaultValueFlag=");
        sb.append(this.defaultValueFlag);
        sb.append("activeFlag=");
        sb.append(this.activeFlag);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the vehicleClassCode
     */
    public String getVehicleClassCode() {
        return this.vehicleClassCode;
        // end getVehicleClassCode
    }

    /**
     * @param vehicleClassCode the vehicleClassCode to set
     */
    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
        // end setVehicleClassCode
    }

    /**
     * @return the vehicleClassDescr
     */
    public String getVehicleClassDescr() {
        return this.vehicleClassDescr;
        // end getVehicleClassDescr
    }

    /**
     * @param vehicleClassDescr the vehicleClassDescr to set
     */
    public void setVehicleClassDescr(String vehicleClassDescr) {
        this.vehicleClassDescr = vehicleClassDescr;
        // end setVehicleClassDescr
    }

    /**
     * @return the vehicleClassLongDescr
     */
    public String getVehicleClassLongDescr() {
        return this.vehicleClassLongDescr;
        // end getVehicleClassLongDescr
    }

    /**
     * @param vehicleClassLongDescr the vehicleClassLongDescr to set
     */
    public void setVehicleClassLongDescr(String vehicleClassLongDescr) {
        this.vehicleClassLongDescr = vehicleClassLongDescr;
        // end setVehicleClassLongDescr
    }

    /**
     * @return the vehicleClassOrder
     */
    public short getVehicleClassOrder() {
        return this.vehicleClassOrder;
        // end getVehicleClassOrder
    }

    /**
     * @param vehicleClassOrder the vehicleClassOrder to set
     */
    public void setVehicleClassOrder(short vehicleClassOrder) {
        this.vehicleClassOrder = vehicleClassOrder;
        // end setVehicleClassOrder
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

    /**
     * @return the activeFlag
     */
    public boolean isActiveFlag() {
        return this.activeFlag;
        // end isActiveFlag
    }

    /**
     * @param activeFlag the activeFlag to set
     */
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
        // end setActiveFlag
    }
}
