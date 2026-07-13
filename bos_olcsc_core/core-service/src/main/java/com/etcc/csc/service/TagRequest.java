package com.etcc.csc.service;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.TagRequestDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;

public class TagRequest implements TagRequestInterface {
	private Logger logger = Logger.getLogger(TagAuthority.class);

	public TagDTO addTag(String sessionId, String ipAddress, String userId,
			TagDTO tagRequestDto, Long posId) throws EtccException,
			EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			TagRequestDAO tagRequestDAO = daoFactory.getTagRequestDAO();

			return tagRequestDAO.addTag(sessionId, ipAddress, userId,
					tagRequestDto, posId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in addTag() " + ese, ese);
			throw ese;
		} catch (Exception e) {
			logger.error("Error in addTag " + e, e);
			throw new EtccException(e);
		}
	}

	public TagDTO modifyTag(String sessionId, String ipAddress, String userId,
			TagDTO tagRequestDto, String transType, Long posId)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			TagRequestDAO tagRequestDAO = daoFactory.getTagRequestDAO();

			return tagRequestDAO.modifyTag(sessionId, ipAddress, userId,
					tagRequestDto, transType, posId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in modifyTag() " + ese, ese);
			throw ese;
		} catch (Exception e) {
			logger.error("Error in modifyTag() " + e, e);
			throw new EtccException(e);
		}
	}

	public boolean confirmAddTags(String sessionId, String ipAddress,
			String userId, String acctId, long transactionId)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			TagRequestDAO tagRequestDAO = daoFactory.getTagRequestDAO();
			return tagRequestDAO.confirmAddTags(sessionId, ipAddress, userId,
					acctId, transactionId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in confirmAddTags() " + ese, ese);
			throw ese;
		} catch (Exception e) {
			logger.error("Error in confirmAddTags() " + e, e);
			throw new EtccException(e);
		}
	}

	public boolean confirmAddTagsTwo(String sessionId, String ipAddress,
			String userId, String acctId, long transactionId)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			TagRequestDAO tagRequestDAO = daoFactory.getTagRequestDAO();
			return tagRequestDAO.confirmAddTagsTwo(sessionId, ipAddress,
					userId, acctId, transactionId);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in confirmAddTagsTwo() " + ese,
					ese);
			throw ese;
		} catch (Exception e) {
			logger.error("Error in confirmAddTagsTwo() " + e, e);
			throw new EtccException(e);
		}
	}

	public String addTagsReceipt(String sessionId, String ipAddress,
			String userId, String acctId, String reportFormat)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			TagRequestDAO tagRequestDAO = daoFactory.getTagRequestDAO();
			return tagRequestDAO.addTagsReceipt(sessionId, ipAddress, userId,
					acctId, reportFormat);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in confirmAddTags() " + ese, ese);
			throw ese;
		} catch (Exception e) {
			logger.error("Error in confirmAddTags() " + e, e);
			throw new EtccException(e);
		}
	}

	public int activateTag(AccountLoginDTO loginDto, TagDTO tagRequestDTO)
			throws EtccException, EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			TagRequestDAO tagRequestDAO = daoFactory.getTagRequestDAO();
			return tagRequestDAO.activateTag(loginDto, tagRequestDTO);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in activateTag() " + ese, ese);
			throw ese;
		} catch (Exception e) {
			logger.error("Error in activateTag() " + e, e);
			throw new EtccException(e);
		}
	}
}
