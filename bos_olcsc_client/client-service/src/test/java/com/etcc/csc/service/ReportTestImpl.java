/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ReportDTO;

/**
 * Test implementation of <tt>ReportInterface</tt>.
 * @author Milosh Boroyevich
 */
public class ReportTestImpl implements ReportInterface {
	private static final Logger logger = Logger.getLogger(ReportTestImpl.class);

    public ReportDTO getReport(ReportType type, FileFormat format, String args,
            AccountLoginDTO loginDto) throws EtccException {
        ReportDTO report = new ReportDTO();
        report.setBytes("JUnit Test Report.".getBytes());
        logger.debug("getReport returning: " + report);
        return report;
    }
}
