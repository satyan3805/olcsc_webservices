package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Calendar;

/**
 * Represents a Toll Violation.
 */
public class ViolationDTO extends BaseDTO {
    private long violationId;
    private long laneId;
    private String date;
    private String time;
    private String microTime;
    private String type;
    private String tollDue;
    private String tollPaid;
    private int licPlateNbr;
    private int licPlateState;
    private String vehicleClass;
    private String status;
    private Calendar statusDate;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleColor;
    private String vehicleYear;
    private String vehicleBody;
    private String occupantDescr;
    private boolean noPayAttempt;
    private boolean windowUp;
    private String violationComment;
    private String recordedBy;
    private String recorderEmpId;
    private String driverLicNbr;
    private String driverLicState;
    private String tolltagAcctId;
    private String tagId;
    private String agencyId;
    private String excusedReason;
    private String excusedBy;
    private Calendar dateExcused;
    private String excusedComment;
    private long violatorId;
    private String reviewStatus;
    private long laneViolationId;
    private Calendar notificationDate;
    private long oldViolatorId;
    private long transactionId;
    private String disposition;
    private Calendar commentDate;
    private Calendar unpaidTollDate;
    private long hostTransactionId;
    private long vioViolationId;
    private String laneDescr;

    public ViolationDTO() {
    }


    public void setViolationId(long violationId) {
        this.violationId = violationId;
    }

    public long getViolationId() {
        return violationId;
    }

    public void setLaneId(long laneId) {
        this.laneId = laneId;
    }

