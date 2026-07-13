/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.ValidatorActionForm;

import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.Address;
import com.etcc.csc.dto.AddressUS;
import com.etcc.csc.dto.BillingInfo;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.presentation.action.signup.SignupBillingPANAction;
import com.etcc.csc.util.DtoUtil;
import com.etcc.csc.util.FormatUtil;
import com.etcc.csc.util.StringUtil;

public class BillingInfoForm extends ValidatorActionForm implements BillingInfo, Address {
	private static final long serialVersionUID = -871343439961528187L;
	private static final Logger logger = Logger.getLogger(BillingInfoForm.class);

	private PaymentType paymentTypeEnum; //credit/eft.
    private String nameOnCard;
    private CreditCardDTO.CreditCardType cardTypeEnum;
    private String cardNumber;
    private String cardExpirationMonth;
    private String cardExpirationYear;

    private boolean reserveCard;
    private String nameOnReserveCard;
    private CreditCardDTO.CreditCardType reserveCardTypeEnum;
    private String reserveCardNumber;
    private String reserveCardExpirationMonth;
    private String reserveCardExpirationYear;

    private boolean diffBillingAddress;
    private boolean nonUsBillingAddress;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String plus4;
    private String address3;
    private String address4;
    private String country;
    protected String paypageRegistrationId;

    private boolean mailMonthlyStatement;

    private AccountEFTDTO.BankAccountType bankAcctTypeEnum;
    private String bankRoutingNumber;
    private String bankAcctNumber;

    private String email;
    private boolean agreementAccepted;
    /** Specifies whether the account closure flow has been initiated. */
    private boolean accountClosure;

    /**
     * <tt>true</tt> if the validate method on this form completed without errors.
     * @see #validate(ActionMapping, HttpServletRequest)
     */
    private boolean valid = false;


    public BillingInfoForm() {
    }

    public PaymentType getBillingType() {
        return this.paymentTypeEnum;
    }

    public AccountCreditCardDTO[] getCards() {
        if (this.paymentTypeEnum == PaymentType.CREDIT) {
            int cardCount = 1;
            if (this.reserveCard)
                cardCount = 2;
            AccountCreditCardDTO[] cards = new AccountCreditCardDTO[cardCount];
            // primary card
            cards[0] = new AccountCreditCardDTO();
            cards[0].setPrimary(true);
            cards[0].setNameOnCard(this.nameOnCard);
            cards[0].setCardType(this.cardTypeEnum);
            cards[0].setCardNbr(this.cardNumber);
            cards[0].setCardExpires(this.cardExpirationMonth + '/' + this.cardExpirationYear);
            if (this.reserveCard) {
                // reserve card
                cards[1] = new AccountCreditCardDTO();
                cards[1].setPrimary(false);
                cards[1].setNameOnCard(this.nameOnReserveCard);
                cards[1].setCardType(this.reserveCardTypeEnum);
                cards[1].setCardNbr(this.reserveCardNumber);
                cards[1].setCardExpires(this.reserveCardExpirationMonth + '/' + this.reserveCardExpirationYear);
            }
            return cards;
        }
        return null;
    }

    public AccountEFTDTO getEft() {
        if (this.paymentTypeEnum == PaymentType.EFT) {
            AccountEFTDTO eft = new AccountEFTDTO();
            eft.setAccountType(this.bankAcctTypeEnum);
            eft.setRoutingNumber(this.bankRoutingNumber);
            eft.setAccountNumber(this.bankAcctNumber);
            return eft;
        }
        return null;
    }

    public void setPaymentType(String paymentCode) {
        this.paymentTypeEnum = PaymentType.valueOfCode(paymentCode);
    }

    public String getPaymentType() {
        return (paymentTypeEnum == null ? null : paymentTypeEnum.toString().toLowerCase());
    }

    public void setCardType(String cardCode) {
        this.cardTypeEnum = CreditCardDTO.CreditCardType.valueOfCode(cardCode);
    }

    public String getCardType() {
        return (cardTypeEnum == null ? "" : Character.toString(cardTypeEnum.getCode()));
    }

    public void setReserveCardType(String reserveCardCode) {
        this.reserveCardTypeEnum = CreditCardDTO.CreditCardType.valueOfCode(reserveCardCode);
    }

    public String getReserveCardType() {
        return (reserveCardTypeEnum == null ? "" : Character.toString(reserveCardTypeEnum.getCode()));
    }

    public void setBankAcctType(String accountType) {
        this.bankAcctTypeEnum = AccountEFTDTO.BankAccountType.valueOfCode(accountType);
    }

