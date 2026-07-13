/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.service.AppInterface;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.util.CacheAdmin;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.UIDateUtil;

/**
 * Wrapper between the web service stub and the web service client.
 */
// BUG: This cache is being mishandled. See
// http://www.opensymphony.com/oscache/api/index.html?com/opensymphony/oscache/general/GeneralCacheAdministrator.html
// for details.

public class AppDelegate implements AppInterface {
    private static final Logger logger = Logger.getLogger(AppDelegate.class);

    private static final String CONTACT_PHONE_NUMBER = "contactPhoneNumber";
    private static final String HELP_URL = "helpUrl";
    private static final String PAGE_SIZE = "tablePageSize";
    private static final String REPORT_DAYS = "reportDays";
    private static final String APP_URL = "applicationUrl";
    private static final String PRIVACY_POLICY_URL = "privacyPolicyUrl";
    private static final String SUPPORTED_BROWSER_URL = "supportedBrowserUrl";
    private static final String SECURE_COOKIES = "secureCookies";
    private static final String TOUR_URL = "tourUrl";
    private static final String SPLASH_PAGE_URL = "splashPageUrl";
    private static final String DEFAULT_RANGE_VIEW_TRANS = "defaultRangeViewTrans";
    private static final String HOME_TAB_URL = "homeTabUrl";
    private static final String MY_EZ_TAG_LABEL = "myEZTAGLabel";
    private static final String HTTP_PORT = "OLCSC_HTTP_PORT";
    private static final String HTTPS_PORT = "OLCSC_HTTPS_PORT";
    private static final String SWITCH_PROTOCAL = "OLCSC_SWITCH_PROTOCAL";

    private AppInterface stub() {
        return ServiceFactory.getImplementation(AppInterface.class);
    }

    public int getAuthContactLimit() throws EtccException {
        int limit = stub().getAuthContactLimit();
        return limit;
    }

