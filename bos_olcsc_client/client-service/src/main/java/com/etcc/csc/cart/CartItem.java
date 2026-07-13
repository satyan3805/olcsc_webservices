/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.cart;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.etcc.csc.common.AgencyEnum;

/**
 * This is the interface that any object that can be added to the shopping cart must implement.
 * There is (at a minimum) a cart item type corresponding to each agency.
 * @see ShoppingCart
 * @see CartSummary
 * @see AgencyEnum
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public interface CartItem extends CartDetail, Comparable<CartItem> {

    /**
     * The types of Shopping Cart Items.  For processing, the Shopping Cart "processor" must know what types
     * of items it has, as they may be handled differently.  For example: while Fort Bend & HCTRA are handled the
     * same way, they have to be processed as separate transactions.
     * @author Stephen Davidson
     * @author Milosh Boroyevich
     */
    public enum ItemType {
        /**
         * EZ Account - this account will be created in the system under all conditions.
         */
        ACCOUNT("EZ Account"),
        /**
         * License plate - this plate will be added to the account IFF all violations are successfully paid.
         */
        TAG("Vehicle Tag"),
        /**
         * HCTRA (Agency) Violations.
         */
        HCTRA(AgencyEnum.AGENCY_HARRIS_COUNTY.getDisplay()),
        /**
         * Fort Bend (Agency) Violations
         */
        FORT_BEND(AgencyEnum.AGENCY_FORT_BEND.getDisplay()),

        MCTRA(AgencyEnum.AGENCY_METRO.getDisplay()),
        /**
         * BRAZORIA COUNTY(Agency) Violations
         */
        BCTRA(AgencyEnum.AGENCY_BCTRA.getDisplay()),
        /**
         * BTG(Agency) Violations
         */
        BTG(AgencyEnum.AGENCY_BTG.getDisplay()), 
        ;

        private static final Logger logger = Logger.getLogger(ItemType.class);

        /**
         * The user-friendly name of this enum.
         */
        private final String display;

        private ItemType(final String display) {
            this.display = display;
        }

        /**
         * Returns the "user-friendly" string form of this object.
         * @return the display string
         */
        public String getDisplay() {
            return this.display;
        }

        /**
         * Returns the agency corresponding to this enum type.
         * @return the agency
         * @see #copy(AgencyEnum)
         */
        public AgencyEnum getAgency() {
            switch(this) {
            case FORT_BEND:
                return AgencyEnum.AGENCY_FORT_BEND;
            case HCTRA:
                return AgencyEnum.AGENCY_HARRIS_COUNTY;
            case MCTRA:
                return AgencyEnum.AGENCY_METRO;
            case BCTRA:
                return AgencyEnum.AGENCY_BCTRA;
            case BTG:
                return AgencyEnum.AGENCY_BTG;  
            }
            return null;
        }

        /**
         * Compare the specified <tt>AgencyEnum</tt> to this <tt>ItemType</tt>.
         * @param a the agency to compare
         * @return <tt>true</tt> if this item type corresponds to the equivalent agency
         * @see #copy(AgencyEnum)
         */
        public boolean equals(AgencyEnum a) {
            return this == copy(a);
        }

        /**
         * A factory method used to convert an <tt>AgencyEnum</tt> to an <tt>ItemType</tt>.
         * Note: there should be an item type corresponding to each agency.
         * @param agency the agency to convert
         * @return the corresponding item type (or <tt>null</tt> if no mapping exists)
         * @see #getAgency()
         */
        //6623
        public static ItemType copy(AgencyEnum agency) {
            if (agency != null)
                switch(agency) {
                case AGENCY_FORT_BEND:
                    return FORT_BEND;
                case AGENCY_HARRIS_COUNTY:
                    return HCTRA;
                case AGENCY_METRO:
                	return MCTRA;
                case AGENCY_BCTRA:
                	return BCTRA;
                case AGENCY_BTG:
                	return BTG; 
                default:
                    logger.warn("No cart item type defined for agency: " + agency);
                    break;
                }
            return null;
        }
    }


    /**
     * The type of Shopping cart item.
     * @return the type of the item
     */
    public ItemType getItemType();

    /**
     * Returns the user-friendly name of this object.
     * @return the name
     */
    public String getName();

    /**
     * The issue date.
     * @return the issue date
     */
    public Calendar getIssueDate();

    /**
     * The due date.
     * @return the due date
     */
    public Calendar getDueDate();

    /**
     * Clears the payment amount.
     * @return <tt>true</tt> if the payment value is modified
     * @see #getPayment()
     */
    public boolean clearPayment();

    /**
     * Returns the lookup key of this cart item.
     * @return the lookup key of this object
     * @see CartItemLocator#lookup(String, String)
     */
    public String getKey();

    /**
     * Returns the name of the class represented by this cart item, as a String.
     * @return the name of the class represented by this object
     * @see Class#getName()
     * @see CartItemLocator#lookup(String, String)
     */
    public String getClassName();
}
