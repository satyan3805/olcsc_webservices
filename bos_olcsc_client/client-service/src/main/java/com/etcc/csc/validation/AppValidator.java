/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.validation;

import java.util.ArrayList;

import com.etcc.csc.util.StringUtil;

/**
 * Copied from OLCSCService com.etcc.csc.service.validation.AppValidator
 * @author Milosh Boroyevich
 */
public abstract class AppValidator extends BaseValidator {

    /**
     * Should not be instantiated.
     * Constructor.
     */
    private AppValidator() {
    }

    public static void contactUsValidator(long docId, String docType, String licState, 
                                 String licPlate, String replyAddress, 
                                 String comment, 
                                 String dbSessionId) throws ValidationException {
//        comment = cleanSpace(comment);
        ArrayList<String> msgs = new ArrayList<String>();
        if (StringUtil.isEmpty(replyAddress))
            msgs.add("E-Mail Address is required");
        else if (!isValidEmail(replyAddress))
            msgs.add("Invalid email address");
        if (msgs.size() > 0)
            throw new ValidationException(msgs);
    }
}
