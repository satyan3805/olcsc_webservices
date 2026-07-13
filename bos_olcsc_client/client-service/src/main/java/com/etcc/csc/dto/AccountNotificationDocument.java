package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountNotificationDocument implements Serializable {

	private static final long serialVersionUID = 7244043088416343580L;
	private BigDecimal rowNum;
	private Long accountId;
	private String description;
	private String documentType;
	private Date generatedDate;
	private String notificationStatus;
	private String reportFilePath;

	public BigDecimal getRowNum() {
		return rowNum;
	}

	public void setRowNum(BigDecimal rowNum) {
		this.rowNum = rowNum;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Date getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	@Override
	public String toString() {
		return "AccountNotificationDocument [rowNum=" + rowNum + ", accountId=" + accountId + ", description="
				+ description + ", documentType=" + documentType + ", generatedDate=" + generatedDate
				+ ", notificationStatus=" + notificationStatus + ", reportFilePath=" + reportFilePath + "]";
	}

}
