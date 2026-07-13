package com.etcc.csc.dao;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;

public class OracleTagRequestDAO extends TagRequestDAO {
	private Logger logger = Logger.getLogger(OracleTagRequestDAO.class);

	public OracleTagRequestDAO() {
	}

	public TagDTO addTag(String sessionId, String ipAddress, String userId,
			TagDTO tagRequestDto, Long posId) throws EtccException,
			EtccSecurityException {
		logger.info("Entering addTag() @ OracleTagRequestDAO");
		try {
			setConnection(Util.getDbConnection());
			setErrorTypeMap();
			logger.info("addTag() :: Calling OLCSC_ACCT_MGMT.Account_Vehicle_Add");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Account_Vehicle_Add(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, tagRequestDto.getAcctId());
			cstmt.setString(3, sessionId);
			cstmt.setString(4, ipAddress);
			cstmt.setString(5, userId);
			cstmt.setString(6, tagRequestDto.getLicPlate());
			cstmt.setString(7, tagRequestDto.getLicState());
			cstmt.setString(8, tagRequestDto.getVehicleYear());
			cstmt.setString(9, tagRequestDto.getVehicleColor());
			cstmt.setString(10, tagRequestDto.getVehicleMake());
			cstmt.setString(11, tagRequestDto.getVehicleModel());
			cstmt.setString(12, tagRequestDto.getVehicleClassCode());

			Calendar assignDt = tagRequestDto.getAssignedDate();
			Timestamp rentalStartDate = null;
			if (assignDt != null) {
				logger.debug("Inside if (assignDt != null) @ addTag()");
				rentalStartDate = new Timestamp(assignDt.getTimeInMillis());
			}
			cstmt.setTimestamp(13, rentalStartDate);

			Calendar expirDt = tagRequestDto.getExpirDate();
			Timestamp rentalEndDate = null;
			if (expirDt != null) {
				logger.debug("Inside if (expirDt != null) @ addTag()");
				rentalEndDate = new Timestamp(expirDt.getTimeInMillis());
			}
			cstmt.setTimestamp(14, rentalEndDate);

			cstmt.setString(15, (tagRequestDto.isCheckDup()) ? "Y" : "N");
			cstmt.setString(16, (tagRequestDto.isTemporaryLicPlate()) ? "Y"
					: "N");
			Calendar expires = tagRequestDto.getPlateExpirDate();
			Timestamp expirationDate = null;
			if (expires != null) {
				logger.debug("Inside if (expires != null) @ addTag()");
				expirationDate = new Timestamp(expires.getTimeInMillis());
			}
			cstmt.setTimestamp(17, expirationDate);
			logger.debug("posId:" + posId);
			cstmt.setLong(18, (posId == null ? -1 : posId.longValue()));
			cstmt.setString(19, (tagRequestDto.isTagless()) ? "Y" : "N");
			cstmt.setLong(20, tagRequestDto.getTransactionId());
			logger.debug("transId before add tag "
					+ tagRequestDto.getLicPlate() + " is "
					+ tagRequestDto.getTransactionId());

			cstmt.registerOutParameter(21, Types.INTEGER);
			cstmt.registerOutParameter(22, Types.VARCHAR);
			cstmt.registerOutParameter(23, Types.VARCHAR);

			cstmt.registerOutParameter(24, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			int result = cstmt.getInt(1);
			if (result == -1) {
				logger.debug("Inside if (result == -1) @ addTag()");
				logger.error("Error in addTag() @ OracleTagRequestDAO :: security exception in addTag");
				throw new EtccSecurityException("security exception in addTag");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ addTag()");
				logger.error("Error in addTag() @ OracleTagRequestDAO");
				Array errors = (Array) cstmt.getObject(20);
				tagRequestDto.setErrors(convertErrorMsgs(errors));
				return tagRequestDto;
			}

			tagRequestDto.setTransactionId(cstmt.getLong(17));

			String tagSeq = cstmt.getString(18);
			if (tagSeq != null) {
				logger.debug("Inside if (tagSeq != null) @ addTag()");
				tagRequestDto.setAcctTagSeq(Long.parseLong(tagSeq));
			}

			String dupFlag = cstmt.getString(19);
			if (dupFlag != null) {
				logger.debug("Inside if (dupFlag != null) @ addTag()");
				tagRequestDto.setDup(dupFlag.equalsIgnoreCase("y"));
			}
			logger.debug("add tag: " + tagRequestDto);
		} catch (SQLException ex) {
			logger.error("Error in addTag() @ OracleTagRequestDAO: " + ex, ex);
			throw new EtccException("error adding tag " + ex, ex);
		} finally {
			closeConnection();
		}
		logger.info("Leaving addTag() @ OracleTagRequestDAO");
		return tagRequestDto;
	}

	public TagDTO modifyTag(String sessionId, String ipAddress, String userId,
			TagDTO tagRequestDto, String transType, Long posId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering modifyTag() @ OracleTagRequestDAO");
		try {
			setConnection(Util.getDbConnection());
			setErrorTypeMap();
			logger.info("modifyTag() :: Calling OLCSC_ACCT_MGMT.Account_Vehicle_Modify");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Account_Vehicle_Modify(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, tagRequestDto.getAcctId());
			cstmt.setString(3, tagRequestDto.getAcctTagSeq() + "");
			cstmt.setString(4, sessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, userId);
			cstmt.setString(7, tagRequestDto.getLicPlate());
			cstmt.setString(8, tagRequestDto.getLicState());
			cstmt.setString(9, tagRequestDto.getVehicleYear());
			cstmt.setString(10, tagRequestDto.getVehicleColor());
			cstmt.setString(11, tagRequestDto.getVehicleMake());
			cstmt.setString(12, tagRequestDto.getVehicleModel());
			cstmt.setString(13, tagRequestDto.getVehicleClassCode());

			Calendar assignDt = tagRequestDto.getAssignedDate();
			Timestamp rentalStartDate = null;
			if (assignDt != null) {
				logger.debug("Inside if (assignDt != null) @ modifyTag()");
				rentalStartDate = new Timestamp(assignDt.getTimeInMillis());
			}
			cstmt.setTimestamp(14, rentalStartDate);

			Calendar expirDt = tagRequestDto.getExpirDate();
			Timestamp rentalEndDate = null;
			if (expirDt != null) {
				logger.debug("Inside if (expirDt != null) @ modifyTag()");
				rentalEndDate = new Timestamp(expirDt.getTimeInMillis());
			}
			cstmt.setTimestamp(15, rentalEndDate);

			cstmt.setString(16, (tagRequestDto.isCheckDup()) ? "Y" : "N");

			cstmt.setString(17, (tagRequestDto.isTemporaryLicPlate()) ? "Y"
					: "N");

			Calendar expires = tagRequestDto.getPlateExpirDate();
			Timestamp expirationDate = null;
			if (expires != null) {
				logger.debug("Inside if (expires != null) @ modifyTag()");
				expirationDate = new Timestamp(expires.getTimeInMillis());
			}
			cstmt.setTimestamp(18, expirationDate);

			cstmt.setString(19, transType);

			logger.debug("posId:" + posId);

			cstmt.setLong(20, (posId == null ? -1 : posId.longValue()));

			cstmt.registerOutParameter(21, Types.VARCHAR);

			cstmt.registerOutParameter(22, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			logger.debug(tagRequestDto);
			cstmt.execute();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ modifyTag()");
				logger.error("Error in modifyTag() @ OracleTagRequestDAO :: security exception: modifyTag");
				throw new EtccSecurityException(
						"security exception in modifyTag");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ modifyTag()");
				logger.error("Error in modifyTag() @ OracleTagRequestDAO");
				Array errors = (Array) cstmt.getObject(20);
				tagRequestDto.setErrors(convertErrorMsgs(errors));
				return tagRequestDto;
			}

			String dupFlag = cstmt.getString(19);
			if (dupFlag != null) {
				logger.debug("Inside if (dupFlag != null) @ modifyTag()");
				tagRequestDto.setDup(dupFlag.equalsIgnoreCase("y"));
			}

			logger.debug("modify tag: " + tagRequestDto.toString());

		} catch (SQLException sqle) {
			logger.error("Error in modifyTag() @ OracleTagRequestDAO " + sqle,
					sqle);
			throw new EtccException("error modifying tag " + sqle, sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving modifyTag() @ OracleTagRequestDAO");
		return tagRequestDto;
	}

	public boolean confirmAddTags(String sessionId, String ipAddress,
			String userId, String acctId, long transactionId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering confirmAddTags() @ OracleTagRequestDAO");
		try {
			setConnection(Util.getDbConnection());
			setErrorTypeMap();
			logger.info("confirmAddTags() :: Calling OLCSC_ACCT_MGMT.Confirm_add_tags");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Confirm_add_tags(?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, acctId);
			cstmt.setString(3, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(4, sessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, userId);
			cstmt.setLong(7, transactionId);
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ confirmAddTags()");
				logger.error("Error in confirmAddTags() @ OracleTagRequestDAO :: security exception: confirmAddTags");
				throw new EtccSecurityException(
						"security exception in confirmAddTags");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ confirmAddTags()");
				logger.error("Error in confirmAddTags() @ OracleTagRequestDAO");
				Array errors = (Array) cstmt.getObject(8);
				convertErrorMsgs(errors);
				logger.info("Leaving confirmAddTags() @ OracleTagRequestDAO");
				return false;
			}
			logger.info("Leaving confirmAddTags() @ OracleTagRequestDAO");
			return true;
		} catch (SQLException sqle) {
			logger.error("Error in confirmAddTags() @ OracleTagRequestDAO: "
					+ sqle, sqle);
			throw new EtccException("error in confirmAddTags " + sqle, sqle);
		} finally {
			closeConnection();
		}
	}

	public boolean confirmAddTagsTwo(String sessionId, String ipAddress,
			String userId, String acctId, long transactionId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering confirmAddTagsTwo() @ OracleTagRequestDAO");
		try {
			setConnection(Util.getDbConnection());
			setErrorTypeMap();
			logger.info("confirmAddTagsTwo() :: Calling OLCSC_ACCT_MGMT.Confirm_Add_Tags_Two");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Confirm_Add_Tags_Two(?,?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, acctId);
			cstmt.setString(3, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(4, sessionId);
			cstmt.setString(5, ipAddress);
			cstmt.setString(6, userId);
			cstmt.setLong(7, transactionId);
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ confirmAddTagsTwo()");
				logger.error("");
				logger.error("Error in confirmAddTagsTwo() @ OracleTagRequestDAO :: security exception: confirmAddTagsTwo");
				throw new EtccSecurityException(
						"security exception in confirmAddTags");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ confirmAddTagsTwo()");
				logger.error("Error in confirmAddTagsTwo() @ OracleTagRequestDAO");
				Array errors = (Array) cstmt.getObject(8);
				convertErrorMsgs(errors);
				logger.info("Leaving confirmAddTagsTwo() @ OracleTagRequestDAO");
				return false;
			}
			logger.info("Leaving confirmAddTagsTwo() @ OracleTagRequestDAO");
			return true;
		} catch (SQLException sqle) {
			logger.error("Error in confirmAddTagsTwo() @ OracleTagRequestDAO: "
					+ sqle, sqle);
			throw new EtccException("error in confirmAddTagsTwo " + sqle, sqle);
		} finally {
			closeConnection();
		}
	}

	public String addTagsReceipt(String sessionId, String ipAddress,
			String userId, String acctId, String reportFormat)
			throws EtccException, EtccSecurityException {
		logger.info("Entering addTagsReceipt() @ OracleTagRequestDAO");
		try {
			setConnection(Util.getDbConnection());
			setErrorTypeMap();
			logger.info("addTagsReceipt() :: Calling OLCSC_REP.Add_Tag_Receipt_RPT");
			cstmt = conn
					.prepareCall("{? = call OLCSC_REP.Add_Tag_Receipt_RPT(?,?,?,?,?,?,?,?)}");

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, reportFormat);
			cstmt.setLong(3, Long.parseLong(acctId));
			cstmt.setString(4, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(5, sessionId);
			cstmt.setString(6, ipAddress);
			cstmt.setString(7, userId);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			int result = cstmt.getInt(1);

			if (result == -1) {
				logger.debug("Inside if (result == -1) @ addTagsReceipt()");
				logger.error("Error in addTagsReceipt() @ OracleTagRequestDAO :: security exception: addTagsReceipt");
				throw new EtccSecurityException(
						"security exception in addTagsReceipt");
			} else if (result == 0) {
				logger.debug("Inside else if (result == 0) @ addTagsReceipt()");
				logger.error("Error in addTagsReceipt() @ OracleTagRequestDAO");
				Array errors = (Array) cstmt.getObject(9);
				convertErrorMsgs(errors);
				return null;
			}
			String url = cstmt.getString(8);
			logger.info("Leaving addTagsReceipt() @ OracleTagRequestDAO");
			return url;
		} catch (SQLException sqle) {
			logger.error("Error in addTagsReceipt() @ OracleTagRequestDAO: "
					+ sqle, sqle);
			throw new EtccException("Error in addTagsReceipt " + sqle, sqle);
		} finally {
			closeConnection();
		}
	}

	private Collection convertErrorMsgs(Array errors) throws SQLException {
		Collection result = null;
		logger.info("Entering convertErrorMsgs() @ OracleTagRequestDAO");
		if (errors != null) {
			logger.debug("Inside if (errors != null) @ convertErrorMsgs()");
			Object array[] = (Object[]) errors.getArray();
			if (array != null && array.length >= 0) {
				logger.debug("Inside if (array != null && array.length >= 0) @ convertErrorMsgs()");
				result = new ArrayList();
				for (int i = 0; i < array.length; i++) {
					ErrorMsgRec msgRec = (ErrorMsgRec) array[i];
					logger.debug("application error:" + msgRec.getErrMsg());
					result.add(msgRec.getErrMsg());
				}
			}
		} else {
			logger.debug("Inside else of if (errors != null) @ convertErrorMsgs()");
			logger.debug("errors array is null");
		}
		logger.info("Leaving convertErrorMsgs() @ OracleTagRequestDAO");
		return result;
	}

	private void setErrorTypeMap() throws SQLException {
		logger.info("Entering setErrorTypeMap() @ OracleTagRequestDAO");
		Map typeMap = new HashMap();
		typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
		conn.setTypeMap(typeMap);
		logger.info("Leaving setErrorTypeMap() @ OracleTagRequestDAO");
	}

	public int activateTag(AccountLoginDTO dto, TagDTO tagRequestDto)
			throws EtccException, EtccSecurityException {
		logger.info("Entering activateTag() @ OracleTagRequestDAO");
		/*
		 * FUNCTION New_Tag(p_acct_id NUMBER, p_session VARCHAR2, p_ip_address
		 * VARCHAR2, p_user VARCHAR2, p_tag_id VARCHAR2, p_lic_plate VARCHAR2,
		 * p_lic_state VARCHAR2, p_year VARCHAR2, p_color VARCHAR2,
		 * p_vehicle_make VARCHAR2, p_vehicle_model VARCHAR2, p_vehicle_class
		 * VARCHAR2, p_temp_plate VARCHAR2, p_temp_date DATE, p_check_dup
		 * VARCHAR2 DEFAULT 'Y', p_agency_id VARCHAR2, p_trxn IN OUT NUMBER,
		 * o_tag_seq OUT VARCHAR2, o_error_msg_arr OUT olc_error_msg_arr) RETURN
		 * NUMBER;
		 */
		int result;

		try {
			setConnection(Util.getDbConnection());
			setErrorTypeMap();
			logger.info("activateTag() :: Calling OLCSC_ACCT_MGMT.Account_Tag_Add");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.Account_Tag_Add(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setLong(2, dto.getAcctId());
			cstmt.setString(3, dto.getDbSessionId());
			cstmt.setString(4, dto.getLastLoginIp());
			cstmt.setString(5, dto.getLoginId());
			cstmt.setString(6, tagRequestDto.getTagId());
			cstmt.setString(7, tagRequestDto.getLicPlate());
			cstmt.setString(8, tagRequestDto.getLicState());
			cstmt.setString(9, tagRequestDto.getVehicleYear());
			cstmt.setString(10, tagRequestDto.getVehicleColor());
			cstmt.setString(11, tagRequestDto.getVehicleMake());
			cstmt.setString(12, tagRequestDto.getVehicleModel());
			cstmt.setString(13, tagRequestDto.getVehicleClassCode());
			Calendar assignDt = tagRequestDto.getAssignedDate();
			Timestamp rentalStartDate = null;
			if (assignDt != null) {
				logger.debug("Inside if (assignDt != null) @ activateTag()");
				rentalStartDate = new Timestamp(assignDt.getTimeInMillis());
			}
			cstmt.setTimestamp(14, rentalStartDate);

			Calendar expirDt = tagRequestDto.getExpirDate();
			Timestamp rentalEndDate = null;
			if (expirDt != null) {
				logger.debug("Inside if (expirDt != null) @ activateTag()");
				rentalEndDate = new Timestamp(expirDt.getTimeInMillis());
			}
			cstmt.setTimestamp(15, rentalEndDate);
			cstmt.setString(16, (tagRequestDto.isTemporaryLicPlate()) ? "Y"
					: "N");

			Calendar expires = tagRequestDto.getPlateExpirDate();
			Timestamp expirationDate = null;
			if (expires != null) {
				logger.debug("Inside if (expires != null) @ activateTag()");
				expirationDate = new Timestamp(expires.getTimeInMillis());
			}

			cstmt.setTimestamp(17, expirationDate);
			cstmt.setString(18, (tagRequestDto.isCheckDup()) ? "Y" : "N");
			logger.debug("transId before add tag "
					+ tagRequestDto.getLicPlate() + " is "
					+ tagRequestDto.getTransactionId());
			cstmt.setString(19, tagRequestDto.getAgencyId());
			if (tagRequestDto.getTransactionId() == 0) {
				logger.debug("Inside if (tagRequestDto.getTransactionId() == 0)) @ activateTag()");
				cstmt.setNull(20, Types.INTEGER);
			} else {
				logger.debug("Inside else of if (tagRequestDto.getTransactionId() == 0)) @ activateTag()");
				cstmt.setLong(20, tagRequestDto.getTransactionId());
			}
			cstmt.registerOutParameter(21, Types.VARCHAR);
			cstmt.registerOutParameter(22, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();
			result = cstmt.getInt(1);
			if (result == -1) {
				logger.debug("Inside if (result == -1) @ activateTag()");
				logger.error("Error in activateTag() @ OracleTagRequestDAO security exception :: activateTag");
				throw new EtccSecurityException(
						"security exception in activateTag");
			} else if (result == 1) {
				logger.debug("Inside else if (result == 1) @ activateTag()");
				tagRequestDto.setAcctTagSeq(cstmt.getLong(19));
			}
			logger.debug("activate tag: " + tagRequestDto);
		} catch (SQLException sqle) {
			logger.error("Error in activateTag() @ OracleTagRequestDAO: "
					+ sqle, sqle);
			throw new EtccException("error activating tag " + sqle, sqle);
		} finally {
			closeConnection();
		}
		logger.info("Leaving activateTag() @ OracleTagRequestDAO");
		return result;
	}

	public TagDTO[] mainAccountVehicleTag(AccountLoginDTO dto,
			TagDTO tagRequestDto, String action) {
		/*
		 * Olcsc_Acct_Mgmt.Maintain_Acct_Veh_Tag(i_acct_id NUMBER, i_session
		 * VARCHAR2, i_ip_address VARCHAR2, i_user VARCHAR2, i_mod_flag CHAR, --
		 * This flag is to indicate if add or modify is called. When this is
		 * called from Edit screen, it should have a value of Y, else N
		 * i_account_veh_arr IN OUT OLC_ACCOUNT_VEH_ARR, i_account_veh_err OUT
		 * OLC_ACCOUNT_VEH_ARR, i_event_id events.event_id%type, o_error_msg_arr
		 * OUT olc_error_msg_arr)
		 */
		logger.info("Entering mainAccountVehicleTag() @ OracleTagRequestDAO");
		logger.info("Leaving mainAccountVehicleTag() @ OracleTagRequestDAO");
		return new TagDTO[10];
	}
}
