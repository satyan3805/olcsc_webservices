/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.SessionDTO;

/**
 * Methods for managing user session.
 */
//Copied from com.etcc.csc.service.SessionInterface in OLCSCService module.
@Local
public interface SessionInterface extends ServiceInterface {
    /**
     * Creates a database session for a user.
     * @param sessionDto
     * @return A db-generated session id.
     * @throws EtccException
     */
    String makeSession(SessionDTO sessionDto) throws EtccException;

    /**
     * Destroys the DB session for the given dbSessionId.
     * @param dbSessionId
     * @throws EtccException
     */
    void destroySession(String dbSessionId) throws EtccException;
}
