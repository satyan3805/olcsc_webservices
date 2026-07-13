/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Contains the result of various payment processing.
 */
public class PaymentResultDTO extends BaseDTO {
    
    /**
     * Unique ID for serialization with version.
     */
    // Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = -1199471146024858223L;
    
    private long retailTransId;
    private double amountDue;
    private boolean successful;
    
    /**
     * Constructor.
     */
    public PaymentResultDTO() {
        //end <init>
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("retailTransId=");
        sb.append(this.retailTransId);
        sb.append(", amountDue=");
        sb.append(this.amountDue);
        sb.append(", successful=");
        sb.append(this.successful);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the retailTransId
     */
    public long getRetailTransId() {
        return this.retailTransId;
        //end getRetailTransId
    }

    /**
     * @param retailTransId the retailTransId to set
     */
    public void setRetailTransId(long retailTransId) {
        this.retailTransId = retailTransId;
        //end setRetailTransId
    }

    /**
     * @return the amountDue
     */
    public double getAmountDue() {
        return this.amountDue;
        //end getAmountDue
    }

    /**
     * @param amountDue the amountDue to set
     */
    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
        //end setAmountDue
    }

    /**
     * @return the successful
     */
    public boolean isSuccessful() {
        return this.successful;
        //end isSuccessful
    }

    /**
     * @param successful the successful to set
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
        //end setSuccessful
    }
}
