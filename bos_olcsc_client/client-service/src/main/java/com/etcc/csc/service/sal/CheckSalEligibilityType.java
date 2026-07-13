
package com.etcc.csc.service.sal;

import java.io.Serializable;

/**
 * <p>Java class for CheckSalEligibilityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CheckSalEligibilityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="licPlateNum" type="{http://www.hctra.org/schema/attlas}LicensePlateNumberType"/>
 *         &lt;element name="jurisdiction" type="{http://www.hctra.org/schema/attlas}JurisdictionType"/>
 *         &lt;element name="invoiceNum" type="{http://www.hctra.org/schema/attlas}InvoiceNumberType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class CheckSalEligibilityType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8311715427495601094L;
	
	protected String licPlateNum;
    protected String jurisdiction;
   	protected String invoiceNum;
    //QC-10317
	protected long accountId;
	//PUSH NOTIFICATION PHASE II START
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
	public void setDbSessionId(String dbSessionId) {
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
    
    //QC-10317 Start
	
    public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	//QC-10317 END
}
