package com.etcc.csc.dto.bean;

import java.io.Serializable;

public class AccountAlertDetailBean implements Serializable {

    /**
     * Unique ID for Serialization.
     */
    private static final long serialVersionUID = -9144814318604451472L;
    private int alertId;
    private String alertText;

    /**
     * Constructor.
     */
    public AccountAlertDetailBean() {
        //end <init>
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getAlertId() {
        return this.alertId;
    }

    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }

    public String getAlertText() {
        return this.alertText;
    }
}
