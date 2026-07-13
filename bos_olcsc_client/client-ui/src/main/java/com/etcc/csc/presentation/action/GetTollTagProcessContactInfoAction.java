/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.delegate.TolltagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.PersonalInfoResponse;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class GetTollTagProcessContactInfoAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        HttpSession session = request.getSession();

        AccountLoginDTO accountLoginDTO = 
            SessionUtil.getSessionAccountLogin(session);

        PersonalInfoResponse personalInfoResponse = null;
        try {
            personalInfoResponse = new TolltagDelegate().setPersonalInfo(
                    accountLoginDTO.getAcctId(), 
                    ActionFormUtil.getString(dynaForm, "address"), 
                    ActionFormUtil.getString(dynaForm, "addressLine2"), 
                    ActionFormUtil.getString(dynaForm, "city"), 
                    ActionFormUtil.getString(dynaForm, "state"), 
                    ActionFormUtil.getString(dynaForm, "zipcode"), 
                    ActionFormUtil.getString(dynaForm, "plus4"), 
                    processPhoneNumber(ActionFormUtil.getString(dynaForm, "homePhoneAreaCode"), 
                            ActionFormUtil.getString(dynaForm, "homePhoneFirst3"), 
                            ActionFormUtil.getString(dynaForm, "homePhoneLast4")), 
                    processPhoneNumber(ActionFormUtil.getString(dynaForm, "workPhoneAreaCode"), 
                            ActionFormUtil.getString(dynaForm, "workPhoneFirst3"), 
                            ActionFormUtil.getString(dynaForm, "workPhoneLast4")), 
                    ActionFormUtil.getString(dynaForm, "workPhoneExt"), 
                    ActionFormUtil.getString(dynaForm, "driverLicenseNumber"), 
                    ActionFormUtil.getString(dynaForm, "driverLicenseState"), 
                    StringUtil.booleanToString(ActionFormUtil.getBoolean(dynaForm, "mailStatement")),
                    Integer.parseInt(ActionFormUtil.getString(dynaForm, "hearFrom")), 
                    accountLoginDTO.getDbSessionId(), 
                    accountLoginDTO.getLoginId(), 
                    accountLoginDTO.getLastLoginIp(), 
                    SessionUtil.getRetailTransId(session),
                    StringUtil.stringToBoolean(ActionFormUtil.getString(dynaForm, "checkDuplicateDL")),
                    SessionUtil.getPosId( request ));
        } catch (EtccErrorMessageException errorMessageException) {
            saveErrorMessages(request, errorMessageException);
            return mapping.findForward("failure");
        }

        if (personalInfoResponse.isDuplicateDriverLicense()) {
            request.setAttribute("alertDuplicateDL", "Y");
            return mapping.findForward("failure");
        } else {
        	long retailTransId = SessionUtil.getRetailTransId(session);
            if (retailTransId == 0) {
            	SessionUtil.setRetailTransId(session, personalInfoResponse.getTransactionId());
            }
        }

        return mapping.findForward("success");
    }

    private String processPhoneNumber(String areaCode, String prefix, String suffix) {
        return StringUtils.isNotEmpty(areaCode) && 
               StringUtils.isNotEmpty(prefix) && 
               StringUtils.isNotEmpty(suffix) ? areaCode + prefix + suffix : 
               "";
    }

/*
    private void processErrorMessages(EtccErrorMessageException errorMessageException, 
                                      HttpServletRequest request) {
        ActionMessages actionMessages = new ActionMessages();
        Iterator iterator = 
            errorMessageException.getErrorMessages().iterator();
        while (iterator.hasNext()) {
            actionMessages.add(ActionErrors.GLOBAL_MESSAGE, DtoUtil.createActionMessage(iterator.next(), false));
        }
        saveErrors(request, actionMessages);
    }
*/
}
