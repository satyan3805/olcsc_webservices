package com.etcc.csc.dto;

/**
 * Based on VelcroDTO from OLCSCService module.
 */
public class VelcroDTO extends BaseDTO implements AddressUS {
 	private static final long serialVersionUID = 1454285005173842733L;

 	private String name;
    private long acctId;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String plus4;
    private int activeTolltag;
    private int orderQty;
    private String companyName;
    
    public VelcroDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return acctId;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setZipCode(String zipCode) {
        this.zip = zipCode;
    }

    public String getZipCode() {
        return zip;
    }

    public void setPlus4(String plus4) {
        this.plus4 = plus4;
    }

    public String getPlus4() {
        return plus4;
    }

    public void setActiveTolltag(int activeTolltag) {
        this.activeTolltag = activeTolltag;
    }

    public int getActiveTolltag() {
        return activeTolltag;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public int getOrderQty() {
        return orderQty;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("name=");
        sb.append(name);
        sb.append("companyName=");
        sb.append(companyName);
        sb.append(", acctId=");
        sb.append(acctId);
        sb.append(", address1=");
        sb.append(address1);
        sb.append(", address2=");
        sb.append(address2);
        sb.append(", city=");
        sb.append(city);
        sb.append(", state=");
        sb.append(state);
        sb.append(", zipCode=");
        sb.append(zip);
        sb.append(", plus4=");
        sb.append(plus4);
        sb.append(", activeTolltag=");
        sb.append(activeTolltag);
        sb.append(", orderQty=");
        sb.append(orderQty);
        sb.append("]");
        return sb.toString();
    }

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
