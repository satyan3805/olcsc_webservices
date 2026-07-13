package com.etcc.csc.presentation.viewhelper;


import com.etcc.csc.delegate.CreditCardDelegate;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.TagDTO;

import java.util.Collection;
import java.util.Map;

public class GetTollTagReviewViewHelper {

    private String creditCardName;
    private String vehicleClassDesc;
    private int vehicleCount;
    private double paymentAmount;

    public GetTollTagReviewViewHelper(Map<String, TagDTO> savedVehicles, double paymentAmount, 
    		String creditCardCode, 
    		Collection<CreditCardDTO> creditCardTypes) 
    {
        setVehicleCount(savedVehicles.size());
        setPaymentAmount(paymentAmount);
        setCreditCardName(CreditCardDelegate.getCreditCardName(creditCardCode, creditCardTypes));
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setVehicleClassDesc(String vehicleClassDesc) {
        this.vehicleClassDesc = vehicleClassDesc;
    }

    public String getVehicleClassDesc() {
        return vehicleClassDesc;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
}
