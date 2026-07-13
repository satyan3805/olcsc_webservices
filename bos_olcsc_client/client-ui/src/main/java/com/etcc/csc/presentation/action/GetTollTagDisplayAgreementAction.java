package com.etcc.csc.presentation.action;

import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetTollTagDisplayAgreementAction extends Action {
  public static final String TOLL_TAG_APPLICATION_AGREEMENT = "TOLL_TAG_APPLICATION_AGREEMENT";

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    throws Exception
  {
    SessionUtil.resetCurrentTabMenuId(request.getSession());

    if( !"false".equalsIgnoreCase( request.getParameter("fresh")))
    {
      HttpSession session = request.getSession( );
      session.removeAttribute( "TollTagForm" );
      SessionUtil.setRetailTransId(session, 0);
      SessionUtil.setSessionAccountLogin(session, null);
      SessionUtil.setSavedVehicleMap(session, null);
    }
    
    String agreement = new TagDelegate( ).getTagApplicationAgreement( );
    request.setAttribute( TOLL_TAG_APPLICATION_AGREEMENT, agreement );
    
    return mapping.findForward("success");
  }
}
