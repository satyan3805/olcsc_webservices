package com.etcc.csc.service;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.datatype.Invoice;
import com.etcc.csc.datatype.OlcUninvoicedViolsRecBean;
import com.etcc.csc.datatype.OlcVpsInvoicesRecBean;
import com.etcc.csc.datatype.PaymentDetail;
import com.etcc.csc.datatype.PaymentResponse;
import com.etcc.csc.datatype.Violation;
import com.etcc.csc.plsql.OlcPaymentInfoRec;

public class ZipCash implements ZipCashInterface {
	private Logger logger = Logger.getLogger(ZipCash.class);

	public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress,
			String loginId, long docId, String docType, String licPlate,
			String licState, BigDecimal transactionId)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getZipCashDAO()
				.getPaymentDetail(dbSessionId, ipAddress, loginId, docId,
						docType, licPlate, licState, transactionId);

	}

	public PaymentResponse zipCashCombinedPayment(long acctId, long docId,
			String docType, String dbSessionId, String ipAddress,
			String loginId, BigDecimal transactionId, BigDecimal tagAmount,
			BigDecimal totalAmount, String dlState, String dlNumber,
			String email, boolean updatePmtInfo, String cardCode,
			String paymentType, String cardNumber, String expireMonth,
			String expireYear, String nameOnCard, String address,
			String address2, String city, String state, String zipCode,
			String plus4, String cardSecurityCode, Invoice[] invoices,
			Violation[] violations, boolean veaAccepted)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getZipCashDAO()
				.zipCashCombinedPayment(acctId, docId, docType, dbSessionId,
						ipAddress, loginId, transactionId, tagAmount,
						totalAmount, dlState, dlNumber, email, updatePmtInfo,
						cardCode, paymentType, cardNumber, expireMonth,
						expireYear, nameOnCard, address, address2, city, state,
						zipCode, plus4, cardSecurityCode, invoices, violations,
						veaAccepted);
	}

	public boolean autoComments(long docId, String docType, String sessionId,
			String ipAddress, String userId, long paymentId, String payerName,
			double payAmt, String failedFlag, OlcVpsInvoicesRecBean[] invoices,
			OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			return daoFactory.getZipCashDAO().autoComments(docId, docType,
					sessionId, ipAddress, userId, paymentId, payerName, payAmt,
					failedFlag, invoices, uninvoiced);
		} catch (Exception e) {
			logger.error("Error in autoComments " + e, e);
			throw new EtccException(e);
		}
	}

	public OlcPaymentInfoRec getPaymentInfo(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId, long eventId)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getZipCashDAO()
				.getPaymentInfo(docId, docType, dbSessionId, ipAddress,
						loginId, eventId);
	}

	public String getPaymentReceipt(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			BigDecimal paymentId, String reportFormat)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getZipCashDAO()
				.getPaymentReceipt(docId, docType, dbSessionId, ipAddress,
						loginId, paymentId, reportFormat);

	}

	public BigDecimal getPaymentLimit() throws Exception {
		return DAOFactory.getDAOFactory().getPaymentDAO().getPaymentLimit();
	}

	public boolean updateSessionId(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			String paymentId) throws EtccErrorMessageException, Exception {
		// return
		// DAOFactory.getDAOFactory().getPaymentDAO().updateSessionId(docId,
		// docType, dbSessionId, ipAddress, loginId, paymentId);
		return false;
	}
}// end of ZipCash Class
