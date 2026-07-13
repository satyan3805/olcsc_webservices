/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.util;

import java.util.List;

import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountName;
import com.etcc.csc.dto.Address;
import com.etcc.csc.dto.BillingInfo;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.form.BillingInfoForm;

import org.apache.commons.validator.util.Flags;
import org.apache.log4j.Logger;

import webservice.paymentappliance.etcc.com.CreditCardTypeEnum;

/**
 * Utility methods for presentation-layer handling of payment methods and addresses.
 * @author Milosh Boroyevich
 */
// from the original CreditCardUtil class
public class FormatUtil {
    public static final Logger logger = Logger.getLogger(FormatUtil.class);

    public static final String DEFAULT_DELIMITER = "<br />";

    /**
     * Options available to specify masking of values.
     * @author Milosh Boroyevich
     */
    public enum MaskValue {
        NONE(0/*MaskValue.NONE_BIT*/),
        ACCOUNT(1 << 0/*MaskValue.ACCOUNT_BIT*/),
        DATE(1 << 1/*MaskValue.DATE_BIT*/),
        ALL((1 << 0) + (1 << 1)/*MaskValue.ACCOUNT_BIT + MaskValue.DATE_BIT*/),
        ;

        private static final int NONE_BIT = 0;
        private static final int ACCOUNT_BIT = 1 << 0;
        private static final int DATE_BIT = 1 << 1;

        private final Flags flags;

        private MaskValue(int bitMask) {
            this.flags = new Flags(bitMask);
        }

        /**
         * Tests whether the specified mask is on.
         * @param mask mask value to check
         * @return whether the specified mask value is on
         * @see Flags#isOn(long)
         */
        public boolean isOn(MaskValue mask) {
            return flags.isOn(mask.flags.getFlags());
        }
    }

    /**
     * Private Constructor.  All methods on this utility class are static.
     */
    private FormatUtil() { }

    public static String formatPaymentMethod(BillingInfo billingInfo) {
    	return formatPaymentMethod(billingInfo, DEFAULT_DELIMITER);
    }

    public static String formatPaymentMethod(BillingInfo billingInfo, String delimiter) {
    	return formatPaymentMethod(billingInfo, DEFAULT_DELIMITER, MaskValue.ACCOUNT, false, true);
    }

    /**
     * Main presentation billing info formatting method.
     * @param billingInfo billing info object to format
     * @param delimiter delimiter to use between output "lines"
     * @param mask determines which values are masked in the output
     * @param primaryOnly whether to format only the primary or all credit cards on the object
     * @param displayName boolean flag specifying whether to display the <tt>name</tt> "line"
     * @return string representation of the specified address
     * @see #formatCreditCards(AccountCreditCardDTO[], String, MaskValue, boolean, boolean)
     * @see #formatEFT(AccountEFTDTO, String, MaskValue)
     */
    public static String formatPaymentMethod(BillingInfo billingInfo, String delimiter, MaskValue mask, boolean primaryOnly, boolean displayName) {
        if (billingInfo == null)
            return "";
        PaymentType billingType = billingInfo.getBillingType();
    	if (billingType == null)
    		return "";
    	if (delimiter == null)
    	    delimiter = DEFAULT_DELIMITER;
        switch (billingType) {
        case CREDIT:
            return formatCreditCards(billingInfo.getCards(), delimiter, mask, primaryOnly, displayName);
        case EFT:
            return formatEFT(billingInfo.getEft(), delimiter, mask);
        case INVOICE:
            return "Invoice";
        }
        return "";
    }

    /**
     * Format the specified EFT as a string.
     * @param eft EFT to format
     * @param delimiter delimiter to use
     * @param mask flag to specify which values should be masked
     * @return string representation of the EFT
     * @see StringUtil#maskAccount(String)
     */
    public static String formatEFT(AccountEFTDTO eft, String delimiter, MaskValue mask) {
        StringBuilder sb = new StringBuilder();
        if (eft != null) {
            if (mask == null)
                mask = MaskValue.ALL;
            sb.append(eft.getAccountType().getDisplay()).append(delimiter);
            sb.append("Routing Number: ").append(eft.getRoutingNumber()).append(delimiter);
            String account = eft.getAccountNumber();
            if (mask.isOn(MaskValue.ACCOUNT))
                account = StringUtil.maskAccount(account);
            sb.append("Account Number: ").append(account);
        }
        return sb.toString();
    }

