package com.etcc.csc.dao;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.VelcroDTO;
import com.etcc.csc.enums.EventEnum;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;

public class OracleVelcroDAO extends VelcroDAO {

	private Logger logger = Logger.getLogger(OracleVelcroDAO.class);

	public OracleVelcroDAO() {
	}

	/**
	 * Retrieves the account's personal info prior to ordering velcro.
	 * 
	 * @return VelcroDTO
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public VelcroDTO getVelcroInfo(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getVelcroInfo() @ OracleVelcroDAO");
		VelcroDTO velcroDto = null;
		Collection result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getVelcroInfo() :: Calling Olcsc_Acct_Mgmt.get_velcro");
			cstmt = conn.prepareCall("{? = call Olcsc_Acct_Mgmt.get_velcro("
					+ "?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);// return number

			cstmt.setLong(idx++, acctLoginDto.getAcctId()); // p_acct_id
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());// p_session
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp()); // p_ip_address
			cstmt.setString(idx++, acctLoginDto.getLoginId()); // p_user
			cstmt.setInt(idx++, -1); // p_sets_ordered

			logger.debug("Inside getVelcroInfo() @ OracleVelcroDAO :: Account Id ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside getVelcroInfo() @ OracleVelcroDAO :: Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside getVelcroInfo() @ OracleVelcroDAO :: Last Login IP Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			logger.debug("Inside getVelcroInfo() @ OracleVelcroDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());

			cstmt.setLong(idx++, EventEnum.GET_VELCRO_REQUEST.getId()); // P_event_id
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // fullname
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // companyName
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // address1
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // address2
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // city
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // state
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // zipCode
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // plus4
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // bad addr flag
			cstmt.registerOutParameter(idx++, Types.INTEGER); // p_max_allow_velcro_cnt
			cstmt.registerOutParameter(idx++, Types.INTEGER); // vehicleCnt
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR"); // error
			cstmt.execute();

			short found = cstmt.getShort(1);
			logger.debug("Inside getVelcroInfo() @ OracleVelcroDAO :: Output ::-->"
					+ found);
			if (found == 1) {
				velcroDto = new VelcroDTO();
				velcroDto.setAcctId(acctLoginDto.getAcctId());
				velcroDto.setActiveTolltag(cstmt.getInt(idx - 1));
				velcroDto.setMaxAllowed(cstmt.getInt(idx - 2));
				velcroDto.setPlus4(cstmt.getString(idx - 4));
				velcroDto.setZipCode(cstmt.getString(idx - 5));
				velcroDto.setState(cstmt.getString(idx - 6));
				velcroDto.setCity(cstmt.getString(idx - 7));
				velcroDto.setAddress2(cstmt.getString(idx - 8));
				velcroDto.setAddress1(cstmt.getString(idx - 9));
				velcroDto.setCompanyName(cstmt.getString(idx - 10));
				velcroDto.setName(cstmt.getString(idx - 11));
			} else if (found == -1) {
				logger.error("Error in getVelcroInfo() @ OracleVelcroDAO :: Security Exception");
				throw new EtccSecurityException("Security Exception in "
						+ "getVelcroInfo()");
			} else if (found == 0) {
				Array errors = (Array) cstmt.getObject(idx);
				result = Util.convertErrorMsgs(errors);
				if (result == null || result.size() == 0) {
					result = new ArrayList();
					ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
					errMsgDto
							.setMessage("Could not process request, please try again.");
					result.add(errMsgDto);
				}
				velcroDto = new VelcroDTO();
				velcroDto.setErrors(result);
			}// end of if-else()
		} catch (SQLException sqle) {
			logger.error("Error in getVelcroInfo() @ OracleVelcroDAO ", sqle);
			throw new EtccException("Error running getVelcroInfo: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getVelcroInfo() @ OracleVelcroDAO");
		return velcroDto;
	}// end of getVelcroInfo()

	public Collection submitVelcroRequest(AccountLoginDTO acctLoginDto, int qty)
			throws EtccException, EtccSecurityException {
		logger.info("Entering submitVelcroRequest() @ OracleVelcroDAO");
		Collection col = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("submitVelcroRequest() :: Calling Olcsc_Acct_Mgmt.get_velcro");
			cstmt = conn.prepareCall("{? = call Olcsc_Acct_Mgmt.get_velcro("
					+ "?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);// return number
			cstmt.setLong(idx++, acctLoginDto.getAcctId()); // p_acct_id
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());// p_session
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());// p_ip_address
			cstmt.setString(idx++, acctLoginDto.getLoginId());// p_user
			cstmt.setInt(idx++, qty);// p_sets_ordered

			logger.debug("Inside submitVelcroRequest() @ OracleVelcroDAO :: Account Id ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside submitVelcroRequest() @ OracleVelcroDAO :: Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside submitVelcroRequest() @ OracleVelcroDAO :: Last Login IP Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			logger.debug("Inside submitVelcroRequest() @ OracleVelcroDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside submitVelcroRequest() @ OracleVelcroDAO :: Quantity ::-->"
					+ qty);
			logger.debug("Inside submitVelcroRequest() @ OracleVelcroDAO :: Event Id ::-->"
					+ EventEnum.GET_VELCRO_REQUEST.getId());

			cstmt.setLong(idx++, EventEnum.GET_VELCRO_REQUEST.getId());// P_event_id
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // fullname
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // companyName
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // address1
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // address2
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // city
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // state
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // zipCode
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // plus4
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // bad addr flag
			cstmt.registerOutParameter(idx++, Types.INTEGER); // p_max_allow_velcro_cnt
			cstmt.registerOutParameter(idx++, Types.INTEGER); // vehicleCnt
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR"); // error
			cstmt.execute();
		} catch (SQLException sqle) {
			logger.error("Error in submitVelcroRequest() @ OracleVelcroDAO ",
					sqle);
			throw new EtccException("Error running submitVelcroRequest: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving submitVelcroRequest() @ OracleVelcroDAO");
		return col;
	}// end of submitVelcroRequest()

	/**
	 * Returns the url that will generate a PDF velcro receipt.
	 * 
	 * @param acctLoginDto
	 * @return
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public String getVelcroReceiptPDF(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getVelcroReceiptPDF() @ OracleVelcroDAO");
		String result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getVelcroReceiptPDF() :: Calling olcsc_rep.Velcro_Receipt_PDF");
			cstmt = conn.prepareCall("{? = call "
					+ "olcsc_rep.Velcro_Receipt_PDF(?, ?, ?, ?, ?, ?, ?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, "PDF");
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			logger.debug("Inside getVelcroReceiptPDF() @ OracleVelcroDAO :: Account Id ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside getVelcroReceiptPDF() @ OracleVelcroDAO :: Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside getVelcroReceiptPDF() @ OracleVelcroDAO :: Last Login IP Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			logger.debug("Inside getVelcroReceiptPDF() @ OracleVelcroDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside getVelcroReceiptPDF() @ OracleVelcroDAO :: Login Account Type ::-->"
					+ Constants.LOGIN_TYPE_ACCOUNT);
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // report_url
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR"); // error
			cstmt.execute();
			short success = cstmt.getShort(1);
			logger.debug("Inside getVelcroReceiptPDF() @ OracleVelcroDAO :: Output ::-->"
					+ success);
			if (success == 1) {
				result = cstmt.getString(idx - 1);
			} else if (success == -1) {
				logger.error("Error in getVelcroReceiptPDF() @ OracleVelcroDAO :: Security Exception");
				throw new EtccSecurityException("Security Exception in "
						+ "getVelcroReceiptPDF()");
			}// end of if-else()
		} catch (SQLException sqle) {
			logger.error("Error in getVelcroReceiptPDF() @ OracleVelcroDAO ",
					sqle);
			throw new EtccException("Error running getVelcroReceiptPDF: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getVelcroReceiptPDF() @ OracleVelcroDAO");
		return result;
	}// end of getVelcroReceiptPDF()

}// end of OracleVelcroDAO Class
