/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Representation of a payment plan.
 * 
 */
public class PaymentPlanDTO extends BaseDTO {

    /**
     * Unique ID for serialization with version.
     */
    // Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = 4275316481609749748L;

    /**
     * Details of the payment plan.
     */
    public class PaymentPlanDetailDTO implements Serializable {
        /**
         * Unique ID for serialization with version.
         */
        // Do NOT regenerate for external clients such as Idea.
        private static final long serialVersionUID = 2826498411443404705L;

        private Calendar dueDate;
        private float amtDue;
        private Calendar payDate;
        private String payMethod;
        private float amt;
        private String clerk;

        // public String getDisplayDueDate() {
        // if (dueDate != null) {
        // return String.format("%1$tm/%1$td/%1$tY", dueDate);
        // } else {
        // return "";
        // }
        // }
        // public String getDisplayAmtDue() {
        // if (amtDue > 0) {
        // return String.format("%9.2f", new Float(amtDue));
        // } else {
        // return "-";
        // }
        // }
        // public String getDisplayPayDate() {
        // if (payDate != null) {
        // return String.format("%1$tm/%1$td/%1$tY", payDate);
        // } else {
        // return "";
        // }
        // }
        // public String getDisplayAmt() {
        // if (amt > 0) {
        // return String.format("%9.2f", new Float(amt));
        // } else {
        // return "-";
        // }
        // }
        /**
         * Gets the Display formatted clerk, if available. This should be handle by the JSP!
         * 
         * @return clerk formatted for display.
         * @deprecated
         */
        @Deprecated
        //BUG: This should be handled by the JSP/UI!
        public String getDisplayClerk() {
            if ((this.clerk != null) && (this.clerk.trim().length() > 0)) {
                return this.clerk;
            } // else
            return "-";
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append("dueDate=");
            sb.append(this.dueDate);
            sb.append(", amtDue=");
            sb.append(this.amtDue);
            sb.append(", payDate=");
            sb.append(this.payDate);
            sb.append(", payMethod=");
            sb.append(this.payMethod);
            sb.append(", amt=");
            sb.append(this.amt);
            sb.append(", clerk=");
            sb.append(this.clerk);
            sb.append("]");
            return sb.toString();
        }

        /**
         * @return the dueDate
         */
        public Calendar getDueDate() {
            return this.dueDate;
            // end getDueDate
        }

        /**
         * @param dueDate the dueDate to set
         */
        public void setDueDate(Calendar dueDate) {
            this.dueDate = dueDate;
            // end setDueDate
        }

        /**
         * @return the amtDue
         */
        public float getAmtDue() {
            return this.amtDue;
            // end getAmtDue
        }

        /**
         * @param amtDue the amtDue to set
         */
        public void setAmtDue(float amtDue) {
            this.amtDue = amtDue;
            // end setAmtDue
        }

        /**
         * @return the payDate
         */
        public Calendar getPayDate() {
            return this.payDate;
            // end getPayDate
        }

        /**
         * @param payDate the payDate to set
         */
        public void setPayDate(Calendar payDate) {
            this.payDate = payDate;
            // end setPayDate
        }

        /**
         * @return the payMethod
         */
        public String getPayMethod() {
            return this.payMethod;
            // end getPayMethod
        }

        /**
         * @param payMethod the payMethod to set
         */
        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
            // end setPayMethod
        }

        /**
         * @return the amt
         */
        public float getAmt() {
            return this.amt;
            // end getAmt
        }

        /**
         * @param amt the amt to set
         */
        public void setAmt(float amt) {
            this.amt = amt;
            // end setAmt
        }

        /**
         * @return the clerk
         */
        public String getClerk() {
            return this.clerk;
            // end getClerk
        }

