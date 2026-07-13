/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;

import com.etcc.csc.dto.bean.OlcAccountTagRecBean;
import com.etcc.csc.dto.bean.TxnTypeBean;
import com.etcc.csc.dto.bean.VehicleNickNameBean;

/**
 * Account Transaction Data transfer object.
 */
public class AccountTransactionDTO extends BaseDTO {

    /**
     * Unique ID for serialization with version.
     */
    // Do not modify for external clients such as Idea.
    private static final long serialVersionUID = 4206245383955915874L;

    protected String pdfLink;
    protected VehicleNickNameBean[] vehicleNickNames;
    protected TxnTypeBean[] txnTypes;
    protected String xslLink;
    protected OlcAccountTagRecBean[] acctTags;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(", pdfLink=");
        sb.append(this.pdfLink);
        sb.append(", vehicleNickNames=");
        sb.append(Arrays.toString(this.vehicleNickNames));
        sb.append(", txnTypes=");
        sb.append(Arrays.toString(this.txnTypes));
        sb.append(", xslLink=");
        sb.append(this.xslLink);
        sb.append(", acctTags=");
        sb.append(Arrays.toString(this.acctTags));
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the pdfLink
     */
    public String getPdfLink() {
        return this.pdfLink;
        // end getPdfLink
    }

    /**
     * @param pdfLink the pdfLink to set
     */
    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
        // end setPdfLink
    }

    /**
     * @return the vehicleNickNames
     */
    public VehicleNickNameBean[] getVehicleNickNames() {
        return this.vehicleNickNames;
        // end getVehicleNickNames
    }

    /**
     * @param vehicleNickNames the vehicleNickNames to set
     */
    public void setVehicleNickNames(VehicleNickNameBean[] vehicleNickNames) {
        this.vehicleNickNames = vehicleNickNames;
        // end setVehicleNickNames
    }

    /**
     * @return the txnTypes
     */
    public TxnTypeBean[] getTxnTypes() {
        return this.txnTypes;
        // end getTxnTypes
    }

    /**
     * @param txnTypes the txnTypes to set
     */
    public void setTxnTypes(TxnTypeBean[] txnTypes) {
        this.txnTypes = txnTypes;
        // end setTxnTypes
    }

    /**
     * @return the xslLink
     */
    public String getXslLink() {
        return this.xslLink;
        // end getXslLink
    }

    /**
     * @param xslLink the xslLink to set
     */
    public void setXslLink(String xslLink) {
        this.xslLink = xslLink;
        // end setXslLink
    }

    /**
     * @return the acctTags
     */
    public OlcAccountTagRecBean[] getAcctTags() {
        return this.acctTags;
        // end getAcctTags
    }

    /**
     * @param acctTags the acctTags to set
     */
    public void setAcctTags(OlcAccountTagRecBean[] acctTags) {
        this.acctTags = acctTags;
        // end setAcctTags
    }

}
