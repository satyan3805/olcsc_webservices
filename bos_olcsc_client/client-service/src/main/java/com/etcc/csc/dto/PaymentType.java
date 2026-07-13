/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dto;

import org.apache.log4j.Logger;

import com.etcc.csc.util.StringUtil;

/**
 * Enumeration of valid payment types.
 * @author Milosh Boroyevich
 */
public enum PaymentType {
    /** Credit card. */
    CREDIT('C'/*PaymentType.CODE_CREDIT*/, "credit card"),
    /** EFT. */
    EFT('E'/*PaymentType.CODE_EFT*/, "EFT"),
    /** Invoice. */
    INVOICE('I'/*PaymentType.CODE_INVOICE*/, "invoice"),
    /** Non-Revenue. */
    NON_REVENUE('X'/*PaymentType.CODE_NONREV*/, "non-revenue"),
    /** Cash. */
    CASH('M'/*PaymentType.CODE_CASH*/, "cash"),
    ;

    public static final char CODE_CREDIT = 'C';
    public static final char CODE_EFT = 'E';
    public static final char CODE_INVOICE = 'I';
    public static final char CODE_NONREV = 'X';
    public static final char CODE_CASH = 'M';

    private static final Logger logger = Logger.getLogger(PaymentType.class);

    /**
     * Character code representing the payment type.
     */
    private final char code;
    /**
     * The user-friendly name of this object.
     */
    private final String display;

    private PaymentType(char code, String display) {
        this.code = code;
        this.display = display;
    }

    /**
     * Returns the enum constant corresponding to the specified code.
     * @param code case-insensitive code of the enum type
     * @return the enum constant (or <tt>null</tt> if code is <tt>empty</tt>)
     * @see #valueOfCode(char)
     * @see StringUtil#isEmpty(String)
     */
    public static PaymentType valueOfCode(String code) {
        if (!StringUtil.isEmpty(code)) {
            return valueOfCode(Character.toUpperCase(code.charAt(0)));
        }
        return null;
    }

    /**
     * Returns the enum constant corresponding to the specified code.
     * @param code character code of the enum type
     * @return the enum constant (or <tt>null</tt> if the specified code has no corresponding enum type)
     */
    public static PaymentType valueOfCode(char code) throws IllegalArgumentException {
        switch (code) {
        case CODE_CREDIT:
            return CREDIT;
        case CODE_EFT:
            return EFT;
        case CODE_INVOICE:
            return INVOICE;
        case CODE_NONREV:
            return NON_REVENUE;
        case CODE_CASH:
            return CASH;
        }
        logger.info("No enum const class for code: " + code);
        return null;
    }

    /**
     * Returns the character code form of this object.
     * @return the character code
     */
    public char getCode() {
        return code;
    }

    /**
     * Returns the character code form of this object as a string .
     * @return the character code as a string
     */
    public String getCodeString() {
        return Character.toString(code);
    }

    /**
     * Returns the "user-friendly" string form of this object.
     * @return the display string
     */
    public String getDisplay() {
        return display;
    }
}
