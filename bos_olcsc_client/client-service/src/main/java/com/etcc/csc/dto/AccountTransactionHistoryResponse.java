package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.List;

public class AccountTransactionHistoryResponse extends BaseDTO {

	private static final long serialVersionUID = -1005650659474295846L;
	
	private List<AccountTransactionHistory> accountTransactionHistoryList = new ArrayList<AccountTransactionHistory>();

	public List<AccountTransactionHistory> getAccountTransactionHistoryList() {
		return accountTransactionHistoryList;
	}

	public void setAccountTransactionHistoryList(List<AccountTransactionHistory> accountTransactionHistoryList) {
		this.accountTransactionHistoryList = accountTransactionHistoryList;
	}

	@Override
	public String toString() {
		return "AccountTransactionHistoryResponse [accountTransactionHistoryList=" + accountTransactionHistoryList
				+ "]";
	}


}
