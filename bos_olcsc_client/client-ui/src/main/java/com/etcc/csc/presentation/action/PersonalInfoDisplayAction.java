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

import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.presentation.form.AccountForm;
import com.etcc.csc.util.SessionUtil;

public class PersonalInfoDisplayAction extends Action {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {

    AccountForm acctForm = (AccountForm) form;
    AccountDTO acctDto = SessionUtil.getAcctDTO(request);
    if (acctDto != null)
        BeanUtils.copyProperties(acctForm, acctDto);
    return mapping.findForward("success");
  }
}
