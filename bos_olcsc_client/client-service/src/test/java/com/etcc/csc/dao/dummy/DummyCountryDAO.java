/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import java.util.Collection;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.CountryDAO;
import com.etcc.csc.dto.CountryDTO;
import com.etcc.csc.service.CountryFactory;

/**
 * @author Stephen Davidson
 *
 */
public class DummyCountryDAO extends CountryDAO {

    /** 
     * @see com.etcc.csc.dao.CountryDAO#loadCountries()
     */
    @Override
    protected Collection<CountryDTO> loadCountries() throws EtccException {
        return CountryFactory.getCountries();
        //end loadCountries
    }

}
