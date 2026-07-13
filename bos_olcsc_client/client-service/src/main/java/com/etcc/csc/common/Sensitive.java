/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a Parameter as containing Sensitive information, such as Passwords or Credit Card info. Anything logging method
 * parameters should check for the presence of this annotation.  And any Parameter that has Sensitive info should be
 * flagged with this Annotation.
 * 
 * @author (task 488) Stephen Davidson
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Sensitive {

    /**
     * The value to display instead of the Sensitive value. Default is "********".
     */
    String displayValue() default "********";

}
