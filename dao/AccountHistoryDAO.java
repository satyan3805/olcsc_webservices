package com.etcc.csc.dao;

import java.util.Calendar;
import java.util.List;

import com.etcc.csc.common.BaseDAO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.generated.OlcAccountTagRecBean;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.AccountSummaryDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.InvoiceArchiveDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.dto.SummaryAvailableDTO;
import com.etcc.csc.dto.TransactionRecordsDTO;

public abstract class AccountHistoryDAO extends BaseDAO {

    public abstract String getReportFilePath(AccountLoginDTO accountLoginDTO) throws EtccReportException;

    public abstract AccountStatementDTO getStatementHTML(AccountLoginDTO accountLoginDTO, 
                                                         String postDate, 
                                                         String acctVPNType) throws EtccException, 
                                                                                    EtccSecurityException;

    public abstract StatementDatesDTO getStatementDates(AccountLoginDTO accountLoginDTO) throws EtccException, 
                                                                                                EtccSecurityException;

    public abstract SummaryAvailableDTO hasLastYearSummary(AccountLoginDTO accountLoginDTO) throws EtccException, 
                                                                                                   EtccSecurityException;

    public abstract OlcAccountTagRecBean[] getAcccountTags(AccountLoginDTO accountLoginDTO) throws EtccException, 
                                                                                               EtccSecurityException;

    public abstract TransactionRecordsDTO getTransactionRecords(AccountLoginDTO accountLoginDTO, 
                                                                     Calendar startDate, 
                                                                     Calendar endDate, 
                                                                     String agencyId, 
                                                                     String tagId, 
                                                                     String licPlate, 
                                                                     String licState,
                                                                     String transTypeId, 
                                                                     String acctVPNType) throws EtccException, 
                                                                                                EtccSecurityException;

    public abstract AccountSummaryDTO getSummaryHTML(AccountLoginDTO accountLoginDTO, 
                                                     String postDate) throws EtccException, 
                                                                             EtccSecurityException;

    public abstract String getStatementPDF(AccountLoginDTO accountLoginDTO, 
                                           String accountVPNType, String lang) throws EtccException, 
                                                                         EtccSecurityException;

    public abstract String getSummaryPDF(AccountLoginDTO accountLoginDTO, 
                                         String accountVPNType, String lang) throws EtccException, 
                                                                       EtccSecurityException;

    public abstract AccountTransactionDTO viewTransactionMain(AccountLoginDTO accountLoginDTO, 
                                                              Calendar startDate, 
                                                              Calendar endDate, 
                                                              String acctVPNType) throws EtccException, 
                                                                                         EtccSecurityException;

    public abstract String getReportServletUrl(String reportName, String sessionId) throws EtccException;  
    
    public abstract List<InvoiceArchiveDTO> getArchiveInvoices(AccountLoginDTO accountLoginDTO) throws EtccException;
}
