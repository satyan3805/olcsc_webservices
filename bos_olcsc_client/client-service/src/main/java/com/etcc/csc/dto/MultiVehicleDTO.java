/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.StringUtil;

/**
 * Data Transfer Object for Multiple vehicles.  From the original version on OLCSCService.
 *
 */
public class MultiVehicleDTO extends TagDTO {
    /**
     * Unique ID for serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = -5476045705059481077L;
    //Defect#9521
    public static final String ADD = "ADD";
    public static final String INACTIVATE = "INACTIVATE";

    
    //Defect#9521
    /*
    public enum UploadAction {
        ADD,
        INACTIVATE,
        ;
    }
    */
    private int rowNumber;
    private boolean ezTag;
    //private UploadAction uploadAction; //Defect#9521
    private String uploadAction;
    private String ezTagEnter;
    private String temporaryLicPlateEnter;
    private String reason;
    private String rawRecord;
    private String errorType;
    private String errorMsg;

    /**
     * @return the action
     */
    public String getAction() {
        return this.uploadAction == null ? null : this.uploadAction.toString();
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        if (StringUtil.isEmpty(action))
            this.uploadAction = null;
        //this.uploadAction = UploadAction.valueOf(action); //Defect#9521
        this.uploadAction = action;
    }

    @Override
    public String toString() {
        return toStringBuilder().toString();
    }
    /**
     * Generates the String representation of this object as a StringBuilder.
     * @return the representation of this object as a StringBuilder.
     */
    @Override
    public StringBuilder toStringBuilder(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("rowNumber=");
        sb.append(this.rowNumber);
        sb.append(", ezTag=");
        sb.append(this.ezTag);
        sb.append(", action=");
        sb.append(this.uploadAction);
        sb.append(", errorType=");
        sb.append(this.errorType);
        sb.append(", errorMsg=");
        sb.append(this.errorMsg);
        sb.append(", super=");
        sb.append(super.toStringBuilder());
        sb.append("]");
        return sb;
    }

    /**
     * @return the rowNumber
     */
    public int getRowNumber() {
        return this.rowNumber;
        // end getRowNumber
    }

    /**
     * @param rowNumber the rowNumber to set
     */
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
        // end setRowNumber
    }

    /**
     * @return the ezTagRequired
     */
    public boolean isEzTagRequired() {
        return this.ezTag;
        // end isEzTagRequired
    }

    /**
     * @param ezTagRequired the ezTagRequired to set
     */
    public void setEzTagRequired(boolean ezTagRequired) {
        this.ezTag = ezTagRequired;
        // end setEzTagRequired
    }

    /**
     * @return the uploadAction
     */
    /*
    public UploadAction getUploadAction() {
        return uploadAction;
    }
	*/
    /**
     * @param uploadAction the uploadAction to set
     */
    /*
    public void setUploadAction(UploadAction uploadAction) {
        this.uploadAction = uploadAction;
    }
	*/
    /**
     * @return the errorType
     */
    public String getErrorType() {
        return this.errorType;
        // end getErrorType
    }

    /**
     * @param errorType the errorType to set
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
        // end setErrorType
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return this.errorMsg;
        // end getErrorMsg
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        // end setErrorMsg
    }
    @IgnoreProperty
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	 @IgnoreProperty
	public String getRawRecord() {
		return rawRecord;
	}

	public void setRawRecord(String rawRecord) {
		this.rawRecord = rawRecord;
	}

	public String getEzTagEnter() {
		return ezTagEnter;
	}

	public void setEzTagEnter(String ezTagEnter) {
		this.ezTagEnter = ezTagEnter;
	}

	public String getTemporaryLicPlateEnter() {
		return temporaryLicPlateEnter;
	}

	public void setTemporaryLicPlateEnter(String temporaryLicPlateEnter) {
		this.temporaryLicPlateEnter = temporaryLicPlateEnter;
	}



}
