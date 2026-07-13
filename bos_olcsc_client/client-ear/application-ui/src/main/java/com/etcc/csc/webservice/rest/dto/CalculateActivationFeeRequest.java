package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;

public class CalculateActivationFeeRequest {
	
	private AccountLoginDTO acctLoginDto;
	private long stickerTagCount; 
	private long motocycleCount;
	private long licensePlateTagCount;
	
	
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public long getStickerTagCount() {
		return stickerTagCount;
	}
	public long getMotocycleCount() {
		return motocycleCount;
	}
	public long getLicensePlateTagCount() {
		return licensePlateTagCount;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	public void setStickerTagCount(long stickerTagCount) {
		this.stickerTagCount = stickerTagCount;
	}
	public void setMotocycleCount(long motocycleCount) {
		this.motocycleCount = motocycleCount;
	}
	public void setLicensePlateTagCount(long licensePlateTagCount) {
		this.licensePlateTagCount = licensePlateTagCount;
	}
	@Override
	public String toString() {
		return "CalculateActivationFeeRequest [acctLoginDto=" + acctLoginDto + ", stickerTagCount=" + stickerTagCount
				+ ", motocycleCount=" + motocycleCount + ", licensePlateTagCount=" + licensePlateTagCount + "]";
	}
	
	

}
