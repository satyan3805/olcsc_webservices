package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

public class VehicleClassDTO extends BaseDTO {
    private String vehicleClassCode;
    private String vehicleClassDescr;
    private String vehicleClassLongDescr;
    private short vehicleClassOrder;
    private boolean defaultValueFlag;
    private boolean activeFlag;
    
    public VehicleClassDTO() {
    }


    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
    }

    public String getVehicleClassCode() {
        return vehicleClassCode;
    }

    public void setVehicleClassDescr(String vehicleClassDescr) {
        this.vehicleClassDescr = vehicleClassDescr;
    }

    public String getVehicleClassDescr() {
        return vehicleClassDescr;
    }

    public void setVehicleClassLongDescr(String vehicleClassLongDescr) {
        this.vehicleClassLongDescr = vehicleClassLongDescr;
    }

    public String getVehicleClassLongDescr() {
        return vehicleClassLongDescr;
    }

    public void setVehicleClassOrder(short vehicleClassOrder) {
        this.vehicleClassOrder = vehicleClassOrder;
    }

    public short getVehicleClassOrder() {
        return vehicleClassOrder;
    }

    public void setDefaultValueFlag(boolean defaultValueFlag) {
        this.defaultValueFlag = defaultValueFlag;
    }

    public boolean isDefaultValueFlag() {
        return defaultValueFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("vehicleClassCode=");
        sb.append(vehicleClassCode);
        sb.append("vehicleClassDescr=");
        sb.append(vehicleClassDescr);
        sb.append("vehicleClassLongDescr=");
        sb.append(vehicleClassLongDescr);
        sb.append("vehicleClassOrder=");
        sb.append(vehicleClassOrder);
        sb.append("defaultValueFlag=");
        sb.append(defaultValueFlag);
        sb.append("activeFlag=");
        sb.append(activeFlag);
        sb.append("]");
        return sb.toString();
    }
}
