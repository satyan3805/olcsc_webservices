package com.etcc.csc.common;

public class Constants {
    public static final String APP_DATASOURCE = "java:comp/env/jdbc/NTTADMVDS";

    public static final String SESSION_ACCOUNT_LOGIN = "accountLogin";
    public static final String SESSION_MONTH_LIST = "monthList";
    
    //****** Changes for DEFECT # 1091 ******//
    public static final String SESSION_PICKUP_LOCATION = "pickupLocationId";

    public static final String LOGIN_TYPE_ACCOUNT = "AC";
    public static final String LOGIN_TYPE_INVOICE = "IN";
    public static final String LOGIN_TYPE_TOLLTAG = "TT";
    public static final String LOGIN_TYPE_COLLECTION_AGENCY = "CA";
    public static final String LOGIN_TYPE_ZIPCASH_INVOICE = "ZI";
    public static final String LOGIN_TYPE_ZIPCASH_ACCOUNT = "ZA";
    public static final String LOGIN_GENERIC_USER = "OLCSC_ANONYMOUS";

    public static final String IOP_DFW = "DFW";
    public static final String IOP_HUB = "IOPHUB";
    public static final String IOP_LOVE = "DAL";
    public static final String IOP_TXDOT = "TTA";
    public static final String IOP_HCTRA = "HCTRA";

    public static final int IOP_DFW_AGCY_ID = 4;
    public static final int IOP_HUB_AGCY_ID = 10;
    public static final int IOP_LOVE_AGCY_ID = 6;
    public static final int IOP_TXDOT_AGCY_ID = 7;
    public static final int IOP_HCTRA_AGCY_ID = 3;

    /**
     * No instantiation needed for this class.
     */
    private Constants() {
    }

}
