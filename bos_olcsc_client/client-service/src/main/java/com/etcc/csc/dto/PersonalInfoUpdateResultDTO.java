/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Data Transfer Object for Personal Information updates.
 * 
 * @author Stephen Davidson
 * 
 */
public class PersonalInfoUpdateResultDTO extends BaseDTO {

    /**
     * Unique Id for serialization.
     */
    //Do NOT modify for external clients such as HCTRA & Idea.
    private static final long serialVersionUID = 1097626960968607852L;
    
    private long retailTransId;
    private boolean dupDriverLic;

    /**
     * Constructor.
     */
    public PersonalInfoUpdateResultDTO() {
        // end <init>
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("retailTransId=");
        sb.append(this.retailTransId);
        sb.append(", dupDriverLic=");
        sb.append(this.dupDriverLic);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the retailTransId
     */
    public long getRetailTransId() {
        return this.retailTransId;
        // end getRetailTransId
    }

    /**
     * @param retailTransId the retailTransId to set
     */
    public void setRetailTransId(long retailTransId) {
        this.retailTransId = retailTransId;
        // end setRetailTransId
    }

    /**
     * @return the dupDriverLic
     */
    public boolean isDupDriverLic() {
        return this.dupDriverLic;
        // end isDupDriverLic
    }

    /**
     * @param dupDriverLic the dupDriverLic to set
     */
    public void setDupDriverLic(boolean dupDriverLic) {
        this.dupDriverLic = dupDriverLic;
        // end setDupDriverLic
    }

}
