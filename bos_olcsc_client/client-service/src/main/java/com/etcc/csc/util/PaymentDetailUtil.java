package com.etcc.csc.util;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.etcc.csc.dto.Invoice;
import com.etcc.csc.plsql.OLC_VPS_INVOICES_REC;

/**
 * Based on the original PaymentDetailUtil from OLCSCService.
 * @author Milosh Boroyevich
 *
 */
public class PaymentDetailUtil {
    private PaymentDetailUtil() { }

    public static Invoice convertToInvoice(OLC_VPS_INVOICES_REC vpsInvoicesRec) throws Exception {
        Invoice invoice = new Invoice();
        /*
        invoice.setId(vpsInvoicesRec.getINVOICE_ID().toPlainString());
        invoice.setDueDate(timestampToCalendar(vpsInvoicesRec.getCURR_DUE_DATE()));
        invoice.setInvoiceDate(timestampToCalendar(vpsInvoicesRec.getINVOICE_DATE()));
        invoice.setFirstName(vpsInvoicesRec.getFIRST_NAME());
        invoice.setLastName(vpsInvoicesRec.getLAST_NAME());
        invoice.setAmount(vpsInvoicesRec.getINVOICE_AMOUNT());
        invoice.setVeaAmount(vpsInvoicesRec.getVEA_AMOUNT());
        invoice.setOnlineFee(vpsInvoicesRec.getONLINE_FEE());
        invoice.setLicPlateNumber(vpsInvoicesRec.getLIC_PLATE_NBR());
        invoice.setLicPlateState(vpsInvoicesRec.getLIC_STATE());
        invoice.setViolatorId(vpsInvoicesRec.getVIOLATOR_ID().toPlainString());
        invoice.setViolations(convertToViolations(vpsInvoicesRec.getVIOLATIONS().getArray()));
        */
        return invoice;
    }

    public static Invoice convertToWsInvoice(OLC_VPS_INVOICES_REC vpsInvoicesRec) throws SQLException {
        Invoice invoice = new Invoice();
        /*
        invoice.setId(vpsInvoicesRec.getINVOICE_ID().toPlainString());
        invoice.setDueDate(timestampToCalendar(vpsInvoicesRec.getCURR_DUE_DATE()));
        invoice.setInvoiceDate(timestampToCalendar(vpsInvoicesRec.getINVOICE_DATE()));
        invoice.setFirstName(vpsInvoicesRec.getFIRST_NAME());
        invoice.setLastName(vpsInvoicesRec.getLAST_NAME());
        invoice.setAmount(vpsInvoicesRec.getINVOICE_AMOUNT());
        invoice.setVeaAmount(vpsInvoicesRec.getVEA_AMOUNT());
        invoice.setOnlineFee(vpsInvoicesRec.getONLINE_FEE());
        invoice.setLicPlateNumber(vpsInvoicesRec.getLIC_PLATE_NBR());
        invoice.setLicPlateState(vpsInvoicesRec.getLIC_STATE());
        invoice.setViolatorId(vpsInvoicesRec.getVIOLATOR_ID().toPlainString());
        invoice.setViolations(convertToViolations(vpsInvoicesRec.getVIOLATIONS().getArray()));
        */
        return invoice;
    }


