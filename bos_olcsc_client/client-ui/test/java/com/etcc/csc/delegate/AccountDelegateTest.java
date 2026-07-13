/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountIopDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BaseContactInfo;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.BillingInfoResultDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.ServiceFactoryTestImpl;

/**
 * Tests for the Account Delegate.  Extended by the EJB & WebService tests, so these tests are run by
 * those modules as well.
 */
public class AccountDelegateTest {
    private static final Logger logger = Logger.getLogger(AccountDelegateTest.class);

    AccountDelegate delegate;

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
        this.delegate = new AccountDelegate();
    }

    /**
     * Test method for
     * {@link AccountDelegate#accountExists(String, String, String, String, String, String)}
     * @throws Exception If any exceptions occur.
     */
    @Test
    public void testAccountExists() throws Exception {
        String accountNumber = "100";
        String tolltagPrefix = "ez";
        String tolltagNumber = "0000000";
        String driverLicenseState = "Tx";
        String driverLicenseNumber = "000000";
        String taxId = null;
        AccountLoginDTO accountLoginDTO = this.delegate.accountExists(accountNumber, tolltagPrefix, tolltagNumber,
                driverLicenseState, driverLicenseNumber, taxId);
        assertNotNull(accountLoginDTO);
    }

    /**
     * Test method for
     * {@link AccountDelegate#setupOnlineAccessStep1(String, String, String, String, String, String, String, String, UserEnvDTO)}
     */
    @Test
    public void testSetupOnlineAccessStep1() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#setupOnlineAccess(AccountLoginDTO, OnlineAccessSetupDTO)}
     */
    @Test
    public void testSetupOnlineAccess() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#loginAccount(String, String, String, String, UserEnvDTO)}
     * @throws Exception thrown if any errors occur.
     */
    @Test
    public void testLoginAccount() throws Exception {
        AccountLoginDTO acctLoginDto = this.delegate.loginAccount("junit", "password", "127.0.0.1", "0000", new UserEnvDTO());
        assertNotNull(acctLoginDto);
    }

    /**
     * Test method for {@link AccountDelegate#createSession(long, String)}.
     */
    @Test
    public void testCreateSession() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountDelegate#getAccount(AccountLoginDTO)}.
     * @throws Exception thrown if any errors occur.
     */
    @Test
    public void testGetAccount() throws Exception{
        AccountLoginDTO acctLoginDto = this.delegate.loginAccount("junit", "password", "127.0.0.1", "0000", new UserEnvDTO());
        assertNotNull("Sanity test failed -- accountLogin returned null!", acctLoginDto);

        AccountDTO accountDto = this.delegate.getAccount(acctLoginDto);
        assertNotNull("No account returned.", accountDto);
        assertEquals(acctLoginDto.getAcctId(), accountDto.getAcctId());
        //end testGetAccount
    }

    /**
     * Test method for {@link AccountDelegate#getAccountTags(AccountLoginDTO)}.
     */
    @Test
    public void testGetAccountTags() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#getUserPreference(AccountLoginDTO)}.
     */
    @Test
    public void testGetUserPreference() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountDelegate#getTollTagUserPreference()}.
     */
    @Test
    public void testGetTollTagUserPreference() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateUserPreference(AccountLoginDTO, AccountIopDTO[], UserPreferenceDTO[])}
     */
    @Test
    public void testUpdateUserPreference() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateAccount(AccountLoginDTO, AccountDTO)}
     */
    @Test
    public void testUpdateAccount() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateAccountTags(AccountLoginDTO, TagDTO[], boolean)}
     */
    @Test
    public void testUpdateAccountTags() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#getLastTransactions(AccountLoginDTO)}.
     */
    @Test
    public void testGetLastTransactions() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountDelegate#getAlerts(AccountLoginDTO)}.
     */
    @Test
    public void testGetAlerts() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#isPaymentOwed(AccountLoginDTO, long, TagDTO[])}
     */
    @Test
    public void testIsPaymentOwed() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AccountDelegate#isBigAccount(long)}.
     */
    @Test
    public void testIsBigAccount() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#setupAccountStep1(String, String, String, int, String, String, String, String)}
     */
    @Test
    public void testSetupAccountStep1() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateLoginId(String, String, String, String, String, String)}
     */
    @Test
    public void testUpdateLoginId() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#setPersonalInfo(AccountLoginDTO, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, boolean, boolean, boolean, Long, String, String)}
     */
    @Test
    public void testSetPersonalInfo() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateLoginInfo(AccountLoginDTO, String, String, String, int, String, String, BaseContactInfo)}
     */
    @Test
    public void testUpdateLoginInfo() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateContactInfo(String, String, String, String, String, String, String, String, String, String)}
     */
    @Test
    public void testUpdateContactInfo() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#setBillingInfo(AccountLoginDTO, BillingInfoDTO)}
     * @throws Exception If any exceptions occur during the test.
     */
   // @Test
    public void testSetBillingInfo() throws Exception {
        AccountLoginDTO acctLoginDto = this.delegate.loginAccount("junit", "password", "127.0.0.1", "0000", new UserEnvDTO());
        assertNotNull("Sanity test failed -- accountLogin returned null!", acctLoginDto);
        AccountCreditCardDTO cardDto = new AccountCreditCardDTO();
        //Set Date for this time next year.
        cardDto.setCardExpiresDate(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 365)));
        cardDto.setCardNbr("4111111111111111");
        BillingInfoDTO billingInfoDto = new BillingInfoDTO();
        billingInfoDto.setBillingType(PaymentType.CREDIT);
        billingInfoDto.setCards(new AccountCreditCardDTO[]{cardDto});
        
        BillingInfoResultDTO resultDto = this.delegate.setBillingInfo(acctLoginDto, billingInfoDto);
        assertNotNull("No results returned.", resultDto);
        if (!(ServiceFactory.getDefaultServiceFactory() instanceof ServiceFactoryTestImpl)){
            assertTrue("Validation errors not set", resultDto.hasErrors());
            assertEquals("Incorrect count on Validation errors: ", 1, resultDto.getErrors().length);
        }
    }

    /**
     * Test method for
     * {@link AccountDelegate#makePayment(AccountLoginDTO, BillingInfoDTO, double)}
     */
    @Test
    public void testMakePayment() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#getBillingInfo(AccountLoginDTO)}
     */
    @Test
    public void testGetBillingInfo() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateMailingAddr(String, String, String, String, String, String, String, String, String, String, String, String, String, String)}
     */
    @Test
    public void testUpdateMailingAddr() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateBillingInfo(AccountLoginDTO, BillingInfoDTO)}
     */
    @Test
    public void testUpdateBillingInfo() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateBillingAddress(String, String, String, String, String, String, String, String, String, String, String, String, String, String)}
     */
    @Test
    public void testUpdateBillingAddress() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#updateRebillAmt(String, String, String, String, String, double, double)}
     */
    @Test
    public void testUpdateRebillAmt() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for
     * {@link AccountDelegate#getAccountStatus(AccountLoginDTO, AccountDTO)}
     */
    @Test
    public void testGetAccountStatus() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }
}
