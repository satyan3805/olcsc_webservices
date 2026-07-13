package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.List;

public class GetAccountNotificationsDocumentResponse extends BaseDTO {

	private static final long serialVersionUID = 5741986640514937527L;
	private List<AccountNotificationDocument> accountNotifictaionDocuments = new ArrayList<AccountNotificationDocument>();

	public List<AccountNotificationDocument> getAccountNotifictaionDocuments() {
		return accountNotifictaionDocuments;
	}

	public void setAccountNotifictaionDocuments(List<AccountNotificationDocument> accountNotifictaionDocuments) {
		this.accountNotifictaionDocuments = accountNotifictaionDocuments;
	}

	@Override
	public String toString() {
		return "GetAccountNotificationsDocumentResponse [accountNotifictaionDocuments=" + accountNotifictaionDocuments
				+ "]";
	}

}
