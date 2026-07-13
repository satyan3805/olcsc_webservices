/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao;

import java.util.Arrays;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.service.DbLoggerInterface;

public abstract class DbLoggerDAO extends BaseDAO implements DbLoggerInterface {
	public String logError(String message, StackTraceElement[] stack) throws EtccException {
		return logError(message, Arrays.toString(stack));
	}
}
