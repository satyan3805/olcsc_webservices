/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.presentation.form.AccountForm;
import com.etcc.csc.util.SessionUtil;

public class AccountPersonalInfoAction extends Action {
  public ActionForward display(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    AccountForm acctForm = (AccountForm) form;
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
        request.getSession());
    if (acctLoginDto != null) {
        AccountDTO acctDto = SessionUtil.getAcctDTO(request);
        BeanUtils.copyProperties(acctForm, acctDto);
    }
   
    // get states
/*    StateDelegate stateDel = new StateDelegate();
    Collection states = stateDel.getStates();
    request.setAttribute("states", states);
*/
    return mapping.findForward("display");
  }

    public ActionForward edit(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
      
      AccountForm acctForm = (AccountForm) form;
      acctForm.setEditMode(true);
      AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
          request.getSession());
      if (acctLoginDto != null) {
          AccountDTO acctDto = SessionUtil.getAcctDTO(request);
          BeanUtils.copyProperties(acctForm, acctDto);
      }
     
      // get states
      StateDelegate stateDel = new StateDelegate();
      StateDTO[] states = stateDel.getStates();
      request.setAttribute("states", states);

      return mapping.findForward("display");
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
      
      AccountForm acctForm = (AccountForm) form;
      acctForm.setEditMode(true);
      AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
          request.getSession());
      if (acctLoginDto != null) {
          AccountDTO acctDto = SessionUtil.getAcctDTO(request);
          BeanUtils.copyProperties(acctForm, acctDto);
      }
     
      // get states
      StateDelegate stateDel = new StateDelegate();
      StateDTO[] states = stateDel.getStates();
      request.setAttribute("states", states);

      return mapping.findForward("display");
    }
}
