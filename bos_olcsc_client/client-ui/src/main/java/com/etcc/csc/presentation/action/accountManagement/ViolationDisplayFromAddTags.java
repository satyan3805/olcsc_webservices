package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.presentation.form.TagRequestForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViolationDisplayFromAddTags extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
        TagRequestForm tagForm = (TagRequestForm) form;
         if (request.getSession() == null)
            throw new EtccSecurityException("session timed out in ViolationDisplayFromAddTags");
            
        request.getSession().setAttribute("tagRequestForm", tagForm);
        
        return mapping.findForward("success");
     
     }
}
