package com.etcc.csc.presentation.action.signup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AppDelegate;

public class SignupBillingInfoDisplayAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    		AppDelegate appDel = new AppDelegate();
            String changePayMethod = request.getParameter("changePayMethod");
            if ("y".equalsIgnoreCase(changePayMethod))
                request.setAttribute("changePayMethod", "true");

            request.setAttribute("LITLE_PAYPAGE_URL",appDel.getSysParam("LITLE_PAYPAGE_URL"));
            request.setAttribute("LITLE_PAYPAGE_ID",appDel.getSysParam("LITLE_PAYPAGE_ID"));
            request.setAttribute("LITLE_REPORT_GROUP",appDel.getSysParam("LITLE_REPORT_GROUP"));
            return mapping.findForward("success");
      }
}
