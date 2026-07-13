package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

public class AccountDTO extends BaseDTO {
    private long acctId;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String plus4;
    private String homePhoNbr;
    private String workPhoNbr;
    private String workPhoExt;
    private String driverLicNbr;
    private String driverLicState;
    private String dLCountry;
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
    private String pmtTypeCode;
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
    private String vpnType;
    private String mobilePhoNbr;
    private String emailAddress2;
    private String emailAddress3;
    private int numOfAcctCards;
    private int numOfTollTags;
    private String excessiveChargeBacks;
    
    public AccountDTO() {
    }

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
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setPlus4(String plus4) {
        this.plus4 = plus4;
    }

    public String getPlus4() {
        return plus4;
    }

    public void setHomePhoNbr(String homePhoNbr) {
        this.homePhoNbr = homePhoNbr;
    }

    public String getHomePhoNbr() {
        return homePhoNbr;
    }

    public void setWorkPhoNbr(String workPhoNbr) {
        this.workPhoNbr = workPhoNbr;
    }

    public String getWorkPhoNbr() {
        return workPhoNbr;
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

	public String getDLCountry() {
		return dLCountry;
	}

	public void setDLCountry(String dLCountry) {
		this.dLCountry = dLCountry;
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

    public void setRebillAmt(double rebillAmt) {
        this.rebillAmt = rebillAmt;
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

    public void setPmtTypeCode(String pmtTypeCode) {
        this.pmtTypeCode = pmtTypeCode;
    }

    public String getPmtTypeCode() {
        return pmtTypeCode;
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

    public void setVpnType(String vpnType) {
        this.vpnType = vpnType;
    }

    public String getVpnType() {
        return vpnType;
    }
    public void setMobilePhoNbr(String mobilePhoNbr) {
        this.mobilePhoNbr = mobilePhoNbr;
    }

    public String getMobilePhoNbr() {
        return mobilePhoNbr;
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
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("acctId=");
        sb.append(acctId);
        sb.append("firstName=");
        sb.append(firstName);
        sb.append("middleInitial=");
        sb.append(middleInitial);
        sb.append("lastName=");
        sb.append(lastName);
        sb.append("address1=");
        sb.append(address1);
        sb.append("address2=");
        sb.append(address2);
        sb.append("city=");
        sb.append(city);
        sb.append("state=");
        sb.append(state);
        sb.append("zipCode=");
        sb.append(zipCode);
        sb.append("plus4=");
        sb.append(plus4);
        sb.append("homePhoNbr=");
        sb.append(homePhoNbr);
        sb.append("workPhoNbr=");
        sb.append(workPhoNbr);
        sb.append("workPhoExt=");
        sb.append(workPhoExt);
        sb.append("driverLicNbr=");
        sb.append(driverLicNbr);
        sb.append("driverLicState=");
        sb.append(driverLicState);
        sb.append("companyName=");
        sb.append(companyName);
        sb.append("companyTaxId=");
        sb.append(companyTaxId);
        sb.append("emailAddress=");
        sb.append(emailAddress);
        sb.append("moStmtFlag=");
        sb.append(moStmtFlag);
        sb.append("badAddressFlag=");
        sb.append(badAddressFlag);
        sb.append("rebillFailedFlag=");
        sb.append(rebillFailedFlag);
        sb.append("rebillAmt=");
        sb.append(rebillAmt);
        sb.append("rebillDate=");
        sb.append(rebillDate);
        sb.append("depAmt=");
        sb.append(depAmt);
        sb.append("balanceAmt=");
        sb.append(balanceAmt);
        sb.append("lowBalLevel=");
        sb.append(lowBalLevel);
        sb.append("balLastUpdated=");
        sb.append(balLastUpdated);
        sb.append("acctStatusCode=");
        sb.append(acctStatusCode);
        sb.append("acctTypeCode=");
        sb.append(acctTypeCode);
        sb.append("addressModified=");
        sb.append(addressModified);
        sb.append("pmtTypeCode=");
        sb.append(pmtTypeCode);
        sb.append("dateApproved=");
        sb.append(dateApproved);
        sb.append("approvedBy=");
        sb.append(approvedBy);
        sb.append("selectedForRebill=");
        sb.append(selectedForRebill);
        sb.append("msId=");
        sb.append(msId);
        sb.append("veaFlag=");
        sb.append(veaFlag);
        sb.append("veaDate=");
        sb.append(veaDate);
        sb.append("veaExpireDate=");
        sb.append(veaExpireDate);
        sb.append("securityQuestion=");
        sb.append(securityQuestion);
        sb.append("securityQuestionAnswer=");
        sb.append(securityQuestionAnswer);
        sb.append("companyCode=");
        sb.append(companyCode);
        sb.append("adjustRebillAmt=");
        sb.append(adjustRebillAmt);
        sb.append("vpnType=");
        sb.append(vpnType);
        sb.append("mobilePhoNbr=");
        sb.append(mobilePhoNbr);
        sb.append("emailAddress2=");
        sb.append(emailAddress2);
        sb.append("emailAddress3=");
        sb.append(emailAddress3);
        sb.append("]");
        return sb.toString();
    }

    public void setAcctTypeDescr(String acctTypeDescr) {
        this.acctTypeDescr = acctTypeDescr;
    }

    public String getAcctTypeDescr() {
        return acctTypeDescr;
    }

    public void setNumOfAcctCards(int numOfAcctCards) {
        this.numOfAcctCards = numOfAcctCards;
    }

    public int getNumOfAcctCards() {
        return numOfAcctCards;
    }

    public void setNumOfTollTags(int numOfTollTags) {
        this.numOfTollTags = numOfTollTags;
    }

    public int getNumOfTollTags() {
        return numOfTollTags;
    }

	public String getExcessiveChargeBacks() {
		return excessiveChargeBacks;
	}

	public void setExcessiveChargeBacks(String excessiveChargeBacks) {
		this.excessiveChargeBacks = excessiveChargeBacks;
	}
	
	public String getAddress(){
		return this.getAddress1();
	}
	
	public String getAddressLine2(){
		return this.getAddress2();
	}
	
	public String getZipcode(){
		return this.getZipCode();
	}
	public String getHomePhoneAreaCode(){
		String number = this.getHomePhoNbr();
		String value = "";
		if(StringUtils.isNotEmpty(number)){
			if(number.length() >= 3){
				value = number.substring(0, 3);
			}
		}
		System.out.println("returning...............1..." + value);
		return value;
	}
	public String getHomePhoneFirst3(){
		String number = this.getHomePhoNbr();
		String value = "";
		if(StringUtils.isNotEmpty(number)){
			if(number.length() >= 6){
				value = number.substring(3, 6);
			}
		}
		System.out.println("returning...............2..." + value);
		return value;
	}
	public String getHomePhoneLast4(){
		String number = this.getHomePhoNbr();
		String value = "";
		if(StringUtils.isNotEmpty(number)){
			if(number.length() >= 6){
				value = number.substring(6);
			}
		}
		System.out.println("returning..............3...." + value);
		return value;
	}

	public String getWorkPhoneAreaCode() {
		String number = this.getWorkPhoNbr();
		String value = "";
		if (StringUtils.isNotEmpty(number)) {
			if (number.length() >= 3) {
				value = number.substring(0, 3);
			}
		}
		System.out.println("returning...............4..." + value);
		return value;
	}

	public String getWorkPhoneFirst3() {
		String number = this.getWorkPhoNbr();
		String value = "";
		if (StringUtils.isNotEmpty(number)) {
			if (number.length() >= 6) {
				value = number.substring(3, 6);
			}
		}
		System.out.println("returning...............5..." + value);
		return value;
	}

	public String getWorkPhoneLast4() {
		String number = this.getWorkPhoNbr();

		String value = "";
		if (StringUtils.isNotEmpty(number)) {
			if (number.length() >= 6) {
				value = number.substring(6);
			}
		}
		System.out.println("returning...............6..." + value);
		return value;
	}
	
	public String getWorkPhoneExt(){
		return getWorkPhoExt();
	}
	
	public String getDriverLicenseNumber(){
		String value  = getDriverLicNbr();
		if(value.length() > 0){
			return value.substring(4) +value.substring(4); 
			
		}
		return "";//TODO return correct value
	}
	
	public String getDriverLicenseState(){
		return getDriverLicState();
	}
}
