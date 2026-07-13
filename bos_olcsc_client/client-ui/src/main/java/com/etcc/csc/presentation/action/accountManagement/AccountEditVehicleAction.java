/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.UIDateUtil;

public class AccountEditVehicleAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccountEditVehicleAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

        TagRequestForm tagRequestForm = (TagRequestForm) form;
        String strIdx = request.getParameter("index");
        int idx = (strIdx == null ? -1 : Integer.parseInt(strIdx));

        logger.debug("strIdx" + idx);
        logger.debug("tagRequestForm.isTempLicense() in AccountEditVehicleAction==>" +  tagRequestForm.isTempLicense());

        String forwardFail = "failure";
        if (Boolean.parseBoolean(request.getParameter("statuschange"))) {
            forwardFail = "tagStatusFailure";
            logger.debug("No of Active Tags ..." + tagRequestForm.getNoOfActiveTags());
        }

        boolean inactivePbpTag = StringUtil.stringToBoolean(request.getParameter("inactive"));

       TagDTO tagDTO = tagRequestForm.newTagDTO();

       if (idx != -1){

           tagDTO = tagRequestForm.getSavedVehicle(idx);
       }


        logger.debug("tagRequestForm.getTagTypeCode() in AccountEditVehicleAction==>" +  tagRequestForm.getTagTypeCode());


        // TagDTO tagDTO = tagRequestForm.newTagDTO(tagRequestForm.isTempLicense());

        logger.debug("tagRequestForm.isTempLicense() in AccountEditVehicleAction==>" +  tagRequestForm.isTempLicense());



        /*
        boolean valid = validateData(tagRequestForm, request);
        if (! valid) {
            return mapping.findForward("failure");
        }
        */
        /** Activation and Inactivation Starts **/
        if(tagRequestForm.getTagStatusFlip()!= null){
            tagDTO.setTagStatusFlip(tagRequestForm.getTagStatusFlip());
            tagDTO.setAcctTagStatus(tagRequestForm.getAcctTagStatus());
        }
        tagDTO.setAcctVehicleId(tagRequestForm.getAcctTagSeq());
        tagDTO.setAgencyId(tagRequestForm.getAgencyId());
        tagDTO.setTagStatusDesc(tagRequestForm.getTagStatusDesc());
        tagDTO.setTagTypeCode(tagRequestForm.getTagTypeCode());
        /** Activation and Inactivation Ends **/

       // tagDTO.setTemporaryLicPlate(tagRequestForm.isTempLicense());

        logger.debug("TagDTO in AccountEditVehicleAction2==>" +  tagDTO.isTemporaryLicPlate());

        if (tagRequestForm.isPbpTag()) {
              tagDTO.setPbpStart(UIDateUtil.convertDateFormat(tagRequestForm.getPbpStart()));
              tagDTO.setPbpEnd(UIDateUtil.convertDateFormat(tagRequestForm.getPbpEnd()));

        }
        else {
            tagDTO.setPbpStartDate(null);
            tagDTO.setPbpEndDate(null);
            //System.out.println("The end time period second time joemon is :"+tagRequestForm.getPbpEnd());
        }

        if (inactivePbpTag) {
            Calendar cal = Calendar.getInstance();
          cal.add(Calendar.DAY_OF_MONTH, -1);
            //tagDTO.setPbpEnd(UIDateUtil.getMediumDateTime(cal));
            tagDTO.setPbpEndDate(cal.getTime());
        }

        TagDelegate delegate = new TagDelegate();
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());

        if (loginDTO == null)
            throw new EtccSecurityException("Session Timed out in AccountEditVehicleAction");

        //tagDTO.setTransactionId(-1);
        tagDTO.setAcctId(loginDTO.getAcctId());

        logger.debug("TagDTO in AccountEditVehicleAction3==>" + tagDTO.isTemporaryLicPlate());

       tagDTO = delegate.updateTag(loginDTO, tagDTO, SessionUtil.getPosId(request));

        //logger.debug("modify tagDTO " + tagDTO.getAgencyId() + " " + tagDTO.getTagTypeCode());


        if (tagDTO.isViolationExist()) {
        /*	System.out.println("Joemon Yes Violation exists.");
            ActionMessages messages = getMessages(request);
            ActionMessage msg = new ActionMessage("tagRequestForm.inactivate.violation.error",
              tagDTO.getFullTagId(), "javascript:payViolations();");
            messages.add("saveFailed", msg);
            saveMessages(request, messages);*/
        	saveAlerts(request, tagDTO.getAlerts());
            return mapping.findForward("failure");
            //return mapping.findForward(forwardFail);
        }
       if (saveErrorMessages(request, tagDTO, "saveFailed")) {
            return mapping.findForward(forwardFail);
        }
        if(saveAlerts(request, tagDTO.getAlerts())){
            if(logger.isDebugEnabled()){
                logger.debug("The Errors for TagStatus" + tagDTO.getAlerts());
            }
            // return mapping.findForward("failure");
        }

        if (tagDTO.isDup()) {
            request.setAttribute("dupLic", tagRequestForm.getState()+ "-"+ tagRequestForm.getLicensePlate());
        }
        //This is for Active and Inactive Tag Change message
        request.setAttribute("vehicleChange",tagDTO.getLicPlate());
        /*ActionMessages messages = new ActionMessages();
        messages.add("alerts", new ActionMessage("The vehicle with Licence Plate Number " + tagDTO.getLicPlate() + " has been edited or status has been changed"));
        saveMessages(request, messages);*/
        request.setAttribute("setEvent3", Boolean.TRUE);
        if (Boolean.parseBoolean(request.getParameter("statuschange")) &&
                "Inactive".equalsIgnoreCase(tagRequestForm.getTagStatusDesc())) {
            request.setAttribute("activationOfInactive", Boolean.TRUE);
        }
        int noOfActivetags = 0;
        noOfActivetags= countOfActiveTags(tagRequestForm, loginDTO);
        tagRequestForm.setNoOfActiveTags(noOfActivetags);

        if(tagRequestForm.getNoOfActiveTags() == 0){
            request.setAttribute("activeTags",Boolean.TRUE);
            request.setAttribute("accountclosure",Boolean.TRUE);
            request.setAttribute("closureAlert",Boolean.TRUE);
        }
        return mapping.findForward("success");
    }

