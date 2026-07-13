package com.etcc.csc.webservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.PasswordRetrievalDAO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.validation.PasswordRetrievalValidator;
import com.etcc.csc.webservice.rest.dto.RetrieveAccountInfoRequest;
import com.etcc.csc.webservice.rest.dto.ValidateSecurityAnswerRequest;

@Path("/password")
public class PasswordRetrievalRest {

	private static final Logger logger = Logger.getLogger(PasswordRetrievalRest.class);

	@POST
	@Path("/retrieveAccountInfo")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public OnlineAccessSetupDTO retrieveAccountInfo(RetrieveAccountInfoRequest retrieveAccountInfoRequest)
			throws EtccException, EtccSecurityException {

		final DAOFactory daoFactory = DAOFactory.getDAOFactory();
		final PasswordRetrievalDAO passwordRetrievalDAO = daoFactory.getDAO(PasswordRetrievalDAO.class);

		final OnlineAccessSetupDTO onlineAccessDTO = retrieveAccountInfoRequest.getOnlineAccessDTO();

		logger.info("AcctId ["+onlineAccessDTO.getAcctId()+"] EmailId ["+onlineAccessDTO.getEmailAddress()+"] retrieveAccountInfo ["+retrieveAccountInfoRequest+"]");

		final OnlineAccessSetupDTO retrieveAccountInfo = passwordRetrievalDAO.retrieveAccountInfo(onlineAccessDTO,
				retrieveAccountInfoRequest.getIpAddress(), retrieveAccountInfoRequest.getSessionId(),
				retrieveAccountInfoRequest.getUserEnvDto());

		logger.info("AcctId ["+onlineAccessDTO.getAcctId()+"] EmailId ["+onlineAccessDTO.getEmailAddress()+"] retrieveAccountInfoResponse ["+retrieveAccountInfo+"]");

		return retrieveAccountInfo;
	}

	@POST
	@Path("/validateSecurityAnswer")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public OnlineAccessSetupDTO validateSecurityAnswer(ValidateSecurityAnswerRequest validateSecurityAnswerRequest)
			throws EtccException, EtccSecurityException {

		final String sessionId = validateSecurityAnswerRequest.getSessionId();
		final String ipAddress = validateSecurityAnswerRequest.getIpAddress();
		final OnlineAccessSetupDTO onlineAccessDTO = validateSecurityAnswerRequest.getOnlineAccessDTO();

		logger.info("AcctId ["+onlineAccessDTO.getAcctId()+"] EmailId ["+onlineAccessDTO.getEmailAddress()+"] ValidateSecurityAnswerRequest ["+validateSecurityAnswerRequest+"]");

		PasswordRetrievalValidator.validateSecurityAnswerValidator(validateSecurityAnswerRequest.getSessionId(),
				ipAddress, onlineAccessDTO);

		final DAOFactory daoFactory = DAOFactory.getDAOFactory();
		final PasswordRetrievalDAO passwordRetrievalDAO = daoFactory.getDAO(PasswordRetrievalDAO.class);

		final OnlineAccessSetupDTO validateSecurityAnswer = passwordRetrievalDAO.validateSecurityAnswer(sessionId,
				ipAddress, onlineAccessDTO);

		logger.info("AcctId ["+onlineAccessDTO.getAcctId()+"] EmailId ["+onlineAccessDTO.getEmailAddress()+"] validateSecurityAnswerResponse ["+validateSecurityAnswer+"]");

		return validateSecurityAnswer;
	}

}
