package com.etcc.csc.enums;

public enum InventoryTypeEnum {
    VELCRO(3,"VELCRO"),
    ACCOUNT_CARD(2, "ACC");
    
	private long id;
	private String code;	
	
    InventoryTypeEnum(long id,String code) {
		this.id = id;
		this.code = code;
	}

	public long getId() {
		return id;
	}
	public String getCode() {
		return code;
	}

}
