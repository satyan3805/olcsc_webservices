package com.etcc.csc.service;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.datatype.Invoice;
import com.etcc.csc.dto.AccountLoginDTO;

/**
 * Contains methods for managing application-wide data.
 */
public interface AppInterface extends BusinessObjectInterface {
	/**
	 * Retrieves the contact phone number of the client.
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getContactPhoneNumber(String locale) throws EtccException;

	String getHomepageContactPhone(String locale) throws EtccException;

	/**
	 * Sends the user comment to the NTTA help email address
	 * 
	 * @param docId
	 * @param docType
	 * @param licPlate
	 * @param replyAddress
	 * @param comment
	 * @return
	 * @throws EtccException
	 */
	boolean contactUs(long docId, String docType, String licState,
			String licPlate, String replyAddress, String topic, String comment,
			String dbSessionId, Boolean is121Comment, Boolean updateEmail,
			String localeStr) throws EtccException;

	/**
	 * Retrieves the url of the 'help' page
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getHelpUrl(String locale) throws EtccException;

	/**
	 * Retrieves the VEA text from the database.
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices)
			throws EtccException;

	/**
	 * Retrieves the page size of the paging tables from the database
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getTablePageSize() throws EtccException;

	/**
	 * Retrieves the search parameter size for the search vehicle/tag feature
	 * from the database
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getSearchParameterSize() throws EtccException;

	/**
	 * Retrieves the max number of days that the transaction report covers.
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getReportDays() throws EtccException;

	/**
	 * Retrieves the url path of the application from the database
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getApplicationUrl(String locale) throws EtccException;

	/**
	 * Retrieves the url for the privacy policy link
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getPrivacyPolicyUrl(String locale) throws EtccException;

	/**
	 * Retrieves the url for the supported brower link
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getSupportedBrowserUrl(String locale) throws EtccException;

	/**
	 * Returns a flag whether to secure the cookies
	 * 
	 * @return
	 * @throws EtccException
	 */
	boolean secureCookies() throws EtccException;

	/**
	 * Returns the notification fee.
	 * 
	 * @return Double representing the notification fee.
	 * @throws EtccException
	 */
	double getNotificationFee() throws EtccException;

	/**
	 * Retrieves the url for the tour pages
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getTourUrl(String locale) throws EtccException;

	/**
	 * Retrieves the url for the splash iframe
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getSplashPageUrl(String locale) throws EtccException;

	Long getPOSId(String url) throws EtccException;

	String getViewTransDefaultRange() throws EtccException;

	/**
	 * Retrieves the URL for the home tab.
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getHomeTabUrl(String locale) throws EtccException;

	/**
	 * Retrieves the URL for the FAQ.
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getHelpFaqUrl(String locale) throws EtccException;

	/**
	 * Generic method to retrieve a system parameter.
	 * 
	 * @param paramName
	 * @return
	 * @throws EtccException
	 */
	String getSysParam(String paramName) throws EtccException;

	/**
	 * Generic method to retrieve a system parameter.
	 * 
	 * @param paramName
	 * @return
	 * @throws EtccException
	 */
	String getSysParam(String paramName, String locale) throws EtccException;

	String getHomePageDaysBack() throws EtccException;

	String getContactUsSuccessMessage(String locale) throws EtccException;

	/**
	 * Retrieves the cc years to add from the database
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getCCYears() throws EtccException;

	/**
	 * Retrieves the toll rate url from the database
	 * 
	 * @return
	 * @throws EtccException
	 */
	String getTollRateUrl() throws EtccException;

	String getStep7MessageUrl(String locale) throws EtccException;

	String getTempPlateExpirationLimit() throws EtccException;

	Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDto,
			Long eventId) throws EtccException;

	Long[] getCardAddressPersonId(AccountLoginDTO acctLoginDto, String fn,
			String ln, String address1, String address2, String city,
			String state, String zipCode, String plus4, Long eventId)
			throws EtccException;

	String getOlcscShiftId() throws EtccException;
}