    public String getBankAcctType() {
        return (bankAcctTypeEnum == null ? null : bankAcctTypeEnum.getCode());
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        diffBillingAddress = false;
        nonUsBillingAddress = false;
        reserveCard = false;
        mailMonthlyStatement = false;
        agreementAccepted = false;
    }

    public String getName() {
        return this.nameOnCard;
    }

    /**
     * Equivalent to JSP tag:
     * &lt;etcc-extended:format address="${billingInfoForm}" displayName="true" &frasl;&gt;
     * @deprecated use custom JSP <tt>FormatTag</tt>
     * @see com.etcc.csc.presentation.taglib.FormatTag
     * @see FormatUtil#formatAddress(Address, String, boolean)
     */
    @Deprecated
    public String getFormattedAddress() {
        return FormatUtil.formatAddress(this);
    }

    /**
     * Equivalent to JSP tag:
     * &lt;etcc-extended:format billingInfo="${billingInfoForm}" mask="ACCOUNT" primaryOnly="true" displayName="false" &frasl;&gt;
     * @deprecated use custom JSP <tt>FormatTag</tt>
     * @see com.etcc.csc.presentation.taglib.FormatTag
     * @see FormatUtil#formatPaymentMethod(BillingInfo, String, FormatUtil.MaskValue, boolean, boolean)
     */
    @Deprecated
    public String getFormattedPaymentMethod() {
        return FormatUtil.formatPaymentMethod(this, false);
    }

    @Deprecated
    public String getFormattedPaymentMethodWCCMask() {
        return FormatUtil.formatPaymentMethod(this, true);
    }

    @Deprecated
    public String getFormattedBankAcctNumber() {
    	return StringUtil.maskAccount(bankAcctNumber);
    }

    @Deprecated
    public String getMaskedCardNumber() {
    	return StringUtil.maskAccount(this.cardNumber);
    }

    /*
    public double getAcctSetupFee() {
        if (paymentType.equalsIgnoreCase(BillingInfoDTO.PAYMENT_TYPE_CREDIT))
            return 40.00;
        else if (paymentType.equalsIgnoreCase(BillingInfoDTO.PAYMENT_TYPE_EFT))
            return 80.00;
        else
            return 0.00;
    }
    */

    /**
     * Initialize this form with data from the specified billing DTO.
     * @param billingInfoDto billing info to use for initialization
     * @see #BillingInfoForm(BillingInfoDTO, AccountDTO)
     */
    public BillingInfoForm(BillingInfoDTO billingInfoDto) {
        this(billingInfoDto, null);
    }

    /**
     * Initialize this form with data from the specified account and billing DTOs.
     * Account is used to determine whether the billing address is different
     * from the account address and for setting the e-mail on the form.
     * @param billingInfoDto billing info to use for initialization
     * @param acctDto account to use for initialization (can be <tt>null</tt>)
     */
    // Consolidated from SignupContactInfoUpdateAction, SignupIncompleteAccountAction, and BillingInfoViewHelper
    public BillingInfoForm(BillingInfoDTO billingInfoDto, AccountDTO acctDto) {
        this();
    	PaymentType billingType = billingInfoDto.getBillingType();
    	if (billingType != null) {
    		this.setPaymentTypeEnum(billingType);
    		switch (billingType) {
    		case CREDIT:
    			for (AccountCreditCardDTO card : billingInfoDto.getCards()) {
					String cardExpires = card.getCardExpires();
					//logger.debug ("**************===========> CardExpires = " + cardExpires);
					String expirationMonth = cardExpires.substring(0,2);
					String expirationYear = cardExpires.substring(3);
    				if (card.isPrimary()) {
    					this.setNameOnCard( card.getNameOnCard() );
    					this.setCardTypeEnum( card.getCardType() );
    					this.setCardNumber( card.getCardNbr() );
    					this.setCardExpirationMonth( expirationMonth );
    					//logger.debug ("**************===========> CardExpiresMo = " + this.getCardExpirationMonth());
    					this.setCardExpirationYear( expirationYear );
    					this.setAddressLine1( card.getAddress1() );
    					this.setAddressLine2( card.getAddress2() );
    					this.setAddressLine3( card.getAddress3() );
    					this.setAddressLine4( card.getAddress4() );
    					this.setCity( card.getCity() );
    					this.setState( card.getState() );
    					this.setZip( card.getZipCode() );
    					this.setPlus4( card.getPlus4() );
    					String cardCountry = card.getCountry();
                        if (cardCountry == null || cardCountry.equalsIgnoreCase(AddressUS.COUNTRY_CODE_USA)) {
                            this.country = AddressUS.COUNTRY_CODE_USA;
                            this.setNonUsBillingAddress(false);
                        } else {
                        	this.country = cardCountry;
                            this.setNonUsBillingAddress(true);
                        }
    					this.setDiffBillingAddress(!card.sameAddress(acctDto));
    				}
    			}
    			break;
    		case EFT:
    			AccountEFTDTO eftDto = billingInfoDto.getEft();
    			this.setBankAcctTypeEnum( eftDto.getAccountType() );
    			logger.debug ("**************===========> Eft Bank Account Type = " + this.getBankAcctType());
    			this.setBankRoutingNumber( eftDto.getRoutingNumber() );
                this.setBankAcctNumber(eftDto.getAccountNumber());
    			this.setAddressLine1( eftDto.getAddress1() );
    			this.setAddressLine2( eftDto.getAddress2() );
    			this.setAddressLine3( eftDto.getAddress3() );
    			this.setAddressLine4( eftDto.getAddress4() );
    			this.setCity( eftDto.getCity() );
    			this.setState( eftDto.getState() );
    			this.setZip( eftDto.getZipCode() );
    			this.setPlus4( eftDto.getPlus4() );
    			this.setCountry( eftDto.getCountry() );
    			break;
    		}
            if (country == null) {
                country = AddressUS.COUNTRY_CODE_USA;
            }
    		// Chase these down later.
    		//this.setMailMonthlyStatement( dto.is() );
            if (acctDto != null)
            	this.setEmail( acctDto.getEmailAddress() );
    		//this.setAgreementAccepted( dto.is() );

    	}
    }

