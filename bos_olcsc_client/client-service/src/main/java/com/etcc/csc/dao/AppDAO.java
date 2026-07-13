/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.service.AppInterface;

/**
 * DAO to get the system parameters from the database.  Cache's the parameters in the local cache.  Currently, the
 * cache unloads at midnight or on restart, and then reloads "on-demand".<br />
 * Unlike many of the other DAO's, this class does NOT implement the service interface.
 * @author Stephen Davidson
 * @see AppInterface
 */
//TODO: JMX Bean - load from cache.
public abstract class AppDAO extends BaseDAO {

//    private Logger logger = Logger.getLogger(AppDAO.class);

    private static Date expires = new Date(); //Expire immediately.

    private static Map<String, String> params = new HashMap<String, String>();

    protected abstract String loadParam(final String paramName) throws EtccException;

    /**
     * @see AppInterface#contactUs(long, String, String, String, String, String, String)
     */
    public abstract boolean contactUs(long docId, String docType, String licState, String licPlate, String replyAddress, String comment, String dbSessionId) throws EtccException;

    /**
     * @see AppInterface#getVeaText(AccountLoginDTO, Invoice[])
     */
    public abstract String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices)  throws EtccException;

    /**
     * @see AppInterface#getPOSId(String)
     */
    public abstract Long getPOSId(String url) throws EtccException;

    /**
     * Gets the specified system param from the database.  First checks the cache to see if the param is present there,
     * and if not, then loads from the database.
     *
     * @param paramName
     * @return
     * @throws EtccException
     */
    public String getSysParam(String paramName) throws EtccException{
        expires = checkAndClearCache(expires, params);
        String paramValue = params.get(paramName);
        if (paramValue == null){
            paramValue = loadParam(paramName);
            params.put(paramName, paramValue);
        }
        return paramValue;
    }

    public abstract  Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDTO,Long eventId) throws EtccException;

    public abstract Long  getAccountBillingMethodIdByToken(long token) throws EtccException ;

	public abstract Long getAcctVehicleId(long accid, String lic_plate_number,String lic_plate_state) throws EtccException ;

	public abstract Long getInvoiceId(String invoiceNum) throws EtccException;
	public abstract Double recalculateAutochargeAndSave(Long accountId, String userName) throws EtccException;
	//QC_10261 changes start here
	public abstract String getPdfFilepath(String invoiceNum) throws EtccException;
	//Express AccountChnages
	public abstract boolean isExpressAccount(Long accountId) throws EtccException;
	//Track Suspicious 
	public abstract String getRegExp(String userValue) throws EtccException;
}
