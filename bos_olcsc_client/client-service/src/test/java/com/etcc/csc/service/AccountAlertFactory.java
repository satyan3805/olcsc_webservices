/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;

/**
 * Generates the return values for the methods needed for the UI Tests (and demos).
 * @author Stephen Davidson
 *
 */
public class AccountAlertFactory {
    //Package level.  The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final AccountAlertInterface mocked){
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                //Note: If the exact values are NOT passed as parameters, the getAccount will through an Exception.
                //So, set up to use "Wildcards".
                allowing(mocked).getAcctSummAlerts(with(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID)));
                will(returnValue(getAlerts()));
                allowing(mocked).getAcctSummAlerts(with(any(AccountLoginDTO.class)));
                will(returnValue(null));
                allowing(mocked).getVehicleInfoAlerts(with(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID)));
                will(returnValue(getVehicleAlerts()));
                allowing(mocked).getVehicleInfoAlerts(with(any(AccountLoginDTO.class)));
                will(returnValue(null));
                allowing(mocked).addressCleanseAlertResponse(with(any(AccountLoginDTO.class)), with(any(String.class)));
                will(returnValue(null));
                
            }});
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    static AlertDTO[] getAlerts(){
        final int alertSize = 3;
        Date now = new Date();
        final AlertDTO[] alerts = new AlertDTO[alertSize];
        for(int idx = 0;idx < 3;idx++){
            AlertDTO alert = new AlertDTO();
            alert.setAlertMsg("Message " + idx);
            alert.setCreatedBy("Junit");
            alert.setDateCreated(now);
            alerts[idx] = alert;
        }
        return alerts;
    }
    
    static AlertDTO[] getVehicleAlerts(){
        final int alertSize = 3;
        Date now = new Date();
        final AlertDTO[] alerts = new AlertDTO[alertSize];
        for(int idx = 0;idx < 3;idx++){
            AlertDTO alert = new AlertDTO();
            alert.setAlertMsg("Vehicle Alert Message " + idx);
            alert.setCreatedBy("Junit");
            alert.setDateCreated(now);
            alerts[idx] = alert;
        }
        return alerts;
    }

}
