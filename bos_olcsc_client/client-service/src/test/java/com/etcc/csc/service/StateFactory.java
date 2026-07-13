/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.StateDTO;

/**
 * Creates a test implementation of <tt>StateInterface</tt>.
 * @author Stephen Davidson
 * 
 */
public class StateFactory {

    // Package level. The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final StateInterface mocked) {
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations() {
                {
                    allowing(mocked).getStates();
                    will(returnValue(getStates()));

                }
            });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static final String[][] states = new String[][] { { "CA", "California", "N" }, { "TX", "Texas", "Y" },
            { "VA", "Virginia", "N" } };

    static StateDTO[] getStates() {
        int length = states.length;
        StateDTO[] result = new StateDTO[length];
        for (int s = 0; s < length; s++) {
            StateDTO state = new StateDTO();
            state.setStateCode(states[s][0]);
            state.setStateName(states[s][1]);
            if (states[s][2].charAt(0) == 'Y')
                state.setDefaultValueFlag(true);
            else
                state.setDefaultValueFlag(false);
            result[s] = state;
        }
        return result;
    }

}
