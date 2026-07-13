/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dto.bean;

import java.io.Serializable;

import java.math.BigDecimal;

/**
 * Copied from OLCSCService AccountSummaryDetailBean, then was regenerated.
 * @author Stephen Davidson
 *
 */
public class AccountSummaryDetailBean implements Serializable{
    /**
     * Unique Id for serialization with version.
     */
    // Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = -1157431002016567054L;
    private String amtType;
    private BigDecimal amt;
    
    /**
     * Default Constructor.
     */
    public AccountSummaryDetailBean() {
        //end <init>
    }

    /**
     * Constructor.
     * @param amtType
     * @param amt
     */
    public AccountSummaryDetailBean(String amtType, BigDecimal amt) {
        super();
        this.amtType = amtType;
        this.amt = amt;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountSummaryDetailBean [amt=" + this.amt + ", amtType=" + this.amtType + "]";
    }

    /**
     * @return the amtType
     */
    public String getAmtType() {
        return this.amtType;
        //end getAmtType
    }

    /**
     * @param amtType the amtType to set
     */
    public void setAmtType(String amtType) {
        this.amtType = amtType;
        //end setAmtType
    }

    /**
     * @return the amt
     */
    public BigDecimal getAmt() {
        return this.amt;
        //end getAmt
    }

    /**
     * @param amt the amt to set
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
        //end setAmt
    }
    
}
