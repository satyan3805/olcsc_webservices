/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.dao.AccountHistoryDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ReportDTO;

import oracle.j2ee.ejb.StatelessDeployment;

/**
 * The API for the Report Service.  Gets the report from the Server, and sends to the client as a byte array.
 * @author (task 488) Stephen Davidson
 * @author Milosh Boroyevich
 * @see #convertReport(String)
 */
// * Copied com.etcc.csc.ws.reports.Report and com.etcc.csc.ws.reports.ReportService in OLCSCService.
@Stateless(name="com/etcc/Report")
@StatelessDeployment(transactionTimeout=300)
// Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Report", targetNamespace = "http://ws.csc.etcc.com/Report")
public class Report implements ReportInterface {

    private static final Logger logger = Logger.getLogger(Report.class);
    private static final Logger reportLogger = Logger.getLogger("com.etcc.csc.ws.reports.rlog");
    private static final int BUF_SIZE = 1024 * 64; //use 64k buffer.
    private static final int LOG_BYTE_COUNT = 24;

    private static int reportLoggerCount = 0;


    /**
     * @throws ParseException
     * @see #generateReport(ReportType, FileFormat, String, AccountLoginDTO)
     */
    //@WebMethod(operationName = "getReport", action = "urn:getReport")
    //@WebResult(name = "report")
    public ReportDTO getReport(ReportType type, FileFormat format, String args, AccountLoginDTO loginDto) throws EtccException {
		return generateReport(type, format, args, loginDto);
    }


    /**
     * Opens a connection to the specified URL and converts the stream into a byte array.
     * <p>
     * Note: if the specified URL is for HTTPS, then a properly signed SSL
     * certificate must be present.  Otherwise, the following exception will
     * be encountered:<br />
     * <tt>javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target</tt>
     * </p>
     * <p>
     * For a wealth of information, read Andreas Sterbenz's Java and Security blog entry at:
     * <a href="http://blogs.sun.com/andreas/entry/no_more_unable_to_find" target="security-blog">http://blogs.sun.com/andreas/entry/no_more_unable_to_find</a>
     * </p>
     * <p>
     * To resolve this issue follow the steps outlined below.
     * The <tt>$ORACLE_HOME</tt> environment variable refers to the OC4J
     * installation path (e.g. <tt>/app/oracle/product/10.1.3/as_1</tt>)
     * and may need to be modified if multiple installations are present.
     * </p>
     * <ol>
     * <li><a href="http://blogs.sun.com/andreas/resource/InstallCert.java" target="InstallCert">Download InstallCert.java</a></li>
     * <li>Compile <tt>InstallCert</tt>:
     * <dl><dd><tt>javac InstallCert.java</tt></dd></dl>
     * </li>
     * <li>Run <tt>InstallCert</tt> with the desired server host (the new
     * certificate will be appended to the OC4J certificate file at
     * <tt>$ORACLE_HOME/jdk/jre/lib/security/cacerts</tt> and written to
     * <tt>jssecacerts</tt>):
     * <dl><dd><tt>$ORACLE_HOME/jdk/bin/java InstallCert</tt> <i>dev-eztag.hctra.net</i></dd></dl>
     * </li>
     * <li><tt>InstallCert</tt> will display:
     * <dl>
     * <dd>Loading KeyStore <i>[path to cacerts]</i>cacerts...</dd>
     * <dd>Opening connection to dev-eztag.hctra.net:443...</dd>
     * <dd>Starting SSL handshake...</dd>
     * </dl>
     * followed by the Exception Stack Trace, and an option to add
     * any of the certificates sent by the server.
     * </li>
     * <li>Select to add the first certificate (option 1).
     * The certificate details are displayed followed by:
     * <dl><dd>Added certificate to keystore 'jssecacerts' using alias 'dev-eztag.hctra.net-1'</dd></dl>
     * </li>
     * <li>Move the updated certificates back to OC4J
     * (make a backup of the original <tt>cacerts</tt> just in case):
     * <dl><dd><tt>mv jssecacerts $ORACLE_HOME/jdk/jre/lib/security/cacerts</tt></dd></dl>
     * </li>
     * <li>Run <tt>InstallCert</tt> again using the command from step 3 to
     * confirm the new certificate is working (select option 'q' to exit).</li>
     * </ol>
     *
     * @param url the URL of the report
     * @return the requested report
     * @throws IOException  if an I/O error occurs
     */
    protected ReportDTO convertReport(String url,long startTime,String args) throws IOException {
        URLConnection conn = null;
        InputStream connInputStream = null;
        ReportDTO report = new ReportDTO();
        try {
            boolean debugEnabled = logger.isDebugEnabled();
            if (debugEnabled)
                logger.info("convertReport entered: " + url);
            conn =  new URL(url).openConnection();
            if (conn == null) {
                //TODO: Should this be an Exception?
                String message = "Unable to find report. ";
                logger.info(message + url);
                report.addError(message);
                return report;
            }
            conn.setConnectTimeout(10000);
            conn.connect();
            int len = conn.getContentLength();
            if (debugEnabled)
                logger.info("convertReport.len=" + len);
            if (len < -1)
                throw new IllegalStateException("Url returned an illegal value for data length: " + url + " size=" + len);
            ByteArrayOutputStream theOutput;
            switch (len) {
            case -1: // content length is unknown
                theOutput = new ByteArrayOutputStream(BUF_SIZE);
                break;
            case 0: //Empty/nothing available, so abort.
                report.addError("Report is empty.");
                return report;
            default:
                theOutput = new ByteArrayOutputStream(len);
            }
            byte[] buffer = new byte[BUF_SIZE];
            connInputStream = new BufferedInputStream(conn.getInputStream(), BUF_SIZE);
            int count = 0;
            while ((count = connInputStream.read(buffer)) != -1) {
                if (debugEnabled)
                    logger.debug("readReport.count=" + count);
                if (count > 0)
                    theOutput.write(buffer, 0, count);
            }
            report.setBytes(theOutput.toByteArray());
            if (debugEnabled) {
                logger.info("convertReport.theOutputArray.length=" + report.size());
                logSomeBytes(report, LOG_BYTE_COUNT);
                logReport(report);
            }
            //15264: Add performance logs for OLCSC updates
            long endTime = System.currentTimeMillis();
            logger.info("generateReport Report Id::: " + startTime + "+::args::+"+args+":: time consumed to report: " + (endTime - startTime) + "mSec");
        } finally {
            if (connInputStream != null)
                try { connInputStream.close(); }
                catch (IOException e) { logger.warn("Exception closing input stream: " + e.getMessage(), e); }
        }
        return report;
    }


