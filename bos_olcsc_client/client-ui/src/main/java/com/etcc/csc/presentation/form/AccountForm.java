/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.form;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.struts.validator.ValidatorActionForm;

import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AddressUS;
import com.etcc.csc.dto.PaymentType;

public class AccountForm extends ValidatorActionForm implements AddressUS {
    private static final long serialVersionUID = 8293418964580657834L;

    private static final Logger logger = Logger.getLogger(AccountForm.class);

    private long acctId;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String plus4;
    private String homePhoNbr;
    private String homePhoNbr1;
    private String homePhoNbr2;
    private String homePhoNbr3;
    private String workPhoNbr;
    private String workPhoNbr1;
    private String workPhoNbr2;
    private String workPhoNbr3;
    private String workPhoExt;
    private String mobilePhoNbr;
    private String mobilePhoNbr1;
    private String mobilePhoNbr2;
    private String mobilePhoNbr3;
    private String driverLicNbr;
    private String driverLicState;
    private String companyName;
    private String companyTaxId;
    private String emailAddress;
    private String emailAddress2;
    private String emailAddress3;
    private boolean moStmtFlag;
    private boolean badAddressFlag;
    private boolean rebillFailedFlag;
    private double rebillAmt;
    private Calendar rebillDate;
    private double depAmt;
    private double balanceAmt;
    private double lowBalLevel;
    private Calendar balLastUpdated;
    private String acctStatusCode;
    private String acctTypeCode;
    private Calendar addressModified;
    private PaymentType pmtTypeEnum;
    private Calendar dateApproved;
    private String approvedBy;
    private boolean selectedForRebill;
    private long msId;
    private boolean veaFlag;
    private Calendar veaDate;
    private Calendar veaExpireDate;
    private String securityQuestion;
    private String securityQuestionAnswer;
    private String companyCode;
    private boolean adjustRebillAmt;
    private boolean editMode = false;
    private int noOfTags;
    
    private double reqMinRebillAmtForCC;
    private double reqMinRebillAmtForEFT;
    private double reqLowBalLevelForCC;
    private double reqLowBalLevelForEFT;
    private double reqMaxRebillAmt;
    private double reqMinRebillAmt;


    public AccountForm() {
    }

    public AccountForm(AccountDTO accountDTO, PaymentType paymentType) {
        this();
        switch (paymentType) {
        case CREDIT:
            setDepAmt(accountDTO.getDepositIncrementCC());
            break;
        case EFT:
            setDepAmt(accountDTO.getDepositIncrementEFT());
            break;
        }
        setPmtTypeEnum(accountDTO.getPmtTypeEnum());
        setNoOfTags(accountDTO.getNoOfTags());
        setReqMinRebillAmtForCC(accountDTO.getDepositAmtCC());
        setReqMinRebillAmtForEFT(accountDTO.getDepositAmtEFT());
        setReqLowBalLevelForCC(accountDTO.getMinBalCC());
        setReqLowBalLevelForEFT(accountDTO.getMinBalEFT());
    }

    public String getName() {
        return this.firstName + ' ' + this.middleInitial + ' ' + this.lastName;
    }

    /**
     * Returns the display value of the payment type for use in JSPs.
     * @return the display value of the payment type
     * @see PaymentType#getDisplay()
     */
    public String getPmtType() {
        return pmtTypeEnum.getDisplay();
    }

    public void setPmtTypeCode(String pmtTypeCode) {
        this.pmtTypeEnum = PaymentType.valueOfCode(pmtTypeCode);
    }

    /**
     * Returns the code for the payment type.
     * @return the code for the payment type
     * @see PaymentType#getCode()
     */
    public String getPmtTypeCode() {
        return (this.pmtTypeEnum == null ? "" : String.valueOf(this.pmtTypeEnum.getCode()));
    }

    public void setRebillAmt(double rebillAmt) {
        logger.debug("accountform.setRebillAmt=" + rebillAmt);
        this.rebillAmt = rebillAmt;
    }

    public void setHomePhoNbr(String homePhoNbr) {
        this.homePhoNbr = homePhoNbr;
        try {
            if (homePhoNbr != null) {
                homePhoNbr = homePhoNbr.replaceAll("\\D", "");
                
                homePhoNbr1 = homePhoNbr.substring(0, 3);
                homePhoNbr2 = homePhoNbr.substring(3, 6);
                homePhoNbr3 = homePhoNbr.substring(6, 10);
            } else {
                homePhoNbr1 = null;
                homePhoNbr2 = null;
                homePhoNbr3 = null;
            }
        } catch (Exception e) {
        }

    }

