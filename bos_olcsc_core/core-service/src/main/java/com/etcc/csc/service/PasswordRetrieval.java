package com.etcc.csc.service;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.PasswordRetrievalDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.UserEnvDTO;

public class PasswordRetrieval implements PasswordRetrievalInterface {
	private Logger logger = Logger.getLogger(PasswordRetrieval.class);

	public PasswordRetrieval() {
	}

	public OnlineAccessSetupDTO retrieveAccountInfo(
			OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,
			String sessionId, UserEnvDTO userEnvDto, String locale)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			PasswordRetrievalDAO passwordRetrievalDAO = daoFactory
					.getPasswordRetrievalDAO();

			return passwordRetrievalDAO.retrieveAccountInfo(onlineAccessDTO,
					ipAddress, sessionId, userEnvDto, locale);
		} catch (EtccException ee) {
			logger.error("EtccException in retrieveAccountInfo() " + ee, ee);
			throw ee;
		}
	}

	public boolean validateSecurityAnswer(String sessionId, String ipAddress,
			OnlineAccessSetupDTO onlineAccessDTO) throws EtccException,
			EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			PasswordRetrievalDAO passwordRetrievalDAO = daoFactory
					.getPasswordRetrievalDAO();

			return passwordRetrievalDAO.validateSecurityAnswer(sessionId,
					ipAddress, onlineAccessDTO);
		} catch (EtccException ee) {
			logger.error("EtccException in validateSecurityAnswer() " + ee, ee);
			throw ee;
		}
	}

	public boolean addSecurityQuestionAnswer(String sessionId,
			String ipAddress, OnlineAccessSetupDTO onlineAccessDTO)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			PasswordRetrievalDAO passwordRetrievalDAO = daoFactory
					.getPasswordRetrievalDAO();

			return passwordRetrievalDAO.addSecurityQuestionAnswer(sessionId,
					ipAddress, onlineAccessDTO);
		} catch (EtccException ee) {
			logger.error("EtccException in addSecurityQuestionAnswer() " + ee,
					ee);
			throw ee;
		}
	}

	public boolean addEmailAddress(String sessionId, String ipAddress,
			OnlineAccessSetupDTO onlineAccessDTO) throws EtccException,
			EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			PasswordRetrievalDAO passwordRetrievalDAO = daoFactory
					.getPasswordRetrievalDAO();

			return passwordRetrievalDAO.addEmailAddress(sessionId, ipAddress,
					onlineAccessDTO);
		} catch (EtccException ee) {
			logger.error("EtccException in addEmailAddress() " + ee, ee);
			throw ee;
		}
	}

	public Collection changePassword(AccountLoginDTO acctLoginDTO,
			String oldPwd, String pwd) throws EtccException,
			EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			PasswordRetrievalDAO passwordRetrievalDAO = daoFactory
					.getPasswordRetrievalDAO();
			Collection col = passwordRetrievalDAO.changePassword(acctLoginDTO,
					oldPwd, pwd);
			return col;
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in changePassword " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in changePassword " + ee, ee);
			throw ee;
		}
	}

	public Collection resetPassword(AccountLoginDTO acctLoginDTO, String pwd)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			PasswordRetrievalDAO passwordRetrievalDAO = daoFactory
					.getPasswordRetrievalDAO();
			Collection col = passwordRetrievalDAO.resetPassword(acctLoginDTO,
					pwd);
			return col;
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in changePassword " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in changePassword " + ee, ee);
			throw ee;
		}
	}
}
