/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.core.IsNot;
import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BillingInfoDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.util.StringUtil;

/**
 * Generates the return values for the methods needed for the UI Tests (and demos).
 * Also serves as a utility object for Account related operations for testing.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 * @see AccountTestImpl
 * @see ServiceFactoryTest
 */
public class AccountFactory {
    /**
     * Basic Account ID.
     */
    public static final long BASIC_ACCOUNT_ID = 100;
    /**
     * Account ID that should be populated with data and alerts.
     */
    public static final long POPULATED_ACCOUNT_ID = 100000;

    public static final String ACCOUNT_USERNAME = "bhhill";
    public static final String ACCOUNT_NAME_FIRST = "Bobby";
    public static final String ACCOUNT_NAME_MIDDLE = "H";
    public static final String ACCOUNT_NAME_LAST = "Hill";
    public static final String ACCOUNT_COMPANY_NAME = "Acme Corporation";
    public static final double ACCOUNT_BALANCE = 15.0;
    public static final String LICENSE_STATE = "TX";
    public static final String LICENSE_PLATE = "DFWHCTRA";

    public static final String ADDRESS_LINE1 = "123 Main St";
    public static final String ADDRESS_CITY = "Anywhere";
    public static final String ADDRESS_STATE = "TX";
    public static final String ADDRESS_ZIP = "77777";

    public static final String CREDITCARD_VISA = "4111111111111111";
    public static final String CREDITCARD_MC = "5499740000000057";
    public static final String CREDITCARD_DISC = "6011000991001201";
    public static final String CREDITCARD_AMEX = "371449635392376";
    public static final String CREDITCARD_DINERS = "38555565010005";
    public static final String CREDITCARD_DINERS_MC = "36050000000003";
    public static final String CREDITCARD_JCB = "3530142019955809";

    /**
     * Matcher that asserts that a JavaBean is empty.
     * Empty is defined as the object being either <tt>null</tt>, or a
     * collection of zero elements or a zero-length string.
     * @author Milosh Boroyevich
     */
    static class IsEmpty<T> extends BaseMatcher<T> {
        public void describeTo(Description description) {
            description.appendText("isEmpty()");
        }

        @Factory
        public static <T> Matcher<T> aEmpty(Class<T> type) {
            return new IsEmpty<T>();
        }

        @Factory
        public static <T> Matcher<T> aNonEmpty(Class<T> type) {
            return new IsNot<T>(new IsEmpty<T>());
        }

		@SuppressWarnings("unchecked")
		public boolean matches(Object obj) {
			if (obj == null)
				return true;
			Class theClass = obj.getClass();
			if (theClass == String.class)
				return StringUtil.isEmpty((String) obj);
			else if (theClass == Collection.class)
				return ((Collection) obj).isEmpty();
			else if (theClass == Map.class)
				return ((Map) obj).isEmpty();
			return false;
		}
    }

    public static <T> Matcher<T> hasProperty(Class<T> type, String propertyName, Matcher<? extends Object> value) {
        return new HasPropertyWithValue<T>(propertyName, value);
    }

