/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.StringUtil;

/**
 * Data transfer object for Credit Cards.
 */
public class CreditCardDTO extends BaseDTO {
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = -1621798322958243534L;

    public enum CreditCardType {

        /**
         * Visa Credit Card Type.
         */
		VISA(2,'V'/*CreditCardType.VISA_CODE*/, "Visa"),
		/**
		 * Mastercard Credit Card Type.
		 */
		MASTERCARD(1,'M'/*CreditCardType.MASTERCARD_CODE*/, "Mastercard"),
        /**
         * AMEX Credit Card Type.
         */
		AMERICANEXPRESS(3,'A'/*CreditCardType.AMERICAN_EXPRESS_CODE*/, "American Express"),
        /**
         * Discover Credit Card Type.
         */
		//DISCOVER(4,'D'/*CreditCardType.DISCOVER_CODE*/, "Discover"),


		//DEBITCARD(5,'E'/*CreditCardType.DISCOVER_CODE*/, "DebitCard"),
        /**
         * Diner's Club Credit Card Type.
         */
		DISCOVER(4,'D'/*CreditCardType.DISCOVER_JCB_DINERS_CLUB_CODE*/, "Discover");

		public static final char VISA_CODE = 'V';
		public static final char MASTERCARD_CODE = 'M';
		public static final char AMERICAN_EXPRESS_CODE = 'A';
		public static final char DISCOVER_CODE = 'D';
		public static final char DINERS_CLUB_CODE = 'C';


		private final int type;
	    /**
	     * Character code representing the payment type.
	     */
		private final char code;
	    /**
	     * The user-friendly name of this object.
	     */
	    private final String display;

		private CreditCardType(int type, char code, String display) {
			this.code = code;
			this.type = type;
	        this.display = display;
		}

	    /**
	     * Returns the enum constant corresponding to the specified code.
	     * @param code case-insensitive code of the enum type
	     * @return the enum constant (or <tt>null</tt> if code is <tt>empty</tt>)
	     * @throws IllegalArgumentException if the specified code has no corresponding enum type
	     * @see #valueOfCode(char)
	     * @see StringUtil#isEmpty(String)
	     */
	    public static CreditCardType valueOfCode(String code) throws IllegalArgumentException {
	        if (!StringUtil.isEmpty(code)) {
	            return valueOfCode(Character.toUpperCase(code.charAt(0)));
	        }
	        return null;
	    }


	    public static CreditCardType valueOfType(int type) throws IllegalArgumentException {
	        for (CreditCardType a : CreditCardType.values())
	        {
	        	if (a.getType() == type )
	        	{
	        		return a;
	        	}
	        }
	        return null;
	    }

	    /**
	     * Returns the enum constant corresponding to the specified code.
	     * @param code character code of the enum type
	     * @return the enum constant
	     * @throws IllegalArgumentException if the specified code has no corresponding enum type
	     */
	    public static CreditCardType valueOfCode(char code) throws IllegalArgumentException {
	        switch (code) {
	        case VISA_CODE:
	            return VISA;
	        case MASTERCARD_CODE:
	            return MASTERCARD;
	        case AMERICAN_EXPRESS_CODE:
	            return AMERICANEXPRESS;
	        case DISCOVER_CODE:
	            return DISCOVER;
	       /* case DINERS_CLUB_CODE:
	            return DISCJCBCUP;*/
	        }
	        throw new IllegalArgumentException("No enum const class for code: " + code);
	    }

	    /**
	     * Returns the character code form of this object.
	     * @return the character code
	     */
		public char getCode() {
			return code;
		}

	    /**
	     * Returns the "user-friendly" string form of this object.
	     * @return the display string
	     */
		public String getDisplay() {
			return display;
		}

		public int getType() {
			return type;
		}



	}

    private CreditCardType cardType;
    private String cardName;
    private short cardNbrLength;
    private String cardNbrPrefix;
    private short cardTypeOrder;
    private boolean defaultValueFlag;
    private boolean activeFlag;

    /**
     * Constructor.
     */
    public CreditCardDTO() {
        // end <init>
    }

