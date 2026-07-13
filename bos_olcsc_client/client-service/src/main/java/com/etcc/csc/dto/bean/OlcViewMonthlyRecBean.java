package com.etcc.csc.dto.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;

public class OlcViewMonthlyRecBean implements Serializable {
    private static final long serialVersionUID = -8143711800407917036L;

    private Calendar transactionDate;
    private Calendar postedDate;
    private String tagId;
    private String unitId;
    private String licensePlate;
    private String lane;
    private String direction;
    private String location;
    private BigDecimal amount;
    private BigDecimal serialNumber;
    private String laneFullName;
    private String vehicleClassCode;
    private String description;

    
    /*
    public OlcViewMonthlyRecBean(OlcViewMonthlyRec record) throws SQLException{
        
        
        if (record.getTransactionDate()!=null)
        {
            Calendar transactionDate = Calendar.getInstance( );
            transactionDate.setTime( new Date(record.getTransactionDate().getTime()) );
            setTransactionDate(transactionDate);
        }
        
        if (record.getPostedDate()!=null) {
            Calendar postedDate = Calendar.getInstance();
            postedDate.setTime(new Date(record.getPostedDate().getTime()));
            setPostedDate(postedDate);
        }
        
        setTagId(record.getTagId());
        setUnitId(record.getUnitId());
        setLicensePlate(record.getLicensePlate());
        setLane(record.getLane());
        setDirection(record.getDirection());
        setLocation(record.getLocation());
        setTransTypeDescr(record.getDescription());
        setAmount(record.getAmount());
        setSerialNumber(record.getSerialNumber());
        setLaneFullName(record.getLaneFullName());
        setVehicleClassCode(record.getVehicleClassCode());
    }
    */

    /**
     * Sets the date of the Transaction.
     * @param transactionDate The Transaction date to set.
     */
    public void setTransactionDate(Calendar transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets the Transaction Date.
     * @return the current transaction date.
     */
    public Calendar getTransactionDate() {
        return this.transactionDate;
    }

    /**
     * Sets the date the transaction was posted.
     * @param postedDate the date the transaction was posted.
     */
    public void setPostedDate(Calendar postedDate) {
        this.postedDate = postedDate;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("laneFullName="); sb.append(this.laneFullName);
        sb.append(", amount="); sb.append(this.amount);
        final DateFormat formatter = DateFormat.getDateTimeInstance();
        sb.append(", transactionDate=");
        sb.append( this.transactionDate == null ? "null" : formatter.format(this.transactionDate.getTime()));
        sb.append(", postedDate=");
        sb.append(this.postedDate == null ? "null" : formatter.format(this.postedDate.getTime()));
        sb.append(", tagId="); sb.append(this.tagId);
        sb.append(", unitId="); sb.append(this.unitId);
        sb.append(", licensePlate="); sb.append(this.licensePlate);
        sb.append(", lane="); sb.append(this.lane);
        sb.append(", direction="); sb.append(this.direction);
        sb.append(", vehicleClassCode="); sb.append(this.vehicleClassCode);
        sb.append(", location="); sb.append(this.location);
        sb.append(", serialNumber="); sb.append(this.serialNumber);
        sb.append(", description="); sb.append(this.description);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the tagId
     */
    public String getTagId() {
        return this.tagId;
        //end getTagId
    }

    /**
     * @param tagId the tagId to set
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
        //end setTagId
    }

    /**
     * @return the unitId
     */
    public String getUnitId() {
        return this.unitId;
        //end getUnitId
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId;
        //end setUnitId
    }

    /**
     * @return the licensePlate
     */
    public String getLicensePlate() {
        return this.licensePlate;
        //end getLicensePlate
    }

    /**
     * @param licensePlate the licensePlate to set
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        //end setLicensePlate
    }

    /**
     * @return the lane
     */
    public String getLane() {
        return this.lane;
        //end getLane
    }

    /**
     * @param lane the lane to set
     */
    public void setLane(String lane) {
        this.lane = lane;
        //end setLane
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return this.direction;
        //end getDirection
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
        //end setDirection
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return this.location;
        //end getLocation
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
        //end setLocation
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return this.amount;
        //end getAmount
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        //end setAmount
    }

    /**
     * @return the serialNumber
     */
    public BigDecimal getSerialNumber() {
        return this.serialNumber;
        //end getSerialNumber
    }

    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(BigDecimal serialNumber) {
        this.serialNumber = serialNumber;
        //end setSerialNumber
    }

    /**
     * @return the laneFullName
     */
    public String getLaneFullName() {
        return this.laneFullName;
        //end getLaneFullName
    }

    /**
     * @param laneFullName the laneFullName to set
     */
    public void setLaneFullName(String laneFullName) {
        this.laneFullName = laneFullName;
        //end setLaneFullName
    }

    /**
     * @return the vehicleClassCode
     */
    public String getVehicleClassCode() {
        return this.vehicleClassCode;
        //end getVehicleClassCode
    }

    /**
     * @param vehicleClassCode the vehicleClassCode to set
     */
    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
        //end setVehicleClassCode
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
        //end getDescription
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
        //end setDescription
    }

    /**
     * @return the postedDate
     */
    public Calendar getPostedDate() {
        return this.postedDate;
        //end getPostedDate
    }
}
