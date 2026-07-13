/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation;

import org.apache.struts.action.ActionMessage;
import com.etcc.csc.common.EtccException;

/**
 * Exception used in the presentation layer with support for handling of action messages.
 * @author Milosh Boroyevich
 * @see ActionMessage
 */
public class EtccActionException extends EtccException {
    private static final long serialVersionUID = 1229144481538756417L;

    private ActionMessage actionMessage = null;

	public EtccActionException(String msg, Throwable t) {
		super(msg, t);
	}

	public EtccActionException(String msg) {
		super(msg);
	}

	public EtccActionException(Throwable t) {
		super(t);
	}

	public EtccActionException(String msg, ActionMessage a, Throwable t) {
		super(msg, t);
		setActionMessage(a);
	}

	public EtccActionException(String msg, ActionMessage a) {
		super(msg);
		setActionMessage(a);
	}

	public EtccActionException(ActionMessage a, Throwable t) {
		super(a.getKey(), t);
		setActionMessage(a);
	}

	public EtccActionException(ActionMessage a) {
		super(a.getKey());
		setActionMessage(a);
	}

	public void setActionMessage(ActionMessage actionMessage) {
		this.actionMessage = actionMessage;
	}

	public ActionMessage getActionMessage() {
		return actionMessage;
	}
}
