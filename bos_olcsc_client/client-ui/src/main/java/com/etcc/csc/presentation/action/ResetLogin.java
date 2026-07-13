package com.etcc.csc.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class ResetLogin extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(ResetLogin.class);

    @Override
    public ActionForward execute (ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        OnlineAccessSetupDTO onlineAccessDTO = (OnlineAccessSetupDTO) request.getSession().getAttribute("passwordRetrievalDTO");
        OnlineAccessForm dynaForm =  (OnlineAccessForm) form;
        
        onlineAccessDTO.setPassword(dynaForm.getPassword());
        onlineAccessDTO.setSecurityQuestionID(Integer.parseInt(dynaForm.getSecurityQuestionID()));
        onlineAccessDTO.setSecurityQuestionAnswer(dynaForm.getSecurityQuestionAnswer());
        //Weblogic upgrade cluster issues fix
        request.getSession().setAttribute("passwordRetrievalDTO",onlineAccessDTO);
      //Weblogic upgrade cluster issues fix ends 
        
        AccountDelegate acctDel = new AccountDelegate();
        AccountLoginDTO result = acctDel.setupOnlineAccess(SessionUtil.getSessionAccountLogin(request.getSession()), onlineAccessDTO);
        if (logger.isDebugEnabled()){
            logger.debug("ResetLogin.result=" + result);
        }
        if (saveErrorMessages(request, result, "onlineAccessSetupError")){
            return mapping.findForward("failure");
        }// else
            result.setLoginId(onlineAccessDTO.getUserID());
            SessionUtil.setSessionAccountLogin(request.getSession(), result);
            HttpDataUtil.setDbSessionIdCookie(response, result.getDbSessionId());
            SessionUtil.setLoginEntryPoint(request.getSession(), AccountLoginDTO.LoginType.AC.toString());
            SessionUtil.getAcctDTO(request);
            AccountDTO acctDTO = SessionUtil.getAcctDTO(request);
            String userInfoCookie = HttpDataUtil.createUserInfoCookie(acctDTO);
            HttpDataUtil.setUserInfoCookie(response, userInfoCookie);
        return mapping.findForward("success");
    }
}
