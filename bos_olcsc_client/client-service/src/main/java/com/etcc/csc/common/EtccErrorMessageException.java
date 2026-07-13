/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.common;

import java.util.ArrayList;
import java.util.Collection;

public class EtccErrorMessageException extends EtccException {
    private static final long serialVersionUID = -3650830955017152978L;

    private Collection<String> errorMessages;

    public EtccErrorMessageException() {
    }

    public EtccErrorMessageException(String message) {
        super(message);
    }

    public EtccErrorMessageException(String message, Throwable t) {
        super(message, t);
    }

    public void addRecoverable(String errorMessage) {
        if (this.errorMessages == null)
            this.errorMessages = new ArrayList<String>();
        this.errorMessages.add(errorMessage);
    }

    public void setErrorMessages(Collection<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Collection<String> getErrorMessages() {
        return this.errorMessages;
    }
}
