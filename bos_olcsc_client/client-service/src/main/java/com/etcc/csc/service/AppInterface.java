package com.etcc.csc.service;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.dto.AccountLoginDTO;

/**
 * Contains methods for managing application-wide data.
 */
@Local
public interface AppInterface extends ServiceInterface {
    public static final String DOMAIN_NAME = "OLCSC_DOMAIN_NAME";
    public static final String HOME_PAGE_DAYS_BACK = "OLC HOME PAGE DAYS BACK";
    public static final String MAKE_PAYMENT_LABEL = "OLCSC_MAKE_PAYMENT_MENU_LABEL";
    public static final String UNPAID_TOLL_LABEL = "OLCSC_UNPAID_TOLL_MENU_LABEL";
    public static final String OLCSC_MY_EZ_TAG_MENU_LABEL = "OLCSC_MY_EZ_TAG_MENU_LABEL";
    public static final String OLC_HOME_TAB_URL = "OLC HOME TAB URL";
    public static final String OLCSC_VIEW_TRANS_DEFAULT_RANGE = "OLCSC_VIEW_TRANS_DEFAULT_RANGE";
    public static final String OLCSC_SPLASH_URL = "OLCSC_SPLASH_URL";
    public static final String OLCSC_TOUR_URL = "OLCSC_TOUR_URL";
    public static final String TS_SECURE_COOKIES = "TS_SECURE_COOKIES";
    public static final String OLCSC_SUPPORTED_BROWSER_URL = "OLCSC_SUPPORTED_BROWSER_URL";
    public static final String OLCSC_PRIVACY_POLICY_URL = "OLCSC_PRIVACY_POLICY_URL";
    public static final String OLCSC_APPLICATION_URL = "OLCSC_APPLICATION_URL";
    public static final String OLCSC_REPORT_DAYS = "OLCSC_REPORT_DAYS";
    public static final String OLCSC_TABLE_PAGE_SIZE = "OLCSC TABLE PAGE SIZE";
    public static final String OLCSC_HELP_URL = "OLCSC HELP URL";
    public static final String CLIENT_PHONE1 = "CLIENT_PHONE1";
    public static final String MAX_AUTHORIZED_CONTACTS = "MAX_AUTHORIZED_CONTACTS";

    /**
     * Key for the maximum Rebill Ammount.
     */
    public static final String MAX_REBILL_AMT = "MAX_REBILL_AMT";

    /**
     * Key for the minimum balance ratio.  Note that this value is not actually currently used except for logging
     * purposes at this time.
     */
    //BUG: Not actually used at this time.
    public static final String REBILL_MIN_BALANCE_RATIO = "REBILL_MIN_BALANCE_RATIO";

    /**
     * Gets the maximum number of contacts for an account.
     * @return The maximum number of contacts.
     * @throws EtccException thrown if an exception occurs talking to the database.
     */
    public int getAuthContactLimit() throws EtccException;

    /**
     * Retrieves the contact phone number of the client.
     * @return
     * @throws EtccException
     */
    String getContactPhoneNumber() throws EtccException;

    /**
     * Sends the user comment to the NTTA help email address
     * @param docId
     * @param docType
     * @param licPlate
     * @param replyAddress
     * @param comment
     * @return
     * @throws EtccException
     */
    boolean contactUs(long docId, String docType, String licState, String licPlate, String replyAddress, String comment, String dbSessionId) throws EtccException;

    /**
     * Retrieves the url of the 'help' page
     * @return
     * @throws EtccException
     */
    String getHelpUrl() throws EtccException;

    /**
     * Retrieves the VEA text from the database.
     * @return
     * @throws EtccException
     */
    String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices)
        throws EtccException;


    /**
     * Retrieves the page size of the paging tables from the database
     * @return
     * @throws EtccException
     */
    String getTablePageSize() throws EtccException;

    /**
     * Retrieves the max number of days that the transaction report covers.
     * @return
     * @throws EtccException
     */
    String getReportDays() throws EtccException;

    /**
     * Retrieves the url path of the application from the database
     * @return
     * @throws EtccException
     */
    String getApplicationUrl() throws EtccException;

    /**Retrieves the url for the privacy policy link
     * @return
     * @throws EtccException
     */
    String getPrivacyPolicyUrl() throws EtccException;

    /**Retrieves the url for the supported brower link
     * @return
     * @throws EtccException
     */
    String getSupportedBrowserUrl() throws EtccException;

    /**
     * Returns a flag whether to secure the cookies
     * @return
     * @throws EtccException
     */
    boolean secureCookies() throws EtccException;

    /**Retrieves the url for the tour pages
     * @return
     * @throws EtccException
     */
    String getTourUrl() throws EtccException;


    /**Retrieves the url for the splash iframe
     * @return
     * @throws EtccException
     */
    String getSplashPageUrl() throws EtccException;

    Long getPOSId( String url ) throws EtccException;

    String getViewTransDefaultRange() throws EtccException;

    /**
     * Retrieves the URL for the home tab.
     * @return
     * @throws EtccException
     */
    String getHomeTabUrl() throws EtccException;

    /**
     * Generic method to retrieve a system parameter.
     * @param paramName
     * @return
     * @throws EtccException
     */
    String getSysParam(String paramName) throws EtccException;

    /**
     * Retrieves the menu label for menu item "My EZ TAG"
     * @return
     * @throws EtccException
     */
    String getMyEZTAGMenuLabel() throws EtccException;

	/*public Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDTO,
			Long eventId) throws EtccException;*/
}
