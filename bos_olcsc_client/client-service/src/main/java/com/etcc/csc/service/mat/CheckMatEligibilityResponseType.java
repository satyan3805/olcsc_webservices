
package com.etcc.csc.service.mat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.etcc.csc.service.webservice.ResponseType;
import com.etcc.csc.service.webservice.ViolationType;


/**
 * <p>Java class for CheckMatEligibilityResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CheckMatEligibilityResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="isEligible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="message" type="{http://www.hctra.org/schema/attlas}MessageType"/>
 *         &lt;element name="violations" type="{http://www.hctra.org/schema/attlas}ViolationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lastMatPerformedDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="serviceFee" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="matPayAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="response" type="{http://www.hctra.org/schema/attlas}ResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class CheckMatEligibilityResponseType  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8006648737370273139L;
	protected boolean isEligible;
    protected String message;
    protected List<ViolationType> violations;
    protected Calendar lastMatPerformedDate;
    protected Double serviceFee;
    protected Double totalAmount;
    protected Double matPayAmount;
    protected ResponseType response;
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
     * Gets the value of the lastMatPerformedDate property.
     * 
     * @return
     *     possible object is
     *     {@link Calendar }
     *     
     */
    public Calendar getLastMatPerformedDate() {
        return lastMatPerformedDate;
    }

    /**
     * Sets the value of the lastMatPerformedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Calendar }
     *     
     */
    public void setLastMatPerformedDate(Calendar value) {
        this.lastMatPerformedDate = value;
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
     * Gets the value of the totalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalAmount(Double value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the matPayAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMatPayAmount() {
        return matPayAmount;
    }

    /**
     * Sets the value of the matPayAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMatPayAmount(Double value) {
        this.matPayAmount = value;
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
