package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;

public class SetupOnlineAccessRequest {
	private AccountLoginDTO acctLoginDto;
	private OnlineAccessSetupDTO onlineAccessDTO;
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	public OnlineAccessSetupDTO getOnlineAccessDTO() {
		return onlineAccessDTO;
	}
	public void setOnlineAccessDTO(OnlineAccessSetupDTO onlineAccessDTO) {
		this.onlineAccessDTO = onlineAccessDTO;
	}
	@Override
	public String toString() {
		return "SetupOnlineAccessRequest [acctLoginDto=" + acctLoginDto + ", onlineAccessDTO=" + onlineAccessDTO + "]";
	}
	
	

}
