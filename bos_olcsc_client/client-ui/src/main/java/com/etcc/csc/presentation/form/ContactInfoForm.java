/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.Address;
import com.etcc.csc.dto.AuthorizedContactDTO;
import com.etcc.csc.dto.BaseContactInfo;
import com.etcc.csc.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/*  Modifications by Idea, for WSR2, Sept, 2008, REM
    1. To support preference "byPhone".
*/

/**
 * Contact information form for collecting user data.
 * Added email for TO488 (OLVPS modifications).
 * @author REM (Idea)
 * @author Milosh Boroyevich
 * @see BaseContactInfo
 */
public class ContactInfoForm extends ValidatorActionForm implements Address {
	private static final long serialVersionUID = 8825112981515088211L;
//	private static final Logger logger = Logger.getLogger(ContactInfoForm.class);

	/**
	 * @author Milosh Boroyevich
	 */
	public enum WhichPage {
		CONTACT("contact-info-page"/*WhichPage.CONTACT_TAG*/),
		CONFIRM("confirm-page"/*WhichPage.CONFIRM_TAG*/),
		UPDATE("update-authorized-contact-page"/*WhichPage.UPDATE_TAG*/),
		INCOMPLETE("incomplete-page"/*WhichPage.INCOMPLETE_TAG*/);

		private static final String CONTACT_TAG = "contact-info-page";
		private static final String CONFIRM_TAG = "confirm-page";
		private static final String UPDATE_TAG = "update-authorized-contact-page";
		private static final String INCOMPLETE_TAG = "incomplete-page";

		private final String tag;

	    private WhichPage(final String tag) {
	        this.tag = tag;
	    }

	    /**
	     * Returns the tag string for this object.
	     * @return the tag string for this object
	     */
		public String getTag() {
			return tag;
		}

	    /**
	     * Returns the enum constant corresponding to the specified tag.
	     * @param tag tag of the enum type
	     * @return the enum constant
	     * @throws IllegalArgumentException if the specified tag has no corresponding enum type
	     * @throws NullPointerException if tag is <tt>null</tt>
	     */
	    public static WhichPage valueOfTag(String tag) throws IllegalArgumentException, NullPointerException {
	        if (tag.length() > 0) {
	        	if (tag.equalsIgnoreCase(CONTACT_TAG))
	        		return CONTACT;
	        	if (tag.equalsIgnoreCase(CONFIRM_TAG))
	        		return CONFIRM;
	        	if (tag.equalsIgnoreCase(UPDATE_TAG))
	        		return UPDATE;
	        	if (tag.equalsIgnoreCase(INCOMPLETE_TAG))
	        		return INCOMPLETE;
	        }
	        throw new IllegalArgumentException("No enum const class for tag: " + tag);
	    }
	}

	private List<AuthorizedContactDTO> authorizedContacts;
    private String accountType;
    private String companyName;
    private String alternatePhone;
    private String altPhoneExt;
    private String taxId;
    private String email;

    private BaseContactInfo contact = new BaseContactInfo();

	private boolean pickup;

    private boolean byMail;
    private boolean byEmail;
    private boolean byPhone;   //To support preference "byPhone".

    private Long retailTransId;
    private boolean fromConfirmation; //used by the create EZ account process. true means coming from the confirmation page
    private WhichPage whichPageEnum = WhichPage.CONTACT;//"contact-info-page", "confirm-page" , "update-authorized-contact-page"
    private String function = "go-to-page"; //add-contact, 
    private int authContactsLimit = 0;

    public ContactInfoForm() {
    }

    @Override
	public void reset(ActionMapping mapping, HttpServletRequest request) { 
        contact.setNonUSAddress(false);
        pickup = false;
        byMail = false;
        byEmail = false;
        byPhone = false;  // To support preference "byPhone".
    }

    /**
     * Initialize this form with data from the specified account DTO.
     * @param acctDto account to use for initialization
     */
    // Consolidated from SignupContactInfoUpdateAction and SignupIncompleteAccountAction
    public ContactInfoForm(AccountDTO acctDto) {
        this();
	    String companyName = acctDto.getCompanyName();
	    this.setAccountType( (companyName==null ? AccountEFTDTO.BankAccountType.PERSONAL : AccountEFTDTO.BankAccountType.BUSINESS).toString().toLowerCase() );
	    this.setFirstName( acctDto.getFirstName() );
	    this.setLastName( acctDto.getLastName() );
	    this.setCompanyName( acctDto.getCompanyName() );
	    this.setPrimaryPhone( acctDto.getHomePhoNbr());  // ?? Not sure this is the right phone number
	    this.setAlternatePhone( acctDto.getWorkPhoNbr() );  // ??
	    this.setAltPhoneExt( acctDto.getWorkPhoExt() );  // ??
	    this.setDriversLicState( acctDto.getDriverLicState() );
	    this.setDriversLic( acctDto.getDriverLicNbr() );
	    this.setTaxId( acctDto.getCompanyTaxId() );
	    this.setAddressLine1( acctDto.getAddress1() );
	    this.setAddressLine2( acctDto.getAddress2() );
	    this.setAddressLine3( acctDto.getAddress3() );
	    this.setAddressLine4( acctDto.getAddress4() );
	    this.setCity( acctDto.getCity() );
	    this.setState( acctDto.getState() );
	    this.setZip( acctDto.getZipCode() );
	    this.setPlus4( acctDto.getPlus4() );
	    this.setCountry( acctDto.getCountry() );
	    this.setNonUSAddress(this.getCountry() != null);
	    this.setByMail( acctDto.isByMail() );
	    this.setByEmail( acctDto.isByEmail() );
	    this.setByPhone( acctDto.isByPhone() );
	    //this.setRetailTransId( dto.getRetailTransId() ); No equivalent in AccountDTO
	    //this.setFromConfirmation( false ); Not setting it results in "false".
    }

