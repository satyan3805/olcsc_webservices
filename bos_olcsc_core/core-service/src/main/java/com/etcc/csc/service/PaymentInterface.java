package com.etcc.csc.service;

import java.math.BigDecimal;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.datatype.Invoice;
import com.etcc.csc.datatype.OlcUninvoicedViolsRecBean;
import com.etcc.csc.datatype.OlcVpsInvoicesRecBean;
import com.etcc.csc.datatype.PaymentDetail;
import com.etcc.csc.datatype.PaymentResponse;
import com.etcc.csc.datatype.Violation;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.plsql.OlcPaymentInfoRec;

public interface PaymentInterface extends BusinessObjectInterface {
	public PaymentDetail getPaymentDetail(String dbSessionId, String ipAddress,
			String loginId, long docId, String docType, String licPlate,
			String licState, BigDecimal transactionId)
			throws EtccErrorMessageException, Exception;

	public boolean veaAutoComments(long docId, String docType,
			String sessionId, String ipAddress, String userId, String veaName,
			OlcVpsInvoicesRecBean[] invoices) throws EtccException;

	public boolean autoComments(long docId, String docType, String sessionId,
			String ipAddress, String userId, long paymentId, String payerName,
			double payAmt, String failedFlag, OlcVpsInvoicesRecBean[] invoices,
			OlcUninvoicedViolsRecBean[] uninvoiced) throws EtccException;

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
			throws EtccErrorMessageException, Exception;

	public boolean veaExists(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			String licPlate, String licState) throws Exception;

	public OlcPaymentInfoRec getPaymentInfo(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId, long eventId)
			throws EtccErrorMessageException, Exception;

	public boolean postViolations(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			Violation[] violations) throws EtccErrorMessageException, Exception;

	public String getPaymentReceipt(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			BigDecimal paymentId, String reportFormat)
			throws EtccErrorMessageException, Exception;

	public BigDecimal getPaymentLimit() throws Exception;

	boolean updateSessionId(BigDecimal docId, String docType,
			String dbSessionId, String ipAddress, String loginId,
			String paymentId) throws EtccErrorMessageException, Exception;

	public byte makePayment(AccountLoginDTO acctLoginDto, String postingXML,
			long eventId) throws EtccErrorMessageException, Exception;
}// end of PaymentInterface Interface