    /**
     * Create a new billing info DTO from this billing form and the specified contact form.
     * @param contactForm used to create the billing info DTO
     * @return new billing info DTO
     */
    public BillingInfoDTO newBillingInfoDTO(ContactInfoForm contactForm) {
    	return newBillingInfoDTO(contactForm, null);
    }

    /**
     * Create a new billing info DTO from this billing form and the specified account.
     * @param accountDto used to create the billing info DTO
     * @return new billing info DTO
     */
    public BillingInfoDTO newBillingInfoDTO(AccountDTO accountDto) {
    	return newBillingInfoDTO(null, accountDto);
    }

    /**
     * Create a new billing info DTO from this form and the specified contact form.
     * @param contactForm to use for creating the billing info DTO
     * @return new billing info DTO
     */
    // Consolidated from BillingInfoViewHelper and AccInfoChgPaymentMethodAction
    private BillingInfoDTO newBillingInfoDTO(ContactInfoForm contactForm, AccountDTO accountDto) {
    	BillingInfoDTO billingInfo = new BillingInfoDTO();
    	billingInfo.setBillingType(this.paymentTypeEnum);
    	billingInfo.setDiffBillingAddress(this.diffBillingAddress);
    	//        TagRequestForm tagRequestForm = null;
    	if (contactForm != null) {
    		//tagRequestForm =(TagRequestForm)request.getSession().getAttribute("tagRequestForm");
    		//             BillingContext billingContext = (BillingContext)request.getSession().getAttribute("billingContext");
    		//logger.debug("TagRequestForm" + tagRequestForm);
    		long transId = -1;
    		if (contactForm.getRetailTransId() != null) {
    			transId = contactForm.getRetailTransId().longValue();
    		} else {
    			// transId = billingContext.getTransactionId();
    			//AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
    			//transId = loginDTO.
    		}
    		logger.debug(" BillingInfoViewHelper : transId:"+ transId);
    		billingInfo.setTransactionId(transId);
    	}
    	if (this.paymentTypeEnum == PaymentType.CREDIT) {
    		Collection<AccountCreditCardDTO> cards = new ArrayList<AccountCreditCardDTO>();
    		AccountCreditCardDTO primaryCard = new AccountCreditCardDTO();
    		primaryCard.setPrimary(true);
    		primaryCard.setNameOnCard(this.getNameOnCard());
    		primaryCard.setCardType(this.getCardTypeEnum());
    		primaryCard.setPaypageRegistrationId(this.getPaypageRegistrationId());
    		primaryCard.setCardNbr(StringUtil.maskCCNumber(this.getCardNumber()));
    		primaryCard.setCardExpires(this.getCardExpirationMonth() + "/"
    				+ this.getCardExpirationYear());
    		/*             String country = billingInfo.getNonUsBillingAddress() ? billingInfo.getCountry() : AddressUS.COUNTRY_CODE_USA;
            if (billingInfo.getDiffBillingAddress()) {
                card.setAddress1(billingInfo.getAddressLine1());
                card.setAddress2(billingInfo.getAddressLine2());
                card.setAddress3(billingInfo.getAddressLine3());
                card.setAddress4(billingInfo.getAddressLine4());
                card.setCity(billingInfo.getCity());
                card.setState(billingInfo.getState());
                card.setZipCode(billingInfo.getZip());
                card.setPlus4(billingInfo.getPlus4());
                card.setCountry(country);
            } else {
                card.setAddress1(accountDTO.getAddress1());
                card.setAddress2(accountDTO.getAddress2());
                card.setAddress3(accountDTO.getAddress3());
                card.setAddress4(accountDTO.getAddress4());
                card.setCity(accountDTO.getCity());
                card.setState(accountDTO.getState());
                card.setZipCode(accountDTO.getZipCode());
                card.setPlus4(accountDTO.getPlus4());
                card.setCountry(accountDTO.getCountry());
            }*/
    		cards.add(populateCardAddress(primaryCard, contactForm, accountDto));

    		/**
    		if (isReserveCard()) {
    			AccountCreditCardDTO reserveCard = new AccountCreditCardDTO();
    			reserveCard.setPrimary(false);
    			reserveCard.setNameOnCard(this.getNameOnReserveCard());
    			reserveCard.setCardType(this.getReserveCardTypeEnum());
    			reserveCard.setCardNbr(this.getReserveCardNumber());
    			reserveCard.setCardExpires(this.getReserveCardExpirationMonth() + "/"
    					+ this.getReserveCardExpirationYear());
    			cards.add(populateCardAddress(reserveCard, contactForm, accountDto));
    		}
    		*/
    		billingInfo.setCards(cards);
    	}
    	else {
    		AccountEFTDTO eft = new AccountEFTDTO();
    		eft.setAccountType(this.getBankAcctTypeEnum());
    		eft.setRoutingNumber(this.getBankRoutingNumber());
    		eft.setAccountNumber(this.getBankAcctNumber());
    		billingInfo.setEft(populateEFTAddress(eft, contactForm, accountDto));    		
    	}
    	return billingInfo;
    }


