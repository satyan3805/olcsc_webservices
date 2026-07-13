package com.etcc.csc.common;

public class EtccSysException extends RuntimeException {
	private static final long serialVersionUID = -3983482023187161369L;

	public EtccSysException() {
	}

	public EtccSysException(String msg, Throwable t) {
		super(msg, t);
	}

	public EtccSysException(String msg) {
		super(msg);
	}

	public EtccSysException(Throwable t) {
		super(t);
	}
}
