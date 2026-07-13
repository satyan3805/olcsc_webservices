/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.etcc.csc.dto.AccountName;
import com.etcc.csc.dto.Address;
import com.etcc.csc.dto.AddressContainer;
import com.etcc.csc.dto.BillingInfo;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.util.FormatUtil;

/**
 * Custom JSP tag to format addresses and billing info objects.
 * One of <tt>address</tt>, <tt>billingInfo</tt> or <tt>accountName</tt>
 * must be set.  All other attributes are optional.
 * The <tt>address</tt> attribute must implement {@link Address}.
 * The <tt>addressContainer</tt> attribute must implement {@link AddressContainer}.
 * The <tt>billingInfo</tt> attribute must implement {@link BillingInfo}.
 * The <tt>accountName</tt> attribute must implement {@link AccountName}.
 * The <tt>vehicle</tt> attribute must extend {@link TagDTO}.
 * <dl>Address formatting JSP example (using all optional attributes with defaults) and sample output:
 * <dt>&lt;etcc-extended:format address="${address}" delimiter="&lt;br &frasl;&gt;" displayName="true" &frasl;&gt;</dt>
 * <dd>
 * Wile E. Coyote<br />
 * 1501 N Main St<br />
 * Flagstaff, AZ 86004<br />
 * </dd>
 * <dt>Note: the addressContainer behaves the same as passing the enclosed address as a parameter, but it silently handles a <tt>null</tt> container.</dt>
 * </dl>
 * <dl>Billing info formatting JSP example (using all optional attributes with defaults) and sample output:
 * <dt>&lt;etcc-extended:format billingInfo="${billingInfo}" delimiter="&lt;br &frasl;&gt;" mask="ALL" primaryOnly="true" displayName="true" &frasl;&gt;</dt>
 * <dd>
 * Wile E. Coyote<br />
 * Visa ***-1234<br />
 * Exp **&frasl;****<br />
 * </dd>
 * </dl>
 * <dl>Name formatting JSP example (using all optional attributes with defaults) and sample output:
 * <dt>&lt;etcc-extended:format accountName="${accountName}" delimiter="&lt;br &frasl;&gt;" maxNameLength="0" &frasl;&gt;</dt>
 * <dd>
 * Wile E. Coyote<br />
 * Acme Corporation<br />
 * </dd>
 * </dl>
 * <dl>Vehicle formatting JSP example and sample output:
 * <dt>&lt;etcc-extended:format vehicle="${tag}" &frasl;&gt;</dt>
 * <dd>
 * <strong>Rocket</strong> &mdash; 1995 silver Acme Skates, AZ License Temporary
 * </dd>
 * </dl>
 * @author Milosh Boroyevich
 * @see Address
 * @see AddressContainer
 * @see BillingInfo
 * @see AccountName
 * @see TagDTO
 * @see FormatUtil#formatAddress(Address, String, boolean)
 * @see FormatUtil#formatPaymentMethod(BillingInfo, String, FormatUtil.MaskValue, boolean, boolean)
 * @see FormatUtil.MaskValue
 * @see FormatUtil#formatName(AccountName, String, int)
 * @see FormatUtil#formatVehicle(TagDTO, boolean)
 */
public class FormatTag extends TagSupport {
    private static final long serialVersionUID = 4341753528663284549L;
    private static final Logger logger = Logger.getLogger(FormatTag.class);

    private static enum FormatObjectType {
        ADDRESS,
        BILLING_INFO,
        ACCOUNT_NAME,
        VEHICLE,
        ;
    }

    public static final String DEFAULT_DELIMITER = "<br />";
    protected static final String ERROR_MSG = "<pre>ERROR: required attribute ('address', 'billingInfo', or 'accountName') not specified.</pre>";

    private FormatObjectType objectType = null;