    /**
     * Format the specified credit cards as a string.
     * @param cards array of credit cards to format
     * @param delimiter delimiter to use
     * @param mask flag to specify which values should be masked
     * @param primaryOnly default of <tt>false</tt> outputs both primary and secondary cards
     * @param displayName default of <tt>true</tt> outputs the name on each credit card
     * @return string representation of the specified credit cards
     * @see #formatCreditCard(AccountCreditCardDTO, String, MaskValue, boolean)
     */
    public static String formatCreditCards(AccountCreditCardDTO[] cards, String delimiter, MaskValue mask, boolean primaryOnly, boolean displayName) {
        StringBuilder sb = new StringBuilder();
        if (cards != null) {
            AccountCreditCardDTO ccp = null;
            AccountCreditCardDTO ccs = null;
            for (AccountCreditCardDTO cc : cards) {
                if (cc.isPrimary()) {
                    ccp = cc;
                    if (primaryOnly)
                        break;
                } else {
                    ccs = cc;
                }
                if (ccp != null && ccs != null)
                    break;
            }
            if (ccp != null) {
                sb.append(formatCreditCard(ccp, delimiter, mask, displayName));
            }
            if (!primaryOnly && ccs != null) {
                sb.append(delimiter)
                  .append(formatCreditCard(ccs, delimiter, mask, displayName));
            }
        }
        return sb.toString();
    }

    /**
     * Format the specified credit card.
     * Uses the default delimiter, masks the account number and displays the billing name.
     * @see #formatCreditCard(AccountCreditCardDTO, String, MaskValue, boolean)
     * @see #DEFAULT_DELIMITER
     * @see MaskValue#ACCOUNT
     */
    public static String formatCreditCard(AccountCreditCardDTO cc) {
        return formatCreditCard(cc, DEFAULT_DELIMITER, MaskValue.ACCOUNT, true);
    }

    /**
     * Format the specified credit card as a string.
     * @param cc credit card to format
     * @param delimiter delimiter to use
     * @param mask flag to specify which values should be masked
     * @param displayName default of <tt>true</tt> outputs the name on the credit card
     * @return string representation of the specified credit card
     * @see StringUtil#maskAccount(String)
     */
    public static String formatCreditCard(AccountCreditCardDTO cc, String delimiter, MaskValue mask, boolean displayName) {
        StringBuilder sb = new StringBuilder();
        if (mask == null)
            mask = MaskValue.ALL;
        if (displayName)
            sb.append(cc.getNameOnCard()).append(delimiter);
        sb.append(cc.getCardType().getDisplay());
        String cardNo = cc.getCardNbr();
        if (mask.isOn(MaskValue.ACCOUNT))
            cardNo = StringUtil.maskAccount(cardNo);
        sb.append(" ").append(cardNo).append(delimiter);
        if (mask.isOn(MaskValue.DATE))
            sb.append("Exp: **/****");
        else
            sb.append("Exp: ").append(cc.getCardExpires());
        return sb.toString();
    }

    public static String formatAddress(BillingInfoDTO billingInfo) {
        return formatAddress(billingInfo, DEFAULT_DELIMITER, false);
    }
    /**
     * Formats the address on the primary credit card for the specified billing info.
     * @param billingInfo the billing info with primary credit card
     * @param delimiter
     * @param displayName
     * @return formatted billing address
     * @see #formatAddress(Address, String, boolean)
     */
    public static String formatAddress(BillingInfoDTO billingInfo, String delimiter, boolean displayName) {
    	if (billingInfo != null)
    	    return formatAddress(billingInfo.getPrimaryCard(), delimiter, displayName);
        return "";
    }

    public static String formatAddress(Address address) {
        return formatAddress(address, DEFAULT_DELIMITER, false);
    }

