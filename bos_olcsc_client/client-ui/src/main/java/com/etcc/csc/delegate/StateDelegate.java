package com.etcc.csc.delegate;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.StateInterface;
import com.etcc.csc.util.CacheAdmin;

/**
 * Wrapper between the service stub and the service client.
 */
public class StateDelegate implements StateInterface {
    private static final Logger logger = Logger.getLogger(StateDelegate.class);
    private static final String STATES = "states";

    public StateDTO[] getStates() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            StateDTO[] states = (StateDTO[]) admin.getFromCache(STATES);
            if (states == null) {
                 try {
                    states = ServiceFactory.getImplementation(StateInterface.class).getStates();
                    admin.putInCache(STATES, states);
                 } catch (Exception ex) {
                    logger.error("Failed to retrieve states: " + ex.getMessage(), ex);
                    admin.cancelUpdate(STATES);
                 }            
            }
            return states;
        } catch (Throwable t) {
            logger.error("getStates: " + t.getMessage(), t);
                throw new EtccException(t.getMessage());
        }
    }

    public static void main(String arg[])throws EtccException, EtccSecurityException {
        StateDelegate deleg = new StateDelegate();
        StateDTO[] col=null;
        try{
        	col = deleg.getStates();
        }catch (EtccException ee) {
        //logger.error("Exception in getAccountCreditCards() " + ee, ee);
        throw ee;
        }
        logger.info("States: " + Arrays.toString(col));
    }
}