    /** Mutually-exclusive attribute for object to format. */
    private Address address = null;
    /** Mutually-exclusive attribute for object to format. */
    private BillingInfo billingInfo = null;
    /** Mutually-exclusive attribute for object to format. */
    private AccountName accountName = null;
    /** Mutually-exclusive attribute for object to format. */
    private TagDTO vehicle = null;

    /** Optional attribute specifying the delimiter string. */
    private String delimiter = DEFAULT_DELIMITER;
    /** Flag specifying whether to display the name.  Defaults to <tt>true</tt>. */
    private boolean displayName = true;

    /**
     * Optional attribute specifying which values to mask.
     * Only used with billing info and defaults to <tt>ALL</tt>.
     */
    private FormatUtil.MaskValue mask = FormatUtil.MaskValue.ALL;
    /** Flag indicating to only display the primary billing info.  Defaults to <tt>true</tt>. */
    private boolean primaryOnly = true;

    /** Optional attribute specifying whether to limit the length of the account name components. */
    private int maxNameLength = 0;

    /**
     * Formats the specified attribute.
     * Exactly one of {@link #address} or {@link #billingInfo} is required.
     * @see FormatUtil#formatAddress(Address, String, boolean)
     * @see FormatUtil#formatPaymentMethod(BillingInfo, String, FormatUtil.MaskValue, boolean, boolean)
     */
    @Override
    public int doStartTag() throws JspException {
        String output;
        if (this.objectType != null) {
            switch (this.objectType) {
            case ADDRESS:
                output = FormatUtil.formatAddress(address, delimiter, displayName);
                break;
            case BILLING_INFO:
                output = FormatUtil.formatPaymentMethod(billingInfo, delimiter, mask, primaryOnly, displayName);
                break;
            case ACCOUNT_NAME:
                output = FormatUtil.formatName(accountName, delimiter, maxNameLength);
                break;
            case VEHICLE:
                output = FormatUtil.formatVehicle(vehicle, true);
                break;
            default:
                output = ERROR_MSG;
            }
        } else {
            output = ERROR_MSG;
        }
        try {
            pageContext.getOut().print(output);
        } catch (IOException ioe) {
            String message = "IOException while writing to client: " + ioe.getMessage();
            logger.trace(output);
            logger.debug(message, ioe);
            throw new JspException(message, ioe);
        }
        return SKIP_BODY;
    }


    /**
     * Same as <tt>setAddress(addressContainer.getAddress())</tt> without the risk of a <tt>NullPointerException</tt>.
     * @param addressContainer object containing an address
     * @see #setAddress(Address)
     */
    public void setAddressContainer(AddressContainer addressContainer) {
        this.setAddress(addressContainer == null ? null : addressContainer.getAddress());
    }

    public void setAddress(Address address) {
        this.objectType = FormatObjectType.ADDRESS;
        this.address = address;
    }

    public void setBillingInfo(BillingInfo billingInfo) {
        this.objectType = FormatObjectType.BILLING_INFO;
        this.billingInfo = billingInfo;
    }

    public void setAccountName(AccountName accountName) {
        this.objectType = FormatObjectType.ACCOUNT_NAME;
        this.accountName = accountName;
    }

    public void setVehicle(TagDTO vehicle) {
        this.objectType = FormatObjectType.VEHICLE;
        this.vehicle = vehicle;
    }

    public void setMask(String mask) {
        this.mask = FormatUtil.MaskValue.valueOf(mask);
    }


    public String getMask() {
        return mask.toString();
    }

    public Address getAddress() {
        return address;
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public AccountName getAccountName() {
        return accountName;
    }

    public TagDTO getVehicle() {
        return vehicle;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDisplayName(boolean displayName) {
        this.displayName = displayName;
    }

    public boolean isDisplayName() {
        return displayName;
    }

    public void setPrimaryOnly(boolean primaryOnly) {
        this.primaryOnly = primaryOnly;
    }

    public boolean isPrimaryOnly() {
        return primaryOnly;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public int getMaxNameLength() {
        return maxNameLength;
    }
}
