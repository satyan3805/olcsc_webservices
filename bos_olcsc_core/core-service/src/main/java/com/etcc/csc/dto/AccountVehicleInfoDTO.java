package com.etcc.csc.dto;

import java.io.Serializable;


	
	public class AccountVehicleInfoDTO implements Serializable {

		private long accountid;
		private Long accountvehicleid;
		private Long dmvvehicleid;
		private Long accounttagid;
		private String effectivedate;
		private String expiredate;
		private String transactionamount;
		private int transactioncount;
		private String transactionvtollamount;
		private int transactionvtollcount;
		private String tagexists;
		
		public AccountVehicleInfoDTO() {
			super();
		}

		public long getAccountid() {
			return accountid;
		}

		public void setAccountid(long accountid) {
			this.accountid = accountid;
		}

		public Long getAccountvehicleid() {
			return accountvehicleid;
		}

		public void setAccountvehicleid(Long accountvehicleid) {
			this.accountvehicleid = accountvehicleid;
		}

		public Long getDmvvehicleid() {
			return dmvvehicleid;
		}

		public void setDmvvehicleid(Long dmvvehicleid) {
			this.dmvvehicleid = dmvvehicleid;
		}

		public Long getAccounttagid() {
			return accounttagid;
		}

		public void setAccounttagid(Long accounttagid) {
			this.accounttagid = accounttagid;
		}

		public String getEffectivedate() {
			return effectivedate;
		}

		public void setEffectivedate(String effectivedate) {
			this.effectivedate = effectivedate;
		}

		public String getExpiredate() {
			return expiredate;
		}

		public void setExpiredate(String expiredate) {
			this.expiredate = expiredate;
		}

		public String getTransactionamount() {
			return transactionamount;
		}

		public void setTransactionamount(String transactionamount) {
			this.transactionamount = transactionamount;
		}

		public int getTransactioncount() {
			return transactioncount;
		}

		public void setTransactioncount(int transactioncount) {
			this.transactioncount = transactioncount;
		}

		public String getTagexists() {
			return tagexists;
		}

		public void setTagexists(String tagexists) {
			this.tagexists = tagexists;
		}

		public String getTransactionvtollamount() {
			return transactionvtollamount;
		}

		public void setTransactionvtollamount(String transactionvtollamount) {
			this.transactionvtollamount = transactionvtollamount;
		}

		public int getTransactionvtollcount() {
			return transactionvtollcount;
		}

		public void setTransactionvtollcount(int transactionvtollcount) {
			this.transactionvtollcount = transactionvtollcount;
		}
	}



