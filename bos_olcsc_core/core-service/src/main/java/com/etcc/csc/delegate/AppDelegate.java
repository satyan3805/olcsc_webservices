package com.etcc.csc.delegate;

import com.etcc.csc.common.AppEnum;
import com.etcc.csc.common.CacheAdmin;
import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.common.Util;
import com.etcc.csc.datatype.Invoice;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.service.AppInterface;

/**
 * Wrapper between the web service stub and the web service client.
 */
public class AppDelegate extends Delegate implements AppInterface {
	AppInterface app = (AppInterface) getServiceObject(ServiceObjectEnum.APP);

	// Cache will be automatically refreshed every day
	public static int CACHE_REFRESH_PERIOD = 14400;

	public AppDelegate() {
		super(AppDelegate.class);
	}

	public String getContactPhoneNumber(String locale) throws EtccException {
		String key = AppEnum.CONTACT_PHONE_NUMBER.getValue()
				+ locale.toUpperCase();
		CacheAdmin admin = CacheAdmin.getInstance();

		try {
			String phone = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (phone == null) {
				try {
					phone = app.getContactPhoneNumber(locale);
					admin.putInCache(key, phone);
				} catch (Exception ex) {
					logger.error(ex);
					admin.cancelUpdate(key);
				}
			}
			return phone;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException(
					"Error running getContactPhoneNumber: " + t, t);
		}
	}

	public String getHomepageContactPhone(String locale) throws EtccException {

		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.HOMEPAGE_CONTACT_PHONE_NUMBER.getValue()
				+ locale.toUpperCase();

		try {
			String phone = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (phone == null) {
				try {
					app.getHomepageContactPhone(locale);
					admin.putInCache(key, phone);
				} catch (Exception ex) {
					admin.cancelUpdate(key);
				}
			}
			return phone;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException(
					"Error running getContactPhoneNumber: " + t, t);
		}
	}

	public boolean contactUs(long docId, String docType, String licState,
			String licPlate, String replyAddress, String topic, String comment,
			String dbSessionId, Boolean is121Comment, Boolean updateEmail,
			String localeStr) throws EtccException {
		try {

			return app.contactUs(docId, docType, licState, licPlate,

			replyAddress, topic, comment, dbSessionId, is121Comment,
					updateEmail, localeStr);

		} catch (Throwable t) {

			logger.error(t);
			throw new EtccException("Error running contactUs:" + t, t);
		}
	}

	public String getContactUsSuccessMessage(String locale)
			throws EtccException {
		try {
			return app.getContactUsSuccessMessage(locale);
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running contactUs:" + t, t);
		}
	}

	public String getHelpUrl(String locale) throws EtccException {

		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.HELP_URL.getValue() + locale.toUpperCase();

		try {

			String helpUrl = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (helpUrl == null) {
				try {
					helpUrl = app.getHelpUrl(locale);
					admin.putInCache(key, helpUrl);
				} catch (Exception ex) {
					admin.cancelUpdate(key);
				}
			}
			return helpUrl;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getHelpUrl: " + t, t);
		}
	}

	public String getTablePageSize() throws EtccException {

		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.PAGE_SIZE.getValue();

		try {
			String pageSize = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (pageSize == null) {
				pageSize = app.getTablePageSize();
				admin.putInCache(key, pageSize);
			}
			return pageSize;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getTablePageSize: " + t, t);
		}
	}

	public String getSearchParameterSize() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.SEARCH_PARAMETER_SIZE.getValue();