    public String getHomePhoNbr() {
        if (homePhoNbr1 != null && homePhoNbr2 != null && homePhoNbr3 != null) {
            return homePhoNbr1 + homePhoNbr2 + homePhoNbr3;
        } else {
            return null;
        }
    }

    public void setWorkPhoNbr(String workPhoNbr) {
        this.workPhoNbr = workPhoNbr;
        try {
            if (workPhoNbr != null) {
                workPhoNbr = workPhoNbr.replaceAll("\\D", "");
                
                workPhoNbr1 = workPhoNbr.substring(0, 3);
                workPhoNbr2 = workPhoNbr.substring(3, 6);
                workPhoNbr3 = workPhoNbr.substring(6, 10);
            } else {
                workPhoNbr1 = null;
                workPhoNbr2 = null;
                workPhoNbr3 = null;
            }
        } catch (Exception e) {
        }

    }

    public String getWorkPhoNbr() {
        if (workPhoNbr1 != null && workPhoNbr2 != null && workPhoNbr3 != null) {
            return workPhoNbr1 + workPhoNbr2 + workPhoNbr3;
        } else {
            return null;
        }
    }

    public void setMobilePhoNbr(String mobilePhoNbr) {
        this.mobilePhoNbr = mobilePhoNbr;
        try {
            if (mobilePhoNbr != null) {
                mobilePhoNbr = mobilePhoNbr.replaceAll("\\D", "");
                
                mobilePhoNbr1 = mobilePhoNbr.substring(0, 3);
                mobilePhoNbr2 = mobilePhoNbr.substring(3, 6);
                mobilePhoNbr3 = mobilePhoNbr.substring(6, 10);
            } else {
                mobilePhoNbr1 = null;
                mobilePhoNbr2 = null;
                mobilePhoNbr3 = null;
            }
        } catch (Exception e) {
        }

    }

    public String getMobilePhoNbr() {
        if (mobilePhoNbr1 != null && mobilePhoNbr2 != null 
            && mobilePhoNbr3 != null) {
            
            return mobilePhoNbr1 + mobilePhoNbr2 + mobilePhoNbr3;
        } else {
            return null;
        }
    }

    public double getReqMinRebillAmt() {
        logger.debug("noOfTags=" + noOfTags + ";depAmt=" + depAmt + ";pmtTypeCode=" + pmtTypeEnum + ";reqMinRebillAmtForCC=" + reqMinRebillAmtForCC + ";reqMinRebillAmtForEFT=" + reqMinRebillAmtForEFT);
        /*
        int units = noOfTags / 3;
        if (units*3 != noOfTags) units++;
        int minRebillAmt = (int)(units*depAmt);
        if ((pmtTypeCode.equals(BillingInfoDTO.PAYMENT_CODE_CREDIT)) && (minRebillAmt > 600)) {
            minRebillAmt = 600;
        } else if ((pmtTypeCode.equals(BillingInfoDTO.PAYMENT_CODE_EFT)) && (minRebillAmt > 1200)) {
            minRebillAmt = 1200;
        }
        return minRebillAmt;
        */
        double minRebillAmt = 0;
        if (pmtTypeEnum == PaymentType.CREDIT) {
            minRebillAmt = reqMinRebillAmtForCC;
        } else if (pmtTypeEnum == PaymentType.EFT) {
            minRebillAmt = reqMinRebillAmtForEFT;
        }
        logger.debug("getReqMinRebillAmt completed");
        return minRebillAmt;
    }

    /*
    public int getReqMinRebillAmtForCC() {
        int units = noOfTags / 3;
        if (units*3 != noOfTags) units++;
        int reqMinRebillAmtForCC = (int)(units*40);
        if (reqMinRebillAmtForCC > 600) reqMinRebillAmtForCC = 600;
        return reqMinRebillAmtForCC;
    }

    public int getReqMinRebillAmtForEFT() {
        int units = noOfTags / 3;
        if (units*3 != noOfTags) units++;
        int reqMinRebillAmtForEFT = (int)(units*80);
        if (reqMinRebillAmtForEFT > 1200) reqMinRebillAmtForEFT = 1200;
        return reqMinRebillAmtForEFT;
    }

    public int getReqLowBalLevelForCC() {
        int units = noOfTags / 3;
        if (units*3 != noOfTags) units++;
        int reqLowBalLevelForCC = (int)(units*10);
        if (reqLowBalLevelForCC > 150) reqLowBalLevelForCC = 150;
        return reqLowBalLevelForCC;
    }

    public int getReqLowBalLevelForEFT() {
        int units = noOfTags / 3;
        if (units*3 != noOfTags) units++;
        int reqLowBalLevelForEFT = (int)(units*20);
        if (reqLowBalLevelForEFT > 300) reqLowBalLevelForEFT = 300;
        return reqLowBalLevelForEFT;
    }
    */


