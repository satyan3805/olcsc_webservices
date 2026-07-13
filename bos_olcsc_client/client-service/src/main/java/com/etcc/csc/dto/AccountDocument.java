package com.etcc.csc.dto;

import java.util.Date;

public class AccountDocument {
	private Long accountDocumentId;
	private Long accountId;
	private String fileLocation;
	private String fileName;
	private String documentType;
	private String description;
	private Date generatedDate;

	/**
	 * @return the accountD
	 * ocumentId
	 */
	public Long getAccountDocumentId() {
		return accountDocumentId;
	}

	/**
	 * @param accountDocumentId
	 *            the accountDocumentId to set
	 */
	public void setAccountDocumentId(Long accountDocumentId) {
		this.accountDocumentId = accountDocumentId;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the fileLocation
	 */
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * @param fileLocation
	 *            the fileLocation to set
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	@Override
	public String toString() {
		return "AccountDocument [accountDocumentId=" + accountDocumentId + ", accountId=" + accountId
				+ ", fileLocation=" + fileLocation + ", fileName=" + fileName + ", documentType=" + documentType
				+ ", description=" + description + ", generatedDate=" + generatedDate + "]";
	}

	
}
