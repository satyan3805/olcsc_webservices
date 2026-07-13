package com.etcc.csc.dto;

import java.io.Serializable;

public class DMVInfoHolder implements Serializable {
	

	
	private DMVVehicleOwnerDTO[] dmvvehRecord;
	
	public DMVInfoHolder(){}

	public DMVVehicleOwnerDTO[] getDmvvehRecord() {
		return dmvvehRecord;
	}

	public void setDmvvehRecord(DMVVehicleOwnerDTO[] dmvvehRecord) {
		this.dmvvehRecord = dmvvehRecord;
	}


}
