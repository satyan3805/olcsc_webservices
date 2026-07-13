package com.etcc.csc.payment.checkout.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Status implements Serializable {
	private static final long serialVersionUID = -2441285624193933429L;

	@XmlElement(name = "error_code", required = true)
	private Long code;
	@XmlElement(name = "error_message", required = true)
	private String errorMessage;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}