package com.etcc.csc.dto.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.StringUtil;

public class OlcAcctSummaryRecBean implements Serializable {
    /**
     * Unique ID for Serialization with version.
     */
    //Do not regenerate for external customers such as Idea/HCTRA
    private static final long serialVersionUID = 4768675991330085777L;
    private BigDecimal amount;
    private BigDecimal quantity;
    private String description;
    private String groupType;
    private String strAmount; // defect 3813

    /*
        public OlcAcctSummaryRecBean(OlcAcctSummaryRec rec) throws SQLException{
            setAmount(rec.getAmount());
            setQuantity(rec.getQuantity());
            setDescription(rec.getDescription());
        }
        */

    @Deprecated
    @IgnoreProperty
    public String getStrAmount() {
        Logger.getLogger(this.getClass()).warn("Deprecated Method \"getStrAmount\" called -- use JSP Formatters");
        Thread.dumpStack();
        if (this.amount != null) {
            return StringUtil.currencyFormat.format(this.amount.doubleValue());
        }// else
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("amount=");
        sb.append(this.amount);
        sb.append(", description=");
        sb.append(this.description);
        sb.append(", quantity=");
        sb.append(this.quantity);
        sb.append(", groupType=");
        sb.append(this.groupType);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return this.amount;
        // end getAmount
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        // end setAmount
    }

    /**
     * @return the quantity
     */
    public BigDecimal getQuantity() {
        return this.quantity;
        // end getQuantity
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        // end setQuantity
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
        // end getDescription
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
        // end setDescription
    }

    /**
     * @return the groupType
     */
    public String getGroupType() {
        return this.groupType;
        // end getGroupType
    }

    /**
     * @param groupType the groupType to set
     */
    public void setGroupType(String groupType) {
        this.groupType = groupType;
        // end setGroupType
    }

	public void setStrAmount(String strAmount) {
		this.strAmount = strAmount;
	}

}
