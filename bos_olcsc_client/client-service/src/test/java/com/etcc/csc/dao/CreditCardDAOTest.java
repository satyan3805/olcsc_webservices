/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import com.etcc.csc.dao.dummy.DummyCreditCardDAO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.service.CreditCardFactory;

/**
 * @author Stephen Davidson
 *
 */
public class CreditCardDAOTest {


    /**
     * Test method for {@link com.etcc.csc.dao.CreditCardDAO#getCreditCardTypes()}.
     * @throws Exception if any exceptions occur during testing.
     */
    @Test
    public final void testGetCreditCardTypes() throws Exception{
        CreditCardDAO dao = new DummyCreditCardDAO();
        Collection<CreditCardDTO> creditCardTypes = dao.getCreditCardTypes();
        assertNotNull("No Credit Card types retrieved.", creditCardTypes);
        assertEquals(CreditCardFactory.getCreditCardTypes(), creditCardTypes);
    }

}