    public AccountCreditCardDTO createAccountCreditCardDTO(AccountDTO accountDto)
    {
    	AccountCreditCardDTO card = new AccountCreditCardDTO();
    	card.setNameOnCard(this.getNameOnCard());
    	card.setCardType(this.getCardTypeEnum());
    	card.setCardNbr(StringUtil.maskCCNumber(this.getCardNumber()));
    	card.setCardExpires(this.getCardExpirationMonth() + "/" + this.getCardExpirationYear());
    	return populateCardAddress(card, null, accountDto);
    }

    /**
     * Populate the specified card from the provided objects.  At least one of the provided objects must be non-null.
     * @param card credit card to populate
     * @param billingForm
     * @param contactForm
     * @param account
     * @return a reference to this specified card
     * @throws NullPointerException if <i>all</i> parameters are <tt>null</tt>
     */
    // Consolidated from BillingInfoViewHelper and AccInfoChgPaymentMethodAction
    private AccountCreditCardDTO populateCardAddress(AccountCreditCardDTO card, ContactInfoForm contactForm, AccountDTO account) {
    	BillingInfoForm billingForm = this;
    	boolean diffAddress = billingForm.isDiffBillingAddress();
    	if (contactForm != null && contactForm.isNonUSAddress()) {
    		logger.trace("This is contact Form NON-US Address");
    		card.setAddress1(contactForm.getAddressLine1());
    		card.setAddress2(contactForm.getAddressLine2());
    		card.setAddress3(contactForm.getAddressLine3());
    		card.setAddress4(contactForm.getAddressLine4());
    		card.setCountry(contactForm.getCountry());
    	} else if (contactForm != null && contactForm.getAddressLine1() != null) {
    		if (logger.isTraceEnabled())
    			logger.trace("This is contact Form US Address" + contactForm.getAddressLine1() + " " + contactForm.getCity() );
    		card.setAddress1(contactForm.getAddressLine1());
    		card.setAddress2(contactForm.getAddressLine2());
    		card.setCity(contactForm.getCity());
    		card.setState(contactForm.getState());
    		card.setZipCode(contactForm.getZip());
    		card.setPlus4(contactForm.getPlus4());
    		card.setCountry(AddressUS.COUNTRY_CODE_USA);

    	} else if (account != null && !diffAddress) {
    		card.setAddress1(account.getAddress1());
    		card.setAddress2(account.getAddress2());
    		card.setAddress3(account.getAddress3());
    		card.setAddress4(account.getAddress4());
    		card.setCity(account.getCity());
    		card.setState(account.getState());
    		card.setZipCode(account.getZipCode());
    		card.setPlus4(account.getPlus4());
    		card.setCountry(account.getCountry());
    	} else {
    		if (!billingForm.isNonUsBillingAddress()) {
    			logger.trace("This is billing Form US address");
    			card.setAddress1(billingForm.getAddressLine1());
    			card.setAddress2(billingForm.getAddressLine2());
    			card.setCity(billingForm.getCity());
    			card.setState(billingForm.getState());
    			card.setZipCode(billingForm.getZip());
    			card.setPlus4(billingForm.getPlus4());
    			card.setCountry(AddressUS.COUNTRY_CODE_USA);
    		} else {
    			logger.trace("This is billing Form different address");
    			card.setAddress1(billingForm.getAddressLine1());
    			card.setAddress2(billingForm.getAddressLine2());
    			card.setAddress3(billingForm.getAddressLine3());
    			card.setAddress4(billingForm.getAddressLine4());
    			card.setCountry(billingForm.getCountry());
    		}
    	}

    	if (diffAddress && !billingForm.isNonUsBillingAddress())
    	{
    		logger.trace("This is Billing form Address and US address");
    		card.setAddress1(billingForm.getAddressLine1());
			card.setAddress2(billingForm.getAddressLine2());
			card.setCity(billingForm.getCity());
			card.setState(billingForm.getState());
			card.setZipCode(billingForm.getZip());
			card.setPlus4(billingForm.getPlus4());
			card.setCountry(AddressUS.COUNTRY_CODE_USA);

    	}

    	if (diffAddress && billingForm.isNonUsBillingAddress())
    	{
    		logger.trace("This is billing Form Non US address");
			card.setAddress1(billingForm.getAddressLine1());
			card.setAddress2(billingForm.getAddressLine2());
			card.setAddress3(billingForm.getAddressLine3());
			card.setAddress4(billingForm.getAddressLine4());
			card.setCity("");
			card.setState("");
			card.setZipCode("");
			card.setPlus4("");
			card.setCountry(billingForm.getCountry());

    	}

    	return card;
    }
    
    
    private AccountEFTDTO populateEFTAddress(AccountEFTDTO eft, ContactInfoForm contactForm, AccountDTO account) {
    	BillingInfoForm billingForm = this;
    	boolean diffAddress = billingForm.isDiffBillingAddress();
    	if (contactForm != null && contactForm.isNonUSAddress()) {
    		logger.trace("This is contact Form NON-US Address");
    		eft.setAddress1(contactForm.getAddressLine1());
    		eft.setAddress2(contactForm.getAddressLine2());
    		eft.setAddress3(contactForm.getAddressLine3());
    		eft.setAddress4(contactForm.getAddressLine4());
    		eft.setCountry(contactForm.getCountry());
    	} else if (contactForm != null && contactForm.getAddressLine1() != null) {
    		if (logger.isTraceEnabled())
    			logger.trace("This is contact Form US Address" + contactForm.getAddressLine1() + " " + contactForm.getCity() );
    		eft.setAddress1(contactForm.getAddressLine1());
    		eft.setAddress2(contactForm.getAddressLine2());
    		eft.setCity(contactForm.getCity());
    		eft.setState(contactForm.getState());
    		eft.setZipCode(contactForm.getZip());
    		eft.setPlus4(contactForm.getPlus4());
    		eft.setCountry(AddressUS.COUNTRY_CODE_USA);

    	} else if (account != null && !diffAddress) {
    		eft.setAddress1(account.getAddress1());
    		eft.setAddress2(account.getAddress2());
    		eft.setAddress3(account.getAddress3());
    		eft.setAddress4(account.getAddress4());
    		eft.setCity(account.getCity());
    		eft.setState(account.getState());
    		eft.setZipCode(account.getZipCode());
    		eft.setPlus4(account.getPlus4());
    		eft.setCountry(account.getCountry());
    	} else {
    		if (!billingForm.isNonUsBillingAddress()) {
    			logger.trace("This is billing Form US address");
    			eft.setAddress1(billingForm.getAddressLine1());
    			eft.setAddress2(billingForm.getAddressLine2());
    			eft.setCity(billingForm.getCity());
    			eft.setState(billingForm.getState());
    			eft.setZipCode(billingForm.getZip());
    			eft.setPlus4(billingForm.getPlus4());
    			eft.setCountry(AddressUS.COUNTRY_CODE_USA);
    		} else {
    			logger.trace("This is billing Form different address");
    			eft.setAddress1(billingForm.getAddressLine1());
    			eft.setAddress2(billingForm.getAddressLine2());
    			eft.setAddress3(billingForm.getAddressLine3());
    			eft.setAddress4(billingForm.getAddressLine4());
    			eft.setCountry(billingForm.getCountry());
    		}
    	}

    	if (diffAddress && !billingForm.isNonUsBillingAddress())
    	{
    		logger.trace("This is Billing form Address and US address");
    		eft.setAddress1(billingForm.getAddressLine1());
			eft.setAddress2(billingForm.getAddressLine2());
			eft.setCity(billingForm.getCity());
			eft.setState(billingForm.getState());
			eft.setZipCode(billingForm.getZip());
			eft.setPlus4(billingForm.getPlus4());
			eft.setCountry(AddressUS.COUNTRY_CODE_USA);

    	}

    	if (diffAddress && billingForm.isNonUsBillingAddress())
    	{
    		logger.trace("This is billing Form Non US address");
			eft.setAddress1(billingForm.getAddressLine1());
			eft.setAddress2(billingForm.getAddressLine2());
			eft.setAddress3(billingForm.getAddressLine3());
			eft.setAddress4(billingForm.getAddressLine4());
			eft.setCity("");
			eft.setState("");
			eft.setZipCode("");
			eft.setPlus4("");
			eft.setCountry(billingForm.getCountry());

    	}
    	return eft;
    }
    
    
    
    

