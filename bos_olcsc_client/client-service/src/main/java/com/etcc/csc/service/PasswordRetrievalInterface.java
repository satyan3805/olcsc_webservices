/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OnlineAccessSetupDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.UserEnvDTO;

import java.util.Collection;

import javax.ejb.Local;

@Local
public interface PasswordRetrievalInterface extends ServiceInterface {
    
    /**
     * @param onlineAccessDTO
     * @param ipAddress
     * @return Returns OnlineAccessSetupDTO containing the user's online access account info. 
     * null indicates that the account does not exist.
     * @throws EtccException
     * @throws EtccSecurityException
     */
     public OnlineAccessSetupDTO retrieveAccountInfo(OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,String sessionId, UserEnvDTO userEnvDto) throws EtccException, EtccSecurityException;

     /**
      * @param onlineAccessDTO
      * @param ipAddress
      * @return Returns OnlineAccessSetupDTO containing the user's online access account info. 
      * null indicates that the account does not exist.
      * @throws EtccException
      * @throws EtccSecurityException
      */
      public OnlineAccessSetupDTO retrieveEmailAddressInfo(OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,String sessionId, UserEnvDTO userEnvDto) throws EtccException, EtccSecurityException;

      /**
       * @param onlineAccessDTO
       * @param ipAddress
       * @return Returns OnlineAccessSetupDTO containing the user's online access account info. 
       * null indicates that the account does not exist.
       * @throws EtccException
       * @throws EtccSecurityException
       */
       public OnlineAccessSetupDTO validationData(OnlineAccessSetupDTO onlineAccessDTO, String ipAddress,String sessionId, UserEnvDTO userEnvDto) throws EtccException, EtccSecurityException;
    /**
     * @param sessionId
     * @param ipAddress
     * @param onlineAccessDTO
     * @return
     * @throws EtccException
     * @throws EtccSecurityException
     */

    public OnlineAccessSetupDTO validateSecurityAnswer(String sessionId, String ipAddress, OnlineAccessSetupDTO onlineAccessDTO) throws EtccException, EtccSecurityException;

    /**
     * @param sessionId
     * @param ipAddress
     * @param onlineAccessDTO
     * @return
     * @throws EtccException
     * @throws EtccSecurityException
     */
    public OnlineAccessSetupDTO addSecurityQuestionAnswer(String sessionId, String ipAddress, OnlineAccessSetupDTO onlineAccessDTO) throws EtccException, EtccSecurityException;

    /**
     * @param sessionId
     * @param ipAddress
     * @param onlineAccessDTO
     * @return
     * @throws EtccException
     * @throws EtccSecurityException
     */
    public OnlineAccessSetupDTO addEmailAddress(String sessionId, String ipAddress, OnlineAccessSetupDTO onlineAccessDTO) throws EtccException, EtccSecurityException;


    /**
     * @param acctLoginDTO
     * @param pwd
     * @return A collection of error message Strings. Null or empty means the operation is successful. These error 
     * messages are from the validation process that are performed at the server.
     * @throws EtccException
     * @throws EtccSecurityException
     */
    //Note: The interface was originally defined as returning a collection of ErrorMessageDTOs, but the implementations
    //only ever returned a Collection of Strings.  -SPD 11/23/2009
    public Collection<String> changePassword(AccountLoginDTO acctLoginDTO, String oldPwd, String pwd) throws EtccException, EtccSecurityException;

	
}
