/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.VelcroDTO;

/**
 * Generates the return values for the methods needed for Tests (and demos).
 * @author Milosh Boroyevich
 */
public class VelcroFactory {
	public static final int TEST_VELCRO_QUANTITY = 4;

	//Package level.  The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final VelcroInterface mocked){
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                //Note: If the exact values are NOT passed as parameters, the getAccount will through an Exception.
                //So, set up to use "Wildcards".
                allowing(mocked).getVelcroInfo(with(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID)));
                will(returnValue(getVelcro()));
                allowing(mocked).getVelcroInfo(with(any(AccountLoginDTO.class)));
                will(returnValue(null));
                allowing(mocked).getVelcroReceiptPDF(with(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID)));
                will(returnValue(getVelcroUrl()));
                allowing(mocked).getVelcroReceiptPDF(with(any(AccountLoginDTO.class)));
                will(returnValue(null));
                allowing(mocked).submitVelcroRequest(with(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID)), with(any(int.class)));
                will(returnValue(new ResultDTO()));
                allowing(mocked).submitVelcroRequest(with(any(AccountLoginDTO.class)), with(any(int.class)));
                will(returnValue(getSubmitVelcroError()));
            }});
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static VelcroDTO getVelcro() {
    	VelcroDTO velcro = new VelcroDTO();
    	velcro.setOrderQty(TEST_VELCRO_QUANTITY);
    	velcro.setActiveTolltag(1);
    	velcro.setAcctId(AccountFactory.POPULATED_ACCOUNT_ID);
        return velcro;
    }

    public static String getVelcroUrl() {
    	return "http://localhost/VelcroReceipt.pdf";
    }

    public static ResultDTO getSubmitVelcroError() {
    	ResultDTO result = new ResultDTO();
    	result.addError(new ErrorMessageDTO("velcroError", "Error processing velcro request."));
        return result;
    }
}
