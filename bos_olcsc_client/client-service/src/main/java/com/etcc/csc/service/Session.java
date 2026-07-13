package com.etcc.csc.service;

import javax.ejb.Stateless;
import javax.jws.WebService;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.SessionDAO;
import com.etcc.csc.dto.SessionDTO;

/**
 * Contains session management methods. Copied from OLCSCService
 * com.etcc.csc.ws.Session, then reworked as part of task 488.
 * @author (task 488) Stephen Davidson
 */
@Stateless(name = "com/etcc/Session")
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Session", targetNamespace = "http://ws.csc.etcc.com/Session")
public class Session implements SessionInterface {

    public String makeSession(SessionDTO sessionDto) throws EtccException {

        SessionDAO sessionDao = DAOFactory.getDAOFactory().getDAO(SessionDAO.class);
        return sessionDao.makeSession(sessionDto);
    }

    public void destroySession(String dbSessionId) throws EtccException {
        SessionDAO sessionDao = DAOFactory.getDAOFactory().getDAO(SessionDAO.class);
        sessionDao.destroySession(dbSessionId);
    }
}
