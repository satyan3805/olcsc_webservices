package com.etcc.csc.service.sal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ViolationType;

/**
 * Data structure for holding response from calling CheckSalEligibility stored procedure
 * @author vsparks
 *
 */
public class CheckSalEligibilityProcResponse {
	private List<InvoiceType> invoices = null;
	private List<ViolationType> violations = null;
	private Boolean isEligible;
	private String errorMessage;
	private Date lastFrpOn;
	private Double totalInvoicedAmount;
	private Double totalUninvoicedAmount;
	private Double serviceFee;
	private java.lang.String dbSessionId;

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
	
	public List<InvoiceType> getInvoices() {
		if (invoices == null) 
			invoices = new ArrayList<InvoiceType>();
		return invoices;
	}
	public void setInvoices(List<InvoiceType> invoices) {
		this.invoices = invoices;
	}
	public List<ViolationType> getViolations() {
		if (violations == null) 
			violations = new ArrayList<ViolationType>();
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
	public Date getLastFrpOn() {
		return lastFrpOn;
	}
	public void setLastFrpOn(Date lastFrpOn) {
		this.lastFrpOn = lastFrpOn;
	}
	public Double getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}
	public Double getTotalInvoicedAmount() {
		return totalInvoicedAmount;
	}
	public void setTotalInvoicedAmount(Double totalInvoicedAmount) {
		this.totalInvoicedAmount = totalInvoicedAmount;
	}
	public Double getTotalUninvoicedAmount() {
		return totalUninvoicedAmount;
	}
	public void setTotalUninvoicedAmount(Double totalUninvoicedAmount) {
		this.totalUninvoicedAmount = totalUninvoicedAmount;
	}
	
}
