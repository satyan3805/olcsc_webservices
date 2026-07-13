/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;


/**
 * Contains the result of tag update process.
 */
public class TagUpdateResultDTO extends BaseDTO {
    
    /**
     * Unique ID for Serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = 6653305851391177102L;
    
    private boolean successful;
    private TagDTO[] duplicateTags;

    /**
     * Constructor.
     */
    public TagUpdateResultDTO() {
        // end <init>
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(", successful=");
        sb.append(this.successful);
        sb.append(", duplicateTages=");
        sb.append(this.duplicateTags);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the successful
     */
    public boolean isSuccessful() {
        return this.successful;
        // end isSuccessful
    }

    /**
     * @param successful the successful to set
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
        // end setSuccessful
    }

    /**
     * @return the duplicateTags
     */
    public TagDTO[] getDuplicateTags() {
        return this.duplicateTags;
        // end getDuplicateTags
    }

    /**
     * @param duplicateTags the duplicateTags to set
     */
    public void setDuplicateTags(TagDTO[] duplicateTags) {
        this.duplicateTags = duplicateTags;
        // end setDuplicateTags
    }

}
