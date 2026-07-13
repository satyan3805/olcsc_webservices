package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

/**
 * Contains information about the user's environment.
 */
public class UserEnvDTO extends BaseDTO {
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
    private String locale;

    public UserEnvDTO() {
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getAttribute5() {
        return attribute5;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("browserType=");
        sb.append(browserType);
        sb.append(",browserVersion=");
        sb.append(browserVersion);
        sb.append(",osType=");
        sb.append(osType);
        sb.append(",osVersion=");
        sb.append(osVersion);
        sb.append(",attribute1=");
        sb.append(attribute1);
        sb.append(",attribute2=");
        sb.append(attribute2);
        sb.append(",attribute3=");
        sb.append(attribute3);
        sb.append(",attribute4=");
        sb.append(attribute4);
        sb.append(",attribute5=");
        sb.append(attribute5);
        sb.append(",locale=");
        sb.append(locale);
        sb.append("]");
        return sb.toString();
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }
}
