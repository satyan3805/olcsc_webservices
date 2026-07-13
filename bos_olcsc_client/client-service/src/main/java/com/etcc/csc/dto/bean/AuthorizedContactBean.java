package com.etcc.csc.dto.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.etcc.csc.validation.ValidationException;

public class AuthorizedContactBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2565162503043885909L;
    private String firstName;
    private String lastName;
    private String password;

    public AuthorizedContactBean() {
        setFirstName("");
        setLastName("");
        setPassword("");
    }

    public void setFirstName(String aFirstName) {
        this.firstName = aFirstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String aLastName) {
        this.lastName = aLastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setPassword(String aPassword) {
        this.password = aPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isEmpty() {
        return (isEmpty(getFirstName()) && isEmpty(getLastName()) && isEmpty(getPassword()));
    }

    public boolean isValid() {
        return ((isEmpty(getFirstName()) == false) && (isEmpty(getLastName()) == false) && (isEmpty(getPassword()) == false));
    }

    private static boolean isEmpty(String s) {
        return ((s == null) || s.equals(""));
    }

    public static String print(List<AuthorizedContactBean> authorizedContacts) {
        if (authorizedContacts == null) {
            return "null";
        } // else {
        StringBuilder printOut = new StringBuilder();
        for (Iterator<AuthorizedContactBean> it = authorizedContacts.iterator(); it.hasNext();) {
            AuthorizedContactBean anAuthorizedContact = it.next();
            String aRecord = "null";
            if (anAuthorizedContact != null) {
                aRecord = "firstName=" + anAuthorizedContact.getFirstName() + ", lastName="
                        + anAuthorizedContact.getLastName() + ", password=" + anAuthorizedContact.getPassword() + "\n";
            }
            printOut.append(aRecord);
        }
        return printOut.toString();

    }

    public static void removeEmptyContacts(List<AuthorizedContactBean> authorizedContacts) {
        if (authorizedContacts == null)
            return;
        ListIterator<AuthorizedContactBean> authorizedContactsIterator = authorizedContacts.listIterator();
        while (authorizedContactsIterator.hasNext()) {
            AuthorizedContactBean aBean = authorizedContactsIterator.next();
            if ((aBean == null) || aBean.isEmpty())
                authorizedContactsIterator.remove();
        }
    }

    /**
     * Verifies if the info for the authorized contacts is valid enough to send to the database.
     * @param authorizedContacts The Contacts to verify.
     * @throws ValidationException thrown if Validation fails for any reason.
     */
    public static void validate(List<AuthorizedContactBean> authorizedContacts) throws ValidationException {
        if (authorizedContacts == null)
            throw new ValidationException("Authorized Contacts do not exist");
        final Collection<String> messages = new ArrayList<String>();
        for (int i = 0; i < authorizedContacts.size(); i++) {
            AuthorizedContactBean authorizedContactBean = authorizedContacts.get(i);
            if (authorizedContactBean == null) {
                messages.add("Authorized Contact at Position " + i + " does not exist");
            } else if (authorizedContactBean.isEmpty()) {
                messages.add("Authorized Contact at Position " + i + " is empty");
            } else {
                Collection<String> validationMessages = authorizedContactBean.validate();
                if (validationMessages.size() > 0){
                    messages.add("Authorized Contact at Position " + i + " is invalid. ");
                    messages.addAll(validationMessages);
                }
            }
        }//end for
        if (messages.size()>0){
            throw new ValidationException(messages);
        }
    }

    private Collection<String> validate() {
        String[] messages = new String[]{
                validateFirstName(),
                validateLastName(),
                validatePassword()
        };
        Collection<String> errors = new ArrayList<String>();
        for (String msg : messages) {
            if (msg != null){
                errors.add(msg);
            }
        }
        return errors;
    }

    private final static String NAME_PATTERN = "^[0-9a-zA-Z,.'\\s-]*$";
    private final static String PASSWORD_PATTERN =  "^[0-9a-zA-Z._@-]*$";
    
    private String validateFirstName(){
        return validate("First Name", getFirstName(), 1, 80, NAME_PATTERN, true);
    }

    private String validateLastName(){
        return validate("Last Name", getLastName(), 1, 80, NAME_PATTERN, true);
    }

    private String validatePassword() {
        // minimum of eight (8) and maximum of twelve (12) alpha-numeric characters;
        return validate("Password", getPassword(), 8, 12, PASSWORD_PATTERN, false);
    }

    private static String validate(String fieldName, String fieldValue, int minLength, int maxLength, String pattern, boolean displayValue) {
        if (fieldValue == null || fieldValue.equals(""))
            return fieldName + " is empty";
        if (fieldValue.matches("^[\\s]+.*$"))
            return fieldName + '\'' + 
            (displayValue ? fieldValue : "********") + 
            "' cannot start with white space";
        if (fieldValue.length() < minLength)
            return fieldName + '\'' + 
            (displayValue ? fieldValue : "********") + 
             "' name is too short (min = " + minLength + " characters)";
        if (fieldValue.length() > maxLength)
            return fieldName + '\''+ 
            (displayValue ? fieldValue : "********") + 
             "' name is too long (max = " + maxLength + " characters)";
        if (fieldValue.matches(pattern) == false)
            return fieldName + '\'' + 
            (displayValue ? fieldValue : "********") + 
             "' contains invalid characters";
        //else made it through the "gauntlet".
        return null;
    }

}
