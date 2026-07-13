package com.etcc.csc.presentation.action.violation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.etcc.csc.delegate.ViolationDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;
import com.etcc.csc.util.HttpDataUtil;
import com.etcc.csc.util.SessionUtil;

public class ViolatorLoginAction extends CSCBaseAction {
	private static final Logger logger = Logger.getLogger(ViolatorLoginAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception {

        DynaValidatorActionForm violatorForm = (DynaValidatorActionForm)form;
        @SuppressWarnings("unchecked")
        Map<String,String> reqParams = violatorForm.getMap();

        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
        /*String agencyId = ((String)reqParams.get("agencyId"));
          if(agencyId!= null && !agencyId.equals("")){
          agencyId = agencyId;
          }else{
          agencyId = "HarrisCounty";
          }*/
//        String agency = reqParams.get("agencyId");
//        if (agency == null) {
//        	agency = AgencyEnum.AGENCY_HARRIS_COUNTY.name();
//        	logger.debug("Agency is null -- setting to default: " + agency);
//        }
        ViolatorDTO violatorDTO = new ViolationDelegate().loginViolator(
        		acctLoginDto,
        		request.getSession().getId(),
        		HttpDataUtil.getClientIpAddress(request),
        		HttpDataUtil.getUserAgentAttributes(request),
        		reqParams.get("invoiceId"),
        		/*pay installment*/
        		//reqParams.get("paymentPlanId"),
        		reqParams.get("licPlateNbr").toUpperCase(),
        		reqParams.get("licPlateState").toUpperCase(),""
        );
//        logger.debug("Agency ID ==>" + agency);
//        request.setAttribute("agencyId",agency);
        //request.setAttribute("county",reqParams.get("county"));
        //request.getSession().removeAttribute(InvoiceDisplayAction.SESSION_KEY_ACCOUNT_OPTION);
        if(saveErrorMessages(request, violatorDTO, "violationError")){
            return mapping.findForward("failure");
        }

        violatorDTO.calculatePreliminaryTotals();

        SelectedInvoiceForm allInvoicesForm = new SelectedInvoiceForm();
        allInvoicesForm.setViolatorDTO(violatorDTO);

        Object entryPoint = request.getParameter("returnAction");
        if (entryPoint != null) {
            allInvoicesForm.setReturnAction(entryPoint.toString());
        }

        HttpSession session = request.getSession();
        session.setAttribute("allInvoicesForm", allInvoicesForm);
        SelectedInvoiceForm selectedInvoiceForm = allInvoicesForm.clone();
        session.setAttribute("selectedInvoiceForm", selectedInvoiceForm);
        if (logger.isTraceEnabled()) {
        	logger.debug(allInvoicesForm);
        	logger.debug(selectedInvoiceForm);
        }

        // Save the Login object in the Session
        SessionUtil.setSessionAccountLogin(session, violatorDTO.getAccountLoginDTO());
        HttpDataUtil.setDbSessionIdCookie(response, violatorDTO.getAccountLoginDTO().getDbSessionId());
        //HttpDataUtil.setAcctIdCookie(response, violatorDTO.getAccountLoginDTO().getAcctId()+"");
        SessionUtil.setLoginEntryPoint(request.getSession(), AccountLoginDTO.LoginType.IN.toString());

        return mapping.findForward("success");
    }
}
