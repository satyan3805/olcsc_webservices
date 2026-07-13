package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.List;

public class GetAccountPhoneInfoResponse extends BaseDTO{
	
	private static final long serialVersionUID = 6440404063423288938L;
	
	private List<AccountPhoneInfo> accountPhoneInfoList = new ArrayList<AccountPhoneInfo>();

	public List<AccountPhoneInfo> getAccountPhoneInfoList() {
		return accountPhoneInfoList;
	}

	public void setAccountPhoneInfoList(List<AccountPhoneInfo> accountPhoneInfoList) {
		this.accountPhoneInfoList = accountPhoneInfoList;
	}

	@Override
	public String toString() {
		return "GetAccountPhoneInfoResponse [accountPhoneInfoList=" + accountPhoneInfoList + "]";
	}
	
	

}
