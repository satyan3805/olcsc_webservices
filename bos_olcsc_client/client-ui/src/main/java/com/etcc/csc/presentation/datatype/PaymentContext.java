package com.etcc.csc.presentation.datatype;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;

import com.etcc.csc.dto.Invoice;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.Violation;
import com.etcc.csc.util.BigDecimalUtil;
//weblogic upgrade cluster issues fix 
public class PaymentContext implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CreditCard paymentMethod;
    private boolean savePaymentMethod;
    private Invoice[] invoices;
    private Invoice[] veaLicPlates;
    private Violation[] violations;
    private TagDTO[] tags;
    private TagDTO[] addedTags;
    private BigDecimal tagAmount;
    private BigDecimal originalTagAmount;
    private BigDecimal transactionId;
    private boolean veaAccepted;
    private String paymentId;
    private String callBack;
    private LoginCreationInfo loginCreation;
    private boolean paymentComplete;
    private String postingStatus;

    public PaymentContext() {
    }

    public void setPaymentMethod(CreditCard paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CreditCard getPaymentMethod() {
        return paymentMethod;
    }

    public void setSavePaymentMethod(boolean savePaymentMethod) {
        this.savePaymentMethod = savePaymentMethod;
    }

    public boolean getSavePaymentMethod() {
        return savePaymentMethod;
    }

    public BigDecimal getAuthorizedInvoiceAmount() {
        BigDecimal total = new BigDecimal(0.0);
        if (!ArrayUtils.isEmpty(invoices)) {
            for (int i = 0; i < invoices.length; i++) {
                if (invoices[i].isAuthorized()) {
                    total = 
                            total.add((invoices[i].isVeaEligible() && veaAccepted) ? 
                                      invoices[i].getVeaAmount() : 
                                      invoices[i].getAmount());
                }
            }
        }
        return total;
    }
    
    public BigDecimal getAuthorizedInvoiceFeeAmount() {
        BigDecimal total = new BigDecimal(0.0);
        if (!ArrayUtils.isEmpty(invoices)) {
            for (int i = 0; i < invoices.length; i++) {
                if (invoices[i].isAuthorized() && veaAccepted && 
                    invoices[i].isVeaEligible()) {
                    total = 
                            total.add(BigDecimalUtil.nullSafe(invoices[i].getOnlineFee()));
                }
            }
        }
        return total;
    }
    
    public BigDecimal getAuthorizedViolationAmount() {
        BigDecimal total = new BigDecimal(0.0);
        if (!ArrayUtils.isEmpty(violations)) {
            for (int i = 0; i < violations.length; i++) {
                if (violations[i].isAuthorized()) {
                    total = total.add(violations[i].getCashAmount());
                }
            }
        }
        return total;
    }

    public BigDecimal getInvoiceAmount() {
        BigDecimal total = new BigDecimal(0.0);
        if (!ArrayUtils.isEmpty(invoices)) {
            for (int i = 0; i < invoices.length; i++) {
                total = 
                        total.add(invoices[i].getAmount());
            }
        }
        return total;
    }

    public BigDecimal getInvoiceVeaAmount() {
        BigDecimal total = new BigDecimal(0.0);
        if (!ArrayUtils.isEmpty(invoices)) {
            for (int i = 0; i < invoices.length; i++) {
                total = 
                        total.add(BigDecimalUtil.nullSafe(invoices[i].getVeaAmount()));
            }
        }
        return total;
    }

    public BigDecimal getViolationAmount() {
        BigDecimal total = new BigDecimal(0.0);
        if (!ArrayUtils.isEmpty(violations)) {
            for (int i = 0; i < violations.length; i++) {
                total = total.add(violations[i].getCashAmount());
            }
        }
        return total;
    }



    public BigDecimal getInvoiceFeeAmount() {
        BigDecimal total = new BigDecimal(0.0);
        if (!ArrayUtils.isEmpty(invoices)) {
            for (int i = 0; i < invoices.length; i++) {
                total = 
                        total.add(BigDecimalUtil.nullSafe(invoices[i].getOnlineFee()));
            }
        }
        return total;
    }

    public BigDecimal getViolationFeeAmount() {
        BigDecimal total = new BigDecimal(0.0);
        if (!ArrayUtils.isEmpty(violations)) {
            for (int i = 0; i < violations.length; i++) {
                total = 
                        total.add(BigDecimalUtil.nullSafe(violations[i].getOnlineFee()));
            }
        }
        return total;
    }

    public BigDecimal getAuthorizedViolationFeeAmount() {
        BigDecimal total = new BigDecimal(0.0);
        if (!ArrayUtils.isEmpty(violations)) {
            for (int i = 0; i < violations.length; i++) {
                if (violations[i].isAuthorized()) {
                    total = 
                            total.add(BigDecimalUtil.nullSafe(violations[i].getOnlineFee()));
                }
            }
        }
        return total;
    }

    public BigDecimal getPaymentAmount() {
        return BigDecimalUtil.nullSafe(getAuthorizedInvoiceAmount()).add(BigDecimalUtil.nullSafe(getAuthorizedViolationAmount())).add(BigDecimalUtil.nullSafe(getTagAmount())).add(BigDecimalUtil.nullSafe(getAuthorizedInvoiceFeeAmount())).add(BigDecimalUtil.nullSafe(getAuthorizedViolationFeeAmount()));
    }

    public void setInvoices(Invoice[] invoices) {
        this.invoices = invoices;
    }

    public Invoice[] getInvoices() {
        return invoices;
    }

    public void setViolations(Violation[] violations) {
        this.violations = violations;
    }

    public Violation[] getViolations() {
        return violations;
    }

    public void setTags(TagDTO[] tags) {
        this.tags = tags;
    }

    public TagDTO[] getTags() {
        return tags;
    }

    public void setTagAmount(BigDecimal tagAmount) {
        this.tagAmount = tagAmount != null ? tagAmount : new BigDecimal(0.0);
    }

    public BigDecimal getTagAmount() {
        return tagAmount;
    }

    public void setVeaAccepted(boolean veaAccepted) {
        this.veaAccepted = veaAccepted;
    }

    public boolean isVeaAccepted() {
        return veaAccepted;
    }


    public void setVeaLicPlates(Invoice[] veaLicPlates) {
        this.veaLicPlates = veaLicPlates;
    }

    public Invoice[] getVeaLicPlates() {
        return veaLicPlates;
    }

    public boolean getVeaEligible() {
        if (!ArrayUtils.isEmpty(invoices)) {
            for (int i = 0; i < invoices.length; i++) {
                if (invoices[i].isVeaEligible()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Invoice[] getDistinctLicPlates() {
        if (!ArrayUtils.isEmpty(invoices)) {
            HashMap<String,Invoice> veaLicPlatesMap = new HashMap<String,Invoice>();
            for (int i = 0; i < invoices.length; i++) {
                veaLicPlatesMap.put(invoices[i].getLicPlateNumber() + 
                                    invoices[i].getLicPlateState(), 
                                    invoices[i]);
            }
            return veaLicPlatesMap.values().toArray(new Invoice[0]);
        }
        return null;
    }

    public void setTransactionId(BigDecimal transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getTransactionId() {
        return transactionId;
    }

    public Invoice[] getAuthorizedInvoices() {
        if (!ArrayUtils.isEmpty(invoices)) {
            ArrayList<Invoice> list = new ArrayList<Invoice>();
            for (int i = 0; i < invoices.length; i++) {
                if (invoices[i].isAuthorized()) {
                    list.add(invoices[i]);
                }
            }
            if (!list.isEmpty()) {
                return list.toArray(new Invoice[0]);
            }
        }
        return null;
    }

    public Violation[] getAuthorizedViolations() {
        if (!ArrayUtils.isEmpty(violations)) {
            ArrayList<Violation> list = new ArrayList<Violation>();
            for (int i = 0; i < violations.length; i++) {
                if (violations[i].isAuthorized()) {
                    list.add(violations[i]);
                }
            }
            if (!list.isEmpty()) {
                return list.toArray(new Violation[0]);
            }
        }
        return null;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setOriginalTagAmount(BigDecimal originalTagAmount) 
    {
      this.originalTagAmount = originalTagAmount != null ? originalTagAmount : new BigDecimal(0.0);
    }

    public BigDecimal getOriginalTagAmount() {
        return originalTagAmount;
    }

    public void setAddedTags(TagDTO[] addedTags) {
        this.addedTags = addedTags;
    }

    public TagDTO[] getAddedTags() {
        return addedTags;
    }

    public void setLoginCreation(LoginCreationInfo loginCreation) {
        this.loginCreation = loginCreation;
    }

    public LoginCreationInfo getLoginCreation() {
        return loginCreation;
    }

    public void setPaymentComplete(boolean paymentComplete) {
        this.paymentComplete = paymentComplete;
    }

    public boolean isPaymentComplete() {
        return paymentComplete;
    }

    public void setPostingStatus(String postingStatus) {
        this.postingStatus = postingStatus;
    }

    public String getPostingStatus() {
        return postingStatus;
    }
}
