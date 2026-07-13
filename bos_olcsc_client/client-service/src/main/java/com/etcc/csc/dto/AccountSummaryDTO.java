/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.etcc.csc.dto.bean.AccountAlertDetailBean;
import com.etcc.csc.dto.bean.AccountSummaryDetailBean;


/**
 * Account Summary Data Transfer Object.  Based on the AccountSummaryDTO's from the original OLSCSC Services project.
 * @author Stephen Davidson
 */
public class AccountSummaryDTO extends BaseDTO {
    /**
     * Unique ID for Serialization.
     */
    //Do not regenerate for external clients such as Idea.
    private static final long serialVersionUID = -5060610653692153198L;
    protected BigDecimal daily;
    protected String billingAcctNumber;
    protected BigDecimal monthly;
    protected AccountSummaryDetailBean[] acctSummary;
    protected AccountAlertDetailBean[] acctAlert;
    protected Date lastLoginDate;
    protected Date rebillDate;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("daily="); sb.append(this.daily);
        sb.append(", billingAcctNumber="); sb.append(this.billingAcctNumber);
        sb.append(", monthly="); sb.append(this.monthly);
        sb.append(", acctSummary="); sb.append(Arrays.toString(this.acctSummary));
        sb.append(", acctAlert="); sb.append(Arrays.toString(this.acctAlert));
        sb.append(", lastLoginDate="); sb.append(this.lastLoginDate);
        sb.append(", rebillDate="); sb.append(this.rebillDate);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the daily
     */
    public BigDecimal getDaily() {
        return this.daily;
        //end getDaily
    }

    /**
     * @param daily the daily to set
     */
    public void setDaily(BigDecimal daily) {
        this.daily = daily;
        //end setDaily
    }

    /**
     * @return the billingAcctNumber
     */
    public String getBillingAcctNumber() {
        return this.billingAcctNumber;
        //end getBillingAcctNumber
    }

    /**
     * @param billingAcctNumber the billingAcctNumber to set
     */
    public void setBillingAcctNumber(String billingAcctNumber) {
        this.billingAcctNumber = billingAcctNumber;
        //end setBillingAcctNumber
    }

    /**
     * @return the monthly
     */
    public BigDecimal getMonthly() {
        return this.monthly;
        //end getMonthly
    }

    /**
     * @param monthly the monthly to set
     */
    public void setMonthly(BigDecimal monthly) {
        this.monthly = monthly;
        //end setMonthly
    }

    /**
     * @return the acctSummary
     */
    public AccountSummaryDetailBean[] getAcctSummary() {
        return this.acctSummary;
        //end getAcctSummary
    }

    /**
     * @param acctSummary the acctSummary to set
     */
    public void setAcctSummary(AccountSummaryDetailBean[] acctSummary) {
        this.acctSummary = acctSummary;
        //end setAcctSummary
    }

    /**
     * @return the acctAlert
     */
    public AccountAlertDetailBean[] getAcctAlert() {
        return this.acctAlert;
        //end getAcctAlert
    }

    /**
     * @param acctAlert the acctAlert to set
     */
    public void setAcctAlert(AccountAlertDetailBean[] acctAlert) {
        this.acctAlert = acctAlert;
        //end setAcctAlert
    }

    /**
     * @return the lastLoginDate
     */
    public Date getLastLoginDate() {
        return this.lastLoginDate;
        //end getLastLoginDate
    }

    /**
     * @param lastLoginDate the lastLoginDate to set
     */
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        //end setLastLoginDate
    }

    /**
     * @return the rebillDate
     */
    public Date getRebillDate() {
        return this.rebillDate;
        //end getRebillDate
    }

    /**
     * @param rebillDate the rebillDate to set
     */
    public void setRebillDate(Date rebillDate) {
        this.rebillDate = rebillDate;
        //end setRebillDate
    }
    
}
