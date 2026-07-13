package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;

public class UpdatePasswordEmailSecurityQARequest {

	private AccountLoginDTO acctLoginDto;
	private int updateFlags;
	private String oldPw;
	private String newPw;
	private String emailAddress;
	private int sQuestionID;
	private String sAnswer;

	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}

	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}

	public int getUpdateFlags() {
		return updateFlags;
	}

	public void setUpdateFlags(int updateFlags) {
		this.updateFlags = updateFlags;
	}

	public String getOldPw() {
		return oldPw;
	}

	public void setOldPw(String oldPw) {
		this.oldPw = oldPw;
	}

	public String getNewPw() {
		return newPw;
	}

	public void setNewPw(String newPw) {
		this.newPw = newPw;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getsQuestionID() {
		return sQuestionID;
	}

	public void setsQuestionID(int sQuestionID) {
		this.sQuestionID = sQuestionID;
	}

	public String getsAnswer() {
		return sAnswer;
	}

	public void setsAnswer(String sAnswer) {
		this.sAnswer = sAnswer;
	}

	@Override
	public String toString() {
		return "UpdatePasswordEmailSecurityQARequest [acctLoginDto=" + acctLoginDto + ", updateFlags=" + updateFlags
				+ ", oldPw=" + oldPw + ", newPw=" + newPw + ", emailAddress=" + emailAddress + ", sQuestionID="
				+ sQuestionID + ", sAnswer=" + sAnswer + "]";
	}

}
