package com.etcc.csc.util;



public enum CartItemTypeEnum {

	PAY_TOLL("pay_toll","Toll Payment"),
	UNASSIGN_TOLL("unassign_toll","unassign_toll"),
    REASSIGN_TOLL("reassign_toll","reassign_toll"),
    EXCUSE_TOLL("excuse_toll","Excuse Toll"),
    RERATE_TOLL("rerate_toll","rerate_toll"),
    AGE_INVOICE("age_invoice","age_invoice"),
    EXCUSE_INVOICE("excuse_invoice","Excuse Invoice"),
    UNASSIGN_INVOICE("unassign_invoice","unassign_invoice"),
    REASSIGN_INVOICE("reassign_invoice","reassign_invoice"),
    PAY_INVOICE("pay_invoice","Invoice Payment"),
    ASSESS_FEE("assess_fee","Charge fee"),
    INSTALLMENT_ASSESS_FEE("installment_assess_fee","Installment Assess fee"),
    EXCUSE_FEE("excuse_fee","Excuse Fee"),
    PAY_FEE("pay_fee","Fee Payment"),
    MOVE_TAG("move_tag","move_tag"),
    MOVE_PAYMENT_OUT("move_payment_out","Unapply Payment"),
    MOVE_PAYMENT_IN("move_payment_in","Apply Unapplied Payment"),
    MERGE("merge","merge"),
    SPLIT_PAYMENT("split_payment","split_payment"),
    APPLY_UNDIRECTED("apply_undirected","Add to Balance"),
    CHANGE_STATUS("change_status","change_status"),
    /*PAY_PLAN("pay_plan","pay_plan"),
    PLACE_HOLD("place_hold", "place_hold"),
    PAY_HOLD("pay_hold","pay_hold"),
    RELEASE_HOLD("release_hold","release_hold"),*/
    OTHER_DEBIT("other_debit","other_debit"),
    CHANGE_RETURNED("change_returned","change_returned"),
	CASH("cash","cash"),
	CHECK("check","check"),
	MO("mo","mo"),
	CARD("card","card"),
	UNREVERSE("unreverse","unreverse"),
	CREDIT("credit","credit"),
	BALANCE("balance","balance"),
	AUTO_EXCUSAL("AE","auto_excusal"),
	EXCUSE_CART("excuse_cart","excuse_cart"),
	DEPOSIT_REFUND("?","?"),
	STATEMENT_FEE_REFUND("?","?"),
	UNEXCUSE_TOLL("unexcuse_toll","unexcuse_toll"),
	UNEXCUSE_INVOICE("unexcuse_invoice","unexcuse_invoice"),
	UNEXCUSE_FEE("unexcuse_fee","unexcuse_fee"),
	REFUND("refund","Refund"),
	PETTY_CASH_REFUND("petty_cash_refund","Petty Cash Refund"),
	TOLL_ADJUSTMENT("adjust_toll","Toll adjustment"),
	UNDO_REFUND("undo_refund","Undo refund"),
    APPLY_TO_BALANCE("apply_to_balance", "Apply to Balance"),
	REVERSE_PAYMENT("reverse_payment", "reverse_payment"),
	AUTO_VOID("auto_void","auto_void"),
	REVERSE_EFT_PAYMENT("reverse_eft_payment", "reverse_eft_payment"),
	UNREVERSE_PAYMENT("unreverse_payment", "unreverse_payment"),
	OTHER_POSTING("other_posting","Other Posting"),
	APPLY_FROM_BALANCE("apply_from_balance", "Apply from Balance"),
	MOVE_PAYMENT_TO_STATE("move_payment_to_state","Move payment to state"),
	APPROVE_REFUND("approve_refund", "Approve Refund"),
	DENIED_REFUND("denied_refund", "Denied Refund"),

	SELL_GOODS("sell_goods", "Add inventory"),
	PAY_GOODS("pay_goods","Inventory sale"),
	UNPAY_GOODS("unpay_goods","Return Inventory Cost"),
	RETURN_GOODS("return_goods","Return inventory"),
	LOSE_GOODS("lose_goods","Lose inventory"),
	UNLOSE_GOODS("unlose_goods","Unlose inventory"),

