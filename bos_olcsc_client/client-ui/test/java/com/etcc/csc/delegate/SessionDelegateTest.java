/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.SessionDTO;
import com.etcc.csc.service.SessionFactory;

/**
 * JUnit tests for the SessionDelegate.
 */
public class SessionDelegateTest {

    private static final String SESSION_ID = "123456789";
    SessionDelegate delegate;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseTestDelegate.setUpBeforeClass();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.delegate = new SessionDelegate();
    }
    
    /**
     * Tests for {@link SessionDelegate#makeSession(SessionDTO)}.
     * @throws Exception if any errors occur during test.
     */
    @Test
    public void testMakeSession() throws Exception {
        SessionDTO sessionDto = new SessionDTO();
        sessionDto.setActivationDate(Calendar.getInstance());
        sessionDto.setIpAddress("127.0.0.1");
        sessionDto.setSessionId(SESSION_ID);
        String dbSessionId = this.delegate.makeSession(sessionDto);
        assertNotNull(dbSessionId);
        assertEquals(SessionFactory.DB_SESSION_ID, dbSessionId);
    }

    /**
     * Test for {@link SessionDelegate#destroySession(String)}
     * @throws Exception if any errors occur during test.
     */
    @Test
    public void testDestroySession() throws Exception{
        this.delegate.destroySession(SessionFactory.DB_SESSION_ID);
    }

}
