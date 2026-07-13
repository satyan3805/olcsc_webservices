package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Calendar;

/**
 * Represents Order data.
 */
public class OrderDTO extends BaseDTO {
    private Calendar orderDate;
    private long acctId;
    private String tagId;
    private String licPlate;
    private String type;
    private int qty;
    private String status;
    private String fullTagId;
    private String location;
    private String isPartial;
    private String isPrepaid;
    private String fulfillmentId;
    private Calendar statusDate;
    private Calendar statusChangedDate;
    private String deliveryMethod;
    private String delPName;
    private String delAdd1;
    private String delAdd2;
    private String city;
    private String state;
    private String zipCode;
    private String isNewAddress = "N";
    private String delPFName;
    private String delPLName;
    private String plus4;
    private String countryCode;
    private Long addrId;
    private Long pickupLocationId;
    private String parentFulfillmentId;
    private String topParentFulfillmentId;
    private int level;
    
    public OrderDTO() {
    }
    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
    }

    public Calendar getOrderDate() {
        return orderDate;
    }

    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }

    public long getAcctId() {
        return acctId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
    }

    public String getLicPlate() {
        return licPlate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setFullTagId(String fullTagId) {
        this.fullTagId = fullTagId;
    }

    public String getFullTagId() {
        return fullTagId;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
    public String getIsPrepaid() {
		return isPrepaid;
	}
	public void setIsPrepaid(String isPrepaid) {
		this.isPrepaid = isPrepaid;
	}
	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("orderDate=");
        sb.append(orderDate.getTime());
        sb.append(", acctId=");
        sb.append(acctId);
        sb.append(", tagId=");
        sb.append(tagId);
        sb.append(", licPlate=");
        sb.append(licPlate);
        sb.append(", type=");
        sb.append(type);
        sb.append(", qty=");
        sb.append(qty);
        sb.append(", status=");
        sb.append(status);
        sb.append(", fullTagId=");
        sb.append(fullTagId);
        sb.append(", isPrepaid=");
        sb.append(isPrepaid);
        sb.append("]");
        return sb.toString();
    }

    public void setStatusDate(Calendar statusDate) {
        this.statusDate = statusDate;
    }

    public Calendar getStatusDate() {
        return statusDate;
    }
	public String getIsPartial() {
		return isPartial;
	}
	public void setIsPartial(String isPartial) {
		this.isPartial = isPartial;
	}
	public String getFulfillmentId() {
		return fulfillmentId;
	}
	public void setFulfillmentId(String fulfillmentId) {
		this.fulfillmentId = fulfillmentId;
	}
	public Calendar getStatusChangedDate() {
		return statusChangedDate;
	}
	public void setStatusChangedDate(Calendar statusChangedDate) {
		this.statusChangedDate = statusChangedDate;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getDelPName() {
		return delPName;
	}
	public void setDelPName(String delPName) {
		this.delPName = delPName;
	}
	public String getDelAdd1() {
		return delAdd1;
	}
	public void setDelAdd1(String delAdd1) {
		this.delAdd1 = delAdd1;
	}
	public String getDelAdd2() {
		return delAdd2;
	}
	public void setDelAdd2(String delAdd2) {
		this.delAdd2 = delAdd2;
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
	public String getIsNewAddress() {
		return this.isNewAddress;
	}
	public void setIsNewAddress(String isNewAddress) {
		this.isNewAddress = isNewAddress;
	}
	public String getDelPFName() {
		return delPFName;
	}
	public void setDelPFName(String delPFName) {
		this.delPFName = delPFName;
	}
	public String getDelPLName() {
		return delPLName;
	}
	public void setDelPLName(String delPLName) {
		this.delPLName = delPLName;
	}
	public String getPlus4() {
		return plus4;
	}
	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Long getAddrId() {
		return addrId;
	}
	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}
	public Long getPickupLocationId() {
		return pickupLocationId;
	}
	public void setPickupLocationId(Long pickupLocationId) {
		this.pickupLocationId = pickupLocationId;
	}
	public String getParentFulfillmentId() {
		return parentFulfillmentId;
	}
	public void setParentFulfillmentId(String parentFulfillmentId) {
		this.parentFulfillmentId = parentFulfillmentId;
	}
	public String getTopParentFulfillmentId() {
		return topParentFulfillmentId;
	}
	public void setTopParentFulfillmentId(String topParentFulfillmentId) {
		this.topParentFulfillmentId = topParentFulfillmentId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
    
}
