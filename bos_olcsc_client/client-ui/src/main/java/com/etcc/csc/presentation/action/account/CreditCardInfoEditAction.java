/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.CreditCardDelegate;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.form.CreditCardForm;
import com.etcc.csc.util.SessionUtil;

public class CreditCardInfoEditAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
      
        CreditCardForm ccForm = (CreditCardForm) form;
        boolean editMode = ccForm.isEditMode();
        CreditCardDelegate ccDel = new CreditCardDelegate();
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
            request.getSession());
        if (acctLoginDto != null) {
            AccountCreditCardDTO ccDto = ccDel.getAccountCreditCard(acctLoginDto);
            if (ccDto != null) {
                BeanUtils.copyProperties(ccForm, ccDto);
                ccForm.setOrigCardNbr(ccDto.getCardNbr());    
                if (ccForm.getCardExpires() != null) {
                    try {
                        ccForm.setMonthExp(Byte.parseByte(
                            ccForm.getCardExpires().substring(0, 
                            ccForm.getCardExpires().indexOf("/"))));
                    } catch (Exception e) {}

                    try {
                        ccForm.setYearExp(Short.parseShort(
                            ccForm.getCardExpires().substring(
                            ccForm.getCardExpires().indexOf("/") + 1,
                            ccForm.getCardExpires().length())));
                    } catch (Exception e) {}
                } else {
                    //ccForm.setYearExp(UIDateUtil.getCurrentYear());                    
                    //ccForm.setMonthExp(UIDateUtil.getCurrentMonth());                    
                }
                if (ccForm.getState() == null 
                    || ccForm.getState().trim().length() == 0) {
                        ccForm.setState("TX");
                }
                
                if (editMode && ccForm.getPaymentType()
                        .equalsIgnoreCase("CASH")) {
                    // populate fields with address from personal info
                    AccountDTO acctDto = SessionUtil.getAcctDTO(request);
                    if (acctDto != null) {
                        ccForm.setAddress1(acctDto.getAddress1());
                        ccForm.setAddress2(acctDto.getAddress2());
                        ccForm.setCity(acctDto.getCity());
                        ccForm.setPlus4(acctDto.getPlus4());
                        ccForm.setState(acctDto.getState());
                        ccForm.setZipCode(acctDto.getZipCode());
                    }
                    ccForm.setCardCode("V");
                }
            }
        } else {
            throw new EtccSecurityException("Security violation in "
                + "CreditCardInfoEditAction");
        }
        ccForm.setEditMode(editMode);

      return mapping.findForward("success");
    }
}
