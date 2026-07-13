/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AuthorizedContactDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.PersistenceOperation;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.plsql.OLC_AUTH_CONTACT_ARR;
import com.etcc.csc.plsql.OLC_AUTH_CONTACT_REC;
import com.etcc.csc.plsql.OLC_ERROR_MSG_ARR;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;
import com.etcc.csc.plsql.OLCSC_AUTH_CONTACTS;

public class OracleAuthorizedContactDAO extends AuthorizedContactDAO {
    private static final Logger logger = Logger.getLogger(OracleAuthorizedContactDAO.class);

    @Override
	protected AuthorizedContactDTO[] retrieveAuthContacts(AccountLoginDTO accountLogin)
    		throws SQLException, EtccSecurityException, EtccException {
        long acctId = accountLogin.getAcctId();
        String docType = docType(accountLogin.getLoginType());
        String dbSessionId = accountLogin.getDbSessionId();
        String lastLoginIp = accountLogin.getLastLoginIp();
        String loginId = accountLogin.getLoginId();
        boolean uiCall = true; // as a consequence database validates input
        OLC_AUTH_CONTACT_ARR[] P_AUTH_CONTACTS = new OLC_AUTH_CONTACT_ARR[] { new OLC_AUTH_CONTACT_ARR() };
        OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
        final boolean debugEnabled = logger.isDebugEnabled();
        if (debugEnabled)
            logger.debug("olcsc_auth_contacts(conn).GET_AUTH_CONTACTS(acctId=" + acctId + ", docType=" + docType + ", dbSessionId=" + dbSessionId +
                ", lastLoginIp=" + lastLoginIp + ", loginId=" + loginId + ", uiCall=" + uiCall);
        BigDecimal ret = new OLCSC_AUTH_CONTACTS(this.conn).GET_AUTH_CONTACTS(new BigDecimal(acctId), docType, dbSessionId, lastLoginIp, loginId, uiCall,
            P_AUTH_CONTACTS, O_ERROR_MSG_ARR);
        if (debugEnabled)
            logger.debug("OLCSC_AUTH_CONTACTS(conn).GET_AUTH_CONTACTS()=" + ret);
        int result = ret.intValue();
        if (result == -1) {
            throw new EtccSecurityException("Authorized contact read operation is blocked");
        } else if (result == 0) { // errors
            OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
            AuthorizedContactDTO errorMsgCarrier = new AuthorizedContactDTO();
            errorMsgCarrier.setErrors(OracleUtil.convertToMessages(errorArray));
            AuthorizedContactDTO[] errorMsgCollector = new AuthorizedContactDTO[1];
            errorMsgCollector[0] = errorMsgCarrier;
            logErrors(errorMsgCarrier);
            return errorMsgCollector;
        } else {
            OLC_AUTH_CONTACT_REC[] authContacts = P_AUTH_CONTACTS[0].getArray();
            return toAuthorizedContactDTOArray(authContacts);
        }
    }

