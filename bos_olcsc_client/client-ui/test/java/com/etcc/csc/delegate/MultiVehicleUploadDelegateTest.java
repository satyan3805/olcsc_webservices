/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.delegate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.MultiVehicleDTO;
import com.etcc.csc.dto.MultiVehicleUploadDTO;
import com.etcc.csc.dto.ReportDTO;
import com.etcc.csc.dto.TagDTO.DeliveryMethod;
import com.etcc.csc.service.AccountFactory;
import com.etcc.csc.service.ReportInterface;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for upload of multiple vehicles.
 * @author Milosh Boroyevich
 * @see MultiVehicleUploadDelegate
 */
// tests imported from the original com.etcc.csc.delegate.MultiVehicleUploadDelegate
public class MultiVehicleUploadDelegateTest {
    private static final Logger logger = Logger.getLogger(MultiVehicleUploadDelegateTest.class);

    MultiVehicleUploadDelegate delegate;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseTestDelegate.setUpBeforeClass();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.delegate = new MultiVehicleUploadDelegate();
    }

    /**
     * This service exercises the web service with minimal approach
     * @throws Exception If any exceptions occur during testing.
     */
    @Test
    public void testMin() throws Exception {
        AccountLoginDTO acctLoginDto = AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID);
//        BaseDTO enabled = delegate.isMultiVehicleUploadAllowed(aLogin);
        MultiVehicleUploadDTO uploadRequest = new MultiVehicleUploadDTO();
        logger.debug("Validating empty upload request");
        MultiVehicleUploadDTO result = delegate.validateMultipleVehicles(acctLoginDto, uploadRequest);
        assertNotNull("Validate returned no result.", result);
        result = delegate.loadMultipleVehicles(acctLoginDto, uploadRequest);
        assertNotNull("Load returned no result.", result);
    }

    @Test
    public void testWithGoodData() throws Exception {
        testWithData(generateData(2, "ADD", "Two Axle Vehicle"));
    }

    @Test
    public void testWithBadData() throws Exception {
        testWithData(generateData(2, "INACTIVATE", Integer.toString(2)));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testWithInvalidAction() throws Exception {
        generateData(2, "update", Integer.toString(2));
    }

    protected void testWithData(MultiVehicleDTO[] theData) throws EtccException {
        AccountLoginDTO acctLoginDto = AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID);
//        BaseDTO enabled = delegate.isMultiVehicleUploadAllowed(aLogin);
//        if ((enabled.getErrors() == null) || (enabled.getErrors().length == 0)) {
        MultiVehicleUploadDTO uploadRequest = new MultiVehicleUploadDTO();
        MultiVehicleDTO[] multiVehicleTags = theData;
        uploadRequest.setMultiVehicleTags(multiVehicleTags);
        MultiVehicleUploadDTO aMultiVehicleUploadResult = delegate.validateMultipleVehicles(acctLoginDto, uploadRequest);
        if ((aMultiVehicleUploadResult.getErrors() == null) || (aMultiVehicleUploadResult.getErrors().length == 0)) {
            long CREATE_NEW_TRXN = -1;
            uploadRequest.setTransactionId(CREATE_NEW_TRXN);
            aMultiVehicleUploadResult = delegate.loadMultipleVehicles(acctLoginDto, uploadRequest);
            if ((aMultiVehicleUploadResult.getErrors() != null) && (aMultiVehicleUploadResult.getErrors().length > 0)) {
                logger.error("Multi vehicle upload failed");
            } else {
                if (aMultiVehicleUploadResult.getHardErrorCount() > 0) {
                    handleHardErrors(acctLoginDto, aMultiVehicleUploadResult);
                } else {
                    commitMultiVehicleUpload(acctLoginDto, aMultiVehicleUploadResult);
                }
            }
        } else {
            if (aMultiVehicleUploadResult.getHardErrorCount() > 0)
                handleHardErrors(acctLoginDto, aMultiVehicleUploadResult);
        }
    }

    private static void handleHardErrors(AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO mvu) throws EtccException {
        if (mvu.getHardErrorCount() > 0) { // validation failed, report id valid
            long reportId = mvu.getReportId();
            ReportDelegate reportDelegate = new ReportDelegate();
            String args = "p_report_id=" + reportId;
            ReportDTO report = reportDelegate.getReport(
                    ReportInterface.ReportType.MVU_REPORT,
                    FileFormat.PDF,
                    args,
                    acctLoginDto
                    );
            assertNotNull("MVU report (" + reportId + ") is null.", report);
            assertNotNull("MVU report bytes (" + reportId + ") are null.", report.getBytes());
            logger.debug("Report (" + reportId + ") length=" + report.size());
        }
    }

    /**
     * Generate multi-vehicle data array.
     * @param count number of items in resulting array
     * @param action valid values are <tt>"ADD"</tt> and <tt>"INACTIVATE"</tt>
     * @param vehicleClass valid values are <tt>"Two Axle Vehicle", "Three Axle Vehicle", "Four Axle Vehicle", "Five Axle Vehicle", "Six plus Axles (other)"</tt>
     * @return array of multi-vehicle data objects
     */
    protected static MultiVehicleDTO[] generateData(int count, final String action, final String vehicleClass) {
        MultiVehicleDTO[] multiVehicleTags = new MultiVehicleDTO[count];
        for (int i = 0; i < count; i++) {
            MultiVehicleDTO aTag = new MultiVehicleDTO();
            aTag.setRowNumber(i + 1);
            aTag.setAction(action); // 'ADD','INACTIVATE'
            aTag.setEzTagRequired(true);
            aTag.setLicState("TX");
            String aLicPlate = ("LP" + i + "ETCC").substring(0, 6);
            aTag.setLicPlate(aLicPlate);
            aTag.setVehicleClassCode(vehicleClass);  // or Three Axle Vehicle or Four Axle Vehicle or Five Axle Vehicle or Six plus Axles (other)
            String aVehicleYear = Integer.toString(Math.max(1985, 2010 - i));
            aTag.setVehicleYear(aVehicleYear);
            aTag.setVehicleColor("red");
            aTag.setVehicleMake("Ford");
            aTag.setVehicleModel("F" + i);
            aTag.setNickname("FordyFordyFordyFordyFordyFordyFordyFordyFordyFordyFordyFordyFordyFordyFordy".substring(0, i+2));
            aTag.setTemporaryLicPlate(false);
            multiVehicleTags[i] = aTag;
        }
        return multiVehicleTags;
    }

    private static void commitMultiVehicleUpload(AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO mvu) throws EtccException {
        // pre-condition: multi vehicle upload succeeded
        double minBalAmt = mvu.getMinBalAmt(); // info only, same as add tag
        double pendingDepositAmt = mvu.getPendingDepositAmt(); // info only, same as add tag
        double tagActivateAmt = mvu.getTagActivateAmt(); // info, tag activation fee
        double totalAmt = mvu.getTotalAmt(); // payable immediately
        logger.debug("minBalAmt=" + minBalAmt);
        logger.debug("pendingDepositAmt=" + pendingDepositAmt);
        logger.debug("tagActivateAmt=" + tagActivateAmt);
        logger.debug("totalAmt=" + totalAmt);
        // make payment
        BaseDTO paymentResultDTO = null;
        double paymentAmt = mvu.getTotalAmt();
        if (paymentAmt > 0) {
            BillingInfoDTO billingInfoDTO = billingInfoDto(acctLoginDto, mvu);
            paymentResultDTO =  new AccountDelegate().makePayment(acctLoginDto, billingInfoDTO, paymentAmt);
            assertNull("Multi Vehicle Upload commit failed due to payment failure", paymentResultDTO.getErrors());
        }
        // confirm tags
        if (mvu.getVehicleAddCount() > 0) {
            // Pre-condition: if payment was due, then it succeeded
            confirmAddTags(acctLoginDto, mvu);
        }
    }

    private static BillingInfoDTO billingInfoDto(AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO mvu) throws EtccException {
        // Retrieve existing billing info or ask for billing info.
        // As this is test only, we try to retrieve existing billing info. If
        // it fails we throw exception as this is a test. In production
        // the FDD specification needs to be followed.
        AccountDelegate acctDelegate = new AccountDelegate();
        BillingInfoDTO acctBillingInfo = acctDelegate.getBillingInfo(acctLoginDto);
        String msg = "Billing info is not available, please provide it";
        assertNull(msg, acctBillingInfo.getErrors());
        acctBillingInfo.setTransactionId(mvu.getTransactionId());
        return acctBillingInfo;
    }

    private static void confirmAddTags(AccountLoginDTO acctLoginDto, MultiVehicleUploadDTO mvuResult) throws EtccException {
        TagDelegate tagDelegate = new TagDelegate();
        BaseDTO resultDTO = null;
        resultDTO = tagDelegate.confirmAddTags(acctLoginDto, mvuResult.getTransactionId(), DeliveryMethod.MAIL);
        assertNull("Failed to commit multi vehicle upload tags.", resultDTO.getErrors());
    }
}
