package com.etcc.csc.delegate;

import java.util.Collection;

import com.etcc.csc.common.CacheAdmin;
import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.service.CountryInterface;

public class CountryDelegate extends Delegate implements CountryInterface {
	private static final String COUNTRY = "country";
	private static final String DEFAULT_COUNTRY_CODE = "defaultCountryCode";

	CountryInterface country = (CountryInterface) getServiceObject(ServiceObjectEnum.COUNTRY);

	public CountryDelegate() {
		super(CountryDelegate.class);
	}

	public Collection getCountry() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		Collection countries = (Collection) admin.getFromCache(COUNTRY);

		try {
			if (countries == null) {
				countries = country.getCountry();
				admin.putInCache(COUNTRY, countries);

			}
			return countries;
		} catch (Throwable t) {
			logger.equals(t);
			admin.cancelUpdate(COUNTRY);
			throw new EtccException("Error running getStates: " + t, t);
		}
	}

	public String getDefaultCountryCode() throws EtccException {
		CacheAdmin admin = CacheAdmin.getInstance();
		String defaultCountryCode = (String) admin
				.getFromCache(DEFAULT_COUNTRY_CODE);
		try {
			if (defaultCountryCode == null) {
				defaultCountryCode = country.getDefaultCountryCode();
				admin.putInCache(DEFAULT_COUNTRY_CODE, defaultCountryCode);
			}
			return defaultCountryCode;
		} catch (Throwable t) {
			logger.equals(t);
			admin.cancelUpdate(COUNTRY);
			throw new EtccException("Error running getStates: " + t, t);
		}
	}
}