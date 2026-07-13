
package com.etcc.csc.service.sal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.etcc.csc.service.webservice.ResponseType;
import com.etcc.csc.service.webservice.ViolationType;


/**
 * <p>Java class for GetInvoiceViolationsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetInvoiceViolationsResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="violations" type="{http://www.hctra.org/schema/attlas}ViolationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="response" type="{http://www.hctra.org/schema/attlas}ResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class GetInvoiceViolationsResponseType implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -790041464605971936L;
	protected List<ViolationType> violations;
    protected ResponseType response;

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

    public void setViolations(List<ViolationType> violations) {
		this.violations = violations;
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
