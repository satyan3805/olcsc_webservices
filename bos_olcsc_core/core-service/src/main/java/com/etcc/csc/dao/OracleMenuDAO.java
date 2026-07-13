package com.etcc.csc.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.plsql.OlcMenuItemRec;

/**
 * Oracle implementation of the Menu DAO.
 */
public class OracleMenuDAO extends MenuDAO {
	private Logger logger = Logger.getLogger(OracleMenuDAO.class);

	public OracleMenuDAO() {
	}

	/**
	 * Retrieves menu items for a given application and user.
	 * 
	 * @throws com.etcc.csc.common.EtccException
	 * @return A collection of MenuItemDTO.
	 * @param userId
	 * @param appName
	 */
	public Collection getMenus(String sessionId, long accountId,
			String loginId, String ipAddress, String menuUser, String locale)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getMenus() @ OracleMenuDAO");
		ArrayList result = new ArrayList();
		List parents = new ArrayList();
		List levels = new ArrayList();

		try {
			/*
			 * FUNCTION Get_Menu(P_SESSION VARCHAR2, P_ACCT_ID NUMBER, P_USER
			 * VARCHAR2, P_MENU_USER VARCHAR2, P_IP_ADDRESS IN VARCHAR2,
			 * P_MENU_ITEMS OUT OLC_MENU_ITEM_ARR, P_ERROR_ARR OUT
			 * OLC_ERROR_MSG_ARR ) RETURN NUMBER;
			 */
			setConnection(Util.getDbConnection());
			logger.info("getMenus() :: Calling OLCSC_MENU.get_menu");
			cstmt = conn
					.prepareCall("{? = call OLCSC_MENU.get_menu(?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_MENU_ITEM_REC", OlcMenuItemRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			setString(cstmt, 2, sessionId);
			if (accountId == -1) {
				logger.debug("Inside if (accountId == -1) @ getMenus()");
				cstmt.setNull(3, Types.NUMERIC);
			} else {
				logger.debug("Inside else of if (accountId == -1) @ getMenus()");
				cstmt.setLong(3, accountId);
			}
			setString(cstmt, 4, loginId);
			setString(cstmt, 5, menuUser);
			setString(cstmt, 6, ipAddress);
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_MENU_ITEM_ARR");
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setString(9, "EN");
			cstmt.setString(10, locale);
			cstmt.executeUpdate();

			Array menus = (Array) cstmt.getObject(7);

			if (menus != null) {
				logger.debug("Inside if (menus != null) @ getMenus()");
				Object array[] = (Object[]) menus.getArray();
				if (array != null && array.length >= 0) {
					logger.debug("Inside if (array != null && array.length >= 0) @ getMenus()");
					for (int i = 0; i < array.length; i++) {
						MenuItemDTO menu = createObject((OlcMenuItemRec) array[i]);

						short level = menu.getMenuLevel();
						boolean iterate = false;
						do {
							iterate = false;
							if (parents.size() > 0) {
								logger.debug("Inside if (parents.size() > 0) @ getMenus()");
								short currLevel = ((Short) levels.get(levels
										.size() - 1)).shortValue();
								if (level == currLevel) {
									logger.debug("Inside if (level == currLevel) @ getMenus()");
									// replace parent
									parents.remove(parents.size() - 1);
									parents.add(menu);
									// if size > 1, add this as a child of the
									// existing parent
									if (parents.size() > 1) {
										logger.debug("Inside if (parents.size() > 1) @ getMenus()");
										MenuItemDTO currMenu = (MenuItemDTO) parents
												.get(parents.size() - 2);
										currMenu.addChild(menu);
									} else {
										logger.debug("Inside else of if (parents.size() > 1) @ getMenus()");
										result.add(menu);
									}
								} else {
									logger.debug("Inside else of if (level == currLevel) @ getMenus()");
									if (level > currLevel) {
										logger.debug("Inside if (level > currLevel)) @ getMenus()");
										// add new parent
										parents.add(menu);
										levels.add(level);
										// add curr menu to parent
										MenuItemDTO currMenu = (MenuItemDTO) parents
												.get(parents.size() - 2);
										currMenu.addChild(menu);
									} else {
										logger.debug("Inside else of if (level > currLevel)) @ getMenus()");
										// less than; remove current parent
										parents.remove(parents.size() - 1);
										levels.remove(levels.size() - 1);
										// save parent (?)
										iterate = true;
									}
								}
							} else {
								logger.debug("Inside if (parents.size() > 0) @ getMenus()");
								result.add(menu);
								parents.add(menu);
								levels.add(level);
							}
						} while (iterate);
					}
				}
			}
		} catch (Throwable t) {
			logger.error("Error in getMenus() @ OracleMenuDAO: " + t, t);
			throw new EtccException("Error getting menus: " + t, t);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getMenus() @ OracleMenuDAO");
		return result;
	}

	public Collection getMenusByCategory(AccountLoginDTO acctLoginDto,
			String menuUser, String category) throws EtccException,
			EtccSecurityException {
		logger.info("Entering getMenusByCategory(AccountLoginDTO acctLoginDto, String menuUser, String category) @ OracleMenuDAO");
		logger.info("Leaving getMenusByCategory(AccountLoginDTO acctLoginDto, String menuUser, String category) @ OracleMenuDAO");
		return getMenusByCategory(acctLoginDto, menuUser, category, 0, "EN");
	}

	private Collection getMenusByCategory(AccountLoginDTO acctLoginDto,
			String menuUser, String category, long parentItemId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getMenusByCategory(AccountLoginDTO acctLoginDto, String menuUser, String category, long parentItemId) @ OracleMenuDAO");
		logger.info("Leaving getMenusByCategory(AccountLoginDTO acctLoginDto, String menuUser, String category, long parentItemId) @ OracleMenuDAO");
		return getMenusByCategory(acctLoginDto, menuUser, category,
				parentItemId, "EN");
	}

	public Collection getMenusByCategoryLocale(AccountLoginDTO acctLoginDto,
			String menuUser, String category, String locale)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getMenusByCategoryLocale() @ OracleMenuDAO");
		logger.info("Leaving getMenusByCategoryLocale() @ OracleMenuDAO");
		return getMenusByCategory(acctLoginDto, menuUser, category, 0, locale);
	}

