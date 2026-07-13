/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.dao.dummy;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.SessionDAO;
import com.etcc.csc.dto.SessionDTO;
import com.etcc.csc.service.SessionFactory;

/**
 * Dummy DAO for the Session Service.
 * @author Stephen Davidson
 *
 */
public class DummySessionDAO extends SessionDAO {

    public void destroySession(String dbSessionId) throws EtccException {
        //Nothing to do.
    }

    public String makeSession(SessionDTO sessionDto) throws EtccException {
        return SessionFactory.DB_SESSION_ID;
    }
}
