package com.etcc.csc.enums;

public enum TagAuthorityEnum {

	DNT(2, "DNT"), 
	FED(2, "FED"), 
	PKY(2, "PKY"), 
	UNK(2, "UNK"), 
	OTA(2, "OTA"), 
	TXDT(2, "TXDT"), 
	DFW(2, "DFW"), 
	PEG(2, "PEG"), 
	LPC(2, "LPC"), 
	GN02(2,"GN02"), 
	KTA(2, "KTA"), 
	HCTRA(3, "HCTRA"), 
	TEX(7, "TEX"), 
	RTL(13,"RTL");
	
	private long id;
	private String code;

	TagAuthorityEnum(long id, String code) {
		this.id = id;
		this.code = code;
	}

	public long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
	
	public static TagAuthorityEnum getByCode(String code) {
		TagAuthorityEnum returnEnum = null;

		if (code.equals(DNT.getCode())) {
			returnEnum = DNT;
		} else if (code.equals(FED.getCode())) {
			returnEnum = FED;
		}  else if (code.equals(PKY.getCode())) {
			returnEnum = PKY;
		}  else if (code.equals(UNK.getCode())) {
			returnEnum = UNK;
		}  else if (code.equals(OTA.getCode())) {
			returnEnum = OTA;
		}  else if (code.equals(FED.getCode())) {
			returnEnum = FED;
		}  else if (code.equals(TXDT.getCode())) {
			returnEnum = TXDT;
		}  else if (code.equals(DFW.getCode())) {
			returnEnum = DFW;
		}  else if (code.equals(PEG.getCode())) {
			returnEnum = PEG;
		}  else if (code.equals(LPC.getCode())) {
			returnEnum = LPC;
		}  else if (code.equals(GN02.getCode())) {
			returnEnum = GN02;
		}  else if (code.equals(KTA.getCode())) {
			returnEnum = KTA;
		} else if (code.equals(HCTRA.getCode())) {
			returnEnum = HCTRA;
		} else if (code.equals(TEX.getCode())) {
			returnEnum = TEX;
		} else if (code.equals(RTL.getCode())) {
			returnEnum = RTL;
		} 
		return returnEnum;
	}

}
