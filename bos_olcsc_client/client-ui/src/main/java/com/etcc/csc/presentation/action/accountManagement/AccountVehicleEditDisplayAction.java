/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.form.TagRequestForm;

public class AccountVehicleEditDisplayAction extends Action {
	private static final Logger logger = Logger.getLogger(AccountVehicleEditDisplayAction.class);

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                                 throws Exception {
		HttpSession session = request.getSession();
        TagRequestForm tagRequestForm = (TagRequestForm) form;
        String strIdx = request.getParameter("index");
        int idx = (strIdx == null ? -1 : Integer.parseInt(strIdx));
        String forward = "success";
       // int noOfActiveTags = 0;
        if(Boolean.parseBoolean(request.getParameter("statuschange"))){
            forward = "changetagstatus";
            //String type =(String) request.getParameter("eztype")!= null ? request.getParameter("eztype"):"EZTAG";
            //List accountTags = null;
            //if(type.equalsIgnoreCase("EZPLATE")){
                 //accountTags = (List)session.getAttribute("activePbpTags");
            //}else{
            @SuppressWarnings("unchecked")
            List<TagDTO>   accountTags = (List<TagDTO>)session.getAttribute("accountTags");
            //}
            session.removeAttribute("accountTags");
            session.removeAttribute("activePbpTags");
            tagRequestForm.setSavedVehicles(accountTags);
            TagDTO tagDTO = tagRequestForm.getSavedVehicles().get(idx);
            logger.debug("Flip 1" + tagDTO.getTagStatusFlip());
        }
        if (idx > -1) {
            TagDTO tagDTO = null;
            if (forward.equals("changetagstatus")) {
                tagDTO = tagRequestForm.getSavedVehicles().get(idx);
                logger.debug("Flip "+ tagDTO.getTagStatusFlip());
                tagRequestForm.setAcctTagStatus(flipTagStatus(tagDTO.getTagStatusFlip(),tagDTO.getTagStatusDesc(),tagDTO.getAcctTagStatus()));

                if (tagDTO.getTagStatusFlip() == null)
                    tagRequestForm.setTagStatusFlip("X");
                else
                    tagRequestForm.setTagStatusFlip(tagDTO.getTagStatusFlip());
            }else{
                tagDTO = tagRequestForm.getSavedVehicle(idx);
                tagRequestForm.setAcctTagStatus(tagDTO.getAcctTagStatus());
                tagRequestForm.setTagStatusFlip(tagDTO.getTagStatusFlip());
            }
            logger.debug("TagDTO ==>" + tagDTO);
            /* Activation and Inactivation */
            tagRequestForm.setAgencyId(tagDTO.getAgencyId());
            //tagRequestForm.setAcctTagStatus(tagDTO.getAcctTagStatus());
            tagRequestForm.setTagStatusDesc(tagDTO.getTagStatusDesc());
            tagRequestForm.setTagTypeCode(tagDTO.getTagTypeCode());
            tagRequestForm.setVehicleIndexToModify(idx);
            tagRequestForm.setTempLicense(tagDTO.isTemporaryLicPlate());

                   //** Activation and Inactivation ends **/
            tagRequestForm.initialize(tagDTO, false);

            tagRequestForm.setTempLicense(tagDTO.isTemporaryLicPlate());

            logger.debug("TagDTO in AccountVehicleEditDisplayAction==>" + tagDTO.isTemporaryLicPlate());

            tagRequestForm.setTagAmount(tagDTO.getTagAmount());
            if (tagRequestForm.updatePbpTag(tagDTO)) {
                tagRequestForm.setAllowEditPbpStart(tagDTO.isAllowEditPbpStart());
                tagRequestForm.setAllowEditPbpEnd(tagDTO.isAllowEditPbpEnd());
            }

            request.setAttribute("tagRequestForm", tagRequestForm);
            logger.debug("tagRequestForm.Templicense  in AccountVehicleEditDisplayAction==>" +  tagRequestForm.isTempLicense());
            logger.debug("tagRequestForm.tagtypecode  in AccountVehicleEditDisplayAction==>" +  tagRequestForm.getTagTypeCode());


        }
        return mapping.findForward(forward);
    }

    private String flipTagStatus(String tagStatusFlip , String tagAcctStatus, String oldTagAcctStatus)
    {
        String function = oldTagAcctStatus;
        if(tagStatusFlip == null || tagAcctStatus == null){
         if(oldTagAcctStatus.equalsIgnoreCase("A"))
                function = "N";
          else  if(oldTagAcctStatus.equalsIgnoreCase("N"))
                function = "A";
        } else {
        if(tagStatusFlip.equalsIgnoreCase("A") && tagAcctStatus.equalsIgnoreCase("InActive")){
            function = "A";
        }else{
            if(tagStatusFlip.equalsIgnoreCase("N") && tagAcctStatus.equalsIgnoreCase("active")){
                function = "N";
            }
        }
        }
        /*
        * End of correct functionality
        */
        //this is done only for testing purpose
        /*if(tagAcctStatus.equalsIgnoreCase("Active")){
                    function = "N";
        }else{
             if(tagAcctStatus.equalsIgnoreCase("Inactive")){
                 function = "A";
                    }
        }*/
        return function;
    }
}
