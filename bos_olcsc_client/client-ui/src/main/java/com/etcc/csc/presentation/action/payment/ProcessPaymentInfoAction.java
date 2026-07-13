package com.etcc.csc.presentation.action.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.etcc.csc.delegate.CreditCardDelegate;
import com.etcc.csc.presentation.datatype.CreditCard;
import com.etcc.csc.presentation.datatype.PaymentContext;
import com.etcc.csc.presentation.validator.CreditCardValidator;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;

public class ProcessPaymentInfoAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        PaymentContext paymentContext = SessionUtil.getPaymentContext(session);
        processPaymentInfo(paymentContext, form);
        SessionUtil.setPaymentContext(session, paymentContext);

        return mapping.findForward("success");
    }

    private void processPaymentInfo(PaymentContext paymentContext, 
                                    ActionForm form) throws Exception 
    {
        DynaActionForm dynaForm = (DynaActionForm)form;
        CreditCard creditCard = new CreditCard();

        creditCard.setBillingName(ActionFormUtil.getString(dynaForm, 
                                                           "billingName"));
        creditCard.setBillingAddress(ActionFormUtil.getString(dynaForm, 
                                                              "billingAddress"));
        creditCard.setBillingAddressLine2(ActionFormUtil.getString(dynaForm, 
                                                                   "billingAddressLine2"));
        creditCard.setBillingCity(ActionFormUtil.getString(dynaForm, 
                                                           "billingCity"));
        creditCard.setBillingState(ActionFormUtil.getString(dynaForm, 
                                                            "billingState"));
        creditCard.setBillingZipcode(ActionFormUtil.getString(dynaForm, 
                                                              "billingZipcode"));
        creditCard.setBillingPlus4(ActionFormUtil.getString(dynaForm, 
                                                            "billingPlus4"));
        creditCard.setCardNumber(ActionFormUtil.getString(dynaForm, 
        		CreditCardValidator.FIELD_CC_NUMBER));
        creditCard.setCardTypeCode(ActionFormUtil.getString(dynaForm, 
        		CreditCardValidator.FIELD_CC_TYPE));
        creditCard.setCardSecurityCode(ActionFormUtil.getString(dynaForm, 
        		CreditCardValidator.FIELD_CC_SECURITY_CODE));
        creditCard.setExpirationMonth(ActionFormUtil.getString(dynaForm, 
        		CreditCardValidator.FIELD_CC_EXPIRE_MONTH));
        creditCard.setExpirationYear(ActionFormUtil.getString(dynaForm, 
        		CreditCardValidator.FIELD_CC_EXPIRE_YEAR));
        creditCard.setCardTypeName(new CreditCardDelegate().getCreditCardName(creditCard.getCardTypeCode()));

        paymentContext.setPaymentMethod(creditCard);
        paymentContext.setSavePaymentMethod(!StringUtils.isEmpty(ActionFormUtil.getString(dynaForm, 
                                                                                          "savePayment")));
    }
}