        /**
         * @param clerk the clerk to set
         */
        public void setClerk(String clerk) {
            this.clerk = clerk;
            // end setClerk
        }
    }

    private String licPlate;
    private Calendar firstPaymentDate;
    private String firstPaymentAmt;
    private String partyName;
    private String partyAddress;
    private String phoneNumber;
    private String totalPlanAmt;
    private String totalAmtPaid;
    private String totalAmtDue;
    private String totalDeliquent;
    private Calendar lastUpdated;
    private PaymentPlanDetailDTO[] paymentPlanDetails;

    /**
     * Constructor.
     */
    public PaymentPlanDTO() {
        // end <init>
    }

    // public String getDisplayFirstPaymentDate() {
    // if (firstPaymentDate != null) {
    // return String.format("%1$tm/%1$td/%1$tY", firstPaymentDate);
    // } else {
    // return "";
    // }
    // }

    // public String getDisplayFirstPaymentAmt() {
    // if ((firstPaymentAmt != null) && (firstPaymentAmt.trim().length() > 0)) {
    // return String.format("%9.2f", new Float(firstPaymentAmt));
    // } else {
    // return "-";
    // }
    // }
    //    
    // public String getDisplayTotalPlanAmt() {
    // if ((totalPlanAmt != null) && (totalPlanAmt.trim().length() > 0)) {
    // return String.format("%9.2f", new Float(totalPlanAmt));
    // } else {
    // return "-";
    // }
    // }
    //    
    // public String getDisplayTotalAmtPaid() {
    // if ((totalAmtPaid != null) && (totalAmtPaid.trim().length() > 0)) {
    // return String.format("%9.2f", new Float(totalAmtPaid));
    // } else {
    // return "-";
    // }
    // }
    //    
    // public String getDisplayTotalAmtDue() {
    // if ((totalAmtDue != null) && (totalAmtDue.trim().length() > 0)) {
    // return String.format("%9.2f", new Float(totalAmtDue));
    // } else {
    // return "-";
    // }
    // }
    // public String getDisplayTotalDeliquent() {
    // if ((totalDeliquent != null) && (totalDeliquent.trim().length() > 0)) {
    // return String.format("%9.2f", new Float(totalDeliquent));
    // } else {
    // return "-";
    // }
    // }

    // public String getDisplayLastUpdated() {
    // if (lastUpdated != null) {
    // return String.format("%1$tm/%1$td/%1$tY", lastUpdated);
    // } else {
    // return "";
    // }
    // }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("licPlate=");
        sb.append(this.licPlate);
        sb.append(", firstPaymentDate=");
        sb.append(this.firstPaymentDate);
        sb.append(", firstPaymentAmt=");
        sb.append(this.firstPaymentAmt);
        sb.append(", partyName=");
        sb.append(this.partyName);
        sb.append(", partyAddress=");
        sb.append(this.partyAddress);
        sb.append(", phoneNumber=");
        sb.append(this.phoneNumber);
        sb.append(", totalPlanAmt=");
        sb.append(this.totalPlanAmt);
        sb.append(", totalAmtPaid=");
        sb.append(this.totalAmtPaid);
        sb.append(", totalAmtDue=");
        sb.append(this.totalAmtDue);
        sb.append(", totalDeliquent=");
        sb.append(this.totalDeliquent);
        sb.append(", lastUpdated=");
        sb.append(this.lastUpdated);
        sb.append(", paymentPlanDetails=");
        sb.append(this.paymentPlanDetails);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the licPlate
     */
    public String getLicPlate() {
        return this.licPlate;
        // end getLicPlate
    }

    /**
     * @param licPlate the licPlate to set
     */
    public void setLicPlate(String licPlate) {
        this.licPlate = licPlate;
        // end setLicPlate
    }

    /**
     * @return the firstPaymentDate
     */
    public Calendar getFirstPaymentDate() {
        return this.firstPaymentDate;
        // end getFirstPaymentDate
    }

    /**
     * @param firstPaymentDate the firstPaymentDate to set
     */
    public void setFirstPaymentDate(Calendar firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
        // end setFirstPaymentDate
    }

    /**
     * @return the firstPaymentAmt
     */
    public String getFirstPaymentAmt() {
        return this.firstPaymentAmt;
        // end getFirstPaymentAmt
    }

    /**
     * @param firstPaymentAmt the firstPaymentAmt to set
     */
    public void setFirstPaymentAmt(String firstPaymentAmt) {
        this.firstPaymentAmt = firstPaymentAmt;
        // end setFirstPaymentAmt
    }

    /**
     * @return the partyName
     */
    public String getPartyName() {
        return this.partyName;
        // end getPartyName
    }

    /**
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
        // end setPartyName
    }

    /**
     * @return the partyAddress
     */
    public String getPartyAddress() {
        return this.partyAddress;
        // end getPartyAddress
    }

    /**
     * @param partyAddress the partyAddress to set
     */
    public void setPartyAddress(String partyAddress) {
        this.partyAddress = partyAddress;
        // end setPartyAddress
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
        // end getPhoneNumber
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        // end setPhoneNumber
    }

    /**
     * @return the totalPlanAmt
     */
    public String getTotalPlanAmt() {
        return this.totalPlanAmt;
        // end getTotalPlanAmt
    }

    /**
     * @param totalPlanAmt the totalPlanAmt to set
     */
    public void setTotalPlanAmt(String totalPlanAmt) {
        this.totalPlanAmt = totalPlanAmt;
        // end setTotalPlanAmt
    }

    /**
     * @return the totalAmtPaid
     */
    public String getTotalAmtPaid() {
        return this.totalAmtPaid;
        // end getTotalAmtPaid
    }

    /**
     * @param totalAmtPaid the totalAmtPaid to set
     */
    public void setTotalAmtPaid(String totalAmtPaid) {
        this.totalAmtPaid = totalAmtPaid;
        // end setTotalAmtPaid
    }

    /**
     * @return the totalAmtDue
     */
    public String getTotalAmtDue() {
        return this.totalAmtDue;
        // end getTotalAmtDue
    }

    /**
     * @param totalAmtDue the totalAmtDue to set
     */
    public void setTotalAmtDue(String totalAmtDue) {
        this.totalAmtDue = totalAmtDue;
        // end setTotalAmtDue
    }

    /**
     * @return the totalDeliquent
     */
    public String getTotalDeliquent() {
        return this.totalDeliquent;
        // end getTotalDeliquent
    }

    /**
     * @param totalDeliquent the totalDeliquent to set
     */
    public void setTotalDeliquent(String totalDeliquent) {
        this.totalDeliquent = totalDeliquent;
        // end setTotalDeliquent
    }

    /**
     * @return the lastUpdated
     */
    public Calendar getLastUpdated() {
        return this.lastUpdated;
        // end getLastUpdated
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(Calendar lastUpdated) {
        this.lastUpdated = lastUpdated;
        // end setLastUpdated
    }

    /**
     * @return the paymentPlanDetails
     */
    public PaymentPlanDetailDTO[] getPaymentPlanDetails() {
        return this.paymentPlanDetails;
        // end getPaymentPlanDetails
    }

    /**
     * @param paymentPlanDetails the paymentPlanDetails to set
     */
    public void setPaymentPlanDetails(PaymentPlanDetailDTO[] paymentPlanDetails) {
        this.paymentPlanDetails = paymentPlanDetails;
        // end setPaymentPlanDetails
    }

}
