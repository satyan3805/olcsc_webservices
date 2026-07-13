package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BillingInfoDTO;

public class SetBillingInfoRequest {

	private AccountLoginDTO acctLoginDto;
	private BillingInfoDTO billingInfoDTO;

	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}

	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}

	public BillingInfoDTO getBillingInfoDTO() {
		return billingInfoDTO;
	}

	public void setBillingInfoDTO(BillingInfoDTO billingInfoDTO) {
		this.billingInfoDTO = billingInfoDTO;
	}

	@Override
	public String toString() {
		return "SetBillingInfoRequest [acctLoginDto=" + acctLoginDto + ", billingInfoDTO=" + billingInfoDTO + "]";
	}

}
