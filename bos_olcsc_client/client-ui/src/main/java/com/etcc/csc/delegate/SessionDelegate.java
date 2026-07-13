/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.SessionInterface;
import com.etcc.csc.dto.SessionDTO;

/**
 * Wrapper between the web service stub and the web service client.
 */
public class SessionDelegate implements SessionInterface {

    public String makeSession(SessionDTO sessionDto) throws EtccException {
        return ServiceFactory.getImplementation(SessionInterface.class).makeSession(sessionDto);
    }

    public void destroySession(String dbSessionId) throws EtccException {
        ServiceFactory.getImplementation(SessionInterface.class).destroySession(dbSessionId);
    }
}
