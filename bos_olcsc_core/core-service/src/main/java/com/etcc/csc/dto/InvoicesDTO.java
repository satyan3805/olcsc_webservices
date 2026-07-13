package com.etcc.csc.dto;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import com.etcc.csc.common.BaseDTO;

public class InvoicesDTO extends BaseDTO{
	
	
	private BigDecimal invoiceId;
	private String invoiceType;
	private Timestamp invoiceDate;
	private String invioceUrl;
	private String invoiceStatusReason;
	private Timestamp invoiceDueDate;
	
	
	
	public String getInvioceUrl() {
		return invioceUrl;
	}

	public void setInvioceUrl(String invioceUrl) {
		this.invioceUrl = invioceUrl;
	}

	public BigDecimal getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(BigDecimal bigDecimal) {
		this.invoiceId = bigDecimal;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Timestamp timestamp) {
		this.invoiceDate = timestamp;
	}



  public InvoicesDTO() {
  }
  
  
  public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[");
      sb.append("invoiceId=");
      sb.append(invoiceId);
      sb.append(", invoiceType=");
      sb.append(invoiceType);
      sb.append(", postedDate=");
      sb.append(invoiceDate);
      sb.append(", invoice status/reason=");
      sb.append(invoiceStatusReason);
      sb.append(", invoice dueDate=");
      sb.append(invoiceDueDate);
     
      sb.append("]");
      return sb.toString();
  }


	public void setInvoiceDueDate(Timestamp invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}
	
	public Timestamp getInvoiceDueDate() {
		return invoiceDueDate;
	}
	
	public void setInvoiceStatusReason(String invoiceStatusReason) {
		this.invoiceStatusReason = invoiceStatusReason;
	}

	public String getInvoiceStatusReason() {
		return invoiceStatusReason;
	}

}
