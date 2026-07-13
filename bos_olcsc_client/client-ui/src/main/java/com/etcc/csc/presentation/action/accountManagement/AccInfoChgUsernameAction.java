/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.SessionUtil;

public class AccInfoChgUsernameAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccInfoChgUsernameAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // TODO update DB. reload AccountDTO.
        OnlineAccessForm dynaActionForm = (OnlineAccessForm) form;
        request.setAttribute("changeUsernameMessage", "");
        AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(request.getSession());

        AccountDelegate acctDel = new AccountDelegate();
        AccountLoginDTO result;
        try {
            result = acctDel.updateLoginId(acctLogin.getDbSessionId(), acctLogin.getLastLoginIp(), dynaActionForm
                    .getLoginId(), acctLogin.getLoginId(), dynaActionForm.getPassword(), acctLogin.getAcctId() + "");
        } catch (EtccSecurityException se) {
            logger.trace("Security error in changePassword.", se);
            return mapping.findForward("securityError");
        }

        if (saveErrorMessages(request, result, "changeUsernameMessage")) {
            request.setAttribute("changeUsernameMessage", "Invalid username and password");
            return mapping.findForward("failure");
        }

        acctLogin.setLoginId(result.getLoginId());
        SessionUtil.setSessionAccountLogin(request.getSession(), acctLogin);

        // SessionUtil.getAcctDTO(request);
        request.setAttribute("setEvent3", Boolean.TRUE);
        return mapping.findForward("success");
    }
}
