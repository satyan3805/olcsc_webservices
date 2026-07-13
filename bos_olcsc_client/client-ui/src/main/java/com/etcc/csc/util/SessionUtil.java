/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.AccountPreferenceDelegate;
import com.etcc.csc.delegate.AuthorizedContactDelegate;
import com.etcc.csc.delegate.MenuDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.datatype.PaymentContext;
import com.etcc.csc.presentation.form.ContactInfoForm;

/**
 * Methods used for managing HTTP session state.
 * Note: some of the methods also set HTTP response attributes.
 * @author unknown
 * @author Milosh Boroyevich
 */
public class SessionUtil {
    private static final String SESSION_CONTACT_INFO_FORM = "contactInfoForm";

    private static final Logger logger = Logger.getLogger(SessionUtil.class);

	private static final String SESSION_CURRENT_TAB_MENU_ID = "currentTabMenuId";
    private static final String SESSION_RETAIL_TRANS_ID = "retailTransId";
    private static final String SESSION_AMOUNT_DUE = "paymentAmountDue";
    private static final String SESSION_CC_SECURITY_CODE = "creditCardSecurityCode";
    private static final String SESSION_LOGIN_ENTRY_POINT = "loginEntryPoint";
    private static final String SESSION_CURRENT_ACCT_MGMT_MENU_ID = "currentAcctMgmtMenuId";
//    private static final String SESSION_CURRENT_TTRY_MENUS = "currentTertiaryMenus";
	private static final String SESSION_ACCOUNT_LOGIN_INFO = "acctLoginInfo";
	private static final String SESSION_ACCOUNT = "account";
//	private static final String SESSION_SHOPPING_CART = "shoppingCart";
	private static final String SESSION_SAVED_VEHICLE_MAP = "savedVehicles";

	public static final String REQUEST_ACCOUNT_INFO = "acctInfo";
	public static final String REQUEST_BILLING_INFO = "billingInfo";
	public static final String REQUEST_ACCOUNT_PREFERENCES = "preferencesDTO";
	public static final String REQUEST_TTRY_MENUS = "tertiaryMenus";
    private static final String REQUEST_VEHICLES = "vehiclesList";

    private static final String POS_ID = "POS_ID";

//    private static final String PAGE_MENU = "pageMenus";
//    private static final String ACCT_MGMT_MENU = "acctMgmtTabMenus";
    private static final String PROTOCAL = "protocal";

    private SessionUtil() { }

    /**
     * Returns the AccountLogin data for the currently logged in user.
     * @param session
     * @return the account login or <tt>null</tt> if not logged in or an error is encountered
     */
    public static AccountLoginDTO getSessionAccountLogin(HttpSession session) {
        if (session != null)
            try {
                return (AccountLoginDTO)session.getAttribute(SessionUtil.SESSION_ACCOUNT_LOGIN_INFO);
            } catch (Throwable t) {
                logger.warn("Exception in SessionUtil.getSessionAccountLogin(): " + t.getMessage(), t);
            }
        return null;
    }

    /**
     * Sets the AccountLogin data for the currently logged in user.
     * @param session
     */
    public static void setSessionAccountLogin(HttpSession session, AccountLoginDTO acctLoginDto) {
        session.setAttribute(SessionUtil.SESSION_ACCOUNT_LOGIN_INFO, acctLoginDto);
    }

    /**
     * Sets the current tab menu id to the given value.
     * @param session
     * @param menuId
     */
    public static void setCurrentTabMenuId(HttpSession session, int menuId) {
    	logger.debug("Setting Tab Menu ID to: " + menuId);
        session.setAttribute(SESSION_CURRENT_TAB_MENU_ID, Integer.valueOf(menuId));
    }

    /**
     * Returns the id of the current tab.
     * @param session
     * @return
     */
    public static int getCurrentTabMenuId(HttpSession session) {
        try {
            return ((Integer)session.getAttribute(SESSION_CURRENT_TAB_MENU_ID)).intValue();
        } catch (Throwable t) {
            return -1;
        }
    }

