
package com.etcc.csc.service.sal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ViolationType;

/**
 * <p>Java class for PostSalEftPaymentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostSalEftPaymentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="licPlateNum" type="{http://www.hctra.org/schema/attlas}LicensePlateNumberType"/>
 *         &lt;element name="jurisdiction" type="{http://www.hctra.org/schema/attlas}JurisdictionType"/>
 *         &lt;element name="invoiceNum" type="{http://www.hctra.org/schema/attlas}InvoiceNumberType"/>
 *         &lt;element name="invoices" type="{http://www.hctra.org/schema/attlas}InvoiceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="violations" type="{http://www.hctra.org/schema/attlas}ViolationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="accountNumber" type="{http://www.hctra.org/schema/attlas}PaymentMethodType"/>
 *         &lt;element name="routingInfo" type="{http://www.hctra.org/schema/attlas}RoutingInfoType"/>
 *         &lt;element name="accountType" type="{http://www.hctra.org/schema/attlas}AccountTypeType"/>
 *         &lt;element name="fullName" type="{http://www.hctra.org/schema/attlas}NameType"/>
 *         &lt;element name="notes" type="{http://www.hctra.org/schema/attlas}NotesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class PostSalEftPaymentType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 753725642702724998L;
	protected String licPlateNum;
    protected String jurisdiction;
    protected String invoiceNum;
	//QC-10317
    protected long accountId;
    protected List<InvoiceType> invoices;
    protected List<ViolationType> violations;
    protected String bankAccountNumber;
    protected String routingInfo;
    protected String bankAccountType;
    protected String fullName;
    protected String notes;
    protected java.lang.String dbSessionId;
    
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

    /**
     * Gets the value of the licPlateNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicPlateNum() {
        return licPlateNum;
    }

    /**
     * Sets the value of the licPlateNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicPlateNum(String value) {
        this.licPlateNum = value;
    }

    /**
     * Gets the value of the jurisdiction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJurisdiction() {
        return jurisdiction;
    }

    /**
     * Sets the value of the jurisdiction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJurisdiction(String value) {
        this.jurisdiction = value;
    }

    /**
     * Gets the value of the invoiceNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceNum() {
        return invoiceNum;
    }

    /**
     * Sets the value of the invoiceNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceNum(String value) {
        this.invoiceNum = value;
    }

    /**
     * Gets the value of the invoices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvoiceType }
     * 
     * 
     */
    public List<InvoiceType> getInvoices() {
        if (invoices == null) {
            invoices = new ArrayList<InvoiceType>();
        }
        return this.invoices;
    }

    /**
     * Gets the value of the violations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the violations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getViolations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ViolationType }
     * 
     * 
     */
    public List<ViolationType> getViolations() {
        if (violations == null) {
            violations = new ArrayList<ViolationType>();
        }
        return this.violations;
    }

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccountNumber(String value) {
        this.bankAccountNumber = value;
    }

    /**
     * Gets the value of the routingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingInfo() {
        return routingInfo;
    }

    /**
     * Sets the value of the routingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingInfo(String value) {
        this.routingInfo = value;
    }

    /**
     * Gets the value of the accountType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountType() {
        return bankAccountType;
    }

    /**
     * Sets the value of the accountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccountType(String value) {
        this.bankAccountType = value;
    }

    /**
     * Gets the value of the fullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the value of the fullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullName(String value) {
        this.fullName = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    //naphan
    public void setViolations(List<ViolationType> violations) {
		this.violations = violations;
	}
    
    public void setInvoices(List<InvoiceType> invoices) {
		this.invoices = invoices;
	}
//QC-10317 Start
    public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	//QC-10317 END
}
