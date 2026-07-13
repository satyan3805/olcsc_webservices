/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dto;

/**
 * DTO for transmitting (DB error/alert message) results where no data is returned.
 * @author Milosh Boroyevich
 */
public class ResultDTO extends BaseDTO {
    /**
     * Unique ID for Serialization
     */
    //Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = 4451523463182174227L;

    private int status;

    public ResultDTO withAlerts(AlertDTO[] alerts) {
        setAlerts(alerts);
        return this;
    }

    public ResultDTO withErrors(ErrorMessageDTO[] errors) {
        setErrors(errors);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResultDTO[status=");
        sb.append(status);
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
