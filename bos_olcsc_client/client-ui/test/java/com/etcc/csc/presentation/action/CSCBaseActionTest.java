/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.presentation.MockHttpServletRequest;

/**
 * Unit tests for {@link CSCBaseAction}.
 * @author Milosh Boroyevich
 */
public class CSCBaseActionTest {
    private static final Logger logger = Logger.getLogger(CSCBaseActionTest.class);

    public static final String ACTION_PROPERTY = "CSCBaseActionTest.property";
    public static final int ALERT_ID = 1001;
    public static final String ALERT_MSG = "This is an alert.";
    public static final String ERROR_KEY = "error.key";
    public static final String ERROR_MSG = "This is an error.";

    CSCBaseAction action;
    HttpServletRequest request;
//    private static BeanUtilsBean beanutil;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
//        beanutil = BeanUtilsBean.getInstance();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.action = new CSCBaseAction();
        this.request = new MockHttpServletRequest();
    }

    /**
     * Test for {@link CSCBaseAction#saveAlerts(HttpServletRequest, AlertDTO[])}.
     */
    @Test
    public void testSaveAlerts() throws Exception {
        AlertDTO[] alerts = null;
        boolean retval = action.saveAlerts(request, alerts);
        assertFalse("Save alerts should not be modified when passed empty array.", retval);
        alerts = new AlertDTO[] {
                new AlertDTO(ALERT_ID, ALERT_MSG)
        };
        int size = alerts.length;
        retval = action.saveAlerts(request, alerts);
        assertTrue("Save alerts should have been modified when passed alerts: " + alerts, retval);
        ActionMessages messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
        logger.debug("testSaveAlerts: " + messages);
        assertEquals("Should have " + size + " action message(s).", messages.size(), size);
        testSaveAlert(size);
    }

    /**
     * Test for {@link CSCBaseAction#saveAlert(HttpServletRequest, AlertDTO, String)}.
     */
    @Test
    public void testSaveAlert() throws Exception {
        boolean retval = action.saveAlert(request, null, ACTION_PROPERTY);
        assertFalse("Save alerts should not be modified when passed empty alert.", retval);
        testSaveAlert(0);
    }

    protected void testSaveAlert(int currentSize) {
        currentSize++;
        AlertDTO alert = new AlertDTO(ALERT_ID + currentSize, ALERT_MSG);
        boolean retval = action.saveAlert(request, alert, ACTION_PROPERTY);
        assertTrue("Save alerts should have been modified when passed an alert: " + alert, retval);
        ActionMessages messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
        logger.debug("testSaveAlert: " + messages);
        assertEquals("Should have " + currentSize + " action message(s).", messages.size(), currentSize);
    }

    /**
     * Test for {@link CSCBaseAction#saveErrorMessages(HttpServletRequest, BaseDTO, String)}.
     */
    @Test
    public void testSaveErrorMessagesBaseDto() throws Exception {
        boolean retval = action.saveErrorMessages(request, new ResultDTO().getErrors(), ACTION_PROPERTY);
        assertFalse("Save error messages should not be modified when passed empty errors.", retval);
    }

    /**
     * Test for {@link CSCBaseAction#saveErrorMessages(HttpServletRequest, ErrorMessageDTO[], String)}.
     */
    @Test
    public void testSaveErrorMessages() throws Exception {
        ErrorMessageDTO[] errors = null;
        boolean retval = action.saveErrorMessages(request, errors, ACTION_PROPERTY);
        assertFalse("Save error messages should not be modified when passed empty errors.", retval);
        errors = new ErrorMessageDTO[] {
                new ErrorMessageDTO().withMessage(ERROR_MSG)
        };
        int size = errors.length;
        retval = action.saveErrorMessages(request, errors, ACTION_PROPERTY);
        assertTrue("Save error messages should have been modified when passed errors: " + errors, retval);
        ActionMessages messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
        logger.debug("testSaveErrorMessages: " + messages);
        assertEquals("Should have " + size + " action message(s).", messages.size(), size);
        testSaveErrorMessage(size);
    }

    /**
     * Test for {@link CSCBaseAction#saveErrorMessage(HttpServletRequest, ErrorMessageDTO, String)}.
     */
    @Test
    public void testSaveErrorMessage() throws Exception {
        boolean retval = action.saveErrorMessage(request, null, ACTION_PROPERTY);
        assertFalse("Save error message should not be modified when passed null.", retval);
        testSaveErrorMessage(0);
    }

    protected void testSaveErrorMessage(int currentSize) {
        currentSize++;
        ErrorMessageDTO error = new ErrorMessageDTO(ERROR_KEY, ERROR_MSG + currentSize);
        boolean retval = action.saveErrorMessage(request, error, ACTION_PROPERTY);
        assertTrue("Save error message should have been modified when passed an error: " + error, retval);
        ActionMessages messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
        logger.debug("testSaveErrorMessage: " + messages);
        assertEquals("Should have " + currentSize + " action message(s).", messages.size(), currentSize);
    }
}
