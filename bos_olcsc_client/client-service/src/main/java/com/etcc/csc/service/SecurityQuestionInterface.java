/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Collection;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.SecurityQuestionDTO;

/**
 * Defines methods that need to be implemented by the Security Question classes.
 */
// Based on the original implementation from OLCSCService.
@Local
public interface SecurityQuestionInterface extends ServiceInterface {
    /**
     * Retrieves the collection of Security Questions.
     * @return The Security Questions for the site.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    Collection<SecurityQuestionDTO> getSecurityQuestions() throws EtccException, EtccSecurityException;
}
