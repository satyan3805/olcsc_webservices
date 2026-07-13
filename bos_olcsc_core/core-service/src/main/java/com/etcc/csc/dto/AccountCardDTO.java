package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Calendar;

public class AccountCardDTO extends BaseDTO {

    private long acctId;
    private long cardId;
    private String cardIdStr;    
    private String acctCardStatus;
    private String cardStatus;
    private Calendar assignDt;
    private Calendar expireDt;
    private String createdBy;
    private Calendar createDt;
    private String modifiedBy;
    private Calendar modifiedDt;
    private String accountInventoryId;

    public AccountCardDTO() {
    }


    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return acctId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public long getCardId() {
        return cardId;
    }

    public void setAcctCardStatus(String acctCardStatus) {
        this.acctCardStatus = acctCardStatus;
    }

    public String getAcctCardStatus() {
        return acctCardStatus;
    }

    public void setAssignDt(Calendar assignDt) {
        this.assignDt = assignDt;
    }

    public Calendar getAssignDt() {
        return assignDt;
    }

    public void setExpireDt(Calendar expireDt) {
        this.expireDt = expireDt;
    }

    public Calendar getExpireDt() {
        return expireDt;
    }

    public void setCreateDt(Calendar createDt) {
        this.createDt = createDt;
    }

    public Calendar getCreateDt() {
        return createDt;
    }

    public void setModifiedDt(Calendar modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public Calendar getModifiedDt() {
        return modifiedDt;
    }
    
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    public String getModifiedBy(){
        return this.modifiedBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getCreatedBy(){
        return this.createdBy;
    }


    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardIdStr(String cardIdStr) {
        this.cardIdStr = cardIdStr;
    }

    public String getCardIdStr() {
        return cardIdStr;
    }


	public String getAccountInventoryId() {
		return accountInventoryId;
	}


	public void setAccountInventoryId(String accountInventoryId) {
		this.accountInventoryId = accountInventoryId;
	}
    
    
}
