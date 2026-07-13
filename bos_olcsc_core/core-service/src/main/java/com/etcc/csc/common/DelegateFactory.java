package com.etcc.csc.common;

public class DelegateFactory extends EtcFactory {

	private DelegateFactory() {
	}

	public static DelegateInterface create(DelegateEnum d) {
		Object o = getObject(d);
		return (DelegateInterface) o;
	}
}