		try {
			String searchSize = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (searchSize == null) {
				searchSize = app.getSearchParameterSize();
				admin.putInCache(key, searchSize);
			}
			return searchSize;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getSearchParameterSize: "
					+ t, t);
		}

	}

	public String getReportDays() throws EtccException {

		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.REPORT_DAYS.getValue();
		String reportDays = (String) admin.getFromCache(key,
				CACHE_REFRESH_PERIOD);

		try {
			if (reportDays == null) {

				reportDays = app.getReportDays();
				admin.putInCache(key, reportDays);
			}
			return reportDays;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getReportDays: " + t, t);
		}
	}

	public String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices)
			throws EtccException {
		try {
			return app.getVeaText(acctLoginDto, invoices);
		} catch (Throwable t) {
			throw new EtccException("Error running getVeaText:" + t, t);
		}
	}

	public String getApplicationUrl(String locale) throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.APP_URL.getValue() + locale.toUpperCase();

		try {
			String appURL = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (appURL == null) {
				appURL = app.getApplicationUrl(locale);
				admin.putInCache(key, appURL);
			}
			return appURL;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getApplicationUrl: " + t, t);
		}
	}

	public String getPrivacyPolicyUrl(String locale) throws EtccException {

		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.PRIVACY_POLICY_URL.getValue()
				+ locale.toUpperCase();

		try {
			String privacyPolicyURL = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (privacyPolicyURL == null) {
				privacyPolicyURL = app.getPrivacyPolicyUrl(locale);
				admin.putInCache(key, privacyPolicyURL);
			}
			return privacyPolicyURL;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getPrivacyPolicyUrl: " + t,
					t);
		}
	}

	public String getSupportedBrowserUrl(String locale) throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.SUPPORTED_BROWSER_URL.getValue()
				+ locale.toUpperCase();

		try {
			String supportedBrowserURL = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (supportedBrowserURL == null) {

				supportedBrowserURL = app.getSupportedBrowserUrl(locale);
				admin.putInCache(key, supportedBrowserURL);

			}
			return supportedBrowserURL;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getPrivacyPolicyUrl: " + t,
					t);
		}
	}

	public boolean secureCookies() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.SECURE_COOKIES.getValue();
		String secureCookies = (String) admin.getFromCache(key,
				CACHE_REFRESH_PERIOD);
		try {

			boolean result = false;
			if (secureCookies == null) {
				result = app.secureCookies();
				secureCookies = Util.booleanToString(result);
				admin.putInCache(key, secureCookies);
			} else {
				result = Util.stringToBoolean(secureCookies);
			}
			return result;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running secureCookies: " + t, t);
		}
	}

	public double getNotificationFee() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.NOTIFICATION_FEE.getValue();
		Double result = (Double) admin.getFromCache(key, CACHE_REFRESH_PERIOD);

		try {
			if (result == null) {
				result = app.getNotificationFee();
				admin.putInCache(key, result);

			}
			return result;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getNotificationFee: " + t, t);
		}
	}

	public String getTourUrl(String locale) throws EtccException {
		String key = AppEnum.TOUR_URL.getValue() + locale.toUpperCase();
		CacheAdmin admin = CacheAdmin.getInstance();
		String tourUrl = (String) admin.getFromCache(key, CACHE_REFRESH_PERIOD);
		try {

			if (tourUrl == null) {

				tourUrl = app.getTourUrl(locale);
				admin.putInCache(key, tourUrl);

			}
			return tourUrl;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getTourUrl: " + t, t);
		}
	}

	public String getSplashPageUrl(String locale) throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.SPLASH_PAGE_URL.getValue() + locale.toUpperCase();
		String splashPageUrl = (String) admin.getFromCache(key,
				CACHE_REFRESH_PERIOD);
		try {
			if (splashPageUrl == null) {

				splashPageUrl = app.getSplashPageUrl(locale);
				admin.putInCache(key, splashPageUrl);
			}
			return splashPageUrl;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);

			throw new EtccException("Error running getSplashPageUrl: " + t, t);
		}
	}

	public String getHelpFaqUrl(String locale) throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.HELP_FAQ_URL.getValue() + locale.toUpperCase();
		String helpFaqUrl = (String) admin.getFromCache(key,
				CACHE_REFRESH_PERIOD);

		try {
			if (helpFaqUrl == null) {
				helpFaqUrl = app.getHelpFaqUrl(locale);
				admin.putInCache(key, helpFaqUrl);
			}
			return helpFaqUrl;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getHelpFaqUrl: " + t, t);
		}
	}

	public Long getPOSId(String url) {
		try {
			return app.getPOSId(url);
		} catch (Throwable t) {
			logger.error(t);
			return null;
		}
	}

	public String getViewTransDefaultRange() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.DEFAULT_RANGE_VIEW_TRANS.getValue();
		String defaultRange = (String) admin.getFromCache(key,
				CACHE_REFRESH_PERIOD);

		try {
			if (defaultRange == null) {
				defaultRange = app.getViewTransDefaultRange();
				admin.putInCache(key, defaultRange);

			}
			return defaultRange;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getViewTransDefaultRange: "
					+ t, t);
		}
	}

	public String getHomeTabUrl(String locale) throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.HOME_TAB_URL.getValue() + locale.toUpperCase();
		String url = (String) admin.getFromCache(key, CACHE_REFRESH_PERIOD);

		try {
			if (url == null) {
				url = app.getHomeTabUrl(locale);
				admin.putInCache(key, url);
			}
			return url;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getHomeTabUrl: " + t, t);
		}
	}

	public String getSysParam(String paramName) throws EtccException {
		try {
			return app.getSysParam(paramName);
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running getSysParam (paramName): "
					+ paramName + " - " + t, t);
		}
	}

	public String getSysParam(String paramName, String locale)
			throws EtccException {
		try {
			return app.getSysParam(paramName, locale);
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running getSysParam (paramName): "
					+ paramName + " - " + t, t);
		}
	}

	public String getHomePageDaysBack() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.HOME_PAGE_DAYS_BACK.getValue();

		try {
			String value = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (value == null) {
				value = app.getHomePageDaysBack();
				admin.putInCache(key, value);
			}
			return value;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getHomePageDaysBack: " + t,
					t);
		}
	}

	public String isSpanishEnabled() throws EtccException {
		try {
			CacheAdmin admin = CacheAdmin.getInstance();
			String key = AppEnum.OLCSC_SPANISH_ACTIVE.getValue();
			String value = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (value == null) {
				value = getSysParam(key);
				admin.putInCache(key, value);

			}
			return value;
		} catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running isSpanishEnabled: " + t, t);
		}

	}

	public String getCCYears() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.OLCSC_CC_YEARS.getValue();

		try {
			String ccYears = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (ccYears == null) {
				ccYears = app.getCCYears();
				admin.putInCache(key, ccYears);
			}
			return ccYears;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getCCYears: " + t, t);
		}
	}

	public String getStep7MessageUrl(String locale) throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.STEP7_MESSAGE_URL.getValue()
				+ locale.toUpperCase();
		String url = (String) admin.getFromCache(key, CACHE_REFRESH_PERIOD);

		try {

			if (url == null) {

				url = app.getStep7MessageUrl(locale);
				admin.putInCache(key, url);

			}
			return url;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getStep7MessageUrl: " + t, t);
		}
	}

	public String getTempPlateExpirationLimit() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.OLCSC_TEMP_PLATE_EXPIRATION_LIMIT.getValue();

		try {
			String limit = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (limit == null) {
				limit = app.getTempPlateExpirationLimit();
				admin.putInCache(key, limit);
			}
			return limit;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException(
					"Error running getTempPlateExpirationLimit: " + t, t);
		}
	}

	public String getTollRateUrl() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String key = AppEnum.TOLLRATE_URL.getValue();

		try {
			String tollRateUrl = (String) admin.getFromCache(key,
					CACHE_REFRESH_PERIOD);
			if (tollRateUrl == null) {
				tollRateUrl = app.getTollRateUrl();
				admin.putInCache(key, tollRateUrl);
			}
			return tollRateUrl;
		} catch (Throwable t) {
			admin.cancelUpdate(key);
			logger.error(t);
			throw new EtccException("Error running getTollRateUrl: " + t, t);
		}
	}

	public Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDto,
			Long eventId) throws EtccException {
		try {
			return app.getAccountPostingAndShiftId(acctLoginDto, eventId);

		} catch (Throwable t) {

			logger.error(t);
			throw new EtccException(
					"Error running getAccountPostingAndShiftId: " + t, t);
		}
	}

	public Long[] getCardAddressPersonId(AccountLoginDTO acctLoginDto,
			String fn, String ln, String address1, String address2,
			String city, String state, String zipCode, String plus4,
			Long eventId) throws EtccException {
		try {
			return app.getCardAddressPersonId(acctLoginDto, fn, ln, address1,
					address2, city, state, zipCode, plus4, eventId);

		} catch (Throwable t) {

			logger.error(t);
			throw new EtccException("Error running getCardAddressPersonId: "
					+ t, t);
		}
	}

	public String getOlcscShiftId() throws EtccException {
		try {
			return app.getOlcscShiftId();

		} catch (Throwable t) {

			logger.error(t);
			throw new EtccException("Error running getOlcscShiftId: " + t, t);
		}
	}
}
