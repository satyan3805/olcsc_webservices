/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountSummaryDTO;
import com.etcc.csc.dto.bean.AccountSummaryDetailBean;

/**
 * Sets the return values for the Mock AccountHistory.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class AccountHistoryFactory {
    /**
     * Report URL, returned by {@link AccountHistoryInterface#getReportURL(AccountLoginDTO, String, String, String)}.
     */
    public static final String REPORT_URL =
        "https://dev-eztag.hctra.net/reports/rwservlet?REPORT=/app/oracle/reports/htd1/olcsc_transactions&p_acct_id=1017851&p_date_from=01/27/2010&p_date_to=04/27/2010&p_vehicle_filter=All%20Vehicles&p_trans_type_filter=All%20Transactions&DESFORMAT=PDF&USERID=CSC_REPORT/CSC_REPORT@htd1&DESTYPE=CACHE&AUTHTYPE=D&p_date_type=P";

    /**
     * Report file path, returned by {@link AccountHistoryInterface#getReportFilePath(AccountLoginDTO)}.
     */
    public static final String FILE_PATH = "file:///tmp";

    //Package level.  The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final AccountHistoryInterface mocked){
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                allowing(mocked).getReportFilePath(with(any(AccountLoginDTO.class)));
                will(returnValue(FILE_PATH));
                allowing(mocked).getSummaryPDF(with(any(AccountLoginDTO.class)), with(any(String.class)));
                will(returnValue(null));
                allowing(mocked).getSummaryGraph(with(any(AccountLoginDTO.class)));
                will(returnValue(makeSummaryGraph()));
            }});
        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new UnsupportedOperationException(e.getMessage(), e);
        }
    }
    
    /**]
     * Creates a summary graph object for testing.
     * @return Summary object for testing.
     */
    public static AccountSummaryDTO makeSummaryGraph(){
        final AccountSummaryDTO dto = new AccountSummaryDTO();
        dto.setBillingAcctNumber("1000");
        AccountSummaryDetailBean[] acctSummary = makeSummaryDetails();
        dto.setAcctSummary(acctSummary);
        return dto;
    }
    
    private static AccountSummaryDetailBean[] makeSummaryDetails(){
        AccountSummaryDetailBean[] details = new AccountSummaryDetailBean[3];
        int i = 0;
        details[i++] = new AccountSummaryDetailBean("Payment", BigDecimal.valueOf(50));
        details[i++] = new AccountSummaryDetailBean("Tolls", BigDecimal.valueOf(2.25));
        details[i++] = new AccountSummaryDetailBean("Tolls", BigDecimal.valueOf(4.50));
        return details;
    }
}
