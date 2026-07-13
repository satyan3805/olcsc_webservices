package com.etcc.csc.webservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AuthorizedContactDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.webservice.rest.dto.AuthorizedContactRequest;

@Path("/authorizedContact")
public class AuthorizedContactRest {
	
	private static final Logger logger = Logger.getLogger(AuthorizedContactRest.class);
	
	@POST
	@Path("/setAuthContacts")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public ResultDTO setAuthContacts(AuthorizedContactRequest authorizedContactRequest) throws EtccException, EtccSecurityException {
		
		//AccountLoginDTO acctLoginDto, AuthorizedContactDTO[] authorizedContacts
		final AccountLoginDTO acctLoginDto = authorizedContactRequest.getAcctLoginDto();
		final AuthorizedContactDTO[] authorizedContactDTO = authorizedContactRequest.getAuthorizedContactDTO();

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] AuthorizedContactRequest ["+authorizedContactRequest+"]");

		
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AuthorizedContactDAO authorizedContactDAO = daoFactory.getDAO(AuthorizedContactDAO.class);
        
		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] AuthorizedContactResponse ["+authorizedContactDAO+"]");
		
        return authorizedContactDAO.setAuthContacts(acctLoginDto, authorizedContactDTO);
	}
}
