/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.StringUtil;

/**
 * EFT Transfer Object.  Based on the AccountEFTDTO's from the original OLSCSC Services project.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class AccountEFTDTO extends AccountPaymentMethodDTO {
    /**
     * Unique ID for Serialization with Version number.
     */
    //Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
    private static final long serialVersionUID = 6090082787917375301L;

    /**
     * The enumeration of bank account types.
     * @author Milosh Boroyevich
     */
    public enum BankAccountType {
    	PERSONAL("PC"/*BankAccountType.CODE_PERSONAL*/, "Personal bank account"),
    	BUSINESS("CC"/*BankAccountType.CODE_BUSINESS*/, "Business bank account");

        public static final String CODE_PERSONAL = "PC";
        public static final String CODE_BUSINESS = "CC";

        /**
         * The code of this enum.
         */
        private final String code;
        /**
         * The user-friendly name of this enum.
         */
        private final String display;

        private BankAccountType(final String code, final String display) {
        	this.code = code;
            this.display = display;
        }

        /**
         * Returns the "user-friendly" string form of this object.
         * @return the display string
         */
        public String getDisplay() {
            return this.display;
        }

        /**
         * Returns the enum constant corresponding to the specified code.
         * @param code case-insensitive code or enum string
         * @return the enum constant, or <tt>null</tt> if no such enum
         */
        public static BankAccountType valueOfCode(String code) {
        	if (!StringUtil.isEmpty(code)) {
        		if (code.equalsIgnoreCase(CODE_PERSONAL) || code.toUpperCase().equals(PERSONAL.toString()))
        			return PERSONAL;
        		else if (code.equalsIgnoreCase(CODE_BUSINESS) || code.toUpperCase().equals(BUSINESS.toString()))
        			return BUSINESS;
        	}
        	return null;
        }

        /**
         * Returns the code form of this object.
         * @return the code
         */
        public String getCode() {
            return code;
        }

        /**
         * Compare the specified code to this enum.
         * @param code the code to compare
         * @return <tt>true</tt> if this enum has a corresponding code
         * @see #valueOfCode(String)
         */
        public boolean equals(String code) {
            return this == valueOfCode(code);
        }
    }


    private BankAccountType accountType;
    private String routingNumber;
    private String accountNumber;
    // Added as part of CCRMA changes
    private String nameOnBankAccount;
    private String firstName;
    private String lastName;
    private boolean primary;

    @IgnoreProperty
	@Override
	public final PaymentType getPaymentType() {
		return PaymentType.EFT;
	}

    @IgnoreProperty
    public String getName() {
        return null;
    }

	/**
     * Gets a masked version of the account number for display.
     * @return masked version of the account number.
     * @deprecated use a formatting utility instead
     */
    @IgnoreProperty
    @Deprecated
    public String getAccountNumberDisplay() {
    	return StringUtil.maskAccount(accountNumber);
    }

	public void setAccountType(String accountType) {
		this.accountType = BankAccountType.valueOfCode(accountType);
	}

	@Override
    public String toString() {
        return toStringBuilder().toString();
    }
    /**
     * Similar to {@link #toString()}, but returns a StringBuilder instead.
     * @return representation of this object in a StringBuilder.
     */
    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("accountType=");
        sb.append(this.accountType);
        sb.append(", routingNumber=");
        if (!StringUtil.isEmpty(this.routingNumber)) {
            sb.append("****");
        }
        sb.append(", accountNumber=");
        sb.append(getAccountNumberDisplay());
        sb.append(", retailTransId=");
        sb.append(this.retailTransId);
        sb.append(", paymentAmount=");
        sb.append(this.paymentAmount);

        sb.append(", address1=");
        sb.append(this.address1);
        sb.append(", address2=");
        sb.append(this.address2);
        sb.append(", address3=");
        sb.append(this.address3);
        sb.append(", address4=");
        sb.append(this.address4);
        sb.append(", city=");
        sb.append(this.city);
        sb.append(", state=");
        sb.append(this.state);
        sb.append(", zipCode=");
        sb.append(this.zip);
        sb.append(", plus4=");
        sb.append(this.plus4);
        sb.append(", country=");
        sb.append(this.country);
        sb.append(", nameOnBankAccount=");
        sb.append(this.nameOnBankAccount);
        sb.append(", firstName=");
        sb.append(this.firstName);
        sb.append(", lastName=");
        sb.append(this.lastName);
        sb.append(", primary=");
        sb.append(this.primary);
        sb.append("]");
        return sb;
    }


    // Default getter/setter pairs

	public BankAccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(BankAccountType accountType) {
		this.accountType = accountType;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getNameOnBankAccount() {
		return nameOnBankAccount;
	}

	public void setNameOnBankAccount(String nameOnBankAccount) {
		this.nameOnBankAccount = nameOnBankAccount;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	
}
