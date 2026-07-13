package com.etcc.csc.dto;

import java.math.BigDecimal;

/**
 * Account inter-operability class. This class is used by NTTA only. Will be deleted.
 * Account preferences Data Transfer Object. From the original OLCSCService module.
 */
public class AccountIopDTO extends BaseDTO {
    public static final String IOP_DFW = "DFW";
    public static final String IOP_HUB = "IOPHUB";
    public static final String IOP_LOVE = "DAL";
    public static final String IOP_TXDOT = "TTA";
    public static final String IOP_HCTRA = "HCTRA";

    public static final BigDecimal IOP_DFW_AGCY_ID = BigDecimal.valueOf(4);
    public static final BigDecimal IOP_HUB_AGCY_ID = BigDecimal.valueOf(10);
    public static final BigDecimal IOP_LOVE_AGCY_ID = BigDecimal.valueOf(6);
    public static final BigDecimal IOP_TXDOT_AGCY_ID = BigDecimal.valueOf(7);
    public static final BigDecimal IOP_HCTRA_AGCY_ID = BigDecimal.valueOf(3);
    
    private long acctId;
    private String licPlate;
    private String licState;
    private String tagId;
    private String tag;
    private long agcyId;
    private String agencyId;
    private String iopStatus;
    private String reasonDescr;
    private boolean updateable;
    private boolean allowHctra;
    private boolean allowDfw;
    private boolean allowLove;
    private boolean allowTxDot;
    private boolean updateHctra;
    private boolean updateDfw;
    private boolean updateLove;
    private boolean updateTxDot;

    public void setUpdateable(Boolean updateable) {
        this.updateable = updateable.booleanValue();
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
        StringBuilder sb = new StringBuilder();
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
        sb.append(",agcyId=");
        sb.append(agcyId);
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
}
