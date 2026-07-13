package com.etcc.csc.presentation.action.accountManagement;

import com.etcc.csc.util.UIDateUtil;
import com.etcc.csc.presentation.form.TagRequestForm;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccountVehicleAddPbpDisplayAction extends Action{

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception {
                                 
            TagRequestForm tagRequestForm = new TagRequestForm();
            tagRequestForm.setPbpTag(true);
            tagRequestForm.setAllowEditPbpStart(true);
            tagRequestForm.setAllowEditPbpEnd(true);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, 1);
            tagRequestForm.setPbpStart(UIDateUtil.getMediumDateTime(cal));
            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.HOUR, 1);
            cal2.add(Calendar.DAY_OF_MONTH, 3);
            tagRequestForm.setPbpEnd(UIDateUtil.getMediumDateTime(cal2));
            request.setAttribute("tagRequestForm", tagRequestForm);
            return mapping.findForward("success");
    }
}
