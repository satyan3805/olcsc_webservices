package com.etcc.csc.delegate;

import java.util.Collection;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.oracleerrortest.ErrorTestInterface;

public class ErrorTestDelegate extends Delegate implements ErrorTestInterface {

	ErrorTestInterface eti = (ErrorTestInterface) getServiceObject(ServiceObjectEnum.ERROR_TEST);

	public ErrorTestDelegate() {
		super(ErrorTestDelegate.class);

	}

	public Collection performETest(String testParam) throws EtccException {
		try {
			return eti.performETest(testParam);
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running accountExists: " + t, t);

		}
	}
}
