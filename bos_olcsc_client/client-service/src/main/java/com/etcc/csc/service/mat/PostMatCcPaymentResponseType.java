
package com.etcc.csc.service.mat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.etcc.csc.service.webservice.PaymentType;
import com.etcc.csc.service.webservice.ResponseType;


/**
 * <p>Java class for PostMatCcPaymentResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostMatCcPaymentResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paymentInfo" type="{http://www.hctra.org/schema/attlas}PaymentType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="response" type="{http://www.hctra.org/schema/attlas}ResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class PostMatCcPaymentResponseType  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4768223720156652829L;
	protected List<PaymentType> paymentInfo;
    protected ResponseType response;

    /**
     * Gets the value of the paymentInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentType }
     * 
     * 
     */
    public List<PaymentType> getPaymentInfo() {
        if (paymentInfo == null) {
            paymentInfo = new ArrayList<PaymentType>();
        }
        return this.paymentInfo;
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
