package com.etcc.csc.enums;

public enum TagStatusEnum {
	NONE(0, 0, "", "Wrong tag status"),// Not in dev db now 2001 Oct 31
	INACTIVE(1, 2, "N", "Inactive"), 
	ACTIVE(2, 2, "A", "Active"), 
	LOST(3, 2, "L", "Lost"), 
	STOLEN(4, 2, "S", "Stolen"), 
	RETURNED(5, 4, "T", "Returned"), 
	RECOVERED(6, 2, "R", "Recovered"),
	RETIRED(7, 2, "X", "Retired"), 
	DAMAGED(8, 2, "G", "Damaged"), 
	DEFECTIVE(9, 2, "D", "Defective"), 
	PROPOSED(10, 1, "P", "Proposed"), 
	INVENTORY(11, 1, "I", "Inventory"), 
	TRANSFERED(12, 2, "F", "Transferred"), 
	LOST_IN_MAIL(13, 2, "M", "Lost in Mail"), 
	UNKNOWN(14, 16, "U", "Unknown"), 
	PENDING(15, 1, "PD", "Pending"), 
	ERROR(16, 1, "E", "Error"), 
	TRANSITION(17, 0, "O", "Transition");// Not in dev db now 2001 Oct 31
	  
	private long id;
	private long inventoryStatusId;
	private String code;
	private String description;

	TagStatusEnum(long id, long inventoryStatusId, String code, String description) {
		this.id = id;
		this.inventoryStatusId = inventoryStatusId;
		this.code = code;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public long getInventoryStatusId() {
		return inventoryStatusId;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static TagStatusEnum getById(long id) {
		TagStatusEnum returnEnum = TagStatusEnum.NONE;
		if (id == NONE.getId()) {
			returnEnum = NONE;
		} else if (id == INACTIVE.getId()) {
			returnEnum = INACTIVE;
		} else if (id == ACTIVE.getId()) {
			returnEnum = ACTIVE;
		} else if (id == LOST.getId()) {
			returnEnum = LOST;
		} else if (id == STOLEN.getId()) {
			returnEnum = STOLEN;
		} else if (id == RETURNED.getId()) {
			returnEnum = RETURNED;
		} else if (id == RECOVERED.getId()) {
			returnEnum = RECOVERED;
		} else if (id == RETIRED.getId()) {
			returnEnum = RETIRED;
		} else if (id == DAMAGED.getId()) {
			returnEnum = DAMAGED;
		} else if (id == DEFECTIVE.getId()) {
			returnEnum = DEFECTIVE;
		} else if (id == PROPOSED.getId()) {
			returnEnum = PROPOSED;
		} else if (id == INVENTORY.getId()) {
			returnEnum = INVENTORY;
		} else if (id == TRANSFERED.getId()) {
			returnEnum = TRANSFERED;
		} else if (id == LOST_IN_MAIL.getId()) {
			returnEnum = LOST_IN_MAIL;
		} else if (id == UNKNOWN.getId()) {
			returnEnum = UNKNOWN;
		}else if (id == PENDING.getId()) {
			returnEnum = PENDING;
		}else if (id == ERROR.getId()) {
			returnEnum = ERROR;
		}else if (id == TRANSITION.getId()) {
			returnEnum = TRANSITION;
		}
		return returnEnum;
		
	}
	
	public static TagStatusEnum getByCode(String code) {
		TagStatusEnum returnEnum = TagStatusEnum.NONE;
		if (code.equals(NONE.getCode())) {
			returnEnum = NONE;
		} else if (code.equals(INACTIVE.getCode())) {
			returnEnum = INACTIVE;
		} else if (code.equals(ACTIVE.getCode())) {
			returnEnum = ACTIVE;
		} else if (code.equals(LOST.getCode())) {
			returnEnum = LOST;
		} else if (code.equals(STOLEN.getCode())) {
			returnEnum = STOLEN;
		} else if (code.equals(RETURNED.getCode())) {
			returnEnum = RETURNED;
		} else if (code.equals(RECOVERED.getCode())) {
			returnEnum = RECOVERED;
		} else if (code.equals(RETIRED.getCode())) {
			returnEnum = RETIRED;
		} else if (code.equals(DAMAGED.getCode())) {
			returnEnum = DAMAGED;
		} else if (code.equals(DEFECTIVE.getCode())) {
			returnEnum = DEFECTIVE;
		} else if (code.equals(PROPOSED.getCode())) {
			returnEnum = PROPOSED;
		} else if (code.equals(INVENTORY.getCode())) {
			returnEnum = INVENTORY;
		} else if (code.equals(TRANSFERED.getCode())) {
			returnEnum = TRANSFERED;
		} else if (code.equals(LOST_IN_MAIL.getCode())) {
			returnEnum = LOST_IN_MAIL;
		} else if (code.equals(UNKNOWN.getCode())) {
			returnEnum = UNKNOWN;
		}else if (code.equals(PENDING.getCode())) {
			returnEnum = PENDING;
		}else if (code.equals(ERROR.getCode())) {
			returnEnum = ERROR;
		}else if (code.equals(TRANSITION.getCode())) {
			returnEnum = TRANSITION;
		}
		return returnEnum;
		
	}
}
