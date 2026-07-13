/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dto;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * Object to pass binary documents around.  Specifically compatible with XFire.
 * @author Stephen Davidson
 *
 */
public class DocumentDTO extends BaseDTO {
    
    /**
     * Unique ID for Serialization.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = -6660497118134522976L;
    
    private byte[] document;

    /**
     * Constructor.
     */
    public DocumentDTO() {
        super();
        // end <init>
    }
    
    /**
     * Convenience constructor that loads the Document from a Blob.
     * @param source The source of the Document.
     * @throws SQLException thrown if any exceptions occur accessing the blob.
     */
    public DocumentDTO(final Blob source) throws SQLException{
        if (source.length() > Integer.MAX_VALUE){
            throw new IllegalArgumentException("Document has too many bytes for this implementation to load: " + source.length());
        }//else
        this.document = source.getBytes(1, (int)source.length());
    }
    
    public DocumentDTO(final byte[] source) throws SQLException{
        if (source != null){
        	this.document = source;
        }
    }

    /**
     * @param document the document to set
     */
    public void setDocument(byte[] document) {
        this.document = document;
        //end setDocument
    }

    /**
     * @return the document
     */
    public byte[] getDocument() {
        return this.document;
        //end getDocument
    }

}
