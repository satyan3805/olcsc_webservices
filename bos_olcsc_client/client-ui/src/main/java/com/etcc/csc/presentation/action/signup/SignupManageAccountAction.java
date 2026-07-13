package com.etcc.csc.presentation.action.signup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class SignupManageAccountAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
      
          HttpSession session = request.getSession();
          if (session != null) {
              session.removeAttribute( "OnlineAccessForm" );
              session.removeAttribute( "contactInfoForm" );
              session.removeAttribute( "tagRequestForm" );
              session.removeAttribute( "billingInfoForm" );
              
              AccountLoginDTO loginDto = SessionUtil.getSessionAccountLogin(session);
              loginDto.setLoginType(AccountLoginDTO.LoginType.AC);
              loginDto.setAcctActivity("A");
              SessionUtil.setSessionAccountLogin(session, loginDto);

//              HttpDataUtil.setUserInfoCookie(response, loginDto.getAcctId()+"");
             SessionUtil.getAcctDTO(request);
             AccountDTO acctDTO = SessionUtil.getAcctDTO(request);
             String userInfoCookie = HttpDataUtil.createUserInfoCookie(acctDTO);
             HttpDataUtil.setUserInfoCookie(response, userInfoCookie);

              request.setAttribute("setEvent2", Boolean.TRUE);
              return mapping.findForward("success");
          }
          return mapping.findForward("success");
    }
}
