
package com.etcc.csc.service.webservice;

import java.io.Serializable;
import java.util.Calendar;


/**
 * <p>Java class for ViolationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ViolationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agencyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="agencyName" type="{http://www.hctra.org/schema/attlas}AgencyNameType"/>
 *         &lt;element name="violationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="eventDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="eventLocation" type="{http://www.hctra.org/schema/attlas}EventLocationType"/>
 *         &lt;element name="tollAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="inPayment" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class ViolationType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1730627670537968834L;
	protected long agencyId;
    protected String agencyName;
    protected long violationId;
    protected Calendar eventDateTime;
    protected String eventLocation;
    protected double tollAmount;
    protected double balance;
    protected boolean inPayment;
    protected String licPlateNum;
    protected String jurisdiction;

    

	/**
     * Gets the value of the agencyId property.
     * 
     */
    public long getAgencyId() {
        return agencyId;
    }

    /**
     * Sets the value of the agencyId property.
     * 
     */
    public void setAgencyId(long value) {
        this.agencyId = value;
    }

    /**
     * Gets the value of the agencyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyName() {
        return agencyName;
    }

    /**
     * Sets the value of the agencyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyName(String value) {
        this.agencyName = value;
    }

    /**
     * Gets the value of the violationId property.
     * 
     */
    public long getViolationId() {
        return violationId;
    }

    /**
     * Sets the value of the violationId property.
     * 
     */
    public void setViolationId(long value) {
        this.violationId = value;
    }

    /**
     * Gets the value of the eventDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link Calendar }
     *     
     */
    public Calendar getEventDateTime() {
        return eventDateTime;
    }

    /**
     * Sets the value of the eventDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Calendar }
     *     
     */
    public void setEventDateTime(Calendar value) {
        this.eventDateTime = value;
    }

    /**
     * Gets the value of the eventLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventLocation() {
        return eventLocation;
    }

    /**
     * Sets the value of the eventLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventLocation(String value) {
        this.eventLocation = value;
    }

    /**
     * Gets the value of the tollAmount property.
     * 
     */
    public double getTollAmount() {
        return tollAmount;
    }

    /**
     * Sets the value of the tollAmount property.
     * 
     */
    public void setTollAmount(double value) {
        this.tollAmount = value;
    }

    /**
     * Gets the value of the balance property.
     * 
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     */
    public void setBalance(double value) {
        this.balance = value;
    }

    /**
     * Gets the value of the inPayment property.
     * 
     */
    public boolean isInPayment() {
        return inPayment;
    }

    /**
     * Sets the value of the inPayment property.
     * 
     */
    public void setInPayment(boolean value) {
        this.inPayment = value;
    }

    public String getLicPlateNum() {
		return licPlateNum;
	}

	public void setLicPlateNum(String licPlateNum) {
		this.licPlateNum = licPlateNum;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
    
}
