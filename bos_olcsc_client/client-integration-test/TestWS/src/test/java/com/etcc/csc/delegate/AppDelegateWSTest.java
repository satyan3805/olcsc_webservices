/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.apache.log4j.Logger;
import org.codehaus.xfire.XFireException;
import org.codehaus.xfire.XFireRuntimeException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class AppDelegateWSTest extends AppDelegateTest {
    private static final Logger logger = Logger.getLogger(AppDelegateWSTest.class);

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateWS.setUpBeforeClass();
    }

    /**
     * Validation test method for {@link AppDelegate#contactUs(long, String, String, String, String, String, String)}.
     * @throws Throwable 
     */
    @Test(expected=XFireException.class)
    public void testInvalidContactUs() throws Throwable {
        String replyAddress = "   ji>''''````a``a n< <     <<g l___e---a...>>>>v828238 n<<<<g l___e---a...>>>>v@@@@e me\"\"\"s<s!ag#$e@    ";
        try {
            contactUs(replyAddress);
        } catch (XFireRuntimeException e) {
            logger.debug(e.getMessage(), e);
            Throwable cause = e;
            while (cause.getCause() != null && cause.getCause() != cause) {
                cause = cause.getCause();
                logger.debug("Nested cause: " + cause.getClass(), cause);
            }
            // should be a ValidationException, but XFire does some weird wrapping!
            throw cause;
        }
    }
}
