/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.validation;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.util.CoreDateUtil;
import com.etcc.csc.util.StringUtil;

/**
 * Validates that the information in the Tag DTO is sane enough to send to the Database.
 * 
 * @author Unknown
 * @author (task 488) Stephen Davidson
 * 
 */
public class TagRequestValidator extends BaseValidator {

    private static final Logger logger = Logger.getLogger(TagRequestValidator.class);

    /**
     * Verifies that the data in the Tag is sane enough to send to the Database.
     * @param tag The Tag to validate.
     * @throws ValidationException contains the list of validation exception messages.
     */
    public void validateTag(TagDTO tag,boolean isExpAccount) throws ValidationException {
        final boolean debugEnabled = logger.isDebugEnabled();

        Collection<String> msgs = new ArrayList<String>();

        if (StringUtil.isEmpty(tag.getLicState())) {
            if (debugEnabled)
                logger.debug("validateTag.tag.getLicState()=" + tag.getLicState());
            msgs.add("Invalid Tag Information. LicState is a required field");
        }

        if (!StringUtil.isEmpty(tag.getLicPlate()) && (!tag.getLicPlate().matches("^[a-zA-Z0-9]{1,15}$"))) {
            if (debugEnabled)
                logger.debug("validateTag.tag.getLicPlate()=" + tag.getLicPlate());
            msgs.add("Invalid Tag Information. Incorrect value for LicPlate");
        }
       //if (!tag.isTemporaryLicPlate() && StringUtil.isEmpty(tag.getLicPlate())) {
	   //D13868 08/19/2013 vgovindaswamy EZPlate Null issue 
	   if (!isExpAccount && !tag.isTemporaryLicPlate() && !tag.isPbpTag() && StringUtil.isEmpty(tag.getLicPlate()) ) {
            if (debugEnabled)
                logger.debug("validateTag.tag.getTemporaryLicPlate()=" + tag.isTemporaryLicPlate()
                        + ", tag.getLicPlate()=" + tag.getLicPlate());
            msgs.add("Invalid Tag Information. License plate Number is required when it is not a Temporary License Plate");
        }
	   //Express Account Changes
	    if (isExpAccount && !StringUtil.isEmpty(tag.getVehicleYear())){
	      if (!isLong(tag.getVehicleYear()) || (tag.getVehicleYear().length() != 4)){
    			if (debugEnabled)
                    logger.debug("validateTag.tag.getVehicleYear()=" + tag.getVehicleYear());
                msgs.add("Invalid Tag Information. VehicleYear should be 4 digits");
	      }
	    }
	    if (!isExpAccount && (StringUtil.isEmpty(tag.getVehicleYear()) || !isLong(tag.getVehicleYear())|| (tag.getVehicleYear().length() != 4))) {
            if (debugEnabled)
                logger.debug("validateTag.tag.getVehicleYear()=" + tag.getVehicleYear());
            msgs.add("Invalid Tag Information. VehicleYear should be 4 digits");
        }
	   //Express Account Changes
	    if (isExpAccount && !StringUtil.isEmpty(tag.getVehicleColor())){
	        if (!isMaxLength(tag.getVehicleColor(), 20, true)) {
	            if (debugEnabled)
	             logger.debug("validateTag.tag.getVehicleColor()=" + tag.getVehicleColor());
	            msgs.add("Invalid Tag Information. VehicleColor is a required field");
	        }
	    }
        if(!isExpAccount && !isMaxLength(tag.getVehicleColor(), 20, true)){
            if (debugEnabled)
                logger.debug("validateTag.tag.getVehicleColor()=" + tag.getVehicleColor());
            msgs.add("Invalid Tag Information. VehicleColor is a required field");
	    }
	    if (isExpAccount && !StringUtil.isEmpty(tag.getVehicleMake())){
	    	if (!tag.getVehicleMake().matches("^[0-9a-zA-Z\\s,#.\\-]*$")){
	    		if (debugEnabled)
	                logger.debug("validateTag.tag.getVehicleMake()=" + tag.getVehicleMake());
	            msgs.add("Invalid Tag Information. Incorrect value for VehicleMake");
	    	}
	    }
	    if (!isExpAccount && (StringUtil.isEmpty(tag.getVehicleMake()) || !tag.getVehicleMake().matches("^[0-9a-zA-Z\\s,#.\\-]*$"))) {
            if (debugEnabled)
                logger.debug("validateTag.tag.getVehicleMake()=" + tag.getVehicleMake());
            msgs.add("Invalid Tag Information. Incorrect value for VehicleMake");
        }
	    if (isExpAccount && !StringUtil.isEmpty(tag.getVehicleModel())){
	    	if ((!isMaxLength(tag.getVehicleModel(), 30, true)  || (!tag.getVehicleModel().matches("^[0-9a-zA-Z\\s,#.\\-]*$")))) {
	            if (debugEnabled)
	                logger.debug("validateTag.tag.getVehicleModel()=" + tag.getVehicleModel());
	            msgs.add("Invalid Tag Information. Incorrect value for VehicleModel");
	    	}
	    }
	    if (!isExpAccount && (!isMaxLength(tag.getVehicleModel(), 30, true) || (!tag.getVehicleModel().matches("^[0-9a-zA-Z\\s,#.\\-]*$")))) {
            if (debugEnabled)
                logger.debug("validateTag.tag.getVehicleModel()=" + tag.getVehicleModel());
            msgs.add("Invalid Tag Information. Incorrect value for VehicleModel");
        }
        if ((!StringUtil.isEmpty(tag.getNickname()) && (!isMaxLength(tag.getNickname(), 30, false) || !tag.getNickname().matches("^[0-9a-zA-Z\\s,#.\\-\']*$")))) {
            if (debugEnabled)
                logger.debug("validateTag.tag.getNickname()=" + tag.getNickname());
            msgs.add("Invalid Tag Information. Incorrect value for the optional field Nickname");
        }
        // if (isEmpty (tag.getPbpStart())) throw new
        // ValidationException("Invalid Tag Information. PbpStart is a required field");
        /*if (isExpAccount && (!StringUtil.isEmpty(tag.getNickname()) && (!isMaxLength(tag.getNickname(), 30, false) || !tag.getNickname().matches("^[0-9a-zA-Z ,#.\\-\']*$")))) {
            if (debugEnabled)
                logger.debug("validateTag.tag.getNickname()=" + tag.getNickname());
            msgs.add("Invalid Tag Information. Incorrect value for the optional field Nickname");
        }*/
        if ((!isExpAccount  || !StringUtil.isEmpty(tag.getTagTypeCode())) && "P".equalsIgnoreCase(tag.getTagTypeCode())) {
            // if (!isDate(tag.getPbpStart()){
            if (tag.getPbpStartDate() == null || tag.getPbpStartDate().getTime() == 0) {
                if (debugEnabled)
                    logger.debug("validateTag.tag.getPbpStart()=" + tag.getPbpStartDate());
                msgs.add("Invalid Tag Information. PbpStart date format must be "
                        + CoreDateUtil.MEDIUM_DATE_TIME_PATTERN);
            }
            // if (!isDate(tag.getPbpEnd())){
            if (tag.getPbpEndDate() == null || tag.getPbpEndDate().getTime() == 0) {
                if (debugEnabled)
                    logger.debug("validateTag.tag.getPbpEnd()=" + tag.getPbpEndDate());
                msgs.add("Invalid Tag Information. PbpEnd date format must be "
                                + CoreDateUtil.MEDIUM_DATE_TIME_PATTERN);
            }
        }
        if (msgs.size() > 0) {
            throw new ValidationException(msgs);
        }
    }

    public boolean validateConfirmAddTags() {
        // nothing needed
        return true;
    }

    public boolean validateAddTagsReceipt() {
        // nothing needed
        return true;
    }
}
