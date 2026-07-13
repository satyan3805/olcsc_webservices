/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Service Parameters annotated with this are to be used as Return Values.  This initially applies to Mapped
 * Validation Exceptions.
 * @author (task 488) Stephen Davidson
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ReturnParam {
//Marker Annotation
}
