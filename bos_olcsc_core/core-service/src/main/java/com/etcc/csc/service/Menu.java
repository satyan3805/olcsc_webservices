package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Logger;
import com.etcc.csc.dao.MenuDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.MenuItemDTO;

public class Menu implements MenuInterface {
	private Logger logger = Logger.getLogger(Menu.class);

	public Menu() {
	}

	public Collection getMenus(String sessionId, long accountId,
			String loginId, String ipAddress, String menuUser, String locale)
			throws EtccException, EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			MenuDAO menuDao = daoFactory.getMenuDAO();
			Collection col = menuDao.getMenus(sessionId, accountId, loginId,
					ipAddress, menuUser, locale);
			return col;
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getMenus() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			// TODO: must call db api to log error
			// this is because the stack trace cannot be transported over
			// the web service
			logger.error("Exception in getMenus() " + ee, ee);
			throw ee;
		}
	}

	public Collection getMenusByCategoryLocale(AccountLoginDTO acctLoginDto,
			String menuUser, String category, String locale)
			throws EtccException, EtccSecurityException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			MenuDAO menuDao = daoFactory.getMenuDAO();
			Collection col = menuDao.getMenusByCategoryLocale(acctLoginDto,
					menuUser, category, locale);
			return col;
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getMenusByCategory() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getMenusByCategory() " + ee, ee);
			throw ee;
		}
	}

	public Collection getMenusByCategory(AccountLoginDTO acctLoginDto,
			String menuUser, String category) throws EtccException,
			EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			MenuDAO menuDao = daoFactory.getMenuDAO();
			Collection col = menuDao.getMenusByCategory(acctLoginDto, menuUser,
					category);
			return col;
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getMenusByCategory() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getMenusByCategory() " + ee, ee);
			throw ee;
		}
	}

	public Collection getMenusByParent(AccountLoginDTO acctLoginDto,
			String menuUser, String category, long parentItemId)
			throws EtccException, EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			MenuDAO menuDao = daoFactory.getMenuDAO();
			Collection col = menuDao.getMenusByParent(acctLoginDto, menuUser,
					category, parentItemId);
			return col;
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getMenusByCategory() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getMenusByCategory() " + ee, ee);
			throw ee;
		}
	}

	public Collection getMenusByParentLocale(AccountLoginDTO acctLoginDto,
			String menuUser, String category, long parentItemId, String locale)
			throws EtccException, EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			MenuDAO menuDao = daoFactory.getMenuDAO();
			Collection col = menuDao.getMenusByParentLocale(acctLoginDto,
					menuUser, category, parentItemId, locale);
			return col;
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getMenusByCategory() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getMenusByCategory() " + ee, ee);
			throw ee;
		}
	}

	/**
	 * Stub method to force the inclusion of MenuItemDTO to the wsdl.
	 * 
	 * @return
	 */
	public MenuItemDTO getMenu() {
		return null;
	}
}