	private Collection getMenusByCategory(AccountLoginDTO acctLoginDto,
			String menuUser, String category, long parentItemId, String locale)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getMenusByCategory(AccountLoginDTO acctLoginDto, String menuUser, String category, long parentItemId, String locale) @ OracleMenuDAO");
		ArrayList result = new ArrayList();
		List parents = new ArrayList();
		List levels = new ArrayList();

		try {
			/*
			 * FUNCTION Get_Item_Menu(P_SESSION VARCHAR2 DEFAULT NULL, P_ACCT_ID
			 * NUMBER DEFAULT NULL, P_USER VARCHAR2 DEFAULT NULL, P_MENU_USER
			 * VARCHAR2, P_IP_ADDRESS IN VARCHAR2, P_ITEM_ID IN NUMBER DEFAULT
			 * NULL, P_ITEM_TYPE IN VARCHAR2, P_MENU_ITEMS OUT
			 * OLC_MENU_ITEM_ARR, P_ERROR_ARR OUT OLC_ERROR_MSG_ARR,
			 * P_BASE_LANG_ID varchar2 DEFAULT 'EN', P_LANG_ID varchar2 DEFAULT
			 * 'EN') RETURN NUMBER;
			 */
			setConnection(Util.getDbConnection());
			logger.info("getMenusByCategory() :: Calling OLCSC_MENU.get_item_menu");
			cstmt = conn.prepareCall("{? = call OLCSC_MENU.get_item_menu(?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_MENU_ITEM_REC", OlcMenuItemRec.class);
			conn.setTypeMap(typeMap);
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			if (acctLoginDto == null) {
				logger.debug("Inside if (acctLoginDto == null) @ getMenusByCategory()");
				cstmt.setNull(idx++, Types.VARCHAR); // session
				cstmt.setNull(idx++, Types.NUMERIC); // acct
				cstmt.setNull(idx++, Types.VARCHAR); // user
			} else {
				logger.debug("Inside else of if (acctLoginDto == null) @ getMenusByCategory()");
				if (acctLoginDto.getDbSessionId() == null) {
					logger.debug("Inside if (acctLoginDto.getDbSessionId() == null) @ getMenusByCategory()");
					cstmt.setNull(idx++, Types.VARCHAR); // session
				} else {
					logger.debug("Inside else of if (acctLoginDto.getDbSessionId() == null) @ getMenusByCategory()");
					cstmt.setString(idx++, acctLoginDto.getDbSessionId()); // session
				}

				if (acctLoginDto.getAcctId() > 0) {
					logger.debug("Inside if (acctLoginDto.getAcctId() > 0) @ getMenusByCategory()");
					cstmt.setLong(idx++, acctLoginDto.getAcctId()); // acct
				} else {
					logger.debug("Inside else of if (acctLoginDto.getAcctId() > 0) @ getMenusByCategory()");
					cstmt.setNull(idx++, Types.NUMERIC); // acct
				}

				if (acctLoginDto.getLoginId() == null) {
					logger.debug("Inside if (acctLoginDto.getLoginId() == null) @ getMenusByCategory()");
					cstmt.setNull(idx++, Types.VARCHAR); // user
				} else {
					logger.debug("Inside else of if (acctLoginDto.getLoginId() == null) @ getMenusByCategory()");
					cstmt.setString(idx++, acctLoginDto.getLoginId()); // user
				}
			}
			logger.debug("param " + idx + "= " + menuUser);
			cstmt.setString(idx++, menuUser); // menu

			cstmt.setNull(idx++, Types.VARCHAR); // ip

			if (parentItemId == 0) {
				logger.debug("Inside if (parentItemId == 0) @ getMenusByCategory()");
				logger.debug("param " + idx + "= NULL");
				cstmt.setNull(idx++, Types.VARCHAR); // item id
			} else {
				logger.debug("Inside else of if (parentItemId == 0) @ getMenusByCategory()");
				logger.debug("param " + idx + "= " + parentItemId);
				cstmt.setLong(idx++, parentItemId); // item id
			}

			logger.debug("param " + idx + "= " + category);
			cstmt.setString(idx++, category); // item type

			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_MENU_ITEM_ARR");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			logger.debug("param " + idx + "= EN");
			cstmt.setString(idx++, "EN");

			logger.debug("param " + idx + "= EN");
			cstmt.setString(idx, locale);

			cstmt.executeUpdate();
			Array menus = (Array) cstmt.getObject(idx - 3);

			if (menus != null) {
				logger.debug("Inside if (menus != null) @ getMenusByCategory()");
				Object array[] = (Object[]) menus.getArray();
				if (array != null && array.length >= 0) {
					logger.debug("Inside if (array != null && array.length >= 0) @ getMenusByCategory()");
					for (int i = 0; i < array.length; i++) {
						MenuItemDTO menu = createObject((OlcMenuItemRec) array[i]);
						short level = menu.getMenuLevel();
						boolean iterate = false;
						do {
							iterate = false;
							if (parents.size() > 0) {
								logger.debug("Inside if (parents.size() > 0) @ getMenusByCategory()");
								short currLevel = ((Short) levels.get(levels
										.size() - 1)).shortValue();
								if (level == currLevel) {
									logger.debug("Inside if (level == currLevel) @ getMenusByCategory()");
									// replace parent
									parents.remove(parents.size() - 1);
									parents.add(menu);
									// if size > 1, add this as a child of the
									// existing parent
									if (parents.size() > 1) {
										logger.debug("Inside if (parents.size() > 1)@ getMenusByCategory()");
										MenuItemDTO currMenu = (MenuItemDTO) parents
												.get(parents.size() - 2);
										currMenu.addChild(menu);
									} else {
										logger.debug("Inside else of if (parents.size() > 1)@ getMenusByCategory()");
										result.add(menu);
									}
								} else {
									logger.debug("Inside else of if (level == currLevel) @ getMenusByCategory()");
									if (level > currLevel) {
										logger.debug("Inside if (level > currLevel) @ getMenusByCategory()");
										// add new parent
										parents.add(menu);
										levels.add(level);
										// add curr menu to parent
										MenuItemDTO currMenu = (MenuItemDTO) parents
												.get(parents.size() - 2);
										currMenu.addChild(menu);
									} else {
										logger.debug("Inside else of if (level > currLevel) @ getMenusByCategory()");
										// less than; remove current parent
										parents.remove(parents.size() - 1);
										levels.remove(levels.size() - 1);
										// save parent (?)
										iterate = true;
									}
								}
							} else {
								logger.debug("Inside else of if (parents.size() > 0) @ getMenusByCategory()");
								result.add(menu);
								parents.add(menu);
								levels.add(level);
							}
						} while (iterate);
					}
				}
			}
		} catch (Throwable t) {
			logger.error(
					"Error in getMenusByCategory(AccountLoginDTO acctLoginDto, String menuUser, String category, long parentItemId, String locale) @ OracleMenuDAO: "
							+ t, t);
			throw new EtccException("Error getting menus: " + t, t);
		} finally {
			closeConnection();
		}
		logger.info("Leaving getMenusByCategory(AccountLoginDTO acctLoginDto, String menuUser, String category, long parentItemId, String locale) @ OracleMenuDAO");
		return result;
	}

	public Collection getMenusByParent(AccountLoginDTO acctLoginDto,
			String menuUser, String category, long parentItemId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getMenusByParent() @ OracleMenuDAO");
		logger.info("Leaving getMenusByParent() @ OracleMenuDAO");
		return getMenusByCategory(acctLoginDto, menuUser, category,
				parentItemId);
	}

	public Collection getMenusByParentLocale(AccountLoginDTO acctLoginDto,
			String menuUser, String category, long parentItemId, String locale)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getMenusByParentLocale() @ OracleMenuDAO");
		logger.info("Leaving getMenusByParentLocale() @ OracleMenuDAO");
		return getMenusByCategory(acctLoginDto, menuUser, category,
				parentItemId, locale);
	}

	/**
	 * Converts a resultset to a menu object.
	 * 
	 * @throws java.sql.SQLException
	 * @return
	 * @param rs
	 */
	private MenuItemDTO createObject(OlcMenuItemRec rec) throws SQLException {
		logger.info("Entering createObject() @ OracleMenuDAO");
		MenuItemDTO menu = new MenuItemDTO();

		if (rec != null) {
			logger.debug("Inside if (rec != null) @ createObject()");
			menu.setItemHelpText(rec.getItemHelpText());
			menu.setItemId(rec.getApItemId().intValue());
			menu.setItemLabel(rec.getItemLabel());
			menu.setItemType(rec.getItemType());
			menu.setItemUrl(rec.getItemUrl());
			menu.setMenuLevel(rec.getMenuLevel().byteValue());
			menu.setOrderWithinParent(rec.getOrderWithinParent().shortValue());
		}

		logger.info("Leaving createObject() @ OracleMenuDAO");
		return menu;
	}

	private void setString(CallableStatement cstmt, int index, String value)
			throws SQLException {
		logger.info("Entering setString() @ OracleMenuDAO");
		if (value == null) {
			logger.debug("Inside if (value == null) @ setString()");
			cstmt.setNull(index, Types.VARCHAR);
		} else {
			logger.debug("Inside else of if (value == null) @ setString()");
			cstmt.setString(index, value);
		}
		logger.info("Leaving setString() @ OracleMenuDAO");
	}
}