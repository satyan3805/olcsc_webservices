/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.dummy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.ViolationDAO;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.BillingInfoDTO;

import com.etcc.csc.dto.CheckEligibilityResponseDTO;
import com.etcc.csc.dto.DocumentDTO;
import com.etcc.csc.dto.GetViolationResponseDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.MakeViolationPaymentRequest;
import com.etcc.csc.dto.PaymentPlanDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.SearchOpenViolationResponse;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolationDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.service.AccountFactory;
import com.etcc.csc.service.ViolationTestImpl;

/**
 * @author Stephen Davidson
 *
 */
public class DummyViolationDAO extends ViolationDAO {
    private static final Logger logger = Logger.getLogger(DummyViolationDAO.class);

    /** 
     * Lic. Plate for test data.
     */
    public static final String V_LIC_PLATE = "123abc";
    /**
     * State for test plate.
     */
    public static final String V_STATE = "TX";
    /**
     * Invoice with test data.
     */
    public static final String V_INVOICE = "0000101";
    /**
     * Invoice with test dbsessionId.
     */
    public static final String V_DB_SESSION_ID = "";
    /** 
     * @see com.etcc.csc.service.ViolationInterface#emailReceipt(com.etcc.csc.dto.ViolatorDTO, AccountPaymentMethodDTO, java.lang.String)
     */
    public ViolatorDTO emailReceipt(ViolatorDTO violator, AccountPaymentMethodDTO paymentMethod,
            String email) throws EtccException, EtccSecurityException {
        return new ViolationTestImpl().emailReceipt(violator, paymentMethod, email);
    }

    /** 
     * @see com.etcc.csc.service.ViolationInterface#getInvoiceDoc(com.etcc.csc.dto.ViolatorDTO, com.etcc.csc.dto.InvoiceDTO)
     */
    public DocumentDTO getInvoiceDoc(ViolatorDTO violatorDTO, InvoiceDTO invoiceDTO) throws EtccException,
            EtccSecurityException {
        return new ViolationTestImpl().getInvoiceDoc(violatorDTO, invoiceDTO);
        //end getInvoiceDoc
        
    }

    /**
     * @see com.etcc.csc.service.ViolationInterface#getInvoices(com.etcc.csc.dto.AccountLoginDTO, java.lang.String, java.lang.String, java.lang.String, AgencyEnum)
     */
	@Override
	protected ViolatorDTO loadInvoices(ViolatorDTO violatorDTO, String ipAddress, String licPlate, String licState, AgencyEnum agency) throws EtccSecurityException, SQLException {
        //return null;
        throw new RuntimeException("getInvoices not implemented yet.");
        //end getInvoices
    }

    /** 
     * @see com.etcc.csc.service.ViolationInterface#getPaymentPlan(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, long)
     */
    public PaymentPlanDTO getPaymentPlan(String licKey, String invoiceID, String licPlate, String sessionID,
            String ipAddress, long acctID) throws EtccException, EtccSecurityException {

        //return null;
        throw new RuntimeException("getPaymentPlan not implemented yet.");
        //end getPaymentPlan

    }

	@Override
	public ViolatorDTO loginViolator(AccountLoginDTO acctLoginDto,
			String sessionId, String ipAddress, UserEnvDTO userEnvDto,
			String invoiceId, String licPlate, String licState,String dbSessionId)
			throws EtccException {
        logger.debug("Invoice: " + invoiceId);
        ViolatorDTO dto;
        if (licPlate != null && licPlate.equals(AccountFactory.LICENSE_PLATE)){
            dto = new ViolationTestImpl().loginViolator(acctLoginDto, sessionId, ipAddress, userEnvDto, invoiceId, licPlate, licState,dbSessionId);
        } else {
            dto = new ViolatorDTO();
            dto.setAccountLoginDTO(acctLoginDto == null ? loginVPS(ipAddress, invoiceId, sessionId) : acctLoginDto);
            if (invoiceId != null && invoiceId.equals(V_INVOICE)){
                dto.addInvoices(makeInvoices(AgencyEnum.AGENCY_HARRIS_COUNTY, "100"));
                dto.addViolation(AgencyEnum.AGENCY_HARRIS_COUNTY, new ViolationDTO());
            }
        }
        logger.debug("Invoice total: " + dto.getInvoiceCount());
        return dto;
	}

