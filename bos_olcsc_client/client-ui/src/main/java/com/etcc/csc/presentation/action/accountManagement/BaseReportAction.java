/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.delegate.ReportDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ReportDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.util.HttpResponseUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Base action that provides common behaviors for reporting.
 * All reports actions should extend this action.
 * @author Milosh Boroyevich
 */
public abstract class BaseReportAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(BaseReportAction.class);

    /**
     * Get and process the specified report.
     * @param request the HTTP request to process
     * @param response the HTTP response for streaming the report
     * @param reportType type of report to generate
     * @param reportName name of the report file
     * @param reportArgs report arguments
     * @param errorMessageProperty property key for adding an error message if encountered
     * @return Display string based on the file format to be used for action mapping (<tt>null</tt> on error)
     * @throws IOException if an I/O error occurs while streaming the report to the HTTP response
     * @see ReportDelegate#getReport(ReportType, FileFormat, String, AccountLoginDTO)
     * @see HttpResponseUtil#streamBytes(HttpServletRequest, HttpServletResponse, byte[], String, FileFormat)
     * @see #saveErrorMessage(HttpServletRequest, ErrorMessageDTO, String)
     */
    protected String processReport(HttpServletRequest request, HttpServletResponse response,
            ReportInterface.ReportType reportType, String reportName, Date date, String reportArgs, String errorMessageProperty)
            throws IOException {
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        FileFormat format = null;
        for (FileFormat ff : FileFormat.values()) {
            if (StringUtil.stringToBoolean(request.getParameter(ff.getDisplay()))) {
                format = ff;
                break;
            }
        }
        if (format != null) {
            ReportDelegate reDelegate = new ReportDelegate();
            try {
                ReportDTO report = reDelegate.getReport(reportType, format, reportArgs, accountLogin);
                if (report.hasErrors())
                    this.saveErrorMessages(request, report, errorMessageProperty);
                else
                    return HttpResponseUtil.streamBytes(request, response, report.getBytes(), reportName, format);
            } catch (EtccException e) {
                String message = "Error encountered while processing the report. ";
                logger.warn(message + e.getMessage(), e);
                ErrorMessageDTO errorMessage = new ErrorMessageDTO().withMessage(message);
                saveErrorMessage(request, errorMessage, errorMessageProperty);
            }
        }
        return null;
    }
}
