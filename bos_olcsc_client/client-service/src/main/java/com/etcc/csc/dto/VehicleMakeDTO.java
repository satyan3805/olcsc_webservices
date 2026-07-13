package com.etcc.csc.dto;

/**
 * Based on VehicleMakeDTO imported from OLCSCService.
 * @author Milosh Boroyevich
 */
public class VehicleMakeDTO extends BaseDTO {
	private static final long serialVersionUID = -4061699589772417683L;
	private String vehicleMake;
    private boolean motorcycleFlag;

    /**
     * @return the vehicleMake
     */
    public String getVehicleMake() {
        return this.vehicleMake;
    }
    /**
     * @param vehicleMake the vehicleMake to set
     */
    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }
    /**
     * @return the motorcycleFlag
     */
    public boolean isMotorcycleFlag() {
        return this.motorcycleFlag;
    }
    /**
     * @param motorcycleFlag the motorcycleFlag to set
     */
    public void setMotorcycleFlag(boolean motorcycleFlag) {
        this.motorcycleFlag = motorcycleFlag;
    }
    
    @Override
    public String toString() {
        return "VehicleMakeDTO[" + (this.motorcycleFlag ? "Motorcycle:" : "Car:") + this.vehicleMake + "]";
    }
}
