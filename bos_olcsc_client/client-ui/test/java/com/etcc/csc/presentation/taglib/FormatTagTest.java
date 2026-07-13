/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.taglib;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountName;
import com.etcc.csc.dto.Address;
import com.etcc.csc.dto.BillingInfo;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.MockPageContext;
import com.etcc.csc.service.AccountFactory;
import com.etcc.csc.util.FormatUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Unit tests for FormatTag and FormatUtil.
 * @author Milosh Boroyevich
 * @see FormatTag
 * @see FormatUtil
 */
public class FormatTagTest {
    private static final Logger logger = Logger.getLogger(FormatTagTest.class);

    FormatTag tag;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        tag = new FormatTag();
        tag.setPageContext(new MockPageContext());
    }

    /**
     * Unit test for {@link FormatTag#doStartTag()} and {@link FormatUtil#formatAddress(Address, String, boolean)}
     * @throws JspException if an error occurs
     */
    @Test
    public void testFormatAddress() throws JspException {
        logger.info("testFormatAddress");
        tag.setAddress(getAccount());
        tag.doStartTag();
        tag.setAddressContainer(getBillingInfo());
        tag.doStartTag();
//    public void testFormatAddress(Address)
//    public void testFormatAddress(Address, String, boolean)
//    public void testFormatAddress(BillingInfoDTO)
//    public void testFormatAddress(BillingInfoDTO, String, boolean)
//    public void testFormatAddress(BillingInfoForm)
//    public void testFormatAddress(BillingInfoForm, String)
    }

    @Test
    public void testFormatCreditCard() {
//    public void testFormatCreditCard(AccountCreditCardDTO)
//    public void testFormatCreditCard(AccountCreditCardDTO, String, MaskValue, boolean)
//    public void testFormatCreditCards(AccountCreditCardDTO[], String, MaskValue, boolean, boolean)
    }

    @Test
    public void testFormatEFT() {
//    public void testFormatEFT(AccountEFTDTO, String, MaskValue)
    }

    /**
     * Unit test for {@link FormatTag#doStartTag()} and {@link FormatUtil#formatName(AccountName, String, int)}
     * @throws JspException if an error occurs
     */
    @Test
    public void testFormatName() throws JspException {
        logger.info("testFormatName");
        tag.setAccountName(getAccount());
        tag.doStartTag();
    }

    /**
     * Unit test for {@link FormatTag#doStartTag()} and {@link FormatUtil#formatPaymentMethod(BillingInfo, String, FormatUtil.MaskValue, boolean, boolean)}
     * @throws JspException if an error occurs
     */
    @Test
    public void testFormatPaymentMethod() throws JspException {
        logger.info("testFormatPaymentMethod");
        tag.setBillingInfo(getBillingInfo());
        tag.doStartTag();
    }

    @Test
    public void testFormatStrings() {
        logger.info("testFormatStrings");
        ArrayList<String> strings = new ArrayList<String>();
        String result = FormatUtil.formatStrings(strings);
        assertTrue(StringUtil.isEmpty(result));
        strings.add("1");
        result = FormatUtil.formatStrings(strings);
        assertEquals("1", result);
        strings.add("2");
        result = FormatUtil.formatStrings(strings);
        assertEquals("1 and 2", result);
        strings.add("3");
        result = FormatUtil.formatStrings(strings);
        assertEquals("1, 2 and 3", result);
        logger.info(result);
    }

    /**
     * Unit test for {@link FormatTag#doStartTag()} and {@link FormatUtil#formatVehicle(TagDTO, boolean)}
     * @throws JspException if an error occurs
     */
    @Test
    public void testFormatVehicle() throws JspException {
        logger.info("testFormatVehicle");
        TagDTO vehicle = new TagDTO();
        vehicle.setAcctId(AccountFactory.BASIC_ACCOUNT_ID);
        vehicle.setLicState("AZ");
        vehicle.setTemporaryLicPlate(true);
        vehicle.setVehicleColor("SILVER");
        vehicle.setVehicleYear("1995");
        vehicle.setVehicleMake("Acme");
        vehicle.setVehicleModel("Skates");
        vehicle.setNickname("Rocket");
        tag.setVehicle(vehicle);
        tag.doStartTag();
    }

    protected static BillingInfoDTO getBillingInfo() {
        AccountLoginDTO accountLogin = AccountFactory.getAccountLoginDTO(AccountFactory.BASIC_ACCOUNT_ID);
        accountLogin.setLoginId("COYOTE");
        return AccountFactory.getBillingInfo(accountLogin);
    }

    protected static AccountDTO getAccount() {
        AccountLoginDTO accountLogin = AccountFactory.getAccountLoginDTO();
        accountLogin.setLoginType(AccountLoginDTO.LoginType.AC);
        return AccountFactory.getAccount(accountLogin);
    }
}
