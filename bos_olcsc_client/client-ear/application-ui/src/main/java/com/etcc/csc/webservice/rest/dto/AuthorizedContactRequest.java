package com.etcc.csc.webservice.rest.dto;

import java.util.Arrays;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;

public class AuthorizedContactRequest {
	
	private AccountLoginDTO acctLoginDto;
	private AuthorizedContactDTO[] authorizedContactDTO;
	
	
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	
	public AuthorizedContactDTO[] getAuthorizedContactDTO() {
		return authorizedContactDTO;
	}
	public void setAuthorizedContactDTO(AuthorizedContactDTO[] authorizedContactDTO) {
		this.authorizedContactDTO = authorizedContactDTO;
	}
	@Override
	public String toString() {
		return "AuthorizedContactRequest [acctLoginDto=" + acctLoginDto + ", authorizedContactDTO="
				+ Arrays.toString(authorizedContactDTO) + "]";
	}
	

}
