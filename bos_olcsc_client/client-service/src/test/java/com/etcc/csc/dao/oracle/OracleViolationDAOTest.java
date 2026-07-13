/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao.oracle;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.plsql.OLC_VPS_INV_ARR_D;
import com.etcc.csc.plsql.OLC_VPS_INV_REC_D;
import com.etcc.csc.plsql.OLC_VPS_INV_REC_H;

/**
 * @author Stephen Davidson
 */
public class OracleViolationDAOTest {
    private static final Logger logger = Logger.getLogger(OracleViolationDAOTest.class);

    OracleViolationDAO dao;

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.dao = new OracleViolationDAO();
        //TODO: Get something that resembles an Oracle connection for testing.
        this.dao.setConnection(null);
    }

    /**
     * Test method for {@link OracleViolationDAO#loginViolator(AccountLoginDTO, String, String, UserEnvDTO, String, String, String)}.
     */
    @Test
    public void testLoginViolatorAccountLoginDTOStringStringUserEnvDTOStringStringString() {
//        this.dao.loginViolator(acctLoginDto, "123abc4567", "127.0.0.1", userEnvDto, invoiceId, licPlate, licState);
        logger.warn("Test not implemented yet.");
    }

    /**
     * Test method for {@link OracleViolationDAO#makePayment(ViolatorDTO, AccountPaymentMethodDTO, String)}.
     */
    @Test
    public void testMakePayment() {
    	
        logger.warn("Test not implemented yet.");
    }

    /**
     * Test method for {@link OracleViolationDAO#getPaymentPlan(String, String, String, String, String, long)}.
     */
    @Test
    public void testGetPaymentPlan() {
        logger.warn("Test not implemented yet.");
    }

    /**
     * Test method for {@link OracleViolationDAO#getInvoices(AccountLoginDTO, String, String, String, AgencyEnum)}.
     */
    @Test
    public void testGetInvoices() {
        logger.warn("Test not implemented yet.");
    }

    /**
     * Test method for {@link OracleViolationDAO#getInvoiceDoc(ViolatorDTO, InvoiceDTO)}.
     */
    @Test
    public void testGetInvoiceDoc() {
        logger.warn("Test not implemented yet.");
    }

    /**
     * Test method for {@link OracleViolationDAO#emailReceipt(ViolatorDTO, AccountPaymentMethodDTO, String)}.
     */
    @Test
    public void testEmailReceipt() {
        logger.warn("Test not implemented yet.");
    }

    private OLC_VPS_INV_REC_H[] getOLC_VPS_INV_REC_H(int size) throws SQLException {
        OLC_VPS_INV_REC_H[] O_VPS_INV_REC_H = new OLC_VPS_INV_REC_H[size];
        for(int idx = 0; idx < size; idx++){
            final OLC_VPS_INV_REC_H olc = new OLC_VPS_INV_REC_H();
            final String strInvoiceId = String.valueOf(idx);
            olc.setINVOICE_ID(strInvoiceId);
            OLC_VPS_INV_REC_D[] items = new OLC_VPS_INV_REC_D[1];
            items[0] = new OLC_VPS_INV_REC_D(BigDecimal.valueOf(idx), strInvoiceId, BigDecimal.valueOf(1.25), 
                    new Timestamp(System.currentTimeMillis()), "", "",BigDecimal.valueOf(10));
            items[0].setSTATUS("Invoiced");
            items[0].setV_LOCATION("Unknown");
            OLC_VPS_INV_ARR_D LINE_ITEMS = new OLC_VPS_INV_ARR_D(items);
            olc.setLINE_ITEMS(LINE_ITEMS);
            O_VPS_INV_REC_H[idx] = olc;
        }
        return O_VPS_INV_REC_H;
    }
    /**
     * Test method for {@link OracleViolationDAO#populateInvoices(OLC_VPS_INV_REC_H[], ViolatorDTO)}
     * @throws Exception If any exceptions occur during testing.
     */
    @Test
    public void testPopulateInvoices() throws Exception {
        logger.error("Test not implemented due to issues with Oracle JDBC Objects");
//    	OLC_VPS_INV_REC_H[] O_VPS_INV_REC_H = getOLC_VPS_INV_REC_H(3);
//        ViolatorDTO violatorDTO = new ViolatorDTO();
//        this.dao.populateInvoices(O_VPS_INV_REC_H, violatorDTO, AgencyEnum.AGENCY_HARRIS_COUNTY);
//        assertNotNull(violatorDTO.getAllInvoices());
//        assertFalse("Invoices not populated to any agency.", 
//                violatorDTO.getInvoicedViolations(AgencyEnum.AGENCY_FORT_BEND) == null 
//                && violatorDTO.getInvoicedViolations(AgencyEnum.AGENCY_HARRIS_COUNTY) == null );
        
    }

    /**
     * Test method for {@link OracleViolationDAO#populateInvoices(OLC_VPS_INV_REC_H[], ViolatorDTO)}
     * @throws SQLException 
     */
    @Test(expected=NullPointerException.class)
    public void testFailPopulateInvoices() throws SQLException {
    	OLC_VPS_INV_REC_H[] O_VPS_INV_REC_H = getOLC_VPS_INV_REC_H(1);
        ViolatorDTO violatorDTO = new ViolatorDTO();
        this.dao.populateInvoices(O_VPS_INV_REC_H, violatorDTO);
        fail("NPE should have been thrown!");
    }
}
