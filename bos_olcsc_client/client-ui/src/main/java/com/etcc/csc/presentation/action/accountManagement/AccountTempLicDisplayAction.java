/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;

public class AccountTempLicDisplayAction extends CSCBaseAction{

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception {

        String seqNum = request.getParameter("tagSeq");

        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());

        TagDelegate del = new TagDelegate();
        TagDTO tagDTO = del.getTagBySeqNum(loginDTO, seqNum);

        if(saveErrorMessages(request, tagDTO, "AccountLicDisplayErrors")){
            return mapping.findForward("failure");
        }//else
            TagRequestForm tagRequestForm = new TagRequestForm();
            tagRequestForm.initialize(tagDTO, false);
            tagRequestForm.setTagAmount(tagDTO.getTagAmount());
            tagRequestForm.setPbpTag(false);
            tagRequestForm.setPbpStart(null);
            tagRequestForm.setPbpEnd(null);

            request.setAttribute("tagRequestForm", tagRequestForm);
            return mapping.findForward("success");
    }
}
