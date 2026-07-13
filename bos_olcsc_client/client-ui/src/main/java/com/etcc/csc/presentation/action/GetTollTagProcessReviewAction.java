/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.presentation.datatype.LoginCreationInfo;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class GetTollTagProcessReviewAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception 
    {
        DynaActionForm dynaForm = (DynaActionForm)form;
        SessionUtil.setRetailTransId(request);
        request.setAttribute("callBack", "GetTollTag");
        LoginCreationInfo loginCreationInfo = new LoginCreationInfo();
        loginCreationInfo.setFirstName(ActionFormUtil.getString(dynaForm, "firstName"));
        loginCreationInfo.setLastName(ActionFormUtil.getString(dynaForm, "lastName"));
        loginCreationInfo.setMiddleInitial(ActionFormUtil.getString(dynaForm, "middleInitial"));
        loginCreationInfo.setUserId(ActionFormUtil.getString(dynaForm, "userId"));
        loginCreationInfo.setEmail(ActionFormUtil.getString(dynaForm, "email"));
        request.setAttribute( "loginCreationInfo", loginCreationInfo );
        return mapping.findForward("success");
    }
}
