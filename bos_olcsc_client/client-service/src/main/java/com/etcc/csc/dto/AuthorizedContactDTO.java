/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.StringUtil;

/**
 * Data Transfer Object for Authorized Contacts.
 * @author unknown
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class AuthorizedContactDTO extends BaseDTO implements Cloneable {
    /**
     * Unique ID for Serialization with Version number.
     */
    //Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
    private static final long serialVersionUID = 5643586931971317951L;

    private String contactId;
    private String firstName;
    private String lastName;
    private String password;
    private PersistenceOperation action = null; // values C,U,D

    /**
     * Creates and returns a copy of this object. The <tt>firstName</tt>,
     * <tt>lastName</tt>, and <tt>password</tt> are copied to the cloned object.
     */
    @Override
    public AuthorizedContactDTO clone() {
        AuthorizedContactDTO clone = new AuthorizedContactDTO();
        clone.setFirstName(this.firstName == null ? null : this.firstName.trim().toUpperCase());
        clone.setLastName(this.lastName == null ? null : this.lastName.trim().toUpperCase());
        clone.setPassword(this.password == null ? null : this.password.trim().toUpperCase());
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AuthorizedContactDTO[");
        sb.append("action=");
        sb.append(this.action);
        sb.append(", contactId=");
        sb.append(this.contactId);
        sb.append(", firstName=");
        sb.append(this.firstName);
        sb.append(", lastName=");
        sb.append(this.lastName);
        sb.append(", password=");
        if (!StringUtil.isEmpty(this.password))
            sb.append("****");
        sb.append("]");
        return sb.toString();
    }

    /**
     * Checks if this DTO is has any blank values.
     * Fields checked are <tt>firstName</tt>, <tt>lastName</tt>, and <tt>password</tt>.
     * @return <tt>true</tt> if this DTO has blanks
     * @see StringUtil#isEmpty(String)
     */
    public boolean hasBlanks() {
        return (StringUtil.isEmpty(this.firstName) || StringUtil.isEmpty(this.lastName) || StringUtil.isEmpty(this.password));
    }

    /**
     * Returns <tt>true</tt> if this DTO has <em>no</em> blanks.
     * @see #hasBlanks()
     */
    @IgnoreProperty
    public boolean isValid() {
        return !hasBlanks();
    }

    /**
     * Sets the operation to <tt>DELETE</tt>.
     * @see #setOperation(PersistenceOperation)
     * @see PersistenceOperation#DELETE
     */
    public void setActionDelete() {
        this.action = PersistenceOperation.DELETE;
    }

    /**
     * Sets the operation to <tt>CREATE</tt> if Contact ID is empty, otherwise <tt>UPDATE</tt>.
     * @see #setOperation(PersistenceOperation)
     * @see #getContactId()
     * @see StringUtil#isEmpty(String)
     * @see PersistenceOperation#CREATE
     * @see PersistenceOperation#UPDATE
     */
    public void setActionModify() {
        if (StringUtil.isEmpty(this.contactId))
            this.action = PersistenceOperation.CREATE;
        else
            this.action = PersistenceOperation.UPDATE;
    }

    /**
     * @return the action
     * @see PersistenceOperation#getCodeString()
     */
    public String getAction() {
        return this.action == null ? null : this.action.getCodeString();
    }

    /**
     * @param action the action to set
     * @see PersistenceOperation#valueOfCode(String)
     */
    public void setAction(String action) {
        this.action = PersistenceOperation.valueOfCode(action);
    }

    public void setOperation(PersistenceOperation operation) {
        this.action = operation;
    }

    public PersistenceOperation getOperation() {
        return this.action;
    }


    /**
     * @return the contactId
     */
    public String getContactId() {
        return this.contactId;
    }

    /**
     * @param contactId the contactId to set
     */
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
