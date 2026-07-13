/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dto;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author Milosh Boroyevich
 */
public class PaymentTest {
    private static final Logger logger = Logger.getLogger(PaymentTest.class);

    /**
     * Test method for {@link PaymentType}
     */
    @Test
    public void testPaymentType() {
        PaymentType pt = PaymentType.valueOfCode("credit");
        logger.debug(pt);
        assertSame(PaymentType.CREDIT, pt);

        pt = PaymentType.valueOfCode(PaymentType.CODE_CREDIT);
        logger.debug(pt);
        assertSame(PaymentType.CREDIT, pt);

        pt = PaymentType.valueOf("CREDIT");
        logger.debug(pt);
        assertSame(PaymentType.CREDIT, pt);

        // Generate IllegalArgumentException -- now just null
        pt = PaymentType.valueOfCode("__invalid__");
        assertNull("Invalid payment code should return null!", pt);
//        fail("IllegalArgumentException should have been thrown!");
    }
}
