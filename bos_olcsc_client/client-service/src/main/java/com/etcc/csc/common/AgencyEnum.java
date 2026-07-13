/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.common;

/**
 * The enumeration of Agencies.
 * @author Milosh Boroyevich
 */
public enum AgencyEnum {
    /**
     * Harris County.
     */
    AGENCY_HARRIS_COUNTY("HCTRA", "Harris County"/*AgencyEnum.CODE_HARRIS_COUNTY, AgencyEnum.HARRIS_COUNTY*/),
    /**
     * Fort Bend County.
     */
    AGENCY_FORT_BEND("FBGPTRA", "Fort Bend"/*AgencyEnum.CODE_FORT_BEND, AgencyEnum.FORT_BEND*/),


    /**
     * RFC20150109 – Montgomery County
     * Nelisa Kiboti
     */
    AGENCY_MONTGOMERY_COUNTY("MCTRA", "Montgomery County"/*AgencyEnum.CODE_HARRIS_COUNTY, AgencyEnum.MONTGOMERY_COUNTY*/),

    
    AGENCY_METRO("MCTRA", "MCTRA"/*AgencyEnum.CODE_FORT_BEND, AgencyEnum.FORT_BEND*/),
    /**
     * BRAZORIA COUNTY TOLL ROAD AUTHORITY.
     */ 
    AGENCY_BCTRA("BCTRA", "BCTRA"/*AgencyEnum.CODE_FORT_BEND, AgencyEnum.FORT_BEND*/),
    /**
     * BLUERIDGE TRANSPORTATION GROUP (BTG).
     */
    AGENCY_BTG("BTG","BTG"/*AgencyEnum.CODE_FORT_BEND, AgencyEnum.FORT_BEND*/);

    /**
     * The database code of this enum.
     */
    private final String code;
    /**
     * The user-friendly name of this enum.
     */
    private final String display;

    private AgencyEnum(final String code, final String display) {
    	this.code = code;
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
     * Returns the enum constant corresponding to the specified code.
     * @param code case-insensitive code of the enum type
     * @return the enum constant
     * @throws IllegalArgumentException if the specified code has no corresponding enum type
     * @throws NullPointerException if <tt>code</tt> is <tt>null</tt>
     */
    public static AgencyEnum valueOfCode(String code) throws IllegalArgumentException, NullPointerException {

    	for (AgencyEnum item : AgencyEnum.values())
    	{
    		if (code.equals(item.getCode()))
    		{
    			return item;
    		}
    	}

        throw new IllegalArgumentException("No enum const class for code: " + code);
    }

    /**
     * Returns the code form of this object.
     * @return the code
     */
    public String getCode() {
        return this.code;
    }
}
