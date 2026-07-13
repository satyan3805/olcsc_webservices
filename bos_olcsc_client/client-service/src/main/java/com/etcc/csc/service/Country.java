/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.CountryDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.CountryDTO;

/**
 * Copied from OLCSCService com.etcc.csc.service.Country.  Returns the DTOs representing various Countries.
 * @author Stephen Davidson
 */
@Stateless(name="com/etcc/Country")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Country", targetNamespace = "http://ws.csc.etcc.com/Country")
public class Country implements CountryInterface {

    //@WebMethod(operationName = "getCountries", action = "urn:getCountries")
    //@WebResult(name = "countries")
    public Collection<CountryDTO> getCountries() throws EtccException{
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		CountryDAO countryDao = daoFactory.getDAO(CountryDAO.class);
		Collection<CountryDTO> col = countryDao.getCountries();
		return col;
    }

//    public static String getCountryCode(String countryName) throws EtccException {
//        if ((countryName != null) && (countryName.trim().length() > 0)) {
//            Country obj = new Country();
//            Collection<CountryDTO> countries = obj.getCountries();
//            for (CountryDTO country : countries) {
//                if (country.getCountry().equalsIgnoreCase(countryName)) {
//                    return country.getCountryCode();
//                }
//            }
//        }
//        return "";
//    }
}
