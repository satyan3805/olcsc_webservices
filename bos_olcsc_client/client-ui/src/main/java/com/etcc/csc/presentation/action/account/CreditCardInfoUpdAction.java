package com.etcc.csc.presentation.action.account;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.delegate.CreditCardDelegate;

import com.etcc.csc.dto.PaymentResultDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.CreditCardForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForward;

public class CreditCardInfoUpdAction extends CSCBaseAction {
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
    	HttpSession session = request.getSession();
        CreditCardForm ccForm = (CreditCardForm) form;
//        boolean editMode = ccForm.isEditMode();
        CreditCardDelegate ccDel = new CreditCardDelegate();
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(session);
        AccountCreditCardDTO acctCreditCardDto = new AccountCreditCardDTO();
        BeanUtils.copyProperties(acctCreditCardDto, ccForm);
        acctCreditCardDto.setCardExpires(ccForm.getMonthExp() + "/" 
            + ccForm.getYearExp());

//        if (ccForm.isUpdateConfirmed()) {
//            SessionUtil.setPaymentInfo(session, 
//                ccForm.getRetailTransId(), ccForm.getAmountDue());
//        }

        PaymentResultDTO paymentResultDto = ccDel.updateAccountCreditCard(
            acctLoginDto, acctCreditCardDto, false);
        ccForm.setSuccessful(paymentResultDto.isSuccessful());
        ccForm.setAmountDue(paymentResultDto.getAmountDue());
        if (paymentResultDto.getAmountDue() != 0) {
            ccForm.setPaymentRequired(true);
            SessionUtil.setPaymentInfo(session, 
                ccForm.getRetailTransId(), ccForm.getAmountDue());
        }
//        SessionUtil.setCcSecurityCode(session, 
//            Long.parseLong(ccForm.getSecurityCode()));
        if (!paymentResultDto.isSuccessful()) {
            saveErrorMessages(request, paymentResultDto, "creditCardUpdateError");
        }else {
            String cardNumber = ccForm.getCardNbr();
            if (cardNumber != null)
                ccForm.setCardNbr(StringUtil.maskAccount(cardNumber));
        }
        
      return mapping.findForward("success");
    }
}
