package com.etcc.csc.webservice.rest;


import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.SecurityQuestionDAO;
import com.etcc.csc.dto.SecurityQuestionDTO;

@Path("/session")
public class SecurityQuestionsRest {
	private static final Logger logger = Logger.getLogger(SecurityQuestionsRest.class);

	@POST
	@Path("/getSecurityQuestions")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Collection<SecurityQuestionDTO> getSecurityQuestions() throws EtccException {
		logger.info("getSecurityQuestions Enty");
		final DAOFactory daoFactory = DAOFactory.getDAOFactory();
		final SecurityQuestionDAO securityQuestionDao = daoFactory.getDAO(SecurityQuestionDAO.class);
		Collection<SecurityQuestionDTO> securityQuestions = securityQuestionDao.getSecurityQuestions();
		logger.info("getSecurityQuestions Exit");
		return securityQuestions;
	}

}