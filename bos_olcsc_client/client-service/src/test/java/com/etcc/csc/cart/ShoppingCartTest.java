/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.cart;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.etcc.csc.cart.CartItem.ItemType;
import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.dto.InvoiceDTO;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class ShoppingCartTest {

    private static final Logger logger = Logger.getLogger(ShoppingCartTest.class);

    ShoppingCart cart;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.cart = new ShoppingCart();
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#addAll(java.util.Collection)}.
     */
    @Test
    public void testAddAll() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#add(com.etcc.csc.cart.CartItem)}.
     */
    @Test
    public void testAdd() {
        //Sanity
        assertEquals("Sanity failure: Starting cart not empty:",0,this.cart.getBins().size());
        InvoiceDTO hctraInvoice = new InvoiceDTO(AgencyEnum.AGENCY_HARRIS_COUNTY).withInvoiceDue(1.0f).withPayment(1.0f);
        this.cart.add(hctraInvoice);
        assertEquals("HCTRA Bin not added to cart:",1,this.cart.lists.size());
        assertEquals("HCTRA Invoice not added to bin:",1,this.cart.lists.get(ItemType.HCTRA).size());
        assertSame("HCTRA Invoice has been mangled", hctraInvoice, this.cart.lists.get(ItemType.HCTRA).first());
        InvoiceDTO fbInvoice = new InvoiceDTO(AgencyEnum.AGENCY_FORT_BEND).withInvoiceDue(1.0f).withPayment(1.0f);
        this.cart.add(fbInvoice);
        assertEquals("Fort Bend Bin not added to cart:",2,this.cart.lists.size());
        assertEquals("Fort Bend Invoice not added to bin:",1,this.cart.lists.get(ItemType.FORT_BEND).size());
        assertSame("Fort Bend Invoice has been mangled", fbInvoice, this.cart.lists.get(ItemType.FORT_BEND).first());
        // confirm can't add invoice with no payment
        this.cart.add(new InvoiceDTO(AgencyEnum.AGENCY_FORT_BEND));
        assertEquals("Fort Bend Invoice (no payment specified) added to bin!",1,this.cart.getBin(ItemType.FORT_BEND).size());
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#getBins()}.
     */
    @Test
    public void testGetBins() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#getBin(com.etcc.csc.cart.CartItem.ItemType)}.
     */
    @Test
    public void testGetBin() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#iterator()}.
     */
    @Test
    public void testIterator() {
        logger.warn("Not yet implemented");
    }


    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#getAllInvoices()}.
     */
   //@Test
    public void testGetAllInvoices() {
        List<InvoiceDTO> invoices = this.cart.getAllInvoices();
        assertNotNull("Null list returned for invoices.", invoices);
        assertEquals("Non-empty invoice list returned for empty Shopping cart:", 0, invoices.size());
        this.cart.add(new InvoiceDTO(AgencyEnum.AGENCY_HARRIS_COUNTY).withInvoiceDue(1.0f).withPayment(1.0f));
        invoices = this.cart.getAllInvoices();
        assertNotNull("Null list returned for cart with HCTRA Invoice", invoices);
        logger.trace(invoices);
        assertEquals("Wrong number of invoices returned for HCTRA Shopping cart:", 1, invoices.size());
        this.cart.add(new InvoiceDTO(AgencyEnum.AGENCY_FORT_BEND).withInvoiceDue(1.0f).withPayment(1.0f));
        invoices = this.cart.getAllInvoices();
        assertNotNull("Null list returned for cart with HCTRA & FB Invoices", invoices);
        logger.trace(invoices);
        assertEquals("Wrong number of invoices returned for HCTRA & FB Shopping cart:", 2, invoices.size());
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#calculateTotals()}.
     */
    @Test
    public void testCalculateTotals() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#getDiscount()}.
     */
    @Test
    public void testGetDiscount() {
        assertEquals("Empty cart discount: " , 0, this.cart.getDiscount(), 0);
        logger.warn("Needs more test cases, once discounts are worked out.");
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#getCurrentAmountDue()}.
     */
    @Test
    public void testGetCurrentAmountDue() {
        this.cart.calculateTotals();
        assertEquals("Empty cart Current Amount Due: " , 0, this.cart.getCurrentAmountDue(), 0);
        InvoiceDTO item = new InvoiceDTO(AgencyEnum.AGENCY_HARRIS_COUNTY);
        item.setCollectionFee(1.00f);
        item.setInvoiceDue(1.0f);
        item.setPayment(1.0f);
        this.cart.add(item);
        this.cart.calculateTotals();
        float total = item.getCurrentAmountDue();
        logger.trace("Total due: " + total);
        assertEquals("HCTRA total Amount Due: " , total, this.cart.getCurrentAmountDue(), 0);
        item = new InvoiceDTO(AgencyEnum.AGENCY_FORT_BEND);
        item.setCollectionFee(1.00f);
        item.setInvoiceDue(1.0f);
        item.setPayment(1.0f);
        this.cart.add(item);
        this.cart.calculateTotals();
        total += item.getCurrentAmountDue();
        logger.trace("Total due: " + total);
        assertEquals("HCTRA & FB total Amount Due: " , total, this.cart.getCurrentAmountDue(), 0);
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#getAmountDue()}.
     */
    @Test
    public void testGetAmountDue() {
        this.cart.calculateTotals();
        assertEquals("Empty cart Amount Due: " , 0, this.cart.getAmountDue(), 0);
        logger.warn("Needs more test cases");
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#getPastPayments()}.
     */
    @Test
    public void testGetPastPayments() {
        this.cart.calculateTotals();
        assertEquals("Empty cart Past Payments: " , 0, this.cart.getPastPayments(), 0);
        logger.warn("Needs more test cases.");
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#getPayment()}.
     */
    @Test
    public void testGetPayment() {
        this.cart.calculateTotals();
        assertEquals("Empty cart Amount Due: " , 0, this.cart.getPayment(), 0);
        logger.warn("Needs more test cases.");
    }

    /**
     * Test method for {@link com.etcc.csc.cart.ShoppingCart#getRemainingBalance()}.
     */
    @Test
    public void testGetRemainingBalance() {
        this.cart.calculateTotals();
        assertEquals("Empty cart Amount Due: " , 0, this.cart.getRemainingBalance(), 0);
        logger.warn("Needs more test cases.");
    }

}
