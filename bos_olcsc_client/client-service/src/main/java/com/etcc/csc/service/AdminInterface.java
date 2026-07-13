package com.etcc.csc.service;

import com.etcc.csc.common.EtccException;

public interface AdminInterface extends ServiceInterface {
  void insertSessionCount( String serverName, int count ) throws EtccException;  
}