    /**
     * What is causing this???
     * <pre>
2010-01-08 22:41:06,587 DEBUG [HTTPThreadGroup-9] violation.ViolationLoginAccountAction (ViolationLoginAccountAction.java:89)     - Error validating EZ account login for violation payment.
unexpected invocation: accountInterface.getAccount(<[acctId=0, lastLoginIp=10.200.11.31, dbSessionId=764aa568a3bfc44849227265e46b87b7f78b0ecd2829cdd54ce27af002955304, loginType=IN, acctStatus=null, acctActivity=null, invoiceId=, violatorId=0, licPlate=0934HTF]>)
expectations:
  allowed, never invoked: accountInterface.getAccount(hasProperty("loginType", "AC")); returns <[acctId=100firstName=BobbymiddleInitial=nulllastName=Hilladdress1=nulladdress2=nullcity=nullstate=nullzipCode=nullplus4=nullhomePhoNbr=nullworkPhoNbr=nullworkPhoExt=nulldriverLicNbr=nulldriverLicState=nullcompanyName=nullcompanyTaxId=nullemailAddress=nullmoStmtFlag=falsebadAddressFlag=falserebillFailedFlag=falserebillAmt=0.0rebillDate=nulldepAmt=0.0balanceAmt=0.0lowBalLevel=0.0balLastUpdated=nullacctStatusCode=nullacctTypeCode=nulladdressModified=nullpmtTypeCode=nulldateApproved=nullapprovedBy=nullselectedForRebill=falsemsId=0veaFlag=falseveaDate=nullveaExpireDate=nullsecurityQuestion=nullsecurityQuestionAnswer=nullcompanyCode=nulladjustRebillAmt=falsevpnType=nullmobilePhoNbr=nullemailAddress2=nullemailAddress3=null]>
  allowed, never invoked: accountInterface.getAccount(ANYTHING); returns null
  allowed, never invoked: accountInterface.loginAccount(not isEmpty(), not isEmpty(), not isEmpty(), not isEmpty(), not null); returns <[acctId=100, lastLoginIp=, dbSessionId=null, loginType=AC, acctStatus=null, acctActivity=null, invoiceId=null, violatorId=0, licPlate=null]>
  allowed, never invoked: accountInterface.loginAccount(not null, not null, not null, not null, not null); returns <[acctId=0, lastLoginIp=null, dbSessionId=null, loginType=null, acctStatus=null, acctActivity=null, invoiceId=null, violatorId=0, licPlate=null]>
  allowed, never invoked: accountInterface.getAccountTags(null); returns null
  allowed, never invoked: accountInterface.getAccountTags(not null); returns [<[com.etcc.csc.dto.TagDTO@4e6138,acctId=100, agencyId=null, tagId=null, acctTagStatus=null, licPlate=DFWHCTRA, licState=TX, vehicleDescr=null, vehicleMake=null, vehicleModel=null, vehicleYear=null, vehicleColor=null, vehicleClassCode=null, assignedDate=null, expirDate=null, tagReadCt=0, acctTagComments=null, vpnId=0, unitId=null, plateExpirDate=null, temporaryLicPlate=null, tagStatusDesc=null, vehicleClassDesc=null, barcodePrefix=null, acctTagSeq=0, checkDup=false, dup=false, transactionId=0, plateExpiration=null, nickname=null, motorcycle=false, tagAmount=0.0, violationExist=false, tagSalesAmt=0.0, depositAmt=0.0, totalAmt=0.0, paymentType=null, pbpStart=null, pbpEnd=null, tagTypeCode=null, startIndex=0, acctSuspended=false, activePbpTagExist=false]>]
what happened before this: nothing!
     * </pre>
     * @param f
     * @param mocked
     */
    // Package level. The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final AccountInterface mocked) {
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations() {
                {
                    // Note: If the exact values are NOT passed as parameters, the getAccount will throw an Exception.
                    // So, set up to use "Wildcards".
                    allowing(mocked).getAccount(with(hasProperty(AccountLoginDTO.class, "loginType", equal(AccountLoginDTO.LoginType.AC))));
                    will(returnValue(getAccountDTO()));
                    allowing(mocked).getAccount(with(aNull(AccountLoginDTO.class)));
                    will(returnValue(null));
                    allowing(mocked).getAccount(with(aNonNull(AccountLoginDTO.class)));
                    will(returnValue(null));

                    allowing(mocked).loginAccount(with(IsEmpty.aNonEmpty(String.class)), with(IsEmpty.aNonEmpty(String.class)),
                            with(IsEmpty.aNonEmpty(String.class)), with(IsEmpty.aNonEmpty(String.class)),
                            with(aNonNull(UserEnvDTO.class)));
                    will(returnValue(getAccountLoginDTO()));
                    allowing(mocked).loginAccount(with(aNonNull(String.class)), with(aNonNull(String.class)),
                            with(aNonNull(String.class)), with(aNonNull(String.class)),
                            with(aNonNull(UserEnvDTO.class)));
                    will(returnValue(getAccountLoginError()));

                    allowing(mocked).getAccountTags(with(aNull(AccountLoginDTO.class)));
                    will(returnValue(null));
                    allowing(mocked).getAccountTags(with(aNonNull(AccountLoginDTO.class)));
                    will(returnValue(getAccountTagDTOs()));
                }
            });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Returns an account using the account login obtained from {@link #getAccountLoginDTO()}.
     * @return an account
     * @see #getAccount(AccountLoginDTO)
     */
    static AccountDTO getAccountDTO() {
        return getAccount(getAccountLoginDTO());
    }

    /**
     * Returns an account with an id the same as the specified account login.
     * @param acctLoginDto account login to use for setting the account id
     * @return an account
     */
    public static AccountDTO getAccount(AccountLoginDTO acctLoginDto) {
        AccountDTO account = null;
        if (/*acctLoginDto != null && */hasProperty(AccountLoginDTO.class, "loginType", Expectations.equal(AccountLoginDTO.LoginType.AC)).matches(acctLoginDto)) {
            account = new AccountDTO();
            account.setAcctId(acctLoginDto.getAcctId());
            account.setFirstName(ACCOUNT_NAME_FIRST);
            account.setMiddleInitial(ACCOUNT_NAME_MIDDLE);
            account.setLastName(ACCOUNT_NAME_LAST);
            account.setCompanyName(ACCOUNT_COMPANY_NAME);
            account.setRevenueAccount(true);
            account.setSuspensionFlag(false);
            account.setBalanceAmt(ACCOUNT_BALANCE);
            account.setAddress1(ADDRESS_LINE1);
            account.setCity(ADDRESS_CITY);
            account.setState(ADDRESS_STATE);
            account.setZip(ADDRESS_ZIP);
//          account.setViolationFlag(true);
        }
        return account;
    }

    /**
     * Generate a Account DTO for testing with the supplied accountID.
     * @param accountId The ID of the AccountDTO
     * @return the accountDTO with its id set to to the supplied accountId.
     */
    public static AccountDTO getAccountDTO(final long accountId) {
        AccountDTO testAccount = new AccountDTO();
        testAccount.setAcctId(accountId);
        testAccount.setFirstName("JUnit");
        testAccount.setLastName("TestAccount");
        return testAccount;
    }

	/**
	 * Get an AccountLoginDTO for test purposes.
	 * @return dto with dummy values loaded.
	 */
    public static AccountLoginDTO getAccountLoginDTO() {
        AccountLoginDTO acctLogin = new AccountLoginDTO();
        acctLogin.setAcctId(POPULATED_ACCOUNT_ID);
        acctLogin.setLoginId(ACCOUNT_USERNAME);
        acctLogin.setLastLoginIp("");
        acctLogin.setPwChangeRequired(false);
        acctLogin.setLocked(false);
        acctLogin.setLoginType(AccountLoginDTO.LoginType.AC);
        return acctLogin;
    }

    /**
     * Generate a Account Login DTO for testing with the supplied accountID
     * @param accountId The ID of the AccountLoginDTO
     * @return the accountLoginDTO with its id set to to the supplied accountId.
     */
    public static AccountLoginDTO getAccountLoginDTO(final long accountId) {
        AccountLoginDTO loginDTO = new AccountLoginDTO();
        loginDTO.setAcctId(accountId);
        return loginDTO;
    }

    static AccountLoginDTO getAccountLoginError() {
    	// get error message
        AccountLoginDTO acctLogin = new AccountLoginDTO();
        ErrorMessageDTO error = new ErrorMessageDTO("loginError", "Invalid username/password combination.");
        acctLogin.setErrors(new ErrorMessageDTO[] {error});
        return acctLogin;
    }

    public static TagDTO[] getAccountTagDTOs() {
    	TagDTO tag = new TagDTO();
    	tag.setAcctId(POPULATED_ACCOUNT_ID);
    	tag.setLicState(LICENSE_STATE);
    	tag.setLicPlate(LICENSE_PLATE);
    	return new TagDTO[] {tag};
    }

    public static BillingInfoDTO getBillingInfo(AccountLoginDTO acctLoginDto) {
        BillingInfoDTO result = new BillingInfoDTO();
    	if (acctLoginDto.getLoginId().equalsIgnoreCase(ACCOUNT_USERNAME)) {
    	    // EFT
    	    AccountEFTDTO eft = new AccountEFTDTO();
    	    eft.setAccountNumber("321654");
    	    eft.setRoutingNumber("123456789");
    	    eft.setAddress1(ADDRESS_LINE1);
    	    eft.setCity(ADDRESS_CITY);
    	    eft.setState(ADDRESS_STATE);
    	    eft.setZipCode(ADDRESS_ZIP);
    	    eft.setAccountType(AccountEFTDTO.BankAccountType.PERSONAL);
    	    result.setEft(eft);
        } else { // default
            // Credit Card
            AccountCreditCardDTO cc = new AccountCreditCardDTO();
            Calendar cardExpires = Calendar.getInstance();
            cardExpires.add(Calendar.MONTH, -1);  // set card to be expired
            cc.setCardExpiresDate(cardExpires.getTime());
            cc.setCardType(CreditCardDTO.CreditCardType.DISCOVER);
            cc.setCardNbr(CREDITCARD_DISC);
            cc.setAddress1(ADDRESS_LINE1);
            cc.setCity(ADDRESS_CITY);
            cc.setState(ADDRESS_STATE);
            cc.setZipCode(ADDRESS_ZIP);
            cc.setNameOnCard(ACCOUNT_COMPANY_NAME);
            cc.setPrimary(true);
            result.setCards(new AccountCreditCardDTO[] {cc});
        }
        return result;
    }
}
