/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.AppDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.service.AppInterface;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.validation.AppValidator;

/**
 * App is the Component that's published for web service app.ws,
 * which provides the following operations:
 * <ul>
 * <li>contactUs
 * <li>getApplicationUrl
 * <li>getAuthContactLimit
 * <li>getContactPhoneNumber()
 * <li>getHelpUrl
 * <li>getHomeTabUrl
 * <li>getMyEZTAGMenuLabel
 * <li>getNotificationFee
 * <li>getApplicationUrl
 * <li>getPOSId
 * <li>getPrivacyPolicyUrl()
 * <li>getSplashPageUrl
 * <li>getSupportedBrowserUrl
 * <li>getSysParam
 * <li>getTablePageSize
 * <li>getTourUrl
 * <li>getVeaText
 * <li>getViewTransDefaultRange
 * <li>secureCookies()
 * </ul>
 * Copied from com.etcc.csc.service.App.
 *
 * @author      Wade Wang
 * @since       phase 1
 * @author Stephen Davidson
 */
@Stateless(name="com/etcc/App")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "App", targetNamespace = "http://ws.csc.etcc.com/App")
public class App implements AppInterface {
    private final Logger logger = Logger.getLogger(App.class);

    //@WebMethod(operationName = "getContactPhoneNumber", action = "urn:getContactPhoneNumber")
    //@WebResult(name = "contactNumber")
    public String getContactPhoneNumber() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(CLIENT_PHONE1);
    }

    //@WebMethod(operationName = "contactUs", action = "urn:contactUs")
    //@WebResult(name = "successful")
    public boolean contactUs(long docId, String docType, String licState,
                             String licPlate, String replyAddress,
                             String comment,
                             String dbSessionId) throws EtccException {
        //BUG: The database should not be tied up with long running async processing!
        //Storing the message in the database is acceptable, but NOT sending it via database call.
        this.logger.warn("Email should go direct, NOT through the database!");

        //Call this validator before calling dao, if failure, throw EtccException with warning
        AppValidator.contactUsValidator(docId, docType, licState, licPlate,replyAddress, comment, dbSessionId);

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.contactUs(docId, docType, licState, licPlate, replyAddress, comment, dbSessionId);
    }

    //@WebMethod(operationName = "getHelpUrl", action = "urn:getHelpUrl")
    //@WebResult(name = "helpUrl")
    public String getHelpUrl() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_HELP_URL);
    }

    //@WebMethod(operationName = "getVeaText", action = "urn:getVeaText")
    //@WebResult(name = "veaText")
    public String getVeaText(AccountLoginDTO acctLoginDto,
                             Invoice[] invoices) throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getVeaText(acctLoginDto, invoices);

    }

    //@WebMethod(operationName = "getTablePageSize", action = "urn:getTablePageSize")
    //@WebResult(name = "tablePageSize")
    public String getTablePageSize() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_TABLE_PAGE_SIZE);
    }

    //@WebMethod(operationName = "getReportDays", action = "urn:getReportDays")
    //@WebResult(name = "reportDays")
    public String getReportDays() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_REPORT_DAYS);
    }

    //@WebMethod(operationName = "getApplicationUrl", action = "urn:getApplicationUrl")
    //@WebResult(name = "applicationUrl")
    public String getApplicationUrl() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_APPLICATION_URL);
    }

    //@WebMethod(operationName = "getPrivacyPolicyUrl", action = "urn:getPrivacyPolicyUrl")
    //@WebResult(name = "privacyPolicyUrl")
    public String getPrivacyPolicyUrl() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_PRIVACY_POLICY_URL);
    }

    //@WebMethod(operationName = "getSupportedBrowserUrl", action = "urn:getSupportedBrowserUrl")
    //@WebResult(name = "supportedBrowserUrl")
    public String getSupportedBrowserUrl() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_SUPPORTED_BROWSER_URL);
    }

    //@WebMethod(operationName = "secureCookies", action = "urn:secureCookies")
    //@WebResult(name = "useSecureCookies")
    public boolean secureCookies() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return StringUtil.stringToBoolean(appDao.getSysParam(TS_SECURE_COOKIES));
    }

    //@WebMethod(operationName = "getTourUrl", action = "urn:getTourUrl")
    //@WebResult(name = "tourUrl")
    public String getTourUrl() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_TOUR_URL);
    }

    //@WebMethod(operationName = "getSplashPageUrl", action = "urn:getSplashPageUrl")
    //@WebResult(name = "splashPageUrl")
    public String getSplashPageUrl() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_SPLASH_URL);
    }

    //@WebMethod(operationName = "getPOSId", action = "urn:getPOSId")
    //@WebResult(name = "posId")
    public Long getPOSId(String url) throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        //only add trim(), no other validation
        return appDao.getPOSId(url == null ? null : url.trim());
    }

    //@WebMethod(operationName = "getViewTransDefaultRange", action = "urn:getViewTransDefaultRange")
    //@WebResult(name = "defaultRange")
    public String getViewTransDefaultRange() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_VIEW_TRANS_DEFAULT_RANGE);
    }

    //@WebMethod(operationName = "getHomeTabUrl", action = "urn:getHomeTabUrl")
    //@WebResult(name = "homeTabUrl")
    public String getHomeTabUrl() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLC_HOME_TAB_URL);
    }

    //@WebMethod(operationName = "getSysParam", action = "urn:getSysParam")
    //@WebResult(name = "paramValue")
    public String getSysParam(String paramName) throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(paramName);
    }

    //@WebMethod(operationName = "getMyEZTAGMenuLabel", action = "urn:getMyEZTAGMenuLabel")
    //@WebResult(name = "eztagMenuLabel")
    public String getMyEZTAGMenuLabel() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
        return appDao.getSysParam(OLCSC_MY_EZ_TAG_MENU_LABEL);
    }

    //@WebMethod(operationName = "getAuthContactLimit", action = "urn:getAuthContactLimit")
    //@WebResult(name = "authContactLimit")
    public int getAuthContactLimit() throws EtccException {
        String aLimit = getSysParam(MAX_AUTHORIZED_CONTACTS);
        aLimit = aLimit.trim();
        int theOutput = Integer.parseInt(aLimit);
        return theOutput;
    }

	public Long getAccountBillingMethodIdByToken(long token) throws EtccException {
		  DAOFactory daoFactory = DAOFactory.getDAOFactory();
	        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
			return appDao.getAccountBillingMethodIdByToken(token);
	}
	public Long getAcctVehicleId(long accid,String lic_plate_number,String lic_plate_state) throws EtccException {
		  DAOFactory daoFactory = DAOFactory.getDAOFactory();
	        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
			return appDao.getAcctVehicleId(accid,lic_plate_number,lic_plate_state);
	}
	
	public Long getInvoiceId(String InvoiceNum) throws EtccException {
		  DAOFactory daoFactory = DAOFactory.getDAOFactory();
	        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
			return appDao.getInvoiceId(InvoiceNum);
	}
	
	public Double recalculateAutochargeAndSave(Long accountId, String userName) throws EtccException {
		  DAOFactory daoFactory = DAOFactory.getDAOFactory();
	        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
			return appDao.recalculateAutochargeAndSave(accountId, userName);
	}
	//QC_10261 changes start here
		public String getPdfFilepath(String invoiceNum) throws EtccException {
			  DAOFactory daoFactory = DAOFactory.getDAOFactory();
		        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
				return appDao.getPdfFilepath(invoiceNum);
		}
   //Express AccountChnages
	public boolean isExpressAccount(Long accountId) throws EtccException {
		  DAOFactory daoFactory = DAOFactory.getDAOFactory();
	        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
			return appDao.isExpressAccount(accountId);
	}
	
	public String getRegExp(String userValue) throws EtccException {
		  DAOFactory daoFactory = DAOFactory.getDAOFactory();
	        AppDAO appDao = daoFactory.getDAO(AppDAO.class);
			return appDao.getRegExp(userValue);
	}


}
