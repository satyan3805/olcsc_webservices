package com.etcc.csc.dto;

public class DocumentUploadResponse extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1063583136414834186L;
	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DcoumentUploadResponse [message=" + message + "]";
	}

}