    /**
     * Clean up the data fields in this form object.
     * Set to <tt>null</tt> bank fields if credit card type and card fields
     * if EFT.  Also clear extraneous address fields based on address type.
     * @return this billing info form
     */
    // moved from PaymentBillingInfoDisplayAction
    public BillingInfoForm cleanupBillingInfo() {
        if (paymentTypeEnum != null) {
            switch (paymentTypeEnum) {
            case CREDIT:
                bankAcctTypeEnum = null;
                bankAcctNumber = null;
                bankRoutingNumber = null;
                break;
            case EFT:
                nameOnCard = null;
                cardTypeEnum = null;
                cardNumber = null;
                cardExpirationMonth = null;
                cardExpirationYear = null;
                break;
            }
        }

    	if (nonUsBillingAddress) {
    		city = null;
    		state = null;
    		zip = null;
    	} else {
    		country = AddressUS.COUNTRY_CODE_USA;
    		address3 = null;
    		address4 = null;
    	}
    	return this;
    }

    public String getCountry() {
        if (this.country == null || !this.nonUsBillingAddress)
            return AddressUS.COUNTRY_CODE_USA;
        return country;
    }

	/**
     * Convert the address on this object to upper case.
     */
    // moved from PaymentConfirmationDisplayAction
	public BillingInfoForm upperCaseAddress() {
	    if (address1 != null)
	        address1 = address1.toUpperCase();
	    if (address2 != null)
	        address2 = address2.toUpperCase();
	    if (address3 != null)
	        address3 = address3.toUpperCase();
	    if (address4 != null)
	        address4 = address4.toUpperCase();
	    if (city != null)
	        city = city.toUpperCase();
	    if (country != null)
	        country = country.toUpperCase();
	    return this;
	}

