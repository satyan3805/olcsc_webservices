/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Date;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ReportDTO;

/**
 * The interface to the Report Service.
 * @author (task 488) Milosh Boroyevich
 * @author (task 488) Stephen Davidson
 */
//Copied from com.etcc.csc.ws.reports.ReportDispatcherInterface in OLCSCService module.
@Local
public interface ReportInterface extends ServiceInterface {

    /**
     * The types of Reports currently supported.  Used to select the type of report desired.
     * @author (task 488) Stephen Davidson
     */
    public enum ReportType {
        /**
         * Receipt Report.
         */
        RCPT_REP("ONLINE_RECEIPTS_REPORT"),
        /**
         * Statement.
         */
        STMT_REP("ONLINE_STATEMENTS"),
        /**
         * Yearly summary report.
         */
        YEAR_REP("ONLINE_YEARLY_SUMMARY_REPORT"),
        /**
         * Monthly Activity Report.
         */
        MNTH_REP("ONLINE_INVOICE_REPORT"),
        /**
         * Transaction Report.
         */
        TRANS_REP("ONLINE_TRANSACTIONS"),
        /**
         * Online statement report.
         */
        OL_STMT_REP("ONLINE_STATEMENTS"),
        /**
         * Multi-vehicle upload report.
         */
        MVU_REPORT("MULTIPLE VEHICLE UPLOAD REPORT"),     
        /**
         * Payment plan report
         */
        PP_REPORT("PAYMENT_PLAN_REPORT"),  //added payment plan report		
		/**
         * Invoice TVI report
         */
		TVI_REPORT("INVOICE_TVI_REPORT"),   // added TVI Report
		/**
         * Invoice TVZ report
         */
		TVZ_REPORT("INVOICE_TVZ_REPORT");  // qc-12405 added TVZ Report

        private final String dbReportName;

        private ReportType(String dbReportName) {
            this.dbReportName = dbReportName;
        }

        public String getDbReportName() {
            return this.dbReportName;
        }
    }

    /**
     * Gets a report or statement from the system as a Byte Array.
     * @param type The type of report being request.
     * @param format The format of the report (such as PDF, CSV, etc.)
     * @param args Arguments for the report, such as date ranges or the Year.
     * @param loginDto The user the report is for.
     * @return the requested report
     * @throws EtccException if any exceptions occur.
     */
    public ReportDTO getReport(ReportType type, FileFormat format, String args, AccountLoginDTO loginDto) throws EtccException;
}
