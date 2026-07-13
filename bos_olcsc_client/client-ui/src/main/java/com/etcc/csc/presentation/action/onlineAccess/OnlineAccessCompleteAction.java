package com.etcc.csc.presentation.action.onlineAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class OnlineAccessCompleteAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {

//TODO: find why the following condition is always true.
//        if (!isTokenValid(request)) {
//            request.setAttribute("invalidToken", "Y");
//            return mapping.findForward("failure");
//        }
        HttpSession session = request.getSession();
        // translate form data into DTO
        OnlineAccessForm oaForm = (OnlineAccessForm) form;
        AccountDelegate acctDel = new AccountDelegate();
        OnlineAccessSetupDTO oasDto = new OnlineAccessSetupDTO();
        BeanUtils.copyProperties(oasDto, oaForm);
        String result = "success";
        AccountLoginDTO accountLogin = acctDel.setupOnlineAccess(SessionUtil.getSessionAccountLogin(session), oasDto);
        if (saveErrorMessages(request, accountLogin, "onlineAccessSetupError")) {
        	result = "failure";
        } else {

            HttpDataUtil.setDbSessionIdCookie(response, accountLogin.getDbSessionId());
            SessionUtil.setLoginEntryPoint(session, AccountLoginDTO.LoginType.AC.toString());
            SessionUtil.getAcctDTO(request);
            AccountDTO acctDTO = SessionUtil.getAcctDTO(request);
            String userInfoCookie = HttpDataUtil.createUserInfoCookie(acctDTO);
            HttpDataUtil.setUserInfoCookie(response, userInfoCookie);
            request.setAttribute("Acctid",Long.valueOf(accountLogin.getAcctId()));
            session.removeAttribute("acctLoginInfo");
            SessionUtil.setSessionAccountLogin(session, accountLogin);
        }
            // save user preference
//            Collection errors2 = acctDel.updateUserPreference(result,
//                null, userPrefFormToDto(oaForm.getUserPreferences()));

         /*
         UserPreferenceResultDTO userPrefResult = acctDel.getUserPreference(
             result);
         Collection col = userPrefResult.getUserPreferences();
         long deviceId = 0;
         // get the lone device id
         if (col != null) {
             deviceId = getDeviceId(col);
         }

         try {
          Collection errors2 = acctDel.updateUserPreference(result,
              null, userPrefFormToDto(oaForm.getUserPreferences(), deviceId));
         } catch (Exception e) {
             e.printStackTrace();
         }
         */

        return mapping.findForward(result);
    }

//    private UserPreferenceDTO[] userPrefFormToDto(Collection userPrefs,
//            long deviceId) throws Exception {
//        if (userPrefs != null) {
//            UserPreferenceDTO[] result = new UserPreferenceDTO[
//                userPrefs.size()];
//            int j = 0;
//            for (Iterator i=userPrefs.iterator(); i.hasNext(); ) {
//                OnlineAccessForm temp = (OnlineAccessForm) i.next();
//                result[j] = new UserPreferenceDTO();
//
//                result[j].setDisplayDesc(temp.getDisplayDesc());
//                result[j].setDisplayOrder(temp.getDisplayOrder());
//                result[j].setPrefId(temp.getPrefId());
//                result[j].setPrefType(temp.getPrefType());
//                if (temp.isSelected()) {
//                    result[j].setPrefValue("Y");
//                } else {
//                    result[j].setPrefValue("N");
//                }
//                result[j].setSelectedDeviceId(deviceId);
//                result[j].setSelecteduserPrefValue(temp.getSelectedValue());
//                j++;
//            }
//            return result;
//        }
//        return null;
//    }

//    private long getDeviceId(Collection col) {
////        UserPreferenceDTO tempUserPref = (UserPreferenceDTO)col.iterator().next();
//        if (col != null) {
//            for (Iterator i=col.iterator(); i.hasNext();) {
//                UserPreferenceDTO userPref = (UserPreferenceDTO) i.next();
//                if (userPref.getDeviceValues() != null) {
//                    return userPref.getDeviceValues()[0].getAcctDeviceId();
//                }
//            }
//        }
//        return 0;
//    }
}
