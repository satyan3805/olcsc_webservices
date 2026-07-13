/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.AppDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;
import com.etcc.csc.util.StringUtil;


/**
 * Oracle specific implementation of the AppDAO.
 *
 * @author Stephen Davidson
 *
 */
// TODO: Some of these methods are generic enough to be moved to AppDAO
public class OracleAppDAO extends AppDAO {

    private Logger logger = Logger.getLogger(OracleAppDAO.class);

    @Override
    public boolean contactUs(long docId, String docType, String licState, String licPlate, String replyAddress,
            String comment, String dbSessionId) throws EtccException {
        this.logger.trace("Start");

        boolean result = false;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_ONLINE_ACCESS.Contact_Us(?,?,?,?,?,?,?)}");

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, dbSessionId);
            cstmt.setLong(3, docId);
            cstmt.setString(4, docType);
            cstmt.setString(5, licState == null ? null : licState.toUpperCase());
            cstmt.setString(6, licPlate == null ? null : licPlate.toUpperCase());
            cstmt.setString(7, replyAddress);

            int maxLen = 4000;
            if (comment.length() > maxLen)
                comment = comment.substring(0, maxLen);

            cstmt.setString(8, comment);

            cstmt.execute();

            result = cstmt.getInt(1) == 1;
            return result;
        } catch (SQLException sqle) {
            throw new EtccException("Error running contactUs():" + sqle, sqle);
        } finally {
            close(cstmt);
        }
    }

    @Override
    @Deprecated
    public String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices) throws EtccException {
        this.logger.trace("Start");
        String result = null;
        /*
        CallableStatement cstmt = null;
        try {
            *
             * FUNCTION get_vea_agreement(p_document_id IN NUMBER, p_document_type IN VARCHAR2, p_session IN VARCHAR2,
             * p_ip_address IN VARCHAR2, p_user IN VARCHAR2, o_vps_invoices_arr IN olc_vps_invoices_arr, o_agreement OUT
             * VARCHAR2, o_error_msg_arr OUT OLC_ERROR_MSG_ARR)
             *
            cstmt = this.conn.prepareCall("{? = call " + "olcsc_param.GET_VEA_AGREEMENT(?,?,?,?,?,?,?,?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_VPS_INVOICES_REC", OlcVpsInvoicesRec.class);

            ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor("OL_OWNER.OLC_VPS_INVOICES_ARR", this.conn);
            ARRAY array = new ARRAY(arraydesc, this.conn, convertInv(invoices));

            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.NUMERIC);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLoginTypeString());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.setArray(idx++, array);
            cstmt.registerOutParameter(idx++, Types.CLOB);
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.execute();

            result = cstmt.getString(idx - 1);

        } catch (SQLException sqle) {
            throw new EtccException("Error running getVeaText(): " + ": " + sqle, sqle);
        } finally {
            close(cstmt);
        }
        */
        return result;
    }

    /*
    private OlcVpsInvoicesRec[] convertInv(Invoice[] invoices) throws EtccException {
        OlcVpsInvoicesRec[] result = null;
        if (invoices != null) {
            result = new OlcVpsInvoicesRec[invoices.length];
            for (int i = 0; i < invoices.length; i++) {
                OlcVpsInvoicesRec inv = new OlcVpsInvoicesRec();
                inv.setCurrDueDate(WSDateUtil.calendarToTimestamp(invoices[i].getDueDate()));
                inv.setFirstName(invoices[i].getFirstName());
                inv.setInvoiceAmount(invoices[i].getAmount());
                inv.setInvoiceDate(WSDateUtil.calendarToTimestamp(invoices[i].getInvoiceDate()));
                inv.setInvoiceId(new BigDecimal(invoices[i].getId()));
                inv.setLastName(invoices[i].getLastName());
                inv.setLicPlateNbr(invoices[i].getLicPlateNumber());
                inv.setLicState(invoices[i].getLicPlateState());
                inv.setOnlineFee(invoices[i].getOnlineFee());
                inv.setVeaAmount(invoices[i].getVeaAmount());
                inv.setViolatorId(new BigDecimal(invoices[i].getViolatorId()));

                result[i] = inv;
            }
        }
        return result;
    }
    */

    @Override
    protected String loadParam(String paramName) throws EtccException {
        this.logger.debug("Start");
        String result = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call " + "utl_web.GETSYSPARM('" + paramName + "')}");

            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.execute();

            result = cstmt.getString(1);
            this.logger.info(paramName + ": " + result);
        } catch (SQLException sqle) {
            throw new EtccException("Error running getSysParam (" + paramName + "): " + ": " + sqle, sqle);
        } finally {
            close(cstmt);
        }
        return result;
    }

    @Override
    public Long getPOSId(String url) throws EtccException {
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_LOGIN_MGMT.GET_POS_ID(?)}");
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.setString(2, url);
            cstmt.execute();

            int result = cstmt.getInt(1);
            if (result > 0) {
                return new Long(result);
            }

        } catch (SQLException sqle) {
            throw new EtccException("Error runninggetPOSId", sqle);
        } finally {
            close(cstmt);
        }
        return null;
    }

    public Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDto,
			Long eventId) throws EtccException {
		logger.info("Entering getAccountPostingAndShiftId() @ OracleAppDAO");
		Long[] ids = new Long[2];
		CallableStatement cstmt = null;
		try {

			logger.info("getAccountPostingAndShiftId() :: Calling olcsc_payment.get_acct_posting_id_shift_id");
			cstmt = conn
					.prepareCall("{? = call  olcsc_payment.get_acct_posting_id_shift_id(?,?,?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			// cstmt.setString( 2, url );
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", OLC_ERROR_MSG_REC.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			if (acctLoginDto.getAcctId()==0) {
				cstmt.setLong(idx++, Long.valueOf(acctLoginDto.getInvoiceId()));
			} else {
				cstmt.setLong(idx++, acctLoginDto.getAcctId());
			}
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setLong(idx++, eventId);
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setString(idx, acctLoginDto.getAcctId()==0 ? "IN" : "AC");
			cstmt.execute();

			Long shiftId = cstmt.getLong(idx - 2);
			Long cartId = cstmt.getLong(idx - 3);
			ids[0] = shiftId;
			ids[1] = cartId;
			byte result = cstmt.getByte(1);
			logger.debug("##################  result   " + result);
			if (result == 0) {
//				Collection errors = Util.convertErrorMsgs(cstmt.getArray(9));
//				Iterator iter = errors.iterator();
//				while (iter.hasNext()) {
//					ErrorMessageDTO errorDTO = (ErrorMessageDTO) iter.next();
//					logger.error("error occured in autoComments:"
//							+ errorDTO.getMessage());
//				}
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in getAccountPostingAndShiftId() @ OracleAppDAO ",
					sqle);
			throw new EtccException("Error getAccountPostingAndShiftId", sqle);
		} finally {
			close(cstmt);
		}// end of try-catch-finally
		logger.info("Leaving getAccountPostingAndShiftId() @ OracleAppDAO");
		return ids;
	}// end of getAccountPostingAndShiftId()

	@Override
	public Long getAccountBillingMethodIdByToken(long token) throws EtccException {
		long accountBillingMehotdId = 0;
		  PreparedStatement ps = null;
	        try {
	            String query = " SELECT ACCOUNT_BILLING_METHOD_ID FROM ACCOUNT_BILLING_METHODS WHERE TOKEN =  ? ";
	            ps = this.conn.prepareStatement(query);
	            ps.setLong(1, token);
	            ResultSet rs =  ps.executeQuery();
	            
	            if(rs.next()) {
	                accountBillingMehotdId = rs.getLong(1);
	                logger.info("accountBillingMehotdId: " + accountBillingMehotdId);
	              }     
	        } catch (SQLException e) {
	        	throw new EtccException("Error getAccountBillingMethodIdByToken", e);
	        } finally {
		close(ps);
		
	}// 
	        return accountBillingMehotdId;
	}
	public Long getAcctVehicleId(long accid,String lic_plate_number,String lic_plate_state) throws EtccException {
		long acctVehicleId = 0;
		  PreparedStatement ps = null;
	        try {
	            String query = "select av.account_vehicle_id from account_vehicles av where av.account_id =? "
	            		+ "and UPPER(av.lic_plate_number) =? and UPPER(av.lic_plate_state)=?  ORDER BY av.account_vehicle_id desc";
	            ps = this.conn.prepareStatement(query);
	            ps.setLong(1, accid);
	            ps.setString(2,lic_plate_number);
	            ps.setString(3,lic_plate_state);
	            ResultSet rs =  ps.executeQuery();
	            if(rs.next()) {
	            	acctVehicleId = rs.getLong(1);
	                logger.info("AcctVehicleId: " + acctVehicleId);
	              }     
	        } catch (SQLException e) {
	        	throw new EtccException("Error getAccountBillingMethodIdByToken", e);
	        } finally {
		close(ps);
		
	}// 
	        return acctVehicleId;
	}
