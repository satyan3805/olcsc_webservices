/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.InvoicedViolationsDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.ViolatorDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.validator.ValidatorActionForm;

public class SelectedInvoiceForm extends ValidatorActionForm implements Cloneable {
    private static final long serialVersionUID = 6475718522220546505L;
    private static final Logger logger = Logger.getLogger(SelectedInvoiceForm.class);

    /**
     * Default Number of invoices per page displayed.
     */
    public static final int NO_OF_INVOICES_PER_PAGE = 5;

    private ViolatorDTO violatorDTO;
    private BillingInfoForm billingInfoForm = new BillingInfoForm();
    private String returnAction = null;
    private int totalItemsFound;
    private int totalNumOfPages;
    private int currentPageNum;
    private int requestedPageNum;
    private int noInvoicesInCurrentPage;
    /** The payment method selected by the user. */
    private String paymentType = null;
    /** Specifies the global override value for invoice payment type (waiver, adjustment). */
    private InvoiceDTO.PaymentOption invoicePaymentTypeOverride = null;
    /** Starting page and display string for each agency (1-indexed). */
    private final Map<Integer, String> agencyPageMap = new HashMap<Integer, String>(AgencyEnum.values().length);

    /**
     * Clone this form and filter invoices for the requested display page.
     * @return a clone of this invoice form corresponding to the requested page
     * @see #NO_OF_INVOICES_PER_PAGE
     * @see #getRequestedPageNum()
     * @see ViolatorDTO#cloneWithoutViolations()
     */
    @Override
	public SelectedInvoiceForm clone() throws CloneNotSupportedException {
    	return clone(this.violatorDTO.getAllInvoices(), true);
    }

    /**
     * Clone this form and filter invoices. Remove invoices without a payment
     * and a discount when <tt>filterByPage</tt> is <tt>false</tt>, otherwise
     * same as {@link #clone()}.
     * @param filterByPage boolean indicating whether to filter by page or by payment/discount
     * @return a clone of this form with filtered invoices
     */
	public SelectedInvoiceForm clone(boolean filterByPage) throws CloneNotSupportedException {
		return clone(this.violatorDTO.getAllInvoices(), filterByPage);
    }

    /**
     * Clone this form and filter invoices for the requested display page.
     * @param invoices list of invoices to filter
     * @param filterByPage boolean indicating whether to filter by page or by payment/discount
     * @return a clone of this invoice form corresponding to the requested page
     * @see #NO_OF_INVOICES_PER_PAGE
     * @see #getRequestedPageNum()
     * @see ViolatorDTO#cloneWithoutViolations()
     * @see #filterInvoices(List, int)
     */
    protected SelectedInvoiceForm clone(List<InvoiceDTO> invoices, boolean filterByPage) throws CloneNotSupportedException {
    	SelectedInvoiceForm clone = (SelectedInvoiceForm) super.clone();
    	clone.violatorDTO = this.violatorDTO.cloneWithoutViolations();
    	clone.totalItemsFound = this.violatorDTO.getInvoiceCount();
        clone.totalNumOfPages = (
            (((clone.totalItemsFound/NO_OF_INVOICES_PER_PAGE)*NO_OF_INVOICES_PER_PAGE)==clone.totalItemsFound)
            ?(clone.totalItemsFound/NO_OF_INVOICES_PER_PAGE)
            :((clone.totalItemsFound/NO_OF_INVOICES_PER_PAGE)+1)
        );
        // get starting page for each agency
        int page = 1;
        int count = 0;
        clone.agencyPageMap.clear();
        //naphan_2451_ avoid null pointer exception in case  invoicedViolationsMap is null
        if (violatorDTO.getInvoicedViolationsMap()!= null) {
        	 for (Map.Entry<String,InvoicedViolationsDTO> e : this.violatorDTO.getInvoicedViolationsMap().entrySet()) {
             	String agencyDisplay = AgencyEnum.valueOfCode(e.getKey()).getDisplay();
             	String existingAgencyDisplay = clone.agencyPageMap.put(Integer.valueOf(page), agencyDisplay);
             	if (existingAgencyDisplay != null) // there's an agency already starting on this page, so append the new one
             		clone.agencyPageMap.put(Integer.valueOf(page), existingAgencyDisplay + "," + agencyDisplay);
             	count += e.getValue().size();
             	page = count/NO_OF_INVOICES_PER_PAGE+1;
             }
        }
        if (this.requestedPageNum <= 1)
        	clone.currentPageNum = clone.requestedPageNum = 1;
        else if (this.requestedPageNum >= clone.totalNumOfPages)
        	clone.currentPageNum = clone.requestedPageNum = clone.totalNumOfPages;
        else
        	clone.currentPageNum = clone.requestedPageNum = this.requestedPageNum;
        if (filterByPage)
        	clone.violatorDTO.addInvoices(filterInvoices(invoices, clone.requestedPageNum));
        else
        	clone.violatorDTO.addInvoices(filterPayingInvoices(invoices));
        clone.violatorDTO.calculatePreliminaryTotals();
        clone.noInvoicesInCurrentPage = clone.violatorDTO.getInvoiceCount();
        if (logger.isDebugEnabled())
        	logger.debug(new StringBuilder("TotalItems=").append(clone.totalItemsFound)
        			.append(", Pages=").append(clone.totalNumOfPages)
        			.append(", CurrentPage=").append(clone.currentPageNum)
        			.append(", InvoicesInCurrentPage=").append(clone.noInvoicesInCurrentPage)
        			.append(", AgencyStartPages=").append(clone.agencyPageMap)
        			.toString());
        return clone;
    }

