package com.etcc.csc.dto;

import com.etcc.csc.plsql.OlcAcctSummaryRec;
import com.etcc.csc.plsql.OlcTagSummaryRec;
import com.etcc.csc.dao.generated.OlcViewMonthlyRecBean;
import com.etcc.csc.common.BaseDTO;

import java.util.Collection;

public class AccountStatementDTO extends BaseDTO{
    private String accountId;
    private String accountHolderName;
    private String statementEndingDate;
    private String endingBalance;
    private String lastDayOfMonth;
    private OlcViewMonthlyRecBean[] records;
    private OlcTagSummaryRec[] tagSummary;
    private OlcTagSummaryRec tagSummaryTotal;
    private OlcAcctSummaryRec[] acctSummary;
    
    private int pageSize = 20;
    
    public AccountStatementDTO() {
    }


    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setStatementEndingDate(String statementEndingDate) {
        this.statementEndingDate = statementEndingDate;
    }

    public String getStatementEndingDate() {
        return statementEndingDate;
    }

    public void setEndingBalance(String endingBalance) {
        this.endingBalance = endingBalance;
    }

    public String getEndingBalance() {
        return endingBalance;
    }

   
    
    public void setLastDayOfMonth(String lastDayOfMonth) {
        this.lastDayOfMonth = lastDayOfMonth;
    }

    public String getLastDayOfMonth() {
        return lastDayOfMonth;
    }
    
    public void setRecords(OlcViewMonthlyRecBean[] records) {
        this.records = records;
    }

    public OlcViewMonthlyRecBean[] getRecords() {
        return records;
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

    public OlcTagSummaryRec getTagSummaryTotal() {
		return tagSummaryTotal;
	}

	public void setTagSummaryTotal(OlcTagSummaryRec tagSummaryTotal) {
		this.tagSummaryTotal = tagSummaryTotal;
	}


	public int getStartIndex(int currentPage) {
        return pageSize*currentPage;    
    }
    
    public int getEndIndex(int currentPage) {
        return Math.min(pageSize*(currentPage+1)-1,records.length-1);
    }
    
    public boolean isFirstPage(int currentPage){
        return currentPage == 0;    
    }
    
    public boolean isLastPage(int currentPage){
        return pageSize*(currentPage+1) >= records.length;    
    }
    
    public long getTotalPages() {
        return Math.round(records.length/pageSize+0.5);
    }


    
}
