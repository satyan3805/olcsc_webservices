package com.etcc.csc.dto;

import java.io.Serializable;

public class AccountTagInfoHolderDTO implements Serializable {

	private AccountTagInfoDTO[] acctTagRecord;

	public AccountTagInfoHolderDTO() {
		super();
	}

	public void setAcctTagRecord(AccountTagInfoDTO[] acctTagRecord) {
		this.acctTagRecord = acctTagRecord;
	}

	public AccountTagInfoDTO[] getAcctTagRecord() {
		return acctTagRecord;
	}

	
}