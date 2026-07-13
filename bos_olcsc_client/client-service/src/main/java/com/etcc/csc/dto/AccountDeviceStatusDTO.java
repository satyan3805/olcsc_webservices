package com.etcc.csc.dto;

import java.util.Date;

public class AccountDeviceStatusDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8133160509371857186L;
	
	private String mfaRequired;
	private Date deviceDate;
	
	public String getMfaRequired() {
		return mfaRequired;
	}
	public void setMfaRequired(String mfaRequired) {
		this.mfaRequired = mfaRequired;
	}
	public Date getDeviceDate() {
		return deviceDate;
	}
	public void setDeviceDate(Date deviceDate) {
		this.deviceDate = deviceDate;
	}
	
	

}
