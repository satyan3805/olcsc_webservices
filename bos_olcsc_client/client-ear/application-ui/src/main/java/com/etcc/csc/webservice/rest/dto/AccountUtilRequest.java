package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;

public class AccountUtilRequest {
	
	private AccountLoginDTO acctLoginDto; 
	private String refundType;
	
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	@Override
	public String toString() {
		return "AccountUtilRequest [acctLoginDto=" + acctLoginDto + ", refundType=" + refundType + "]";
	}
	
	

}