    /**
     * Main presentation address formatting method.
     * @param address the address to format
     * @param delimiter delimiter to use between output "lines"
     * @param displayName boolean flag specifying whether to display the <tt>name</tt> "line"
     * @return string representation of the specified address
     * @see Address#getName()
     */
    public static String formatAddress(Address address, String delimiter, boolean displayName) {
    	if (address == null) return "";
    	StringBuilder sb = new StringBuilder();
    	/*
         * String countryName = ""; if (cc.getCountry() != null) { Country country = new Country(); Collection
         * countries = country.getCountries(); Iterator countriesItr = countries.iterator(); CountryDTO countryDTO;
         * while (countriesItr.hasNext()) { countryDTO = (CountryDTO)countriesItr.next(); if
         * (cc.getCountry().equals(countryDTO.getCountryCode())) { countryName = countryDTO.getCountry(); } } } else {
         * countryName = Address.COUNTRY_CODE_USA; }
         */

        // sb.append(((cc.getNameOnCard() != null) && (cc.getNameOnCard().length() > 0)) ? cc.getNameOnCard() +
        // delimiter : "");
        if (displayName && !StringUtil.isEmpty(address.getName()))
            sb.append(address.getName()).append(delimiter);
        if (!StringUtil.isEmpty(address.getAddress1()))
            sb.append(address.getAddress1()).append(delimiter);
        if (!StringUtil.isEmpty(address.getAddress2()))
            sb.append(address.getAddress2()).append(delimiter);
        /*
         * if (countryName.equals(Address.COUNTRY_CODE_USA)) { sb.append(cc.getCity() + ", " + cc.getState() + " " + cc.getZipCode()); }
         * else { sb.append(countryName); }
         */
        boolean countryUSA = false;
        if (StringUtil.isEmpty(address.getCountry())) {
            address.setCountry(Address.COUNTRY_CODE_USA);
            countryUSA = true;
        }

        if (countryUSA || address.getCountry().equalsIgnoreCase(Address.COUNTRY_CODE_USA)) {
            sb.append(address.getCity()).append(", ").append(address.getState()).append(" ").append(address.getZip());
            if (!StringUtil.isEmpty(address.getPlus4()))
                sb.append("-").append(address.getPlus4());
        } else {
            if (!StringUtil.isEmpty(address.getAddress3()))
                sb.append(address.getAddress3()).append(delimiter);
            if (!StringUtil.isEmpty(address.getAddress4()))
                sb.append(address.getAddress4()).append(delimiter);
            sb.append(address.getCountry());
        }
        return sb.toString();
    }

    public static String formatAddress(BillingInfoForm billingInfoForm) {
    	return formatAddress(billingInfoForm, DEFAULT_DELIMITER);
    }

