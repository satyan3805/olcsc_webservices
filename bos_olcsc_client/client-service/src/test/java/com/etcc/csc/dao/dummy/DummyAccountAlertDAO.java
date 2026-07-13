/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountAlertDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.service.AccountFactory;

/**
 * @author Stephen Davidson
 *
 */
public class DummyAccountAlertDAO extends AccountAlertDAO {

    /** 
     * @see com.etcc.csc.service.AccountAlertInterface#addressCleanseAlertResponse(com.etcc.csc.dto.AccountLoginDTO, java.lang.String)
     */
    public ResultDTO addressCleanseAlertResponse(AccountLoginDTO acctLoginDto, String cleanseAlertResponse)
            throws EtccException, EtccSecurityException {
        return null;
        //end addressCleanseAlertResponse

    }

    /** 
     * @see com.etcc.csc.service.AccountAlertInterface#getAcctSummAlerts(com.etcc.csc.dto.AccountLoginDTO)
     */
    public AlertDTO[] getAcctSummAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        return makeAlerts(acctLoginDto);
        //end getAcctSummAlerts

    }

    /** 
     * @see com.etcc.csc.service.AccountAlertInterface#getContactInfoAlerts(com.etcc.csc.dto.AccountLoginDTO)
     */
    public AlertDTO[] getContactInfoAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        return null;
        //end getContactInfoAlerts

    }

    /** 
     * @see com.etcc.csc.service.AccountAlertInterface#getVehicleInfoAlerts(com.etcc.csc.dto.AccountLoginDTO)
     */
    public AlertDTO[] getVehicleInfoAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        return makeAlerts(acctLoginDto);
        //end getVehicleInfoAlerts

    }
    
    protected AlertDTO[] makeAlerts(AccountLoginDTO acctLoginDto){
        if (acctLoginDto.getAcctId() == AccountFactory.POPULATED_ACCOUNT_ID){
            final int size = 3;
            AlertDTO[] alerts = new AlertDTO[size];
            for(int idx = 0; idx < size; idx++){
                alerts[idx] = new AlertDTO(idx, "Test Alert #" + idx);
            }
            return alerts;
        }
        return null;

    }

}
