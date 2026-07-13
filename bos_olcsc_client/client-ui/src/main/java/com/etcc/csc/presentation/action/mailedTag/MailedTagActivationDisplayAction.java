package com.etcc.csc.presentation.action.mailedTag;

import com.etcc.csc.delegate.AppDelegate;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MailedTagActivationDisplayAction extends Action{
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
          HttpSession session = request.getSession();
          AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(session);
          if ((acctLoginDto != null) && (!(acctLoginDto.getAcctId() > 0))) {
            session.invalidate();
            session = request.getSession();
          }
          String label = new AppDelegate().getSysParam("OLCSC_MY_EZ_TAG_MENU_LABEL");
          int menuId = SessionUtil.getPageMenuIdByLabel(session, label);
          SessionUtil.setCurrentTabMenuId(session, menuId);

          Object entryPoint = request.getParameter("statuschange");
          if (entryPoint!=null) {
              request.setAttribute("returnAction", entryPoint);
          }

          return mapping.findForward("success");
      }
}
