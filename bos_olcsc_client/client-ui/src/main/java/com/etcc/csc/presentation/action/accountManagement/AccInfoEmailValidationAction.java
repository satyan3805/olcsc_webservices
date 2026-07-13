/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.delegate.EmailValidationDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.EmailValidationDataDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.EmailValidationForm;
import com.etcc.csc.util.HttpDataUtil;

public class AccInfoEmailValidationAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccInfoEmailValidationAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception
    {
        logger.debug("AccInfoEmailValidationAction.execute()");
        // AccountLoginDTO acctLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountLoginDTO acctLogin = new AccountLoginDTO();
        EmailValidationForm emailValidForm = (EmailValidationForm)form;
        logger.debug("AccInfoEmailValidationAction.execute()" + emailValidForm); 
        if (request.getAttribute("method") != null && request.getAttribute("method").equals("generateEMail")){
            
        }
        if(emailValidForm == null){
            emailValidForm = new EmailValidationForm();
            long acctId = 0;
            if(request.getParameter("id") != null){
                acctId = Long.parseLong(request.getParameter("id"));
                acctLogin.setAcctId(acctId);
                acctLogin.setLastLoginIp(HttpDataUtil.getClientIpAddress( request ));
                //acctLogin.setDbSessionId(request.getSession().getId());
                //acctLogin.setLoginType("AC");
                //acctLogin.setLoginId(request.getParameter("id"));               
                logger.trace("Acct Login " + acctLogin);
            }
            logger.trace("Acct Id " + acctId);             
            //emailValidForm = new EmailValidationForm();
            //emailValidForm.setPrimaryEmailAddress("warren_moody@xxxx.com");
             
            try {
                if(request.getParameter("url") != null && request.getParameter("url").equals("confirm")){
                    //changeValidationStatus(emailValidForm,acctLogin);
                    //emailValidForm = (EmailValidationForm)request.getAttribute("emailValidForm");
                     
                    //logger.trace("Email FOrm in confirm ==>" + request.getAttribute("emailValidForm") +" " + emailValidForm);
                    emailValidForm.setConfirm(true);
                    request.setAttribute("emailValidForm",emailValidForm);
                    return mapping.findForward("failure");
                }
                if(acctId != 0){
                    emailValidForm  = getPrimaryEmailAddress(emailValidForm,acctLogin,request);
                       
                    if(emailValidForm.getValidationStatus() != 1){
                        request.setAttribute("emailValidForm",emailValidForm);
                        return mapping.findForward("failure");
                    }
                    request.setAttribute("emailValidForm",emailValidForm);
                }
            /*} catch (EtccSecurityException se) {
               logger.trace("Security error in AccInfoEmailValidationAction.");
               //return mapping.findForward("securityError");
               return mapping.findForward("failure");*/
            } catch (EtccException etccEx) {
                ErrorMessageDTO msg = new ErrorMessageDTO().withMessage(etccEx.getMessage());
                saveErrorMessage(request, msg, "AccInfoEmailValidationActionError");
                return mapping.findForward("failure");
            }catch (Exception e) {
                logger.trace("Exception error in AccInfoEmailValidationActionError." + e.getMessage());
                return mapping.findForward("failure");
            }
             
            request.setAttribute("emailValidForm",emailValidForm);
        }
        // emailValidForm.setPrimaryEmailAddress("warren_moody@xxxx.com");
        //getPrimaryEmailAddress(emailValidForm);  
        //if(emailValidForm.getDisplay() == false){
        //return mapping.findForward("failure");
        //}
        request.setAttribute("emailValidForm",emailValidForm);
        return mapping.findForward("success");
        //return mapping.findForward("acctSummary");
    }
     
    private EmailValidationForm getPrimaryEmailAddress(EmailValidationForm emailValidForm,AccountLoginDTO acctLoginDto, HttpServletRequest request) throws EtccException, Exception
    {
        // emailValidForm.setPrimaryEmailAddress("warren_moody@xxxx.com");
        
        //AccountDelegate acctDel = new AccountDelegate();
        //  AccountDTO accountDTO = acctDel.getAccount(acctLoginDto, acctLoginDto.getAcctId());
        EmailValidationDelegate emailValDel = new EmailValidationDelegate();
        
//        com.etcc.csc.emailvalidation.dto.AccountLoginDTO newacctLoginDto = new com.etcc.csc.emailvalidation.dto.AccountLoginDTO();
//        DtoUtil.copySimpleProperties(newacctLoginDto,acctLoginDto);
         
        EmailValidationDataDTO emailValidDto = emailValDel.validationData(acctLoginDto);
        // com.etcc.csc.emailvalidation.dto.EmailValidationDataDTO emailValidDto = emailValDel.getEmailValidationData(newacctLoginDto,request);
        logger.trace("Email DTO " + emailValidDto.getAccountId() + " " + emailValidDto.getEmailAddress() + "  " + emailValidDto.getValidationStatus());
        //  logger.debug("account email DTO " + accountDTO.getEmailAddress());
        logger.debug("email DTO " + emailValidDto.getAccountId() + " " + emailValidDto.getEmailAddress() + "  " + emailValidDto.getValidationStatus());
        //if(emailValidDto.getEmailAddress() != null && !emailValidDto.getEmailAddress().equals("")){
        emailValidForm.setAccountId(emailValidDto.getAccountId());
        emailValidForm.setEmailAddress(emailValidDto.getEmailAddress());
        emailValidForm.setPrimaryEmailAddress(emailValidDto.getEmailAddress());
        emailValidForm.setValidationStatus(emailValidDto.getValidationStatus());
        emailValidForm.setDisplay(true);
        //}else{
        //emailValidForm.setDisplay(false);
            
        //}
        
        //emailValidForm.setAccountId(emailValidDto.getAccountId());
        //emailValidForm.setEmailAddress(emailValidDto.getEmailAddress());
        //emailValidForm.setPrimaryEmailAddress(emailValidDto.getEmailAddress());
        //emailValidForm.setValidationStatus(emailValidDto.getValidationStatus());
        return emailValidForm;
    }

/*
    private EmailValidationForm changeValidationStatus(EmailValidationForm emailValidForm,AccountLoginDTO acctLoginDto) throws EtccException, Exception
    {
        //emailValidForm.setAlternateEmailAddress("warren_moody@xxxx.com");
//        AccountDelegate acctDel = new AccountDelegate();
//        AccountDTO accountDTO = acctDel.getAccount(acctLoginDto, acctLoginDto.getAcctId());
        EmailValidationDelegate emailValDel = new EmailValidationDelegate();

//        com.etcc.csc.emailvalidation.dto.AccountLoginDTO newacctLoginDto = new com.etcc.csc.emailvalidation.dto.AccountLoginDTO();
//        DtoUtil.copySimpleProperties(newacctLoginDto,acctLoginDto);
        emailValDel.setValidationDone(acctLoginDto);
        EmailValidationDataDTO emailValidDto = emailValDel.validationData(acctLoginDto);
        emailValidForm.setValidationStatus(emailValidDto.getValidationStatus());
        return emailValidForm;
    }
*/
//    private boolean generateEmail(AccountLoginDTO loginDTO){
//        //EmailValidationDelegate emailValidDel = new EmailValidationDelegate();
//        //emailValidDel.generateEmailValidationMsg(loginDTO);
//        return true;
//    }
}
