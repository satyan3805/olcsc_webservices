package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.List;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;

/**
 * Defines methods that need to be implemented by the State classes.
 */
public interface SecurityQuestionInterface extends BusinessObjectInterface {
	/**
	 * Retrieves the collection of states for the default country.
	 * 
	 * @return
	 */
	ArrayList getSecurityQuestions(String locale) throws EtccException,
			EtccSecurityException;

	ArrayList getSecurityQuestionsnAnswers(AccountLoginDTO acctLoginDto,
			String locale) throws EtccException;

	String getSysParam(String paramName) throws EtccException;

	int saveAnswers(AccountLoginDTO acctLoginDto,
			List<SecurityQuestionDTO> listOfQuestionsnAnswers)
			throws EtccException;
}