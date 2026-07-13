/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.common;

/**
 * 
 * @author (task 488) Stephen Davidson
 *
 */
public class EtccException extends Exception {
    private static final long serialVersionUID = -1198751790425376813L;

    private String relatedIssueId;

    /**
     * Constructor.
     */
    public EtccException() {
        //end <init>
    }

    /**
     * Constructor.
     * @param msg Message for the Exception.
     * @param t Original/source exception.
     */
    public EtccException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * Constructor.
     * @param msg The message for the Exception.
     */
    public EtccException(String msg) {
        super(msg);
    }

    /**
     * Constructor.
     * @param t The original Exception.
     */
    public EtccException(Throwable t) {
        super(t);
    }

    @Override
    public String getMessage() {
        String message = this.relatedIssueId != null ? 
                " Related issue id " + this.relatedIssueId + ". " + super.getMessage() : 
                    super.getMessage();

        return message;
    }

    /**
     * @return the relatedIssueId
     */
    public String getRelatedIssueId() {
        return this.relatedIssueId;
    }

    /**
     * @param relatedIssueId the relatedIssueId to set
     */
    public void setRelatedIssueId(String relatedIssueId) {
        this.relatedIssueId = relatedIssueId;
    }

}
