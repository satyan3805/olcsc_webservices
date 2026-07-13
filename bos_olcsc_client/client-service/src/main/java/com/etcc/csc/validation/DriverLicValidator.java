/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.validation;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.StateDAO;
import com.etcc.csc.dto.StateDTO;

public abstract class DriverLicValidator extends BaseValidator {

	public static boolean validateDriverLic(String dlState, String driverLic)
			throws EtccException {
		
		boolean isValid = false;
		if (dlState == "" || dlState == null || dlState == "-") {
			throw new EtccException(
					"You did not choose Driver License State, please try again");
		}
		if (driverLic == "" || driverLic == null) {
			throw new EtccException(
					"You did not enter Driver License Number,please try again");
		}
		//msp_5827_olcscfix_dl_driving_table
		String dlPattern = getDriverLicensePattern(dlState);
		if(dlPattern == null || dlPattern.isEmpty()){
			isValid = true;
		}else{
			isValid = java.util.regex.Pattern.matches(dlPattern, driverLic);
		}
		
		return isValid;

	}
	
	//msp_5827_olcscfix_dl_driving_table
	private static String getDriverLicensePattern(String dlState) throws EtccException{
		String dlPattern = null;
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
        StateDAO stateDao = daoFactory.getDAO(StateDAO.class);
        for(StateDTO state : stateDao.getStates()){
        	if(state.getStateCode().equals(dlState.toUpperCase())){
        		dlPattern = state.getDriverLicensePattern();
        		break;
        	}
        }
		return dlPattern;
	}

}
