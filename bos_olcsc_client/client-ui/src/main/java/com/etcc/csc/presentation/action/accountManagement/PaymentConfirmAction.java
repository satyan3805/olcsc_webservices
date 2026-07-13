/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import webservice.paymentappliance.etcc.com.GetCCTokenResponseDTO;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountCreditCardDTO;

import com.etcc.csc.dto.AccountLoginDTO;

import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.datatype.BillingContext;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.CartUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WebServiceClientUtil;

public class PaymentConfirmAction extends CSCBaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    throws Exception
    {
        HttpSession session = request.getSession();

        if (session == null)
            throw new EtccSecurityException("session timed out in PaymentConfirmationAction");

        BillingContext billingContext = (BillingContext)session.getAttribute("billingContext");
        BillingInfoForm billingInfoForm = billingContext.getBillingInfoForm();
        TagRequestForm tagRequestForm = (TagRequestForm) request.getSession().getAttribute("tagRequestForm");

        AccountDelegate acctDel = new AccountDelegate();
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(session);

        String pageReturn = request.getParameter("pageReturn");
        request.setAttribute("retPageInfo", Boolean.valueOf((pageReturn != null && pageReturn.equalsIgnoreCase("info"))));

        if (request.getParameter("ezTagsExist") != null)
            request.setAttribute("ezTagsExist", request.getParameter("ezTagsExist"));
        if(request.getParameter("mutipleUpload")!=null)
            request.setAttribute("mutipleUpload",request.getParameter("mutipleUpload"));
        request.setAttribute("fleetVeh", Boolean.valueOf(request.getParameter("fleetVeh")));
        AccountCreditCardDTO primaryCard = null;
        BillingInfoDTO billingInfoDTO = null;
        if (billingInfoForm != null) {
            billingInfoDTO = billingInfoForm.newBillingInfoDTO((ContactInfoForm) session.getAttribute("contactInfoForm"));
            //request.setAttribute("primaryCardName", new CreditCardDelegate().getCreditCardName(billingInfoForm.getCardTypeEnum()));

            billingInfoDTO.setTransactionId(billingContext.getTransactionId());
            PaymentType paymentType = billingInfoDTO != null ? billingInfoDTO.getBillingType() : null;
            if (PaymentType.CREDIT.getCodeString().equals(paymentType.getCodeString())){
	              primaryCard = (AccountCreditCardDTO) billingInfoDTO.getPrimaryCard();
		            if (primaryCard instanceof AccountCreditCardDTO)
		            {
		            	primaryCard = (AccountCreditCardDTO)primaryCard;
						GetCCTokenResponseDTO getCCResp = WebServiceClientUtil.getPANManagerImpl().getCCProcessorToken(CartUtil.MERCHARD_ID,
			    				loginDTO.getAcctId()+"", ((AccountCreditCardDTO) primaryCard).getCardType().name(), primaryCard.getPaypageRegistrationId() ,
			    				StringUtil.last4Of(((AccountCreditCardDTO) primaryCard).getCardNbr()), loginDTO.getLoginId(), "OLCSC");
			    		((AccountCreditCardDTO)primaryCard).setToken(Long.valueOf(getCCResp.getToken()));
		            }
            }

        }

        double paymentAmt = billingContext.getPaymentAmt();
    	//List<CartXML> carts = generateCartItems(tagRequestForm,loginDTO,paymentAmt);
    	//Long[] ids = new AppDelegate().getAccountPostingAndShiftId(loginDTO, 1L);
		//String xmlPosting = CartUtil.getInstance().generatecheckoutxml(loginDTO.getAcctId(), carts, primaryCard, new BigDecimal(paymentAmt), ids) ;

        Double rebillAmount = null;
        Double lowBalance = null;
        List<TagDTO> tagList = new ArrayList<TagDTO>();
        if(tagRequestForm!=null)
        {
        	rebillAmount = tagRequestForm.getRebillAmount();
        	lowBalance = tagRequestForm.getLowBalanceAmount();
        	tagList = tagRequestForm.getSavedVehicles();
        }
        BaseDTO resultDTO;
       /* if (primaryCard instanceof AccountCreditCardDTO)
        {
        	resultDTO =  acctDel.makeAccountPaymentCreditCard(loginDTO, (AccountCreditCardDTO)primaryCard,rebillAmount, depAmount,tagList,paymentAmt);
        }
        else
        {
        	resultDTO =  acctDel.makeAccountPaymentEFT(loginDTO, (AccountEFTDTO)primaryCard,rebillAmount, depAmount,tagList,paymentAmt);
        }
        */
        resultDTO =  acctDel.makePayment(loginDTO, billingInfoDTO,rebillAmount, lowBalance,tagList,paymentAmt);

        if (saveErrorMessages(request, resultDTO, "saveFailed")){
            SessionUtil.getAcctDTO(request);
            return mapping.findForward("failure");
        }

        if ( billingInfoDTO != null )
        {
        	billingContext.setTransactionId(billingInfoDTO.getTransactionId());
        }

        if (tagRequestForm != null) {
            tagRequestForm.setPickup(Boolean.parseBoolean(request.getParameter("pickup")));
            List<TagDTO> addedVehicles =  tagRequestForm.getSavedVehicles();
            if (addedVehicles != null && addedVehicles.size()>0) {
                resultDTO = new TagDelegate().confirmAddTags(loginDTO, billingContext.getTransactionId(), tagRequestForm.getDeliveryMethod(),tagRequestForm.getEzTagVehicles());
                if (saveErrorMessages(request, resultDTO, "saveFailed"))
                    return mapping.findForward("failure");
            }
        }
        Double prevBalanceAmt;
        String balanceParam = request.getParameter("prevBalanceAmt");
        if (StringUtil.isEmpty(balanceParam))
            prevBalanceAmt = new Double(0);
        else
            prevBalanceAmt = Double.valueOf(balanceParam);
        request.setAttribute("prevBalanceAmt", prevBalanceAmt);
        request.setAttribute("billingContext", billingContext);
        session.removeAttribute("billingContext");
        request.setAttribute("acctId", Long.valueOf(loginDTO.getAcctId()));
        String currentDate = DateFormat.getDateInstance().format(new Date());
        request.setAttribute("dateCreated", currentDate);
        String orderShippingTime = new AppDelegate().getSysParam("ORDER SHIPPING TIME");
        request.setAttribute("orderShippingTime", orderShippingTime);
        return mapping.findForward("success");
    }




}
