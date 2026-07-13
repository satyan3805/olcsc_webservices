package com.etcc.csc.util;

import java.net.MalformedURLException;
import java.net.URL;

import com.etcc.csc.delegate.AppDelegate;

import webservice.paymentappliance.etcc.com.PANManagerImpl;
import webservice.paymentappliance.etcc.com.PANManagerServiceLocator;

public class WebServiceClientUtil {

	/**
	 * Returns the service implementation for Payment appliance webservice
	 * @return
	 */
	public static PANManagerImpl getPANManagerImpl() throws Exception {
		try {
			StringBuilder paUrl = new StringBuilder(new AppDelegate().getSysParam("PCI_APPLIANCE_WS_URL"));
			URL url = new URL(paUrl.toString());
			PANManagerServiceLocator serviceLocator = new PANManagerServiceLocator();
			return serviceLocator.getPANManagerPort(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new Exception(e.getLocalizedMessage());
		}
	}
}
																											