
package com.etcc.csc.service.sal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.etcc.csc.service.webservice.InvoiceType;
import com.etcc.csc.service.webservice.ResponseType;
import com.etcc.csc.service.webservice.ViolationType;



/**
 * Data structure for holding response from calling GetInvoiceViolations stored procedure
 * @author naphan
 *
 */
public class GetInvoiceViolationsProcResponse {
	private List<ViolationType> violations = null;
	
	public void setViolations(List<ViolationType> violations) {
		this.violations = violations;
	}
	
	public List<ViolationType> getViolations() {
		return violations;
	}
}
