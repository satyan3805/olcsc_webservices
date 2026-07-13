
package com.etcc.csc.service.sal;

import java.io.Serializable;



/**
 * <p>Java class for GetInvoiceViolationsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetInvoiceViolationsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="violationDocumentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class GetInvoiceViolationsType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3258002694416675574L;
	protected String violationDocumentId;
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
     * Gets the value of the violationDocumentId property.
     * 
     */
    public String getViolationDocumentId() {
        return violationDocumentId;
    }

    /**
     * Sets the value of the violationDocumentId property.
     * 
     */
    public void setViolationDocumentId(String value) {
        this.violationDocumentId = value;
    }

}
