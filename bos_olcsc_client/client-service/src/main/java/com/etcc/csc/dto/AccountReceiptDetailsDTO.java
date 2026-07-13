/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;
import java.util.Date;

/**
 * Data Transfer object for Receipt details.
 */
public class AccountReceiptDetailsDTO extends BaseDTO {
    /**
     * Unique ID for serialization
     */
    // Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = 505345637623907250L;
    protected long transactionID;
    protected String csrID;
    protected Date transactionDate;
    protected AccountTransaction[] accountTransactions;
    protected long acctId;
    protected ParkTransaction parkTransaction;
    protected AccountTransaction[] pmtDetails;
    protected AccountTransaction[] depositTransactions;
    protected String transactionType;

    /**
     * Constructor.
     */
    public AccountReceiptDetailsDTO() {
        // end <init>
    }

    /**
     * @return the transactionID
     */
    public long getTransactionID() {
        return this.transactionID;
        // end getTransactionID
    }

    /**
     * @param transactionID the transactionID to set
     */
    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
        // end setTransactionID
    }

    /**
     * @return the csrID
     */
    public String getCsrID() {
        return this.csrID;
        // end getCsrID
    }

    /**
     * @param csrID the csrID to set
     */
    public void setCsrID(String csrID) {
        this.csrID = csrID;
        // end setCsrID
    }

    /**
     * @return the transactionDate
     */
    public Date getTransactionDate() {
        return this.transactionDate;
        // end getTransactionDate
    }

    /**
     * @param transactionDate the transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
        // end setTransactionDate
    }

    /**
     * @return the accountTransactions
     */
    public AccountTransaction[] getAccountTransactions() {
        return this.accountTransactions;
        // end getAccountTransactions
    }

    /**
     * @param accountTransactions the accountTransactions to set
     */
    public void setAccountTransactions(AccountTransaction[] accountTransactions) {
        this.accountTransactions = accountTransactions;
        // end setAccountTransactions
    }

    /**
     * @return the acctId
     */
    public long getAcctId() {
        return this.acctId;
        // end getAcctId
    }

    /**
     * @param acctId the acctId to set
     */
    public void setAcctId(long acctId) {
        this.acctId = acctId;
        // end setAcctId
    }

    /**
     * @return the parkTransaction
     */
    public ParkTransaction getParkTransaction() {
        return this.parkTransaction;
        // end getParkTransaction
    }

    /**
     * @param parkTransaction the parkTransaction to set
     */
    public void setParkTransaction(ParkTransaction parkTransaction) {
        this.parkTransaction = parkTransaction;
        // end setParkTransaction
    }

    /**
     * @return the pmtDetails
     */
    public AccountTransaction[] getPmtDetails() {
        return this.pmtDetails;
        // end getPmtDetails
    }

    /**
     * @param pmtDetails the pmtDetails to set
     */
    public void setPmtDetails(AccountTransaction[] pmtDetails) {
        this.pmtDetails = pmtDetails;
        // end setPmtDetails
    }

    /**
     * @return the depositTransactions
     */
    public AccountTransaction[] getDepositTransactions() {
        return this.depositTransactions;
        // end getDepositTransactions
    }

    /**
     * @param depositTransactions the depositTransactions to set
     */
    public void setDepositTransactions(AccountTransaction[] depositTransactions) {
        this.depositTransactions = depositTransactions;
        // end setDepositTransactions
    }

    /**
     * @return the transactionType
     */
    public String getTransactionType() {
        return this.transactionType;
        // end getTransactionType
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
        // end setTransactionType
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("transactionID=");
        sb.append(this.transactionID);
        sb.append(", csrID=");
        sb.append(this.csrID);
        sb.append(", transactionDate=");
        sb.append(this.transactionDate);
        sb.append(", accountTransactions=");
        sb.append(Arrays.toString(this.accountTransactions));
        sb.append(", acctId=");
        sb.append(this.acctId);
        sb.append(", parkTransaction=");
        sb.append(this.parkTransaction);
        sb.append(", pmtDetails=");
        sb.append(Arrays.toString(this.pmtDetails));
        sb.append(", depositTransactions=");
        sb.append(Arrays.toString(this.depositTransactions));
        sb.append(", transactionType=");
        sb.append(this.transactionType);
        sb.append("]");
        return sb.toString();
    }
}
