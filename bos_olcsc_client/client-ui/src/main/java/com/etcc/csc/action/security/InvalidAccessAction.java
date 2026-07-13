package com.etcc.csc.action.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class InvalidAccessAction extends Action {
  private static final Logger logger = Logger.getLogger(InvalidAccessAction.class);
    
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
    
    Exception ex = (Exception) request.getAttribute(Globals.EXCEPTION_KEY);
    logger.error("Security exception " + ex + ". Terminating session for "
        + SessionUtil.getSessionAccountLogin(request.getSession()), ex);
    HttpDataUtil.removeCookies(response);
    request.getSession().invalidate();
    request.removeAttribute(Globals.ERROR_KEY);
    
    return mapping.findForward("success");
  }
}