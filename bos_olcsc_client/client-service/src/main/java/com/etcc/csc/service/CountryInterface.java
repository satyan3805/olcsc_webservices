package com.etcc.csc.service;

import java.util.Collection;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.CountryDTO;

@Local
//Copied from com.etcc.csc.service.CountryInterface in OLCSCService module.
public interface CountryInterface extends ServiceInterface {
    /**
     * Retrieves the collection of countries from the country table.
     * @return the collection of countries from the country table
     */
    Collection<CountryDTO> getCountries() throws EtccException;
}
