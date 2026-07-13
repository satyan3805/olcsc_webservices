/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;



/**
 * Data Transfer Object for if a Summary is available.
 */
public class SummaryAvailableDTO extends BaseDTO {
    
    /**
     * Unique ID for Serialization
     */
    //Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = -6633776579564661969L;
    
    protected boolean summaryAvail;
    
    /**
     * Constructor.
     */
    public SummaryAvailableDTO() {
        //end <init>
    }
    
    /**
     * Is a summary available?
     * @return is a summary available?
     */
    public boolean isSummaryAvail() {
        return this.summaryAvail;
    }
    
    /**
     * Sets if a summary is available.
     * @param summaryAvail Sets if a sumamry is available.
     */
    public void setSummaryAvail(boolean summaryAvail) {
        this.summaryAvail = summaryAvail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("summaryAvail="); sb.append(this.summaryAvail);
        sb.append("]");
        return sb.toString();
    }
}
