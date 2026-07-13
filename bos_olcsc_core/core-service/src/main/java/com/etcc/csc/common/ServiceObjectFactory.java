package com.etcc.csc.common;

public class ServiceObjectFactory extends EtcFactory {

	public ServiceObjectFactory() {
	}

	public static BusinessObjectInterface create(ServiceObjectEnum s) {
		Object o = getObject(s);
		return (BusinessObjectInterface) o;
	}
}
