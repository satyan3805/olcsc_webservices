/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.OnlineAccessForm;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;

public class SignupActivateAccountAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(SignupActivateAccountAction.class);

    // HACK: Due to the fact this is Struts1, we can not save state in the Action class.
    // So, we need to store properties in the Thread.
    private static final ThreadLocal<PaymentType> paymentType = new ThreadLocal<PaymentType>();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

          HttpSession session = request.getSession();
          BillingInfoDTO billingInfoDTO  = (BillingInfoDTO) session.getAttribute("billingInfoDTO");
          OnlineAccessForm onlineAcessForm = (OnlineAccessForm) session.getAttribute("OnlineAccessForm");
          logger.debug("AccId : "+onlineAcessForm.getAcctId());
          TagRequestForm tagRequestForm = (TagRequestForm)session.getAttribute("tagRequestForm");
          ContactInfoForm contactInfoForm = (ContactInfoForm) session.getAttribute("contactInfoForm");
          String pickup = request.getParameter("pickup");
          logger.debug("Pickup:" + pickup);
          tagRequestForm.setPickup(Boolean.parseBoolean(pickup));
          //BillingInfoDTO billingInfoDTO = billingInfoForm.newBillingInfoDTO((ContactInfoForm) session.getAttribute("contactInfoForm"));
          //request.setAttribute("primaryCardName", new CreditCardDelegate().getCreditCardName(billingInfoDTO.getPrimaryCard().getCardType()));
          AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(session);
          onlineAcessForm.setAcctId(Long.toString(loginDTO.getAcctId()));
          session.setAttribute("OnlineAccessForm",onlineAcessForm);
          //Weblogic upgrade cluster issues fix
          session.setAttribute("tagRequestForm",tagRequestForm);
        //Weblogic upgrade cluster issues fix ends 
          double paymentAmt = tagRequestForm.getTotalAmount();
          if (paymentAmt <= 0 ) {
              ErrorMessageDTO errMsg = new ErrorMessageDTO();
              errMsg.setMessage("The Payment amount is incorrect please.Please contact the system administrator.");
              //java.util.Collection ce = (ArrayList)errorList;
              saveErrorMessages(request,new ErrorMessageDTO[]{errMsg}, "saveFailed");
              return mapping.findForward("failure");
          } else if (paymentAmt > 0) {
        	 // List<CartXML> carts = generateCartItems(tagRequestForm, loginDTO, paymentAmt);
        	  //Long[] ids = new AppDelegate().getAccountPostingAndShiftId(loginDTO, 1L);
			  //String xmlPosting = CartUtil.getInstance().generatecheckoutxml(loginDTO.getAcctId(), carts, billingInfoDTO.getPrimaryCard(), new BigDecimal(paymentAmt), ids);
              //BaseDTO resultDTO =  new AccountDelegate().makePayment(loginDTO, billingInfoDTO.getPrimaryCard(), xmlPosting, null);
        	  //BaseDTO resultDTO; =   new AccountDelegate().makeAccountPayment(loginDTO, billingInfoDTO.getPrimaryCard(), tagRequestForm.getRebillAmount(), tagRequestForm.getDepositAmount(),tagRequestForm.getSavedVehicles(),paymentAmt);
        	  AccountDelegate acctDel = new AccountDelegate();
        	  AccountPaymentMethodDTO primaryCard = billingInfoDTO.getPrimaryCard();
        	  BaseDTO resultDTO;
        	  Double rebillAmount = null;
              Double lowBalance = null;
              List<TagDTO> tagList = new ArrayList<TagDTO>();
              if(tagRequestForm!=null)
              {
              	rebillAmount = tagRequestForm.getRebillAmount();
              	lowBalance = tagRequestForm.getLowBalanceAmount();
              	tagList = tagRequestForm.getSavedVehicles();
              }
        	  /*if (primaryCard instanceof AccountCreditCardDTO)
              {
              	resultDTO =  acctDel.makeAccountPaymentCreditCard(loginDTO, (AccountCreditCardDTO)primaryCard,rebillAmount, depAmount,tagList,paymentAmt);
              }
              else
              {
              	resultDTO =  acctDel.makeAccountPaymentEFT(loginDTO, (AccountEFTDTO)primaryCard,rebillAmount, depAmount,tagList,paymentAmt);
              }*/
              
        	  
              resultDTO =  acctDel.makePayment(loginDTO, billingInfoDTO,rebillAmount, lowBalance,tagList,paymentAmt);
              
        	  
              paymentType.set(billingInfoDTO.getBillingType());
              if (saveErrorMessages(request, resultDTO, "saveFailed")){
                  return mapping.findForward("failure");
              }//else

              paymentType.remove();

           // Build EZ Tag DTOS  list from tag request form
              List<TagDTO> vehicles = tagRequestForm.getEzTagVehicles();
              resultDTO = new TagDelegate().confirmAddTags(loginDTO, billingInfoDTO.getTransactionId(), tagRequestForm.getDeliveryMethod(), vehicles);
              contactInfoForm.setRetailTransId(((TagDTO)resultDTO).getTransactionId());

              //Send new account welcome notification
              if (vehicles.size() == 0)
              {
            	  vehicles = tagRequestForm.getSavedVehicles();
              }

              resultDTO = new AccountDelegate().sendWelcomeNotification(loginDTO, tagRequestForm.getTagSaleAmount(), vehicles);
//              contactInfoForm.setRetailTransId(((TagDTO)resultDTO).getTransactionId());

              if (saveErrorMessages(request, resultDTO, "saveFailed")){
                  return mapping.findForward("failure");
              }

              /* This is added for account closure */
               loginDTO.setAcctActivity("A");
               SessionUtil.setSessionAccountLogin(session,loginDTO);
          }

//          request.setAttribute("primaryCardName", new CreditCardDelegate().getCreditCardName(billingInfoDTO.getPrimaryCard().getCardType()));
          String dateCreated = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
          request.setAttribute("dateCreated", dateCreated);
          SessionUtil.getAcctPrefDTO(request);
          String orderShippingTime = new AppDelegate().getSysParam("ORDER SHIPPING TIME");
          request.setAttribute("orderShippingTime", orderShippingTime);
        //Weblogic upgrade cluster issues fix
          session.setAttribute("tagRequestForm",tagRequestForm);
        //Weblogic upgrade cluster issues fix ends 
          return mapping.findForward("success");
      }

	

    /**
     * Adds URL for editing credit card billing.
     * @return error message with URL
     * @see CSCBaseAction#toString(ErrorMessageDTO)
     */
    @Override
    protected String toString(ErrorMessageDTO errorMessageDTO) {
        String text= errorMessageDTO.getMessage();
        PaymentType pt = paymentType.get();
        if(pt != null && pt == PaymentType.CREDIT)
            text = text + " Click <a href=\"##\" onclick=\"javascript:gotoBillingInfo('"+paymentType+"');\"> here </a> to Edit Credit Card Billing.";
        return text;
    }
}
