package com.etcc.csc.dto.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class OlcTagSummaryRecBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4735490367042494392L;
    private BigDecimal amount;
    private BigDecimal quantity;
    private String description;
    private String licensePlate;
    private String unitId;
    private String tagId;
    
    
    /*
    public OlcTagSummaryRecBean (OlcTagSummaryRec rec) throws SQLException{
        setAmount(rec.getAmount());
        setQuantity(rec.getQuantity());
        setDescription(rec.getDescription());
        setLicensePlate(rec.getLicensePlate());
        setUnitId(rec.getUnitId());
        setTagId(rec.getTagId());
    }
*/

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return this.tagId;
    }

}
