package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.DelegateInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.service.ViolationInterface;


/**
 * Wrapper between the web service stub and the web service client.
 */
public class ViolationDelegate extends Delegate implements ViolationInterface{

    ViolationInterface vi = (ViolationInterface)getServiceObject(ServiceObjectEnum.VIOLATION);
    
    
    public ViolationDelegate() {
        super(ViolationDelegate.class);
    }

    /**
     * Checks for the existence of an invoice in the database.
     * @param invoiceId
     * @param licPlateNbr
     * @param licPlateState
     * @return
     * @throws EtccException
     */
    public AccountLoginDTO loginViolator(String sessionId, String ipAddress, 
            UserEnvDTO userEnvDto, String invoiceId, String collectionsId, 
            String licPlate, String licState) throws EtccException {
        try {
            
            return vi.loginViolator(sessionId, ipAddress,
                userEnvDto, invoiceId, collectionsId, licPlate, 
                licState);
            
            
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running loginViolator: " + t, t);
        }
    }

    public AccountLoginDTO loginZipCash(String sessionId, String ipAddress, 
            UserEnvDTO userEnvDto, String invoiceId, String acctId, 
            String licPlate, String licState) throws EtccException {
        try {
            
            return vi.loginZipCash(sessionId, ipAddress,
                userEnvDto, invoiceId, acctId, licPlate, 
                licState);
            
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running loginZipCash: " + t, t);
        }
    }
    public ViolatorDTO getViolations(AccountLoginDTO acctLoginDto, 
        String invoiceId, String collectionsId, String licPlate, 
        String licState) throws EtccException, EtccSecurityException {

        try {
            
            return vi.getViolations(acctLoginDto, 
                invoiceId, collectionsId, licPlate, licState);
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Exception e) {
            logger.error(e);
            throw new EtccException(e);
        }

    }
    
    
}
