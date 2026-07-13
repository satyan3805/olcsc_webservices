package com.etcc.csc.common;

public class AppEnum extends StringEnum {

    public static final AppEnum CONTACT_PHONE_NUMBER = new AppEnum("contactPhoneNumber");
    public static final AppEnum HOMEPAGE_CONTACT_PHONE_NUMBER = new AppEnum("homepageContactPhone");
    public static final AppEnum HELP_URL = new AppEnum("helpUrl");
    public static final AppEnum HELP_FAQ_URL = new AppEnum("helpFaqUrl");
    public static final AppEnum PAGE_SIZE = new AppEnum("tablePageSize");
    public static final AppEnum REPORT_DAYS = new AppEnum("reportDays");
    public static final AppEnum APP_URL = new AppEnum("applicationUrl");
    public static final AppEnum PRIVACY_POLICY_URL= new AppEnum("privacyPolicyUrl");
    public static final AppEnum SUPPORTED_BROWSER_URL= new AppEnum("supportedBrowserUrl");
    public static final AppEnum SECURE_COOKIES= new AppEnum("secureCookies");
    public static final AppEnum NOTIFICATION_FEE= new AppEnum("notificationFee");
    public static final AppEnum TOUR_URL= new AppEnum("tourUrl");
    public static final AppEnum SPLASH_PAGE_URL= new AppEnum("splashPageUrl");
    public static final AppEnum DEFAULT_RANGE_VIEW_TRANS= new AppEnum("defaultRangeViewTrans");
    public static final AppEnum HOME_TAB_URL= new AppEnum("homeTabUrl");
    public static final AppEnum HOME_PAGE_DAYS_BACK =  new AppEnum("OLC HOME PAGE DAYS BACK");
    public static final AppEnum OLCSC_SPANISH_ACTIVE= new AppEnum("OLCSC_SPANISH_ACTIVE");
    public static final AppEnum SEARCH_PARAMETER_SIZE = new AppEnum("searchParameterSize");  
    public static final AppEnum OLCSC_CC_YEARS = new AppEnum("OLCSC_CC_YEARS");
    public static final AppEnum TOLLRATE_URL = new AppEnum("tollRateUrl");
    public static final AppEnum STEP7_MESSAGE_URL = new AppEnum("STEP7_MESSAGE_URL");
    public static final AppEnum OLCSC_TEMP_PLATE_EXPIRATION_LIMIT = new AppEnum("OLCSC_TEMP_PLATE_EXPIRATION_LIMIT");
    

    private AppEnum(String x){
        super(x);
    }

    private AppEnum() {
        
    }
}
