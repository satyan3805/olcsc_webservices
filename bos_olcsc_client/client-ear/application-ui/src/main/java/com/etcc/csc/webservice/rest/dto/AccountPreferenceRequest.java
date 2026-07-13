package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;

public class AccountPreferenceRequest {
	private AccountLoginDTO acctLoginDto;
	private AccountPreferencesDTO accountPreferencesDTO;
	
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	public AccountPreferencesDTO getAccountPreferencesDTO() {
		return accountPreferencesDTO;
	}
	public void setAccountPreferencesDTO(AccountPreferencesDTO accountPreferencesDTO) {
		this.accountPreferencesDTO = accountPreferencesDTO;
	}
	@Override
	public String toString() {
		return "AccountPreferenceRequest [acctLoginDto=" + acctLoginDto + ", accountPreferencesDTO="
				+ accountPreferencesDTO + "]";
	}
	
	

}
