/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.delegate.VehicleDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.UIDateUtil;

public class GetTollTagSaveVehicleAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(GetTollTagSaveVehicleAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        DynaActionForm actionForm = (DynaActionForm)form;

        TagDTO tagDTO = new TagDTO();
        tagDTO.setLicPlate(ActionFormUtil.getString(actionForm, 
                                                    "licensePlate"));
        tagDTO.setLicState(ActionFormUtil.getString(actionForm, 
                                                    "licensePlateState"));

        boolean tempLicensePlate = 
            ActionFormUtil.getBoolean(actionForm, "tempLicensePlate");
        tagDTO.setTemporaryLicPlate(tempLicensePlate);
        if (tempLicensePlate) {
            Calendar plateExpireDate = Calendar.getInstance();
            plateExpireDate.setTime(UIDateUtil.parseDate(ActionFormUtil.getString(actionForm, "tempLicensePlateExpireDate")));
            tagDTO.setPlateExpireDate(plateExpireDate);
        }
        tagDTO.setVehicleYear(ActionFormUtil.getString(actionForm, 
                                                       "vehicleYear"));
        tagDTO.setVehicleColor(ActionFormUtil.getString(actionForm, 
                                                        "vehicleColor"));
        tagDTO.setVehicleMake(ActionFormUtil.getString(actionForm, 
                                                       "vehicleMake"));
        tagDTO.setVehicleModel(ActionFormUtil.getString(actionForm, 
                                                        "vehicleModel"));
        tagDTO.setVehicleClassCode(ActionFormUtil.getString(actionForm, 
                                                            "vehicleClassCode"));
        if (StringUtils.isNotEmpty(ActionFormUtil.getString(actionForm, 
                                                            "vehicleIndexToModify"))) {
            tagDTO.setAcctTagSeq(Long.parseLong(ActionFormUtil.getString(actionForm, 
                                                                         "vehicleIndexToModify")));
        }
        tagDTO.setCheckDup(!ActionFormUtil.getBoolean(actionForm, 
                                                      "skipDuplicateCheck"));

        actionForm.set("skipDuplicateCheck", null);

        boolean saveVehicleSuccess = 
            saveVehicle(tagDTO, request, actionForm, ActionFormUtil.getString(actionForm, 
                                                                              "vehicleIndexToModify"), 
                        ActionFormUtil.getBoolean(actionForm, 
                                                  "deleteVehicle"));

        if (saveVehicleSuccess) {
            actionForm.set("licensePlate", null);
            actionForm.set("licensePlateState", "TX");
            actionForm.set("tempLicensePlate", null);
            actionForm.set("tempLicensePlateExpireDate", null);
            actionForm.set("vehicleYear", null);
            actionForm.set("vehicleColor", null);
            actionForm.set("vehicleMake", null);
            actionForm.set("vehicleModel", null);
            actionForm.set("vehicleClassCode", "2");
            actionForm.set("vehicleIndexToModify", null);
            actionForm.set("deleteVehicle", null);
        }
        else {
            mapping.findForward("failure");
        }

        request.setAttribute("states", new StateDelegate().getStates());
        Collection<VehicleClassDTO> vehicleClasses = new VehicleDelegate().getVehicleClasses();
        request.setAttribute("vehicleClasses", vehicleClasses);
        setVehicleClassDesc(SessionUtil.getSavedVehicleMap(request.getSession()), vehicleClasses);

        return mapping.findForward("success");
    }


    private boolean saveVehicle(TagDTO tagDTO, HttpServletRequest request, 
                                DynaActionForm actionForm, 
                                String vehicleIndexToModify, 
                                boolean deleteVehicle) throws Exception {
        if (tagDTO == null) {
            return false;
        }
        boolean success = false;
        HttpSession session = request.getSession();
        Map<String, TagDTO> savedVehicles = SessionUtil.getSavedVehicleMap(session);
        if (savedVehicles == null) {
            savedVehicles = new HashMap<String, TagDTO>();
        }

        if ( StringUtils.isEmpty( vehicleIndexToModify ) ) {
            // add new vehicle
            TagDTO addedTagDTO = addTag(tagDTO, request);
            if (logger.isDebugEnabled()){
                logger.debug(addedTagDTO);
            }
            if (saveErrorMessages(request,addedTagDTO.getErrors(), "tagSaveFailed")){
                //Do nothing
            } else if (addedTagDTO.isDup()) {
                request.setAttribute("alertDuplicate", "Y");
            } else {
                savedVehicles.put(Long.toString(addedTagDTO.getAcctTagSeq()), tagDTO);
                success = true;
            }
        } else {
            if (deleteVehicle) {
                // delete 
//                TagDTO deletedTagDTO = updateTag(tagDTO, request, "D");
                savedVehicles.remove(vehicleIndexToModify);
                success = true;
            } else {
                // update
                TagDTO updatedTagDTO = updateTag(tagDTO, request);
                if (updatedTagDTO.isDup()) {
                    request.setAttribute("alertDuplicate", "Y");
                } else {
                    savedVehicles.remove(vehicleIndexToModify);
                    savedVehicles.put("" + updatedTagDTO.getAcctTagSeq(), 
                                      tagDTO);
                    success = true;
                }
            }
        }
        SessionUtil.setSavedVehicleMap(session, savedVehicles);
        return success;
    }


    private TagDTO addTag(TagDTO tagDTO, 
                          HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();
        AccountLoginDTO accountLoginDTO = SessionUtil.getSessionAccountLogin(session);

        tagDTO.setAcctId(accountLoginDTO.getAcctId());
        tagDTO.setTransactionId(SessionUtil.getRetailTransId(session));

        TagDTO addedDTO = new TagDelegate().addTag(accountLoginDTO, tagDTO, SessionUtil.getPosId(request));
        saveErrorMessages(request, addedDTO, "addTagError");
        return addedDTO;
    }


    private TagDTO updateTag(TagDTO tagDTO, HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();
        AccountLoginDTO accountLoginDTO = SessionUtil.getSessionAccountLogin(session);

        tagDTO.setAcctId(accountLoginDTO.getAcctId());
        tagDTO.setTransactionId(SessionUtil.getRetailTransId(session));

        TagDTO updatedTagDTO = new TagDelegate().updateTag(accountLoginDTO, tagDTO, SessionUtil.getPosId(request));
        saveErrorMessages(request, updatedTagDTO, "updateTagError");
        return updatedTagDTO;
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
