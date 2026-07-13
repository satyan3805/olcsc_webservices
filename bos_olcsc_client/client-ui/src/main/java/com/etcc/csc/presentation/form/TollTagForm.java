/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorActionForm;

public class TollTagForm extends DynaValidatorActionForm {
	private static final long serialVersionUID = 3099676311797661810L;

	@Override
	public void reset(ActionMapping actionMapping, 
                      HttpServletRequest request) {
        super.reset(actionMapping, request);
        set("deleteVehicle", Boolean.FALSE);
        set("tempLicensePlate", Boolean.FALSE);
        // set("vehicleIndexToModify", null);
    }
}