public Long getInvoiceId(String invoiceId) throws EtccException {
		long invoice_Id = 0;
		  PreparedStatement ps = null;
	        try {
	            String query = "select inv.invoice_id from invoices inv where inv.invoice_number=?";
	            ps = this.conn.prepareStatement(query);
	            ps.setString(1, invoiceId);
	            ResultSet rs =  ps.executeQuery();
	            if(rs.next()) {
	            	invoice_Id = rs.getLong(1);
	                logger.info("invoice_Id: " + invoice_Id);
	              }     
	        } catch (SQLException e) {
	        	throw new EtccException("Error getInvoiceId", e);
	        } finally {
		close(ps);
		
	}// 
	        return invoice_Id;
	}

// recalculateAutochargeAndSave for make payment
public Double recalculateAutochargeAndSave(Long accountId, String userName) throws EtccException{
	
	double initialPrepaidBalance = 0.0;
	double accountBalance =0.00;
	double prevMinRebillAmt=0.00;
	double prevMinLowBalLevel=0.00;
	double rebillAmt =0.00;
	double lowBalLevel=0.00;
	boolean autochargeReqd=false;
	Long planDetailId =null;
	 PreparedStatement ps = null;
     try {
         String query = "SELECT A.PLAN_DETAIL_ID, A.ACCOUNT_BALANCE,A.REBILL_AMOUNT,A.MIN_REBILL_AMOUNT,"
         		+ "A.LOW_BALANCE_LEVEL,A.MIN_LOW_BALANCE_LEVEL "
         		+ "FROM ACCOUNTS A WHERE A.ACCOUNT_ID =?";
         ps = this.conn.prepareStatement(query);
         ps.setLong(1, accountId);
         ResultSet rs =  ps.executeQuery();
         if(rs.next()) {
        	 planDetailId =rs.getLong(1);
        	 accountBalance =rs.getDouble(2);
        	 rebillAmt =rs.getDouble(3);
        	 prevMinRebillAmt=rs.getDouble(4);
        	 lowBalLevel =rs.getDouble(5);
        	 prevMinLowBalLevel =rs.getDouble(6);
           }     
     } catch (SQLException e) {
    	 logger.error("Error get the Account details", e);
     } finally {
    	 close(ps);
     }
     autochargeReqd = isAutochargeRequiredForAccount(accountId,planDetailId);
     
	if (autochargeReqd){
		Double[] arrAutocharge = getAutochargeValuesForActiveAccount(accountId,planDetailId,rebillAmt,prevMinRebillAmt,lowBalLevel,prevMinLowBalLevel);
		
		Double rebillAmountVal = arrAutocharge[3];
		Double lowBalLevelarrVal = arrAutocharge[4];
		
		logger.info("rebillAmount to save = " + rebillAmountVal);
		logger.info("lowBalLevel to save = " + lowBalLevelarrVal);
		
		boolean IS_SUCCESS = saveAutoChargeAndNotify(accountId, rebillAmountVal, lowBalLevelarrVal, userName);
		if(IS_SUCCESS){
			initialPrepaidBalance = arrAutocharge[0];	
		} else {
			logger.error("Saving autocharge values failed.");
		}
		//double currentAccountBalance = 0.0;
		if(accountBalance>0){
			try{
				initialPrepaidBalance = accountBalance - initialPrepaidBalance;
			}catch(NumberFormatException nfe){
				// should not happen this scenario, log it and proceed for current minimum required balance
				logger.error("Account balance for account: " + accountId + " has a number format issue in recalculateAutochargeAndSave process >> " + nfe.getMessage());
			}
		}
		//initialPrepaidBalance = currentAccountBalance - initialPrepaidBalance;
	}// end of if (autochargeReqd)
	return initialPrepaidBalance;
}// end of recalculateAutochargeAndSave()
private boolean isAutochargeRequiredForAccount(Long accountId,Long planDetailId) {
	
	boolean autochargeRequired = true;
	Long planDetailIdfromDB =null;
	String paymentTimingCode=null;
	 PreparedStatement ps = null;
     try {
         String query = "SELECT PPT.PLAN_DETAIL_ID,PPT.PAYMENT_TIMING_CODE FROM PLAN_PAYMENT_TIMINGS PPT WHERE  PPT.PLAN_DETAIL_ID =?";
         ps = this.conn.prepareStatement(query);
         ps.setLong(1, planDetailId);
         ResultSet rs =  ps.executeQuery();
         if(rs.next()) {
        	 planDetailIdfromDB = rs.getLong(1);
        	 paymentTimingCode=rs.getString(2);
        	 boolean transit = false;
        	 if (planDetailIdfromDB != null) {
        		  planDetailId=planDetailIdfromDB;
        		  transit = true;
        	 }
        	 if ("PST".equals(!StringUtil.isEmpty(paymentTimingCode))){
     			autochargeRequired = false;
        	 }
             logger.info("autochargeRequired: " + autochargeRequired);
           }     
     } catch (SQLException e) {
    	 logger.error("Error in  isAutochargeRequiredForAccount::", e);
     } finally {
	close(ps);
    }//
	return autochargeRequired;
}
/**
 * Get autocharge values for an active account
 * @param account
 * @return
 */