    /**
     * This is the main entry point for reports processing.
     * Note: the URL accessed for the report details is determined by the DB
     * and thus could go just about anywhere.  The implementation could be
     * optimized by using localhost if the reports are deployed on the same
     * server, or non-SSL connections if the network segment itself is known
     * to be secure.
     * @param reportType the type of report requested
     * @param format the report format
     * @param args report arguments
     * @param loginDto the account login details
     * @return the requested report
     * @throws EtccException if an error occurs when getting the report URL, or if any I/O error occurs when processing the URL
     * @throws SQLException 
     * @see AccountHistoryDAO#getReportURL(AccountLoginDTO, String, String, String)
     * @see #convertReport(String)
     */
    private ReportDTO generateReport(ReportType reportType, FileFormat format, String args, AccountLoginDTO loginDto) throws EtccException {
        AccountHistoryDAO acctHistDao = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
        boolean debugEnabled = logger.isDebugEnabled();
        //15264: Add performance logs for OLCSC updates
        long startTime = System.currentTimeMillis();
        logger.info("generateReport.name=" + reportType.getDbReportName() + ", generateReport.format=" + format + ", generateReport.args=" + args+"::Report Id::"+startTime);
        String url = acctHistDao.getReportURL(loginDto, format.name(), reportType.getDbReportName(), args);
        logger.info("generateReport.url=" + url);
        // QC -15178 : modified for monthly statement , plsql might return the pdf file path for pre-existing notifications generated by questmark. 
        if  (!url.isEmpty() && !url.toLowerCase().startsWith("http") && reportType.equals(ReportType.STMT_REP)) {
			return new ReportDTO(convertTextToXlsx(getBinaryData(url)));
        }
        try {
            return convertReport(url,startTime,args);
        } catch (IOException e) {
            throw new EtccException(e.getMessage() + ". When attempting to access URL:\n" + url, e);
        }
    }
    
