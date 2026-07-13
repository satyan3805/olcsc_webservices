package com.etcc.csc.common;

public class ServiceObjectEnum extends StringEnum {

	public static ServiceObjectEnum ACCOUNT = new ServiceObjectEnum(
			"com.etcc.csc.service.Account");
	public static ServiceObjectEnum ACCOUNT_HISTORY = new ServiceObjectEnum(
			"com.etcc.csc.service.AccountHistory");
	public static ServiceObjectEnum ADMIN = new ServiceObjectEnum(
			"com.etcc.csc.service.Admin");
	public static ServiceObjectEnum APP = new ServiceObjectEnum(
			"com.etcc.csc.service.App");
	public static ServiceObjectEnum CREDIT_CARD = new ServiceObjectEnum(
			"com.etcc.csc.service.CreditCard");
	public static ServiceObjectEnum DB_LOGGER = new ServiceObjectEnum(
			"com.etcc.csc.service.DbLogger");
	public static ServiceObjectEnum ERROR_TEST = new ServiceObjectEnum(
			"com.etcc.csc.service.ErrorTest");
	public static ServiceObjectEnum MENU = new ServiceObjectEnum(
			"com.etcc.csc.service.Menu");
	public static ServiceObjectEnum ORDER = new ServiceObjectEnum(
			"com.etcc.csc.service.Order");
	public static ServiceObjectEnum PASSWORD_RETRIEVAL = new ServiceObjectEnum(
			"com.etcc.csc.service.PasswordRetrieval");
	public static ServiceObjectEnum PAYMENT = new ServiceObjectEnum(
			"com.etcc.csc.service.Payment");
	public static ServiceObjectEnum SECURITY_QUESTION = new ServiceObjectEnum(
			"com.etcc.csc.service.SecurityQuestion");
	public static ServiceObjectEnum SESSION = new ServiceObjectEnum(
			"com.etcc.csc.service.Session");
	public static ServiceObjectEnum STATE = new ServiceObjectEnum(
			"com.etcc.csc.service.State");
	public static ServiceObjectEnum TAG_AUTHORITY = new ServiceObjectEnum(
			"com.etcc.csc.service.TagAuthority");
	public static ServiceObjectEnum TAG_REQUEST = new ServiceObjectEnum(
			"com.etcc.csc.service.TagRequest");
	public static ServiceObjectEnum TOLLTAG = new ServiceObjectEnum(
			"com.etcc.csc.service.Tolltag");
	public static ServiceObjectEnum VEHICLE = new ServiceObjectEnum(
			"com.etcc.csc.service.Vehicle");
	public static ServiceObjectEnum VELCRO = new ServiceObjectEnum(
			"com.etcc.csc.service.Velcro");
	public static ServiceObjectEnum VIOLATION = new ServiceObjectEnum(
			"com.etcc.csc.service.Violation");
	public static ServiceObjectEnum ZIPCASH = new ServiceObjectEnum(
			"com.etcc.csc.service.ZipCash");
	public static ServiceObjectEnum INVENTORY = new ServiceObjectEnum(
			"com.etcc.csc.service.Inventory");
	public static ServiceObjectEnum COUNTRY = new ServiceObjectEnum(
			"com.etcc.csc.service.Country");

	private ServiceObjectEnum(String forName) {
		super(forName);
	}

	private ServiceObjectEnum() {
	}
}
