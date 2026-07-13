package com.etcc.csc.webservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountUtilDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.webservice.rest.dto.AccountUtilRequest;

@Path("/accountUtil")
public class AccountUtilRest {

	private static final Logger logger = Logger.getLogger(AccountUtilRest.class);

	@POST
	@Path("/closeAccount")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public ResultDTO closeAccount(AccountUtilRequest accountUtilRequest) throws EtccException, EtccSecurityException {

		logger.info("AcctId [" + accountUtilRequest.getAcctLoginDto().getAcctId() + "] LoginId ["
				+ accountUtilRequest.getAcctLoginDto().getLoginId() + "] closeAccountRequest [" + accountUtilRequest
				+ "]");

		AccountUtilDAO accountUtilDAO = DAOFactory.getDAOFactory().getDAO(AccountUtilDAO.class);

		logger.info("AcctId [" + accountUtilRequest.getAcctLoginDto().getAcctId() + "] LoginId ["
				+ accountUtilRequest.getAcctLoginDto().getLoginId() + "] closeAccountResponse [" + accountUtilDAO
				+ "]");

		return accountUtilDAO.closeAccount(accountUtilRequest.getAcctLoginDto(), accountUtilRequest.getRefundType());
	}

}