private Double[] getAutochargeValuesForActiveAccount(Long accountId,Long planDetailId,double rebillAmt,double prevMinRebillAmt,double lowBalLevel,double prevMinLowBalLevel) {		
	
	Double[] arrAutocharge = getMinAutochargeValues(accountId,planDetailId);
	double minPrepaidBal = arrAutocharge[0];
	double minRebillAmt = arrAutocharge[1];
	double minLowBalLevel = arrAutocharge[2];

	double dispRebillAmt = 0.0;
	double dispLowBalLevel = 0.0;
	
	logger.info(minRebillAmt + "...minRebillAmt");
	logger.info(minLowBalLevel + "...minLowBalLevel");
	logger.info(rebillAmt + "...rebillAmt in ACCOUNTS");
	logger.info(lowBalLevel + "...lowBalLevel in ACCOUNTS");

	/**
	 * Compare new min RBL to previous RBL 
	 * 		§ If new min RBL >= previous RBL, show new min RBL 
	 * 		§ If new min RBL < previous RBL 
	 * 			· Compare the previous RBL with previous min RBL 
	 * 					o If previous min RBL = previous RBL, show new min RBL in screen (means that the patron had not overridden the minimum values earlier) 
	 * 					o Else if previous min RBL < previous RBL, show the previous RBL values (means user had bumped up the minimum values earlier)
	 */

	if (minRebillAmt >= rebillAmt) {
		logger.info("...minRebillAmt >= rebillAmt");
		// If the calculated amount is greater, show that value
		dispRebillAmt = minRebillAmt;
	} else {
		// If the calculated value is less, determine if user had changed the values earlier
		if (prevMinRebillAmt < rebillAmt) {
			logger.info("...prevMinRebillAmt < rebillAmt");
			// if the values differ, keep the user changed value
			dispRebillAmt = rebillAmt;
		} else {
			// If the user had changed not changed earlier, we can replace earlier value with new min value
			dispRebillAmt = minRebillAmt;
		}
	}

	if (minLowBalLevel >= lowBalLevel) {
		// If the calculated amount is greater, show that value
		dispLowBalLevel = minLowBalLevel;
	} else {
		// If the calculated value is less, determine if user had changed the values earlier
		if (prevMinLowBalLevel < lowBalLevel) {
			// if the values differ, keep the user changed value
			dispLowBalLevel = lowBalLevel;
		} else {
			// If the user had changed not changed earlier, we can replace earlier value with new min value
			dispLowBalLevel = minLowBalLevel;
		}
	}	
	
	logger.info(minPrepaidBal + "...minPrepaidBal RETURNED");
	logger.info(minRebillAmt + "...minRebillAmt RETURNED");
	logger.info(minLowBalLevel + "...minLowBalLevel RETURNED");
	logger.info(dispRebillAmt + "...dispRebillAmt RETURNED");
	logger.info(dispLowBalLevel + "...dispLowBalLevel RETURNED");
	
	Double[] arrReturn = { minPrepaidBal, minRebillAmt, minLowBalLevel, dispRebillAmt, dispLowBalLevel };		
	return arrReturn;

}
/**
 * call the AccountMaintenance API Update_Autocharge_Amounts to save the
 * given autocharge amount and low balance threshold
 */
	private boolean saveAutoChargeAndNotify(Long acctId,
			double autoRechargeAmt, double lowBalanceAmt, String userName)
			throws EtccException {
		logger.info("Entering saveAutoChargeAndNotify() @ OracleAppDAO");

		CallableStatement cs = null;
		boolean IS_SUCCESS = false;
		try {

			String query = "{call ACCOUNT_MAINTENANCE_API.UPDATE_AUTOCHARGE_AMOUNTS(?,?,?,?,?,?,?,?)}";
			cs = this.conn.prepareCall(query);
			int count = 1;
			Long event_Id = findEventsByConstantName();
			cs.setLong(count++, event_Id); // 1 event TODO: set up //
											// enumeration
			cs.setString(count++, userName);// 2 user name
			cs.setLong(count++, acctId);// 3 account id
			cs.setBigDecimal(count++, new BigDecimal(autoRechargeAmt));// 4
																		// auto-charge
																		// amount
			cs.setBigDecimal(count++, new BigDecimal(lowBalanceAmt)); // 5 low
																		// balance
																		// amount
			cs.registerOutParameter(count++, Types.VARCHAR);// 6 -- Error Code
			cs.registerOutParameter(count++, Types.VARCHAR);// 7 -- Error
															// Message
			cs.registerOutParameter(count++, Types.VARCHAR);// 8 -- Patron Error
															// Message
			cs.execute();
			String errorMessage = cs.getString(7);
			if (logger.isDebugEnabled()) {
				logger.debug("Inside saveAutoChargeAndNotify() @ OracleAppDAO :: errorMessage::-->"
						+ errorMessage);
			}// end of if (logger.isDebugEnabled())
			if (StringUtil.isEmpty(errorMessage))
				IS_SUCCESS = true;
			else
			// throw new AccountException(errorMessage);
			if (logger.isDebugEnabled()) {
				logger.debug("Inside saveAutoChargeAndNotify() @ OracleAppDAO :: IS_SUCCESS::-->"
						+ IS_SUCCESS);
			}// end of if (logger.isDebugEnabled())
		} catch (Exception ex) {
			logger.error(
					"Error in Invoking saveAutoChargeAndNotify():ACCOUNT_MAINTENANCE_API.UPDATE_AUTOCHARGE_AMOUNTS @ OracleAppDAO",
					ex);
			throw new EtccException(ex.getMessage());
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (Throwable tt) {
					logger.error(tt);
				}
			}

		}// end of try-catch()
		logger.info("Leaving saveAutoChargeAndNotify() @ OracleAppDAO");
		return IS_SUCCESS;
	}// end of saveAutoChargeAndNotify()