    public String getContactPhoneNumber() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String phone = (String) admin.getFromCache(CONTACT_PHONE_NUMBER);
            if (phone == null) {
                try {
                    phone = stub().getContactPhoneNumber();
                    admin.putInCache(CONTACT_PHONE_NUMBER, phone);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching phone number: " + ex.getMessage(), ex);
                    admin.cancelUpdate(CONTACT_PHONE_NUMBER);
                }
            }
            return phone;
        } catch (Throwable t) {
            throw new EtccException("Error running getContactPhoneNumber: " + t, t);
        }
    }

    public boolean contactUs(long docId, String docType, String licState, String licPlate, String replyAddress,
            String comment, String dbSessionId) throws EtccException {
        boolean result = stub().contactUs(docId, docType, licState, licPlate, replyAddress, comment, dbSessionId);
        return result;
    }

    public String getHelpUrl() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String helpUrl = (String) admin.getFromCache(HELP_URL);
            if (helpUrl == null) {
                try {
                    helpUrl = stub().getHelpUrl();
                    admin.putInCache(HELP_URL, helpUrl);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching help url: " + ex.getMessage(), ex);
                    admin.cancelUpdate(HELP_URL);
                }
            }
            return helpUrl;
        } catch (Throwable t) {
            throw new EtccException("Error running getHelpUrl: " + t, t);
        }
    }

    public String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices) throws EtccException {
        String veaText = stub().getVeaText(acctLoginDto, invoices);
        return veaText;
    }

    public String getTablePageSize() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String pageSize = (String) admin.getFromCache(PAGE_SIZE);
            if (pageSize == null) {
                try {
                    pageSize = stub().getTablePageSize();
                    admin.putInCache(PAGE_SIZE, pageSize);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching table page size: " + ex.getMessage(), ex);
                    admin.cancelUpdate(PAGE_SIZE);
                }
            }
            return pageSize;
        } catch (Throwable t) {
            throw new EtccException("Error running getTablePageSize: " + t, t);
        }
    }

    public String getReportDays() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String reportDays = (String) admin.getFromCache(REPORT_DAYS);
            if (reportDays == null) {
                try {
                    reportDays = stub().getReportDays();
                    admin.putInCache(REPORT_DAYS, reportDays);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching report days: " + ex.getMessage(), ex);
                    admin.cancelUpdate(REPORT_DAYS);
                }
            }
            return reportDays;
        } catch (Throwable t) {
            throw new EtccException("Error running getReportDays: " + t, t);
        }
    }

    public String getApplicationUrl() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String appURL = (String) admin.getFromCache(APP_URL);
            if (appURL == null) {
                try {
                    appURL = stub().getApplicationUrl();
                    admin.putInCache(APP_URL, appURL);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching application url: " + ex.getMessage(), ex);
                    admin.cancelUpdate(APP_URL);
                }
            }
            return appURL;
        } catch (Throwable t) {
            throw new EtccException("Error running getApplicationUrl: " + t, t);
        }
    }

    public String getPrivacyPolicyUrl() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String privacyPolicyURL = (String) admin.getFromCache(PRIVACY_POLICY_URL);
            if (privacyPolicyURL == null) {
                try {
                    privacyPolicyURL = stub().getPrivacyPolicyUrl();
                    admin.putInCache(PRIVACY_POLICY_URL, privacyPolicyURL);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching privacy policy url: " + ex.getMessage(), ex);
                    admin.cancelUpdate(PRIVACY_POLICY_URL);
                }
            }
            return privacyPolicyURL;
        } catch (Throwable t) {
            throw new EtccException("Error running getPrivacyPolicyUrl: " + t, t);
        }
    }

    public String getSupportedBrowserUrl() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String supportedBrowserURL = (String) admin.getFromCache(SUPPORTED_BROWSER_URL);
            if (supportedBrowserURL == null) {
                try {
                    supportedBrowserURL = stub().getSupportedBrowserUrl();
                    admin.putInCache(SUPPORTED_BROWSER_URL, supportedBrowserURL);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching support browser url: " + ex.getMessage(), ex);
                    admin.cancelUpdate(SUPPORTED_BROWSER_URL);
                }
            }
            return supportedBrowserURL;
        } catch (Throwable t) {
            throw new EtccException("Error running getPrivacyPolicyUrl: " + t, t);
        }
    }

    public boolean secureCookies() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String secureCookies = (String) admin.getFromCache(SECURE_COOKIES);
            boolean result = false;
            if (secureCookies == null) {
                try {
                    result = stub().secureCookies();
                    admin.putInCache(SECURE_COOKIES, StringUtil.booleanToString(result));
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching secure cookies: " + ex.getMessage(), ex);
                    admin.cancelUpdate(SECURE_COOKIES);
                }
            } else {
                result = StringUtil.stringToBoolean(secureCookies);
            }
            return result;
        } catch (Throwable t) {
            throw new EtccException("Error running secureCookies: " + t, t);
        }
    }

    public String getTourUrl() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String tourUrl = (String) admin.getFromCache(TOUR_URL);
            if (tourUrl == null) {
                try {
                    tourUrl = stub().getTourUrl();
                    admin.putInCache(TOUR_URL, tourUrl);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching tour url: " + ex.getMessage(), ex);
                    admin.cancelUpdate(TOUR_URL);
                }
            }
            return tourUrl;
        } catch (Throwable t) {
            throw new EtccException("Error running getTourUrl: " + t, t);
        }
    }

    public String getSplashPageUrl() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String splashPageUrl = (String) admin.getFromCache(SPLASH_PAGE_URL);
            if (splashPageUrl == null) {
                try {
                    splashPageUrl = stub().getSplashPageUrl();
                    admin.putInCache(SPLASH_PAGE_URL, splashPageUrl);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching splash page url: " + ex.getMessage(), ex);
                    admin.cancelUpdate(SPLASH_PAGE_URL);
                }
            }
            return splashPageUrl;
        } catch (Throwable t) {
            throw new EtccException("Error running getSplashPageUrl: " + t, t);
        }
    }

    public Long getPOSId(String url) {
        try {
            return stub().getPOSId(url);
        } catch (EtccException ex) {
            logger.warn("Exception getting/caching pos id: " + ex.getMessage(), ex);
            return null;
        }
    }

    public String getViewTransDefaultRange() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String defaultRange = (String) admin.getFromCache(DEFAULT_RANGE_VIEW_TRANS);
            if (defaultRange == null) {
                try {
                    defaultRange = stub().getViewTransDefaultRange();
                    admin.putInCache(DEFAULT_RANGE_VIEW_TRANS, defaultRange);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching default range: " + ex.getMessage(), ex);
                    admin.cancelUpdate(DEFAULT_RANGE_VIEW_TRANS);
                }
            }
            return defaultRange;
        } catch (Throwable t) {
            throw new EtccException("Error running getApplicationUrl: " + t, t);
        }
    }

    public String getHomeTabUrl() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String url = (String) admin.getFromCache(HOME_TAB_URL);
            if (url == null) {
                try {
                    url = stub().getHomeTabUrl();
                    admin.putInCache(HOME_TAB_URL, url);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching home tab url: " + ex.getMessage(), ex);
                    admin.cancelUpdate(HOME_TAB_URL);
                }
            }
            return url;
        } catch (Throwable t) {
            throw new EtccException("Error running getHomeTabUrl: " + t, t);
        }
    }

    public String getSysParam(String paramName) throws EtccException {
        return ServiceFactory.getImplementation(AppInterface.class).getSysParam(paramName);
    }

    public String getHomePageDaysBack() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String value = (String) admin.getFromCache(HOME_PAGE_DAYS_BACK);
            if (value == null) {
                try {
                    value = getSysParam(HOME_PAGE_DAYS_BACK);
                    admin.putInCache(HOME_PAGE_DAYS_BACK, value);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching home page days back: " + ex.getMessage(), ex);
                    admin.cancelUpdate(HOME_PAGE_DAYS_BACK);
                }
            }
            return value;
        } catch (Throwable t) {
            throw new EtccException("Error running getHomePageDaysBack: " + t, t);
        }
    }

    public String getMyEZTAGMenuLabel() throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String label = (String) admin.getFromCache(MY_EZ_TAG_LABEL);
            if (label == null) {
                try {
                    label = stub().getMyEZTAGMenuLabel();
                    admin.putInCache(MY_EZ_TAG_LABEL, label);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching eztag menu label: " + ex.getMessage(), ex);
                    admin.cancelUpdate(MY_EZ_TAG_LABEL);
                }
            }
            return label;
        } catch (Throwable t) {
            throw new EtccException("Error running getMyEZTAGMenuLabel: " + t, t);
        }
    }

    public String getSysParamFromCache(String paramName) throws EtccException {
        try {
            CacheAdmin admin = CacheAdmin.getInstance();
            String label = (String) admin.getFromCache(paramName);
            if (label == null) {
                try {
                    label = stub().getSysParam(paramName);
                    admin.putInCache(paramName, label);
                } catch (Exception ex) {
                    logger.warn("Exception getting/caching system param[" + paramName + "]: " + ex.getMessage(), ex);
                    admin.cancelUpdate(paramName);
                }
            }
            return label;
        } catch (Throwable t) {
            // TODO: Log & FIX This!
            String msg = "Error running getSysParam (paramName): " + paramName + " - " + t.getMessage();
            logger.warn(msg, t);
            throw new EtccException(msg, t);
        }
    }

    public String getMakePaymentMenuLabel() throws EtccException {
        return getSysParamFromCache(MAKE_PAYMENT_LABEL);
    }

    public String getUnpaidTollMenuLabel() throws EtccException {
        return getSysParamFromCache(UNPAID_TOLL_LABEL);
    }

    public String getHttpPort() throws EtccException {
        return getSysParamFromCache(HTTP_PORT);
    }

    public String getHttpsPort() throws EtccException {
        return getSysParamFromCache(HTTPS_PORT);
    }

    public String getDomainName() {
        String domainName = null;
        try {
            domainName = getSysParamFromCache(DOMAIN_NAME);
        } catch (Exception e) {
            logger.error("Problem retrieving domain name:" + e.getMessage(), e);
        }
        return domainName;
    }

    /**
     * Returns whether the communications protocol should be switched.
     * @return <tt>true</tt> if the communications protocol should be switched
     * @throws EtccException if an error is encountered
     */
    public boolean isSwitchProtocol() throws EtccException {
        // return getSysParamFromCache(SWITCH_PROTOCAL);
        String switchProtocolFlag = "N";
        return StringUtil.stringToBoolean(switchProtocolFlag);
    }

    public Collection<LabelValueBean> getVehicleYears() {
        return UIDateUtil.getVehicleYears(1900);
    }

	/*public Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDTO,
			Long eventId) throws EtccException {
		return stub().getAccountPostingAndShiftId(acctLoginDTO,eventId);
	}*/
}
