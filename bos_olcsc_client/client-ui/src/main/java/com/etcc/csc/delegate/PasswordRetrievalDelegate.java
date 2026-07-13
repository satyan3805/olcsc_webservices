/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.service.PasswordRetrievalInterface;
import com.etcc.csc.service.ServiceFactory;

public class PasswordRetrievalDelegate implements PasswordRetrievalInterface {
    private static final Logger logger = Logger.getLogger(PasswordRetrievalDelegate.class);

    public OnlineAccessSetupDTO retrieveAccountInfo(OnlineAccessSetupDTO onlineAccessDTO, 
        String ipAddress, String sessionId, UserEnvDTO userEnvDto) throws EtccException{
        logger.debug ("**************===========>  - in retrieveAccountInfo()");
        try {

//            com.etcc.csc.service.types.UserEnvDTO newUserEnvDto = 
//                        new com.etcc.csc.service.types.UserEnvDTO();
//            com.etcc.csc.service.types.OnlineAccessSetupDTO newOasDto =
//                        new com.etcc.csc.service.types.OnlineAccessSetupDTO();                        
//
//            DtoUtil.copySimpleProperties(newUserEnvDto,userEnvDto);
//            DtoUtil.copySimpleProperties(newOasDto, onlineAccessDTO);
            
//            com.etcc.csc.service.types.OnlineAccessSetupDTO onlineAccessTypesDTO = 
//                stub().retrieveAccountInfo(newOasDto, ipAddress, sessionId, newUserEnvDto);
            return ServiceFactory.getImplementation(PasswordRetrievalInterface.class)
                        .retrieveAccountInfo(onlineAccessDTO, ipAddress, sessionId, userEnvDto);
//            return convertOnlineAccessDtoToDtoPackage(onlineAccessTypesDTO);//onlineAccessDTO;

        } catch (Throwable t) {
            throw new EtccException("Error running retrieveAccountInfo: " + t, t);
        }
    }
    

        public OnlineAccessSetupDTO retrieveEmailAddressInfo
        (OnlineAccessSetupDTO onlineAccessDTO, 
            String ipAddress, String sessionId, UserEnvDTO userEnvDto) throws EtccException{
            logger.debug ("**************===========>  - in retrieveAccountInfo()");
            try {

//                com.etcc.csc.service.types.UserEnvDTO newUserEnvDto = 
//                            new com.etcc.csc.service.types.UserEnvDTO();
//                com.etcc.csc.service.types.OnlineAccessSetupDTO newOasDto =
//                            new com.etcc.csc.service.types.OnlineAccessSetupDTO();                        
    //
//                DtoUtil.copySimpleProperties(newUserEnvDto,userEnvDto);
//                DtoUtil.copySimpleProperties(newOasDto, onlineAccessDTO);
                
//                com.etcc.csc.service.types.OnlineAccessSetupDTO onlineAccessTypesDTO = 
//                    stub().retrieveAccountInfo(newOasDto, ipAddress, sessionId, newUserEnvDto);
                return ServiceFactory.getImplementation(PasswordRetrievalInterface.class)
                            .retrieveEmailAddressInfo(onlineAccessDTO, ipAddress, sessionId, userEnvDto);
//                return convertOnlineAccessDtoToDtoPackage(onlineAccessTypesDTO);//onlineAccessDTO;

            } catch (Throwable t) {
                throw new EtccException("Error running retrieveAccountInfo: " + t, t);
            }
        }
        public OnlineAccessSetupDTO validationData(OnlineAccessSetupDTO onlineAccessDTO, 
                String ipAddress, String sessionId, UserEnvDTO userEnvDto) throws EtccException{
                logger.debug ("**************===========>  - in retrieveAccountInfo()");
                try {
      
                    return ServiceFactory.getImplementation(PasswordRetrievalInterface.class)
                    .validationData(onlineAccessDTO, ipAddress, sessionId, userEnvDto);

                } catch (Throwable t) {
                    throw new EtccException("Error running retrieveAccountInfo: " + t, t);
                }
            }
    public OnlineAccessSetupDTO validateSecurityAnswer(String sessionId, 
                                          String ipAddress, 
                                          OnlineAccessSetupDTO onlineAccessDTO) throws EtccException{
        logger.debug ("**************===========>  - in validateSecurityAnswer");
        try {

//            com.etcc.csc.service.types.OnlineAccessSetupDTO newOasDto =
//                        new com.etcc.csc.service.types.OnlineAccessSetupDTO();                        
//            DtoUtil.copySimpleProperties(newOasDto, onlineAccessDTO);
//            com.etcc.csc.service.types.OnlineAccessSetupDTO returnDTO = stub().validateSecurityAnswer(sessionId,ipAddress, newOasDto);
//            return convertOnlineAccessDtoToDtoPackage(returnDTO);
            return ServiceFactory.getImplementation(PasswordRetrievalInterface.class)
                        .validateSecurityAnswer(sessionId,ipAddress, onlineAccessDTO);
        } catch (Throwable t) {
            throw new EtccException("Error running validateSecurityAnswer: " + t, t);
        }
    }
    
