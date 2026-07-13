/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.payment;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.dto.Violation;
import com.etcc.csc.delegate.PaymentDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.datatype.PaymentContext;
import com.etcc.csc.util.SessionUtil;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

@Deprecated
public class PostViolationsAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        DynaActionForm dynaForm = (DynaActionForm)form;
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);
        PaymentContext paymentContext = SessionUtil.getPaymentContext(session);
        processViolationsSelected((String[])dynaForm.get("payViolation"), paymentContext.getViolations());

        try {
            new PaymentDelegate().postViolations(new BigDecimal(accountLogin.getAcctId()), 
                                                             accountLogin.getLoginType().toString(), 
                                                             accountLogin.getDbSessionId(), 
                                                             accountLogin.getLastLoginIp(), 
                                                             accountLogin.getLoginId(), 
                                                             paymentContext.getAuthorizedViolations( ) );
        } catch (EtccErrorMessageException errorMessageException) {
            saveErrorMessages(request, errorMessageException);
            return mapping.findForward("failure");
        }
        
        if ( "addTags".equalsIgnoreCase( paymentContext.getCallBack() ) ) 
        {
          return mapping.findForward( "fulfillTag" );
        }

        return mapping.findForward("success");
    }

/*
    private void processErrorMessages(EtccErrorMessageException errorMessageException, 
                                      HttpServletRequest request) {
        ActionMessages actionMessages = new ActionMessages();
        Iterator<String> iterator = 
            errorMessageException.getErrorMessages().iterator();
        while (iterator.hasNext()) {
            actionMessages.add(ActionErrors.GLOBAL_MESSAGE, DtoUtil.createActionMessage(iterator.next(), false));
        }
        saveErrors(request, actionMessages);
    }
*/

    private void processViolationsSelected(String[] selected, 
                                           Violation[] violations) {
        if (!ArrayUtils.isEmpty(violations)) {
            for (int i = 0; i < violations.length; i++) {
                violations[i].setAuthorized(!ArrayUtils.isEmpty(selected) && 
                                            ArrayUtils.contains(selected, 
                                                                violations[i].getId()));
            }
        }
    }
}
