/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Arrays;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.dto.bean.OlcAcctSummaryRecBean;
import com.etcc.csc.dto.bean.OlcTagSummaryRecBean;
import com.etcc.csc.dto.bean.OlcViewMonthlyRecBean;

/**
 * Account Statement Data Transfer Object.  Based on the AccountStatementDTO's from the original OLSCSC Services project.
 * @author Stephen Davidson
 */
public class AccountStatementDTO extends BaseDTO {
    /**
     * Unique ID for Serialization with version.
     */
    //Do not change for external clients such as Idea.
    private static final long serialVersionUID = 4872004209280106236L;
    private String accountId;
    private String accountHolderName;
    private String statementEndingDate;
    private String endingBalance;
    private String lastDayOfMonth;
    private OlcViewMonthlyRecBean[] records;
    private OlcTagSummaryRecBean[] tagSummary;
    private OlcAcctSummaryRecBean[] acctSummary;
    private OlcViewMonthlyRecBean[] recordsHAS;
    private OlcTagSummaryRecBean[] tagSummaryHAS;
    private OlcAcctSummaryRecBean[] acctSummaryHAS;
    
    private int pageSize = 20;

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderName() {
        return this.accountHolderName;
    }

    public void setStatementEndingDate(String statementEndingDate) {
        this.statementEndingDate = statementEndingDate;
    }

    public String getStatementEndingDate() {
        return this.statementEndingDate;
    }

    public void setEndingBalance(String endingBalance) {
        this.endingBalance = endingBalance;
    }

    public String getEndingBalance() {
        return this.endingBalance;
    }

    public void setLastDayOfMonth(String lastDayOfMonth) {
        this.lastDayOfMonth = lastDayOfMonth;
    }

    public String getLastDayOfMonth() {
        return this.lastDayOfMonth;
    }
    
    public void setRecords(OlcViewMonthlyRecBean[] records) {
        this.records = records;
    }

    public OlcViewMonthlyRecBean[] getRecords() {
        return this.records;
    }

    public void setTagSummary(OlcTagSummaryRecBean[] tagSummary) {
        this.tagSummary = tagSummary;
    }

    public OlcTagSummaryRecBean[] getTagSummary() {
        return this.tagSummary;
    }

    public void setAcctSummary(OlcAcctSummaryRecBean[] acctSummary) {
        this.acctSummary = acctSummary;
    }

    public OlcAcctSummaryRecBean[] getAcctSummary() {
        return this.acctSummary;
    }

    public int getStartIndex(int currentPage) {
        return this.pageSize*currentPage;    
    }
    
    public int getEndIndex(int currentPage) {
        return Math.min(this.pageSize*(currentPage+1)-1,this.records.length-1);
    }
    
    public boolean isFirstPage(int currentPage){
        return currentPage == 0;    
    }
    
    public boolean isLastPage(int currentPage){
        return this.pageSize*(currentPage+1) >= this.records.length;    
    }
    
    @IgnoreProperty
    public long getTotalPages() {
        return Math.round(this.records.length/this.pageSize+0.5);
    }


    public void setRecordsHAS(OlcViewMonthlyRecBean[] recordsHAS) {
        this.recordsHAS = recordsHAS;
    }

    public OlcViewMonthlyRecBean[] getRecordsHAS() {
        return this.recordsHAS;
    }

    public void setTagSummaryHAS(OlcTagSummaryRecBean[] tagSummaryHAS) {
        this.tagSummaryHAS = tagSummaryHAS;
    }

    public OlcTagSummaryRecBean[] getTagSummaryHAS() {
        return this.tagSummaryHAS;
    }

    public void setAcctSummaryHAS(OlcAcctSummaryRecBean[] acctSummaryHAS) {
        this.acctSummaryHAS = acctSummaryHAS;
    }

    public OlcAcctSummaryRecBean[] getAcctSummaryHAS() {
        return this.acctSummaryHAS;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("acctSummary="); sb.append(Arrays.toString(this.acctSummary));
        sb.append(", tagSummary="); sb.append(Arrays.toString(this.tagSummary));
        sb.append(", records="); sb.append(Arrays.toString(this.records));
        sb.append(", lastDayOfMonth="); sb.append(this.lastDayOfMonth);
        sb.append(", acctSummaryHAS="); sb.append(Arrays.toString(this.acctSummaryHAS));
        sb.append(", recordsHAS="); sb.append(Arrays.toString(this.recordsHAS));
        sb.append(", accountId="); sb.append(this.accountId);
        sb.append(", statementEndingDate="); sb.append(this.statementEndingDate);
        sb.append(", accountHolderName="); sb.append(this.accountHolderName);
        sb.append(", endingBalance="); sb.append(this.endingBalance);
        sb.append(", tagSummaryHAS="); sb.append(Arrays.toString(this.tagSummaryHAS));
        sb.append("]");
        return sb.toString();
    }

}//end AccountStatementDTO
