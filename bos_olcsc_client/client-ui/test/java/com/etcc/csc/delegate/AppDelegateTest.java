/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.service.AccountFactory;

/**
 * Delegate tests for application service.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class AppDelegateTest {
    private static final Logger logger = Logger.getLogger(AppDelegateTest.class);

    AppDelegate delegate;

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
        this.delegate = new AppDelegate();
    }

    /**
     * Test method for {@link AppDelegate#getAuthContactLimit()}.
     * @throws Exception if any exceptions occur.
     */
    @Test
    public void testGetAuthContactLimit() throws Exception {
        int limit = this.delegate.getAuthContactLimit();
        assertFalse("Limit was not retrieved", limit == 0);
    }

    /**
     * Test method for {@link AppDelegate#getContactPhoneNumber()}.
     * @throws Exception if any exceptions occur.
     */
    @Test
    public void testGetContactPhoneNumber() throws Exception{
        String phoneNumber = this.delegate.getContactPhoneNumber();
        assertNotNull(phoneNumber);
    }

    /**
     * From the original <tt>main</tt> method test in AppDelegate.
     * @param replyAddress
     * @return
     * @throws EtccException 
     */
    protected boolean contactUs(String replyAddress) throws EtccException {
        // ContactUs params
        long docId = 1;
        String docType = AccountLoginDTO.LoginType.AC.toString();
        String licState = AccountFactory.LICENSE_STATE;
        String licPlate = AccountFactory.LICENSE_PLATE;
        String comment = " ji>''''````a n< <     <<g l___e---a...>>>>v8282838383838383838383838383838383838383838383838383838383838383838@@@@e me\"\"\"s<s!ag#$e@   ";
        String dbSessionId = "1231230das3424324";

        // 1:test getContactPhoneNumber()
        // str = deleg.getContactPhoneNumber();
        // 2:test contactUs()
        return this.delegate.contactUs(docId, docType, licState, licPlate, replyAddress, comment, dbSessionId);
    }

    /**
     * Test method for {@link AppDelegate#contactUs(long, String, String, String, String, String, String)}.
     * @throws Exception if any error occurs
     */
    @Test
    public void testContactUs() throws Exception {
        String replyAddress = "mboroyevich@etcc.com";
        boolean ret = this.contactUs(replyAddress);
        assertTrue("ContactUs should return true on success.", ret);
    }

    /**
     * Test method for {@link AppDelegate#getHelpUrl()}.
     */
    @Test
    public void testGetHelpUrl() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getVeaText(AccountLoginDTO, Invoice[])}.
     */
    @Test
    public void testGetVeaText() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getTablePageSize()}.
     */
    @Test
    public void testGetTablePageSize() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getReportDays()}.
     */
    @Test
    public void testGetReportDays() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getApplicationUrl()}.
     */
    @Test
    public void testGetApplicationUrl() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getPrivacyPolicyUrl()}.
     */
    @Test
    public void testGetPrivacyPolicyUrl() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getSupportedBrowserUrl()}.
     */
    @Test
    public void testGetSupportedBrowserUrl() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#secureCookies()}.
     */
    @Test
    public void testSecureCookies() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getTourUrl()}.
     */
    @Test
    public void testGetTourUrl() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getSplashPageUrl()}.
     */
    @Test
    public void testGetSplashPageUrl() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getPOSId(String)}.
     */
    @Test
    public void testGetPOSId() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getViewTransDefaultRange()}.
     */
    @Test
    public void testGetViewTransDefaultRange() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getHomeTabUrl()}.
     */
    @Test
    public void testGetHomeTabUrl() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getSysParam(String)}.
     */
    @Test
    public void testGetSysParam() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getHomePageDaysBack()}.
     */
    @Test
    public void testGetHomePageDaysBack() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getMyEZTAGMenuLabel()}.
     */
    @Test
    public void testGetMyEZTAGMenuLabel() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getSysParamFromCache(String)}.
     */
    @Test
    public void testGetSysParamFromCache() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getMakePaymentMenuLabel()}.
     */
    @Test
    public void testGetMakePaymentMenuLabel() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getUnpaidTollMenuLabel()}.
     */
    @Test
    public void testGetUnpaidTollMenuLabel() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getHttpPort()}.
     */
    @Test
    public void testGetHttpPort() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getHttpsPort()}.
     */
    @Test
    public void testGetHttpsPort() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#getDomainName()}.
     */
    @Test
    public void testGetDomainName() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }

    /**
     * Test method for {@link AppDelegate#isSwitchProtocol()}.
     * @throws Exception if any error occurs
     */
    @Test
    public void testIsSwitchProtocol() throws Exception {
        // invoke to ensure no exceptions are encountered (returns either true or false)
        this.delegate.isSwitchProtocol();
    }

    /**
     * Test method for {@link AppDelegate#getVehicleYears()}.
     */
    @Test
    public void testGetVehicleYears() {
        logger.warn(BaseTestDelegate.NOT_IMPLEMENTED_MSG);
    }
}
