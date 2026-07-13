/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.dao;

import com.etcc.csc.common.EtccSysException;

/**
 * Exception normally thrown and caught by the Aspects.  Some classes, such as Menu, do not throw any Checked
 * Exceptions, so this Exception is to handle those cases.  Should NOT be instatiated by anything but a Connection
 * Managing Aspect.
 * @author (task 488) Stephen Davidson
 *
 */
//Needs to be public, as instantiated by code injected by the Aspects into the Services.
public class EtccConnectionException extends EtccSysException {

    /**
     * Unique ID for serialization with version.
     */
    private static final long serialVersionUID = -3850416350617641090L;

    /**
     * Constructor.
     * @param t the original exception.
     */
    public EtccConnectionException(Throwable t) {
        super(t);
    }

}
