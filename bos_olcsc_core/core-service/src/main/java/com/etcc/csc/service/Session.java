package com.etcc.csc.service;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.SessionDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SessionDTO;

/**
 * Contains session management methods.
 */
public class Session implements SessionInterface {
	Logger logger = Logger.getLogger(Session.class);

	public Session() {
	}

	public String makeSession(SessionDTO sessionDto) throws EtccException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			SessionDAO sessionDao = daoFactory.getSessionDAO();
			return sessionDao.makeSession(sessionDto);
		} catch (Exception e) {
			logger.error("Error in createSession " + sessionDto + " " + e, e);
			throw new EtccException(e);
		}
	}

	public void destroySession(String dbSessionId) throws EtccException {
		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			SessionDAO sessionDao = daoFactory.getSessionDAO();
			sessionDao.destroySession(dbSessionId);
		} catch (Exception e) {
			logger.error("Error in destroySession " + dbSessionId + " " + e, e);
			throw new EtccException(e);
		}
	}

	public void updateSessionLanguage(AccountLoginDTO acctLoginDto, String lang)
			throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			SessionDAO sessionDao = daoFactory.getSessionDAO();
			sessionDao.updateSessionLanguage(acctLoginDto, lang);
		} catch (Exception e) {
			logger.error("Error in updateSessionLanguage " + " " + e, e);
			throw new EtccException(e);
		}
	}
}
