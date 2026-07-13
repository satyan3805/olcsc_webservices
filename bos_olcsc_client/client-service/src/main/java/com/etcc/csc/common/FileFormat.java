/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.common;

/**
 * Supported file formats to use.
 * @author Milosh Boroyevich
 * @author (task 488) Stephen Davidson
 */
public enum FileFormat {
    /**
     * PDF format.
     */
    PDF("pdf", new String[] {"application/pdf"/*FileFormat.CONTENT_TYPE_PDF*/}),
    /**
     * MS Excel format.
     */
    XLS("excel", new String[] {"application/vnd.ms-excel"/*FileFormat.CONTENT_TYPE_XLS*/, "application/xls"/*FileFormat.CONTENT_TYPE_XLS_ALT*/}),
    /**
     * HTML format.
     */
    HTML("html", new String[] {"text/html"/*FileFormat.CONTENT_TYPE_HTML*/}),
    /**
     * Unknown format.
     */
    UNKNOWN("unknown", new String[] {"application/octet-stream"/*FileFormat.CONTENT_TYPE_UNKNOWN*/});

    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";
    public static final String CONTENT_TYPE_XLS_ALT = "application/xls";
    public static final String CONTENT_TYPE_HTML = "text/html";
    public static final String CONTENT_TYPE_UNKNOWN = "application/octet-stream";

    private final String display;
    private final String[] contentTypes;

    private FileFormat(String display, String[] contentTypes) {
        this.display = display;
        this.contentTypes = contentTypes;
    }

    /**
     * Validates the content type against this file format.
     * @param contentType the file content type to verify
     * @return <tt>true</tt> if the specified content type matches is valid
     */
    public boolean isValidContentType(String contentType) {
        for (String validType : this.contentTypes)
            if (validType.equalsIgnoreCase(contentType))
                return true;
        return false;
    }

    /**
     * Returns the primary content type.
     * @return the primary content type
     */
    public String getContentType() {
        return contentTypes[0];
    }

    /**
     * Returns the all valid content types.
     * @return the all valid content types
     */
    public String[] getContentTypes() {
        return contentTypes;
    }

    public String getDisplay() {
        return this.display;
    }
}
