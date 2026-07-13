/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import com.etcc.csc.dto.AccountDeviceDTO;
import com.etcc.csc.dto.UserPreferenceValueDTO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class TollTagUserPreferenceForm extends ValidatorActionForm {
	private static final long serialVersionUID = 3185975817892004856L;

	private String firstName;
    private String middleInitial;
    private String lastName;
    private String companyName;
    private String email;
    private String emailVerify;
    private String userId;
    private String securityQuestion;
    private String securityAnswer;
    private String securityAnswerVerify;

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
    private boolean useDefault = true;

    private List<TollTagUserPreferenceForm> userPreferences;
    private List<AccountDeviceDTO> deviceValues;
    private List<UserPreferenceValueDTO> userPrefValues;

    @Override
	public void reset(ActionMapping actionMapping, 
                      HttpServletRequest request) {
        selected = false;
        useDefault = true;
    }

    public TollTagUserPreferenceForm getUserPreference(int index) {
        if (userPreferences == null) {
            userPreferences = new ArrayList<TollTagUserPreferenceForm>();
        }
        int size = userPreferences.size();
        for (int i=0; i<index+1-size; i++) {
         userPreferences.add(new TollTagUserPreferenceForm());
        }
        return userPreferences.get(index);
    }

    public void setUserPreferences(List<TollTagUserPreferenceForm> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public List<TollTagUserPreferenceForm> getUserPreferences() {
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmailVerify(String emailVerify) {
        this.emailVerify = emailVerify;
    }

    public String getEmailVerify() {
        return emailVerify;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswerVerify(String securityAnswerVerify) {
        this.securityAnswerVerify = securityAnswerVerify;
    }

    public String getSecurityAnswerVerify() {
        return securityAnswerVerify;
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

    public void setUseDefault(boolean useDefault) {
        this.useDefault = useDefault;
    }

    public boolean isUseDefault() {
        return useDefault;
    }
}
