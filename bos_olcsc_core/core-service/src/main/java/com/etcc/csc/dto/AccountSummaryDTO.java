package com.etcc.csc.dto;

import com.etcc.csc.plsql.OlcAcctSummaryRec;
import com.etcc.csc.plsql.OlcTagSummaryRec;
import com.etcc.csc.common.BaseDTO;

public class AccountSummaryDTO extends BaseDTO {

    private String endingBalance;
    private String lastDayOfYear;
    
    private OlcTagSummaryRec[] tagSummary;
    private OlcAcctSummaryRec[] acctSummary;
    
    public AccountSummaryDTO() {
    }


    public void setEndingBalance(String endingBalance) {
        this.endingBalance = endingBalance;
    }

    public String getEndingBalance() {
        return endingBalance;
    }

    public void setLastDayOfYear(String lastDayOfYear) {
        this.lastDayOfYear = lastDayOfYear;
    }

    public String getLastDayOfYear() {
        return lastDayOfYear;
    }


    public void setTagSummary(OlcTagSummaryRec[] tagSummary) {
        this.tagSummary = tagSummary;
    }

    public OlcTagSummaryRec[] getTagSummary() {
        return tagSummary;
    }

    public void setAcctSummary(OlcAcctSummaryRec[] acctSummary) {
        this.acctSummary = acctSummary;
    }

    public OlcAcctSummaryRec[] getAcctSummary() {
        return acctSummary;
    }
}
