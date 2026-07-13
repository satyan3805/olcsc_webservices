package com.etcc.csc.delegate;

import org.apache.commons.lang.StringUtils;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.service.AdminInterface;

/**
 * Wrapper between the web service stub and the web service client.
 */
public class AdminDelegate extends Delegate implements AdminInterface {
	AdminInterface admin = (AdminInterface) getServiceObject(ServiceObjectEnum.ADMIN);

	public AdminDelegate() {
		super(AdminDelegate.class);
	}

	public void insertSessionCount(String serverName, int count) {
		try {
			if (!StringUtils.isEmpty(serverName)) {
				admin.insertSessionCount(serverName, count);
			}
		} catch (Throwable t) {
			logger.error(t);
			// handle the exception quietly, but still panic
		}
	}
}
