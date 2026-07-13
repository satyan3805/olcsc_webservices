package com.etcc.csc.dto;

import java.util.Calendar;

/**
 * Represents Order data.
 */
public class OrderDTO extends BaseDTO {
    /**
     * Unique ID for serialization with version.
     */
    // Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = 5672713525187302568L;

    private Calendar orderDate;
    private long acctId;
    private long tagId;
    private String licPlate;
    private String type;
    private int qty;
    private String status;
    private String fullTagId;
    private String location;
    private Calendar statusDate;
    private long transactionId;

    /**
     * Constructor.
     */
    public OrderDTO() {
        // end <init>
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("orderDate=");
        sb.append(this.orderDate.getTime());
        sb.append(", acctId=");
        sb.append(this.acctId);
        sb.append(", tagId=");
        sb.append(this.tagId);
        sb.append(", licPlate=");
        sb.append(this.licPlate);
        sb.append(", type=");
        sb.append(this.type);
        sb.append(", qty=");
        sb.append(this.qty);
        sb.append(", status=");
        sb.append(this.status);
        sb.append(", fullTagId=");
        sb.append(this.fullTagId);
        sb.append(", transactionId=");
        sb.append(this.transactionId);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the orderDate
     */
    public Calendar getOrderDate() {
        return this.orderDate;
        // end getOrderDate
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
        // end setOrderDate
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
     * @return the tagId
     */
    public long getTagId() {
        return this.tagId;
        // end getTagId
    }

    /**
     * @param tagId the tagId to set
     */
    public void setTagId(long tagId) {
        this.tagId = tagId;
        // end setTagId
    }

    /**
     * @return the licPlate
     */
    public String getLicPlate() {
        return this.licPlate;
        // end getLicPlate
    }

    /**
     * @param licPlate the licPlate to set
     */
    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
        // end setLicPlate
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
        // end getType
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
        // end setType
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return this.qty;
        // end getQty
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
        // end setQty
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return this.status;
        // end getStatus
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
        // end setStatus
    }

    /**
     * @return the fullTagId
     */
    public String getFullTagId() {
        return this.fullTagId;
        // end getFullTagId
    }

    /**
     * @param fullTagId the fullTagId to set
     */
    public void setFullTagId(String fullTagId) {
        this.fullTagId = fullTagId;
        // end setFullTagId
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return this.location;
        // end getLocation
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
        // end setLocation
    }

    /**
     * @return the statusDate
     */
    public Calendar getStatusDate() {
        return this.statusDate;
        // end getStatusDate
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(Calendar statusDate) {
        this.statusDate = statusDate;
        // end setStatusDate
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
}
