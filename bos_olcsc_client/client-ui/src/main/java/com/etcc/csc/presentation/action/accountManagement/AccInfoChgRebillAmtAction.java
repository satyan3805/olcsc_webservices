package com.etcc.csc.presentation.action.accountManagement;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.AccountForm;
import com.etcc.csc.util.SessionUtil;

public class AccInfoChgRebillAmtAction extends CSCBaseAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception {
        AccountForm accountForm = (AccountForm)form;
        
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDelegate acctDel = new AccountDelegate();
        double lowBalLevel = accountForm.getRebillAmt()/4;
        String lowBalLevelS = String.format("%9.2f", new Float(lowBalLevel));
        
        AccountLoginDTO result = acctDel.updateRebillAmt(""+acctLogin.getAcctId(), "AC",
                              acctLogin.getDbSessionId(), acctLogin.getLastLoginIp(),
                              acctLogin.getLoginId(), new BigDecimal(accountForm.getRebillAmt()),
                              new BigDecimal(Double.parseDouble(lowBalLevelS)));
        
        if (saveErrorMessages(request, result, "changeRebillAmtError")){
            return mapping.findForward("failure");
        }//else
        
             //acctLogin.setLoginId(result.getLoginId());
             //SessionUtil.setSessionAccountLogin(request.getSession(), acctLogin);
        
//        SessionUtil.getAcctDTO(request);
        request.setAttribute("setEvent3", Boolean.TRUE);
        return mapping.findForward("success");
     }
}
