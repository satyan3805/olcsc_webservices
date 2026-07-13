package com.etcc.csc.webservice.rest.dto;

import java.util.List;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.TagDTO;

public class MakePaymentRequest {

	private AccountLoginDTO loginDTO;
	private BillingInfoDTO billingInfoDTO;
	private Double rebillAmount;
	private Double lowBalanceAmount;
	private List<TagDTO> tagList;
	private double paymentAmt;

	public AccountLoginDTO getLoginDTO() {
		return loginDTO;
	}

	public void setLoginDTO(AccountLoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}

	public BillingInfoDTO getBillingInfoDTO() {
		return billingInfoDTO;
	}

	public void setBillingInfoDTO(BillingInfoDTO billingInfoDTO) {
		this.billingInfoDTO = billingInfoDTO;
	}

	public Double getRebillAmount() {
		return rebillAmount;
	}

	public void setRebillAmount(Double rebillAmount) {
		this.rebillAmount = rebillAmount;
	}

	public Double getLowBalanceAmount() {
		return lowBalanceAmount;
	}

	public void setLowBalanceAmount(Double lowBalanceAmount) {
		this.lowBalanceAmount = lowBalanceAmount;
	}

	public List<TagDTO> getTagList() {
		return tagList;
	}

	public void setTagList(List<TagDTO> tagList) {
		this.tagList = tagList;
	}

	public double getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	@Override
	public String toString() {
		return "MakePaymentRequest [loginDTO=" + loginDTO + ", billingInfoDTO=" + billingInfoDTO + ", rebillAmount="
				+ rebillAmount + ", lowBalanceAmount=" + lowBalanceAmount + ", tagList=" + tagList + ", paymentAmt="
				+ paymentAmt + "]";
	}

}
