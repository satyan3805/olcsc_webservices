package com.etcc.csc.dao;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Constants;
import com.etcc.csc.common.DateUtil;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolationDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;

/**
 * Oracle implementation of the Menu DAO.
 */
public class OracleViolationDAO extends ViolationDAO {
	private Logger logger = Logger.getLogger(OracleViolationDAO.class);

	public OracleViolationDAO() {
	}

	/**
	 * Validates if an invoice exists for a given invoice id, license plate, and
	 * license state.
	 * 
	 * @param String
	 *            sessionId
	 * @param String
	 *            ipAddress
	 * @param UserEnvDTO
	 *            userEnvDto
	 * @param String
	 *            invoiceId
	 * @param String
	 *            collectionsId
	 * @param String
	 *            licPlate
	 * @param String
	 *            licState
	 * @return AccountLoginDTO
	 * @throws EtccException
	 */
	public AccountLoginDTO loginViolator(String sessionId, String ipAddress,
			UserEnvDTO userEnvDto, String invoiceId, String collectionsId,
			String licPlate, String licState) throws EtccException {
		logger.info("Entering loginViolator() @ OracleViolationDAO");
		AccountLoginDTO acctLoginDto = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("loginViolator() :: Calling OLCSC_LOGIN.LOGIN_VPS");
			cstmt = conn
					.prepareCall("{? = call OLCSC_LOGIN.LOGIN_VPS(?, ?, ?, ?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			byte idx = 1;
			if (userEnvDto == null) {
				userEnvDto = new UserEnvDTO();
			}
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, Constants.LOGIN_GENERIC_USER); // user
			cstmt.setString(idx++, invoiceId); // password
			cstmt.setString(idx++, sessionId); // session id
			cstmt.setString(idx++, ipAddress); // ip address
			cstmt.setString(idx++, userEnvDto.getBrowserType());
			cstmt.setString(idx++, userEnvDto.getBrowserVersion());
			cstmt.setString(idx++, userEnvDto.getOsType());
			cstmt.setString(idx++, userEnvDto.getOsVersion());
			cstmt.setString(idx++, userEnvDto.getAttribute1());
			cstmt.setString(idx++, userEnvDto.getAttribute2());
			cstmt.setString(idx++, userEnvDto.getAttribute3());
			cstmt.setString(idx++, userEnvDto.getAttribute4());
			cstmt.setString(idx++, userEnvDto.getAttribute5());
			cstmt.setString(idx++, userEnvDto.getLocale());
			cstmt.registerOutParameter(idx++, Types.NUMERIC); // acct id
			cstmt.setString(idx++, invoiceId);
			cstmt.setString(idx++, collectionsId);
			cstmt.setString(idx++, licPlate);
			cstmt.setString(idx++, licState);
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Invoice Id ::-->"
					+ invoiceId);
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Session Id ::-->"
					+ sessionId);
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: IP Address ::-->"
					+ ipAddress);
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Browser Type ::-->"
					+ userEnvDto.getBrowserType());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Browser Version ::-->"
					+ userEnvDto.getBrowserVersion());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: OS Type ::-->"
					+ userEnvDto.getOsType());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: OS Version ::-->"
					+ userEnvDto.getOsVersion());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Attribute 1 ::-->"
					+ userEnvDto.getAttribute1());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Attribute 2::-->"
					+ userEnvDto.getAttribute2());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Attribute 3::-->"
					+ userEnvDto.getAttribute3());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Attribute 4::-->"
					+ userEnvDto.getAttribute4());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Attribute 5 ::-->"
					+ userEnvDto.getAttribute5());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Locale ::-->"
					+ userEnvDto.getLocale());
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Collection Id ::-->"
					+ collectionsId);
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: License Plate ::-->"
					+ licPlate);
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: License State ::-->"
					+ licState);
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // session id
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.executeUpdate();
			int exists = cstmt.getInt(1);
			logger.debug("Inside loginViolator() @ OracleViolationDAO :: Output Value ::-->"
					+ exists);
			if (exists == 1) {
				acctLoginDto = new AccountLoginDTO();
				acctLoginDto.setAcctId(cstmt.getLong(idx - 6));
				acctLoginDto.setLastLoginIp(ipAddress);
				if (invoiceId != null && invoiceId.trim().length() > 0) {
					acctLoginDto.setLoginType(Constants.LOGIN_TYPE_INVOICE);
					acctLoginDto.setInvoiceId(Long.parseLong(invoiceId));
				} else {
					acctLoginDto
							.setLoginType(Constants.LOGIN_TYPE_COLLECTION_AGENCY);
					acctLoginDto.setInvoiceId(Long.parseLong(collectionsId));
				}
				acctLoginDto.setDbSessionId(cstmt.getString(idx - 1));
				acctLoginDto.setLoginId(Constants.LOGIN_GENERIC_USER);
				acctLoginDto.setLicPlate(licPlate);
				acctLoginDto.setLicState(licState);
			} else {
				// get error message
				acctLoginDto = new AccountLoginDTO();
				acctLoginDto.setErrors(Util.convertErrorMsgs(cstmt
						.getArray(idx)));
			}// end of if-else()
		} catch (Throwable t) {
			logger.error("Error in loginViolator() @ OracleViolationDAO ", t);
			throw new EtccException("Error checking invoice: " + t, t);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving loginViolator() @ OracleViolationDAO");
		return acctLoginDto;
	}// end of loginViolator()

	public AccountLoginDTO loginZipCash(String sessionId, String ipAddress,
			UserEnvDTO userEnvDto, String invoiceId, String acctId,
			String licPlate, String licState) throws EtccException {
		logger.info("Entering loginZipCash() @ OracleViolationDAO");
		AccountLoginDTO acctLoginDto = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("loginZipCash() :: Calling OLCSC_LOGIN.LOGIN_VB");
			cstmt = conn
					.prepareCall("{? = call OLCSC_LOGIN.LOGIN_VB(?, ?, ?, ?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			byte idx = 1;
			if (userEnvDto == null) {
				userEnvDto = new UserEnvDTO();
			}
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, Constants.LOGIN_GENERIC_USER); // user
			cstmt.setString(idx++, invoiceId); // password
			cstmt.setString(idx++, sessionId); // session id
			cstmt.setString(idx++, ipAddress); // ip address
			cstmt.setString(idx++, userEnvDto.getBrowserType());
			cstmt.setString(idx++, userEnvDto.getBrowserVersion());
			cstmt.setString(idx++, userEnvDto.getOsType());
			cstmt.setString(idx++, userEnvDto.getOsVersion());
			cstmt.setString(idx++, userEnvDto.getAttribute1());
			cstmt.setString(idx++, userEnvDto.getAttribute2());
			cstmt.setString(idx++, userEnvDto.getAttribute3());
			cstmt.setString(idx++, userEnvDto.getAttribute4());
			cstmt.setString(idx++, userEnvDto.getAttribute5());
			cstmt.setString(idx++, userEnvDto.getLocale());
			cstmt.registerOutParameter(idx++, Types.NUMERIC); // acct id
			cstmt.setString(idx++, invoiceId);
			cstmt.setString(idx++, acctId);
			cstmt.setString(idx++, licPlate);
			cstmt.setString(idx++, licState);
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Invoice Id ::-->"
					+ invoiceId);
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Session Id ::-->"
					+ sessionId);
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: IP Address ::-->"
					+ ipAddress);
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Browser Type ::-->"
					+ userEnvDto.getBrowserType());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Browser Version ::-->"
					+ userEnvDto.getBrowserVersion());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: OS Type ::-->"
					+ userEnvDto.getOsType());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: OS Version ::-->"
					+ userEnvDto.getOsVersion());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Attribute 1 ::-->"
					+ userEnvDto.getAttribute1());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Attribute 2::-->"
					+ userEnvDto.getAttribute2());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Attribute 3::-->"
					+ userEnvDto.getAttribute3());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Attribute 4::-->"
					+ userEnvDto.getAttribute4());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Attribute 5 ::-->"
					+ userEnvDto.getAttribute5());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Locale ::-->"
					+ userEnvDto.getLocale());
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Account Id ::-->"
					+ acctId);
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: License Plate ::-->"
					+ licPlate);
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: License State ::-->"
					+ licState);
			cstmt.registerOutParameter(idx++, Types.VARCHAR); // session id
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.executeUpdate();
			int exists = cstmt.getInt(1);
			logger.debug("Inside loginZipCash() @ OracleViolationDAO :: Output Value ::-->"
					+ exists);
			if (exists == 1) {
				acctLoginDto = new AccountLoginDTO();
				acctLoginDto.setAcctId(cstmt.getLong(idx - 6));
				acctLoginDto.setLastLoginIp(ipAddress);

				if (invoiceId != null && invoiceId.trim().length() > 0) {
					acctLoginDto
							.setLoginType(Constants.LOGIN_TYPE_ZIPCASH_INVOICE);
					acctLoginDto.setInvoiceId(Long.parseLong(invoiceId));
					// acctLoginDto.setAcctId(Long.parseLong(invoiceId));
				} else {
					acctLoginDto
							.setLoginType(Constants.LOGIN_TYPE_ZIPCASH_ACCOUNT);
					// acctLoginDto.setInvoiceId(Long.parseLong(acctId));
					acctLoginDto.setAcctId(Long.parseLong(acctId));
				}
				acctLoginDto.setDbSessionId(cstmt.getString(idx - 1));
				acctLoginDto.setLoginId(Constants.LOGIN_GENERIC_USER);
				acctLoginDto.setLicPlate(licPlate);
				acctLoginDto.setLicState(licState);
			} else {
				// get error message
				acctLoginDto = new AccountLoginDTO();
				acctLoginDto.setErrors(Util.convertErrorMsgs(cstmt
						.getArray(idx)));
			}// end of if-else()
		} catch (Throwable t) {
			logger.error("Error in loginZipCash() @ OracleViolationDAO ", t);
			throw new EtccException("Error checking invoice: " + t, t);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving loginZipCash() @ OracleViolationDAO");
		return acctLoginDto;
	}// end of loginZipCash()

	public ViolatorDTO getViolations(AccountLoginDTO acctLoginDto,
			String invoiceId, String collectionsId, String licPlate,
			String licState) throws EtccException, EtccSecurityException {
		logger.info("Entering getViolations() @ OracleViolationDAO");
		StringBuffer sb1 = new StringBuffer();
		sb1.append("   select violator_id ");
		sb1.append("   from   viol_invoices@vps.world ");
		sb1.append("   where  viol_invoice_id = ?");
		StringBuffer sb = new StringBuffer();
		sb.append("select inv.viol_invoice_id, inv.invoice_date, inv.curr_due_date, viol.violator_fname, ");
		sb.append("       viol.violator_lname, viol.lic_plate_nbr, viol.lic_plate_state, ");
		sb.append("       inv.invoice_amount, v.violation_id, v.viol_time, l.name lane_name ");
		sb.append("from   viol_invoices@vps.world inv, ");
		sb.append("       violators@vps.world viol, ");
		sb.append("       viol_invoice_viol@vps.world viv, ");
		sb.append("       violations@vps.world v, ");
		sb.append("       lanes@vps.world l ");
		sb.append("where viol.violator_id = ? ");
		sb.append("and   viol.violator_id = inv.violator_id ");
		sb.append("and   viv.viol_invoice_id = inv.viol_invoice_id ");
		sb.append("and   v.violation_id = viv.violation_id ");
		sb.append("and   l.lane_id = v.lane_id ");
		ViolatorDTO violatorDto = null;
		try {
			setConnection(Util.getDbConnection());
			ps = conn.prepareStatement(sb1.toString());
			ps.setString(1, invoiceId);
			rs = ps.executeQuery();
			long violatorId = 0;
			if (rs.next()) {
				violatorId = rs.getLong(1);
			}
			ps = conn.prepareStatement(sb.toString());
			logger.debug("Inside getViolations() @ OracleViolationDAO :: SQL ::-->"
					+ sb.toString());
			ps.setLong(1, violatorId);
			rs = ps.executeQuery();
			boolean hasNext = rs.next();
			double totalInvoicedAmt = 0;
			double totalInvoicedVeaAmt = 0;
			if (hasNext) {
				violatorDto = new ViolatorDTO();
				violatorDto.setFirstName(rs.getString("violator_fname"));
				violatorDto.setLastName(rs.getString("violator_lname"));
				violatorDto.setLicPlateNbr(rs.getString("lic_plate_nbr"));
				violatorDto.setLicPlateState(rs.getString("lic_plate_state"));
			}
			while (hasNext) {
				InvoiceDTO invDto = new InvoiceDTO();
				long invId = rs.getLong("viol_invoice_id");
				invDto.setInvoiceId(invId);
				invDto.setInvoiceDate(DateUtil.timestampToCalendar(rs
						.getTimestamp("invoice_date")));
				invDto.setCurrDueDate(DateUtil.timestampToCalendar(rs
						.getTimestamp("curr_due_date")));
				invDto.setInvoiceAmount(rs.getDouble("invoice_amount"));
				invDto.setVeaAmount(rs.getDouble("invoice_amount"));
				violatorDto.addInvoice(invDto);
				totalInvoicedAmt += invDto.getInvoiceAmount();
				totalInvoicedVeaAmt += invDto.getVeaAmount();
				while (hasNext && invId == rs.getLong("viol_invoice_id")) {
					ViolationDTO violDto = new ViolationDTO();
					violDto.setViolationId(rs.getLong("violation_id"));
					violDto.setTime(rs.getString("viol_time"));
					violDto.setLaneDescr(rs.getString("lane_name"));
					invDto.addViolation(violDto);

					hasNext = rs.next();
				}
			}
			violatorDto.setTotalInvoiceAmount(totalInvoicedAmt);
			violatorDto.setTotalInvoiceVeaAmount(totalInvoicedVeaAmt);
		} catch (SQLException sqlEx) {
			logger.error("Error in getViolations() @ OracleViolationDAO ",
					sqlEx);
			throw new EtccException("Error getting violations: " + sqlEx, sqlEx);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getViolations() @ OracleViolationDAO");
		return violatorDto;
	}// end of getViolations(()

}// end of OracleViolationDAO Class