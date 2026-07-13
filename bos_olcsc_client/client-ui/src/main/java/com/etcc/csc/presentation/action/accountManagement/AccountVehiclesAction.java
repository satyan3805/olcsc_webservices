/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;

public class AccountVehiclesAction extends CSCBaseAction {
	
	private static final Logger logger = Logger.getLogger(AccountVehiclesAction.class);
	
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) 
                                 throws Exception 
     {
        HttpSession session = request.getSession();
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(session);

         if (loginDTO == null)
             throw new EtccSecurityException("session timed out in AccountVehicleAction");

        String searchString = request.getParameter("searchString");
        logger.debug("searchString"+  searchString);
        AccountTagsDTO tagsDTO =  new TagDelegate().getAccountTags(loginDTO, searchString);

        AccountDTO acctDto = SessionUtil.getAcctDTO(request);
        acctDto = new AccountDelegate().getAccountStatus(loginDTO, acctDto);

        if (acctDto.isSuspensionFlag()) {
            ActionMessages messages = getMessages(request);
            if (acctDto.isViolationFlag()) {
                 ActionMessage msg = new ActionMessage("account.suspended.with.violation", 
                         acctDto.getViolationLicPlatesDisplay(), 
                         request.getContextPath()+"/violatorLoginDisplay.do?returnAction=accountVehiclesAndTags");
                messages.add(DEFAULT_ALERTS_PROPERTY, msg);
            } else {
                 ActionMessage msg = new ActionMessage("account.suspended.without.violation");
                messages.add(DEFAULT_ALERTS_PROPERTY, msg);
                String vehicleChangeAttr = StringUtil.safeToString(request.getAttribute("vehicleChange"));
                if (vehicleChangeAttr != null) {
                    msg = new ActionMessage("The vehicle with Licence Plate Number " + vehicleChangeAttr + " has been edited or status has been changed");
                    messages.add(DEFAULT_ALERTS_PROPERTY, msg);
                }
            }
             saveMessages(request, messages);
        }

        if (searchString != null)
            request.setAttribute("searchString", searchString);

        if (acctDto.isSuspensionFlag()) {
            tagsDTO.suspendAllTags();
        }

        if (tagsDTO.isActivePbpTagExists())
            tagsDTO.activatePbpTagFlags();

        request.setAttribute("uploadAccess",Boolean.valueOf(tagsDTO.isMultiVehicleAllowed()));
        request.setAttribute("accountTags", tagsDTO.getAccountTags());
        request.setAttribute("pbpTags", tagsDTO.getPbpTags());
        request.setAttribute("accountTagsCount", Integer.valueOf(tagsDTO.getAccountTagCount()));
        request.setAttribute("pbpTagsCount", Integer.valueOf(tagsDTO.getPbpTagCount()));
        request.setAttribute("activePbpTagExists", Boolean.valueOf(tagsDTO.isActivePbpTagExists()));

        /*List acctTags = new ArrayList();
        if(tagsDTO.getPbpTags()!= null && tagsDTO.getPbpTags().size() > 0){
        List pbptags = (List) tagsDTO.getPbpTags();
        for(int idx =0 ; idx < pbptags.size();idx++)
            acctTags.add((TagDTO)(pbptags.get(idx)));
        }*/
        session.setAttribute("accountTags", tagsDTO.getAccountTags());
         request.setAttribute("acctLoginInfo", loginDTO);
       // session.setAttribute("activePbpTags", acctTags);
        //session.setAttribute("activeTags",acctTags);
        
      //  logger.debug(tagDTO.getAcctTagStatus() + " " + tagDTO.getTagTypeCode());
        
        if (StringUtil.stringToBoolean(request.getParameter("preview"))) {
            request.setAttribute("pageSize", Integer.valueOf(0));
            request.setAttribute("preview", Boolean.TRUE);
             return mapping.findForward("preview");
        } else {
            request.setAttribute("pageSize", Integer.valueOf(50));
            request.setAttribute("preview", Boolean.FALSE);
            return mapping.findForward("success");
        }
    }

/*
     private ActionMessages saveAlerts(HttpServletRequest request, Collection alerts) {        
            ActionMessages messages = new ActionMessages(); 
            if(alerts != null) {
            for (Iterator it=alerts.iterator(); it.hasNext(); ) {               
                 com.etcc.csc.accountalert.dto.AlertDTO alertDTO =(com.etcc.csc.accountalert.dto.AlertDTO)it.next();
                 String text = alertDTO.getAlertMsg();
                Pattern p = Pattern.compile("\\{0\\}");
                Matcher m = p.matcher(text);
                if (m.find()) {
                    String url = request.getContextPath()+"/violatorLoginDisplay.do?returnAction=accountVehiclesAndTags";
                    text = m.replaceFirst("\""+url+ "\"");
                }                
                ActionMessage msg = new ActionMessage("account.suspended.with.violation", text);
                messages.add("alerts", msg);
            }
            //saveMessages(request, messages);
            }
             return messages;
        }
*/
}
