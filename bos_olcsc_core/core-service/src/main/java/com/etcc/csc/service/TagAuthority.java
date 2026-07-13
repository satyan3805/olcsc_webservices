package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Logger;
import com.etcc.csc.dao.TagAuthorityDAO;
import com.etcc.csc.dto.TagAuthorityDTO;

/**
 * Business logic for the tag authority.
 */
public class TagAuthority implements TagAuthorityInterface {

	Logger logger = Logger.getLogger(TagAuthority.class);

	public TagAuthority() {
	}

	public Collection getTagAuthorities() throws EtccException,
			EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			TagAuthorityDAO taDao = daoFactory.getTagAuthorityDAO();
			return taDao.getTagAuthorities();
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getTagAuthorities() " + ese,
					ese);
			throw ese;
		} catch (Exception e) {
			logger.error("Error in getTagAuthorities " + e, e);
			throw new EtccException(e);
		}

	}

	/**
	 * Stub method to force the inclusion of tagAuthorityDTO to the wsdl.
	 * 
	 * @return
	 */
	public TagAuthorityDTO getTagAuthority() {
		return null;
	}

	public String getTagApplicationAgreement(String lang) throws EtccException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		TagAuthorityDAO taDao = daoFactory.getTagAuthorityDAO();
		return taDao.getTagApplicationAgreement(lang);
	}
}
