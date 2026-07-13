package com.etcc.csc.delegate;

import com.etcc.csc.common.CacheAdmin;
import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.DelegateEnum;
import com.etcc.csc.common.DelegateFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.service.CountryInterface;
import com.etcc.csc.service.StateInterface;

import java.util.Collection;


/**
 * Wrapper between the web service stub and the web service client.
 */
public class StateDelegate extends Delegate implements StateInterface{
    private static final String STATES = "states";
    
    StateInterface state = (StateInterface)getServiceObject(ServiceObjectEnum.STATE);
    
    public StateDelegate() {
        super(StateDelegate.class);
    }

    public Collection getStates() throws EtccException {
        CacheAdmin admin = CacheAdmin.getInstance();
        Collection states = (Collection) admin.getFromCache(STATES);
        try {            
            if (states == null) {
                 states = state.getStates();
               	 admin.putInCache(STATES, states);                           
            }
            return states;
        } catch (Throwable t) {
            logger.equals(t);
            admin.cancelUpdate(STATES);
            throw new EtccException("Error running getStates: " + t, t);
        }
    }

	public Collection getStatesByCountry(String countryCode)
			throws EtccException, EtccSecurityException {
		Collection states = null;
		CountryInterface cntry = (CountryInterface)DelegateFactory.create(DelegateEnum.COUNTRY_DELEGATE);
        String defaultCountry = cntry.getDefaultCountryCode();
        try {
            if (countryCode==null) 
            	countryCode = defaultCountry;
           states = state.getStatesByCountry(countryCode);
               
            return states;
        } catch (Throwable t) {
            logger.equals(t);            
            throw new EtccException("Error running getStates: " + t, t);
        }
	}
    
    
}
