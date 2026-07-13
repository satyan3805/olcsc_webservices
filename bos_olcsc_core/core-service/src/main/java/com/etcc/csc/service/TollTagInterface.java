package com.etcc.csc.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.datatype.OlcMarketSourceRec;
import com.etcc.csc.datatype.PersonalInfoRequest;
import com.etcc.csc.datatype.PersonalInfoResponse;
import com.etcc.csc.dto.AccountCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.dto.TagDTO;

public interface TollTagInterface extends BusinessObjectInterface {
	public AccountLoginDTO createAccount(String ipAddress, String sessionId,
			String browserType, String browserVersion, String os_type,
			String osVersion, String attribute1, String attribute2,
			String attributes3, String attribute4, String attribute5,
			String accountType, String firstName, String middleInitial,
			String lastName, String taxId, String email, String loginId,
			String password, List<SecurityQuestionDTO> listOfQuestionsnAnswers,
			String locale, String planCode, String eventId, String startDate,
			String endDate, String companyName) throws Exception;

	public PersonalInfoResponse setPersonalInfo(PersonalInfoRequest pir)
			throws EtccErrorMessageException, Exception;

	public void setPaymentInfo(long acctId, String dbSessionId,
			String ipAddress, String loginId, long personId, String cardNumber,
			long cardType, String token, Calendar expirationDate, long eventId)
			throws EtccErrorMessageException, Exception;

	public OlcMarketSourceRec[] getMarketSource(String lang) throws Exception;

	public double getTagPayInfo(long accountId, String dbSessionId,
			String loginId, String ipAddress, long transactionId, Long posId)
			throws EtccErrorMessageException, Exception;

	public String loginCreation(BigDecimal accountId, String loginType,
			String dbSessionId, String loginId, String password,
			String ipAddress, String firstName, String middleInitial,
			String lastName, String email) throws EtccErrorMessageException,
			Exception;

	public ArrayList<AccountCardDTO> getTollRemoveReasonList(
			AccountLoginDTO acctLoginDto) throws EtccErrorMessageException,
			Exception;

	public Collection removeTag(AccountLoginDTO acctLoginDto, String tagId,
			String rsnCode, String flag) throws EtccErrorMessageException,
			Exception;

	public int validateVehicleInfo(AccountLoginDTO acctLoginDto, TagDTO tags)
			throws EtccException, EtccSecurityException, IOException;

	public Collection getAccountVehicles(AccountLoginDTO accountLoginDTO,
			String includeTag) throws EtccException, EtccSecurityException;
	
	void createShiftEditsRecord(Long posId, String editItemTypeCode,
			String activityCode) throws EtccException; 
}
