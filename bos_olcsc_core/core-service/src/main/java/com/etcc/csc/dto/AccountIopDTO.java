package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

/**
 * Account inter-operability class.
 */
public class AccountIopDTO extends BaseDTO {
    private long acctId;
    private String licPlate;
    private String licState;
    private String tagId;
    private String accountVehicleId;
    private String tag;
    private long agcyId;
    private String agencyId;
    private String iopStatus;
    private String reasonDescr;
    
    private boolean updateable=true;
  //preserve existing settings to find user changes
    private boolean existingAllowHctra=true;
    private boolean existingAllowDfw=true;
    private boolean existingAllowLove=true;
    private boolean existingAllowTxDot=true;
    //as per new design default all are allowed
    //iop_blocks records informs only blocked one
    private boolean allowHctra=true;
    private boolean allowDfw=true;
    private boolean allowLove=true;
    private boolean allowTxDot=true;
    private boolean updateHctra=true;
    private boolean updateDfw=true;
    private boolean updateLove=true;
    private boolean updateTxDot=true;

    public AccountIopDTO() {
    }


    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return acctId;
    }

    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
    }

    public String getLicPlate() {
        return licPlate;
    }

    public void setLicState(String licState) {
        this.licState = licState;
    }

    public String getLicState() {
        return licState;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
    }

    public String getAccountVehicleId() {
		return accountVehicleId;
	}


	public void setAccountVehicleId(String accountVehicleId) {
		this.accountVehicleId = accountVehicleId;
	}


	public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setAgcyId(long agcyId) {
        this.agcyId = agcyId;
    }

    public long getAgcyId() {
        return agcyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setIopStatus(String iopStatus) {
        this.iopStatus = iopStatus;
    }

    public String getIopStatus() {
        return iopStatus;
    }

    public void setReasonDescr(String reasonDescr) {
        this.reasonDescr = reasonDescr;
    }

    public String getReasonDescr() {
        return reasonDescr;
    }

    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    public boolean isUpdateable() {
        return updateable;
    }

    public void setAllowHctra(boolean allowHctra) {
        this.allowHctra = allowHctra;
    }

    public boolean isAllowHctra() {
        return allowHctra;
    }

    public void setAllowDfw(boolean allowDfw) {
        this.allowDfw = allowDfw;
    }

    public boolean isAllowDfw() {
        return allowDfw;
    }

    public void setAllowLove(boolean allowLove) {
        this.allowLove = allowLove;
    }

    public boolean isAllowLove() {
        return allowLove;
    }

    public void setAllowTxDot(boolean allowTxDot) {
        this.allowTxDot = allowTxDot;
    }

    public boolean isAllowTxDot() {
        return allowTxDot;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("acctId=");
        sb.append(acctId);
        sb.append(",licPlate=");
        sb.append(licPlate);
        sb.append(",licState=");
        sb.append(licState);
        sb.append(",tagId=");
        sb.append(tagId);
        sb.append(",tag=");
        sb.append(tag);
        sb.append(",agencyId=");
        sb.append(agencyId);
        sb.append(",iopStatus=");
        sb.append(iopStatus);
        sb.append(",reasonDescr=");
        sb.append(reasonDescr);
        sb.append(",updateable=");
        sb.append(updateable);
        sb.append(",allowHctra=");
        sb.append(allowHctra);
        sb.append(",allowDfw=");
        sb.append(allowDfw);
        sb.append(",allowLove=");
        sb.append(allowLove);
        sb.append(",allowTxDot=");
        sb.append(allowTxDot);
        sb.append("]");
        return sb.toString();
    }

    public void setUpdateHctra(boolean updateHctra) {
        this.updateHctra = updateHctra;
    }

    public boolean isUpdateHctra() {
        return updateHctra;
    }

    public void setUpdateDfw(boolean updateDfw) {
        this.updateDfw = updateDfw;
    }

    public boolean isUpdateDfw() {
        return updateDfw;
    }

    public void setUpdateLove(boolean updateLove) {
        this.updateLove = updateLove;
    }

    public boolean isUpdateLove() {
        return updateLove;
    }

    public void setUpdateTxDot(boolean updateTxDot) {
        this.updateTxDot = updateTxDot;
    }

    public boolean isUpdateTxDot() {
        return updateTxDot;
    }


	public boolean isExistingAllowHctra() {
		return existingAllowHctra;
	}


	public void setExistingAllowHctra(boolean existingAllowHctra) {
		this.existingAllowHctra = existingAllowHctra;
	}


	public boolean isExistingAllowDfw() {
		return existingAllowDfw;
	}


	public void setExistingAllowDfw(boolean existingAllowDfw) {
		this.existingAllowDfw = existingAllowDfw;
	}


	public boolean isExistingAllowLove() {
		return existingAllowLove;
	}


	public void setExistingAllowLove(boolean existingAllowLove) {
		this.existingAllowLove = existingAllowLove;
	}


	public boolean isExistingAllowTxDot() {
		return existingAllowTxDot;
	}


	public void setExistingAllowTxDot(boolean existingAllowTxDot) {
		this.existingAllowTxDot = existingAllowTxDot;
	}
}
