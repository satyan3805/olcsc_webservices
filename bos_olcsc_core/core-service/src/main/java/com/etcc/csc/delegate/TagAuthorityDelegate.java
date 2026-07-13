package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.service.TagAuthorityInterface;

import java.util.Collection;


/**
 * Wrapper between the web service stub and the web service client.
 */
public class TagAuthorityDelegate extends Delegate implements TagAuthorityInterface{

    TagAuthorityInterface tai = (TagAuthorityInterface)getServiceObject(ServiceObjectEnum.TAG_AUTHORITY);
    public TagAuthorityDelegate() {
        super(TagAuthorityDelegate.class);
    }


    public Collection getTagAuthorities() throws EtccException, 
                                                 EtccSecurityException {

        try {
            
            return tai.getTagAuthorities();
            
        } catch (EtccSecurityException se) {
            logger.equals(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.equals(t);
            throw new EtccException("Error running invoiceExist: " + t, t);
        }
    }

    public String getTagApplicationAgreement(String lang) throws EtccException {
        try {
            return tai.getTagApplicationAgreement(lang);


        } catch (Throwable t) {
            logger.equals(t);
            throw new EtccException("Error running getTagApplicationAgreement: " + 
                                    t, t);
        }

    }
}
