/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.presentation.action.accountManagement.AccountSummaryAction;
import com.etcc.csc.service.AccountFactory;

/**
 * Tests the AccountAlertDelegate.
 * @author Stephen Davidson
 */
public class AccountAlertDelegateTest {

    AccountAlertDelegate delegate;

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
        this.delegate = new AccountAlertDelegate();
    }

    /**
     * Test method for AccountAlertDelegate.getAcctSummAlertsRequest(AccountLoginDTO, HttpServletRequest)}.
     * @throws Exception If any exceptions occur during testing.
     * @deprecated method moved from delegate to {@link AccountSummaryAction#getAcctSummAlertsRequest}
     */
    @Deprecated
    @Test
    public void testGetAcctSummAlertsRequest() throws Exception {
//        final Mockery mockery = new Mockery();
//        final HttpServletRequest request = mockery.mock(HttpServletRequest.class);
//        mockery.checking(new Expectations(){{
//            allowing(request).getContextPath();
//            will(returnValue("http://localhost"));
//        }});
//
//        AlertDTO[] alerts = this.delegate.getAcctSummAlertsRequest(AccountUtil.getAccountLoginDTO(AccountUtil.POPULATED_ACCOUNT_ID));
//        assertNotNull("No alerts retrieved for populated test account", alerts);
//        alerts = this.delegate.getAcctSummAlertsRequest(AccountUtil.getAccountLoginDTO(AccountUtil.BASIC_ACCOUNT_ID));
//        assertNull("Alerts retrieved for empty test account", alerts);
    }

    /**
     * Test method for {@link AccountAlertDelegate#getAcctSummAlerts(AccountLoginDTO)}.
     * @throws Exception If any exceptions occur during testing.
     */
    @Test
    public void testGetAcctSummAlerts() throws Exception{
        AlertDTO[] alerts = this.delegate.getAcctSummAlerts(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID));
        assertNotNull("No alerts retrieved for populated test account", alerts);
        alerts = this.delegate.getAcctSummAlerts(AccountFactory.getAccountLoginDTO(AccountFactory.BASIC_ACCOUNT_ID));
        assertNull("Alerts retrieved for empty test account", alerts);
    }

    /**
     * Test method for {@link AccountAlertDelegate#getVehicleInfoAlerts(AccountLoginDTO)}.
     * @throws Exception If any exceptions occur during testing.
     */
    @Test
    public void testGetVehicleInfoAlerts() throws Exception{
        AlertDTO[] alerts = this.delegate.getVehicleInfoAlerts(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID));
        assertNotNull("No alerts retrieved for populated test account", alerts);
        alerts = this.delegate.getVehicleInfoAlerts(AccountFactory.getAccountLoginDTO(AccountFactory.BASIC_ACCOUNT_ID));
        assertNull("Alerts retrieved for empty test account", alerts);
    }

    /**
     * Test method for {@link AccountAlertDelegate#getContactInfoAlerts(AccountLoginDTO)}.
     * @throws Exception If any exceptions occur during testing.
     */
    @Test
    public void testGetContactInfoAlerts() throws Exception{
        AlertDTO[] alerts = this.delegate.getContactInfoAlerts(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID));
//        assertNotNull("No alerts retrieved for populated test account", alerts);
//        alerts = this.delegate.getContactInfoAlerts(AccountUtil.getAccountLoginDTO(AccountUtil.BASIC_ACCOUNT_ID));
//        assertNull("Alerts retrieved for empty test account", alerts);
        assertNull("Tests need to be written for this case.", alerts);
    }

    /**
     * Test method for {@link AccountAlertDelegate#addressCleanseAlertResponse(AccountLoginDTO, String)}.
     * @throws Exception If any exceptions occur during testing.
     */
    @Test
    public void testAddressCleanseAlertResponse() throws Exception{
        ResultDTO test = this.delegate.addressCleanseAlertResponse(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID), "Test");
        assertNull("Tests need to be written for this test case.",test);
        //end
    }
}
