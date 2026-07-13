/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.velcro;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.VelcroDelegate;
import com.etcc.csc.dto.VelcroDTO;
import com.etcc.csc.util.SessionUtil;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.validator.DynaValidatorActionForm;

public class VelcroDisplayAction extends Action {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @return 
   */
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    DynaValidatorActionForm velcroForm = (DynaValidatorActionForm) form;
    
    VelcroDelegate del = new VelcroDelegate();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
        request.getSession());
//    ArrayList tags = null;
    if (acctLoginDto != null) {
        VelcroDTO velcroDto = del.getVelcroInfo(acctLoginDto);
        if (velcroDto != null) {
            BeanUtils.copyProperties(velcroForm, velcroDto);
        }
        velcroForm.set("orderQty", Integer.valueOf(1));
    } else {
        throw new EtccSecurityException("Security violation in "
            + "VelcroDisplayAction");
    }
    return mapping.findForward("success");
  }
}
