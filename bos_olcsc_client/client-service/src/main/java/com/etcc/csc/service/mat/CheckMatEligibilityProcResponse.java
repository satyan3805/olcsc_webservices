package com.etcc.csc.service.mat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.etcc.csc.service.webservice.ViolationType;

/**
 * Data structure holding the response from calling the Check Mat Eligibility stored procedure
 * @author vsparks
 *
 */
public class CheckMatEligibilityProcResponse {

	private List<ViolationType> violations = null;
	private Boolean isEligible;
	private String errorMessage;
	private Date lastMatOn;
	private Double totalAmount;
	private Double serviceFee;
	private Double matPayAmount;
	//PUSH NOTIFICATION PHASE II START
	private java.lang.String dbSessionId;
	//PUSH NOTIFICATION PHASE II END

    /**
     * Gets the value of the dbSessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public java.lang.String getDbSessionId() {
		return dbSessionId;
	}

    /**
     * Sets the value of the dbSessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setDbSessionId(java.lang.String dbSessionId) {
		this.dbSessionId = dbSessionId;
	}
	
	public List<ViolationType> getViolations() {
		if (violations == null) {
			violations = new ArrayList<ViolationType>();
		}
		return violations;
	}
	public void setViolations(List<ViolationType> violations) {
		this.violations = violations;
	}
	public Boolean getIsEligible() {
		return isEligible;
	}
	public void setIsEligible(Boolean isEligible) {
		this.isEligible = isEligible;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Date getLastMatOn() {
		return lastMatOn;
	}
	public void setLastMatOn(Date lastMatOn) {
		this.lastMatOn = lastMatOn;
	}
	public Double getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getMatPayAmount() {
		return matPayAmount;
	}
	public void setMatPayAmount(Double matPayAmount) {
		this.matPayAmount = matPayAmount;
	}
	

}
