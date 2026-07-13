package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.AccountUpdateForm;
import com.etcc.csc.service.AccountInterface;
import com.etcc.csc.util.SessionUtil;

public class AccountRequiredUpdateAction extends CSCBaseAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
    {
        AccountUpdateForm acctForm = (AccountUpdateForm)form;
        int requiredUpdate = Integer.parseInt(acctForm.getRequiredUpdate());
        int sqId = 0;
        if ((requiredUpdate & AccountInterface.SECURITY_QA) > 0) {
        	sqId = Integer.parseInt(acctForm.getSecurityQuestionID());
        } else {
            acctForm.setSecurityQuestionID(null);
            acctForm.setSecurityQuestionAnswer(null);
        }

        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
        BaseDTO resultDto = new AccountDelegate().updatePasswordEmailSecurityQA(loginDTO, requiredUpdate,
        		acctForm.getPassword(), acctForm.getNewPassword(), acctForm.getEmailAddress(),
        		sqId, acctForm.getSecurityQuestionAnswer());

        if(saveErrorMessages(request, resultDto, "accountUpdateFailed")){
            return mapping.findForward("failure");
        }

        request.setAttribute("setEvent3", Boolean.TRUE);
        return mapping.findForward("success");
    }
}
