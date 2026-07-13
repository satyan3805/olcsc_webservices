package com.etcc.csc.service;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Logger;
import com.etcc.csc.dao.DbLoggerDAO;

/**
 * Exposes Violation functionality through web service.
 */
public class DbLogger implements DbLoggerInterface {
	Logger logger = Logger.getLogger(DbLogger.class);

	public DbLogger() {
	}

	public String logError(String message, String stack, String sessionId,
			String ip) throws EtccException {
		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			DbLoggerDAO dbLoggerDao = daoFactory.getDbLoggerDAO();
			String result = dbLoggerDao.logError(message, stack, sessionId, ip);
			return result;
		} catch (Exception e) {
			logger.error("Error logging error " + e, e);
			throw new EtccException(e);
		}
	}

	public String logSecurityViolation(String message) throws EtccException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			DbLoggerDAO dbLoggerDao = daoFactory.getDbLoggerDAO();
			String result = dbLoggerDao.logSecurityViolation(message);
			return result;
		} catch (Exception e) {
			logger.error("Error logging security violation " + e, e);
			throw new EtccException(e);
		}
	}

}