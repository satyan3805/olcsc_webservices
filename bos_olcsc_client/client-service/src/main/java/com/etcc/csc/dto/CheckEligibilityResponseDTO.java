package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.List;

public class CheckEligibilityResponseDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8474483307698122997L;
	private List<OlcVpsPlateTypeRec> OlcVpsPlateTypeRecList = new ArrayList<OlcVpsPlateTypeRec>();
	private String sessionId;
	private Long accountId;

	/**
	 * @return the olcVpsPlateTypeRecList
	 */
	public List<OlcVpsPlateTypeRec> getOlcVpsPlateTypeRecList() {
		return OlcVpsPlateTypeRecList;
	}

	/**
	 * @param olcVpsPlateTypeRecList
	 *            the olcVpsPlateTypeRecList to set
	 */
	public void setOlcVpsPlateTypeRecList(List<OlcVpsPlateTypeRec> olcVpsPlateTypeRecList) {
		OlcVpsPlateTypeRecList = olcVpsPlateTypeRecList;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CheckEligibilityResponseDTO [OlcVpsPlateTypeRecList=" + OlcVpsPlateTypeRecList + ", sessionId="
				+ sessionId + ", accountId=" + accountId + "]";
	}

}
