/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * Interface for account name (contact) fields.
 * @author Milosh Boroyevich
 */
public interface AccountName {
    public String getFirstName();
    public void setFirstName(String firstName);

    public String getMiddleInitial();
    public void setMiddleInitial(String middleInitial);

    public String getLastName();
    public void setLastName(String lastName);

    public String getCompanyName();
    public void setCompanyName(String companyName);
}
