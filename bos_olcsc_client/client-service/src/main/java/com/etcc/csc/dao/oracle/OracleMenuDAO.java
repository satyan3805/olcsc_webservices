/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.etcc.csc.dao.MenuDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.plsql.OLCSC_MENU;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_MENU_ITEM_ARR;
import com.etcc.csc.plsql.OLC_MENU_ITEM_REC;

/**
 * Oracle implementation of the Menu DAO.
 */
public class OracleMenuDAO extends MenuDAO {
	private static final Logger logger = Logger.getLogger(OracleMenuDAO.class);

//    /**
//     * Retrieves menu items for a given application and user.
//     *
//     * @throws com.etcc.csc.common.EtccException
//     * @return A collection of MenuItemDTO.
//     * @see #buildMenus(Array)
//     * @deprecated
//     */
//	@Override
//    protected MenuItemDTO[] loadMenus(String sessionId, long accountId, String loginId, String ipAddress,
//            String menuUser) throws EtccException, EtccSecurityException {
//
//        MenuItemDTO[] result;
//        try {
//
//            /*
//             * FUNCTION Get_Menu(P_SESSION VARCHAR2, P_ACCT_ID NUMBER, P_USER VARCHAR2, P_MENU_USER VARCHAR2,
//             * P_IP_ADDRESS IN VARCHAR2, P_MENU_ITEMS OUT OLC_MENU_ITEM_ARR, P_ERROR_ARR OUT OLC_ERROR_MSG_ARR )
//             * RETURN NUMBER;
//             */
//
//            cstmt = this.conn.prepareCall("{? = call OLCSC_MENU.get_menu(?, ?, ?, ?, ?, ?, ?)}");
//
//            Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
//            typeMap.put("OL_OWNER.OLC_MENU_ITEM_REC", OlcMenuItemRec.class);
//            this.conn.setTypeMap(typeMap);
//
//            cstmt.registerOutParameter(1, Types.SMALLINT);
//            setStringCheckNull(cstmt, 2, sessionId);
//            if (accountId == -1) {
//                cstmt.setNull(3, Types.NUMERIC);
//            } else {
//                cstmt.setLong(3, accountId);
//            }
//            setStringCheckNull(cstmt, 4, loginId);
//            setStringCheckNull(cstmt, 5, menuUser);
//            setStringCheckNull(cstmt, 6, ipAddress);
//            cstmt.registerOutParameter(7, Types.ARRAY, "OL_OWNER.OLC_MENU_ITEM_ARR");
//            cstmt.registerOutParameter(8, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
//            cstmt.executeUpdate();
//
//            result = buildMenus((Array) cstmt.getObject(7));
//        } catch (SQLException t) {
//            t.printStackTrace();
//            throw new EtccException("Error getting menus: " + t, t);
//        } finally {
//            closeConnection();
//        }
//        return result;
//    }

//    protected MenuItemDTO[] loadMenus(String menuUser, MenuItemDTO.MenuCategory category, int parentItemId) throws EtccException, EtccSecurityException {
//        MenuItemDTO[] result;
//        CallableStatement cstmt = null;
//        try {
//            /*
//             * FUNCTION Get_Item_Menu( P_SESSION VARCHAR2 DEFAULT NULL, P_ACCT_ID NUMBER DEFAULT NULL, P_USER VARCHAR2
//             * DEFAULT NULL, P_MENU_USER VARCHAR2, P_IP_ADDRESS IN VARCHAR2, P_ITEM_ID IN NUMBER DEFAULT NULL,
//             * P_ITEM_TYPE IN VARCHAR2, P_MENU_ITEMS OUT OLC_MENU_ITEM_ARR, P_ERROR_ARR OUT OLC_ERROR_MSG_ARR ) RETURN
//             * NUMBER;
//             */
//
//            Map<String, Class<?>> typeMap = setTypeMap();
//            typeMap.put("OL_OWNER.OLC_MENU_ITEM_REC", OlcMenuItemRec.class);
//            cstmt = this.conn.prepareCall("{? = call OLCSC_MENU.get_item_menu(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
//
//            byte idx = 1;
//            cstmt.registerOutParameter(idx++, Types.SMALLINT);
//            cstmt.setNull(idx++, Types.VARCHAR); // session
//            cstmt.setNull(idx++, Types.NUMERIC); // acct
//            cstmt.setNull(idx++, Types.VARCHAR); // user
//            cstmt.setString(idx++, menuUser); // menu
//            cstmt.setNull(idx++, Types.VARCHAR); // ip
//            if (parentItemId == 0) {
//                cstmt.setNull(idx++, Types.VARCHAR); // item id
//            } else {
//                cstmt.setLong(idx++, parentItemId); // item id
//            }
//            cstmt.setString(idx++, category.toString()); // item type
//            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_MENU_ITEM_ARR");
//            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
//            cstmt.executeUpdate();
//            //BUG: Error Messages are not actually read.
//
//            result = buildMenus((Array) cstmt.getObject(idx - 1));
//        } catch (SQLException t) {
//            throw new EtccException("Error getting menus: " + t.getMessage(), t);
//        } finally {
//            close(cstmt);
//        }
//        return result;
//    }

