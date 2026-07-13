package com.etcc.csc.common;

public class EtccException extends Exception {
	private static final long serialVersionUID = -1198751790425376813L;
	private String message;
	private String relatedIssueId;

	public EtccException() {
	}

	public EtccException(String msg, Throwable t) {
		super(msg, t);
	}

	public EtccException(String msg) {
		super(msg);
	}

	public EtccException(Throwable t) {
		super(t);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		if (message != null) {
			if (relatedIssueId != null) {
				message = " Related issue id " + relatedIssueId + ". "
						+ message;
			}
			return message;
		} else {
			return super.getMessage();
		}
	}

	public void setRelatedIssueId(String relatedIssueId) {
		this.relatedIssueId = relatedIssueId;
	}

	public String getRelatedIssueId() {
		return relatedIssueId;
	}
}
