package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MakePaymentMenuAdjustmentAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
         HttpSession session = request.getSession();
//         SessionUtil.getAcctDTO(request);
         String makePaymentMenuLabel = new AppDelegate().getSysParam(AppDelegate.MAKE_PAYMENT_LABEL);
         if (session==null)
            throw new EtccSecurityException("session timed out in MakePaymentMenuAdjustmentAction");
         int menuId = SessionUtil.getAcctMgmtTabMenuIdByLabel(session, makePaymentMenuLabel);
         SessionUtil.setCurrentAcctMgmtTabMenuId(session, menuId);
         SessionUtil.setTertiaryMenusInRequest(request);
         return mapping.findForward("success");
     }
}
