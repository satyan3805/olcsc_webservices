/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.CoreDateUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Account Data Transfer Object - represents a user account in the system.
 */
public class AccountDTO extends BaseDTO implements Address, AccountName {
    /**
     * Unique Id for serialization with version.
     */
    // Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = 1490837173682007076L;

    private static final Logger logger = Logger.getLogger(AccountDTO.class);

    private long acctId;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String city;
    private String state;
    private String zip;
    private String plus4;
    private String country;
    private String countryCode;  // unique in DB
    private String homePhoNbr;
    private String workPhoNbr;
    private String workPhoExt;
    private String driverLicNbr;
    private String driverLicState;
    private String companyName;
    private String companyTaxId;
    private String emailAddress;
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
    private String acctTypeDescr;
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
    private long securityQuestionID;
    private String companyCode;
    private boolean adjustRebillAmt;
    private String vpnType;
    private String mobilePhoNbr;
    private String emailAddress2;
    private String emailAddress3;
    private int noOfTags;

    private double depositIncrementCC;
    private double depositIncrementEFT;
    private double minBalIncrementCC;
    private double minBalIncrementEFT;
    private double depositAmtCC;
    private double depositAmtEFT;
    private double minBalCC;
    private double minBalEFT;

    private boolean suspensionFlag;
    private boolean violationFlag;
    private List<String> violationLicPlates;

    private boolean byMail;
    private boolean byPhone;
    private boolean byEmail;
    private boolean revenueAccount;
	//Pay Instalment change
    private boolean paymentPlanExist;
    private double paymentPlanDueAmount;
   //Express Account change
    private String plan;
    // Added as part of CCRMA changes
    private String homePhoExt;
    private String mobilePhoExt;
    private String smsAlertsOptIn;

    //
    // public String getWorkPhoneNumberDisplay() {
    // if (workPhoNbr!=null)
    // return workPhoNbr.concat(((workPhoExt != null) && (workPhoExt.trim().length() > 0)) ? " x" + workPhoExt : "");
    // else
    // return null;
    // }

    /**
     * Is there an extension with the work phone number with this account.
     * @return if there is a Work Phone Number set.
     */
    @IgnoreProperty
    public boolean isWorkPhoExtSet() {
        return !StringUtil.isEmpty(this.workPhoExt);
    }

    /**
     * Gets the masked version of the Driver's license for display.
     * @return Masked version of the DL number.
     * @deprecated
     */
    @IgnoreProperty
    @Deprecated
    public String getDriverLicDisplay() {
    	return StringUtil.maskAccount(this.driverLicNbr, 5);
        // return driverLicState + " DL: *****" + driverLicNbr.substring(5);
    }

    /**
     * Returns if the Account Address is a USA or not.
     * @return if USA.
     */
    @IgnoreProperty
    public boolean isUsa() {
        return (this.country == null || this.country.equals(AddressUS.COUNTRY_CODE_USA));
    }

    /**
     * Gets an HTML formatted version of this Accounts address.  Needs to be replaced by a Custom Tag.
     * @return HTML formatted version of the address.
     * @deprecated use {@link FormatUtil#formatAddress(Address, String, boolean)}
     */
    @Deprecated
    @IgnoreProperty
    public String getAddressDisplay() {
        logger.info("Deprecated (anti-pattern) method used.  Please replace with a custom tag.");
        String out;

        if (this.country == null || this.country.equals("USA")) {
            out = this.address1 + "<br />"
                    + (((this.address2 != null) && (this.address2.trim().length() > 0)) ? (this.address2 + "<br />") : "") + this.city
                    + ", " + this.state + " " + this.zip + ((this.plus4 != null && this.plus4.trim().length() > 0) ? "-" + this.plus4 : "");
        } else {
            out = this.address1 + "<br />"
                    + (((this.address2 != null) && (this.address2.trim().length() > 0)) ? (this.address2 + "<br />") : "")
                    + (((this.address3 != null) && (this.address3.trim().length() > 0)) ? (this.address3 + "<br />") : "")
                    + (((this.address4 != null) && (this.address4.trim().length() > 0)) ? (this.address4 + "<br />") : "") + this.country;
        }
        return out;
    }

