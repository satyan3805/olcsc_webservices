package com.etcc.csc.webservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountPreferenceDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;
import com.etcc.csc.webservice.rest.dto.AccountPreferenceRequest;

@Path("/accountPreference")
public class AccountPreferenceRest {

	private static final Logger logger = Logger.getLogger(AccountPreferenceRest.class);

	@POST
	@Path("/setPreferences")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountPreferencesDTO setPreferences(AccountPreferenceRequest accountPreferenceRequest)
			throws EtccException, EtccSecurityException {

		logger.info("AcctId [" + accountPreferenceRequest.getAcctLoginDto().getAcctId() + "] LoginId ["
				+ accountPreferenceRequest.getAcctLoginDto().getLoginId() + "] setPreferencesRequest ["
				+ accountPreferenceRequest + "]");

		final AccountLoginDTO acctLoginDto = accountPreferenceRequest.getAcctLoginDto();
		final AccountPreferencesDTO accountPreferencesDTO = accountPreferenceRequest.getAccountPreferencesDTO();

		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		AccountPreferenceDAO accountPreferenceDAO = daoFactory.getDAO(AccountPreferenceDAO.class);

		logger.info("AcctId [" + accountPreferenceRequest.getAcctLoginDto().getAcctId() + "] LoginId ["
				+ accountPreferenceRequest.getAcctLoginDto().getLoginId() + "] setPreferencesResponse ["
				+ accountPreferenceDAO + "]");

		return accountPreferenceDAO.setPreferences(acctLoginDto, accountPreferencesDTO);
	}

}
