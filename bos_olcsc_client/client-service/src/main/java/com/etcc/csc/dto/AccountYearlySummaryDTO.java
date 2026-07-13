/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;

import com.etcc.csc.dto.bean.OlcAcctSummaryRecBean;
import com.etcc.csc.dto.bean.OlcTagSummaryRecBean;

/**
 * Account Yearly Summary Data transfer Object.
 *
 */
public class AccountYearlySummaryDTO extends BaseDTO {
    /**
     * Unique ID for serialization with version.
     */
    //Do NOT change for external clients such as Idea.
    private static final long serialVersionUID = 2263560402778079346L;
    protected String lastDayOfYear;
    protected String endingBalance;
    
    protected OlcAcctSummaryRecBean[] acctSummary;
    protected OlcTagSummaryRecBean[] tagSummary;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("acctSummary="); sb.append(Arrays.toString(this.acctSummary));
        sb.append(", tagSummary="); sb.append(Arrays.toString(this.tagSummary));
        sb.append(", lastDayOfYear="); sb.append(this.lastDayOfYear);
        sb.append(", endingBalance="); sb.append(this.endingBalance);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the lastDayOfYear
     */
    public String getLastDayOfYear() {
        return this.lastDayOfYear;
        //end getLastDayOfYear
    }

    /**
     * @param lastDayOfYear the lastDayOfYear to set
     */
    public void setLastDayOfYear(String lastDayOfYear) {
        this.lastDayOfYear = lastDayOfYear;
        //end setLastDayOfYear
    }

    /**
     * @return the endingBalance
     */
    public String getEndingBalance() {
        return this.endingBalance;
        //end getEndingBalance
    }

    /**
     * @param endingBalance the endingBalance to set
     */
    public void setEndingBalance(String endingBalance) {
        this.endingBalance = endingBalance;
        //end setEndingBalance
    }

    /**
     * @return the tagSummary
     */
    public OlcTagSummaryRecBean[] getTagSummary() {
        return this.tagSummary;
        //end getTagSummary
    }

    /**
     * @param tagSummary the tagSummary to set
     */
    public void setTagSummary(OlcTagSummaryRecBean[] tagSummary) {
        this.tagSummary = tagSummary;
        //end setTagSummary
    }

    /**
     * Gets the list of Summaries.
     * @return The summaries for this account/year.
     */
    public OlcAcctSummaryRecBean[] getAcctSummary() {
        return this.acctSummary;
    }
    
    /**
     * @param acctSummary the acctSummary to set
     */
    public void setAcctSummary(OlcAcctSummaryRecBean[] acctSummary) {
        this.acctSummary = acctSummary;
        //end setAcctSummary
    }
}