    /**
     * Gets the Address as a String delimited line.
     * Use {@code FormatUtil.formatAddress(address, ",", false)}
     * @param delimiter the Delimiter to use (ex. ',')
     * @return The Address as a String, broken out by the supplied delimiter.
     * @deprecated use {@link FormatUtil#formatAddress(Address, String, boolean)}
     */
    @IgnoreProperty
    @Deprecated
    public String getAddressAsLine(final String delimiter) {
        StringBuilder sb = new StringBuilder();
        getAddrSegment(sb, this.address1, delimiter);
        getAddrSegment(sb, this.address2, delimiter);
        getAddrSegment(sb, this.address3, delimiter);
        getAddrSegment(sb, this.address4, delimiter);
        getAddrSegment(sb, this.city, delimiter);
        getAddrSegment(sb, this.state, delimiter);
        getAddrSegment(sb, this.country, delimiter);
        if (this.country == null || this.country.equals("USA")) {
            if (this.plus4 != null && this.plus4.trim().length() > 0) {
                getAddrSegment(sb, this.zip, null);
                sb.append('-');
                getAddrSegment(sb, this.plus4, null);
            } else {
                getAddrSegment(sb, this.zip, null);
            }
        } else {
            getAddrSegment(sb, this.zip, null);
        }

        return sb.toString();
    }

    @Deprecated
    private StringBuilder getAddrSegment(final StringBuilder target, final String segment, final String delimiter) {
        if (segment != null) {
            // Initial length of the Target StringBuilder.
            final int targetLength = target.length();
            target.append(segment.trim());
            if (target.length() > targetLength && delimiter != null) {
                target.append(delimiter);
            }
        }
        return target;
    }

    /**
     * Returns the display value of the payment type for use in JSPs.
     * @return the display value of the payment type
     * @see PaymentType#getDisplay()
     */
    @IgnoreProperty
    public String getPmtType() {
    	return (this.pmtTypeEnum == null ? null : this.pmtTypeEnum.getDisplay());
    }

    /**
     * Returns the code for the payment type.
     * @return the code for the payment type
     * @see PaymentType#getCode()
     */
    @IgnoreProperty
    public String getPmtTypeCode() {
    	return (this.pmtTypeEnum == null ? "" : String.valueOf(this.pmtTypeEnum.getCode()));
    }

    /**
     * Returns the string value of the payment type.
     * @return the string value of the payment type
     * @see PaymentType#toString()
     */
    @IgnoreProperty
    public String getPmtTypeString() {
    	return (this.pmtTypeEnum == null ? null : this.pmtTypeEnum.toString());
    }

    /**
     * Gets the full name of the Account Holder.
     * @return the full name of the account holder.
     */
    @IgnoreProperty
    public String getName() {
        StringBuilder name = new StringBuilder();
        if (this.firstName != null)
            name.append(this.firstName);
        if (this.middleInitial != null)
            name.append(" ").append(this.middleInitial);
        if (this.lastName != null)
            name.append(" ").append(this.lastName);
        return name.toString();
    }

