/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dto;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.dto.InvoiceDTO.PaymentOption;

/**
 * @author Milosh Boroyevich
 */
public class InvoiceTest {
    private static final Logger logger = Logger.getLogger(InvoiceTest.class);

    /**
     * Test method for {@link InvoiceDTO#setStringPaymentType(String)}
     */
    @Test
    public void testInvoiceStringPaymentOption() {
        InvoiceDTO invoice = new InvoiceDTO(AgencyEnum.AGENCY_HARRIS_COUNTY);
        InvoiceDTO.PaymentOption payment = null;
        invoice.setPaymentType(payment);
        assertNull(invoice.getPaymentType());
        invoice.setPaymentType(PaymentOption.PAY_IN_FULL);
        assertSame(PaymentOption.PAY_IN_FULL, invoice.getPaymentType());
        invoice.setStringPaymentType(PaymentOption.PAY_IN_FULL.name());
        payment = invoice.getPaymentType();
        logger.debug(invoice);
        assertNotNull(payment);
        assertSame(PaymentOption.PAY_IN_FULL, invoice.getPaymentType());
    }
}
