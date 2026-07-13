/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.EmailValidationDataDTO;
import com.etcc.csc.dto.ResultDTO;

@Local
public interface EmailValidationInterface extends ServiceInterface {

    public static final int VDST_NONE = -1;
    public static final int VDST_INVALID_ID = 0;
    public static final int VDST_VALIDATION_REQUIRED = 1;
    public static final int VDST_VALIDATION_DONE = 2;
    public static final int VDST_OBSOLETE = 3;
    
    EmailValidationDataDTO validationData(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException;
    ResultDTO validationStatus(AccountLoginDTO acctLogin) throws EtccSecurityException, EtccException;
    ResultDTO setValidationDone(AccountLoginDTO acctLogin) throws EtccException;
    ResultDTO generateEmailValidationMsg(AccountLoginDTO acctLogin) throws EtccException;
}
