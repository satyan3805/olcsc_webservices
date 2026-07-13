/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.form.AccountForm;
import com.etcc.csc.util.SessionUtil;

public class PersonalInfoSaveAction extends CSCBaseAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {

      AccountForm acctForm = (AccountForm) form;
      acctForm.setEditMode(true);
      AccountDelegate acctDel = new AccountDelegate();
      AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
          request.getSession());
      AccountDTO acctDto = new AccountDTO();
      BeanUtils.copyProperties(acctDto, acctForm);
      if (saveErrorMessages(request, acctDel.updateAccount(acctLoginDto, acctDto), "personalInfoError")){
          //handled by saveErrorMessages
      } else {
          ActionMessages messages = getMessages(request);
          ActionMessage msg = new ActionMessage("message.save.success");
          messages.add("success", msg);
          saveMessages(request, messages);
      }

      // get states
      StateDelegate stateDel = new StateDelegate();
      request.setAttribute("states", stateDel.getStates());

      return mapping.findForward("success");
    }
}
