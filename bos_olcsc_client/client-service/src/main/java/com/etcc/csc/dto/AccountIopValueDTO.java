/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.math.BigDecimal;

/**
 * Account inter-operability (IOP) Value Data Transfer Object. From the original OLCSCService module.
 */
public class AccountIopValueDTO extends BaseDTO {
	
    /**
	 * Unique ID for Serialization with Version number.
	 */
	//Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
	private static final long serialVersionUID = 8560979459862570759L;
	private BigDecimal acctId;
    private BigDecimal agcyId;
    private String agcyAbbrev;
    private String agcyName;
    private String reasonCode;
    private boolean active;
    
    public AccountIopValueDTO() {
    	//end <init>
    }
    
    public AccountIopValueDTO(BigDecimal acctId, BigDecimal agcyId, String agcyAbbrev, String agcyName,
            String reasonCode, boolean active) {
        this.acctId = acctId;
        this.agcyId = agcyId;
        this.agcyAbbrev = agcyAbbrev;
        this.agcyName = agcyName;
        this.reasonCode = reasonCode;
        this.active = active;
    }
    public AccountIopValueDTO(BigDecimal acctId, BigDecimal agcyId, String agcyAbbrev, String agcyName,
            String reasonCode, String active) {
        this(acctId, agcyId, agcyAbbrev, agcyName, reasonCode, ("Yy".indexOf(active.charAt(0)) >= 0));
    }
    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("acctId=");
        sb.append(this.acctId);
        sb.append(",agcyId=");
        sb.append(this.agcyId);
        sb.append(",agcyAbbrev=");
        sb.append(this.agcyAbbrev);
        sb.append(",agcyName=");
        sb.append(this.agcyName);
        sb.append(",reasonCode=");
        sb.append(this.reasonCode);
        sb.append(",active=");
        sb.append(this.active);
        sb.append("]");
        return sb.toString();
    }

    public void setAcctId(BigDecimal acctId) {
        this.acctId = acctId;
    }

    public BigDecimal getAcctId() {
        return this.acctId;
    }

    public void setAgcyId(BigDecimal agcyId) {
        this.agcyId = agcyId;
    }

    public BigDecimal getAgcyId() {
        return this.agcyId;
    }

    public void setAgcyAbbrev(String agcyAbbrev) {
        this.agcyAbbrev = agcyAbbrev;
    }

    public String getAgcyAbbrev() {
        return this.agcyAbbrev;
    }

    public void setAgcyName(String agcyName) {
        this.agcyName = agcyName;
    }

    public String getAgcyName() {
        return this.agcyName;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() {
        return this.reasonCode;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }
}