/**
 * Get the minimum autocharge values fort the account
 * @param accountId
 * @return
 */
private Double[] getMinAutochargeValues(Long accountId,Long planDetailId) {
	double minPrepaidBal = 0.0;
	double minRebillAmt = 0.0;
	double minLowBalLevel = 0.0;
	
	boolean autochargeReqd = isAutochargeRequiredForAccount(accountId,planDetailId);
	if(autochargeReqd) { 
		// Calculate new minimum values
		String result = getInitialAutochargeAmounts(accountId);
		logger.info("result--->"+result.length());
		if (result != null && !result.isEmpty()) {
			StringTokenizer st = new StringTokenizer(result, ",");
			for (int i = 0; st.hasMoreTokens(); i++) {
				if (i == 0) {
					String val = st.nextToken();
					minPrepaidBal = StringUtil.isEmpty(val) ? 0.0 : Double.valueOf(val);
				} else if (i == 1) {
					String val = st.nextToken();
					minRebillAmt = StringUtil.isEmpty(val) ? 0.0 : Double.valueOf(val);
				} else if (i == 2) {
					String val = st.nextToken();
					minLowBalLevel = StringUtil.isEmpty(val) ? 0.0 : Double.valueOf(val);
				}
			}
		}
	}
	Double[] arrReturn = { minPrepaidBal, minRebillAmt, minLowBalLevel };
	logger.info("arrReturn from getMinAutochargeValues minPrepaidBal=" + minPrepaidBal + ", minRebillAmt=" + minRebillAmt + "minLowBalLevel="+ minLowBalLevel);
	return arrReturn;
}
/**
 * Get initial autocharge amounts,
 */
