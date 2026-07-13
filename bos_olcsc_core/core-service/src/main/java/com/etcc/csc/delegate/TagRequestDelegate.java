package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.service.TagRequestInterface;


public class TagRequestDelegate extends Delegate implements TagRequestInterface{

    TagRequestInterface tri = (TagRequestInterface)getServiceObject(ServiceObjectEnum.TAG_REQUEST);
    
    public TagRequestDelegate() {
        super(TagRequestDelegate.class);
    }
    
    public TagDTO addTag(String sessionId, String ipAddress, String userId, TagDTO tagRequestDto, Long posId) throws EtccException, EtccSecurityException {
        try {
            
            return tri.addTag(sessionId, ipAddress, userId, tagRequestDto, posId);
            
        }catch (EtccSecurityException se) {
            throw new EtccSecurityException(se);
        }
        catch (Throwable t) {
            logger.equals(t);
            throw new EtccException("Error running addTag: " + t, t);
        }    
    }
    
    public TagDTO modifyTag(String sessionId, String ipAddress, String userId, TagDTO tagRequestDto, String transType, Long posId)throws EtccException, EtccSecurityException{
        try {
            
            return tri.modifyTag(sessionId, ipAddress, userId, tagRequestDto, transType, posId);
        } catch (EtccSecurityException se) {
            throw new EtccSecurityException(se);
        }
        catch (Throwable t) {
            logger.equals(t);
            throw new EtccException("Error running addTag: " + t, t);
        }        
    }
    
    public boolean confirmAddTags(String sessionId, String ipAddress, 
                                  String userId, String acctId, 
                                  long transactionId) throws EtccSecurityException, EtccException {
        try
        {
            
            return tri.confirmAddTags(sessionId, ipAddress, userId, acctId, transactionId);
            
        } catch (EtccSecurityException se) {
            logger.equals(se);
            throw new EtccSecurityException(se);
        }
        catch (Throwable t) {
            logger.equals(t);
            throw new EtccException("Error running confirmAddTags: " + t, t);
        }       
    }
    
   
    public boolean confirmAddTagsTwo(String sessionId, String ipAddress, 
                                  String userId, String acctId, 
                                  long transactionId) throws EtccSecurityException, EtccException {
        try
        {
            
            return tri.confirmAddTagsTwo(sessionId, ipAddress, userId, acctId, transactionId);
            
        } catch (EtccSecurityException se) {
            logger.equals(se);
            throw new EtccSecurityException(se);
        }
        catch (Throwable t) {
            logger.equals(t);
            throw new EtccException("Error running confirmAddTags: " + t, t);
        }       
    }
    
    public String addTagsReceipt(String sessionId, String ipAddress, 
                                 String userId, String acctId, 
                                 String reportFormat) throws EtccException, EtccSecurityException{
        try
        {
            
            return tri.addTagsReceipt(sessionId, ipAddress, userId, acctId, reportFormat);
            
        } catch (EtccSecurityException se) {
            logger.equals(se);
            throw new EtccSecurityException(se);
        }
        catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running addTagsReceipt: " + t, t);
        }       
    }
    
    public int activateTag(AccountLoginDTO loginDto, TagDTO tagRequestDTO)
        throws EtccException, EtccSecurityException{
        
        try
        {
            return tri.activateTag(loginDto, tagRequestDTO);
        } catch (EtccSecurityException se) {
            logger.equals(se);
            throw new EtccSecurityException(se);
        }
        catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running activateTag: " + t, t);
        }           
    }
}
