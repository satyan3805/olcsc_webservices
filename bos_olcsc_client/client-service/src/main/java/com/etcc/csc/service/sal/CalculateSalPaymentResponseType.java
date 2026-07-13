
package com.etcc.csc.service.sal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ResponseType;
import com.etcc.csc.service.webservice.ViolationType;


/**
 * <p>Java class for CalculateSalPaymentResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CalculateSalPaymentResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invoices" type="{http://www.hctra.org/schema/attlas}InvoiceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="violations" type="{http://www.hctra.org/schema/attlas}ViolationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="wavedFees" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="salPayAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="response" type="{http://www.hctra.org/schema/attlas}ResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class CalculateSalPaymentResponseType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3980166053655860272L;
	protected List<InvoiceType> invoices;
    protected List<ViolationType> violations;
    protected double wavedFees;
    protected double salPayAmount;
    protected ResponseType response;
    protected String message;

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
     * Gets the value of the wavedFees property.
     * 
     */
    public double getWavedFees() {
        return wavedFees;
    }

    /**
     * Sets the value of the wavedFees property.
     * 
     */
    public void setWavedFees(double value) {
        this.wavedFees = value;
    }

    /**
     * Gets the value of the salPayAmount property.
     * 
     */
    public double getSalPayAmount() {
        return salPayAmount;
    }

    /**
     * Sets the value of the salPayAmount property.
     * 
     */
    public void setSalPayAmount(double value) {
        this.salPayAmount = value;
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
    
    //naphan
    public void setInvoices(List<InvoiceType> invoices) {
		this.invoices = invoices;
	}
    
    public void setViolations(List<ViolationType> violations) {
		this.violations = violations;
	}
    
    public String getMessage() {
		return message;
	}
    
    public void setMessage(String message) {
		this.message = message;
	}

}
