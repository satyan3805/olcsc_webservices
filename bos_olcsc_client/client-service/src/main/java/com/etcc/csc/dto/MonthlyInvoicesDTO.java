/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;
import java.util.Calendar;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.dto.bean.OlcAccountHistoryRecBean;
import com.etcc.csc.dto.bean.OlcAcctSummaryRecBean;

/**
 * Data Transfer Object for Monthly Invoices.
 */
public class MonthlyInvoicesDTO extends BaseDTO implements Address {
    
    /**
     * Unique ID for serialization with version.
     */
    //Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = -1926436179788580722L;
    
    private long acctId;
    private String statementMonth;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String invoiceNumber;
    private String stmtPeriod;
    private Calendar invoiceDate;
    private double totalAmtDue;
    private OlcAccountHistoryRecBean[] invoiceDetails;
    private double invoiceSubTotal;

    protected OlcAcctSummaryRecBean[] invoiceSummary;
    protected double balSumBalanceRequirement;
    protected double totalOutstandingAmt;
    protected double invSumEndingBalance;
    protected double balSumReplenishmentAmt;
    protected double balSumCurrentBalance;
    protected String dueMonth;
    protected double dueAmount;
    protected OlcAcctSummaryRecBean[] outstandingInvoices;
    protected OlcAcctSummaryRecBean[] suspensionDate;
    
    /**
     * Constructor.
     */
    public MonthlyInvoicesDTO() {
        //end <inti>
    }

    @IgnoreProperty
    public String getName() {
        return this.firstName + ' ' + this.lastName;
    }

    public String getPlus4() {
		return null;
	}
	public void setPlus4(String plus4) { }

	public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setInvoiceDetails(OlcAccountHistoryRecBean[] invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public OlcAccountHistoryRecBean[] getInvoiceDetails() {
        return this.invoiceDetails;
    }

    public void setInvoiceSummary(OlcAcctSummaryRecBean[] invoiceSummary) {
        this.invoiceSummary = invoiceSummary;
    }

    public OlcAcctSummaryRecBean[] getInvoiceSummary() {
        return this.invoiceSummary;
    }
    // added for TO424 Monthly invoices link - begin
    
    public void setSuspensionDate(OlcAcctSummaryRecBean[] suspensionDate) {
        this.suspensionDate = suspensionDate;
    }

    public OlcAcctSummaryRecBean[] getSuspensionDate() {
        return this.suspensionDate;
    }
    
    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public double getDueAmount() {
        return this.dueAmount;
    }
    
    public void setDueMonth(String dueMonth) {
        this.dueMonth = dueMonth;
    }

    public String getDueMonth() {
        return this.dueMonth;
    }
      
    
    // added for TO424 Monthly invoices link - end

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setStatementMonth(String statementMonth) {
        this.statementMonth = statementMonth;
    }

    public String getStatementMonth() {
        return this.statementMonth;
    }

    public void setBalSumBalanceRequirement(double balSumBalanceRequirement) {
        this.balSumBalanceRequirement = balSumBalanceRequirement;
    }

    public double getBalSumBalanceRequirement() {
        return this.balSumBalanceRequirement;
    }

    public void setInvoiceDate(Calendar invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Calendar getInvoiceDate() {
        return this.invoiceDate;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return this.zip;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setTotalOutstandingAmt(double totalOutstandingAmt) {
        this.totalOutstandingAmt = totalOutstandingAmt;
    }

    public double getTotalOutstandingAmt() {
        return this.totalOutstandingAmt;
    }

    public void setInvSumEndingBalance(double invSumEndingBalance) {
        this.invSumEndingBalance = invSumEndingBalance;
    }

    public double getInvSumEndingBalance() {
        return this.invSumEndingBalance;
    }

    public void setBalSumReplenishmentAmt(double balSumReplenishmentAmt) {
        this.balSumReplenishmentAmt = balSumReplenishmentAmt;
    }

    public double getBalSumReplenishmentAmt() {
        return this.balSumReplenishmentAmt;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress4() {
        return this.address4;
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return this.acctId;
    }

    public void setStmtPeriod(String stmtPeriod) {
        this.stmtPeriod = stmtPeriod;
    }

    public String getStmtPeriod() {
        return this.stmtPeriod;
    }

    public void setInvoiceSubTotal(double invoiceSubTotal) {
        this.invoiceSubTotal = invoiceSubTotal;
    }

    public double getInvoiceSubTotal() {
        return this.invoiceSubTotal;
    }

    public void setBalSumCurrentBalance(double balSumCurrentBalance) {
        this.balSumCurrentBalance = balSumCurrentBalance;
    }

    public double getBalSumCurrentBalance() {
        return this.balSumCurrentBalance;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setOutstandingInvoices(OlcAcctSummaryRecBean[] outstandingInvoices) {
        this.outstandingInvoices = outstandingInvoices;
    }

    public OlcAcctSummaryRecBean[] getOutstandingInvoices() {
        return this.outstandingInvoices;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return this.country;
    }

    public void setTotalAmtDue(double totalAmtDue) {
        this.totalAmtDue = totalAmtDue;
    }

    public double getTotalAmtDue() {
        return this.totalAmtDue;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress3() {
        return this.address3;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("address1="); sb.append(this.address1);
        sb.append(", firstName="); sb.append(this.firstName);
        sb.append(", invoiceDetails="); sb.append(Arrays.toString(this.invoiceDetails));
        sb.append(", invoiceSummary="); sb.append(Arrays.toString(this.invoiceSummary));
        sb.append(", address2="); sb.append(this.address2);
        sb.append(", statementMonth="); sb.append(this.statementMonth);
        sb.append(", balSumBalanceRequirement="); sb.append(this.balSumBalanceRequirement);
        sb.append(", invoiceDate="); sb.append(this.invoiceDate);
        sb.append(", zip="); sb.append(this.zip);
        sb.append(", invoiceNumber="); sb.append(this.invoiceNumber);
        sb.append(", city="); sb.append(this.city);
        sb.append(", totalOutstandingAmt="); sb.append(this.totalOutstandingAmt);
        sb.append(", invSumEndingBalance="); sb.append(this.invSumEndingBalance);
        sb.append(", balSumReplenishmentAmt="); sb.append(this.balSumReplenishmentAmt);
        sb.append(", address4="); sb.append(this.address4);
        sb.append(", acctId="); sb.append(this.acctId);
        sb.append(", stmtPeriod="); sb.append(this.stmtPeriod);
        sb.append(", invoiceSubTotal="); sb.append(this.invoiceSubTotal);
        sb.append(", balSumCurrentBalance="); sb.append(this.balSumCurrentBalance);
        sb.append(", lastName="); sb.append(this.lastName);
        sb.append(", outstandingInvoices="); sb.append(Arrays.toString(this.outstandingInvoices));
        sb.append(", country="); sb.append(this.country);
        sb.append(", totalAmtDue="); sb.append(this.totalAmtDue);
        sb.append(", address3="); sb.append(this.address3);
        sb.append(", state="); sb.append(this.state);
        sb.append("]");
        return sb.toString();
    }
}
