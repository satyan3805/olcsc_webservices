/**
 * 
 */
package com.etcc.csc.webservice.rest.dto;

import java.math.BigDecimal;
import java.util.List;

public class AccountTransactionHistoryRequest {

	private Long acctId;
	private String loginType;
	private String dbSessionId;
	private String ipAddress;
	private String loginId;
	private String fromDate;
	private String toDate;
	private String dateType;
	private List<Integer> categoryTab;
	private List<Integer> subCategoryTab;
	private List<Integer> acctVehIds;
	private List<Integer> acctTagIds;
	private Integer pageSize;
	private Integer pageNumber;
	private Integer sortOrder;
	private BigDecimal eventId;
	private Integer paramId;
	private Integer maxReturnRows;
	public Long getAcctId() {
		return acctId;
	}
	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getDbSessionId() {
		return dbSessionId;
	}
	public void setDbSessionId(String dbSessionId) {
		this.dbSessionId = dbSessionId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public List<Integer> getCategoryTab() {
		return categoryTab;
	}
	public void setCategoryTab(List<Integer> categoryTab) {
		this.categoryTab = categoryTab;
	}
	public List<Integer> getSubCategoryTab() {
		return subCategoryTab;
	}
	public void setSubCategoryTab(List<Integer> subCategoryTab) {
		this.subCategoryTab = subCategoryTab;
	}
	public List<Integer> getAcctVehIds() {
		return acctVehIds;
	}
	public void setAcctVehIds(List<Integer> acctVehIds) {
		this.acctVehIds = acctVehIds;
	}
	public List<Integer> getAcctTagIds() {
		return acctTagIds;
	}
	public void setAcctTagIds(List<Integer> acctTagIds) {
		this.acctTagIds = acctTagIds;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public BigDecimal getEventId() {
		return eventId;
	}
	public void setEventId(BigDecimal eventId) {
		this.eventId = eventId;
	}
	public Integer getParamId() {
		return paramId;
	}
	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}
	public Integer getMaxReturnRows() {
		return maxReturnRows;
	}
	public void setMaxReturnRows(Integer maxReturnRows) {
		this.maxReturnRows = maxReturnRows;
	}
	@Override
	public String toString() {
		return "AccountTransactionHistoryRequest [acctId=" + acctId + ", loginType=" + loginType + ", dbSessionId="
				+ dbSessionId + ", ipAddress=" + ipAddress + ", loginId=" + loginId + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", dateType=" + dateType + ", categoryTab=" + categoryTab + ", subCategoryTab="
				+ subCategoryTab + ", acctVehIds=" + acctVehIds + ", acctTagIds=" + acctTagIds + ", pageSize="
				+ pageSize + ", pageNumber=" + pageNumber + ", sortOrder=" + sortOrder + ", eventId=" + eventId
				+ ", paramId=" + paramId + ", maxReturnRows=" + maxReturnRows + "]";
	}

	
}
