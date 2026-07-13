/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.it.basic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.it.BaseIntegrationTest;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

/**
 * Tests the OLVPS (On-Line Violation Processing System) flows.
 * @author Milosh Boroyevich
 */
public class ViolationIT extends BaseIntegrationTest {
    private static final Logger logger = Logger.getLogger(ViolationIT.class);

    /**
     * Tests the happy path flow.
     * @throws Exception if any errors occur during testing.
     */
    @Test
    public void testHappyPath() throws Exception {
    	// login the violator
        WebResponse resp = getViolatorLoginResponse();
        if (resp == null) return;  // could not connect to server
        // Select an invoice to partially pay and submit
        WebForm form = resp.getFormWithID("invoice-details");
        assertNotNull(form);
        String param = "invoice[0].payment";
        String paymentAmount = form.getParameterValue(param);
        assertNotNull(paymentAmount);
        assertTrue(Float.parseFloat(paymentAmount) == 0.0f);
        form.setParameter(param, "3.45");
        final String paymentTypeNoPay = InvoiceDTO.PaymentOption.DONT_PAY.toString();
        for (String name : form.getParameterNames())
        	if (name.endsWith("].stringPaymentType"))
        		form.setParameter(name, paymentTypeNoPay);
        param = "invoice[0].stringPaymentType";
        String paymentType = form.getParameterValue(param);
        assertNotNull(paymentType);
        form.setParameter(param, InvoiceDTO.PaymentOption.SPECIFY_AMOUNT.toString());
        logger.debug("Submitting form: " + toString(form));
/* TODO: The form submission causes a Server 500 error
        resp = form.submit();
        assertNotNull(resp);
        // review the submitted data
        form = resp.getFormWithID("selected-invoices");
        assertNotNull(form);
        logger.debug("Submitting form: " + toString(form));
        resp = form.submit();
        assertNotNull(resp);
        // select payment type (e.g. EFT) and continue flow...
*/
 	    // debug logging
        WebForm[] forms = resp.getForms();
        logger.debug("Forms: " + toString(forms));
        WebLink[] links = resp.getLinks();
        logger.debug("Links: " + toString(links));
    }
}
