/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.service.AppInterface;
import com.etcc.csc.service.AuthorizedContactInterface;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.util.StringUtil;

public class AuthorizedContactDelegate implements AuthorizedContactInterface {
    private static final Logger logger = Logger.getLogger(AuthorizedContactDelegate.class);

    /**
     * @see AppInterface#getAuthContactLimit()
     * @return the int limit on number of authorized contacts
     */
    public int getAuthContactLimit() {
        try {
            int limit = appStub().getAuthContactLimit();
            return limit;
        } catch (Throwable t) {
            String msg = t.getMessage();
            logger.debug(msg, t);
        }
        return -1;
    }

    public AuthorizedContactDTO[] getAuthContacts(AccountLoginDTO acctLoginDto) throws EtccException,
            EtccSecurityException {
        logger.trace("Start AuthorizedContactDelegate.getAuthContacts()");
        AuthorizedContactDTO[] authContacts = stub().getAuthContacts(acctLoginDto);
        if (logger.isDebugEnabled())
            logger.debug("Number of Auth Contacts in getAuthContacts() webservice: "
                    + (authContacts == null ? "null" : String.valueOf(authContacts.length)));
        return authContacts;
    }

    public ResultDTO modifyAuthContacts(AccountLoginDTO accountLogin, AuthorizedContactDTO[] authorizedContacts,
            String password) throws EtccException, EtccSecurityException {
        logger.trace("Start AuthorizedContactDelegate.modifyAuthContacts() ");
        ResultDTO resultDTO = stub().modifyAuthContacts(accountLogin, authorizedContacts, password);
        if (logger.isDebugEnabled()) {
            logger.debug("Auth Contacts information " + resultDTO.getErrors() + " " + resultDTO.getDateModified());
        }
        return resultDTO;
    }

    /**
     * @see #modifyAuthContacts(AccountLoginDTO, AuthorizedContactDTO[], String)
     */
    public ResultDTO deleteAuthorizedContactsList(AccountLoginDTO acctLoginDto, List<AuthorizedContactDTO> deleteAuthList,
            String password) throws EtccException, EtccSecurityException {
        AuthorizedContactDTO[] authArr = new AuthorizedContactDTO[deleteAuthList.size()];
        boolean traceEnabled = logger.isTraceEnabled();
        for (int i = 0; i < deleteAuthList.size(); i++) {
            AuthorizedContactDTO authConts = deleteAuthList.get(i);
            String contactId = StringUtil.safeToString(authConts.getContactId());
            if (contactId != null && authConts.isValid()) {
                AuthorizedContactDTO authCont = authConts.clone();
                authCont.setContactId(contactId);
                authCont.setActionDelete();

                if (traceEnabled)
                    logger.trace("Before calling modifyAuth Web service " + authCont.getAction() + " "
                        + contactId + " " + authCont.getFirstName() + " " + authCont.getLastName() + " "
                        + authCont.getPassword());
                authArr[i] = authCont;
            }
        }
        return modifyAuthContacts(acctLoginDto, authArr, password);
    }

    /**
     * @see #modifyAuthContacts(AccountLoginDTO, AuthorizedContactDTO[], String)
     */
    public ResultDTO modifyAuthorizedContactsList(AccountLoginDTO acctLoginDto, List<AuthorizedContactDTO> modifiedAuthList,
            String password) throws EtccException, EtccSecurityException {
        AuthorizedContactDTO[] authArr = new AuthorizedContactDTO[modifiedAuthList.size()];
        boolean traceEnabled = logger.isTraceEnabled();
        for (int i = 0; i < modifiedAuthList.size(); i++) {
            AuthorizedContactDTO authConts = modifiedAuthList.get(i);
            if (authConts != null && authConts.isValid()) {
                AuthorizedContactDTO authCont = authConts.clone();
                authCont.setContactId(StringUtil.safeToString(authConts.getContactId()));
                authCont.setActionModify();
                authArr[i] = authCont;

                if (traceEnabled) {
                    logger.trace("Before calling modifyAuth Web service " + authCont.getAction() + " "
                            + authCont.getContactId() + " " + authCont.getFirstName() + " "
                            + authCont.getLastName() + " " + authCont.getPassword());
                }
            }
        }
        return modifyAuthContacts(acctLoginDto, authArr, password);
    }

    private AuthorizedContactInterface stub() {
        return ServiceFactory.getImplementation(AuthorizedContactInterface.class);
    }

    private AppInterface appStub() {
        return ServiceFactory.getImplementation(AppInterface.class);
    }
}
