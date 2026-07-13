package com.etcc.csc.dto;

import java.util.Calendar;

/**
 * The user's session information.
 */
public class SessionDTO {
    private String sessionId;
    private long documentId;
    private String userName;
    private String ipAddress;
    private Calendar activationDate;
    private Calendar expirationDate;
    private Calendar dateCreated;
    private String sessionStatus;
    private String docType;
    private String lang;
    public SessionDTO() {
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setActivationDate(Calendar activationDate) {
        this.activationDate = activationDate;
    }

    public Calendar getActivationDate() {
        return activationDate;
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocType() {
        return docType;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("sessionId=");
        sb.append(sessionId);
        sb.append(",documentId=");
        sb.append(documentId);
        sb.append(",userName=");
        sb.append(userName);
        sb.append(",ipAddress=");
        sb.append(ipAddress);
        sb.append(",activationDate=");
        sb.append(activationDate);
        sb.append(",expirationDate=");
        sb.append(expirationDate);
        sb.append(",dateCreated=");
        sb.append(dateCreated);
        sb.append(",sessionStatus=");
        sb.append(docType);
        sb.append("]");
        return sb.toString();
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }
}
