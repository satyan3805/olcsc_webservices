package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Represents a violation Invoice.
 */
public class InvoiceDTO extends BaseDTO {
    private long invoiceId;
    private Calendar invoiceDate;
    private double invoiceAmount;
    private double veaAmount;
    private double invoiceAmountPaid;
    private String status;
    private int violatorAddrSeq;
    private long violatorId;
    private Calendar currDueDate;
    private Calendar mailReturnDate;
    private Calendar invoiceClosedDate;
    private String invoiceComment;
    private boolean contested;
    private String excusedBy;
    private Calendar dateExcused;
    private String excusedComment;
    private String excusedReason;
    private Calendar statusDate;
    private Calendar commentDate;
    private Calendar dpsDate;
    private Calendar dpsRejectDate;
    private boolean vea;
    private Collection violations;
    
    public InvoiceDTO() {
    }


    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceDate(Calendar invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Calendar getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmountPaid(double invoiceAmountPaid) {
        this.invoiceAmountPaid = invoiceAmountPaid;
    }

    public double getInvoiceAmountPaid() {
        return invoiceAmountPaid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setViolatorAddrSeq(int violatorAddrSeq) {
        this.violatorAddrSeq = violatorAddrSeq;
    }

    public int getViolatorAddrSeq() {
        return violatorAddrSeq;
    }

    public void setViolatorId(long violatorId) {
        this.violatorId = violatorId;
    }

    public long getViolatorId() {
        return violatorId;
    }

    public void setCurrDueDate(Calendar currDueDate) {
        this.currDueDate = currDueDate;
    }

    public Calendar getCurrDueDate() {
        return currDueDate;
    }

    public void setMailReturnDate(Calendar mailReturnDate) {
        this.mailReturnDate = mailReturnDate;
    }

    public Calendar getMailReturnDate() {
        return mailReturnDate;
    }

    public void setInvoiceClosedDate(Calendar invoiceClosedDate) {
        this.invoiceClosedDate = invoiceClosedDate;
    }

    public Calendar getInvoiceClosedDate() {
        return invoiceClosedDate;
    }

    public void setInvoiceComment(String invoiceComment) {
        this.invoiceComment = invoiceComment;
    }

    public String getInvoiceComment() {
        return invoiceComment;
    }

    public void setContested(boolean contested) {
        this.contested = contested;
    }

    public boolean isContested() {
        return contested;
    }

    public void setExcusedBy(String excusedBy) {
        this.excusedBy = excusedBy;
    }

    public String getExcusedBy() {
        return excusedBy;
    }

    public void setDateExcused(Calendar dateExcused) {
        this.dateExcused = dateExcused;
    }

    public Calendar getDateExcused() {
        return dateExcused;
    }

    public void setExcusedComment(String excusedComment) {
        this.excusedComment = excusedComment;
    }

    public String getExcusedComment() {
        return excusedComment;
    }

    public void setExcusedReason(String excusedReason) {
        this.excusedReason = excusedReason;
    }

    public String getExcusedReason() {
        return excusedReason;
    }

    public void setStatusDate(Calendar statusDate) {
        this.statusDate = statusDate;
    }

    public Calendar getStatusDate() {
        return statusDate;
    }

    public void setCommentDate(Calendar commentDate) {
        this.commentDate = commentDate;
    }

    public Calendar getCommentDate() {
        return commentDate;
    }

    public void setDpsDate(Calendar dpsDate) {
        this.dpsDate = dpsDate;
    }

    public Calendar getDpsDate() {
        return dpsDate;
    }

    public void setDpsRejectDate(Calendar dpsRejectDate) {
        this.dpsRejectDate = dpsRejectDate;
    }

    public Calendar getDpsRejectDate() {
        return dpsRejectDate;
    }

    public void setVea(boolean vea) {
        this.vea = vea;
    }

    public boolean isVea() {
        return vea;
    }
    public void setViolations(Collection violations) {
        this.violations = violations;
    }

    public Collection getViolations() {
        return violations;
    }
    
    public void addViolation(ViolationDTO violationDto) {
        if (violations == null) {
            violations = new ArrayList();
        }
        violations.add(violationDto);
    }

    public void setVeaAmount(double veaAmount) {
        this.veaAmount = veaAmount;
    }

    public double getVeaAmount() {
        return veaAmount;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("invoiceId=");
        sb.append(invoiceId);
        sb.append("invoiceDate=");
        sb.append(invoiceDate);
        sb.append(", invoiceAmount=");
        sb.append(invoiceAmount);
        sb.append(", invoiceAmountPaid=");
        sb.append(invoiceAmountPaid);
        sb.append(", status=");
        sb.append(status);
        sb.append(", violatorAddrSeq=");
        sb.append(violatorAddrSeq);
        sb.append(", violatorId=");
        sb.append(violatorId);
        sb.append(", currDueDate=");
        sb.append(currDueDate);
        sb.append(", mailReturnDate=");
        sb.append(mailReturnDate);
        sb.append(", invoiceClosedDate=");
        sb.append(invoiceClosedDate);
        sb.append(", invoiceComment=");
        sb.append(invoiceComment);
        sb.append(", contested=");
        sb.append(contested);
        sb.append(", excusedBy=");
        sb.append(excusedBy);
        sb.append(", dateExcused=");
        sb.append(dateExcused);
        sb.append(", excusedComment=");
        sb.append(excusedComment);
        sb.append(", excusedReason=");
        sb.append(excusedReason);
        sb.append(", statusDate=");
        sb.append(statusDate);
        sb.append(", commentDate=");
        sb.append(commentDate);
        sb.append(", dpsDate=");
        sb.append(dpsDate);
        sb.append(", dpsRejectDate=");
        sb.append(dpsRejectDate);
        sb.append(", vea=");
        sb.append(vea);
        sb.append("]");
        return sb.toString();
    }

}
