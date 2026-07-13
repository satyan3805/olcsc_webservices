/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.sql.Array;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.service.TagInterface;
import com.etcc.csc.util.StringUtil;

/**
 * @author Milosh Boroyevich
 */
public abstract class TagDAO extends BaseDAO implements TagInterface {
    private static final Logger logger = Logger.getLogger(TagDAO.class);

    protected enum TransactionType {
        UPDATE("U"),
        DELETE("D"),
        ;

        private final String code;

        TransactionType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * Collection of TagAuthorities.
     */
    private static final Collection<TagAuthorityDTO> authorities = new ArrayList<TagAuthorityDTO>();

    /**
     * Tag application agreement per agency (tag authority).
     * <tt>null</tt> key indicates default agreement.
     */
    private static Map<AgencyEnum,String> agreements = new HashMap<AgencyEnum,String>(1);
	private static Map<AgencyEnum,String> agencies =   new HashMap<AgencyEnum,String>(1);

    private static Date lastLoad;
    private static Date expires;

    protected abstract TagDTO modifyTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, TransactionType transType, Long posId) throws EtccException;
    protected abstract Collection<TagAuthorityDTO> loadTagAuthorities() throws EtccException, EtccSecurityException;
    protected abstract String loadTagApplicationAgreement() throws EtccException;
	protected abstract String loadAgencyInfo() throws EtccException;
    protected abstract AlertDTO[] convertAlertMsgs(Array alerts) throws SQLException;
	protected abstract ErrorMessageDTO[] convertErrorMsgs(Array errors) throws SQLException;

    public TagDTO deleteTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId) throws EtccException, EtccSecurityException {
        return modifyTag(acctLoginDTO, tagRequestDto, TransactionType.DELETE, posId);
    }

    public TagDTO updateTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId) throws EtccException, EtccSecurityException {
        return modifyTag(acctLoginDTO, tagRequestDto, TransactionType.UPDATE, posId);
    }

    public Collection<TagAuthorityDTO> getTagAuthorities() throws EtccException, EtccSecurityException {
        expires = checkAndClearCache(expires, authorities);
        if (authorities.isEmpty()){
            if (logger.isDebugEnabled()){
                logger.debug("Last load: " + (lastLoad == null ? "never" : DateFormat.getDateTimeInstance().format(lastLoad)));
            }
            authorities.addAll(loadTagAuthorities());
            lastLoad = new Date();
        }
        return authorities;
	}

	public String getTagApplicationAgreement() throws EtccException {
        expires = checkAndClearCache(expires, agreements);
        if (agreements.isEmpty()){
            if (logger.isDebugEnabled()){
                logger.debug("Last load: " + (lastLoad == null ? "never" : DateFormat.getDateTimeInstance().format(lastLoad)));
            }
            agreements.put(null,loadTagApplicationAgreement());
            lastLoad = new Date();
        }
        return agreements.get(null);
	}
	
	
	public String getAgencyInfo() throws EtccException {
        expires = checkAndClearCache(expires, agencies);
        if (agencies.isEmpty()){
            if (logger.isDebugEnabled()){
                logger.debug("Last load: " + (lastLoad == null ? "never" : DateFormat.getDateTimeInstance().format(lastLoad)));
            }
            agencies.put(null,loadAgencyInfo());
            lastLoad = new Date();
        }
        return agencies.get(null);
	}
	
	
	

	@SuppressWarnings("unused")
	private static void denyTagStatusFlip(TagDTO[] theTags) {
	    if (theTags == null)
	        return;
	    for (int i = 0; i < theTags.length; i++) {
	        TagDTO aTag = theTags[i];
	        if (aTag != null) {
	            aTag.setTagStatusFlip("N");
	            aTag.setPromptAcctClose("N");
	        }
	    }
	}

	protected void setMessages(BaseDTO dto, Array dbErrors, Array dbAlerts, String defaultError) throws EtccException{
		if (dto == null)
			return;
		if (dbErrors != null) {
			try {
				ErrorMessageDTO[] theErrors = convertErrorMsgs(dbErrors);
				if (theErrors == null || theErrors.length == 0) {
					if (!StringUtil.isEmpty(defaultError)) {
						ErrorMessageDTO theDefaultError = new ErrorMessageDTO();
						theDefaultError.setMessage(defaultError);
						theErrors = new ErrorMessageDTO[]{theDefaultError};
					}
				}
				dto.setErrors(theErrors);
			} catch (SQLException e) {
			    throw new EtccException("Unable to read error messages: " + e.getMessage(), e);
			}
		}

		if (dbAlerts != null) {
			try {
				dto.setAlerts(convertAlertMsgs(dbAlerts));
				//Q: Why are we setting errors with alerts?
				//                if (theAlerts != null) {
				//                    for (int i = 0; i < theAlerts.length; i++) {
				//                        if (theAlerts[i] != null) {
				//                            ErrorMessageDTO theOutput = new ErrorMessageDTO();
				//                            theOutput.setMessage(theAlerts[i].getAlertMsg());
				//                            // Do not set key as key is reserved for highlighting feature
				//                            theErrors.add(theOutput);
				//                        }
				//                    }
				//                }
			} catch (SQLException e) {
				logger.error("Failed to convert alert messages", e);
			}
		}

		//        if ((theErrors == null) || (theErrors.size() == 0)) {
		//            if (StringUtils.isEmpty(defaultError) == false) {
		//                ErrorMessageDTO theDefaultError = new ErrorMessageDTO();
		//                theDefaultError.setMessage(defaultError);
		//                theErrors.add(theDefaultError);
		//            }
		//        }
		//        aBaseDto.setErrors(theErrors);
	}

	@SuppressWarnings("unused")
	private static ErrorMessageDTO[] sum(ErrorMessageDTO[] theErrors, AlertDTO[] theAlerts, String defaultErrorMsg) {
	    ArrayList<ErrorMessageDTO> theSum = new ArrayList<ErrorMessageDTO>();
	    if (theErrors != null) {
	        for (int i = 0; i < theErrors.length; i++) {
	            theSum.add(theErrors[i]);
	        }
	    }
	    if (theAlerts != null) {
	        for (int i = 0; i < theAlerts.length; i++) {
	            AlertDTO anAlert = theAlerts[i];
	            if (anAlert != null) {
	                ErrorMessageDTO anError  = new ErrorMessageDTO();
	                anError.setMessage(anAlert.getAlertMsg());
	                theSum.add(anError);
	            }
	        }
	    }
	    if (theSum.size() == 0) {
	        ErrorMessageDTO anError  = new ErrorMessageDTO();
	        anError.setMessage(defaultErrorMsg);
	        theSum.add(anError);
	    }
	    return theSum.toArray(new ErrorMessageDTO[theSum.size()]);
	}
}
