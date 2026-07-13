/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.presentation.action.signup.SignupBillingPANAction;
import com.etcc.csc.presentation.datatype.BillingContext;
import com.etcc.csc.presentation.form.BillingInfoForm;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class AddTagPaymentDisplayAction extends Action {
	private static final Logger logger = Logger.getLogger(AddTagPaymentDisplayAction.class);

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                                 throws Exception
     {

        TagRequestForm tagRequestForm = (TagRequestForm)form;
        HttpSession session = request.getSession();
        if(session == null)
             throw new EtccSecurityException("session timed out in AddTagPaymentDisplayAction");
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(session);
         tagRequestForm.setActivePbpTagExists(checkforEzPlateAndTagCombination(request));
         SessionUtil.getAcctDTO(request);

          if(request.getAttribute("mutipleUpload")!=null){
                 Double tagSalesAmt = (Double) request.getAttribute("activationAmount");
                 Double depositAmt = (Double) request.getAttribute("depositAmount");
                 Double totalSalesAmt = (Double) request.getAttribute("totalAmount");

                 tagRequestForm.setTagSaleAmount(tagSalesAmt.doubleValue());
                 tagRequestForm.setDepositAmount(depositAmt.doubleValue());
                 tagRequestForm.setTotalAmount(totalSalesAmt.doubleValue());

                 if(request.getAttribute("retailTransId")!=null)
                   {
                     Long transId = (Long) request.getAttribute("retailTransId");
                     tagRequestForm.setRetailTransId(transId.longValue());
                     }
             tagRequestForm.setSavedVehicles(request);
             }

         if(request.getAttribute("tagSalesAmt")!=null)    {
             Double tagSalesAmt = (Double) request.getAttribute("tagSalesAmt");
             Double depositAmt = (Double) request.getAttribute("depositAmt");
             Double totalSalesAmt = (Double) request.getAttribute("totalAmt");

             tagRequestForm.setTagSaleAmount(tagSalesAmt.doubleValue());
             tagRequestForm.setDepositAmount(depositAmt.doubleValue());
             tagRequestForm.setTotalAmount(totalSalesAmt.doubleValue());
         }
         else
         {
        	 Double tagSalesAmt = SignupBillingPANAction.getActiviationFee(acctLoginDto, tagRequestForm);
        	 tagRequestForm.setTagSaleAmount(tagSalesAmt.doubleValue());
         }

         if (tagRequestForm.getRetailTransId() == -1 && request.getAttribute("retailTransId")!=null) {
             Long transId = (Long) request.getAttribute("retailTransId");
             tagRequestForm.setRetailTransId(transId.longValue());
         }

         if( tagRequestForm.isFromConfirmation()) {
             BillingContext billingContext = (BillingContext)session.getAttribute("billingContext");
             billingContext.setTagRequestForm(tagRequestForm);
             billingContext.setPaymentAmt(tagRequestForm.getTotalAmount());

             if (billingContext.getBillingInfoForm()!=null || !(tagRequestForm.getTotalAmount()>0) || (StringUtil.stringToBoolean(request.getParameter("veh")))) {
                if (tagRequestForm.setSavedVehicles(request))
                    billingContext.setTagRequestForm(tagRequestForm);
                return mapping.findForward("confirmation");
             }
             else {
                 populateBillingInfoForm(request, tagRequestForm, billingContext);
                 return mapping.findForward(SessionUtil.REQUEST_BILLING_INFO);
             }
         }
         else
         {
             BillingContext billingContext = new BillingContext();
             billingContext.setTagRequestForm(tagRequestForm);
             billingContext.setTransactionId(tagRequestForm.getRetailTransId());
             billingContext.setEntryPoint("addTags");

             if (tagRequestForm.getTotalAmount()>0 && !(request.getParameter("veh")!=null && request.getParameter("veh").equalsIgnoreCase("y")))
             {
                 populateBillingInfoForm(request, tagRequestForm, billingContext);
                 return mapping.findForward(SessionUtil.REQUEST_BILLING_INFO);
             }
             else
             {
               if(tagRequestForm.getTotalAmount()>0)
                 billingContext.setPaymentAmt(tagRequestForm.getTotalAmount());
              else
                billingContext.setPaymentAmt(0.0);
               if(request.getParameter("veh")!=null && request.getParameter("veh").equalsIgnoreCase("y"))
                 {
                  if(tagRequestForm.getTotalAmount()>0){
                   request.setAttribute("prevPayementType","true");
                   populateBillingInfoForm(request, tagRequestForm, billingContext);
                   request.setAttribute("billing",Boolean.TRUE);
                  }
                 }
                session.setAttribute("billingContext", billingContext);
                return mapping.findForward("confirmation");
             }
         }
     }

    private void populateBillingInfoForm(HttpServletRequest request,
                                         TagRequestForm tagRequestForm,
                                         BillingContext billingContext) throws EtccSecurityException,
                                                                               EtccException {
        billingContext.setPaymentAmt(tagRequestForm.getTotalAmount());

        BillingInfoDTO billingInfoDTO = SessionUtil.getBillingInfo(request);
        if (billingInfoDTO != null) {
           BillingInfoForm billingInfoForm = new BillingInfoForm(billingInfoDTO);
           logger.debug("payment type :"+billingInfoForm.getPaymentType());
           if(request.getAttribute("prevPayementType")!=null && !(request.getAttribute("prevPayementType").toString().equalsIgnoreCase("true")))
              billingInfoForm.setPaymentTypeEnum(null);
           billingContext.setBillingInfoForm(billingInfoForm);
           billingContext.setTransactionId(tagRequestForm.getRetailTransId());//Lakshmi's Addition
           request.setAttribute("billingInfoForm", billingInfoForm);
        }
        request.getSession().setAttribute("billingContext", billingContext);
    }

    public boolean checkforEzPlateAndTagCombination(HttpServletRequest request) throws EtccException
    {
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
         if (loginDTO == null)
             throw new EtccException("session timed out in AccountVehicleAction");

//        String searchString = request.getParameter("searchString");
        AccountTagsDTO tagsDTO =  new TagDelegate().getAccountTags(loginDTO, "");
        if (tagsDTO.getAccountTagCount() > 0 && tagsDTO.getPbpTagCount() > 0){
            return true;
        }
        return false;
    }
}
