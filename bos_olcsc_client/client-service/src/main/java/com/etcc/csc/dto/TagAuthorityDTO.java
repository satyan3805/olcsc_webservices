package com.etcc.csc.dto;

/**
 * The authority that issues the physical tag.
 * Note: This is distinct and different from an Agency.
 * Specifically, the expected set of authorities (from the DB) would look like:
 * <pre>
 * <b>ID   Identifier   Name                 Barcode</b>
 * 5    HCTRA        Houston Tollway      HCTR
 * 3    TEX          Texas Tollway Auth   TEX.
 * 6    TXDT         TX Dept Trans        TXDT
 * 7    OTA          OK Turnpike Auth     OTA.
 * </pre>
 */
// Moved from OLCSCService module.
public class TagAuthorityDTO extends BaseDTO {
	private static final long serialVersionUID = 5944185117955612950L;
	private long taId;
    private String tagIdentifier;
    private String name;
    private String barcodePrefix;
    private Byte taLcId;
    private boolean active;
    private boolean defaultValueFlag = false;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("taId=");
        sb.append(this.taId);
        sb.append(", tagIdentifier=");
        sb.append(this.tagIdentifier);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", barcodePrefix=");
        sb.append(this.barcodePrefix);
        sb.append(", taLcId=");
        sb.append(this.taLcId);
        sb.append(", active=");
        sb.append(this.active);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the taId
     */
    public long getTaId() {
        return this.taId;
        //end getTaId
    }

    /**
     * @param taId the taId to set
     */
    public void setTaId(long taId) {
        this.taId = taId;
        //end setTaId
    }

    /**
     * @return the tagIdentifier
     */
    public String getTagIdentifier() {
        return this.tagIdentifier;
        //end getTagIdentifier
    }

    /**
     * @param tagIdentifier the tagIdentifier to set
     */
    public void setTagIdentifier(String tagIdentifier) {
        this.tagIdentifier = tagIdentifier;
        //end setTagIdentifier
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
        //end getName
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
        //end setName
    }

    /**
     * @return the barcodePrefix
     */
    public String getBarcodePrefix() {
        return this.barcodePrefix;
        //end getBarcodePrefix
    }

    /**
     * @param barcodePrefix the barcodePrefix to set
     */
    public void setBarcodePrefix(String barcodePrefix) {
        this.barcodePrefix = barcodePrefix;
        //end setBarcodePrefix
    }

    /**
     * @return the taLcId
     */
    public Byte getTaLcId() {
        return this.taLcId;
        //end getTaLcId
    }

    /**
     * @param taLcId the taLcId to set
     */
    public void setTaLcId(Byte taLcId) {
        this.taLcId = taLcId;
        //end setTaLcId
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return this.active;
        //end isActive
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
        //end setActive
    }

    /**
     * @return the defaultValueFlag
     */
    public boolean isDefaultValueFlag() {
        return this.defaultValueFlag;
        //end isDefaultValueFlag
    }

    /**
     * @param defaultValueFlag the defaultValueFlag to set
     */
    public void setDefaultValueFlag(boolean defaultValueFlag) {
        this.defaultValueFlag = defaultValueFlag;
        //end setDefaultValueFlag
    }
}