	ADD_TAG("add_tag","Add transponder"),
	PAY_TAG("pay_tag","Transponder sale"),
	UNPAY_TAG("unpay_tag","Return Transponder cost"),
	RETURN_TAG("return_tag","Return transponder"),
	LOSE_TAG("lose_tag","Lose transponder"),
	UNLOSE_TAG("unlose_tag","Unlose transponder"),

	ADD_ACCOUNT("add_account","Add Account"),
	ADD_SHORTAGE("add_shortage","Add Shortage"),
	PAY_SHORTAGE("pay_shortage","Pay Shortage"),
	UNPAY_SHORTAGE("unpay_shortage","UnPay Shortage"),
	EXCUSE_SHORTAGE("excuse_shortage","Excuse Shortage"),
	UNEXCUSE_SHORTAGE("unexcuse_shortage","UnExcuse Shortage"),
	ADD_DEPOSIT("add_deposit","Add to account deposit"),
	REMOVE_DEPOSIT("remove_deposit", "Remove from deposit balance"),
	PAY_DEPOSIT("pay_deposit", "Payment for transponder deposit"),
	UNPAY_DEPOSIT("unpay_deposit", "Refund account deposit to balance"),

	MOVE_VEHICLE("move_vehicle","move_vehicle"),
	INACTIVATE_VEHICLE("inactivate_vehicle","inactivate_vehicle"),
	MOVE_GOODS("move_goods","move_goods"),

	UNPAY_FEE("unpay_fee", "Unpay fee"),
	POST_ACTION("post_action", "Post checkout action"),
	POST_FAIL_ACTION("post_fail_action", "Post checkout fail action"),
	END_INSTALLMENT("end_installments", "end installments"),
	CREATE_INSTALLMENTS("create_installments", "Create Payment Plan"),
	PAY_INSTALLMENTS("pay_installment", "Pay Plan Installment"),
	ADD_VEHICLE("add_vehicle", "Add Vehicle"),
	APPLY_HOLD("apply_hold", "Apply Hold"),
	APPLY_HOLD_ITEM("apply_hold_item", "Apply Hold Item"),
	UNAPPLY_HOLD("unapply_hold", "Unapply Hold"),
	UNAPPLY_HOLD_ITEM("unapply_hold_item", "Unapply Hold Item"),
	UNPAY_TOLL("unpay_toll", "Unpay Toll"),
	UNPAY_INVOICE("unpay_invoice", "Unpay Invoice"),
	IS_DISPUTE("is_dispute","Is Dispute"),
	SL_VARIANCE("sl_variance","Is Variance"),

	REVERSE_INSTALLMENT("reverse_installment", "Reverse Payment Plan Installment"),
	CANCEL_INSTALLMENTS("cancel_installments", "Cancel Payment Plan Installments"),

	ESCALATE_INVOICE("escalate_invoice", "Escalate Invoice"),
	ESCALATE_FEE("escalate_fee", "Escalate Fee"),
	ESCALATE_TOLL("escalate_toll", "Escalate Toll"),
	DISTRIBUTE_TAG("distribute_tag", "Distribute Tag"),
	DISTRIBUTE_GOODS("distribute_goods", "Distribute Goods"),
	PROCESS_CA_PAYMENT("process_ca_payment","PROCESS CA PAYMENTS"),
	ACCOUNT_FEE( "account_fee", "account_fee" ),
	CHANGE_VEH_EFF_DT("change_veh_eff_dt", "Vehicle Effective Date Change"),
	TRANSITION("transition", "Account Transition");

	private String value;
	private String description;
    private CartItemTypeEnum(String value,String description) {
    	this.value = value;
    	this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public static CartItemTypeEnum getValue(String value){
		CartItemTypeEnum retEnum = null;
		for(CartItemTypeEnum v:CartItemTypeEnum.values()){
			if(v.getValue().equals(value)){
				retEnum = v;
				break;
			}
		}

		return retEnum;
	}
}