    public OnlineAccessSetupDTO addSecurityQuestionAnswer(String sessionId, String ipAddress, OnlineAccessSetupDTO onlineAccessDTO) throws EtccException {
        logger.debug ("**************===========>  - in addSecurityQuestionAnswer()");
        try {

//            com.etcc.csc.service.types.OnlineAccessSetupDTO newOasDto =
//                        new com.etcc.csc.service.types.OnlineAccessSetupDTO();                        
//            DtoUtil.copySimpleProperties(newOasDto, onlineAccessDTO);
//            com.etcc.csc.service.types.OnlineAccessSetupDTO returnDTO = stub().addSecurityQuestionAnswer(sessionId,ipAddress, newOasDto);
//            return convertOnlineAccessDtoToDtoPackage(returnDTO);
            return ServiceFactory.getImplementation(PasswordRetrievalInterface.class)
                        .addSecurityQuestionAnswer(sessionId,ipAddress, onlineAccessDTO);
        } catch (Throwable t) {
            throw new EtccException("error adding security question and answer: " +t, t);
        }
    }
    
    public OnlineAccessSetupDTO addEmailAddress(String sessionId, String ipAddress, OnlineAccessSetupDTO onlineAccessDTO) throws EtccException {
        logger.debug ("**************===========>  - in addEmailAddress()");
        try {

//            com.etcc.csc.service.types.OnlineAccessSetupDTO newOasDto =
//                        new com.etcc.csc.service.types.OnlineAccessSetupDTO();                        
//            DtoUtil.copySimpleProperties(newOasDto, onlineAccessDTO);
//            com.etcc.csc.service.types.OnlineAccessSetupDTO returnDTO = stub().addEmailAddress(sessionId,ipAddress, newOasDto);
//            return convertOnlineAccessDtoToDtoPackage(returnDTO);
            return ServiceFactory.getImplementation(PasswordRetrievalInterface.class)
                    .addEmailAddress(sessionId,ipAddress, onlineAccessDTO);
            
        } catch (Throwable t) {
            throw new EtccException("error adding email address: "+t, t);
        }
    }

    public Collection<String> changePassword(AccountLoginDTO acctLoginDTO, String oldPwd, String pwd) throws EtccException, EtccSecurityException {
        logger.debug ("**************===========>  - in changePassword()");
        try{
//         PasswordretrievalWsSoapHttpPortClient  port = stub();
//         return port.changePassword(convertToWSForm(acctLoginDTO),oldPwd, pwd);
            return ServiceFactory.getImplementation(PasswordRetrievalInterface.class)
                        .changePassword(acctLoginDTO,oldPwd, pwd);
        }catch (EtccSecurityException se) {
            throw se;
        }
        catch (Throwable t) {
            logger.error("changePassword: " + t.getMessage(), t);
            throw new EtccException(t.getMessage(), t);
        }
    }

