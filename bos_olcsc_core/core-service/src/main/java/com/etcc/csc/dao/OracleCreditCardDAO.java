package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.DateUtil;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.PaymentResultDTO;
import com.etcc.csc.enums.EventEnum;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcCrCardTypeRec;
import com.etcc.csc.plsql.OlcPaymentInfoRec;

public class OracleCreditCardDAO extends CreditCardDAO {
	private Logger logger = Logger.getLogger(OracleCreditCardDAO.class);

	public OracleCreditCardDAO() {
	}

	/**
	 * FUNCTION Get_Payment_Info(p_doc_id VARCHAR2, p_doc_type VARCHAR2 DEFAULT
	 * 'AC', p_session_id VARCHAR2, p_ip_address VARCHAR2, p_user_id VARCHAR2,
	 * P_event_id EVENTS.EVENT_ID%TYPE, o_payment_info OUT olc_payment_info_rec,
	 * o_error_msg_arr OUT olc_error_msg_arr) RETURN NUMBER;
	 * 
	 */
	public AccountCreditCardDTO getAccountCreditCard(
			AccountLoginDTO acctLoginDto, long acctId) throws EtccException,
			EtccSecurityException {
		logger.info("Entering getAccountCreditCard() @ OracleCreditCardDAO");
		logger.debug(new StringBuilder("getAccountCreditCard: acct=")
				.append(acctId));
		AccountCreditCardDTO ccDto = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getAccountCreditCard() :: Calling Olcsc_Acct_Mgmt.Get_Payment_Info");
			cstmt = conn
					.prepareCall("{? = call Olcsc_Acct_Mgmt.Get_Payment_Info(?, ?, ?, ?, ?, ?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			typeMap.put("OL_OWNER.OLC_PAYMENT_INFO_REC",
					OlcPaymentInfoRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setLong(idx++, EventEnum.ACCOUNTCCPAGE.getId());
			cstmt.registerOutParameter(idx++, Types.STRUCT,
					"OL_OWNER.OLC_PAYMENT_INFO_REC");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR"); // error
			cstmt.execute();

			short docExists = cstmt.getShort(1);
			if (docExists == 1) {
				logger.debug("Inside if (docExists == 1) @ getAccountCreditCard()");
				OlcPaymentInfoRec tempRec = (OlcPaymentInfoRec) cstmt
						.getObject(idx - 1);
				if (tempRec != null) {
					logger.debug("Inside if (tempRec != null) @ getAccountCreditCard()");
					ccDto = new AccountCreditCardDTO();
					ccDto.setAcctId(acctId);
					ccDto.setAddress1(tempRec.getAddress1());
					ccDto.setAddress2(tempRec.getAddress2());
					ccDto.setCardCode(tempRec.getCardName());
					ccDto.setCardExpires(DateUtil.getMonthYear(DateUtil
							.timestampToCalendar(tempRec.getCardExpires())));
					ccDto.setCardNbr(tempRec.getCardNbr());
					ccDto.setCity(tempRec.getCity());
					ccDto.setNameOnCard(tempRec.getFirstName() + " "
							+ tempRec.getLastName());
					ccDto.setPlus4(tempRec.getPlus4());
					ccDto.setState(tempRec.getState());
					ccDto.setZipCode(tempRec.getZipcode());
					ccDto.setPaymentType(tempRec.getPmtType());
					BigDecimal acctBillingMethodId = tempRec
							.getAccountBillingMethodId();
					if (acctBillingMethodId != null) {
						logger.debug("Inside if (acctBillingMethodId != null) @ getAccountCreditCard()");
						ccDto.setAccountBillingMethodId(acctBillingMethodId
								.toString());
					}
					if (tempRec.getToken() != null) {
						logger.debug("Inside if (tempRec.getToken() != null) @ getAccountCreditCard()");
						ccDto.setTokenId(tempRec.getToken().toString());
					}
					ccDto.setCardExpires(DateUtil.getShortDate(DateUtil
							.timestampToCalendar(tempRec.getCardExpires())));
					if (tempRec.getPersonId() != null) {
						logger.debug("Inside if (tempRec.getPersonId() != null) @ getAccountCreditCard()");
						ccDto.setPersonId(new Long(tempRec.getPersonId()
								.longValue()));
					}
					if (tempRec.getAddressId() != null) {
						logger.debug("Inside if (tempRec.getAddressId() != null) @ getAccountCreditCard()");
						ccDto.setAddressId(new Long(tempRec.getAddressId()
								.longValue()));
					}
					if (tempRec.getPhoneId() != null) {
						logger.debug("Inside if (tempRec.getPhoneId() != null) @ getAccountCreditCard()");
						ccDto.setPhoneId(new Long(tempRec.getPhoneId()
								.longValue()));
					}
				}
			}

		} catch (SQLException sqle) {
			logger.error(
					"Error in getAccountCreditCard() @ OracleCreditCardDAO "
							+ sqle, sqle);
			throw new EtccException("Error running getAccountCreditCard: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Entering getAccountCreditCard() @ OracleCreditCardDAO");
		return ccDto;
	}

	/**
	 * 
	 * FUNCTION Set_Payment_Info(p_acct_id NUMBER, p_doc_type VARCHAR2 DEFAULT
	 * 'AC', p_session_id VARCHAR2 DEFAULT NULL, p_ip_address VARCHAR2 DEFAULT
	 * NULL, p_user_id VARCHAR2 DEFAULT NULL, p_ui_call BOOLEAN DEFAULT TRUE,
	 * p_default_Blng_Methd VARCHAR2 DEFAULT 'N', P_event_id
	 * EVENTS.EVENT_ID%TYPE, p_payment_info olc_payment_info_rec,
	 * o_error_msg_arr OUT OLC_ERROR_MSG_ARR) RETURN NUMBER
	 * 
	 */
	public PaymentResultDTO updateAccountCreditCard(
			AccountLoginDTO acctLoginDto,
			AccountCreditCardDTO acctCreditCardDto, boolean isAddPan)
			throws EtccException, EtccSecurityException {
		logger.info("Entering updateAccountCreditCard() @ OracleCreditCardDAO");
		PaymentResultDTO paymentResultDto = new PaymentResultDTO();

		try {
			setConnection(Util.getDbConnection());
			StringBuffer call = new StringBuffer();

			call.append("{? = call OLCSC_ACCT_MGMT.set_payment_info(");
			call.append("p_acct_id=>?,");
			call.append("p_doc_type=>?, ");
			call.append("p_session_id=>?, ");
			call.append("p_ip_address=>?, ");
			call.append("p_user_id=>?, ");
			call.append("p_ui_call=>?, ");
			call.append("p_default_Blng_Methd=>?, ");
			call.append("P_event_id=>?, ");
			call.append("p_payment_info=>?, ");
			call.append("o_error_msg_arr=>?");
			call.append(")}");

			logger.info("updateAccountCreditCard() :: Calling OLCSC_ACCT_MGMT.set_payment_info");
			cstmt = conn.prepareCall(call.toString());

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_PAYMENT_INFO_REC",
					OlcPaymentInfoRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			byte idx = 1;
			OlcPaymentInfoRec temp = new OlcPaymentInfoRec();
			temp.setPmtType("CREDIT");

			Long personId = acctCreditCardDto.getPersonId();
			if (personId != null) {
				logger.debug("Inside if (personId != null) @ updateAccountCreditCard()");
				temp.setPersonId(new BigDecimal(personId.longValue()));
			}
			logger.debug("personId:  " + personId);

			Long addressId = acctCreditCardDto.getAddressId();
			if (addressId != null) {
				logger.debug("Inside if (addressId != null)) @ updateAccountCreditCard()");
				temp.setAddressId(new BigDecimal(addressId.longValue()));
			}
			logger.debug("addressId: " + personId);

			Long phoneId = acctCreditCardDto.getPhoneId();
			if (phoneId != null) {
				logger.debug("Inside if (phoneId != null) @ updateAccountCreditCard()");
				temp.setPhoneId(new BigDecimal(phoneId.longValue()));
			}
			logger.debug("phoneId:  " + phoneId);

			temp.setCardNbr(acctCreditCardDto.getCardNbr());
			logger.debug("Card Nbr:  " + acctCreditCardDto.getCardNbr());

			temp.setCardExpires(new Timestamp(acctCreditCardDto
					.getCardExpryDt().getTimeInMillis()));
			logger.debug("CardExpires:  "
					+ acctCreditCardDto.getCardExpryDt().getTimeInMillis());

			temp.setCardType(new BigDecimal(acctCreditCardDto.getCardCode()));
			logger.debug("Card Code:  " + acctCreditCardDto.getCardCode());

			temp.setToken(new BigDecimal(acctCreditCardDto.getTokenId()));
			logger.debug("Token:  " + acctCreditCardDto.getTokenId());

			temp.setFirstName(acctCreditCardDto.getFirstName());
			logger.debug("First Name:  " + acctCreditCardDto.getFirstName());

			temp.setLastName(acctCreditCardDto.getLastName());
			logger.debug("Last Name:  " + acctCreditCardDto.getLastName());

			temp.setAddress1(acctCreditCardDto.getAddress1());
			logger.debug("Address 1:  " + acctCreditCardDto.getAddress1());

			temp.setAddress2(acctCreditCardDto.getAddress2());
			logger.debug("Address 2:  " + acctCreditCardDto.getAddress2());

			temp.setCity(acctCreditCardDto.getCity());
			logger.debug("City:  " + acctCreditCardDto.getCity());

			temp.setState(acctCreditCardDto.getState());
			logger.debug("State:  " + acctCreditCardDto.getState());

			temp.setZipcode(acctCreditCardDto.getZipCode());
			logger.debug("zip code:  " + acctCreditCardDto.getZipCode());

			temp.setPlus4(acctCreditCardDto.getPlus4());
			logger.debug("plus 4:  " + acctCreditCardDto.getPlus4());

			temp.setPhoneNbr(acctCreditCardDto.getPhone());
			logger.debug("phone nbr:  " + acctCreditCardDto.getPhone());

			temp.setPhoneExtn(acctCreditCardDto.getPhoneExtn());
			logger.debug("phone etxn:  " + acctCreditCardDto.getPhoneExtn());

			if (acctCreditCardDto.getAccountBillingMethodId() != null
					&& !acctCreditCardDto.getAccountBillingMethodId().equals(
							"null")) {
				logger.debug("Inside if (acctCreditCardDto.getAccountBillingMethodId() != null && !acctCreditCardDto.getAccountBillingMethodId().equals(\"null\")) @ updateAccountCreditCard()");
				temp.setAccountBillingMethodId(new BigDecimal(acctCreditCardDto
						.getAccountBillingMethodId()));
				logger.debug("billing method id:  "
						+ acctCreditCardDto.getAccountBillingMethodId());
			}

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, acctLoginDto.getAcctId());
			cstmt.setString(3, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(4, acctLoginDto.getDbSessionId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.setString(6, acctLoginDto.getLoginId());
			cstmt.setString(7, "Y");
			cstmt.setString(8, "Y");
			cstmt.setLong(9, 1);
			cstmt.setObject(10, temp);
			cstmt.registerOutParameter(11, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short result = cstmt.getShort(1);
			if (result == 0) {
				logger.debug("Inside if (result == 0) @ updateAccountCreditCard()");
				Array errs = (Array) cstmt.getObject(11);
				Collection errors = Util.convertErrorMsgs(errs);
				if (errors == null || errors.size() == 0) {
					logger.debug("Inside if (errors == null || errors.size() == 0) @ updateAccountCreditCard()");
					errors = new ArrayList();
					ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
					errMsgDto.setMessage("Invalid information entered. "
							+ "Please try again.");
					errors.add(errMsgDto);
				}
				paymentResultDto.setErrors(errors);
			} else if (result == -1) {
				logger.debug("Inside else if (result == -1) @ updateAccountCreditCard()");
				throw new EtccSecurityException(
						"Security exception in updateAccountCreditCard()");
			} else {
				logger.debug("Inside else of if (result == 0) @ updateAccountCreditCard()");
				paymentResultDto.setSuccessful(true);
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in updateAccountCreditCard() @ OracleCreditCardDAO "
							+ sqle, sqle);
			throw new EtccException("Error running updateAccountCreditCard: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving updateAccountCreditCard() @ OracleCreditCardDAO");
		return paymentResultDto;
	}

	public Collection getCreditCardTypes() throws EtccException {
		logger.info("Entering getCreditCardTypes() @ OracleCreditCardDAO");
		Collection result = null;

		try {
			setConnection(Util.getDbConnection());
			logger.info("getCreditCardTypes() :: Calling OLCSC_UTIL.GET_CR_CARD_TYPES");
			cstmt = conn
					.prepareCall("{? = call OLCSC_UTIL.GET_CR_CARD_TYPES(?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			typeMap.put("OL_OWNER.OLC_CR_CARD_TYPE_REC", OlcCrCardTypeRec.class);
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.registerOutParameter(2, Types.ARRAY,
					"OL_OWNER.OLC_CR_CARD_ARR");
			cstmt.registerOutParameter(3, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			if (cstmt.getInt(1) == 1) {
				logger.debug("Inside if (cstmt.getInt(1) == 1) @ getCreditCardTypes()");
				Object[] objArray = (Object[]) cstmt.getArray(2).getArray(
						conn.getTypeMap());
				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0) @ getCreditCardTypes()");
					result = new ArrayList();
					for (int i = 0; i < objArray.length; i++) {
						CreditCardDTO ccDto = new CreditCardDTO();
						ccDto.setCardCode(((OlcCrCardTypeRec) objArray[i])
								.getCardCode());
						ccDto.setCardName(((OlcCrCardTypeRec) objArray[i])
								.getCardName());
						result.add(ccDto);
					}
				}
			} else {
				logger.debug("Inside else of if (cstmt.getInt(1) == 1) @ getCreditCardTypes()");
				logger.error("Error in getCreditCardTypes() @ OracleCreditCardDA :: Error running getCreditCards");
				throw new EtccException("Error running getCreditCards");
			}
		} catch (SQLException sqle) {
			logger.error("Error in getCreditCardTypes() @ OracleCreditCardDA "
					+ sqle, sqle);
			throw new EtccException("Error running getCreditCards: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving getCreditCardTypes() @ OracleCreditCardDAO");
		return result;
	}

	public String isCreditCardScrubbed(long accountNum) throws EtccException {
		logger.info("Entering isCreditCardScrubbed() @ OracleCreditCardDAO");
		String result = null;

		try {
			setConnection(Util.getDbConnection());
			cstmt = conn
					.prepareCall("{? = call cc_audit_processing.verify_cc_scrubbed(?)}");
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.setLong(2, accountNum);
			cstmt.execute();
			if (cstmt.getString(1) != null) {
				result = cstmt.getString(1);
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in isCreditCardScrubbed() @ OracleCreditCardDA "
							+ sqle, sqle);
			throw new EtccException("Error running isCreditCardScrubbed: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}

		logger.info("Leaving isCreditCardScrubbed() @ OracleCreditCardDAO");
		return result;
	}

	public int saveBillingPersonInfo(AddressDTO add) throws EtccException {
		logger.info("Entering saveBillingPersonInfo() @ OracleCreditCardDAO");
		logger.info("Leaving saveBillingPersonInfo() @ OracleCreditCardDAO");
		return 1;
	}
}