    /**
     * Constructor, generally for testing/demo.  Sets the activeFlag to True.
     * @param cardType The card type
     * @param cardName The name of the Card.
     * @param cardTypeOrder The order of the card.
     */
    public CreditCardDTO(CreditCardType cardType, String cardName, byte cardTypeOrder) {
    	this.cardType = cardType;
        this.cardName = cardName;
        this.cardTypeOrder = cardTypeOrder;
        this.activeFlag = true;
        // end <init>
    }
    /**
     * Constructor, generally for testing/demo.  Sets the activeFlag to True.
     * @param cardType The card type
     * @param cardName The name of the Card.
     * @param cardTypeOrder The order of the card.
     */
    public CreditCardDTO(CreditCardType cardType, String cardName, int cardTypeOrder) {
        this(cardType, cardName, (byte)cardTypeOrder);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        try {
            return (obj != null && this.cardType == ((CreditCardDTO)obj).cardType);
        } catch (ClassCastException e){
            //Not a credit card DTO.
            return false;
        }
        //end equals

    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.cardType == null ? Integer.MAX_VALUE : this.cardType.ordinal();
        //end hashCode

    }

    /**
     * Returns the char code for the credit card type.
     * @return the char code for the credit card type
     */
    public String getCardCode() {
        return (this.cardType == null ? null : Character.toString(this.cardType.getCode()));
    }

    public void setCardCode(String code) {
        this.cardType = CreditCardDTO.CreditCardType.valueOfCode(code);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("cardCode=");
        sb.append(this.cardType);
        sb.append(", cardName=");
        sb.append(this.cardName);
        sb.append(", cardNbrLength=");
        sb.append(this.cardNbrLength);
        sb.append(", cardNbrPrefix=");
        sb.append(this.cardNbrPrefix);
        sb.append(",cardTypeOrder=");
        sb.append(this.cardTypeOrder);
        sb.append(", defaultValueFlag=");
        sb.append(this.defaultValueFlag);
        sb.append(", activeFlag=");
        sb.append(this.activeFlag);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the cardName
     */
    public String getCardName() {
        return this.cardName;
        // end getCardName
    }

    /**
     * @param cardName the cardName to set
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
        // end setCardName
    }

    /**
     * @return the cardNbrLength
     */
    public short getCardNbrLength() {
        return this.cardNbrLength;
        // end getCardNbrLength
    }

    /**
     * @param cardNbrLength the cardNbrLength to set
     */
    public void setCardNbrLength(short cardNbrLength) {
        this.cardNbrLength = cardNbrLength;
        // end setCardNbrLength
    }

    /**
     * @return the cardNbrPrefix
     */
    public String getCardNbrPrefix() {
        return this.cardNbrPrefix;
        // end getCardNbrPrefix
    }

    /**
     * @param cardNbrPrefix the cardNbrPrefix to set
     */
    public void setCardNbrPrefix(String cardNbrPrefix) {
        this.cardNbrPrefix = cardNbrPrefix;
        // end setCardNbrPrefix
    }

    /**
     * @return the cardTypeOrder
     */
    public short getCardTypeOrder() {
        return this.cardTypeOrder;
        // end getCardTypeOrder
    }

    /**
     * @param cardTypeOrder the cardTypeOrder to set
     */
    public void setCardTypeOrder(short cardTypeOrder) {
        this.cardTypeOrder = cardTypeOrder;
        // end setCardTypeOrder
    }

    /**
     * @return the defaultValueFlag
     */
    public boolean isDefaultValueFlag() {
        return this.defaultValueFlag;
        // end isDefaultValueFlag
    }

    /**
     * @param defaultValueFlag the defaultValueFlag to set
     */
    public void setDefaultValueFlag(boolean defaultValueFlag) {
        this.defaultValueFlag = defaultValueFlag;
        // end setDefaultValueFlag
    }

    /**
     * @return the activeFlag
     */
    public boolean isActiveFlag() {
        return this.activeFlag;
        // end isActiveFlag
    }

    /**
     * @param activeFlag the activeFlag to set
     */
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
        // end setActiveFlag
    }

	public CreditCardType getCardType() {
		return cardType;
	}

	public void setCardType(CreditCardType cardType) {
		this.cardType = cardType;
	}


}
