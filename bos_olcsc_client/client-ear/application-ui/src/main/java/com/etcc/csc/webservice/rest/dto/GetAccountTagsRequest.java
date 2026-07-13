package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;

public class GetAccountTagsRequest {
	private AccountLoginDTO acctLoginDto;
	private String searchValue;
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	@Override
	public String toString() {
		return "GetAccountTagsRequest [acctLoginDto=" + acctLoginDto + ", searchValue=" + searchValue + "]";
	}
	
	

}
