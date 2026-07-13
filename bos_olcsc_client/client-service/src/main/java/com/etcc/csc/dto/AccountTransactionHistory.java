package com.etcc.csc.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AccountTransactionHistory {

	private Date postedDate;
	private Date transactionDate;
	private String itemId;
	private String itemType;
	private String itemInfo;
	private String itemDesc;
	private String itemLocation;
	private String itemVehicle;
	private BigDecimal itemAmount;
	private BigDecimal accountBalance;
	private BigDecimal depositAmount;
	private String itemFlag;
	private String itemGrouping;
	private String csrName;
	private String paymentId;
	private String shiftId;
	private String invoiceNumber;
	private BigDecimal fileSizeBytes;
	private BigDecimal fileGroupId;
	private BigDecimal runningAcctBalance;
	private BigDecimal totalRecord;
	private String qryBlockName;
	private String wildCardAttribute;
	private String categoryName;
	private String subCategoryName;
	private BigDecimal rowNumber;
	private String maxRecordMess;
	private BigDecimal previousBalance;

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(String itemInfo) {
		this.itemInfo = itemInfo;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemLocation() {
		return itemLocation;
	}

	public void setItemLocation(String itemLocation) {
		this.itemLocation = itemLocation;
	}

	public String getItemVehicle() {
		return itemVehicle;
	}

	public void setItemVehicle(String itemVehicle) {
		this.itemVehicle = itemVehicle;
	}

	public BigDecimal getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getItemFlag() {
		return itemFlag;
	}

	public void setItemFlag(String itemFlag) {
		this.itemFlag = itemFlag;
	}

	public String getItemGrouping() {
		return itemGrouping;
	}

	public void setItemGrouping(String itemGrouping) {
		this.itemGrouping = itemGrouping;
	}

	public String getCsrName() {
		return csrName;
	}

	public void setCsrName(String csrName) {
		this.csrName = csrName;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public BigDecimal getFileSizeBytes() {
		return fileSizeBytes;
	}

	public void setFileSizeBytes(BigDecimal fileSizeBytes) {
		this.fileSizeBytes = fileSizeBytes;
	}

	public BigDecimal getFileGroupId() {
		return fileGroupId;
	}

	public void setFileGroupId(BigDecimal fileGroupId) {
		this.fileGroupId = fileGroupId;
	}

	public BigDecimal getRunningAcctBalance() {
		return runningAcctBalance;
	}

	public void setRunningAcctBalance(BigDecimal runningAcctBalance) {
		this.runningAcctBalance = runningAcctBalance;
	}

	public BigDecimal getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(BigDecimal totalRecord) {
		this.totalRecord = totalRecord;
	}

	public String getQryBlockName() {
		return qryBlockName;
	}

	public void setQryBlockName(String qryBlockName) {
		this.qryBlockName = qryBlockName;
	}

	public String getWildCardAttribute() {
		return wildCardAttribute;
	}

	public void setWildCardAttribute(String wildCardAttribute) {
		this.wildCardAttribute = wildCardAttribute;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public BigDecimal getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(BigDecimal rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getMaxRecordMess() {
		return maxRecordMess;
	}

	public void setMaxRecordMess(String maxRecordMess) {
		this.maxRecordMess = maxRecordMess;
	}

	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}

	@Override
	public String toString() {
		return "AccountTransactionHistory [postedDate=" + postedDate + ", transactionDate=" + transactionDate
				+ ", itemId=" + itemId + ", itemType=" + itemType + ", itemInfo=" + itemInfo + ", itemDesc=" + itemDesc
				+ ", itemLocation=" + itemLocation + ", itemVehicle=" + itemVehicle + ", itemAmount=" + itemAmount
				+ ", accountBalance=" + accountBalance + ", depositAmount=" + depositAmount + ", itemFlag=" + itemFlag
				+ ", itemGrouping=" + itemGrouping + ", csrName=" + csrName + ", paymentId=" + paymentId + ", shiftId="
				+ shiftId + ", invoiceNumber=" + invoiceNumber + ", fileSizeBytes=" + fileSizeBytes + ", fileGroupId="
				+ fileGroupId + ", runningAcctBalance=" + runningAcctBalance + ", totalRecord=" + totalRecord
				+ ", qryBlockName=" + qryBlockName + ", wildCardAttribute=" + wildCardAttribute + ", categoryName="
				+ categoryName + ", subCategoryName=" + subCategoryName + ", rowNumber=" + rowNumber
				+ ", maxRecordMess=" + maxRecordMess + ", previousBalance=" + previousBalance + "]";
	}

}
