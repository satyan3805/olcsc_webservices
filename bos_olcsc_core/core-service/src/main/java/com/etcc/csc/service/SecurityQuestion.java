package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.SecurityQuestionDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;

public class SecurityQuestion implements SecurityQuestionInterface {
	private Logger logger = Logger.getLogger(SecurityQuestion.class);

	public SecurityQuestion() {
	}

	public ArrayList getSecurityQuestions(String locale) throws EtccException,
			EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			SecurityQuestionDAO securityQuestionDao = daoFactory
					.getSecurityQuestionDAO();
			ArrayList col = securityQuestionDao.getSecurityQuestions(locale);
			return col;
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getSecurityQuestions() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getSecurityQuestions() " + ee, ee);
			throw ee;
		}
	}

	public ArrayList getSecurityQuestionsnAnswers(AccountLoginDTO acctLoginDto,
			String locale) throws EtccException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			SecurityQuestionDAO securityQuestionDao = daoFactory
					.getSecurityQuestionDAO();
			ArrayList col = securityQuestionDao.getSecurityQuestionsnAnswers(
					acctLoginDto, locale);
			return col;
		} catch (EtccException ee) {
			logger.error("Exception in getSecurityQuestions() " + ee, ee);
			throw ee;
		}
	}

	public String getSysParam(String paramName) throws EtccException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			SecurityQuestionDAO securityQuestionDao = daoFactory
					.getSecurityQuestionDAO();
			String col = securityQuestionDao.getSysParam(paramName);
			return col;
		} catch (EtccException ee) {
			logger.error("Exception in getSecurityQuestions() " + ee, ee);
			throw ee;
		}
	}

	public int saveAnswers(AccountLoginDTO acctLoginDto,
			List<SecurityQuestionDTO> listOfQuestionsnAnswers)
			throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			SecurityQuestionDAO securityQuestionDao = daoFactory
					.getSecurityQuestionDAO();
			int col = securityQuestionDao.saveAnswers(acctLoginDto,
					listOfQuestionsnAnswers);
			return col;
		} catch (EtccException ee) {
			logger.error("Exception in getSecurityQuestions() " + ee, ee);
			throw ee;
		}

	}
}