    private AccountLoginDTO loginVPS(String ipAddress, String invoiceId, String sessionId){
        AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
        accountLoginDTO.setDbSessionId(sessionId);
        accountLoginDTO.setLoginId(AccountLoginDTO.GENERIC_USER);
        accountLoginDTO.setLastLoginIp(ipAddress);
        accountLoginDTO.setInvoiceId(invoiceId);
        return accountLoginDTO;
    }

    private List<InvoiceDTO> makeInvoices(final AgencyEnum agency, String idPrefix){
        final int size = 3;
        List<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>(size);
        for(int idx = 0; idx < size; idx++){
            InvoiceDTO invoice = new InvoiceDTO(agency);
            invoice.setInvoiceId(idPrefix + idx);
            invoice.setInvoiceDue(20);
            invoice.setInvoiceDate(Calendar.getInstance());
            invoice.addViolation(new ViolationDTO(idx, Calendar.getInstance(), agency + "J1", 1));
            invoices.add(invoice);
        }
        return invoices;
    }

    /** 
     * @see com.etcc.csc.service.ViolationInterface#makePayment(com.etcc.csc.dto.ViolatorDTO, AccountPaymentMethodDTO, java.lang.String)
     */
    /*public ViolatorDTO makePayment(ViolatorDTO violatorDTO, AccountPaymentMethodDTO paymentMethod, String ipAddress)
            throws EtccException, EtccSecurityException {
        if (violatorDTO == null) {
            throw new NullPointerException("Missing ViolatorDTO");
        } else if (paymentMethod == null) {
            throw new NullPointerException("Missing Payment object.");
        }//else
        return new ViolationTestImpl().makePayment(violatorDTO, paymentMethod, ipAddress);
    }*/
    
    public ResultDTO makePayment(ViolatorDTO violatorDTO, BillingInfoDTO billingInfoDTO, BigDecimal amout, String email) throws EtccException ,EtccSecurityException {return null;};

	@Override
	protected AccountLoginDTO loginVPS(String sessionId, String ipAddress,
			String invoiceId, String licPlate, String licState,
			ViolatorDTO violatorDTO,String sourceUserName,String dbSessionId) throws SQLException {
		return null;
	}
	public ResultDTO makeInvoicePaymentCreditCard(AccountLoginDTO acctLoginDto,
			AccountCreditCardDTO paymentMethodDTO, ViolatorDTO violatorDTO,
			BigDecimal paymentAmount, String email) throws EtccException,
			EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}
	public ResultDTO makeInvoicePaymentEFT(AccountLoginDTO acctLoginDto,
			AccountEFTDTO paymentMethodDTO, ViolatorDTO violatorDTO,
			BigDecimal paymentAmount, String email) throws EtccException,
			EtccSecurityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AccountLoginDTO loginVPS1(String sessionId, String ipAddress,
			String invoiceId, String licPlate, String licState,
			ViolatorDTO violatorDTO, String sourceUserName, String dbSessionId)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public CheckEligibilityResponseDTO checkEligibility(String licPlate, String licState, String invoiceNo,
			Long accountId, String ipAddress, String jsessionId, String sourceUserName, String user, String sessionId,
			Integer PmtPlanId, String source) throws EtccException {
		return null;

	}

	public SearchOpenViolationResponse searchOpenViolations(String licPlate, String licState) throws EtccException {
		return null;
	}

	public GetViolationResponseDTO getViolationsForCCRMA(String licPlate, String licState, String invoiceNo, Long accountId,
			String ipAddress, String jsessionId, String sourceUserName, String user, String sessionId,
			Integer PmtPlanId) throws Exception {
		return null;
	}

	public ResultDTO makeViolationPayment(MakeViolationPaymentRequest makeViolationPaymentRequest)
			throws EtccException {
		return null;
	}
	
}
