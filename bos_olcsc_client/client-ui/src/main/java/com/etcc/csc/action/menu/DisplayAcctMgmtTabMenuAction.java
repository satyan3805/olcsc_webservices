package com.etcc.csc.action.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.util.SessionUtil;

public class DisplayAcctMgmtTabMenuAction extends Action {
    private static final Logger logger = Logger.getLogger(DisplayAcctMgmtTabMenuAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

        MenuItemDTO[] col = null;
        int currentMenuId = SessionUtil.getCurrentAcctMgmtTabMenuId(request.getSession());
    	HttpSession session = request.getSession();
        try {
            col = SessionUtil.getAcctMgmtTabMenus(session);

            if (currentMenuId == 0 && col != null) {
                currentMenuId = Integer.parseInt(BeanUtils.getProperty(col[0], "itemId"));
            }
        } catch (Exception e) {
            logger.error("Exception getting menu for OLCSC_USER/OLCSC_LOG: " + e.getMessage(),e);
            throw new EtccSysException("Error displaying account management tab menus: " + e);
        }

        SessionUtil.setCurrentAcctMgmtTabMenuId(session, currentMenuId);

        return mapping.findForward("success");
    }
}
