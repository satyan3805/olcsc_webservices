package com.etcc.csc.common;

import java.util.ArrayList;
import java.util.Collection;

public class EtccErrorMessageException extends Exception {
	private static final long serialVersionUID = -3650830955017152978L;

	public EtccErrorMessageException() {
	}

	public EtccErrorMessageException(String message) {
		super(message);
	}

	private Collection errorMessages;

	public void addRecoverable(String errorMessage) {
		if (getErrorMessages() == null) {
			setErrorMessages(new ArrayList());
		}
		getErrorMessages().add(errorMessage);
	}

	public void setErrorMessages(Collection errorMessages) {
		this.errorMessages = errorMessages;
	}

	public Collection getErrorMessages() {
		return errorMessages;
	}
}
