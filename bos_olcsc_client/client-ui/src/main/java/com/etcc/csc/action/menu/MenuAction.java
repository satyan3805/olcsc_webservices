package com.etcc.csc.action.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.delegate.MenuDelegate;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.util.SessionUtil;

public class MenuAction extends Action {
    private static final Logger logger = Logger.getLogger(MenuAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                HttpServletRequest request, HttpServletResponse response) throws Exception {
        MenuItemDTO[] col = null;
        try {
            MenuDelegate menuDel = new MenuDelegate();
            int currentTabMenuId = SessionUtil.getCurrentTabMenuId(request.getSession());
            col = menuDel.getMenus(MenuItemDTO.MenuCategory.MENU, currentTabMenuId);

            StringBuilder arraySize = new StringBuilder();
            StringBuilder subSize = new StringBuilder();
            if (col != null) {
                subSize.append("new Array(");
                for (MenuItemDTO menu : col) {
                    arraySize.append(menu.getMenuItemsSize()).append(",");
                    MenuItemDTO[] sub = menu.getMenuItems();
                    if (sub != null) {
                        subSize.append("new Array(");
                        for (MenuItemDTO menuItem : sub) {
                            if (menuItem.getMenuItemsSize() > 0) {
                                subSize.append("true,");
                            } else {
                                subSize.append("false,");
                            }
                        }
                        int len = subSize.length();
                        if (subSize.charAt(len-1) == ',') {  // ends with ','
                            subSize.setLength(len-1);
                            subSize.append("),");
                        }
                    } else {
                        subSize.append("new Array(false),");
                    }
                }
            }

            int len = subSize.length();
            if (subSize.charAt(len-1) == ',') {  // ends with ','
                subSize.setLength(len-1);
                subSize.append(")");
            }

            len = arraySize.length();
            if (arraySize.charAt(len-1) == ',') {  // ends with ','
                subSize.setLength(len-1);
            }

            request.setAttribute("menuSize", col == null ? Integer.valueOf(0) : Integer.valueOf(col.length));
            request.setAttribute("menus", col);
            request.setAttribute("arraySize", arraySize.toString());
            request.setAttribute("subSize", subSize.toString());

            return mapping.findForward("success");

        } catch (Throwable t) {
            String msg = "Error getting menus.";
            logger.debug(msg, t);
            throw new EtccSysException(msg, t);
        }
    }
}
