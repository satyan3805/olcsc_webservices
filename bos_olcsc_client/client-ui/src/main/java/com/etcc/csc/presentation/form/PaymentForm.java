package com.etcc.csc.presentation.form;

import java.util.Calendar;

import org.apache.struts.validator.ValidatorActionForm;

public class PaymentForm extends ValidatorActionForm {
    private double payAmount;

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getPayAmount() {
        return payAmount;
    }
}
