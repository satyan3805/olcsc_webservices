/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.delegate.VehicleDelegate;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.presentation.form.TagRequestForm;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

public class TagRequestDisplayAction extends CSCBaseAction {
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {

        TagRequestForm actionForm = (TagRequestForm)form;
        actionForm.setVehicle(new TagDTO());
        getStates(request, actionForm, true);
        boolean setDefault =(actionForm.getVehicleClass() == null || actionForm.getVehicleClass().length()==0);
        getVehicleClasses(request,actionForm,setDefault);

        saveToken(request);
        return mapping.findForward("success");
    }
    
    protected void getStates(HttpServletRequest request, TagRequestForm form, boolean setDefault) throws Exception {
        StateDelegate stateDel = new StateDelegate();
        StateDTO[] states = stateDel.getStates();
        Collection<LabelValueBean> statesOptions = new ArrayList<LabelValueBean>(states.length);
        for (StateDTO stateDTO : states) {
            statesOptions.add(new LabelValueBean(stateDTO.getStateCode(), stateDTO.getStateCode()));
            if (setDefault && stateDTO.isDefaultValueFlag()) {
                if (form.getState()==null || form.getState().length()==0)
                    form.setState(stateDTO.getStateCode());
            }
        }
        request.getSession().setAttribute("states", statesOptions);  
    }

    protected void getVehicleClasses(HttpServletRequest request, TagRequestForm form, boolean setDefault) throws Exception {
        VehicleDelegate vehicleDel = new VehicleDelegate();
        Collection<VehicleClassDTO> vehicleClasses = vehicleDel.getVehicleClasses();
        ArrayList<LabelValueBean> vehicleClassOptions = new ArrayList<LabelValueBean>();
        for (VehicleClassDTO vehicleClass : vehicleClasses) {
            vehicleClassOptions.add(new LabelValueBean(vehicleClass.getVehicleClassDescr(), vehicleClass.getVehicleClassCode()));
            if (setDefault && vehicleClass.isDefaultValueFlag())
                form.setVehicleClass(vehicleClass.getVehicleClassCode());
        }
        request.getSession().setAttribute("vehicleClasses", vehicleClassOptions);
    }
}
