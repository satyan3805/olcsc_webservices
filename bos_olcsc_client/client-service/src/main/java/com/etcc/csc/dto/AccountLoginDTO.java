/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Date;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

/**
 * Represents the Account Login.
 */
public class AccountLoginDTO extends BaseDTO {
    /**
     * Unique ID for Serialization with Version number.
     */
    //Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
    private static final long serialVersionUID = -3631550352704031203L;

    public enum LoginType {
    	/** Account. */
    	AC,
    	/** Invoice. */
    	IN,
    	/** Tolltag. */
    	TT,
    	/** Collection Agency. */
    	CA
    }

    public static final String GENERIC_USER = "OLCSC_ANONYMOUS";

    private long acctId;
    private String loginId;
    private String lastLoginIp;
    private String dbSessionId;
    private LoginType loginType;
    private String acctStatus;
    private String acctActivity;
    /**
     * When LoginType is <tt>LoginType.IN</tt>, will contain the 
     * invoice id. When login type is <tt>LoginType.CA</tt>, 
     * will contain the collection agency id.
     */
    private String invoiceId;
    private long violatorId;
    private String licPlate;
    private String licState;
    private boolean pwChangeRequired;
    private boolean locked;
    private Date lastLoginDate;
    private long validationId;
    private String largeAccountFlag;		//defect 9954 txphung
    private String extraLargeAccountFlag;	//defect 9954 txphung
    //TODO: add last logged in date.
    // Pay Installment enhancement
    private String paymentPlanId;

    @IgnoreProperty
    public String getLoginTypeString() {
        return (this.loginType == null ? null : this.loginType.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            //This object is not a null instance, so is not equal to null.
            return false;
        }//else
        try {
            final AccountLoginDTO other = (AccountLoginDTO)obj;
            return this.acctId == other.acctId;
        } catch (ClassCastException e){
            //Not an AccountLoginDTO
            return false;
        }
        //end equals
    }

    @Override
    public int hashCode() {
        return (int)(this.acctId % Integer.MAX_VALUE);
        //end hashCode
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return this.acctId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }

    public void setDbSessionId(String dbSessionId) {
        this.dbSessionId = dbSessionId;
    }

    public String getDbSessionId() {
        return this.dbSessionId;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public LoginType getLoginType() {
        return this.loginType;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public String getAcctStatus() {
        return this.acctStatus;
    }

    public void setAcctActivity(String acctActivity) {
        this.acctActivity = acctActivity;
    }

    public String getAcctActivity() {
        return this.acctActivity;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    /**
     * When LoginType is <tt>LoginType.IN</tt>, will contain the 
     * invoice id. When login type is <tt>LoginType.CA</tt>, 
     * will contain the collection agency id.
     * @return invoice id (or collection agency id)
     */
    public String getInvoiceId() {
        return this.invoiceId;
    }

    public void setViolatorId(long violatorId) {
        this.violatorId = violatorId;
    }

    public long getViolatorId() {
        return this.violatorId;
    }

    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
    }

    public String getLicPlate() {
        return this.licPlate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("com.etcc.csc.dto.AccountLoginDTO[");
        sb.append("acctId=");
        sb.append(this.acctId);
        sb.append(", loginId=");
        sb.append(this.loginId);
        sb.append(", lastLoginIp=");
        sb.append(this.lastLoginIp);
        sb.append(", dbSessionId=");
        sb.append(this.dbSessionId);
        sb.append(", loginType=");
        sb.append(this.loginType);
        sb.append(", acctStatus=");
        sb.append(this.acctStatus);
        sb.append(", acctActivity=");
        sb.append(this.acctActivity);
        sb.append(", pwChangeRequired=");
        sb.append(this.pwChangeRequired);
        sb.append(", invoiceId=");
        sb.append(this.invoiceId);
        sb.append(", violatorId=");
        sb.append(this.violatorId);
        sb.append(", licPlate=");
        sb.append(this.licPlate);
        
	    // defect 9954 txphung - begin
        sb.append(", largeAccountFlag=");
        sb.append(this.largeAccountFlag);
        sb.append(", extraLargeAccountFlag=");
        sb.append(this.extraLargeAccountFlag);
	    // defect 9954 txphung - end
        sb.append("]");
        return sb.toString();
    }

    public void setLicState(String licState) {
        this.licState = licState;
    }

    public String getLicState() {
        return this.licState;
    }

    public void setPwChangeRequired(boolean pwChangeRequired) {
        this.pwChangeRequired = pwChangeRequired;
    }

    public boolean isPwChangeRequired() {
        return this.pwChangeRequired;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }
    
    public void setValidationId(long validationId) {
		this.validationId = validationId;
	}
    public long getValidationId() {
		return validationId;
	}
    
	// defect 9954 txphung - begin
	public void setLargeAccountFlag(String largeAccountFlag) {
		this.largeAccountFlag = largeAccountFlag;
	}

	public String getLargeAccountFlag() {
		return this.largeAccountFlag;
	}

	public void setExtraLargeAccountFlag(String extraLargeAccountFlag) {
		this.extraLargeAccountFlag = extraLargeAccountFlag;
	}

	public String getExtraLargeAccountFlag() {
		return this.extraLargeAccountFlag;
	}

	// defect 9954 txphung - end
	
	//Pay Installment enhancement start
	public String getPaymentPlanId() {
		return paymentPlanId;
	}

	public void setPaymentPlanId(String paymentPlanId) {
		this.paymentPlanId = paymentPlanId;
	}
	//Pay Installment enhancement end
}
