/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.etcc.csc.dao.dummy.DummyStateDAO;
import com.etcc.csc.dto.StateDTO;

/**
 * @author Stephen Davidson
 *
 */
public class StateDAOTest {

    /**
     * Test method for {@link com.etcc.csc.dao.StateDAO#getStates()}.
     * @throws Exception if any exceptions occur.
     */
    @Test
    public void testGetStates() throws Exception {
        StateDAO dao = new DummyStateDAO();
        StateDTO[] states = dao.getStates();
        assertNotNull("States not loaded.", states);
        assertTrue("States are empty.", states.length > 0);
    }

}
