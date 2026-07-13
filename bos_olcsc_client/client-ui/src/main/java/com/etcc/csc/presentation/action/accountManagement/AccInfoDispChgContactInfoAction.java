/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.SessionUtil;

public class AccInfoDispChgContactInfoAction extends Action{
    private static final Logger logger = Logger.getLogger(AccInfoDispChgContactInfoAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
        OnlineAccessForm onlineAccessForm = new OnlineAccessForm();
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDTO accountDTO = SessionUtil.getAcctDTO(request);
        onlineAccessForm.setLoginId(acctLoginDto.getLoginId());
        onlineAccessForm.setEmailAddress(accountDTO.getEmailAddress());
        onlineAccessForm.setEmailAddress2(accountDTO.getEmailAddress());
        onlineAccessForm.setAlternateEmail(accountDTO.getEmailAddress2());
        onlineAccessForm.setConfirmAlternateEmail(accountDTO.getEmailAddress2());
        onlineAccessForm.setPrimaryPhone(accountDTO.getHomePhoNbr());
        onlineAccessForm.setAlternatePhone(accountDTO.getWorkPhoNbr());
        onlineAccessForm.setAlternatePhoneExt(accountDTO.getWorkPhoExt());
        if (logger.isTraceEnabled()){
            logger.trace("***ext=" + onlineAccessForm.getAlternatePhoneExt() + 
                    ";email=" + accountDTO.getEmailAddress() + 
                    ";altemail=" + accountDTO.getEmailAddress2());
        }

        onlineAccessForm.setAcctTypeCode(accountDTO.getAcctTypeCode());
        onlineAccessForm.setAcctTypeDescr(accountDTO.getAcctTypeDescr());
        onlineAccessForm.setCompanyName(accountDTO.getCompanyName());
        onlineAccessForm.setFirstName(accountDTO.getFirstName());
        onlineAccessForm.setLastName(accountDTO.getLastName());
        if(accountDTO.getCompanyTaxId() != null && accountDTO.getCompanyTaxId().length() > 0){
          String taxId = accountDTO.getCompanyTaxId();
          String dispTaxId="";
          for(int i=0; i < taxId.length(); i++)
          {
          if(i < taxId.length()-3)
            dispTaxId = dispTaxId + "*";
          else
            dispTaxId = dispTaxId + taxId.charAt(i);
          }        
          onlineAccessForm.setCompanyTaxId(dispTaxId);
        }else
           onlineAccessForm.setCompanyTaxId(accountDTO.getCompanyTaxId());
        onlineAccessForm.setDriverLicState(accountDTO.getDriverLicState());
        onlineAccessForm.setDriverLicDisplay(accountDTO.getDriverLicDisplay());

        request.setAttribute("OnlineAccessForm", onlineAccessForm);
        request.setAttribute(SessionUtil.REQUEST_ACCOUNT_INFO, accountDTO);

        return mapping.findForward("success");
     }
}
