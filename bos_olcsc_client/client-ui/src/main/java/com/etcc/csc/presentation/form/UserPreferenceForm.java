/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

import com.etcc.csc.dto.AccountDeviceDTO;
import com.etcc.csc.dto.UserPreferenceValueDTO;

public class UserPreferenceForm extends ValidatorActionForm {
	private static final long serialVersionUID = 7557895961906466783L;

	private boolean useParkingFacility;
    private boolean emailParkingReceipt;
    private String parkingReceiptEmail;
    private boolean emailOtherReceipt;
    private String otherReceiptEmail;
    private boolean emailStatement;
    private String statementEmail;
    private boolean emailNewProgram;
    private String newProgramEmail;
    private boolean usePostalMail;
    private String emailFormat;

    private long prefId;
    private String displayDesc;
    private String prefValue;
    private String prefType;
    private int displayOrder;
    private List<UserPreferenceForm> userPreferences;
    private List<AccountDeviceDTO> deviceValues;
    private List<UserPreferenceValueDTO> userPrefValues;
    private boolean selected;
    private String selectedValue;
    private long selectedDeviceId;
    private List<AccountIopForm> acctIops;
    private String message;

    public UserPreferenceForm getUserPreference(int index) {
        if (userPreferences == null) {
            userPreferences = new ArrayList<UserPreferenceForm>();
        }
        int size = userPreferences.size();
        for (int i=0; i<index+1-size; i++) {
            userPreferences.add(new UserPreferenceForm());
        }
        return userPreferences.get(index);
    }

    public void setUserPreferences(List<UserPreferenceForm> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public List<UserPreferenceForm> getUserPreferences() {
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

    public AccountIopForm getAccountIop(int index) {
        if (acctIops == null) {
            acctIops = new ArrayList<AccountIopForm>();
        }
        int size = acctIops.size();
        for (int i=0; i<index+1-size; i++) {
            acctIops.add(new AccountIopForm());
        }
        return acctIops.get(index);
    }

    public void setAccountIops(List<AccountIopForm> acctIops) {
        this.acctIops = acctIops;
    }

    public List<AccountIopForm> getAccountIops() {
        return acctIops;
    }

    public void setUseParkingFacility(boolean useParkingFacility) {
        this.useParkingFacility = useParkingFacility;
    }

    public boolean isUseParkingFacility() {
        return useParkingFacility;
    }

    public void setEmailParkingReceipt(boolean emailParkingReceipt) {
        this.emailParkingReceipt = emailParkingReceipt;
    }

    public boolean isEmailParkingReceipt() {
        return emailParkingReceipt;
    }

    public void setParkingReceiptEmail(String parkingReceiptEmail) {
        this.parkingReceiptEmail = parkingReceiptEmail;
    }

    public String getParkingReceiptEmail() {
        return parkingReceiptEmail;
    }

    public void setEmailOtherReceipt(boolean emailOtherReceipt) {
        this.emailOtherReceipt = emailOtherReceipt;
    }

    public boolean isEmailOtherReceipt() {
        return emailOtherReceipt;
    }

    public void setOtherReceiptEmail(String otherReceiptEmail) {
        this.otherReceiptEmail = otherReceiptEmail;
    }

    public String getOtherReceiptEmail() {
        return otherReceiptEmail;
    }

    public void setEmailStatement(boolean emailStatement) {
        this.emailStatement = emailStatement;
    }

    public boolean isEmailStatement() {
        return emailStatement;
    }

    public void setStatementEmail(String statementEmail) {
        this.statementEmail = statementEmail;
    }

    public String getStatementEmail() {
        return statementEmail;
    }

    public void setEmailNewProgram(boolean emailNewProgram) {
        this.emailNewProgram = emailNewProgram;
    }

    public boolean isEmailNewProgram() {
        return emailNewProgram;
    }

    public void setNewProgramEmail(String newProgramEmail) {
        this.newProgramEmail = newProgramEmail;
    }

    public String getNewProgramEmail() {
        return newProgramEmail;
    }

    public void setUsePostalMail(boolean usePostalMail) {
        this.usePostalMail = usePostalMail;
    }

    public boolean isUsePostalMail() {
        return usePostalMail;
    }

    public void setEmailFormat(String emailFormat) {
        this.emailFormat = emailFormat;
    }

    public String getEmailFormat() {
        return emailFormat;
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

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        selected = false;        
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedDeviceId(long selectedDeviceValue) {
        this.selectedDeviceId = selectedDeviceValue;
    }

    public long getSelectedDeviceId() {
        return selectedDeviceId;
    }
    
    public int getIopSize() {
        if (acctIops != null) {
            return acctIops.size();
        } else {
            return 0;
        }
    }
    public int getUserPreferenceSize() {
        if (userPreferences != null) {
            return userPreferences.size();
        } else {
            return 0;
        }
    }

    public void setAcctIops(List<AccountIopForm> acctIops) {
        this.acctIops = acctIops;
    }

    public List<AccountIopForm> getAcctIops() {
        return acctIops;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
