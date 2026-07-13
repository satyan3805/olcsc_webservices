package com.etcc.csc.dto;

import java.io.Serializable;

public class StatementRecord implements Serializable{
    
    private String dateTime;
    private String tagId;
    private String lane;
    private String dir;
    private String location;
    private String description;
    double amount;
   
    public StatementRecord() {
    }


    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
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

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return dir;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setDescription(String description) {
        this.description = convertEmptyString(description);
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
    
    private String convertEmptyString(String str) {
        return (str==null || str.length()==0)?"&nbsp;":str;
    }
    
}
