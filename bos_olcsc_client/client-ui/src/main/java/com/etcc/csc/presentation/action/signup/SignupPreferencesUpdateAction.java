/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action.signup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountPreferenceDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountIopValueDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;
import com.etcc.csc.dto.AccountPreferenceValueDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.AccountPreferenceForm;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.SessionUtil;

public class SignupPreferencesUpdateAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    	try{
    	    HttpSession session = request.getSession();
            AccountPreferenceForm preferencesForm;
    	    if(session.getAttribute("preferencesForm")==null){
    	    	 preferencesForm = (AccountPreferenceForm)form;
    	    	session.setAttribute("preferencesForm",preferencesForm);
    	    } else {
    	    	  preferencesForm = (AccountPreferenceForm) session.getAttribute("preferencesForm");
    	    }
    	   
            String notificationFormat = preferencesForm.getEmailFormat();

            OnlineAccessForm onlineAccessForm = (OnlineAccessForm)session.getAttribute("OnlineAccessForm");

            List<AccountIopValueDTO> iopList = preferencesForm.getIopPreferences();
            if(iopList == null)
            	iopList = new ArrayList<AccountIopValueDTO>();
            int i = 0;
            for (AccountIopValueDTO iopDTO : iopList) {
                iopDTO.setReasonCode("USR");
                iopDTO.setActive("on".equalsIgnoreCase(request.getParameter("iop" + i++)));
            }

            List<AccountPreferenceValueDTO> preferenceList = preferencesForm.getUserPreferences();
            if(preferenceList == null)
              preferenceList = new ArrayList<AccountPreferenceValueDTO>();

            i = 0;
            for (AccountPreferenceValueDTO prefDTO : preferenceList) {
                prefDTO.setNotificationFormat(notificationFormat);
                prefDTO.setActive("on".equalsIgnoreCase(request.getParameter("pref" + i++)));
                if ("ER".equalsIgnoreCase(prefDTO.getNotificationType()))
                    request.setAttribute("prefChange", Boolean.TRUE);
            }

          AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(session);
          AccountPreferenceDelegate del = new AccountPreferenceDelegate();   
          AccountPreferencesDTO acctPrefDTO = new AccountPreferencesDTO();
          if(iopList.size() == 0){
              AccountPreferencesDTO resultDTO = del.getPreferences(loginDTO);
              acctPrefDTO.setIopSettings(resultDTO.getIopSettings());
          } else {
              acctPrefDTO.setIopSettings(iopList.toArray(new AccountIopValueDTO[iopList.size()]));
          }
          acctPrefDTO.setPreferences(preferenceList.toArray( new AccountPreferenceValueDTO[preferenceList.size()]));
          acctPrefDTO.setEmailFormat(notificationFormat);          

          acctPrefDTO = del.setPreferences(loginDTO, acctPrefDTO);
          if (saveErrorMessages(request, acctPrefDTO, "prefUpdateFailed")) {
              return mapping.findForward("failure");
          }
          session.setAttribute(SessionUtil.REQUEST_ACCOUNT_PREFERENCES, acctPrefDTO); 
          SessionUtil.getAcctPrefDTO(request);
          request.setAttribute("sitePrefonLoad", Boolean.TRUE);      

          if(onlineAccessForm==null){             
              SessionUtil.getAcctDTO(request);
              onlineAccessForm=new OnlineAccessForm();
          }
          AccountDTO acctDto = SessionUtil.getAcctDTO(request);           
          if (acctDto != null) {
             onlineAccessForm.setLoginId(loginDTO.getLoginId());            
             onlineAccessForm.setEmailAddress(acctDto.getEmailAddress());
             request.setAttribute("OnlineAccessForm",onlineAccessForm);
          }

    	}catch(Exception e){System.out.println("SignupPreferenceUpdateAction(joe)"+e.getMessage());}
          return mapping.findForward("success");
    }
}
