/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;

import com.etcc.csc.dto.bean.OlcCorpInvRecP;


public class InvoiceDatesDTO extends BaseDTO {
    
    /**
     * Unique ID for serialization.
     */
    private static final long serialVersionUID = -838835091252464877L;
    private OlcCorpInvRecP[] invoiceDates;
    
    /**
     * Constructor.
     */
    public InvoiceDatesDTO() {
        //end <init>
    }

    public void setInvoiceDates(OlcCorpInvRecP[] invoiceDates) {
        this.invoiceDates = invoiceDates;
    }

    public OlcCorpInvRecP[] getInvoiceDates() {
        return this.invoiceDates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("invoiceDates="); sb.append(Arrays.toString(this.invoiceDates));
        sb.append("]");
        return sb.toString();
    }
}
