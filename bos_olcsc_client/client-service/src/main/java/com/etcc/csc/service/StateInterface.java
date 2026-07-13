/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.StateDTO;

/**
 * Defines methods that need to be implemented by the State classes.
 */
@Local
// Copied from com.etcc.csc.service.StateInterface in OLCSCService module.
public interface StateInterface extends ServiceInterface {

    /**
     * Retrieves the collection of states for the default country.
     * 
     * @return collection of states (provinces) for the default country
     * @throws EtccException thrown if any exceptions occur loading the States from the Database.
     */
    StateDTO[] getStates() throws EtccException;
}
