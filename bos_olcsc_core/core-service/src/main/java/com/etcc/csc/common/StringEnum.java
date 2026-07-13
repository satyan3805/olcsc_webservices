package com.etcc.csc.common;

public class StringEnum {
	private String value;

	protected StringEnum() {
	}

	protected StringEnum(String x) {
		value = x;
	}

	public String getValue() {
		return value;
	}

	public boolean equals(String x) {
		return this.value.equals(x);
	}

	public String toString() {
		return getValue();
	}
}