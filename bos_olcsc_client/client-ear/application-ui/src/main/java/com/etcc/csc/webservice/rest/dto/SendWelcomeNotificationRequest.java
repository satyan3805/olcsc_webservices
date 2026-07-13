package com.etcc.csc.webservice.rest.dto;

import java.util.List;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;

public class SendWelcomeNotificationRequest {

	private AccountLoginDTO acctLoginDto;
	private double activationFee;
	private List<TagDTO> tagDTOSs;

	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}

	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}

	public double getActivationFee() {
		return activationFee;
	}

	public void setActivationFee(double activationFee) {
		this.activationFee = activationFee;
	}

	public List<TagDTO> getTagDTOSs() {
		return tagDTOSs;
	}

	public void setTagDTOSs(List<TagDTO> tagDTOSs) {
		this.tagDTOSs = tagDTOSs;
	}

	@Override
	public String toString() {
		return "SendWelcomeNotificationRequest [acctLoginDto=" + acctLoginDto + ", activationFee=" + activationFee
				+ ", tagDTOSs=" + tagDTOSs + "]";
	}

}