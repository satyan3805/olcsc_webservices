package com.etcc.csc.service;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.CountryDAO;

public class Country implements CountryInterface {
	private Logger logger = Logger.getLogger(Country.class);

	public Country() {
	}

	public Collection getCountry() throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			CountryDAO countryDao = daoFactory.getcountryDAO();
			Collection col = countryDao.getCountry();
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

	public String getDefaultCountryCode() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			CountryDAO countryDao = daoFactory.getcountryDAO();
			String col = countryDao.getDefaultCountryCode();
			return col;
		} catch (EtccException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Exception in getDefaultCountryCode() " + ee, ee);
			throw ee;
		}
	}
}
