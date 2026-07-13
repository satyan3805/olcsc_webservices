package com.etcc.csc.common;

/**
 * Exception thrown when a security violation is caught.
 * Needs to extend Exception as RuntimeException gets swallowed as RemoteException in
 * web services.
 */
public class EtccSecurityException extends EtccException {

    private static final long serialVersionUID = 6833245972806621068L;

    /**
     * Constructor.
     */
    public EtccSecurityException() {
        super();
    }

    /**
     * Constructor.
     * @param msg Message for this Exception.
     * @param t Source Exception.
     */
    public EtccSecurityException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * Constructor.
     * @param msg Message for this Exception.
     */
    public EtccSecurityException(String msg) {
        super(msg);
    }

    /**
     * Constructor.
     * @param t Source (Original) Exception.
     */
    public EtccSecurityException(Throwable t) {
        super(t);
    }

}
