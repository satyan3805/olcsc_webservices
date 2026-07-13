/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.validation;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.util.StringUtil;

public abstract class PasswordRetrievalValidator extends BaseValidator {


    public static void changePasswordValidator(AccountLoginDTO acctLoginDTO, 
                                               String oldPwd, 
                                               String pwd) throws EtccException {
    
        PasswordValidator.validateLoginId(acctLoginDTO.getLoginId());
        PasswordValidator.validateAccountId(acctLoginDTO.getAcctId());
        PasswordValidator.validatePassword(oldPwd);
        PasswordValidator.validatePassword(pwd);
        PasswordValidator.compareTwoStringsIgnoreCase(oldPwd,pwd);
        

    }


    public static void retrieveAccountInfoValidator(OnlineAccessSetupDTO dto, 
                                                    String ip, 
                                                    String sessionId, 
                                                    UserEnvDTO userEnvDto) throws EtccException {

        if ((dto == null || StringUtil.isEmpty(ip) || StringUtil.isEmpty(sessionId) || 
             userEnvDto == null)) {
            throw new EtccException("Please provide data.");
        }
        //here I assume if dto.getAcctId()==0, it means "user does not input any value
        if ((StringUtil.isEmpty(dto.getTagId())) && ((dto.getAcctId() == 0))) {
            throw new EtccException("Please provide a Account Number or TollTag Number.");
        } else if ((StringUtil.isEmpty(dto.getDriverLicNbr())) && 
                   (StringUtil.isEmpty(dto.getCompanyTaxId()))) {
            throw new EtccException("Please provide an Driver License Number or Tax ID.");
        } else if ((StringUtil.isEmpty(dto.getAgencyId())) || 
                   (StringUtil.isEmpty(dto.getDriverLicState()))) {
            throw new EtccException("Please provide an Agency ID or Driver License State.");
        }

        if (dto.getAcctId()==0){
            throw new EtccException("0 is not a validate account number.");
        }

        if (!StringUtil.isEmpty(dto.getTagId())) {
            if (!isLong(dto.getTagId() + "")) {
                throw new EtccException("EZ TAG Number must be a number.");
            }
        }

        if (!StringUtil.isEmpty(dto.getCompanyTaxId())) {
            if (!isLong(dto.getCompanyTaxId() + "")) {
                throw new EtccException("Tax ID Number must be a number.");
            } else if (dto.getCompanyTaxId().length() < 9) {
                throw new EtccException("Tax ID Number can not be less than 9 characters.");
            } else if (dto.getCompanyTaxId().length() > 11) {
                throw new EtccException("Tax ID Number can not be greater than 11 characters.");
            }
        }

        if (!DriverLicValidator.validateDriverLic(dto.getDriverLicState(), 
                                                  dto.getDriverLicNbr())) {
            throw new EtccException("Invalidate Driver License Number.");
        }
    }

    public static void validateSecurityAnswerValidator(String sessionId, 
                                                       String ip, 
                                                       OnlineAccessSetupDTO dto) throws EtccException {

        /*
         *note: OnlineAccessSetupDTO has some params: loginId="OLCSC_ANONYMOUS",acctId,Sqanswer
         */
        if ((dto == null || StringUtil.isEmpty(ip) || StringUtil.isEmpty(sessionId))) {
            throw new EtccException("Please provide data.");
        }

        if (dto.getAcctId() == 0) {
            throw new EtccException("0 is not a validate account number.");
        }

        if (StringUtil.isEmpty(dto.getSecurityQuestionAnswer())) {
            throw new EtccException("Please provide a Your Answer.");
        }
    }


    public static void addEmailAddressValidator(String sessionId, String ip, 
                                                OnlineAccessSetupDTO dto) throws EtccException {
        if ((dto == null || StringUtil.isEmpty(ip) || StringUtil.isEmpty(sessionId))) {
            throw new EtccException("Please provide data.");
        }

        if (dto.getAcctId() == 0) {
            throw new EtccException("0 is not a validate account number.");
        }

        if (StringUtil.isEmpty(dto.getEmailAddress())) {
            throw new EtccException("Please provide your email address.");
        }

        if (!isValidEmail(dto.getEmailAddress())) {
            throw new EtccException("Invalidate email address.");
        }
    }

    public static void addSQAnswerValidator(String sessionId, String ip, 
                                            OnlineAccessSetupDTO dto) throws EtccException {
        if ((dto == null || StringUtil.isEmpty(ip) || StringUtil.isEmpty(sessionId))) {
            throw new EtccException("Please provide data.");
        }
        String answer = dto.getSecurityQuestionAnswer();
        String RegExp = "^[0-9a-zA-Z?.,\"'\\s]*$";

        if (dto.getAcctId() == 0) {
            throw new EtccException("0 is not a validate account number.");
        }

        if (StringUtil.isEmpty(answer)) {
            throw new EtccException("Security answer is required.");
        } else if ((!validateMaxLength(answer.length(), 20)) || 
                   (!answer.matches(RegExp))) {
            throw new EtccException("Invalid Security Question Answer.");
        }
    }
}
