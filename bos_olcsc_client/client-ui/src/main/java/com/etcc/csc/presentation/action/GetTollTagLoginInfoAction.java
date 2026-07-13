package com.etcc.csc.presentation.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.SecurityQuestionDelegate;
import com.etcc.csc.dto.UserPreferenceDTO;
import com.etcc.csc.dto.UserPreferenceResultDTO;
import com.etcc.csc.presentation.form.TollTagUserPreferenceForm;

public class GetTollTagLoginInfoAction extends Action {

    public static final String SECURITY_QUESTIONS = "securityQuestions";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception 
    {
        TollTagUserPreferenceForm tollTagForm = (TollTagUserPreferenceForm) form;
        request.setAttribute(SECURITY_QUESTIONS, 
                             new SecurityQuestionDelegate().getSecurityQuestions());

        AccountDelegate acctDel = new AccountDelegate();
        UserPreferenceResultDTO userPrefResult = acctDel
            .getTollTagUserPreference();
        UserPreferenceDTO[] col = userPrefResult.getUserPreferences();
        //BeanUtils.copyProperties(userPrefForm, new UserPreferenceDTO());
        //request.setAttribute("userPrefs", col);
        tollTagForm.setUserPreferences(convertToFormList(col));

        return mapping.findForward("success");
    }

    private List<TollTagUserPreferenceForm> convertToFormList(UserPreferenceDTO[] cols) {
        List<TollTagUserPreferenceForm> result = null;
        if (cols != null) {
            result = new ArrayList<TollTagUserPreferenceForm>(cols.length);
            for (UserPreferenceDTO upDto : cols) {
                TollTagUserPreferenceForm upForm = 
                    new TollTagUserPreferenceForm();
                upForm.setDisplayDesc(upDto.getDisplayDesc());
                upForm.setDisplayOrder(upDto.getDisplayOrder());
                upForm.setPrefId(upDto.getPrefId());
                upForm.setPrefType(upDto.getPrefType());
                upForm.setPrefValue(upDto.getPrefValue());
                upForm.setSteId(upDto.getSteId());
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
}
