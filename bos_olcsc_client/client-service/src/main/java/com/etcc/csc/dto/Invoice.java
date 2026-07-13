package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Invoice implements Serializable {
	private static final long serialVersionUID = -354688440900236029L;

	private BigDecimal amount;
    private BigDecimal veaAmount;
    private BigDecimal onlineFee;
    private String violatorId;
    private String id;
    private Calendar invoiceDate;
    private Calendar dueDate;
    private String firstName;
    private String lastName;
    private String licPlateNumber;
    private String licPlateState;
    private Violation[] violations;
    private boolean authorized;
    private boolean veaEligible;
    
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setInvoiceDate(Calendar invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Calendar getInvoiceDate() {
        return invoiceDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLicPlateNumber(String licPlateNumber) {
        this.licPlateNumber = licPlateNumber;
    }

    public String getLicPlateNumber() {
        return licPlateNumber;
    }

    public void setLicPlateState(String licPlateState) {
        this.licPlateState = licPlateState;
    }

    public String getLicPlateState() {
        return licPlateState;
    }

    /*
    public void addViolation( Violation violation ) {
        if (  violations == null ) {
            violations = new ArrayList();
        }
        violations.add(violation  );
    }
    */
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public void setVeaAmount(BigDecimal veaAmount) {
        this.veaAmount = veaAmount;
    }

    public BigDecimal getVeaAmount() {
        return veaAmount;
    }

    public void setOnlineFee(BigDecimal onlineFee) {
        this.onlineFee = onlineFee;
    }

    public BigDecimal getOnlineFee() {
        return onlineFee;
    }

    public void setViolatorId(String violatorId) {
        this.violatorId = violatorId;
    }

    public String getViolatorId() {
        return violatorId;
    }

    public void setViolations(Violation[] violations) {
        this.violations = violations;
    }

    public Violation[] getViolations() {
        return violations;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setVeaEligible(boolean veaEligible) {
        this.veaEligible = veaEligible;
    }

    public boolean isVeaEligible() {
        return veaEligible;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("amount=");
        sb.append(amount);
        sb.append(", veaAmount=");
        sb.append(veaAmount);
        sb.append(", onlineFee=");
        sb.append(onlineFee);
        sb.append(", violatorId=");
        sb.append(violatorId);
        sb.append(", id=");
        sb.append(id);
        sb.append(", invoiceDate=");
        sb.append(invoiceDate);
        sb.append(", dueDate=");
        sb.append(dueDate);
        sb.append(", firstName=");
        sb.append(firstName);
        sb.append(", lastName=");
        sb.append(lastName);
        sb.append(", licPlateNumber=");
        sb.append(licPlateNumber);
        sb.append(", licPlateState=");
        sb.append(licPlateState);
        for (int i = 0; i < violations.length; i++) {
            if (violations[i] != null)
                violations[i].toString();
            else
                sb.append("[null]");
        }
        sb.append(", authorized=");
        sb.append(authorized);
        sb.append(", veaEligible=");
        sb.append(veaEligible);
        sb.append("]");
        return sb.toString();
    }
}
