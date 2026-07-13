package com.etcc.csc.webservice.rest.dto;

import java.math.BigDecimal;

public class GetInitialAutoChargeAmountsRequest {
	private Long acctId;
	private String loginType;
	private String dbSessionId;
	private String ipAddress;
	private String loginId;
	private BigDecimal acctTypeId;
	private BigDecimal acctPlanId;
	private BigDecimal planDetailId;
	private BigDecimal paymentFormId;
	private BigDecimal vehicleCount;

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

	public BigDecimal getAcctTypeId() {
		return acctTypeId;
	}

	public void setAcctTypeId(BigDecimal acctTypeId) {
		this.acctTypeId = acctTypeId;
	}

	public BigDecimal getAcctPlanId() {
		return acctPlanId;
	}

	public void setAcctPlanId(BigDecimal acctPlanId) {
		this.acctPlanId = acctPlanId;
	}

	public BigDecimal getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(BigDecimal planDetailId) {
		this.planDetailId = planDetailId;
	}

	public BigDecimal getPaymentFormId() {
		return paymentFormId;
	}

	public void setPaymentFormId(BigDecimal paymentFormId) {
		this.paymentFormId = paymentFormId;
	}

	public BigDecimal getVehicleCount() {
		return vehicleCount;
	}

	public void setVehicleCount(BigDecimal vehicleCount) {
		this.vehicleCount = vehicleCount;
	}

	@Override
	public String toString() {
		return "GetInitialAutoChargeAmountsRequest [acctId=" + acctId + ", loginType=" + loginType + ", dbSessionId="
				+ dbSessionId + ", ipAddress=" + ipAddress + ", loginId=" + loginId + ", acctTypeId=" + acctTypeId
				+ ", acctPlanId=" + acctPlanId + ", PlanDetailId=" + planDetailId + ", paymentFormId=" + paymentFormId
				+ ", vehicleCount=" + vehicleCount + "]";
	}

}
