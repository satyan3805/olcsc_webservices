package com.etcc.csc.enums;

public enum TagTypeEnum {
	// ROOF_MOUNT(1),
	NONE(0, "Wrong tag status"), HARD_CASE(1, "H"), FLAT_PACK(5, "R"), LICENSE_PLATE(
			2, "L"),
	// RETAIL_TAG(4,""),
	STICKER(3, "S");
	// CASE(6,"");
	private long id;
	private String code;

	TagTypeEnum(long id, String code) {
		this.id = id;
		this.code = code;
	}

	public long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public static TagTypeEnum getById(long id) {
		TagTypeEnum returnEnum = TagTypeEnum.NONE;

		if (id == NONE.getId()) {
			returnEnum = NONE;
		} else if (id == HARD_CASE.getId()) {
			returnEnum = HARD_CASE;
		} else if (id == FLAT_PACK.getId()) {
			returnEnum = FLAT_PACK;
		} else if (id == LICENSE_PLATE.getId()) {
			returnEnum = LICENSE_PLATE;
		} else if (id == STICKER.getId()) {
			returnEnum = STICKER;
		}

		return returnEnum;
	}

	public static TagTypeEnum getByCode(String code) {
		TagTypeEnum returnEnum = TagTypeEnum.NONE;

		if (code.equals(NONE.getCode())) {
			returnEnum = NONE;
		} else if (code.equals(HARD_CASE.getCode())) {
			returnEnum = HARD_CASE;
		} else if (code.equals(FLAT_PACK.getCode())) {
			returnEnum = FLAT_PACK;
		} else if (code.equals(LICENSE_PLATE.getCode())) {
			returnEnum = LICENSE_PLATE;
		} else if (code.equals(STICKER.getCode())) {
			returnEnum = STICKER;
		}

		return returnEnum;
	}
}
