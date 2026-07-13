/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.util.Collection;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.service.SecurityQuestionInterface;
import com.etcc.csc.service.ServiceFactory;

/**
 * Delegate for getting the Security Questions.
 */
public class SecurityQuestionDelegate implements SecurityQuestionInterface {
    public Collection<SecurityQuestionDTO> getSecurityQuestions() throws EtccException, EtccSecurityException {
        SecurityQuestionInterface sc = ServiceFactory.getImplementation(SecurityQuestionInterface.class);
        return sc.getSecurityQuestions();
    }
}
