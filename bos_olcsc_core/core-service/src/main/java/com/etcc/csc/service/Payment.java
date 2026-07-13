package com.etcc.csc.service;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DAOFactory;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.PaymentDAO;
import com.etcc.csc.datatype.Invoice;
import com.etcc.csc.datatype.OlcUninvoicedViolsRecBean;
import com.etcc.csc.datatype.OlcVpsInvoicesRecBean;
import com.etcc.csc.datatype.PaymentDetail;
import com.etcc.csc.datatype.PaymentResponse;
import com.etcc.csc.datatype.Violation;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.plsql.OlcPaymentInfoRec;

public class Payment implements PaymentInterface {
	private Logger logger = Logger.getLogger(Payment.class);

	public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress,
			String loginId, long docId, String docType, String licPlate,
			String licState, BigDecimal transactionId)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getPaymentDAO()
				.getPaymentDetail(dbSessionId, ipAddress, loginId, docId,
						docType, licPlate, licState, transactionId);
	}// end of getPaymentDetail()

	public PaymentResponse combinedPayment(long docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			BigDecimal transactionId, BigDecimal tagAmount,
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
				.getPaymentDAO()
				.combinedPayment(docId, docType, dbSessionId, ipAddress,
						loginId, transactionId, tagAmount, totalAmount,
						dlState, dlNumber, email, updatePmtInfo, cardCode,
						paymentType, cardNumber, expireMonth, expireYear,
						nameOnCard, address, address2, city, state, zipCode,
						plus4, cardSecurityCode, invoices, violations,
						veaAccepted);
	}// end of combinedPayment()

	public boolean veaAutoComments(long docId, String docType,
			String sessionId, String ipAddress, String userId, String veaName,
			OlcVpsInvoicesRecBean[] invoices) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			PaymentDAO paymentDAO = daoFactory.getPaymentDAO();
			return paymentDAO.veaAutoComments(docId, docType, sessionId,
					ipAddress, userId, veaName, invoices);
		} catch (Exception ex) {
			logger.error("Error in veaAutoComments() @ Payment ", ex);
			throw new EtccException(ex);
		}// end of try-catch()
	}// end of veaAutoComments()

	public boolean autoComments(long docId, String docType, String sessionId,
			String ipAddress, String userId, long paymentId, String payerName,
			double payAmt, String failedFlag, OlcVpsInvoicesRecBean[] invoices,
			OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException {
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			PaymentDAO paymentDAO = daoFactory.getPaymentDAO();
			return paymentDAO.autoComments(docId, docType, sessionId,
					ipAddress, userId, paymentId, payerName, payAmt,
					failedFlag, invoices, uninvoiced);
		} catch (Exception ex) {
			logger.error("Error in autoComments() @ Payment ", ex);
			throw new EtccException(ex);
		}// end of try-catch()
	}// end of autoComments()

	public boolean veaExists(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			String licPlate, String licState) throws Exception {
		return DAOFactory
				.getDAOFactory()
				.getPaymentDAO()
				.veaExists(docId, docType, dbSessionId, ipAddress, loginId,
						licPlate, licState);
	}// end of veaExists()

	public OlcPaymentInfoRec getPaymentInfo(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId, long eventId)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getPaymentDAO()
				.getPaymentInfo(docId, docType, dbSessionId, ipAddress,
						loginId, eventId);
	}// end of getPaymentInfo()

	public boolean postViolations(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			Violation[] violations) throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getPaymentDAO()
				.postViolations(docId, docType, dbSessionId, ipAddress,
						loginId, violations);
	}// end of postViolations()

	public String getPaymentReceipt(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			BigDecimal paymentId, String reportFormat)
			throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getPaymentDAO()
				.getPaymentReceipt(docId, docType, dbSessionId, ipAddress,
						loginId, paymentId, reportFormat);
	}// end of getPaymentReceipt()

	public BigDecimal getPaymentLimit() throws Exception {
		return DAOFactory.getDAOFactory().getPaymentDAO().getPaymentLimit();
	}// end of getPaymentLimit()

	public boolean updateSessionId(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			String paymentId) throws EtccErrorMessageException, Exception {
		return DAOFactory
				.getDAOFactory()
				.getPaymentDAO()
				.updateSessionId(docId, docType, dbSessionId, ipAddress,
						loginId, paymentId);
	}// end of updateSessionId()

	public byte makePayment(AccountLoginDTO acctLoginDto, String postingXML,
			long eventId) throws EtccErrorMessageException, Exception {
		return DAOFactory.getDAOFactory().getPaymentDAO()
				.makePayment(acctLoginDto, postingXML, eventId);
	}// end of makePayment()

}// end of Payment Class