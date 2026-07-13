package com.etcc.csc.enums;

public enum CartItemTypeEnum {
	PAY_TOLL("pay_toll","Toll Payment"),
	UNASSIGN_TOLL("unassign_toll","unassign_toll"),
    REASSIGN_TOLL("reassign_toll","reassign_toll"),
    EXCUSE_TOLL("excuse_toll","Excuse Toll"),
    UNEXCUSE_TOLL("unexcuse_toll","unexcuse_toll"),
    RERATE_TOLL("rerate_toll","rerate_toll"),
    AGE_INVOICE("age_invoice","age_invoice"),
    EXCUSE_INVOICE("excuse_invoice","Excuse Invoice"),
    UNEXCUSE_INVOICE("unexcuse_invoice","unexcuse_invoice"),
    UNASSIGN_INVOICE("unassign_invoice","unassign_invoice"),
    REASSIGN_INVOICE("reassign_invoice","reassign_invoice"),
    PAY_INVOICE("pay_invoice","Invoice Payment"),
    PAY_GOODS("pay_goods","pay_goods"),
    RETURN_GOODS("return_goods","return_goods"),
    LOSE_GOODS("lose_goods","lose_goods"),
    UNLOSE_GOODS("unlose_goods","unlose_goods"),
    ASSESS_FEE("assess_fee","assess_fee"),
    EXCUSE_FEE("excuse_fee","Excuse Fee"),
    PAY_FEE("pay_fee","Fee Payment"),
    ADD_TAG("add_tag","add_tag"),
    RETURN_TAG("return_tag","return_tag"),
    LOSE_TAG("lose_tag","lose_tag"),
    UNLOSE_TAG("unlose_tag","unlose_tag"),
    MOVE_TAG("move_tag","move_tag"),
    MOVE_PAYMENT_OUT("move_payment_out","move_payment_out"),
    MOVE_PAYMENT_IN("move_payment_in","move_payment_in"),     
    MERGE("merge","merge"),
    SPLIT_PAYMENT("split_payment","split_payment"),
    APPLY_UNDIRECTED("apply_undirected","Add to Balance"),
    CHANGE_STATUS("change_status","change_status"),
    PAY_PLAN("pay_plan","pay_plan"),
    PLACE_HOLD("place_hold","place_hold"),
    PAY_HOLD("pay_hold","pay_hold"),
    RELEASE_HOLD("release_hold","release_hold"),
    OTHER_DEBIT("other_debit","other_debit"),
    CHANGE_RETURNED("change_returned","change_returned"),
	CASH("cash","cash"),
	CHECK("check","check"),
	MO("mo","mo"),
	CARD("card","card"),   
	UNREVERSE("unreverse","unreverse"),     
	CREDIT("credit","credit"),     
	BALANCE("balance","balance"),
	AUTO_EXCUSAL("auto_excusal","auto_excusal"),
	EXCUSE_CART("excuse_cart","excuse_cart"),
	DEPOSIT_REFUND("?","?"),
	STATEMENT_FEE_REFUND("?","?"),
	UNEXCUSE_FEE("unexcuse_fee","unexcuse_fee"),
	REFUND("refund","Refund"),
    ADD_DEPOSIT("add_deposit","add_deposit"),
    REMOVE_DEPOSIT("remove_deposit", "remove_deposit"),
    APPLY_TO_BALANCE("apply_to_balance", "Apply to Balance"),
	REVERSE_PAYMENT("reverse_payment", "reverse_payment"),
	UNREVERSE_PAYMENT("unreverse_payment", "unreverse_payment"),
	OTHER_POSTING("other_posting","Other Posting"),
	
	ADD_ACCOUNT("add_account","Add Account"),
	PAY_TAG("pay_tag","Pay tag"),
	UNPAY_TAG("unpay_tag","Return Transponder cost"),
	PAY_DEPOSIT("pay_deposit", "Pay deposit"),
	UNPAY_DEPOSIT("unpay_deposit", "Unpay deposit"),
	UNPAY_FEE("unpay_fee", "Unpay fee"),
	ADD_VEHICLE("add_vehicle","ADD_VEHICLE"),
	MOVE_VEHICLE("move_vehicle","MOVE_VEHICLE"),	
	SELL_GOODS("sell_goods", "Add inventory"),
	UNPAY_GOODS("unpay_goods","Return Inventory Cost"),
	POST_ACTION("post_action", "Post checkout action"),
/* For M6735 */
	DISTRIBUTE_TAG("distribute_tag", "Distribute Tag"),
	DISTRIBUTE_GOODS("distribute_goods", "Distribute Goods");
	
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

}
