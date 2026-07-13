package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.VelcroDTO;
import com.etcc.csc.service.VelcroInterface;

import java.util.Collection;


/**
 * Wrapper between the web service stub and the web service client.
 */
public class VelcroDelegate extends Delegate implements VelcroInterface{

    VelcroInterface vi = (VelcroInterface)getServiceObject(ServiceObjectEnum.VELCRO);
    public VelcroDelegate() {
        super(VehicleDelegate.class);
    }

    public VelcroDTO getVelcroInfo(AccountLoginDTO acctLoginDto) 
        throws EtccException, EtccSecurityException {
        
        try {
        
            return vi.getVelcroInfo(acctLoginDto);
            
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getVelcroInfo: " + t, t);
        }
    }

    public Collection submitVelcroRequest(AccountLoginDTO acctLoginDto, int qty) 
        throws EtccException, EtccSecurityException {
        
        try {
            
            
            return vi.submitVelcroRequest(acctLoginDto, qty);
            
            
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running submitVelcroRequest: " + t, 
                t);
        }
    }
    
    public String getVelcroReceiptPDF(AccountLoginDTO acctLoginDto)
        throws EtccException, EtccSecurityException {
    
    try {
                   
        return vi.getVelcroReceiptPDF(acctLoginDto);
        
    } catch (EtccSecurityException se) {
        logger.error(se);
        throw new EtccSecurityException(se);
    } catch (Throwable t) {
        logger.error(t);
        throw new EtccException("Error running getVelcroReceiptPDF: " + t, t);
    }
    }
    
}