	/**
	 * @see #validate(Collection, boolean)
	 * @see #isValid()
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors result = super.validate(mapping, request);
		ErrorMessageDTO[] errors = this.validate(null, false);
		boolean messagesPresent = (errors != null);
		if (result == null && messagesPresent)
			result = new ActionErrors();
		if (messagesPresent)
			result.add(DtoUtil.createActionMessages("violationError", errors));

		String dispatchAction = request.getParameter("dispatchAction");

		BillingInfoForm form = (BillingInfoForm) request.getSession().getAttribute(mapping.getName());
	    if (request.getRequestURI().endsWith("/signupBillingInfo.do") && form.getPaymentTypeEnum() == PaymentType.CREDIT)
	    {
	    	BillingInfoDTO billingInfoDTO = (BillingInfoDTO) request.getSession().getAttribute("billingInfoDTO");
	    	if (billingInfoDTO == null)
	    	{
	    		result = new ActionErrors();
	    		ActionMessages messages = new ActionMessages();
	    		messages.add("violationError", new ActionMessage("billingInfoForm.requireBillingMethod"));
				result.add(messages );
	    	}
	    	else
	    	{
	    		result = null;
	    	}
	    }
	    else if (request.getRequestURI().endsWith("/signupBillingPAN.do") && SignupBillingPANAction.CHANGE_DEFAULT_BILLING.equals(dispatchAction))
	    {
	    	result = null;
	    }
	    if (logger.isTraceEnabled()) logger.trace("ValidateErros:" + result);
	    this.valid = (result != null && !result.isEmpty());
	    return result;

	}

	/**
	 * Only validate the basic payment types of credit card or EFT.
	 * Sets <tt>valid</tt> to <tt>false</tt> on error; doesn't change value if no errors.
	 * @param cardTypes collection of valid credit cards
	 * @param validateNull if <tt>false</tt> a validation message will <i>not</i> be generated for <tt>null</tt> payment type
	 * @return <tt>null</tt> if no errors are found; otherwise the errors
	 * @see #validate(ActionMapping, HttpServletRequest)
	 * @see #isValid()
	 */
    // modified from original validate method in ViolationPaymentMethodAction
	public ErrorMessageDTO[] validate(Collection<CreditCardDTO> cardTypes, boolean validateNull) {
		ErrorMessageDTO msg = null;
		final String key = "error.generic";
	    if (paymentTypeEnum != null) {
	    	switch (paymentTypeEnum) {
	    	case EFT:
	    		if (bankAcctTypeEnum == null
	    				|| StringUtil.isEmpty(bankAcctNumber)
	    				|| StringUtil.isEmpty(bankRoutingNumber))
	    			msg = new ErrorMessageDTO(key, "Invalid account");
	    		break;
	    	case CREDIT:
	    		// check for accepted card types if present
	    		if (cardTypes != null && !cardTypes.isEmpty()) {
	    			boolean found = false;
	    			for (CreditCardDTO ccDto : cardTypes) {
	    				if (ccDto.getCardType() == cardTypeEnum) {
	    					found = true;
	    					break;
	    				}
	    			}
	    			if (!found)
	    				msg = new ErrorMessageDTO(key, "Invalid credit card type");
	    		}
	            break;
	    	}
	    } else {
	    	// not valid
	    	if (validateNull)
	    		msg = new ErrorMessageDTO(key, "Invalid payment type");
	    }
	    ErrorMessageDTO[] errors = null;
	    if (msg != null) {
	    	// not valid
	    	this.valid = false;
	    	errors = new ErrorMessageDTO[] {msg};
	    }
	    return errors;
	}

