package com.etcc.csc.datatype;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

import com.etcc.csc.plsql.OlcUninvoicedViolsRec;
import com.etcc.csc.plsql.OlcViolationRec;
import com.etcc.csc.plsql.OlcVpsInvoicesRec;

public class PaymentDetailUtil {
	public PaymentDetailUtil() {
	}

	public static Invoice convertToInvoice(OlcVpsInvoicesRec vpsInvoicesRec)
			throws Exception {
		Invoice invoice = new Invoice();
		invoice.setId(vpsInvoicesRec.getInvoiceId().toPlainString());
		invoice.setDueDate(timestampToCalendar(vpsInvoicesRec.getCurrDueDate()));
		invoice.setInvoiceDate(timestampToCalendar(vpsInvoicesRec
				.getInvoiceDate()));
		invoice.setFirstName(vpsInvoicesRec.getFirstName());
		invoice.setLastName(vpsInvoicesRec.getLastName());
		invoice.setAmount(vpsInvoicesRec.getInvoiceAmount());
		invoice.setVeaAmount(vpsInvoicesRec.getVeaAmount());
		invoice.setOnlineFee(vpsInvoicesRec.getOnlineFee());
		invoice.setLicPlateNumber(vpsInvoicesRec.getLicPlateNbr());
		invoice.setLicPlateState(vpsInvoicesRec.getLicState());
		invoice.setViolatorId(vpsInvoicesRec.getViolatorId().toPlainString());
		invoice.setCaAccountId(vpsInvoicesRec.getCaAccountId());
		invoice.setCaPhoneNumber(vpsInvoicesRec.getCaPhoneNumber());
		invoice.setCaCompany(vpsInvoicesRec.getCaCompany());
		invoice.setTollTrxnAmount(vpsInvoicesRec.getTollTrxnAmount());
		invoice.setRetailChargeAmount(vpsInvoicesRec.getRetailChargeAmount());
		invoice.setViolations(convertToViolations(vpsInvoicesRec
				.getViolations().getArray()));
		return invoice;
	}

	public static Violation[] convertToViolations(
			OlcViolationRec[] violationRecs) throws Exception {
		if (ArrayUtils.isEmpty(violationRecs)) {
			return null;
		}
		Violation[] violations = new Violation[violationRecs.length];
		for (int i = 0; i < violationRecs.length; i++) {
			violations[i] = convertToViolation(violationRecs[i]);
		}
		return violations;
	}

	public static Violation convertToViolation(OlcViolationRec violationRec)
			throws Exception {
		Violation violation = new Violation();
		violation.setId(violationRec.getViolationId().toPlainString());
		violation.setLocation(violationRec.getLaneName());
		violation.setLocationDesc(violationRec.getFullLaneName());
		violation.setCitationNumber(violationRec.getCitationNbr());
		violation.setTimestamp(timestampToCalendar(violationRec
				.getViolationTime()));
		return violation;
	}

	public static Violation convertToViolation(
			OlcUninvoicedViolsRec uninvoicedViolsRec) throws Exception {
		Violation violation = new Violation();
		violation.setId(uninvoicedViolsRec.getViolationId().toPlainString());
		violation.setLocation(uninvoicedViolsRec.getViolationLocation());
		violation.setLocationDesc(uninvoicedViolsRec.getFullLocationName());
		violation.setTimestamp(timestampToCalendar(uninvoicedViolsRec
				.getViolationDateTime()));
		violation.setStatus(uninvoicedViolsRec.getStatus());
		violation.setCashAmount(uninvoicedViolsRec.getCashAmt());
		violation.setAviAmount(uninvoicedViolsRec.getAviAmt());
		violation.setLicPlate(uninvoicedViolsRec.getLicPlate());
		violation.setLicState(uninvoicedViolsRec.getLicState());
		violation.setViolatorId(uninvoicedViolsRec.getViolatorId());
		violation.setOnlineFee(uninvoicedViolsRec.getOnlineFee());
		// violation.setOnlineFee( new BigDecimal( 0.0 ) );
		return violation;
	}

	public static OlcUninvoicedViolsRec[] convertToOLC_UNINVOICED_VIOLS_RECs(
			Violation[] violations) throws Exception {
		if (ArrayUtils.isEmpty(violations)) {
			return null;
		}
		OlcUninvoicedViolsRec[] recs = new OlcUninvoicedViolsRec[violations.length];
		for (int i = 0; i < violations.length; i++) {
			recs[i] = convertToOLC_UNINVOICED_VIOLS_REC(violations[i]);
		}
		return recs;
	}

	public static OlcUninvoicedViolsRec convertToOLC_UNINVOICED_VIOLS_REC(
			Violation violation) throws Exception {
		OlcUninvoicedViolsRec rec = new OlcUninvoicedViolsRec();
		rec.setViolationId(new BigDecimal(violation.getId()));
		rec.setViolationLocation(violation.getLocation());
		rec.setViolationDateTime(new Timestamp(violation.getTimestamp()
				.getTimeInMillis()));
		rec.setStatus(violation.getStatus());
		rec.setCashAmt(violation.getCashAmount());
		rec.setAviAmt(violation.getAviAmount());
		rec.setLicPlate(violation.getLicPlate());
		rec.setLicState(violation.getLicState());
		rec.setViolatorId(violation.getViolatorId());
		rec.setOnlineFee(violation.getOnlineFee());
		return rec;
	}

	public static Calendar timestampToCalendar(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timestamp.getTime()));
		return calendar;
	}
}
