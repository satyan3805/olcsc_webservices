/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class AccountReceiptsForm extends ValidatorActionForm {
	private static final long serialVersionUID = -3129340954739537748L;

	private String acctId;
    private String startDate;
    private String endDate;

    @Override
	public void reset(ActionMapping actionMapping, 
                      HttpServletRequest request) {
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
