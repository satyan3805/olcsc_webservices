/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.AccountUtilDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.util.FormatUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class AccountInformationAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccountInformationAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // SessionUtil.getAcctDTO(request);
    	HttpSession session = request.getSession();
        AccountDelegate acctDel = new AccountDelegate();
        AccountUtilDelegate acctUtilDel = new AccountUtilDelegate();
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(session);
        BaseDTO baseDto = null;
        if (acctLoginDto == null)
            throw new EtccSecurityException("Session Timed out in AccountInformationAction");

        // This is for Account Closure.
        AccountTagsDTO tagsDTO = new TagDelegate().getAccountTags(acctLoginDto, "");
        if (Boolean.parseBoolean(request.getParameter("accountClosure"))
                || Boolean.parseBoolean(StringUtil.safeToString(request.getAttribute("accountclosure")))) {
            request.setAttribute("accountclosure", Boolean.TRUE);
            baseDto = acctUtilDel.checkCloseAccount(acctLoginDto);
        }
        AccountDTO acctDto = SessionUtil.getAcctDTO(request);
        acctDto = acctDel.getAccountStatus(acctLoginDto, acctDto);

        if (saveErrorMessages(request, acctDto, "acctInfoError"))
            return mapping.findForward("success");

        request.setAttribute("acctLoginInfo", acctLoginDto);
        session.setAttribute("acctLoginInfo", acctLoginDto);
        
        request.setAttribute(SessionUtil.REQUEST_ACCOUNT_INFO, acctDto);
        //session.setAttribute(SessionUtil.REQUEST_ACCOUNT_INFO, acctDto);
        
        SessionUtil.loadAuthorizedContacts(request);
        
        // Lakshmi Harish balance AMt
        
                
        session.setAttribute("acctInfo.balanceAmt", Double.toString(acctDto.getBalanceAmt()));
             
          
        
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        request.setAttribute("currentDate", currentDate); // This was added for the account closure

        BillingInfoDTO billingInfoDTO = SessionUtil.getBillingInfo(request);
//        request.setAttribute("billingPaymentMethodDisplay", FormatUtil.formatPaymentMethod(billingInfoDTO));
//        request.setAttribute("billingAddressDisplay", FormatUtil.formatAddress(billingInfoDTO));
        SessionUtil.getAcctPrefDTO(request);

        if (saveErrorMessages(request, baseDto, "closeAlert"))
            return mapping.findForward("success");

        // check for active tags
        request.setAttribute("activeTags", checkForActiveTags(tagsDTO));

        String termsCond = acctUtilDel.getCloseAccountAgreement();
        if (StringUtil.isEmpty(termsCond))
            session.removeAttribute("termsCond");
        else
            session.setAttribute("termsCond", termsCond);

        // ***** Account closer *****

        if (acctDto.isRevenueAccount()) {
    		String creditCardInfo = FormatUtil.formatCreditCards(billingInfoDTO.getCards(), ",", FormatUtil.MaskValue.ACCOUNT, true, false);
            String url = request.getContextPath() + "/accountInformation/dispChangePaymentMethod.do?closure=true";
            String closeAlertTextForCredit = "Your Refund will be credited to " + creditCardInfo
                    + ".<br />\nValidate or Update by clicking <a href=\"" + url + "\">here</a>.";
//                    + ".<br />\n<a href=\"" + url + "\">Update the credit card.</a>";
            request.setAttribute("closeAlertTextForCredit", closeAlertTextForCredit);

            url = request.getContextPath() + "/accountInformation/dispChangeMailingAddr.do?closure=true";
            String mailInfo = FormatUtil.formatAddress(acctDto, ", ", false);
//            String mailInfo = acctDto.getAddressAsLine(",");
            String closeAlertTextForCheck = "Your mailing address is listed as " + mailInfo
                    + ".<br />\nValidate or Update by clicking <a href=\"" + url + "\">here</a>.";
//                    + ".<br />\n<a href=\"" + url + "\">Update the mailing address.</a>";
            request.setAttribute("closeAlertTextForCheck", closeAlertTextForCheck);
        }

        if (Boolean.parseBoolean(request.getParameter("rf"))) {
            request.setAttribute("acctInfoRefund", "true");
            String rt = request.getParameter("rt");
            if (rt != null)
                if (rt.equalsIgnoreCase("K"))
                request.setAttribute("paymenttype", "checkV");
                else if (rt.equalsIgnoreCase("C"))
                request.setAttribute("paymenttype", "creditC");
        }
        // only set 'paymenttype' attribute if it has not already been set
        if (StringUtil.safeToString(request.getAttribute("paymenttype")) == null) {
            if (billingInfoDTO != null && billingInfoDTO.isCreditCard())
                request.setAttribute("paymenttype", "creditC");
            else
                request.setAttribute("paymenttype", "checkV");
        }

        if (Boolean.parseBoolean(StringUtil.safeToString(request.getAttribute("acctInfoRefund")))) {
            request.setAttribute("Done", Boolean.TRUE);
            request.setAttribute("accountclosure", Boolean.TRUE);
        }
        return mapping.findForward("success");
    }

    private Boolean checkForActiveTags(AccountTagsDTO tagsDTO) {
        if (tagsDTO.getAccountTagCount() > 0){
            for (TagDTO tagDTO : tagsDTO.getAccountTags()) {
                if (tagDTO.getAcctTagStatus() != null && tagDTO.getAcctTagStatus().equalsIgnoreCase("A")) {
                    return Boolean.TRUE;
                }
            }
        }
        Collection<TagDTO> acctPbpTags = tagsDTO.getPbpTags();
        if (acctPbpTags != null) {
            for (TagDTO tagDTO : acctPbpTags) {
                if (tagDTO.getAcctTagStatus() != null && tagDTO.getAcctTagStatus().equalsIgnoreCase("A")) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Adds page info parameter to the URL in the message.
     * @return error message with page info
     * @see CSCBaseAction#toString(ErrorMessageDTO)
     */
    @Override
    protected String toString(ErrorMessageDTO errorMessageDTOs) {
        String text = errorMessageDTOs.getMessage();
        logger.trace(" text :" + text);
        text = text.replaceAll("/accountMakePayment.do", "/accountMakePayment.do?page=info");
        logger.trace(" text after :" + text);
        return text;
    }
}
