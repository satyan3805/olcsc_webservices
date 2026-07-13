/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.payment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.CreditCardDelegate;
import com.etcc.csc.delegate.PaymentDelegate;
import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.dto.bean.OlcPaymentInfoRecBean;
import com.etcc.csc.presentation.datatype.CreditCard;
import com.etcc.csc.presentation.datatype.PaymentContext;
import com.etcc.csc.presentation.validator.CreditCardValidator;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class DisplayPaymentInfoAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        if (!"true".equalsIgnoreCase(request.getParameter("validate"))) {
            PaymentContext paymentContext = SessionUtil.getPaymentContext(session);
            processStoredPaymentInfo(SessionUtil.getSessionAccountLogin(session), paymentContext);
            processPopulate(paymentContext, form);
            SessionUtil.setPaymentContext(session, paymentContext);
        }
        else {
            setDefaultValues((DynaActionForm)form);
        }
        request.setAttribute("states", new StateDelegate().getStates());
        request.setAttribute("creditCardTypes", new CreditCardDelegate().getCreditCardTypes());

        return mapping.findForward("success");
    }

    private void processStoredPaymentInfo(AccountLoginDTO accountLogin, 
                                          PaymentContext paymentContext) {
    	if (paymentContext.getPaymentMethod() == null || 
    			StringUtils.isEmpty(paymentContext.getPaymentMethod().getCardNumber())) 
    	{
    		if (accountLogin.getLoginType() == null || 
    				AccountLoginDTO.LoginType.AC == accountLogin.getLoginType()) 
    		{
    			OlcPaymentInfoRecBean paymentRecord = null;
    			try {
    				paymentRecord = 
    					new PaymentDelegate().getPaymentInfo(
    							new BigDecimal(accountLogin.getAcctId()), 
    							accountLogin.getLoginType().toString(), 
    							accountLogin.getDbSessionId(), 
    							accountLogin.getLastLoginIp(), 
    							accountLogin.getLoginId());
    			} catch (Exception e) {
    				System.out.println( "Error while trying to get payment record");
    			}
    			if (paymentRecord != null) 
    			{
    				CreditCard card = new CreditCard();
    				card.setBillingAddress(paymentRecord.getAddress1());
    				card.setBillingAddressLine2(paymentRecord.getAddress2());
    				card.setBillingCity(paymentRecord.getCity());
    				card.setBillingState(paymentRecord.getState());
    				card.setBillingZipcode(paymentRecord.getZipCode());
    				card.setBillingPlus4(paymentRecord.getPlus4());
    				card.setBillingName(paymentRecord.getNameOnCard());
    				card.setCardNumber(paymentRecord.getCardNbr());
    				if (paymentRecord.getCardExpires() != null) {
    					card.setExpirationYear( new SimpleDateFormat( "yyyy" ).format( paymentRecord.getCardExpires().getTime( ) ) );
    					card.setExpirationMonth( new SimpleDateFormat( "MM" ).format( paymentRecord.getCardExpires().getTime( ) ) );
    				}
    				card.setCardSecurityCode(paymentRecord.getCardSecCode());
    				card.setCardTypeCode(paymentRecord.getCardCode());
    				paymentContext.setPaymentMethod( card );
    			}
    		}
    	}
    }

    private void processPopulate(PaymentContext paymentContext, 
                                 ActionForm form) throws Exception {
        DynaActionForm dynaForm = (DynaActionForm)form;
        CreditCard paymentMethod = null;
        if (paymentContext == null) {
            setDefaultValues(dynaForm);
            return;
        }
        paymentMethod = paymentContext.getPaymentMethod();
        if (paymentMethod == null) {
            setDefaultValues(dynaForm);
            return;
        }

        dynaForm.set("billingName", paymentMethod.getBillingName());
        dynaForm.set("billingAddress", paymentMethod.getBillingAddress());
        dynaForm.set("billingAddressLine2", paymentMethod.getBillingAddressLine2());
        dynaForm.set("billingCity", paymentMethod.getBillingCity());
        dynaForm.set("billingState", paymentMethod.getBillingState());
        dynaForm.set("billingZipcode", paymentMethod.getBillingZipcode());
        dynaForm.set("billingPlus4", paymentMethod.getBillingPlus4());
        dynaForm.set(CreditCardValidator.FIELD_CC_NUMBER, paymentMethod.getCardNumber());
        dynaForm.set(CreditCardValidator.FIELD_CC_SECURITY_CODE, paymentMethod.getCardSecurityCode());
        dynaForm.set(CreditCardValidator.FIELD_CC_TYPE, paymentMethod.getCardTypeCode());
        dynaForm.set(CreditCardValidator.FIELD_CC_EXPIRE_MONTH, paymentMethod.getExpirationMonth());
        dynaForm.set(CreditCardValidator.FIELD_CC_EXPIRE_YEAR, paymentMethod.getExpirationYear());
        dynaForm.set("savePayment", 
                     paymentContext.getSavePaymentMethod() ? "Y" : "");
        setDefaultValues(dynaForm);
    }

    private void setDefaultValues(DynaActionForm dynaForm) throws EtccException, EtccSecurityException {
    	String cctype = (String) dynaForm.get(CreditCardValidator.FIELD_CC_TYPE);
        if (StringUtil.isEmpty(cctype))
            dynaForm.set(CreditCardValidator.FIELD_CC_TYPE, Character.valueOf(CreditCardDTO.CreditCardType.VISA_CODE));

        if (dynaForm.get("billingState")==null || dynaForm.get("billingState").toString().length()==0){
            StateDTO[] states = new StateDelegate().getStates();
            for (StateDTO state : states) {
                if (state.isDefaultValueFlag()) {
                    dynaForm.set("billingState", state.getStateCode());
                    break;
                }//end if
            }//end for states
        }//if (billingState null...
        //end setDefaultValues
    }
}