    public static void main(String arg[])throws EtccException, EtccSecurityException {
            PasswordRetrievalDelegate deleg = new PasswordRetrievalDelegate();
//            boolean result=true;
            OnlineAccessSetupDTO onlineAccessDTO= new OnlineAccessSetupDTO();
//            UserEnvDTO userEdto = new UserEnvDTO();
            onlineAccessDTO.setSecurityQuestionAnswer("xia");
            onlineAccessDTO.setEmailAddress("2@2.com");
            onlineAccessDTO.setTagId("10203233");
            onlineAccessDTO.setCompanyTaxId("0123456789");
            onlineAccessDTO.setAgencyId("HCTA");
            onlineAccessDTO.setDriverLicState("AK");
            onlineAccessDTO.setDriverLicNbr("231%fd22");
            onlineAccessDTO.setSecurityQuestionAnswer("xia");
            onlineAccessDTO.setAcctId(233123);
            //onlineAccessDTO.setEmailAddress("!@#..@yahoo.com..!");
             onlineAccessDTO.setEmailAddress("yuanlujiang@yahoo.com");
            String sessionId = "fsdfsd123123";
            String ipAddress = "10.20.20.30";
            
            /*test retrieveAccountInfo( OnlineAccessSetupDTO onlineAccessDTO, 
                                        String ipAddress, 
                                        String sessionId,  
                                        UserEnvDTO userEnvDto)
            */
             
            
            try{
            
            //result = deleg.validateSecurityAnswer(ssId,ip,onASetupDto);
            // deleg.retrieveAccountInfo(onASetupDto,ipAddress,sessionId, userEdto);
             
           //     deleg.validateSecurityAnswer(sessionId, ipAddress, onlineAccessDTO);
            deleg.addEmailAddress(sessionId, ipAddress, onlineAccessDTO) ;
             

            }catch (EtccException ee) {
            //logger.error("Exception in getAccountCreditCards() " + ee, ee);
            throw ee;
            }
        }
    
//    private com.etcc.csc.service.types.OnlineAccessSetupDTO  convertOnlineAccessDtoToTypesPackage (OnlineAccessSetupDTO onlineAccessDTO) {
//        com.etcc.csc.service.types.OnlineAccessSetupDTO result = null;
//        
//        if (onlineAccessDTO != null) {
//            result = new com.etcc.csc.service.types.OnlineAccessSetupDTO();
//            result.setAcctId(onlineAccessDTO.getAcctId());
//            result.setAgencyId(onlineAccessDTO.getAgencyId());
//            result.setCompanyTaxId(onlineAccessDTO.getCompanyTaxId());
//            result.setCreatedBy(onlineAccessDTO.getCreatedBy());
//            result.setDriverLicNbr(onlineAccessDTO.getDriverLicNbr());
//            result.setDriverLicState(onlineAccessDTO.getDriverLicState());
//            result.setEmailAddress(onlineAccessDTO.getEmailAddress());
//            result.setErrors(onlineAccessDTO.getErrors());
//            result.setLoginId(onlineAccessDTO.getLoginId());
//            result.setModifiedBy(onlineAccessDTO.getModifiedBy());
//            result.setSecurityQuestion(onlineAccessDTO.getSecurityQuestion());
//            result.setSecurityQuestionAnswer(onlineAccessDTO.getSecurityQuestionAnswer());
//            result.setTagId(onlineAccessDTO.getTagId());
//            result.setDbSessionId(onlineAccessDTO.getDbSessionId());
//            
//        }
//        return result;
//    }
//    
//    private OnlineAccessSetupDTO convertOnlineAccessDtoToDtoPackage(com.etcc.csc.service.types.OnlineAccessSetupDTO onlineAccessDTO) {
//        OnlineAccessSetupDTO result = null;
//        
//        if (onlineAccessDTO != null) {
//            result = new OnlineAccessSetupDTO();
//            result.setAcctId(onlineAccessDTO.getAcctId());
//            result.setAgencyId(onlineAccessDTO.getAgencyId());
//            result.setCompanyTaxId(onlineAccessDTO.getCompanyTaxId());
//            result.setCreatedBy(onlineAccessDTO.getCreatedBy());
//            result.setDriverLicNbr(onlineAccessDTO.getDriverLicNbr());
//            result.setDriverLicState(onlineAccessDTO.getDriverLicState());
//            result.setEmailAddress(onlineAccessDTO.getEmailAddress());
//            result.setLoginId(onlineAccessDTO.getLoginId());
//            result.setModifiedBy(onlineAccessDTO.getModifiedBy());
//            result.setSecurityQuestion(onlineAccessDTO.getSecurityQuestion());
//            result.setSecurityQuestionAnswer(onlineAccessDTO.getSecurityQuestionAnswer());
//            result.setTagId(onlineAccessDTO.getTagId());
//            result.setDbSessionId(onlineAccessDTO.getDbSessionId());
//            result.setErrors(convertWsErrorCollection(onlineAccessDTO.getErrors()));
//            
//        }
//        return result;
//    }
    
//    public com.etcc.csc.service.types.AccountLoginDTO convertToWSForm(AccountLoginDTO acctLoginDto)
//    {
//
//      if (acctLoginDto != null)
//      {
//        com.etcc.csc.service.types.AccountLoginDTO result = 
//          new com.etcc.csc.service.types.AccountLoginDTO();
//        result.setAcctId(acctLoginDto.getAcctId());
//        result.setDbSessionId(acctLoginDto.getDbSessionId());
//        result.setLastLoginIp(acctLoginDto.getLastLoginIp());
//        result.setLoginId(acctLoginDto.getLoginId());
//        result.setAcctStatus(acctLoginDto.getAcctStatus());
//        return result;
//      }
//      return null;
//    }

//    private Collection convertWsErrorCollection(Collection theInput) {
//        if (theInput == null || theInput.size() == 0)
//            return null;
//            
//        Object[] errors = theInput.toArray();
//        ArrayList theOutput = new ArrayList();
//        for (int i = 0; i < errors.length; i++) {
//            theOutput.add(convertWsErrorToErrorMessageDTO(errors[i]));//theOutput.add(convertWsErrorToString(theInput[i])); // or theOutput.add(convertWsErrorToErrorMessageDTO(theInput[i]));
//        }
//        
//        return theOutput;
//    }

//    private Object convertWsErrorToErrorMessageDTO(Object theInput) {
//        if (theInput == null)
//            return null;
//        ErrorMessageDTO theOutput = new ErrorMessageDTO();
//        theOutput.setMessage((String)theInput);
//        return theOutput;
//    }
    
//    private PasswordretrievalWsSoapHttpPortClient stub() throws Exception {
//        PasswordretrievalWsSoapHttpPortClient stub = new PasswordretrievalWsSoapHttpPortClient();
//        stub.setEndpoint(WsClient.getInstance().getWsEndPointContext() + "passwordretrievalWsSoapHttpPort");
//        return stub;
//    }
}