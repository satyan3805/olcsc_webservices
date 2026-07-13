/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.delegate.VehicleDelegate;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.dto.VehicleClassDTO;

public class AccountTollTagEditAction extends Action {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    StateDelegate stateDel = new StateDelegate();
    StateDTO[] states = stateDel.getStates();
    request.setAttribute("states", states);
    VehicleDelegate vehicleDel = new VehicleDelegate();
    Collection<VehicleClassDTO> vehicleClasses = vehicleDel.getVehicleClasses();
    request.setAttribute("vehicleClasses", vehicleClasses);
    return mapping.findForward("success");
  }
}
