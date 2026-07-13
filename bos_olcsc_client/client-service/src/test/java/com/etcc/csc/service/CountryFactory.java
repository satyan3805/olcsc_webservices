/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.CountryDTO;

/**
 * Creates a test implementation of <tt>CountryInterface</tt>.
 * @author Stephen Davidson
 * 
 */
public class CountryFactory {

    // Package level. The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final CountryInterface mocked) {
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations() {
                {
                    allowing(mocked).getCountries();
                    will(returnValue(getCountries()));

                }
            });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static final String[][] countries = new String[][] { { "USA", "United States" }, { "CA", "Canada" },
            { "FR", "France" } };

    /**
     * Gets the currently supported countries.
     * @return The currently supported countries.
     */
    public static Collection<CountryDTO> getCountries(){
        Collection<CountryDTO> result = new ArrayList<CountryDTO>(countries.length);
        for (int i = 0; i < countries.length; i++) {
            CountryDTO dto = new CountryDTO();
            dto.setCountryCode(countries[i][0]);
            dto.setCountry(countries[i][1]);
            result.add(dto);
        }
        return result;
    }

}