    public void setNonUsBillingAddress(boolean nonUsBillingAddress) {
        this.nonUsBillingAddress = nonUsBillingAddress;
    }

    public boolean isNonUsBillingAddress() {
        return nonUsBillingAddress;
    }


    @Override
    public String toString() {
    	return new StringBuilder("BillingInfoForm [")
	    	.append("paymentTypeEnum=").append(paymentTypeEnum)
	    	.append(", address1=").append(address1)
	    	.append(", address2=").append(address2)
	    	.append(", address3=").append(address3)
	    	.append(", address4=").append(address4)
	    	.append(", agreementAccepted=").append(agreementAccepted)
	    	.append(", cardTypeEnum=").append(cardTypeEnum)
	    	.append(", city=").append(city)
	    	.append(", country=").append(country)
	    	.append(", diffBillingAddress=").append(diffBillingAddress)
	    	.append(", email=").append(email)
	    	.append(", mailMonthlyStatement=").append(mailMonthlyStatement)
	    	.append(", nameOnCard=").append(nameOnCard)
	    	.append(", nameOnReserveCard=").append(nameOnReserveCard)
	    	.append(", nonUsBillingAddress=").append(nonUsBillingAddress)
	    	.append(", plus4=").append(plus4)
	    	.append(", reserveCard=").append(reserveCard)
	    	.append(", state=").append(state)
	    	.append(", valid=").append(valid)
	    	.append(", zip=").append(zip)
	    	.append("]").toString();
	}


    // standard getters/setters
    public void setPaymentTypeEnum(PaymentType paymentType) {
        this.paymentTypeEnum = paymentType;
    }

