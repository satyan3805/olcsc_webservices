package com.etcc.csc.delegate;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dao.generated.OlcAccountTagRecBean;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.AccountSummaryDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.InvoiceArchiveDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.dto.SummaryAvailableDTO;
import com.etcc.csc.dto.TransactionRecordsDTO;
import com.etcc.csc.service.AccountHistoryInterface;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AccountHistoryDelegate extends Delegate implements AccountHistoryInterface {
    AccountHistoryInterface ahi = (AccountHistoryInterface)getServiceObject(ServiceObjectEnum.ACCOUNT_HISTORY);
    
    
    public AccountHistoryDelegate() {
        super(AccountHistoryDelegate.class);
    }

    public String getStatement(AccountLoginDTO accountLoginDTO, Date postDate,
                               String reportFormat) throws EtccException, EtccSecurityException{
        try {

             return ahi.getStatement(accountLoginDTO, postDate, reportFormat);

        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getStatement: " + t, t);
        }

    }


    public String getTransactions(AccountLoginDTO accountLoginDTO,
                                  Date startDate, Date endDate,
                                  String agencyId, String tagId,
                                  String transactionTypeCode,
                                  String reportFormat) throws EtccException, EtccSecurityException {

        try {

            return ahi.getTransactions(accountLoginDTO, startDate, endDate, 
                                    agencyId, tagId, transactionTypeCode, 
                                    reportFormat);

        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getTransactions: " + t, t);
        }


    }


    public String getSummary(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException {
        try {
            
            return ahi.getSummary(accountLoginDTO);

        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getSummary: " + t, t);
        }

    }


    public String getReportFilePath(AccountLoginDTO accountLoginDTO) throws EtccReportException {
        try {

            return ahi.getReportFilePath(accountLoginDTO);

        } catch (Throwable t) {
            logger.error(t);
            throw new EtccReportException("Error running getReportFilePath: " + t, t);
        }

    }

    public String getStatementPDF(AccountLoginDTO accountLoginDTO,
                                  String accountVPNType, String lang) throws EtccException, EtccSecurityException {
        try {

           return ahi.getStatementPDF(accountLoginDTO, accountVPNType, lang);
           
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getStatementPDF: " + t, t);
        }
    }

    public String getSummaryPDF(AccountLoginDTO accountLoginDTO,
                                String accountVPNType, String lang) throws EtccException, EtccSecurityException {
        try {
            
            return ahi.getSummaryPDF(accountLoginDTO, accountVPNType, lang);
            
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getSummaryPDF: " + t, t);
        }
    }


    public AccountStatementDTO getStatementHTML(AccountLoginDTO accountLoginDTO,
                                                String postDate,
                                                String acctVPNType) throws EtccException, EtccSecurityException {
        try {
            
            return ahi.getStatementHTML(accountLoginDTO, postDate, acctVPNType);
            
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getStatementHTML: " + t, t);
        }
    }

    public AccountSummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO,
                                            String postDate) throws EtccException, EtccSecurityException {
        try {
            
            return ahi.getSummaryHTML(accountLoginDTO, postDate);
            
         } catch (EtccSecurityException se) {
             logger.error(se);
             throw new EtccSecurityException(se);
         } catch (Throwable t) {
             logger.error(t);
             throw new EtccException("Error running getSummaryHTML: " + t, t);
         }
    }

    public StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException {
        try {
            
            return ahi.getStatementDates(accountLoginDTO);

        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getStatementDates: " + t, t);
        }
    }

    public SummaryAvailableDTO hasLastYearSummary(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException {
        try {
            
            return ahi.hasLastYearSummary(accountLoginDTO);

        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running hasLastYearSummary: " + t, t);
        }
    }

    public OlcAccountTagRecBean[] getAcccountTags(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException {
        try {
            
            return ahi.getAcccountTags(accountLoginDTO);
            
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getAcccountTags: " + t, t);
        }
    }

    public TransactionRecordsDTO getTransactionRecords(AccountLoginDTO accountLoginDTO,
                                                            Calendar startDate,
                                                            Calendar endDate,
                                                            String agencyId,
                                                            String tagId,
                                                            String licPlate, 
                                                            String licState,
                                                            String transTypeId,
                                                            String acctVPNType) throws EtccException, EtccSecurityException {
        try {
           
           return ahi.getTransactionRecords(accountLoginDTO, startDate, endDate, 
                                            agencyId, tagId,
                                            licPlate, licState,
                                            transTypeId,acctVPNType);
        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getTransactionRecords: " + t, t);
        }
    }

    

    


    public AccountTransactionDTO viewTransactionMain(AccountLoginDTO accountLoginDTO, 
                                                     Calendar startDate, 
                                                     Calendar endDate, 
                                                     String acctVPNType) throws EtccException, EtccSecurityException{
        try
        {
            return ahi.viewTransactionMain(accountLoginDTO, startDate, 
                                            endDate, acctVPNType);

        } catch (EtccSecurityException se) {
            logger.error(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running viewTransactionMain: " + t, t);
        }
    }
    
    
    

    public String getReportServletUrl(String reportName, String sessionId) throws EtccException{
        try{
        
            return ahi.getReportServletUrl(reportName, sessionId);
            
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getReportServletUrl: " + t, t);
        }  
    }
    
    public List<InvoiceArchiveDTO> getArchiveInvoices(AccountLoginDTO accountLoginDTO) throws EtccException{
        try{
        
            return ahi.getArchiveInvoices(accountLoginDTO);
            
        } catch (Throwable t) {
            logger.error(t);
            throw new EtccException("Error running getArchiveInvoices: " + t, t);
        }  
    }
}
