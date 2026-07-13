package com.etcc.csc.service;

import com.etcc.csc.common.BusinessObjectInterface;

public interface AdminInterface extends BusinessObjectInterface {
	void insertSessionCount(String serverName, int count);
}