    public PaymentType getPaymentTypeEnum() {
        return paymentTypeEnum;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setCardTypeEnum(CreditCardDTO.CreditCardType cardType) {
        this.cardTypeEnum = cardType;
    }

    public CreditCardDTO.CreditCardType getCardTypeEnum() {
        return cardTypeEnum;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardExpirationMonth(String cardExpirationMonth) {
        this.cardExpirationMonth = cardExpirationMonth;
    }

    public String getCardExpirationMonth() {
        return cardExpirationMonth;
    }

    public void setCardExpirationYear(String cardExpirationYear) {
        this.cardExpirationYear = cardExpirationYear;
    }

    public String getCardExpirationYear() {
        return cardExpirationYear;
    }

    public void setReserveCard(boolean reserveCard) {
        this.reserveCard = reserveCard;
    }

    public boolean isReserveCard() {
        return reserveCard;
    }

    public void setNameOnReserveCard(String nameOnReserveCard) {
        this.nameOnReserveCard = nameOnReserveCard;
    }

    public String getNameOnReserveCard() {
        return nameOnReserveCard;
    }

	public CreditCardDTO.CreditCardType getReserveCardTypeEnum() {
		return reserveCardTypeEnum;
	}

	public void setReserveCardTypeEnum(
			CreditCardDTO.CreditCardType reserveCardTypeEnum) {
		this.reserveCardTypeEnum = reserveCardTypeEnum;
	}

    public void setReserveCardNumber(String reserveCardNumber) {
        this.reserveCardNumber = reserveCardNumber;
    }

    public String getReserveCardNumber() {
        return reserveCardNumber;
    }

    public void setReserveCardExpirationMonth(String reserveCardExpirationMonth) {
        this.reserveCardExpirationMonth = reserveCardExpirationMonth;
    }

    public String getReserveCardExpirationMonth() {
        return reserveCardExpirationMonth;
    }

    public void setReserveCardExpirationYear(String reserveCardExpirationYear) {
        this.reserveCardExpirationYear = reserveCardExpirationYear;
    }

    public String getReserveCardExpirationYear() {
        return reserveCardExpirationYear;
    }

    public void setDiffBillingAddress(boolean diffBillingAddress) {
        this.diffBillingAddress = diffBillingAddress;
    }

    public boolean isDiffBillingAddress() {
        return diffBillingAddress;
    }

    public void setAddressLine1(String addressLine1) {
        this.address1 = addressLine1;
    }

    public String getAddressLine1() {
        return address1;
    }

    public void setAddressLine2(String addressLine2) {
        this.address2 = addressLine2;
    }

    public String getAddressLine2() {
        return address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setMailMonthlyStatement(boolean mailMonthlyStatement) {
        this.mailMonthlyStatement = mailMonthlyStatement;
    }

    public boolean isMailMonthlyStatement() {
        return mailMonthlyStatement;
    }

    public void setBankAcctTypeEnum(AccountEFTDTO.BankAccountType bankAcctType) {
        this.bankAcctTypeEnum = bankAcctType;
    }

    public AccountEFTDTO.BankAccountType getBankAcctTypeEnum() {
        return bankAcctTypeEnum;
    }

    public void setBankRoutingNumber(String bankRoutingNumber) {
        this.bankRoutingNumber = bankRoutingNumber;
    }

    public String getBankRoutingNumber() {
        return bankRoutingNumber;
    }

    public void setBankAcctNumber(String bankAcctNumber) {
        this.bankAcctNumber = bankAcctNumber;
    }

    public String getBankAcctNumber() {
        return bankAcctNumber;
    }

    public void setAddressLine3(String addressLine3) {
        this.address3 = addressLine3;
    }

    public String getAddressLine3() {
        return address3;
    }

    public void setAddressLine4(String addressLine4) {
        this.address4 = addressLine4;
    }

    public String getAddressLine4() {
        return address4;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAgreementAccepted(boolean agreementAccepted) {
        this.agreementAccepted = agreementAccepted;
    }

    public boolean isAgreementAccepted() {
        return agreementAccepted;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPlus4(String plus4) {
        this.plus4 = plus4;
    }

    public String getPlus4() {
        return plus4;
    }

	public boolean isValid() {
		return valid;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

    public boolean isAccountClosure() {
        return accountClosure;
    }

    public void setAccountClosure(boolean accountClosure) {
        this.accountClosure = accountClosure;
    }

	public String getPaypageRegistrationId() {
		return paypageRegistrationId;
	}

	public void setPaypageRegistrationId(String paypageRegistrationId) {
		this.paypageRegistrationId = paypageRegistrationId;
	}


}
