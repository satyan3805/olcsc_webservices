/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.payment;

import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.TagDTO;

import com.etcc.csc.presentation.datatype.PaymentContext;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class FulfillTagRequestAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {
      HttpSession session = request.getSession();
      AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin( session );
      PaymentContext paymentContext = SessionUtil.getPaymentContext( session );

      TagDelegate delegate = new TagDelegate( );
      BaseDTO confirmed = delegate.confirmAddTags(accountLogin,
                                        paymentContext.getTransactionId( ).longValue( ),
                                        TagDTO.DeliveryMethod.MAIL,null);

      TagRequestForm tagRequestForm = new TagRequestForm( );
      tagRequestForm.setSavedVehicles( Arrays.asList( paymentContext.getAddedTags()) );
      tagRequestForm.setRetailTransId(paymentContext.getTransactionId().longValue());
      request.setAttribute( "tagRequestForm", tagRequestForm );

      return mapping.findForward("success");
    }
}
