package com.etcc.csc.presentation.action;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.delegate.VehicleDelegate;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.util.SessionUtil;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class GetTollTagVehicleInfoAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        DynaValidatorForm actionForm = (DynaValidatorForm)form;
        StateDTO[] states = new StateDelegate().getStates();
        request.setAttribute("states", states);
        
        for (StateDTO state : states) {
            if (state.isDefaultValueFlag()) {
                if (actionForm.get("licensePlateState")==null || actionForm.get("licensePlateState").toString().length()==0)
                    actionForm.set("licensePlateState", state.getStateCode());
                break;
            }
        }

        Collection<VehicleClassDTO> vehicleClasses = new VehicleDelegate().getVehicleClasses();
        request.setAttribute("vehicleClasses", vehicleClasses);
        setVehicleClassDesc(SessionUtil.getSavedVehicleMap(request.getSession()), vehicleClasses);
        Object code = actionForm.get("vehicleClassCode");
        if (code==null || code.toString().length()==0)
            actionForm.set("vehicleClassCode", "2");
        return mapping.findForward("success");
    }

    private void setVehicleClassDesc(Map<String, TagDTO> savedVehicles, 
                                     Collection<VehicleClassDTO> vehicleClasses) {
    	if ( savedVehicles != null && !savedVehicles.isEmpty() ) {
    		for (TagDTO tagDTO : savedVehicles.values()) {
    			tagDTO.setVehicleDescr( mapVehicleClassToDesc( tagDTO.getVehicleClassCode(), vehicleClasses) );
    		}
    	}
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
