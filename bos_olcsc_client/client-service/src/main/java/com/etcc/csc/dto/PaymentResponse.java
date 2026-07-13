/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentResponse implements Serializable {
    /**
     * Unique ID for serialization with version.
     */
    //Do NOT regenerate for external clients such as Idea.
	private static final long serialVersionUID = -3136720548799458809L;
	
	private String postingStatus;
    private BigDecimal paymentId;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("postingStatus=").append(this.postingStatus);
        sb.append(", paymentId=").append(this.paymentId);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the postingStatus
     */
    public String getPostingStatus() {
        return this.postingStatus;
        //end getPostingStatus
    }

    /**
     * @param postingStatus the postingStatus to set
     */
    public void setPostingStatus(String postingStatus) {
        this.postingStatus = postingStatus;
        //end setPostingStatus
    }

    /**
     * @return the paymentId
     */
    public BigDecimal getPaymentId() {
        return this.paymentId;
        //end getPaymentId
    }

    /**
     * @param paymentId the paymentId to set
     */
    public void setPaymentId(BigDecimal paymentId) {
        this.paymentId = paymentId;
        //end setPaymentId
    }
}
