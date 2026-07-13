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
import com.etcc.csc.dto.AddressUS;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.SessionUtil;

public class AccInfoDispChgMailingAddrAction extends Action{
    private static final Logger logger = Logger.getLogger(AccInfoDispChgMailingAddrAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
//        SessionUtil.getAcctDTO(request);
        logger.trace("in AccInfoDispChgMailingAddrAction");
        
        OnlineAccessForm onlineAccessForm = new OnlineAccessForm();
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDTO accountDTO = SessionUtil.getAcctDTO(request);
       
        onlineAccessForm.setLoginId(acctLoginDto.getLoginId());
        onlineAccessForm.setAddress1(accountDTO.getAddress1());
        onlineAccessForm.setAddress2(accountDTO.getAddress2());
        onlineAccessForm.setAddress3(accountDTO.getAddress3());
        onlineAccessForm.setAddress4(accountDTO.getAddress4());
        onlineAccessForm.setCity(accountDTO.getCity());
        onlineAccessForm.setState(accountDTO.getState());
        String country = accountDTO.getCountry();
        if (logger.isTraceEnabled()){
            logger.trace("*-*-*-AccInfoDispChgMailingAddrAction.country=" + country);
            logger.trace("*-*-*-AccInfoDispChgMailingAddrAction.countryCode=" + accountDTO.getCountryCode());
        }
        if (country == null) {
            onlineAccessForm.setCountry(AddressUS.COUNTRY_CODE_USA);
            onlineAccessForm.setCountryCode("US");
            onlineAccessForm.setNonUSAddress(false);
        } else {
            onlineAccessForm.setCountry(country);
            onlineAccessForm.setCountryCode(accountDTO.getCountryCode());
            onlineAccessForm.setNonUSAddress(!country.equals(AddressUS.COUNTRY_CODE_USA));
        }
        onlineAccessForm.setZip(accountDTO.getZipCode());
        onlineAccessForm.setPlus4(accountDTO.getPlus4());
         
        request.setAttribute("OnlineAccessForm", onlineAccessForm);
        if (Boolean.parseBoolean(request.getParameter("closure"))
                || Boolean.parseBoolean(request.getParameter("acctInfoRefund")))
             request.setAttribute("closure", "true");
        return mapping.findForward("success");
     }
}
