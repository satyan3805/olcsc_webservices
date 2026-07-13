package com.etcc.csc.dto;

public class ValidateDocDownloadResponse extends BaseDTO {
	
	private static final long serialVersionUID = -6036635802502973189L;
	
	private Boolean isAccountValidationSuccess;

	public Boolean getIsAccountValidationSuccess() {
		return isAccountValidationSuccess;
	}

	public void setIsAccountValidationSuccess(Boolean isAccountValidationSuccess) {
		this.isAccountValidationSuccess = isAccountValidationSuccess;
	}

	@Override
	public String toString() {
		return "ValidateDocDownloadResponse [isAccountValidationSuccess=" + isAccountValidationSuccess + "]";
	}
	
	

}
