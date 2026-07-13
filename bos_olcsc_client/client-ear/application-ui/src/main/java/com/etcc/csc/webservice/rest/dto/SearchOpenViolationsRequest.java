package com.etcc.csc.webservice.rest.dto;

public class SearchOpenViolationsRequest {

	private String licPlate;
	private String licState;
	/**
	 * @return the licPlate
	 */
	public String getLicPlate() {
		return licPlate;
	}
	/**
	 * @param licPlate the licPlate to set
	 */
	public void setLicPlate(String licPlate) {
		this.licPlate = licPlate;
	}
	/**
	 * @return the licState
	 */
	public String getLicState() {
		return licState;
	}
	/**
	 * @param licState the licState to set
	 */
	public void setLicState(String licState) {
		this.licState = licState;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchOpenViolationsRequest [licPlate=" + licPlate + ", licState=" + licState + "]";
	}
	
	

}
