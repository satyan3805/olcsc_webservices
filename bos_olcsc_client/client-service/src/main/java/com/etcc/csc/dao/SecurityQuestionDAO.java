/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.service.SecurityQuestionInterface;

/**
 * DAO for Security Questions. These rarely change, so the data is cached, with
 * the cache set to expire at midnight. This is reloaded immediately the first
 * time it is hit after application startup.
 */
// TODO: JMX Bean - set this up to query/use JMX Bean for cache access/control
public abstract class SecurityQuestionDAO extends BaseDAO implements SecurityQuestionInterface {
    private static final Logger logger = Logger.getLogger(SecurityQuestionDAO.class);
    
    /**
     * The list of Security Questions.
     */
    protected static final Collection<SecurityQuestionDTO> questions = new ArrayList<SecurityQuestionDTO>();

    protected static Calendar lastLoad;
    /**
     * When does the cache expire.
     */
    protected static Date expires;

    /**
     * Loads the Security Questions from the Database.
     * @return The current Security Questions from the DB.
     */
    protected abstract Collection<SecurityQuestionDTO> loadSecurityQuestions() throws EtccException;

    public Collection<SecurityQuestionDTO> getSecurityQuestions() throws EtccException{
        expires = checkAndClearCache(expires, questions);
        if (questions.size() == 0){
            questions.addAll(loadSecurityQuestions());
            lastLoad = Calendar.getInstance();
            logger.info("Reloaded Security questions at: " + DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(lastLoad));
        }
        return questions;
    }
}// end SecurityQuestionDAO.
