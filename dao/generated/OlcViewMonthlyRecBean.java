package com.etcc.csc.dao.generated;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.etcc.csc.plsql.OlcViewMonthlyRec;

public class OlcViewMonthlyRecBean {
    private java.util.Calendar transactionDate;
    private java.util.Calendar postedDate;
    private String tagId;
    private String unitId;
    private String licensePlate;
    private String lane;
    private String direction;
    private String location;
    private String transTypeDescr;
    private java.math.BigDecimal amount;
    private java.math.BigDecimal serialNumber;
    private String laneFullName;

    public OlcViewMonthlyRecBean() {
    }
    
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
    }


    public void setTransactionDate(Calendar transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Calendar getTransactionDate() {
        return transactionDate;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getLane() {
        return lane;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setTransTypeDescr(String transTypeDescr) {
        this.transTypeDescr = transTypeDescr;
    }

    public String getTransTypeDescr() {
        return transTypeDescr;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }


    public void setSerialNumber(BigDecimal serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getSerialNumber() {
        return serialNumber;
    }

    public void setLaneFullName(String laneFullName) {
        this.laneFullName = laneFullName;
    }

    public String getLaneFullName() {
        return laneFullName;
    }

    public void setPostedDate(Calendar postedDate) {
        this.postedDate = postedDate;
    }

    public Calendar getPostedDate() {
        return postedDate;
    }
}
