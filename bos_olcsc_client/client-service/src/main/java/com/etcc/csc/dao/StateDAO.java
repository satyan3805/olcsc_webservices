package com.etcc.csc.dao;

import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.service.StateInterface;


/**
 * DAO for the states. This implementation caches the states for one month before reloading.
 * @author unknown
 * @author Stephen Davidson
 */
//TODO: JMX Bean - load from cache.
public abstract class StateDAO extends BaseDAO implements StateInterface {
    private static final Logger logger = Logger.getLogger(StateDAO.class);

    private static Date lastLoad;
    private static Date expires = new Date(); //Expire immediately.
    
    private static StateDTO[] states;
    
    protected abstract StateDTO[] loadStates() throws EtccException;
    
    public final StateDTO[] getStates() throws EtccException{
        checkAndClearCache();
        if (states == null){
            logger.info("Last load: " + 
                    (lastLoad == null ? "never loaded" : DateFormat.getInstance().format(lastLoad)));
            states = loadStates();
        }
        return states;
    }
    
    private void checkAndClearCache(){
        Date currentDate = new Date();
        if (checkExpires(currentDate, expires)){
            synchronized(StateDAO.class){
                if (!checkExpires(currentDate, expires)){
                    //Another thread already reset the cache, so ABORT.
                    return;
                }//else
                states = null;
                expires = getNextExpire();
            }
            logger.trace("Cache cleared.");
        }
    }


}
