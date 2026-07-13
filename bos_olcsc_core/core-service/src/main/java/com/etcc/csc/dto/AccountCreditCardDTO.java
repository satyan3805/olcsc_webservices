package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Calendar;

public class AccountCreditCardDTO extends BaseDTO {
    private String nameOnCard;
	private String cardNbr;
    private String firstName;
    private String lastName;
    private String cardExpires;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String plus4;
    private Calendar dateApplied;
    private Calendar dateWithdrawn;
    private long acctId;
    private String cardStatus;
    private String cardCode;
    private boolean paymentFailedFlag;
    private Calendar paymentFailedDate;
    private String cardNbrNew;
    private String personalizedName;
    private String paymentType;
    private long retailTransId;
    private double paymentAmount;
    private String tokenId;
    private String accountBillingMethodId;
    private Calendar cardExpryDt;
    private String phone;
    private String phoneExtn;
    private Long personId;
    private Long phoneId;
    private Long addressId;


    public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public void setCardNbr(String cardNbr) {
        this.cardNbr = cardNbr;
    }

    public String getCardNbr() {
        return cardNbr;
    }


    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCardExpires(String cardExpires) {
        this.cardExpires = cardExpires;
    }

    public String getCardExpires() {
        return cardExpires;
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
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setPlus4(String plus4) {
        this.plus4 = plus4;
    }

    public String getPlus4() {
        return plus4;
    }

    public void setDateApplied(Calendar dateApplied) {
        this.dateApplied = dateApplied;
    }

    public Calendar getDateApplied() {
        return dateApplied;
    }

    public void setDateWithdrawn(Calendar dateWithdrawn) {
        this.dateWithdrawn = dateWithdrawn;
    }

    public Calendar getDateWithdrawn() {
        return dateWithdrawn;
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return acctId;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneExtn() {
		return phoneExtn;
	}

	public void setPhoneExtn(String phoneExtn) {
		this.phoneExtn = phoneExtn;
	}

	public void setPaymentFailedFlag(boolean paymentFailedFlag) {
        this.paymentFailedFlag = paymentFailedFlag;
    }

    public boolean isPaymentFailedFlag() {
        return paymentFailedFlag;
    }

    public void setPaymentFailedDate(Calendar paymentFailedDate) {
        this.paymentFailedDate = paymentFailedDate;
    }

    public Calendar getPaymentFailedDate() {
        return paymentFailedDate;
    }

    public void setCardNbrNew(String cardNbrNew) {
        this.cardNbrNew = cardNbrNew;
    }

    public String getCardNbrNew() {
        return cardNbrNew;
    }

    public void setPersonalizedName(String personalizedName) {
        this.personalizedName = personalizedName;
    }

    public String getPersonalizedName() {
        return personalizedName;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }
    public void setRetailTransId(long retailTransId) {
        this.retailTransId = retailTransId;
    }

    public long getRetailTransId() {
        return retailTransId;
    }
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
    
    
    public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getAccountBillingMethodId() {
		return accountBillingMethodId;
	}

	public void setAccountBillingMethodId(String accountBillingMethodId) {
		this.accountBillingMethodId = accountBillingMethodId;
	}
	

	public Calendar getCardExpryDt() {
		return cardExpryDt;
	}

	public void setCardExpryDt(Calendar cardExpryDt) {
		this.cardExpryDt = cardExpryDt;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(Long phoneId) {
		this.phoneId = phoneId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("cardNbr=");
        sb.append(cardNbr);
        sb.append("firstName=");
        sb.append(firstName);
        sb.append("lastName=");
        sb.append(lastName);
        sb.append("cardExpires=");
        sb.append(cardExpires);
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
        sb.append("dateApplied=");
        sb.append(dateApplied);
        sb.append("dateWithdrawn=");
        sb.append(dateWithdrawn);
        sb.append("acctId=");
        sb.append(acctId);
        sb.append("cardStatus=");
        sb.append(cardStatus);
        sb.append("cardCode=");
        sb.append(cardCode);
        sb.append("paymentFailedFlag=");
        sb.append(paymentFailedFlag);
        sb.append("paymentFailedDate=");
        sb.append(paymentFailedDate);
        sb.append("cardNbrNew=");
        sb.append(cardNbrNew);
        sb.append("personalizedName=");
        sb.append(personalizedName);
        sb.append("accountBillingMethodId=");
        sb.append(accountBillingMethodId);
        sb.append("tokenId=");
        sb.append(tokenId);
        sb.append("cardExpryDt=");
        sb.append(cardExpryDt);
        sb.append("phone=");
        sb.append(phone);
        sb.append("phoneEtxn=");
        sb.append(phoneExtn);
        sb.append("]");
        return sb.toString();
    }

}
