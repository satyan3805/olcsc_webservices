package com.etcc.csc.common;

import java.util.Date;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Collection;

/**
 * Base class for Data Transfer Objects.
 */
public class BaseDTO implements Serializable {
    private Calendar dateModified;
    private String modifiedBy;
    private Calendar dateCreated;
    private String createdBy;

    private Collection<ErrorMessageDTO> errors;

    public BaseDTO() {
    }

    //  public Date getDateModified() {
    //    return dateModified;
    //  }
    //  
    //  public void setDateModified(Date value) {
    //    dateModified = value;
    //  }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String value) {
        modifiedBy = value;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar value) {
        dateCreated = value;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String value) {
        createdBy = value;
    }

    /**
     * @param errors
     */
    public void setErrors(Collection errors) {
        this.errors = errors;
    }

    /**
     * @return
     */
    public Collection getErrors() {
        return errors;
    }
}
