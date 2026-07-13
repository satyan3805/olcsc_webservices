/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;

public class AccountAddPbpVehicleAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccountAddPbpVehicleAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

        TagRequestForm tagRequestForm = (TagRequestForm) form;

        /*
        boolean valid = validateData(tagRequestForm, request);
        if (! valid) {
            return mapping.findForward("failure");
        }
        */
      if(request.getParameter("added")==null) {
        TagDTO tagDTO = tagRequestForm.newTagDTO(false);
        tagDTO.setPbpStart(tagRequestForm.getPbpStart());
        tagDTO.setPbpEnd(tagRequestForm.getPbpEnd());
        tagDTO.setTagTypeCode("P");

        TagDelegate delegate = new TagDelegate();
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());

        if (loginDTO == null)
            throw new EtccSecurityException("Session Timed out in AccountAddPbpVehicleAction");

        tagDTO.setTransactionId(-1);
        tagDTO.setAcctId(loginDTO.getAcctId());

        tagDTO = delegate.addTag(loginDTO, tagDTO, SessionUtil.getPosId(request));

      /*  if (tagDTO.isViolationExist()) {
       /*     ActionMessages messages = getMessages(request);
            ActionMessage msg = new ActionMessage("tagRequestForm.violation.error",
              tagDTO.getLicState()+ "-"+ tagDTO.getLicPlate(), "javascript:payViolations();", new AppDelegate().getContactPhoneNumber());
            messages.add("saveFailed", msg);
            saveMessages(request, messages);
        	//joe 03/03 start
            if (tagDTO.getAlerts()!=null && tagDTO.getAlerts().length>0)
            {
                saveAlerts(request,tagDTO.getAlerts());
                request.setAttribute("added",Boolean.TRUE);
                return mapping.findForward("failure");
            }
           // return mapping.findForward("failure");
          //joe 03/03 end
        } */

        if (tagDTO.isDup()) {
            request.setAttribute("dupLic", tagRequestForm.getState()+ "-"+ tagRequestForm.getLicensePlate());
        }
        if(saveErrorMessages(request, tagDTO, "saveFailed")){
            return mapping.findForward("failure");
        } //else
        //No Error messages yet.
        BaseDTO resultDTO = new TagDelegate().confirmAddTags(loginDTO, tagDTO.getTransactionId(), TagDTO.DeliveryMethod.MAIL,null);
        if (saveErrorMessages(request, resultDTO, "saveFailed")){
            return mapping.findForward("failure");
        }

        if (tagDTO.getAlerts()!=null && tagDTO.getAlerts().length>0)
        {
            saveAlerts(request,tagDTO.getAlerts());
            request.setAttribute("added",Boolean.TRUE);
            return mapping.findForward("failure");
        }
      }
        request.setAttribute("setEvent3", Boolean.TRUE);
        return mapping.findForward("success");
    }

//    private boolean validateData(TagRequestForm tagRequestForm, HttpServletRequest request) throws EtccException {
//        String make = tagRequestForm.getMake();
//        logger.debug("AccountAddPbpVehicleAction.make=" + make);
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

    @Override
    protected boolean saveAlerts(HttpServletRequest request, AlertDTO[] alerts) {
        if(alerts != null) {
            ActionMessages messages = getMessages(request);
            for (AlertDTO alertDTO : alerts) {
                //AccountAlertDetailBean accountAlertDetailBean = (AccountAlertDetailBean)it.next();
                //String text = accountAlertDetailBean.getAlertText();
                 String text =  alertDTO.getAlertMsg();
                   logger.debug("before Alert messages **** :"+text);
                    Pattern p = Pattern.compile("<a href.*?>");
                    Matcher m = p.matcher(text);
                    String licNum = null;
                    if (m.find()) {
                        String tagToken = m.group();
                        Pattern p1 = Pattern.compile("p_lic_plate=[^s]*");
                        Matcher m1 = p1.matcher(tagToken);
                        if (m1.find()) {
                            licNum = m1.group();
                        }
                        String page="signup";
                        if(request.getParameter("page")!=null && request.getParameter("page").equalsIgnoreCase("manage") )
                            page="manage";
                        String url = "<a href=\""+request.getContextPath() + "/generatePlateReminder.do?page="+page+"&" + licNum;
                        text = m.replaceFirst(url);
                    }
                    if(licNum!=null) {
                        String [] str=licNum.split("=");
                        String subStr=str[1].substring(0,str[1].length()-2);
                        text=text.replaceFirst("license plate","license plate <b>"+subStr+"</b> ");
                    }
                    ActionMessage msg = new ActionMessage("error.generic", text);

                logger.debug("Alert messages **** :"+text);
                messages.add("alerts", msg);
            }
            saveMessages(request, messages);
            return true;
        }
        return false;
    }
}
