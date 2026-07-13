package com.etcc.csc.presentation.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountIopDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.dto.UserPreferenceResultDTO;
import com.etcc.csc.presentation.form.AccountIopForm;
import com.etcc.csc.presentation.form.UserPreferenceForm;
import com.etcc.csc.util.SessionUtil;

public class AccountUserPreferenceDisplayAction extends CSCBaseAction {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @return 
   */
  @Override
public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    UserPreferenceForm userPrefForm = (UserPreferenceForm) form;
    
    AccountDelegate acctDel = new AccountDelegate();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
        request.getSession());
    if (acctLoginDto != null) {
        UserPreferenceResultDTO userPrefResult = acctDel.getUserPreference(acctLoginDto);
        UserPreferenceDTO[] col = userPrefResult.getUserPreferences();
        userPrefForm.setUserPreferences(convertToFormList(col));
        userPrefForm.setAccountIops(convertIopToFormList(
            userPrefResult.getAccountIops()));
        if (userPrefResult.getErrors() != null && userPrefResult.getErrors().length > 0 ) {
            userPrefForm.setMessage(userPrefResult.getErrors()[0].getMessage());
        }
            
    } else {
        throw new EtccSecurityException("Security violation in "
            + "AccountUserPreferenceDisplayAction");
    }
    return mapping.findForward("success");
  }

    private List<UserPreferenceForm> convertToFormList(UserPreferenceDTO[] cols) {
        List<UserPreferenceForm> result = null;
        if (cols != null) {
            result = new ArrayList<UserPreferenceForm>(cols.length);
            for (UserPreferenceDTO upDto : cols) {
                UserPreferenceForm upForm = new UserPreferenceForm();
                upForm.setDisplayDesc(upDto.getDisplayDesc());
                upForm.setDisplayOrder(upDto.getDisplayOrder());
                upForm.setPrefId(upDto.getPrefId());
                upForm.setPrefType(upDto.getPrefType());
                upForm.setPrefValue(upDto.getPrefValue());
                if (upDto.getDeviceValues() != null) {
                    upForm.setDeviceValues(Arrays.asList(
                        upDto.getDeviceValues()));
                }
                if (upDto.getUserPrefValues() != null) {
                    upForm.setUserPrefValues(Arrays.asList(
                        upDto.getUserPrefValues()));
                }
                result.add(upForm);
            }
        }
        return result;
    }

    private List<AccountIopForm> convertIopToFormList(AccountIopDTO[] cols) throws Exception {
        List<AccountIopForm> result = null;
        if (cols != null) {
            result = new ArrayList<AccountIopForm>(cols.length);
            for (AccountIopDTO upDto : cols) {
                AccountIopForm upForm = new AccountIopForm();
                BeanUtils.copyProperties(upForm, upDto);
                result.add(upForm);
            }
        }
        return result;
    }
}