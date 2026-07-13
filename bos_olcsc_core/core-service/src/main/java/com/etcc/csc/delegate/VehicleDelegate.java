package com.etcc.csc.delegate;

import com.etcc.csc.common.DAOFactory;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Delegate;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ServiceObjectEnum;
import com.etcc.csc.dao.VehicleDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.service.VehicleInterface;


/**
 * Wrapper between the web service stub and the web service client.
 */
public class VehicleDelegate extends Delegate implements VehicleInterface{
    
    VehicleInterface vi = (VehicleInterface)getServiceObject(ServiceObjectEnum.VEHICLE);
    
	private static Logger logger = Logger.getLogger(VehicleDelegate.class);
    
    
    public VehicleDelegate() {
        super(VehicleDelegate.class);
    }

    public Collection getVehicleClasses(String lang) throws EtccException{
		logger.info("Entering getVehicleClasses() @ VehicleDelegate");    	
        try {
            return vi.getVehicleClasses(lang);
        } catch (Throwable t) {
			logger.error("Error in getVehicleClasses() @ VehicleDelegate ", t);        	
            throw new EtccException("Error running getVehicleClasses: " + t, t);
        }// end of try-catch()
    }// end of getVehicleClasses()
    
    public Collection getLicPlateTypes(String lang) throws EtccException{
		logger.info("Entering getLicPlateTypes() @ VehicleDelegate");        
    	try {
            return vi.getLicPlateTypes(lang);
        } catch (Throwable t) {
			logger.error("Error in getLicPlateTypes() @ VehicleDelegate ", t);        	
            throw new EtccException("Error running getLicPlateTypes: " + t, t);
        }// end of try-catch()
    }// end of getLicPlateTypes()
    
    public String[] getValidationMessages(AccountLoginDTO accountLoginDTO,TagDTO tag,long eventId,
    		String action) throws EtccException{
		logger.info("Entering getValidationMessages() @ VehicleDelegate");
		try {
            return vi.getValidationMessages(accountLoginDTO,tag,eventId,action);
        } catch (Throwable t) {
			logger.error("Error in getValidationMessages() @ VehicleDelegate ", t);        	
            throw new EtccException("Error running getVehicleClasses: " + t, t);
        }// end of try-catch()
    }// end of getValidationMessages()
    
    public TagDTO[] saveVehicleInfo(AccountLoginDTO acctLoginDto, TagDTO[] tags, String action) throws EtccSecurityException, EtccException {
		logger.info("Entering saveVehicleInfo() @ VehicleDelegate");
    	try {
			return vi.saveVehicleInfo(acctLoginDto, tags, action);
		} catch (EtccSecurityException se) {
            logger.equals(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
			logger.error("Error in saveVehicleInfo() @ VehicleDelegate ", t);        	
			throw new EtccException("Error running saveVehicleInfo: " + t, t);
		}// end of try-catch()
	}// end of saveVehicleInfo()
    
    public String[] deleteVehicle(AccountLoginDTO acctLoginDto, TagDTO tags) throws EtccSecurityException, EtccException {
		logger.info("Entering deleteVehicle() @ VehicleDelegate");    	
		try {
			return vi.deleteVehicle(acctLoginDto, tags);
		} catch (EtccSecurityException se) {
            logger.equals(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
			logger.error("Error in deleteVehicle() @ VehicleDelegate ", t);        	
			throw new EtccException("Error running saveVehicleInfo: " + t, t);
		}// end of try-catch()
	}// end of deleteVehicle()

	public boolean findExistingLicPlateNbr(String licPlate,Long accountId)
			throws EtccException {
		logger.info("Entering findExistingLicPlateNbr() @ VehicleDelegate");		
		try {
			DAOFactory daoFactory = DAOFactory.getDAOFactory();
			VehicleDAO vehicleDao = daoFactory.getVehicleDAO();
			boolean result = vehicleDao.findExistingLicPlateNbr(licPlate,accountId);
			return result;
		} catch (EtccException etccEx) {
			logger.error("Error in findExistingLicPlateNbr() @ VehicleDelegate ", etccEx);			
			throw etccEx;
		}// end of try-catch()
	}// end of findExistingLicPlateNbr()

	public String calculateAutochargeAmount(AccountLoginDTO acctLoginDto) throws EtccSecurityException, EtccException {
		logger.info("Entering calculateAutochargeAmount() @ VehicleDelegate");		
		try {
			return vi.calculateAutochargeAmount(acctLoginDto);
		} catch (EtccSecurityException se) {
            logger.equals(se);
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
			logger.error(t);
			throw new EtccException("Error running calculateAutochargeAmount: " + t, t);
		}// end of try-catch()
	}// end of calculateAutochargeAmount()

}// end of VehicleDelegate Class
