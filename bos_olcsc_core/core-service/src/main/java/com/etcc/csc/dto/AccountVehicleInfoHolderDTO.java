package com.etcc.csc.dto;

import java.io.Serializable;

public class AccountVehicleInfoHolderDTO implements Serializable {

	private AccountVehicleInfoDTO[] acctvehRecord;

	public AccountVehicleInfoHolderDTO() {
		super();
	}

	public AccountVehicleInfoDTO[] getAcctvehRecord() {
		return acctvehRecord;
	}

	public void setAcctvehRecord(AccountVehicleInfoDTO[] acctvehRecord) {
		this.acctvehRecord = acctvehRecord;
	}
	
}
