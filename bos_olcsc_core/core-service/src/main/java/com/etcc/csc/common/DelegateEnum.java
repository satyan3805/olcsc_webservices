package com.etcc.csc.common;


public class DelegateEnum extends StringEnum{
    
    private int delegate = 0;
    
    public static DelegateEnum ACCOUNT = new DelegateEnum("com.etcc.csc.delegate.AccountDelegate");
    public static DelegateEnum ACCOUNT_HISTORY = new DelegateEnum("com.etcc.csc.delegate.AccountHistoryDelegate");
    public static DelegateEnum ADMIN_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.AdminDelegate");
    public static DelegateEnum APP_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.AppDelegate");
    public static DelegateEnum CREDIT_CARD_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.CreditCardDelegate");
    public static DelegateEnum DB_LOGGER_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.DbLoggerDelegate");
    public static DelegateEnum ERROR_TEST_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.ErrorTestDelegate");
    public static DelegateEnum MENU_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.MenuDelegate");
    public static DelegateEnum ORDER_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.OrderDelegate");
    public static DelegateEnum PASSWORD_RETRIEVAL_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.PasswordRetrievalDelegate");
    public static DelegateEnum SECURITY_QUESTION_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.SecurityQuestionDelegate");
    public static DelegateEnum SESSION_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.SessionDelegate");
    public static DelegateEnum TAG_AUTHORITY_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.TagAuthorityDelegate");
    public static DelegateEnum TAG_REQUEST_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.TagRequestDelegate");
    public static DelegateEnum TOLLTAG_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.TolltagDelegate");
    public static DelegateEnum VEHICLE_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.VehicleDelegate");
    public static DelegateEnum VELCRO_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.VelcroDelegate");
    public static DelegateEnum VIOLATION_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.ViolationDelegate");
    public static DelegateEnum STATE_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.StateDelegate");
    public static DelegateEnum PAYMENT_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.PaymentDelegate");
    public static DelegateEnum ZIPCASH_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.ZipCashDelegate");
    public static DelegateEnum INVENTORY_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.InventoryDelegate");
    public static DelegateEnum COUNTRY_DELEGATE = new DelegateEnum("com.etcc.csc.delegate.CountryDelegate");
    
    private DelegateEnum(String x){
        super(x);
    }
    
    private DelegateEnum() {
    }
    
    
    
}
