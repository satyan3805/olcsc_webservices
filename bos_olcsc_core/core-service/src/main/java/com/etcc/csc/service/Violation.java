package com.etcc.csc.service;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Logger;
import com.etcc.csc.dao.ViolationDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolationDTO;
import com.etcc.csc.dto.ViolatorDTO;

/**
 * Exposes Violation functionality through web service.
 */
public class Violation implements ViolationInterface {
	Logger logger = Logger.getLogger(Violation.class);

	public Violation() {
	}

	public AccountLoginDTO loginViolator(String sessionId, String ipAddress,
			UserEnvDTO userEnvDto, String invoiceId, String collectionsId,
			String licPlate, String licState) throws EtccException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			ViolationDAO violationDao = daoFactory.getViolationDAO();
			return violationDao.loginViolator(sessionId, ipAddress, userEnvDto,
					invoiceId, collectionsId, licPlate, licState);
		} catch (Exception e) {
			logger.error("Error loggin in violator " + invoiceId + e, e);
			throw new EtccException(e);
		}
	}

	public AccountLoginDTO loginZipCash(String sessionId, String ipAddress,
			UserEnvDTO userEnvDto, String invoiceId, String acctId,
			String licPlate, String licState) throws EtccException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			ViolationDAO violationDao = daoFactory.getViolationDAO();
			return violationDao.loginZipCash(sessionId, ipAddress, userEnvDto,
					invoiceId, acctId, licPlate, licState);
		} catch (Exception e) {
			logger.error("Error logging in zipCash " + invoiceId + "/" + acctId
					+ e, e);
			throw new EtccException(e);
		}
	}

	public ViolatorDTO getViolations(AccountLoginDTO acctLoginDto,
			String invoiceId, String collectionsId, String licPlate,
			String licState) throws EtccException, EtccSecurityException {

		logger.debug("Start");
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			ViolationDAO violationDao = daoFactory.getViolationDAO();
			return violationDao.getViolations(acctLoginDto, invoiceId,
					collectionsId, licPlate, licState);
		} catch (EtccSecurityException ese) {
			logger.error(
					"Security exception in getInvoices() "
							+ acctLoginDto.getAcctId() + " " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			String issueId = logger.error("Exception in getInvoices() "
					+ acctLoginDto.getAcctId() + " " + ee, ee, true);
			ee.setRelatedIssueId(issueId);
			throw ee;
		}
	}

	/**
	 * Stub only to force the inclusion of ViolationDTO in the wsdl.
	 * 
	 * @return
	 */
	public ViolationDTO getViolation() {
		return null;
	}

	/**
	 * Stub only to force the inclusion of InvoiceDTO in the wsdl.
	 * 
	 * @return
	 */
	public InvoiceDTO getInvoice() {
		return null;
	}

	/**
	 * Stub only.
	 * 
	 * @return
	 */
	public ErrorMessageDTO getError() {
		return null;
	}
}