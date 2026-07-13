/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.PersistenceOperation;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.service.AuthorizedContactInterface;

/**
 * @author Milosh Boroyevich
 */
public abstract class AuthorizedContactDAO extends BaseDAO implements AuthorizedContactInterface {
    private static final Logger logger = Logger.getLogger(AuthorizedContactDAO.class);

    protected abstract ResultDTO changeAuthContacts(AccountLoginDTO accountLogin, AuthorizedContactDTO[] authorizedContacts, String acctPassword) throws SQLException, EtccSecurityException, EtccException;
	protected abstract AuthorizedContactDTO[] retrieveAuthContacts(AccountLoginDTO accountLogin) throws SQLException, EtccSecurityException, EtccException;

	/**
	 * This service retrieves the authorized contact for the given account
	 * @param accountLogin the parameter created during login to the account
	 * @return the current authorized contacts. 
	 * If an error occurred, then one AuthorizedContactDTO is returned with
	 * AuthorizedContactDTO.errors being not empty.
	 * @throws EtccSecurityException security breach is detected
	 * @throws EtccException unexpected condition is encountered
	 */
	public AuthorizedContactDTO[] getAuthContacts(AccountLoginDTO accountLogin) throws EtccSecurityException, EtccException {
	    validateAccountLogin(accountLogin);
	    try {
            return retrieveAuthContacts(accountLogin);
        } catch (SQLException e) {
            throw new EtccException(e.getMessage(), e);
        }
	}

	/**
	 * This service sets the authorized contacts during account set-up time.
	 * The service cannot be used once the account is set up.
	 * The existing authorized contacts are overwritten with the give authorized
	 * contacts.
	 * @param accountLogin the parameter created during login to the account
	 * @param authorizedContacts the new authorized contacts
	 * @return error indicator. If BaseDTO.errors is not empty, then the service failed.
	 * @throws EtccSecurityException security breach is detected
	 * @throws EtccException unexpected condition is encountered
	 */
	public ResultDTO setAuthContacts(AccountLoginDTO accountLogin, AuthorizedContactDTO[] authorizedContacts)
			throws EtccSecurityException, EtccException {
		try {
			validateAccountLogin(accountLogin);
			setAction(authorizedContacts, PersistenceOperation.CREATE);
			validateAuthContacts(authorizedContacts);
			AuthorizedContactDTO[] currentAuthContacts = retrieveAuthContacts(accountLogin);
			ErrorMessageDTO[] theErrors = getErrors(currentAuthContacts);
			if (isEmpty(theErrors) == false) {
				ResultDTO errorMsgCarrier = new ResultDTO();
				errorMsgCarrier.setErrors(theErrors);
				return errorMsgCarrier;
			}
			// The current contacts are to be deleted
			setAction(currentAuthContacts, PersistenceOperation.DELETE);
			AuthorizedContactDTO[] allContacts = add(currentAuthContacts, authorizedContacts);
			String acctPwd = null;
			return changeAuthContacts(accountLogin, allContacts, acctPwd);
		} catch (SQLException e) {
			throw new EtccException("setAuthContacts: " + e.getMessage(), e);
		}
	}

	/**
	 * This service creates, changes, deletes authorized contacts.
	 * If AuthorizedContactDTO.action is C, then the contact is created.
	 * If AuthorizedContactDTO.action is U, then the contact is changed.
	 * If AuthorizedContactDTO.action is D, then the contact is deleted.
	 * The service validates the password for the account before changes
	 * are performed.
	 * @param accountLogin the parameter created during login to the account
	 * @param authorizedContacts the authorized contacts to be modified
	 * @param acctPassword is the account password
	 * @return error indicator. If BaseDTO.errors is not empty, then the service failed.
	 * @throws EtccSecurityException security breach is detected
	 * @throws EtccException unexpected condition is encountered
	 */
	public ResultDTO modifyAuthContacts(AccountLoginDTO accountLogin, AuthorizedContactDTO[] authorizedContacts, String acctPassword)
			throws EtccSecurityException, EtccException {
	    validateAccountLogin(accountLogin);
	    validateAuthContacts(authorizedContacts);
	    try {
            return changeAuthContacts(accountLogin, authorizedContacts, acctPassword);
        } catch (SQLException e) {
            throw new EtccException(e.getMessage(), e);
        }
	}

	protected void validateAccountLogin(AccountLoginDTO accountLogin) {
	    if (accountLogin == null) {
	        String msg = "Non-existing (null) account login";
	        logger.error("validateAccountLogin: " + msg);
	        throw new NullPointerException(msg);
	    }
	    //long acctId = accountLogin.getAcctId();
	    //String dbSessionId = accountLogin.getDbSessionId();
	    //String loginType = accountLogin.getLoginType();
	    //String loginId = accountLogin.getLoginId();
	    //String lastLoginIp = accountLogin.getLastLoginIp();
	}

	protected void validateAuthContacts(AuthorizedContactDTO[] authorizedContacts) {
	    if ((authorizedContacts == null) || (authorizedContacts.length == 0))
	        return;
	    for (int i = 0; i < authorizedContacts.length; i++) {
	        validateAuthContact(authorizedContacts[i]);
	    }
	}

