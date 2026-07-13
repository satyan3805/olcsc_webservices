package com.etcc.csc.service;

import com.etcc.csc.common.DAOFactory;

public class Admin implements AdminInterface {
	public Admin() {
	}

	public void insertSessionCount(String serverName, int count) {
		DAOFactory.getDAOFactory().getAdminDAO()
				.insertSessionCount(serverName, count);
	}
}
