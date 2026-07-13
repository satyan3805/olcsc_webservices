package com.etcc.csc.enums;

public enum ResponsibilityTypeEnum {
	OWNER('O'),
	RENTER('R');
	
	private char code;

	ResponsibilityTypeEnum(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}
}
