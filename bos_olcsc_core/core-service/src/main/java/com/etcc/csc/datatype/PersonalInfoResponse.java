package com.etcc.csc.datatype;

public class PersonalInfoResponse {
    public PersonalInfoResponse() {
    }
    
    private long transactionId;
    private boolean duplicateDriverLicense;

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setDuplicateDriverLicense(boolean duplicateDriverLicense) {
        this.duplicateDriverLicense = duplicateDriverLicense;
    }

    public boolean isDuplicateDriverLicense() {
        return duplicateDriverLicense;
    }
}