    public static void resetCurrentTabMenuId(HttpSession session) {
        if (session != null)
        session.removeAttribute(SESSION_CURRENT_TAB_MENU_ID);
    }

    public static void setCurrentAcctMgmtTabMenuId(HttpSession session, int menuId) {
        session.setAttribute(SESSION_CURRENT_ACCT_MGMT_MENU_ID, Integer.valueOf(menuId));
    }


    public static int getCurrentAcctMgmtTabMenuId(HttpSession session) {
        try {
            return ((Integer)session.getAttribute(SESSION_CURRENT_ACCT_MGMT_MENU_ID)).intValue();
        } catch (Throwable t) {
            return -1;
        }
    }

    public static void resetCurrentAcctMgmtTabMenuId(HttpSession session) {
        if (session != null)
        session.removeAttribute(SESSION_CURRENT_ACCT_MGMT_MENU_ID);
    }


    /**
     * Stores the user's retail transaction id and amount due to session so
     * it can be retrieved by the payment screen.
     *
     * @param retailTransId
     * @param amountDue
     */
    public static void setPaymentInfo(HttpSession session, long retailTransId, double amountDue) {
    	SessionUtil.setRetailTransId(session, retailTransId);
    	SessionUtil.setAmountDue(session, amountDue);
    }

    /**
     * Stores the credit card security code in session for use by the
     * payment module.
     * @param session
     * @param value
     */
    public static void setCcSecurityCode(HttpSession session, long value) {
        if (session != null) {
            session.setAttribute(SESSION_CC_SECURITY_CODE, Long.valueOf(value));
        }
    }
    /**
     * Retrieves the security code stored in session.
     * @param session
     * @return
     */
    public static long getCcSecurityCode(HttpSession session) {
        if (session != null) {
            return ((Long) session.getAttribute(SESSION_CC_SECURITY_CODE)).longValue();
        }
        return 0;
    }
    /**
     * Returns the retail transaction id for the account if there's any.
     * @param session
     * @return
     */
    public static long getRetailTransId(HttpSession session) {
        if (session != null) {
            return ((Long) session.getAttribute(SESSION_RETAIL_TRANS_ID)).longValue();
        }
        return 0;
    }
    public static void setRetailTransId(HttpSession session, long retailTransId) {
        if (session != null) {
			if (retailTransId > 0)
                session.setAttribute(SESSION_RETAIL_TRANS_ID, Long.valueOf(retailTransId));
        	else
                session.setAttribute(SESSION_RETAIL_TRANS_ID, null);
        }
    }
    /**
     * Sets the retail transaction ID attribute on the request based on the value in the session.
     * @param request HTTP servlet request object
     * @see #getRetailTransId(HttpSession)
     */
    public static void setRetailTransId(HttpServletRequest request) {
        request.setAttribute(SESSION_RETAIL_TRANS_ID, Long.valueOf(getRetailTransId(request.getSession())));
    }

    /**
     * Returns the amount owed by the account if there's any.
     * @param session
     * @return
     */
    public static double getAmountDue(HttpSession session) {
        if (session != null) {
            return ((Double) session.getAttribute(SESSION_AMOUNT_DUE)).doubleValue();
        }
        return 0;
    }
    public static void setAmountDue(HttpSession session, double amountDue) {
        if (session != null) {
        	if (amountDue > 0)
                session.setAttribute(SESSION_AMOUNT_DUE, Double.valueOf(amountDue));
        	else
                session.setAttribute(SESSION_AMOUNT_DUE, null);
        }
    }

    public static PaymentContext getPaymentContext(HttpSession session) {
        return (PaymentContext)session.getAttribute("PAYMENT_CONTEXT");
    }
    public static void setPaymentContext(HttpSession session, PaymentContext paymentContext) {
        session.setAttribute("PAYMENT_CONTEXT", paymentContext);
    }

