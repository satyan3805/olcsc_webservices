package com.etcc.csc.presentation.action.onlineAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;


public class OnlineAccessUserInfoAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(OnlineAccessUserInfoAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, HttpServletResponse response) 
        throws Exception {
        
//        if (!isTokenValid(request)) {
//            request.setAttribute("invalidToken", "Y");
//            return mapping.findForward("failure");
//        }

//        DynaActionForm dynaActionForm = (DynaActionForm)form;
        OnlineAccessForm dynaActionForm = (OnlineAccessForm)form;
        HttpSession session = request.getSession();

        AccountDelegate accountDelegate = new AccountDelegate();
        AccountLoginDTO acctLoginDto = 
            accountDelegate.setupOnlineAccessStep1(
            dynaActionForm.getAcctId(), 
            dynaActionForm.getTagIdentifier(),
            dynaActionForm.getTagId(), 
            dynaActionForm.getDriverLicState(), 
            dynaActionForm.getDriverLicNbr(),
            dynaActionForm.getCompanyTaxId(),
            HttpDataUtil.getClientIpAddress( request ), session.getId(),
            HttpDataUtil.getUserAgentAttributes(request));
            
        if (saveErrorMessages(request, acctLoginDto, "invalidAccount")){
            return mapping.findForward("failure");
        }// else
            // TODO: set session info
/*            long acctId = acctLoginDto.getAcctId();
            dynaActionForm.set("acctId", Long.toString(acctId));
            AccountLoginDTO acctLogin = accountDelegate
                .createSession(acctId, request.getRemoteAddr());
*/                
                          
             logger.info("Successful login: " + acctLoginDto);
             SessionUtil.setSessionAccountLogin(session, acctLoginDto);
             resetToken(request);
             saveToken(request);
            HttpDataUtil.setDbSessionIdCookie(response, 
                acctLoginDto.getDbSessionId());
//            UserPreferenceResultDTO userPrefResult = accountDelegate
//                .getUserPreference(acctLoginDto);
//            Collection col = userPrefResult.getUserPreferences();
//            //BeanUtils.copyProperties(userPrefForm, new UserPreferenceDTO());
//            //request.setAttribute("userPrefs", col);
//            dynaActionForm.setUserPreferences(convertToFormList(col));
        return mapping.findForward("success");
    }

//    private List convertToFormList(Collection cols) {
//        List result = null;
//        if (cols != null) {
//            result = new ArrayList();
//            for (Iterator i=cols.iterator(); i.hasNext();) {
//                UserPreferenceDTO upDto = (UserPreferenceDTO) i.next();
//                OnlineAccessForm upForm = 
//                    new OnlineAccessForm();
//                upForm.setDisplayDesc(upDto.getDisplayDesc());
//                upForm.setDisplayOrder(upDto.getDisplayOrder());
//                upForm.setPrefId(upDto.getPrefId());
//                upForm.setPrefType(upDto.getPrefType());
//                upForm.setPrefValue(upDto.getPrefValue());
//                upForm.setSteId(upDto.getSteId());
//                if (upDto.getDeviceValues() != null) {
//                    upForm.setDeviceValues(Arrays.asList(
//                        upDto.getDeviceValues()));
//                }
//                if (upDto.getUserPrefValues() != null) {
//                    upForm.setUserPrefValues(Arrays.asList(
//                        upDto.getUserPrefValues()));
//                }
//                result.add(upForm);
//            }
//        }
//        return result;
//    }

}
