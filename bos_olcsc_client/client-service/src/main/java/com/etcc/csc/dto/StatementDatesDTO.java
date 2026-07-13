/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;

/**
 * Data Transfer object for Statement Dates.
 * 
 * @todo TODO: Modify to use java.util.Date??? instead of String
 */
public class StatementDatesDTO extends BaseDTO {
    /**
     * Unique Id for serialization.
     */
    //Do NOT modify for external clients such as HCTRA & Idea.
    private static final long serialVersionUID = -5559529796563927434L;

    /** Collection of statement dates. */
    protected String[] dates;

    /**
     * Gets the dates for the statement.
     * @return The dates for the statement
     */
    public String[] getDates() {
        return this.dates;
    }

    /**
     * Sets the collection of dates for the statement.
     * @param dates the dates for the statement.
     */
    public void setDates(String[] dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("dates="); sb.append(Arrays.toString(this.dates));
        sb.append("]");
        return sb.toString();
    }
}
