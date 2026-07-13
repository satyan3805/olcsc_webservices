package com.etcc.csc.webservice.rest.dto;

public class DocumentUploadRequest {
	private Long acctId;
	private String loginType;
	private String dbSessionId;
	private String lastLoginIp;
	private String loginId;
	private String fileLocation;
	private String fileName;
	private String action;
	private Long accountDocumentId;
	private String documentType;
	private String description;
	/**
	 * @return the acctId
	 */
	public Long getAcctId() {
		return acctId;
	}
	/**
	 * @param acctId the acctId to set
	 */
	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}
	/**
	 * @return the loginType
	 */
	public String getLoginType() {
		return loginType;
	}
	/**
	 * @param loginType the loginType to set
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	/**
	 * @return the dbSessionId
	 */
	public String getDbSessionId() {
		return dbSessionId;
	}
	/**
	 * @param dbSessionId the dbSessionId to set
	 */
	public void setDbSessionId(String dbSessionId) {
		this.dbSessionId = dbSessionId;
	}
	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return the fileLocation
	 */
	public String getFileLocation() {
		return fileLocation;
	}
	/**
	 * @param fileLocation the fileLocation to set
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
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the accountDocumentId
	 */
	public Long getAccountDocumentId() {
		return accountDocumentId;
	}
	/**
	 * @param accountDocumentId the accountDocumentId to set
	 */
	public void setAccountDocumentId(Long accountDocumentId) {
		this.accountDocumentId = accountDocumentId;
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
	@Override
	public String toString() {
		return "DocumentUploadRequest [acctId=" + acctId + ", loginType=" + loginType + ", dbSessionId=" + dbSessionId
				+ ", lastLoginIp=" + lastLoginIp + ", loginId=" + loginId + ", fileLocation=" + fileLocation
				+ ", fileName=" + fileName + ", action=" + action + ", accountDocumentId=" + accountDocumentId
				+ ", documentType=" + documentType + ", description=" + description + "]";
	}
	
	
	
}
