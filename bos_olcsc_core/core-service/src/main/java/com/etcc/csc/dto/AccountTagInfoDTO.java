package com.etcc.csc.dto;

import java.io.Serializable;

public class AccountTagInfoDTO implements Serializable {
	
	private long accountid;
	private long accountvehicleid;
	private String effectivedate;
	private String expirydate;
	private String transactionamount;
	private String transactioncount;
	
	
	public long getAccountid() {
		return accountid;
	}
	public void setAccountid(long accountid) {
		this.accountid = accountid;
	}
	public long getAccountvehicleid() {
		return accountvehicleid;
	}
	public void setAccountvehicleid(long accountvehicleid) {
		this.accountvehicleid = accountvehicleid;
	}
	public String getEffectivedate() {
		return effectivedate;
	}
	public void setEffectivedate(String effectivedate) {
		this.effectivedate = effectivedate;
	}
	public String getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}
	public String getTransactionamount() {
		return transactionamount;
	}
	public void setTransactionamount(String transactionamount) {
		this.transactionamount = transactionamount;
	}
	public String getTransactioncount() {
		return transactioncount;
	}
	public void setTransactioncount(String transactioncount) {
		this.transactioncount = transactioncount;
	}
}
