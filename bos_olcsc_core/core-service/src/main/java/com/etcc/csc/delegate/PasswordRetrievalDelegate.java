package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.service.PasswordRetrievalInterface;

import java.util.Collection;


public class PasswordRetrievalDelegate extends Delegate implements PasswordRetrievalInterface {
    
    PasswordRetrievalInterface pri = (PasswordRetrievalInterface)getServiceObject(ServiceObjectEnum.PASSWORD_RETRIEVAL);
    
    public PasswordRetrievalDelegate() {
        super(PasswordRetrievalDelegate.class);
    }

    public OnlineAccessSetupDTO retrieveAccountInfo(OnlineAccessSetupDTO onlineAccessDTO, 
        String ipAddress, String sessionId, UserEnvDTO userEnvDto, String locale) throws EtccException{
        try {
                        
            return pri.retrieveAccountInfo(onlineAccessDTO, ipAddress, sessionId, 
                                        userEnvDto, locale);
                        
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running retrieveAccountInfo: " + t, t);
        }
    }
    
    public boolean validateSecurityAnswer(String sessionId, 
                                          String ipAddress, 
                                          OnlineAccessSetupDTO onlineAccessDTO) throws EtccException{
        try {
            
            return pri.validateSecurityAnswer(sessionId, ipAddress, onlineAccessDTO);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running validateSecurityAnswer: " + t, t);
        }
    }
    
    public boolean addSecurityQuestionAnswer(String sessionId, String ipAddress, OnlineAccessSetupDTO onlineAccessDTO) throws EtccException {
        try{
            
            return pri.addSecurityQuestionAnswer(sessionId, ipAddress, onlineAccessDTO);
            
        }catch (Throwable t) {
            logger.error(t);
            throw new EtccException("error adding security question and answer: " +t, t);
        }
    }
    
    public boolean addEmailAddress(String sessionId, String ipAddress, OnlineAccessSetupDTO onlineAccessDTO) throws EtccException {
        try{
            
            return pri.addEmailAddress(sessionId, ipAddress, onlineAccessDTO);
            
        }catch (Throwable t) {
            logger.error(t);
            throw new EtccException("error adding email address: "+t, t);
        }
    }
    
    public Collection changePassword(AccountLoginDTO acctLoginDTO, String oldPwd, String pwd) throws EtccException, EtccSecurityException {
        try{
            return pri.changePassword(acctLoginDTO, oldPwd, pwd);
        }catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        }
        catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running changePassword: " + t, t);
        }
    }
    
   
    public Collection resetPassword(AccountLoginDTO acctLoginDTO,  String pwd) throws EtccException, EtccSecurityException {
        try{
            return pri.resetPassword(acctLoginDTO,pwd);
        }catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        }
        catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running changePassword: " + t, t);
        }
    }
    
    

    
}
