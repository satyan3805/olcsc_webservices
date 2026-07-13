/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.CreditCardDTO;

/**
 * Sets the default/test values for the Credit Card Interface to return.
 * @author Stephen Davidson
 */
public class CreditCardFactory {

    //Package level.  The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final CreditCardInterface mocked){
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                allowing(mocked).getCreditCardTypes();
                will(returnValue(getCreditCardTypes()));
            }});
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Dummy Credit Card Types for testing.
     * @return Credit Card Types for testing.
     */
    public static Collection<CreditCardDTO> getCreditCardTypes(){
        final Collection<CreditCardDTO> types = new ArrayList<CreditCardDTO>(4);
        int idx = 0;
        types.add(new CreditCardDTO(CreditCardDTO.CreditCardType.VISA, "Visa", idx++));
        types.add(new CreditCardDTO(CreditCardDTO.CreditCardType.MASTERCARD, "Mastercard", idx++));
        types.add(new CreditCardDTO(CreditCardDTO.CreditCardType.AMERICANEXPRESS, "Amex", idx++));
        types.add(new CreditCardDTO(CreditCardDTO.CreditCardType.DISCOVER, "Discover/JCB/CUP", idx++));
        return types;
    }
}
