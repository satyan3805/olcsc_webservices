/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import org.apache.log4j.Logger;
import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.util.StringUtil;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class AccountUtilFactory {
    private static final Logger logger = Logger.getLogger(AccountUtilFactory.class);
    public static final String CLOSE_ACCOUNT_AGREEMENT = "This is the agreement for closing an account.  Accept it!";

    //Package level.  The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final AccountUtilInterface mocked){
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                allowing(mocked).closeAccount(with(any(AccountLoginDTO.class)), with(equal("C")));
                will(returnValue(processRefund("C")));
                allowing(mocked).closeAccount(with(any(AccountLoginDTO.class)), with(equal("O")));
                will(returnValue(processRefund("O")));
                allowing(mocked).closeAccount(with(any(AccountLoginDTO.class)), with(any(String.class)));
                will(returnValue(processRefund(null)));
                allowing(mocked).autoLogin(with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(String.class)), with(aNull(UserEnvDTO.class)));
                will(returnValue(AccountFactory.getAccountLoginDTO()));
                allowing(mocked).generatePlateReminder(with(any(AccountLoginDTO.class)), with(any(String.class)));
                will(returnValue(null));
                allowing(mocked).getCloseAccountAgreement();
                will(returnValue(CLOSE_ACCOUNT_AGREEMENT));
                allowing(mocked).checkCloseAccount(with(any(AccountLoginDTO.class)));
                will(returnValue(null));
            }});
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static ResultDTO processRefund(String refundType) {
        ResultDTO result = new ResultDTO();
        if (!StringUtil.isEmpty(refundType)) {
            char rt = Character.toUpperCase(refundType.charAt(0));
            switch (rt) {
            case 'C':
            case 'O':
                logger.info("Refund success: " + rt);
                return result;
            }
        }
        result.addError("Invalid refund type: " + refundType);
        return result;
    }
}
