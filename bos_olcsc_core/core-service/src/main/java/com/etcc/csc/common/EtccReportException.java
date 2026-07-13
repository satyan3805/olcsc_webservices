package com.etcc.csc.common;

/**
 * Exception thrown when trying to create a report.
 */
public class EtccReportException extends Exception {
	private static final long serialVersionUID = 3462527843163171778L;

	public EtccReportException() {
	}

	public EtccReportException(String msg, Throwable t) {
		super(msg, t);
	}

	public EtccReportException(String msg) {
		super(msg);
	}

	public EtccReportException(Throwable t) {
		super(t);
	}

}