    /**
     * Returns <tt>true</tt> if this form contains any authorized contacts.
     * @return <tt>true</tt> if this form contains any authorized contacts
     */
    public boolean hasAuthorizedContacts() {
        return this.authorizedContacts != null && this.authorizedContacts.size() > 0;
    }

    /**
     * Returns the authorized contacts.
     * @return the authorized contacts (never <tt>null</tt>)
     */
    public List<AuthorizedContactDTO> getAuthorizedContacts() {
        if (this.authorizedContacts == null)
            this.authorizedContacts = new ArrayList<AuthorizedContactDTO>();
        return this.authorizedContacts;
    }

    /**
     * Returns the contact at the specified index.
     * @param index index of the contact to return
     * @return the contact at the specified index
     * @see #createAuthorizedContacts(int)
     * @see #getAuthorizedContacts()
     */
    public AuthorizedContactDTO getAuthorizedContact(int index) {
        createAuthorizedContacts(index);
        return getAuthorizedContacts().get(index);
    }

    /**
     * Ensures the size of the contacts list to the specified index.
     * @param index size of list to ensure
     * @see #getAuthorizedContacts()
     */
    private void createAuthorizedContacts(int index) {
        List<AuthorizedContactDTO> authorizedContacts = getAuthorizedContacts();
        int size = authorizedContacts.size();
        for (int i=0; i<index+1-size; i++) {
            authorizedContacts.add(new AuthorizedContactDTO());
        }
    }

    public void setAuthorizedContacts(List<AuthorizedContactDTO> authorizedContacts) {
        this.authorizedContacts = authorizedContacts;
    }

    /**
     * Sets the authorized contacts after converting the specified array to a list.
     * Note: this method does not use <tt>Arrays.asList()</tt> because
     * <tt>add</tt> and <tt>remove</tt> methods need to be called.
     * @param authContacts array of authorized contacts to convert and set
     * @see #setAuthorizedContacts(List)
     */
    public void setAuthorizedContacts(AuthorizedContactDTO[] authContacts) {
        List<AuthorizedContactDTO> acList = null;
        if (authContacts != null) {
            acList = new ArrayList<AuthorizedContactDTO>(authContacts.length);
            for (AuthorizedContactDTO ac : authContacts)
                acList.add(ac);
        }
        setAuthorizedContacts(acList);
    }

    /**
     * Set all of the fields related to authorized contacts.
     * @param authContacts authorized contacts to set
     * @param limit max number of authorized contacts allowed
     * @param page which page
     * @see #setAuthorizedContacts(List)
     * @see #setAuthContactsLimit(int)
     * @see #setWhichPageEnum(WhichPage)
     */
    // Moved from SignupIncompleteAccountAction
    public void setAuthorizedContacts(AuthorizedContactDTO[] authContacts, int limit, WhichPage page) {
    	if (authContacts != null && authContacts.length > 0) {
            if (authContacts.length > limit) {
    	        AuthorizedContactDTO[] copy = new AuthorizedContactDTO[limit];
    	        System.arraycopy(authContacts, 0, copy, 0, limit);
    	        setAuthorizedContacts(copy);
            } else {
                setAuthorizedContacts(authContacts);
            }
    	} else {
    	    this.authorizedContacts = null;
    	}
        this.authContactsLimit = limit;
        this.whichPageEnum = page;
    }

    /**
     * Removes any contacts that have blanks.
     * @see AuthorizedContactDTO#hasBlanks()
     */
    public void removeBlankAuthContacts() {
        List<AuthorizedContactDTO> contacts = getAuthorizedContacts();
        for (int i = 0; i < contacts.size(); i++) {
            AuthorizedContactDTO authContact = contacts.get(i);
            if (authContact.hasBlanks())
                contacts.remove(i);
        }
    }

    /**
     * Returns the underlying contact info.
     * @return the underlying contact info
     */
    public BaseContactInfo getContactInfo() {
        return this.contact;
    }

    /**
     * Set which page using the string tag.
     * This method exists for use by JSPs.
     * @param tag the string tag for the page
     * @deprecated use {@link #setWhichPageEnum(WhichPage)}
     */
    @Deprecated
    public void setWhichPage(String tag) {
        this.whichPageEnum = WhichPage.valueOfTag(tag);
    }

