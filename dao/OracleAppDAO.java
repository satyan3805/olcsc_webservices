package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DateUtil;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Util;
import com.etcc.csc.datatype.Invoice;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcVpsInvoicesRec;

public class OracleAppDAO extends AppDAO {

	private Logger logger = Logger.getLogger(OracleAppDAO.class);

	public OracleAppDAO() {
	}

	public String getContactPhoneNumber(String locale) throws EtccException {
		logger.debug("Start");
		return getSysParam("CLIENT_PHONE1", locale);
	}

	public String getContactUsSuccessMessage(String locale)
			throws EtccException {
		logger.info("Entering getContactUsSuccessMessage() @ OracleAppDAO");
		String text_id = "CONTACT_SUCCESS_" + locale.toUpperCase();
		StringBuffer sb = new StringBuffer();
		sb.append("   select c_text from OL_OWNER.olc_text where text_id=?");
		String message = null;
		setConnection(Util.getDbConnection());
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, text_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				message = rs.getString(1);
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in getContactUsSuccessMessage() @ OracleAppDAO ",
					sqle);
			throw new EtccException("Error getting ContactUsSuccessMessage: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch()-finally()
		logger.info("Leaving getContactUsSuccessMessage() @ OracleAppDAO");
		return message;
	}// end of getContactUsSuccessMessage()

	public String getHomepageContactPhone(String locale) throws EtccException {
		logger.info("Entering getContactUsSuccessMessage() @ OracleAppDAO");
		return getSysParam("CLIENT PHONE HOME PAGE", locale);
	}// end of getHomepageContactPhone()

