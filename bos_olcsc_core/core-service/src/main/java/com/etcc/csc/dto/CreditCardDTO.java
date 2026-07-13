package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

public class CreditCardDTO extends BaseDTO {
    private String cardCode;
    private String cardName;
    private byte cardNbrLength;
    private String cardNbrPrefix;
    private byte cardTypeOrder;
    private boolean defaultValueFlag;
    private boolean activeFlag;

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardNbrLength(byte cardNbrLength) {
        this.cardNbrLength = cardNbrLength;
    }

    public byte getCardNbrLength() {
        return cardNbrLength;
    }

    public void setCardNbrPrefix(String cardNbrPrefix) {
        this.cardNbrPrefix = cardNbrPrefix;
    }

    public String getCardNbrPrefix() {
        return cardNbrPrefix;
    }

    public void setCardTypeOrder(byte cardTypeOrder) {
        this.cardTypeOrder = cardTypeOrder;
    }

    public byte getCardTypeOrder() {
        return cardTypeOrder;
    }

    public void setDefaultValueFlag(boolean defaultValueFlag) {
        this.defaultValueFlag = defaultValueFlag;
    }

    public boolean isDefaultValueFlag() {
        return defaultValueFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("cardCode=");
        sb.append(cardCode);
        sb.append("cardName=");
        sb.append(cardName);
        sb.append("cardNbrLength=");
        sb.append(cardNbrLength);
        sb.append("cardNbrPrefix=");
        sb.append(cardNbrPrefix);
        sb.append("cardTypeOrder=");
        sb.append(cardTypeOrder);
        sb.append("defaultValueFlag=");
        sb.append(defaultValueFlag);
        sb.append("activeFlag=");
        sb.append(activeFlag);
        sb.append("]");
        return sb.toString();
    }

}
