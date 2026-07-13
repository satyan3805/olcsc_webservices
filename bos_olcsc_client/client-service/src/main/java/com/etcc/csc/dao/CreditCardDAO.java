package com.etcc.csc.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.service.CreditCardInterface;

/**
 * 
 * @author Stephen Davidson
 */
//TODO: JMX Bean -- should be cached, with a VERY low refresh rate.
public abstract class CreditCardDAO extends BaseDAO implements CreditCardInterface {
    private static final Logger logger = Logger.getLogger(CreditCardDAO.class);
    
    private static final Collection<CreditCardDTO> types = new ArrayList<CreditCardDTO>(0);
    private static Date lastLoad;
    private static Date expires;
    
    protected abstract Collection<CreditCardDTO> loadCreditCardTypes() throws EtccException;
    
    public Collection<CreditCardDTO> getCreditCardTypes() throws EtccException {
        expires = checkAndClearCache(expires, types);
        if (types.isEmpty()){
            if (logger.isTraceEnabled()){
                logger.trace("Last load: " + lastLoad);
            }
            lastLoad = new Date();
            types.addAll(loadCreditCardTypes());
        }
        return types;
    }

}
