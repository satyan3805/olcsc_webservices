
package com.etcc.csc.service.sal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ViolationType;

/**
 * <p>Java class for PostSalCcPaymentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostSalCcPaymentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="licPlateNum" type="{http://www.hctra.org/schema/attlas}LicensePlateNumberType"/>
 *         &lt;element name="jurisdiction" type="{http://www.hctra.org/schema/attlas}JurisdictionType"/>
 *         &lt;element name="invoiceNum" type="{http://www.hctra.org/schema/attlas}InvoiceNumberType"/>
 *         &lt;element name="invoices" type="{http://www.hctra.org/schema/attlas}InvoiceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="violations" type="{http://www.hctra.org/schema/attlas}ViolationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="paymentType" type="{http://www.hctra.org/schema/attlas}PaymentTypeType"/>
 *         &lt;element name="cardNumber" type="{http://www.hctra.org/schema/attlas}PaymentMethodType"/>
 *         &lt;element name="expireMonth" type="{http://www.hctra.org/schema/attlas}ExpireMonthType"/>
 *         &lt;element name="expireYear" type="{http://www.hctra.org/schema/attlas}ExpireYearType"/>
 *         &lt;element name="fullName" type="{http://www.hctra.org/schema/attlas}NameType"/>
 *         &lt;element name="zipCode" type="{http://www.hctra.org/schema/attlas}ZipCodeType"/>
 *         &lt;element name="notes" type="{http://www.hctra.org/schema/attlas}NotesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class PostSalCcPaymentType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7469316026666937094L;
	protected String licPlateNum;
    protected String jurisdiction;
    protected String invoiceNum;
	//QC-10317
    protected long accountId;
    protected List<InvoiceType> invoices;
    protected List<ViolationType> violations;
    protected String paymentType;
    protected String cardNumber;
    protected String expireMonth;
    protected String expireYear;
    protected String fullName;
    protected String zipCode;
    protected String notes;
    private String paypageRegistrationId;
    private CreditCardDTO.CreditCardType cardType;
  //changes for Tripos Phase 2
    private String omniToken;
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
     * Gets the value of the paymentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentType(String value) {
        this.paymentType = value;
    }

    /**
     * Gets the value of the cardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the value of the cardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNumber(String value) {
        this.cardNumber = value;
    }

    /**
     * Gets the value of the expireMonth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpireMonth() {
        return expireMonth;
    }

    /**
     * Sets the value of the expireMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpireMonth(String value) {
        this.expireMonth = value;
    }

    /**
     * Gets the value of the expireYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpireYear() {
        return expireYear;
    }

    /**
     * Sets the value of the expireYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpireYear(String value) {
        this.expireYear = value;
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
     * Gets the value of the zipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the value of the zipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipCode(String value) {
        this.zipCode = value;
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

	public String getPaypageRegistrationId() {
		return paypageRegistrationId;
	}

	public void setPaypageRegistrationId(String paypageRegistrationId) {
		this.paypageRegistrationId = paypageRegistrationId;
	}

	public CreditCardDTO.CreditCardType getCardType() {
		return cardType;
	}

	public void setCardType(CreditCardDTO.CreditCardType cardType) {
		this.cardType = cardType;
	}
	
	//QC-10317 Start
		
	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	//QC-10317 END
	//changes for Tripos Phase 2
	public String getOmniToken() {
		return omniToken;
	}

	public void setOmniToken(String omniToken) {
		this.omniToken = omniToken;
	}

}