    @Override
    protected ResultDTO changeAuthContacts(AccountLoginDTO accountLogin, AuthorizedContactDTO[] authorizedContacts, String acctPassword)
    		throws SQLException, EtccSecurityException, EtccException {
        long acctId = accountLogin.getAcctId();
        String docType = docType(accountLogin.getLoginType());
        String dbSessionId = accountLogin.getDbSessionId();
        String lastLoginIp = accountLogin.getLastLoginIp();
        String loginId = accountLogin.getLoginId();
        String loginPwd = acctPassword;
        if (loginPwd == null)
            loginPwd = "";
        //boolean uiCall = true;
        /*OLC_AUTH_CONTACT_ARR[] P_AUTH_CONTACTS = new OLC_AUTH_CONTACT_ARR[] { toOLC_AUTH_CONTACT_ARR(authorizedContacts) }; //{ new OLC_AUTH_CONTACT_ARR() };*/
        OLC_AUTH_CONTACT_ARR P_AUTH_CONTACTS = toOLC_AUTH_CONTACT_ARR(authorizedContacts);
        OLC_ERROR_MSG_ARR[] O_ERROR_MSG_ARR = new OLC_ERROR_MSG_ARR[] { new OLC_ERROR_MSG_ARR() };
        if (logger.isDebugEnabled())
            logger.debug("OLCSC_AUTH_CONTACTS(conn).MODIFY_AUTH_CONTACTS(new BigDecimal(acctId=" + acctId + "), docType=" + docType +
                ", dbSessionId=" + dbSessionId + ", lastLoginIp=" + lastLoginIp + ", loginId=" + loginId + ", loginPwd=" + "****" +
                ", P_AUTH_CONTACTS=" + toString(authorizedContacts/*P_AUTH_CONTACTS*/));
        BigDecimal ret = new OLCSC_AUTH_CONTACTS(conn).MODIFY_AUTH_CONTACTS(new BigDecimal(acctId), docType, dbSessionId, lastLoginIp, loginId, loginPwd,
            P_AUTH_CONTACTS, O_ERROR_MSG_ARR);
        int result = ret.intValue();
        if (result == -1) {
            throw new EtccSecurityException("Authorized contact change operation is blocked");
        } else if (result == 0) { // errors
            OLC_ERROR_MSG_REC[] errorArray = O_ERROR_MSG_ARR[0].getArray();
            ResultDTO errorMsgCarrier = new ResultDTO();
            errorMsgCarrier.setErrors(OracleUtil.convertToMessages(errorArray));
            logErrors(errorMsgCarrier);
            return errorMsgCarrier;
        } else {
            ResultDTO successCarrier = new ResultDTO();
            successCarrier.setErrors((ErrorMessageDTO[])null); // success indicator
            return successCarrier;
        }
    }
    
//    private ErrorMessageDTO[] toErrorMessageDTOArray(OLC_ERROR_MSG_REC[] errorArray) throws EtccException, SQLException {
//        if ((errorArray == null) || (errorArray.length == 0)) {
//            String msg = "Database error message does not match return value";
//            logger.error("toErrorMessageDTO: " + msg);
//            throw new EtccException(msg);
//        }
//        ErrorMessageDTO[] result = new ErrorMessageDTO[errorArray.length];
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < errorArray.length; i++) {
//            sb.append("toErrorMessageDTOArray.errorArray[").append(i).append("]=").append(errorArray[i].getERROR_MSG());
//            ErrorMessageDTO anErrorMessageDTO = new ErrorMessageDTO();
//            String aKey = OracleUtil.getFieldMapping(errorArray[i].getERROR_MSG());
//            anErrorMessageDTO.setKey(aKey);
//            sb.append(", toErrorMessageDTOArray.aKey=").append(aKey);
//            String aMsg = OracleUtil.trimErrorMessage(errorArray[i].getERROR_MSG());
//            anErrorMessageDTO.setMessage(aMsg);
//            sb.append(", toErrorMessageDTOArray.aMsg=").append(aMsg);
//            result[i] = anErrorMessageDTO;
//        }
//        if (logger.isDebugEnabled()) {
//            logger.debug(sb.toString());
//        }
//        return result;
//    }

    private AuthorizedContactDTO[] toAuthorizedContactDTOArray(OLC_AUTH_CONTACT_REC[] authContacts) throws SQLException {
        if ((authContacts == null) || (authContacts.length == 0))
            return new AuthorizedContactDTO[0];
        ArrayList<AuthorizedContactDTO> theOutput = new ArrayList<AuthorizedContactDTO>(authContacts.length);
        for (int i = 0; i < authContacts.length; i++) {
            theOutput.add(toAuthorizedContactDTO(authContacts[i]));
        }
        return theOutput.toArray(new AuthorizedContactDTO[theOutput.size()]);
    }

    private AuthorizedContactDTO toAuthorizedContactDTO(OLC_AUTH_CONTACT_REC authContact) throws SQLException {
        if (authContact == null)
            return null;
        AuthorizedContactDTO anAuthorizedContactDTO = new AuthorizedContactDTO();
        anAuthorizedContactDTO.setContactId(authContact.getAUTH_CONTACT_ID().toString());
        anAuthorizedContactDTO.setFirstName(authContact.getFIRST_NAME());
        anAuthorizedContactDTO.setLastName(authContact.getLAST_NAME());
        anAuthorizedContactDTO.setPassword(authContact.getAUTH_CONTACT_PASSWORD());
        return anAuthorizedContactDTO;
    }

    private OLC_AUTH_CONTACT_ARR toOLC_AUTH_CONTACT_ARR(AuthorizedContactDTO[] authorizedContacts) throws SQLException {
        OLC_AUTH_CONTACT_ARR aContainer = new OLC_AUTH_CONTACT_ARR();
        if (authorizedContacts == null) {
            aContainer.setArray(new OLC_AUTH_CONTACT_REC[0]);
        } else {
            OLC_AUTH_CONTACT_REC[] theOutput = new OLC_AUTH_CONTACT_REC[authorizedContacts.length];
            for (int i = 0; i < theOutput.length; i++) {
                if (authorizedContacts[i] != null)
                    theOutput[i] = toOLC_AUTH_CONTACT_REC(authorizedContacts[i]);
            }
            aContainer.setArray(theOutput);
        }
        return aContainer;
    }
    
    private OLC_AUTH_CONTACT_REC toOLC_AUTH_CONTACT_REC(AuthorizedContactDTO authorizedContact) throws SQLException {
        if (authorizedContact == null)
            return null;
        OLC_AUTH_CONTACT_REC theOutput = new OLC_AUTH_CONTACT_REC();
        theOutput.setACTION_FLAG(authorizedContact.getAction());
        theOutput.setAUTH_CONTACT_ID(new BigDecimal("0"));
        PersistenceOperation operation = authorizedContact.getOperation();
        if (operation == PersistenceOperation.DELETE || operation == PersistenceOperation.UPDATE)
            theOutput.setAUTH_CONTACT_ID(new BigDecimal(authorizedContact.getContactId()));
        theOutput.setAUTH_CONTACT_PASSWORD(authorizedContact.getPassword());
        theOutput.setFIRST_NAME(authorizedContact.getFirstName());
        theOutput.setLAST_NAME(authorizedContact.getLastName());
        return theOutput;
    }
}
