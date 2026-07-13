/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ReportDTO;
import com.etcc.csc.service.Report;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.service.ServiceFactory;

/**
 * Delegate for the Report Service.
 */
public class ReportDelegate implements ReportInterface {
    private static int reportLoggerCount = 0;
    private static final Logger logger = Logger.getLogger(ReportDelegate.class);
    private static final Logger reportLogger = Logger.getLogger("com.etcc.csc.delegate.ReportDelegate.rlog");

    public ReportDTO getReport(ReportType type, FileFormat format, String args, AccountLoginDTO loginDto)
            throws EtccException {
    	//msp_2309_olcscfix
        Report reportService = new Report();
        ReportDTO report = reportService.getReport(type, format, args, loginDto);
        logReport(report);
        return report;
    }

    private ReportInterface stub() {
        return ServiceFactory.getImplementation(ReportInterface.class);
    }

    private void logReport(ReportDTO report) {
        FileOutputStream fos = null;
        try {
            if (reportLogger.isDebugEnabled() == false) {
                logger.debug("reportLogger is disabled");
                return;
            }
            StringBuilder fileName = new StringBuilder();
            // ../OnlineTagStore/log/revamp/webreportfile_2009-03-05_153245_2.rlog
            fileName.append("../OnlineTagStore/log/revamp/"); // the directory
            Calendar now = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
            String aTimeStamp = format.format(now.getTime());
            fileName.append("webreportfile_").append(aTimeStamp);
            fileName.append("_").append(reportLoggerCount()).append(".rlog");
            logger.debug("logReport.fileName=" + fileName);
            fos = new FileOutputStream(fileName.toString());
            fos.write(report.getBytes());
            fos.flush();
        } catch (IOException e) {
            logger.warn("logReport failed: " + e.getMessage(), e);
        } finally {
            try {
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                logger.error("logReport.fos.close failed: " + e.getMessage(), e);
            }
        }
    }

    private static synchronized int reportLoggerCount() {
        return ReportDelegate.reportLoggerCount++;
    }
}
