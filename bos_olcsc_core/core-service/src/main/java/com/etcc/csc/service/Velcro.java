package com.etcc.csc.service;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.VelcroDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.VelcroDTO;

public class Velcro implements VelcroInterface {
	private Logger logger = Logger.getLogger(Velcro.class);

	public Velcro() {
	}

	public VelcroDTO getVelcroInfo(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VelcroDAO velcroDao = daoFactory.getVelcroDAO();
			return velcroDao.getVelcroInfo(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getVelcroInfo() " + ese, ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getVelcroInfo() " + ee, ee);
			throw ee;
		}
	}

	public Collection submitVelcroRequest(AccountLoginDTO acctLoginDto, int qty)
			throws EtccException, EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VelcroDAO velcroDao = daoFactory.getVelcroDAO();
			return velcroDao.submitVelcroRequest(acctLoginDto, qty);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in submitVelcroRequest() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in submitVelcroRequest " + ee, ee);
			throw ee;
		}
	}

	public String getVelcroReceiptPDF(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {

		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VelcroDAO velcroDao = daoFactory.getVelcroDAO();
			return velcroDao.getVelcroReceiptPDF(acctLoginDto);
		} catch (EtccSecurityException ese) {
			logger.error("Security exception in getVelcroReceiptPDF() " + ese,
					ese);
			throw ese;
		} catch (EtccException ee) {
			logger.error("Exception in getVelcroReceiptPDF " + ee, ee);
			throw ee;
		}
	}

	/**
	 * Stub only to force the inclusion of errorMessageDTO.
	 * 
	 * @return
	 */
	public ErrorMessageDTO getErrorMessage() {
		return null;
	}
}