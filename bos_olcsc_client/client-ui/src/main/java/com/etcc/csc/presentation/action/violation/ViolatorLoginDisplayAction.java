package com.etcc.csc.presentation.action.violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.util.SessionUtil;

public class ViolatorLoginDisplayAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, HttpServletResponse response) 
        throws Exception {
    	HttpSession session = request.getSession();
        try {
          AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(session);
          if ((acctLoginDto != null) && (!(acctLoginDto.getAcctId() > 0))) {
            session.invalidate();
            session = request.getSession();
          }
          String label = new AppDelegate().getSysParam(AppDelegate.UNPAID_TOLL_LABEL);
          int menuId = SessionUtil.getPageMenuIdByLabel(session, label);
          SessionUtil.setCurrentTabMenuId(session, menuId);
          //request.setAttribute("county","HarrisCounty");
          Object entryPoint = request.getParameter("returnAction");
          if (entryPoint!=null) {
              request.setAttribute("returnAction", entryPoint);
          }
          return mapping.findForward("success");
        } catch (Exception t) {
            t.printStackTrace();
            throw new EtccSysException("Error displaying violator login page: " + t);
        }
    }
}
