package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.io.Serializable;

/**
 * Represents the Account Login domain.
 */
public class AccountLoginDTO extends BaseDTO {
    private long acctId;
    private String loginId;
    private String password;
    private String lastLoginIp;
    private String dbSessionId;
    private String loginType;
    private String acctStatus;
    private String acctActivity;
    private long invoiceId;
    private long violatorId;
    private String licPlate;
    private String licState;
    private long minTag = -100;
    private long maxTag= -100;
    private long minVehicle;
    private long maxVehicle;
    private long planDetailId;
    private long personId;
    private String acctPlan;
    private String acctStartDate;
    private String acctEndDate;
    
    public AccountLoginDTO() {
    	
    }

    public AccountLoginDTO(long acctId, String loginId, String lastLoginIp) {
        this.acctId = acctId;
        this.loginId = loginId;
        this.lastLoginIp = lastLoginIp;
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return acctId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setDbSessionId(String dbSessionId) {
        this.dbSessionId = dbSessionId;
    }

    public String getDbSessionId() {
        return dbSessionId;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctActivity(String acctActivity) {
        this.acctActivity = acctActivity;
    }

    public String getAcctActivity() {
        return acctActivity;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    /**
     * When LoginType is IN, will contain the invoice id. When login type is CA, 
     * will contain the collection agency id.
     * @return
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    public void setViolatorId(long violatorId) {
        this.violatorId = violatorId;
    }

    public long getViolatorId() {
        return violatorId;
    }

    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
    }

    public String getLicPlate() {
        return licPlate;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("acctId=");
        sb.append(acctId);
        sb.append(", loginId=");
        sb.append(loginId);
        sb.append(", lastLoginIp=");
        sb.append(lastLoginIp);
        sb.append(", dbSessionId=");
        sb.append(dbSessionId);
        sb.append(", loginType=");
        sb.append(loginType);
        sb.append(", acctStatus=");
        sb.append(acctStatus);
        sb.append(", acctActivity=");
        sb.append(acctActivity);
        sb.append("invoiceId=");
        sb.append(invoiceId);
        sb.append("violatorId=");
        sb.append(violatorId);
        sb.append("licPlate=");
        sb.append(licPlate);
        sb.append("]");
        return sb.toString();
    }

    public void setLicState(String licState) {
        this.licState = licState;
    }

    public String getLicState() {
        return licState;
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

	public long getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(long planDetailId) {
		this.planDetailId = planDetailId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getAcctPlan() {
		return acctPlan;
	}

	public void setAcctPlan(String acctPlan) {
		this.acctPlan = acctPlan;
	}

	public String getAcctStartDate() {
		return acctStartDate;
	}

	public void setAcctStartDate(String acctStartDate) {
		this.acctStartDate = acctStartDate;
	}

	public String getAcctEndDate() {
		return acctEndDate;
	}

	public void setAcctEndDate(String acctEndDate) {
		this.acctEndDate = acctEndDate;
	}
	
	
    
}