    @Deprecated
    @IgnoreProperty
    public String getViolationLicPlatesDisplay() {
        if (this.violationLicPlates == null || this.violationLicPlates.size() == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.violationLicPlates.size(); i++) {
                if (i == (this.violationLicPlates.size() - 1)) {
                    if (i != 0) {
                        sb.append(" and " + this.violationLicPlates.get(i));
                    } else {
                        sb.append(this.violationLicPlates.get(i));
                    }
                } else {
                    if (i != 0) {
                        sb.append(", " + this.violationLicPlates.get(i));
                    } else {
                        sb.append(this.violationLicPlates.get(i));
                    }
                }
            }
            return sb.toString();
        }
    }

    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("AccountDTO[");
    	sb.append("acctId=");
    	sb.append(this.acctId);
    	sb.append(",firstName=");
    	sb.append(this.firstName);
    	sb.append(",middleInitial=");
    	sb.append(this.middleInitial);
    	sb.append(",lastName=");
    	sb.append(this.lastName);
    	sb.append(",address1=");
    	sb.append(this.address1);
    	sb.append(",address2=");
    	sb.append(this.address2);
    	sb.append(",city=");
    	sb.append(this.city);
    	sb.append(",state=");
    	sb.append(this.state);
    	sb.append(",zipCode=");
    	sb.append(this.zip);
    	sb.append(",plus4=");
    	sb.append(this.plus4);
    	sb.append(",homePhoNbr=");
    	sb.append(this.homePhoNbr);
    	sb.append(",workPhoNbr=");
    	sb.append(this.workPhoNbr);
    	sb.append(",workPhoExt=");
    	sb.append(this.workPhoExt);
    	sb.append(",driverLicNbr=");
    	sb.append(this.driverLicNbr);
    	sb.append(",driverLicState=");
    	sb.append(this.driverLicState);
    	sb.append(",companyName=");
    	sb.append(this.companyName);
    	sb.append(",companyTaxId=");
    	sb.append(this.companyTaxId);
    	sb.append(",emailAddress=");
    	sb.append(this.emailAddress);
    	sb.append(",moStmtFlag=");
    	sb.append(this.moStmtFlag);
    	sb.append(",badAddressFlag=");
    	sb.append(this.badAddressFlag);
    	sb.append(",rebillFailedFlag=");
    	sb.append(this.rebillFailedFlag);
    	sb.append(",rebillAmt=");
    	sb.append(this.rebillAmt);
    	sb.append(",rebillDate=");
    	sb.append(CoreDateUtil.getMediumDateTime(this.rebillDate));
    	sb.append(",depAmt=");
    	sb.append(this.depAmt);
    	sb.append(",balanceAmt=");
    	sb.append(this.balanceAmt);
    	sb.append(",lowBalLevel=");
    	sb.append(this.lowBalLevel);
    	sb.append(",balLastUpdated=");
    	sb.append(CoreDateUtil.getMediumDateTime(this.balLastUpdated));
    	sb.append(",acctStatusCode=");
    	sb.append(this.acctStatusCode);
    	sb.append(",acctTypeCode=");
    	sb.append(this.acctTypeCode);
    	sb.append(",addressModified=");
    	sb.append(CoreDateUtil.getMediumDateTime(this.addressModified));
    	sb.append(",pmtTypeEnum=");
    	sb.append(this.pmtTypeEnum);
    	sb.append(",dateApproved=");
    	sb.append(CoreDateUtil.getMediumDateTime(this.dateApproved));
    	sb.append(",approvedBy=");
    	sb.append(this.approvedBy);
    	sb.append(",selectedForRebill=");
    	sb.append(this.selectedForRebill);
    	sb.append(",msId=");
    	sb.append(this.msId);
    	sb.append(",veaFlag=");
    	sb.append(this.veaFlag);
    	sb.append(",veaDate=");
        sb.append(CoreDateUtil.getMediumDateTime(this.veaDate));
    	sb.append(",veaExpireDate=");
    	sb.append(CoreDateUtil.getMediumDateTime(this.veaExpireDate));
    	sb.append(",securityQuestion=");
    	sb.append(this.securityQuestion);
    	sb.append(",securityQuestionAnswer=<*******>");
    	sb.append(",companyCode=");
    	sb.append(this.companyCode);
    	sb.append(",adjustRebillAmt=");
    	sb.append(this.adjustRebillAmt);
    	sb.append(",vpnType=");
    	sb.append(this.vpnType);
    	sb.append(",mobilePhoNbr=");
    	sb.append(this.mobilePhoNbr);
    	sb.append(",emailAddress2=");
    	sb.append(this.emailAddress2);
    	sb.append(",emailAddress3=");
    	sb.append(this.emailAddress3);
    	sb.append(",homePhoExt=");
    	sb.append(this.homePhoExt);
    	sb.append(",mobilePhoExt=");
    	sb.append(this.mobilePhoExt);
    	sb.append(",smsAlertsOptIn=");
    	sb.append(this.smsAlertsOptIn);
    	sb.append("]");
    	return sb.toString();
    }


    /**
     * @return the acctId
     */
    public long getAcctId() {
        return this.acctId;
        // end getAcctId
    }

    /**
     * @param acctId the acctId to set
     */
    public void setAcctId(long acctId) {
        this.acctId = acctId;
        // end setAcctId
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return this.middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the address1
     */
    public String getAddress1() {
        return this.address1;
        // end getAddress1
    }

    /**
     * @param address1 the address1 to set
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
        // end setAddress1
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
        return this.address2;
        // end getAddress2
    }

    /**
     * @param address2 the address2 to set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
        // end setAddress2
    }

    /**
     * @return the address3
     */
    public String getAddress3() {
        return this.address3;
        // end getAddress3
    }

    /**
     * @param address3 the address3 to set
     */
    public void setAddress3(String address3) {
        this.address3 = address3;
        // end setAddress3
    }

    /**
     * @return the address4
     */
    public String getAddress4() {
        return this.address4;
        // end getAddress4
    }

    /**
     * @param address4 the address4 to set
     */
    public void setAddress4(String address4) {
        this.address4 = address4;
        // end setAddress4
    }

    /**
     * @return the city
     */
    public String getCity() {
        return this.city;
        // end getCity
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
        // end setCity
    }

    /**
     * @return the state
     */
    public String getState() {
        return this.state;
        // end getState
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
        // end setState
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return this.zip;
        // end getZipCode
    }

    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zip = zipCode;
        // end setZipCode
    }

    /**
     * @return the plus4
     */
    public String getPlus4() {
        return this.plus4;
        // end getPlus4
    }

    /**
     * @param plus4 the plus4 to set
     */
    public void setPlus4(String plus4) {
        this.plus4 = plus4;
        // end setPlus4
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return this.country;
        // end getCountry
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
        // end setCountry
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return this.countryCode;
        // end getCountryCode
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        // end setCountryCode
    }

    /**
     * @return the homePhoNbr
     */
    public String getHomePhoNbr() {
        return this.homePhoNbr;
        // end getHomePhoNbr
    }

    /**
     * @param homePhoNbr the homePhoNbr to set
     */
    public void setHomePhoNbr(String homePhoNbr) {
        this.homePhoNbr = homePhoNbr;
        // end setHomePhoNbr
    }

    /**
     * @return the workPhoNbr
     */
    public String getWorkPhoNbr() {
        return this.workPhoNbr;
        // end getWorkPhoNbr
    }

    /**
     * @param workPhoNbr the workPhoNbr to set
     */
    public void setWorkPhoNbr(String workPhoNbr) {
        this.workPhoNbr = workPhoNbr;
        // end setWorkPhoNbr
    }

    /**
     * @return the workPhoExt
     */
    public String getWorkPhoExt() {
        return this.workPhoExt;
        // end getWorkPhoExt
    }

    /**
     * @param workPhoExt the workPhoExt to set
     */
    public void setWorkPhoExt(String workPhoExt) {
        this.workPhoExt = workPhoExt;
        // end setWorkPhoExt
    }

    /**
     * @return the driverLicNbr
     */
    public String getDriverLicNbr() {
        return this.driverLicNbr;
        // end getDriverLicNbr
    }

    /**
     * @param driverLicNbr the driverLicNbr to set
     */
    public void setDriverLicNbr(String driverLicNbr) {
        this.driverLicNbr = driverLicNbr;
        // end setDriverLicNbr
    }

    /**
     * @return the driverLicState
     */
    public String getDriverLicState() {
        return this.driverLicState;
        // end getDriverLicState
    }

    /**
     * @param driverLicState the driverLicState to set
     */
    public void setDriverLicState(String driverLicState) {
        this.driverLicState = driverLicState;
        // end setDriverLicState
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the companyTaxId
     */
    public String getCompanyTaxId() {
        return this.companyTaxId;
        // end getCompanyTaxId
    }

    /**
     * @param companyTaxId the companyTaxId to set
     */
    public void setCompanyTaxId(String companyTaxId) {
        this.companyTaxId = companyTaxId;
        // end setCompanyTaxId
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return this.emailAddress;
        // end getEmailAddress
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        // end setEmailAddress
    }

    /**
     * @return the moStmtFlag
     */
    public boolean isMoStmtFlag() {
        return this.moStmtFlag;
        // end isMoStmtFlag
    }

    /**
     * @param moStmtFlag the moStmtFlag to set
     */
    public void setMoStmtFlag(boolean moStmtFlag) {
        this.moStmtFlag = moStmtFlag;
        // end setMoStmtFlag
    }

    /**
     * @return the badAddressFlag
     */
    public boolean isBadAddressFlag() {
        return this.badAddressFlag;
        // end isBadAddressFlag
    }

    /**
     * @param badAddressFlag the badAddressFlag to set
     */
    public void setBadAddressFlag(boolean badAddressFlag) {
        this.badAddressFlag = badAddressFlag;
        // end setBadAddressFlag
    }

    /**
     * @return the rebillFailedFlag
     */
    public boolean isRebillFailedFlag() {
        return this.rebillFailedFlag;
        // end isRebillFailedFlag
    }

    /**
     * @param rebillFailedFlag the rebillFailedFlag to set
     */
    public void setRebillFailedFlag(boolean rebillFailedFlag) {
        this.rebillFailedFlag = rebillFailedFlag;
        // end setRebillFailedFlag
    }

    /**
     * @return the rebillAmt
     */
    public double getRebillAmt() {
        return this.rebillAmt;
        // end getRebillAmt
    }

    /**
     * @param rebillAmt the rebillAmt to set
     */
    public void setRebillAmt(double rebillAmt) {
        this.rebillAmt = rebillAmt;
        // end setRebillAmt
    }

    /**
     * @return the rebillDate
     */
    public Calendar getRebillDate() {
        return this.rebillDate;
        // end getRebillDate
    }

    /**
     * @param rebillDate the rebillDate to set
     */
    public void setRebillDate(Calendar rebillDate) {
        this.rebillDate = rebillDate;
        // end setRebillDate
    }

    /**
     * @return the depAmt
     */
    public double getDepAmt() {
        return this.depAmt;
        // end getDepAmt
    }

    /**
     * @param depAmt the depAmt to set
     */
    public void setDepAmt(double depAmt) {
        this.depAmt = depAmt;
        // end setDepAmt
    }

    /**
     * @return the balanceAmt
     */
    public double getBalanceAmt() {
        return this.balanceAmt;
        // end getBalanceAmt
    }

    /**
     * @param balanceAmt the balanceAmt to set
     */
    public void setBalanceAmt(double balanceAmt) {
        this.balanceAmt = balanceAmt;
        // end setBalanceAmt
    }

    /**
     * @return the lowBalLevel
     */
    public double getLowBalLevel() {
        return this.lowBalLevel;
        // end getLowBalLevel
    }

    /**
     * @param lowBalLevel the lowBalLevel to set
     */
    public void setLowBalLevel(double lowBalLevel) {
        this.lowBalLevel = lowBalLevel;
        // end setLowBalLevel
    }

    /**
     * @return the balLastUpdated
     */
    public Calendar getBalLastUpdated() {
        return this.balLastUpdated;
        // end getBalLastUpdated
    }

    /**
     * @param balLastUpdated the balLastUpdated to set
     */
    public void setBalLastUpdated(Calendar balLastUpdated) {
        this.balLastUpdated = balLastUpdated;
        // end setBalLastUpdated
    }

    /**
     * @return the acctStatusCode
     */
    public String getAcctStatusCode() {
        return this.acctStatusCode;
        // end getAcctStatusCode
    }

    /**
     * @param acctStatusCode the acctStatusCode to set
     */
    public void setAcctStatusCode(String acctStatusCode) {
        this.acctStatusCode = acctStatusCode;
        // end setAcctStatusCode
    }

    /**
     * @return the acctTypeCode
     */
    public String getAcctTypeCode() {
        return this.acctTypeCode;
        // end getAcctTypeCode
    }

    /**
     * @param acctTypeCode the acctTypeCode to set
     */
    public void setAcctTypeCode(String acctTypeCode) {
        this.acctTypeCode = acctTypeCode;
        // end setAcctTypeCode
    }

    /**
     * @return the acctTypeDescr
     */
    public String getAcctTypeDescr() {
        return this.acctTypeDescr;
        // end getAcctTypeDescr
    }

    /**
     * @param acctTypeDescr the acctTypeDescr to set
     */
    public void setAcctTypeDescr(String acctTypeDescr) {
        this.acctTypeDescr = acctTypeDescr;
        // end setAcctTypeDescr
    }

    /**
     * @return the addressModified
     */
    public Calendar getAddressModified() {
        return this.addressModified;
        // end getAddressModified
    }

    /**
     * @param addressModified the addressModified to set
     */
    public void setAddressModified(Calendar addressModified) {
        this.addressModified = addressModified;
        // end setAddressModified
    }

    /**
     * @return the pmtTypeEnum
     */
    public PaymentType getPmtTypeEnum() {
        return this.pmtTypeEnum;
    }

    /**
     * @param pmtType the pmtTypeEnum to set
     */
    public void setPmtTypeEnum(PaymentType pmtType) {
        this.pmtTypeEnum = pmtType;
    }

    /**
     * @return the dateApproved
     */
    public Calendar getDateApproved() {
        return this.dateApproved;
        // end getDateApproved
    }

    /**
     * @param dateApproved the dateApproved to set
     */
    public void setDateApproved(Calendar dateApproved) {
        this.dateApproved = dateApproved;
        // end setDateApproved
    }

    /**
     * @return the approvedBy
     */
    public String getApprovedBy() {
        return this.approvedBy;
        // end getApprovedBy
    }

    /**
     * @param approvedBy the approvedBy to set
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
        // end setApprovedBy
    }

    /**
     * @return the selectedForRebill
     */
    public boolean isSelectedForRebill() {
        return this.selectedForRebill;
        // end isSelectedForRebill
    }

    /**
     * @param selectedForRebill the selectedForRebill to set
     */
    public void setSelectedForRebill(boolean selectedForRebill) {
        this.selectedForRebill = selectedForRebill;
        // end setSelectedForRebill
    }

    /**
     * @return the msId
     */
    public long getMsId() {
        return this.msId;
        // end getMsId
    }

    /**
     * @param msId the msId to set
     */
    public void setMsId(long msId) {
        this.msId = msId;
        // end setMsId
    }

    /**
     * @return the veaFlag
     */
    public boolean isVeaFlag() {
        return this.veaFlag;
        // end isVeaFlag
    }

    /**
     * @param veaFlag the veaFlag to set
     */
    public void setVeaFlag(boolean veaFlag) {
        this.veaFlag = veaFlag;
        // end setVeaFlag
    }

    /**
     * @return the veaDate
     */
    public Calendar getVeaDate() {
        return this.veaDate;
        // end getVeaDate
    }

    /**
     * @param veaDate the veaDate to set
     */
    public void setVeaDate(Calendar veaDate) {
        this.veaDate = veaDate;
        // end setVeaDate
    }

    /**
     * @return the veaExpireDate
     */
    public Calendar getVeaExpireDate() {
        return this.veaExpireDate;
        // end getVeaExpireDate
    }

    /**
     * @param veaExpireDate the veaExpireDate to set
     */
    public void setVeaExpireDate(Calendar veaExpireDate) {
        this.veaExpireDate = veaExpireDate;
        // end setVeaExpireDate
    }

    /**
     * @return the securityQuestion
     */
    public String getSecurityQuestion() {
        return this.securityQuestion;
        // end getSecurityQuestion
    }

    /**
     * @param securityQuestion the securityQuestion to set
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
        // end setSecurityQuestion
    }

    /**
     * @return the securityQuestionAnswer
     */
    public String getSecurityQuestionAnswer() {
        return this.securityQuestionAnswer;
        // end getSecurityQuestionAnswer
    }

    /**
     * @param securityQuestionAnswer the securityQuestionAnswer to set
     */
    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
        // end setSecurityQuestionAnswer
    }

    /**
     * @return the securityQuestionID
     */
    public long getSecurityQuestionID() {
        return this.securityQuestionID;
        // end getSecurityQuestionID
    }

    /**
     * @param securityQuestionID the securityQuestionID to set
     */
    public void setSecurityQuestionID(long securityQuestionID) {
        this.securityQuestionID = securityQuestionID;
        // end setSecurityQuestionID
    }

    /**
     * @return the companyCode
     */
    public String getCompanyCode() {
        return this.companyCode;
        // end getCompanyCode
    }

    /**
     * @param companyCode the companyCode to set
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        // end setCompanyCode
    }

    /**
     * @return the adjustRebillAmt
     */
    public boolean isAdjustRebillAmt() {
        return this.adjustRebillAmt;
        // end isAdjustRebillAmt
    }

    /**
     * @param adjustRebillAmt the adjustRebillAmt to set
     */
    public void setAdjustRebillAmt(boolean adjustRebillAmt) {
        this.adjustRebillAmt = adjustRebillAmt;
        // end setAdjustRebillAmt
    }

    /**
     * @return the vpnType
     */
    public String getVpnType() {
        return this.vpnType;
        // end getVpnType
    }

    /**
     * @param vpnType the vpnType to set
     */
    public void setVpnType(String vpnType) {
        this.vpnType = vpnType;
        // end setVpnType
    }

    /**
     * @return the mobilePhoNbr
     */
    public String getMobilePhoNbr() {
        return this.mobilePhoNbr;
        // end getMobilePhoNbr
    }

    /**
     * @param mobilePhoNbr the mobilePhoNbr to set
     */
    public void setMobilePhoNbr(String mobilePhoNbr) {
        this.mobilePhoNbr = mobilePhoNbr;
        // end setMobilePhoNbr
    }

    /**
     * @return the emailAddress2
     */
    public String getEmailAddress2() {
        return this.emailAddress2;
        // end getEmailAddress2
    }

    /**
     * @param emailAddress2 the emailAddress2 to set
     */
    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
        // end setEmailAddress2
    }

    /**
     * @return the emailAddress3
     */
    public String getEmailAddress3() {
        return this.emailAddress3;
        // end getEmailAddress3
    }

    /**
     * @param emailAddress3 the emailAddress3 to set
     */
    public void setEmailAddress3(String emailAddress3) {
        this.emailAddress3 = emailAddress3;
        // end setEmailAddress3
    }

    /**
     * @return the noOfTags
     */
    public int getNoOfTags() {
        return this.noOfTags;
        // end getNoOfTags
    }

    /**
     * @param noOfTags the noOfTags to set
     */
    public void setNoOfTags(int noOfTags) {
        this.noOfTags = noOfTags;
        // end setNoOfTags
    }

    /**
     * @return the depositIncrementCC
     */
    public double getDepositIncrementCC() {
        return this.depositIncrementCC;
        // end getDepositIncrementCC
    }

    /**
     * @param depositIncrementCC the depositIncrementCC to set
     */
    public void setDepositIncrementCC(double depositIncrementCC) {
        this.depositIncrementCC = depositIncrementCC;
        // end setDepositIncrementCC
    }

    /**
     * @return the depositIncrementEFT
     */
    public double getDepositIncrementEFT() {
        return this.depositIncrementEFT;
        // end getDepositIncrementEFT
    }

    /**
     * @param depositIncrementEFT the depositIncrementEFT to set
     */
    public void setDepositIncrementEFT(double depositIncrementEFT) {
        this.depositIncrementEFT = depositIncrementEFT;
        // end setDepositIncrementEFT
    }

    /**
     * @return the minBalIncrementCC
     */
    public double getMinBalIncrementCC() {
        return this.minBalIncrementCC;
        // end getMinBalIncrementCC
    }

    /**
     * @param minBalIncrementCC the minBalIncrementCC to set
     */
    public void setMinBalIncrementCC(double minBalIncrementCC) {
        this.minBalIncrementCC = minBalIncrementCC;
        // end setMinBalIncrementCC
    }

    /**
     * @return the minBalIncrementEFT
     */
    public double getMinBalIncrementEFT() {
        return this.minBalIncrementEFT;
        // end getMinBalIncrementEFT
    }

    /**
     * @param minBalIncrementEFT the minBalIncrementEFT to set
     */
    public void setMinBalIncrementEFT(double minBalIncrementEFT) {
        this.minBalIncrementEFT = minBalIncrementEFT;
        // end setMinBalIncrementEFT
    }

    /**
     * @return the depositAmtCC
     */
    public double getDepositAmtCC() {
        return this.depositAmtCC;
        // end getDepositAmtCC
    }

    /**
     * @param depositAmtCC the depositAmtCC to set
     */
    public void setDepositAmtCC(double depositAmtCC) {
        this.depositAmtCC = depositAmtCC;
        // end setDepositAmtCC
    }

    /**
     * @return the depositAmtEFT
     */
    public double getDepositAmtEFT() {
        return this.depositAmtEFT;
        // end getDepositAmtEFT
    }

    /**
     * @param depositAmtEFT the depositAmtEFT to set
     */
    public void setDepositAmtEFT(double depositAmtEFT) {
        this.depositAmtEFT = depositAmtEFT;
        // end setDepositAmtEFT
    }

    /**
     * @return the minBalCC
     */
    public double getMinBalCC() {
        return this.minBalCC;
        // end getMinBalCC
    }

    /**
     * @param minBalCC the minBalCC to set
     */
    public void setMinBalCC(double minBalCC) {
        this.minBalCC = minBalCC;
        // end setMinBalCC
    }

    /**
     * @return the minBalEFT
     */
    public double getMinBalEFT() {
        return this.minBalEFT;
        // end getMinBalEFT
    }

    /**
     * @param minBalEFT the minBalEFT to set
     */
    public void setMinBalEFT(double minBalEFT) {
        this.minBalEFT = minBalEFT;
        // end setMinBalEFT
    }

    /**
     * @return the suspensionFlag
     */
    public boolean isSuspensionFlag() {
        return this.suspensionFlag;
        // end isSuspensionFlag
    }

    /**
     * @param suspensionFlag the suspensionFlag to set
     */
    public void setSuspensionFlag(boolean suspensionFlag) {
        this.suspensionFlag = suspensionFlag;
        // end setSuspensionFlag
    }

    /**
     * @return the violationFlag
     */
    public boolean isViolationFlag() {
        return this.violationFlag;
        // end isViolationFlag
    }

    /**
     * @param violationFlag the violationFlag to set
     */
    public void setViolationFlag(boolean violationFlag) {
        this.violationFlag = violationFlag;
        // end setViolationFlag
    }

    /**
     * @return the violationLicPlates
     */
    public List<String> getViolationLicPlates() {
        return this.violationLicPlates;
        // end getViolationLicPlates
    }

    /**
     * @param violationLicPlates the violationLicPlates to set
     */
    public void setViolationLicPlates(List<String> violationLicPlates) {
        this.violationLicPlates = violationLicPlates;
        // end setViolationLicPlates
    }

    /**
     * @return the byMail
     */
    public boolean isByMail() {
        return this.byMail;
        // end isByMail
    }

    /**
     * @param byMail the byMail to set
     */
    public void setByMail(boolean byMail) {
        this.byMail = byMail;
        // end setByMail
    }

    /**
     * @return the byPhone
     */
    public boolean isByPhone() {
        return this.byPhone;
        // end isByPhone
    }

    /**
     * @param byPhone the byPhone to set
     */
    public void setByPhone(boolean byPhone) {
        this.byPhone = byPhone;
        // end setByPhone
    }

    /**
     * @return the byEmail
     */
    public boolean isByEmail() {
        return this.byEmail;
        // end isByEmail
    }

    /**
     * @param byEmail the byEmail to set
     */
    public void setByEmail(boolean byEmail) {
        this.byEmail = byEmail;
        // end setByEmail
    }

    /**
     * @return the revenueAccount
     */
    public boolean isRevenueAccount() {
       // return this.revenueAccount;
    	return true;
        // end isRevenueAccount
    }

    /**
     * @param revenueAccount the revenueAccount to set
     */
    public void setRevenueAccount(boolean revenueAccount) {
        this.revenueAccount = revenueAccount;
        // end setRevenueAccount
    }

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
    //Pay Instalment change  
	public boolean isPaymentPlanExist() {
		return paymentPlanExist;
	}

	public void setPaymentPlanExist(boolean paymentPlanExist) {
		this.paymentPlanExist = paymentPlanExist;
	}

	public double getPaymentPlanDueAmount() {
		return paymentPlanDueAmount;
	}

	public void setPaymentPlanDueAmount(double paymentPlanDueAmount) {
		this.paymentPlanDueAmount = paymentPlanDueAmount;
	}		
	//Pay Instalment change 

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getHomePhoExt() {
		return homePhoExt;
	}

	public void setHomePhoExt(String homePhoExt) {
		this.homePhoExt = homePhoExt;
	}

	public String getMobilePhoExt() {
		return mobilePhoExt;
	}

	public void setMobilePhoExt(String mobilePhoExt) {
		this.mobilePhoExt = mobilePhoExt;
	}

	public String getSmsAlertsOptIn() {
		return smsAlertsOptIn;
	}

	public void setSmsAlertsOptIn(String smsAlertsOptIn) {
		this.smsAlertsOptIn = smsAlertsOptIn;
	}
	
}
