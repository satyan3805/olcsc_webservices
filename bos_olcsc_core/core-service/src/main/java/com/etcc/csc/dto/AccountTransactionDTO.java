package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;
import com.etcc.csc.dao.generated.OlcAccountTagRecBean;
import com.etcc.csc.plsql.OlcViewtxnTxntypeRec;

public class AccountTransactionDTO extends BaseDTO{
    private String pdfLink;
    private String xslLink;
    private OlcAccountTagRecBean[] acctTags;
    private OlcViewtxnTxntypeRec[] txnTypes;
    
    public AccountTransactionDTO() {
    }


    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setXslLink(String xslLink) {
        this.xslLink = xslLink;
    }

    public String getXslLink() {
        return xslLink;
    }

    public void setAcctTags(OlcAccountTagRecBean[] acctTags) {
        this.acctTags = acctTags;
    }

    public OlcAccountTagRecBean[] getAcctTags() {
        return acctTags;
    }

    public void setTxnTypes(OlcViewtxnTxntypeRec[] txnTypes) {
        this.txnTypes = txnTypes;
    }

    public OlcViewtxnTxntypeRec[] getTxnTypes() {
        return txnTypes;
    }
}
