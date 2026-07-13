/**
 * 
 */
package com.etcc.csc.webservice.rest.dto;

/**
 * @author adeshpande
 *
 */
public class GetAccountDocumentRequest {
	private Long acctId;
	private String loginType;
	private String dbSessionId;
	private String lastLoginIp;
	private String loginId;

	/**
	 * @return the acctId
	 */
	public Long getAcctId() {
		return acctId;
	}

	/**
	 * @param acctId
	 *            the acctId to set
	 */
	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	/**
	 * @return the loginType
	 */
	public String getLoginType() {
		return loginType;
	}

	/**
	 * @param loginType
	 *            the loginType to set
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	/**
	 * @return the dbSessionId
	 */
	public String getDbSessionId() {
		return dbSessionId;
	}

	/**
	 * @param dbSessionId
	 *            the dbSessionId to set
	 */
	public void setDbSessionId(String dbSessionId) {
		this.dbSessionId = dbSessionId;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp
	 *            the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GetAccountDocumentRequest [acctId=" + acctId + ", loginType=" + loginType + ", dbSessionId="
				+ dbSessionId + ", lastLoginIp=" + lastLoginIp + ", loginId=" + loginId + "]";
	}

}
