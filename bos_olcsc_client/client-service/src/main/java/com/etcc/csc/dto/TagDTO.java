/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Calendar;
import java.util.Date;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.CoreDateUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Data Transfer object for Tags.
 * PBP refers to Pay-by-Plate aka EZ Plate.
 * @author unknown
 * @author Milosh Boroyevich
 */
// From the original version in OLCSCService.
public class TagDTO extends BaseDTO {
    /**
     * Unique ID for serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = -38949726646972002L;

    private static final String TEMPORARY_LICENSE_PLATE = "TEMP";

    public static enum DeliveryMethod {
        MAIL,
        PICKUP,
        ;
    }

    // TODO: (MB) acctTagStatus vs tagStatusDesc -- enumeration?
    // TODO: (MB) expireDate vs plateExpireDate -- same???
    // TODO: (MB) remove all the Date<->String conversions
    private long acctId;
    private String agencyId;
    private String tagId;
    private String acctTagStatus;
    private long acctVehicleId;
    private String licPlate; //This value should always be uppercase.
    private String licState; //This value should always be uppercase.

    // private String licPlateTag;
    private String vehicleDescr;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleYear;
    private String vehicleColor;
    private String vehicleClassCode;
    private Calendar assignedDate;
    private Calendar expireDate;
    private int tagReadCt;
    private String acctTagComments;
    private long vpnId;
    private String unitId;
    private Calendar plateExpireDate;
    private boolean temporaryLicPlate;
    private String tagStatusDesc;
    private String vehicleClassDesc;
    private String barcodePrefix;

    // added for tag request
    private long acctTagSeq;
    private boolean checkDup;
    private boolean dup; // dup lic plate flag
    private long transactionId;
    private String plateExpiration;

    private String nickname;
    private boolean motorcycle;
    private double tagAmount;
    private boolean violationExist;

    private double tagSalesAmt;
    private double depositAmt;
    private double totalAmt;

    private String paymentType;

    private Date pbpStartDate;
    private Date pbpEndDate;

    //TODO: convert from String to Enum
    private String tagTypeCode;

    private long startIndex; // This is only used when retrieve account tag to handle to two tables (regular and pbp)
                             // on the same page

    private boolean acctSuspended;

    private boolean activePbpTagExist;
    private String tagStatusFlip; // indicate if tag status can be changed
    private String promptAcctClose; // indicates if modify tag detected there is no more active tag, it tells the UI
                                    // when to prompt the user to close account
    private String showContinueMsg;
    //Flex Pay changes
    private String noTagFlag;
    
    //TxDOT changes
    private String tagPrefix;
    private long accountTagId;


    public String getShowContinueMsg() {
		return showContinueMsg;
	}

	public void setShowContinueMsg(String showContinueMsg) {
		this.showContinueMsg = showContinueMsg;
	}

	/**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.licPlate == null) ? 0 : this.licPlate.hashCode());
        result = prime * result + ((this.licState == null) ? 0 : this.licState.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TagDTO other = (TagDTO) obj;
        if (this.licPlate == null) {
            if (other.licPlate != null)
                return false;
        } else if (!this.licPlate.equals(other.licPlate))
            return false;
        if (this.licState == null) {
            if (other.licState != null)
                return false;
        } else if (!this.licState.equals(other.licState))
            return false;
        return true;
    }

    /**
     * Gets the full id of the Tag.
     * @return the agencyID & Tag ID as a single string.
     */
    @IgnoreProperty
    public String getFullTagId() {
        if (this.agencyId == null)
            return this.tagId;
        // return this.agencyId + this.tagId;
		return this.barcodePrefix + this.tagId;
    }

    /**
     * Returns if this is a PBP Tag.
     * @return If a PBP Tag.
     */
    @IgnoreProperty
    public boolean isPbpTag() {
        // return pbpStartDate != null;
        return this.pbpStartDate != null && this.pbpStartDate.getTime() > 0;
    }

    @IgnoreProperty
    public String getPbpStart() {
        return CoreDateUtil.getMediumDateTime(this.pbpStartDate);
    }
    public void setPbpStart(String pbpStart) {
    	if (!StringUtil.isEmpty(pbpStart))
    		this.pbpStartDate = CoreDateUtil.stringDateTimeToDate(pbpStart);
    	else
    		this.pbpStartDate = null;
    }

