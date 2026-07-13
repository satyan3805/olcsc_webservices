package com.etcc.csc.dto.bean;

import java.io.Serializable;

public class TxnTypeBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7102484999083565685L;
    private int transTypeId;
    private String description;


    public void setTransTypeId(int transTypeId) {
        this.transTypeId = transTypeId;
    }

    public int getTransTypeId() {
        return this.transTypeId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