//    private boolean validateData(TagRequestForm tagRequestForm, HttpServletRequest request) throws EtccException {
//        String make = tagRequestForm.getMake();
//        VehicleDelegate vmDel = new VehicleDelegate();
//        boolean vehicleMakeValid = false;
//        for (VehicleMakeDTO vm : vmDel.getVehicleMakes()) {
//            if (vm.getVehicleMake().equals(make)) {
//                vehicleMakeValid = true;
//                break;
//            }
//        }
//
//        if (! vehicleMakeValid) {
//            ActionMessages messages = new ActionMessages();
//            ActionMessage msg = new ActionMessage("error.generic", "Invalid vehicle make");
//            messages.add("saveFailed", msg);
//            saveMessages(request, messages);
//        }
//        return vehicleMakeValid;
//    }


    private int countOfActiveTags(TagRequestForm tagRequestForm, AccountLoginDTO loginDTO) {
        int count = 0;
        AccountTagsDTO tagsDTO = null;
        // logger.debug("Saved Vehicle Size " + tagRequestForm.getSavedVehicles());
        try {
            tagsDTO = new TagDelegate().getAccountTags(loginDTO, "");

        } catch (EtccException ee) {
            return count;
        }
        if (tagsDTO == null)
            return count;

        Collection<TagDTO> acctTags = tagsDTO.getAccountTags();
        if (acctTags != null) {
            for (TagDTO tagDTO : acctTags) {
                if (tagDTO.getAcctTagStatus() != null && (tagDTO.getAcctTagStatus().equalsIgnoreCase("A"))
                        || tagDTO.getTagStatusDesc().equalsIgnoreCase("Pending")) {
                    count++;
                }
            }
        }
        Collection<TagDTO> acctPbpTags = tagsDTO.getPbpTags();
        if (acctPbpTags != null) {
            for (TagDTO tagDTO : acctPbpTags) {
                if (tagDTO.getTagStatusDesc() != null
                        && (tagDTO.getTagStatusDesc().equalsIgnoreCase("Pending"))) {
                    count++;
                }
            }
        }
        return count;
    }
}
