/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.form.TagRequestForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TagRequestRetrieveAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(TagRequestRetrieveAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response)
	throws Exception {
		TagRequestForm tagRequestForm = (TagRequestForm) form;
		int vehicleIndex = 
			Integer.parseInt(request.getParameter("vehicleIndex"));

		TagDTO vehicle = tagRequestForm.getSavedVehicle(vehicleIndex);
		if (vehicle == null)
			vehicle = new TagDTO();
		else if (logger.isDebugEnabled())
		    logger.debug("Retrieved Vehicle: " + vehicle);

		tagRequestForm.setVehicle(vehicle);
		request.setAttribute("vehicleIndex", Integer.valueOf(vehicleIndex));
		return mapping.findForward("success");
    }
}
