/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Contains information about the user's environment.
 */
public class UserEnvDTO extends BaseDTO {
    /**
     * Unique ID for serialization.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = -5499535986428245766L;
    
    private String browserType;
    private String browserVersion;
    private String osType;
    private String osVersion;
    private String userAgent;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String sourceUserName;

    /**
     * Constructor.
     */
    public UserEnvDTO() {
        // end <init>
    }

    /**
     * @return the browserType
     */
    public String getBrowserType() {
        return this.browserType;
        // end getBrowserType
    }

    /**
     * @param browserType the browserType to set
     */
    public void setBrowserType(String browserType) {
        this.browserType = browserType;
        // end setBrowserType
    }

    /**
     * @return the browserVersion
     */
    public String getBrowserVersion() {
        return this.browserVersion;
        // end getBrowserVersion
    }

    /**
     * @param browserVersion the browserVersion to set
     */
    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
        // end setBrowserVersion
    }

    /**
     * @return the osType
     */
    public String getOsType() {
        return this.osType;
        // end getOsType
    }

    /**
     * @param osType the osType to set
     */
    public void setOsType(String osType) {
        this.osType = osType;
        // end setOsType
    }

    /**
     * @return the osVersion
     */
    public String getOsVersion() {
        return this.osVersion;
        // end getOsVersion
    }

    /**
     * @param osVersion the osVersion to set
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
        // end setOsVersion
    }

    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return this.userAgent;
        // end getUserAgent
    }

    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        // end setUserAgent
    }

    /**
     * @return the attribute1
     */
    public String getAttribute1() {
        return this.attribute1;
        // end getAttribute1
    }

    /**
     * @param attribute1 the attribute1 to set
     */
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
        // end setAttribute1
    }

    /**
     * @return the attribute2
     */
    public String getAttribute2() {
        return this.attribute2;
        // end getAttribute2
    }

    /**
     * @param attribute2 the attribute2 to set
     */
    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
        // end setAttribute2
    }

    /**
     * @return the attribute3
     */
    public String getAttribute3() {
        return this.attribute3;
        // end getAttribute3
    }

    /**
     * @param attribute3 the attribute3 to set
     */
    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
        // end setAttribute3
    }

    /**
     * @return the attribute4
     */
    public String getAttribute4() {
        return this.attribute4;
        // end getAttribute4
    }

    /**
     * @param attribute4 the attribute4 to set
     */
    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
        // end setAttribute4
    }

    /**
     * @return the attribute5
     */
    public String getAttribute5() {
        return this.attribute5;
        // end getAttribute5
    }

    /**
     * @param attribute5 the attribute5 to set
     */
    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
        // end setAttribute5
    }
    
    /**
     * @param sourceUserName the attribute5 to set
     */
    public void setSourceUserName(String sourceUserName) {
        this.sourceUserName = sourceUserName;
        // end setAttribute5
    }
    
    /**
     * @return the ourceUserName
     */
    public String getSourceUserName() {
        return this.sourceUserName;
        // end sourceUserName
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("browserType=");
        sb.append(this.browserType);
        sb.append(",browserVersion=");
        sb.append(this.browserVersion);
        sb.append(",osType=");
        sb.append(this.osType);
        sb.append(",osVersion=");
        sb.append(this.osVersion);
        sb.append(",attribute1=");
        sb.append(this.attribute1);
        sb.append(",attribute2=");
        sb.append(this.attribute2);
        sb.append(",attribute3=");
        sb.append(this.attribute3);
        sb.append(",attribute4=");
        sb.append(this.attribute4);
        sb.append(",attribute5=");
        sb.append(this.attribute5);
        sb.append("]");
        return sb.toString();
    }
}
