package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.List;

public class GetViolationResponseDTO extends BaseDTO {

	private static final long serialVersionUID = -5509676367511884242L;

	private List<OlcAccountPlateRec> accountPlateArr = new ArrayList<OlcAccountPlateRec>();
	private String sessionId;
	private Long accountId;
	private OlcVpsAccountConvRec vpsAccountConvRec;
	private boolean inCollection;

	public List<OlcAccountPlateRec> getAccountPlateArr() {
		return accountPlateArr;
	}

	public void setAccountPlateArr(List<OlcAccountPlateRec> accountPlateArr) {
		this.accountPlateArr = accountPlateArr;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public OlcVpsAccountConvRec getVpsAccountConvRec() {
		return vpsAccountConvRec;
	}

	public void setVpsAccountConvRec(OlcVpsAccountConvRec vpsAccountConvRec) {
		this.vpsAccountConvRec = vpsAccountConvRec;
	}

	public boolean isInCollection() {
		return inCollection;
	}

	public void setInCollection(boolean inCollection) {
		this.inCollection = inCollection;
	}

	@Override
	public String toString() {
		return "GetViolationResponseDTO [accountPlateArr=" + accountPlateArr + ", sessionId=" + sessionId
				+ ", accountId=" + accountId + ", vpsAccountConvRec=" + vpsAccountConvRec + ", inCollection="
				+ inCollection + "]";
	}

}
