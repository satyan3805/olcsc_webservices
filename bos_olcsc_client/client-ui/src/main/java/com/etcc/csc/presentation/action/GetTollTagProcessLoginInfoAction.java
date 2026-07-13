/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.TolltagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.dto.UserPreferenceResultDTO;
import com.etcc.csc.presentation.form.TollTagUserPreferenceForm;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetTollTagProcessLoginInfoAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
                                 
        TollTagUserPreferenceForm upForm = (TollTagUserPreferenceForm) form;
        
        //DynaActionForm dynaForm = (DynaActionForm)form;
        HttpSession session = request.getSession();
        AccountLoginDTO accountLoginDTO = null;
        try {
/*            accountLoginDTO = 
                    new TolltagDelegate().createAccount(ActionFormUtil.getString(dynaForm, "firstName"), 
                                                        ActionFormUtil.getString(dynaForm, "middleInitial"), 
                                                        ActionFormUtil.getString(dynaForm, "lastName"), 
                                                        ActionFormUtil.getString(dynaForm, "companyName"), 
                                                        ActionFormUtil.getString(dynaForm, "email"), 
                                                        ActionFormUtil.getString(dynaForm, "userId"), 
                                                        ActionFormUtil.getString(dynaForm, "securityQuestion"), 
                                                        ActionFormUtil.getString(dynaForm, "securityAnswer"), 
                                                        HttpDataUtil.getClientIpAddress( request ), 
                                                        session.getId(), 
                                                        request.getHeader("USER-AGENT"));
*/
            accountLoginDTO = 
                    new TolltagDelegate().createAccount(upForm.getFirstName(), 
                                                        upForm.getMiddleInitial(), 
                                                        upForm.getLastName(), 
                                                        upForm.getCompanyName(), 
                                                        upForm.getEmail(), 
                                                        upForm.getUserId(), 
                                                        upForm.getSecurityQuestion(), 
                                                        upForm.getSecurityAnswer(), 
                                                        HttpDataUtil.getClientIpAddress( request ), 
                                                        session.getId(), 
                                                        request.getHeader("USER-AGENT"));
        } catch (EtccErrorMessageException errorMessageException) {
            saveErrorMessages(request, errorMessageException);
//            processErrorMessages(errorMessageException, request);
            return mapping.findForward("failure");
        }

/*        DynaActionForm dynaForm = new DynaActionForm();
        Map map = dynaForm.getMap();
        map.put("firstName", upForm.getFirstName());
        map.put("middleInitial", upForm.getMiddleInitial());
        map.put("lastName", upForm.getLastName());
        map.put("companyName", upForm.getCompanyName());
        map.put("email", upForm.getEmail());
        map.put("userId", upForm.getUserId());
        map.put("securityQuestion", upForm.getSecurityQuestion());
        map.put("securityAnswer", upForm.getSecurityAnswer());
//        session.setAttribute("TollTagForm", dynaForm);
*/

        AccountLoginDTO accountLogin = new AccountLoginDTO();
        accountLogin.setAcctId(accountLoginDTO.getAcctId());
        accountLogin.setLoginType(AccountLoginDTO.LoginType.AC);
        accountLogin.setLoginId(AccountLoginDTO.GENERIC_USER);
        accountLogin.setDbSessionId(accountLoginDTO.getDbSessionId());
        accountLogin.setLastLoginIp(accountLoginDTO.getLastLoginIp());
        SessionUtil.setSessionAccountLogin(session, accountLogin);
        HttpDataUtil.setDbSessionIdCookie(response, accountLogin.getDbSessionId());

        // save user preferences
         AccountDelegate acctDel = new AccountDelegate();

        UserPreferenceResultDTO userPrefResult = acctDel.getUserPreference(accountLogin);
        UserPreferenceDTO[] col = userPrefResult.getUserPreferences();
        long deviceId = 0;
        // get the lone device id
        if (col != null) {
            deviceId = getDeviceId(col);
        }
        
        try {
            if (saveErrorMessages(request,
                    acctDel.updateUserPreference(accountLogin, 
             null, userPrefFormToDto(upForm.getUserPreferences(), deviceId)),
             "tolltagUpdateUserPrefError")){
                return mapping.findForward("failure");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("failure");
        }

        // display errors
        // instantiate tolltag form
        // populate with data here
        // save to session
        // verify that validation is still working

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

    private UserPreferenceDTO[] userPrefFormToDto(Collection<TollTagUserPreferenceForm> userPrefs, 
            long deviceId) throws Exception {
        if (userPrefs != null) {
            UserPreferenceDTO[] result = new UserPreferenceDTO[
                userPrefs.size()];
            int j = 0;
            for (TollTagUserPreferenceForm temp : userPrefs) {
                result[j] = new UserPreferenceDTO();
                result[j].setDisplayDesc(temp.getDisplayDesc());
                result[j].setDisplayOrder(temp.getDisplayOrder());
                result[j].setPrefId(temp.getPrefId());
                result[j].setPrefType(temp.getPrefType());
                result[j].setPrefValue(StringUtil.booleanToString(temp.isSelected()));
                result[j].setSelectedDeviceId(deviceId);
                result[j].setSelecteduserPrefValue(temp.getSelectedValue());
                j++;
            }
            return result;
        }
        return null;
    }

    private long getDeviceId(UserPreferenceDTO[] col) {
        if (col != null && col.length > 0) {
            return col[0].getDeviceValues()[0].getAcctDeviceId();
        }//else
        return 0;
    }
/*
    private long getDeviceId(Collection<UserPreferenceDTO> col) {
//        UserPreferenceDTO tempUserPref = (UserPreferenceDTO)col.iterator().next();
        if (col != null) {
            for (Iterator<UserPreferenceDTO> i=col.iterator(); i.hasNext();) {
                UserPreferenceDTO userPref = i.next();
                if (userPref.getDeviceValues() != null) {
                    return userPref.getDeviceValues()[0].getAcctDeviceId();
                }
            }
        }
        return 0;
    }
*/
}
