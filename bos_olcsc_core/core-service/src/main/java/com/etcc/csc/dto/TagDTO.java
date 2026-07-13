package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TagDTO extends BaseDTO {
    private long acctId;
    private String agencyId;
    private String tagId;
    private String acctTagStatus;
    private String licPlate;
    //private String formattedLicPlate;
	private String licState;
    private String licPlateTag;
    private String vehicleDescr;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleYear;
    private String vehicleColor;
    private String vehicleClassCode;
    private Calendar assignedDate;
    private Calendar effectiveDate;
    private Calendar expirDate;
    private int tagReadCt;
    private String acctTagComments;
    private long vpnId;
    private String unitId;
    private Calendar plateExpirDate;
    private boolean temporaryLicPlate;
    private String tagStatusDesc;
    private String vehicleClassDesc;
    private String barcodePrefix;
    private long acctTagSeq;
    private boolean checkDup;
    private boolean dup;
    private long transactionId;
    private String plateExpiration;
    private Date assignDate;
    private Date expireDate;
    private boolean rentalVehicle;
    private String isPostable;
    private boolean isTagless=false;
    private long tagTypeId;
    private long accountVehicleId;
    private long tagStatusId;
    private String vinNumber;
    private String plateTypeCode;
    private String plateTypeDesc;
    private String responsibilityCode;
	private Long fulfillmentDetailId;
	private boolean isSelected;
	private String expirationDate;
	private String rentalStart;
	private String rentalEnd;
	private int rowIndex = 0;
	private String tagExists;
	private boolean dmvAccepted = false;
	private long dmvVehicleId;
    private String tagTypeCode;
    private String effectiveDateStr;
	private String tagTransferFlag;
	private String sourceAccountVehicleId;	//original Account Vehicle Id
    private String sourceAccountTagId;		//original Account Tag Id

    public TagDTO() {
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

    public void setAcctTagStatus(String acctTagStatus) {
        this.acctTagStatus = acctTagStatus;
    }

    public String getAcctTagStatus() {
        return acctTagStatus;
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

    public void setLicPlateTag(String licPlateTag) {
        this.licPlateTag = licPlateTag;
    }

    public String getLicPlateTag() {
        return licPlateTag;
    }

    public void setVehicleDescr(String vehicleDescr) {
        this.vehicleDescr = vehicleDescr;
    }

    public String getVehicleDescr() {
        return vehicleDescr;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
    }

    public String getVehicleClassCode() {
        return vehicleClassCode;
    }

    public void setAssignedDate(Calendar assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Calendar getAssignedDate() {
        return assignedDate;
    }

    public String getEffectiveDateStr() {
    	if(effectiveDateStr == null && effectiveDate != null){
    		this.setEffectiveDateStr(new SimpleDateFormat("MM/dd/yyyy").format(effectiveDate.getTime()));
    	}
		return effectiveDateStr;
	}


	public void setEffectiveDateStr(String effectiveDateStr) {
		this.effectiveDateStr = effectiveDateStr;
	}


	public void setExpirDate(Calendar expirDate) {
        this.expirDate = expirDate;
    }

    public Calendar getExpirDate() {
        return expirDate;
    }

    public void setTagReadCt(int tagReadCt) {
        this.tagReadCt = tagReadCt;
    }

    public int getTagReadCt() {
        return tagReadCt;
    }

    public void setAcctTagComments(String acctTagComments) {
        this.acctTagComments = acctTagComments;
    }

    public String getAcctTagComments() {
        return acctTagComments;
    }

    public void setVpnId(long vpnId) {
        this.vpnId = vpnId;
    }

    public long getVpnId() {
        return vpnId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setPlateExpirDate(Calendar plateExpirDate) {
        this.plateExpirDate = plateExpirDate;
    }

    public Calendar getPlateExpirDate() {
        return plateExpirDate;
    }

    public void setTemporaryLicPlate(boolean temporaryLicPlate) {
        this.temporaryLicPlate = temporaryLicPlate;
    }

    public boolean isTemporaryLicPlate() {
        return temporaryLicPlate;
    }
    
    public void setAcctTagSeq(long acctTagSeq) {
        this.acctTagSeq = acctTagSeq;
    }

    public long getAcctTagSeq() {
        return acctTagSeq;
    }

    public void setCheckDup(boolean checkDup) {
        this.checkDup = checkDup;
    }

    public boolean isCheckDup() {
        return checkDup;
    }

    public void setDup(boolean dup) {
        this.dup = dup;
    }

    public boolean isDup() {
        return dup;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getTransactionId() {
        return transactionId;
    }
    public void setTagStatusDesc(String tagStatusDesc) {
        this.tagStatusDesc = tagStatusDesc;
    }

    public String getTagStatusDesc() {
        return tagStatusDesc;
    }
    public void setVehicleClassDesc(String vehicleClassDesc) {
        this.vehicleClassDesc = vehicleClassDesc;
    }

    public String getVehicleClassDesc() {
        return vehicleClassDesc;
    }
    
    public void setPlateExpiration(String plateExpiration) {
        this.plateExpiration = plateExpiration;
    }

    public String getPlateExpiration() {
        return plateExpiration;
    }

    public void setBarcodePrefix(String barcodePrefix) {
        this.barcodePrefix = barcodePrefix;
    }

    public String getBarcodePrefix() {
        return barcodePrefix;
    }


    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }


	public boolean isRentalVehicle() {
		return rentalVehicle;
	}


	public void setRentalVehicle(boolean rentalVehicle) {
		this.rentalVehicle = rentalVehicle;
	}


	public Calendar getEffectiveDate() {
		if (effectiveDate == null && effectiveDateStr != null) {
			Calendar plateStartDate = Calendar.getInstance();
			try {
				plateStartDate.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(effectiveDateStr));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setEffectiveDate(plateStartDate);
		}
		return effectiveDate;
	}


	public void setEffectiveDate(Calendar effectiveDate) {
		this.effectiveDate = effectiveDate;
	}


	public String getIsPostable() {
		return isPostable;
	}


	public void setIsPostable(String isPostable) {
		this.isPostable = isPostable;
	}


	public boolean isTagless() {
		return isTagless;
	}


	public void setTagless(boolean isTagless) {
		this.isTagless = isTagless;
	}


	public long getTagTypeId() {
		return tagTypeId;
	}


	public void setTagTypeId(long tagTypeId) {
		this.tagTypeId = tagTypeId;
	}


	public long getAccountVehicleId() {
		return accountVehicleId;
	}


	public void setAccountVehicleId(long accountVehicleId) {
		this.accountVehicleId = accountVehicleId;
	}


	public long getTagStatusId() {
		return tagStatusId;
	}


	public void setTagStatusId(long tagStatusId) {
		this.tagStatusId = tagStatusId;
	}


	public String getVinNumber() {
		return vinNumber;
	}


	public void setVinNumber(String vinNumber) {
		this.vinNumber = vinNumber;
	}


	public String getPlateTypeCode() {
		return plateTypeCode;
	}


	public void setPlateTypeCode(String plateTypeCode) {
		this.plateTypeCode = plateTypeCode;
	}


	public String getResponsibilityCode() {
		return responsibilityCode;
	}


	public void setResponsibilityCode(String responsibilityCode) {
		this.responsibilityCode = responsibilityCode;
	}
	public Long getFulfillmentDetailId() {
		return fulfillmentDetailId;
	}
	public void setFulfillmentDetailId(Long fulfillmentDetailId) {
		this.fulfillmentDetailId = fulfillmentDetailId;
	}
	public boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() {
        if (expirationDate == null) {
            return "N/A";
        } else {
            return expirationDate;
        }
    }
    public void setRentalStart(String rentalStart) {
		this.rentalStart = rentalStart;
	}

	public String getRentalEnd() {
		return rentalEnd;
	}
	public void setRentalEnd(String rentalEnd) {
		this.rentalEnd = rentalEnd;
	}


	public String getRentalStart() {
		return rentalStart;
	}


	public int getRowIndex() {
		return rowIndex;
	}


	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}


	public String getPlateTypeDesc() {
		return plateTypeDesc;
	}


	public void setPlateTypeDesc(String plateTypeDesc) {
		this.plateTypeDesc = plateTypeDesc;
	}


	public void setTagExists(String tagExists) {
		this.tagExists = tagExists;
	}


	public String getTagExists() {
		return tagExists;
	}


	public long getDmvVehicleId() {
		return dmvVehicleId;
	}


	public void setDmvVehicleId(long dmvVehicleId) {
		this.dmvVehicleId = dmvVehicleId;
	}


	public boolean isDmvAccepted() {
		return dmvAccepted;
	}


	public void setDmvAccepted(boolean dmvAccepted) {
		this.dmvAccepted = dmvAccepted;
	}


	public String getTagTypeCode() {
		return tagTypeCode;
	}

	public void setTagTypeCode(String tagTypeCode) {
		this.tagTypeCode = tagTypeCode;
	}


	public String getTagTransferFlag() {
		return tagTransferFlag;
	}


	public void setTagTransferFlag(String tagTransferFlag) {
		this.tagTransferFlag = tagTransferFlag;
	}


	public String getSourceAccountVehicleId() {
		return sourceAccountVehicleId;
	}


	public void setSourceAccountVehicleId(String sourceAccountVehicleId) {
		this.sourceAccountVehicleId = sourceAccountVehicleId;
	}


	public String getSourceAccountTagId() {
		return sourceAccountTagId;
	}


	public void setSourceAccountTagId(String sourceAccountTagId) {
		this.sourceAccountTagId = sourceAccountTagId;
	}

/*    public String getFormattedLicPlate() {
		String formattedLicPlate = "";
		if(licState != null){
			formattedLicPlate = licState + " ";
		}
		if(licPlate != null){
			formattedLicPlate += licPlate;
		}
		return formattedLicPlate;
	  }
*/
}
