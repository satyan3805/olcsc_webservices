package com.etcc.csc.dto;

import java.util.List;

import com.etcc.csc.common.BaseDTO;


/**
 * DTO for handling the setup of online access to the system.
 */
public class OnlineAccessSetupDTO extends BaseDTO{

    private long acctId;
    private String agencyId;
    private String tagId;
    private String driverLicNbr;
    private String driverLicState;
    private String companyTaxId;
    private String loginId;
    private String password;
    private String emailAddress;
    private String securityQuestion;
    private String securityQuestionAnswer;
    private String dbSessionId;
    private String acctCard;
    private List<SecurityQuestionDTO> listOfQuestions;
    private List<SecurityQuestionDTO> listOfQuestionsnAnswers;
    private long minTag;
    private long maxTag;
    private long minVehicle;
    private long maxVehicle;
    

    public OnlineAccessSetupDTO() {
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
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

    public void setDbSessionId(String dbSessionId) {
        this.dbSessionId = dbSessionId;
    }

    public String getDbSessionId() {
        return dbSessionId;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("acctId=");
        sb.append(acctId);
        sb.append(", agencyId=");
        sb.append(agencyId);
        sb.append(", tagId=");
        sb.append(tagId);
        sb.append(", driverLicNbr=");
        sb.append(driverLicNbr);
        sb.append(", driverLicState=");
        sb.append(driverLicState);
        sb.append(", companyTaxId=");
        sb.append(companyTaxId);
        sb.append(", loginId=");
        sb.append(loginId);
        sb.append(", emailAddress=");
        sb.append(emailAddress);
        sb.append(", securityQuestion=");
        sb.append(securityQuestion);
        sb.append(", securityQuestionAnswer=");
        sb.append(securityQuestionAnswer);
        sb.append("]");
        return sb.toString();
    }

    public void setAcctCard(String acctCard) {
        this.acctCard = acctCard;
    }

    public String getAcctCard() {
        return acctCard;
    }

	public List<SecurityQuestionDTO> getListOfQuestions() {
		return listOfQuestions;
	}

	public void setListOfQuestions(
			List<SecurityQuestionDTO> listOfQuestionsnAnswers) {
		this.listOfQuestions = listOfQuestionsnAnswers;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getMinTag() {
		return minTag;
	}

	public void setMinTag(long minTag) {
		this.minTag = minTag;
	}

	public long getMaxTag() {
		return maxTag;
	}

	public void setMaxTag(long maxTag) {
		this.maxTag = maxTag;
	}

	public long getMinVehicle() {
		return minVehicle;
	}

	public void setMinVehicle(long minVehicle) {
		this.minVehicle = minVehicle;
	}

	public long getMaxVehicle() {
		return maxVehicle;
	}

	public void setMaxVehicle(long maxVehicle) {
		this.maxVehicle = maxVehicle;
	}

	public List<SecurityQuestionDTO> getListOfQuestionsnAnswers() {
		return listOfQuestionsnAnswers;
	}

	public void setListOfQuestionsnAnswers(
			List<SecurityQuestionDTO> listOfQuestionsnAnswers) {
		this.listOfQuestionsnAnswers = listOfQuestionsnAnswers;
	}
    
}
