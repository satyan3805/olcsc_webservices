package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.List;

public class GetAccountDocumentResponse extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -269464340437297400L;

	private List<AccountDocument> accountDocumentList = new ArrayList<AccountDocument>();

	/**
	 * @return the accountDocumentList
	 */
	public List<AccountDocument> getAccountDocumentList() {
		return accountDocumentList;
	}

	/**
	 * @param accountDocumentList
	 *            the accountDocumentList to set
	 */
	public void setAccountDocumentList(List<AccountDocument> accountDocumentList) {
		this.accountDocumentList = accountDocumentList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GetAccountDocumentResponse [accountDocumentList=" + accountDocumentList + "]";
	}

}
