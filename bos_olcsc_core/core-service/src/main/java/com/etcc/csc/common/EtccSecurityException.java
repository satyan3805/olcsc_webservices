package com.etcc.csc.common;

/**
 * Exception thrown when a security violation is caught. Needs to extend
 * Exception as RuntimeException gets swallowed as RemoteException in web
 * services.
 */
public class EtccSecurityException extends Exception {
	private static final long serialVersionUID = -3734387408342525999L;
	private String message;

	public EtccSecurityException() {
	}

	public EtccSecurityException(String msg, Throwable t) {
		super(msg, t);
	}

	public EtccSecurityException(String msg) {
		super(msg);
	}

	public EtccSecurityException(Throwable t) {
		super(t);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		if (message != null) {
			return message;
		} else {
			return super.getMessage();
		}
	}

}
