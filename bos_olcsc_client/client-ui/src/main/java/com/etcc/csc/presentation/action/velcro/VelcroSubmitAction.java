/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.velcro;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.etcc.csc.delegate.VelcroDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.UIDateUtil;

public class VelcroSubmitAction extends CSCBaseAction {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    DynaValidatorActionForm velcroForm = (DynaValidatorActionForm) form;
    
    VelcroDelegate del = new VelcroDelegate();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
        request.getSession());
    ResultDTO result = del.submitVelcroRequest(acctLoginDto, ((Integer) (velcroForm.get("orderQty"))).intValue());
    if (saveErrorMessages(request, result, "velcroError")) {
        return mapping.findForward("failure");
    } else {
        velcroForm.set("editMode", Boolean.FALSE);
        velcroForm.set("requestDate", UIDateUtil.getShortDateTime(new Date()));
    }
    return mapping.findForward("success");
  }
}
