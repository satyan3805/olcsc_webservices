package com.etcc.csc.service;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.AppDAO;
import com.etcc.csc.datatype.Invoice;
import com.etcc.csc.dto.AccountLoginDTO;

public class App implements AppInterface {
	private Logger logger = Logger.getLogger(App.class);

	public App() {
	}

	public String getHomePageDaysBack() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getHomePageDaysBack();
		} catch (EtccException ee) {
			logger.error("Exception in getContactPhoneNumber() " + ee, ee);
			throw ee;
		}
	}

	public String getContactPhoneNumber(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getContactPhoneNumber(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getContactPhoneNumber() " + ee, ee);
			throw ee;
		}
	}

	public String getHomepageContactPhone(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getHomepageContactPhone(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getContactPhoneNumber() " + ee, ee);
			throw ee;
		}
	}

	public boolean contactUs(long docId, String docType, String licState,
			String licPlate, String replyAddress, String topic, String comment,
			String dbSessionId, Boolean is121Comment, Boolean updateEmail,
			String localeStr) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.contactUs(docId, docType, licState, licPlate,
					replyAddress, topic, comment, dbSessionId, is121Comment,
					updateEmail, localeStr);
		} catch (EtccException ee) {
			logger.error("Exception in contactUs() " + ee, ee);
			throw ee;
		}
	}

	public String getHelpUrl(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getHelpUrl(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getHelpUrl() " + ee, ee);
			throw ee;
		}
	}

	public String getHelpFaqUrl(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getHelpFaqUrl(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getHelpUrl() " + ee, ee);
			throw ee;
		}
	}

	public String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices)
			throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getVeaText(acctLoginDto, invoices);
		} catch (EtccException ee) {
			logger.error("Exception in getVeaText() " + ee, ee);
			throw ee;
		}

	}

	public String getTablePageSize() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getTablePageSize();
		} catch (EtccException ee) {
			logger.error("Exception in getTablePageSize() " + ee, ee);
			throw ee;
		}
	}

	public String getSearchParameterSize() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getSearchParameterSize();
		} catch (EtccException ee) {
			logger.error("Exception in getSearchParameterSize() " + ee, ee);
			throw ee;
		}
	}

	public String getReportDays() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getReportDays();
		} catch (EtccException ee) {
			logger.error("Exception in getReportDays() " + ee, ee);
			throw ee;
		}
	}

	public String getApplicationUrl(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getApplicationUrl(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getApplicationUrl() " + ee, ee);
			throw ee;
		}
	}

	public String getPrivacyPolicyUrl(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getPrivacyPolicyUrl(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getPrivatePolicyUrl() " + ee, ee);
			throw ee;
		}
	}

	public String getSupportedBrowserUrl(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getSupportedBrowserUrl(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getSupportedBrowserUrl() " + ee, ee);
			throw ee;
		}
	}

	public boolean secureCookies() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.secureCookies();
		} catch (EtccException ee) {
			logger.error("Exception in secureCookies() " + ee, ee);
			throw ee;
		}
	}

	public double getNotificationFee() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getNotificationFee();
		} catch (EtccException ee) {
			logger.error("Exception in getNotificationFee() " + ee, ee);
			throw ee;
		}
	}

	public String getTourUrl(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getTourUrl(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getTourUrl() " + ee, ee);
			throw ee;
		}
	}

	public String getSplashPageUrl(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getSplashPageUrl(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getSplashPageUrl() " + ee, ee);
			throw ee;
		}
	}

	public Long getPOSId(String url) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getPOSId(url);
		} catch (EtccException ee) {
			logger.error("Exception in getPOSId() " + ee, ee);
			throw ee;
		}
	}

	public String getViewTransDefaultRange() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getViewTransDefaultRange();
		} catch (EtccException ee) {
			logger.error("Exception in getViewTransDefaultRange() " + ee, ee);
			throw ee;
		}
	}

	public String getHomeTabUrl(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getHomeTabUrl(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getHomeTabUrl() " + ee, ee);
			throw ee;
		}
	}

	public String getContactUsSuccessMessage(String locale)
			throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getContactUsSuccessMessage(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getHomeTabUrl() " + ee, ee);
			throw ee;
		}

	}

	public String getSysParam(String paramName) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getSysParam(paramName);
		} catch (EtccException ee) {
			logger.error("Exception in getSysParam() " + ee, ee);
			throw ee;
		}
	}

	public String getSysParam(String paramName, String locale)
			throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getSysParam(paramName);
		} catch (EtccException ee) {
			logger.error("Exception in getSysParam() " + ee, ee);
			throw ee;
		}
	}

	public String getCCYears() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getCCYears();
		} catch (EtccException ee) {
			logger.error("Exception in getCCYears() " + ee, ee);
			throw ee;
		}
	}

	public String getTollRateUrl() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getTollRateUrl();
		} catch (EtccException ee) {
			logger.error("Exception in getTollRateUrl() " + ee, ee);
			throw ee;
		}
	}

	public String getStep7MessageUrl(String locale) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getStep7MessageUrl(locale);
		} catch (EtccException ee) {
			logger.error("Exception in getStep7MessageUrl() " + ee, ee);
			throw ee;
		}
	}

	public String getTempPlateExpirationLimit() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getTempPlateExpirationLimit();
		} catch (EtccException ee) {
			logger.error("Exception in getTempPlateExpirationLimit() " + ee, ee);
			throw ee;
		}
	}

	public Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDto,
			Long eventId) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getAccountPostingAndShiftId(acctLoginDto, eventId);
		} catch (EtccException ee) {
			logger.error("Exception in getAccountPostingAndShiftId() " + ee, ee);
			throw ee;
		}
	}

	public Long[] getCardAddressPersonId(AccountLoginDTO acctLoginDto,
			String fn, String ln, String address1, String address2,
			String city, String state, String zipCode, String plus4,
			Long eventId) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getCardAddressPersonId(acctLoginDto, fn, ln,
					address1, address2, city, state, zipCode, plus4, eventId);
		} catch (EtccException ee) {
			logger.error("Exception in getCardAddressPersonId() " + ee, ee);
			throw ee;
		}
	}

	public String getOlcscShiftId() throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			AppDAO appDao = daoFactory.getAppDAO();
			return appDao.getOlcscShiftId();
		} catch (EtccException ee) {
			logger.error("Exception in getTempPlateExpirationLimit() " + ee, ee);
			throw ee;
		}
	}

}
