/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountUtilDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class AccountInactivateAllVehiclesAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccountInactivateAllVehiclesAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(session);
        if (loginDTO == null)
            throw new EtccSecurityException("session timed out in AccountInactivateAllVehicleAction");
        request.removeAttribute("Done");
        AccountDTO accountDTO = SessionUtil.getAcctDTO(request);
        AccountTagsDTO tagsDTO = new TagDelegate().getAccountTags(loginDTO, "");
        List<TagDTO> accountTagsList = (List<TagDTO>) tagsDTO.getAccountTags();
        if (accountDTO.isRevenueAccount()) {
            BillingInfoDTO billingInfo = SessionUtil.getBillingInfo(request);
            String refund = request.getParameter("refund");
            if (!StringUtil.isEmpty(refund)) {
                request.setAttribute("refund", getRefundDescription(refund));
                    boolean closeAcctSuccess = closeAccount(request, loginDTO, accountDTO, accountTagsList, billingInfo, refund);
                    if (closeAcctSuccess) {
                        loginDTO.setAcctActivity("I");
                        SessionUtil.setSessionAccountLogin(session, loginDTO);
                        request.setAttribute("Done", "");
                        request.setAttribute("accountclosure", "");
                        request.setAttribute("closeAccountCheck", Boolean.TRUE);
                        request.setAttribute("closeAccountAcctId", Long.valueOf(loginDTO.getAcctId()));
                        request.setAttribute("acctLoginInfo", loginDTO);
                    } else {
                        request.setAttribute("acctLoginInfo", loginDTO);
                        session.setAttribute("acctLoginInfo", loginDTO);
                        return mapping.findForward("failure");
                    }
                    // logger.debug("Closed the Acct " + closeAcct);
            }
        }

        if (request.getAttribute("Done") == null) {
            request.setAttribute("Done", Boolean.TRUE);
            request.setAttribute("accountclosure", Boolean.TRUE);
        } else {
            request.setAttribute("accountclosure", "");
            return mapping.findForward("summary");
        }
        // request.setAttribute("accountclosure",true);
        request.setAttribute("acctLoginInfo", loginDTO);
        session.setAttribute("acctLoginInfo", loginDTO);
        // request.setAttribute("activeTags", true);
        return mapping.findForward("success");
    }

    private boolean closeAccount(HttpServletRequest request, AccountLoginDTO loginDTO, AccountDTO accountDTO,
            List<TagDTO> accountTagsList, BillingInfoDTO billingInfo, String refund) {
        boolean closeAccount = false;
        ResultDTO resultDto = null;
        AccountUtilDelegate acctUtilDel = new AccountUtilDelegate();
        logger.debug("Begin : AccountInactivateAllVehiclesAction.closeAccount()");
        if (iscloseAcct(loginDTO, accountDTO, accountTagsList, billingInfo)) {
            try {
                if (PaymentType.valueOfCode(refund) == PaymentType.EFT)
                    refund = "O";
                else if (refund.length() >= 2)
                    refund = "";
                if (accountDTO.getBalanceAmt() >= 0) {
                    resultDto = acctUtilDel.closeAccount(loginDTO, refund);
                    closeAccount = !(saveErrorMessages(request, resultDto.getErrors(), DEFAULT_ALERTS_PROPERTY));
                }
            } catch (Exception e) {
                closeAccount = false;
                logger.warn("Exception closing account: " + e.getMessage(), e);
            }
        }
        logger.debug("End : AccountInactivateAllVehiclesAction.closeAccount(): " + closeAccount);
        return closeAccount;
    }

    public boolean iscloseAcct(AccountLoginDTO loginDTO, AccountDTO accountDTO, List<TagDTO> accountTagsList,
            BillingInfoDTO billingInfo) {
        boolean closeAccount = false;
        logger.info("Begin : AccountInactivateAllVehiclesAction.iscloseAcct() " + loginDTO.getAcctActivity());
        // Step 1
        /*
         if(loginDTO.getAcctActivity() != null && loginDTO.getAcctActivity().equalsIgnoreCase("S") || accountDTO.getBalanceAmt() < 0 || checkForViolations(accountTagsList)){
             closeAccount = false;
             logger.info("AccountInactivateAllVehiclesAction.iscloseAcct() suspend /balAmt/Violation  " + closeAccount);
             return closeAccount;
         } */
        if (accountDTO.getBalanceAmt() < 0) {
            closeAccount = false;
            logger.info("AccountInactivateAllVehiclesAction.iscloseAcct() balAmt " + closeAccount);
            return closeAccount;
        }
        if (accountDTO.getBalanceAmt() >= 0) {
            closeAccount = true;
        }
        if (!billingInfo.getBillingTypeDisplay().equalsIgnoreCase("I")) {
            closeAccount = true;
            logger.info("AccountInactivateAllVehiclesAction.iscloseAcct() Invoice Account " + closeAccount);
            // return closeAccount;
        } else {
            closeAccount = false;
        }
        logger.info("Closing Account ==> " + closeAccount);
        return closeAccount;
    }

    public boolean checkForViolations(List<TagDTO> accountTagsList) {
        boolean violation = false;
        if (accountTagsList != null) {
            TagDTO tagDTO = null;
            for (int idx = 0; idx < accountTagsList.size(); idx++) {
                tagDTO = accountTagsList.get(idx);
                if ((tagDTO.getAcctTagStatus().equalsIgnoreCase("A")) && tagDTO.isViolationExist()) {
                    return true;
                }
            }
        }
        return violation;
    }

    /**
     * Returns the refund description based on the specified refund input.
     * @param refund string to check for refund type
     * @return the refund description
     * @throws NullPointerException if input is empty
     * @throws IndexOutOfBoundsException if input is empty
     * @see StringUtil#isEmpty(String)
     */
    private String getRefundDescription(String refund) throws NullPointerException, IndexOutOfBoundsException {
        char paymentType = refund.charAt(0);
        switch (paymentType) {
        case PaymentType.CODE_CREDIT:
            return "Credit Card";
        case PaymentType.CODE_EFT:
        case '0':
            return "Check";
        }
        return null;
    }
}
