package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.EtccException;

/**
 * Oracle error test interface based on ErrorTestInterface from OLCSCService.
 * @author Milosh Boroyevich
 */
public interface OracleErrorTestInterface  {
    Collection performETest(String testParam) throws EtccException;
}
