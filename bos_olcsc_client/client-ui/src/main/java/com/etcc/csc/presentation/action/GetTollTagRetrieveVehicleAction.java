/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.delegate.VehicleDelegate;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.UIDateUtil;

import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class GetTollTagRetrieveVehicleAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        DynaActionForm actionForm = (DynaActionForm)form;
        HttpSession session = request.getSession();
        String vehicleIndex = request.getParameter("vehicleIndex");
        TagDTO tagDTO = retrieveVehicle(vehicleIndex, session);

        actionForm.set("licensePlate", tagDTO.getLicPlate());
        actionForm.set("licensePlateState", tagDTO.getLicState());
        actionForm.set("tempLicensePlate", 
                       Boolean.valueOf(tagDTO.isTemporaryLicPlate()));
        if (tagDTO.isTemporaryLicPlate()) {
            actionForm.set("tempLicensePlateExpireDate", UIDateUtil.getShortDate(tagDTO.getPlateExpireDate()));
        }
        actionForm.set("vehicleYear", tagDTO.getVehicleYear());
        actionForm.set("vehicleColor", tagDTO.getVehicleColor());
        actionForm.set("vehicleMake", tagDTO.getVehicleMake());
        actionForm.set("vehicleModel", tagDTO.getVehicleModel());
        actionForm.set("vehicleClassCode", tagDTO.getVehicleClassCode());
        actionForm.set("deleteVehicle", Boolean.valueOf(false));
        actionForm.set("vehicleIndexToModify", vehicleIndex);

        request.setAttribute("vehicleIndex", vehicleIndex);
        request.setAttribute("states", new StateDelegate().getStates());
        Collection<VehicleClassDTO> vehicleClasses = new VehicleDelegate().getVehicleClasses();
        request.setAttribute("vehicleClasses", vehicleClasses);
        setVehicleClassDesc(SessionUtil.getSavedVehicleMap(request.getSession()), vehicleClasses);

        return mapping.findForward("success");
    }


    private TagDTO retrieveVehicle(String vehicleIndex, HttpSession session) {
        return SessionUtil.getSavedVehicleMap(session).get(vehicleIndex);
    }

    private void setVehicleClassDesc(Map<String,TagDTO> savedVehicles, 
                                     Collection<VehicleClassDTO> vehicleClasses) {
      if ( savedVehicles != null && !savedVehicles.isEmpty() )
        for (TagDTO tagDTO : savedVehicles.values())
          tagDTO.setVehicleDescr( mapVehicleClassToDesc( tagDTO.getVehicleClassCode(), 
                                          vehicleClasses) );
    }

    private String mapVehicleClassToDesc(String vehicleClassCode, 
                                         Collection<VehicleClassDTO> vehicleClasses) {
        for (VehicleClassDTO vehicleClassDTO : vehicleClasses) {
            if (vehicleClassDTO != null && 
                vehicleClassCode.equals(vehicleClassDTO.getVehicleClassCode())) {
                return vehicleClassDTO.getVehicleClassDescr();
            }
        }
        return null;
    }
}
