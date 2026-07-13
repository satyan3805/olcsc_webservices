/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.StateDAO;
import com.etcc.csc.dto.StateDTO;

/**
 * @author Stephen Davidson
 *
 */
public class DummyStateDAO extends StateDAO {

    /** 
     * @see com.etcc.csc.dao.StateDAO#loadStates()
     */
    @Override
    protected StateDTO[] loadStates() throws EtccException {
        StateDTO[] states = new StateDTO[4];
        int idx = 0;
        short order = 1;
        StateDTO state = makeState("Texas", "TX", order++);
        state.setDefaultValueFlag(true);
        states[idx++] = state;
        states[idx++] = makeState("Arizona", "AZ", order++);
        states[idx++] = makeState("New Mexico", "NM", order++);
        states[idx++] = makeState("Louisiana", "LA", order++);
        return states;
        //end loadStates

    }
    
    private StateDTO makeState(final String state, final String code, short order){
        final StateDTO dto = new StateDTO(code, state);
        dto.setStateOrder(order);
        return dto;
    }

}
