/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.SessionDTO;

/**
 * Creates a test implementation of <tt>SessionInterface</tt>.
 * @author Stephen Davidson
 * 
 */
public class SessionFactory {
    
    /**
     * The id of the Database session used for testing.
     */
    public static final String DB_SESSION_ID = "db987654321";

    // Package level. The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final SessionInterface mocked) {
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations() {
                {
                    allowing(mocked).makeSession(with(any(SessionDTO.class)));
                    will(returnValue(DB_SESSION_ID));
                    allowing(mocked).destroySession(with(any(String.class)));
                }
            });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
