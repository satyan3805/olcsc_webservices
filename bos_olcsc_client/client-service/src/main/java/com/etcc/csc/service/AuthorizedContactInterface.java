/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;

@Local
public interface AuthorizedContactInterface extends ServiceInterface {
    /**
     * Returns an array of authorized contacts for the specified account.
     * @param accountLogin
     * @return array of authorized contacts
     * @throws EtccException
     * @throws EtccSecurityException
     */
    AuthorizedContactDTO[] getAuthContacts(AccountLoginDTO accountLogin) throws EtccException, EtccSecurityException;

    /**
     * Modify the authorized contacts for the specified account.
     * Performs an update, create, or delete as specified on each contact.
     * @param accountLogin
     * @param authorizedContacts
     * @param password
     * @return result object for any response data as appropriate
     * @throws EtccException
     * @throws EtccSecurityException
     */
    ResultDTO modifyAuthContacts(AccountLoginDTO accountLogin, AuthorizedContactDTO[] authorizedContacts, String password) throws EtccException, EtccSecurityException;
}