   /* public static Violation[] convertToViolations(OLC_VIOLATION_REC[] violationRecs) throws Exception {
        if (ArrayUtils.isEmpty(violationRecs)) {
            return null;
        }
        Violation[] violations = new Violation[violationRecs.length];
        for (int i = 0; i < violationRecs.length; i++) {
            violations[i] = convertToViolation(violationRecs[i]);
        }
        return violations;
    }

    public static Violation convertToViolation(OLC_VIOLATION_REC violationRec) throws Exception {
        Violation violation = new Violation();
        violation.setId(violationRec.getVIOLATION_ID().toPlainString());
        violation.setLocation(violationRec.getLANE_NAME());
        violation.setLocationDesc(violationRec.getFULL_LANE_NAME());
        violation.setTimestamp(timestampToCalendar(violationRec.getVIOLATION_TIME()));
        return violation;
    }

    public static Violation convertToViolation(OLC_UNINVOICED_VIOLS_REC uninvoicedViolsRec) throws Exception {
        Violation violation = new Violation();
        violation.setId(uninvoicedViolsRec.getVIOLATION_ID().toPlainString());
        violation.setLocation(uninvoicedViolsRec.getVIOLATION_LOCATION());
        violation.setLocationDesc(uninvoicedViolsRec.getFULL_LOCATION_NAME());
        violation.setTimestamp(timestampToCalendar(uninvoicedViolsRec.getVIOLATION_DATE_TIME()));
        violation.setStatus(uninvoicedViolsRec.getSTATUS());
        violation.setCashAmount(uninvoicedViolsRec.getCASH_AMT());
        violation.setAviAmount(uninvoicedViolsRec.getAVI_AMT());
        violation.setLicPlate(uninvoicedViolsRec.getLIC_PLATE());
        violation.setLicState(uninvoicedViolsRec.getLIC_STATE());
        violation.setViolatorId(uninvoicedViolsRec.getVIOLATOR_ID());
        // violation.setOnlineFee(uninvoicedViolsRec.getONLINE_FEE());
        violation.setOnlineFee( new BigDecimal( 0.0 ) );
        return violation;
    }


    public static OLC_UNINVOICED_VIOLS_REC[] convertToOLC_UNINVOICED_VIOLS_RECs(Violation[] violations) throws Exception {
        if (ArrayUtils.isEmpty(violations)) {
            return null;
        }
        OLC_UNINVOICED_VIOLS_REC[] recs =
            new OLC_UNINVOICED_VIOLS_REC[violations.length];
        for (int i = 0; i < violations.length; i++) {
            recs[i] = convertToOLC_UNINVOICED_VIOLS_REC(violations[i]);
        }
        return recs;
    }


    public static OLC_UNINVOICED_VIOLS_REC convertToOLC_UNINVOICED_VIOLS_REC(Violation violation) throws Exception {
        OLC_UNINVOICED_VIOLS_REC rec = new OLC_UNINVOICED_VIOLS_REC();
        rec.setVIOLATION_ID(new BigDecimal(violation.getId()));
        rec.setVIOLATION_LOCATION(violation.getLocation());
        rec.setVIOLATION_DATE_TIME(new Timestamp(violation.getTimestamp().getTimeInMillis()));
        rec.setSTATUS(violation.getStatus());
        rec.setCASH_AMT(violation.getCashAmount());
        rec.setAVI_AMT(violation.getAviAmount());
        rec.setLIC_PLATE(violation.getLicPlate());
        rec.setLIC_STATE(violation.getLicState());
        rec.setVIOLATOR_ID(violation.getViolatorId());
        rec.setONLINE_FEE(violation.getOnlineFee());
        return rec;
    }*/