    /**
     * Returns the tag representation of which page.
     * @return the tag representation of which page
     * @see #getWhichPageEnum()
     */
    public String getWhichPage() {
        return whichPageEnum.getTag();
    }

    public String getMaskedDriversLic() {
        return StringUtil.maskAccount(this.contact.getDriverLic(), 3);
    }

    public boolean isNonUSAddress() {
    	return contact.isNonUSAddress();
    }

    public String getName() {
        return contact.getName();
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setFirstName(String firstName) {
        this.contact.setFirstName(firstName);
    }

    public String getFirstName() {
        return contact.getFirstName();
    }

    public void setLastName(String lastName) {
        this.contact.setLastName(lastName);
    }

    public String getLastName() {
        return contact.getLastName();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.contact.setPrimaryPhone(primaryPhone);
    }

    public String getPrimaryPhone() {
        return contact.getPrimaryPhone();
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setDriversLicState(String driverLicState) {
        this.contact.setDriverLicState(driverLicState);
    }

    public String getDriversLicState() {
        return contact.getDriverLicState();
    }

    public void setDriversLic(String driverLic) {
        this.contact.setDriverLic(driverLic);
    }

    public String getDriversLic() {
        return contact.getDriverLic();
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setNonUSAddress(boolean foreignAddress) {
        this.contact.setNonUSAddress(foreignAddress);
    }

    public void setAddressLine1(String addressLine1) {
        this.contact.setAddress1(addressLine1);
    }

    public String getAddressLine1() {
        return contact.getAddress1();
    }

    public void setAddressLine2(String addressLine2) {
        this.contact.setAddress2(addressLine2);
    }

    public String getAddressLine2() {
        return contact.getAddress2();
    }

    public void setAddressLine3(String addressLine3) {
        this.contact.setAddress3(addressLine3);
    }

    public String getAddressLine3() {
        return contact.getAddress3();
    }

    public void setAddressLine4(String addressLine4) {
        this.contact.setAddress4(addressLine4);
    }

    public String getAddressLine4() {
        return contact.getAddress4();
    }

    public void setCountry(String country) {
        this.contact.setCountry(country);
    }

    public String getCountry() {
        return contact.getCountry();
    }

    public void setCity(String city) {
        this.contact.setCity(city);
    }

    public String getCity() {
        return contact.getCity();
    }

    public void setState(String state) {
        this.contact.setState(state);
    }

    public String getState() {
        return contact.getState();
    }

    public void setZip(String zip) {
        this.contact.setZip(zip);
    }

    public String getZip() {
        return contact.getZip();
    }

    public void setPickup(boolean forPickUp) {
        this.pickup = forPickUp;
    }

    public boolean isPickup() {
        return pickup;
    }

    public void setByMail(boolean byMail) {
        this.byMail = byMail;
    }

    public boolean isByMail() {
        return byMail;
    }

    public void setByEmail(boolean byEmail) {
        this.byEmail = byEmail;
    }

    public boolean isByEmail() {
        return byEmail;
    }
    
    // Following 2 methods added by Idea to support preference "byPhone".
    public void setByPhone(boolean byPhone) {
        this.byPhone = byPhone;
    }

    public boolean isByPhone() {
        return byPhone;
    }

    public void setRetailTransId(Long retailTransId) {
        this.retailTransId = retailTransId;
    }

    public Long getRetailTransId() {
        return retailTransId;
    }

    public void setFromConfirmation(boolean refresh) {
        this.fromConfirmation = refresh;
    }

    public boolean isFromConfirmation() {
        return fromConfirmation;
    }

    public void setAltPhoneExt(String altPhoneExt) {
        this.altPhoneExt = altPhoneExt;
    }

    public String getAltPhoneExt() {
        return altPhoneExt;
    }

    public void setPlus4(String plus4) {
        this.contact.setPlus4(plus4);
    }

    public String getPlus4() {
        return contact.getPlus4();
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    public void setAuthContactsLimit(int authContactsLimit) {
        this.authContactsLimit = authContactsLimit;
    }

    public int getAuthContactsLimit() {
        return authContactsLimit;
    }

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public WhichPage getWhichPageEnum() {
		return whichPageEnum;
	}

	public void setWhichPageEnum(WhichPage whichPageEnum) {
		this.whichPageEnum = whichPageEnum;
	}

	public String getAddress1() {
        return contact.getAddress1();
	}
	public String getAddress2() {
        return contact.getAddress2();
	}
	public String getAddress3() {
        return contact.getAddress3();
	}
	public String getAddress4() {
        return contact.getAddress4();
	}
	public void setAddress1(String address1) {
        this.contact.setAddress1(address1);
	}
	public void setAddress2(String address2) {
        this.contact.setAddress2(address2);
	}
	public void setAddress3(String address3) {
        this.contact.setAddress3(address3);
	}
	public void setAddress4(String address4) {
        this.contact.setAddress4(address4);
	}
}
