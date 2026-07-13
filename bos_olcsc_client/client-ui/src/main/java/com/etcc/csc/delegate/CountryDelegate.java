package com.etcc.csc.delegate;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.CountryDTO;
import com.etcc.csc.service.CountryInterface;
import com.etcc.csc.service.ServiceFactory;

import java.util.Collection;

public class CountryDelegate implements CountryInterface {
    public Collection<CountryDTO> getCountries() throws EtccException {
        try {
            //Country country = new Country();
            //return country.getCountries();
//             UtilWsSoapHttpPortClient stub = new UtilWsSoapHttpPortClient();
//             stub.setEndpoint(WsClient.getInstance().getWsEndPointContext() + "utilWsSoapHttpPort");
//             return stub.getCountries();
        	return ServiceFactory.getImplementation(CountryInterface.class).getCountries();
        }
        catch (Throwable t) {
            throw new EtccException("Error running getCountries: " + t, t);
        }
    }

    public static void main (String[] args) throws Exception {
        CountryDelegate dl = new CountryDelegate();
        Collection<CountryDTO> countries = dl.getCountries();
        System.out.println(countries.size());
    }
}
