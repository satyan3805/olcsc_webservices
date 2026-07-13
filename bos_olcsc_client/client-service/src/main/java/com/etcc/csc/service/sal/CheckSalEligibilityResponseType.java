
package com.etcc.csc.service.sal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ResponseType;
import com.etcc.csc.service.webservice.ViolationType;


/**
 * <p>Java class for CheckSalEligibilityResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CheckSalEligibilityResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="isEligible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="message" type="{http://www.hctra.org/schema/attlas}MessageType"/>
 *         &lt;element name="invoices" type="{http://www.hctra.org/schema/attlas}InvoiceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="violations" type="{http://www.hctra.org/schema/attlas}ViolationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lastSalPerformedDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="totalInvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalUninvoicedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="serviceFee" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="response" type="{http://www.hctra.org/schema/attlas}ResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class CheckSalEligibilityResponseType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3584010939748690896L;
	
	protected boolean isEligible;
    protected String message;
    protected List<InvoiceType> invoices;
	protected List<ViolationType> violations;
    protected Calendar lastSalPerformedDate;
    protected Double totalInvoicedAmount;
    protected Double totalUninvoicedAmount;
    protected Double serviceFee;
    protected ResponseType response;
	//PUSH NOTIFICATION PHASE II START
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
     * Gets the value of the isEligible property.
     * 
     */
    public boolean isIsEligible() {
        return isEligible;
    }

    /**
     * Sets the value of the isEligible property.
     * 
     */
    public void setIsEligible(boolean value) {
        this.isEligible = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
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
     * <p>
     * 
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
    
    public void setInvoices(List<InvoiceType> invoices) {
		this.invoices = invoices;
	}

	public void setViolations(List<ViolationType> violations) {
		this.violations = violations;
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
     * Gets the value of the lastSalPerformedDate property.
     * 
     * @return
     *     possible object is
     *     {@link Calendar }
     *     
     */
    public Calendar getLastSalPerformedDate() {
        return lastSalPerformedDate;
    }

    /**
     * Sets the value of the lastSalPerformedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Calendar }
     *     
     */
    public void setLastSalPerformedDate(Calendar value) {
        this.lastSalPerformedDate = value;
    }

    /**
     * Gets the value of the totalInvoicedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalInvoicedAmount() {
        return totalInvoicedAmount;
    }

    /**
     * Sets the value of the totalInvoicedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalInvoicedAmount(Double value) {
        this.totalInvoicedAmount = value;
    }

    /**
     * Gets the value of the totalUninvoicedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalUninvoicedAmount() {
        return totalUninvoicedAmount;
    }

    /**
     * Sets the value of the totalUninvoicedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalUninvoicedAmount(Double value) {
        this.totalUninvoicedAmount = value;
    }

    /**
     * Gets the value of the serviceFee property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getServiceFee() {
        return serviceFee;
    }

    /**
     * Sets the value of the serviceFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setServiceFee(Double value) {
        this.serviceFee = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseType }
     *     
     */
    public ResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseType }
     *     
     */
    public void setResponse(ResponseType value) {
        this.response = value;
    }

}
