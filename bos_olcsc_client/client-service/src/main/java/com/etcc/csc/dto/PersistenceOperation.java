/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.dto;

import org.apache.log4j.Logger;

import com.etcc.csc.util.StringUtil;

/**
 * Enumeration specifying the valid persistence operations for a DTO.
 * Create, Update, and Delete.
 * @author Milosh Boroyevich
 */
public enum PersistenceOperation {
    /** Create. */
    CREATE('C'/*PersistenceOperation.CODE_CREATE*/),
    /** Update. */
    UPDATE('U'/*PersistenceOperation.CODE_UPDATE*/),
    /** Delete. */
    DELETE('D'/*PersistenceOperation.CODE_DELETE*/),
    ;

    public static final char CODE_CREATE = 'C';
    public static final char CODE_UPDATE = 'U';
    public static final char CODE_DELETE = 'D';

    private static final Logger logger = Logger.getLogger(PersistenceOperation.class);

    /**
     * Character code representing the operation.
     */
    private final char code;

    private PersistenceOperation(char code) {
        this.code = code;
    }

    /**
     * Returns the enum constant corresponding to the specified code.
     * @param code case-insensitive code of the enum type
     * @return the enum constant (or <tt>null</tt> if code is <tt>empty</tt>)
     * @see #valueOfCode(char)
     * @see StringUtil#isEmpty(String)
     */
    public static PersistenceOperation valueOfCode(String code) {
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
    public static PersistenceOperation valueOfCode(char code) {
        switch (code) {
        case CODE_CREATE:
            return CREATE;
        case CODE_UPDATE:
            return UPDATE;
        case CODE_DELETE:
            return DELETE;
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
}
