
package com.etcc.csc.service.mat;

import java.io.Serializable;



/**
 * <p>Java class for CheckMatEligibilityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CheckMatEligibilityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="licPlateNum" type="{http://www.hctra.org/schema/attlas}LicensePlateNumberType"/>
 *         &lt;element name="jurisdiction" type="{http://www.hctra.org/schema/attlas}JurisdictionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class CheckMatEligibilityType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6337942177513358942L;
	protected String licPlateNum;
    protected String jurisdiction;
    // PUSH NOTIFICATION PHASE II START
    protected String userName;
    protected String ipAddress;
	protected String sourceUserName;
    protected String jSessionId;    
    protected String dbSessionId;
    //PUSH NOTIFICATION PHASE II END
    
    /**
     * Gets the value of the dbSessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getDbSessionId() {
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
	public void setdbSessionId(String dbSessionId) {
		this.dbSessionId = dbSessionId;
	}
    	
	/**
     * Gets the value of the sourceUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceUserName() {
		return sourceUserName;
	}

    /**
     * Sets the value of the sourceUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}
	/**
     * Gets the value of the jSessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getjSessionId() {
		return jSessionId;
	}
	
	/**
     * Sets the value of the jSessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setjSessionId(String jSessionId) {
		this.jSessionId = jSessionId;
	}
    
    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
		return userName;
	}
    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
     * Gets the value of the ipAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getIpAddress() {
		return ipAddress;
	}

	 /**
     * Sets the value of the ipAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

}
