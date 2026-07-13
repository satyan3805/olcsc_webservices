/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

/**
 * Base Data Transfer Object. Based on the BaseDTO's from the original OLSCSC Services project.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public abstract class BaseDTO implements Serializable {
    /**
     * Unique ID for Serialization with Version number.
     */
    // Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
    private static final long serialVersionUID = 8096814078289439024L;

    private Date dateModified;
    private String modifiedBy;
    private Date dateCreated;
    private String createdBy;

    // Arrays for WS Clients.
    private ErrorMessageDTO[] errors;
    private AlertDTO[] alerts;
    private boolean valid;

    /**
     * XFire needs write access to some properties and methods that should not otherwise be used.  So, this
     * routine checks to see what the caller is, and if XFire, returns true.
     * @return true if one of the objects in the stack is XFire's BeanType, which is used by XFire to
     * serialize/deserialize an object.
     */
    protected static boolean isCallerXFire() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stack) {
            final String className = stackTraceElement.getClassName();
            if (className.startsWith("org") && className.equals("org.codehaus.xfire.aegis.type.basic.BeanType")){
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Checks whether this DTO is valid.
     * The default implementation always returns <tt>true</tt>.
     * @return <tt>true</tt> if this DTO is valid
     */
   /* public boolean isValid() {
        return true;
    }*/

    /**

    /**
     * Whether or not this DTO has errors set.
     * @return If any errors are set on this DTO.
     */
    public boolean hasErrors() {
        return this.errors != null && this.errors.length > 0;
    }

    /**
     * @return the date modified
     */
    public Date getDateModified() {
        return this.dateModified;
        // end getDateModified
    }

    /**
     * @param dateModified sets the date modified
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
        // end setDateModified
    }

    /**
     * @return the modified by
     */
    public String getModifiedBy() {
        return this.modifiedBy;
        // end getModifiedBy
    }

    /**
     * @param modifiedBy sets modified by
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        // end setModifiedBy
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return this.dateCreated;
        // end getDateCreated
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        // end setDateCreated
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
        // end getCreatedBy
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        // end setCreatedBy
    }

    /**
     * Clears any errors/error Messages.
     */
    public void clearErrors(){
        this.errors = null;
    }

    /**
     * @return the errors
     */

    public ErrorMessageDTO[] getErrors() {
        return this.errors;
    }

    /**
     * Sets the Errors to the specified array.
     * @param errors the errors to set
     */
    public void setErrors(ErrorMessageDTO[] errors) {
    	this.errors = errors;
    }

    /**
     * Adds the String error message to this DTO.
     * @param error The error to add.
     * @return a reference to this object
     */
    public BaseDTO addError(String error){
        return addError(new ErrorMessageDTO(null, error));
    }
    /**
     * Adds the Error to the internal list/array of errors.
     * @param error The error to add.
     * @return a reference to this object
     */
    public BaseDTO addError(ErrorMessageDTO error) {
        if (this.errors == null) {
            this.errors = new ErrorMessageDTO[1];
            this.errors[0] = error;
        } else {
            ErrorMessageDTO[] old = this.errors;
            this.errors = new ErrorMessageDTO[old.length + 1];
            System.arraycopy(old, 0, this.errors, 0, old.length);
            this.errors[old.length] = error;
        }
        return this;
    }

    /**
     * Adds a String Array of error messages to this DTO.
     * @param errors The error messages to add.
     */
    public BaseDTO addErrors(String[] errors){
        if (errors != null && errors.length > 0) {
            int start;
            if (this.errors == null){
                this.errors = new ErrorMessageDTO[errors.length];
                start = 0;
            } else {
                ErrorMessageDTO[] old = this.errors;
                this.errors = new ErrorMessageDTO[old.length + errors.length];
                // copy the old errors to the new array
                System.arraycopy(old, 0, this.errors, 0, old.length);
                start = old.length;
            }
            for(int i = start, size = errors.length; i < size; i++){
                this.errors[i] = new ErrorMessageDTO(null, errors[i]);
            }
        }
        return this;
    }

    /**
     * Adds the Errors to the internal list/array of errors.
     * @param errors The errors to add.
     */
    public BaseDTO addErrors(ErrorMessageDTO[] errors) {
    	if (errors != null && errors.length > 0) {
    		if (this.errors == null) {
    			this.errors = errors;
    		} else {
        		int addcount = errors.length;
    			ErrorMessageDTO[] old = this.errors;
    			this.errors = new ErrorMessageDTO[old.length + addcount];
    			// copy the old errors to the new array
    			System.arraycopy(old, 0, this.errors, 0, old.length);
    			// append the new errors
    			System.arraycopy(errors, 0, this.errors, old.length, addcount);
    		}
    	}
    	return this;
    }

    /**
     * Adds the Errors to the internal list/array of errors.
     * @param errors The errors to add.
     */
    public BaseDTO addErrors(Collection<ErrorMessageDTO> errors) {
    	if (errors != null & !errors.isEmpty()) {
    		int addcount = errors.size();
    		if (this.errors == null) {
    			this.errors = errors.toArray(new ErrorMessageDTO[addcount]);
    		} else {
    			ErrorMessageDTO[] old = this.errors;
    			this.errors = new ErrorMessageDTO[old.length + addcount];
    			// copy the old errors to the new array
    			System.arraycopy(old, 0, this.errors, 0, old.length);
    			// append the new errors
    			int i = old.length;
    			for (ErrorMessageDTO error : errors)
    				this.errors[i++] = error;
    		}
    	}
    	return this;
    }

    /**
     * Sets the Errors to the specified collection.
     * @deprecated use {@link #setErrors(ErrorMessageDTO[])}
     * @param errors a collection of error message objects
     */
    @Deprecated
    public void setErrors(Collection<ErrorMessageDTO> errors) {
        this.errors = errors.toArray(new ErrorMessageDTO[errors.size()]);
    }

    /**
     * Converts the specified collection of strings to error messages.
     * @param errors a collection of message strings
     * @return the collection of error messages represented by the specified collection
     */
    public static Collection<ErrorMessageDTO> convertToMessages(Collection<String> errors) {
    	ArrayList<ErrorMessageDTO> messages = null;
    	if (errors != null) {
    		messages = new ArrayList<ErrorMessageDTO>(errors.size());
    		for (String error : errors) {
    			ErrorMessageDTO msg = new ErrorMessageDTO();
    			msg.setMessage(error);
    			messages.add(msg);
    		}
    	}
        return messages;
    }

    /**
     * @param alerts the alerts to set
     * @deprecated use {@link #setAlerts(AlertDTO[])}
     * @see #setAlerts(AlertDTO[])
     */
    @Deprecated
    public void setAlerts(Collection<AlertDTO> alerts) {
        this.alerts = alerts.toArray(new AlertDTO[alerts.size()]);
    }

    /**
     * @param alerts the alerts to set
     */
    public void setAlerts(AlertDTO[] alerts) {
        this.alerts = alerts;
        // end setAlerts
    }

    public boolean isValid() {
		return true;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
	}


	/**
     * @return the alerts
     */
    public AlertDTO[] getAlerts() {
        return this.alerts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("alerts=");
        sb.append(Arrays.toString(alerts));
        sb.append(", errors=");
        sb.append(Arrays.toString(errors));
        sb.append("]");
        return sb.toString();
    }
}
