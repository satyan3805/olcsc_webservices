package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;

public class GetTransactionRecordsRequest {

	private AccountLoginDTO acctLoginDto;
	private String startDate;
	private String endDate;
	private String agencyId;
	private String tagId;
	private String transFilter;
	private String nickName;
	private String nickNameType;
	private String dateType;

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

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTransFilter() {
		return transFilter;
	}

	public void setTransFilter(String transFilter) {
		this.transFilter = transFilter;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickNameType() {
		return nickNameType;
	}

	public void setNickNameType(String nickNameType) {
		this.nickNameType = nickNameType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	@Override
	public String toString() {
		return "GetTransactionRecordsRequest [acctLoginDto=" + acctLoginDto + ", startDate=" + startDate + ", endDate="
				+ endDate + ", agencyId=" + agencyId + ", tagId=" + tagId + ", transFilter=" + transFilter
				+ ", nickName=" + nickName + ", nickNameType=" + nickNameType + ", dateType=" + dateType + "]";
	}

}
