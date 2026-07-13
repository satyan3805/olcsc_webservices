package com.etcc.csc.service.sal;

import java.util.ArrayList;
import java.util.List;

import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ViolationType;

/**
 * Data structure used for holding response from calling CalculateSalPayment stored procedure
 * @author vsparks
 *
 */
public class CalculateSalPaymentProcResponse {
	private List<InvoiceType> invoices = null;
	private List<ViolationType> violations = null;
	private Double wavedFees;
	private Double salPayAmount;
	
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
	public Double getWavedFees() {
		return wavedFees;
	}
	public void setWavedFees(Double wavedFees) {
		this.wavedFees = wavedFees;
	}
	public Double getSalPayAmount() {
		return salPayAmount;
	}
	public void setSalPayAmount(Double salPayAmount) {
		this.salPayAmount = salPayAmount;
	}
	
}
