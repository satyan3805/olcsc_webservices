package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Collection;

/**
 * Contains the result of tag update process.
 */
public class TagUpdateResultDTO extends BaseDTO{
    private boolean successful;
    private Collection duplicateTags;
    
    public TagUpdateResultDTO() {
    }


    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setDuplicateTags(Collection duplicateTags) {
        this.duplicateTags = duplicateTags;
    }

    public Collection getDuplicateTags() {
        return duplicateTags;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(", successful=");
        sb.append(successful);
        sb.append("]");
        return sb.toString();
    }
}
