/*
 * Copyright 2015 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.signup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.datatype.BillingContext;
import com.etcc.csc.presentation.form.ContactInfoForm;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.UIDateUtil;

public class SaveVehicleAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(SaveVehicleAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

        TagRequestForm tagRequestForm = (TagRequestForm)form;
        HttpSession session = request.getSession();
        String showContinueMsg = request.getParameter("showContinueMsg");
          /*
          boolean valid = validateData(tagRequestForm, request);
          if (! valid) {
              return mapping.findForward("failure");
          }
          */
        request.setAttribute("radioValue", "false");
        if( !"false".equalsIgnoreCase( request.getParameter("refresh"))) {
            if(!isTokenValid(request)) {
                logger.error("This code is NOT implemented! Action token is invalid!");
                logger.debug("AccountLogin: " + SessionUtil.getSessionAccountLogin(session));
                logger.info("TagRequestForm: " + tagRequestForm);
                String saved = (String) session.getAttribute(Globals.TRANSACTION_TOKEN_KEY);
                logger.info("Token: " + saved);

              //TODO retrieve the vehicles on account and set those in the form
              tagRequestForm.clearForm();
              return mapping.findForward("success");
            }

            TagDTO tagDTO = tagRequestForm.newTagDTO();
            tagDTO.setTagSalesAmt(tagRequestForm.getTagSaleAmount());
            tagDTO.setDepositAmt(tagRequestForm.getDepositAmount());
            tagDTO.setTotalAmt(tagRequestForm.getTotalAmount());
            tagDTO.setShowContinueMsg(showContinueMsg);

            String pbpParam = request.getParameter("pbp");
            if (!StringUtil.isEmpty(pbpParam)) {
                boolean theValue = Boolean.parseBoolean(pbpParam);
                tagRequestForm.setPbpTag(theValue);
                if (!tagRequestForm.isActivePbpTagExists()) {
                    tagRequestForm.setActivePbpTagExists(theValue);
                }
            }else{
                tagRequestForm.setPbpTag(false);
            }
            if (tagRequestForm.isPbpTag()) {
                tagDTO.setActivePbpTagExist(true);
                String start_date = null;
                String end_date = null;
                try{
                    // Converting the date format
                    logger.debug("EZPlate before format ===> Start and End date " + tagRequestForm.getPbpStart() + " " + tagRequestForm.getPbpEnd());
                    start_date = UIDateUtil.convertDateFormat(tagRequestForm.getPbpStart());
                    end_date = UIDateUtil.convertDateFormat(tagRequestForm.getPbpEnd());
                    logger.debug("EZPlate after format ===> Start and End date " + start_date + " " + end_date);
                    request.setAttribute("EZplate",Boolean.TRUE);
                }catch(Exception e){
                    logger.info(e.toString(), e);
                }
                tagDTO.setPbpStart(start_date);
                tagDTO.setPbpEnd(end_date);
                tagDTO.setTagTypeCode("P");
            }
            else {
                tagDTO.setActivePbpTagExist(false);
                tagDTO.setPbpStartDate(null);
                tagDTO.setPbpEndDate(null);
                request.setAttribute("EZplate",Boolean.FALSE);
            }
            session.setAttribute("tagRequestFormEmail",tagRequestForm);

            boolean saved = saveVehicle(tagDTO, tagRequestForm, request);

            if (saved) {
                if (tagDTO.isDup()) {
                    request.setAttribute("dupLic", tagDTO.getLicState()+ "-"+ tagDTO.getLicPlate());
                }
            } else {
			// 0047 - Suppress the alert message issued by UI for adding violation LP to EZTag/EZPlate
			//        instead display from backend
               /** if (tagDTO.isViolationExist()) {
                    ActionMessages messages = getMessages(request);
                    ActionMessage msg = new ActionMessage("tagRequestForm.violation.error",
                            tagDTO.getLicState()+ "-"+ tagDTO.getLicPlate(), "javascript:payViolations();", new AppDelegate().getContactPhoneNumber());
                    messages.add("saveFailed", msg);
                    saveMessages(request, messages);
                } **/
                return mapping.findForward("failure");
            }


            request.setAttribute("depositAmt", Double.valueOf(tagRequestForm.getDepositAmount()));
            request.setAttribute("totalAmt", Double.valueOf(tagRequestForm.getTotalAmount()));
            request.setAttribute("retailTransId", Long.valueOf(tagRequestForm.getRetailTransId()));
            SessionUtil.setVehicles(request, tagRequestForm.copySavedVehicles());

            tagRequestForm.setTagSaleAmount(SignupBillingPANAction.
            		getActiviationFee(SessionUtil.getSessionAccountLogin(session), tagRequestForm));
            request.setAttribute("tagSalesAmt", Double.valueOf(tagRequestForm.getTagSaleAmount()));


            session.setAttribute("tagRequestForm", tagRequestForm);
            if (tagDTO.getAlerts()!=null && tagDTO.getAlerts().length>0) {
                return mapping.findForward("failure");
            }
            return mapping.findForward("success");
        }
        else {

            int vehicleIndexToModify = tagRequestForm.getVehicleIndexToModify();
            List<TagDTO> savedVehicles = tagRequestForm.getSavedVehicles();
            TagDTO tagDTO = savedVehicles.get(vehicleIndexToModify);
            if (tagRequestForm.isDeleteVehicle()) {
                 saveVehicle(tagDTO, tagRequestForm, request);
                 //tagRequestForm.setTagSaleAmount(tagDTO.getTagSalesAmt());
                 tagRequestForm.setRebillAmount(tagDTO.getTagSalesAmt());
                 tagRequestForm.setDepositAmount(tagDTO.getDepositAmt());
                 tagRequestForm.setTotalAmount(tagDTO.getTotalAmt());
                 if(tagRequestForm.getRetailTransId()<=0)
                    tagRequestForm.setRetailTransId(tagDTO.getTransactionId());

                BillingContext billingContext = ((BillingContext) session.getAttribute("billingContext"));
                if (billingContext != null)
                    billingContext.update(tagRequestForm);
                //weblogic upgrade cluster issues fix
                session.setAttribute("billingContext" , billingContext);
                //weblogic upgrade issues fix ends
                return mapping.findForward("confirmation");
            }
            else {
                tagRequestForm.initialize(tagDTO, true);
               /* VehicleMakeDelegate vehicleMakeDelegate=new VehicleMakeDelegate();
                Collection col=vehicleMakeDelegate.getVehicleMakes();
                Iterator it=col.iterator();

                while(it.hasNext())
                {
                  VehicleMakeDTO VehicleMakeDTO=(VehicleMakeDTO)it.next();
                  if(VehicleMakeDTO.getVehicleMake().equalsIgnoreCase(str))
                  {
                    str = VehicleMakeDTO.getVehicleMake();
                    break;
                  }
                }  */
                if (StringUtil.equals(request.getAttribute("confirmEdit"), "confirmEdit")) {
                    tagRequestForm.setTagAmount(tagDTO.getTagAmount());
                    //tagRequestForm.setTagSaleAmount(tagDTO.getTagSalesAmt());
                    tagRequestForm.setRebillAmount(tagDTO.getTagSalesAmt());
                    tagRequestForm.setTotalAmount(tagDTO.getTotalAmt());
                    tagRequestForm.setDepositAmount(tagDTO.getDepositAmt());
                }
                if (tagRequestForm.getRetailTransId() <= 0) {
                  String retailTransId = request.getParameter("retailTransId");
                  if (!StringUtil.isEmpty(retailTransId))
                      tagRequestForm.setRetailTransId(Long.parseLong(retailTransId));
                  else
                    tagRequestForm.setRetailTransId(tagDTO.getTransactionId());
                }
                if (tagRequestForm.updatePbpTag(tagDTO)) {
                    tagRequestForm.setEzTagOrEZPlate("pbpvehicle");
                    tagRequestForm.setActivePbpTagExists(false);
                    request.setAttribute("editActivePbpTagExists", Boolean.TRUE);
                } else {
                    tagRequestForm.setEzTagOrEZPlate("ezTagvehicle");
                    request.setAttribute("radioValue", "true");
                }
                request.setAttribute("fromConfirmation", Boolean.TRUE);
                return mapping.findForward("success");
            }
        }
    }

    private boolean saveVehicle(TagDTO tagDTO, TagRequestForm tagRequestForm, HttpServletRequest request) throws EtccSecurityException, EtccException {
        if (tagDTO == null)
            return false;

        boolean success = false;
        HttpSession session = request.getSession();
        List<TagDTO> savedVehicles = tagRequestForm.getSavedVehicles();
        int vehicleIndexToModify = tagRequestForm.getVehicleIndexToModify();

        TagDelegate delegate = new TagDelegate();
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(session);
        if (loginDTO == null)
          throw new EtccSecurityException("session timed out in SaveVehicleAction");

        long transId;
        ContactInfoForm contactForm = (ContactInfoForm) session.getAttribute("contactInfoForm");
        if (contactForm != null && contactForm.getRetailTransId() != null)
            transId = contactForm.getRetailTransId().longValue();
        else
            transId = tagRequestForm.getRetailTransId();

        tagDTO.setTransactionId(transId);
        tagDTO.setAcctId(loginDTO.getAcctId());

        if (savedVehicles == null) {
            savedVehicles = new ArrayList<TagDTO>();
        }

        if (vehicleIndexToModify ==-1) {
            //logger.debug("The Vehicle is a EZ Plate addTag " + tagDTO.isActivePbpTagExist());

            logger.info("addTag is called");
            tagDTO = delegate.addTag(loginDTO, tagDTO, SessionUtil.getPosId(request));
            logger.info("addTag returned: deposit: " + tagDTO.getDepositAmt() + ", tag fee: " + tagDTO.getTagSalesAmt() + ", current total charge: " + tagDTO.getTotalAmt());

            if (saveErrorMessages(request, tagDTO, "saveFailed")) {
              if (tagRequestForm.updatePbpTag(tagDTO)) {
                  tagRequestForm.setEzTagOrEZPlate("pbpvehicle");
                  tagRequestForm.setActivePbpTagExists(false);
              }
              return false;
            }

            saveAlerts(request, tagDTO);
            if (tagDTO.isViolationExist()) {
              if (tagRequestForm.updatePbpTag(tagDTO)) {
                  tagRequestForm.setEzTagOrEZPlate("pbpvehicle");
                  tagRequestForm.setActivePbpTagExists(false);
              }
              return false;
            }

            savedVehicles.add(tagDTO);
			  //D13868 08/19/2013 vgovindaswamy EZNull plate issue

			  if (tagRequestForm.updatePbpTag(tagDTO)) {
                tagRequestForm.setEzTagOrEZPlate("pbpvehicle");
               }
            else
            {
          	  tagRequestForm.setEzTagOrEZPlate("ezTagvehicle");
            }
            success = true;
        }
        else {
            if(tagRequestForm.isDeleteVehicle()) {
                tagDTO = tagRequestForm.getSavedVehicle(vehicleIndexToModify);
                tagDTO.setTransactionId(transId);
                tagDTO.setAcctId(loginDTO.getAcctId());
                boolean delete=false;
                if(tagDTO.isPbpTag())
                {
                  delete= true;
                  logger.debug("Ezplate delete");
                }
                tagDTO = delegate.deleteTag(loginDTO, tagDTO, SessionUtil.getPosId(request));
                if (saveErrorMessages(request, tagDTO, "saveFailed")){
                   return false;
                }
                saveAlerts(request, tagDTO);
                savedVehicles.remove(vehicleIndexToModify);
                if(delete){
                  tagRequestForm.setEzTagOrEZPlate("");
                  tagRequestForm.setActivePbpTagExists(false);
                  tagRequestForm.setPbpTag(false);
                }
                success = false;
            }
            else {
          	  logger.debug("The Vehicle is a EZ Plate updateTag " + tagDTO.isActivePbpTagExist());
              tagDTO = delegate.updateTag(loginDTO, tagDTO, SessionUtil.getPosId(request));
              tagDTO.setTagSalesAmt(tagRequestForm.getTagSaleAmount());
              tagDTO.setDepositAmt(tagRequestForm.getDepositAmount());
              tagDTO.setTotalAmt(tagRequestForm.getTotalAmount());
              if (saveErrorMessages(request, tagDTO, "saveFailed")){

				// D13868 08/19/2013 vgovindaswamy EZPlate null value issue begin
				 if (tagRequestForm.updatePbpTag(tagDTO)) {
                      tagRequestForm.setEzTagOrEZPlate("pbpvehicle");
                      tagRequestForm.setActivePbpTagExists(false);
                  } // end
				 return false;
              }
              saveAlerts(request, tagDTO);

              if (tagDTO.isViolationExist()){
				// D13868 08/19/2013 vgovindaswamy EZPlate null value issue begin
                  if (tagRequestForm.updatePbpTag(tagDTO)) {
                      tagRequestForm.setEzTagOrEZPlate("pbpvehicle");
                      tagRequestForm.setActivePbpTagExists(false);
                  } // end
                  return false;
                }

              if(savedVehicles.size() > 0)
                 savedVehicles.remove(vehicleIndexToModify);
              savedVehicles.add(vehicleIndexToModify, tagDTO);

				// D13868 08/19/2013 vgovindaswamy EZPlate null value issue begin

				 if (tagRequestForm.updatePbpTag(tagDTO)) {
                  tagRequestForm.setEzTagOrEZPlate("pbpvehicle");
                 }
              else
              {
            	  tagRequestForm.setEzTagOrEZPlate("ezTagvehicle");
              } // end

              success = true;
            }
        }

        tagRequestForm.setSavedVehicles(savedVehicles);

        resetToken(request);
        saveToken(request);
        tagRequestForm.clearForm();
        //tagRequestForm.setTagSaleAmount(tagDTO.getTagSalesAmt());
        tagRequestForm.setRebillAmount(tagDTO.getTagSalesAmt());
        tagRequestForm.setDepositAmount(tagDTO.getDepositAmt());
        tagRequestForm.setTotalAmount(tagDTO.getTotalAmt());
        tagRequestForm.setRetailTransId(tagDTO.getTransactionId());

        return success;
  }

    private void saveAlerts(HttpServletRequest request, TagDTO tag) {
        boolean dmvAlert = false;
        if (tag != null) {
            AlertDTO[] alerts = tag.getAlerts();
            if (alerts != null && alerts.length > 0) {
            	// D Commented out the code because the message change
//                String strLicence = tag.getLicPlate();
//                // may need to add DMV alert message in addition to existing alerts (length+1)
//                String[] messages = new String[alerts.length+1];
//                Pattern p = Pattern.compile("<a href.*?>");
//                Pattern p1 = Pattern.compile("p_lic_plate=[^s]*");
////                for (int i = 0; i < alerts.length; i++) {
//                    AlertDTO alertDTO = alerts[i];
//                    String text = alertDTO.getAlertMsg();
//                    logger.debug("before Alert messages **** :"+text);
//                    Matcher m = p.matcher(text);
//                    String licNum = null;
//                    if (m.find()) {
//                        String tagToken = m.group();
//                        Matcher m1 = p1.matcher(tagToken);
//                        if (m1.find()) {
//                            licNum = m1.group();
//                        }
//                        String page = request.getParameter("page");
//                        if (page == null || !page.equalsIgnoreCase("manage"))
//                            page = "signup";
//
//                        String url = "/generatePlateReminder.do?page="+page+"&p_lic_plate=";
//                        text = text.replace("/generatePlateReminder.do?p_lic_plate=",url);
//                    }
//                    if (licNum != null) {
//                        text=text.replaceFirst("license plate","license plate <b>"+strLicence.toUpperCase()+"</b> ");
//                        dmvAlert = true;
//                    }
//                    logger.debug("Alert messages **** :"+text);
//                    messages[i] = text;
//                }
//                if (dmvAlert) {
//                    messages[alerts.length] = "Click Continue button again to proceed for next step.";
//                }
                saveAlerts(request, alerts);
            }
        }
    }
}
