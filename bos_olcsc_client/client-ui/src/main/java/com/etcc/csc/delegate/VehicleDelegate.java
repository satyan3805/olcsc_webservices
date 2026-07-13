package com.etcc.csc.delegate;

import java.util.Collection;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.dto.VehicleMakeDTO;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.VehicleInterface;

/**
 * Delegate for the Vehicle Service.
 */
public class VehicleDelegate implements VehicleInterface {
    public Collection<VehicleClassDTO> getVehicleClasses() throws EtccException {
        try {
            /*VehicleWS_Service service = new VehicleWS_ServiceLocator();
            VehicleWS_PortType port = service.getVehicleWSSoapHttpPort();
            GetVehicleClasses param = new GetVehicleClasses();
            Object[] result = port.getVehicleClasses(param).getResult();
            if (result != null) {
                return Arrays.asList(result);
            } else {
                return null;
            }
            */
//            return new Vehicle().getVehicleClasses();
//             //com.etcc.csc.service.VehicleWSSoapHttpPortClient port = new com.etcc.csc.service.VehicleWSSoapHttpPortClient();
//             //return port.getVehicleClasses();
//              UtilWsSoapHttpPortClient  port = stub();
//              return port.getVehicleClasses();
             return ServiceFactory.getImplementation(VehicleInterface.class).getVehicleClasses();
        } catch (Throwable t) {
            throw new EtccException("Error running getVehicleClasses: " + t, t);
        }
    }

    public Collection<VehicleMakeDTO> getVehicleMakes() throws EtccException {
        return ServiceFactory.getImplementation(VehicleInterface.class).getVehicleMakes();
    }
}
