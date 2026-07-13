/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.dto.TagDTO;

public class AccountTagForm extends ActionForm {
	private static final long serialVersionUID = -5597646990498707161L;

	private List<AccountTagForm> tollTags;
    private boolean editMode = false;
    private long acctId;
    private String agencyId;
    private String tagId;
    private String acctTagStatus;
    private String licPlate;
    private String licState;
    private String licPlateTag;
    private String vehicleDescr;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleYear;
    private String vehicleColor;
    private String vehicleClassCode;
    private Calendar assignedDate;
    private Calendar expirDate;
    private int tagReadCt;
    private String acctTagComments;
    private long vpnId;
    private String unitId;
    private Calendar plateExpirDate;
    private boolean temporaryLicPlate;
    private String expirationDate;
    private Collection states;
    private Collection vehicleClasses;
    private String tagStatusDesc;
    private long acctTagSeq;
    private String vehicleClassDesc;
    private boolean successful;
    private Collection<TagDTO> duplicateTags;
    private boolean hasDuplicates;
    private boolean overrideDuplicates;
    private String barcodePrefix;
    private int index;
    private int maxRows;
    private int currentPage;
    private boolean showFormValue;
    private List<AccountTagForm> editedTollTags;
    

    @Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
        temporaryLicPlate = false;        
        tollTags = null;
        setOverrideDuplicates(false);
    }

    public void setDuplicateTags(TagDTO[] duplicateTags) {
        this.duplicateTags = duplicateTags == null ? null : Arrays.asList(duplicateTags);
    }

    public AccountTagForm getTollTag(int index) {
        if (this.tollTags == null) {
            this.tollTags = new ArrayList<AccountTagForm>();
        }
        int size = this.tollTags.size();
        for (int i=0; i<index+1-size; i++) {
            this.tollTags.add(new AccountTagForm());
        }
        return this.tollTags.get(index);
    }

    public void setTollTags(List<AccountTagForm> tollTags) {
        this.tollTags = tollTags;
    }

    public List<AccountTagForm> getTollTags() {
        return this.tollTags;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isEditMode() {
        return this.editMode;
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return this.acctId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyId() {
        return this.agencyId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return this.tagId;
    }

    public void setAcctTagStatus(String acctTagStatus) {
        this.acctTagStatus = acctTagStatus;
    }

    public String getAcctTagStatus() {
        return this.acctTagStatus;
    }

    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
    }

    public String getLicPlate() {
        return this.licPlate;
    }

    public void setLicState(String licState) {
        this.licState = licState;
    }

    public String getLicState() {
        return this.licState;
    }

    public void setLicPlateTag(String licPlateTag) {
        this.licPlateTag = licPlateTag;
    }

    public String getLicPlateTag() {
        return this.licPlateTag;
    }

    public void setVehicleDescr(String vehicleDescr) {
        this.vehicleDescr = vehicleDescr;
    }

    public String getVehicleDescr() {
        return this.vehicleDescr;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleMake() {
        return this.vehicleMake;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleModel() {
        return this.vehicleModel;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleYear() {
        return this.vehicleYear;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleColor() {
        return this.vehicleColor;
    }

    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
    }

    public String getVehicleClassCode() {
        return this.vehicleClassCode;
    }

    public void setAssignedDate(Calendar assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Calendar getAssignedDate() {
        return this.assignedDate;
    }

    public void setExpirDate(Calendar expirDate) {
        this.expirDate = expirDate;
    }

    public Calendar getExpirDate() {
        return this.expirDate;
    }

    public void setTagReadCt(int tagReadCt) {
        this.tagReadCt = tagReadCt;
    }

    public int getTagReadCt() {
        return this.tagReadCt;
    }

    public void setAcctTagComments(String acctTagComments) {
        this.acctTagComments = acctTagComments;
    }

    public String getAcctTagComments() {
        return this.acctTagComments;
    }

    public void setVpnId(long vpnId) {
        this.vpnId = vpnId;
    }

    public long getVpnId() {
        return this.vpnId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setPlateExpirDate(Calendar plateExpirDate) {
        this.plateExpirDate = plateExpirDate;
    }

    public Calendar getPlateExpirDate() {
        return this.plateExpirDate;
    }

    public void setTemporaryLicPlate(boolean temporaryLicPlate) {
        this.temporaryLicPlate = temporaryLicPlate;
    }

    public boolean isTemporaryLicPlate() {
        return this.temporaryLicPlate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() {
        if (this.expirationDate == null) {
            return "N/A";
        } else {
            return this.expirationDate;
        }
    }
    
    public int getTollTagSize() {
        if (this.tollTags == null) {
            return 0;
        } else {
            return this.tollTags.size();
        }
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("acctId=");
        sb.append(this.acctId);
        sb.append("agencyId=");
        sb.append(this.agencyId);
        sb.append("tagId=");
        sb.append(this.tagId);
        sb.append("acctTagStatus=");
        sb.append(this.acctTagStatus);
        sb.append("licPlate=");
        sb.append(this.licPlate);
        sb.append("licState=");
        sb.append(this.licState);
        sb.append("licPlateTag=");
        sb.append(this.licPlateTag);
        sb.append("vehicleDescr=");
        sb.append(this.vehicleDescr);
        sb.append("vehicleMake=");
        sb.append(this.vehicleMake);
        sb.append("vehicleModel=");
        sb.append(this.vehicleModel);
        sb.append("vehicleYear=");
        sb.append(this.vehicleYear);
        sb.append("vehicleColor=");
        sb.append(this.vehicleColor);
        sb.append("vehicleClassCode=");
        sb.append(this.vehicleClassCode);
        sb.append("assignedDate=");
        sb.append(this.assignedDate);
        sb.append("expirDate=");
        sb.append(this.expirDate);
        sb.append("tagReadCt=");
        sb.append(this.tagReadCt);
        sb.append("acctTagComments=");
        sb.append(this.acctTagComments);
        sb.append("vpnId=");
        sb.append(this.vpnId);
        sb.append("unitId=");
        sb.append(this.unitId);
        sb.append("plateExpirDate=");
        sb.append(this.plateExpirDate);
        sb.append("temporaryLicPlate=");
        sb.append(this.temporaryLicPlate);
        sb.append("]");
        return sb.toString();
    }

    public void setStates(Collection states) {
    	this.states = states;
    }

    public Collection getStates() {
    	return this.states;
    }

    public void setVehicleClasses(Collection vehicleClasses) {
    	this.vehicleClasses = vehicleClasses;
    }

    public Collection getVehicleClasses() {
    	return this.vehicleClasses;
    }

    public void setTagStatusDesc(String tagStatusDesc) {
        this.tagStatusDesc = tagStatusDesc;
    }

    public String getTagStatusDesc() {
        return this.tagStatusDesc;
    }

    public void setAcctTagSeq(long acctTagSeq) {
        this.acctTagSeq = acctTagSeq;
    }

    public long getAcctTagSeq() {
        return this.acctTagSeq;
    }

    public void setVehicleClassDesc(String vehicleClassDesc) {
        this.vehicleClassDesc = vehicleClassDesc;
    }

    public String getVehicleClassDesc() {
        return this.vehicleClassDesc;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    public void setDuplicateTags(Collection<TagDTO> duplicateTags) {
        this.duplicateTags = duplicateTags;
    }

    public Collection<TagDTO> getDuplicateTags() {
        return this.duplicateTags;
    }

    public void setHasDuplicates(boolean hasDuplicates) {
        this.hasDuplicates = hasDuplicates;
    }

    public boolean isHasDuplicates() {
        return this.hasDuplicates;
    }

    public void setOverrideDuplicates(boolean overrideDuplicates) {
        this.overrideDuplicates = overrideDuplicates;
    }

    public boolean isOverrideDuplicates() {
        return this.overrideDuplicates;
    }

    public void setBarcodePrefix(String barcodePrefix) {
        this.barcodePrefix = barcodePrefix;
    }

    public String getBarcodePrefix() {
        return this.barcodePrefix;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setShowFormValue(boolean showFormValue) {
        this.showFormValue = showFormValue;
    }

    public boolean isShowFormValue() {
        return showFormValue;
    }

    public void setEditedTollTags(List<AccountTagForm> editedTollTags) {
        this.editedTollTags = editedTollTags;
    }

    public List<AccountTagForm> getEditedTollTags() {
        return editedTollTags;
    }
}
