/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.validation;

import java.util.Arrays;
import java.util.Collection;

import com.etcc.csc.common.EtccException;

/**
 * Thrown when a Validator fails validation.
 * @author (task 488) Stephen Davidson
 *
 */
public class ValidationException extends EtccException {

    /**
     * Unique ID for serialization.
     */
    private static final long serialVersionUID = 882203844269018112L;
    
    private String[] messages;
    
    /**
     * Constructor.
     */
    public ValidationException() {
        //end <init>
    }

    /**
     * Constructor.
     * @param msg The Message for the Exception.
     */
    public ValidationException(String msg) {
        super(msg);
    }

    /**
     * Constructor.
     * @param messages The validation error messages.
     */
    public ValidationException(Collection<String> messages) {
        super();
        this.messages = messages == null ? null : messages.toArray(new String[messages.size()]);
    }

    /**
     * Adds additional error messages to this Validation Exception.
     * @param newMessages The messages to add.
     */
    public void addErrorMessages(Collection<String> newMessages){
        String[] messages = getErrorMessages();
        int count = messages.length;
        this.messages = new String[count + newMessages.size()];
        System.arraycopy(messages, 0, this.messages, 0, count);
        for (String string : newMessages) {
            this.messages[count++] = string;
        }
    }
    /**
     * Gets the error messages, or if not set, an array populated with the (single) Message.
     * @return The Validation Errors for this Exception.
     */
    public String[] getErrorMessages(){
        return this.messages == null ? new String[]{super.getMessage()} : this.messages;
    }

    @Override
    public String getMessage() {
        return Arrays.toString(getErrorMessages());
    }

}
