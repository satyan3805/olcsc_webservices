package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

/**
 * The agency that authorizes the tag.
 */
public class TagAuthorityDTO extends BaseDTO {
    private long taId;
    private String tagIdentifier;
    private String name;
    private String barcodePrefix;
    private byte taLcId;
    private boolean active;

    public TagAuthorityDTO() {
    }

    public void setTaId(long taId) {
        this.taId = taId;
    }

    public long getTaId() {
        return taId;
    }

    public void setTagIdentifier(String tagIdentifier) {
        this.tagIdentifier = tagIdentifier;
    }

    public String getTagIdentifier() {
        return tagIdentifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBarcodePrefix(String barcodePrefix) {
        this.barcodePrefix = barcodePrefix;
    }

    public String getBarcodePrefix() {
        return barcodePrefix;
    }

    public void setTaLcId(byte taLcId) {
        this.taLcId = taLcId;
    }

    public byte getTaLcId() {
        return taLcId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("taId=");
        sb.append(taId);
        sb.append(", tagIdentifier=");
        sb.append(tagIdentifier);
        sb.append(", name=");
        sb.append(name);
        sb.append(", barcodePrefix=");
        sb.append(barcodePrefix);
        sb.append(", taLcId=");
        sb.append(taLcId);
        sb.append(", active=");
        sb.append(active);
        sb.append("]");
        return sb.toString();
    }
}