private String getInitialAutochargeAmounts(Long accountId) {

	String initialAutochargeAmounts = null;
	String query = " { call ACCOUNT_MAINTENANCE_API.GET_INITIAL_AUTOCHARGE_AMOUNTS(?,?) } ";
	CallableStatement cs = null;
	try {
		cs = this.conn.prepareCall(query);
		cs.setLong(1, Long.valueOf(accountId));
		cs.registerOutParameter(2, Types.VARCHAR);
		cs.execute();
		initialAutochargeAmounts = cs.getString(2);
		logger.debug("ACCOUNT_MAINTENANCE_API.GET_INITIAL_AUTOCHARGE_AMOUNTS for account Id: "+ accountId + ", result is " + initialAutochargeAmounts);
		logger.info("Calling ACCOUNT_MAINTENANCE_API.GET_INITIAL_AUTOCHARGE_AMOUNTS for account Id: "+ accountId + ", result is " + initialAutochargeAmounts);
	} catch (Exception ex) {
		logger.error("Error during calling DB package function ACCOUNT_MAINTENANCE_API.GET_INITIAL_AUTOCHARGE_AMOUNTS ",ex);
		//ex.printStackTrace();
	} finally {
		if (cs != null) {
			try {
				cs.close();
			} catch (Throwable tt) {
				logger.error(tt);
			}
		}
	}
	return initialAutochargeAmounts;
}
private Long findEventsByConstantName(){
		Long eventId =null;
		PreparedStatement ps = null;
	    try {
	        String query = "SELECT EV.EVENT_ID FROM EVENTS EV WHERE EV.CONSTANT_NAME =?";
	        ps = this.conn.prepareStatement(query);
	        ps.setString(1, "AUTOCHARGE_UI");
	        ResultSet rs =  ps.executeQuery();
	        if(rs.next()) {
	        	eventId = rs.getLong(1);
	            logger.info("eventId: " + eventId);
	          }     
	    } catch (SQLException e) {
	    	logger.error("Error eventId", e);
	    } finally {
	close(ps);
	
	}//
	return eventId==null ? 0 : eventId;
}
//QC_10261 changes start here
public String getPdfFilepath(String invoiceNum) throws EtccException {
	String pdfFilePath = null;
	  PreparedStatement ps = null;
      try {
          /*String query = "select nit.pdf_file_path from invoices inv   "
          		+ "join invoice_notifications inn on inv.invoice_id = inn.invoice_id  and inv.invoice_batch_id = inn.invoice_batch_id "
          		+ "  join notification_items nit on nit.notification_item_id = inn.notification_item_id  where inv.invoice_number =?";*/
				//Changes for collection letter 1,2,3 Enh
		  StringBuilder query = new StringBuilder();
			query.append("SELECT NI.PDF_FILE_PATH");
			query.append("   FROM INVOICES INV,");
			query.append("        INVOICE_NOTIFICATIONS INN,");
			query.append("        NOTIFICATION_ITEMS    NI,");
			query.append("        NOTIFICATION_BATCHES  NB   ");
			query.append("   WHERE INV.INVOICE_ID = INN.INVOICE_ID");
			query.append("     AND INV.INVOICE_BATCH_ID = INN.INVOICE_BATCH_ID");
			query.append("     AND INN.NOTIFICATION_ITEM_ID = NI.NOTIFICATION_ITEM_ID");
			query.append("     AND NI.NOTIFICATION_BATCH_ID = NB.NOTIFICATION_BATCH_ID ");
			query.append("     AND NB.NOTIFICATION_TYPE_ID IN (12700,12701,12702)");
			query.append("     AND INV.INVOICE_NUMBER = ?  AND INV.IS_INVOICE_MAILED_FLAG = 'Y'");
			
          ps = this.conn.prepareStatement(query.toString());
          ps.setString(1, invoiceNum);
          ResultSet rs =  ps.executeQuery();
          if(rs.next()) {
          	pdfFilePath = rs.getString(1);
              //logger.info("pdfFilePath: " + pdfFilePath);
            }     
      } catch (SQLException e) {
      	throw new EtccException("Error pdfFilePath", e);
      } finally {
	          close(ps);
	
      }// 
   return pdfFilePath;
}
//Express Account Chnages
public boolean isExpressAccount(Long accountId) throws EtccException {
	boolean isexpAccount = false;
	  PreparedStatement ps = null;
	  ResultSet rs =null;
      try {
		  StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*) FROM ACCOUNTS A");
			query.append("   JOIN PLAN_DETAILS PD  ON A.PLAN_DETAIL_ID = PD.PLAN_DETAIL_ID");
			query.append("   JOIN PLANS P ON P.PLAN_ID = PD.PLAN_ID   ");
			query.append("WHERE A.ACCOUNT_ID = ? AND P.PLAN_CODE = 'EXP'");
			
          ps = this.conn.prepareStatement(query.toString());
          ps.setLong(1, accountId);
          rs =  ps.executeQuery();
          while(rs.next()) {
        	  if (rs.getInt(1) > 0){
        		isexpAccount =true;
        	  }
            }     
      } catch (SQLException e) {
      	throw new EtccException("Error isExpressAccount", e);
      } finally {
	    close(ps);
	    close(rs);
      }// 
   return isexpAccount;
}
public String getRegExp(String userValue) throws EtccException {
	String regExp = "";
	  PreparedStatement ps = null;
	  ResultSet rs =null;
      try {
		  StringBuilder query = new StringBuilder();
			query.append("SELECT TSV.REG_EXP FROM OL_OWNER.TS_VALIDATION_RULES TSV");
			query.append("   WHERE TSV.FIELD_NAME = ?  AND TSV.FORM_NAME = 'olcsc'");
          ps = this.conn.prepareStatement(query.toString());
          ps.setString(1, userValue);
          rs =  ps.executeQuery();
          while(rs.next()) {
        	  regExp = rs.getString("REG_EXP");
            }     
      } catch (SQLException e) {
      	throw new EtccException("Error getRegExp", e);
      } finally {
	    close(ps);
	    close(rs);
      }// 
   return regExp;
}
}