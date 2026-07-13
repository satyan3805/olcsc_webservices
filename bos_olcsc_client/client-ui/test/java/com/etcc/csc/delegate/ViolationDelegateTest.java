/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.*;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.cart.ShoppingCart;
import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.dao.dummy.DummyViolationDAO;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.DocumentDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.service.ViolationTestImpl;
import com.etcc.csc.validation.ValidationException;

/**
 * Tests the ViolationDelegate using WebServices.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class ViolationDelegateTest {
    private static final Logger logger = Logger.getLogger(ViolationDelegateTest.class);

    private static final String IP_ADDRESS = "192.168.1.1";
    private static final float PAYMENT_AMOUNT = 13.0f;

    ViolationDelegate delegate;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseTestDelegate.setUpBeforeClass();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.delegate = new ViolationDelegate();
    }

    /**
     * Test method for {@link ViolationDelegate#loginViolator(AccountLoginDTO, String, String, UserEnvDTO, String, String, String)}.
     * @throws Exception if any errors occur during testing.
     */
    @Test
    public void testLoginViolator() throws Exception {
        //Initial Sanity checks
        ViolatorDTO violator = getViolator();
        assertNotNull("DTO was null.", violator);
        violator.calculatePreliminaryTotals();
        violator.calculateTotals();
        assertNotNull("Violations were null.", violator.getInvoicedViolationsMap());
        assertFalse("Violations are not present.", violator.getInvoicedViolationsMap().size() == 0);
        assertNotNull("Uninvoiced were null.", violator.getUninvoicedViolationsMap());
        assertFalse("Uninvoiced are not present.", violator.getUninvoicedViolationsMap().size() == 0);
        assertFalse("Totals were not set", violator.getAmountDue() == 0);
//        logger.trace("Violator Logged in: " + violator);
    }

    /**
     * Test method for {@link ViolationDelegate#makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)}.
     * @throws Exception if any errors occur during testing.
     */
    @Test
    public void testMakePaymentEft() throws Exception {
        ViolatorDTO violator = getViolatorWithCart();
        AccountEFTDTO eftPayment = new AccountEFTDTO();
        eftPayment.setAccountType(AccountEFTDTO.BankAccountType.PERSONAL);
        eftPayment.setRoutingNumber("123456789");
        eftPayment.setAccountNumber("112245789");
        ViolatorDTO response = this.delegate.makePayment(violator, eftPayment, IP_ADDRESS);
        assertNotNull("No response from make payment", response);
        assertFalse("Unexpected errors returned in response: " + ErrorMessageDTO.toStringBuilder(response.getErrors()), response.hasErrors());
        ShoppingCart cart = response.getShoppingCart();
        assertFalse("Cart should contain invoices, but is empty.", cart.isEmpty());
        assertTrue("Violator and Cart payments should still be equal", violator.getPayment() == cart.getPayment());
    }

    /**
     * Test method for {@link ViolationDelegate#makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)}.
     * @throws Exception if any errors occur during testing.
     */
    @Test
    public void testMakePaymentCreditCard() throws Exception {
        ViolatorDTO violator = getViolatorWithCart();
    	ViolatorDTO response = this.delegate.makePayment(violator, CreditCardDelegateTest.getCreditCard(), IP_ADDRESS);
    	assertNotNull("No response from make payment", response);
        assertFalse("Unexpected errors returned in response: " + ErrorMessageDTO.toStringBuilder(response.getErrors()), response.hasErrors());
        ShoppingCart cart = response.getShoppingCart();
        assertFalse("Cart should contain invoices, but is empty.", cart.isEmpty());
        assertTrue("Violator and Cart payments should still be equal", violator.getPayment() == cart.getPayment());
    }

    /**
     * Test method for {@link ViolationDelegate#getPaymentPlan(String, String, String, String, String, long)}.
     */
    @Test
    public void testGetPaymentPlan() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link ViolationDelegate#getInvoices(AccountLoginDTO, String, String, String, AgencyEnum)}.
     */
    @Test
    public void testGetInvoices() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link ViolationDelegate#getInvoiceDoc(ViolatorDTO, InvoiceDTO)}.
     * @throws Exception If any exceptions occur during testing.
     */
    @Test
    public void testGetInvoiceDoc() throws Exception {
        ViolatorDTO violatorDTO = getViolator();
        InvoiceDTO invoiceDTO = new InvoiceDTO(AgencyEnum.AGENCY_HARRIS_COUNTY);
        DocumentDTO dto = this.delegate.getInvoiceDoc(violatorDTO, invoiceDTO);
        assertNotNull("No Document was returned.", dto);
        assertNotNull("Empty Document returned.", dto.getDocument());
        assertArrayEquals(ViolationTestImpl.testDocument.getDocument(), dto.getDocument());
    }

    /**
     * Test method for {@link ViolationDelegate#emailReceipt(ViolatorDTO, AccountPaymentMethodDTO, String)}.
     */
    @Test
    public void testEmailReceipt() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link ViolationDelegate#makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)}.
     * This test requires validation of input data (which is not available for the TestImpl).
     * Subclasses should annotate a test and call this method if invalid credit card input is expected to cause an error.
     * @throws Exception if any errors occur during testing.
     */
    protected void makePaymentInvalidCreditCard() throws Exception {
        logger.debug("Test Invalid Credit Card.");
        ViolatorDTO violator = getViolatorWithCart();
        float initialViolatorPaymentAmount = violator.getPayment();
        logger.debug("Starting payments: " + initialViolatorPaymentAmount + '/' + violator.getShoppingCart().getPayment());
        // Test invalid card number
        AccountCreditCardDTO ccPayment = CreditCardDelegateTest.getCreditCard();
        ccPayment.setCardNbr("987123");  // invalid number
        ViolatorDTO response = null;
        try {
            response = this.delegate.makePayment(violator, ccPayment, IP_ADDRESS);
        } catch (ValidationException e) {  // aspects should normally take care of this
            logger.error(e, e);
            response = violator;
            response.addError(e.getMessage());
        }
        assertNotNull("No response from make payment", response);
        assertTrue("Expected errors NOT returned in response", response.hasErrors());
        // Payment failed for all attempts -- therefore payment amounts are NOT cleared
        ShoppingCart cart = response.getShoppingCart();
        logger.debug("After make payment (total failure): " + response.getPayment() + '/' + cart.getPayment());
        assertTrue("Cart payment should not be reset upon TOTAL failure: " + cart.getPayment(), cart.getPayment() == initialViolatorPaymentAmount);
        assertFalse("Cart should contain failed invoices, but is empty.", cart.isEmpty());
        assertTrue("Violator (" + response.getPayment() + ") and Cart (" + cart.getPayment() + ") payments should still be equal", response.getPayment() == cart.getPayment());
        assertTrue("Original Violator and updated Cart payments should NOT be equal", initialViolatorPaymentAmount == cart.getPayment());

        // TODO: perform test where only 1 agency fails...
        // Since payment failed, payment amount for the failed agency should be cleared
//        cart = response.getShoppingCart();
//        assertTrue("Cart payment not reset for failure: " + cart.getPayment(), cart.getPayment() == 0.0f);
//        assertFalse("Cart should contain failed invoices, but is empty.", cart.isEmpty());
//        assertTrue("Violator and Cart payments should still be equal", response.getPayment() == cart.getPayment());
//        assertFalse("Original Violator and updated Cart payments should NOT be equal", initialViolatorPaymentAmount == cart.getPayment());
        logger.debug("Test Invalid Credit Card Completed.");
    }

    protected ViolatorDTO getViolatorWithCart() throws Exception {
        ViolatorDTO violator = getViolator();
        assertFalse("No invoices found", violator.getAllInvoices().isEmpty());
        InvoiceDTO invoice = violator.getAllInvoices().get(0);
        assertNotNull("Invoice on violator doesn't exist", invoice);
        invoice.setPaymentType(InvoiceDTO.PaymentOption.SPECIFY_AMOUNT);
        invoice.setPayment(PAYMENT_AMOUNT);
        ShoppingCart cart = violator.updateShoppingCart();
        assertNotNull("Add to shopping cart failed", violator.getShoppingCart());
        assertEquals(cart, violator.getShoppingCart());
        violator.calculatePreliminaryTotals();
        violator.calculateTotals();
        assertTrue("Violator and Cart payments should be equal", violator.getPayment() == cart.getPayment());
        assertTrue("Invalid violator payment amount", violator.getPayment() == PAYMENT_AMOUNT);
        assertFalse("Sanity Check - cart should contain invoices.", cart.isEmpty());
        return violator;
    }

    protected ViolatorDTO getViolator() throws Exception {
        ViolatorDTO violator = this.delegate.loginViolator(null, null, null,
                                                           new UserEnvDTO(),
                                                           DummyViolationDAO.V_INVOICE,
                                                           DummyViolationDAO.V_LIC_PLATE,
                                                           DummyViolationDAO.V_STATE,
                                                           DummyViolationDAO.V_STATE);
        assertNotNull("Login violator failed", violator);
        return violator;
    }
}
