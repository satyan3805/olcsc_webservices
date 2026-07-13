/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

public class BillingInfoResultDTO extends BaseDTO {
    
    /**
     * Unique ID for Serialization with Version number.
     */
    //Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
    private static final long serialVersionUID = 1441877817618101349L;
    
    private double totalAmount;
    private double tagAmount;
    private double depositAmount;
    private long accontBillingMethodId;
    // new fields added
    private double rebillAmount;
    private double lowBalanceAmount;
    
    public long getAccontBillingMethodId() {
		return accontBillingMethodId;
	}
	public void setAccontBillingMethodId(long accontBillingMethodId) {
		this.accontBillingMethodId = accontBillingMethodId;
	}
	/**
     * @return the totalAmount
     */
    public double getTotalAmount() {
        return this.totalAmount;
        //end getTotalAmount
    }
    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        //end setTotalAmount
    }
    /**
     * @return the tagAmount
     */
    public double getTagAmount() {
        return this.tagAmount;
        //end getTagAmount
    }
    /**
     * @param tagAmount the tagAmount to set
     */
    public void setTagAmount(double tagAmount) {
        this.tagAmount = tagAmount;
        //end setTagAmount
    }
    /**
     * @return the depositAmount
     */
    public double getDepositAmount() {
        return this.depositAmount;
        //end getDepositAmount
    }
    /**
     * @param depositAmount the depositAmount to set
     */
    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
        //end setDepositAmount
    }
	public double getRebillAmount() {
		return rebillAmount;
	}
	public void setRebillAmount(double rebillAmount) {
		this.rebillAmount = rebillAmount;
	}
	public double getLowBalanceAmount() {
		return lowBalanceAmount;
	}
	public void setLowBalanceAmount(double lowBalanceAmount) {
		this.lowBalanceAmount = lowBalanceAmount;
	}

}
