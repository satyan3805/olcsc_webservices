package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;

public class ViewTransactionMainRequest {
	private AccountLoginDTO acctLoginDto;
	private String startDate;
	private String endDate;
	private String acctVPNType;

	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}

	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAcctVPNType() {
		return acctVPNType;
	}

	public void setAcctVPNType(String acctVPNType) {
		this.acctVPNType = acctVPNType;
	}

	@Override
	public String toString() {
		return "ViewTransactionMainRequest [acctLoginDto=" + acctLoginDto + ", startDate=" + startDate + ", endDate="
				+ endDate + ", acctVPNType=" + acctVPNType + "]";
	}

}
