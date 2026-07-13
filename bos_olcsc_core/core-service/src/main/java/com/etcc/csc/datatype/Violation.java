package com.etcc.csc.datatype;

import java.math.BigDecimal;
import java.util.Calendar;

public class Violation {
	private BigDecimal cashAmount;
	private BigDecimal aviAmount;
	private String id;
	private Calendar timestamp;
	private String location;
	private String locationDesc;
	private String status;
	private String type;
	private String licPlate;
	private String licState;
	private String citationNumber;
	private BigDecimal violatorId;
	private BigDecimal onlineFee;
	private boolean authorized;
	private String retaiDetailId;

	public Violation() {
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setAviAmount(BigDecimal aviAmount) {
		this.aviAmount = aviAmount;
	}

	public BigDecimal getAviAmount() {
		return aviAmount;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setLicPlate(String licPlate) {
		this.licPlate = licPlate;
	}

	public String getLicPlate() {
		return licPlate;
	}

	public void setLicState(String licState) {
		this.licState = licState;
	}

	public String getLicState() {
		return licState;
	}

	public void setViolatorId(BigDecimal violatorId) {
		this.violatorId = violatorId;
	}

	public BigDecimal getViolatorId() {
		return violatorId;
	}

	public void setOnlineFee(BigDecimal onlineFee) {
		this.onlineFee = onlineFee;
	}

	public BigDecimal getOnlineFee() {
		return onlineFee;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setCitationNumber(String citationNumber) {
		this.citationNumber = citationNumber;
	}

	public String getCitationNumber() {
		return citationNumber;
	}

	public String getRetaiDetailId() {
		return retaiDetailId;
	}

	public void setRetaiDetailId(String retaiDetailId) {
		this.retaiDetailId = retaiDetailId;
	}
}
