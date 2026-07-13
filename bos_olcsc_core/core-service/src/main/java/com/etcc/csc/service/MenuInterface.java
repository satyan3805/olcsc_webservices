package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;

/**
 * Defines methods that need to be implemented by the Menu classes.
 */
public interface MenuInterface extends BusinessObjectInterface {
	/**
	 * Retrieves the available menus for a specific application and user.
	 * 
	 * @return
	 * @param appName
	 *            The application name.
	 * @param userId
	 *            The user id.
	 */
	Collection getMenus(String sessionId, long accountId, String loginId,
			String ipAddress, String menuUser, String locale)
			throws EtccException, EtccSecurityException;

	/**
	 * Retrieves the available menus given a menu category.
	 * 
	 * @param acctLoginDto
	 * @param category
	 * @return
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	Collection getMenusByCategoryLocale(AccountLoginDTO acctLoginDto,
			String menuUser, String category, String locale)
			throws EtccException, EtccSecurityException;

	/**
	 * Retrieves the available menus given a menu category.
	 * 
	 * @param acctLoginDto
	 * @param category
	 * @return
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	Collection getMenusByCategory(AccountLoginDTO acctLoginDto,
			String menuUser, String category) throws EtccException,
			EtccSecurityException;

	/**
	 * Retrieves the available menus given a menu parent id.
	 * 
	 * @param acctLoginDto
	 * @param parentItemId
	 * @return
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	Collection getMenusByParentLocale(AccountLoginDTO acctLoginDto,
			String menuUser, String category, long parentItemId, String locale)
			throws EtccException, EtccSecurityException;

	/**
	 * Retrieves the available menus given a menu parent id.
	 * 
	 * @param acctLoginDto
	 * @param parentItemId
	 * @return
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	Collection getMenusByParent(AccountLoginDTO acctLoginDto, String menuUser,
			String category, long parentItemId) throws EtccException,
			EtccSecurityException;
}