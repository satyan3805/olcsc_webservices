package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SessionDTO;
import com.etcc.csc.service.SessionInterface;


/**
 * Wrapper between the web service stub and the web service client.
 */
public class SessionDelegate extends Delegate implements SessionInterface{
    
    SessionInterface si = (SessionInterface)getServiceObject(ServiceObjectEnum.SESSION);
        
    public SessionDelegate() {
        super(SessionDelegate.class);
    }

    public String makeSession(SessionDTO sessionDto) throws EtccException {

        try {
            
            return si.makeSession(sessionDto);
                        
        } catch (Exception e) {
            logger.error(e);
            throw new EtccException(e);
        }
    }

    public void destroySession(String dbSessionId) throws EtccException {

        try {
            
            si.destroySession(dbSessionId);
        } catch (Exception e) {
            logger.error(e);
            throw new EtccException(e);
        }

    }
    
    public void updateSessionLanguage(AccountLoginDTO acctLoginDto, 
                                    String lang) throws EtccException {

        try {
           si.updateSessionLanguage(acctLoginDto, lang);
        } catch (Exception e) {
            logger.error(e);
            throw new EtccException(e);
        }

    }
}