    /**
     * Return a collection of invoices representing the specified page
     * with {@link #NO_OF_INVOICES_PER_PAGE} invoices.
     * @param invoices collection of invoices to select
     * @param pageNum the number of the page to select
     * @return list of invoices with at most <tt>NO_OF_INVOICES_PER_PAGE</tt> elements
     */
    private static List<InvoiceDTO> filterInvoices(List<InvoiceDTO> invoices, int pageNum) {
        int begin = (pageNum - 1) * NO_OF_INVOICES_PER_PAGE;
        int end = Math.min(pageNum * NO_OF_INVOICES_PER_PAGE, invoices.size());
        return invoices.subList(begin, end);
    }

    /**
     * Return a collection of invoices that are being paid.  Paid invoices
     * have a payment or discount value greater than zero.
     * @param invoices collection of invoices to select
     * @return list of paying invoices
     */
    private static List<InvoiceDTO> filterPayingInvoices(List<InvoiceDTO> invoices) {
    	List<InvoiceDTO> result = new ArrayList<InvoiceDTO>(invoices.size());
    	for (InvoiceDTO i : invoices)
    		if (i.getPayment() > 0 || i.getDiscount() > 0)
    			result.add(i);
    	return result;
    }

    public boolean isValid() {
        for (InvoicedViolationsDTO invoiced : violatorDTO.getInvoicedViolationsMap().values()) {
            for (InvoiceDTO inv : invoiced.getInvoices()) {
                if (inv.getInvoiceDue() < inv.getPayment()) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getPageSelector() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= totalNumOfPages; i++) {
            if (i == currentPageNum) {
                sb.append("<strong>").append(i).append("</strong>");
            } else {
                sb.append("<a href=\"javascript:gotoPage(")
                	.append(i).append(")\" title=\"Go to page ")
                	.append(i).append("\">");
                sb.append(i);
                String agencyDisplay = this.agencyPageMap.get(Integer.valueOf(i));
                if (agencyDisplay != null) {
                	sb.append(" (").append(agencyDisplay).append(")");
                }
                sb.append("</a>");
            }
            if (i != totalNumOfPages) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
	public String toString() {
        return new StringBuilder("entryPoint=").append(returnAction)
            .append(";violatorDTO=").append(violatorDTO)
            .append(";billingInfoForm=").append(billingInfoForm)
            .toString();
    }

    public void setPaymentType(String paymentType) {
    	this.paymentType = paymentType;
    }

    public String getPaymentType() {
    	return this.paymentType;
    }

	public void setInvoicePaymentTypeOverride(String s) {
		this.invoicePaymentTypeOverride = InvoiceDTO.PaymentOption.valueOf(s);
		for (InvoiceDTO invoice : violatorDTO.getAllInvoices()) {
			invoice.setPaymentType(this.invoicePaymentTypeOverride);
		}
	}

	public String getInvoicePaymentTypeOverride() {
		return invoicePaymentTypeOverride.name();
	}

	/**
     * Returns the invoice at the specified index.
     * @param index position of index in the list of invoices
     * @return the invoice at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of range (<tt>index < 0 || index >= invoices.size()</tt>)
     * @see ViolatorDTO#getAllInvoices()
     */
	public InvoiceDTO getInvoice(int index) throws IndexOutOfBoundsException {
    	if (violatorDTO == null) return null;
    	List<InvoiceDTO> invoices = violatorDTO.getAllInvoices();
        return invoices.get(index);
    }

	/**
	 * Creates a new payment method. The payment type on the enclosed
	 * billing info form determines the type of payment to create.
	 * @return a new payment method object
	 * @see #getBillingInfoForm()
	 * @see AccountCreditCardDTO
	 * @see AccountEFTDTO
	 */
	public AccountPaymentMethodDTO newPaymentMethod() {
	    AccountPaymentMethodDTO result = null;
	    if (billingInfoForm.getPaymentTypeEnum() == PaymentType.CREDIT) {
	        AccountCreditCardDTO paymentMethod = new AccountCreditCardDTO();
	        paymentMethod.setNameOnCard(billingInfoForm.getNameOnCard());
	        paymentMethod.setCardType(billingInfoForm.getCardTypeEnum());
	        paymentMethod.setCardNbr(billingInfoForm.getCardNumber());
	        paymentMethod.setCardExpires(billingInfoForm.getCardExpirationMonth() + "/" +
	                                     billingInfoForm.getCardExpirationYear());
	        paymentMethod.setPaymentAmount(violatorDTO.getPayment());
	        result = paymentMethod;
	    } else {
	        AccountEFTDTO paymentMethod = new AccountEFTDTO();
	        paymentMethod.setAccountType(billingInfoForm.getBankAcctTypeEnum());
	        paymentMethod.setRoutingNumber(billingInfoForm.getBankRoutingNumber());
	        paymentMethod.setAccountNumber(billingInfoForm.getBankAcctNumber());
	        paymentMethod.setPaymentAmount(violatorDTO.getPayment());
	        result = paymentMethod;
	    }
	    result.setAddress1(billingInfoForm.getAddress1());
	    result.setAddress2(billingInfoForm.getAddress2());
	    result.setAddress3(billingInfoForm.getAddress3());
	    result.setAddress4(billingInfoForm.getAddress4());
	    result.setCity(billingInfoForm.getCity());
	    result.setState(billingInfoForm.getState());
	    result.setCountry(billingInfoForm.getCountry());
	    result.setZipCode(billingInfoForm.getZip());
		result.setPlus4(billingInfoForm.getPlus4());
	    return result;
	}



	// Standard getters/setters
    public void setViolatorDTO(ViolatorDTO violatorDTO) {
        this.violatorDTO = violatorDTO;
    }

    public ViolatorDTO getViolatorDTO() {
        return violatorDTO;
    }

    public void setBillingInfoForm(BillingInfoForm billingInfoForm) {
        this.billingInfoForm = billingInfoForm;
    }

    public BillingInfoForm getBillingInfoForm() {
        return billingInfoForm;
    }

    public void setReturnAction(String entryPoint) {
        this.returnAction = entryPoint;
    }

    public String getReturnAction() {
        return returnAction;
    }

    public void setTotalItemsFound(int totalItemsFound) {
        this.totalItemsFound = totalItemsFound;
    }

    public int getTotalItemsFound() {
        return totalItemsFound;
    }

    public void setTotalNumOfPages(int totalNumOfPages) {
        this.totalNumOfPages = totalNumOfPages;
    }

    public int getTotalNumOfPages() {
        return totalNumOfPages;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setRequestedPageNum(int requestedPageNum) {
        this.requestedPageNum = requestedPageNum;
    }

    public int getRequestedPageNum() {
        return requestedPageNum;
    }

	public int getNoInvoicesInCurrentPage() {
		return noInvoicesInCurrentPage;
	}

	public void setNoInvoicesInCurrentPage(int noInvoicesInCurrentPage) {
		this.noInvoicesInCurrentPage = noInvoicesInCurrentPage;
	}
}
