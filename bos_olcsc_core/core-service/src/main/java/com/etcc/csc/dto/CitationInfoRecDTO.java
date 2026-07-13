package com.etcc.csc.dto;

import java.sql.Timestamp;
import java.util.Collections;

import com.etcc.csc.common.BaseDTO;

/**
 * Represents an alert message for a given account.
 */
public class CitationInfoRecDTO extends BaseDTO {
    private Long invoiceId;
    private Long citationId;
    private Long courtId;
    private String courtName;
    private String judge;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String plus4;
    private Timestamp courtDate;
    private String overallAddress;
    private String cityStateZip;

    public CitationInfoRecDTO() {
    }

    public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getCitationId() {
		return citationId;
	}

	public void setCitationId(Long citationId) {
		this.citationId = citationId;
	}

	public Long getCourtId() {
		return courtId;
	}

	public void setCourtId(Long courtId) {
		this.courtId = courtId;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPlus4() {
		return plus4;
	}

	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}

	public Timestamp getCourtDate() {
		return courtDate;
	}

	public void setCourtDate(Timestamp courtDate) {
		this.courtDate = courtDate;
	}

	public String getOverallAddress() {
		return overallAddress;
	}

	public void setOverallAddress(String overallAddress) {
		this.overallAddress = overallAddress;
	}

	public String getCityStateZip() {
		return cityStateZip;
	}

	public void setCityStateZip(String cityStateZip) {
		this.cityStateZip = cityStateZip;
	}

	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("invoiceId=");
        sb.append(invoiceId);
        sb.append("citationId=");
        sb.append(citationId);
        sb.append("courtId=");
        sb.append(courtId);
        sb.append("courtName=");
        sb.append(courtName);
        sb.append("judge=");
        sb.append(judge);
        sb.append("address1=");
        sb.append(address1);
        sb.append("address2=");
        sb.append(address2);
        sb.append("city=");
        sb.append(city);
        sb.append("state=");
        sb.append(state);
        sb.append("zipCode=");
        sb.append(zipCode);
        sb.append("plus4=");
        sb.append(plus4);
        sb.append("courtDate=");
        sb.append(courtDate);
        sb.append("]");
        return sb.toString();
    }
}