    @IgnoreProperty
    public String getPbpEnd() {
        return CoreDateUtil.getMediumDateTime(this.pbpEndDate);
    }
    public void setPbpEnd(String pbpEnd) {
    	if (!StringUtil.isEmpty(pbpEnd))
    		this.pbpEndDate = CoreDateUtil.stringDateTimeToDate(pbpEnd);
    	else
    		this.pbpEndDate = null;
    }

    @IgnoreProperty
    public boolean isAllowEditPbpStart() {
        if (this.pbpStartDate == null)
            return true;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 1);
        Calendar calPbpStart = CoreDateUtil.dateToCalendar(this.pbpStartDate);
        return cal.before(calPbpStart);
    }

    @IgnoreProperty
    public boolean isAllowEditPbpEnd() {
        if (this.pbpEndDate == null)
            return true;
        Calendar cal = Calendar.getInstance();
        Calendar calPbpEnd = CoreDateUtil.dateToCalendar(this.pbpEndDate);
        return cal.before(calPbpEnd);
    }

    /**
     * Returns the license plate.
     * @return the license plate (or <tt>TEMPORARY_LICENSE_PLATE</tt> if temporary plate)
     * @see #isTemporaryLicPlate()
     */
    public String getLicPlate() {
        return this.licPlate;
    }

    /**
     * Sets the license plate.  Converts the plate to upper case.
     * Note: will not set the plate if specified plate is a
     * <tt>TEMPORARY_LICENSE_PLATE</tt> but set the flag.
     * @param licPlate the license plate to set
     * @see String#toUpperCase()
     * @see #setTemporaryLicPlate(boolean)
     */
    public void setLicPlate(String licPlate) {
        //The database is doing case sensitive index & compare when it should be case-insensitive.
        //Due to some vagaries of the DB Indexing scheme, this can not be readily corrected by the DB.
        //So, set everything here to uppercase, to match what TagStore is doing.
        if (StringUtil.isEmpty(licPlate))
            this.licPlate = null;
        else if (licPlate.equalsIgnoreCase(TEMPORARY_LICENSE_PLATE))
            this.temporaryLicPlate = true;
        else
            this.licPlate = licPlate.toUpperCase();
    }

    /**
     * Returns the license state.
     * @return the license state
     */
    public String getLicState() {
        return this.licState;
    }

    /**
     * Sets the license state.  Converts the plate state to upper case.
     * @param licState the license state to set
     */
    public void setLicState(String licState) {
        this.licState = (licState == null ? null : licState.toUpperCase());
    }

    /**
     * Returns the vehicle name.
     * @return the <i>nickname</i> or <i>year make model</i> if nickname is <tt>null</tt>
     * @see #getNickname()
     * @see #getVehicleYear()
     * @see #getVehicleMake()
     * @see #getVehicleModel()
     */
    @IgnoreProperty
    public String getName() {
        if (this.nickname == null)
            return (this.vehicleYear + ' ' + this.vehicleMake + ' ' + this.vehicleModel);
        return this.nickname;
    }

    /**
     * Returns the vehicle nickname.
     * @return the nickname
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Sets the vehicle nickname.  An empty string will be set as <tt>null</tt>.
     * @param nickname the nickname to set
     * @see StringUtil#safeToString(Object)
     */
    public void setNickname(String nickname) {
        this.nickname = StringUtil.safeToString(nickname);
    }


    @Override
    public String toString() {
        return toStringBuilder().toString();
    }
    /**
     * Generates the String representation of this object as a StringBuilder.
     * @return the representation of this object as a StringBuilder.
     */
    public StringBuilder toStringBuilder(){
        StringBuilder sb = new StringBuilder();
        sb.append("com.etcc.csc.dto.TagDTO[");
        sb.append("acctId=");
        sb.append(this.acctId);
        sb.append(", agencyId=");
        sb.append(this.agencyId);
        sb.append(", tagId=");
        sb.append(this.tagId);
        sb.append(", acctTagStatus=");
        sb.append(this.acctTagStatus);
        sb.append(", acctVehicleId=");
        sb.append(this.acctVehicleId);
        sb.append(", licPlate=");
        sb.append(this.licPlate);
        sb.append(", licState=");
        sb.append(this.licState);

        // sb.append(", licPlateTag="); sb.append(licPlateTag);
        sb.append(", vehicleDescr=");
        sb.append(this.vehicleDescr);
        sb.append(", vehicleMake=");
        sb.append(this.vehicleMake);
        sb.append(", vehicleModel=");
        sb.append(this.vehicleModel);
        sb.append(", vehicleYear=");
        sb.append(this.vehicleYear);
        sb.append(", vehicleColor=");
        sb.append(this.vehicleColor);
        sb.append(", vehicleClassCode=");
        sb.append(this.vehicleClassCode);
        sb.append(", assignedDate=");
        sb.append(CoreDateUtil.getMediumDateTime(this.assignedDate));
        sb.append(", expirDate=");
        sb.append(CoreDateUtil.getMediumDateTime(this.expireDate));
        sb.append(", tagReadCt=");
        sb.append(this.tagReadCt);
        sb.append(", acctTagComments=");
        sb.append(this.acctTagComments);
        sb.append(", vpnId=");
        sb.append(this.vpnId);
        sb.append(", unitId=");
        sb.append(this.unitId);
        sb.append(", plateExpirDate=");
        sb.append(CoreDateUtil.getMediumDateTime(this.plateExpireDate));
        sb.append(", temporaryLicPlate=");
        sb.append(this.temporaryLicPlate);
        sb.append(", tagStatusDesc=");
        sb.append(this.tagStatusDesc);
        sb.append(", vehicleClassDesc=");
        sb.append(this.vehicleClassDesc);
        sb.append(", barcodePrefix=");
        sb.append(this.barcodePrefix);

        // added for tag request
        sb.append(", acctTagSeq=");
        sb.append(this.acctTagSeq);
        sb.append(", checkDup=");
        sb.append(this.checkDup);
        sb.append(", dup=");
        sb.append(this.dup); // dup lic plate flag
        sb.append(", transactionId=");
        sb.append(this.transactionId);
        sb.append(", plateExpiration=");
        sb.append(this.plateExpiration);

        sb.append(", nickname=");
        sb.append(this.nickname);
        sb.append(", motorcycle=");
        sb.append(this.motorcycle);
        sb.append(", tagAmount=");
        sb.append(this.tagAmount);
        sb.append(", violationExist=");
        sb.append(this.violationExist);

        sb.append(", tagSalesAmt=");
        sb.append(this.tagSalesAmt);
        sb.append(", depositAmt=");
        sb.append(this.depositAmt);
        sb.append(", totalAmt=");
        sb.append(this.totalAmt);

        sb.append(", paymentType=");
        sb.append(this.paymentType);

        // sb.append(", pbpStartDate="); sb.append(pbpStartDate);
        // sb.append(", pbpEndDate="); sb.append(pbpEndDate);

        sb.append(", pbpStart=");
        sb.append(this.pbpStartDate);
        sb.append(", pbpEnd=");
        sb.append(this.pbpEndDate);

        sb.append(", tagTypeCode=");
        sb.append(this.tagTypeCode);

        sb.append(", startIndex=");
        sb.append(this.startIndex); // This is only used when retrieve account tag to handle to two tables (regular and
                                    // pbp) on the same page

        sb.append(", acctSuspended=");
        sb.append(this.acctSuspended);

        sb.append(", activePbpTagExist=");
        sb.append(this.activePbpTagExist);
        sb.append("]");
        return sb;
    }

    /**
     * @return the acctId
     */
    public long getAcctId() {
        return this.acctId;
        // end getAcctId
    }

    /**
     * @param acctId the acctId to set
     */
    public void setAcctId(long acctId) {
        this.acctId = acctId;
        // end setAcctId
    }

    /**
     * @return the agencyId
     */
    public String getAgencyId() {
        return this.agencyId;
        // end getAgencyId
    }

    /**
     * @param agencyId the agencyId to set
     */
    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
        // end setAgencyId
    }

    /**
     * @return the tagId
     */
    public String getTagId() {
        return this.tagId;
        // end getTagId
    }

    /**
     * @param tagId the tagId to set
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
        // end setTagId
    }

    /**
     * @return the acctTagStatus
     */
    public String getAcctTagStatus() {
        return this.acctTagStatus;
        // end getAcctTagStatus
    }

    /**
     * @param acctTagStatus the acctTagStatus to set
     */
    public void setAcctTagStatus(String acctTagStatus) {
        this.acctTagStatus = acctTagStatus;
        // end setAcctTagStatus
    }

    /**
     * @return the vehicleDescr
     */
    public String getVehicleDescr() {
        return this.vehicleDescr;
        // end getVehicleDescr
    }

    /**
     * @param vehicleDescr the vehicleDescr to set
     */
    public void setVehicleDescr(String vehicleDescr) {
        this.vehicleDescr = vehicleDescr;
        // end setVehicleDescr
    }

    /**
     * @return the vehicleMake
     */
    public String getVehicleMake() {
        return this.vehicleMake;
        // end getVehicleMake
    }

    /**
     * @param vehicleMake the vehicleMake to set
     */
    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
        // end setVehicleMake
    }

    /**
     * @return the vehicleModel
     */
    public String getVehicleModel() {
        return this.vehicleModel;
        // end getVehicleModel
    }

    /**
     * @param vehicleModel the vehicleModel to set
     */
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
        // end setVehicleModel
    }

    /**
     * @return the vehicleYear
     */
    public String getVehicleYear() {
        return this.vehicleYear;
        // end getVehicleYear
    }

    /**
     * @param vehicleYear the vehicleYear to set
     */
    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
        // end setVehicleYear
    }

    /**
     * @return the vehicleColor
     */
    public String getVehicleColor() {
        return this.vehicleColor;
        // end getVehicleColor
    }

    /**
     * @param vehicleColor the vehicleColor to set
     */
    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
        // end setVehicleColor
    }

    /**
     * @return the vehicleClassCode
     */
    public String getVehicleClassCode() {
        return this.vehicleClassCode;
        // end getVehicleClassCode
    }

    /**
     * @param vehicleClassCode the vehicleClassCode to set
     */
    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
        // end setVehicleClassCode
    }

    /**
     * @return the assignedDate
     */
    public Calendar getAssignedDate() {
        return this.assignedDate;
        // end getAssignedDate
    }

    /**
     * @param assignedDate the assignedDate to set
     */
    public void setAssignedDate(Calendar assignedDate) {
        this.assignedDate = assignedDate;
        // end setAssignedDate
    }

    /**
     * @return the expirDate
     */
    public Calendar getExpireDate() {
        return this.expireDate;
        // end getExpirDate
    }

    /**
     * @param expirDate the expirDate to set
     */
    public void setExpireDate(Calendar expirDate) {
        this.expireDate = expirDate;
        // end setExpirDate
    }

    /**
     * @return the tagReadCt
     */
    public int getTagReadCt() {
        return this.tagReadCt;
        // end getTagReadCt
    }

    /**
     * @param tagReadCt the tagReadCt to set
     */
    public void setTagReadCt(int tagReadCt) {
        this.tagReadCt = tagReadCt;
        // end setTagReadCt
    }

    /**
     * @return the acctTagComments
     */
    public String getAcctTagComments() {
        return this.acctTagComments;
        // end getAcctTagComments
    }

    /**
     * @param acctTagComments the acctTagComments to set
     */
    public void setAcctTagComments(String acctTagComments) {
        this.acctTagComments = acctTagComments;
        // end setAcctTagComments
    }

    /**
     * @return the vpnId
     */
    public long getVpnId() {
        return this.vpnId;
        // end getVpnId
    }

    /**
     * @param vpnId the vpnId to set
     */
    public void setVpnId(long vpnId) {
        this.vpnId = vpnId;
        // end setVpnId
    }

    /**
     * @return the unitId
     */
    public String getUnitId() {
        return this.unitId;
        // end getUnitId
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId;
        // end setUnitId
    }

    /**
     * @return the plateExpirDate
     */
    public Calendar getPlateExpireDate() {
        return this.plateExpireDate;
        // end getPlateExpirDate
    }

    /**
     * @param plateExpirDate the plateExpirDate to set
     */
    public void setPlateExpireDate(Calendar plateExpirDate) {
        this.plateExpireDate = plateExpirDate;
        // end setPlateExpirDate
    }

    /**
     * @return the temporaryLicPlate
     */
    public boolean isTemporaryLicPlate() {
        return this.temporaryLicPlate;
        // end getTemporaryLicPlate
    }

    /**
     * @param temporaryLicPlate the temporaryLicPlate to set
     */
    public void setTemporaryLicPlate(boolean temporaryLicPlate) {
        this.temporaryLicPlate = temporaryLicPlate;
    }

    /**
     * @return the tagStatusDesc
     */
    public String getTagStatusDesc() {
        return this.tagStatusDesc;
        // end getTagStatusDesc
    }

    /**
     * @param tagStatusDesc the tagStatusDesc to set
     */
    public void setTagStatusDesc(String tagStatusDesc) {
        this.tagStatusDesc = tagStatusDesc;
        // end setTagStatusDesc
    }

    /**
     * @return the vehicleClassDesc
     */
    public String getVehicleClassDesc() {
        return this.vehicleClassDesc;
        // end getVehicleClassDesc
    }

    /**
     * @param vehicleClassDesc the vehicleClassDesc to set
     */
    public void setVehicleClassDesc(String vehicleClassDesc) {
        this.vehicleClassDesc = vehicleClassDesc;
        // end setVehicleClassDesc
    }

    /**
     * @return the barcodePrefix
     */
    public String getBarcodePrefix() {
        return this.barcodePrefix;
        // end getBarcodePrefix
    }

    /**
     * @param barcodePrefix the barcodePrefix to set
     */
    public void setBarcodePrefix(String barcodePrefix) {
        this.barcodePrefix = barcodePrefix;
        // end setBarcodePrefix
    }

    /**
     * @return the acctTagSeq
     */
    public long getAcctTagSeq() {
        return this.acctTagSeq;
        // end getAcctTagSeq
    }

    /**
     * @param acctTagSeq the acctTagSeq to set
     */
    public void setAcctTagSeq(long acctTagSeq) {
        this.acctTagSeq = acctTagSeq;
        // end setAcctTagSeq
    }

    /**
     * @return the checkDup
     */
    public boolean isCheckDup() {
        return this.checkDup;
        // end isCheckDup
    }

    /**
     * @param checkDup the checkDup to set
     */
    public void setCheckDup(boolean checkDup) {
        this.checkDup = checkDup;
        // end setCheckDup
    }

    /**
     * @return the dup
     */
    public boolean isDup() {
        return this.dup;
        // end isDup
    }

    /**
     * @param dup the dup to set
     */
    public void setDup(boolean dup) {
        this.dup = dup;
        // end setDup
    }

    /**
     * @return the transactionId
     */
    public long getTransactionId() {
        return this.transactionId;
        // end getTransactionId
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
        // end setTransactionId
    }

    /**
     * @return the plateExpiration
     */
    public String getPlateExpiration() {
        return this.plateExpiration;
        // end getPlateExpiration
    }

    /**
     * @param plateExpiration the plateExpiration to set
     */
    public void setPlateExpiration(String plateExpiration) {
        this.plateExpiration = plateExpiration;
        // end setPlateExpiration
    }

    /**
     * @return the motorcycle
     */
    public boolean isMotorcycle() {
        return this.motorcycle;
        // end isMotorcycle
    }

    /**
     * @param motorcycle the motorcycle to set
     */
    public void setMotorcycle(boolean motorcycle) {
        this.motorcycle = motorcycle;
        // end setMotorcycle
    }

    /**
     * @return the tagAmount
     */
    public double getTagAmount() {
        return this.tagAmount;
        // end getTagAmount
    }

    /**
     * @param tagAmount the tagAmount to set
     */
    public void setTagAmount(double tagAmount) {
        this.tagAmount = tagAmount;
        // end setTagAmount
    }

    /**
     * @return the violationExist
     */
    public boolean isViolationExist() {
        return this.violationExist;
        // end isViolationExist
    }

    /**
     * @param violationExist the violationExist to set
     */
    public void setViolationExist(boolean violationExist) {
        this.violationExist = violationExist;
        // end setViolationExist
    }

    /**
     * @return the tagSalesAmt
     */
    public double getTagSalesAmt() {
        return this.tagSalesAmt;
        // end getTagSalesAmt
    }

    /**
     * @param tagSalesAmt the tagSalesAmt to set
     */
    public void setTagSalesAmt(double tagSalesAmt) {
        this.tagSalesAmt = tagSalesAmt;
        // end setTagSalesAmt
    }

    /**
     * @return the depositAmt
     */
    public double getDepositAmt() {
        return this.depositAmt;
        // end getDepositAmt
    }

    /**
     * @param depositAmt the depositAmt to set
     */
    public void setDepositAmt(double depositAmt) {
        this.depositAmt = depositAmt;
        // end setDepositAmt
    }

    /**
     * @return the totalAmt
     */
    public double getTotalAmt() {
        return this.totalAmt;
        // end getTotalAmt
    }

    /**
     * @param totalAmt the totalAmt to set
     */
    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
        // end setTotalAmt
    }

    /**
     * @return the paymentType
     */
    public String getPaymentType() {
        return this.paymentType;
        // end getPaymentType
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        // end setPaymentType
    }

    /**
     * @return the pbpStart
     */
    public Date getPbpStartDate() {
        return this.pbpStartDate;
        // end getPbpStart
    }

    /**
     * @param pbpStart the pbpStart to set
     */
    public void setPbpStartDate(Date pbpStart) {
        this.pbpStartDate = pbpStart;
        // end setPbpStart
    }

    /**
     * @return the pbpEnd
     */
    public Date getPbpEndDate() {
        return this.pbpEndDate;
        // end getPbpEnd
    }

    /**
     * @param pbpEnd the pbpEnd to set
     */
    public void setPbpEndDate(Date pbpEnd) {
        this.pbpEndDate = pbpEnd;
        // end setPbpEnd
    }

    /**
     * @return the tagTypeCode
     */
    public String getTagTypeCode() {
        return this.tagTypeCode;
        // end getTagTypeCode
    }

    /**
     * @param tagTypeCode the tagTypeCode to set
     */
    public void setTagTypeCode(String tagTypeCode) {
        this.tagTypeCode = tagTypeCode;
        // end setTagTypeCode
    }

    /**
     * @return the startIndex
     */
    public long getStartIndex() {
        return this.startIndex;
        // end getStartIndex
    }

    /**
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
        // end setStartIndex
    }

    /**
     * @return the acctSuspended
     */
    public boolean isAcctSuspended() {
        return this.acctSuspended;
        // end isAcctSuspended
    }

    /**
     * @param acctSuspended the acctSuspended to set
     */
    public void setAcctSuspended(boolean acctSuspended) {
        this.acctSuspended = acctSuspended;
        // end setAcctSuspended
    }

    /**
     * @return the activePbpTagExist
     */
    public boolean isActivePbpTagExist() {
        return this.activePbpTagExist;
        // end isActivePbpTagExist
    }

    /**
     * @param activePbpTagExist the activePbpTagExist to set
     */
    public void setActivePbpTagExist(boolean activePbpTagExist) {
        this.activePbpTagExist = activePbpTagExist;
        // end setActivePbpTagExist
    }

    /**
     * @return the tagStatusFlip
     */
    public String getTagStatusFlip() {
        return this.tagStatusFlip;
        // end getTagStatusFlip
    }

    /**
     * @param tagStatusFlip the tagStatusFlip to set
     */
    public void setTagStatusFlip(String tagStatusFlip) {
        this.tagStatusFlip = tagStatusFlip;
        // end setTagStatusFlip
    }

    /**
     * @return the promptAcctClose
     */
    public String getPromptAcctClose() {
        return this.promptAcctClose;
        // end getPromptAcctClose
    }

    /**
     * @param promptAcctClose the promptAcctClose to set
     */
    public void setPromptAcctClose(String promptAcctClose) {
        this.promptAcctClose = promptAcctClose;
        // end setPromptAcctClose
    }

    /*
     * get Account Vehicle Id
     */
	public long getAcctVehicleId() {
		return acctVehicleId;
	}

	/*
     * set Account Vehicle Id
     */
	public void setAcctVehicleId(long acctVehicleId) {
		this.acctVehicleId = acctVehicleId;
	}

	/**
	 * @return the noTagFlag
	 */
	public String getNoTagFlag() {
		return noTagFlag;
	}

	/**
	 * @param noTagFlag the noTagFlag to set
	 */
	public void setNoTagFlag(String noTagFlag) {
		this.noTagFlag = noTagFlag;
	}

	/**
	 * @return the tagPrefix
	 */
	public String getTagPrefix() {
		return tagPrefix;
	}

	/**
	 * @param tagPrefix the tagPrefix to set
	 */
	public void setTagPrefix(String tagPrefix) {
		this.tagPrefix = tagPrefix;
	}

	/**
	 * @return the accountTagId
	 */
	public long getAccountTagId() {
		return accountTagId;
	}

	/**
	 * @param accountTagId the accountTagId to set
	 */
	public void setAccountTagId(long accountTagId) {
		this.accountTagId = accountTagId;
	}

	
}
