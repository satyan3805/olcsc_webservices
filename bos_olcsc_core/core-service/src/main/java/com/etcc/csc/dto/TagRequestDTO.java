package com.etcc.csc.dto;


public class TagRequestDTO extends TagDTO {
    private long acctTagSeq;
    private boolean checkDup;
    private boolean dup;
    private long transactionId = -1;
    
    public TagRequestDTO() {
    }


    public void setAcctTagSeq(long acctTagSeq) {
        this.acctTagSeq = acctTagSeq;
    }

    public long getAcctTagSeq() {
        return acctTagSeq;
    }

    /**sets whether to check duplicated license plate
     * @param checkDup
     */
    public void setCheckDup(boolean checkDup) {
        this.checkDup = checkDup;
    }


    /**determines whether to check for duplicated license plate
     * @return checkDup
     */
    public boolean isCheckDup() {
        return checkDup;
    }

    public void setDup(boolean dup) {
        this.dup = dup;
    }

    public boolean isDup() {
        return dup;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getTransactionId() {
        return transactionId;
    }
}
