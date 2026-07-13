package com.etcc.csc.dto.bean;

import java.io.Serializable;

public class OlcCorpInvRecP implements Serializable {
    
    /**
     * Unique ID for serialization.
     */
    //Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = 525942603476365998L;
    private String statementPeriod;
    private String invoicePeriod;
    
    /**
     * Constructor.
     */
    public OlcCorpInvRecP() {
        //end <init>
    }

    public void setStatementPeriod(String statementPeriod) {
        this.statementPeriod = statementPeriod;
    }

    public String getStatementPeriod() {
        return this.statementPeriod;
    }

    public void setInvoicePeriod(String invoicePeriod) {
        this.invoicePeriod = invoicePeriod;
    }

    public String getInvoicePeriod() {
        return this.invoicePeriod;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("statementPeriod="); sb.append(this.statementPeriod);
        sb.append(", invoicePeriod="); sb.append(this.invoicePeriod);
        sb.append("]");
        return sb.toString();
    }
}
