/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.math.BigDecimal;

/**
 * Preference Value Data Transfer Object. From the original OLCSCService module.
 */
public class AccountPreferenceValueDTO extends BaseDTO {
    /**
     * Unique ID for Serialization with Version number.
     */
    // Do NOT regenerate, to allow compatibility with "foreign" clients, such as
    // Idea.
    private static final long serialVersionUID = 1389089499288043679L;
    private BigDecimal acctId;
    private String notificationType;
    private String description;
    private String notificationFormat;
    private boolean active;
    private BigDecimal section;

    /**
     * Default Constructor.
     */
    public AccountPreferenceValueDTO() {
        super();
        // end <init>
    }

    /**
     * Constructor.
     * 
     * @param acctId
     * @param notificationType
     * @param description
     * @param notificationFormat
     * @param active
     * @param section
     */
    public AccountPreferenceValueDTO(BigDecimal acctId, String notificationType, String description,
            String notificationFormat, boolean active, BigDecimal section) {
        super();
        this.acctId = acctId;
        this.notificationType = notificationType;
        this.description = description;
        this.notificationFormat = notificationFormat;
        this.active = active;
        this.section = section;
    }

    public AccountPreferenceValueDTO(BigDecimal acctId, String notificationType, String description,
            String notificationFormat, String active, BigDecimal section) {
        this(acctId, notificationType, description, notificationFormat, ("Yy".indexOf(active.charAt(0)) >= 0), section);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("acctId=");
        sb.append(this.acctId);
        sb.append(",notificationType=");
        sb.append(this.notificationType);
        sb.append(",description=");
        sb.append(this.description);
        sb.append(",notificationFormat=");
        sb.append(this.notificationFormat);
        sb.append(",section=");
        sb.append(this.section);
        sb.append(",active=");
        sb.append(this.active);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the acctId
     */
    public BigDecimal getAcctId() {
        return this.acctId;
        // end getAcctId
    }

    /**
     * @param acctId the acctId to set
     */
    public void setAcctId(BigDecimal acctId) {
        this.acctId = acctId;
        // end setAcctId
    }

    /**
     * @return the notificationType
     */
    public String getNotificationType() {
        return this.notificationType;
        // end getNotificationType
    }

    /**
     * @param notificationType the notificationType to set
     */
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
        // end setNotificationType
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
        // end getDescription
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
        // end setDescription
    }

    /**
     * @return the notificationFormat
     */
    public String getNotificationFormat() {
        return this.notificationFormat;
        // end getNotificationFormat
    }

    /**
     * @param notificationFormat the notificationFormat to set
     */
    public void setNotificationFormat(String notificationFormat) {
        this.notificationFormat = notificationFormat;
        // end setNotificationFormat
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return this.active;
        // end isActive
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
        // end setActive
    }

    /**
     * @return the section
     */
    public BigDecimal getSection() {
        return this.section;
        // end getSection
    }

    /**
     * @param section the section to set
     */
    public void setSection(BigDecimal section) {
        this.section = section;
        // end setSection
    }

}// end AccountPreferenceValue
