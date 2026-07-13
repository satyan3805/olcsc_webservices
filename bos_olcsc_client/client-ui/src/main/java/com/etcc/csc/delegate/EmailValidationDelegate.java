package com.etcc.csc.delegate;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.EmailValidationDataDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.service.EmailValidationInterface;
import com.etcc.csc.service.ServiceFactory;

public class EmailValidationDelegate implements EmailValidationInterface {
    private static final Logger logger = Logger.getLogger(EmailValidationDelegate.class);
    
    public EmailValidationDataDTO getEmailValidationData(AccountLoginDTO acctLoginDto, HttpServletRequest request) throws EtccException, EtccSecurityException {
        //acctLoginDto.setLastLoginIp(HttpDataUtil.getClientIpAddress( request ));               
        //acctLoginDto.setLoginType("AC");
        //acctLoginDto.setDbSessionId(null);
        if (logger.isTraceEnabled())
            logger.trace(" Acct Login .....  " + acctLoginDto.getLastLoginIp() + " " + acctLoginDto.getAcctId() );
      //  StatusDTO statusDto = validationStatus(acctLoginDto);
       // System.out.println("Status "  + statusDto.getStatus());
        return validationData(acctLoginDto);
    }

    public EmailValidationDataDTO validationData(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
            //AccountLoginDTO newacctLoginDto = new AccountLoginDTO();      
            //System.out.println("The acct Id " + acctLoginDto.getAcctId());
            //DtoUtil.copySimpleProperties(newacctLoginDto,acctLoginDto);
        if (logger.isTraceEnabled()){
            logger.trace("before calling validation email service" );
            logger.trace("The new acct Id " + acctLoginDto.getAcctId() + " " + acctLoginDto.getLoginType() +  " " + acctLoginDto.getDbSessionId() + acctLoginDto.getLastLoginIp());
        }
        EmailValidationDataDTO emailtypesDTO = stub().validationData(acctLoginDto);
            //StatusDTO statusDto = stub().validationStatus(newacctLoginDto);
            //BaseDTO bDTO = stub().generateEmailValidationMsg(newacctLoginDto);
        if (logger.isTraceEnabled()){
            logger.trace("after calling validation email service" + emailtypesDTO.getAccountId() + " " + emailtypesDTO.getEmailAddress() + " " + emailtypesDTO.getValidationStatus());
        }
        return emailtypesDTO;
    }

    public ResultDTO validationStatus(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        return stub().validationStatus(acctLoginDto);
    }
    
    public ResultDTO setValidationDone(AccountLoginDTO acctLoginDto) throws EtccException {
        return stub().setValidationDone(acctLoginDto);
    }

    public ResultDTO generateEmailValidationMsg(AccountLoginDTO acctLoginDto) throws EtccException {
        return stub().generateEmailValidationMsg(acctLoginDto);
    }
    
    protected EmailValidationInterface stub(){
        return ServiceFactory.getImplementation(EmailValidationInterface.class);
    }
}
