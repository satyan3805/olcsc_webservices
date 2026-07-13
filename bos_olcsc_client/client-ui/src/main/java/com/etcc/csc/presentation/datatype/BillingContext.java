package com.etcc.csc.presentation.datatype;

import java.io.Serializable;

import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.presentation.form.TagRequestForm;

//weblogic upgrade cluster issues fix 
public class BillingContext implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TagRequestForm tagRequestForm;
    private BillingInfoForm billingInfoForm;
    private double paymentAmt;
    private long transactionId = -1;
    private String entryPoint;

    // from com.etcc.csc.presentation.action.signup.SaveVehicleAction#updateBillingContext
    public BillingContext update(TagRequestForm tagRequestForm) {
        if (tagRequestForm != null) {
            setTagRequestForm(tagRequestForm);
            setPaymentAmt(tagRequestForm.getTotalAmount());
        }
        return this;
    }

    public void setPaymentAmt(double paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public double getPaymentAmt() {
        return paymentAmt;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTagRequestForm(TagRequestForm tagRequestForm) {
        this.tagRequestForm = tagRequestForm;
    }

    public TagRequestForm getTagRequestForm() {
        return tagRequestForm;
    }

    public void setBillingInfoForm(BillingInfoForm billingInfoForm) {
        this.billingInfoForm = billingInfoForm;
    }

    public BillingInfoForm getBillingInfoForm() {
        return billingInfoForm;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getEntryPoint() {
        return entryPoint;
    }
}
