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
import com.etcc.csc.dao.SessionDAO;


@Path("/session")
public class SessionRest {
	
	private static final Logger logger = Logger.getLogger(SessionRest.class);
	
	@POST
	@Path("/destroySession")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public void destroySession(String dbSessionId) throws EtccException, EtccSecurityException {
		logger.info("destroySession Enty");
		SessionDAO sessionDao = DAOFactory.getDAOFactory().getDAO(SessionDAO.class);
		sessionDao.destroySession(dbSessionId);
		logger.info("destroySession Exit");
	}
	
	
}