package com.etcc.csc.presentation.action.signup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.util.SessionUtil;


public class SignupPreferencesDisplayAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
      
//        AccountPreferenceDelegate del = new AccountPreferenceDelegate();
//        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
//        System.out.println(loginDTO.toString());
////        AccountLoginDTO loginDTO = new AccountLoginDTO();
////        loginDTO.setAcctId(1016026);
////        loginDTO.setLoginId("shuang801");
////        loginDTO.setDbSessionId("8J2AP49A7A2XEXULQ7YBPMM9SAU1V74P1MF6UWRFC6MU64MBSORFV5N54T86UIHVVDD4ITCPP9PEC3VH3BE1Z7WC8O98DYDDFFHJ");
////        loginDTO.setLastLoginIp("127.0.0.1");
//        AccountPreferenceDTO resultDTO = del.getPreferences(loginDTO);
//        request.setAttribute(SessionUtil.REQUEST_ACCOUNT_PREFERENCES, resultDTO);
        
         SessionUtil.getAcctPrefDTO(request);
            
        return mapping.findForward("success");
        
      }
    
}
