package com.etcc.csc.webservice.rest.dto;

import com.etcc.csc.dto.AccountLoginDTO;

public class SetPersonalInfoRequest {
	
	private AccountLoginDTO acctLoginDto;
	private String acctType;
	private String companyName;
	private String firstName;
	private String lastName;
	private String primaryPhone;
	private String alternatePhone;
	private String taxId;
	private String driverLicState;
	private String driverLicNumber;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String city;
	private String state;
	private String zip;
	private String country;
	private boolean byMail;
	private boolean byEmail;
	private boolean byPhone;
	private Long retailTransId;
	private String altPhoneExt;
	private String plus4;
	private String primaryPhoneExt;
	private String smsAlertsOptIn;
	public AccountLoginDTO getAcctLoginDto() {
		return acctLoginDto;
	}
	public void setAcctLoginDto(AccountLoginDTO acctLoginDto) {
		this.acctLoginDto = acctLoginDto;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPrimaryPhone() {
		return primaryPhone;
	}
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	public String getAlternatePhone() {
		return alternatePhone;
	}
	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getDriverLicState() {
		return driverLicState;
	}
	public void setDriverLicState(String driverLicState) {
		this.driverLicState = driverLicState;
	}
	public String getDriverLicNumber() {
		return driverLicNumber;
	}
	public void setDriverLicNumber(String driverLicNumber) {
		this.driverLicNumber = driverLicNumber;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean isByMail() {
		return byMail;
	}
	public void setByMail(boolean byMail) {
		this.byMail = byMail;
	}
	public boolean isByEmail() {
		return byEmail;
	}
	public void setByEmail(boolean byEmail) {
		this.byEmail = byEmail;
	}
	public boolean isByPhone() {
		return byPhone;
	}
	public void setByPhone(boolean byPhone) {
		this.byPhone = byPhone;
	}
	public Long getRetailTransId() {
		return retailTransId;
	}
	public void setRetailTransId(Long retailTransId) {
		this.retailTransId = retailTransId;
	}
	public String getAltPhoneExt() {
		return altPhoneExt;
	}
	public void setAltPhoneExt(String altPhoneExt) {
		this.altPhoneExt = altPhoneExt;
	}
	public String getPlus4() {
		return plus4;
	}
	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}
	public String getPrimaryPhoneExt() {
		return primaryPhoneExt;
	}
	public void setPrimaryPhoneExt(String primaryPhoneExt) {
		this.primaryPhoneExt = primaryPhoneExt;
	}
	public String getSmsAlertsOptIn() {
		return smsAlertsOptIn;
	}
	public void setSmsAlertsOptIn(String smsAlertsOptIn) {
		this.smsAlertsOptIn = smsAlertsOptIn;
	}
	@Override
	public String toString() {
		return "SetPersonalInfoRequest [acctLoginDto=" + acctLoginDto + ", acctType=" + acctType + ", companyName="
				+ companyName + ", firstName=" + firstName + ", lastName=" + lastName + ", primaryPhone=" + primaryPhone
				+ ", alternatePhone=" + alternatePhone + ", taxId=" + taxId + ", driverLicState=" + driverLicState
				+ ", driverLicNumber=" + driverLicNumber + ", address1=" + address1 + ", address2=" + address2
				+ ", address3=" + address3 + ", address4=" + address4 + ", city=" + city + ", state=" + state + ", zip="
				+ zip + ", country=" + country + ", byMail=" + byMail + ", byEmail=" + byEmail + ", byPhone=" + byPhone
				+ ", retailTransId=" + retailTransId + ", altPhoneExt=" + altPhoneExt + ", plus4=" + plus4
				+ ", primaryPhoneExt=" + primaryPhoneExt + ", smsAlertsOptIn=" + smsAlertsOptIn + "]";
	}
	
	

}
