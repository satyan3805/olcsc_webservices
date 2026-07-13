/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.action;

import java.text.DateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.util.SessionUtil;

public class AccountInfoAction extends Action {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    try {
        SessionUtil.getAcctDTO(request);
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        
        request.setAttribute("currentDate", currentDate);
    
        return mapping.findForward("success");
    } 
    catch (Throwable t) {
        throw new EtccSysException("Error getting account information.");
    }
  }
}
