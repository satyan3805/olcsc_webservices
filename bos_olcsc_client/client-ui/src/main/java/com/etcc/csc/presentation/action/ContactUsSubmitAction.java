package com.etcc.csc.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@Deprecated
public class ContactUsSubmitAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
      /*
          DynaValidatorForm dynaForm =  (DynaValidatorForm) form;
          String replyAddress = dynaForm.getString("replyAddress");
          String comment = dynaForm.getString("comment");
          AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(request.getSession());
          long docId = -1;
          String docType = null;
          String licState = null;
          String licPlate = null;
          String dbSessionId = null;
          if (accountLogin != null) {
                docType = accountLogin.getLoginType();
                if (docType!=null)
                {
                    if (docType.equalsIgnoreCase(UIConstants.LOGIN_TYPE_INVOICE)) {
                        docId = accountLogin.getInvoiceId();
                    } 
                    else if ((docType.equalsIgnoreCase(UIConstants.LOGIN_TYPE_ACCOUNT) || docType==null) && accountLogin.getAcctId()>0) {
                        docId = accountLogin.getAcctId();
                        docType = UIConstants.LOGIN_TYPE_ACCOUNT;
                    }
                    if (accountLogin.getLicPlate()!= null && accountLogin.getLicPlate().length()>0)
                        licPlate =  accountLogin.getLicPlate();
                        
                    licState = accountLogin.getLicState();
                }
                
                dbSessionId = accountLogin.getDbSessionId();
          }
          
          AppDelegate delegate = new AppDelegate();
          
          boolean submitted = delegate.contactUs(docId, docType, licState, licPlate, replyAddress, comment, dbSessionId);
          
          if (submitted)
          {
            request.setAttribute("contactUsResult", "true");
            return mapping.findForward("success");
          }
          else
          */
            return mapping.findForward("failure");
        
      }
}
