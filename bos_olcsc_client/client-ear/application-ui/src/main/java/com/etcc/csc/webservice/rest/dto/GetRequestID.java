package com.etcc.csc.webservice.rest.dto;

public class GetRequestID {

	public String accountid;
	public String transactionType;
	public String customerid;
	public String userid;
	public String language;
	public String companycode;
	public String currencycode;
	public String customername;
	public String paymentmethod;
	public String transactionamount;
	public String openiniframe;
	public String redirecturl;
	public String stylecss;

	/**
	 * @return the accountid
	 */
	public String getAccountid() {
		return accountid;
	}

	/**
	 * @param accountid
	 *            the accountid to set
	 */
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the customerid
	 */
	public String getCustomerid() {
		return customerid;
	}

	/**
	 * @param customerid
	 *            the customerid to set
	 */
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the companycode
	 */
	public String getCompanycode() {
		return companycode;
	}

	/**
	 * @param companycode
	 *            the companycode to set
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	/**
	 * @return the currencycode
	 */
	public String getCurrencycode() {
		return currencycode;
	}

	/**
	 * @param currencycode
	 *            the currencycode to set
	 */
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	/**
	 * @return the customername
	 */
	public String getCustomername() {
		return customername;
	}

	/**
	 * @param customername
	 *            the customername to set
	 */
	public void setCustomername(String customername) {
		this.customername = customername;
	}

	/**
	 * @return the paymentmethod
	 */
	public String getPaymentmethod() {
		return paymentmethod;
	}

	/**
	 * @param paymentmethod
	 *            the paymentmethod to set
	 */
	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}

	/**
	 * @return the transactionamount
	 */
	public String getTransactionamount() {
		return transactionamount;
	}

	/**
	 * @param transactionamount
	 *            the transactionamount to set
	 */
	public void setTransactionamount(String transactionamount) {
		this.transactionamount = transactionamount;
	}

	/**
	 * @return the openiniframe
	 */
	public String getOpeniniframe() {
		return openiniframe;
	}

	/**
	 * @param openiniframe
	 *            the openiniframe to set
	 */
	public void setOpeniniframe(String openiniframe) {
		this.openiniframe = openiniframe;
	}

	/**
	 * @return the redirecturl
	 */
	public String getRedirecturl() {
		return redirecturl;
	}

	/**
	 * @param redirecturl
	 *            the redirecturl to set
	 */
	public void setRedirecturl(String redirecturl) {
		this.redirecturl = redirecturl;
	}

	/**
	 * @return the stylecss
	 */
	public String getStylecss() {
		return stylecss;
	}

	/**
	 * @param stylecss
	 *            the stylecss to set
	 */
	public void setStylecss(String stylecss) {
		this.stylecss = stylecss;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GetRequestID [accountid=" + accountid + ", transactionType=" + transactionType + ", customerid="
				+ customerid + ", userid=" + userid + ", language=" + language + ", companycode=" + companycode
				+ ", currencycode=" + currencycode + ", customername=" + customername + ", paymentmethod="
				+ paymentmethod + ", transactionamount=" + transactionamount + ", openiniframe=" + openiniframe
				+ ", redirecturl=" + redirecturl + ", stylecss=" + stylecss + "]";
	}

}
