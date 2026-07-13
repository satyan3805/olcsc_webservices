package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.presentation.form.TagRequestForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddTagDisplayFromViolation extends Action{
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
        if (request.getSession().getAttribute("tagRequestForm")!=null)
        {
            TagRequestForm tagForm = (TagRequestForm)request.getSession().getAttribute("tagRequestForm");
            request.setAttribute("tagRequestForm", tagForm);
            
            if (request.getSession()== null)
                throw new EtccSecurityException("session timed out in AddTagDisplayFromViolation");
                
            request.getSession().removeAttribute("tagRequestForm");
        }
        
        return mapping.findForward("success");
     }
}
