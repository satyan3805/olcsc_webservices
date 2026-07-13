/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.UIDateUtil;

public class TagRequestSaveAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(TagRequestSaveAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response)
      throws Exception
    {
      TagRequestForm actionForm = (TagRequestForm) form;
      
      if(!isTokenValid(request))
      {
        actionForm.setVehicle(new TagDTO());
        return mapping.findForward("success");
      }
        
      TagDTO vehicle = new TagDTO();
      vehicle.setLicPlate(actionForm.getLicensePlate());
      vehicle.setLicState(actionForm.getState());
      vehicle.setVehicleYear(actionForm.getYear());
      vehicle.setVehicleMake(actionForm.getMake());
      vehicle.setVehicleModel(actionForm.getModel());
      vehicle.setVehicleColor(actionForm.getColor());
      vehicle.setTemporaryLicPlate(actionForm.isTempLicense());
      vehicle.setVehicleClassCode(actionForm.getVehicleClass());
      vehicle.setCheckDup(actionForm.isCheckDup());
      vehicle.setAcctTagSeq(actionForm.getAcctTagSeq());
        
      if (vehicle.isTemporaryLicPlate())
      {
        String dateString = actionForm.getLicenseExpiration();
        if (dateString != null && dateString.trim().length()>0)
        {
            Date expireDate = UIDateUtil.parseDate(dateString);
            Calendar expireCal = Calendar.getInstance();
            expireCal.setTime(expireDate);
            vehicle.setPlateExpireDate(expireCal);
            vehicle.setPlateExpiration(dateString);
        }
      }
      else
      {
        vehicle.setPlateExpireDate(null);
        vehicle.setPlateExpiration("");
      }

      boolean saved = saveVehicle(vehicle, request, 
                  actionForm.getVehicleIndexToModify(), 
                  actionForm.isDeleteVehicle(), actionForm);
      if (saved)
      {
         TagDTO newVehicle = new TagDTO();
         newVehicle.setVehicleClassCode("2");
         actionForm.setVehicle(newVehicle);
         resetToken(request);
         saveToken(request);
      }
     
      return mapping.findForward("success");
    }


    private boolean saveVehicle(TagDTO vehicle, HttpServletRequest request, 
                             int vehicleIndexToModify, 
                             boolean deleteVehicle, TagRequestForm tagRequestForm) throws EtccException, EtccSecurityException
    {
      if (vehicle == null)
      {
        return false;
      }

      List<TagDTO> formSavedVehicles = tagRequestForm.getSavedVehicles();
      List<TagDTO> savedVehicles;

      HttpSession session = request.getSession();

      if (formSavedVehicles != null){
        savedVehicles = formSavedVehicles;
      } else {
          savedVehicles = new ArrayList<TagDTO>();
      }
    
      TagDelegate delegate = new TagDelegate();
      AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(session);
      long transId =  tagRequestForm.getRetailTransId();
      vehicle.setTransactionId(transId);
          
       vehicle.setAcctId(loginDTO.getAcctId());
      
      if (vehicleIndexToModify == -1)
      {
        logger.info(loginDTO);
        logger.info(vehicle);
        vehicle = delegate.addTag(loginDTO, vehicle, SessionUtil.getPosId(request));
        logger.info(vehicle);
        if(saveErrorMessages(request, vehicle, "saveFailed")){
           return false;
        }
        if (vehicle.isCheckDup() && vehicle.isDup()){
            request.setAttribute("dupPlate", "Y");
            return false;
        }//else
        request.setAttribute("dupPlate", "N");
        // add new vehicle
        
        tagRequestForm.setRetailTransId(vehicle.getTransactionId());
        logger.info("after add acct tag seq:" + vehicle.getAcctTagSeq());
        
        savedVehicles.add(vehicle);
      }
      else
      {
        int index = vehicleIndexToModify;
        if (deleteVehicle)
        {
          logger.info("before delete acct tag seq:" + vehicle.getAcctTagSeq());
          // delete 
          vehicle = delegate.deleteTag(loginDTO, vehicle, SessionUtil.getPosId(request));
          if (saveErrorMessages(request, vehicle, "saveFailed")){
             return false;
          }
          request.setAttribute("vehicleIndex", null);
          savedVehicles.remove(index);
        }
        else
        {
           logger.info("before update acct tag seq:" + vehicle.getAcctTagSeq());
          // update
           vehicle = delegate.updateTag(loginDTO, vehicle, SessionUtil.getPosId(request));
           if (saveErrorMessages(request, vehicle, "saveFailed")){
               return false;
            }
            if (vehicle.isCheckDup() && vehicle.isDup())
            {
                request.setAttribute("dupPlate", "Y");
                request.setAttribute("vehicleIndex", Integer.valueOf(index));
                return false;
            }//
            request.setAttribute("dupPlate", "N");
                
            logger.info("after update acct tag seq:" + vehicle.getAcctTagSeq());    
            savedVehicles.remove(index);
            savedVehicles.add(index, vehicle);
        }
      }

//      SessionUtil.setSavedVehicles(session, savedVehicles);
      tagRequestForm.setSavedVehicles(savedVehicles);
      return true;
    }
}
