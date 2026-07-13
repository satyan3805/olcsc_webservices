/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    private BigDecimalUtil() { }

    public static BigDecimal nullSafe( BigDecimal init ) {
    	return  init != null ? init : new BigDecimal( 0.0 );   
    }
}
