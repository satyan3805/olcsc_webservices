/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import com.etcc.csc.dto.AccountDeviceDTO;
import com.etcc.csc.dto.Address;
import com.etcc.csc.dto.AddressUS;
import com.etcc.csc.dto.BaseContactInfo;
import com.etcc.csc.dto.UserPreferenceValueDTO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class OnlineAccessForm extends ValidatorActionForm implements Address {
	private static final long serialVersionUID = -6518876439905897765L;

	private String acctId;
	/** @see TagAuthorityDTO */
    private String agencyId = "HCTR";
    private String tagId;
    private String tagIdentifier;
    private String companyTaxId;
    private String loginId;
    private String emailAddress;
    private String emailAddress2;
    private String securityQuestion;
    private String securityQuestionID;
    private String securityQuestionAnswer;
    private String securityQuestionAnswer2;

    private long prefId;
    private String displayDesc;
    private String prefValue;
    private String prefType;
    private int displayOrder;
    private boolean selected;
    private String selectedValue;
    private long selectedDeviceId;
    private long steId;
    private String selectedDeviceValue;

    private List<OnlineAccessForm> userPreferences;
    private List<AccountDeviceDTO> deviceValues;
    private List<UserPreferenceValueDTO> userPrefValues;
    
    //added for hctra
    private String userName;
    private String password;
    private String confirmPassword;
    private String alternateEmail;
    private String confirmAlternateEmail;
    private String newPassword;
    private String confirmNewPassword;
    private boolean fromConfirmation;
    private String alternatePhone;
    private String alternatePhoneExt;
    private String accountType;
    /** Specifies whether the account closure flow has been initiated. */
    private boolean accountClosure;

    private String acctTypeCode;
    private String acctTypeDescr;
    private String companyName;
    private String driverLicDisplay;
    
    private String countryCode;
	private BaseContactInfo contact = new BaseContactInfo();

    @Override
	public void reset(ActionMapping actionMapping, 
                      HttpServletRequest request) {
//        super.reset(actionMapping, request);
//        set("deleteVehicle", Boolean.valueOf(false));
//        set("tempLicensePlate", Boolean.valueOf(false));
        selected = false;
        fromConfirmation = false;
        contact.setNonUSAddress(false);
        // set("vehicleIndexToModify", null);
    }

    /**
     * Clean up the properties on this form.
     * @return a reference to this form
     */
    public OnlineAccessForm clean() {
        if (isNonUSAddress()) {
            setCity(null);
            setState(null);
            setZip(null);
            setPlus4(null);
        } else {
            setAddress3(null);
            setAddress4(null);
            setCountry(AddressUS.COUNTRY_CODE_USA);
        }
        return this;
    }

    public OnlineAccessForm getUserPreference(int index) {
        if (userPreferences == null) {
            userPreferences = new ArrayList<OnlineAccessForm>();
        }
        int size = userPreferences.size();
        for (int i=0; i<index+1-size; i++) {
         userPreferences.add(new OnlineAccessForm());
        }
        return userPreferences.get(index);
    }

    public void setUserPreferences(List<OnlineAccessForm> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public List<OnlineAccessForm> getUserPreferences() {
        return userPreferences;
    }
         
    public AccountDeviceDTO getDeviceValue(int index) {
        if (deviceValues == null) {
             deviceValues = new ArrayList<AccountDeviceDTO>();
        }
        int size = deviceValues.size();
        for (int i=0; i<index+1-size; i++) {
            deviceValues.add(new AccountDeviceDTO());
        }
        return deviceValues.get(index);
    }

    public void setDeviceValues(List<AccountDeviceDTO> deviceValues) {
        this.deviceValues = deviceValues;
    }

    public List<AccountDeviceDTO> getDeviceValues() {
        return deviceValues;
    }

    public UserPreferenceValueDTO getUserPrefValue(int index) {
        if (userPrefValues == null) {
            userPrefValues = new ArrayList<UserPreferenceValueDTO>();
        }
        int size = userPrefValues.size();
        for (int i=0; i<index+1-size; i++) {
            userPrefValues.add(new UserPreferenceValueDTO());
        }
        return userPrefValues.get(index);
    }

    public void setUserPrefValues(List<UserPreferenceValueDTO> userPrefValues) {
        this.userPrefValues = userPrefValues;
    }

    public List<UserPreferenceValueDTO> getUserPrefValues() {
        return userPrefValues;
    }

    public int getUserPreferenceSize() {
        if (userPreferences != null) {
            return userPreferences.size();
        } else {
            return 0;
        }
    }

    public String getName() {
        return this.contact.getFirstName() + ' ' + this.contact.getLastName();
    }

    public void setPrefId(long prefId) {
        this.prefId = prefId;
    }

    public long getPrefId() {
        return prefId;
    }

    public void setDisplayDesc(String displayDesc) {
        this.displayDesc = displayDesc;
    }

    public String getDisplayDesc() {
        return displayDesc;
    }

    public void setPrefValue(String prefValue) {
        this.prefValue = prefValue;
    }

    public String getPrefValue() {
        return prefValue;
    }

    public void setPrefType(String prefType) {
        this.prefType = prefType;
    }

    public String getPrefType() {
        return prefType;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedDeviceId(long selectedDeviceId) {
        this.selectedDeviceId = selectedDeviceId;
    }

    public long getSelectedDeviceId() {
        return selectedDeviceId;
    }

    public void setSteId(long steId) {
        this.steId = steId;
    }

    public long getSteId() {
        return steId;
    }

    public void setSelectedDeviceValue(String selectedDeviceValue) {
        this.selectedDeviceValue = selectedDeviceValue;
    }

    public String getSelectedDeviceValue() {
        return selectedDeviceValue;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setDriverLicNbr(String driverLicNbr) {
        this.contact.setDriverLic(driverLicNbr);
    }

    public String getDriverLicNbr() {
        return contact.getDriverLic();
    }

    public void setDriverLicState(String driverLicState) {
        this.contact.setDriverLicState(driverLicState);
    }

    public String getDriverLicState() {
        return contact.getDriverLicState();
    }

    public void setCompanyTaxId(String companyTaxId) {
        this.companyTaxId = companyTaxId;
    }

    public String getCompanyTaxId() {
        return companyTaxId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
    }

    public String getEmailAddress2() {
        return emailAddress2;
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

    public void setSecurityQuestionAnswer2(String securityQuestionAnswer2) {
        this.securityQuestionAnswer2 = securityQuestionAnswer2;
    }

    public String getSecurityQuestionAnswer2() {
        return securityQuestionAnswer2;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setConfirmAlternateEmail(String confirmAlternateEmail) {
        this.confirmAlternateEmail = confirmAlternateEmail;
    }

    public String getConfirmAlternateEmail() {
        return confirmAlternateEmail;
    }

    public void setSecurityQuestionID(String securityQuestionID) {
        this.securityQuestionID = securityQuestionID;
    }

    public String getSecurityQuestionID() {
        return securityQuestionID;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setFromConfirmation(boolean refresh) {
        this.fromConfirmation = refresh;
    }

    public boolean isFromConfirmation() {
        return fromConfirmation;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.contact.setPrimaryPhone(primaryPhone);
    }

    public String getPrimaryPhone() {
        return contact.getPrimaryPhone();
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAcctTypeCode(String acctTypeCode) {
        this.acctTypeCode = acctTypeCode;
    }

    public String getAcctTypeCode() {
        return acctTypeCode;
    }

    public void setAcctTypeDescr(String acctTypeDescr) {
        this.acctTypeDescr = acctTypeDescr;
    }

    public String getAcctTypeDescr() {
        return acctTypeDescr;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setFirstName(String firstName) {
        this.contact.setFirstName(firstName);
    }

    public String getFirstName() {
        return contact.getFirstName();
    }

    public void setLastName(String lastName) {
        this.contact.setLastName(lastName);
    }

    public String getLastName() {
        return contact.getLastName();
    }

    public void setDriverLicDisplay(String driverLicDisplay) {
        this.driverLicDisplay = driverLicDisplay;
    }

    public String getDriverLicDisplay() {
        return driverLicDisplay;
    }

    public void setAddress1(String address1) {
        this.contact.setAddress1(address1);
    }

    public String getAddress1() {
        return contact.getAddress1();
    }

    public void setAddress2(String address2) {
        this.contact.setAddress2(address2);
    }

    public String getAddress2() {
        return contact.getAddress2();
    }

    public void setAddress3(String address3) {
        this.contact.setAddress3(address3);
    }

    public String getAddress3() {
        return contact.getAddress3();
    }

    public void setAddress4(String address4) {
        this.contact.setAddress4(address4);
    }

    public String getAddress4() {
        return contact.getAddress4();
    }

    public void setCity(String city) {
        this.contact.setCity(city);
    }

    public String getCity() {
        return contact.getCity();
    }

    public void setState(String state) {
        this.contact.setState(state);
    }

    public String getState() {
        return contact.getState();
    }

    public void setCountry(String country) {
        this.contact.setCountry(country);
    }

    public String getCountry() {
        return contact.getCountry();
    }

    public void setZip(String zip) {
        this.contact.setZip(zip);
    }

    public String getZip() {
        return contact.getZip();
    }

    public void setNonUSAddress(boolean nonUSAddress) {
        this.contact.setNonUSAddress(nonUSAddress);
    }

    public boolean isNonUSAddress() {
        return contact.isNonUSAddress();
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setAlternatePhoneExt(String alternatePhoneExt) {
        this.alternatePhoneExt = alternatePhoneExt;
    }

    public String getAlternatePhoneExt() {
        return alternatePhoneExt;
    }

    public void setPlus4(String plus4) {
        this.contact.setPlus4(plus4);
    }

    public String getPlus4() {
        return contact.getPlus4();
    }

    public void setTagIdentifier(String tagIdentifier) {
        this.tagIdentifier = tagIdentifier;
    }

    public String getTagIdentifier() {
        return tagIdentifier;
    }

    public boolean isAccountClosure() {
        return accountClosure;
    }

    public void setAccountClosure(boolean accountClosure) {
        this.accountClosure = accountClosure;
    }
}