    private void validateAuthContact(AuthorizedContactDTO authorizedContactDTO) {
        if (authorizedContactDTO == null)
            return;
        PersistenceOperation operation = authorizedContactDTO.getOperation();
        if (operation != null) {
            switch (operation) {
            case UPDATE:
                // update checks contact id and all CREATE fields
                if (isValidContactId(authorizedContactDTO.getContactId()) == false) {
                    String msg = "Invalid contact ID: " + authorizedContactDTO.getContactId();
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }
            case CREATE:
                if (isValidFirstName(authorizedContactDTO.getFirstName()) == false) {
                    String msg = "Invalid first name: " + authorizedContactDTO.getFirstName();
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }
                if (isValidLastName(authorizedContactDTO.getLastName()) == false) {
                    String msg = "Invalid last name: " + authorizedContactDTO.getLastName();
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }
                if (isValidPassword(authorizedContactDTO.getPassword()) == false) {
                    String msg = "Invalid password: " + authorizedContactDTO.getPassword();
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }
                return;
            case DELETE:
                // delete only checks contact id
                if (isValidContactId(authorizedContactDTO.getContactId()) == false) {
                    String msg = "Invalid contact ID: " + authorizedContactDTO.getContactId();
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }
                return;
            }
        }
        String msg = "Invalid authorized contact action: " + operation.getCode() + ", expected C or U or D";
        logger.error(msg);
        throw new IllegalArgumentException(msg);
    }

	private boolean isValidFirstName(String string) {
	    if (string == null)
	        return false;
	    if (string.length() == 0)
	        return false;
	    if (string.length() > 30) // database column size limitation
	        return false;
	    /*if (string.matches("[a-zA-Z]+") == false)
	        return false;*/
	    return true;
	}

	private boolean isValidLastName(String string) {
	    if (string == null)
	        return false;
	    if (string.length() == 0)
	        return false;
	    if (string.length() > 40) // database column size limitation
	        return false;
	    /*if (string.matches("[a-zA-Z]+") == false)
	        return false;*/
	    return true;
	}

	private boolean isValidPassword(String string) {
	    if (string == null)
	        return false;
	    if (string.length() < 6) // requirement
	        return false;
	    if (string.length() > 12) // requirement
	        return false;
	    if (string.matches("[a-zA-Z0-9]+") == false)
	        return false;
	    return true;
	}

	private boolean isValidContactId(String string) {
	    try {
	        if (string ==  null)
	            return false;
	        if (string.length() == 0)
	            return false;
	        new BigDecimal(string); // probe for conversion
	        return true;
	    } catch (Throwable t) {
	        String msg = "Invalid contact ID: " + string;
	        logger.error(msg + ", " + t.getMessage(), t);
	        return false;
	    }
	}

	protected String toString(AuthorizedContactDTO[] authorizedContacts) {
	    StringBuffer sb = new StringBuffer();
	    sb.append("AuthorizedContactDTO[]=[");
	    if (authorizedContacts == null) {
	        sb.append(NULL_STRING);
	    } else {
	        sb.append("length=" + authorizedContacts.length);
	        for (int i = 0; i < authorizedContacts.length; i++) {
	            sb.append(", [");
	            sb.append(i);
	            sb.append("]=");
	            AuthorizedContactDTO aContact = authorizedContacts[i];
	            if (aContact == null) {
	                sb.append(NULL_STRING);
	            } else {
	                sb.append(aContact.toString());
	            }
	        }
	    }
	    sb.append("]");
	    return sb.toString();
	}

	protected void setAction(AuthorizedContactDTO[] authContacts, PersistenceOperation operation) {
	    if (authContacts != null) {
	        for (int i = 0; i < authContacts.length; i++) {
	            if (authContacts[i] != null)
	                authContacts[i].setOperation(operation);
	        }
	    }
	}

	protected AuthorizedContactDTO[] add(AuthorizedContactDTO[] authContacts1, AuthorizedContactDTO[] authContacts2) {
	    if (authContacts1 == null)
	        authContacts1 = new AuthorizedContactDTO[0];
	    if (authContacts2 == null)
	        authContacts2 = new AuthorizedContactDTO[0];
	    ArrayList<AuthorizedContactDTO> theOutput = new ArrayList<AuthorizedContactDTO>(authContacts1.length + authContacts2.length);
	    for (int i = 0; i < authContacts1.length; i++) {
	        theOutput.add(authContacts1[i]);
	    }
	    for (int i = 0; i < authContacts2.length; i++) {
	        theOutput.add(authContacts2[i]);
	    }
	    return theOutput.toArray(new AuthorizedContactDTO[theOutput.size()]);
	}

	protected ErrorMessageDTO[] getErrors(AuthorizedContactDTO[] currentAuthContacts) {
	    if (currentAuthContacts ==  null)
	        return null;
	    if (currentAuthContacts.length == 1) {
	        if (currentAuthContacts[0].getErrors() != null) {
	            if (currentAuthContacts[0].getErrors().length > 0) {
	                if (currentAuthContacts[0].getErrors()[0] != null) {
	                    return currentAuthContacts[0].getErrors();
	                } else {
	                    ErrorMessageDTO anError = new ErrorMessageDTO();
	                    anError.setMessage("Operation failed");
	                    return new ErrorMessageDTO[] { anError };
	                }
	            }
	        }
	    }
	    return null;
	}

	protected void logErrors(BaseDTO anErrorCarrier) {
	    if (anErrorCarrier != null) {
	        ErrorMessageDTO[] theErrors = anErrorCarrier.getErrors();
	        if ((theErrors != null) && (theErrors.length > 0)) {
	            for (int i = 0; i < theErrors.length; i++) {
	                if (theErrors[i] != null) {
	                    logger.error("Found error: " + theErrors[i].getMessage());
	                }
	            }
	        }
	    }
	}

	protected boolean isEmpty(Object[] anArray) {
	    return ((anArray == null) || (anArray.length == 0));
	}

	protected String docType(AccountLoginDTO.LoginType loginType) {
	    if (loginType == null)
	    	loginType = AccountLoginDTO.LoginType.AC;
	    return loginType.toString();
	}
}
