package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;

public class OracleTagRequest {
	
	private AccountLoginDTO acctLoginDto;
	private TagDTO tagDTO;
	private Long posId;
	
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	public TagDTO getTagDTO() {
		return tagDTO;
	}
	public void setTagDTO(TagDTO tagDTO) {
		this.tagDTO = tagDTO;
	}
	public Long getPosId() {
		return posId;
	}
	public void setPosId(Long posId) {
		this.posId = posId;
	}
	@Override
	public String toString() {
		return "OracleTagRequest [acctLoginDto=" + acctLoginDto + ", tagDTO=" + tagDTO + ", posId=" + posId + "]";
	}

	
}
