/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.LovDTO;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface LovInterface extends ServiceInterface {
    /**
     * Retrieves the List Of Values (LOV) corresponding to the specified name.
     * Typically used as a list of drop-down items.
     * @param lovName name of the requested List Of Values
     * @return the List of Values (Collection of LovDTO)
     */
    public Collection<LovDTO> getLov(String lovName) throws EtccException, EtccSecurityException;

    public static String MSTAT_SORT_ACCT_SUM_BY = "mstatb";
    public static String MSTAT_SORT_TAG_SUM_BY = "mstatc";
    public static String MSTAT_SORT_STMT_DETAILS_BY = "mstatd";
}
