/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountIopDTO;
import com.etcc.csc.presentation.form.AccountIopForm;
import com.etcc.csc.presentation.form.UserPreferenceForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountUserPreferenceSaveAction extends CSCBaseAction {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    UserPreferenceForm userPrefForm = (UserPreferenceForm) form;
    
    AccountDelegate acctDel = new AccountDelegate();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
        request.getSession());
    
    // display what's in the form
    Collection<UserPreferenceForm> col = userPrefForm.getUserPreferences();
    
    if (acctLoginDto != null) {
//        UserPreferenceDTO userPrefDto = acctDel.getUserPreference(acctLoginDto);
//        BeanUtils.copyProperties(userPrefForm, userPrefDto);
        // transform form data to dto data
        Collection<AccountIopForm> acctIops = userPrefForm.getAccountIops();
        if (saveErrorMessages(request, acctDel.updateUserPreference(acctLoginDto, 
            iopFormToDto(acctIops), userPrefFormToDto(col)), "userPrefError")){
            //Handled by saveErrorMessages
        } else {
            ActionMessages messages = getMessages(request);
            ActionMessage msg = new ActionMessage("message.save.success");
            messages.add("success", msg);
            saveMessages(request, messages);
        }
    } else {
        throw new EtccSecurityException("Security violation in "
            + "AccountUserPreferenceSaveAction");
    }
    return mapping.findForward("success");
  }

    private AccountIopDTO[] iopFormToDto(Collection<AccountIopForm> acctIops) throws Exception {
        if (acctIops != null) {
            AccountIopDTO[] result = new AccountIopDTO[acctIops.size()];
            int j = 0;
            for (Iterator<AccountIopForm> i = acctIops.iterator(); i.hasNext(); ) {
                result[j] = new AccountIopDTO();
                BeanUtils.copyProperties(result[j], i.next());
                j++;
            }
            return result;
        }
        return null;
    }

    private UserPreferenceDTO[] userPrefFormToDto(Collection<UserPreferenceForm> userPrefs) throws Exception {
        if (userPrefs != null) {
            UserPreferenceDTO[] result = new UserPreferenceDTO[userPrefs.size()];
            int j = 0;
            for (UserPreferenceForm temp : userPrefs) {
                result[j] = new UserPreferenceDTO();
                result[j].setDisplayDesc(temp.getDisplayDesc());
                result[j].setDisplayOrder(temp.getDisplayOrder());
                result[j].setPrefId(temp.getPrefId());
                result[j].setPrefType(temp.getPrefType());
                result[j].setPrefValue(StringUtil.booleanToString(temp.isSelected()));
                result[j].setSelectedDeviceId(temp.getSelectedDeviceId());
                result[j].setSelecteduserPrefValue(temp.getSelectedValue());
                j++;
            }
            return result;
        }
        return null;
    }
}
