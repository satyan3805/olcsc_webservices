/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;

/**
 * Copied from OLCSCService com.etcc.csc.tolltag.dto.PersonalInfoResponse
 */
public class PersonalInfoResponse implements Serializable {
    
    /**
     * Unique ID for serialization with version.
     */
    //Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = -2912281565914970118L;
    private long transactionId;
    private boolean duplicateDriverLicense;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("transactionId="); sb.append(this.transactionId);
        sb.append(", duplicateDriverLicense="); sb.append(this.duplicateDriverLicense);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the transactionId
     */
    public long getTransactionId() {
        return this.transactionId;
        //end getTransactionId
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
        //end setTransactionId
    }

    /**
     * @return the duplicateDriverLicense
     */
    public boolean isDuplicateDriverLicense() {
        return this.duplicateDriverLicense;
        //end isDuplicateDriverLicense
    }

    /**
     * @param duplicateDriverLicense the duplicateDriverLicense to set
     */
    public void setDuplicateDriverLicense(boolean duplicateDriverLicense) {
        this.duplicateDriverLicense = duplicateDriverLicense;
        //end setDuplicateDriverLicense
    }
}
