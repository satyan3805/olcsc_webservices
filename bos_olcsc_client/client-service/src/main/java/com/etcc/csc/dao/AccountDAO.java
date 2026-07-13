/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.Address;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.service.AccountInterface;

/**
 * Base class for the Accounts.  Copied from com.etcc.csc.dao.AccountDAO/NewAccountDAO in OLCSCService
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public abstract class AccountDAO extends BaseDAO implements AccountInterface {
    private static final Logger logger = Logger.getLogger(AccountDAO.class);

    @SuppressWarnings("deprecation")
    public AccountLoginDTO updateMailingAddress(AccountLoginDTO acctLogin, Address address)
            throws EtccSecurityException, EtccException {
        return this.updateMailingAddr(
                Long.toString(acctLogin.getAcctId()),
                AccountLoginDTO.LoginType.AC.toString(),
                acctLogin.getDbSessionId(),
                acctLogin.getLastLoginIp(),
                acctLogin.getLoginId(),
                address.getAddress1(),
                address.getAddress2(), 
                address.getAddress3(),
                address.getAddress4(),
                address.getCity(),
                address.getState(),
                address.getCountry(), 
                address.getZip(),
                address.getPlus4());
    }

    @SuppressWarnings("deprecation")
    public AccountLoginDTO updateBillingAddress(AccountLoginDTO acctLogin, Address address)
            throws EtccException, EtccSecurityException {
        return updateBillingAddress(
                Long.toString(acctLogin.getAcctId()),
                AccountLoginDTO.LoginType.AC.toString(),
                acctLogin.getDbSessionId(), acctLogin.getLastLoginIp(),
                acctLogin.getLoginId(), address.getAddress1(),
                address.getAddress2(), address.getAddress3(),
                address.getAddress4(), address.getCity(),
                address.getState(), address.getZip(), address.getPlus4(),
                address.getCountry());
    }

    /**
     * @deprecated use {@link #updatePasswordEmailSecurityQA(AccountLoginDTO, int, String, String, String, int, String)}
     */
	@SuppressWarnings("deprecation")
	@Deprecated
	public final AccountLoginDTO updatePassword(String sessionID, String ipAddress, String loginId, String oldPassword,
            String password, String acctID) throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("Use updatePasswordEmailSecurityQA.");
	}

    /**
     * @deprecated use {@link #updatePasswordEmailSecurityQA(AccountLoginDTO, int, String, String, String, int, String)}
     */
	@SuppressWarnings("deprecation")
	@Deprecated
	public final AccountLoginDTO updateSecQn(String sessionID, String ipAddress, String loginId, String password,
            String acctID, int securityQnID, String securityAns) throws EtccException, EtccSecurityException {
        throw new UnsupportedOperationException("Use updatePasswordEmailSecurityQA.");
	}


	//Default implementation should be moved here.

	/**
	 * @deprecated Stub only. This is implemented in Account as a call to Session.
	 * @param acctId
	 * @param ipAddress
	 * @return null.
	 * @throws EtccException
	 * @see com.etcc.csc.service.Account#createSession(long, String)
	 */
	@Deprecated
	public final AccountLoginDTO createSession(long acctId, String ipAddress) throws EtccException {
	    return null;
	}

	protected String getReserveCardFlag(BillingInfoDTO billingInfo) {
	    String result = "N";
	    if (billingInfo.getBillingType() == PaymentType.CREDIT) {
	        for (AccountCreditCardDTO card : billingInfo.getCards()) {
	            if (!card.isPrimary()) {
	                result = "Y";
	                break;
	            }
	        }
	    }
	    return result;
	}

	protected boolean convertYN(String[] ynStr) {
	    if ((ynStr == null) || (ynStr.length == 0)) {
	        logger.debug("convertYN.ynStr does not exist or is empty");
	        return false;
	    }
	    if (ynStr[0] == null) {
	        logger.debug("convertYN.ynStr[0] does not exist");
	        return false;
	    }
	    if (logger.isDebugEnabled())
	        logger.debug("convertYN.ynStr[0]=" + ynStr[0]);
	    return ynStr[0].toUpperCase().startsWith("Y");
	}
}