	@Override
    protected MenuItemDTO[] loadMenus(String menuUser,
    		MenuItemDTO.MenuCategory category,
    		AccountLoginDTO accountLogin, String ipAddress)
    		throws SQLException {
		/*
		 * function get_menu_type(
		 *	p_session     varchar2 default null,
		 *	p_acct_id     number default null,
		 *	p_user        varchar2 default null,
		 *	p_menu_user   varchar2,
		 *	p_ip_address  in varchar2,
		 *	p_item_type   in varchar2,
		 *	p_menu_items  out olc_menu_item_arr,
		 *	p_error_arr   out olc_error_msg_arr
		 *	) return number;
		 */
        MenuItemDTO[] result;
        String P_SESSION = null;
		BigDecimal P_ACCT_ID = null;
		String P_USER = null;
		if (accountLogin != null) {
			P_SESSION = accountLogin.getDbSessionId();
			P_ACCT_ID = BigDecimal.valueOf(accountLogin.getAcctId());
			P_USER = accountLogin.getLoginId();
		}
		String P_MENU_USER = menuUser;
		String P_IP_ADDRESS = ipAddress;
		String P_ITEM_TYPE = (category == null ? null : category.toString());
		OLC_MENU_ITEM_ARR[] O_MENU_ITEMS = new OLC_MENU_ITEM_ARR[] { new OLC_MENU_ITEM_ARR() };
        OLC_ERROR_MSG_ARR[] O_ERROR_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
        CallableStatement cstmt = null;
        try {
//            Map<String, Class<?>> typeMap = setTypeMap();
//            typeMap.put("OL_OWNER.OLC_MENU_ITEM_REC", OlcMenuItemRec.class);
//            cstmt = this.conn.prepareCall("{? = call OLCSC_MENU.get_menu_type(?, ?, ?, ?, ?, ?, ?, ?)}");
//            byte idx = 1;
//            cstmt.registerOutParameter(idx++, Types.SMALLINT);
//            if (accountLogin == null) {
//            	cstmt.setNull(idx++, Types.VARCHAR); // session
//            	cstmt.setNull(idx++, Types.NUMERIC); // acct
//            	cstmt.setNull(idx++, Types.VARCHAR); // user
//            } else {
//            	cstmt.setString(idx++, accountLogin.getDbSessionId()); // session
//            	cstmt.setBigDecimal(idx++, BigDecimal.valueOf(accountLogin.getAcctId())); // acct
//            	cstmt.setString(idx++, accountLogin.getLoginId()); // user
//            }
//            cstmt.setString(idx++, menuUser); // menu_user
//            if (ipAddress == null)
//            	cstmt.setNull(idx++, Types.VARCHAR); // ip
//            else
//            	cstmt.setString(idx++, ipAddress); // ip
//            if (category == null)
//            	cstmt.setNull(idx++, Types.VARCHAR); // item_type
//            else
//            	cstmt.setString(idx++, category.toString()); // item_type
//            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_MENU_ITEM_ARR");
//            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
//            cstmt.executeUpdate();
//            BigDecimal ret = cstmt.getBigDecimal(1);
        	String version = this.conn.getMetaData().getDriverVersion();
    		System.out.println("Version: " + version);
			BigDecimal ret = new OLCSC_MENU(this.conn).GET_MENU_TYPE(P_SESSION, P_ACCT_ID, P_USER, P_MENU_USER, P_IP_ADDRESS, P_ITEM_TYPE, O_MENU_ITEMS, O_ERROR_ARR);
            // check the return value and process results
            int retval = (ret == null ? 0 : ret.intValue());
            if (retval > 0) {
            	// success
                //result = buildMenus((Array) cstmt.getObject(idx - 1));
                result = buildMenus(O_MENU_ITEMS[0].getArray());
            } else {
            	MenuItemDTO errorHolder = new MenuItemDTO();
            	if (retval == 0) {
            		// error
//            		errorHolder.addErrors(OracleUtil.convertToMessages((Array) cstmt.getObject(idx)));
            		errorHolder.addErrors(OracleUtil.convertToMessages(O_ERROR_ARR));
            	} else { // retval < 0
            		// security exception
            		String errmsg = "Security exception in loadMenus.";
            		logger.warn(errmsg);
            		errorHolder.addError(errmsg);
            	}
            	result = new MenuItemDTO[] {errorHolder};
            }
        } catch (SQLException e) {
        	logger.fatal("Error getting menus: " + e.getMessage(), e);
        	throw e;
//        } finally {
//            close(cstmt);
        }
        return result;
    }

    public MenuItemDTO createObject(Object record) {
    	try {
    		return createMenuItem((OLC_MENU_ITEM_REC) record);
    	} catch (ClassCastException e) {
    		logger.info("Cannot cast menu record to OLC_MENU_ITEM_REC", e);
        	try {
        		return createMenuItem((OLC_MENU_ITEM_REC) record);
        	} catch (ClassCastException ge) {
        		logger.warn("Cannot cast menu record to OlcMenuItemRec", ge);
        	}
    	}
    	return null;
    }

    private MenuItemDTO createMenuItem(OLC_MENU_ITEM_REC rec) {
        MenuItemDTO menu = new MenuItemDTO();
        if (rec != null) {
			try {
	    		// since the menu hierarchy and category are co-mingled, the parent ID is set on each menu item
				BigDecimal parentId = rec.getPARENT_AP_ITEM_ID();
				menu.setParentId((parentId == null ? 0 : parentId.intValue()));
				menu.setItemHelpText(rec.getITEM_HELP_TEXT());
				menu.setItemId(rec.getAP_ITEM_ID().intValue());
				menu.setItemLabel(rec.getITEM_LABEL());
				menu.setItemType(MenuItemDTO.MenuCategory.valueOf(rec.getITEM_TYPE()));
				menu.setItemUrl(rec.getITEM_URL());
				menu.setMenuLevel(rec.getMENU_LEVEL().byteValue());
				menu.setOrderWithinParent(rec.getORDER_WITHIN_PARENT().shortValue());
        	} catch (SQLException e) {
        		logger.warn("Error reading Menu Item Record.", e);
        		return null;
        	}
        }
        return menu;
	}

}
