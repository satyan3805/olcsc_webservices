/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.util.StringUtil;

/**
 * Test implementation of <tt>AppInterface</tt>.
 * @author Milosh Boroyevich
 */
public class AppTestImpl implements AppInterface {
    private static final Logger logger = Logger.getLogger(AppTestImpl.class);
//    private String user;
//    private String category;

    /**
     * @see AppInterface#getAuthContactLimit()
     */
    public int getAuthContactLimit() throws EtccException {
        return 5;
    }

    public String getContactPhoneNumber() throws EtccException {
        return "214-615-2381";
    }

    public boolean contactUs(long docId, String docType, String licState, String licPlate, String replyAddress, String comment, String dbSessionId) throws EtccException {
        logger.debug("contactUs: " + licState + "-" + licPlate + ":" + replyAddress);
        logger.debug("contactUs comment: " + comment);
        return true;
    }

    public String getHelpUrl() throws EtccException {
        return "/about/";
    }

    public String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices) throws EtccException {
        return "Vea Text for account login: " + acctLoginDto;
    }

    public String getTablePageSize() throws EtccException {
        return "2";
    }

    public String getReportDays() throws EtccException {
        return "60";
    }

    public String getApplicationUrl() throws EtccException {
        return "/eztagstore/";
    }

    public String getPrivacyPolicyUrl() throws EtccException {
        return "/about/";
    }

    public String getSupportedBrowserUrl() throws EtccException {
        return "/about/";
    }

    public boolean secureCookies() throws EtccException {
        return false;
    }

    /**
     * @throws EtccException
     */
    public double getNotificationFee() throws EtccException {
        return 25.00;
    }

    public String getTourUrl() throws EtccException {
        return "/about/";
    }

    public String getSplashPageUrl() throws EtccException {
        return "/";
    }

    public Long getPOSId( String url ) throws EtccException {
        return new Long(1);
    }

    public String getViewTransDefaultRange() throws EtccException {
        return "10-50";
    }

    public String getHomeTabUrl() throws EtccException {
        return "/";
    }

    public String getSysParam(String paramName) throws EtccException {
        if (DOMAIN_NAME.equals(paramName))
            return "http://localhost:7001";
        else if ("MULTIPLE_VEHICLE_UPLOAD_MAXIMUM".equals(paramName))
            return "50";
        else if ("PCI_APPLIANCE_WS_URL".equals(paramName))
            return "http://rchq-hctraboswcd02-vmli.etcchostedservices.local/paymentws/PANManager";
        return "AppTestImpl Sys Param for: " + paramName;
    }

    public String getMyEZTAGMenuLabel() throws EtccException {
        return "EZ Account";
    }

    public boolean isSwitchProtocol() throws EtccException {
        // return getSysParamFromCache(SWITCH_PROTOCAL);
        String switchProtocolFlag = "N";
        return StringUtil.stringToBoolean(switchProtocolFlag);
    }

	public Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDTO,
			Long eventId) throws EtccException {
		// TODO Auto-generated method stub
		return new Long[]{1L,2L};
	}
}
