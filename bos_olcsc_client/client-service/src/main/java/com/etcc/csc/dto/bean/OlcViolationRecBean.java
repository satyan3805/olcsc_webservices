package com.etcc.csc.dto.bean;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Calendar;

public class OlcViolationRecBean implements Serializable {
    private BigDecimal violationId;
    private Calendar violationTime;
    private String laneName;

    public OlcViolationRecBean() {
    }

    public void setViolationId(BigDecimal violationId) {
        this.violationId = violationId;
    }

    public BigDecimal getViolationId() {
        return violationId;
    }

    public void setViolationTime(Calendar violationTime) {
        this.violationTime = violationTime;
    }

    public Calendar getViolationTime() {
        return violationTime;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public String getLaneName() {
        return laneName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("violationId=").append(violationId);
        sb.append(", violationTime=").append(violationTime);
        sb.append(", laneName=").append(laneName);
        sb.append("]");
        return sb.toString();
    }
}
