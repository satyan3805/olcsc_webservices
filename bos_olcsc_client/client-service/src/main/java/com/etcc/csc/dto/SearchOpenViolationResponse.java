package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.List;
import com.etcc.csc.dto.ViolationDetails;

public class SearchOpenViolationResponse extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5809868777809073956L;
	private List<ViolationDetails> violationDetails = new ArrayList<ViolationDetails>();

	/**
	 * @return the violationDetails
	 */
	public List<ViolationDetails> getViolationDetails() {
		return violationDetails;
	}

	/**
	 * @param violationDetails the violationDetails to set
	 */
	public void setViolationDetails(List<ViolationDetails> violationDetails) {
		this.violationDetails = violationDetails;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchOpenViolationResponse [violationDetails=" + violationDetails + "]";
	}
	
	

}
