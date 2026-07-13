package com.etcc.csc.action.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.dto.StateDTO;

public class DisplayViolatorLoginAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, HttpServletResponse response) 
        throws Exception {

        // get states
        StateDelegate stateDel = new StateDelegate();
        StateDTO[] states = stateDel.getStates();
        request.setAttribute("states", states);
        
        return mapping.findForward("success");
    }

}
