package com.etcc.csc.service;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.DelegateEnum;
import com.etcc.csc.common.DelegateFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.StateDAO;
import com.etcc.csc.dto.StateDTO;

public class State implements StateInterface {
	private Logger logger = Logger.getLogger(State.class);

	public State() {
	}

	public Collection getStates() throws EtccException, EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			StateDAO stateDao = daoFactory.getStateDAO();
			Collection col = stateDao.getStates();
			return col;
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getStates() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Exception in getStates() " + ee, ee);
			throw ee;
		}
	}

	/**
	 * Stub only to get StateDTO included in the WSDL.
	 * 
	 * @return
	 */
	public StateDTO getState() {
		return null;
	}

	public Collection getStatesByCountry(String countryCode)
			throws EtccException, EtccSecurityException {
		CountryInterface cntry = (CountryInterface) DelegateFactory
				.create(DelegateEnum.COUNTRY_DELEGATE);
		String defaultCountry = cntry.getDefaultCountryCode();
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		StateDAO stateDao = daoFactory.getStateDAO();
		if (countryCode == null)
			countryCode = defaultCountry;
		Collection col = stateDao.getStatesByCountry(countryCode);
		return col;
	}
}