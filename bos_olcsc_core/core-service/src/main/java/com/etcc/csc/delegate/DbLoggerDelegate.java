package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.service.DbLoggerInterface;

/**
 * Wrapper between the web service stub and the web service client.
 */
public class DbLoggerDelegate extends Delegate implements DbLoggerInterface {

	DbLoggerInterface dbl = (DbLoggerInterface) getServiceObject(ServiceObjectEnum.DB_LOGGER);

	public DbLoggerDelegate() {
		super(DbLoggerDelegate.class);
	}

	public String logError(String message, String stack, String sessionId,
			String ip) throws EtccException {
		try {

			return dbl.logError(message, stack, sessionId, ip);

		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running logError: " + t, t);
		}
	}

	public String logSecurityViolation(String message) throws EtccException {
		try {
			return dbl.logSecurityViolation(message);
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running logSecurityViolation: " + t,
					t);
		}
	}
}
