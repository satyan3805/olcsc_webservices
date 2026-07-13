package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

/**
 * Contains the result of various payment processing.
 */
public class PaymentResultDTO extends BaseDTO{
    private long retailTransId;
    private double amountDue;
    private boolean successful;
    
    public PaymentResultDTO() {
    }


    public void setRetailTransId(long retailTransId) {
        this.retailTransId = retailTransId;
    }

    public long getRetailTransId() {
        return retailTransId;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("retailTransId=");
        sb.append(retailTransId);
        sb.append(", amountDue=");
        sb.append(amountDue);
        sb.append(", successful=");
        sb.append(successful);
        sb.append("]");
        return sb.toString();
    }
}
