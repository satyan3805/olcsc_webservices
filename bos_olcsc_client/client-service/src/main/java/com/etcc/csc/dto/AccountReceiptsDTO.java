/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Calendar;

/**
 * Based on the original AcocuntReceiptsDTO from OLCSCServices.  The receipts for a given account.
 *
 */
public class AccountReceiptsDTO extends BaseDTO {
    /**
     * Unique ID with version for serialization.
     */
    private static final long serialVersionUID = 4490467994636507258L;

    /**
     * Representation of an individual Account Receipt.
     */
    public static class AccountReceipt {
        private Calendar transactionDate;
        private long transactionID;
        private float transactionAmt;
        private String transactionType;
        private String HASFlag;

        /**
         * Sets the Transaction Date.
         * @param transactionDate The Transaction date.
         */
        public void setTransactionDate(Calendar transactionDate) {
            this.transactionDate = transactionDate;
        }
        /**
         * Gets the Transaction Date.
         * @return The Date of the transaction.
         */
        public Calendar getTransactionDate() {
            return this.transactionDate;
        }

//        public String getTransactionDateStr() {
//            return String.format("%1$tm/%1$td/%1$ty", this.transactionDate);
//        }

        /**
         * Sets the Transaction ID.
         * @param transactionID The transaction Id to set.
         */
        public void setTransactionID(long transactionID) {
            this.transactionID = transactionID;
        }
        /**
         * Gets the transaction id.
         * @return the transaction id.
         */
        public long getTransactionID() {
            return this.transactionID;
        }

        /**
         * Sets the transaction amount.
         * @param transactionAmt The amount for the transaction.
         */
        public void setTransactionAmt(float transactionAmt) {
            this.transactionAmt = transactionAmt;
        }
        /**
         * Gets the transaction amount.
         * @return The amount of the transaction.
         */
        public float getTransactionAmt() {
            return this.transactionAmt;
        }
//        public String getTransactionAmtStr() {
//            return StringUtil.currencyFormat.format(this.transactionAmt);
//        }

        /**
         * Sets the transaction type.
         * @param transactionType the transaction type.
         */
        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }
        /**
         * Gets the transaction type.
         * @return the transaction type.
         */
        public String getTransactionType() {
            return this.transactionType;
        }

        public void setHASFlag(String HASFlag) {
            this.HASFlag = HASFlag;
        }
        public String getHASFlag() {
            return this.HASFlag;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append("transactionDate=").append(this.transactionDate);
            sb.append(", transactionID=").append(this.transactionID);
            sb.append(", transactionAmt=").append(this.transactionAmt);
            sb.append(", transactionType=").append(this.transactionType);
            sb.append(", HASFlag=").append(this.HASFlag);
            sb.append("]");
            return sb.toString();
        }
    }//end AccountReceipt


    private long acctId;
    private AccountReceipt[] accountReceipts;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("acctId=").append(this.acctId);
        sb.append(", accountReceipts=").append(this.accountReceipts);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the acctId
     */
    public long getAcctId() {
        return this.acctId;
        //end getAcctId
    }

    /**
     * @param acctId the acctId to set
     */
    public void setAcctId(long acctId) {
        this.acctId = acctId;
        //end setAcctId
    }

    /**
     * Sets the Receipts for this account.
     * @param accountReceipts The Receipts for this account.
     */
    public void setAccountReceipts(AccountReceipt[] accountReceipts) {
        this.accountReceipts = accountReceipts;
    }

    /**
     * @return The receipts for the account.
     */
    public AccountReceipt[] getAccountReceipts() {
        return this.accountReceipts;
    }


}