    public static Calendar timestampToCalendar(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp.getTime()));
        return calendar;
    }

    /*
    public static OLC_VPS_INV_PMT_REC[] invoicesToOLC_VPS_INV_PMT_RECs(Invoice[] invoices, boolean veaAccepted) throws SQLException {
        if (ArrayUtils.isEmpty(invoices)) {
            return null;
        }
        OLC_VPS_INV_PMT_REC[] OLC_VPS_INV_PMT_RECs =
            new OLC_VPS_INV_PMT_REC[invoices.length];
        for (int i = 0; i < invoices.length; i++) {
            OLC_VPS_INV_PMT_REC rec =
                invoiceToOLC_VPS_INV_PMT_REC(invoices[i],veaAccepted);
            rec.setARR_INDEX(new BigDecimal(i + 1));
            OLC_VPS_INV_PMT_RECs[i] = rec;
        }
        return OLC_VPS_INV_PMT_RECs;
    }

    public static OLC_VPS_INV_PMT_REC[] wsInvoicesToOLC_VPS_INV_PMT_RECs(Invoice[] invoices, boolean veaAccepted) throws SQLException {
        if (ArrayUtils.isEmpty(invoices)) {
            return null;
        }
        OLC_VPS_INV_PMT_REC[] OLC_VPS_INV_PMT_RECs =
            new OLC_VPS_INV_PMT_REC[invoices.length];
        for (int i = 0; i < invoices.length; i++) {
            OLC_VPS_INV_PMT_REC rec =
                wsInvoiceToOLC_VPS_INV_PMT_REC(invoices[i],veaAccepted);
            rec.setARR_INDEX(new BigDecimal(i + 1));
            OLC_VPS_INV_PMT_RECs[i] = rec;
        }
        return OLC_VPS_INV_PMT_RECs;
    }

    public static OLC_VPS_INV_PMT_REC invoiceToOLC_VPS_INV_PMT_REC(Invoice invoice,boolean veaAccepted) throws SQLException {
        if (invoice == null) {
            return null;
        }
        OLC_VPS_INV_PMT_REC rec = new OLC_VPS_INV_PMT_REC();
        rec.setINVOICE_ID(new BigDecimal(invoice.getId()));
        rec.setAMT_PAID(( invoice.isVeaEligible( ) && veaAccepted ) ? invoice.getVeaAmount() : invoice.getAmount() );
        rec.setVEA_FLAG( ( invoice.isVeaEligible( ) && veaAccepted ) ? "Y" : "N");
        rec.setVIOLATOR_ID(new BigDecimal(invoice.getViolatorId()));
        rec.setLIC_PLATE_NBR(invoice.getLicPlateNumber());
        rec.setLIC_PLATE_STATE(invoice.getLicPlateState());

        return rec;
    }

    public static OLC_VPS_INV_PMT_REC wsInvoiceToOLC_VPS_INV_PMT_REC(Invoice invoice,boolean veaAccepted) throws SQLException {
        if (invoice == null) {
            return null;
        }
        OLC_VPS_INV_PMT_REC rec = new OLC_VPS_INV_PMT_REC();
        rec.setINVOICE_ID(new BigDecimal(invoice.getId()));
        rec.setAMT_PAID(( invoice.isVeaEligible( ) && veaAccepted ) ? invoice.getVeaAmount() : invoice.getAmount() );
        rec.setVEA_FLAG( ( invoice.isVeaEligible( ) && veaAccepted ) ? "Y" : "N");
        rec.setVIOLATOR_ID(new BigDecimal(invoice.getViolatorId()));
        rec.setLIC_PLATE_NBR(invoice.getLicPlateNumber());
        rec.setLIC_PLATE_STATE(invoice.getLicPlateState());

        return rec;
    }

    public static OLC_VPS_INV_FEE_PMT_REC[] invoicesToOLC_VPS_INV_FEE_PMT_RECs(Invoice[] invoices,boolean veaAccepted) throws SQLException {
        if (ArrayUtils.isEmpty(invoices)) {
            return null;
        }
        OLC_VPS_INV_FEE_PMT_REC[] OLC_VPS_INV_FEE_PMT_RECs =
            new OLC_VPS_INV_FEE_PMT_REC[invoices.length];
        for (int i = 0; i < invoices.length; i++) {
            OLC_VPS_INV_FEE_PMT_REC rec =
                invoiceToOLC_VPS_INV_FEE_PMT_REC(invoices[i], veaAccepted);
            rec.setARR_INDEX(new BigDecimal(i + 1));
            OLC_VPS_INV_FEE_PMT_RECs[i] = rec;
        }
        return OLC_VPS_INV_FEE_PMT_RECs;
    }

    public static OLC_VPS_INV_FEE_PMT_REC[] wsInvoicesToOLC_VPS_INV_FEE_PMT_RECs(Invoice[] invoices,boolean veaAccepted) throws SQLException {
        if (ArrayUtils.isEmpty(invoices)) {
            return null;
        }
        OLC_VPS_INV_FEE_PMT_REC[] OLC_VPS_INV_FEE_PMT_RECs =
            new OLC_VPS_INV_FEE_PMT_REC[invoices.length];
        for (int i = 0; i < invoices.length; i++) {
            OLC_VPS_INV_FEE_PMT_REC rec =
                wsInvoiceToOLC_VPS_INV_FEE_PMT_REC(invoices[i], veaAccepted);
            rec.setARR_INDEX(new BigDecimal(i + 1));
            OLC_VPS_INV_FEE_PMT_RECs[i] = rec;
        }
        return OLC_VPS_INV_FEE_PMT_RECs;
    }*/

    /*
     setARR_INDEX(ARR_INDEX);
     setOL_PMT_DET_ID(OL_PMT_DET_ID);
     setPAYMENT_TXN_ID(PAYMENT_TXN_ID);
     setPAYMENT_LINE_ITEM_ID(PAYMENT_LINE_ITEM_ID);
     setPAYMENT_XREF_ID(PAYMENT_XREF_ID);
     setPAYMENT_FORM(PAYMENT_FORM);
     setINVOICE_ID(INVOICE_ID);
     setAMT_PAID(AMT_PAID);
     setCOLLECTION_ID(COLLECTION_ID);
     setCITATIONS(CITATIONS);
     setVIOLATOR_ID(VIOLATOR_ID);
     setLIC_PLATE_NBR(LIC_PLATE_NBR);
     setLIC_PLATE_STATE(LIC_PLATE_STATE);
     */

    /*
     public static OLC_VPS_INV_FEE_PMT_REC invoiceToOLC_VPS_INV_FEE_PMT_REC(Invoice invoice, boolean veaAccepted) throws SQLException {
         if (invoice == null) {
             return null;
         }
         OLC_VPS_INV_FEE_PMT_REC rec = new OLC_VPS_INV_FEE_PMT_REC();
         rec.setINVOICE_ID(new BigDecimal(invoice.getId()));
         rec.setAMT_PAID( ( invoice.isVeaEligible( ) && veaAccepted ) ? invoice.getOnlineFee() : new BigDecimal(0.0) );
         rec.setVIOLATOR_ID(new BigDecimal(invoice.getViolatorId()));
         rec.setLIC_PLATE_NBR(invoice.getLicPlateNumber());
         rec.setLIC_PLATE_STATE(invoice.getLicPlateState());

         return rec;
     }

    public static OLC_VPS_INV_FEE_PMT_REC wsInvoiceToOLC_VPS_INV_FEE_PMT_REC(Invoice invoice, boolean veaAccepted) throws SQLException {
        if (invoice == null) {
            return null;
        }
        OLC_VPS_INV_FEE_PMT_REC rec = new OLC_VPS_INV_FEE_PMT_REC();
        rec.setINVOICE_ID(new BigDecimal(invoice.getId()));
        rec.setAMT_PAID( ( invoice.isVeaEligible( ) && veaAccepted ) ? invoice.getOnlineFee() : new BigDecimal(0.0) );
        rec.setVIOLATOR_ID(new BigDecimal(invoice.getViolatorId()));
        rec.setLIC_PLATE_NBR(invoice.getLicPlateNumber());
        rec.setLIC_PLATE_STATE(invoice.getLicPlateState());

        return rec;
    }


    public static OLC_VPS_UNINV_PMT_REC[] violationsToOLC_VPS_UNINV_PMT_RECs(Violation[] violations) throws SQLException {
        if (ArrayUtils.isEmpty(violations)) {
            return null;
        }
        OLC_VPS_UNINV_PMT_REC[] OLC_VPS_UNINV_PMT_RECs =
            new OLC_VPS_UNINV_PMT_REC[violations.length];
        for (int i = 0; i < violations.length; i++) {
            OLC_VPS_UNINV_PMT_REC rec =
                violationToOLC_VPS_UNINV_PMT_REC(violations[i]);
            rec.setARR_INDEX(new BigDecimal(i + 1));
            OLC_VPS_UNINV_PMT_RECs[i] = rec;
        }
        return OLC_VPS_UNINV_PMT_RECs;
    }

    public static OLC_VPS_UNINV_PMT_REC[] wsViolationsToOLC_VPS_UNINV_PMT_RECs(Violation[] violations) throws SQLException {
        if (ArrayUtils.isEmpty(violations)) {
            return null;
        }
        OLC_VPS_UNINV_PMT_REC[] OLC_VPS_UNINV_PMT_RECs =
            new OLC_VPS_UNINV_PMT_REC[violations.length];
        for (int i = 0; i < violations.length; i++) {
            OLC_VPS_UNINV_PMT_REC rec =
                wsViolationToOLC_VPS_UNINV_PMT_REC(violations[i]);
            rec.setARR_INDEX(new BigDecimal(i + 1));
            OLC_VPS_UNINV_PMT_RECs[i] = rec;
        }
        return OLC_VPS_UNINV_PMT_RECs;
    }*/

    /*
     *     setOL_PMT_DET_ID(OL_PMT_DET_ID);
    setPAYMENT_FORM(PAYMENT_FORM);
    setPAYMENT_TXN_ID(PAYMENT_TXN_ID);
    setPAYMENT_LINE_ITEM_ID(PAYMENT_LINE_ITEM_ID);
    setPAYMENT_XREF_ID(PAYMENT_XREF_ID);
    setVIOLATION_ID(VIOLATION_ID);
    setAMT_PAID(AMT_PAID);
    setVIOLATOR_ID(VIOLATOR_ID);
    setLIC_PLATE_NBR(LIC_PLATE_NBR);
    setLIC_PLATE_STATE(LIC_PLATE_STATE);
    setSTATUS(STATUS);
    setERROR_CODE(ERROR_CODE);
    setARR_INDEX(ARR_INDEX);
     */

    /*
     public static OLC_VPS_UNINV_PMT_REC violationToOLC_VPS_UNINV_PMT_REC(Violation violation) throws SQLException {
         if (violation == null) {
             return null;
         }

         OLC_VPS_UNINV_PMT_REC rec = new OLC_VPS_UNINV_PMT_REC();
         rec.setVIOLATION_ID(new BigDecimal(violation.getId()));
         rec.setAMT_PAID(violation.getCashAmount());
         rec.setVIOLATOR_ID(violation.getViolatorId());
         rec.setLIC_PLATE_NBR(violation.getLicPlate());
         rec.setLIC_PLATE_STATE(violation.getLicState());
         return rec;
     }

    public static OLC_VPS_UNINV_PMT_REC wsViolationToOLC_VPS_UNINV_PMT_REC(Violation violation) throws SQLException {
        if (violation == null) {
            return null;
        }

        OLC_VPS_UNINV_PMT_REC rec = new OLC_VPS_UNINV_PMT_REC();
        rec.setVIOLATION_ID(new BigDecimal(violation.getId()));
        rec.setAMT_PAID(violation.getCashAmount());
        rec.setVIOLATOR_ID(violation.getViolatorId());
        rec.setLIC_PLATE_NBR(violation.getLicPlate());
        rec.setLIC_PLATE_STATE(violation.getLicState());
        return rec;
    }

    public static OLC_VPS_UNINV_FEE_PMT_REC[] violationsToOLC_VPS_UNINV_FEE_PMT_RECs(Violation[] violations) throws SQLException {
        if (ArrayUtils.isEmpty(violations)) {
            return null;
        }

        OLC_VPS_UNINV_FEE_PMT_REC[] OLC_VPS_UNINV_FEE_PMT_RECs =
            new OLC_VPS_UNINV_FEE_PMT_REC[violations.length];
        for (int i = 0; i < violations.length; i++) {
            OLC_VPS_UNINV_FEE_PMT_REC rec =
                violationToOLC_VPS_UNINV_FEE_PMT_REC(violations[i]);
            rec.setARR_INDEX(new BigDecimal(i + 1));
            OLC_VPS_UNINV_FEE_PMT_RECs[i] = rec;
        }
        return OLC_VPS_UNINV_FEE_PMT_RECs;
    }

    public static OLC_VPS_UNINV_FEE_PMT_REC[] wsViolationsToOLC_VPS_UNINV_FEE_PMT_RECs(Violation[] violations) throws SQLException {
        if (ArrayUtils.isEmpty(violations)) {
            return null;
        }

        OLC_VPS_UNINV_FEE_PMT_REC[] OLC_VPS_UNINV_FEE_PMT_RECs =
            new OLC_VPS_UNINV_FEE_PMT_REC[violations.length];
        for (int i = 0; i < violations.length; i++) {
            OLC_VPS_UNINV_FEE_PMT_REC rec =
                wsViolationToOLC_VPS_UNINV_FEE_PMT_REC(violations[i]);
            rec.setARR_INDEX(new BigDecimal(i + 1));
            OLC_VPS_UNINV_FEE_PMT_RECs[i] = rec;
        }
        return OLC_VPS_UNINV_FEE_PMT_RECs;
    }*/

    /*
     *     setARR_INDEX(ARR_INDEX);
    setOL_PMT_DET_ID(OL_PMT_DET_ID);
    setPAYMENT_TXN_ID(PAYMENT_TXN_ID);
    setPAYMENT_LINE_ITEM_ID(PAYMENT_LINE_ITEM_ID);
    setPAYMENT_XREF_ID(PAYMENT_XREF_ID);
    setPAYMENT_FORM(PAYMENT_FORM);
    setVIOLATION_ID(VIOLATION_ID);
    setAMT_PAID(AMT_PAID);
    setVIOLATOR_ID(VIOLATOR_ID);
    setLIC_PLATE_NBR(LIC_PLATE_NBR);
    setLIC_PLATE_STATE(LIC_PLATE_STATE);
    setSTATUS(STATUS);
    setERROR_CODE(ERROR_CODE);

     */

    /*
     public static OLC_VPS_UNINV_FEE_PMT_REC violationToOLC_VPS_UNINV_FEE_PMT_REC(Violation violation) throws SQLException{
         if (violation == null) {
             return null;
         }

         OLC_VPS_UNINV_FEE_PMT_REC rec = new OLC_VPS_UNINV_FEE_PMT_REC();
         rec.setVIOLATION_ID(new BigDecimal(violation.getId()));
         rec.setAMT_PAID(violation.getOnlineFee());
         rec.setVIOLATOR_ID(violation.getViolatorId());
         rec.setLIC_PLATE_NBR(violation.getLicPlate());
         rec.setLIC_PLATE_STATE(violation.getLicState());
         return rec;
     }

    public static OLC_VPS_UNINV_FEE_PMT_REC wsViolationToOLC_VPS_UNINV_FEE_PMT_REC(Violation violation) throws SQLException{
        if (violation == null) {
            return null;
        }

        OLC_VPS_UNINV_FEE_PMT_REC rec = new OLC_VPS_UNINV_FEE_PMT_REC();
        rec.setVIOLATION_ID(new BigDecimal(violation.getId()));
        rec.setAMT_PAID(violation.getOnlineFee());
        rec.setVIOLATOR_ID(violation.getViolatorId());
        rec.setLIC_PLATE_NBR(violation.getLicPlate());
        rec.setLIC_PLATE_STATE(violation.getLicState());
        return rec;
    }
    */

}
