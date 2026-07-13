package com.etcc.csc.common;

/**
 * Base System Runtime Exception.  Use very sparingly, for something major
 * has gone wrong in the system that can't be handled.
 *
 */
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
