/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.ResultDTO;

/**
 * Generates the return values for the methods needed for Tests (and demos).
 * @author Milosh Boroyevich
 */
public class AuthorizedContactFactory {
    // the contacts reference should not be modified -- just the contents of the list
    private static final List<AuthorizedContactDTO> contacts;

    static {
        contacts = new ArrayList<AuthorizedContactDTO>();
        AuthorizedContactDTO contact = new AuthorizedContactDTO();
        contact.setFirstName("Hank");
        contact.setLastName(AccountFactory.ACCOUNT_NAME_LAST);
        contact.setPassword("password");
        contacts.add(contact);
    }

    //Package level.  The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final AuthorizedContactInterface mocked) {
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                //Note: If the exact values are NOT passed as parameters, the getAccount will throw an Exception.
                //So, set up to use "Wildcards".
                allowing(mocked).getAuthContacts(with(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID)));
                will(returnValue(getAuthorizedContacts()));
                allowing(mocked).getAuthContacts(with(any(AccountLoginDTO.class)));
                will(returnValue(new AuthorizedContactDTO[0]));
                allowing(mocked).modifyAuthContacts(
                        with(AccountFactory.getAccountLoginDTO(AccountFactory.POPULATED_ACCOUNT_ID)),
                        with(any(AuthorizedContactDTO[].class)),
                        with(aNonNull(String.class))
                );
                will(returnValue(addAuthorizedContact()));
            }
            });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            //Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static AuthorizedContactDTO[] getAuthorizedContacts() {
        AuthorizedContactDTO[] result = new AuthorizedContactDTO[contacts.size()];
        return contacts.toArray(result);
    }

    public static String getAuthorizedContactUrl() {
    	return "http://localhost/AuthorizedContactReceipt.pdf";
    }

    public static Collection<ErrorMessageDTO> getSubmitAuthorizedContactError() {
    	Collection<ErrorMessageDTO> errors = new ArrayList<ErrorMessageDTO>(1);
    	ErrorMessageDTO error = new ErrorMessageDTO("velcroError", "Error processing velcro request.");
    	errors.add(error);
        return errors;
    }

    private static ResultDTO addAuthorizedContact() {
        AuthorizedContactDTO contact = new AuthorizedContactDTO();
        contact.setFirstName("Anita" + contacts.size());
        contact.setLastName(AccountFactory.ACCOUNT_NAME_LAST);
        contact.setPassword("password");
        contacts.add(contact);
        return new ResultDTO();
    }
}