    public static String formatAddress(BillingInfoForm billingInfoForm, String delimiter) {
        /*Collection countries = new CountryDelegate().getCountries();
        Iterator itr = countries.iterator();
        String countryName = "";
        CountryDTO countryDTO;
        while (itr.hasNext()) {
            countryDTO = (CountryDTO)itr.next();
            if (country.equals(countryDTO.getCountryCode())) {
                countryName = countryDTO.getCountry();
            }
        }*/
        //String countryName = country;
        if (billingInfoForm == null)
            return null;
        PaymentType paymentType = billingInfoForm.getPaymentTypeEnum();
        if (paymentType == null)
            return null;

        StringBuilder sb = new StringBuilder();
        if (paymentType == PaymentType.CREDIT)
            sb.append(billingInfoForm.getNameOnCard()).append(delimiter);
        sb.append(billingInfoForm.getAddressLine1()).append(delimiter);
        if (!StringUtil.isEmpty(billingInfoForm.getAddressLine2()))
            sb.append(billingInfoForm.getAddressLine2()).append(delimiter);
        if (!StringUtil.isEmpty(billingInfoForm.getAddressLine3()))
            sb.append(billingInfoForm.getAddressLine3()).append(delimiter);
        if (!StringUtil.isEmpty(billingInfoForm.getAddressLine4()))
            sb.append(billingInfoForm.getAddressLine4()).append(delimiter);

        if (billingInfoForm.isNonUsBillingAddress()) {
            sb.append(billingInfoForm.getCountry());
        } else {
            sb.append(billingInfoForm.getCity()).append(", ")
                .append(billingInfoForm.getState()).append(" ")
                .append(billingInfoForm.getZip());
            if (!StringUtil.isEmpty(billingInfoForm.getPlus4()))
                sb.append("-").append(billingInfoForm.getPlus4());
        }
        return sb.toString();
//    	i f ((paymentType.equals(AccountPaymentMethodDTO.PAYMENT_TYPE_CREDIT)) && (nonUsBillingAddress == null || ! nonUsBillingAddress)) {
//            return nameOnCard + "<br />"
//            + addressLine1 + "<br />"
//            + (((addressLine2 != null) && (addressLine2.length() > 0)) ? addressLine2 + "<br />" : "")
//            + (((addressLine3 != null) && (addressLine3.length() > 0)) ? addressLine3 + "<br />" : "")
//            + (((addressLine4 != null) && (addressLine4.length() > 0)) ? addressLine4 + "<br />" : "")
//            + city + ", " + state + " " + zip
//            + (((plus4 != null) && (plus4.length() > 0))?"-"+plus4:"");
//        } else if ((paymentType.equals(AccountPaymentMethodDTO.PAYMENT_TYPE_CREDIT)) && (nonUsBillingAddress != null && nonUsBillingAddress)) {
//            return nameOnCard + "<br />"
//            + addressLine1 + "<br />"
//            + (((addressLine2 != null) && (addressLine2.length() > 0)) ? addressLine2 + "<br />" : "")
//            + (((addressLine3 != null) && (addressLine3.length() > 0)) ? addressLine3 + "<br />" : "")
//            + (((addressLine4 != null) && (addressLine4.length() > 0)) ? addressLine4 + "<br />" : "")
//            + country;
//        } else if ((paymentType.equals(AccountPaymentMethodDTO.PAYMENT_TYPE_EFT)) && (nonUsBillingAddress == null ||! nonUsBillingAddress)) {
//            return addressLine1 + "<br />"
//            + (((addressLine2 != null) && (addressLine2.length() > 0)) ? addressLine2 + "<br />" : "")
//            + (((addressLine3 != null) && (addressLine3.length() > 0)) ? addressLine3 + "<br />" : "")
//            + (((addressLine4 != null) && (addressLine4.length() > 0)) ? addressLine4 + "<br />" : "")
//            + city + ", " + state + " " + zip
//            + (((plus4 != null) && (plus4.length() > 0))?"-"+plus4:"");
//        } else if ((paymentType.equals(AccountPaymentMethodDTO.PAYMENT_TYPE_EFT)) && (nonUsBillingAddress != null && nonUsBillingAddress)) {
//            return addressLine1 + "<br />"
//            + (((addressLine2 != null) && (addressLine2.length() > 0)) ? addressLine2 + "<br />" : "")
//            + (((addressLine3 != null) && (addressLine3.length() > 0)) ? addressLine3 + "<br />" : "")
//            + (((addressLine4 != null) && (addressLine4.length() > 0)) ? addressLine4 + "<br />" : "")
//            + country;
//        } else {
//            return "";
//        }
    }

    /**
     * Return a formatted string (using the <tt>DEFAULT_DELIMITER</tt>) that
     * represents the payment method of the specified billing form.
     * @param billingInfoForm billing information is extracted from this object
     * @param maskDate boolean flag specifying whether to mask the credit card expiration date
     * @return the payment method displayed as a string
     * @see #formatPaymentMethod(BillingInfoForm, String, boolean)
     * @see #DEFAULT_DELIMITER
     */
    public static String formatPaymentMethod(BillingInfoForm billingInfoForm, boolean maskDate) {
    	return formatPaymentMethod(billingInfoForm, DEFAULT_DELIMITER, maskDate);
    }

    /**
     * Return a formatted string representing the payment method of the specified billing form.
     * @param billingInfoForm billing information is extracted from this object
     * @param delimiter delimiter to use
     * @param maskDate boolean flag specifying whether to mask the credit card expiration date
     * @return the payment method displayed as a string
     */
    public static String formatPaymentMethod(BillingInfoForm billingInfoForm, String delimiter, boolean maskDate) {
        MaskValue mask = MaskValue.ACCOUNT;
        if (maskDate)
            mask = MaskValue.ALL;
        return formatPaymentMethod(billingInfoForm, delimiter, mask, true, false);
    }