    /**
     * Sets the login entry point for the user. This is used to determine
     * what kind of layout the user will be directed to in case of an error.
     * @param session
     * @param entryPoint
     */
    public static void setLoginEntryPoint(HttpSession session,
            String entryPoint) {
        if (session != null) {
            session.setAttribute(SESSION_LOGIN_ENTRY_POINT, entryPoint);
        }
    }
    /**
     * Retrieves the login entry point for the user. This is used to determine
     * what kind of layout the user will be directed to in case of an error.
     * @param session
     * @return
     */
    public static String getLoginEntryPoint(HttpSession session) {
        if (session != null) {
            try {
                return (String) session.getAttribute(SESSION_LOGIN_ENTRY_POINT);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Checks if the session that expired was logged in. This is used to
     * determine whether to direct the user to the login page or to the
     * session expired page.
     * @param request
     * @return
     */
    public static boolean isSessionExpired(HttpServletRequest request) {
        if (request.getSession() != null) {
            AccountLoginDTO acctLoginDto = getSessionAccountLogin(request.getSession());
            if (acctLoginDto == null || HttpDataUtil.getDbSessionIdCookie(request) == null) {

            	logger.trace("Sesssion expired --Viji --- " );
                return true;


            }
        }
        logger.trace("Sesssion not expired --Viji --- " );
        return false;
    }

    public static boolean isAcctLoginSessionActive(HttpSession session) {
        return isLoginSessionActive(session, AccountLoginDTO.LoginType.AC);
    }

    public static boolean isInvoiceLoginSessionActive(HttpSession session) {
        return isLoginSessionActive(session, AccountLoginDTO.LoginType.IN);
    }

    private static boolean isLoginSessionActive(HttpSession session, AccountLoginDTO.LoginType acctType) {
    	boolean isTraceEnabled = logger.isTraceEnabled();
    	if (isTraceEnabled)
    		logger.trace("isLoginSessionActive input:" + acctType);
        if (session != null) {
            AccountLoginDTO acctLoginDto = getSessionAccountLogin(session);
            if (isTraceEnabled)
            	logger.trace("isLoginSessionActive acctLoginDto:" + acctLoginDto);
            return (acctLoginDto != null && acctType == acctLoginDto.getLoginType());
        }
        return false;
    }


    public static void setPosId( HttpServletRequest request, HttpServletResponse response, Long posId )
    {
      HttpSession session = request.getSession( );

      if ( posId != null )
      {
        session.setAttribute( POS_ID, posId );
        Cookie posCookie = new Cookie( POS_ID, posId.toString( ) );
        posCookie.setMaxAge( -1 );
        response.addCookie( posCookie );
      }
      else
      {
        session.removeAttribute( POS_ID );
        Cookie posCookie = new Cookie( POS_ID, "" );
        posCookie.setMaxAge( 0 );
        response.addCookie( posCookie );
      }
    }

    public static Long getPosId( HttpServletRequest request )
    {
      Cookie [] cookies = request.getCookies( );
      Cookie posCookie = null;
      if ( cookies != null )
      {
        for ( int i = 0; i < cookies.length; i++ )
        {
          if ( POS_ID.equals( cookies[i].getName( ) ) )
          {
            posCookie = cookies[i];
          }
        }
      }

      if ( posCookie != null )
      {
        String value = posCookie.getValue( );
        if ( !StringUtil.isEmpty( value ) )
        {
          return Long.valueOf( value );
        }
      }
      return ( Long ) request.getSession( ).getAttribute( POS_ID );
    }


    public static int getPageMenuIdByLabel(HttpSession session, String menuLabel) {
        return getMenuIdByLabel(getPageMenus(session), menuLabel);
    }

    public static int getAcctMgmtTabMenuIdByLabel(HttpSession session, String menuLabel) {
        return getMenuIdByLabel(getAcctMgmtTabMenus(session), menuLabel);
    }

    private static int getMenuIdByLabel(MenuItemDTO[] menus, String menuLabel) {
    	if (menus != null)
    		for (MenuItemDTO menuItem : menus)
    			if (menuItem.getItemLabel().equals(menuLabel))
    				return menuItem.getItemId();
    	// this should NOT happen often
    	logger.error("No label: " + menuLabel + ", in menus: " + Arrays.toString(menus));
    	return -1;
    }

    private static MenuItemDTO[] getMenus(HttpSession session, MenuItemDTO.MenuCategory category) {
        MenuItemDTO[] menus = (MenuItemDTO[]) session.getAttribute(category.toString());
        if (menus == null) {
            MenuDelegate menuDel = new MenuDelegate();
            menus = menuDel.getMenus(category);
            session.setAttribute(category.toString(), menus);
        }
		return menus;
    }

    public static void setPageMenus(HttpSession session, MenuItemDTO[] col) {
        session.setAttribute(MenuItemDTO.MenuCategory.OLCSC_PAGE.toString(), col);
    }
    public static MenuItemDTO[] getPageMenus(HttpSession session) {
    	return SessionUtil.getMenus(session, MenuItemDTO.MenuCategory.OLCSC_PAGE);
    }

    public static MenuItemDTO[] getAcctMgmtTabMenus(HttpSession session) {
    	return SessionUtil.getMenus(session, MenuItemDTO.MenuCategory.OLCSC_LOG);
    }

    public static void resetAcctMgmtTabMenus(HttpSession session) {
        if (session != null)
            session.removeAttribute(MenuItemDTO.MenuCategory.OLCSC_LOG.toString());
    }

    public static void setTertiaryMenusInRequest(HttpServletRequest request) {
        MenuItemDTO[] col = null;
        int acctMgmtMenuId = SessionUtil.getCurrentAcctMgmtTabMenuId(request.getSession());
        MenuDelegate menuDel = new MenuDelegate();
        col = menuDel.getMenus(MenuItemDTO.MenuCategory.OLCSC_TTRY, acctMgmtMenuId);
        request.setAttribute(REQUEST_TTRY_MENUS, col);
    }

    public static String getProtocal (HttpSession session) {
        try{
            return (String) session.getAttribute(PROTOCAL);
        }catch(Throwable t){
            return null;
        }
    }

    public static void setProtocal(HttpSession session, String protocal) {
        session.setAttribute(PROTOCAL, protocal);
    }

    /**
     * Return the Account stored in the specified request object.
     * Note: If the account is not found in the request attributes,
     * this method gets the account login from the session and invokes
     * the account delegate to get a new account which is then stored
     * in the request object.
     * @param request
     * @return the account
     * @throws EtccSecurityException
     * @throws EtccException
     * @see #getSessionAccountLogin(HttpSession)
     * @see #getSessionAccount(AccountLoginDTO, HttpSession)
     */
    public static AccountDTO getAcctDTO(HttpServletRequest request) throws EtccSecurityException, EtccException {
    	AccountDTO account = (AccountDTO) request.getAttribute(REQUEST_ACCOUNT_INFO);
    	if (account == null) {
    		HttpSession session = request.getSession();
    		AccountLoginDTO acctLoginDto = getSessionAccountLogin(session);
    		if (acctLoginDto != null) {
    			account = getSessionAccount(acctLoginDto, session);
    			request.setAttribute(REQUEST_ACCOUNT_INFO, account);
    		}
    	}
    	return account;
    }

    /**
     * Login and return the corresponding account.
     * Note: This method caches the AccountDTO in the user's session.
     * @param acctLoginDto account login object to use for creating the corresponding account
     * @param session
     * @return the account corresponding to the account login
     * @see AccountDelegate#getAccount(AccountLoginDTO)
     */
    public static AccountDTO getSessionAccount(AccountLoginDTO acctLoginDto, HttpSession session) throws EtccException, EtccSecurityException {
    	//Map<String, Object> session = ActionContext.getContext().getSession();
    	AccountDTO acctDto = (AccountDTO) session.getAttribute(SESSION_ACCOUNT);
    	if (acctDto == null && ((acctLoginDto.getAcctActivity()!=null && acctLoginDto.getAcctActivity().equals("A")) ||
    			acctLoginDto.getAcctStatus()==null || acctLoginDto.getAcctStatus().equals("A"))) {
	    	acctDto = new AccountDelegate().getAccount(acctLoginDto);
    	} else if (acctDto != null && acctLoginDto.getAcctId() != acctDto.getAcctId()) {
    		session.removeAttribute(SESSION_ACCOUNT);
    		String msg = "getAccount invoked with AccountLoginDTO (" + acctLoginDto.getAcctId()
    			+ ") that differs from the AccountDTO (" + acctDto.getAcctId() + ") in session.";
    		logger.warn(msg);
    		logger.debug(acctLoginDto);
    		logger.debug(acctDto);
    		acctDto = null;
    		//throw new EtccException(msg);
    	}
        return acctDto;
    }

    public static BillingInfoDTO getBillingInfo(HttpServletRequest request) throws EtccSecurityException, EtccException {
        AccountDelegate acctDel = new AccountDelegate();
        AccountLoginDTO acctLoginDto = getSessionAccountLogin(request.getSession());
        if (acctLoginDto == null)
            return null;
        BillingInfoDTO billingInfo = acctDel.getBillingInfo(acctLoginDto);
        request.setAttribute(REQUEST_BILLING_INFO, billingInfo);
        return billingInfo;
    }

    public static AccountPreferencesDTO getAcctPrefDTO(HttpServletRequest request) throws EtccSecurityException, EtccException {
    	HttpSession session = request.getSession();
    	AccountPreferencesDTO accountPref = (AccountPreferencesDTO) session.getAttribute(REQUEST_ACCOUNT_PREFERENCES);
    	if (accountPref == null) {
            AccountLoginDTO loginDTO = getSessionAccountLogin(request.getSession());
            logger.debug(loginDTO.toString());
            accountPref = new AccountPreferenceDelegate().getPreferences(loginDTO);
    	}
        request.setAttribute(REQUEST_ACCOUNT_PREFERENCES, accountPref);
        session.setAttribute(REQUEST_ACCOUNT_PREFERENCES, accountPref);
        return accountPref;
    }

//    public static void removeShoppingCart(HttpSession session) {
//    	session.removeAttribute(SESSION_SHOPPING_CART);
//    }
//
//    public static ShoppingCart getShoppingCart(HttpSession session) {
//    	ShoppingCart cart = (ShoppingCart) session.getAttribute(SESSION_SHOPPING_CART);
//    	if (cart == null) {
//    		cart = new ShoppingCart();
//    		session.setAttribute(SESSION_SHOPPING_CART, cart);
//    	}
//    	return cart;
//    }

    @SuppressWarnings("unchecked")
	public static Map<String, TagDTO> getSavedVehicleMap(HttpSession session) {
    	return (Map<String, TagDTO>) session.getAttribute(SESSION_SAVED_VEHICLE_MAP);
    }
    public static void setSavedVehicleMap(HttpSession session, Map<String, TagDTO> vehicles) {
    	session.setAttribute(SESSION_SAVED_VEHICLE_MAP, vehicles);
    }

    public static void setVehicles(HttpServletRequest request, Collection<TagDTO> vehicles) {
        request.setAttribute(REQUEST_VEHICLES, vehicles);
    }

    public static void setVehicles(HttpServletRequest request, TagDTO[] vehicles) {
        request.setAttribute(REQUEST_VEHICLES, vehicles);
    }

    /**
     * Returns the list of vehicle tags.  If the vehicles are saved as a
     * collection or array, a new list is created with the same elements
     * as the stored vehicles object.
     * @param request the request containing the vehicles
     * @return the list of vehicle tags
     * @see #setVehicles(HttpServletRequest, Collection)
     * @see #setVehicles(HttpServletRequest, TagDTO[])
     * @see #getVehiclesObject(HttpServletRequest)
     */
    public static List<TagDTO> getVehicleList(HttpServletRequest request) {
        Object o = getVehiclesObject(request);
        boolean trace = logger.isTraceEnabled();
        if (trace)
            logger.trace("Vehicles in request attribute: " + o);
        List<TagDTO> result = null;
        if (o != null) {
            Class<? extends Object> c = o.getClass();
            if (isImplementedBy(c, List.class)) { //(c == List.class) {
                @SuppressWarnings("unchecked")
                List<TagDTO> vehicles = (List<TagDTO>) o;
                result = vehicles;
            } else if (isImplementedBy(c, Collection.class)) { //(c == Collection.class) {
                @SuppressWarnings("unchecked")
                Collection<TagDTO> vehicles = (Collection<TagDTO>) o;
                if (!vehicles.isEmpty()) {
                    result = new ArrayList<TagDTO>(vehicles.size());
                    for (TagDTO tag : vehicles)
                        result.add(tag);
                } else {
                    result = new ArrayList<TagDTO>();
                }
            } else if (c.isArray()) {
                TagDTO[] vehicles = (TagDTO[]) o;
                if (vehicles.length > 0) {
                    result = new ArrayList<TagDTO>(vehicles.length);
                    for (TagDTO tag : vehicles)
                        result.add(tag);
                } else {
                    result = new ArrayList<TagDTO>();
                }
            }
        }
        if (trace)
            logger.trace("Converted vehicles: " + result);
        return result;
    }

    private static boolean isImplementedBy(Class<? extends Object> implementor, Class<? extends Object> ifc) {
    	@SuppressWarnings("unchecked")
    	Class<? extends Object>[] theInterfaces = implementor.getInterfaces();
    	if ((theInterfaces == null) || (theInterfaces.length == 0))
    		return false;
    	String ifcName = ifc.getName();
    	for (int i = 0; i < theInterfaces.length; i++) {
    		Class<? extends Object> anInterface = theInterfaces[i];
    		boolean eq = (anInterface == ifc);
    		if (eq)
    			return true;
    		String anInterfaceName = anInterface.getName();
    		if (anInterfaceName.equals(ifcName))
    			return true;
    	}
    	return false;
	}


	/**
     * Returns the vehicles object stored in the request attribute with key {@link #REQUEST_VEHICLES}.
     * @param request the request containing the vehicles
     * @return the vehicles object
     */
    private static Object getVehiclesObject(HttpServletRequest request) {
        return request.getAttribute(REQUEST_VEHICLES);
    }

    /**
     * Loads the authorized contacts into the contact information form.
     * @param request the HTTP servlet request containing the contact form
     * @see ContactInfoForm
     * @see #getContactInfoForm(HttpSession)
     * @see #getSessionAccountLogin(HttpSession)
     * @see AuthorizedContactDelegate#getAuthContacts(AccountLoginDTO)
     * @see AuthorizedContactDelegate#getAuthContactLimit()
     */
    // migrated getAuthorizedContacts() from com.etcc.csc.presentation.action.accountManagement.AccountInformationAction and AccountSummaryAction
    public static void loadAuthorizedContacts(HttpServletRequest request) {
        AuthorizedContactDelegate authContDel = new AuthorizedContactDelegate();
        HttpSession session = request.getSession();
        ContactInfoForm contactForm = getContactInfoForm(session);
        try {
            AccountLoginDTO acctLogin = getSessionAccountLogin(session);
            contactForm.setAuthorizedContacts(authContDel.getAuthContacts(acctLogin));
            contactForm.setAuthContactsLimit(authContDel.getAuthContactLimit());
        } catch (EtccException ee) {
            // throw new EtccException("Could not get Authorized Contacts " + ee.getMessage());
            logger.warn("Could not get Authorized Contacts " + ee.getMessage(), ee);
        }
    }

    /**
     * Returns the contact information form (or creates a new instance) in the session.
     * @param session HTTP session containing the form
     * @return the contact information form
     */
    public static ContactInfoForm getContactInfoForm(HttpSession session) {
        ContactInfoForm contactForm = (ContactInfoForm) session.getAttribute(SESSION_CONTACT_INFO_FORM);
        if (contactForm == null)
            contactForm = new ContactInfoForm();
        session.setAttribute(SESSION_CONTACT_INFO_FORM, contactForm);
        return contactForm;
    }
}
