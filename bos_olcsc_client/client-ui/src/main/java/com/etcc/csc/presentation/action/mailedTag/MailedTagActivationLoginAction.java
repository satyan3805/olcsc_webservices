/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.mailedTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;

public class MailedTagActivationLoginAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(MailedTagActivationLoginAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {

        DynaValidatorActionForm mailedTagActivationForm = (DynaValidatorActionForm)form;
        TagDelegate tagReqDel = new TagDelegate();
        AppDelegate appdel = new AppDelegate();
        String domainName = appdel.getDomainName();
        logger.debug("DOMAIN: " + domainName);
        Object entryPoint = request.getParameter("statuschange");
        String page = request.getParameter("page");
        HttpSession session = request.getSession();
        if (page != null && page.equalsIgnoreCase("page1")) {
            logger.debug("tagActivationNbr :"+mailedTagActivationForm.get("tagActivationNbr"));
            logger.debug("driverLicenseNumber :"+mailedTagActivationForm.get("driverLicenseNumber"));
            logger.debug("driverLicenseState :"+mailedTagActivationForm.get("driverLicenseState"));
            logger.debug("taxId :"+mailedTagActivationForm.get("taxId"));

            if (entryPoint!=null)
                request.setAttribute("returnAction", entryPoint);

            String tagActivationNbr= mailedTagActivationForm.get("tagActivationNbr").toString();
            String driverLicenseNumber= "";
            String driverLicenseState= "";
            String taxId= "";
            request.setAttribute("tagActNbr",tagActivationNbr);

            if (mailedTagActivationForm.get("driverLicenseNumber") != null) {
                driverLicenseNumber = mailedTagActivationForm.get("driverLicenseNumber").toString();
                driverLicenseState = mailedTagActivationForm.get("driverLicenseState").toString();
            }
            if (mailedTagActivationForm.get("taxId") != null)
                taxId = mailedTagActivationForm.get("taxId").toString();

            List<TagDTO> mailedTagDTOs= tagReqDel.getMailedTags(tagActivationNbr,driverLicenseNumber,driverLicenseState,taxId);
            boolean forward=true;
            for (TagDTO tagDTO : mailedTagDTOs) {
                if(saveErrorMessages(request, tagDTO, "saveFailed")){
                    forward=false;
                }
                if (saveAlerts(request, tagDTO)) {
                    saveAlert(request, getLinkMessage(request, domainName, entryPoint));
                    forward = false;
                }
                // TODO: Why initiate a loop then break out on the first iteration?
                break;
            }
            session.setAttribute("mailedTagDTOs",mailedTagDTOs);
            request.setAttribute("transId",mailedTagActivationForm.get("tagActivationNbr"));
            request.setAttribute("mailedTags",mailedTagDTOs);
            request.setAttribute("mailedTagsCount",Integer.valueOf(mailedTagDTOs.size()));
            request.setAttribute("confirm","true");
            if(forward==true){
                request.setAttribute("tagActNbr",mailedTagActivationForm.get("tagActivationNbr"));
                return mapping.findForward("success");
            }//else
            return mapping.findForward("failure");
        } else if(page != null && page.equalsIgnoreCase("page2")) {
            Boolean confirm = Boolean.FALSE;
            if (entryPoint != null)
                request.setAttribute("returnAction", entryPoint);
            String transId = request.getParameter("transId");
            if (transId != null && !transId.equalsIgnoreCase("-1")) {
                BaseDTO baseDTO = tagReqDel.activateMailedTags(transId);
                if (saveErrorMessages(request, baseDTO, "saveFailed"))
                    confirm = Boolean.TRUE;
                if (saveAlerts(request, baseDTO))
                    saveAlert(request, getLinkMessage(request, domainName, entryPoint));
                request.setAttribute("transId", transId);
                request.setAttribute("mailedTags", session.getAttribute("mailedTagDTOs"));
                @SuppressWarnings("unchecked")
                List<TagDTO> mailedTagDTOs = (List<TagDTO>)session.getAttribute("mailedTagDTOs");
                request.setAttribute("mailedTagsCount",Integer.valueOf(mailedTagDTOs.size()));
                request.setAttribute("tagActNbr",request.getParameter("tagActNbr"));
                request.setAttribute("confirm", confirm);
                return mapping.findForward("success");
            }
        }
        return mapping.findForward("success");
    }

    private static String getLinkMessage(HttpServletRequest request, String domainName, Object entryPoint) {
        String linkMessage = (entryPoint != null ?
                  "Please click here for the vehicles & EZ TAGs page <a href=\""+ request.getContextPath() +"/accountVehiclesAndTags.do\">vehicles & EZ TAGs</a>"
                : "Please click here for Home page <a href=\""+ domainName +"\">Home</a>"
        );
        return linkMessage;
    }
}
