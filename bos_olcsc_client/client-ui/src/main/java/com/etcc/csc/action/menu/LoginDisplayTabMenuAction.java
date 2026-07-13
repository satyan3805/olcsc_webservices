package com.etcc.csc.action.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.delegate.MenuDelegate;
import com.etcc.csc.dto.MenuItemDTO;
import com.etcc.csc.util.SessionUtil;

/**
 * Action to generate the tab menu.
 */
public class LoginDisplayTabMenuAction extends Action {
  private static final Logger logger = Logger.getLogger(LoginDisplayTabMenuAction.class);

  /**
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @return
   */
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    try {
        MenuItemDTO[] col = null;
        DynaValidatorForm dynaValidatorForm = ( DynaValidatorForm ) form;
//        String menu = (String) dynaValidatorForm.get("menu");
        int menuId = SessionUtil.getCurrentTabMenuId(request.getSession());
        MenuDelegate menuDel = new MenuDelegate();
        col = menuDel.getMenus(MenuItemDTO.MenuCategory.OLCSC_UNLG);
        if (menuId == 0 && col != null && col.length > 0 && col[0] != null) {
            menuId = col[0].getItemId();
            SessionUtil.setCurrentTabMenuId(request.getSession(), menuId);
        }
        request.setAttribute(mapping.getName(), dynaValidatorForm);
        if (logger.isTraceEnabled()) {
        	logger.trace("Setting tab menus for " + MenuItemDTO.MenuCategory.OLCSC_UNLG);
        }
        request.setAttribute("tabMenus", col);

        return mapping.findForward("success");
    } catch (Exception e) {
        e.printStackTrace();
        throw new EtccSysException("Error displaying login tab menus: " + e);
    }
  }
}
