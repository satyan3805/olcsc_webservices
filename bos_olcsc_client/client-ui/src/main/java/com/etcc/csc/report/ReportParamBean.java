/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.report;

public class ReportParamBean {
    public static final String KEY = "ReportParamBean";

    private String format;
    private String filename;
    private String filePath;

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