    public long getLaneId() {
        return laneId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setMicroTime(String microTime) {
        this.microTime = microTime;
    }

    public String getMicroTime() {
        return microTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTollDue(String tollDue) {
        this.tollDue = tollDue;
    }

    public String getTollDue() {
        return tollDue;
    }

    public void setTollPaid(String tollPaid) {
        this.tollPaid = tollPaid;
    }

    public String getTollPaid() {
        return tollPaid;
    }

    public void setLicPlateNbr(int licPlateNbr) {
        this.licPlateNbr = licPlateNbr;
    }

    public int getLicPlateNbr() {
        return licPlateNbr;
    }

    public void setLicPlateState(int licPlateState) {
        this.licPlateState = licPlateState;
    }

    public int getLicPlateState() {
        return licPlateState;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusDate(Calendar statusDate) {
        this.statusDate = statusDate;
    }

    public Calendar getStatusDate() {
        return statusDate;
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

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleBody(String vehicleBody) {
        this.vehicleBody = vehicleBody;
    }

    public String getVehicleBody() {
        return vehicleBody;
    }

    public void setOccupantDescr(String occupantDescr) {
        this.occupantDescr = occupantDescr;
    }

    public String getOccupantDescr() {
        return occupantDescr;
    }

    public void setNoPayAttempt(boolean noPayAttempt) {
        this.noPayAttempt = noPayAttempt;
    }

    public boolean isNoPayAttempt() {
        return noPayAttempt;
    }

    public void setWindowUp(boolean windowUp) {
        this.windowUp = windowUp;
    }

    public boolean isWindowUp() {
        return windowUp;
    }

    public void setViolationComment(String violationComment) {
        this.violationComment = violationComment;
    }

    public String getViolationComment() {
        return violationComment;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecorderEmpId(String recorderEmpId) {
        this.recorderEmpId = recorderEmpId;
    }

    public String getRecorderEmpId() {
        return recorderEmpId;
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

    public void setTolltagAcctId(String tolltagAcctId) {
        this.tolltagAcctId = tolltagAcctId;
    }

    public String getTolltagAcctId() {
        return tolltagAcctId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setExcusedReason(String excusedReason) {
        this.excusedReason = excusedReason;
    }

    public String getExcusedReason() {
        return excusedReason;
    }

    public void setExcusedBy(String excusedBy) {
        this.excusedBy = excusedBy;
    }

    public String getExcusedBy() {
        return excusedBy;
    }

    public void setDateExcused(Calendar dateExcused) {
        this.dateExcused = dateExcused;
    }

    public Calendar getDateExcused() {
        return dateExcused;
    }

    public void setExcusedComment(String excusedComment) {
        this.excusedComment = excusedComment;
    }

    public String getExcusedComment() {
        return excusedComment;
    }

    public void setViolatorId(long violatorId) {
        this.violatorId = violatorId;
    }

    public long getViolatorId() {
        return violatorId;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setLaneViolationId(long laneViolationId) {
        this.laneViolationId = laneViolationId;
    }

    public long getLaneViolationId() {
        return laneViolationId;
    }

    public void setNotificationDate(Calendar notificationDate) {
        this.notificationDate = notificationDate;
    }

    public Calendar getNotificationDate() {
        return notificationDate;
    }

    public void setOldViolatorId(long oldViolatorId) {
        this.oldViolatorId = oldViolatorId;
    }

    public long getOldViolatorId() {
        return oldViolatorId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setCommentDate(Calendar commentDate) {
        this.commentDate = commentDate;
    }

    public Calendar getCommentDate() {
        return commentDate;
    }

    public void setUnpaidTollDate(Calendar unpaidTollDate) {
        this.unpaidTollDate = unpaidTollDate;
    }

    public Calendar getUnpaidTollDate() {
        return unpaidTollDate;
    }

    public void setHostTransactionId(long hostTransactionId) {
        this.hostTransactionId = hostTransactionId;
    }

    public long getHostTransactionId() {
        return hostTransactionId;
    }

    public void setVioViolationId(long vioViolationId) {
        this.vioViolationId = vioViolationId;
    }

    public long getVioViolationId() {
        return vioViolationId;
    }
    public void setLaneDescr(String laneDescr) {
        this.laneDescr = laneDescr;
    }

    public String getLaneDescr() {
        return laneDescr;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("violationId=");
        sb.append(violationId);
        sb.append("laneId=");
        sb.append(laneId);
        sb.append(", date=");
        sb.append(date);
        sb.append(", time=");
        sb.append(time);
        sb.append(", microTime=");
        sb.append(microTime);
        sb.append(", type=");
        sb.append(type);
        sb.append(", tollDue=");
        sb.append(tollDue);
        sb.append(", tollPaid=");
        sb.append(tollPaid);
        sb.append(", licPlateNbr=");
        sb.append(licPlateNbr);
        sb.append(", licPlateState=");
        sb.append(licPlateState);
        sb.append(", vehicleClass=");
        sb.append(vehicleClass);
        sb.append(", status=");
        sb.append(status);
        sb.append(", statusDate=");
        sb.append(statusDate);
        sb.append(", vehicleMake=");
        sb.append(vehicleMake);
        sb.append(", vehicleModel=");
        sb.append(vehicleModel);
        sb.append(", vehicleColor=");
        sb.append(vehicleColor);
        sb.append(", vehicleYear=");
        sb.append(vehicleYear);
        sb.append(", vehicleBody=");
        sb.append(vehicleBody);
        sb.append(", occupantDescr=");
        sb.append(occupantDescr);
        sb.append(", noPayAttempt=");
        sb.append(noPayAttempt);
        sb.append(", windowUp=");
        sb.append(windowUp);
        sb.append(", violationComment=");
        sb.append(violationComment);
        sb.append(", recordedBy=");
        sb.append(recordedBy);
        sb.append(", recorderEmpId=");
        sb.append(recorderEmpId);
        sb.append(", driverLicNbr=");
        sb.append(driverLicNbr);
        sb.append(", driverLicState=");
        sb.append(driverLicState);
        sb.append(", tolltagAcctId=");
        sb.append(tolltagAcctId);
        sb.append(", tagId=");
        sb.append(tagId);
        sb.append(", agencyId=");
        sb.append(agencyId);
        sb.append(", excusedReason=");
        sb.append(excusedReason);
        sb.append(", excusedBy=");
        sb.append(excusedBy);
        sb.append(", dateExcused=");
        sb.append(dateExcused);
        sb.append(", excusedComment=");
        sb.append(excusedComment);
        sb.append(", violatorId=");
        sb.append(violatorId);
        sb.append(", reviewStatus=");
        sb.append(reviewStatus);
        sb.append(", laneViolationId=");
        sb.append(laneViolationId);
        sb.append(", notificationDate=");
        sb.append(notificationDate);
        sb.append(", oldViolatorId=");
        sb.append(oldViolatorId);
        sb.append(", transactionId=");
        sb.append(transactionId);
        sb.append(", disposition=");
        sb.append(disposition);
        sb.append(", commentDate=");
        sb.append(commentDate);
        sb.append(", unpaidTollDate=");
        sb.append(unpaidTollDate);
        sb.append(", hostTransactionId=");
        sb.append(hostTransactionId);
        sb.append(", vioViolationId=");
        sb.append(vioViolationId);
        sb.append("]");
        return sb.toString();
    }

}