    // *** Standard Getters/Setters ***
    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return acctId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setZipCode(String zipCode) {
        this.zip = zipCode;
    }

    public String getZipCode() {
        return zip;
    }

    public void setPlus4(String plus4) {
        this.plus4 = plus4;
    }

    public String getPlus4() {
        return plus4;
    }

    public void setWorkPhoExt(String workPhoExt) {
        this.workPhoExt = workPhoExt;
    }

    public String getWorkPhoExt() {
        return workPhoExt;
    }

    public void setDriverLicNbr(String driverLicNbr) {
        this.driverLicNbr = driverLicNbr;
    }

    public String getDriverLicNbr() {
        return driverLicNbr;
    }

    public void setDriverLicState(String driverLicState) {
        this.driverLicState = driverLicState;
    }

    public String getDriverLicState() {
        return driverLicState;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyTaxId(String companyTaxId) {
        this.companyTaxId = companyTaxId;
    }

    public String getCompanyTaxId() {
        return companyTaxId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setMoStmtFlag(boolean moStmtFlag) {
        this.moStmtFlag = moStmtFlag;
    }

    public boolean isMoStmtFlag() {
        return moStmtFlag;
    }

    public void setBadAddressFlag(boolean badAddressFlag) {
        this.badAddressFlag = badAddressFlag;
    }

    public boolean isBadAddressFlag() {
        return badAddressFlag;
    }

    public void setRebillFailedFlag(boolean rebillFailedFlag) {
        this.rebillFailedFlag = rebillFailedFlag;
    }

    public boolean isRebillFailedFlag() {
        return rebillFailedFlag;
    }

    public double getRebillAmt() {
        return rebillAmt;
    }

    public void setRebillDate(Calendar rebillDate) {
        this.rebillDate = rebillDate;
    }

    public Calendar getRebillDate() {
        return rebillDate;
    }

    public void setDepAmt(double depAmt) {
        this.depAmt = depAmt;
    }

    public double getDepAmt() {
        return depAmt;
    }

    public void setBalanceAmt(double balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public double getBalanceAmt() {
        return balanceAmt;
    }

    public void setLowBalLevel(double lowBalLevel) {
        this.lowBalLevel = lowBalLevel;
    }

    public double getLowBalLevel() {
        return lowBalLevel;
    }

    public void setBalLastUpdated(Calendar balLastUpdated) {
        this.balLastUpdated = balLastUpdated;
    }

    public Calendar getBalLastUpdated() {
        return balLastUpdated;
    }

    public void setAcctStatusCode(String acctStatusCode) {
        this.acctStatusCode = acctStatusCode;
    }

    public String getAcctStatusCode() {
        return acctStatusCode;
    }

    public void setAcctTypeCode(String acctTypeCode) {
        this.acctTypeCode = acctTypeCode;
    }

    public String getAcctTypeCode() {
        return acctTypeCode;
    }

    public void setAddressModified(Calendar addressModified) {
        this.addressModified = addressModified;
    }

    public Calendar getAddressModified() {
        return addressModified;
    }

    public void setDateApproved(Calendar dateApproved) {
        this.dateApproved = dateApproved;
    }

    public Calendar getDateApproved() {
        return dateApproved;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setSelectedForRebill(boolean selectedForRebill) {
        this.selectedForRebill = selectedForRebill;
    }

    public boolean isSelectedForRebill() {
        return selectedForRebill;
    }

    public void setMsId(long msId) {
        this.msId = msId;
    }

    public long getMsId() {
        return msId;
    }

    public void setVeaFlag(boolean veaFlag) {
        this.veaFlag = veaFlag;
    }

    public boolean isVeaFlag() {
        return veaFlag;
    }

    public void setVeaDate(Calendar veaDate) {
        this.veaDate = veaDate;
    }

    public Calendar getVeaDate() {
        return veaDate;
    }

    public void setVeaExpireDate(Calendar veaExpireDate) {
        this.veaExpireDate = veaExpireDate;
    }

    public Calendar getVeaExpireDate() {
        return veaExpireDate;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setAdjustRebillAmt(boolean adjustRebillAmt) {
        this.adjustRebillAmt = adjustRebillAmt;
    }

    public boolean isAdjustRebillAmt() {
        return adjustRebillAmt;
    }

    public String getHomePhoNbr1() {
        return homePhoNbr1;
    }
    public String getHomePhoNbr2() {
        return homePhoNbr2;
    }
    public String getHomePhoNbr3() {
        return homePhoNbr3;
    }

    public void setHomePhoNbr1(String value) {
        homePhoNbr1 = value;
    }

    public void setHomePhoNbr2(String value) {
        homePhoNbr2 = value;
    }
    public void setHomePhoNbr3(String value) {
        homePhoNbr3 = value;
    }

    public void setWorkPhoNbr1(String workPhoNbr1) {
        this.workPhoNbr1 = workPhoNbr1;
    }

    public String getWorkPhoNbr1() {
        return workPhoNbr1;
    }

    public void setWorkPhoNbr2(String workPhoNbr2) {
        this.workPhoNbr2 = workPhoNbr2;
    }

    public String getWorkPhoNbr2() {
        return workPhoNbr2;
    }

    public void setWorkPhoNbr3(String workPhoNbr3) {
        this.workPhoNbr3 = workPhoNbr3;
    }

    public String getWorkPhoNbr3() {
        return workPhoNbr3;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setMobilePhoNbr1(String mobilePhoNbr1) {
        this.mobilePhoNbr1 = mobilePhoNbr1;
    }

    public String getMobilePhoNbr1() {
        return mobilePhoNbr1;
    }

    public void setMobilePhoNbr2(String mobilePhoNbr2) {
        this.mobilePhoNbr2 = mobilePhoNbr2;
    }

    public String getMobilePhoNbr2() {
        return mobilePhoNbr2;
    }

    public void setMobilePhoNbr3(String mobilePhoNbr3) {
        this.mobilePhoNbr3 = mobilePhoNbr3;
    }

    public String getMobilePhoNbr3() {
        return mobilePhoNbr3;
    }

    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
    }

    public String getEmailAddress2() {
        return emailAddress2;
    }

    public void setEmailAddress3(String emailAddress3) {
        this.emailAddress3 = emailAddress3;
    }

    public String getEmailAddress3() {
        return emailAddress3;
    }
    
    public void setNoOfTags(int noOfTags) {
        this.noOfTags = noOfTags;
    }

    public int getNoOfTags() {
        return noOfTags;
    }

    public void setReqMinRebillAmtForCC(double reqMinRebillAmtForCC) {
        this.reqMinRebillAmtForCC = reqMinRebillAmtForCC;
    }

    public double getReqMinRebillAmtForCC() {
        return reqMinRebillAmtForCC;
    }

    public void setReqMinRebillAmtForEFT(double reqMinRebillAmtForEFT) {
        this.reqMinRebillAmtForEFT = reqMinRebillAmtForEFT;
    }

    public double getReqMinRebillAmtForEFT() {
        return reqMinRebillAmtForEFT;
    }

    public void setReqLowBalLevelForCC(double reqLowBalLevelForCC) {
        this.reqLowBalLevelForCC = reqLowBalLevelForCC;
    }

    public double getReqLowBalLevelForCC() {
        return reqLowBalLevelForCC;
    }

    public void setReqLowBalLevelForEFT(double reqLowBalLevelForEFT) {
        this.reqLowBalLevelForEFT = reqLowBalLevelForEFT;
    }

    public double getReqLowBalLevelForEFT() {
        return reqLowBalLevelForEFT;
    }

    public void setReqMaxRebillAmt(double reqMaxRebillAmt) {
        this.reqMaxRebillAmt = reqMaxRebillAmt;
    }

    public double getReqMaxRebillAmt() {
        return reqMaxRebillAmt;
    }

    public void setReqMinRebillAmt(double reqMinRebillAmt) {
        this.reqMinRebillAmt = reqMinRebillAmt;
    }

    public double get_reqMinRebillAmt() {
        return reqMinRebillAmt;
    }

    public PaymentType getPmtTypeEnum() {
        return pmtTypeEnum;
    }

    public void setPmtTypeEnum(PaymentType pmtTypeEnum) {
        this.pmtTypeEnum = pmtTypeEnum;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
