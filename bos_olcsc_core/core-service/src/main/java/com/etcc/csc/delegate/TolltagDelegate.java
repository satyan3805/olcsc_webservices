package com.etcc.csc.delegate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.datatype.OlcMarketSourceRec;
import com.etcc.csc.datatype.PersonalInfoRequest;
import com.etcc.csc.datatype.PersonalInfoResponse;
import com.etcc.csc.dto.AccountCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.service.TollTagInterface;


public class TolltagDelegate extends Delegate implements TollTagInterface{

    TollTagInterface tti = (TollTagInterface)getServiceObject(ServiceObjectEnum.TOLLTAG);
    public TolltagDelegate() {
        super(TolltagDelegate.class);
    }


    public AccountLoginDTO createAccount(String ipAddress,
			String sessionId, 
			String browserType,
			String browserVersion,
			String os_type,
			String osVersion,
			String attribute1,
			String attribute2,
			String attributes3,
			String attribute4,
			String attribute5,
			String accountType,
		String firstName,
		String middleInitial,
		String lastName, 
             String taxId, 
             String email, 
             String loginId,
             String password,
             List<SecurityQuestionDTO> listOfQuestionsnAnswers,
             String locale,
            String planCode,
            String eventId,String startDate,String endDate,String companyName) throws Exception {
        return tti.createAccount(ipAddress,
   			 sessionId, 
			 browserType,
			 browserVersion,
			 os_type,
			 osVersion,
			 attribute1,
			 attribute2,
			 attributes3,
			 attribute4,
			 attribute5,
			 accountType,
		 firstName,
		 middleInitial,
		 lastName, 
              taxId, 
              email, 
              loginId,
              password,
              listOfQuestionsnAnswers,
              locale,
             planCode,
             eventId, startDate, endDate, companyName);
    }// end of createAccount()


    public PersonalInfoResponse setPersonalInfo(PersonalInfoRequest pir) throws EtccErrorMessageException, 
                                                                   Exception{
        return tti.setPersonalInfo(pir);
    }// end of setPersonalInfo()
     
    public void setPaymentInfo(long acctId, String dbSessionId, 
            String ipAddress, String loginId, 
            long personId,String cardNumber, long cardType,
            String token,Calendar expirationDate,long eventId) throws EtccErrorMessageException,
                                                             Exception {
         tti.setPaymentInfo(acctId,dbSessionId, 
                ipAddress, loginId, 
                 personId,cardNumber,cardType,
                 token, expirationDate, eventId);
    }// end of setPaymentInfo()
     
    public OlcMarketSourceRec[] getMarketSource(String lang) throws Exception{
        return tti.getMarketSource(lang);
    }// end of getMarketSource()
    
    public double getTagPayInfo(long accountId, String dbSessionId, 
                                         String loginId, String ipAddress, 
                                         long transactionId, Long posId) 
                                         throws EtccErrorMessageException, Exception{
        return tti.getTagPayInfo(accountId, dbSessionId, loginId, ipAddress,
                                    transactionId, posId);
    }// end of getTagPayInfo()
    
    public String loginCreation(BigDecimal accountId, String loginType, 
                                       String dbSessionId, String loginId, String password,
                                       String ipAddress, String firstName, 
                                       String middleInitial, String lastName, 
                                       String email) throws EtccErrorMessageException, 
                                                            Exception{
        return tti.loginCreation(accountId, loginType, dbSessionId, loginId,password, ipAddress,
                        firstName, middleInitial, lastName, email);
    }// end of loginCreation()
    
    public ArrayList<AccountCardDTO> getTollRemoveReasonList(AccountLoginDTO acctLoginDto) throws EtccErrorMessageException, 
         Exception{
    	return tti.getTollRemoveReasonList(acctLoginDto);
    }// end of getTollRemoveReasonList()
    
    public  Collection removeTag(AccountLoginDTO acctLoginDto,String tagId,String rsnCode,String flag) throws EtccErrorMessageException, 
    	Exception{
    	return tti.removeTag( acctLoginDto, tagId, rsnCode, flag);
    }// end of removeTag()
    
    public int validateVehicleInfo(AccountLoginDTO acctLoginDto, TagDTO tags) throws EtccException, EtccSecurityException, IOException{
    	return tti.validateVehicleInfo(acctLoginDto,  tags);
    }// end of validateVehicleInfo()
    
    public Collection getAccountVehicles(AccountLoginDTO accountLoginDTO, String includeTag) 
    	throws EtccException, EtccSecurityException{
    	return tti.getAccountVehicles(accountLoginDTO, includeTag);
    }// end of getAccountVehicles()


	public void createShiftEditsRecord(Long posId, String editItemTypeCode,
			String activityCode) throws EtccException {
		// TODO Auto-generated method stub
		tti.createShiftEditsRecord(posId, editItemTypeCode, activityCode);
		
	}

}// end of TolltagDelegate Class
