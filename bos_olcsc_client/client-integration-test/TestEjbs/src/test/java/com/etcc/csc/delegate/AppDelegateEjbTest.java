/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import org.junit.BeforeClass;
import org.junit.Test;

import com.etcc.csc.validation.ValidationException;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class AppDelegateEjbTest extends AppDelegateTest{
    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseDelegateEjb.setUpBeforeClass();
    }

    /**
     * Validation test method for {@link AppDelegate#contactUs(long, String, String, String, String, String, String)}.
     * @throws Exception if any error occurs
     */
    @Test(expected=ValidationException.class)
    public void testInvalidContactUs() throws Exception {
        String replyAddress = "   ji>''''````a``a n< <     <<g l___e---a...>>>>v828238 n<<<<g l___e---a...>>>>v@@@@e me\"\"\"s<s!ag#$e@    ";
        this.contactUs(replyAddress);
    }
}
