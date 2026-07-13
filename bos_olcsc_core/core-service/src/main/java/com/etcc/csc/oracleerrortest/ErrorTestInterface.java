package com.etcc.csc.oracleerrortest;

import java.util.Collection;

import com.etcc.csc.common.EtccException;

public interface ErrorTestInterface {
	Collection performETest(String testParam) throws EtccException;
}