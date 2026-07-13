package com.etcc.olcsc.attlas.exceptions;

import java.sql.SQLException;

public class AttlasException extends Exception {
	private static final long serialVersionUID = 8475338265262446558L;
	
	public static final String ERR_CODE_GENERAL = "20010";
	public static final String ERR_CODE_INVALID_AGENCY  = "20011";
	public static final String ERR_CODE_INVALID_PLATE = "20012";
	public static final String ERR_CODE_INVALID_PARTY = "20013";
	public static final String ERR_CODE_INVALID_INVOICE = "20014";
	public static final String ERR_CODE_NO_VIOLATION_QUALIFIES = "20015";
	public static final String ERR_CODE_NO_INVOICE_QUALIFIES = "20016";
	public static final String ERR_CODE_INVALID_PAYMENT_TYPE = "20017";
	public static final String ERR_CODE_COULD_NOT_APPLY_PAYMENT = "20018";
	public static final String ERR_CODE_INVALID_PLATE_STATE = "20019";
	public static final String EXPIRATION_DATE_PAS = "-20032";
	
	
	public static final int MAXLEN_ERROR_MSG = 1000;
	
	private int errCode = Integer.parseInt(ERR_CODE_GENERAL);
	private String errMsg = "";

	public static AttlasException translateException (Exception ex) {
		if (ex instanceof SQLException) {
			String msg = ex.getMessage();
			
			if (msg.contains(ERR_CODE_INVALID_AGENCY)) {
				return new AttlasException(Integer.parseInt(ERR_CODE_INVALID_AGENCY), extractString(msg ,ERR_CODE_INVALID_AGENCY ), ex);
			}
			else if (msg.contains(ERR_CODE_INVALID_PLATE)) {
				return new AttlasException(Integer.parseInt(ERR_CODE_INVALID_PLATE), extractString(msg , ERR_CODE_INVALID_PLATE), ex);
			}
			else if (msg.contains(ERR_CODE_INVALID_PARTY)) {
				return new AttlasException(Integer.parseInt(ERR_CODE_INVALID_PARTY), extractString(msg ,ERR_CODE_INVALID_PARTY ), ex);
			}
			else if (msg.contains(ERR_CODE_INVALID_INVOICE)) {
				return new AttlasException(Integer.parseInt(ERR_CODE_INVALID_INVOICE),extractString(msg ,ERR_CODE_INVALID_INVOICE ) , ex);
			}
			else if (msg.contains(ERR_CODE_NO_VIOLATION_QUALIFIES)) {
				return new AttlasException(Integer.parseInt(ERR_CODE_NO_VIOLATION_QUALIFIES),extractString(msg ,ERR_CODE_NO_VIOLATION_QUALIFIES) , ex);
			}
			else if (msg.contains(ERR_CODE_NO_INVOICE_QUALIFIES)) {
				return new AttlasException(Integer.parseInt(ERR_CODE_NO_INVOICE_QUALIFIES),extractString(msg ,ERR_CODE_NO_INVOICE_QUALIFIES) , ex);
			}
			else if (msg.contains(ERR_CODE_INVALID_PAYMENT_TYPE)) {
				return new AttlasException(Integer.parseInt(ERR_CODE_INVALID_PAYMENT_TYPE), extractString(msg ,ERR_CODE_INVALID_PAYMENT_TYPE), ex);
			}
			else if (msg.contains(ERR_CODE_COULD_NOT_APPLY_PAYMENT)) {
				return new AttlasException(Integer.parseInt(ERR_CODE_COULD_NOT_APPLY_PAYMENT), extractString(msg ,ERR_CODE_COULD_NOT_APPLY_PAYMENT), ex);
			}
			else if (msg.contains(ERR_CODE_INVALID_PLATE_STATE)) {
				return new AttlasException(Integer.parseInt(ERR_CODE_INVALID_PLATE_STATE), extractString(msg , ERR_CODE_INVALID_PLATE_STATE), ex);
			}
		}
		String msg = ex.getMessage();
		if (msg.length() > MAXLEN_ERROR_MSG) {
			msg = msg.substring(0, MAXLEN_ERROR_MSG);
		}
		return new AttlasException(Integer.parseInt(ERR_CODE_GENERAL), msg, ex);
	}
	
	//extractString method is used to return the error message which is return from ATTALASU and returns message excludes codes(ex:ORA-12345)
	//it returns just the message if it doesn't have the codes
    
	public static String extractString(String msg , String code){
         
         String extract_msg = msg;
         String word="ORA-";
         if (msg.indexOf(word, 2) > 0)
         extract_msg =  msg.substring(msg.indexOf(code)+code.length()+1 , msg.indexOf(word, 2)) ;
         else
             extract_msg =  msg.substring(msg.indexOf(code)+code.length()+1 ) ;
         return extract_msg;
      }
 
	
	public AttlasException(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public AttlasException(int errCode, String errMsg, Throwable ex) {
		super(ex);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public int getErrCode() {
		return errCode;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
}
