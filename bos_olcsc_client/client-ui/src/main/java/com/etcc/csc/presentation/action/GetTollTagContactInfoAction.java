/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.delegate.TolltagDelegate;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.presentation.form.TollTagForm;
import com.etcc.csc.presentation.validator.CreditCardValidator;
import com.etcc.csc.util.StringUtil;

public class GetTollTagContactInfoAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception 
    { 
    /*
       AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin( request.getSession() );
       AccountDTO accountDTO = null;
       try
       {
         accountDTO = new AccountDelegate( ).getAccount(accountLogin, accountLogin.getAcctId() );
       }
       catch ( Exception e ) 
       {
       }
       processPopulate( accountDTO, form );
       */
       StateDTO[] states = new StateDelegate().getStates();
       request.setAttribute("states", states);
       request.setAttribute("marketSource", new TolltagDelegate().getMarketSource() );
       
       for (StateDTO state : states) {
            if (state.isDefaultValueFlag()) {
                setDefaultStateValue(form, state.getStateCode());
                break;
            }
        }

        setDefaultValues((TollTagForm) form);
        return mapping.findForward("success");
    }
    
    /*
     *       <form-property name="address" type="java.lang.String"/>
      <form-property name="addressLine2" type="java.lang.String"/>
      <form-property name="city" type="java.lang.String"/>
      <form-property name="state" type="java.lang.String"/>
      <form-property name="zipcode" type="java.lang.String"/>
      <form-property name="plus4" type="java.lang.String"/>
      <form-property name="homePhoneAreaCode" type="java.lang.String"/>
      <form-property name="homePhoneFirst3" type="java.lang.String"/>
      <form-property name="homePhoneLast4" type="java.lang.String"/>
      <form-property name="workPhoneAreaCode" type="java.lang.String"/>
      <form-property name="workPhoneFirst3" type="java.lang.String"/>
      <form-property name="workPhoneLast4" type="java.lang.String"/>
      <form-property name="workPhoneExt" type="java.lang.String"/>
      <form-property name="driverLicenseNumber" type="java.lang.String"/>
      <form-property name="driverLicenseState" type="java.lang.String"/>
      <form-property name="mailStatement" type="java.lang.Boolean"/>
      <form-property name="hearFrom" type="java.lang.String"/>

     */
/*
    private void processPopulate( AccountDTO accountDTO, ActionForm form )
    {
      if ( accountDTO == null ) {
          return;
      }
      DynaActionForm dynaForm = ( DynaActionForm ) form;
      dynaForm.set( "address", accountDTO.getAddress1() );
      dynaForm.set( "addressLine2", accountDTO.getAddress2() );
      dynaForm.set( "city", accountDTO.getCity() );
      dynaForm.set( "state", accountDTO.getState() );
      dynaForm.set( "zipcode", accountDTO.getZipCode() );
      dynaForm.set( "plus4", accountDTO.getPlus4() );
      dynaForm.set( "homePhoneAreaCode", PhoneNumberUtil.getAreaCode( accountDTO.getHomePhoNbr( ) ) );
      dynaForm.set( "homePhoneFirst3", PhoneNumberUtil.getPrefix( accountDTO.getHomePhoNbr( ) ) );
      dynaForm.set( "homePhoneLast4", PhoneNumberUtil.getSuffix( accountDTO.getHomePhoNbr( ) ) );
      dynaForm.set( "workPhoneAreaCode", PhoneNumberUtil.getAreaCode( accountDTO.getWorkPhoNbr( ) ) );
      dynaForm.set( "workPhoneFirst3", PhoneNumberUtil.getPrefix( accountDTO.getWorkPhoNbr( ) ) );
      dynaForm.set( "workPhoneLast4", PhoneNumberUtil.getSuffix( accountDTO.getWorkPhoNbr( ) ) );
      dynaForm.set( "workPhoneExt", accountDTO.getWorkPhoExt() );
      dynaForm.set( "mobilePhoneAreaCode", PhoneNumberUtil.getAreaCode( accountDTO.getMobilePhoNbr( ) ) );
      dynaForm.set( "mobilePhoneFirst3", PhoneNumberUtil.getPrefix( accountDTO.getMobilePhoNbr( ) ) );
      dynaForm.set( "mobilePhoneLast4", PhoneNumberUtil.getSuffix( accountDTO.getMobilePhoNbr( ) ) );
      dynaForm.set( "driverLicenseNumber", accountDTO.getDriverLicNbr() );
      dynaForm.set( "driverLicenseState", accountDTO.getDriverLicState() );
      dynaForm.set( "mailStatement", Boolean.valueOf( accountDTO.isMoStmtFlag() ) );
      dynaForm.set( "hearFrom", "" + accountDTO.getMsId() );
    }
*/

    private void setDefaultStateValue(ActionForm form, String defaultStateCode) {
        TollTagForm tollTagForm = (TollTagForm) form;
        if (tollTagForm.get("state")==null || tollTagForm.get("state").toString().length()==0)
            tollTagForm.set("state", defaultStateCode);
        if (tollTagForm.get("driverLicenseState")==null || tollTagForm.get("driverLicenseState").toString().length()==0)
            tollTagForm.set("driverLicenseState", defaultStateCode);
//        if (tollTagForm.get("billingState")==null || tollTagForm.get("billingState").toString().length()==0)
//            tollTagForm.set("billingState", defaultStateCode);
        if (tollTagForm.get("licensePlateState")==null || tollTagForm.get("licensePlateState").toString().length()==0)
            tollTagForm.set("licensePlateState", defaultStateCode);
    }
    
    private void setDefaultValues(TollTagForm tollTagForm) {
//        TollTagForm tollTagForm = (TollTagForm)form;
    	String cctype = (String) tollTagForm.get(CreditCardValidator.FIELD_CC_TYPE);
        if (StringUtil.isEmpty(cctype))
            tollTagForm.set(CreditCardValidator.FIELD_CC_TYPE, Character.valueOf(CreditCardDTO.CreditCardType.VISA_CODE));

//        if (tollTagForm.get("creditCardExpirationMonth")==null || tollTagForm.get("creditCardExpirationMonth").toString().length()==0)
//            tollTagForm.set("creditCardExpirationMonth", UIDateUtil.getNextMonthValue());
//            
//        if (tollTagForm.get("creditCardExpirationYear")==null || tollTagForm.get("creditCardExpirationYear").toString().length()==0)                
//            tollTagForm.set("creditCardExpirationYear", UIDateUtil.getNextMonth().get(Calendar.YEAR)+"");     
    }
}
