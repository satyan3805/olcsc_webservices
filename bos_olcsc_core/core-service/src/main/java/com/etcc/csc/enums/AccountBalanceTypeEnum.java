package com.etcc.csc.enums;

public enum AccountBalanceTypeEnum {
	INITIAL_PREPAID_BALANCE("INP"),
	MINIMUM_REBILL_AMT("RBL"),
	LOW_BALANCE_AMT("LOW"),
	TAG_SALE_ACTION("TDP"),
	TAG_DEPOSIT_ACTION("TDA"),
	LOST_TAG_FEE("LTF");

	private String code;
	AccountBalanceTypeEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

}// end of AccountBalanceTypeEnum Class
