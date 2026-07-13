package com.etcc.csc.webservice.rest.dto;

import java.util.List;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;

public class OracleConfirmTagRequest {

	private AccountLoginDTO acctLoginDto;
	private long transactionId;
	private TagDTO.DeliveryMethod deliveryMethod;
	private List<TagDTO> tagDtos;
	
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public TagDTO.DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(TagDTO.DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public List<TagDTO> getTagDtos() {
		return tagDtos;
	}
	public void setTagDtos(List<TagDTO> tagDtos) {
		this.tagDtos = tagDtos;
	}
	

}
