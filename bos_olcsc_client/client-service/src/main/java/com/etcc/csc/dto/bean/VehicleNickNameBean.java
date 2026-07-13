package com.etcc.csc.dto.bean;

import java.io.Serializable;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

/**
 * Bean representing a vehicle's nick name.
 */
public class VehicleNickNameBean implements Serializable {
    /**
     * Unique ID for serialization with version.
     */
    private static final long serialVersionUID = 8625962137812349423L;
    private String vehicleNickName;
    private String vehicleNickNameType;
    

    /**
     * Gets a combination of Vehicle Nickname & NickName type.
     * @return <code>vehicleNickName + ":::" + vehicleNickNameType</code>
     */
    @IgnoreProperty
    public String getVehicleNickNameValue(){
        return this.vehicleNickName + ":::" + this.vehicleNickNameType;
    }

    /**
     * @return the vehicleNickName
     */
    public String getVehicleNickName() {
        return this.vehicleNickName;
    }


    /**
     * @param vehicleNickName the vehicleNickName to set
     */
    public void setVehicleNickName(String vehicleNickName) {
        this.vehicleNickName = vehicleNickName;
    }


    /**
     * @return the vehicleNickNameType
     */
    public String getVehicleNickNameType() {
        return this.vehicleNickNameType;
    }


    /**
     * @param vehicleNickNameType the vehicleNickNameType to set
     */
    public void setVehicleNickNameType(String vehicleNickNameType) {
        this.vehicleNickNameType = vehicleNickNameType;
    }
}
