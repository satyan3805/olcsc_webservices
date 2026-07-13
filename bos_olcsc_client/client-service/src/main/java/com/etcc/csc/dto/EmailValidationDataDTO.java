package com.etcc.csc.dto;

/**
 * Based on original class EmailValidationDataDTO from OLCSCService.
 * @author Milosh Boroyevich
 */
public class EmailValidationDataDTO extends BaseDTO {
	private static final long serialVersionUID = -4225603562095907863L;
	
    /* Validation Status values */
//    private static final int VDST_INVALID_ID = 0;
//    private static final int VDST_VALIDATION_REQUIRED = 1;
//    private static final int VDST_VALIDATION_DONE = 2;
//    private static final int VDST_OBSOLETE = 3;

	private Integer validationStatus;
    private long accountId;
    private String emailAddress;

    public void setValidationStatus(int validationStatus) {
        this.validationStatus = Integer.valueOf(validationStatus);
    }

    public int getValidationStatus() {
        return validationStatus.intValue();
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    
    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("validationStatus=");
        sb.append(validationStatus);
        sb.append(", accountId=");
        sb.append(accountId);
        sb.append(",emailAddress=");
        sb.append(emailAddress);
        sb.append("]");
        return sb.toString();
    }
}