    /**
     * Returns the formatted name.
     * If <tt>maxLen&gt;0</tt> the output will consist of <i>firstName lastName</i>
     * if not empty and <i>companyName</i> otherwise.
     * If <tt>maxLen==0</tt>, the name is formatted as:
     * <i>firstName middleInitial lastName <tt>delimiter</tt> companyName</i>.
     * @param name the account name to format
     * @param delimiter delimiter to use between output "lines"
     * @param maxLen maximum characters to display from each name component (<tt>maxLen == 0</tt> indicates no limit)
     * @return First, last, and company name.
     * @throws IndexOutOfBoundsException if <tt>maxLen</tt> is negative
     */
    // moved from com.etcc.csc.dto.AccountDTO
    public static String formatName(AccountName name, String delimiter, int maxLen) throws IndexOutOfBoundsException {
        //int len = 15;
        if (maxLen < 0)
            throw new StringIndexOutOfBoundsException(maxLen);

        StringBuilder formattedName = new StringBuilder();
        String first = name.getFirstName();
        String middle = name.getMiddleInitial();
        String last = name.getLastName();
        String company = name.getCompanyName();

        if (!StringUtil.isEmpty(first)) {
            if (maxLen > 0 && first.length() > maxLen)
                formattedName.append(first.substring(0, maxLen));
            else
                formattedName.append(first);
        } else {
            first = null;
        }

        if (maxLen == 0 && !StringUtil.isEmpty(middle))
            formattedName.append(" ").append(middle).append(".");

        if (!StringUtil.isEmpty(last)) {
            formattedName.append(" ");
            if (maxLen > 0 && last.length() > maxLen)
                formattedName.append(last.substring(0, maxLen));
            else
                formattedName.append(last);
        } else {
            last = null;
        }

        if (!StringUtil.isEmpty(company)) {
            int length = formattedName.length();
            if (maxLen == 0) {
                if (length > 0)
                    formattedName.append(delimiter);
                formattedName.append(company);
            } if (length == 0) {
                if (company.length() > maxLen)
                    formattedName.append(company.substring(0, maxLen));
                else
                    formattedName.append(company);
            }
        }

        return formattedName.toString();
    }

    /**
     * Returns the formatted vehicle tag.
     * @param vehicle vehicle tag to format as a string
     * @param html whether to include HTML tags in the formatting
     * @return vehicle name - year color make model, license plate
     */
    public static String formatVehicle(TagDTO vehicle, boolean html) {
        StringBuilder formattedVehicle = new StringBuilder();
        if (html)
            formattedVehicle.append("<strong>");
        formattedVehicle.append(vehicle.getName());
        if (html)
            formattedVehicle.append("</strong> &mdash; ");
        else
            formattedVehicle.append(" - ");

        formattedVehicle
            .append(vehicle.getVehicleYear()).append(" ")
            .append(vehicle.getVehicleColor()).append(" ")
            .append(vehicle.getVehicleMake()).append(" ")
            .append(vehicle.getVehicleModel()).append(", ")
        ;
        formattedVehicle
            .append(vehicle.getLicState())
            .append(" License ")
        ;
        if (vehicle.isTemporaryLicPlate())
            formattedVehicle.append("Temporary");
        else
            formattedVehicle.append(vehicle.getLicPlate());
        return formattedVehicle.toString();
    }

    /**
     * Returns the formatted human-readable list representation.
     * @param list list of strings to format
     * @return values separated by ", " and the final element separated by " and "
     */
    public static String formatStrings(List<String> list) {
        if (list == null || list.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        sb.append(list.get(0));
        int size = list.size();
        for (int i = 1; i < size-1; i++)
            sb.append(", " + list.get(i));
        if (size > 1)
            sb.append(" and " + list.get(size-1));
        return sb.toString();
    }


    /*
     * Convert Type to PAN Credit Card Type
     */
	public static CreditCardTypeEnum convertCardType(String cardType) {
		if ("V".equals(cardType)) {
			return CreditCardTypeEnum.VISA;
		}
		else if ("M".equals(cardType))
		{
			return CreditCardTypeEnum.MASTERCARD;
		}
		else if ("A".equals(cardType))
		{
			return CreditCardTypeEnum.AMERICANEXPRESS;
		}
		else if ("D".equals(cardType))
		{
			return CreditCardTypeEnum.DISCJCBCUP;
		}
		return null;
	}
}
