package com.etcc.csc.datatype;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import oracle.jdbc.OracleTypes;

public class OlcPaymentInfoRec implements SQLData {
	public static final String _SQL_NAME = "OL_OWNER.OLC_PAYMENT_INFO_REC";
	public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

	private String m_pmtType;
	private String m_cardCode;
	private String m_cardNbr;
	private java.sql.Timestamp m_cardExpires;
	private String m_nameOnCard;
	private String m_address1;
	private String m_address2;
	private String m_city;
	private String m_state;
	private String m_zipCode;
	private String m_plus4;
	private String m_bankAcctType;
	private String m_bankAcctNumber;
	private String m_routingNbr;
	private String m_tokenId;
	private String m_accountBillingMethodId;

	/* constructor */
	public OlcPaymentInfoRec() {
	}

	public OlcPaymentInfoRec(String pmtType, String cardCode, String cardNbr,
			java.sql.Timestamp cardExpires, String nameOnCard, String address1,
			String address2, String city, String state, String zipCode,
			String plus4, String bankAcctType, String bankAcctNumber,
			String routingNbr) throws SQLException {
		setPmtType(pmtType);
		setCardCode(cardCode);
		setCardNbr(cardNbr);
		setCardExpires(cardExpires);
		setNameOnCard(nameOnCard);
		setAddress1(address1);
		setAddress2(address2);
		setCity(city);
		setState(state);
		setZipCode(zipCode);
		setPlus4(plus4);
		setBankAcctType(bankAcctType);
		setBankAcctNumber(bankAcctNumber);
		setRoutingNbr(routingNbr);
	}

	public void readSQL(SQLInput stream, String type) throws SQLException {
		setPmtType(stream.readString());
		setCardCode(stream.readString());
		setCardNbr(stream.readString());
		setCardExpires(stream.readTimestamp());
		setNameOnCard(stream.readString());
		setAddress1(stream.readString());
		setAddress2(stream.readString());
		setCity(stream.readString());
		setState(stream.readString());
		setZipCode(stream.readString());
		setPlus4(stream.readString());
		setBankAcctType(stream.readString());
		setBankAcctNumber(stream.readString());
		setRoutingNbr(stream.readString());
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		stream.writeString(getPmtType());
		stream.writeString(getCardCode());
		stream.writeString(getCardNbr());
		stream.writeTimestamp(getCardExpires());
		stream.writeString(getNameOnCard());
		stream.writeString(getAddress1());
		stream.writeString(getAddress2());
		stream.writeString(getCity());
		stream.writeString(getState());
		stream.writeString(getZipCode());
		stream.writeString(getPlus4());
		stream.writeString(getBankAcctType());
		stream.writeString(getBankAcctNumber());
		stream.writeString(getRoutingNbr());
	}

	public String getSQLTypeName() throws SQLException {
		return _SQL_NAME;
	}

	/* accessor methods */
	public String getPmtType() {
		return m_pmtType;
	}

	public void setPmtType(String pmtType) {
		m_pmtType = pmtType;
	}

	public String getCardCode() {
		return m_cardCode;
	}

	public void setCardCode(String cardCode) {
		m_cardCode = cardCode;
	}

	public String getCardNbr() {
		return m_cardNbr;
	}

	public void setCardNbr(String cardNbr) {
		m_cardNbr = cardNbr;
	}

	public java.sql.Timestamp getCardExpires() {
		return m_cardExpires;
	}

	public void setCardExpires(java.sql.Timestamp cardExpires) {
		m_cardExpires = cardExpires;
	}

	public String getNameOnCard() {
		return m_nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		m_nameOnCard = nameOnCard;
	}

	public String getAddress1() {
		return m_address1;
	}

	public void setAddress1(String address1) {
		m_address1 = address1;
	}

	public String getAddress2() {
		return m_address2;
	}

	public void setAddress2(String address2) {
		m_address2 = address2;
	}

	public String getCity() {
		return m_city;
	}

	public void setCity(String city) {
		m_city = city;
	}

	public String getState() {
		return m_state;
	}

	public void setState(String state) {
		m_state = state;
	}

	public String getZipCode() {
		return m_zipCode;
	}

	public void setZipCode(String zipCode) {
		m_zipCode = zipCode;
	}

	public String getPlus4() {
		return m_plus4;
	}

	public void setPlus4(String plus4) {
		m_plus4 = plus4;
	}

	public String getBankAcctType() {
		return m_bankAcctType;
	}

	public void setBankAcctType(String bankAcctType) {
		m_bankAcctType = bankAcctType;
	}

	public String getBankAcctNumber() {
		return m_bankAcctNumber;
	}

	public void setBankAcctNumber(String bankAcctNumber) {
		m_bankAcctNumber = bankAcctNumber;
	}

	public String getRoutingNbr() {
		return m_routingNbr;
	}

	public void setRoutingNbr(String routingNbr) {
		m_routingNbr = routingNbr;
	}

	public String getTokenId() {
		return m_tokenId;
	}

	public void setTokenId(String tokenId) {
		m_tokenId = tokenId;
	}

	public String getAccountBillingMethodId() {
		return m_accountBillingMethodId;
	}

	public void setAccountBillingMethodId(String accountBillingMethodId) {
		m_accountBillingMethodId = accountBillingMethodId;
	}

}
