/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.service.AccountFactory;
import com.etcc.csc.service.CreditCardFactory;
import com.etcc.csc.util.FormatUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Tests the CreditCardDelegate APIs.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class CreditCardDelegateTest {
    private static final Logger logger = Logger.getLogger(CreditCardDelegateTest.class);

    CreditCardDelegate delegate;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseTestDelegate.setUpBeforeClass();
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.delegate = new CreditCardDelegate();
    }

    /**
     * Test method for {@link CreditCardDelegate#getAccountCreditCard(AccountLoginDTO)}.
     */
    @Test
    public final void testGetAccountCreditCard() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link CreditCardDelegate#updateAccountCreditCard(AccountLoginDTO, AccountCreditCardDTO, boolean)}.
     */
    @Test
    public final void testUpdateAccountCreditCard() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link CreditCardDelegate#getCreditCardTypes()}.
     * @throws Exception if any errors occur during testing.
     */
    @Test
    public final void testGetCreditCardTypes() throws Exception{
        Collection<CreditCardDTO> creditCardTypes = this.delegate.getCreditCardTypes();
        assertNotNull("No Credit Card types retrieved.", creditCardTypes);
        assertEquals(CreditCardFactory.getCreditCardTypes(), creditCardTypes);
    }

    /**
     * Test method for {@link CreditCardDelegate#getCreditCardYears()}.
     */
    @Test
    public final void testGetCreditCardYears() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link CreditCardDelegate#getCreditCardMonths()}.
     */
    @Test
    public final void testGetCreditCardMonths() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link CreditCardDelegate#getCreditCardName(CreditCardDTO.CreditCardType)}.
     */
    @Test
    public final void testGetCreditCardNameCreditCardType() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link CreditCardDelegate#getCreditCardName(String)}.
     */
    @Test
    public final void testGetCreditCardNameString() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link CreditCardDelegate#getCreditCardName(CreditCardDTO.CreditCardType, Collection)}.
     */
    @Test
    public final void testGetCreditCardNameCreditCardTypeCollectionOfCreditCardDTO() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for {@link CreditCardDelegate#getCreditCardName(String, Collection)}.
     */
    @Test
    public final void testGetCreditCardNameStringCollectionOfCreditCardDTO() {
        logger.warn("Not yet implemented");
    }

    /**
     * Test method for formatting of credit card data.
     */
    @Test
    public final void testFormatCreditCards() {
        AccountCreditCardDTO cc = getCreditCard();
        String formattedCC = FormatUtil.formatCreditCard(cc);
        assertFalse("Failed formatting CC DEFAULT", StringUtil.isEmpty(formattedCC));
        logger.info("DEFAULT: " + formattedCC);
        formattedCC = FormatUtil.formatCreditCard(cc, ", ", null, true);
        assertFalse("Failed formatting CC MASKALL", StringUtil.isEmpty(formattedCC));
        logger.info("MASKALL: " + formattedCC);
        formattedCC = FormatUtil.formatCreditCard(cc, ", ", FormatUtil.MaskValue.NONE, false);
        assertFalse("Failed formatting CC MASKNONE", StringUtil.isEmpty(formattedCC));
        logger.info("MASKNONE: " + formattedCC);
    }

    /**
     * Returns a valid pre-populated credit card.
     * @return a valid credit card
     */
    protected static AccountCreditCardDTO getCreditCard() {
        AccountCreditCardDTO cc = new AccountCreditCardDTO();
        cc.setNameOnCard(AccountFactory.ACCOUNT_NAME_FIRST + " " + AccountFactory.ACCOUNT_NAME_LAST);
        cc.setCardCode("M");
        cc.setCardNbr("5499740000000057");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        cc.setCardExpiresDate(cal.getTime());
        cc.setAddress1("Plano Rd");
        cc.setCity("Plano");
        cc.setState("TX");
        cc.setZip("75230");
        cc.setPrimary(true);
        return cc;
    }
}
