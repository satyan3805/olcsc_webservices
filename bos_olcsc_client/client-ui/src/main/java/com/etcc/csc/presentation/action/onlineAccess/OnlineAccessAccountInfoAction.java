package com.etcc.csc.presentation.action.onlineAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.util.SessionUtil;

public class OnlineAccessAccountInfoAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        //TODO: create a framework for calling urls from within the tab
        // content. place the resetting of tab menu there
        SessionUtil.resetCurrentTabMenuId(request.getSession());
        saveToken(request);

        StateDelegate stateDel = new StateDelegate();
        StateDTO[] states = stateDel.getStates();
        request.setAttribute("states", states);
        TagDelegate delegate = new TagDelegate();
        request.setAttribute("tagAuthorities", delegate.getTagAuthorities());


        AppDelegate appDel = new AppDelegate();
        String clientPhoneNumber = appDel.getContactPhoneNumber();
        request.setAttribute("clientPhoneNumber", clientPhoneNumber);

        return mapping.findForward("success");
    }


}
