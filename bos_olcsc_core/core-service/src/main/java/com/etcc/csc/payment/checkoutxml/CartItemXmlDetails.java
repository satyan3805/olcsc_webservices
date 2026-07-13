package com.etcc.csc.payment.checkoutxml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CartItemXmlDetails implements CartXML, Serializable {
	private static final long serialVersionUID = -1744381124163583634L;

	@XmlAttribute(name = "sequence", required = true)
	private int sequence;

	@XmlElement(name = "type", required = true)
	private String itemType;

	@XmlElement(name = "supervisor_id")
	private Long supervisorId;

	@XmlElement(name = "inventory_id")
	private Long inventoryId;

	@XmlElement(name = "invoice_id")
	private Long invoiceId;

	@XmlElement(name = "payment_id")
	private Long paymentId;

	@XmlElement(name = "payment_status_code")
	private String paymentStatusCode;

	@XmlElement(name = "toll_id")
	private Long tollId;

	@XmlElement(name = "refund_id")
	private Long refundId;

	@XmlElement(name = "refund_status_code")
	private String refundStatusCode;

	@XmlElement(name = "new_rate")
	private Double newRate;

	@XmlElement(name = "new_account_id")
	private Long newAccountId;

	@XmlElement(name = "reassign_fees")
	private Boolean reassignFees;

	@XmlElement(name = "unpaid_only")
	private Boolean unpaidOnly;

	@XmlElement(name = "new_workflow_state")
	private Long newWorkflowState;

	@XmlElement(name = "fee_type")
	private String feeType;

	@XmlElement(name = "agency_id")
	private String agencyId;

	@XmlElement(name = "tag_id")
	private String tagId;

	@XmlElement(name = "retail_detail_id")
	private Long retailDetailId;

	@XmlElement(name = "account_balance_type_code")
	private String accountBalanceTypeCode;

	@XmlElement(name = "other_posting_code")
	private String otherPostingCode;

	@XmlElement(name = "excused_reason")
	private String excusalReason;

	@XmlElement(name = "posting_id")
	private Long postingId;

	@XmlElement(name = "ref_sequence")
	private Long refSequence;

	@XmlElement(name = "new_status")
	private String newStatus;

	@XmlElement(name = "to_account_id")
	private Long toAccountId;

	@XmlElement(name = "force")
	private Boolean force;

	@XmlElement(name = "hold_id")
	private Long holdId;

	@XmlElement(name = "hold_txn_id")
	private Long holdTxnId;

	@XmlElement(name = "payment_plan_installment_id")
	private Long paymentPlanInstallmentId;

	@XmlElement(name = "payment_plan_status_code")
	private String paymentPlanStatusCode;

	@XmlElement(name = "amount")
	private Double amount;

	@XmlElement(name = "comment_action_id")
	private Long commentActionId;

	@XmlElement(name = "comment")
	private String comments;

	@XmlElement(name = "account_tag_id")
	private String accountTagId;

	@XmlElement(name = "account_vehicle_id")
	private String accountVehicleId;
	
	@XmlElement(name="old_account_vehicle_id")
	private String oldAccountVehicleId;
	
	@XmlElement(name="old_account_tag_id")
	private String oldAccountTagId;

	@XmlElement(name = "account_inventory_id")
	private String accountInventoryId;

	@XmlElement(name = "post_action")
	private String postAction;

	@XmlElement(name = "ignore_status")
	private String ignoreStatus;

	public String getitemTypeDescription() {
		return itemType;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Long getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Long supervisorId) {
		this.supervisorId = supervisorId;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentStatusCode() {
		return paymentStatusCode;
	}

	public void setPaymentStatusCode(String paymentStatusCode) {
		this.paymentStatusCode = paymentStatusCode;
	}

	public Long getTollId() {
		return tollId;
	}

	public void setTollId(Long tollId) {
		this.tollId = tollId;
	}

	public Double getNewRate() {
		return newRate;
	}

	public void setNewRate(Double newRate) {
		this.newRate = newRate;
	}

	public Long getNewAccountId() {
		return newAccountId;
	}

	public void setNewAccountId(Long newAccountId) {
		this.newAccountId = newAccountId;
	}

	public Boolean getReassignFees() {
		return reassignFees;
	}

	public void setReassignFees(Boolean reassignFees) {
		this.reassignFees = reassignFees;
	}

	public Boolean getUnpaidOnly() {
		return unpaidOnly;
	}

	public void setUnpaidOnly(Boolean unpaidOnly) {
		this.unpaidOnly = unpaidOnly;
	}

	public Long getNewWorkflowState() {
		return newWorkflowState;
	}

	public void setNewWorkflowState(Long newWorkflowState) {
		this.newWorkflowState = newWorkflowState;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public Long getRetailDetailId() {
		return retailDetailId;
	}

	public void setRetailDetailId(Long retailDetailId) {
		this.retailDetailId = retailDetailId;
	}

	public String getAccountBalanceTypeCode() {
		return accountBalanceTypeCode;
	}

	public void setAccountBalanceTypeCode(String accountBalanceTypeCode) {
		this.accountBalanceTypeCode = accountBalanceTypeCode;
	}

	public Long getPostingId() {
		return postingId;
	}

	public void setPostingId(Long postingId) {
		this.postingId = postingId;
	}

	public Long getRefSequence() {
		return refSequence;
	}

	public void setRefSequence(Long refSequence) {
		this.refSequence = refSequence;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}

	public Boolean getForce() {
		return force;
	}

	public void setForce(Boolean force) {
		this.force = force;
	}

	public Long getHoldId() {
		return holdId;
	}

	public void setHoldId(Long holdId) {
		this.holdId = holdId;
	}

	public Long getHoldTxnId() {
		return holdTxnId;
	}

	public void setHoldTxnId(Long holdTxnId) {
		this.holdTxnId = holdTxnId;
	}

	public Long getPaymentPlanInstallmentId() {
		return paymentPlanInstallmentId;
	}

	public void setPaymentPlanInstallmentId(Long paymentPlanInstallmentId) {
		this.paymentPlanInstallmentId = paymentPlanInstallmentId;
	}

	public String getPaymentPlanStatusCode() {
		return paymentPlanStatusCode;
	}

	public void setPaymentPlanStatusCode(String paymentPlanStatusCode) {
		this.paymentPlanStatusCode = paymentPlanStatusCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getCommentActionId() {
		return commentActionId;
	}

	public void setCommentActionId(Long commentActionId) {
		this.commentActionId = commentActionId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getExcusalReason() {
		return excusalReason;
	}

	public void setExcusalReason(String excusalReason) {
		this.excusalReason = excusalReason;
	}

	public String getOtherPostingCode() {
		return otherPostingCode;
	}

	public void setOtherPostingCode(String otherPostingCode) {
		this.otherPostingCode = otherPostingCode;
	}

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public String getRefundStatusCode() {
		return refundStatusCode;
	}

	public void setRefundStatusCode(String refundStatusCode) {
		this.refundStatusCode = refundStatusCode;
	}

	public String getAccountTagId() {
		return accountTagId;
	}

	public void setAccountTagId(String accountTagId) {
		this.accountTagId = accountTagId;
	}

	public String getAccountVehicleId() {
		return accountVehicleId;
	}

	public void setAccountVehicleId(String accountVehicleId) {
		this.accountVehicleId = accountVehicleId;
	}

	public String getAccountInventoryId() {
		return accountInventoryId;
	}

	public void setAccountInventoryId(String accountInventoryId) {
		this.accountInventoryId = accountInventoryId;
	}

	public String getPostAction() {
		return postAction;
	}

	public void setPostAction(String postAction) {
		this.postAction = postAction;
	}

	public String getIgnoreStatus() {
		return ignoreStatus;
	}

	public void setIgnoreStatus(String ignoreStatus) {
		this.ignoreStatus = ignoreStatus;
	}

	public String getOldAccountVehicleId() {
		return oldAccountVehicleId;
	}

	public void setOldAccountVehicleId(String oldAccountVehicleId) {
		this.oldAccountVehicleId = oldAccountVehicleId;
	}

	public String getOldAccountTagId() {
		return oldAccountTagId;
	}

	public void setOldAccountTagId(String oldAccountTagId) {
		this.oldAccountTagId = oldAccountTagId;
	}
}// end of CartItemXmlDetails Class
