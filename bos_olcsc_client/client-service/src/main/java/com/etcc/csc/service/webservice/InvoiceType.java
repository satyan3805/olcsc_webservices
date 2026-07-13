
package com.etcc.csc.service.webservice;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;


/**
 * <p>Java class for InvoiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agencyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="agencyName" type="{http://www.hctra.org/schema/attlas}AgencyNameType"/>
 *         &lt;element name="docId" type="{http://www.hctra.org/schema/attlas}DocumentIdType"/>
 *         &lt;element name="violationDocId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="status" type="{http://www.hctra.org/schema/attlas}InvoiceStatusType"/>
 *         &lt;element name="createDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dueDateTime" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="adminFees" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="otherFees" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="inPayment" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="waveFees" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="addServiceFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class InvoiceType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3188210316648539454L;
	protected long agencyId;
    protected String agencyName;
    protected String docId;
    protected long violationDocId;
    protected String status;
    protected Calendar createDateTime;
    protected Calendar dueDateTime;
    protected double balance;
    protected double adminFees;
    protected double otherFees;
    protected boolean inPayment;
    protected boolean waveFees;
    protected boolean addServiceFee;
    protected String licPlateNum;
    protected String jurisdiction;
	protected double collectionFee;

    public double getCollectionFee() {
		return collectionFee;
	}

	public void setCollectionFee(double collectionFee) {
		this.collectionFee = collectionFee;
	}

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
     * Gets the value of the docId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocId() {
        return docId;
    }

    /**
     * Sets the value of the docId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocId(String value) {
        this.docId = value;
    }

    /**
     * Gets the value of the violationDocId property.
     * 
     */
    public long getViolationDocId() {
        return violationDocId;
    }

    /**
     * Sets the value of the violationDocId property.
     * 
     */
    public void setViolationDocId(long value) {
        this.violationDocId = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the createDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link Calendar }
     *     
     */
    public Calendar getCreateDateTime() {
        return createDateTime;
    }

    /**
     * Sets the value of the createDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Calendar }
     *     
     */
    public void setCreateDateTime(Calendar value) {
        this.createDateTime = value;
    }

    /**
     * Gets the value of the dueDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link Calendar }
     *     
     */
    public Calendar getDueDateTime() {
        return dueDateTime;
    }

    /**
     * Sets the value of the dueDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Calendar }
     *     
     */
    public void setDueDateTime(Calendar value) {
        this.dueDateTime = value;
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
     * Gets the value of the adminFees property.
     * 
     */
    public double getAdminFees() {
        return adminFees;
    }

    /**
     * Sets the value of the adminFees property.
     * 
     */
    public void setAdminFees(double value) {
        this.adminFees = value;
    }

    /**
     * Gets the value of the otherFees property.
     * 
     */
    public double getOtherFees() {
        return otherFees;
    }

    /**
     * Sets the value of the otherFees property.
     * 
     */
    public void setOtherFees(double value) {
        this.otherFees = value;
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

    /**
     * Gets the value of the waveFees property.
     * 
     */
    public boolean isWaveFees() {
        return waveFees;
    }

    /**
     * Sets the value of the waveFees property.
     * 
     */
    public void setWaveFees(boolean value) {
        this.waveFees = value;
    }

    /**
     * Gets the value of the addServiceFee property.
     * 
     */
    public boolean isAddServiceFee() {
        return addServiceFee;
    }

    /**
     * Sets the value of the addServiceFee property.
     * 
     */
    public void setAddServiceFee(boolean value) {
        this.addServiceFee = value;
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
