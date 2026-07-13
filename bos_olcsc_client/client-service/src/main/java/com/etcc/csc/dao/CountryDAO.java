/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.CountryDTO;
import com.etcc.csc.service.CountryInterface;

/**
 * Copied from OLCSCService.  Current implementation cache's the countries until the first hit after midnight.
 * @author Stephen Davidson
 *
 */
//TODO: JMX Bean - load from cache.
public abstract class CountryDAO extends BaseDAO implements CountryInterface{
    private static final Logger logger = Logger.getLogger(CountryDAO.class);
    
    /**
     * Map of Country name, Country DTO.  Used to look up Country by name.
     */
    private static final Map<String, CountryDTO> countriesByCountry = new TreeMap<String, CountryDTO>();
    
    private static Date lastLoad;
    private static Date expires;

    protected abstract Collection<CountryDTO> loadCountries() throws EtccException;
    
    protected static CountryDTO getCountryByName(final String name) throws EtccException{
        if (countriesByCountry.size() == 0){
            DAOFactory.getDAOFactory().getDAO(CountryDAO.class).getCountries();
        }
        final CountryDTO countryDTO = countriesByCountry.get(name);
        return countryDTO == null ? new CountryDTO() : countryDTO;
    }
    
    /** 
     * @see com.etcc.csc.service.CountryInterface#getCountries()
     */
    public final Collection<CountryDTO> getCountries() throws EtccException {
        expires = checkAndClearCache(expires, countriesByCountry);
        if (countriesByCountry.size() == 0){
            //empty
            if (logger.isTraceEnabled()){
                logger.trace("Country DAO: Cache was last loaded: " + lastLoad);
            }
            for (CountryDTO country : loadCountries()) {
                countriesByCountry.put(country.getCountry(), country);
            }
            lastLoad = new Date();
        }
        return countriesByCountry.values();
        //end getCountries
    }
  
}