    private byte[] convertTextToXlsx(byte[] textBytes) throws EtccException {
    	logger.info("convertTextToXlsx started");
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Sheet1");
		String content = new String(textBytes, StandardCharsets.UTF_8);
		String[] lines = content.split("\\R");
		int rowNum = 0;
		for (String line : lines) {
			if (line.trim().isEmpty())
				continue;
			Row row = sheet.createRow(rowNum++);
			String[] cols = line.split("\\t| {2,}");
			for (int c = 0; c < cols.length; c++) {
				row.createCell(c).setCellValue(cols[c].trim());
			}
		}
		ByteArrayOutputStream out = null;
		byte[] xlsxBytes = null;
		try {
			out = new ByteArrayOutputStream();
			wb.write(out);

			xlsxBytes = out.toByteArray();
		} catch (Exception e) {
			logger.error("convertTextToXlsx Error while conversion...", e);
			throw new EtccException(e.getMessage() + " while conversion", e);
		} finally {
			try {
				wb.close();
				out.close();
			} catch (Exception e) {
				logger.error("convertTextToXlsx Error while closing workbook or out stream in xlsx conversion...", e);
				throw new EtccException(e.getMessage() + " while closing", e);
			}
		}
		logger.info("convertTextToXlsx ended");
		return xlsxBytes;
	}

    /**
     * Log the specified number of bytes from the report.
     * @param report
     * @param byteCount
     * @see ReportDTO#bytesToString(int)
     */
    private void logSomeBytes(ReportDTO report, int byteCount) {
        if (logger.isDebugEnabled()) {
            String s = report.bytesToString(byteCount);
            if (s != null)
                logger.debug("logSomeBytes: " + s);
        }
    }

 // QC -15178 : modified for monthly statement , plsql might return the pdf file path for pre-existing notifications generated by questmark. 
    private byte[] getBinaryData(String path) throws EtccException {

    	FileInputStream fileInputStream=null;
        File file = new File(path);

        byte[] bFile = new byte[(int) file.length()];

        try {
            //convert file into array of bytes
        	fileInputStream = new FileInputStream(file);
        	fileInputStream.read(bFile);
        	fileInputStream.close();

        }catch(Exception e){
        	logger.error("e.getMessage() + \". When attempting to access URL:\\n\" + path, e");
        	throw new EtccException(e.getMessage() + ". When attempting to access URL:\n" + path, e);
        }
        finally
        {
        	if(fileInputStream!=null)
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("e.getMessage() + \". When attempting to access URL:\\n\" + path, e");
					throw new EtccException(e.getMessage() + ". When attempting to access URL:\n" + path, e);
				}
        }
        return bFile;
	}
    
	
    
    private void logReport(ReportDTO report) {
        if (reportLogger.isDebugEnabled() == false) {
            logger.debug("reportLogger is disabled");
            return;
        }
        FileOutputStream fos = null;
        try {
            StringBuilder fileName = new StringBuilder();
            // ../OnlineTagStore/log/revamp/wsreportfile_2009-03-05_153245_2.rlog
            fileName.append("../OnlineTagStore/log/revamp/"); // the directory
            Calendar now = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
            String aTimeStamp = format.format(now.getTime());
            fileName.append("wsreportfile_").append(aTimeStamp);
            fileName.append("_").append(reportLoggerCount()).append(".rlog");
            logger.debug("logReport.fileName=" + fileName);
            fos = new FileOutputStream(fileName.toString());
            fos.write(report.getBytes());
        } catch (Throwable t) {
            logger.error("logReport failed: " + t.getMessage(), t);
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (Throwable t) {
                logger.error("logReport.fos.close failed: " + t.getMessage(), t);
            }
        }
    }

    private static synchronized int reportLoggerCount() {
        return Report.reportLoggerCount++;
    }
}
