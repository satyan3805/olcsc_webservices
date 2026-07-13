/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;

/**
 * Generates the return values for the methods needed for the UI Tests (and demos).
 * @author Stephen Davidson
 */
public class EmailValidationFactory {
    //Package level.  The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final EmailValidationInterface mocked){
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                allowing(mocked).validationStatus(with(any(AccountLoginDTO.class)));
                will(returnValue(getStatus()));
            }});
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    /**
     * Gets a dummy Status value for testing.
     * @return status value for testing.
     */
    public static ResultDTO getStatus(){
        ResultDTO dto = new ResultDTO();
        dto.setStatus(EmailValidationInterface.VDST_VALIDATION_DONE);
        return dto;
    }
}
