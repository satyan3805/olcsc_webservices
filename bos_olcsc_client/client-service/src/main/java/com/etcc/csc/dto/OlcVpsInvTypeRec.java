package com.etcc.csc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OlcVpsInvTypeRec implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6379628508717124020L;
	private BigDecimal invoiceId;
	private String invoiceNumber;
	private Date invoiceDate;
	private String licPlate;
	private String licState;
	private Date dueDate;
	private String agencyId;
	private String agencyName;
	private String invEscStCd;
	private BigDecimal openTollAmount;
	private BigDecimal origTollAmount;
	private BigDecimal openAdminFees;
	private BigDecimal origAdminFees;
	private BigDecimal openCollectionFees;
	private BigDecimal origCollectionFees;
	private BigDecimal openOtherFees;
	private BigDecimal origOtherFees;
	private BigDecimal invoiceAmt;
	private BigDecimal invPaidAmount;
	private BigDecimal invOpenAmount;
	private BigDecimal invExcAmount;
	private BigDecimal invFeeOpenAmt;
	private BigDecimal invFeeExcAmt;
	private BigDecimal invDueAfterExcAmt;
	private List<OlcVpsInvRec> lineItems = new ArrayList<OlcVpsInvRec>();
	private String discountRule;
	private BigDecimal acctConvExcAmt;
	private BigDecimal invDueAfterAcctConvExAmt;
	private BigDecimal invoiceFineAmount;
	private List<OlcOpenFeeRec> openFeeArr = new ArrayList<OlcOpenFeeRec>();

	public OlcVpsInvTypeRec() {
		super();
	}

	public BigDecimal getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(BigDecimal invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getLicPlate() {
		return licPlate;
	}

	public void setLicPlate(String licPlate) {
		this.licPlate = licPlate;
	}

	public String getLicState() {
		return licState;
	}

	public void setLicState(String licState) {
		this.licState = licState;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getInvEscStCd() {
		return invEscStCd;
	}

	public void setInvEscStCd(String invEscStCd) {
		this.invEscStCd = invEscStCd;
	}

	public BigDecimal getOpenTollAmount() {
		return openTollAmount;
	}

	public void setOpenTollAmount(BigDecimal openTollAmount) {
		this.openTollAmount = openTollAmount;
	}

	public BigDecimal getOrigTollAmount() {
		return origTollAmount;
	}

	public void setOrigTollAmount(BigDecimal origTollAmount) {
		this.origTollAmount = origTollAmount;
	}

	public BigDecimal getOpenAdminFees() {
		return openAdminFees;
	}

	public void setOpenAdminFees(BigDecimal openAdminFees) {
		this.openAdminFees = openAdminFees;
	}

	public BigDecimal getOrigAdminFees() {
		return origAdminFees;
	}

	public void setOrigAdminFees(BigDecimal origAdminFees) {
		this.origAdminFees = origAdminFees;
	}

	public BigDecimal getOpenCollectionFees() {
		return openCollectionFees;
	}

	public void setOpenCollectionFees(BigDecimal openCollectionFees) {
		this.openCollectionFees = openCollectionFees;
	}

	public BigDecimal getOrigCollectionFees() {
		return origCollectionFees;
	}

	public void setOrigCollectionFees(BigDecimal origCollectionFees) {
		this.origCollectionFees = origCollectionFees;
	}

	public BigDecimal getOpenOtherFees() {
		return openOtherFees;
	}

	public void setOpenOtherFees(BigDecimal openOtherFees) {
		this.openOtherFees = openOtherFees;
	}

	public BigDecimal getOrigOtherFees() {
		return origOtherFees;
	}

	public void setOrigOtherFees(BigDecimal origOtherFees) {
		this.origOtherFees = origOtherFees;
	}

	public BigDecimal getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(BigDecimal invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public BigDecimal getInvPaidAmount() {
		return invPaidAmount;
	}

	public void setInvPaidAmount(BigDecimal invPaidAmount) {
		this.invPaidAmount = invPaidAmount;
	}

	public BigDecimal getInvOpenAmount() {
		return invOpenAmount;
	}

	public void setInvOpenAmount(BigDecimal invOpenAmount) {
		this.invOpenAmount = invOpenAmount;
	}

	public BigDecimal getInvExcAmount() {
		return invExcAmount;
	}

	public void setInvExcAmount(BigDecimal invExcAmount) {
		this.invExcAmount = invExcAmount;
	}

	public BigDecimal getInvFeeOpenAmt() {
		return invFeeOpenAmt;
	}

	public void setInvFeeOpenAmt(BigDecimal invFeeOpenAmt) {
		this.invFeeOpenAmt = invFeeOpenAmt;
	}

	public BigDecimal getInvFeeExcAmt() {
		return invFeeExcAmt;
	}

	public void setInvFeeExcAmt(BigDecimal invFeeExcAmt) {
		this.invFeeExcAmt = invFeeExcAmt;
	}

	public BigDecimal getInvDueAfterExcAmt() {
		return invDueAfterExcAmt;
	}

	public void setInvDueAfterExcAmt(BigDecimal invDueAfterExcAmt) {
		this.invDueAfterExcAmt = invDueAfterExcAmt;
	}

	public List<OlcVpsInvRec> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<OlcVpsInvRec> lineItems) {
		this.lineItems = lineItems;
	}

	public String getDiscountRule() {
		return discountRule;
	}

	public void setDiscountRule(String discountRule) {
		this.discountRule = discountRule;
	}

	public BigDecimal getAcctConvExcAmt() {
		return acctConvExcAmt;
	}

	public void setAcctConvExcAmt(BigDecimal acctConvExcAmt) {
		this.acctConvExcAmt = acctConvExcAmt;
	}

	public BigDecimal getInvDueAfterAcctConvExAmt() {
		return invDueAfterAcctConvExAmt;
	}

	public void setInvDueAfterAcctConvExAmt(BigDecimal invDueAfterAcctConvExAmt) {
		this.invDueAfterAcctConvExAmt = invDueAfterAcctConvExAmt;
	}

	public BigDecimal getInvoiceFineAmount() {
		return invoiceFineAmount;
	}

	public void setInvoiceFineAmount(BigDecimal invoiceFineAmount) {
		this.invoiceFineAmount = invoiceFineAmount;
	}

	public List<OlcOpenFeeRec> getOpenFeeArr() {
		return openFeeArr;
	}

	public void setOpenFeeArr(List<OlcOpenFeeRec> openFeeArr) {
		this.openFeeArr = openFeeArr;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OlcVpsInvTypeRec [invoiceId=" + invoiceId + ", invoiceNumber=" + invoiceNumber + ", invoiceDate="
				+ invoiceDate + ", licPlate=" + licPlate + ", licState=" + licState + ", dueDate=" + dueDate
				+ ", agencyId=" + agencyId + ", agencyName=" + agencyName + ", invEscStCd=" + invEscStCd
				+ ", openTollAmount=" + openTollAmount + ", origTollAmount=" + origTollAmount + ", openAdminFees="
				+ openAdminFees + ", origAdminFees=" + origAdminFees + ", openCollectionFees=" + openCollectionFees
				+ ", origCollectionFees=" + origCollectionFees + ", openOtherFees=" + openOtherFees + ", origOtherFees="
				+ origOtherFees + ", invoiceAmt=" + invoiceAmt + ", invPaidAmount=" + invPaidAmount + ", invOpenAmount="
				+ invOpenAmount + ", invExcAmount=" + invExcAmount + ", invFeeOpenAmt=" + invFeeOpenAmt
				+ ", invFeeExcAmt=" + invFeeExcAmt + ", invDueAfterExcAmt=" + invDueAfterExcAmt + ", lineItems="
				+ lineItems + ", discountRule=" + discountRule + ", acctConvExcAmt=" + acctConvExcAmt
				+ ", invDueAfterAcctConvExAmt=" + invDueAfterAcctConvExAmt + ", invoiceFineAmount=" + invoiceFineAmount
				+ ", openFeeArr=" + openFeeArr + "]";
	}

}