	public boolean contactUs(long docId, String docType, String licState,
			String licPlate, String replyAddress, String topic, String comment,
			String dbSessionId, Boolean is121Comment, Boolean updateEmail,
			String localeStr) throws EtccException {
		logger.info("Entering contactUs() @ OracleAppDAO");
		boolean result = false;
		String is121 = is121Comment.equals(Boolean.TRUE) ? "Y" : "N";
		String updEmail = updateEmail.equals(Boolean.TRUE) ? "Y" : "N";
		try {
			setConnection(Util.getDbConnection());
			logger.info("contactUs() :: Calling OLCSC_ONLINE_ACCESS.Contact_Us");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ONLINE_ACCESS.Contact_Us(?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, dbSessionId);
			cstmt.setLong(3, docId);
			cstmt.setString(4, docType);
			cstmt.setString(5, licState == null ? null : licState.toUpperCase());
			cstmt.setString(6, licPlate == null ? null : licPlate.toUpperCase());
			cstmt.setString(7, replyAddress);
			cstmt.setString(8, topic);
			int maxLen = 4000;
			if (comment.length() > maxLen)
				comment = comment.substring(0, maxLen);
			cstmt.setString(9, comment);
			cstmt.setString(10, localeStr);
			cstmt.setString(11, is121);
			cstmt.setString(12, updEmail);
			cstmt.execute();
			logger.info("Leaving contactUs() @ OracleAppDAO");
			return result = (cstmt.getInt(1) == 1);
		} catch (SQLException sqle) {
			logger.error("Error in contactUs() @ OracleAppDAO ", sqle);
			throw new EtccException("Error running contactUs():" + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally
	}// end of contactUs()

	public String getHelpUrl(String locale) throws EtccException {
		logger.info("Entering getHelpUrl() @ OracleAppDAO");
		return getSysParam("OLCSC HELP URL", locale);
	}// end of getHelpUrl()

	public String getHelpFaqUrl(String locale) throws EtccException {
		logger.info("Entering getHelpFaqUrl() @ OracleAppDAO");
		return getSysParam("HELP_FAQ_LINK", locale);
	}// end of getHelpFaqUrl()

	public String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices)
			throws EtccException {
		logger.info("Entering getVeaText() @ OracleAppDAO");
		String result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getVeaText() :: Calling olcsc_param.GET_VEA_AGREEMENT");
			cstmt = conn.prepareCall("{? = call "
					+ "olcsc_param.GET_VEA_AGREEMENT(?,?,?,?,?,?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_VPS_INVOICES_REC",
					OlcVpsInvoicesRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_VPS_INVOICES_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn, convertInv(invoices));
			byte idx = 1;
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getLoginType());
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setArray(idx++, array);
			cstmt.registerOutParameter(idx++, Types.CLOB);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			result = cstmt.getString(idx - 1);
		} catch (SQLException sqle) {
			logger.error("Error in getVeaText() @ OracleAppDAO ", sqle);
			throw new EtccException("Error running getVeaText(): " + ": "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally
		logger.info("Leaving getVeaText() @ OracleAppDAO");
		return result;
	}// end of getVeaText()

	public String getTablePageSize() throws EtccException {
		logger.info("Entering getTablePageSize() @ OracleAppDAO");
		return getSysParam("OLCSC TABLE PAGE SIZE");
	}// end of getTablePageSize()

	public String getSearchParameterSize() throws EtccException {
		logger.info("Entering getSearchParameterSize() @ OracleAppDAO");
		return getSysParam("OLCSC SEARCH PARAMETER SIZE");
	}// end of getSearchParameterSize()

	public String getReportDays() throws EtccException {
		logger.info("Entering getReportDays() @ OracleAppDAO");
		return getSysParam("REPORT_DAYS");
	}// end of getReportDays()

	private OlcVpsInvoicesRec[] convertInv(Invoice[] invoices)
			throws EtccException {
		logger.info("Entering convertInv() @ OracleAppDAO");
		try {
			OlcVpsInvoicesRec[] result = null;
			if (invoices != null) {
				result = new OlcVpsInvoicesRec[invoices.length];
				for (int i = 0; i < invoices.length; i++) {
					OlcVpsInvoicesRec inv = new OlcVpsInvoicesRec();
					inv.setCurrDueDate(DateUtil.calendarToTimestamp(invoices[i]
							.getDueDate()));
					inv.setFirstName(invoices[i].getFirstName());
					inv.setInvoiceAmount(invoices[i].getAmount());
					inv.setInvoiceDate(DateUtil.calendarToTimestamp(invoices[i]
							.getInvoiceDate()));
					inv.setInvoiceId(new BigDecimal(invoices[i].getId()));
					inv.setLastName(invoices[i].getLastName());
					inv.setLicPlateNbr(invoices[i].getLicPlateNumber());
					inv.setLicState(invoices[i].getLicPlateState());
					inv.setOnlineFee(invoices[i].getOnlineFee());
					inv.setVeaAmount(invoices[i].getVeaAmount());
					inv.setViolatorId(new BigDecimal(invoices[i]
							.getViolatorId()));

					result[i] = inv;
				}
			}
			logger.info("Leaving convertInv() @ OracleAppDAO");
			return result;
		} catch (Exception e) {
			logger.error("Error in convertInv() @ OracleAppDAO ", e);
			throw new EtccException("Error copying invoices: " + e);
		}// end of try-catch-finally
	}// end of convertInv()

	public String getApplicationUrl(String locale) throws EtccException {
		logger.info("Entering getApplicationUrl() @ OracleAppDAO");
		return getSysParam("OLCSC_APPLICATION_URL", locale);
	}// end of getApplicationUrl()

	public String getPrivacyPolicyUrl(String locale) throws EtccException {
		logger.info("Entering getPrivacyPolicyUrl() @ OracleAppDAO");
		return getSysParam("OLCSC_PRIVACY_POLICY_URL", locale);
	}// end of getPrivacyPolicyUrl()

	public String getSupportedBrowserUrl(String locale) throws EtccException {
		logger.info("Entering getSupportedBrowserUrl() @ OracleAppDAO");
		return getSysParam("OLCSC_SUPPORTED_BROWSER_URL", locale);
	}// end of getSupportedBrowserUrl()

	public boolean secureCookies() throws EtccException {
		logger.info("Entering secureCookies() @ OracleAppDAO");
		return Util.stringToBoolean(getSysParam("TS_SECURE_COOKIES"));
	}// end of secureCookies()

	public String getSysParam(String paramName) throws EtccException {
		logger.info("Entering getSysParam(String paramName) @ OracleAppDAO");
		String result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getSysParam(String paramName) :: Calling utl_web.GETSYSPARM");
			cstmt = conn.prepareCall("{? = call " + "utl_web.GETSYSPARM('"
					+ paramName + "')}");

			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.execute();

			result = cstmt.getString(1);
			logger.debug(paramName + ": " + result);
		} catch (SQLException sqle) {
			logger.error(
					"Error in getSysParam(String paramName) @ OracleAppDAO ",
					sqle);
			throw new EtccException("Error running getSysParam (" + paramName
					+ "): " + ": " + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally
		logger.info("Leaving getSysParam(String paramName) @ OracleAppDAO");
		return result;
	}// end of getSysParam()

	public String getHomePageDaysBack() throws EtccException {
		logger.info("Entering getHomePageDaysBack() @ OracleAppDAO");
		return getSysParam("OLC HOME PAGE DAYS BACK");
	}// end of getHomePageDaysBack()

	public String getSysParam(String paramName, String locale)
			throws EtccException {
		logger.info("Entering getSysParam(String paramName, String locale) @ OracleAppDAO");
		String result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getSysParam(String paramName, String locale) :: Calling OLCSC_UTIL.GETSYSPARM");
			cstmt = conn.prepareCall("{? = call " + "OLCSC_UTIL.GETSYSPARM('"
					+ paramName + "', NULL, NULL, 'EN', '" + locale + "')}");

			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.execute();

			result = cstmt.getString(1);
			logger.debug(paramName + ": " + result);
		} catch (SQLException sqle) {
			logger.error(
					"Error in getSysParam(String paramName, String locale) @ OracleAppDAO ",
					sqle);
			throw new EtccException("Error running getSysParam (" + paramName
					+ "): " + ": " + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally
		logger.info("Leaving getSysParam(String paramName, String locale) @ OracleAppDAO");
		return result;
	}// end of getSysParam()

	public double getNotificationFee() throws EtccException {
		logger.info("Entering getNotificationFee() @ OracleAppDAO");
		StringBuilder sb = new StringBuilder();
		// Defect 4360 changes
		sb.append("select value from sys_params where upper(name) = upper('ACCOUNT_STMNT_FEE') and date_expires is null");
		double result = 0;
		// if (true) return result;
		try {
			setConnection(Util.getDbConnection());
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				result = Double.valueOf(rs.getString("value"));
			}
		} catch (SQLException sqle) {
			logger.error("Error in getNotificationFee() @ OracleAppDAO ", sqle);
			throw new EtccException("Error running getNotificationFee(): "
					+ ": " + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally
		logger.info("Leaving getNotificationFee() @ OracleAppDAO");
		return result;
	}// end of getNotificationFee()

	public String getTourUrl(String locale) throws EtccException {
		logger.info("Entering getTourUrl() @ OracleAppDAO");
		return getSysParam("OLCSC_TOUR_URL", locale);
	}// end of getTourUrl()

	public String getSplashPageUrl(String locale) throws EtccException {
		logger.info("Entering getSplashPageUrl() @ OracleAppDAO");
		return getSysParam("OLCSC_SPLASH_URL", locale);
	}// end of getSplashPageUrl()

	public Long getPOSId(String url) throws EtccException {
		logger.info("Entering getPOSId() @ OracleAppDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("getPOSId() :: Calling OLCSC_LOGIN_MGMT.GET_POS_ID");
			cstmt = conn
					.prepareCall("{? = call OLCSC_LOGIN_MGMT.GET_POS_ID(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, url);
			cstmt.execute();

			int result = cstmt.getInt(1);
			if (result > 0) {
				return new Long(result);
			}
		} catch (SQLException sqle) {
			logger.error("Error in getPOSId() @ OracleAppDAO ", sqle);
			throw new EtccException("Error runninggetPOSId", sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally
		logger.info("Leaving getPOSId() @ OracleAppDAO");
		return null;
	}// end of getPOSId()

	public String getViewTransDefaultRange() throws EtccException {
		logger.info("Entering getViewTransDefaultRange() @ OracleAppDAO");
		return getSysParam("OLCSC_VIEW_TRANS_DEFAULT_RANGE");
	}// end of getViewTransDefaultRange()

	public String getHomeTabUrl(String locale) throws EtccException {
		logger.info("Entering getHomeTabUrl() @ OracleAppDAO");
		return getSysParam("OLC HOME TAB URL", locale);
	}// end of getHomeTabUrl()

	public String getCCYears() throws EtccException {
		logger.info("Entering getCCYears() @ OracleAppDAO");
		return getSysParam("OLCSC CC YEARS");
	}// end of getCCYears()

	public String getTollRateUrl() throws EtccException {
		logger.info("Entering getTollRateUrl() @ OracleAppDAO");
		return getSysParam("OLCSC_TOLLRATE_URL");
	}// end of getTollRateUrl()

	public String getStep7MessageUrl(String locale) throws EtccException {
		logger.info("Entering getStep7MessageUrl() @ OracleAppDAO");
		return getSysParam("OLCSC_STEP7_MESSAGE_URL", locale);
	}// end of getStep7MessageUrl()

	public String getTempPlateExpirationLimit() throws EtccException {
		logger.info("Entering getTempPlateExpirationLimit() @ OracleAppDAO");
		return getSysParam("OLCSC_TEMP_PLATE_EXPIRATION_LIMIT");
	}// end of getTempPlateExpirationLimit()

	public Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDto,
			Long eventId) throws EtccException {
		logger.info("Entering getAccountPostingAndShiftId() @ OracleAppDAO");
		Long[] ids = new Long[2];
		try {
			setConnection(Util.getDbConnection());
			logger.info("getAccountPostingAndShiftId() :: Calling olcsc_payment.get_acct_posting_id_shift_id");
			cstmt = conn
					.prepareCall("{? = call  olcsc_payment.get_acct_posting_id_shift_id(?,?,?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			// cstmt.setString( 2, url );
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			if ("IN".equals(acctLoginDto.getLoginType())) {
				cstmt.setLong(idx++, acctLoginDto.getInvoiceId());
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
			cstmt.setString(idx, acctLoginDto.getLoginType());
			cstmt.execute();

			Long shiftId = cstmt.getLong(idx - 2);
			Long cartId = cstmt.getLong(idx - 3);
			ids[0] = shiftId;
			ids[1] = cartId;
			byte result = cstmt.getByte(1);
			logger.debug("##################  result   " + result);
			if (result == 0) {
				Collection errors = Util.convertErrorMsgs(cstmt.getArray(9));
				Iterator iter = errors.iterator();
				while (iter.hasNext()) {
					ErrorMessageDTO errorDTO = (ErrorMessageDTO) iter.next();
					logger.error("error occured in autoComments:"
							+ errorDTO.getMessage());
				}
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in getAccountPostingAndShiftId() @ OracleAppDAO ",
					sqle);
			throw new EtccException("Error getAccountPostingAndShiftId", sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally
		logger.info("Leaving getAccountPostingAndShiftId() @ OracleAppDAO");
		return ids;
	}// end of getAccountPostingAndShiftId()

	public Long[] getCardAddressPersonId(AccountLoginDTO acctLoginDto,
			String fn, String ln, String address1, String address2,
			String city, String state, String zipCode, String plus4,
			Long eventId) throws EtccException {
		logger.info("Entering getCardAddressPersonId() @ OracleAppDAO");
		Long[] ids = new Long[2];
		try {
			setConnection(Util.getDbConnection());
			logger.info("getCardAddressPersonId() :: Calling olcsc_acct_mgmt.get_person_address");
			cstmt = conn
					.prepareCall("{? = call olcsc_acct_mgmt.get_person_address(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			if ("IN".equals(acctLoginDto.getLoginType())) {
				cstmt.setLong(idx++, acctLoginDto.getInvoiceId());
			} else {
				cstmt.setLong(idx++, acctLoginDto.getAcctId());
			}
			cstmt.setString(idx++, acctLoginDto.getLoginType());
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setLong(idx++, eventId);
			cstmt.setString(idx++, fn);
			cstmt.setString(idx++, ln);
			cstmt.setString(idx++, address1);
			cstmt.setString(idx++, address2);
			cstmt.setString(idx++, city);
			cstmt.setString(idx++, state);
			cstmt.setString(idx++, zipCode);
			cstmt.setString(idx++, plus4);
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.registerOutParameter(idx++, Types.NUMERIC);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			Long addressId = cstmt.getLong(idx - 1);
			Long personId = cstmt.getLong(idx - 2);
			logger.debug("The addressId and personId are " + addressId + " "
					+ personId);
			ids[0] = addressId;
			ids[1] = personId;
			byte result = cstmt.getByte(1);
			logger.debug("##################  result   " + result);
			if (result == 0) {
				Collection errors = Util.convertErrorMsgs(cstmt.getArray(9));
				Iterator iter = errors.iterator();
				while (iter.hasNext()) {
					ErrorMessageDTO errorDTO = (ErrorMessageDTO) iter.next();
					logger.debug("error occured in autoComments:"
							+ errorDTO.getMessage());
				}
			}
		} catch (SQLException sqle) {
			logger.error("Error in getCardAddressPersonId() @ OracleAppDAO ",
					sqle);
			throw new EtccException("Error getAccountPostingAndShiftId", sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getCardAddressPersonId() @ OracleAppDAO");
		return ids;
	}// end of getCardAddressPersonId()

	public String getOlcscShiftId() throws EtccException {
		logger.info("Entering getOlcscShiftId() @ OracleAppDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("getOlcscShiftId() :: Calling OLCSC_UTIL.get_olcsc_shift_id");
			cstmt = conn
					.prepareCall("{? = call OLCSC_UTIL.get_olcsc_shift_id()}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			// cstmt.setString( 2, url );
			cstmt.execute();

			String result = cstmt.getString(1);
			if (result != null) {
				return result;
			}
		} catch (SQLException sqle) {
			logger.error("Error in getOlcscShiftId() @ OracleAppDAO ", sqle);
			throw new EtccException("Error getOlcscShiftId", sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getOlcscShiftId() @ OracleAppDAO");
		return null;
	}// end of getOlcscShiftId()

}// end of OracleAppDAO Class
